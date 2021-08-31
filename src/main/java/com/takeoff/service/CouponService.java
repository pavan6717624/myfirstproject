package com.takeoff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.ImageDetails;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.VendorCouponsDTO;
import com.takeoff.repository.CategoryRepository;
import com.takeoff.repository.ImageDetailsRepository;
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
	
	public List<ImageStatusDTO> getImages(Long vendorId) throws UnsupportedEncodingException
	{
		List<ImageDetails> coupons = couponDetailsRepository.findByLatest(vendorId);
		List<ImageStatusDTO> images = new ArrayList<>();
		
		for(int i=0;i<coupons.size();i++)
		{
			ImageStatusDTO image=new ImageStatusDTO();
			String img = "data:image/jpeg;base64," +coupons.get(i).getImage();
			image.setImage(img);
			image.setId(coupons.get(i).getId());
			images.add(image);
		
		}
		
		System.out.println(images);
		return images;
	}
	
	
	public List<VendorCouponsDTO> getCoupons(Long vendorId, Long couponType) throws UnsupportedEncodingException
	{
		List<VendorCoupons> coupons = vendorCouponsRepository.findByLatest(vendorId,couponType);
		List<VendorCouponsDTO> vendorCoupons = new ArrayList<>();
		
		for(int i=0;i<coupons.size();i++)
		{
			VendorCoupons coupon = coupons.get(i);
			VendorCouponsDTO vendorCoupon= new VendorCouponsDTO();
			
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
			vendorCoupon.setLike(true);
			vendorCoupon.setDislike(false);
			
			vendorCoupon.setLikeCount(100l);
			
			

			
			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
			 
				
			
		vendorCoupon.setExpireTime(formatter.format(coupon.getToDate()));
			
			vendorCoupon.setFromDate(coupon.getFromDate().toString());
			vendorCoupon.setToDate(coupon.getToDate().toString());
			
			vendorCoupon.setImageId(coupon.getImage().getId());
			vendorCoupon.setImage("data:image/jpeg;base64,"+coupon.getImage().getImage());
			
			vendorCoupon.setCouponType(coupon.getCouponType().getCouponType());

			vendorCoupon.setKeywords(coupon.getKeywords());
			vendorCoupon.setDescription(coupon.getDescription());
			
			vendorCoupon.setVendorId(coupon.getVendor().getUser().getUserId());
			vendorCoupon.setLogo("data:image/jpeg;base64,"+coupon.getVendor().getLogo());
			vendorCoupon.setId(coupon.getId());
			vendorCoupon.setVendorName(coupon.getVendor().getUser().getName());
			
			vendorCoupons.add(vendorCoupon);
		}

		return vendorCoupons;
	}
	
	@Transactional
	public ImageStatusDTO uploadCoupon(MultipartFile file, Long vendorId, String subCategory, String keywords) throws UnsupportedEncodingException, IOException {
		
		
		InputStream is = new ByteArrayInputStream(file.getBytes());
        BufferedImage img = ImageIO.read(is);
		
       img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 350,250);
               
        
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ImageIO.write(img,"JPEG",bos);
		
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		ImageDetails coupon = new ImageDetails();
		
		coupon.setImage(image);
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
		
       img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 350,250);
               
       for(int i=0;i<2;i++)
   	{
   	        img = logoService.trim(img);
   	       
   	}
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ImageIO.write(img,"JPEG",bos);
		
		
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		System.out.println(vendorId);
		
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


}
