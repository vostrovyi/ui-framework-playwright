package automationexercise.utils;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FormData;
import io.qameta.allure.Allure;
import org.json.JSONArray;
import org.json.JSONObject;

public class AllureLogger {

    // Logging for requests w/o body (GET, DELETE)
    public static void log(String method, String url, APIResponse response) {
        log(method, url, null, response);
    }

    // Main logging method
    public static void log(String method, String url, Object requestBody, APIResponse response) {
        Allure.step("HTTP Request: " + method + " " + url, step -> {

            // 1. Attach Request
            Allure.step("Request Details", () -> {
                if (requestBody != null) {
                    // If it is FormData (for login), display it as text
                    if (requestBody instanceof FormData) {
                        Allure.addAttachment("Request Body (Form Data)", requestBody.toString());
                    } else {
                        // If JSON or string
                        Allure.addAttachment("Request Body", "application/json", prettyPrintJson(requestBody.toString()));
                    }
                } else {
                    Allure.addAttachment("Request Body", "No Body");
                }
            });

            // 2. Attach Response
            Allure.step("Response Details: " + response.status() + " " + response.statusText(), () -> {
                String responseBody = response.text();
                Allure.addAttachment("Response Body", "application/json", prettyPrintJson(responseBody));
                // Attach headers
                Allure.addAttachment("Response Headers", response.headers().toString());
            });

            // If the status is invalid (4xx, 5xx)
            if (response.status() >= 400) {
                Allure.addAttachment("Error Status", "Status code implies error: " + response.status());
                System.err.println("API Error: " + method + " " + url + " returned " + response.status());
            }
        });
    }

    // formatting JSON
    private static String prettyPrintJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) return "";
        try {
            if (jsonString.trim().startsWith("{")) {
                return new JSONObject(jsonString).toString(4);
            } else if (jsonString.trim().startsWith("[")) {
                return new JSONArray(jsonString).toString(4);
            }
        } catch (Exception e) {
            return jsonString; // If not JSON, return as is
        }
        return jsonString;
    }
}
