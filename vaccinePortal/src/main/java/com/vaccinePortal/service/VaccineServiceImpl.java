package com.vaccinePortal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.RegisterVaccineDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.dto.VaccineHospResponse;
import com.vaccinePortal.entities.Vaccine;
import com.vaccinePortal.repository.HospitalRepository;
import com.vaccinePortal.repository.VaccineRepository;

@Service
@Transactional
public class VaccineServiceImpl implements VaccineService{
	@Autowired
	private VaccineRepository vaccineRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<VaccineDTO> getAllVaccines() throws Exception {
		List<VaccineDTO> vaccines = new ArrayList<>();
		for(Vaccine v  : vaccineRepo.findAll()) {
			vaccines.add(mapper.map(v, VaccineDTO.class));
		}
		return vaccines;
	}

	@Override
	public VaccineDTO addVaccine(VaccineDTO vaccineDTO) {
		System.out.println("vaccine from client add"+vaccineDTO);
		Vaccine v = mapper.map(vaccineDTO , Vaccine.class);
		System.out.println("vaccine to add"+v);
		return mapper.map(vaccineRepo.save(v),VaccineDTO.class);
	}

	@Override
	public VaccineHospResponse getHospitalsForVaccine(Long id) throws Exception {
		VaccineHospResponse returnDTO = new VaccineHospResponse();
		Vaccine vc = vaccineRepo.findById(id).orElseThrow(()-> new Exception("No vaccine found"));
		returnDTO.setVaccineDTO(mapper.map(vc,VaccineDTO.class));
		returnDTO.setHospitals(vc.getVaccineStock().stream().map(vs -> mapper.map(vs.getHospital(),HospitalDTO.class)).toList());
		returnDTO.setQuantities(vc.getVaccineStock().stream().collect(Collectors.toMap(v->v.getHospital().getId().toString(), v->v.getQty())));
		return returnDTO;
	}
	
	

}
