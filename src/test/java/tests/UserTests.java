package tests;

import assertions.Assertions;
import endpoints.UserEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payload.userPayload.UserData;
import setUp.BaseTest;


public class UserTests extends BaseTest {

    Faker faker;
    UserData userPayload;

    @BeforeMethod
    public void setup() {
        faker = new Faker();

        userPayload = new UserData(
                faker.idNumber().hashCode(),
                faker.name().name(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.internet().password(5, 10),
                faker.phoneNumber().cellPhone(),
                faker.number().numberBetween(0, 0));

        //Creating a user Since it s required in almost all cases
        Response createUserResponse = UserEndpoints.createUser(userPayload);
        createUserResponse.then().log().all();

        Assertions.statusCodeAssertion(200, createUserResponse);
        Assertions.createUserAssertions(createUserResponse, userPayload, config.getProperty("createUserResponseSchema"));
    }


    @Test
    public void createUserTest() {
        //Sending the Response and logging all data
        Response createUserResponse = UserEndpoints.createUser(userPayload);
        createUserResponse.then().log().all();

        //Adding Assertions
        Assertions.statusCodeAssertion(200, createUserResponse);
        Assertions.createUserAssertions(createUserResponse, userPayload, config.getProperty("createUserResponseSchema"));
    }

    @Test
    public void findUserByUserNameTest() {
        //Finding the created user
        Response getUserResponse = UserEndpoints.getUser(this.userPayload.getUsername());
        getUserResponse.then().log().all();

        //Adding Assertions
        Assertions.statusCodeAssertion(200, getUserResponse);
        getUserResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(config.getProperty("createUserSchema")));
    }

    @Test
    public void testUpdateUserByName() {
        //Update data using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        //Updating the above created user
        Response updateUserResponse = UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
        updateUserResponse.then().log().all();
        //Assertions on the put update user endpoint
        Assertions.updateUserAssertions(updateUserResponse, userPayload, config.getProperty("createUserResponseSchema"));
        Assertions.statusCodeAssertion(200, updateUserResponse);

        //Validating the data is updated to get userBy Id
        Response responseAfterUpdate = UserEndpoints.getUser(this.userPayload.getUsername());
        responseAfterUpdate.then().log().all();
        //Adding assertions and validating the user was correctly updated
        Assertions.statusCodeAssertion(200, responseAfterUpdate);
        Assertions.findUserByUserNameAssertions(responseAfterUpdate, this.userPayload, config.getProperty("createUserSchema"));
    }

    @Test
    public void testDeleteUserByName() {
        //Deleting  the user and adding assertions
        Response deleteUserResponse = UserEndpoints.deleteUser(this.userPayload.getUsername());
        deleteUserResponse.then().log().all();
        //Adding Deletion Assertions
        Assertions.statusCodeAssertion(200, deleteUserResponse);
        Assertions.deleteUserAssertions(deleteUserResponse, userPayload, config.getProperty("deleteUserResponseSchema"));
    }
}