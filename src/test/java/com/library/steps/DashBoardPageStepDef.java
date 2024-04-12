package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashBoardPageStepDef {

    LoginPage login=new LoginPage();
    DashBoardPage dashBoard = new DashBoardPage();

    String actualResultfromUI;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String userName) {

        login.login(userName);

    }


    @When("the librarian gets borrowed books number")
    public void the_librarian_gets_borrowed_books_number() {

         actualResultfromUI = dashBoard.borrowedBooksNumber.getText();

    }


    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {

        DB_Util.runQuery("select count(*) from book_borrow\n" +
                "    where is_returned = 0");
        String expectedResultfromDB = DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals(actualResultfromUI,expectedResultfromDB);

    }




}
