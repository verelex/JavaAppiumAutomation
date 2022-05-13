package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    public static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
            ADD_TO_MY_LIST_OVERLAY = "//android.widget.TextView[@text='GOT IT']",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),"Cannot find article title on page",15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),"Cannot find the end of article",20
        );
    }

    public void addArticleToMyList(String name_of_folder){

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON), // меню ...
                "Cannot find button to open article options",
                5);

        this.waitForElementAndClick( // элемент меню 'Add to reading list'
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5);

        this.waitForElementAndClick( // нажать кнопку 'GOT IT'
                By.xpath(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                30);

        this.waitForElementAndClear( // очистить поле ввода
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder",
                5);

        /*this.waitForElementAndSetValue(
                By.id("org.wikipedia:id/text_input"),
                "Learning programming",
                "Cannot put text into article folder input",
                5);
        */

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot find input to set name of articles folder",
                5);

        this.waitForElementAndClick( // нажать кнопку "ОК"
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5);

        /*this.waitForElementAndClick( // нажать кнопку закрытия
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                15);*/
    }

    public void closeArticle(){
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),"Cannot close article, cannot find X link",5);
    }
}
