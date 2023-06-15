package com.takeoff.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Redemption;
import com.takeoff.model.RedemptionSummary;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption,Long> {

	@Query("select r from Redemption r where r.coupon.id=(:couponId) and r.vendor.userId=(:vendorId) and r.customer.userId=(:customerId) and r.validTill >= (:currentTime) and r.userRedempted!=true")
	Redemption findPasscode(@Param("couponId") Long couponId,@Param("customerId")  Long customerId, @Param("vendorId")  Long vendorId, @Param("currentTime") Timestamp currentTime);

	@Query("select r from Redemption r where r.coupon.id=(:couponId) and r.vendor.userId=(:vendorId) and r.customer.userId=(:customerId) and r.validTill >= (:currentTime) and r.vendorAccepted=true and r.userRedempted!=true")
	Redemption findVendorAcceptedPasscode(@Param("couponId") Long couponId,@Param("customerId")  Long customerId, @Param("vendorId")  Long vendorId, @Param("currentTime") Timestamp currentTime);

	@Query("select r.coupon.id as couponId,r.customer.userId as customerId, r.vendor.userId as vendorId, "
			+ "DATE_FORMAT(r.redemOn, '%d %M %Y %h:%i:%s %p') as redemOn, r.vendor.name as vendorName, "
			+ "r.customer.name as customerName,r.coupon.couponType.couponType as couponType,"
			+ "r.coupon.vendor.address as address, r.coupon.vendor.user.contact as contact, r.customer.contact as customerContact,"
			+ " r.coupon.image.subCateogry.category.categoryName as category,  r.coupon.image.subCateogry.subCategoryName as subCategory "
			+ "from Redemption r where r.userRedempted=true and r.vendorAccepted=true and (r.customer.userId = (:customerId) or (:customerId)=0) and (r.vendor.userId = (:vendorId) or (:vendorId)=0) order by r.validTill desc	")
	List<RedemptionSummary> getRedemptionSummary(@Param("customerId")  Long customerId, @Param("vendorId")  Long vendorId);

}
