package com.takeoff.controller;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.RazorpayException;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.LoginStatusDTO;
import com.takeoff.model.OrderDTO;
import com.takeoff.model.RefererCodeDTO;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.StructureDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.service.CouponService;
import com.takeoff.service.CustomerService;
import com.takeoff.service.DisplayService;
import com.takeoff.service.LoginService;
import com.takeoff.service.RazorpayService;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RazorpayService razorpayService;
	
	
	@Autowired
	LoginService loginService;
	
	
	@Autowired
	DisplayService displayService;
	
	
	@Autowired
	CouponService couponService;
	
	

	@RequestMapping("/getImage")
	public ImageStatusDTO getImage(@RequestParam("file") MultipartFile file) throws IOException
	{
	
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		String image =   new String(Base64.encodeBase64(file.getBytes()), "UTF-8");
		
		imageStatus.setImage(image);
		imageStatus.setStatus(true);
		
		
		return imageStatus;
	}
	
	@RequestMapping("/getImages")
	public List<ImageStatusDTO> getImages() throws UnsupportedEncodingException 
	{
		return couponService.getImages();
	}
	
	@RequestMapping("/uploadCoupon")
	public ImageStatusDTO uploadCoupon(@RequestParam("file") MultipartFile file) throws IOException
	{
		
		return couponService.uploadCoupon(file);
		
	}
	
	@RequestMapping("/getTreeStructure")
	public StructureDTO getTreeStructure(@RequestParam("type") String type)
	{
	return displayService.getTreeStructure(Integer.parseInt(type));
	}
	@RequestMapping("/login")
	public LoginStatusDTO login(@RequestParam("userid") String userid, @RequestParam("password") String password)
	{
		
		return loginService.login(userid,password);
		
	}
	
	@RequestMapping("/checkRefererId")
	public RefererCodeDTO checkRefererId(@RequestParam("refererid") String refererid)
	{
		
		Boolean status = customerService.checkRefererId(refererid);
		RefererCodeDTO sendStatus = new RefererCodeDTO(status);
		return sendStatus;
	}
	
	
	@RequestMapping("/subscribe")
	public StatusDTO subscribe(@RequestBody SubscriptionDTO subscription)
	{
		
		StatusDTO statusDto = new StatusDTO();
		
		try
		{
		Boolean paymentStatus =razorpayService.subscribe(subscription);	
		
		
		if(paymentStatus)
		{
			statusDto = customerService.createCustomer(subscription);
		 if(!statusDto.getStatus())
		 {
			 statusDto.setCustomerId(0L);
			 statusDto.setReferCode("0");
			 statusDto.setMessage(" Mapping Failed. If Amount Debited, it will be refunded in 5 Business Days. ");
		 }
		}
		else
		{
			statusDto=new StatusDTO(subscription);
		}
		}
		catch(RazorpayException | IOException ex)
		{
			statusDto=new StatusDTO(subscription);
			statusDto.setMessage(" User Creation Failed. If Amount Debited, it will be refunded in 5 Business Days. ");
		}
		
		return statusDto;
	}
	
	@RequestMapping(value="/getOrderId")
	public OrderDTO getOrderId() throws RazorpayException
	{
		String orderid=razorpayService.getOrderId();
OrderDTO sendOrder = new OrderDTO(orderid);
return sendOrder;



	}

}
