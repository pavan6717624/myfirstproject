package com.takeoff.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.model.GMLoginStatusDTO;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.model.ProfileDTO;
import com.takeoff.service.HeidigiService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "Heidigi")
public class HeidigiController {


	@Autowired
	HeidigiService service;

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
	public List<String> getImages() {
		return service.getImages();
	}
	
	@RequestMapping(value = "uploadLogo")
	public String uploadLogo(@RequestParam("file") MultipartFile file) throws IOException {
		
		
		System.out.println("came here");
		return service.uploadLogo(file);
	}
	@RequestMapping(value = "editAddress")
	public ProfileDTO uploadLogo(@RequestParam("address") String address) throws IOException {
		
		
		System.out.println("came here1");
		return service.editAddress(address);
	}

	@RequestMapping(value = "downloadImage")
	public String downloadImage(@RequestParam("image") String image) throws IOException {
		
		return service.downloadImage(image);
	}
	@RequestMapping(value = "getProfile")
	public ProfileDTO getProfile() throws IOException {
		
		return service.getProfile();
	}

	@RequestMapping(value = "videoConvert")
	public void videoConvert() throws Exception, IOException {
		String inputVideoPath = "input.mp4";
		String outputVideoPath = "outputVideo_"+UUID.randomUUID()+".mp4";
		String text = "Pavankumar123";
		String imageOverlayPath = "logo.png";

		String command = "ffmpeg -i " + inputVideoPath + " -i " + imageOverlayPath
				+ " -filter_complex \"[0:v][1:v]overlay=5:5,drawtext=fontfile=C\\\\:/Windows/fonts/consola.ttf:text='"
				+ text
				+ "':x=(w-text_w)/2:y=(h-text_h-5):fontsize=22:fontcolor=black:box=1:boxcolor=white@0.5: boxborderw=5\" -c:a copy -movflags +faststart "
				+ outputVideoPath;

		try {
			System.out.println("Started.."+new Date());
			ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
			Process process = processBuilder.start();
			process.waitFor();
			System.out.println("Ended.."+new Date());
			// process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
