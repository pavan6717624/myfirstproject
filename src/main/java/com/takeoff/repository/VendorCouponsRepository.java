package com.takeoff.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.VendorCoupons;
import com.takeoff.model.VendorCouponsDTO1;

public interface VendorCouponsRepository  extends PagingAndSortingRepository<VendorCoupons,Long> {
	
	@Query("select c from VendorCoupons c where (c.couponType.id = (:couponType) or (:couponType) = 0) and (c.vendor.user.userId = (:userId) or (:userId) = 0) order by id desc")
	List<VendorCoupons> findByLatest(@Param("userId") Long userId, @Param("couponType") Long couponType);
	
	
// 	@Query("select "
// 			+ "c.header as header, c.body as body, c.footer as footer, "
// 			+ " c.header_color as header_color, c.body_color as body_color, c.footer_color as footer_color,"
// 			+ "c.header_align as header_align, c.body_align as body_align, c.footer_align as footer_align, c.image_align as image_align, "
// 			+ "c.header_size as header_size, c.body_size as body_size, c.footer_size as footer_size, "
// 			+ "c.header_font as header_font, c.body_font as body_font, c.footer_font as footer_font, "
// 			+ "c.header_bold as header_bold, c.body_bold as body_bold, c.footer_bold as footer_bold, "
// 			+ "c.header_style as header_style, c.body_style as body_style, c.footer_style as footer_style, "
// 			+ "c.header_decoration as header_decoration, c.body_decoration as body_decoration, c.footer_decoration as footer_decoration, "
// 			+ "c.profession as profession, c.gender as gender,  "
// 			+" (select l.likeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as likeCoupon, "
// 			+" (select l.disLikeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as disLikeCoupon, "
// 			+" (select sum(case when l.likeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as likeCount, "
// 			+" (select sum(case when l.disLikeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as dislikeCount, "
// 			+" (select count(r) from Redemption r where r.coupon = c and r.vendorAccepted=true and r.userRedempted=true) as redemptionCount, "
// 			+" DATE_FORMAT(c.toDate, '%d %M %Y %h:%i:%s %p') as expireTime, c.fromDate as fromDate, c.toDate as toDate, "
// 			+" c.image.id as imageId, concat('data:image/jpeg;base64,',c.image.image) as image, "
// 			+" c.couponType.couponType as couponType, c.image.keywords as keywords, c.description as description, "
// 			+" c.vendor.user.userId as vendorId, concat('data:image/jpeg;base64,',c.vendor.logo) as logo, "
// 			+" c.id as id, c.vendor.user.name as vendorName, (case when (c.fromDate <= (:current_time) and c.toDate >=(:current_time)) then false else true end) as expired, "
// 	       		+" c.vendor.address as address, c.vendor.user.contact as contact "
			
// 			+ " from VendorCoupons c where ((c.fromDate <= (:current_time) and c.toDate >=(:current_time)) or "
// 			+ "(:userId)!=0L) and (c.id not in (:couponIds)) and (c.couponType.id = (:couponType) or ((:couponType) = 0 "
// 			+ "and ((c.couponType.id!=1 and c.couponType.id!=2) or (:userId)!=0 ))) and (c.vendor.user.userId = (:userId) or "
// 			+ "(:userId) = 0) and (c.image.subCateogry.category.id=(:category) or (:category=0)) and "
// 			+" (c.image.subCateogry.id=(:subCategory) or (:subCategory=0)) "
// 			+ " order by id desc")
// 	List<VendorCouponsDTO1> findByLatest1(@Param("userId") Long userId, @Param("couponType") Long couponType, @Param("customerId") Long customerId , @Param("couponIds") List<Long> couponIds,@Param("current_time") Timestamp current_time,@Param("category") Long category,@Param("subCategory") Long subCategory,Pageable pageable);
	
	
	@Query("select "
			+ "c.header as header, c.body as body, c.footer as footer, "
			+ " c.header_color as header_color, c.body_color as body_color, c.footer_color as footer_color,"
			+ "c.header_align as header_align, c.body_align as body_align, c.footer_align as footer_align, c.image_align as image_align, "
			+ "c.header_size as header_size, c.body_size as body_size, c.footer_size as footer_size, "
			+ "c.header_font as header_font, c.body_font as body_font, c.footer_font as footer_font, "
			+ "c.header_bold as header_bold, c.body_bold as body_bold, c.footer_bold as footer_bold, "
			+ "c.header_style as header_style, c.body_style as body_style, c.footer_style as footer_style, "
			+ "c.header_decoration as header_decoration, c.body_decoration as body_decoration, c.footer_decoration as footer_decoration, "
			+ "c.profession as profession, c.gender as gender,  "
			//10013, 10145, 102936, 103043, 10117, 102570, 103136, 10128, 104382,10025
	       //	+" (select (case when ((u.type='free' or u.type='Free') and c.exclusiveFor = 'ALL' and c.vendor.user.loginId in (10013, 102936, 109375, 10145,109309, 106598, 103200, 103136, 10028, 105035,109451,109440) and c.couponType.id != 1) then 'Pay' else u.type end) from UserDetails u where u.userId=(:customerId) or u.loginId=(:customerId)) as subscriptionType, "
	     	+" (select (case when ((u.type='free' or u.type='Free') and c.couponType.id = 1) then 'Free' else 'Pay' end) from UserDetails u where u.userId=(:customerId) or u.loginId=(:customerId)) as subscriptionType, "
	       +" (select l.likeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as likeCoupon, "
			+" (select l.disLikeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as disLikeCoupon, "
			+" (select sum(case when l.likeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as likeCount, "
			+" (select sum(case when l.disLikeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as dislikeCount, "
			+" (select count(r) from Redemption r where r.coupon = c and r.vendorAccepted=true and r.userRedempted=true) as redemptionCount, "
			+" DATE_FORMAT(c.toDate, '%d %M %Y %h:%i:%s %p') as expireTime, c.fromDate as fromDate, c.toDate as toDate, "
			+" c.image.id as imageId, concat('data:image/jpeg;base64,',c.image.image) as image, "
			+" c.couponType.couponType as couponType, c.image.keywords as keywords, c.description as description, "
			+" c.vendor.user.userId as vendorId, concat('data:image/jpeg;base64,',c.vendor.logo) as logo, "
			+" c.id as id, c.vendor.user.name as vendorName, (case when (c.fromDate <= (:current_time) and c.toDate >=(:current_time)) then false else true end) as expired, "
	       		+" c.vendor.address as address, c.vendor.user.contact as contact,c.exclusiveFor as exclusiveFor "
			
			+ " from VendorCoupons c where ((c.fromDate <= (:current_time) and c.toDate >=(:current_time)) or "
			+ "(:userId)!=0L) and (c.id not in (:couponIds)) and (c.couponType.id = (:couponType) or ((:couponType) = 0 "
			+ "and ((c.couponType.id!=1) or (:userId)!=0 ))) and (c.vendor.user.userId = (:userId) or "
			+ "((:userId) = 0   and :customerId!=0 and c.vendor.user.city in (select city from UserDetails ud where ud.userId=:customerId)   )) and (c.image.subCateogry.category.id=(:category) or (:category=0)) and "
			+ " (c.image.subCateogry.id=(:subCategory) or (:subCategory=0))  and c.vendor.user.isDeleted!=true and c.vendor.user.isDisabled!=true and "

// 	       		+ " ((c.couponType.id > 2) or "
// 	       		+ "(c.couponType.id = 1 and c.vendor.user.userId not in (select r.vendor.userId from Redemption r where r.userRedempted = true and r.vendorAccepted = true and r.customer.userId=(:customerId) and r.coupon.couponType=1)) or "
// 	       		+ "(c.couponType.id = 2 and c not in (select r.coupon from Redemption r where r.userRedempted = true and r.vendorAccepted = true and r.customer.userId=(:customerId) and r.coupon.couponType=2))) and "
			
	      		+ " ((c.couponType.id > 3) or "
	       		+ "(c.couponType.id = 1 and c.vendor.user.userId not in (select r.vendor.userId from Redemption r where r.userRedempted = true and r.vendorAccepted = true and r.customer.userId=(:customerId) and r.coupon.couponType=1)) or "
	       		+ "(c.couponType.id = 2 and c not in (select r.coupon from Redemption r where r.userRedempted = true and r.vendorAccepted = true and r.customer.userId=(:customerId) and r.coupon.couponType=2)) or "
	       		+ "(c.couponType.id = 3 and c not in (select r.coupon from Redemption r where r.userRedempted = true and r.vendorAccepted = true and r.customer.userId=(:customerId) and r.coupon.couponType=3 and date(r.redemOn)=CURRENT_DATE()))) and "
			
	       
			+ "( c.image.keywords like :keyword1 or c.image.keywords like :keyword2 or c.image.keywords like :keyword3 or "
			+ " c.image.keywords like :keyword4 or c.image.keywords like :keyword5 or "
			
			+ " c.header like :keyword1 or c.header like :keyword2 or c.header like :keyword3 or "
			+ " c.header like :keyword4 or c.header like :keyword5 or "
			
			+ " c.body like :keyword1 or c.body like :keyword2 or c.body like :keyword3 or "
			+ " c.body like :keyword4 or c.body like :keyword5 or "
			
			+ " c.footer like :keyword1 or c.footer like :keyword2 or c.footer like :keyword3 or "
			+ "c.footer like :keyword4 or c.footer like :keyword5 or  "
	       
	       		+ " c.keywords like :keyword1 or c.keywords like :keyword2 or c.keywords like :keyword3 or "
			+ "c.keywords like :keyword4 or c.keywords like :keyword5 or  "
			
			+ " c.vendor.user.name like :keyword1 or c.vendor.user.name like :keyword2 or c.vendor.user.name like :keyword3 or "
			+ "c.vendor.user.name like :keyword4 or c.vendor.user.name like :keyword5 ) and "
			+ " (c.exclusiveFor like 'ALL' or c.exclusiveFor like %:refererId% or c.exclusiveFor like %:contact% or (:customerId) = 0) and "
			+ " (c.vendor.user.userId = (:vendorSelected) or (:vendorSelected) = 0) "
			+ " order by concat(c.vendor.rating,'',rand()) desc")
	
