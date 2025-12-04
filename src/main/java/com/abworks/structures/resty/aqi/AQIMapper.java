package com.abworks.structures.resty.aqi;


import com.google.gson.Gson;
import lombok.Getter;
import lombok.ToString;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record AQIResponse (String stationName, float aqi){
}

@Getter
@ToString
class PartData{
    private String city;
    private int aqi;
    private float pm25;
}

@ToString
@Getter
class CodeAndMsg {
    private String code;
    private String msg;
    private PartData data;
}
public class AQIMapper {

    private static final String apikey = "3aebe184d7696a24edc57940fdd0f71d";
    private static final String city = "mumbai";
    private static final String baseURL = "https://hub.juheapi.com/aqi/v1/";


    public List<AQIResponse> getAQIForNearbyCities(String city) throws IOException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q",city);
        queryParams.put("apikey", apikey);
        String getByCity = baseURL+"city";

        var client = createHTTPClient();
        var urlBuilder = HttpUrl.parse(getByCity).newBuilder();
        queryParams.forEach(urlBuilder::addQueryParameter);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()){
                String respAsString = response.body().string();
                Gson gson = new Gson();
                CodeAndMsg codeAndMsg = gson.fromJson(respAsString, CodeAndMsg.class);
                System.out.println(codeAndMsg);
            }
        }

        return null;
    }

    private OkHttpClient createHTTPClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return httpClient;
    }

    public static void main(String[] args) throws IOException {
        AQIMapper aqiMapper = new AQIMapper();
        aqiMapper.getAQIForNearbyCities("mumbai");
    }


}
