package automationexercise.controllers;

import automationexercise.utils.AllureLogger;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Step;

public class ProductController {

    private final APIRequestContext request;

    public ProductController(APIRequestContext request) {
        this.request = request;
    }

    @Step("GET All Products List")
    public APIResponse getAllProducts() {
        APIResponse response = request.get("/api/productsList");
        AllureLogger.log("GET", "/api/productsList", response);
        return response;
    }

    @Step("POST To All Products List (Unsupported)")
    public APIResponse postAllProducts() {
        APIResponse response = request.post("/api/productsList");
        AllureLogger.log("POST", "/api/productsList", response);
        return response;
    }

    @Step("Search Product by keyword: {keyword}")
    public APIResponse searchProduct(String keyword) {
        String bodyData = "search_product=" + keyword;

        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .setData(bodyData);

        APIResponse response = request.post("/api/searchProduct", options);
        AllureLogger.log("POST", "/api/searchProduct", bodyData, response);
        return response;
    }

    @Step("Search Product without parameters")
    public APIResponse searchProductNoParams() {
        APIResponse response = request.post("/api/searchProduct");
        AllureLogger.log("POST", "/api/searchProduct", response);
        return response;
    }
}