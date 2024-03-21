package com.takeoff.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.RazorpayException;
import com.takeoff.domain.Category;
import com.takeoff.domain.Contacts;
import com.takeoff.domain.CouponType;
import com.takeoff.domain.HitsReceived;
import com.takeoff.domain.ImageDetails;
import com.takeoff.domain.SubCategory;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.jwt.JwtTokenUtil;
import com.takeoff.model.CouponsRequest;
import com.takeoff.model.CustomerDetailsDTO;
import com.takeoff.model.GstDetails;
import com.takeoff.model.GstDetailsDTO;
import com.takeoff.model.ImageDetailsDTO;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.ImagesRequest;
import com.takeoff.model.KYCDetailsDTO;
import com.takeoff.model.LoginStatusDTO;
import com.takeoff.model.NotificationDTO;
import com.takeoff.model.OrderDTO;
import com.takeoff.model.OrderDetails;
import com.takeoff.model.RedemptionDTO;
import com.takeoff.model.RedemptionHistory;
import com.takeoff.model.RedemptionSummary;
import com.takeoff.model.RefererCodeDTO;
import com.takeoff.model.ResponseStatusDTO;
import com.takeoff.model.ScanCodeDTO;
import com.takeoff.model.StatementDTO;
import com.takeoff.model.StatsDTO;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.StructureDTO;
import com.takeoff.model.SubCategoryDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.model.TdsDTO;
import com.takeoff.model.VendorCouponsDTO;
import com.takeoff.model.VendorCouponsDTO1;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.model.VendorList;
import com.takeoff.repository.RolesRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.service.CategoryService;
import com.takeoff.service.ContactsService;
import com.takeoff.service.CouponService;
import com.takeoff.service.CouponTypeService;
import com.takeoff.service.CustomerService;
import com.takeoff.service.DisplayService;
import com.takeoff.service.FacebookService;
import com.takeoff.service.JwtUserDetailsService;
import com.takeoff.service.KYCService;
import com.takeoff.service.LoginService;
import com.takeoff.service.OrderService;
import com.takeoff.service.RazorpayService;
import com.takeoff.service.RedemptionService;
import com.takeoff.service.StatementService;
import com.takeoff.service.UtilService;
import com.takeoff.service.VendorService;




