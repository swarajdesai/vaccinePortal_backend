package com.vaccinePortal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.entities.Hospital;
import com.vaccinePortal.entities.Vaccine;
import com.vaccinePortal.entities.VaccineStock;
import com.vaccinePortal.repository.HospitalRepository;
import com.vaccinePortal.repository.VaccineRepository;
import com.vaccinePortal.repository.VaccineStockRepository;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private VaccineRepository vaccineRepo;
	@Autowired
	private HospitalRepository hospitalRepo;
	@Autowired
	private VaccineStockRepository vsRepo;
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
	public HospitalDTO addVaccinesToHospitals(HospitalDTO hospitalDTO , Map<String,Integer> vaccines) throws Exception {
		Hospital h = hospitalRepo.findById(hospitalDTO.getId()).orElseThrow(() -> new Exception("No hospital found"));
		Set<Vaccine> vaccineFromDTo =  new HashSet<>();
		Map<Vaccine,Integer> mapFromDb = new HashMap<>();
		for(String vDTO:vaccines.keySet()) {
			Optional<Vaccine> vaccine = vaccineRepo.findById((Long)Long.parseLong(vDTO));
			if(vaccine.isPresent()) {
				mapFromDb.put(vaccine.get(), vaccines.get(vDTO));
			}
		}
		List<VaccineStock> vsList = new ArrayList<>();
		for(Vaccine v:mapFromDb.keySet()){
			System.out.println(h.getId().getClass()+" "+v.getId().getClass());
			Optional<VaccineStock> optional = vsRepo.findByHospitalAndVaccine(h, v);
			VaccineStock vs = null;
			if(optional.isPresent()) {
				vs = optional.get();
				vs.setQty(vs.getQty()+mapFromDb.get(v));
				vsList.add(vs);
			}else {
				vs = new VaccineStock();
				vs.setHospital(h);
				vs.setVaccine(v);
				vs.setQty(mapFromDb.get(v));
				vsList.add(vs);
			}
			
		}
//		System.out.println("vacciness "+vaccineFromDTo);
		vsRepo.saveAll(vsList);
		h.getVaccineStocks().addAll(vsList);
//		vaccineRepo.saveAll(vaccineFromDTo);
		return mapper.map(hospitalRepo.save(h),HospitalDTO.class);
	}
	
}
