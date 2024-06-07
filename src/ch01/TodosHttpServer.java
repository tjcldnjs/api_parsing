package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class TodosHttpServer {
	public static Todos[] realTodos;

	public static void main(String[] args) throws IOException {

	HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080),0);
		String urlString = "https://jsonplaceholder.typicode.com/todos";

		try {
			httpServer.createContext("/test", new MyTestHandler());
			httpServer.start();
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

//			String[] strHtmls = responseBuffer.toString().split(",");
//			System.out.println("index count : " + strHtmls.length);
//			for (String word : strHtmls) {
//				System.out.println(word);
//			}
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Todos[] todosObject = gson.fromJson(responseBuffer.toString(), Todos[].class);
//			System.out.println(todosObject[104].getTitle());
			
			String todosStr = gson.toJson(todosObject[5]);
			realTodos = todosObject;
			System.out.println(todosStr);
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	static class MyTestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			// 사용자의 요청 방식(METHOD) , GET ,POST 알아야 우리가 동작 시킬 수 있다.
			String method = exchange.getRequestMethod();
			System.out.println("method : " + method);

			if ("GET".equalsIgnoreCase(method)) {
				// Get 이라면 여기서 동작
				// System.out.println("여기는 Get 방식으로 호출 됨");

				// Get -> path: /test 라고 들어오면 어떤 응답 처리를 내려 주면 된다.
				handleGetRequest(exchange);

			} else if ("POST".equalsIgnoreCase(method)) {
				// Post 요청시 여기 동작
				// System.out.println("여기는 Post 방식으로 호출 됨");
				handlePostRequest(exchange);
			} else {
				// 지원하지 않는 메서드에 대한 응답
				String response = "Unsupported Method :" + method;
				exchange.sendResponseHeaders(405, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.flush();
				os.close();
			}
		}
		
		private void handleGetRequest(HttpExchange exchange) throws IOException {
			String response = """
					
					""";
			// String response = "Hello Get Request"; // 응답 메세지

			exchange.sendResponseHeaders(200, realTodos.length);
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes()); // 응답 본문 전송
			os.close();

		}
		private void handlePostRequest(HttpExchange exchange) throws IOException{
			String response = """ 
					<!DOCTYPE html>
					<html lang=ko>
						<head></head>
						<body></body>
							<h1 style ="background-color:red"> Hello path by /test </h1>
					</html>
				""";
		// HTTP 응답 헤더 설정
		exchange.setAttribute("Content-Type", "text/html; charser=UTF-8");
		exchange.sendResponseHeaders(200, response.length());
		
		//getResponseBody
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.flush();
		os.close();
	}
		
}
}
