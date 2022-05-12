package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    @Test
    public void shouldValidValue() {
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='name']").setValue("Иван Петров-Иванов");
        $("[name='phone']").setValue("+79119999999");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).should(visible);
    }
}
