package automationexercise.tests;

import automationexercise.base.BaseTest;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductListTest extends BaseTest {

    @Test
    @DisplayName("API 1: GET All Products List")
    @Description("GET All Products List. Check status code and response body")
    @Story("Products API")
    void getAllProductsList() {
        APIResponse response = getAllProducts();

        assertThat("Response status code not 200", response.status(), equalTo(200));
        assertThat("The response does not contain any products.", response.text(), notNullValue());
        assertThat("", response.text(), containsString("price"));
    }

    @Step("Execute GET-request /api/productsList")
    private APIResponse getAllProducts() {
        APIResponse apiResponse = requestContext.get("/api/productsList");
        System.out.println("Response Body: " + apiResponse.text());
        return apiResponse;
    }

    @Test
    @DisplayName("API 2: POST To All Products List")
    @Description("POST To All Products List. Check response code from body")
    @Story("Products API")
    void postAllProductsList() {
        APIResponse response = postAllProducts();

        assertThat("HTTP status is not 200", response.status(), equalTo(200));
        assertThat("The response body is null", response.text(), notNullValue());
        assertThat("The response body is empty", response.text().trim(), not(emptyString()));

        // Check value of the responseCode field from JSON
        JSONObject json = new JSONObject(response.text());
        assertThat("The logical responseCode is not 405", json.getInt("responseCode"), equalTo(405));

        // Check message
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo("This request method is not supported."));
    }

    @Step("Execute POST-request /api/productsList")
    private APIResponse postAllProducts() {
        APIResponse apiResponse = requestContext.post("/api/productsList");
        System.out.println("Response Body: " + apiResponse.text());
        return apiResponse;
    }
}
