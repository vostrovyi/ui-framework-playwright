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

    private static final String VALID_EMAIL = System.getenv().getOrDefault("LOGIN_EMAIL", "test@tes342424wefft.com");
    private static final String VALID_PASSWORD = System.getenv().getOrDefault("LOGIN_PASSWORD", "cxLszNRJ4avN@Qy");
    private static final String MESSAGE_USER_EXISTS = "User exists!";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found!";
    private static final String MESSAGE_BAD_REQUEST = "Bad request, email or password parameter is missing in POST request.";
    private static final String MESSAGE_METHOD_NOT_SUPPORTED = "This request method is not supported.";

    @Test
    @DisplayName("API 7: POST /verifyLogin: Should return HTTP 200 and valid response message")
    @Description("Verifies POST request with valid email and password returns HTTP 200 and valid response message")
    @Story("Login API")
    void verifyLoginWithValidDetails() {
        APIResponse response = loginWithEmailAndPassword();
        JSONObject json = new JSONObject(response.text());
        System.out.println(json);

        assertThat("Expected HTTP status 200 but got " + response.status(), response.status(), equalTo(200));
        assertThat("Expected logical responseCode 200 but got " + json.getInt("responseCode"),
                json.getInt("responseCode"), equalTo(200));
        assertThat("Unexpected response message",
                json.getString("message"), equalTo(MESSAGE_USER_EXISTS));
    }

    @Test
    @DisplayName("API 8: POST /verifyLogin: Should return HTTP 400 and invalid response message")
    @Description("Verifies POST request without email parameter returns HTTP 400 and invalid response message")
    @Story("Login API")
    void verifyLoginWithoutEmail() {
        APIResponse response = loginWithoutEmail();

        JSONObject json = new JSONObject(response.text());
        System.out.println(json);

        assertThat("Expected HTTP status 200 but got " + response.status(), response.status(), equalTo(200));
        assertThat("The logical responseCode is not 400", json.getInt("responseCode"), equalTo(400));
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo(MESSAGE_BAD_REQUEST));
    }

    @Test
    @DisplayName("API 9: DELETE /verifyLogin: Should return HTTP 405 and invalid response message")
    @Description("Verifies DELETE request to /verifyLogin returns HTTP 405 and invalid response message")
    @Story("Login API")
    void verifyLoginWithoutPassword() {
        APIResponse response = deleteLogin();

        JSONObject json = new JSONObject(response.text());
        System.out.println(json);

        assertThat("Expected HTTP status 200 but got " + response.status(), response.status(), equalTo(200));
        assertThat("The logical responseCode is not 405", json.getInt("responseCode"), equalTo(405));
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo(MESSAGE_METHOD_NOT_SUPPORTED));
    }

    @Step("Send DELETE request to /api/verifyLogin")
    private APIResponse deleteLogin() {
        return requestContext.delete("/api/verifyLogin");
    }

    @Test
    @DisplayName("API 10: POST /verifyLogin: Should return HTTP 404 and invalid response message")
    @Description("Verifies POST request to /verifyLogin returns HTTP 404 and invalid response message")
    @Story("Login API")
    void verifyLoginWithInvalidDetails() {
        APIResponse response = loginWithInvalidDetails();

        JSONObject json = new JSONObject(response.text());
        System.out.println(json);

        assertThat("Expected HTTP status 200 but got " + response.status(), response.status(), equalTo(200));
        assertThat("The logical responseCode is not 404", json.getInt("responseCode"), equalTo(404));
        assertThat("The response message is incorrect",
                json.getString("message"), equalTo(MESSAGE_USER_NOT_FOUND));
    }

    private APIResponse postLoginRequest(String email, String password) {
        FormData formData = FormData.create();
        if (email != null) formData.set("email", email);
        if (password != null) formData.set("password", password);

        return requestContext.post("/api/verifyLogin",
                RequestOptions.create().setForm(formData));
    }

    private APIResponse loginWithEmailAndPassword() {
        return postLoginRequest(VALID_EMAIL, VALID_PASSWORD);
    }

    private APIResponse loginWithoutEmail() {
        return postLoginRequest(null, VALID_PASSWORD);
    }

    private APIResponse loginWithInvalidDetails() {
        return postLoginRequest(VALID_EMAIL, "123");
    }
}
