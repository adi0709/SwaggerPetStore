package tests;

import assertions.Assertions;
import endpoints.StoreEndpoints;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payload.storepayload.StoreData;
import setUp.BaseTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class StoreTests extends BaseTest {

    Faker faker;
    StoreData storePayload;
    StoreData inCorrectStorePayload;
    SimpleDateFormat time;
    Timestamp timestamp;

    @BeforeMethod
    public void setup() {

        //Getting current date for the order shipDate
        time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        timestamp = new Timestamp(System.currentTimeMillis());


        faker = new Faker();
        storePayload = new StoreData(
                faker.number().hashCode(),
                faker.number().positive(),
                faker.number().numberBetween(1, 5),
                time.format(timestamp),
                "PENDING",
                true
        );

        inCorrectStorePayload = new StoreData(
                faker.number().hashCode(),
                faker.number().positive(),
                faker.number().numberBetween(1, 5),
                faker.date().birthday().toString(),
                "PENDING",
                true
        );
    }

    @Test
    public void createStoreOrder() {

        Response createOrderResponse = StoreEndpoints.placeOrder(this.storePayload);
        createOrderResponse.then().log().all();

        //Assertions on the create store order endpoint
        Assertions.statusCodeAssertion(200, createOrderResponse);
        Assertions.createStoreOrderAssertions(createOrderResponse, this.storePayload, config.getProperty("createOrderResponseSchema"));
    }

    @Test
    public void getOrderByOrderId() {
        //Create the order for the test
        Response createOrderResponse = StoreEndpoints.placeOrder(this.storePayload);
        createOrderResponse.then().log().all();

        //Assertions for the orderByOrderId Endpoint
        Assertions.statusCodeAssertion(200, createOrderResponse);
        Assertions.createStoreOrderAssertions(createOrderResponse, this.storePayload, config.getProperty("createOrderResponseSchema"));

        //Hit the get order by id response
        Response getOrderByOrderIdResponse = StoreEndpoints.getPurchaseOrderById(this.storePayload.getId());
        getOrderByOrderIdResponse.then().log().all();

        //Assertions on the get order by id endpoint
        Assertions.statusCodeAssertion(200, getOrderByOrderIdResponse);
        Assertions.getOrderByOrderIdAssertions(getOrderByOrderIdResponse, this.storePayload, config.getProperty("createOrderResponseSchema"));
    }

    @Test
    public void getPetsInventory() {
        Response getPetsInventoryResponse = StoreEndpoints.getPetInventoriesByStatus();
        getPetsInventoryResponse.then().log().all();
        Assertions.statusCodeAssertion(200, getPetsInventoryResponse);
    }

    @Test
    public void deletePetOrder() {
        Response createOrderResponse = StoreEndpoints.placeOrder(this.storePayload);
        createOrderResponse.then().log().all();
        Assertions.statusCodeAssertion(200, createOrderResponse);

        //Hit the delete pet order endpoint
        Response deletePetOrderResponse = StoreEndpoints.deleteStoreOrderByOrderId(this.storePayload.getId());
        deletePetOrderResponse.then().log().all();

        //Assertions on the delete store orderEndpoint
        Assertions.statusCodeAssertion(200, deletePetOrderResponse);
        Assertions.deleteStoreOrderAssertions(deletePetOrderResponse, this.storePayload, config.getProperty("deletePetOrderResponseSchema"));
    }


    @Test
    public void createIncorrectStoreOrder() {
        Response createIncorrectStoreOrder = StoreEndpoints.placeOrder(inCorrectStorePayload);
        createIncorrectStoreOrder.then().log().all();

        //Assertions on the Create Incorrect Store order
        Assertions.statusCodeAssertion(500, createIncorrectStoreOrder);
        Assertions.incorrectCreateOrderAssertions(createIncorrectStoreOrder, config.getProperty("deletePetOrderResponseSchema"));
    }
}