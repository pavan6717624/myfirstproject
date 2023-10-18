package com.takeoff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.takeoff.repository.CustomerDetailsRepository;
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
	

@Autowired

CustomerDetailsRepository customerDetailsRepository;
	

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
	
	public List<ImageDetailsDTO> getImages(Long vendorId, List<Long> imageIds,Long category, Long subCategory, String city, String keywords) throws UnsupportedEncodingException
	{
		Pageable paging = PageRequest.of(0, 10);
		
		String[] keyWords = keywords.split(",");
		
		String[] keyword = {"","","","",""};
		
		for(int i=0;i<keyWords.length && i<5; i++)
			keyword[i]="%"+keyWords[i]+"%";
		System.out.println("vendorId size :: "+vendorId);
		List<ImageDetailsDTO> images = 
		couponDetailsRepository.findByLatest(vendorId,imageIds,category, subCategory, keyword[0], keyword[1],keyword[2],keyword[3],keyword[4],paging);
		
		System.out.println("images size :: "+images.size());
		
		//images=images.stream().filter( c -> filterImages(c,keyWords)).collect(Collectors.toList());
		
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
	
	public Boolean filterImages(ImageDetailsDTO image,List<String> keyWords)
	{
		for(int i=0;i<keyWords.size();i++)
		{
			if(image.getKeywords().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
		}
		
		
		return false;
	}
	
// 	public List<VendorCouponsDTO1> getCoupons(Long vendorId, Long couponType, Long customerId, List<Long> couponIds, Long category, Long subCategory, String city, String keywords) throws UnsupportedEncodingException
// 	{
		
		
// 		/*System.out.println("1."+new java.util.Date());
// 		List<VendorCoupons> coupons = vendorCouponsRepository.findByLatest(vendorId,couponType);
// 		System.out.println("2."+new java.util.Date());		
// 		List<VendorCouponsDTO> vendorCoupons = toVendorCouponsDTO(coupons);
// 		System.out.println("3."+new java.util.Date());
// 		return vendorCoupons;*/
// 		Pageable paging = PageRequest.of(0, 10);
		
// 		System.out.println(category+" "+subCategory+" "+city+" "+keywords);
		
// 		List<String> keyWords = Arrays.asList(keywords.split(","));
		
// 		List<VendorCouponsDTO1> coupons = vendorCouponsRepository.findByLatest1(vendorId,couponType,customerId,couponIds,Timestamp.valueOf(LocalDateTime.now()),category, subCategory, paging);
		
// 		coupons=coupons.stream().filter( c -> check(c,keyWords)).collect(Collectors.toList());
		
// 		return coupons;
// 	}
	
		public List<VendorCouponsDTO1> getCoupons(Long vendorId, Long couponType, Long customerId, List<Long> couponIds, Long category, Long subCategory, String city, String keywords, Long vendorSelected) throws UnsupportedEncodingException
	{
		
		Pageable  paging = null;	
		if(vendorId > 0 && customerId == 0)
		paging = PageRequest.of(0, 100);
		else
		paging = PageRequest.of(0, 10);
		

		String[] keyWords = keywords.split(",");
		
		String[] keyword = {"0","0","0","0","0"};
		
		for(int i=0;i<keyWords.length && i<5; i++)
			keyword[i]="%"+keyWords[i]+"%";
		
		String refererId="";
		
		if(customerId!=0L)
		refererId = customerDetailsRepository.findByUserId(customerId).get().getRefererId();
		
		String contact="";
		if(customerId!=0L)
			contact = customerDetailsRepository.findByUserId(customerId).get().getUser().getContact();
			                                                                                                                      
	//	System.out.println("RefererId:: "+refererId+", City : "+ customerDetailsRepository.findByUserId(customerId).get().getUser().getCity());      
                            
		
		List<VendorCouponsDTO1> coupons = vendorCouponsRepository.findByLatest1(vendorId,couponType,customerId,couponIds,Timestamp.valueOf(LocalDateTime.now()),category, subCategory, keyword[0], keyword[1],keyword[2],keyword[3],keyword[4],refererId, vendorSelected, contact, paging);
		
		// coupons=coupons.stream().filter( c -> check(c,keyWords)).collect(Collectors.toList());
		
		return coupons;
	}
	
	public Boolean check(VendorCouponsDTO1 coupon,List<String> keyWords)
	{
		for(int i=0;i<keyWords.size();i++)
		{
			if(coupon.getKeywords().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
		
			else if(coupon.getVendorName().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
			
			else if(coupon.getHeader().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
			else if(coupon.getFooter().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
			else if(coupon.getBody().toLowerCase().contains(keyWords.get(i).toLowerCase()))
			{
				return true;
			}
		}
		
		
		return false;
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
		
   //    img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300,300);
               
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
	public String downloadCoupon(Long couponId) throws IOException {
		
		
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		 Long customerId = Long.valueOf(userDetails.getUsername());
		
		VendorCoupons coupon = vendorCouponsRepository.findById(couponId).get();
		
		ImageDetails image = coupon.getImage();
		
	
		
		//System.out.println(classMap.get(coupon.getHeader_align()));
		
		String htmlData="<html><head><style>@page { size: 1000px 1000px; } @page { margin: 0; }"
				+ "body { margin: 0; padding-left:2px }</style></head><body>"
				+ "<table border='1'  style='border-collapse: collapse;border: 2px solid grey;word-wrap:break-word;'  cellpadding='3'>"
				+ "<tr><td style='vertical-align: center'>"
				+ "<div style=' padding: 0px; position: relative;width:300px;height:300px;overflow:hidden;'>"
				+ "<img style='width:300px;height:300px;' src='data:image/jpeg;base64,"+image.getImage()+"'></img>"
				
				
+ "                                    </div></td><td style='vertical-align: center;'>"
				
+" <div"
+ "                                        style='text-align:center;padding: 5px; width: 300px'>"
+ ""
+ ""
+ "                                        <h5 style='text-decoration:"+coupon.getHeader_decoration()+";"
+ "                                            font-weight:"+coupon.getHeader_bold()+";"
+ "                                            font-style:"+coupon.getHeader_style()+";"
+ "                                            margin-bottom:0.5em;word-wrap: break-word;'>"
+ "                                            "+StringEscapeUtils.escapeXml11(coupon.getHeader())+"</h5>"
+ ""
+ "                                        <p style='text-decoration:"+coupon.getBody_decoration()+";"
+ "                                            font-weight:"+coupon.getBody_bold()+";font-style:"+coupon.getBody_style()+";"
+ "                                            margin-bottom:0.5em;word-wrap: break-word;'>"
+ "                                            "+StringEscapeUtils.escapeXml11(coupon.getBody())+"</p>"
+ ""
+ "                                        <p style='text-decoration:"+coupon.getFooter_decoration()+";"
+ "                                            font-weight:"+coupon.getFooter_bold()+";"
+ "                                            font-style:"+coupon.getFooter_style()+"; margin-bottom:0.5em; word-wrap:break-word;'>"
+ "                                          "+StringEscapeUtils.escapeXml11(coupon.getFooter())+"</p>"
+ "											<p> OutLet Name: "+ StringEscapeUtils.escapeXml11(coupon.getVendor().getUser().getName())+"</p>"
+ "											<p> Address: "+StringEscapeUtils.escapeXml11(coupon.getVendor().getAddress())+"<p>Mobile: "+coupon.getVendor().getUser().getContact()+"</p></p>"
+ ""

+"</div>"
				+"<div style='text-align:center'>wwww.thetakeoff.in<br/><br/>Subscribe to TakeOff by <br/>Reference Code 'TO"+customerId+"'.<br/><br/>Enjoy the Experience of TakeOff."
				+"</div></td></tr></table>"
				
				
		+"</body></html>";
		
		
		String couponStr="data:image/jpeg;base64,"+logoService.createImage(htmlData,false);
		
		return couponStr;
		
		
	}
	public Boolean deleteImage(Long imageId) {
	
		ImageDetails image=couponDetailsRepository.findById(imageId).get();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		image.setDeleted(true);
		image.setDeletedBy(Long.valueOf(userDetails.getUsername()));
		
	
		
		couponDetailsRepository.save(image);
		
		
		return image.getDeleted();
	}
	
	public ImageStatusDTO getImage(MultipartFile file)
	{
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		try
		{
			
			
			
			InputStream is = new ByteArrayInputStream(file.getBytes());
	        BufferedImage img = ImageIO.read(is);
			
	      
	        img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT,600,600);
	        
	        
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

	public VendorCouponsDTO1 getCoupon(Long couponId)
	{
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vendorCouponsRepository.getCoupon(Long.valueOf(userDetails.getUsername()),couponId,Timestamp.valueOf(LocalDateTime.now()));
	}
	public List<VendorCouponsDTO1> getHomePageCoupons()
	{
		
		Pageable paging = PageRequest.of(0, 20);
		
		List<VendorCouponsDTO1> coupons = vendorCouponsRepository.getHomePageCoupons(Timestamp.valueOf(LocalDateTime.now()), paging);
		
		// coupons=coupons.stream().filter( c -> check(c,keyWords)).collect(Collectors.toList());
		
		return coupons;
	}


}
