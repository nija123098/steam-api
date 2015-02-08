package com.github.goive.steamapi;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

/**
 * Created by ive on 25.01.15.
 */
public class ConsoleApplication {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("First argument must be appId.");
            System.exit(0);
        }

        SteamApi steamApi = new SteamApiImpl();

        if (args.length > 1) {
            String countryCode = args[1].toUpperCase();

            for (CountryCode code : CountryCode.values()) {
                if (code.equals(countryCode)) {
                    steamApi.setCountryCode(code);
                    System.out.println("Country code is " + code);
                    break;
                }
            }
        }

        long appId = Long.parseLong(args[0]);
        try{
            SteamApp steamApp = steamApi.retrieveData(appId);
            if(!steamApp.isFreeToPlay()){
                System.out.println(steamApp.getPrice().getFinalPrice());
            } else {
                System.out.println("0");
            }
        } catch (SteamApiException e){
            System.err.println("error");
        }
    }
}
