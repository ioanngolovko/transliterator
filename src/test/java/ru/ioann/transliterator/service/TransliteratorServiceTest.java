package ru.ioann.transliterator.service;

import static org.junit.Assert.*;
import org.junit.Test;


import static ru.ioann.transliterator.enums.Language.*;

import ru.ioann.transliterator.service.impl.TransliteratorServiceImpl;
import ru.ioann.transliterator.web.dto.MessageDto;

public class TransliteratorServiceTest {

    //Зачем тянуть контекст спринга, пусть даже урезанный, когда можно протестить реализацию.
    protected TransliteratorService transliteratorService = new TransliteratorServiceImpl();

    @Test
    public void testRu() {
        MessageDto dto = new MessageDto();

        dto.language = RU;
        dto.text = "Тестовое задание, ЧАЯ хочУЯ. Как-ЩЯ тАК.ачепятка";


        assertEquals(
                "Testovoe zadanie, CHAYA xochUYA. Kak-SHHYA tAK.achepyatka",
                this.transliteratorService.transliterate(dto)
        );
    }

    @Test
    public void testUa() {
        MessageDto dto = new MessageDto();

        dto.language = UA;
        //Украинской раскладки нет, так что копипастом
        dto.text = "щ ЩЩЩ щщщ шя яЯ Яя ’’’’’ є і ї ЄЇ п";


        assertEquals(
                "shch SHCHSHCHSHCH shchshchshch shia iaIa Iaia  ie i i IEI p",
                this.transliteratorService.transliterate(dto)
        );
    }

    @Test
    public void testNotLetterInjection() {
        MessageDto dto = new MessageDto();

        dto.language = RU;
        dto.text = "Цифры 1212 странные !@#$%^&*(";


        assertEquals(
                "Czifry` 1212 stranny`e !@#$%^&*(",
                this.transliteratorService.transliterate(dto)
        );
    }

    @Test
    public void testOtherLanguageInjection() {
        MessageDto dto = new MessageDto();

        dto.language = RU;
        dto.text = "Ходил на тест драйв Dodge Charger SRT. Вещь!!!";


        assertEquals(
                "Xodil na test drajv Dodge Charger SRT. Veshh`!!!",
                this.transliteratorService.transliterate(dto)
        );
    }

    @Test
    public void testUpperCase() {
        MessageDto dto = new MessageDto();

        dto.language = RU;
        dto.text = "я Я яЯ Яя яЯЯя ЯяяЯ ЯЯ2 ЯЯя ЯЯ";


        assertEquals(
                "ya Ya yaYa Yaya yaYAYaya YayayaYa YAYA2 YAYaya YAYA",
                this.transliteratorService.transliterate(dto)
        );
    }
}
