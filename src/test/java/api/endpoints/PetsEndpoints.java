package api.endpoints;
import static io.restassured.RestAssured.given;

import api.payload.Pets;
import io.restassured.response.Response;


public class PetsEndpoints {
	Pets pet;
	
	public static Response createPet(Pets payload) {
		
		Response response = given()
				.contentType("application/json")
				.accept("application/json")
				.body(payload)
				
				.when().post(Routes.post_pet);
		return response;
		
	}
	
	public static Response getPetByID(int petID) {
		
		Response response = given()
		.contentType("application/json")
		.accept("application/json")
		.pathParam("petId", petID)
		
		.when().get(Routes.getPet_ById);
		
		return response;
		
	}
	
public static Response getPetByStatus(String status) {
		
		Response response = given()
		.contentType("application/json")
		.accept("application/json")
		.queryParam("status", status)
		
		.when().get(Routes.getPet_ByStatus);
		
		return response;
		
	}

public static Response putPet(Pets payload) {
	
	Response response = given()
	.contentType("application/json")
	.accept("application/json")
	.body(payload)
	
	.when().put(Routes.put_pet);
	
	return response;
	
}
		
		
}
