package com.takeoff.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.takeoff.model.SubscriptionDTO;

@Service
public class RazorpayService {
	
	public Boolean subscribe(SubscriptionDTO subscription) throws RazorpayException, IOException
	{
	RazorpayClient razorpayClient = new RazorpayClient("rzp_live_nWA6UVrzTQFr9W", "IoKCC6msyc9zduVg5ZK6sIa5");
	
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
	Boolean paymentStatus = Utils.verifyPaymentSignature(options, "IoKCC6msyc9zduVg5ZK6sIa5");
	if(amount == 1 && paymentStatus)
	{
		
		statusStr=true;	
	}
	}
	return statusStr;
	}
	public String getOrderId() throws RazorpayException
	{
	
//	RazorpayClient razorpayClient = new RazorpayClient("rzp_test_tqgJ9eimxluVhi", "xaOlf2KK1hTOS7yotlzCn7qs");
		
		RazorpayClient razorpayClient = new RazorpayClient("rzp_live_nWA6UVrzTQFr9W", "IoKCC6msyc9zduVg5ZK6sIa5");

	 
    Timestamp ts = new Timestamp(new java.util.Date().getTime());
    String txnid = ts.toString().replace(":","").replace(" ","").replace("-","").replace(".","");

JSONObject options = new JSONObject();
options.put("amount", 100);
options.put("currency", "INR");
options.put("receipt", "txn_"+txnid);
Order order = razorpayClient.Orders.create(options);

JSONObject jsonObject = new JSONObject(String.valueOf(order));
String orderid = jsonObject.getString("id");
return orderid;
	}
	
}
