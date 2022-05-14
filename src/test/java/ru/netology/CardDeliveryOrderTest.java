package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {
    private LocalDate today = LocalDate.now();
    private int days = 7;
    private LocalDate dDay = LocalDate.now()
                                      .plusDays(days);
    private int onlyDate = dDay.getDayOfMonth();
    String onDaysDelivery = LocalDate.now()
                                     .plusDays(days)
                                     .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));


    public boolean isMonthNotOk() {

        if (today.getMonth() != dDay.getMonth()) {
            return true;
        } else {
            return false;
        }
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
    public void shouldSetCityBy2Char() throws InterruptedException {
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").findBy(text("Санкт-Петербург"))
                                 .click();
        $(".icon_name_calendar").click();
        if (isMonthNotOk()) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(String.valueOf(onlyDate)))
                            .click();
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + onDaysDelivery), Duration.ofSeconds(15));
    }

    @Test
    public void shouldSet7DaysAfterCurrentDate() {
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(onDaysDelivery);
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + onDaysDelivery), Duration.ofSeconds(15));
    }
}
