package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.PetsEndpoints;
import api.payload.Category;
import api.payload.Pets;
import api.payload.Tag;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class PetsTests {
	Logger log;

	@BeforeClass
	public void setUp() {
		log = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void createPetTest(ITestContext context) {
		Pets petsPayload = new Pets();
		Category category = new Category();
		Tag tag = new Tag();

		category.setId(1);
		category.setName("Blacky");

		tag.setId(1);
		tag.setName("Blacky");

		petsPayload.setId(3);
		petsPayload.setCategory(category);
		petsPayload.setName("doggie");
		String[] photos = { "photoUrl.com" };
		petsPayload.setPhotoUrls(photos);
		petsPayload.setStatus("Available");
		Tag[] tags = { tag };
		petsPayload.setTags(tags);

		log.info("Starting Create Pet test");
		Response response = PetsEndpoints.createPet(petsPayload);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("category.name").toString(), "Blacky");
		Assert.assertEquals(response.jsonPath().get("name").toString(), "doggie");
		Assert.assertEquals(response.jsonPath().get("photoUrls[0]").toString(), "photoUrl.com");
		Assert.assertEquals(response.jsonPath().get("status").toString(), "Available");
		Assert.assertEquals(response.jsonPath().get("id").toString(), "3");
		context.setAttribute("id", response.jsonPath().get("id"));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}

	@Test(priority = 2)
	public void getPetById(ITestContext context) {
		int id = (int) context.getAttribute("id");
		log.info("Starting Get Pet by ID test");
		Response response = PetsEndpoints.getPetByID(id);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("category.name").toString(), "Blacky");
		Assert.assertEquals(response.jsonPath().get("name").toString(), "doggie");
		Assert.assertEquals(response.jsonPath().get("photoUrls[0]").toString(), "photoUrl.com");
		Assert.assertEquals(response.jsonPath().get("status").toString(), "Available");
		Assert.assertEquals(response.jsonPath().get("id").toString(), String.valueOf(id));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}

	@Test(priority = 3)
	public void getPetByStatus(ITestContext context) {
		int id = (int) context.getAttribute("id");
		log.info("Starting Get Pet by Status test");
		Response response = PetsEndpoints.getPetByStatus("Available");
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().getString("[0].category.name"), "Blacky");
		Assert.assertEquals(response.jsonPath().getString("[0].name"), "doggie");
		Assert.assertEquals(response.jsonPath().getString("[0].photoUrls[0]"), "photoUrl.com");
		Assert.assertEquals(response.jsonPath().getString("[0].status"), "Available");
		Assert.assertEquals(response.jsonPath().getString("[0].id"), String.valueOf(id));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetPetByStatus.json"));

		log.info("Test finished");

	}
	
	@Test(priority = 4)
	public void putPet(ITestContext context) {
		int id = (int) context.getAttribute("id");
		Pets petsPayload = new Pets();
		Category category = new Category();
		Tag tag = new Tag();

		category.setId(1);
		category.setName("Ruby");

		tag.setId(1);
		tag.setName("Blacky");

		petsPayload.setId(id);
		petsPayload.setCategory(category);
		petsPayload.setName("Dog");
		String[] photos = { "test.com" };
		petsPayload.setPhotoUrls(photos);
		petsPayload.setStatus("Unavailable");
		Tag[] tags = { tag };
		petsPayload.setTags(tags);
		
		log.info("Starting Put Pet test");
		Response response = PetsEndpoints.putPet(petsPayload);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("category.name").toString(), "Ruby");
		Assert.assertEquals(response.jsonPath().get("name").toString(), "Dog");
		Assert.assertEquals(response.jsonPath().get("photoUrls[0]").toString(), "test.com");
		Assert.assertEquals(response.jsonPath().get("status").toString(), "Unavailable");
		Assert.assertEquals(response.jsonPath().get("id").toString(), String.valueOf(id));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}
	
	@Test(priority = 5)
	public void uploadImagePet(ITestContext context) {
		int id = (int) context.getAttribute("id");		
		
		log.info("Starting Upload Pet Image test");
		Response response = PetsEndpoints.uploadPetImage(System.getProperty("user.dir")+"\\src\\test\\resources\\DataFiles\\Test1.txt",id);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertTrue(response.jsonPath().get("message").toString().contains("File uploaded"));
		Assert.assertEquals(response.jsonPath().get("code").toString(), "200");

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("uploadPet.json"));

		log.info("Test finished");

	}
	
	@Test(priority = 6)
	public void updatePetById(ITestContext context) {
		
		int id = (int) context.getAttribute("id");
		log.info("Starting Update Pet By ID test");
		Response response = PetsEndpoints.updatePetById(id,"Cookie","Available");
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("code").toString(),"200");
		Assert.assertEquals(response.jsonPath().get("message").toString(), String.valueOf(id));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("uploadPet.json"));

		log.info("Test finished");

	}
	
	@Test(priority = 7)
	public void deletePet(ITestContext context) {
		
		int id = (int) context.getAttribute("id");
		log.info("Starting Update Pet By ID test");
		Response response = PetsEndpoints.deletPet(id);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("code").toString(),"200");
		Assert.assertEquals(response.jsonPath().get("message").toString(), String.valueOf(id));

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("uploadPet.json"));

		log.info("Test finished");

	}
	
}
