package api.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import api.endpoints.StoreEndpoints;
import api.payload.StorePetOrder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class StoreTests {
	
	@Test(priority=1)
	public void getStoreInventoryTest() {
		
		Response response = StoreEndpoints.getStoreInventory();
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		System.out.println(response.jsonPath().get("available").toString());	
		Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
		
	}
	
	@Test(priority=2)
	public void placeOrderForAPetTest(ITestContext context) {
		
		StorePetOrder petOrder = new StorePetOrder();
		petOrder.setId(1);
		petOrder.setPetId(1);
		petOrder.setQuantity(2);
		petOrder.setShipDate("2024-09-25T06:50:19.516Z");
		petOrder.setStatus("placed");
		petOrder.setComplete(true);
		
		Response response = StoreEndpoints.placeOrderForAPet(petOrder);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		Assert.assertEquals(response.jsonPath().get("id").toString(),"1");	
		Assert.assertEquals(response.jsonPath().get("petId").toString(),"1");
		Assert.assertEquals(response.jsonPath().get("quantity").toString(),"2");	
		Assert.assertEquals(response.jsonPath().get("status").toString(),"placed");	
		Assert.assertEquals(response.jsonPath().get("complete").toString(),"true");	
		Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
		
		context.setAttribute("id", response.jsonPath().get("id").toString());
		
		//schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("placeOrderPet.json"));
	}

	@Test(priority=3)
	public void findPurchaseByIdTest(ITestContext context) {
		
		Response response = StoreEndpoints.findPurchaseOrderById(Integer.parseInt(context.getAttribute("id").toString()));
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		Assert.assertEquals(response.jsonPath().get("id").toString(),"1");	
		Assert.assertEquals(response.jsonPath().get("petId").toString(),"1");
		Assert.assertEquals(response.jsonPath().get("quantity").toString(),"2");	
		Assert.assertEquals(response.jsonPath().get("status").toString(),"placed");	
		Assert.assertEquals(response.jsonPath().get("complete").toString(),"true");	
		Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
		
		//schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("placeOrderPet.json"));
		
	}
	
	@Test(priority=4)
	public void deletePurchaseByIdTest(ITestContext context) {
		Response response = StoreEndpoints.deletePurchaseOrderById(Integer.parseInt(context.getAttribute("id").toString()));
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		Assert.assertEquals(response.jsonPath().get("code").toString(),"200");	
		Assert.assertEquals(response.jsonPath().get("message").toString(),context.getAttribute("id").toString());	

		Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
		
		//schema validation
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("deleteOrder.json"));
		
	}
}
