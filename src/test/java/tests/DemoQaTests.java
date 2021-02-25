package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class DemoQaTests extends TestBase {


    @Test
    void SubmitForm() {

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String gender = "Other";
        String mobile = faker.phoneNumber().subscriberNumber(10);
        LocalDate birthday = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String subjects = "Maths";
        String hobby = "Reading";
        String pictureFile = "test.png";
        String address = faker.address().fullAddress();
        String state = "Haryana";
        String city = "Panipat";

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
        });

        step("Fill the form", () -> {
            $("#firstName").setValue(firstName);

            $("#lastName").setValue(lastName);

            $("#userEmail").setValue(email);

            $("input[name=gender][value=" + gender + "] ~ label").click();

            $("#userNumber").setValue(mobile);

            $("#dateOfBirthInput").sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $("#dateOfBirthInput").sendKeys(birthday.format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
            $("#dateOfBirthInput").pressEnter();

            $("#subjectsInput").setValue(subjects).pressEnter();

            $x("//label[contains(text(), '" + hobby + "')]").click();

            $("#uploadPicture").uploadFromClasspath(pictureFile);

            $("#currentAddress").setValue(address);

            $("#state input").setValue(state).pressEnter();

            $("#city input").setValue(city).pressEnter();
        });

        step("Click submit", () -> {
            $("#submit").click();
        });

        // Assert
        step("Verify form is submitted", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        });

        step("Verify submitted data", () -> {
            $x("//td[text()='Student Name']").sibling(0).shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").sibling(0).shouldHave(text(email));
            $x("//td[text()='Gender']").sibling(0).shouldHave(text(gender));
            $x("//td[text()='Mobile']").sibling(0).shouldHave(text(mobile));
//            $x("//td[text()='Date of Birth']").sibling(0).shouldHave(text(birthday.format(DateTimeFormatter.ofPattern("dd LLLL,yyyy"))));
            $x("//td[text()='Subjects']").sibling(0).shouldHave(text(subjects));
            $x("//td[text()='Hobbies']").sibling(0).shouldHave(text(hobby));
            $x("//td[text()='Picture']").sibling(0).shouldHave(text(pictureFile));
            $x("//td[text()='Address']").sibling(0).shouldHave(text(address));
            $x("//td[text()='State and City']").sibling(0).shouldHave(text(state + " " + city));
        });
    }

    @Test
    void SubmitFailedForm() {

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
        });

        step("Click submit", () -> {
            $("#submit").click();
        });

        // Assert
        step("Verify form is submitted", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        });
    }

}