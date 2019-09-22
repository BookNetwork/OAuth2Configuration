package com.sliit.ssd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sliit.ssd.domain.OAuthService;
import com.sliit.ssd.utils.AuthStatus;

@Controller
public class MainRouterController {

	@Autowired
	OAuthService OAuthService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() throws IOException {

		if (OAuthService.userAuthenticationStatus().equals(AuthStatus.STATUS_GREEN)) {
			return "redirect:/dashboard";

		} else {
			return "redirect:/index";
		}

	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() throws IOException {

		if (OAuthService.userAuthenticationStatus().equals(AuthStatus.STATUS_GREEN)) {
			return "redirect:/dashboard";

		} else {
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(OAuthService.authenticateUser());
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) throws IOException {
		OAuthService.discardUserSession(request);
		return "redirect:/login";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletResponse response) {

		// TODO validate whether the user is logged in
		return "File Upload";
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.POST)
	public String authorizationCode(@RequestParam(name = "code") String code) throws IOException {

		if (code != null) {
			OAuthService.tokenExchange(code);
			return "dashboard.html";
		} else {
			return "index.html";
		}

	}

}
