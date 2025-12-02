package automationexercise.tests;

import automationexercise.base.BaseTest;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductListTest extends BaseTest {

    @Test
    @DisplayName("API 1: GET /productsList should return HTTP 200 and a list of products")
    @Description("GET All Products List. Check status code and response body")
    @Story("Products API")
    void getAllProductsList() {
        APIResponse response = productController.getAllProducts();

        assertThat("Response status code not 200", response.status(), equalTo(200));
        assertThat("The response does not contain any products.", response.text(), notNullValue());
        assertThat("Response must contain the essential 'price' field in product data.", response.text(), containsString("price"));
    }

    @Test
    @DisplayName("API 2: POST To All Products List")
    @Description("POST To All Products List. Check response code from body")
    @Story("Products API")
    void postAllProductsList() {
        APIResponse response = productController.postAllProducts();

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
}
