package com.takeoff.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.CouponDetails;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.repository.CouponDetailsRepository;

@Service
public class CouponService {
	
	@Autowired
	
	CouponDetailsRepository couponDetailsRepository;
	
	
	
	public List<ImageStatusDTO> getImages() throws UnsupportedEncodingException
	{
		List<CouponDetails> coupons = couponDetailsRepository.findAll();
		List<ImageStatusDTO> images = new ArrayList<>();
		
		for(int i=0;i<coupons.size();i++)
		{
			ImageStatusDTO image=new ImageStatusDTO();
			String img = "data:image/jpeg;base64," +coupons.get(i).getImage();
			image.setImage(img);
			images.add(image);
		}
		
		
		return images;
	}

	public ImageStatusDTO uploadCoupon(MultipartFile file) throws UnsupportedEncodingException, IOException {
		
		
		String image = new String(Base64.encodeBase64(file.getBytes()), "UTF-8");
		
		ImageStatusDTO imageStatus=new ImageStatusDTO();
		
		CouponDetails coupon = new CouponDetails();
		
		coupon.setImage(image);
		
		CouponDetails couponSaved = couponDetailsRepository.save(coupon);
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

}
