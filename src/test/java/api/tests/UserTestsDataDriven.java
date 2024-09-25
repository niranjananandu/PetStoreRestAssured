package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.TestUtilities;
import io.restassured.response.Response;

public class UserTestsDataDriven {
	@Test(dataProvider="userData" ,dataProviderClass=TestUtilities.class)
	public void createUserDataDriven(String id,String username, String firstName,String lastName,String email, String password, String phone, String status) {
			User user = new User();
			id = id.replaceAll("\\.0+$", "");
			status = status.replaceAll("\\.0+$", "");
			
			user.setId(Integer.parseInt(id));
			user.setUsername(username);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhone(phone);
			user.setUserStatus(Integer.parseInt(status));
			Object[] userObj = {user};


			Response response = UserEndpoints.createUserWithList(userObj);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.jsonPath().get("code").toString(), "200");
			Assert.assertEquals(response.jsonPath().get("message").toString(), "ok");
			
			//schema validations
//			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createUser.json"));

		
	}

}
