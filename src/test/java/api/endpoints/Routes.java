package api.endpoints;

public class Routes {
	
	static String base_url = "https://petstore.swagger.io/v2";
	//Pets urls	
	static String post_pet ="https://petstore.swagger.io/v2/pet";
	static String getPet_ById ="https://petstore.swagger.io/v2/pet/{petId}";
	static String getPet_ByStatus ="https://petstore.swagger.io/v2/pet/findByStatus";
	static String put_pet ="https://petstore.swagger.io/v2/pet";
	static String post_uploadImage = "https://petstore.swagger.io/v2/pet/2/uploadImage";
	static String post_pet_ById = "https://petstore.swagger.io/v2/pet/{petId}";
	static String delete_pet = "https://petstore.swagger.io/v2/pet/{petId}";
}
