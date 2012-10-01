package com.app;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class Twitter
{
	public static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1/statuses/update.json";

	public static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json?q=%23foodbangalore";

	public static String getTwitterAuthURL(OAuthService service) {
		Token requestToken = service.getRequestToken();

		return service.getAuthorizationUrl(requestToken);
	}

	public static Token getAccessToken(OAuthService service, HttpServletRequest req) {

		HttpSession session = req.getSession();
		Token accessToken = (Token) session.getAttribute("accessToken");

		if(accessToken == null) {
			Token requestToken = new Token(req.getParameter("oauth_token"), "");
			Verifier verifier = new Verifier(req.getParameter("oauth_verifier"));

			accessToken =   service.getAccessToken(requestToken, verifier);
			session.setAttribute("accessToken", accessToken);
		}

		return accessToken;

	}

	public static ArrayList<String> search(OAuthService service, HttpServletRequest req) throws JSONException {

		ArrayList<String> results = new ArrayList<String>();

		OAuthRequest request = new OAuthRequest(Verb.GET, Twitter.SEARCH_URL);
		service.signRequest(getAccessToken(service, req), request);
		Response response = request.send();

		String respJSON = response.getBody();

		JSONObject jObj = new JSONObject(respJSON);

		JSONArray jarray = jObj.getJSONArray("statuses");
		for(int i=0; i<jarray.length(); i++) {
			JSONObject arrayObj =  (JSONObject) jarray.get(i);
			String text = "" +arrayObj.get("text");

			results.add(text);
		}

		return results;
	}

}