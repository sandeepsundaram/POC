package com.app;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class AuthServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();
		session.setAttribute("comments", req.getParameter("comments"));
		
		Token accessToken = (Token) session.getAttribute("accessToken");

		if(accessToken == null) {

			OAuthService service = new ServiceBuilder()
			.provider(TwitterApi.class)
			.apiKey("WJm0bPyQ356kNCn3HCEWQ")
			.apiSecret("9ZtpNDauYf58g28tJbB7VSTDrNYyaYBlTh6lLLCk")
			.callback("http://localhost:8080/Twitter/post")
			.build();

			Twitter tweet = new Twitter();
			String authURL = tweet.getTwitterAuthURL(service);
			
			try {
				resp.sendRedirect(authURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("/post");
			try {
				rd.forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
