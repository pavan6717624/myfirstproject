package com.takeoff.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.takeoff.model.GMLoginStatusDTO;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.model.ImageDTO;
import com.takeoff.model.ProfileDTO;
import com.takeoff.service.HeidigiService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "Heidigi")
public class HeidigiController {

	@Autowired
	HeidigiService service;

	public static Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "hwlyozehf", "api_key",
			"453395666963287", "api_secret", "Q-kgBVQlRlGtdccq-ATYRFSoR8s"));

	@RequestMapping(value = "check")
	public GMLoginStatusDTO check() {
		System.out.println("came00000 ");
		return null;
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
	public List<ImageDTO> getImages() {
		return service.getImages();
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

	@RequestMapping(value = "uploadLogo")

	public String uploadLogo(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadLogo(file);
	}

	@RequestMapping(value = "uploadImage")
	public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadImage(file);
	}
	
	@RequestMapping(value = "uploadPhoto")
	public String uploadPhoto(@RequestParam("file") MultipartFile file) throws Exception {
		return service.uploadPhoto(file);
	}

	@RequestMapping(value = "downloadImage")
	public String downloadImage(@RequestParam("image") String image) throws IOException {

		return service.downloadImage(image);
	}

}
