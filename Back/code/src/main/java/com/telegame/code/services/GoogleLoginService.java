package com.telegame.code.services;

import com.google.gson.Gson;
import com.telegame.code.Utils.HashUtils;
import com.telegame.code.exceptions.player.LoginException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.repos.PlayerRepo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class GoogleLoginService {
    @Value("${redirectcallback}")
    String redirectUri;

    @Value("${clientid}")
    String clientId;

    @Value("${clientsecret}")
    String clientSecret;

    PlayerRepo playerRepo;

    PlayerService playerService;

    TokenService tokenService;

    public GoogleLoginService(PlayerRepo playerRepo, PlayerService playerService, TokenService tokenService) {
        this.playerRepo = playerRepo;
        this.playerService = playerService;
        this.tokenService = tokenService;
    }

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

        return new Gson().fromJson(json, HashMap.class).get("access_token").toString();
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
        throw new LoginException();
    }

    public String getUserInfo(String code) throws URISyntaxException, IOException, NoSuchAlgorithmException {
        URIBuilder uriBuilder = new URIBuilder("https://www.googleapis.com/oauth2/v1/userinfo");
        uriBuilder.addParameter("access_token", getAccessToken(code));
        uriBuilder.addParameter("alt", "json");
        String json = doGet(uriBuilder.build().toURL());
        String email = new Gson().fromJson(json, HashMap.class).get("email").toString();
        Player player = buildPlayerByEmail(email);
        return tokenService.createUserToken(player);
    }

    private Player buildPlayerByEmail(String email) throws NoSuchAlgorithmException {
        Optional<Player> player = playerRepo.findByEmailEquals(email);
        if (player.isEmpty()) playerService.signUp(buildPlayerForm(email));
        if (player.isPresent()) return player.get();
        throw new PlayerNameException();
    }

    private PlayerForm buildPlayerForm(String email) throws NoSuchAlgorithmException {
        String randomValue = HashUtils.getHashSHA256(email).substring(0, 6);
        return PlayerForm.builder()
                .playerName(randomValue)
                .firstName(randomValue)
                .lastName(randomValue)
                .email(email)
                .build();
    }

    private String doGet(URL url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        throw new LoginException();
    }
}
