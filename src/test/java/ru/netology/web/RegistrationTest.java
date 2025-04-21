package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBookAnAppoinmentSetValue() {
        String planningDate = generateDate(4, "dd.MM.yyyy");

        open("http://localhost:9999");
        SelenideElement formElement = $("form");
        formElement.$("[data-test-id='city'] input").setValue("Екатеринбург");
        formElement.$("[data-test-id='date'] input.input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        formElement.$("[data-test-id='date'] input.input__control").setValue(planningDate);
        formElement.$("[data-test-id='name'] input").setValue("Иванов Иван");
        formElement.$("[data-test-id='phone'] input").setValue("+79600000000");
        formElement.$("[data-test-id='agreement']").click();
        formElement.$(".button").click();
        $("[data-test-id='notification'] .notification__content").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldBookAnAppoinmentPopupMenu() {
        String planningDate = generateDate(7, "d");

        open("http://localhost:9999");
        SelenideElement formElement = $("form");
        formElement.$("[data-test-id='city'] input").setValue("Ек");
        $(Selectors.withText("Екатеринбург")).click();
        formElement.$(".icon-button").click();
        $(Selectors.withText(planningDate)).click();
        formElement.$("[data-test-id='name'] input").setValue("Иванов Иван");
        formElement.$("[data-test-id='phone'] input").setValue("+79600000000");
        formElement.$("[data-test-id='agreement']").click();
        formElement.$(".button").click();
        $("[data-test-id='notification'] .notification__content").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}

