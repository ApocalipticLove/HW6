package org.example;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestSource {

    @BeforeEach
    void setUp() {
        open("https://www.ebay.com/");
    }

    @ValueSource(strings = {
            "Home",
            "saved",
            "Electronics",
            "Motors", "Fashion",
            "Collectibles and Art",
            "Sports",
            "Health & Beauty"})
    @ParameterizedTest(name = "Проверка наличия кнопки {0} в меню Ebay")
    void ebayTitleTest(String button) {
        $(".hl-cat-nav").shouldHave(text(button));
    }

    @CsvSource({
            "Fashion, Clothing, Shoes & Accessories",
            "Health & Beauty, Health & Beauty"})
    @ParameterizedTest(name = "Проверка что кнопка {0} открывает страницу с текстом {1}")
    void ebayHeaderTest(String button, String expectedText) {
        $(".hl-cat-nav").$(byText(button)).click();
        $(".b-pageheader__text").shouldHave(text(expectedText));
    }

    static Stream<Arguments> ebayCatTest() {
        return Stream.of(
                Arguments.of("Electronics", List.of("Cameras & Photo", "Cell Phones & Accessories",
                        "Computers/Tablets & Networking", "Home Surveillance", "Major Appliances",
                        "Portable Audio & Headphones", "Surveillance & Smart Home Electronics", "TV, Video & Home Audio",
                        "Vehicle Electronics & GPS", "Video Games & Consoles", "Virtual Reality")),
                Arguments.of("Fashion", List.of("Women", "Men", "Kids", "Baby", "Specialty","Jewelry & Watches"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка списка категорий {1} в меню {0}")
    void ebayCatTest(String header,List<String> category) {
        $(".hl-cat-nav").$(byText(header)).click();
        $$(".b-accordion-text").shouldHave(CollectionCondition.texts(category));
    }
}