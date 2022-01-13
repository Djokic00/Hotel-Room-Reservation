package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import sk.hotelclientapplication.restclient.dto.HotelCreateDto;
import sk.hotelclientapplication.restclient.dto.TokenRequestDto;
import sk.hotelclientapplication.restclient.dto.TokenResponseDto;

import java.io.IOException;

public class ReservationServiceRestClient{

    public static final String URL = "http://localhost:8082/api";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();


    public String saveHotel(HotelCreateDto hotelCreateDto) throws IOException{



        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(hotelCreateDto));

        Request request = new Request.Builder()
                .url(URL + "/hotel")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 201) {
            System.out.println("napravio hotel");
        }

        throw new RuntimeException("Hotel not created");
    }

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