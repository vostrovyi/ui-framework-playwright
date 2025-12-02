package automationexercise.controllers;

import automationexercise.utils.AllureLogger;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Step;

public class LoginController {

    private final APIRequestContext request;

    public LoginController(APIRequestContext request) {
        this.request = request;
    }

    @Step("Verify Login with email: '{email}' and password: '{password}'")
    public APIResponse verifyLogin(String email, String password) {
        FormData formData = FormData.create();

        if (email != null) {
            formData.set("email", email);
        }
        if (password != null) {
            formData.set("password", password);
        }

        APIResponse response = request.post("/api/verifyLogin",
                RequestOptions.create().setForm(formData));

        String logBody = "email=" + email + "&password=" + password;

        AllureLogger.log("POST", "/api/verifyLogin", logBody, response);

        return response;
    }

    @Step("DELETE Verify Login (Check Method Not Allowed)")
    public APIResponse deleteVerifyLogin() {
        APIResponse response = request.delete("/api/verifyLogin");
        AllureLogger.log("DELETE", "/api/verifyLogin", response);
        return response;
    }
}
