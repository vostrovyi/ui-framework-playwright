package automationexercise.controllers;

import automationexercise.utils.AllureLogger;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Step;

public class BrandsController {

    private final APIRequestContext request;

    public BrandsController(APIRequestContext request) {
        this.request = request;
    }

    @Step("GET All Brands List")
    public APIResponse getAllBrands() {
        APIResponse response = request.get("/api/brandsList");
        AllureLogger.log("GET", "/api/brandsList", response);
        return response;
    }

    @Step("PUT To All Brands List")
    public APIResponse putToBrandsList() {
        APIResponse response = request.put("/api/brandsList");
        AllureLogger.log("PUT", "/api/brandsList", response);
        return response;
    }
}
