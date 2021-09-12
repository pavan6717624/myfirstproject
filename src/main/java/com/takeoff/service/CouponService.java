package com.takeoff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.ImageDetails;
import com.takeoff.domain.LikeCoupons;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.ImageDetailsDTO;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.VendorCouponsDTO1;
import com.takeoff.repository.CategoryRepository;
import com.takeoff.repository.ImageDetailsRepository;
import com.takeoff.repository.LikeCouponRepository;
import com.takeoff.repository.SubCategoryRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorCouponsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class CouponService {
	
	@Autowired
	
	ImageDetailsRepository couponDetailsRepository;
	
	@Autowired
	
	VendorDetailsRepository vendorDetailsRepository;
	
	@Autowired
	
	SubCategoryRepository subCategoryRepository;
	
	@Autowired
	
	CategoryRepository categoryRepository;
	
	@Autowired
	
	LogoService logoService;
	
	
	@Autowired
	
	UserDetailsRepository userDetailsRepository;
	
@Autowired
	
	VendorCouponsRepository vendorCouponsRepository;

@Autowired

LikeCouponRepository likeCouponRepository;
	

public ImageDetails getImageDetails(Long id)
{
	return couponDetailsRepository.findById(id).get();
}
	@Transactional
	public Boolean saveCoupon(VendorCoupons coupon) 
	{
		coupon =  vendorCouponsRepository.save(coupon);
		
		if(coupon.getId() == null)
		return false;
		else
			return true;
	}
	
	public List<ImageDetailsDTO> getImages(Long vendorId, List<Long> imageIds) throws UnsupportedEncodingException
	{
		Pageable paging = PageRequest.of(0, 10);
		
		
		List<ImageDetailsDTO> images = couponDetailsRepository.findByLatest(vendorId,imageIds,paging);
		
		
		
		return images;
//		List<ImageStatusDTO> images = new ArrayList<>();
//		
//		for(int i=0;i<coupons.size();i++)
//		{
//			ImageStatusDTO image=new ImageStatusDTO();
//			String img = "data:image/jpeg;base64," +coupons.get(i).getImage();
//			image.setImage(img);
//			image.setId(coupons.get(i).getId());
//			images.add(image);
//		
//		}
		
		//System.out.println(images);
		//return images;
	}
	
	
	public List<VendorCouponsDTO1> getCoupons(Long vendorId, Long couponType, Long customerId, List<Long> couponIds) throws UnsupportedEncodingException
	{
		
		
		/*System.out.println("1."+new java.util.Date());
		List<VendorCoupons> coupons = vendorCouponsRepository.findByLatest(vendorId,couponType);
		System.out.println("2."+new java.util.Date());		
		List<VendorCouponsDTO> vendorCoupons = toVendorCouponsDTO(coupons);
		System.out.println("3."+new java.util.Date());
		return vendorCoupons;*/
		Pageable paging = PageRequest.of(0, 10);
		return vendorCouponsRepository.findByLatest1(vendorId,couponType,customerId,couponIds,Timestamp.valueOf(LocalDateTime.now()),paging);
	}
	
//	public List<VendorCouponsDTO> toVendorCouponsDTO(List<VendorCoupons> coupons)
//	{
//		
//		List<VendorCouponsDTO> vendorCoupons = new ArrayList<>();
//		
//		for(int i=0;i<coupons.size();i++)
//		{
//			VendorCoupons coupon = coupons.get(i);
//			VendorCouponsDTO vendorCoupon= new VendorCouponsDTO();
//			
//			vendorCoupon.setHeader(coupon.getHeader());
//			vendorCoupon.setBody(coupon.getBody());
//			vendorCoupon.setFooter(coupon.getFooter());
//			vendorCoupon.setHeader_color(coupon.getHeader_color());
//			vendorCoupon.setBody_color(coupon.getBody_color());
//			vendorCoupon.setFooter_color(coupon.getFooter_color());
//			vendorCoupon.setHeader_align(coupon.getHeader_align());
//			vendorCoupon.setBody_align(coupon.getBody_align());
//			vendorCoupon.setFooter_align(coupon.getFooter_align());
//			vendorCoupon.setHeader_size(coupon.getHeader_size());
//			vendorCoupon.setBody_size(coupon.getBody_size());
//			vendorCoupon.setFooter_size(coupon.getFooter_size());
//			vendorCoupon.setFooter_font(coupon.getFooter_font());
//			vendorCoupon.setHeader_font(coupon.getHeader_font());
//			vendorCoupon.setBody_font(coupon.getBody_font());
//			vendorCoupon.setFooter_bold(coupon.getFooter_bold());
//			vendorCoupon.setHeader_bold(coupon.getHeader_bold());
//			vendorCoupon.setBody_bold(coupon.getBody_bold());
//			vendorCoupon.setFooter_style(coupon.getFooter_style());
//			vendorCoupon.setHeader_style(coupon.getHeader_style());
//			vendorCoupon.setBody_style(coupon.getBody_style());
//			vendorCoupon.setImage_align(coupon.getImage_align());
//			vendorCoupon.setFooter_decoration(coupon.getFooter_decoration());
//			vendorCoupon.setHeader_decoration(coupon.getHeader_decoration());
//			vendorCoupon.setBody_decoration(coupon.getBody_decoration());
//			vendorCoupon.setProfession(coupon.getProfession());
//			vendorCoupon.setGender(coupon.getGender());
//			vendorCoupon.setLikeCoupon(true);
//			vendorCoupon.setDisLikeCoupon(false);
//			
//			vendorCoupon.setLikeCount(100l);
//			
//			
//
//			
//			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
//			 
//				
//			
//		vendorCoupon.setExpireTime(formatter.format(coupon.getToDate()));
//			
//			vendorCoupon.setFromDate(coupon.getFromDate().toString());
//			vendorCoupon.setToDate(coupon.getToDate().toString());
//			
//			vendorCoupon.setImageId(coupon.getImage().getId());
//			vendorCoupon.setImage("data:image/jpeg;base64,"+coupon.getImage().getImage());
//			
//			vendorCoupon.setCouponType(coupon.getCouponType().getCouponType());
//
//			vendorCoupon.setKeywords(coupon.getKeywords());
//			vendorCoupon.setDescription(coupon.getDescription());
//			
//			vendorCoupon.setVendorId(coupon.getVendor().getUser().getUserId());
//			vendorCoupon.setLogo("data:image/jpeg;base64,"+coupon.getVendor().getLogo());
//			vendorCoupon.setId(coupon.getId());
//			vendorCoupon.setVendorName(coupon.getVendor().getUser().getName());
//			
//			vendorCoupons.add(vendorCoupon);
//		}
//		return vendorCoupons;
//		
//	}
	
	
	
	
	@Transactional
	public ImageStatusDTO uploadCoupon(MultipartFile file, Long vendorId, String subCategory, String keywords) throws UnsupportedEncodingException, IOException {
		
		
		InputStream is = new ByteArrayInputStream(file.getBytes());
        BufferedImage img = ImageIO.read(is);
		
       img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300,300);
               
        
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ImageIO.write(img,"JPEG",bos);
		
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		ImageDetails coupon = new ImageDetails();
		
		coupon.setImage(image);
		coupon.setDeleted(false);
		coupon.setUser(userDetailsRepository.findById(vendorId).get());
		coupon.setKeywords(keywords);
		coupon.setSubCateogry(subCategoryRepository.findById(Long.valueOf(subCategory)).get());
		
		ImageDetails couponSaved = couponDetailsRepository.save(coupon);
		if(couponSaved!= null)
		{
			imageStatus.setStatus(true);
			imageStatus.setMessage("Coupon Uploaded Successfully.");
		}
		else
		{
			imageStatus.setStatus(false);
			imageStatus.setMessage("Oops! Something Went Wrong.");
		}
		
		imageStatus.setId(couponSaved.getId());	
		return imageStatus;
	}
	@Transactional
