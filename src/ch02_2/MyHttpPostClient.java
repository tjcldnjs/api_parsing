package ch02_2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpPostClient {

	public static void main(String[] args) {
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/posts");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			System.out.println("response : "  +conn.getResponseCode());
			
			
			
			String inputLine;
			StringBuffer buffer;
			
//			while((inputLine))
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
