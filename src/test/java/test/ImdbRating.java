package test;

import DriverManager.DriverClient;
import apiHelper.Constant;
import customReporter.ExtentManager;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.imdbSearch;
import utils.Utility;

import java.util.*;
import java.util.stream.Stream;

public class ImdbRating extends BaseTest {

    @Test
    public void GET_HIGHEST_RATING() throws Exception {
        String movieSearch = "lord of the rings";
        Map<String, Float> storeMovieRating = new HashMap<>();
        Response response = apiClient.getRequest(API_KEY, movieSearch, Constant.SEARCH_MOVIE);
        List<Map<String, String>> movieLists = response.jsonPath().getList("results");
        for (int i = 0; i < movieLists.size(); i++) {
            Response response1 = apiClient.getRequest(Constant.MOVIE_RATINGS + "/" + API_KEY + "/" + movieLists.get(i).get("id"));
            if (!response1.jsonPath().getString("imDb").isEmpty())
                storeMovieRating.put(response1.jsonPath().getString("fullTitle"), Float.parseFloat(response1.jsonPath().getString("imDb")));
        }
        List<Map.Entry<String, Float>> sortedMap = Utility.getSortedMap(storeMovieRating);
        WebDriver webDriver = DriverClient.getDriver();
        webDriver.manage().window().maximize();
        imdbSearch imdbSearch = new imdbSearch(webDriver);
        webDriver.get("https://www.imdb.com/");
        imdbSearch.searchMovie(movieSearch);
        imdbSearch.clickCategories("Movie");
        ExtentManager.getTest().get().info("Top 3 imdb Rating :: " + sortedMap);
        boolean flag = true;
        List<String> movieList = imdbSearch.getMovieList();
        ExtentManager.getTest().get().info("Movie List in Web :: " + movieList);
        for (int i = 0; i < sortedMap.size(); i++) {
            if (!movieList.get(i).contains(sortedMap.get(i).getKey())) {
                flag = false;
                break;
            }
        }
        Assert.assertTrue(flag);
        webDriver.quit();
    }
}
