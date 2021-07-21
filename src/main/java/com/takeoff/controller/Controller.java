package com.takeoff.controller;



import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
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
	
	@RequestMapping("/checkReferCode")
	public Boolean checkReferCode(@RequestParam("refercode") String refercode)
	{
		return customerService.checkReferCode(refercode);
	}
	
	@RequestMapping(value="/getOrderId")
	public String getOrderId() throws RazorpayException
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

return "{\"id\":\""+orderid+"\"}";



	}

}
