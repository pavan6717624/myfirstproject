package com.takeoff.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.model.ImageDTO;
import com.takeoff.model.ProfileDTO;
import com.takeoff.service.FacebookService;
import com.takeoff.service.HeidigiService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "Heidigi")
public class HeidigiController {

	@Autowired
	HeidigiService service;
	@Autowired
	FacebookService fservice;

	public static Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "hwlyozehf", "api_key",
			"453395666963287", "api_secret", "Q-kgBVQlRlGtdccq-ATYRFSoR8s"));

	@RequestMapping(value = "check")
	public String check() {
		return fservice.createFacebookAuthorizationURL();
		
	}

	@RequestMapping(value = "login")
	public Boolean login(@RequestBody HeidigiLoginDTO login) {
		return service.login(login);

	}

	@RequestMapping(value = "signup")
	public Boolean signup(@RequestBody HeidigiSignupDTO signup) {
		return service.signup(signup);
	}

	@RequestMapping(value = "getImages")
	public List<String> getImages() {
		return service.getImages();
	}
	
	@RequestMapping(value = "getVideos")
	public List<String> getVideos() {
		return service.getVideos();
	}


//	@RequestMapping(value = "uploadLogo1")
//	public String uploadLogo1(@RequestParam("file") MultipartFile file) throws IOException {
//
//		System.out.println("came here");
//		return service.uploadLogo1(file);
//	}

	@RequestMapping(value = "editContent")
	public ProfileDTO editContent(@RequestParam("line1") String line1,@RequestParam("line2") String line2,@RequestParam("line3") String line3,
			@RequestParam("line4") String line4,@RequestParam("email") String email,@RequestParam("website") String website,
			@RequestParam("address") String address) throws Exception {

		
		return service.editContent(line1,line2,line3,line4,email,website,address);
	}

//	@RequestMapping(value = "downloadImage")
//	public String downloadImage(@RequestParam("image") String image) throws IOException {
//
//		return service.downloadImage(image);
//	}

	@RequestMapping(value = "getProfile")
	public ProfileDTO getProfile() throws Exception {

		return service.getProfile();
	}
	
	@RequestMapping(value = "changeTemplate")
	public ProfileDTO changeTemplate(String template) throws Exception {

		return service.changeTemplate(template);
	}
	
	@RequestMapping(value = "getTemplate")
	public String getTemplate(String template) throws Exception {

		return "{\"img\":\""+service.getTemplate(template)+"\"}";
	}
	
	
	@RequestMapping(value = "uploadLogo")

	public String uploadLogo(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadLogo(file);
	}

	@RequestMapping(value = "uploadImage")
	public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadImage(file);
	}
	
	@RequestMapping(value = "uploadVideo")
	public String uploadVideo(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadVideo(file);
	}
	
	@RequestMapping(value = "uploadPhoto")
	public String uploadPhoto(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadPhoto(file);
	}

	@RequestMapping(value = "downloadImage")
	public String downloadImage(@RequestParam("image") String image) throws Exception {

		return service.downloadImage(image);
	}

	
	@RequestMapping(value = "downloadVideo")
	public String downloadVideo() throws Exception {

		return service.downloadVideo();
	}
	
	@RequestMapping(value = "postToFacebookImage")
	public String postToFacebookImage(@RequestParam("image") String image) throws Exception {
		
		
		return service.postToFacebookImage(image);
	}
	
	@RequestMapping(value = "postToFacebookVideo")
	public String postToFacebookVideo(@RequestParam("video") String video) throws Exception {
		
		
		return service.postToFacebookVideo(video);
	}
	
	@RequestMapping(value = "video/{tag}")
	public ResponseEntity<Object> video(@PathVariable String tag) throws Exception {
		
		
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(service.downloadVideo(tag))).build();
    }
	
	
	@RequestMapping(value = "image/{tag}")
	public ResponseEntity<Object> image(@PathVariable String tag) throws Exception {
		
		
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(service.getImage(tag))).build();
    }
	
}
