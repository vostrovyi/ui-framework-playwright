package automationexercise.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static Playwright playwright;
    protected static APIRequestContext requestContext;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://automationexercise.com"));
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
