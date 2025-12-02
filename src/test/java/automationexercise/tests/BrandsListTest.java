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

public class BrandsListTest extends BaseTest {

    @Test
    @DisplayName("API 3: GET /brandsList should return 200 and a list of brands")
    @Description("GET All Brands List. Check status code and response body")
    @Story("Brands API")
    void getAllBrandsList() {
        APIResponse response = brandsController.getAllBrands();

        assertThat("Response status code not 200", response.status(), equalTo(200));
        assertThat("The response does not contain any brands.", response.text(), notNullValue());
        assertThat("Response must contain the 'brand' field", response.text(), containsString("brand"));
        assertThat("Response must contain the 'id' field", response.text(), containsString("id"));
    }

    @Test
    @DisplayName("API 4: PUT /brandsList should return 405")
    @Description("PUT To All Brands List. Check response code from body")
    @Story("Brands API")
    void putToBrandsListShouldReturn405() {
        APIResponse response = brandsController.putToBrandsList();

        assertThat("HTTP status is not 200", response.status(), equalTo(200));
        assertThat("The response body is null", response.text(), notNullValue());
        assertThat("The response body is empty", response.text().trim(), not(emptyString()));

        // Check value of the responseCode field from JSON
        JSONObject json = new JSONObject(response.text());
        assertThat("Logical responseCode is incorrect", json.getInt("responseCode"), equalTo(405));

        // Check message
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo("This request method is not supported."));
    }
}
