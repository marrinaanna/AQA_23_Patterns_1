package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.Keys;

class DeliveryTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $(By.cssSelector("[data-test-id='city'] input")).setValue(validUser.getCity());
        $(By.cssSelector("[data-test-id='name'] input")).setValue(validUser.getName());
        $(By.cssSelector("[data-test-id='date'] input")).click();
        $(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(firstMeetingDate);
        $(By.cssSelector("[data-test-id='phone'] input")).setValue(validUser.getPhone());
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button_view_extra")).click();
        $(By.cssSelector("[data-test-id='success-notification']")).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        $(By.cssSelector("[data-test-id='date'] input")).click();
        $(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(secondMeetingDate);
        $(By.className("button_view_extra")).click();
        $x("//*[text()='Перепланировать']").click();
        $(By.cssSelector("[data-test-id='success-notification']")).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);

    }
}
