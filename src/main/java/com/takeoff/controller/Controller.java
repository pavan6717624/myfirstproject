package com.takeoff.controller;



import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.takeoff.model.OrderDTO;
import com.takeoff.model.RefererCodeDTO;
import com.takeoff.service.CustomerService;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/user")
	public String checkProject()
	{
		return "Checked and Worked";
	}
	
	@RequestMapping("/checkRefererId")
	public RefererCodeDTO checkRefererId(@RequestParam("refererid") String refererid)
	{
		
		Boolean status = customerService.checkRefererId(refererid);
		RefererCodeDTO sendStatus = new RefererCodeDTO(status);
		return sendStatus;
	}
	
	
	@RequestMapping("/callBackUrl")
	public void callBackUrl(@RequestParam("razorpay_payment_id") String razorpay_payment_id, @RequestParam("razorpay_order_id") String razorpay_order_id, @RequestParam("razorpay_signature") String razorpay_signature,HttpServletResponse response ) throws RazorpayException, IOException
	{
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_tqgJ9eimxluVhi", "xaOlf2KK1hTOS7yotlzCn7qs");

		List<Payment> payments = razorpayClient.Orders.fetchPayments(razorpay_order_id);
		
		
		String statusStr="Failed";
		
		
		List<Payment> pay = payments.stream().filter(o-> {JSONObject jsonObject = new JSONObject(String.valueOf(o));
		Boolean captured = jsonObject.getBoolean("captured"); return captured; }).collect(Collectors.toList());

		
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
		Boolean paymentStatus = Utils.verifyPaymentSignature(options, "xaOlf2KK1hTOS7yotlzCn7qs");
		if(amount == 1199 && paymentStatus)
		{
			statusStr="Success";	
		}
		}
		
		response.sendRedirect("https://takeoff-angular.herokuapp.com/paymentStatus");
		
		//response.sendRedirect("http://localhost:4200/paymentStatus");
	}
	
	@RequestMapping(value="/getOrderId")
	public OrderDTO getOrderId() throws RazorpayException
	{
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_tqgJ9eimxluVhi", "xaOlf2KK1hTOS7yotlzCn7qs");

		 
        Timestamp ts = new Timestamp(new java.util.Date().getTime());
        String txnid = ts.toString().replace(":","").replace(" ","").replace("-","").replace(".","");

	JSONObject options = new JSONObject();
options.put("amount", 119900);
options.put("currency", "INR");
options.put("receipt", "txn_"+txnid);
Order order = razorpayClient.Orders.create(options);

JSONObject jsonObject = new JSONObject(String.valueOf(order));
String orderid = jsonObject.getString("id");

OrderDTO sendOrder = new OrderDTO(orderid);

return sendOrder;



	}

}
