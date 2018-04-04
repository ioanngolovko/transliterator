package ru.ioann.transliterator.service;

import ru.ioann.transliterator.web.dto.MessageDto;

public interface TransliteratorService {

    /**
     * Execute transliterating
     * @param message
     * @return
     */
    String transliterate(MessageDto message);

}
