package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import sk.hotelclientapplication.restclient.dto.ClientCreateDto;
import sk.hotelclientapplication.restclient.dto.TokenRequestDto;
import sk.hotelclientapplication.restclient.dto.TokenResponseDto;

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


    public String registerClient(String email, String firstname, String lastname, String username,
                                 String password, String birthday, String contact, String passportnumber)
            throws IOException {
        ClientCreateDto clientCreateDto = new ClientCreateDto(email,firstname,lastname,username,password,
                birthday,contact,passportnumber);

        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(clientCreateDto));

        Request request = new Request.Builder()
                .url(URL + "/register/client")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 200) {
            //String json = response.body().string();
            //TokenResponseDto dto = objectMapper.readValue(json, TokenResponseDto.class);

           // return dto.getToken();
        } throw new RuntimeException("Registration failed");
    }
}
