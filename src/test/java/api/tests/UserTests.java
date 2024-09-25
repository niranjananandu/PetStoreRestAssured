package api.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserTests {

	@Test(priority = 1)
	public void createUserTest(ITestContext context) {

		User user = new User();
		user.setId(1);
		user.setUsername("nandu");
		user.setFirstName("Ninja");
		user.setLastName("Pillai");
		user.setEmail("ninjapill@gmai.com");
		user.setPassword("122344");
		user.setPhone("122345545");
		user.setUserStatus(1);

		Response response = UserEndpoints.createUser(user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("code").toString(), "200");
		Assert.assertEquals(response.jsonPath().get("message").toString(), "1");
		context.setAttribute("name", "nandu");
		
		//schema validations
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createUser.json"));

	}
	@Test(priority = 2)
	public void getUserByNameTest(ITestContext context) {

		Response response = UserEndpoints.getUser(context.getAttribute("name").toString());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("id").toString(), "1");
		Assert.assertEquals(response.jsonPath().get("username").toString(), "nandu");
		Assert.assertEquals(response.jsonPath().get("firstName").toString(), "Ninja");
		Assert.assertEquals(response.jsonPath().get("lastName").toString(), "Pillai");
		Assert.assertEquals(response.jsonPath().get("email").toString(), "ninjapill@gmai.com");
		Assert.assertEquals(response.jsonPath().get("password").toString(), "122344");
		Assert.assertEquals(response.jsonPath().get("phone").toString(), "122345545");
		Assert.assertEquals(response.jsonPath().get("userStatus").toString(), "1");
		
		//schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUser.json"));

	}
	@Test(priority = 3)
	public void updateUserTest(ITestContext context) {

		User user = new User();
		user.setId(1);
		user.setUsername("nandu");
		user.setFirstName("Ninja1");
		user.setLastName("Pillai1");
		user.setEmail("ninjapill1@gmai.com");
		user.setPassword("1223441");
		user.setPhone("1223455451");
		user.setUserStatus(11);

		Response response = UserEndpoints.updateUser(user,context.getAttribute("name").toString());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("code").toString(), "200");
		Assert.assertEquals(response.jsonPath().get("message").toString(), "1");
		
		//schema validations
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createUser.json"));

	}
	@Test(priority = 4)
	public void deleteUserByNameTest(ITestContext context) {

		Response response = UserEndpoints.deleteUser(context.getAttribute("name").toString());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("code").toString(), "200");
		Assert.assertEquals(response.jsonPath().get("message").toString(), context.getAttribute("name").toString());
		
		//schema validations
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createUser.json"));

	}
	
	
}
