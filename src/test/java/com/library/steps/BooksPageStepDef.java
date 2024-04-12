package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.*;

public class BooksPageStepDef {

    BookPage bookPage = new BookPage();
    List<String> actualCategory;

    String bookName;

    //US 3.
    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String nameOfModule) {
        bookPage.navigateModule(nameOfModule);
    }


    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {

        actualCategory = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategory.remove("ALL");
    }


    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {


        DB_Util.runQuery("select name from book_categories");
        List<String> expectedCategory = DB_Util.getColumnDataAsList("name");

        Assert.assertEquals(actualCategory,expectedCategory);

    }

    //US 4.
    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String book) {

        bookName=book;
        bookPage.search.sendKeys(bookName);
    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {

        bookPage.editBook(bookName);


    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {

        BrowserUtil.waitFor(4);

        String UIbookName = bookPage.bookName.getAttribute("value");
        String UIauthorName = bookPage.author.getAttribute("value");
        String UI_ISBN = bookPage.isbn.getAttribute("value");
        String UIdescription = bookPage.description.getAttribute("value");
        String UI_year = bookPage.year.getAttribute("value");

        Select categoryDropDown = new Select(bookPage.categoryDropdown);
        String UI_category = categoryDropDown.getFirstSelectedOption().getText();

        DB_Util.runQuery("select b.name, b.isbn, b.year, b.author,b.description, bc.name from books b\n" +
                "inner join book_categories bc\n" +
                "on b.book_category_id = bc.id\n" +
                "where b.name='"+ bookName +"'");

        Map<String, String> expectedInfoDB = DB_Util.getRowMap(1);



        Assert.assertEquals(expectedInfoDB.get("b.name"), UIbookName);
        Assert.assertEquals(expectedInfoDB.get("isbn"), UI_ISBN);
        Assert.assertEquals(expectedInfoDB.get("year"), UI_year);
        Assert.assertEquals(expectedInfoDB.get("author"), UIauthorName);
        Assert.assertEquals(expectedInfoDB.get("description"), UIdescription);
        Assert.assertEquals(expectedInfoDB.get("bc.name"), UI_category);


    }


}
