package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

import static java.lang.Thread.*;

public class BooksPageStepDef {

    BookPage bookPage = new BookPage();
    List<String> actualCategory;

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

}
