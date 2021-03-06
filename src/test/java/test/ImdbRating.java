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

public class ImdbRating extends BaseTest {

    @Test
    public void GET_HIGHEST_RATING() throws Exception {
        String movieSearch = "lord of the rings";
        Map<String, Float> storeMovieRating = new HashMap<>();

        //Get all movie Response from api
        Response response = apiClient.getRequest(API_KEY, movieSearch, Constant.SEARCH_MOVIE);
        List<Map<String, String>> movieLists = response.jsonPath().getList("results");

        //Get all the  movie imdb rating from previous response
        for (int i = 0; i < movieLists.size(); i++) {
            Response response1 = apiClient.getRequest(Constant.MOVIE_RATINGS + "/" + API_KEY + "/" + movieLists.get(i).get("id"));
            if (!response1.jsonPath().getString("imDb").isEmpty())
                storeMovieRating.put(response1.jsonPath().getString("fullTitle"), Float.parseFloat(response1.jsonPath().getString("imDb")));
        }

        //Get top 3 movie on basis of ratings
        List<Map.Entry<String, Float>> sortedMap = Utility.getSortedMap(storeMovieRating);

        WebDriver webDriver = DriverClient.getDriver();
        webDriver.manage().window().maximize();
        imdbSearch imdbSearch = new imdbSearch(webDriver);
        webDriver.get("https://www.imdb.com/");
        imdbSearch.searchMovie(movieSearch);
        imdbSearch.clickCategories("Movie");
        ExtentManager.logPass(sortedMap.toString(), "Top 3 imdb Rating :: ");
        boolean flag = true;
        List<String> movieList = imdbSearch.getMovieList();
        ExtentManager.logPass(movieList.toString(), "Movie List in Web :: ");
        for (int i = 0; i < sortedMap.size(); i++) {
            if (!movieList.contains(sortedMap.get(i).getKey())) {
                ExtentManager.logPass(sortedMap.get(i).getKey(), "Movie not present in web  :: ");
                flag = false;
                break;
            }
        }
        Assert.assertTrue(flag);
        webDriver.quit();
    }
}
