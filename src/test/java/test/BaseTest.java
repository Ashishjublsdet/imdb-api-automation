package test;

import apiHelper.ApiClient;
import org.testng.annotations.BeforeSuite;
import utils.Utility;

import java.util.Properties;

public class BaseTest {
    protected static String API_KEY;
    protected ApiClient apiClient;

    Properties properties;

    @BeforeSuite
    public void initSetup() throws Exception {
        String path = System.getProperty("user.dir") + "//src//main//resources//local.properties";
        properties = Utility.loadProperty(path);
        API_KEY = properties.getProperty("API_KEY");
        apiClient= new ApiClient(properties.getProperty("API_BASE_URL"));
    }

}
