import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;


public class FirstTest extends CoreTestCase {

    @Test
    public void testSearch() /* в приложении wikipedia.apk находим поле ввода с надписью 'Search wikipedia' и кликаем на нем
     появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Java'. Далее происходит поиск и
      мы определяем есть ли в найденном строка oop lang */
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() /* находим поле ввода с надписью 'Search Wikipedia' по его id и кликаем на нем
    появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Java'. Далее происходит поиск
    находим поле ввода по id и стираем ранее введеный запрос 'Java'
    ищем кнопку закрытия по id и нажимаем ее
    ещё раз ищем кнопку закрытия и убеждаемся, что её нет. */
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        //SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    //my new test
    @Test
    public void testElementHasText() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
    }

    @Test
    public void testCancelSearchEx3()
        /* находим поле ввода с надписью 'Search Wikipedia' по его id и кликаем на нем
    появляется поле ввода с надписью 'Search...'. Находим его и посылаем в него текст 'Kotlin'. Далее происходит поиск
    Убеждаемся, что найдено несколько статей
    ищем кнопку закрытия по id и нажимаем ее
    ещё раз ищем кнопку закрытия и убеждаемся, что её нет, тем самым убеждаемся, что результат поиска пропал */ {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.makeMeHappy1();

        /*MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Kotlin",
                "Cannot find search input",
                5
        );
        ////
        System.out.println("Count of elements is " +
                MainPageObject.assertElementHasChildren(
                        "org.wikipedia:id/page_list_item_container",
                        "Cannot find list of search results (Ex3)"
                )
        );
        ////
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );*/
    }

    /*@Test
    public void testCheckWordInSearchResultsEx4() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "JAVA",
                "Cannot find search input",
                5
        );

        List<String> LS = MainPageObject.getWords(
                "org.wikipedia:id/page_list_item_title",
                "Cannot find search elements (Ex4)"
        );

        for (String s : LS) {
            System.out.println(s);
            assertTrue("item don't have word 'Java'", s.contains("Java"));
        }
    }*/

    @Test
    public void testSwipeArticle() /* swipe test */ {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() { // NOT WORK! --------------- не работает!
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(name_of_folder);
        MyListPageObject.swipeByArticleToDelete(article_title);

        //MainPageObject.saveFirstArticleToMyListEx();
    }

    @Test
    public void testAmountOfNotEmptySearch() { // убеждаемся, что по запросу хотя бы что-то найдено

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() { // убеждаемся, что по запросу ничего не найдено

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNotResultOfSearch();
    }

    @Test
    public void testChangeScreenOrientationSearchResults() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();

        this.rotateScreenLandscape();

        String title_after_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);

        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testAssertElementPresentEx6() { // Ex6 test
        /* тест, который открывает статью и убеждается, что у нее есть элемент title.
        Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
        Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent */

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.makeMeHappy3();

        /*MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot search 'Search Wikipedia' input",
                5
        );

        String search_line = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "org.wikipedia:id/title";

        MainPageObject.assertElementPresent(
                By.id(search_result_locator),
                "cannot find some results by request " + search_line
        );*/
    }

    @Test
    public void testSaveTwoArticlesToMyListEx5() { // Ex5 test
        /* тест, который:
        1. Сохраняет две статьи в одну папку
        2. Удаляет одну из статей
        3. Убеждается, что вторая осталась
        4. Переходит в неё и убеждается, что title совпадает */

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.makeMeHappy2();

        // сначала добавляем две статьи в папку
        /*MainPageObject.saveFirstArticleToMyListEx();
        MainPageObject.saveSecondArticleToMyList();

        // затем одну удаляем
        MainPageObject.waitForElementAndClick( // найти и кликнуть на раздел 'my lists'
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                15);

        MainPageObject.waitForElementAndClick( // найти и кликнуть на созданную ранее папку 'Learning programming'
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find created folder",
                15);

        MainPageObject.swipeElementToLeft( // свайпнуть влево тем самым удалив статью
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent( // убедиться, что статьи нет
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                15
        );*/
    }
}
