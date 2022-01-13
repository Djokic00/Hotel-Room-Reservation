package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.restclient.dto.*;

import java.io.IOException;

public class UserServiceRestClient {

    public static final String URL = "http://localhost:8080/api";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public String login(String username, String password) throws IOException {

        TokenRequestDto tokenRequestDto = new TokenRequestDto(username, password);

        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(tokenRequestDto));

        Request request = new Request.Builder()
                .url(URL + "/user/login")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 200) {
            String json = response.body().string();
            TokenResponseDto dto = objectMapper.readValue(json, TokenResponseDto.class);

            return dto.getToken();
        }

        throw new RuntimeException("Invalid username or password");
    }


    public String registerClient(ClientCreateDto clientCreateDto)
            throws IOException {

        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(clientCreateDto));

        Request request = new Request.Builder()
                .url(URL + "/registration/activemq")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 201) {
            String json = response.body().string();
            UserDto dto = objectMapper.readValue(json, UserDto.class);

            return dto.getEmail();
        } throw new RuntimeException("Registration failed");
    }


    public UserListDto getAllUsers() throws IOException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(URL + "/movie")
                .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                .get()
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 200) {
            String json = response.body().string();

            System.out.println("eeej citam usere a ti ih ne vidis");

            return objectMapper.readValue(json, UserListDto.class);
        }

        throw new RuntimeException("nisam uspeo");
    }
}
