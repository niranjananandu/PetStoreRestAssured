package api.endpoints;

import static io.restassured.RestAssured.given;

import java.io.File;

import api.payload.Pets;
import io.restassured.response.Response;

public class PetsEndpoints {

	public static Response createPet(Pets payload) {

		Response response = given().contentType("application/json").accept("application/json").body(payload)

				.when().post(Routes.post_pet);
		return response;

	}

	public static Response getPetByID(int petID) {

		Response response = given().contentType("application/json").accept("application/json").pathParam("petId", petID)

				.when().get(Routes.getPet_ById);

		return response;

	}

	public static Response getPetByStatus(String status) {

		Response response = given().contentType("application/json").accept("application/json")
				.queryParam("status", status)

				.when().get(Routes.getPet_ByStatus);

		return response;

	}

	public static Response putPet(Pets payload) {

		Response response = given().contentType("application/json").accept("application/json").body(payload)

				.when().put(Routes.put_pet);

		return response;

	}

	public static Response uploadPetImage(String path,int petId) {
		File file = new File(path);
		Response response = given()
				.contentType("multipart/form-data")
				.accept("application/json")
				.multiPart(file)
				.pathParam("petId", petId)

				.when().post(Routes.post_uploadImage);

		return response;

	}
	
	public static Response updatePetById(int petId,String name, String status) {
		
		Response response = given()
				.contentType("application/x-www-form-urlencoded")
				.accept("application/json")
				.pathParam("petId", petId)
				.formParam("name", name)
				.formParam("status", status)

				.when().post(Routes.post_pet_ById);

		return response;

	}
	
	public static Response deletPet(int petId) {
		
		Response response = given()
				.contentType("application/x-www-form-urlencoded")
				.accept("application/json")
				.pathParam("petId", petId)	

				.when().post(Routes.delete_pet);

		return response;

	}

}
