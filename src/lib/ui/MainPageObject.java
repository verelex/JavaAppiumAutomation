package lib.ui;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MainPageObject {

        //private int tmp;

        protected AppiumDriver driver;

        public MainPageObject(AppiumDriver driver) {
            this.driver = driver;
            //tmp = 777;
        }

        public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
        }

        public WebElement waitForElementPresent(By by, String error_message){
            return waitForElementPresent(by,error_message,5);
        }

        public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
            WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
            element.click();
            return element;
        }

        public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
            WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
            element.sendKeys(value);
            return element;
        }

        public WebElement waitForElementAndSetValue(By by, String value, String error_message, long timeoutInSeconds){
            WebElement el = waitForElementPresent(by,error_message,timeoutInSeconds);
            MobileElement me = (MobileElement) el;
            me.setValue(value);
            return el;
        }

        public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(by)
            );
        }

        public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
            WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
            element.clear();
            return element;
        }

        // my new method
        public boolean assertElementHasText(By by, String value, String error_message){
            WebElement element = waitForElementPresent(by,error_message);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(by,value)
            );
        }

        // ex3 method
        public int assertElementHasChildren(String id, String error_message){
            List<WebElement> listWEs = driver.findElementsById(id);
            int length = listWEs.size();
            return length;
        }

        // ex4 method
        public List<String> getWords(String id, String error_message){
            List<WebElement> listWEs = driver.findElementsById(id);
            List<String> names = new LinkedList<>();
            ListIterator<WebElement> WEIterator = listWEs.listIterator();
            int index=0;
            while (WEIterator.hasNext()) {
                //String tmp = title_element.getAttribute("text");
                names.add(listWEs.get(index).getAttribute("text"));
                WEIterator.next();
                index+=1;
            }
            return names;
        }

        public void swipeUp(int timeOfSwipe){
            TouchAction action = new TouchAction(driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width/2;
            int start_y = (int)(size.height * 0.8);
            int end_y = (int)(size.height * 0.2);

            action
                    .press(x,start_y)
                    .waitAction(timeOfSwipe)
                    .moveTo(x,end_y)
                    .release()
                    .perform();
        }

        public void swipeUpQuick(){
            swipeUp(200);
        }

        public void swipeUpToFindElement(By by, String error_message, int max_swipes){
            int already_swiped = 0;
            while (driver.findElements(by).size() == 0) {
                if (already_swiped > max_swipes){
                    waitForElementPresent(by,"Cannot find element by swiping up. \n"+error_message,0);
                    return;
                }
                swipeUpQuick();
                ++already_swiped;
            }
        }

        public void swipeElementToLeft(By by, String error_message){
            WebElement element = waitForElementPresent(
                    by,
                    error_message,
                    10);

            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction(driver);
            action
                    .press(right_x,middle_y)
                    .waitAction(300)
                    .moveTo(left_x,middle_y)
                    .release()
                    .perform();
        }

        public int getAmountOfElements(By by){
            List elements = driver.findElements(by);
            return elements.size();
        }

        public void assertElementNotPresent(By by, String error_message){
            int amount_of_elements = getAmountOfElements(by);
            if (amount_of_elements > 0){
                String default_message = "An element '" + by.toString() + "' supposed to be not present";
                throw new AssertionError(default_message + " " + error_message);
            }
        }

        public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds){
            WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
            return element.getAttribute(attribute);
        }

        // Ex5 methods
        public void saveFirstArticleToMyListEx() {
            waitForElementAndClick(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot search 'Search Wikipedia' input",
                    5
            );

            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    "Java",
                    "Cannot find search input",
                    5
            );

            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                    "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                    5);

            waitForElementPresent(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "Cannot find article title",
                    15
            );

            waitForElementAndClick(
                    By.xpath("//android.widget.ImageView[@content-desc='More options']"), // меню ...
                    "Cannot find button to open article options",
                    5);

            waitForElementAndClick( // элемент меню 'Add to reading list'
                    By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                    "Cannot find option to add article to reading list",
                    5);

            waitForElementAndClick( // нажать кнопку 'GOT IT'
                    By.xpath("//android.widget.TextView[@text='GOT IT']"),
                    "Cannot find 'Got it' tip overlay",
                    30);

            waitForElementAndClear( // очистить поле ввода
                    By.id("org.wikipedia:id/text_input"),
                    "Cannot find input to set name of articles folder",
                    5
            );

            waitForElementAndSetValue(
                    By.id("org.wikipedia:id/text_input"),
                    "Learning programming",
                    "Cannot put text into article folder input",
                    5);

            waitForElementAndClick( // нажать кнопку "ОК"
                    By.xpath("//*[@text='OK']"),
                    "Cannot press OK button",
                    5);

            waitForElementAndClick( // нажать кнопку закрытия
                    By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                    "Cannot close article, cannot find X link",
                    15);
        }

        public void saveSecondArticleToMyList() {
            waitForElementAndClick(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot search 'Search Wikipedia' input",
                    5
            );

            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    "Java",
                    "Cannot find search input",
                    5
            );

            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Island of Indonesia']"),
                    "Cannot find 'Island of Indonesia' topic searching by 'Java'",
                    5);

            waitForElementPresent(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "Cannot find article title",
                    15
            );

            waitForElementAndClick(
                    By.xpath("//android.widget.ImageView[@content-desc='More options']"), // меню ...
                    "Cannot find button to open article options",
                    5);

            waitForElementAndClick( // элемент меню 'Add to reading list'
                    By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                    "Cannot find option to add article to reading list",
                    5);

            waitForElementAndClick( // найти уже существующий список 'Learning programming'
                    By.xpath("//android.widget.TextView[@text='Learning programming']"),
                    "Cannot find 'Learning programming' list overlay",
                    30);

            waitForElementAndClick( // нажать кнопку закрытия
                    By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                    "Cannot close article, cannot find X link",
                    15);
        }

        // Ex6 method
        public void assertElementPresent(By by, String error_message) {
            int amount_of_elements = getAmountOfElements(by);
            if (amount_of_elements == 0) {
                String default_message = "An element '" + by.toString() + "' supposed to be present";
                throw new AssertionError(default_message + " " + error_message);
            }
        }
}


