import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","9");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/verelex/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest() /* в приложении wikipedia.apk находим поле ввода с надписью 'Search wikipedia' и кликаем на нем
     появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Java'. Далее происходит поиск и
      мы определяем есть ли в найденном строка oop lang */
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);
    }

    @Test
    public void testCancelSearch() /* находим поле ввода с надписью 'Search Wikipedia' по его id и кликаем на нем
    появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Java'. Далее происходит поиск
    находим поле ввода по id и стираем ранее введеный запрос 'Java'
    ищем кнопку закрытия по id и нажимаем ее
    ещё раз ищем кнопку закрытия и убеждаемся, что её нет. */
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() /* в приложении wikipedia.apk находим поле ввода с надписью 'Search wikipedia' и кликаем на нем
     появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Java'. Далее происходит поиск и
      мы определяем есть ли в найденном строка 'Object-oriented programming language' и кликаем на ней
       далее находим по id заголовок открытой статьи (про яву)
       получаем текст этого заголовка
       удостоверяемся, что это текст: 'Java (programming language)'
       */
    {
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
                15);

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    //my new test
    @Test
    public void testElementHasText(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "input don't have text: 'Search Wikipedia'"
        );
    }

    @Test
    public void testCancelSearchEx3()
        /* находим поле ввода с надписью 'Search Wikipedia' по его id и кликаем на нем
    появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Kotlin'. Далее происходит поиск
    Убеждаемся, что найдено несколько статей
    ищем кнопку закрытия по id и нажимаем ее
    ещё раз ищем кнопку закрытия и убеждаемся, что её нет, тем самым убеждаемся, что результат поиска пропал */
        {
            waitForElementAndClick(
                    By.id("org.wikipedia:id/search_container"),
                    "Cannot search 'Search Wikipedia' input",
                    5
            );

            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    "Kotlin",
                    "Cannot find search input",
                    5
            );
            ////
            System.out.println("Count of elements is "+
                    assertElementHasChildren(
                        "org.wikipedia:id/page_list_item_container",
                        "Cannot find list of search results (Ex3)"
                )
            );
            ////
            waitForElementAndClear(
                    By.id("org.wikipedia:id/search_src_text"),
                    "Cannot find search field",
                    5
            );

            waitForElementAndClick(
                    By.id("org.wikipedia:id/search_close_btn"),
                    "Cannot find X to cancel search",
                    5
            );

            waitForElementNotPresent(
                    By.id("org.wikipedia:id/search_close_btn"),
                    "X is still present on the page",
                    5
            );
    }

    @Test
    public void testCheckWordInSearchResultsEx4()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "JAVA",
                "Cannot find search input",
                5
        );

        List<String> LS = getWords(
                "org.wikipedia:id/page_list_item_title",
                "Cannot find search elements (Ex4)"
        );

        for (String s : LS) {
            System.out.println(s);
            Assert.assertTrue("item don't have word 'Java'",s.contains("Java"));
        }
    }
    // приватные методы:

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message,5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    // my new method
    private boolean assertElementHasText(By by, String value, String error_message){
        WebElement element = waitForElementPresent(by,error_message);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.textToBePresentInElementLocated(by,value)
        );
    }

    // ex3 method
    private int assertElementHasChildren(String id, String error_message){
        List<WebElement> listWEs = driver.findElementsById(id);
        int length = listWEs.size();
        return length;
    }

    // ex4 method
    private List<String> getWords(String id, String error_message){
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
}
