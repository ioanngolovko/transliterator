package ru.ioann.transliterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import ru.ioann.transliterator.service.TransliteratorService;
import ru.ioann.transliterator.web.dto.MessageDto;

import ru.ioann.transliterator.enums.Language;

import javax.validation.Valid;


@RestController
@SpringBootApplication
public class TransliteratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransliteratorApplication.class, args);
    }

    private TransliteratorService transliteratorService;

    public TransliteratorApplication(TransliteratorService transliteratorService) {
        this.transliteratorService = transliteratorService;
    }

    /**
     * Можно задокументировать сваггером, но не буду =)
     * @param dto
     * @return
     */
    @PostMapping(value = "/", produces = {"application/json"})
    public MessageDto doTransliteration(
            @Valid @RequestBody MessageDto dto
    ) {
        //Никогда не передавайте dto в сервисный слой.
        dto.text = transliteratorService.transliterate(dto);
        dto.language = Language.EN;
        return dto;
    }



}
