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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleLoginService {
    @Value("${redirectcallback}")
    String redirectURI;
    @Value("${clientid}")
    String clientid;
    @Value("${clientsecret}")
    String clientsecret;


    public String getRedirect() throws Exception {
        URIBuilder b = new URIBuilder("https://accounts.google.com/o/oauth2/v2/auth");
        b.addParameter("scope", "https://www.googleapis.com/auth/userinfo.email");
        b.addParameter("access_type", "offline");
        b.addParameter("state", "state_parameter_passthrough_value");
        b.addParameter("redirect_uri", redirectURI);
        b.addParameter("client_id", clientid);
        b.addParameter("response_type", "code");
        b.addParameter("prompt", "select_account");
        return b.build().toURL().toString();
    }

    public String getAccessToken(String code) throws Exception {
        URL url = new URL("https://oauth2.googleapis.com/token");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", clientid);
        parameters.put("client_secret", clientsecret);
        parameters.put("code", code);
        parameters.put("grant_type", "authorization_code");
        parameters.put("redirect_uri", redirectURI);

        String result = doPost(url,parameters);
        Map<String, Object> map = new Gson().fromJson(result, HashMap.class);

        return map.get("access_token").toString();

    }

    private String doPost(URL url, Map<String, String> parameters) throws Exception {
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

    public Map<String, String> getUserDetails(String token) throws Exception {
        URIBuilder b = new URIBuilder("https://www.googleapis.com/oauth2/v1/userinfo");
        b.addParameter("access_token",token);
        b.addParameter("alt","json");
        String resp = doGet(b.build().toURL());
        return  new Gson().fromJson(resp, HashMap.class);

    }

    private String doGet(URL URL) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(URL.toString());
        CloseableHttpResponse resp = httpClient.execute(get);

        if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            return EntityUtils.toString(resp.getEntity());
        }
        throw new RuntimeException("Get ERROR");
    }
}
