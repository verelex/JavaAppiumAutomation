import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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

    @Test
    public void testSwipeArticle() /* swipe test */
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' title in search",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        /*swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);*/
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of article",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList(){ // так и не заработал
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
                By.xpath("//android.widget.ImageView[@content-desc='More options']"), // work OK!
                "Cannot find button to open article options",
                5);

        /*waitForElementAndClick(////////////////// ERROR
                By.id("org.wikipedia:id/title"),
                //By.xpath("//android.widget.TextView[@content-desc='Add to reading list']"),
                //By.xpath("//*[@text='Add to reading list']"), // ERROR: tap on change language !
                "Cannot find option to add article to reading list",
                5);*/

        List<WebElement> listWEs = driver.findElementsById("org.wikipedia:id/title");
        int size = listWEs.size();
        for (int idx=0; idx< size; idx++){
            WebElement we = listWEs.get(idx);
            if ( we.getText().contains("Add to reading list") ){
                we.click(); // dbg OK!
            }
        }

        WebElement webE = driver.findElementById("org.wikipedia:id/onboarding_button"); // NOT FOUND!
        if (webE.getText().contains("GOT IT"))
        {
            System.out.println(webE.getText());
            webE.click();
        }
        /*waitForElementAndClick( //// ERROR not found!
                By.xpath("//android.widget.TextView[@text='GOT IT']"),
                //By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                30);*/

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into article folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find created folder",
                5);

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch(){ // убеждаемся, что по запросу хотя бы что-то найдено
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        String search_line = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );
        //                                  parent                                                 child
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "cannot find anything by the request " +search_line,
                15);

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch(){ // убеждаемся, что по запросу ничего не найдено
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        String search_line = "zxcvasdfqwer";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "cannot find empty result label by the reqest "+ search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "we found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationSearchResults(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " +search_line,
                15);

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                15);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                15);

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                15);

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground(){
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

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returned from background",
                5
        );
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

    protected void swipeUp(int timeOfSwipe){
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

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
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

    protected void swipeElementToLeft(By by, String error_message){
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

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
