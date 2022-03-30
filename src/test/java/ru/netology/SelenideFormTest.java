package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFormTest {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //устанавливаем формат даты

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно")).should(appear, Duration.ofSeconds(15));
    }

//    @Test
//    void shouldAcceptЁ() {
//        $("[data-test-id='city'] input").val("Москва");
//        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
//        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
//        $("[data-test-id='name'] input").val("Семёнов Пётр");
//        $("[data-test-id='phone'] input").val("+79991112233");
//        $("[data-test-id='agreement'] span").click();
//        $(withText("Забронировать")).click();
//        $(withText("Успешно")).should(appear, Duration.ofSeconds(15));
//    }

    @Test
    void shouldAcceptDash() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Петрова-Водкина Анна-Мария");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно")).should(appear, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotSendEmptyByDefault() {
        $(withText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendWithoutDate() {
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldNotSendWithoutName() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendWithoutPhone() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendWithoutAgreement() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+79991112233");
        $(withText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid").should(appear);
    }

//    @Test
//    void shouldNotSendWithoutSurname() {
//        $("[data-test-id='city'] input").val("Москва");
//        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
//        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
//        $("[data-test-id='name'] input").val("Иван");
//        $("[data-test-id='phone'] input").val("+79991112233");
//        $("[data-test-id='agreement'] span").click();
//        $(withText("Забронировать")).click();
//        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
//    }

//    @Test
//    void shouldNotSendWithDashForName() {
//        $("[data-test-id='city'] input").val("Москва");
//        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
//        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
//        $("[data-test-id='name'] input").val("-");
//        $("[data-test-id='phone'] input").val("+79991112233");
//        $("[data-test-id='agreement'] span").click();
//        $(withText("Забронировать")).click();
//        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
//    }

    @Test
    void shouldNotSendCityWithinList() {
        $("[data-test-id='city'] input").val("Королев");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text(
                "Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotAcceptDateInPast() {
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(
                "0", "1", ".", "0", "3", ".", "2", "0", "2", "2");
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text(
                "Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotSendLatinName() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Ivanov Ivan");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text(
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendSpecialSymbolsForName() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("!#&?");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text(
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendNumbersInName() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иван 1");
        $("[data-test-id='phone'] input").val("+79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text(
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendWithoutPlusInPhone() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("79991112233");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text(
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSendLessDigitsInPhone() {
        $("[data-test-id='city'] input").val("Москва");
        calendar.add(Calendar.DAY_OF_MONTH, 4); //добавляем 4 дня к текущей дате
        $("[data-test-id='date'] input").doubleClick().val(dateFormat.format(calendar.getTime()));
        $("[data-test-id='name'] input").val("Иванов Иван");
        $("[data-test-id='phone'] input").val("+7999111222");
        $("[data-test-id='agreement'] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text(
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
