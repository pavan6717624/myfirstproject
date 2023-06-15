package com.takeoff.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.model.VendorList;
import com.takeoff.repository.RolesRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class VendorService {
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	UtilService utilService;
	

	@Autowired
	LogoService logoService;

	    
	
	public VendorDetailsDTO getVendorDetails(Long vendorId)
	{
		return new VendorDetailsDTO(vendorDetailsRepository.findByUserId(vendorId).get());
	}
	
	public VendorDetails getVendorDetails(String vendorId)
	{
		return vendorDetailsRepository.findByUserId(Long.valueOf(vendorId)).get();
	}

	public VendorDetailsDTO getDesignerDetails(Long vendorId)
	{
		return new VendorDetailsDTO(vendorDetailsRepository.findByDesignerId(vendorId).get());
	}

	public List<VendorDetailsDTO> getDesigners() {
		
		return userDetailsRepository.findByRole(rolesRepository.findByRoleName("Designer").get());
	}
	
	public List<VendorDetailsDTO> getInvestors() {
		
		return userDetailsRepository.findByRole(rolesRepository.findByRoleName("Investor").get());
	}

public List<VendorDetailsDTO> getVendors() {
		
		return vendorDetailsRepository.getVendors();
	}

public Boolean addDesginer(VendorDetailsDTO designer) throws NoSuchAlgorithmException {
	
	UserDetails user=new UserDetails();
	user.setCity(designer.getCity());
	user.setContact(designer.getContact());
	user.setEmail(designer.getEmail());
	user.setName(designer.getName());
	user.setRole(rolesRepository.findByRoleName("Designer").get());
	String password=utilService.generatePassword(9);
	user.setPassword(utilService.getSHA(password));
	user.setIsDeleted(false);
	user.setIsDisabled(false);
	user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
	
	userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
	{
	
		SecureRandom random = new SecureRandom();
		
		 int randomInt = random.nextInt(10);
		user.setLoginId(Long.valueOf(user.getUserId()+""+randomInt));
		
		userDetailsRepository.save(user);
		
	String text="\nCongrats! Your Designer Account got Created in TakeOff\n"
     		+ "User Id: "+user.getLoginId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff @ www.thetakeoff.in";
	

     utilService.sendMessage(designer.getEmail(), "TakeOff Designer Account", text);
     
     utilService.sendSMS(designer.getContact(),text);
     return true;
	}
	else
	return false;
}

public Boolean addInvestor(VendorDetailsDTO designer) throws NoSuchAlgorithmException {
	
	UserDetails user=new UserDetails();
	user.setCity(designer.getCity());
	user.setContact(designer.getContact());
	user.setEmail(designer.getEmail());
	user.setName(designer.getName());
	user.setRole(rolesRepository.findByRoleName("Investor").get());
	String password=utilService.generatePassword(9);
	user.setPassword(utilService.getSHA(password));
	user.setIsDeleted(false);
	user.setIsDisabled(false);
	user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
	
	userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
	{
	
		SecureRandom random = new SecureRandom();
		
		 int randomInt = random.nextInt(10);
		user.setLoginId(Long.valueOf(user.getUserId()+""+randomInt));
		
		userDetailsRepository.save(user);
		
	String text="\nGreetings! Your Investor Account got Created in TakeOff\n"
     		+ "User Id: "+user.getLoginId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff @ www.thetakeoff.in";
	

     utilService.sendMessage(designer.getEmail(), "TakeOff Investor Account", text);
     
     utilService.sendSMS(designer.getContact(),text);
     return true;
	}
	else
	return false;
}

public Boolean changePassword(String userId, String password, String newpassword) {
	
	Optional<UserDetails> user=userDetailsRepository.findById(Long.valueOf(userId));
	
	Boolean status=false;
	
	System.out.println(user.get().getPassword()+" "+(password));
	
	if(user.isPresent() && user.get().getPassword().equals(password))
	{
		user.get().setPassword(newpassword);
		userDetailsRepository.save(user.get());
		if(user.get().getUserId()!=null)
			status=true;
	}
	
	return status;
}

public Boolean disableDesigner(Long vendorId) {
Optional<UserDetails> user=userDetailsRepository.findById(vendorId);
	
	Boolean status=false;
	
	
	if(user.isPresent())
	{
		user.get().setIsDisabled(!user.get().getIsDisabled());
		userDetailsRepository.save(user.get());
		if(user.get().getUserId()!=null)
			status=true;
	}
	
	return status;
}

public Boolean deleteDesigner(Long vendorId) {
Optional<UserDetails> user=userDetailsRepository.findById(vendorId);
	
	Boolean status=false;
	
	
	if(user.isPresent())
	{
		user.get().setIsDeleted(true);
		userDetailsRepository.save(user.get());
		if(user.get().getUserId()!=null)
			status=true;
	}
	
	return status;
}

public Boolean editDesigner(VendorDetailsDTO designer) {
	
	System.out.println(designer.getVendorId());
	
	UserDetails user=userDetailsRepository.findById(designer.getVendorId()).get();
	user.setCity(designer.getCity());
	user.setContact(designer.getContact());
	user.setEmail(designer.getEmail());
	user.setName(designer.getName());
	
	userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
		  return true;
			
			else
			return false;
}

public Boolean addVendor(VendorDetailsDTO vendor) throws NoSuchAlgorithmException, IOException {
	UserDetails user=new UserDetails();
	user.setCity(vendor.getCity());
	user.setContact(vendor.getContact());
	user.setEmail(vendor.getEmail());
	user.setName(vendor.getName());
	user.setRole(rolesRepository.findByRoleName("Vendor").get());
	String password=utilService.generatePassword(9);
	user.setPassword(utilService.getSHA(password));
	user.setIsDeleted(false);
	user.setIsDisabled(false);
	user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
	
	userDetailsRepository.save(user);
	
	VendorDetails vendorDetails=new VendorDetails();
	vendorDetails.setAddress(vendor.getAddress());
	vendorDetails.setUser(user);
	vendorDetails.setLogo(logoService.createImage("<html><head><style>@page { size: 500px 500px; } @page { margin: 0; } "
			+ "	body { margin: 0; }</style></head><body style='padding:5px;text-align:center;'><h1>"+user.getName().replaceAll("[^a-zA-Z0-9]"," ").replaceAll("\\s{2,}", " ").trim().replaceAll(" ","<br/>")+"</h1></body></html>",true));
	
	vendorDetailsRepository.save(vendorDetails);
	
	if(user.getUserId() != null)
	{
		
		SecureRandom random = new SecureRandom();
		
		 int randomInt = random.nextInt(10);
		user.setLoginId(Long.valueOf(user.getUserId()+""+randomInt));
		
		userDetailsRepository.save(user);
	
	String text="\nCongrats! Your Vendor Account got Created in TakeOff\n"
     		+ "User Id: "+user.getLoginId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff @ www.thetakeoff.in";
	

     utilService.sendMessage(vendor.getEmail(), "TakeOff Vendor Account", text);
     
     utilService.sendSMS(vendor.getContact(),text);
     return true;
	}
	else
	return false;
}

public Boolean disableVendor(Long vendorId) {
Optional<UserDetails> user=userDetailsRepository.findById(vendorId);
	
	Boolean status=false;
	
	
	if(user.isPresent())
	{
		user.get().setIsDisabled(!user.get().getIsDisabled());
		userDetailsRepository.save(user.get());
		if(user.get().getUserId()!=null)
			status=true;
	}
	
	return status;
}

public Boolean deleteVendor(Long vendorId) {
Optional<UserDetails> user=userDetailsRepository.findById(vendorId);
	
	Boolean status=false;
	
	
	if(user.isPresent())
	{
		user.get().setIsDeleted(true);
		userDetailsRepository.save(user.get());
		if(user.get().getUserId()!=null)
			status=true;
	}
	
	return status;
}

public Boolean editVendor(VendorDetailsDTO vendor) throws IOException {
	
	
	VendorDetails vendorDetails=vendorDetailsRepository.findByUserId(vendor.getVendorId()).get();
	vendorDetails.setAddress(vendor.getAddress());
	//vendorDetails.setLogo(logoService.createImage("<html><body style='padding:10;text-align:center;'><h1>"+vendor.getName()+"</h1></body></html>",true));
	
	vendorDetailsRepository.save(vendorDetails);
	
	UserDetails user=vendorDetails.getUser();
			user.setCity(vendor.getCity());
			user.setContact(vendor.getContact());
			user.setEmail(vendor.getEmail());
			user.setName(vendor.getName());
			
			userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
	{
     return true;
	}
	else
	return false;
}

public List<String> getLogos()
{
	Pageable  paging = PageRequest.of(0, 10);
	return vendorDetailsRepository.getLogos(paging);
}

public List<VendorList> getVendorList(String city)
{
	return vendorDetailsRepository.getVendorList(city);
}
	
	public List<VendorDetailsDTO> getExecutives() {
	
	return userDetailsRepository.findByRole(rolesRepository.findByRoleName("Executive").get());
}


public Boolean addExecutive(VendorDetailsDTO designer) throws NoSuchAlgorithmException {
	
	UserDetails user=new UserDetails();
	user.setCity(designer.getCity());
	user.setContact(designer.getContact());
	user.setEmail(designer.getEmail());
	user.setName(designer.getName());
	user.setRole(rolesRepository.findByRoleName("Executive").get());
	String password=utilService.generatePassword(9);
	user.setPassword(utilService.getSHA(password));
	user.setIsDeleted(false);
	user.setIsDisabled(false);
	user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
	
	userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
	{
	
		SecureRandom random = new SecureRandom();
		
		 int randomInt = random.nextInt(10);
		user.setLoginId(Long.valueOf(user.getUserId()+""+randomInt));
		
		userDetailsRepository.save(user);
		
	String text="\nCongrats! Your Executive Account got Created in TakeOff\n"
     		+ "User Id: "+user.getLoginId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff @ www.thetakeoff.in";
	

     utilService.sendMessage(designer.getEmail(), "TakeOff Executive Account", text);
     
     utilService.sendSMS(designer.getContact(),text);
     return true;
	}
	else
	return false;
}

}
