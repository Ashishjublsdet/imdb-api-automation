package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;

public class imdbSearch extends BasePage {

    public imdbSearch(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "suggestion-search")
    WebElement search_Input;

    @FindBy(id = "suggestion-search-button")
    WebElement searchButton;

    @FindBy(xpath = "//*[@class=\"findFilterList\"]//ul//a")
    List<WebElement> categories;

    @FindBy(xpath = "//td[@class=\"result_text\"]")
    List<WebElement> movieList;

    @FindBy(xpath = "//*[contains(text(),\"Displaying\")]")
    WebElement result;

    @FindBy(xpath = "//*[contains(text(),\"Results\")]")
    WebElement results;

    public void waitTillElementVisible(WebElement element) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void scroll(WebElement element) {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void searchMovie(String movieName) {
        search_Input.sendKeys(movieName);
        searchButton.click();
    }

    public void clickCategories(String movieName) {
        waitTillElementVisible(results);
        scroll(categories.get(0));
        waitTillElementVisible(categories.get(0));
        categories.stream().filter(p -> p.getText().equals(movieName)).findFirst().get().click();
    }

    public List<String> getMovieList() {
        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(result));
        List<String> movieName = new LinkedList<>();
        for (WebElement movie : movieList) {
            movieName.add(movie.getText());
        }
        return movieName;
    }
}
