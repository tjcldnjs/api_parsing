package ch01_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TodosHttpServer {

	public static void main(String[] args) throws IOException {

		String urlString = "https://jsonplaceholder.typicode.com/todos";

		try {
			URL url = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//			conn.setRequestMethod("GET");
//			int responseCode = conn.getResponseCode();
//			System.out.println("HTTP CODE : " + responseCode);

			BufferedReader brIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();

			while ((inputLine = brIn.readLine()) != null) {
				responseBuffer.append(inputLine);
			}

			brIn.close();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Todos[] todosObject = gson.fromJson(responseBuffer.toString(), Todos[].class);
//			System.out.println(todosObject[104].getTitle());
//			for (Todos todos : todosObject) {
//				System.out.println(todos.getId()+todos.getTitle());
//			}
			String todosStr = gson.toJson(todosObject[3]);
			System.out.println(todosStr);
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
