package ch01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dog {

	private String name;
	private int age;
	
	
	public static void main(String[] args) {
		Dog dog1= new Dog("멍멍이", 4);
		Dog dog2= new Dog("왈왈이", 2);
		
//		Dog[] dogArr = {dog1, dog2};
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String dog1Str = gson.toJson(dog1);
		System.out.println(dog1Str);
		//--------------
		Dog dogObject = gson.fromJson(dog1Str, Dog.class);
		
		System.out.println(dogObject.getName());
		System.out.println("======================");
		
		
	}
}
