package api.endpoints;

public class Routes {
	
	static String base_url = "https://petstore.swagger.io/v2";
	//Pets urls	
	static String post_pet ="https://petstore.swagger.io/v2/pet";
	static String getPet_ById ="https://petstore.swagger.io/v2/pet/{petId}";
	static String getPet_ByStatus ="https://petstore.swagger.io/v2/pet/findByStatus";
	static String put_pet ="https://petstore.swagger.io/v2/pet";
	static String post_uploadImage = "https://petstore.swagger.io/v2/pet/{petId}/uploadImage";
	static String post_pet_ById = "https://petstore.swagger.io/v2/pet/{petId}";
	static String delete_pet = "https://petstore.swagger.io/v2/pet/{petId}";
	
	//Store
	static String get_store_inventory ="https://petstore.swagger.io/v2/store/inventory";
	static String place_order_pet = "https://petstore.swagger.io/v2/store/order";
	static String find_purchase_by_id ="https://petstore.swagger.io/v2/store/order/{orderID}";
	static String delete_purchase_by_id ="https://petstore.swagger.io/v2/store/order/{orderID}";
	
	//user
	static String create_single_user = "https://petstore.swagger.io/v2/user";
	static String get_user_by_name = "https://petstore.swagger.io/v2/user/{username}";
	static String update_user = "https://petstore.swagger.io/v2/user/{username}";
	static String delete_user ="https://petstore.swagger.io/v2/user/{username}";
	static String create_user_with_List ="https://petstore.swagger.io/v2/user/createWithList";
			
}
