package com.takeoff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.transformation.Layer;
import com.cloudinary.transformation.TextLayer;
import com.cloudinary.utils.ObjectUtils;
import com.takeoff.domain.HeidigiImage;
import com.takeoff.domain.HeidigiProfile;
import com.takeoff.domain.HeidigiUser;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.model.ImageDTO;
import com.takeoff.model.ProfileDTO;
import com.takeoff.repository.HeidigiImageRepository;
import com.takeoff.repository.HeidigiProfileRepository;
import com.takeoff.repository.HeidigiRoleRepository;
import com.takeoff.repository.HeidigiUserRepository;

@Service
public class HeidigiService {
	
	 @Lazy
	    @Autowired
	    private HeidigiService hService;

	@Autowired
	LogoService logoService;
	@Autowired
	HeidigiImageRepository heidigiImageRepository;

	static RestTemplate restTemplate = new RestTemplate();

	@Autowired
	HeidigiUserRepository userRepository;
	@Autowired
	HeidigiRoleRepository roleRepository;
	@Autowired
	HeidigiProfileRepository profileRepository;

	public static Cloudinary cloudinary1 = new Cloudinary(ObjectUtils.asMap("cloud_name", "hwlyozehf", "api_key",
			"453395666963287", "api_secret", "Q-kgBVQlRlGtdccq-ATYRFSoR8s"));

	public static Cloudinary cloudinary2 = new Cloudinary(ObjectUtils.asMap("cloud_name", "hu4jsyyt8", "api_key",
			"491845868955893", "api_secret", "oYgotm7eQgCcLzffOoo7oHPJ874"));

	public static Cloudinary cloudinary[] = { cloudinary1, cloudinary2 };

	public Boolean signup(HeidigiSignupDTO signup) {

		HeidigiUser user = new HeidigiUser();
		user.setEmail(signup.getEmail());
		user.setMobile(Long.valueOf(signup.getMobile()));
		user.setName(signup.getName());
		user.setPassword(signup.getPassword());
		user.setMessage("Customer Signup");
		user.setRole(roleRepository.findByRoleName("Customer").get());

		userRepository.save(user);

		return userRepository.findByMobileAndPassword(user.getMobile(), user.getPassword()).isPresent();
	}

	public String uploadImage(MultipartFile file) throws Exception {
		HeidigiImage image = uploadImage(file, "", "", "Image");
		return "";
	}

	public HeidigiImage uploadImage(MultipartFile file, String category, String subCategory, String type)
			throws Exception {

		File convFile = new File(UUID.randomUUID() + "" + file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();

		InputStream is = new ByteArrayInputStream(file.getBytes());
		BufferedImage img = ImageIO.read(is);

		img = Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300, 300);

		String extension = file.getOriginalFilename().split("\\.")[1].toLowerCase();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (extension.toLowerCase().equals("jpg"))
			ImageIO.write(img, "JPEG", bos);
		else if (extension.toLowerCase().equals("png"))
			ImageIO.write(img, "PNG", bos);

		String imageText = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");

		System.out.println(imageText);

		Map uploadResult1 = cloudinary1.uploader().upload(convFile, ObjectUtils.emptyMap());

		HeidigiImage image = new HeidigiImage();
		image.setCategory(category);
		image.setSubcategory(subCategory);
		image.setType(type);
		image.setPublicId(uploadResult1.get("public_id") + "");

		image.setResponse(uploadResult1.toString());

		image.setExtension(extension);
		image.setUser(userRepository.findByMobile(9449840144L).get());
		image.setImageText(imageText);

		heidigiImageRepository.save(image);
		
		System.out.println("Backup started");
		
		hService.backupUploadedImage(convFile,image);
		
		System.out.println("Backup Ended");

		return image;
	}

	@Async
	public void backupUploadedImage(File convFile, HeidigiImage image) throws Exception {
		
		System.out.println("In Backup started");
		Map uploadResult2 = cloudinary2.uploader().upload(convFile, ObjectUtils.emptyMap());

		image.setBackupPublicId(uploadResult2.get("public_id") + "");

		image.setBackupResponse(uploadResult2.toString());

		heidigiImageRepository.save(image);
		System.out.println("In Backup ended");
	}

