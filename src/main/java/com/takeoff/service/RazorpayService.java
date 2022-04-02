package com.takeoff.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.takeoff.domain.HitsReceived;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.repository.HitsReceivedRepository;

@Service
public class RazorpayService {
	
//	private final String keyId="rzp_test_WJFhmfMmFRxETB";
//	private final String keySecret="g5Wm3ap5wCmLsioYJSoQoa35";
	
	
	private final String keyId="rzp_live_nWA6UVrzTQFr9W";
	private final String keySecret="IoKCC6msyc9zduVg5ZK6sIa5";
	//private final Long amount = 119900L;
	private final Long amount = 99900L;
	// private final Long amount = 100L;
	
	@Autowired
	HitsReceivedRepository hitsReceivedRepository;
	
	@Transactional
	public void recordHits(String referer)
	{
		HitsReceived hit = new HitsReceived();
		hit.setReferer(referer);
		hit.setHitOn(Timestamp.valueOf(LocalDateTime.now()));
		hitsReceivedRepository.save(hit);
	}
	
	public List<HitsReceived> getHitsReceived()
	{
		return hitsReceivedRepository.getHitsReceived();
	}
	
	public Boolean subscribe(SubscriptionDTO subscription) throws RazorpayException, IOException
	{
	RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
	
	String razorpay_order_id  = subscription.getRazorpay_order_id();
	String razorpay_payment_id  = subscription.getRazorpay_payment_id();
	String razorpay_signature  = subscription.getRazorpay_signature();
	

	List<Payment> payments = razorpayClient.Orders.fetchPayments(razorpay_order_id);
	
	
	Boolean statusStr=false;
	
	
	List<Payment> pay = payments.stream().filter(o-> {JSONObject jsonObject = new JSONObject(String.valueOf(o));
	Boolean captured = jsonObject.getBoolean("captured"); return captured; }).collect(Collectors.toList());

	System.out.println(pay+" "+payments);
	
	if(pay.size() > 0)
	{
	Payment payment = pay.get(0);
	System.out.println(payments);
	JSONObject jsonObject = new JSONObject(String.valueOf(payment));
	String id = jsonObject.getString("id");
	int amount = (jsonObject.getInt("amount")/100);
	System.out.println(amount+" "+id+" "+razorpay_payment_id);
	JSONObject options = new JSONObject();
	options.put("razorpay_order_id", razorpay_order_id);
	options.put("razorpay_payment_id", razorpay_payment_id);
	options.put("razorpay_signature", razorpay_signature);
	Boolean paymentStatus = Utils.verifyPaymentSignature(options, keySecret);
	if(amount == (int)(this.amount/100) && paymentStatus)
	{
		
		statusStr=true;	
	}
	else
	{
		System.out.println("Mapping Failed for "+razorpay_order_id+", "+razorpay_payment_id+" @ "+amount+","+paymentStatus);
	}
	}
	return statusStr;
	}
	public String getOrderId() throws RazorpayException
	{
	
		
		RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

	 
    Timestamp ts = new Timestamp(new java.util.Date().getTime());
    String txnid = ts.toString().replace(":","").replace(" ","").replace("-","").replace(".","");

JSONObject options = new JSONObject();
options.put("amount", amount);
options.put("currency", "INR");
options.put("receipt", "txn_"+txnid);
Order order = razorpayClient.Orders.create(options);

JSONObject jsonObject = new JSONObject(String.valueOf(order));
String orderid = jsonObject.getString("id");
return orderid;
	}
	
}
