package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.KYCDetails;
import com.takeoff.model.KYCDetailsDTO;

@Repository
public interface KYCDetailsRepository  extends JpaRepository<KYCDetails,Long> {

	
	@Query("select concat((:message),'') as message,k.id as id, k.customer.user.name as name, k.customer.user.email as email, k.customer.user.userId as customerId, "
		+ "k.customer.user.contact as contact,  k.pan as pan, k.cname as cname, k.bname as bname, k.ifsc as ifsc,k.account as account,"
		+ " k.customer.walletAmount as walletAmount, k.customer.creditAmount as creditAmount,"
		+ " k.panStatus as panStatus, k.kycStatus as kycStatus, k.statement as statement "
		+ "from KYCDetails k where k.customer.user.userId=(:customerId) or (:customerId)=0 order by k.customer.user.userId asc")
	List<KYCDetailsDTO> getKYCDetails(@Param("customerId") Long customerId, @Param("message") String message) ;
 
	@Query("select k from KYCDetails k where k.customer.user.userId=(:customerId) ")
	KYCDetails findByCustomerId(@Param("customerId") Long customerId);
	
	@Query("select  c.user.name as name, c.user.userId as customerId, c.user.email as email, "
		+ " c.user.contact as contact, c.walletAmount as walletAmount, c.creditAmount as creditAmount "
		+ " from CustomerDetails c where c.user.userId=(:customerId) order by c.user.userId asc")
	List<KYCDetailsDTO> getWalletDetails(@Param("customerId") Long customerId);
	
	@Query("select  c.user.name as name, c.user.userId as customerId, c.user.email as email,"
			+ " c.user.contact as contact, c.walletAmount as walletAmount, c.creditAmount as creditAmount "
			+ " from CustomerDetails c where c.user.userId not in (:customerIds) and c.user.role.roleName not like 'Admin' order by c.user.userId asc")
		List<KYCDetailsDTO> getOtherCustomerDetails(@Param("customerIds") List<Long> customerIds);
	
	
	

}
