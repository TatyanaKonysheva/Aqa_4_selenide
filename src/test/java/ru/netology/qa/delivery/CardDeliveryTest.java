package ru.netology.qa.delivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }

    @Test
    void shouldCardDeliverySuccess() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Тюмень");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Конышева Татьяна");
        $("[data-test-id=phone] input").setValue("+79199098876");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(12));
        $("[data-test-id='notification'].notification .notification__content").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Встреча успешно забронирована на " + date));
    }
    @Test
    void shouldInvalidCity() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Анапа");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Конышева Татьяна");
        $("[data-test-id=phone] input").setValue("+79199098876");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldNotFilledInCity() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Конышева Татьяна");
        $("[data-test-id=phone] input").setValue("+79199098876");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldInvalidName() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("DDD");
        $("[data-test-id=phone] input").setValue("+79199098876");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldNotFilledInName() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=phone] input").setValue("+79199098876");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldInvalidPhone() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Владимир Владимирович");
        $("[data-test-id=phone] input").setValue("+7919");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldNotFilledInPhone() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Владимир Владимирович");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(5)).should(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldCheckboxIsNotSelected() {
        String date = setLocalDate(10);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Тюмень");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Конышева Татьяна");
        $("[data-test-id=phone] input").setValue("+79199098876");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible).should(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));;
    }
}