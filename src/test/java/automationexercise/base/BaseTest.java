package automationexercise.base;

import automationexercise.controllers.BrandsController;
import automationexercise.controllers.LoginController;
import automationexercise.controllers.ProductController;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static Playwright playwright;
    protected static APIRequestContext requestContext;
    protected static ProductController productController;
    protected static BrandsController brandsController;
    protected static LoginController loginController;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://automationexercise.com"));

        productController = new ProductController(requestContext);
        brandsController = new BrandsController(requestContext);
        loginController = new LoginController(requestContext);
    }

    @AfterAll
    static void tearDown() {
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
