package automationexercise.controllers;

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
        return request.get("/api/productsList");
    }

    @Step("POST To All Products List (Unsupported)")
    public APIResponse postAllProducts() {
        return request.post("/api/productsList");
    }

    @Step("Search Product by keyword: {keyword}")
    public APIResponse searchProduct(String keyword) {
        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .setData("search_product=" + keyword);

        return request.post("/api/searchProduct", options);
    }

    @Step("Search Product without parameters")
    public APIResponse searchProductNoParams() {
        return request.post("/api/searchProduct");
    }
}