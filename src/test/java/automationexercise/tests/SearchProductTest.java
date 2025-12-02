package automationexercise.tests;

import automationexercise.base.BaseTest;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SearchProductTest extends BaseTest {

    @Test
    @DisplayName("API 5: POST /searchProduct: Should return HTTP 200 and products matching 'top'")
    @Description("Verifies POST request with 'search_product=top' returns HTTP 200 and valid list")
    @Story("Search product")
    void searchProductByTopKeyword() {
        APIResponse response = productController.searchProduct("top");

        assertThat("Response status code not 200", response.status(), equalTo(200));
        assertThat("The response does not contain any products.", response.text(), notNullValue());
        assertThat("The response does not contain search product.", response.text().toLowerCase(), containsString("top"));
    }

    @Test
    @DisplayName("API 6: POST /searchProduct w/o search_product: Should return HTTP 400")
    @Description("Verifies POST request without 'search_product' returns HTTP 400 and valid response message")
    @Story("Search product")
    void searchProductWithoutSearchProduct() {
        APIResponse response = productController.searchProductNoParams();

        assertThat("HTTP status is not 200", response.status(), equalTo(200));
        assertThat("The response body is null", response.text(), notNullValue());
        assertThat("The response body is empty", response.text().trim(), not(emptyString()));

        // Check value of the responseCode field from JSON
        JSONObject json = new JSONObject(response.text());
        System.out.println(json);
        assertThat("The logical responseCode is not 400", json.getInt("responseCode"), equalTo(400));

        // Check message
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo("Bad request, search_product parameter is missing in POST request."));
    }
}
