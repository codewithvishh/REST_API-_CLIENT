package com.codtech;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApiClient {

	private static final String API_KEY = "a0773d22c97689c2d3e17b36a287ca48"; // Replace with this valid key

    private static final String CITY = "London";

    public static void main(String[] args) {
        try {
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                    + CITY + "&appid=" + API_KEY + "&units=metric";

            // Debugging URL
            System.out.println("Request URL: " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode); // Debugging response

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                parseAndDisplay(response.toString());
            } else {
                System.out.println("Error: Unable to fetch weather data.");
                System.out.println("Response Message: " + conn.getResponseMessage()); // Debugging error message
            }

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void parseAndDisplay(String jsonResponse) {
        JSONObject obj = new JSONObject(jsonResponse);

        String cityName = obj.getString("name");
        JSONObject main = obj.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");

        JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");

        System.out.println("=== Weather Info ===");
        System.out.println("City: " + cityName);
        System.out.println("Temperature: " + temperature + " °C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Condition: " + description);
    }
}
