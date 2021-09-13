package com.takeoff.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.VendorDetailsDTO;
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
	
	userDetailsRepository.save(user);
	
	if(user.getUserId() != null)
	{
	
	String text="\nCongrats! Your Designer Account got Created in TakeOff\n"
     		+ "User Id: "+user.getUserId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff";
	

     utilService.sendMessage(designer.getEmail(), "TakeOff Designer Account", text);
     
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
	
	userDetailsRepository.save(user);
	
	VendorDetails vendorDetails=new VendorDetails();
	vendorDetails.setAddress(vendor.getAddress());
	vendorDetails.setUser(user);
	vendorDetails.setLogo(logoService.createImage("<html><body style='padding:5px;text-align:center;'><h1>"+user.getName().replaceAll("[^a-zA-Z0-9]"," ").replaceAll("\\s{2,}", " ").trim().replaceAll(" ","<br/>")+"</h1></body></html>",true));
	
	vendorDetailsRepository.save(vendorDetails);
	
	if(user.getUserId() != null)
	{
	
	String text="\nCongrats! Your Vendor Account got Created in TakeOff\n"
     		+ "User Id: "+user.getUserId()+"\n"
     		+ "Password: "+password+"\n"
     		+ "Login and Enjoy the TakeOff";
	

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
	vendorDetails.setLogo(logoService.createImage("<html><body style='padding:10;text-align:center;'><h1>"+vendor.getName()+"</h1></body></html>",true));
	
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


}