@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	ContactsService contactsService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	RazorpayService razorpayService;
	
	
	@Autowired
	LoginService loginService;
	
	
	@Autowired
	DisplayService displayService;
	
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	CouponTypeService couponTypeService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	CategoryService categoryService;
	
	
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	UserDetailsRepository userRepository;
	
	
	
	@Autowired
	RedemptionService redemptionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	@Autowired
	KYCService kycService;
	
	@Autowired
	OrderService orderService;
	
	
	@Autowired
	StatementService statementService;
	
	@Autowired
    FacebookService facebookService;

    @GetMapping("/createFacebookAuthorization")
    public String createFacebookAuthorization(){
        return facebookService.createFacebookAuthorizationURL();
    }
    
    @GetMapping("/getName")
    public String getNameResponse(){
        return facebookService.getName();
    }
    
    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        facebookService.createFacebookAccessToken(code);
    }
	
	
	
	@RequestMapping("/emailChange")
	public Boolean emailChange(String email, String userId)
	{
		return customerService.emailChange(email,userId);
	}
	
	@RequestMapping(value = "payment")
	public Boolean payment(@RequestBody OrderDetails order)
	{
		
		 return orderService.payment(order);
		 
	}
	
		@RequestMapping("/check")
	 public void check() 
		   {
		 
		 String host = "mail.silverstone-insp.com";// change accordingly
      		String mailStoreType = "pop3";
	      String username = "info@silverstone-insp.com";// change accordingly
	      String password = " info@silverstone#insp";// change accordingly
		 
		      try {

		      //create properties field
		      Properties properties = new Properties();

		      properties.put("mail.pop3.host", host);
		      properties.put("mail.pop3.port", "995");
		      properties.put("mail.pop3.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, username, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("messages.length---" + messages.length);

		      for (int i = 0, n = 10; i < n; i++) {
		         Message message = messages[messages.length-i-1];
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
		         System.out.println("Text: " + message.getContent().toString());

		      }

		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }
	
	@RequestMapping("/recordHits")
	public void recordHits(@RequestParam("referer")  String referer)
	{
	
		razorpayService.recordHits(referer);
	}
	@RequestMapping("/checkCustomerDetails")
	public StatusDTO checkCustomerDetails(@RequestParam("mobileNumber") String mobileNumber, @RequestParam("email") String email)
	{
		return customerService.checkCustomerDetails(mobileNumber, email);
	}
	
	@RequestMapping("/getNotification")
	public NotificationDTO getNotification()
	{
		return customerService.getNotification();
	
	}
	
	@RequestMapping("/getWalletBalance")
	public String getWalletBalance()
	{
	return "{\"walletBalance\": \""+customerService.getWalletBalance()+"\"}";
	}
	
	@RequestMapping("/sendRedemptionCode")
	public RedemptionDTO sendRedemptionCode(@RequestParam("scanCode") String scanCode, @RequestParam("couponId") String couponId)
	{
	return redemptionService.sendRedemptionCode(scanCode,couponId);
	}
	
		@RequestMapping("/scheduleInterview")
	public Boolean scheduleInterview(@RequestParam("id") String id, @RequestParam("date") String date, @RequestParam("time") String time,@RequestParam("duration") String duration,@RequestParam("email1") String email1,@RequestParam("email2") String email2)
	{
		System.out.println("Reached the code");
	return utilService.scheduleInterview(id,date,time,duration,email1,email2);
	}
	
	
	@RequestMapping("/getUserStats")
	public List<StatsDTO> getUserStats()
	{
	return customerService.getUserStats();
	}
	
	@RequestMapping("/gstDetails")
	public List<GstDetails> gstDetails()
	{
	return customerService.gstDetails();
	}

	
	@RequestMapping("/addContacts")
	public String addContacts(
			@RequestParam("name1") String name1,@RequestParam("contact1") String contact1,
			@RequestParam("name2") String name2,@RequestParam("contact2") String contact2,
			@RequestParam("name3") String name3,@RequestParam("contact3") String contact3)
	{
		System.out.print("kjlhkj");
		
		System.out.println(name1+" "+contact1+" "+name2+" "+contact2+" "+name3+" "+contact3);
	return contactsService.addContacts(name1,contact1,name2,contact2,name3,contact3);
	}
	
	
	@RequestMapping("/getAllCustomerAccountDetails")
	public List<CustomerDetailsDTO> getAllCustomerAccountDetails()
	{
	return customerService.getAllCustomerAccountDetails();
	}
	
	@RequestMapping("/getInvestorCustomerAccountDetails")
	public List<CustomerDetailsDTO> getInvestorCustomerAccountDetails()
	{
	return customerService.getInvestorCustomerAccountDetails();
	}
	
	@RequestMapping("/getExecutiveCustomerAccountDetails")
	public List<CustomerDetailsDTO> getExecutiveCustomerAccountDetails()
	{
	return customerService.getExecutiveCustomerAccountDetails();
	}
	
	
	
	
	
	@RequestMapping("/takeOffStatement")
	public List<StatementDTO> takeOffStatement(@RequestParam("customerId") String customerId)
	{
	return statementService.takeOffStatement(customerId);
	}
	
	@RequestMapping("/generateMailPasscode")
	public Boolean generateMailPasscode(@RequestParam("userId") String userId, @RequestParam("email") String email, @RequestParam("city") String city)
	{
		System.out.println("Reached the code");
	return utilService.generateMailPasscode(userId,email,city);
	}
	
	@RequestMapping("/checkPasswordOTP")
	public Boolean checkPasswordOTP(@RequestParam("userId") String userId, @RequestParam("email") String email, @RequestParam("city") String city, @RequestParam("otp") String otp)
	{
		
//		List<com.takeoff.domain.UserDetails> mobile = userRepository.findByContactNumber(userId);
//		
//		if(mobile.size() == 1)
//			userId=mobile.get(0).getLoginId()+"";
//		else if(mobile.size() > 1)
//		{
//			
//			return false;
//		}
//		
//		
//		if(userId.startsWith("TO"))
//			
//            userId=userId.substring(2,7);
//		
//		userId=userId.substring(0,5);
//		
//		System.out.println("User Id is "+userId);
	
		Optional<com.takeoff.domain.UserDetails> user = userRepository.checkForPasswordChange(userId,email,city);
		
		if(!user.isPresent())
		{
			return false;
		}
		
		
	return utilService.checkPasswordOTP(user.get().getUserId()+"",otp);
	}
	
	
	@RequestMapping("/vendorRedemptionProcess")
	public RedemptionDTO vendorRedemptionProcess(@RequestBody RedemptionDTO redemption)
	{
	return redemptionService.vendorRedemptionProcess(redemption);
	}
	
	@RequestMapping("/getRedemptionHistory")
	public List<RedemptionSummary> getRedemptionSummary()
	{
		return redemptionService.getRedemptionSummary();
	}
	
	@RequestMapping("/getCustomerAccountDetails")
	public CustomerDetailsDTO getCustomerAccountDetails(@RequestParam("userId") String userId)
	{
	return customerService.getCustomerAccountDetails(Long.valueOf(userId));
	}
	
	@RequestMapping("/deleteImage")
	public Boolean deleteImage(@RequestParam("imageId") String imageId)
	{
	return couponService.deleteImage(Long.valueOf(imageId));
	}
	
	@RequestMapping("/getKYCDetails")
	public List<KYCDetailsDTO> getKYCDetails()
	{
	return kycService.getKYCDetails("");
	}
	
	
	@RequestMapping("/verifyPanStatus")
	public KYCDetailsDTO verifyPanStatus(@RequestParam("customerId") String customerId, @RequestParam("status") String status)
	{
	return kycService.verifyPanStatus(customerId, status);
	}
	
	
	@RequestMapping("/creditAmount")
	public KYCDetailsDTO creditAmount(@RequestParam("customerId") String customerId, @RequestParam("creditAmount") String creditAmount, @RequestParam("creditDate") String creditDate)
	{
	return kycService.creditAmount(customerId, creditAmount, creditDate);
	}
	
	
	@RequestMapping("/verifyKycStatus")
	public KYCDetailsDTO verifyKycStatus(@RequestParam("customerId") String customerId, @RequestParam("status") String status)
	{
	return kycService.verifyKycStatus(customerId, status);
	}
	
	@RequestMapping("/updateKYC")
	public KYCDetailsDTO updateKYC(@RequestParam("file") MultipartFile file,@RequestParam("cname") String cname, @RequestParam("bname") String bname, @RequestParam("account") String account, @RequestParam("ifsc") String ifsc )
	{
		
	return kycService.updateKyc(file,cname,bname,account,ifsc);
	}
	
	@RequestMapping("/updatePan")
	public KYCDetailsDTO updatePan(@RequestParam("pan") String pan)
	{
		
	return kycService.updatePan(pan);
	}
	
	
	@RequestMapping("/customerRedemption")
	public RedemptionDTO customerRedemption(@RequestBody RedemptionDTO redemption)
	{
	return redemptionService.customerRedemption(redemption);
	}
	
	@RequestMapping("/likeCoupon")
	public Long likeCoupon(@RequestParam("couponId") String couponId, @RequestParam("userId") String userId, @RequestParam("like") String like)
	{
		//System.out.println("asdfasdf");
	return couponService.likeCoupon(Long.valueOf(couponId), Long.valueOf(userId), Boolean.parseBoolean(like));
	}
	
	@RequestMapping("/disLikeCoupon")
	public Long disLikeCoupon(@RequestParam("couponId") String couponId, @RequestParam("userId") String userId, @RequestParam("dislike") String dislike)
	{
		//System.out.println("asdfasdf12");
	return couponService.disLikeCoupon(Long.valueOf(couponId), Long.valueOf(userId), Boolean.parseBoolean(dislike));
	}
	
	@RequestMapping("/getCoupon")
	public VendorCouponsDTO1 getCoupon(@RequestParam("couponId") String couponId)
	{
		//System.out.println("asdfasdf12");
	return couponService.getCoupon(Long.valueOf(couponId));
	}
	
	@RequestMapping("/getTDS")
	public List<TdsDTO> getTDS(@RequestParam("month") String month)
	{
		
		String fromDate = month.split("-")[0]+"-"+month.split("-")[1]+"-"+"01 00:00";
		String toDate = month.split("-")[0]+"-"+month.split("-")[1]+"-"+"31 23:59";
		
		System.out.println(month+" "+fromDate+" "+toDate);
		
		return customerService.getTDS(fromDate,toDate);
	}
	
	
	
	@RequestMapping("/generateRedemption")
	public RedemptionDTO generateRedemption(@RequestBody RedemptionDTO redemption)
	{
	return redemptionService.generateRedemption(redemption,8,0);
	}
	
	@RequestMapping("/acceptRedemption")
	public Boolean acceptRedemption(@RequestBody RedemptionDTO redemption)
	{
	return redemptionService.acceptRedemption(redemption);
	}
	@RequestMapping("/approveSMS")
	public String approveSMS(HttpServletRequest request) 
	{		
		try
		{
		
		String whatsappNumber = request.getParameter("WaId");
		String body=request.getParameter("Body");
		body=body.toLowerCase().replaceAll(","," ").replaceAll("approve","").trim();
		
		//System.out.println(body);
		
		Long couponId=Long.valueOf(body.split(" ")[0]);
		
		Long customerId=Long.valueOf(body.split(" ")[1]);
		
		String passcode=body.split(" ")[2];
			
		ResponseStatusDTO status = redemptionService.acceptRedemptionWhatsApp(couponId, customerId, passcode,whatsappNumber);
			String statusStr="Success.";
			if(!status.getStatus())
			statusStr="Failed.";
			return "Hi "+whatsappNumber+"!"
					+ "\nCoupon Id: "+couponId+""
							+ "\nCustomer Id: "+customerId
							+"\nRedemption Status: "+statusStr+""
									+ "\nMessage: "+status.getMessage();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return "Hi! Wrong Format of Approval! Try Again with Format\nApprove <CouponId> <CustomerId> <Customer Shared 4 Characters Passcode>\n from your Registered Mobile Number..";
		}
	}   
	
	
	@RequestMapping("/getVendorDetails")
	public VendorDetailsDTO getVendorDetails(@RequestParam("vendorId") String vendorId) 
	{
		return vendorService.getVendorDetails(Long.valueOf(vendorId));
	}   
	
	@RequestMapping("/editCategory")
	public Boolean editCategory(@RequestParam("categoryId") String categoryId,@RequestParam("categoryName") String categoryName) 
	{
		return categoryService.editCategory(Long.valueOf(categoryId),categoryName);
	}  
	
	@RequestMapping("/editCouponType")
	public Boolean editCouponType(@RequestParam("couponTypeId") String couponTypeId,@RequestParam("couponTypeName") String couponTypeName) 
	{
		return couponTypeService.editCouponType(Long.valueOf(couponTypeId),couponTypeName);
	}  
	
	
	@RequestMapping("/editSubCategory")
	public Boolean editSubCategory(@RequestParam("subCategoryId") String subCategoryId,@RequestParam("subCategoryName") String subCategoryName) 
	{
		return categoryService.editSubCategory(Long.valueOf(subCategoryId),subCategoryName);
	}  
	
	
	@RequestMapping("/getDesignerDetails")
	public VendorDetailsDTO getDesignerDetails(@RequestParam("vendorId") String vendorId) 
	{
		return vendorService.getDesignerDetails(Long.valueOf(vendorId));
	}   
	


	@RequestMapping("/getImage")
	public ImageStatusDTO getImage(@RequestParam("file") MultipartFile file) throws IOException
	{
		ImageStatusDTO imageStatus=new ImageStatusDTO();
	try
	{
		
		
		
		InputStream is = new ByteArrayInputStream(file.getBytes());
        BufferedImage img = ImageIO.read(is);
		
      
        img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300,300);
        
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ImageIO.write(img,"JPEG",bos);
    	
    	
		
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		
		imageStatus.setImage(image);
		imageStatus.setStatus(true);
		}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
		
		return imageStatus;
	}
	
	 @RequestMapping(value = "/downloadGST", produces = "text/csv")
	    @ResponseBody
	    public void downloadGST(@RequestBody List<GstDetailsDTO> gstDetails, HttpServletResponse response) {
	        response.setContentType("text/plain; charset=utf-8");
	        StringBuilder csvData = new StringBuilder("");

	      String headers[]= {"SLNO.","Subscription Date","Customer Id","Customer Name","Taxable Amount","CGST (9%)","SGST (9%)","TOTAL GST"};
	      
	      for (int i = 0; i < headers.length - 1; i++) {
	            csvData.append(headers[i] + ",");
	        }
	        csvData.append(headers[headers.length - 1] + "\n");
	        
	       
	        
	        for(int i=0;i<gstDetails.size();i++)
	        {
	        	csvData.append((i+1)+",");
	        	csvData.append(gstDetails.get(i).getDate() + ",");
	        	csvData.append(gstDetails.get(i).getId() + ",");
	        	csvData.append(gstDetails.get(i).getName() + ",");
	        	csvData.append("1016,");
	        	csvData.append("91.50,");
	        	csvData.append("91.50,");
	        	csvData.append("183\n");
	        	
	        }

	        try {
	            PrintWriter writer = response.getWriter();
	            writer.write(csvData.toString().trim());
	            writer.close();
	        } catch (IOException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
	 
	 
	 @RequestMapping(value = "/downloadRedemHistory", produces = "text/csv")
	    @ResponseBody
	    public void downloadRedemHistory(@RequestBody List<RedemptionHistory> redemptionHistory, HttpServletResponse response) {
	        response.setContentType("text/plain; charset=utf-8");
	        StringBuilder csvData = new StringBuilder("");

	     String headers[]= {"SLNO.","Coupon Id","Outlet Id","Outlet Name","Address","Contact","Customer Id","Customer Name","Contact","Coupon Type","Coupon Category", "Coupon Sub Category","Redemption On"};
	       
	      for (int i = 0; i < headers.length - 1; i++) {
	            csvData.append(headers[i] + ",");
	        }
	        csvData.append(headers[headers.length - 1] + "\n");
	        
	       
	        
	        for(int i=0;i<redemptionHistory.size();i++)
	        {
	        	csvData.append((i+1)+",");
	        	csvData.append(redemptionHistory.get(i).getCouponId() + ",");
	        	csvData.append(redemptionHistory.get(i).getVendorId() + ",");
	        	csvData.append(redemptionHistory.get(i).getVendorName().replace(","," ") + ",");
	        	csvData.append(redemptionHistory.get(i).getAddress().replace(","," ") + ",");
	        	csvData.append(redemptionHistory.get(i).getContact() + ",");
	        	csvData.append(redemptionHistory.get(i).getCustomerId() + ",");
	        	csvData.append(redemptionHistory.get(i).getCustomerName().replace(","," ") + ",");
	        	csvData.append(redemptionHistory.get(i).getCustomerContact() + ",");
	        	csvData.append(redemptionHistory.get(i).getCouponType() + ",");
			csvData.append(redemptionHistory.get(i).getCategory() + ",");
	        	csvData.append(redemptionHistory.get(i).getSubCategory() + ",");
	        	csvData.append(redemptionHistory.get(i).getRedemOn() + ",");
	   
	           	csvData.append("\n");
	        }

	        try {
	            PrintWriter writer = response.getWriter();
	            writer.write(csvData.toString().trim());
	            writer.close();
	        } catch (IOException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
	
	@RequestMapping("/getImageStatement")
	public ImageStatusDTO getImageStatement(@RequestParam("file") MultipartFile file) throws IOException
	{
		
		return couponService.getImage(file);
	}
	
	@RequestMapping("/getImages")
	public List<ImageDetailsDTO> getImages(@RequestBody ImagesRequest request ) throws UnsupportedEncodingException 
	{
		System.out.println(request.getVendorId());
		
		List<Long> imageIds = request.getImageIds();
		
		
		
		if(imageIds.size() == 0)
			imageIds=Arrays.asList(-1l);
		
		return couponService.getImages(Long.valueOf(request.getVendorId()),imageIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords());
	}
	
	@RequestMapping("/getCoupons")
	public List<VendorCouponsDTO1> getCoupons(@RequestParam("vendorId") String vendorId) throws UnsupportedEncodingException 
	{
	
		return couponService.getCoupons(Long.valueOf(vendorId),0l,0l,Arrays.asList(-1l),0l,0l,"","",0l);
	}
	
	@RequestMapping("/getHomePageCoupons")
	public List<VendorCouponsDTO1> getHomePageCoupons() throws UnsupportedEncodingException 
	{
	
		return couponService.getHomePageCoupons();
	}
	
	@RequestMapping("/getLogos")
	public List<String> getLogos()
	{
		return vendorService.getLogos();
	}
	
	@RequestMapping("/getVendorList")
	public List<VendorList> getVendorList()
	{
		
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		 Long customerId = Long.valueOf(userDetails.getUsername());
		 
		String city="";
		
		Optional<com.takeoff.domain.UserDetails> user=userRepository.findByUserId(customerId);
		
		if(user.isPresent())
		{
			city=user.get().getCity();
		}
		
		return vendorService.getVendorList(city);
	}
	
	@RequestMapping("/getTakeOffRecommendations")
	public List<VendorCouponsDTO1> getTakeOffRecommendations(@RequestBody CouponsRequest request) throws Exception
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username= "";

		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		   username = principal.toString();
		}
		
		if(!username.equals(request.getUserId()+""))
		{
			throw new Exception("UnAuthorized Exception");
		}
		
		List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		
		
		return couponService.getCoupons(0l,0l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}

	
	@RequestMapping("/getComplimentaryCoupons")
	public List<VendorCouponsDTO1> getComplimentaryCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,1l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}

	@RequestMapping("/getFreeCoupons")
	public List<VendorCouponsDTO1> getFreeCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,2l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}
	
	@RequestMapping("/getDailyCoupons")
	public List<VendorCouponsDTO1> getDailyCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,3l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}
	
	@RequestMapping("/getLimitedCoupons")
	public List<VendorCouponsDTO1> getLimitedCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,4l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}
	
	@RequestMapping("/getRedeemableCoupons")
	public List<VendorCouponsDTO1> getRedeemableCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,5l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	}
	
	@RequestMapping("/getDiscountCoupons")
	public List<VendorCouponsDTO1> getDiscountCoupons(@RequestBody CouponsRequest request) throws UnsupportedEncodingException
	{
	
List<Long> couponIds = request.getCouponIds();
		
		
		
		if(couponIds.size() == 0)
			couponIds=Arrays.asList(-1l);
		
		return couponService.getCoupons(0l,6l,request.getUserId(),couponIds,request.getCategory(),request.getSubCategory(), request.getCity(), request.getKeywords(),request.getVendorSelected());
	
	
	}

	@RequestMapping("/editCoupon")
	public Boolean editCoupon(@RequestBody VendorCouponsDTO coupon) throws IOException
	{
	
		VendorCoupons vendorCoupon = new VendorCoupons();
		
		vendorCoupon.setHeader(coupon.getHeader());
		vendorCoupon.setBody(coupon.getBody());
		vendorCoupon.setFooter(coupon.getFooter());
		vendorCoupon.setHeader_color(coupon.getHeader_color());
		vendorCoupon.setBody_color(coupon.getBody_color());
		vendorCoupon.setFooter_color(coupon.getFooter_color());
		vendorCoupon.setHeader_align(coupon.getHeader_align());
		vendorCoupon.setBody_align(coupon.getBody_align());
		vendorCoupon.setFooter_align(coupon.getFooter_align());
		vendorCoupon.setHeader_size(coupon.getHeader_size());
		vendorCoupon.setBody_size(coupon.getBody_size());
		vendorCoupon.setFooter_size(coupon.getFooter_size());
		vendorCoupon.setFooter_font(coupon.getFooter_font());
		vendorCoupon.setHeader_font(coupon.getHeader_font());
		vendorCoupon.setBody_font(coupon.getBody_font());
		vendorCoupon.setFooter_bold(coupon.getFooter_bold());
		vendorCoupon.setHeader_bold(coupon.getHeader_bold());
		vendorCoupon.setBody_bold(coupon.getBody_bold());
		vendorCoupon.setFooter_style(coupon.getFooter_style());
		vendorCoupon.setHeader_style(coupon.getHeader_style());
		vendorCoupon.setBody_style(coupon.getBody_style());
		vendorCoupon.setImage_align(coupon.getImage_align());
		vendorCoupon.setFooter_decoration(coupon.getFooter_decoration());
		vendorCoupon.setHeader_decoration(coupon.getHeader_decoration());
		vendorCoupon.setBody_decoration(coupon.getBody_decoration());
		vendorCoupon.setProfession(coupon.getProfession());
		vendorCoupon.setGender(coupon.getGender());
		
		
		vendorCoupon.setFromDate(coupon.getFromDate());
		vendorCoupon.setToDate(coupon.getToDate());
		
		ImageDetails image=couponService.getImageDetails(coupon.getImageId());
		
		vendorCoupon.setImage(image);
		vendorCoupon.setCouponType(couponTypeService.getCouponType(coupon.getCouponType()));

		vendorCoupon.setKeywords(coupon.getKeywords());
		String exclusiveFor = coupon.getExclusiveFor();
		
		if(exclusiveFor == null || (exclusiveFor+"").trim().length()  == 0)
			exclusiveFor = "ALL";
		
		vendorCoupon.setExclusiveFor(exclusiveFor.toUpperCase());

		
		vendorCoupon.setVendor(vendorService.getVendorDetails(coupon.getVendorId()+""));
		vendorCoupon.setDescription(coupon.getDescription());
		vendorCoupon.setId(coupon.getId());
		
//		
//		String bottom_left="position: absolute;bottom: 2%;left: 5%;";
//		String top_left="position: absolute; top: 2%;left: 5%;";
//		String top_center="position: absolute;top: 2%;left: 50%;transform: translate(-50%, -0%);";
//		String top_right="position: absolute;top: 2%;right: 5%;";
//		String bottom_right="position: absolute;bottom: 2%;right: 5%;";
//		String bottom_center="position:absolute;bottom: 2%;left: 50%;transform: translate(-50%, -0%);";
//		String centered="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -0%);";
//		String centered_left="position: absolute;top: 50%;left: 5%;"; 
//		String centered_right="position: absolute;top: 50%;right: 5%;";
//		
//		Map<String, String> classMap = new HashMap<String, String>();
//		
//		classMap.put("bottom-left",bottom_left);
//		classMap.put("top-left",top_left);
//		classMap.put("top-center",top_center);
//		classMap.put("top-right",top_right);
//		classMap.put("bottom-right",bottom_right);
//		classMap.put("bottom-center",bottom_center);
//		classMap.put("centered",centered);
//		classMap.put("centered-left",centered_left);
//		classMap.put("centered-right",centered_right);
//		
//		System.out.println(classMap.get(coupon.getHeader_align()));
//		
//		String htmlData="<div style='position: relative;'>"
//				+ "<img  src='data:image/jpeg;base64,"+image.getImage()+"' />"
//				
//				+ "<div style='"+classMap.get(coupon.getHeader_align())+";"
//				+ "font-family:"+coupon.getHeader_font()+";color:"+coupon.getHeader_color()+";"
//				+ "text-decoration:"+coupon.getHeader_decoration()+";font-weight:"+coupon.getHeader_bold()+";"
//				+ "font-style:"+coupon.getHeader_style()+";font-size:"+coupon.getHeader_size()+"px'>"+coupon.getHeader()+"</div>"
//				
//				+ "<div style='"+classMap.get(coupon.getBody_align())+";"
//				+ "font-family:"+coupon.getBody_font()+";color:"+coupon.getBody_color()+";"
//				+ "text-decoration:"+coupon.getBody_decoration()+";font-weight:"+coupon.getBody_bold()+";"
//				+ "font-style:"+coupon.getBody_style()+";font-size:"+coupon.getBody_size()+"px'>"+coupon.getBody()+"</div>"
//				
//				+ "<div style='"+classMap.get(coupon.getFooter_align())+";"
//				+ "font-family:"+coupon.getFooter_font()+";color:"+coupon.getFooter_color()+";"
//				+ "text-decoration:"+coupon.getFooter_decoration()+";font-weight:"+coupon.getFooter_bold()+";"
//				+ "font-style:"+coupon.getFooter_style()+";font-size:"+coupon.getFooter_size()+"px'>"+coupon.getFooter()+"</div>"
//
//
//				
//				+"</div>";
//		
//		String couponStr=logoService.createImage(htmlData,false);
//		
////		FileOutputStream input=new FileOutputStream("f:\\demo.html");
////		input.write(htmlData.getBytes());
////		input.close();
////		 input=new FileOutputStream("f:\\demo1.html");
////		input.write(("<img src='data:image/jpeg;base64,"+couponStr+"' />").getBytes());
////		input.close();
//		
//		vendorCoupon.setCoupon(couponStr);
		
		
		return couponService.saveCoupon(vendorCoupon);
	}
	
	
		@RequestMapping("/downloadCoupon")
	public String downloadCoupon(@RequestParam("couponId") String couponId) throws NumberFormatException, IOException
	{
		//String couponId="8";
		return "{\"img\":\""+couponService.downloadCoupon(Long.valueOf(couponId))+"\"}";
	}
	
	@RequestMapping("/getAllSubCategories")
	public List<SubCategoryDTO> getAllSubCategories() 
	{
		return categoryService.getAllSubCategories();
	}
	
	@RequestMapping("/addCategory")
	public Boolean addCategory(@RequestParam("category") String category) 
	{
		return categoryService.addCategory(category);
	}
	
	@RequestMapping("/changePassword")
	public Boolean changePassword(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("newpassword") String newpassword) 
	{
		return vendorService.changePassword(userId,password,newpassword);
		
	}
	
	@RequestMapping("/forgetPassword")
	public Boolean forgetPassword(@RequestParam("userId") String userId, @RequestParam("newpassword") String newpassword) 
	{
		
		
		
		List<com.takeoff.domain.UserDetails> mobile = userRepository.findByContactNumber(userId);
		
		if(mobile.size() == 1)
			userId=mobile.get(0).getLoginId()+"";
		else if(mobile.size() > 1)
		{
			
			return false;
		}
		
		
		if(userId.startsWith("TO"))
			
            userId=userId.substring(2,7);
		
		userId=userId.substring(0,5);
		
		return customerService.forgetPassword(userId,newpassword);
		
	}

	
	
	@RequestMapping("/addSubCategory")
	public Boolean addSubCategory(@RequestParam("categoryId") String categoryId,@RequestParam("subcategory") String subcategory) 
	{
		return categoryService.addSubCategory(Long.valueOf(categoryId),subcategory);
	}
	
	@RequestMapping("/addDesigner")
	public Boolean addDesigner(@RequestBody VendorDetailsDTO designer) throws NoSuchAlgorithmException 
	{
		
		return vendorService.addDesginer(designer);
	}
	
	@RequestMapping("/addInvestor")
	public Boolean addInvestor(@RequestBody VendorDetailsDTO designer) throws NoSuchAlgorithmException 
	{
		
		return vendorService.addInvestor(designer);
	}
	
	@RequestMapping("/addVendor")
	public Boolean addVendor(@RequestBody VendorDetailsDTO vendor) throws NoSuchAlgorithmException, IOException 
	{
		
		return vendorService.addVendor(vendor);
	}
	
	
	@RequestMapping("/editDesigner")
	public Boolean editDesigner(@RequestBody VendorDetailsDTO designer) throws NoSuchAlgorithmException 
	{
		
		return vendorService.editDesigner(designer);
	}
	
	@RequestMapping("/editVendor")
	public Boolean editVendor(@RequestBody VendorDetailsDTO vendor) throws NoSuchAlgorithmException, IOException 
	{
		
		return vendorService.editVendor(vendor);
	}
	
	@RequestMapping("/disableDesigner")
	public Boolean disableDesigner(@RequestParam("vendorId") String vendorId)
	{
		
		return vendorService.disableDesigner(Long.valueOf(vendorId));
	}
	
	@RequestMapping("/deleteDesigner")
	public Boolean deleteDesigner(@RequestParam("vendorId") String vendorId)
	{
		
		return vendorService.deleteDesigner(Long.valueOf(vendorId));
	}
	
	@RequestMapping("/disableVendor")
	public Boolean disableVendor(@RequestParam("vendorId") String vendorId)
	{
		
		return vendorService.disableVendor(Long.valueOf(vendorId));
	}
	
	@RequestMapping("/deleteVendor")
	public Boolean deleteVendor(@RequestParam("vendorId") String vendorId)
	{
		
		return vendorService.deleteVendor(Long.valueOf(vendorId));
	}
	
	
	@RequestMapping("/deleteSubCategory")
	public Boolean deleteSubCategory(@RequestParam("subCategoryId") String subCategoryId) 
	{
		return categoryService.deleteSubCategory(Long.valueOf(subCategoryId));
	}
	
	
	
	@RequestMapping("/deleteCategory")
	public Boolean deleteCategory(@RequestParam("categoryId") String categoryId) 
	{
		return categoryService.deleteCategory(Long.valueOf(categoryId));
	}
	
	@RequestMapping("/visibleCouponType")
	public Boolean visibleCouponType(@RequestParam("couponTypeId") String couponTypeId) 
	{
		return couponTypeService.visibleCouponType(Long.valueOf(couponTypeId));
	}
	
	@RequestMapping("/deleteCouponType")
	public Boolean deleteCouponType(@RequestParam("couponTypeId") String couponTypeId) 
	{
		return couponTypeService.deleteCouponType(Long.valueOf(couponTypeId));
	}
	
	@RequestMapping("/addCouponType")
	public Boolean addCouponType(@RequestParam("couponTypeName") String couponTypeName) 
	{
		return couponTypeService.addCouponType(couponTypeName);
	}
	
	@RequestMapping("/visibleSubCategory")
	public Boolean visibleSubCategory(@RequestParam("subCategoryId") String subCategoryId) 
	{
		return categoryService.visibleSubCategory(Long.valueOf(subCategoryId));
	}
	
	@RequestMapping("/visibleCategory")
	public Boolean visibleCategory(@RequestParam("categoryId") String categoryId) 
	{
		return categoryService.visibleCategory(Long.valueOf(categoryId));
	}
	
	@RequestMapping("/mandatoryComplimentaryChange")
	public Boolean mandatoryComplimentaryChange(@RequestParam("subCategoryId") String subCategoryId) 
	{
		return categoryService.mandatoryComplimentaryChange(Long.valueOf(subCategoryId));
	}
	
	
	@RequestMapping("/getCategories")
	public List<Category> getCategories() 
	{
		return categoryService.getCategories();
	}
	
	
	
	@RequestMapping("/getCouponTypes")
	public List<CouponType> getCouponTypes() 
	{
		return couponTypeService.getCouponTypes();
	}
	
	@RequestMapping("/getAllCouponTypes")
	public List<CouponType> getAllCouponTypes() 
	{
		return couponTypeService.getAllCouponTypes();
	}
	
	
	@RequestMapping("/getSubCategories")
	public List<SubCategory> getSubCategories(@RequestParam("category") String category) 
	{
		return categoryService.getSubCategories(category);
	}
	
	
	
	@RequestMapping("/getDesigners")
	public List<VendorDetailsDTO> getDesigners() 
	{
		return vendorService.getDesigners();
	}
	
	@RequestMapping("/getInvestors")
	public List<VendorDetailsDTO> getInvestors() 
	{
		return vendorService.getInvestors();
	}
	
	@RequestMapping("/getExecutives")
	public List<VendorDetailsDTO> getExecutives() 
	{
		return vendorService.getExecutives();
	}
	
	@RequestMapping("/addExecutive")
	public Boolean addExecutive(@RequestBody VendorDetailsDTO designer) throws NoSuchAlgorithmException 
	{
		
		return vendorService.addExecutive(designer);
	}
	
	@RequestMapping("/getVendors")
	public List<VendorDetailsDTO> getVendors() 
	{
		return vendorService.getVendors();
	}
	
	@RequestMapping("/saveCoupon")
	public Boolean saveCoupon(@RequestBody VendorCouponsDTO coupon) throws IOException 
	{
		VendorCoupons vendorCoupon = new VendorCoupons();
		
		vendorCoupon.setHeader(coupon.getHeader());
		vendorCoupon.setBody(coupon.getBody());
		vendorCoupon.setFooter(coupon.getFooter());
		vendorCoupon.setHeader_color(coupon.getHeader_color());
		vendorCoupon.setBody_color(coupon.getBody_color());
		vendorCoupon.setFooter_color(coupon.getFooter_color());
		vendorCoupon.setHeader_align(coupon.getHeader_align());
		vendorCoupon.setBody_align(coupon.getBody_align());
		vendorCoupon.setFooter_align(coupon.getFooter_align());
		vendorCoupon.setHeader_size(coupon.getHeader_size());
		vendorCoupon.setBody_size(coupon.getBody_size());
		vendorCoupon.setFooter_size(coupon.getFooter_size());
		vendorCoupon.setFooter_font(coupon.getFooter_font());
		vendorCoupon.setHeader_font(coupon.getHeader_font());
		vendorCoupon.setBody_font(coupon.getBody_font());
		vendorCoupon.setFooter_bold(coupon.getFooter_bold());
		vendorCoupon.setHeader_bold(coupon.getHeader_bold());
		vendorCoupon.setBody_bold(coupon.getBody_bold());
		vendorCoupon.setFooter_style(coupon.getFooter_style());
		vendorCoupon.setHeader_style(coupon.getHeader_style());
		vendorCoupon.setBody_style(coupon.getBody_style());
		vendorCoupon.setImage_align(coupon.getImage_align());
		vendorCoupon.setFooter_decoration(coupon.getFooter_decoration());
		vendorCoupon.setHeader_decoration(coupon.getHeader_decoration());
		vendorCoupon.setBody_decoration(coupon.getBody_decoration());
		vendorCoupon.setProfession(coupon.getProfession());
		vendorCoupon.setGender(coupon.getGender());
		
		
		vendorCoupon.setFromDate(coupon.getFromDate());
		vendorCoupon.setToDate(coupon.getToDate());
		
		vendorCoupon.setImage(couponService.getImageDetails(coupon.getImageId()));
		vendorCoupon.setCouponType(couponTypeService.getCouponType(coupon.getCouponType()));

		vendorCoupon.setKeywords(coupon.getKeywords());
		
		vendorCoupon.setVendor(vendorService.getVendorDetails(coupon.getVendorId()+""));
		vendorCoupon.setDescription(coupon.getDescription());
		
		String exclusiveFor = coupon.getExclusiveFor();
		
		if(exclusiveFor == null || (exclusiveFor+"").trim().length()  == 0)
			exclusiveFor = "ALL";
		
		vendorCoupon.setExclusiveFor(exclusiveFor.toUpperCase());
		
		
		
//		String bottom_left="position: absolute;bottom: 2%;left: 5%;";
//		String top_left="position: absolute; top: 2%;left: 5%;";
//		String top_center="position: absolute;top: 2%;left: 50%;transform: translate(-50%, -0%);";
//		String top_right="position: absolute;top: 2%;right: 5%;";
//		String bottom_right="position: absolute;bottom: 2%;right: 5%;";
//		String bottom_center="position:absolute;bottom: 2%;left: 50%;transform: translate(-50%, -0%);";
//		String centered="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -0%);";
//		String centered_left="position: absolute;top: 50%;left: 5%;"; 
//		String centered_right="position: absolute;top: 50%;right: 5%;";
//		
//		Map<String, String> classMap = new HashMap<String, String>();
//		
//		classMap.put("bottom-left",bottom_left);
//		classMap.put("top-left",top_left);
//		classMap.put("top-center",top_center);
//		classMap.put("top-right",top_right);
//		classMap.put("bottom-right",bottom_right);
//		classMap.put("bottom-center",bottom_center);
//		classMap.put("centered",centered);
//		classMap.put("centered-left",centered_left);
//		classMap.put("centered-right",centered_right);
//		
//		System.out.println(classMap.get(coupon.getHeader_align()));
//		
//		ImageDetails image=vendorCoupon.getImage();
//		
//		String htmlData="<div style='position: relative;'>"
//				+ "<img  src='data:image/jpeg;base64,"+image.getImage()+"' />"
//				
//				+ "<div style='"+classMap.get(coupon.getHeader_align())+";"
//				+ "font-family:"+coupon.getHeader_font()+";color:"+coupon.getHeader_color()+";"
//				+ "text-decoration:"+coupon.getHeader_decoration()+";font-weight:"+coupon.getHeader_bold()+";"
//				+ "font-style:"+coupon.getHeader_style()+";font-size:"+coupon.getHeader_size()+"px'>"+coupon.getHeader()+"</div>"
//				
//				+ "<div style='"+classMap.get(coupon.getBody_align())+";"
//				+ "font-family:"+coupon.getBody_font()+";color:"+coupon.getBody_color()+";"
//				+ "text-decoration:"+coupon.getBody_decoration()+";font-weight:"+coupon.getBody_bold()+";"
//				+ "font-style:"+coupon.getBody_style()+";font-size:"+coupon.getBody_size()+"px'>"+coupon.getBody()+"</div>"
//				
//				+ "<div style='"+classMap.get(coupon.getFooter_align())+";"
//				+ "font-family:"+coupon.getFooter_font()+";color:"+coupon.getFooter_color()+";"
//				+ "text-decoration:"+coupon.getFooter_decoration()+";font-weight:"+coupon.getFooter_bold()+";"
//				+ "font-style:"+coupon.getFooter_style()+";font-size:"+coupon.getFooter_size()+"px'>"+coupon.getFooter()+"</div>"
//
//
//				
//				+"</div>";
//		
//		String couponStr=logoService.createImage(htmlData,false);
//		
//		FileOutputStream input=new FileOutputStream("f:\\demo.html");
//		input.write(htmlData.getBytes());
//		input.close();
//		 input=new FileOutputStream("f:\\demo1.html");
//		input.write(("<img src='data:image/jpeg;base64,"+couponStr+"' />").getBytes());
//		input.close();
//		
//		vendorCoupon.setCoupon(couponStr);
		
		
		
		
		return couponService.saveCoupon(vendorCoupon);
	}
	
	@RequestMapping("/uploadCoupon")
	public ImageStatusDTO uploadCoupon(@RequestParam("file") MultipartFile file,@RequestParam("vendorId") String vendorId, @RequestParam("subCategory") String subCategory, @RequestParam("keywords") String keywords) throws IOException
	{
		
		return couponService.uploadCoupon(file, Long.valueOf(vendorId),subCategory,keywords);
		
	}
	
	@RequestMapping("/uploadLogo")
	public ImageStatusDTO uploadLogo(@RequestParam("file") MultipartFile file,@RequestParam("vendorId") String vendorId) throws IOException
	{
		
		return couponService.uploadLogo(file, Long.valueOf(vendorId));
		
	}
	
	
	@RequestMapping("/getTreeStructure")
	public StructureDTO getTreeStructure()
	{
		StructureDTO structure =  new StructureDTO();
		
		String str =displayService.getTreeStructure();
		System.out.println(str);
		structure.setStructure(str);
		
		
		
		return structure;
	}
