package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class ReservationServiceRestClient{

    public static final String URL = "http://localhost:8082/api";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

//    public MovieListDto getMovies() throws IOException {
//
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Request request = new Request.Builder()
//                .url(URL + "/movie")
//                .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
//                .get()
//                .build();
//
//        Call call = client.newCall(request);
//
//        Response response = call.execute();
//
//        if (response.code() == 200) {
//            String json = response.body().string();
//
//            return objectMapper.readValue(json, MovieListDto.class);
//        }
//
//        throw new RuntimeException();
//    }
}