package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import io.restassured.response.Response;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;



public class UserTest2 {
	
	Faker faker ;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setUp() {
		
		faker = new Faker();
		userPayload =new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger= LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("********************      Creating User        *************************************");
		
		Response response =UserEndPoints2.creatUser(userPayload);
		response.then().log().all();
		response.prettyPrint();
		
        Assert.assertEquals(response.getStatusCode(), 200);
        
		logger.info("********************     User is Created        *************************************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		
		logger.info("********************      Reading  User   info     *************************************");
		Response response =UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        response.prettyPrint();

		logger.info("********************      User   info  is displayed   *************************************");
        
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("********************      Updating  User   info     *************************************");
		
		//Update the data using Payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response =UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        
        //Checking data after the update(GET)
        Response responseAfterUpdate =UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("********************      User   info  is Updated   *************************************");
        
	}

	@Test(priority=4)
	public void testDeleteUserByName() {
		
		logger.info("********************      Deleting  User   info     *************************************");
		
		Response response =UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
        Assert.assertEquals(response.getStatusCode(), 200);
        
		logger.info("********************      User   info is deleated    *************************************");
		
	}

	
}
