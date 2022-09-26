package com.vaccinePortal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.vaccinePortal.dto.BookingDTO;
import com.vaccinePortal.dto.BookingResp;
import com.vaccinePortal.dto.BookingStatus;
import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.RegisterVaccineDTO;
import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.dto.VaccineHospResponse;
import com.vaccinePortal.entities.Hospital;
import com.vaccinePortal.entities.UserEntity;
import com.vaccinePortal.entities.Vaccine;
import com.vaccinePortal.entities.VaccineBooking;
import com.vaccinePortal.entities.VaccineStock;
import com.vaccinePortal.repository.HospitalRepository;
import com.vaccinePortal.repository.UserRepository;
import com.vaccinePortal.repository.VaccineBookingRepository;
import com.vaccinePortal.repository.VaccineRepository;
import com.vaccinePortal.repository.VaccineStockRepository;

@Service
@Transactional
public class VaccineServiceImpl implements VaccineService{
	@Autowired
	private VaccineRepository vaccineRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private HospitalRepository hospRepo;
	@Autowired
	private VaccineBookingRepository vbRepo;
	@Autowired
	private VaccineStockRepository vsRepo;
	@Autowired
	private HospitalServiceImpl hospServ;
	@Autowired
	private MailService mail;
	
	@Override
	public List<VaccineDTO> getAllVaccines(Authentication auth) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		List<VaccineDTO> vaccines = new ArrayList<>();
		for(Vaccine v  : vaccineRepo.findAll()) {
			VaccineDTO dto = mapper.map(v, VaccineDTO.class);
			if(user.getAge() <= dto.getMaxAge() && user.getAge() >= dto.getMinAge() && (dto.getGender().equals("") || dto.getGender().equals(user.getGender()))) dto.setEligible(true);
			vaccines.add(dto);
		}
		return vaccines;
	}

	@Override
	public VaccineDTO addVaccine(VaccineDTO vaccineDTO) throws Exception {
		System.out.println("vaccine from client add"+vaccineDTO);
		Vaccine v = mapper.map(vaccineDTO , Vaccine.class);
		if(vaccineRepo.findByName(vaccineDTO.getName()).size()>0) throw new Exception("Vaccine with this name already present");
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

	@Override
	public List<VaccineDTO> getMyEligibleVaccines(Authentication auth) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		System.out.println("age is "+user.getAge());
		List<Vaccine> vaccines = vaccineRepo.findEligibleVaccines(user.getAge(), user.getGender());
		
		return vaccines.stream().map(v -> mapper.map(v,VaccineDTO.class)).map(v->{v.setEligible(true); return v;}).toList();
	}
	
	

	@Override
	public BookingResp bookVaccine(Authentication auth , HospitalDTO hospitalDTO , VaccineDTO vaccineDTO, LocalDate date) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		Vaccine vaccine = vaccineRepo.findById(vaccineDTO.getId()).orElseThrow(()->new Exception("No vaccine Found"));
		Hospital hospital = hospRepo.findById(hospitalDTO.getId()).orElseThrow(()-> new Exception("No Hospital Found"));
		if(date.isBefore(LocalDate.now())) throw new Exception("Booking cannot be made for paste dates");
		VaccineStock vs = vsRepo.findByHospitalAndVaccine(hospital, vaccine).orElseThrow(() -> new Exception("Vaccine is not available in hospital"));
		if(user.getAge() > vaccine.getMaxAge() || user.getAge() < vaccine.getMinAge() || (!vaccine.getGender().equals("") && !vaccine.getGender().equals(user.getGender()))) throw new Exception("You are not eligible for this vaccine");
		VaccineBooking vb = new VaccineBooking();
		vb.setHospital(hospital);
		vb.setVaccine(vaccine);
		vb.setUser(user);
		vb.setDate(date);
		vb.setStatus(BookingStatus.BOOKED);
		vb = vbRepo.save(vb);
		vs.setQty(vs.getQty() -1);
		if(vs.getQty()>0) {
			vsRepo.save(vs);
			System.out.println("savedddd "+vs.getQty());
		}
		else {
//			vsRepo.delete(vs);
			vaccine.getVaccineStock().remove(vs);
			hospital.getVaccineStocks().remove(vs);
			vaccineRepo.save(vaccine);
			hospRepo.save(hospital);
			vsRepo.deleteById(vs.getId());
			System.out.println("deleted");
		}
		return new BookingResp("Booking succesfull with ID : "+vb.getId());
		
	}

	@Override
	public BookingResp cancelBooking(Authentication auth, BookingDTO bookingDTO) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		Vaccine vaccine = vaccineRepo.findById(bookingDTO.getVaccine().getId()).orElseThrow(()->new Exception("No vaccine Found"));
		Hospital hospital = hospRepo.findById(bookingDTO.getHospital().getId()).orElseThrow(()-> new Exception("No Hospital Found"));
		VaccineBooking vb = vbRepo.findById(bookingDTO.getId()).orElseThrow(() -> new Exception("No Booking Found"));
		if(!user.getBookings().contains(vb)) throw new Exception("You dont have access to this booking");
		vb.setStatus(BookingStatus.CANCELLED);
		vbRepo.save(vb);
		hospServ.addVaccinesToHospitals(bookingDTO.getHospital(), Map.of(bookingDTO.getVaccine().getId().toString(),1));
		mail.sendMail(user.getEmail(), "<h1> Booking No "+vb.getId()+"is cancelled </h1>", "Booking cancellation");
		return new BookingResp("Booking with ID : "+vb.getId()+" is cancelled");
	}

	@Override
	public List<BookingDTO> getMyBookings(Authentication auth) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		List<BookingDTO> bookings = new ArrayList<>();
		for(VaccineBooking vb :vbRepo.findByUser(user)) {
			bookings.add(mapper.map(vb,BookingDTO.class));
		}
		return bookings;
	}
	
	

}
