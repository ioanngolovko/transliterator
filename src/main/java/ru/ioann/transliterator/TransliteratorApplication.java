package ru.ioann.transliterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import ru.ioann.transliterator.web.dto.MessageDto;

import ru.ioann.transliterator.enums.Language;

import javax.validation.Valid;


@RestController
@SpringBootApplication
public class TransliteratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransliteratorApplication.class, args);
    }


    @PostMapping(value = "/", produces = {"application/json"})
    public MessageDto doTransliteration(
            @Valid @RequestBody MessageDto dto
    ) {
        dto.language = Language.EN;
        return dto;
    }



}
