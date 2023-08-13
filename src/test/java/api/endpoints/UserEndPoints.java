package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//this is user end points java file created for perform CRUD operations

public class UserEndPoints {
	
	public static Response creatUser(User payload)
	{
		
		Response response=given()                   //pre requisited
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(payload)
		.when()
		    .post(Routes.post_url);                  //request
		
		return response;
		
	}

	
	public static Response readUser(String userName)
	{
		
		Response response=given()                   //pre requisited
		   .pathParam("username", userName)
		.when()
		    .get(Routes.get_url);                  //request
		
		return response;
		
	}
	
	public static Response updateUser(String userName ,User payload)
	{
		
		Response response=given()                   //pre requisited
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .pathParam("username", userName)
		   .body(payload)
		.when()
		    .put(Routes.update_url);                  //request
		
		return response;
		
	}
	
	public static Response deleteUser(String userName)
	{
		
		Response response=given()                   //pre requisited
		   .pathParam("username", userName)
		.when()
		    .delete(Routes.delete_url);                  //request
		
		return response;
		
	}
}