	List<VendorCouponsDTO1> findByLatest1(
			@Param("userId") Long userId, 
			@Param("couponType") Long couponType, 
			@Param("customerId") Long customerId , 
			@Param("couponIds") List<Long> couponIds,
			@Param("current_time") Timestamp current_time,
			@Param("category") Long category,
			@Param("subCategory") Long subCategory,
			@Param("keyword1") String keyword1,
			@Param("keyword2") String keyword2,
			@Param("keyword3") String keyword3,
			@Param("keyword4") String keyword4,
			@Param("keyword5") String keyword5,
			@Param("refererId") String refererId,
			@Param("vendorSelected") Long vendorSelected,
			@Param("contact") String contact,
			Pageable pageable);
	
	@Query("select "
			+ "c.header as header, c.body as body, c.footer as footer, "
			+ " c.header_color as header_color, c.body_color as body_color, c.footer_color as footer_color,"
			+ "c.header_align as header_align, c.body_align as body_align, c.footer_align as footer_align, c.image_align as image_align, "
			+ "c.header_size as header_size, c.body_size as body_size, c.footer_size as footer_size, "
			+ "c.header_font as header_font, c.body_font as body_font, c.footer_font as footer_font, "
			+ "c.header_bold as header_bold, c.body_bold as body_bold, c.footer_bold as footer_bold, "
			+ "c.header_style as header_style, c.body_style as body_style, c.footer_style as footer_style, "
			+ "c.header_decoration as header_decoration, c.body_decoration as body_decoration, c.footer_decoration as footer_decoration, "
			+ "c.profession as profession, c.gender as gender,  "
			+" (select l.likeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as likeCoupon, "
			+" (select l.disLikeCoupon from LikeCoupons l where l.coupon=c and l.customer.userId=(:customerId)) as disLikeCoupon, "
			+" (select sum(case when l.likeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as likeCount, "
			+" (select sum(case when l.disLikeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon=c) as dislikeCount, "
			+" (select count(r) from Redemption r where r.coupon = c and r.vendorAccepted=true and r.userRedempted=true) as redemptionCount, "
			+" DATE_FORMAT(c.toDate, '%d %M %Y %h:%i:%s %p') as expireTime, c.fromDate as fromDate, c.toDate as toDate, "
			+" c.image.id as imageId, concat('data:image/jpeg;base64,',c.image.image) as image, "
			+" c.couponType.couponType as couponType, c.image.keywords as keywords, c.description as description, "
			+" c.vendor.user.userId as vendorId, concat('data:image/jpeg;base64,',c.vendor.logo) as logo, "
			+" c.id as id, c.vendor.user.name as vendorName, (case when (c.fromDate <= (:current_time) and c.toDate >=(:current_time)) then false else true end) as expired, "
	       		+" c.vendor.address as address, c.vendor.user.contact as contact, c.exclusiveFor as exclusiveFor "
			
			+ " from VendorCoupons c where id=(:couponId)")
	
