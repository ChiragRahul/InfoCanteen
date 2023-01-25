package com.infovision.canteen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infovision.geocodeservice.GeocodeResult;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@CrossOrigin("*")
public class GeocodeController {
   @RequestMapping(value = "/geocode", method = RequestMethod.GET )
   public GeocodeResult getGeocode(@RequestParam String address) throws IOException {
       OkHttpClient client = new OkHttpClient();
       String encodedAddress = URLEncoder.encode(address, "UTF-8");
       Request request = new Request.Builder()
               .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address=" + encodedAddress)
               .get()
               .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
               .addHeader("x-rapidapi-key", "8f16e323femsh8906f68aec65187p11a9a0jsn025ae0f77545"/*  Use your API Key here */)
               .build();
       ResponseBody responseBody = client.newCall(request).execute().body();
       ObjectMapper objectMapper = new ObjectMapper();
       GeocodeResult result = objectMapper.readValue(responseBody.string(), GeocodeResult.class);
       return result;
   }
}