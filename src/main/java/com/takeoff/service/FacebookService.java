package com.takeoff.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;
    
    String accessToken;
    
    public String getName() {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "name"};
       
        
        facebook.feedOperations().updateStatus("Hi! Testing...");
        return facebook.fetchObject("me", String.class, fields);
    }
    
    public String createFacebookAuthorizationURL(){
    	System.out.println(facebookAppId+" "+facebookSecret);
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://takeoff-pavan.herokuapp.com/facebook");
        params.setScope("public_profile,email,publish_stream");
        return oauthOperations.buildAuthorizeUrl(params);
    }
    
    public void createFacebookAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "https://takeoff-pavan.herokuapp.com/facebook", null);
        accessToken = accessGrant.getAccessToken();
    }

}
