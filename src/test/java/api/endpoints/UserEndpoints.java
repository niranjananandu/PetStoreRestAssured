package api.endpoints;
import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.response.Response;

public class UserEndpoints {
	
	public static Response createUser(User payload) {
		
		Response response  = given()
				.contentType("application/json")
				.accept("application/json")
				.body(payload)
				
				.when()
				.post(Routes.create_single_user);
		
		return response;		
	}
	
	
	public static Response getUser(String name) {
		
		Response response  = given()
				.contentType("application/json")
				.accept("application/json")
				.pathParam("username", name)
				
				.when()
				.get(Routes.get_user_by_name);
		
		return response;		
	}

	public static Response updateUser(User payload,String name) {

		Response response = given().contentType("application/json").accept("application/json").body(payload).pathParam("username", name)

				.when().put(Routes.update_user);

		return response;
	}
	
	public static Response deleteUser(String name) {

		Response response  = given()
				.contentType("application/json")
				.accept("application/json")
				.pathParam("username", name)
				
				.when()
				.delete(Routes.delete_user);
		
		return response;	
	}

	public static Response createUserWithList(Object[] payload) {
		
		Response response  = given()
				.contentType("application/json")
				.accept("application/json")
				.body(payload)
				
				.when()
				.post(Routes.create_user_with_List);
		
		return response;		
	}

}