// 	@RequestMapping(value = "/login")
// 	public LoginStatusDTO createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password) {
// 		//System.out.println("entered in authenticate...");
// 		LoginStatusDTO loginStatus=new LoginStatusDTO();
// 		try
// 		{
// 			username=username.toLowerCase().replaceAll("to","");
			
// 		authenticate(username, password);

// 		final UserDetails userDetails = userDetailsService
// 				.loadUserByUsername(username);

// 		final String token = jwtTokenUtil.generateToken(userDetails);
// 		//System.out.println("exited in authenticate...");
		
		
		
// 		loginStatus.setUserId(userDetails.getUsername());
		
// 		loginStatus.setLoginStatus(true);
		
// 		loginStatus.setJwt(token);
			
// 		loginStatus.setUserType(userDetails.getAuthorities().toArray()[0].toString());
// 		}
// 		catch(Exception ex)
// 		{
// 			System.out.println("Error Occured while logging in "+ex);
// 		}
	
// 		return loginStatus;
// 	}
	
	@RequestMapping(value = "/login")
	public LoginStatusDTO createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("asVendor") String asVendor) {
		//System.out.println("entered in authenticate...");
		LoginStatusDTO loginStatus=new LoginStatusDTO();
		try
		{
			

List<com.takeoff.domain.UserDetails> mobile = userRepository.findByContactNumber(username,Long.valueOf(Boolean.valueOf(asVendor)?"4":"2"));
			
			if(mobile.size() == 1)
				username=mobile.get(0).getLoginId()+"";
			else if(mobile.size() > 1)
			{
				
				
				loginStatus.setLoginStatus(false);
				
				loginStatus.setMessage("You have Multiple Accounts with Same Mobile Number. So Please login with Login Id.");
				return loginStatus;
			}
			
			username=username.toLowerCase().replaceAll("to","");
			
			Boolean isUser = customerService.isUser(username);
			
			if(!isUser)
			{
				System.out.println("He is not user");
				
				loginStatus.setLoginStatus(false);
				loginStatus.setMessage("Invalid Credientails..");
				
				
				return loginStatus;
				
				
			}
						
			username=username.substring(0,5);
			
			
			
		authenticate(username, password);

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(username);

		final String token = jwtTokenUtil.generateToken(userDetails);
		//System.out.println("exited in authenticate...");
		
		
		
		loginStatus.setUserId(userDetails.getUsername());
		
		loginStatus.setLoginStatus(true);
		
		loginStatus.setJwt(token);

		loginStatus.setLoginId(userRepository.findById(Long.valueOf(userDetails.getUsername())).get().getLoginId()+"");
		
		loginStatus.setUserType(userDetails.getAuthorities().toArray()[0].toString());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error Occured while logging in "+ex);
			loginStatus.setLoginStatus(false);
			loginStatus.setMessage("Invalid Credientails..");
		}
	
		return loginStatus;
	}
	
	
	@RequestMapping(value = "/getLoginDetails")
	public LoginStatusDTO getLoginDetails() throws Exception {
		
		LoginStatusDTO loginStatus=new LoginStatusDTO();
		
		 if(SecurityContextHolder.getContext().getAuthentication() == null)
		 {
			 loginStatus.setUserId("");
				
				loginStatus.setLoginStatus(false);
					
				loginStatus.setUserType("");
		 }
		 else
		 {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		loginStatus.setUserId(userDetails.getUsername());
		
		loginStatus.setLoginStatus(true);
		
		System.out.println(Long.valueOf(userDetails.getUsername()));
		
		loginStatus.setLoginId(userRepository.findById(Long.valueOf(userDetails.getUsername())).get().getLoginId()+"");
			
		loginStatus.setUserType(userDetails.getAuthorities().toArray()[0].toString());
		 };
	
		return loginStatus;
	}
	
	

	private void authenticate(String username, String password) throws Exception {
	//	System.out.println("entered in authenticate sub function...");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		//System.out.println("exited in authenticate sub function...");
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
		if(subscription.getSubscription()!= null && subscription.getSubscription().equalsIgnoreCase("free"))	
		return customerService.subscribeFree(subscription);	
		else	
				
		return customerService.subscribe(subscription);
	}
	
	@RequestMapping("/upgradeSubscription")
	public StatusDTO upgradeSubscription(@RequestBody SubscriptionDTO subscription)
	{
		
				
		return customerService.upgradeSubscription(subscription);
	}
	
	
	@RequestMapping(value="/getOrderId")
	public OrderDTO getOrderId() throws RazorpayException
	{
		
	
		
		String orderid=razorpayService.getOrderId();
		OrderDTO sendOrder = new OrderDTO(orderid);
		return sendOrder;



	}
	
	@RequestMapping(value="/getScanCodes")
	public List<ScanCodeDTO> getCodes()
	{
		return redemptionService.getCodes();
	}
	
	@RequestMapping(value="/getContacts")
	public List<Contacts> getContacts()
	{
		return contactsService.getContacts();
	}
	
	
	@RequestMapping(value="/hitsReceived")
	public List<HitsReceived> getHitsReceived()
	{
		return razorpayService.getHitsReceived();
	}
	
	@RequestMapping(value="/updateScanCode")
	public RedemptionDTO updateScanCode(@RequestParam("scanCode") String scanCode)
	{
		return redemptionService.updateScanCode(scanCode);
	}

}
