package org.panthercode.arctic.core.http;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.panthercode.arctic.core.processing.modules.helper.TimerOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by architect on 21.04.17.
 */
@Test(sequential = true)
public class HttpClientModuleTest {

    private static final String ENDPOINT_1 = "https://duckduckgo.com/";

    private static final String ENDPOINT_2 = "https://www.youtube.com/";

    private static final TimerOptions OPTIONS = new TimerOptions(60000L, 1000, true, true);

    private static final HttpClient CLIENT = HttpClient.create().setHost("").setSocketTimeout(1000).setConnectionTimeout(1000).build();

    @Test
    public void T001_HttpClientModule_start() {
        final HttpClientModule module = new HttpClientModule(OPTIONS);

        final boolean result = module.start(CLIENT.get(ENDPOINT_1));

        Assert.assertTrue(result, "Result of start method");

        Assert.assertTrue(module.hasResult(), "Module has result");

        Assert.assertNotNull(module.result(), "Actual value of response");
    }

    @Test
    public void T002_HttpClientModule_start_VariousEndpoints() throws IOException {
        final HttpClientModule module = new HttpClientModule(OPTIONS);

        CLIENT.setHost(ENDPOINT_1);

        final boolean result1 = module.start(CLIENT.get("/"));

        Assert.assertTrue(result1, "Result of start method");

        Assert.assertTrue(module.hasResult(), "Module has result");

        final String response1 = EntityUtils.toString(((HttpResponse) module.result()).getEntity());

        CLIENT.setHost(ENDPOINT_2);


        final boolean result2 = module.start(CLIENT.get("/"));

        Assert.assertTrue(result2, "Result of start method");

        Assert.assertTrue(module.hasResult(), "Module has result");

        final String response2 = EntityUtils.toString(((HttpResponse) module.result()).getEntity());

        Assert.assertNotEquals(response1, response2, "Responses of module");
    }

    @Test
    public void T003_HttpClientModule_reset() throws IOException {
        final HttpClientModule module = new HttpClientModule(OPTIONS);

        CLIENT.setHost(ENDPOINT_1);

        final boolean result1 = module.start(CLIENT.get("/"));

        Assert.assertTrue(result1, "Result of start method");

        Assert.assertTrue(module.hasResult(), "Module has result");

        Assert.assertNotNull(module.result(), "Actual value of response");

        module.reset();

        Assert.assertFalse(module.hasResult(), "Module has result");

        Assert.assertNull(module.result(), "Actual value of response");
    }
}