public ImageStatusDTO uploadLogo(MultipartFile file, Long vendorId) throws UnsupportedEncodingException, IOException {
		
		
		InputStream is = new ByteArrayInputStream(file.getBytes());
        BufferedImage img = ImageIO.read(is);
		
       img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300,300);
               
//       for(int i=0;i<2;i++)
//   	{
//   	        img = logoService.trim(img);
//   	       
//   	}
//        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ImageIO.write(img,"JPEG",bos);
		
		
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		//System.out.println(vendorId);
		
		VendorDetails vendor=vendorDetailsRepository.findByUserId(vendorId).get();
		
		
		vendor.setLogo(image);
		
		VendorDetails vendorSaved = vendorDetailsRepository.save(vendor);
		if(vendorSaved!= null)
		{
			imageStatus.setStatus(true);
			imageStatus.setMessage("Logo Uploaded Successfully.");
		}
		else
		{
			imageStatus.setStatus(false);
			imageStatus.setMessage("Logo Upload Failed. Try Again");
		}
		
		return imageStatus;
	}
	@Transactional
	public Long likeCoupon(Long couponId, Long userId, boolean like) {
		
		
		Optional<LikeCoupons> coupon = likeCouponRepository.getCouponDetails(couponId, userId);
		
		
		
		LikeCoupons likeCoupon = new LikeCoupons();
		
		if(coupon.isPresent())
		{
			likeCoupon = coupon.get();
		}
		else
		{
		likeCoupon.setCoupon(vendorCouponsRepository.findById(couponId).get());
		likeCoupon.setCustomer(userDetailsRepository.findById(userId).get());
		}
		
		likeCoupon.setLikeCoupon(like);
		likeCoupon.setDisLikeCoupon(!like);
		
		
		likeCouponRepository.save(likeCoupon);
		
		return likeCouponRepository.getLikes(couponId);
		
		
	}
	@Transactional
