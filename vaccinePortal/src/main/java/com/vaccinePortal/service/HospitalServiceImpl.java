package com.vaccinePortal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.entities.Hospital;
import com.vaccinePortal.entities.Vaccine;
import com.vaccinePortal.repository.HospitalRepository;
import com.vaccinePortal.repository.VaccineRepository;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private VaccineRepository vaccineRepo;
	@Autowired
	private HospitalRepository hospitalRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<HospitalDTO> getAllHospitals() throws Exception {
		List<HospitalDTO> hospitals = new ArrayList<>();
		for(Hospital h  : hospitalRepo.findAll()) {
			hospitals.add(mapper.map(h, HospitalDTO.class));
		}
		return hospitals;
	}

	@Override
	public HospitalDTO addHospital(HospitalDTO hospitalDTO) {
		Hospital h = mapper.map(hospitalDTO , Hospital.class);
		return mapper.map(hospitalRepo.save(h),HospitalDTO.class);
	}
	
	@Override
	public HospitalDTO addVaccinesToHospitals(HospitalDTO hospitalDTO , List<VaccineDTO> vaccines) throws Exception {
		Hospital h = hospitalRepo.findById(hospitalDTO.getId()).orElseThrow(() -> new Exception("No hospital found"));
		Set<Vaccine> vaccineFromDTo =  new HashSet<>();
		
		for(Vaccine v:vaccineRepo.findVaccinesByIds(vaccines.stream().map(v -> v.getId()).toList())){
			v.getHosiptals().add(h);
			vaccineFromDTo.add(v);
		}
		System.out.println("vacciness "+vaccineFromDTo);
		vaccineRepo.saveAll(vaccineFromDTo);
		
		return mapper.map(hospitalRepo.save(h),HospitalDTO.class);
	}
	
}
