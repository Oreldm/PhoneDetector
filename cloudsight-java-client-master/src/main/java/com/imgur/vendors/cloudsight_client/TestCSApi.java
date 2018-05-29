package com.imgur.vendors.cloudsight_client;

import java.io.IOException;

import org.json.JSONObject;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class TestCSApi {

	private static final String API_KEY = "hzKWLZy8PVdE7YIcOaJiXA";
	String imageUrl="https://s3-eu-west-1.amazonaws.com/nbaprojectoracle/phoneImage.jpg";
	public String output="";

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	public TestCSApi() {
		CSApi api = new CSApi(HTTP_TRANSPORT, JSON_FACTORY, API_KEY);
		CSPostConfig imageToPost = CSPostConfig.newBuilder().withRemoteImageUrl(imageUrl)
				.build();

		CSPostResult portResult;
		try {
			portResult = api.postImage(imageToPost);
			Thread.sleep(3000);
			System.out.println("Post result: " + portResult);
			JSONObject jsonObj = new JSONObject(portResult.toString());
			output=jsonObj.get("name").toString();
			System.out.println(output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	//	Thread.sleep(30000);

	//	CSGetResult scoredResult = api.getImage(portResult);

	//	System.out.println(scoredResult);
	}
}