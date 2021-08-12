package com.takeoff.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.RazorpayException;
import com.takeoff.domain.Category;
import com.takeoff.domain.CouponType;
import com.takeoff.domain.SubCategory;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.LoginStatusDTO;
import com.takeoff.model.OrderDTO;
import com.takeoff.model.RefererCodeDTO;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.StructureDTO;
import com.takeoff.model.SubCategoryDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.model.VendorCouponsDTO;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.repository.RolesRepository;
import com.takeoff.service.CategoryService;
import com.takeoff.service.CouponService;
import com.takeoff.service.CouponTypeService;
import com.takeoff.service.CustomerService;
import com.takeoff.service.DisplayService;
import com.takeoff.service.LoginService;
import com.takeoff.service.RazorpayService;
import com.takeoff.service.VendorService;

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
	
	@Autowired
	CouponTypeService couponTypeService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	CategoryService categoryService;
	
	
	
	@Autowired
	RolesRepository rolesRepository;
	
	
	@RequestMapping("/approveSMS")
	public String approveSMS(HttpServletRequest request) 
	{
		String str="";
		List<String> params = Collections.list(request.getParameterNames());
		
		for(int i=0;i<params.size();i++)
			str+=params.get(i)+","+request.getParameter(params.get(i))+"\n";
		
		return "contents are \n"+str;
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
		
		String image =   new String(Base64.encodeBase64(file.getBytes()), "UTF-8");
		
		imageStatus.setImage(image);
		imageStatus.setStatus(true);
		
		
		return imageStatus;
	}
	
	@RequestMapping("/getImages")
	public List<ImageStatusDTO> getImages(@RequestParam("vendorId") String vendorId) throws UnsupportedEncodingException 
	{
		System.out.println(vendorId);
		return couponService.getImages(Long.valueOf(vendorId));
	}
	
	@RequestMapping("/getCoupons")
	public List<VendorCouponsDTO> getCoupons(@RequestParam("vendorId") String vendorId) throws UnsupportedEncodingException 
	{
	
		return couponService.getCoupons(Long.valueOf(vendorId));
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
	
	@RequestMapping("/editDesigner")
	public Boolean editDesigner(@RequestBody VendorDetailsDTO designer) throws NoSuchAlgorithmException 
	{
		
		return vendorService.editDesigner(designer);
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
	
	@RequestMapping("/getVendors")
	public List<VendorDetailsDTO> getVendors() 
	{
		return vendorService.getVendors();
	}
	
	@RequestMapping("/saveCoupon")
	public Boolean saveCoupon(@RequestBody VendorCouponsDTO coupon) 
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
		subscription.setRole(rolesRepository.findByRoleName("Customer").get());
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