	public String uploadLogo(MultipartFile file) throws Exception {

		HeidigiProfile profile = null;
		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		if (!profileOpt.isPresent()) {
			profile = new HeidigiProfile();

			profile.setUser(userRepository.findByMobile(9449840144L).get());
		} else
			profile = profileOpt.get();

		profile.setLogo(uploadImage(file, "Logo", "Logo", "Logo"));
		profileRepository.save(profile);

		return "";
	}

	public String uploadPhoto(MultipartFile file) throws Exception {
		
		HeidigiProfile profile = null;
		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		if (!profileOpt.isPresent()) {
			profile = new HeidigiProfile();

			profile.setUser(userRepository.findByMobile(9449840144L).get());
		} else
			profile = profileOpt.get();

		profile.setPhoto(uploadImage(file, "Photo", "Photo", "Photo"));
		profileRepository.save(profile);

		return "";
		
	}
	
	public ProfileDTO editAddress(String address) throws Exception {

		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		HeidigiProfile profile = null;

		if (!profileOpt.isPresent()) {
			profile = new HeidigiProfile();
			profile.setUser(userRepository.findByMobile(9449840144L).get());
		} else
			profile = profileOpt.get();
		System.out.println(address);
		profile.setAddress(address);
		profileRepository.save(profile);

		return getProfile();
	}

	public Boolean login(HeidigiLoginDTO login) {

		return userRepository.findByMobileAndPassword(Long.valueOf(login.getMobile()), login.getPassword()).isPresent();

	}

	public List<ImageDTO> getImages() {

		List<HeidigiImage> images = heidigiImageRepository.getImageIds();
		return images.stream().map(
				o -> new ImageDTO("data:image/" + o.getExtension() + ";base64," + o.getImageText(), o.getPublicId()))
				.collect(Collectors.toList());
	}

	public ProfileDTO getProfile() throws Exception {

		HeidigiProfile profile = profileRepository.findByMobile(9449840144L).get();

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setAddress(profile.getAddress());
//		profileDTO.setImage(getImage(profile.getLogo().get, profile.getLogo().getExtension()));
		profileDTO.setImage(
				"data:image/" + profile.getLogo().getExtension() + ";base64," + profile.getLogo().getImageText());
		profileDTO.setMobile(profile.getUser().getMobile() + "");
		profileDTO.setEmail(profile.getEmail());
		profileDTO.setWebsite(profile.getWebsite());
		profileDTO.setLine1(profile.getLine1());
		profileDTO.setLine2(profile.getLine2());
		profileDTO.setLine3(profile.getLine3());
		profileDTO.setLine4(profile.getLine4());
		
		

		return profileDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadImage(String image) throws IOException {

		String logoId = profileRepository.findByMobile(9449840144L).get().getLogo().getPublicId();
		
		String photoId = profileRepository.findByMobile(9449840144L).get().getPhoto().getPublicId();
		
		String line1=profileRepository.findByMobile(9449840144L).get().getLine1();
		String line2=profileRepository.findByMobile(9449840144L).get().getLine2();
		String line3=profileRepository.findByMobile(9449840144L).get().getLine3();
		String line4=profileRepository.findByMobile(9449840144L).get().getLine4();
		
		String email=profileRepository.findByMobile(9449840144L).get().getEmail();
		String address=profileRepository.findByMobile(9449840144L).get().getAddress();
		String website=profileRepository.findByMobile(9449840144L).get().getWebsite();
		
		System.out.println(logoId);

		String imageUrl = cloudinary1.url().transformation(new Transformation().height(1080).width(1080).crop("scale")
				.chain()

				// logo
				.overlay(new Layer().publicId(logoId)).chain().flags("layer_apply", "relative").gravity("north_west")
				.opacity(100).radius(30).width(0.15).x(10).y(10).crop("scale").chain()

				// 65% bottom background
				.overlay(new Layer().publicId("akdvbdniqfbncjrapghb")).chain().flags("layer_apply", "relative")
				.gravity("south_west").width(0.65).height(0.18).opacity(100).chain()

				// 35% bottom background
				.overlay(new Layer().publicId("tff8vf9ciycuste9iupb")).chain().flags("layer_apply", "relative")
				.gravity("south_east").width(0.35).height(0.18).opacity(100).chain()

				// icon1: Envelope
				.overlay(new Layer().publicId("dt7fah8qrkeleor3gpq3")).width(20).height(20).chain()
				.flags("layer_apply", "relative").gravity("south_east").x(340).y(110).chain()

				// icon2: Internet Globe
				.overlay(new Layer().publicId("rnxve3ik0plwyrvh3whh")).width(20).height(20).chain()
				.flags("layer_apply", "relative").gravity("south_east").x(340).y(80).chain()

				// icon3: Red Map Marker
				.overlay(new Layer().publicId("b5dnqxn9rd21wpekin6w")).width(20).height(20).chain()
				.flags("layer_apply", "relative").gravity("south_east").x(340).y(50).chain()

				// Person Photo
				.overlay(new Layer().publicId(photoId)).aspectRatio("1.0").gravity("faces").width(0.5)
				.zoom(0.7).crop("thumb").chain().flags("layer_apply", "relative").gravity("south_west").opacity(100)
				.radius("max").width(0.15).x(10).y(15).crop("scale").chain()

				// Text: Line 1
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(30).fontWeight("bold").textAlign("center")
						.text(line1))
				.flags("layer_apply", "relative").gravity("south_west").x(200).y(120).color("white").chain()

				// Text: Line 2
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(15).textAlign("center")
						.text(line2))
				.gravity("south_west").x(200).y(90).color("white").chain()

				// Text: Line 3
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(15).textAlign("center")
						.text(line3))
				.gravity("south_west").x(200).y(65).color("white").chain()