	VendorCouponsDTO1 getCoupon(@Param("customerId") Long customerId, @Param("couponId") Long couponId,@Param("current_time") Timestamp current_time );
	
	//@Query("select (case when count(*)>0 then true else false end) from VendorCoupons c where c.vendor.user.userId = (:userId) and c.couponType.id=1")
	@Query("select (case when count(*)>0 then true else true end) from VendorCoupons c where c.vendor.user.userId = (:userId) and c.couponType.id=1")
	Boolean complimentaryExists(@Param("userId") Long userId);	
	
	@Query("select count(*) from Redemption r where r.vendorAccepted=true and r.userRedempted= true and r.coupon.vendor.user.userId = (:vendorId) and r.customer.userId = (:userId) and r.coupon.couponType.id in (3,4,5,6)")
	Long other3456Count(@Param("vendorId") Long vendorId, @Param("userId") Long userId);
	
	@Query("select count(*) from Redemption r where r.vendorAccepted=true and r.userRedempted= true and r.coupon.couponType.id=1  and r.customer.userId = (:userId) and r.coupon.vendor.user.userId = (:vendorId)")
	Long specific12Count(@Param("vendorId") Long vendorId, @Param("userId") Long userId);
	
	@Query("select count(*) from Redemption r where r.vendorAccepted=true and r.userRedempted= true and r.coupon.couponType.id=2  and r.customer.userId = (:userId) and r.coupon.id = (:couponId)")
	Long freeCount(@Param("couponId") Long couponId, @Param("userId") Long userId);

