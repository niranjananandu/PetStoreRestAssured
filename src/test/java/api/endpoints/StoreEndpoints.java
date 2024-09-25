package api.endpoints;
import static io.restassured.RestAssured.given;

import api.payload.StorePetOrder;
import io.restassured.response.Response;

public class StoreEndpoints {

	public static Response getStoreInventory() {
		Response response = given()
				.contentType("application/json")
				.accept("application/json")
				
				.when()
				.get(Routes.get_store_inventory);
		
		return response;
	}

	public static Response placeOrderForAPet(StorePetOrder payload) {
		Response response = given()
				.contentType("application/json")
				.accept("application/json")
				.body(payload)
				
				.when()
				.post(Routes.place_order_pet);
		
		return response;
	}
	
	public static Response findPurchaseOrderById(int id) {
		Response response = given()
				.contentType("application/json")
				.accept("application/json")
				.pathParam("orderID", id)
				
				.when()
				.get(Routes.find_purchase_by_id);
		
		return response;
	}
	
	public static Response deletePurchaseOrderById(int id) {
		Response response = given()
				.contentType("application/json")
				.accept("application/json")
				.pathParam("orderID", id)
				
				.when()
				.delete(Routes.delete_purchase_by_id);
		
		return response;
	}
}