public Long disLikeCoupon(Long couponId, Long userId, boolean dislike) {
		
		
	Optional<LikeCoupons> coupon = likeCouponRepository.getCouponDetails(couponId, userId);
	
	
	
	LikeCoupons dislikeCoupon = new LikeCoupons();
	
	if(coupon.isPresent())
	{
		dislikeCoupon = coupon.get();
	}
	else
	{
		dislikeCoupon.setCoupon(vendorCouponsRepository.findById(couponId).get());
		dislikeCoupon.setCustomer(userDetailsRepository.findById(userId).get());
	}
	
	dislikeCoupon.setLikeCoupon(!dislike);
	dislikeCoupon.setDisLikeCoupon(dislike);
	
	
	likeCouponRepository.save(dislikeCoupon);
	
	return likeCouponRepository.getDisLikes(couponId);
		
		
	}
	public String downloadCoupon(Long couponId, Long customerId) throws IOException {
		
		VendorCoupons coupon = vendorCouponsRepository.findById(couponId).get();
		
		ImageDetails image = coupon.getImage();
		
		String bottom_left="position: absolute;bottom: 2%;left: 7%;";
		String top_left="position: absolute; top: 2%;left: 7%;";
		String top_center="position: absolute;top: 2%;left: 50%;transform: translate(-50%, -0%);";
		String top_right="position: absolute;top: 2%;right: 7%;";
		String bottom_right="position: absolute;bottom: 2%;right: 7%;";
		String bottom_center="position:absolute;bottom: 2%;left: 50%;transform: translate(-50%, -0%);";
		String centered="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -0%);";
		String centered_left="position: absolute;top: 50%;left: 7%;"; 
		String centered_right="position: absolute;top: 50%;right: 7%;";
		
		Map<String, String> classMap = new HashMap<String, String>();
		
		classMap.put("bottom-left",bottom_left);
		classMap.put("top-left",top_left);
		classMap.put("top-center",top_center);
		classMap.put("top-right",top_right);
		classMap.put("bottom-right",bottom_right);
		classMap.put("bottom-center",bottom_center);
		classMap.put("centered",centered);
		classMap.put("centered-left",centered_left);
		classMap.put("centered-right",centered_right);
		
		//System.out.println(classMap.get(coupon.getHeader_align()));
		
		String htmlData="<div style='border :5px'> <div style=' padding: 0px; position: relative;width:300px;height:300px;overflow:hidden;'>"
				+ "<img style='width:300px;height:300px;' src='data:image/jpeg;base64,"+image.getImage()+"'></img>"
				+" <img src='data:image/jpeg;base64,"+coupon.getVendor().getLogo()+"' >"
                +" style='top_left;max-height: 50px;border: 1px solid  #bbb;'></img>"
				+ "<div style='"+classMap.get(coupon.getHeader_align())+";"
				+ "font-family:"+coupon.getHeader_font()+";color:"+coupon.getHeader_color()+";"
				+ "text-decoration:"+coupon.getHeader_decoration()+";font-weight:"+coupon.getHeader_bold()+";"
				+ "font-style:"+coupon.getHeader_style()+";font-size:"+coupon.getHeader_size()+"px'>"+coupon.getHeader().replace("\n", "<br>").replace(" ","&nbsp;")+"</div>"
				
				+ "<div style='"+classMap.get(coupon.getBody_align())+";"
				+ "font-family:"+coupon.getBody_font()+";color:"+coupon.getBody_color()+";"
				+ "text-decoration:"+coupon.getBody_decoration()+";font-weight:"+coupon.getBody_bold()+";"
				+ "font-style:"+coupon.getBody_style()+";font-size:"+coupon.getBody_size()+"px'>"+coupon.getBody().replace("\n", "<br>").replace(" ","&nbsp;")+"</div>"
				
				+ "<div style='"+classMap.get(coupon.getFooter_align())+";"
				+ "font-family:"+coupon.getFooter_font()+";color:"+coupon.getFooter_color()+";"
				+ "text-decoration:"+coupon.getFooter_decoration()+";font-weight:"+coupon.getFooter_bold()+";"
				+ "font-style:"+coupon.getFooter_style()+";font-size:"+coupon.getFooter_size()+"px'>"+coupon.getFooter().replace("\n", "<br>").replace(" ","&nbsp;")+"</div>"
				+"</div>"
				+"<div>wwww.thetakeoff.in<br/><br/>Subscribe to TakeOff by Reference Code 'TO'"+customerId+"'.<br/><br/>Enjoy the Experience of TakeOff."
				+"</div>"
				
				+"</div>";
		
		String couponStr="<img style='width:300px;height:350px;' src='data:image/jpeg;base64,"+logoService.createImage(htmlData,false)+"' />";
		
		return couponStr;
		
		
	}
	public Boolean deleteImage(Long imageId) {
	
		ImageDetails image=couponDetailsRepository.findById(imageId).get();
		
		image.setDeleted(true);
		
		couponDetailsRepository.save(image);
		
		return image.getDeleted();
	}



}
