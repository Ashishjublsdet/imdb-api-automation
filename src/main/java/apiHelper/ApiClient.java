package apiHelper;


import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import customReporter.ExtentManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class ApiClient {

    public ApiClient(String URI) {
        RestAssured.baseURI = URI;
    }

    public Response getRequest(String params) {
        ExtentManager.getTest().get().createNode(params).log(Status.INFO, params);
        Response response = given().relaxedHTTPSValidation().log().all()
                .with()
                .contentType(ContentType.JSON)
                .get(params).then().extract().response();
        ExtentManager.getTest().get().createNode(" API RESPONSE ")
                .info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        return response;
    }

    public Response getRequest(String apiKey, String movie, String params) {
        ExtentManager.getTest().get().createNode(params + apiKey + movie).log(Status.INFO, params);
        Response response = given().relaxedHTTPSValidation().log().all()
                .with()
                .pathParam("apiKey", apiKey)
                .pathParam("searchMovie", movie)
                .contentType(ContentType.JSON)
                .get(params + "/{apiKey}/{searchMovie}").then().extract().response();
        ExtentManager.getTest().get().createNode(" API RESPONSE ")
                .info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        return response;
    }

}
