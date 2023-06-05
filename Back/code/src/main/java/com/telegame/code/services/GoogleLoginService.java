package com.telegame.code.services;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Service
public class GoogleLoginService {
    @Value("${redirectcallback}")
    String redirectUri;

    @Value("${clientid}")
    String clientId;

    @Value("${clientsecret}")
    String clientSecret;

    public String getRedirect() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder("https://accounts.google.com/o/oauth2/v2/auth");
        uriBuilder.addParameter("scope", "https://www.googleapis.com/auth/userinfo.email");
        uriBuilder.addParameter("access_type", "offline");
        uriBuilder.addParameter("state", "state_parameter_passthrough_value");
        uriBuilder.addParameter("redirect_uri", redirectUri);
        uriBuilder.addParameter("client_id", clientId);
        uriBuilder.addParameter("response_type", "code");
        uriBuilder.addParameter("prompt", "select_account");

        return uriBuilder.build().toURL().toString();
    }

    public String getAccessToken(String code) throws IOException {
        URL url = new URL("https://oauth2.googleapis.com/token");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("code", code);
        parameters.put("grant_type", "authorization_code");
        parameters.put("redirect_uri", redirectUri);
        String json = doPost(url, parameters);
        Map<String, Object> mappedJason = new Gson().fromJson(json, HashMap.class);

        return mappedJason.get("access_token").toString();
    }

    private String doPost(URL url, Map<String, String> parameters) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url.toString());
        List<NameValuePair> nvps = new ArrayList<>();
        for (String s : parameters.keySet()) {
            nvps.add(new BasicNameValuePair(s, parameters.get(s)));
        }
        post.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse resp = httpClient.execute(post);
        if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(resp.getEntity());
        }
        throw new RuntimeException("Error in post");
    }

    public Map<String, String> getUserInfo(String code) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder("https://www.googleapis.com/oauth2/v1/userinfo");
        uriBuilder.addParameter("access_token", getAccessToken(code));
        uriBuilder.addParameter("alt", "json");
        String json = doGet(uriBuilder.build().toURL());

        return new Gson().fromJson(json, HashMap.class);
    }

    private String doGet(URL url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        throw new RuntimeException("Error in get");
    }
}
