package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    public String generateDate(int days) {
        return LocalDate.now()
                        .plusDays(days)
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldSendFormWithSucceed() {
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
    }

    @Test
    public void shouldSetCityBy2Char() {
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Са");
        $(withText("Санкт-Петербург")).click();
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
    }

    @Test
    public void shouldSet7DaysAfterCurrentDate() {
        String setDate = generateDate(7);
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(setDate);
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
    }
}
