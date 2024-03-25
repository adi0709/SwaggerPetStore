package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.storepayload.StoreData;
import setUp.BaseTest;

public class StoreEndpoints extends BaseTest {
    public static Response placeOrder(StoreData payload)
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post(config.getProperty("createStoreOrderEndpoint"));
    }

    public static Response getPurchaseOrderById(int orderId)
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("orderId", orderId)
                .when()
                .get(config.getProperty("getPurchaseOrderByIdEndpoint"));
    }

    public static Response getPetInventoriesByStatus()
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(config.getProperty("getPetInventoriesByStatusEndpoint"));
    }

    public static Response deleteStoreOrderByOrderId(int orderId){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("orderId", orderId)
                .when()
                .delete(config.getProperty("deleteStoreOrderEndpoint"));
    }
}
