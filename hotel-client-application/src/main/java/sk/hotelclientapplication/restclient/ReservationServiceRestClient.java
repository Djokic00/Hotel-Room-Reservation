package sk.hotelclientapplication.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.restclient.dto.*;

import java.io.IOException;

public class ReservationServiceRestClient{

    public static final String URL = "http://localhost:8085/reservations";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();


    public String saveHotel(HotelCreateDto hotelCreateDto) throws IOException{



        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(hotelCreateDto));

        System.out.println(objectMapper.writeValueAsString(hotelCreateDto));
        System.out.println(body.toString());

        Request request = new Request.Builder()
                .url(URL + "/reservation/hotel")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 201) {
            System.out.println("napravio hotel");
        } else throw new RuntimeException("Hotel not created");
        return "";
    }

    public String saveBooking(BookingCreateDto bookingCreateDto) throws IOException{



        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(bookingCreateDto));

        System.out.println(objectMapper.writeValueAsString(bookingCreateDto));
        System.out.println(body.toString());

        Request request = new Request.Builder()
                .url(URL + "/reservation/booking")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 201) {
            System.out.println("napravio");
        } else throw new RuntimeException("not created");
        return "";
    }


    public RoomsListDto getAllRooms(BookingCreateDto bookingCreateDto) throws IOException {

        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(bookingCreateDto));

       // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(URL + "/reservation/allrooms")
                .post(body)
                .build();


        Call call = client.newCall(request);

        Response response = call.execute();

        if (response.code() == 200) {
            String json = response.body().string();

            System.out.println("eeej citam usere a ti ih ne vidis");

            return objectMapper.readValue(json, RoomsListDto.class);
        }else throw new RuntimeException("nisam uspeo");

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