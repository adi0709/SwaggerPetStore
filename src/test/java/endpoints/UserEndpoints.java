package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.userPayload.UserData;
import setUp.BaseTest;


public class UserEndpoints extends BaseTest {

    public static Response createUser(UserData payload)
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(config.getProperty("createUserEndpointEndpoint"));
    }

    public static Response getUser(String userName) {

        return RestAssured
                .given()
                .pathParam ("username", userName)
                .when ()
                .get(config.getProperty("getUserByUserNameEndpoint"));

    }

    public static Response updateUser(String userName, UserData payload) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept (ContentType.JSON)
                .pathParam("username", userName)
                .body (payload)
                .when ()
                .put(config.getProperty("updateUserByUserNameEndpoint"));
    }


    public static Response deleteUser(String userName) {

        return RestAssured
                .given ()
                .pathParam ("username", userName)
                .when ()
                .delete(config.getProperty("deleteUserByUserNameEndpoint"));
    }
}