	@Query("select count(*) from Redemption r where r.vendorAccepted=true and r.userRedempted= true and r.customer.userId = (:userId) and MONTH(r.redemOn) = MONTH(CURRENT_DATE) and YEAR(r.redemOn) = YEAR(CURRENT_DATE)")	
	Long freeSubscriberRedemptionCount(@Param("userId") Long userId);	

	
	@Query("select "
			+ "c.header as header, c.body as body, "
			+ " c.header_color as header_color, c.body_color as body_color, "
			+ "c.header_align as header_align, c.body_align as body_align, "
			+ "c.header_size as header_size, c.body_size as body_size, "
			+ "c.header_font as header_font, c.body_font as body_font, "
			+ "c.header_bold as header_bold, c.body_bold as body_bold,  "
			+ "c.header_style as header_style, c.body_style as body_style, "
			+ "c.header_decoration as header_decoration, c.body_decoration as body_decoration, "
			+" DATE_FORMAT(c.toDate, '%d %M %Y %h:%i:%s %p') as expireTime, "
			+" c.image.id as imageId, concat('data:image/jpeg;base64,',c.image.image) as image "
			+ " from VendorCoupons c where c.couponType.id!=2 and (c.fromDate <= (:current_time) and c.toDate >=(:current_time)) order by rand()")
	
	List<VendorCouponsDTO1> getHomePageCoupons(@Param("current_time") Timestamp current_time,Pageable pageable );
	
	@Query("select count(*) from Redemption r where r.vendorAccepted=true and r.userRedempted= true and r.coupon.couponType.id=3  and r.customer.userId = (:userId) and r.coupon.id = (:couponId) and date(r.redemOn)=CURRENT_DATE() ")
	Long DailyDealsCount(@Param("couponId") Long couponId, @Param("userId") Long userId);
	

	
	
	
}
