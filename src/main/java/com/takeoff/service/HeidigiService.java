package com.takeoff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.HeidigiProfile;
import com.takeoff.domain.HeidigiUser;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.model.ProfileDTO;
import com.takeoff.repository.HeidigiProfileRepository;
import com.takeoff.repository.HeidigiRoleRepository;
import com.takeoff.repository.HeidigiUserRepository;

@Service
public class HeidigiService {

	@Autowired
	LogoService logoService;

	@Autowired
	HeidigiUserRepository userRepository;
	@Autowired
	HeidigiRoleRepository roleRepository;
	@Autowired
	HeidigiProfileRepository profileRepository;

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

	public String uploadLogo(MultipartFile file) throws IOException {

		InputStream is = new ByteArrayInputStream(file.getBytes());
		BufferedImage img = ImageIO.read(is);

		String extension = file.getOriginalFilename().split("\\.")[1].toLowerCase();

		System.out.println(extension);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (extension.equals("png"))
			ImageIO.write(img, "PNG", bos);
		else
			ImageIO.write(img, "JPEG", bos);
		img = Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, 300, 300);
		String image = new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
		image = "data:image/" + extension + ";base64," + image;

		System.out.println(image);

		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		HeidigiProfile profile = null;

		if (!profileOpt.isPresent()) {
			profile = new HeidigiProfile();
			profile.setUser(userRepository.findByMobile(9449840144L).get());
		} else
			profile = profileOpt.get();
		System.out.println(image);
		profile.setImage(image);
		profileRepository.save(profile);

		System.out.println(file);

		return "";
	}

	public ProfileDTO editAddress(String address) throws IOException {

		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);
		HeidigiProfile profile = null;

		if (profileOpt.isEmpty()) {
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

	public List<String> getImages() {

		return userRepository.getRandomImages();
	}

	public ProfileDTO getProfile() {

		HeidigiProfile profile = profileRepository.findByMobile(9449840144L).get();

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setAddress(profile.getAddress());
		profileDTO.setImage(profile.getImage());
		profileDTO.setMobile(profile.getUser().getMobile() + "");

		return profileDTO;
	}

	public String downloadImage(String image) throws IOException {
		Optional<HeidigiProfile> profileOpt = profileRepository.findByMobile(9449840144L);

		String logo = "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\\";
		String address = "&nbsp;";
		if (profileOpt.isPresent()) {
			logo = profileOpt.get().getImage();
			address = profileOpt.get().getAddress();
		}

		String htmlData = "<html><body style=\" margin: 0; padding-left:2px\">"
				// +"<link rel=\"stylesheet\"
				// href=\"https://fonts.googleapis.com/css?family=Sofia\"></link>"
				+ "<div style=' padding: 0px; position: relative;width:300px;height:300px;'>"
				+ "<img style='width:300px;height:300px;' src='data:image/jpeg;base64," + image + "'></img>"
				+ " <img src=\"" + logo
				+ "\" style=' position: absolute;    top: 2%;    left: 2%;max-height: 50px;border: 1px solid  #bbb;'></img> </div>"
				+ " <div style='font-family: Times New Roman, Times, serif; font-size:10px;background-color:black;width:300px;color:white;text-align:center'> "
				+ address + "</div></body></html>" + "";
		System.out.println("HtmlData:: " + htmlData);

		return "{\"img\":\"" + "data:image/jpeg;base64," + logoService.createImage(htmlData, true) + "\"}";
	}

}
