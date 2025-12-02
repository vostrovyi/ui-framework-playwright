package automationexercise.base;

import automationexercise.controllers.BrandsController;
import automationexercise.controllers.LoginController;
import automationexercise.controllers.ProductController;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected Playwright playwright;
    protected APIRequestContext requestContext;
    protected ProductController productController;
    protected BrandsController brandsController;
    protected LoginController loginController;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://automationexercise.com"));

        productController = new ProductController(requestContext);
        brandsController = new BrandsController(requestContext);
        loginController = new LoginController(requestContext);
    }

    @AfterEach
    void tearDown() {
        if (requestContext != null) {
            requestContext.dispose();
            requestContext = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
