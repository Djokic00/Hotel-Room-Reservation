package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.restclient.dto.*;

import java.io.IOException;

public class UserServiceRestClient {

    public static final String URL = "http://localhost:8085/users";

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
        System.out.println(objectMapper.writeValueAsString(clientCreateDto));
        System.out.println(body.toString());

        Request request = new Request.Builder()
                .url(URL + "/user/registration/activemq")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 201) {
          //  String json = response.body().string();
            //UserDto dto = objectMapper.readValue(json, UserDto.class);
            System.out.println("radi");
            //return dto.getEmail();
        } else new RuntimeException("Registration failed");
        return "";
    }


    public UserListDto getAllUsers() throws IOException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(URL + "/user/all")
                .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                .get()
                .build();

        System.out.println(request.toString()+URL + "/user/all");

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 200) {
           String json = response.body().string();

            System.out.println("eeej citam usere a ti ih ne vidis");

            return objectMapper.readValue(json, UserListDto.class);
        }else throw new RuntimeException("nisam uspeo");

    }

//    public String banUser(Long id, BanUserDto banUserDto) throws IOException {
//
//
//        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString());
//
//        Request request = new Request.Builder()
//                .url(URL + "/user/login")
//                .post(body)
//                .build();
//
//        Call call = client.newCall(request);
//
//        Response response = call.execute();
//
//        if (response.code() == 200) {
//            String json = response.body().string();
//            TokenResponseDto dto = objectMapper.readValue(json, TokenResponseDto.class);
//
//            return dto.getToken();
//        }
//
//        throw new RuntimeException("Invalid username or password");
//    }



}