				// Text: Line 4
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(18).fontWeight("bold").textAlign("center")
						.text(line4))
				.gravity("south_west").x(200).y(37).color("white").chain()

				// Text: Mail
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(16).textAlign("center")
						.text(email))
				.gravity("south_west").x(750).y(110).color("black").chain()

				// Text: Website
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(16).textAlign("center")
						.text(website))
				.gravity("south_west").x(750).y(80).color("black").chain()

				// Text: Address
				.overlay(new TextLayer().fontFamily("montserrat").fontSize(16).textAlign("center")
						.text(address))
				.gravity("south_west").x(750).y(50).color("black").chain()

		).imageTag(image + ".jpg");

		imageUrl = URLDecoder.decode(imageUrl.substring(10, imageUrl.length() - 3), "UTF-8");
		String imageStr = getImage(imageUrl);
		return imageStr;

	}

	public String getImage(String id, String ext) {

		try {

			String url = "https://res.cloudinary.com/hwlyozehf/image/upload/" + id + ".jpg";
			byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

			String image = new String(Base64.encodeBase64(imageBytes), "UTF-8");

			return "data:image/" + ext + ";base64," + image;
		} catch (Exception ex) {
			return "";
		}

	}

	public String getImage(String url) {

		try {

			byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

			String image = new String(Base64.encodeBase64(imageBytes), "UTF-8");

			return "{\"img\":\"" + "data:image/jpeg;base64," + image + "\"}";

		} catch (Exception ex) {
			return "";
		}

	}

	public ProfileDTO editContent(String line1, String line2, String line3, String line4, String email, String website,
			String address) throws Exception {
		
		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		HeidigiProfile profile = null;

		if (!profileOpt.isPresent()) {
			profile = new HeidigiProfile();
			profile.setUser(userRepository.findByMobile(9449840144L).get());
		} else
			profile = profileOpt.get();
		
		profile.setAddress(address);
		profile.setLine1(line1);
		profile.setLine2(line2);
		profile.setLine3(line3);
		profile.setLine4(line4);
		profile.setEmail(email);
		profile.setWebsite(website);
		
		profileRepository.save(profile);
		
		return getProfile();
	}

	

}
