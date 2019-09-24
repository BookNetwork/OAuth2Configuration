package com.sliit.ssd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sliit.ssd.application.FileUploadDTO;
import com.sliit.ssd.domain.OAuthDriverImplementaion;
import com.sliit.ssd.domain.OAuthService;
import com.sliit.ssd.utils.AuthStatus;

/**
 * 
 * @author fazlan.m
 *
 */
@Controller
public class MainRouterController {

	@Autowired
	OAuthService OAuthService;

	@Autowired
	OAuthDriverImplementaion OAuthDriverImplementaion;

	/**
	 * 
	 * index router
	 * 
	 * -> if user already logged in to the application it will redirect the user to
	 * the dashboard
	 * 
	 * -> else it will redirect the user into the index page
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() throws IOException {

		if (OAuthService.userAuthenticationStatus().equals(AuthStatus.STATUS_GREEN)) {
			return "redirect:/dashboard";

		} else {
			return "index.html";
		}

	}

	/**
	 * 
	 * dashboard router
	 * 
	 * -> if user already logged in to the application it will redirect the user to
	 * the dashboard
	 * 
	 * -> else it will redirect the user into the index page
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() throws IOException {

		if (OAuthService.userAuthenticationStatus().equals(AuthStatus.STATUS_GREEN)) {
			return "redirect:/dashboard";

		} else {
			return "index.html";
		}
	}

	/**
	 * 
	 * login router <br>
	 * 
	 * authenticating the user.
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(OAuthService.authenticateUser());
	}

	/**
	 * 
	 * 
	 * logout router
	 * 
	 * discard the user session using this session
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws IOException {
		OAuthService.discardUserSession(request);
		return "redirect:/";
	}

	/**
	 * 
	 * upload router
	 * 
	 * uploading files in GDrive
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, @ModelAttribute FileUploadDTO file) throws Exception {

		MultipartFile multipartFile = file.getMultipartFile();
		OAuthDriverImplementaion.uploadFile(multipartFile);
		return "redirect:/dashboard?status=uploaded";
	}

	/**
	 * 
	 * redirect router
	 * 
	 * -> if user already logged in to the application it will redirect the user
	 * to the dashboard
	 * 
	 * -> else it will redirect the user into the index page
	 *
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String authorizationCode(@RequestParam(name = "code") String code) throws IOException {

		if (code != null) {
			OAuthService.tokenExchange(code);
			return "dashboard.html";
		} else {
			return "index.html";
		}

	}

}
