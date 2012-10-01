package com.app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class PostServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		OAuthService service = new ServiceBuilder()
		.provider(TwitterApi.class)
		.apiKey("WJm0bPyQ356kNCn3HCEWQ")
		.apiSecret("9ZtpNDauYf58g28tJbB7VSTDrNYyaYBlTh6lLLCk")
		.callback("http://localhost:8080/Twitter/post")
		.build();

		HttpSession session = req.getSession();
		Token accessToken = Twitter.getAccessToken(service, req);

		OAuthRequest request = new OAuthRequest(Verb.POST, Twitter.PROTECTED_RESOURCE_URL);
		String status = session.getAttribute("comments")+" #foodbangalore";
		request.addBodyParameter("status", status);
		service.signRequest(accessToken, request);
		Response response = request.send();

		try {
			session.setAttribute("response", response);
			session.setAttribute("status", status);

			ArrayList<String> sResults = Twitter.search(service, req);
			session.setAttribute("search", sResults);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = req.getRequestDispatcher("result.jsp");
		try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
