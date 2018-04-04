package ru.ioann.transliterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.ioann.transliterator.enums.Language.*;

import org.springframework.test.web.servlet.ResultActions;
import ru.ioann.transliterator.enums.Language;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TransliteratorApplicationTests {

    private static final String MESSAGE_TEMPLATE = "{\"language\":\"%s\",\n\"text\":\"%s\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLanguagesValidationError() throws Exception {
        this.performPost(Language.EN, "Привет")
                .andExpect(status().is(400))
                .andExpect(
                        content().json(
                                "{\"status\":\"BAD_REQUEST\",\"errorMessage\":\"language:Invalid language\"}"
                        )
                );
    }

    @Test
    public void testTextValidationError() throws Exception {

        StringBuilder sb = new StringBuilder("s"); //length = 1;

        IntStream.range(1, 501).forEach(i -> sb.append("s")); //length = 501

        this.performPost(Language.RU, sb.toString())
                .andExpect(status().is(400))
                .andExpect(
                        content().json(
                                "{" +
                                        "\"status\":\"BAD_REQUEST\",\"" +
                                        "errorMessage\":\"text:The maximum number of characters in text can not be more than 500\"" +
                                        "}"
                        )
                );
    }

    @Test
    public void testLanguagesOnCorrectValidation() {
        String crossLanguageMessage = "123";
        Stream.of(RU, UA)
                .forEach(language -> {
                    try {
                        this.performPost(language, crossLanguageMessage)
                                .andExpect(status().is(200))
                                .andExpect(
                                        content().json(String.format(MESSAGE_TEMPLATE, EN, crossLanguageMessage))
                                );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    @Test
    public void testTransliteration() throws Exception {
        this.performPost(RU, "привет")
                .andExpect(status().is(200))
                .andExpect(
                        content().json(String.format(MESSAGE_TEMPLATE, EN, "privet"))
                );
    }

    private ResultActions performPost(Language language, String message) throws Exception {
        return this.mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(MESSAGE_TEMPLATE, language, message))
        );
    }
}
