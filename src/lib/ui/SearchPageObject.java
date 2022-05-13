package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
    SEARCH_INPUT = "//*[contains(@text,'Search…')]",
    SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
    SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
    SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    /* TEMPLATES METHODS */

    public void  initSearchInput(){
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find search input before clicking search input element");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element",5);
    }

    public void waitForCancelButtonToAppear(){
        waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),"Cannot find search cancel button",5);
    }

    public void waitForCancelButtonToDisappear(){
        waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),"Search cancel button is still present",5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),"Cannot find and click search cancel button",5);
    }

    public void typeSearchLine(String search_Line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_Line,"Cannot find and type into search input",5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with substring "+substring);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),"Cannot find and click search result with substring "+substring,10);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "cannot find anything by the request",
                15);
        return getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),"Cannot find empty result element",15);
    }

    public void assertThereIsNotResultOfSearch(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"We supposed not to find any result");
    }

    public void makeMeHappy1(){ // 4m ex3
        this.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        this.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Kotlin",
                "Cannot find search input",
                5
        );
        ////
        System.out.println("Count of elements is " +
                this.assertElementHasChildren(
                        "org.wikipedia:id/page_list_item_container",
                        "Cannot find list of search results (Ex3)"
                )
        );
        ////
        this.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        this.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        this.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    public void makeMeHappy2(){
        // сначала добавляем две статьи в папку
        this.saveFirstArticleToMyListEx();
        this.saveSecondArticleToMyList();

        // затем одну удаляем
        this.waitForElementAndClick( // найти и кликнуть на раздел 'my lists'
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                15);

        this.waitForElementAndClick( // найти и кликнуть на созданную ранее папку 'Learning programming'
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find created folder",
                15);

        this.swipeElementToLeft( // свайпнуть влево тем самым удалив статью
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find saved article"
        );

        this.waitForElementNotPresent( // убедиться, что статьи нет
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                15
        );
    }

    public void makeMeHappy3(){ // 4m ex6
        /*this.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );*/

        String search_line = "Java";

        this.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "org.wikipedia:id/title";

        this.assertElementPresent(
                By.id(search_result_locator),
                "cannot find some results by request " + search_line
        );
    }
}
