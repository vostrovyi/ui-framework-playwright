package automationexercise.tests;

import automationexercise.base.BaseTest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("API 7: POST /verifyLogin: Should return HTTP 200 and valid response message")
    @Description("Verifies POST request with valid email and password returns HTTP 200 and valid response message")
    @Story("Login API")
    void verifyLoginWithValidDetails() {
        APIResponse response = loginWithEmailAndPassword();

        JSONObject json = new JSONObject(response.text());
        System.out.println(json);
        assertThat("The logical responseCode is not 200", json.getInt("responseCode"), equalTo(200));

        // Check message
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo("User exists!"));
    }

    @Step("Send POST request to /api/verifyLogin with valid credentials")
    private APIResponse loginWithEmailAndPassword() {

        FormData formData = FormData.create()
                .set("email", "test@tes342424wefft.com")
                .set("password", "cxLszNRJ4avN@Qy");

        RequestOptions options = RequestOptions.create()
                .setForm(formData);
        return requestContext.post("/api/verifyLogin", options);
    }
}
