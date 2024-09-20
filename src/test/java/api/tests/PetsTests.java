package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
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
	public void createPetTest() {
		Pets petsPayload = new Pets();
		Category category = new Category();
		Tag tag = new Tag();

		category.setId(1);
		category.setName("Blacky");

		tag.setId(1);
		tag.setName("Blacky");

		petsPayload.setId(2);
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
		Assert.assertEquals(response.jsonPath().get("id").toString(), "2");

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}

	@Test(priority = 2)
	public void getPetById() {
		log.info("Starting Get Pet by ID test");
		Response response = PetsEndpoints.getPetByID(2);
		response.then().log().all();
		// Response code 200
		response.then().statusCode(200);
		// Validating response body
		Assert.assertEquals(response.jsonPath().get("category.name").toString(), "Blacky");
		Assert.assertEquals(response.jsonPath().get("name").toString(), "doggie");
		Assert.assertEquals(response.jsonPath().get("photoUrls[0]").toString(), "photoUrl.com");
		Assert.assertEquals(response.jsonPath().get("status").toString(), "Available");
		Assert.assertEquals(response.jsonPath().get("id").toString(), "2");

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}

	@Test(priority = 3)
	public void getPetByStatus() {
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
		Assert.assertEquals(response.jsonPath().getString("[0].id"), "2");

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetPetByStatus.json"));

		log.info("Test finished");

	}
	
	@Test(priority = 4)
	public void putPet() {
		Pets petsPayload = new Pets();
		Category category = new Category();
		Tag tag = new Tag();

		category.setId(1);
		category.setName("Ruby");

		tag.setId(1);
		tag.setName("Blacky");

		petsPayload.setId(3);
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
		Assert.assertEquals(response.jsonPath().get("id").toString(), "3");

		// headers
		response.then().header("Content-Type", "application/json");

		// Schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreatePet.json"));

		log.info("Test finished");

	}
}
