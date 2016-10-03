package de.elderbyte.auctionhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import de.elderbyte.auctionhelper.model.AuctionQuery;
import de.elderbyte.auctionhelper.model.AuctionQueryWrapper;
import de.elderbyte.auctionhelper.model.QueryResult;

public class AuctionQueryHelper {
    private String basicUrl;
    
    AuctionQueryHelper(String basicUrl)
    {
	this.basicUrl = basicUrl;
    }

    private String httpsQuery(String urlString) {
	URL url;
	HttpsURLConnection con;
	StringBuilder result = new StringBuilder();

	try {
	    url = new URL(urlString);
	    con = (HttpsURLConnection) url.openConnection();
	    con.setRequestMethod("GET");

	    BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    String line;

	    while ((line = rd.readLine()) != null) {
		result.append(line);
	    }
	    rd.close();
	    con.disconnect();
	} catch (IOException e) {
	    return null;
	}

	return result.toString();
    }
    
    private String httpQuery(String urlString) {
	URL url;
	HttpURLConnection con;
	StringBuilder result = new StringBuilder();

	try {
	    url = new URL(urlString);
	    con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");

	    BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    String line;

	    while ((line = rd.readLine()) != null) {
		result.append(line);
	    }
	    rd.close();
	    con.disconnect();
	} catch (IOException e) {
	    return null;
	}

	return result.toString();
    }

    public AuctionQueryWrapper getAuctions() {
	Gson gson = new Gson();
	
	String basicQueryResultString = httpsQuery(basicUrl);
	QueryResult basicQuery = gson.fromJson(basicQueryResultString, QueryResult.class);
	
	String auctionQueryResultString = httpQuery(basicQuery.getFiles()[0].getUrl());
	AuctionQuery auctionQuery = gson.fromJson(auctionQueryResultString, AuctionQuery.class);
	
	AuctionQueryWrapper auctionQueryWrapper = new AuctionQueryWrapper();
	auctionQueryWrapper.setAuctionQuery(auctionQuery);
	auctionQueryWrapper.setQueryResult(basicQuery);
	
	return auctionQueryWrapper;
    }
}
