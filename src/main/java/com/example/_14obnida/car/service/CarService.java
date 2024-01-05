package com.example._14obnida.car.service;

import com.example._14obnida.car.dto.CarRequest;
import com.example._14obnida.car.dto.CarResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CarService {
    private String requestUrl = "https://naveropenapi.apigw.ntruss.com";
    private String clientId = "X-NCP-APIGW-API-KEY-ID";
    private String clientSecret = "X-NCP-APIGW-API-KEY";
    //response(reqiest.get)
    public CarResponse getResponse(CarRequest carRequest) {
        double startLongitude = carRequest.getStartLongitude();
        double startLatitude = carRequest.getStartLatitude();
        double goalLongitude = carRequest.getGoalLongitude();
        double goalLatitude = carRequest.getGoalLatitude();
        String queryParam = "start=" + startLongitude + "," + startLatitude + "&" + "goal=" + goalLongitude + "," + goalLatitude;

        WebClient client = WebClient.builder()
                .baseUrl(requestUrl)
                .defaultHeader(clientId, "u8p1b6yi2l")
                .defaultHeader(clientSecret, "NVhSN3C90k0OKfcJXoVVqqbaOpdt1UDOSVGsJi9t")
                .build();
        ResponseEntity<String> result = client
                .get()
                .uri("/map-direction/v1/driving?" + queryParam)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();
        return parseResponse(result.getBody());
    }

    private CarResponse parseResponse(String result) {
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject)parser.parse(result);
        } catch (ParseException e) {

        }
        Number code = (Number)object.get("code");
        if (code.intValue() != 0)
            ; //0이면 길찾기 성공 else 예외처리

        JSONObject route = (JSONObject)object.get("route");
        JSONArray trafastArr = (JSONArray)route.get("traoptimal");
        JSONObject summary = (JSONObject)((JSONObject)trafastArr.get(0)).get("summary");

        int distance = ((Number)summary.get("distance")).intValue();
        int duration = ((Number)summary.get("duration")).intValue();
        int tollFare = ((Number)summary.get("tollFare")).intValue();
        int fuelPrice = ((Number)summary.get("fuelPrice")).intValue();

        distance = distance / 1000; //meter -> kilometer
        duration = duration / 1000 / 60; //millisecond -> second -> minute

        CarResponse carResponse = new CarResponse();
        carResponse.setDistance(distance);
        carResponse.setDuration(duration);
        carResponse.setTollFare(tollFare);
        carResponse.setFuelPrice(fuelPrice);



        /**
         * 표준 출력으로 테스트
         * 서버 띄워서 아래 URL로 접속
         * http://127.0.0.1:8080/api/car?startLongitude=127.1058342&startLatitude=37.3597078&goalLongitude=129.0759853&goalLatitude=35.1794697
         */
        String test = "distance: " + distance + " duration: " + duration + " tollFare: " + tollFare + " fuelPrice: " + fuelPrice;
        System.out.println(test);



        return carResponse;
    }
}
