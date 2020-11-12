package com.jumbo1907.carrots.modes.writing;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jumbo1907.carrots.modes.WritingMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class ModeBTCPrice extends WritingMode {
    private String fetchResult = "";

    public ModeBTCPrice(){
        super("BTCPrice", 5000l);

        //Run timer:
        new Thread(() -> {
            while (true) {
                fetchResult = new DecimalFormat("0.00000000").format(getPrice());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e){
                    fetchResult = "X";
                }
            }
        }).start();
    }


    @Override
    public String fetch () {
        return fetchResult.isEmpty() ? " " : fetchResult + " BTC";
    }

    public double getPrice(){
        try {
            return getJSON().getAsJsonObject("market_data").getAsJsonObject("current_price").get("btc").getAsDouble();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private JsonObject getJSON() throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://api.coingecko.com/api/v3/coins/vitae?tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        return new JsonParser().parse(result.toString()).getAsJsonObject();
    }
}
