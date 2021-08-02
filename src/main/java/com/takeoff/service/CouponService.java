package com.takeoff.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.ImageDetails;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.repository.ImageCouponDetailsRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class CouponService {
	
	@Autowired
	
	ImageCouponDetailsRepository couponDetailsRepository;
	
	@Autowired
	
	VendorDetailsRepository vendorDetailsRepository;
	
	
	@Autowired
	
	UserDetailsRepository userDetailsRepository;
	
	public List<ImageStatusDTO> getImages(Long vendorId) throws UnsupportedEncodingException
	{
		List<ImageDetails> coupons = couponDetailsRepository.findByLatest(vendorId);
		List<ImageStatusDTO> images = new ArrayList<>();
		
		for(int i=0;i<coupons.size();i++)
		{
			ImageStatusDTO image=new ImageStatusDTO();
			String img = "data:image/jpeg;base64," +coupons.get(i).getImage();
			image.setImage(img);
			images.add(image);
		}
		
		System.out.println(images);
		return images;
	}

	public ImageStatusDTO uploadCoupon(MultipartFile file, Long vendorId) throws UnsupportedEncodingException, IOException {
		
		
		String image = new String(Base64.encodeBase64(file.getBytes()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		ImageDetails coupon = new ImageDetails();
		
		coupon.setImage(image);
		coupon.setUser(userDetailsRepository.findById(vendorId).get());
		
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
		
		return imageStatus;
	}
	
public ImageStatusDTO uploadLogo(MultipartFile file, Long vendorId) throws UnsupportedEncodingException, IOException {
		
		
		String image = new String(Base64.encodeBase64(file.getBytes()), "UTF-8");
		
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
