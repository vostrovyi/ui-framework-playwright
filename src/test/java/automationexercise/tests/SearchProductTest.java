package automationexercise.tests;

import automationexercise.base.BaseTest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SearchProductTest extends BaseTest {

    @Test
    @DisplayName("POST /searchProduct: Should return HTTP 200 and products matching 'top'")
    @Description("Verifies POST request with 'search_product=top' returns HTTP 200 and valid list")
    @Story("Search product")
    void searchProductByTopKeyword() {
        APIResponse response = searchProducts();

        assertThat("Response status code not 200", response.status(), equalTo(200));
        assertThat("The response does not contain any products.", response.text(), notNullValue());
        assertThat("", response.text().toLowerCase(), containsString("top"));
    }

    @Step("Send POST request to /api/searchProduct with search_product='top'")
    private APIResponse searchProducts() {

        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .setData("search_product=top");

        APIResponse apiResponse = requestContext.post("/api/searchProduct", options);

        return apiResponse;
    }
}
