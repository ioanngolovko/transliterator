package ru.ioann.transliterator.service.impl;

import org.springframework.stereotype.Service;
import ru.ioann.transliterator.service.TransliteratorService;
import ru.ioann.transliterator.util.Dictionary;
import ru.ioann.transliterator.web.dto.MessageDto;

import java.util.Map;

/**
 * Вроде как транслитерацию можно реализовать при помощи вот этого класса
 * http://icu-project.org/apiref/icu4j/com/ibm/icu/text/Transliterator.html
 * <p>
 * Но мы ведь не ищем легких путей?)
 */
@Service
public final class TransliteratorServiceImpl implements TransliteratorService {

    @Override
    public String transliterate(MessageDto message) {
        Map<Character, String> dictionary = Dictionary.getDictionary(message.language);

        StringBuilder sb = new StringBuilder();

        String text = message.text;

        boolean prevIsUpperCase = false;

        for (int i = 0; i < text.length(); ++i) {
            Character ch = text.charAt(i);

            String transliterated = dictionary.getOrDefault(ch, ch.toString());

            if (Character.isLowerCase(ch)) {
                prevIsUpperCase = false;
            } else if (Character.isUpperCase(ch)) {
                transliterated = dictionary.getOrDefault(Character.toLowerCase(ch), ch.toString());

                //Лень было заносить в словарь прописные буквы. Так что напишем пару лишних строчек.
                if (!ch.equals(transliterated)) {
                    //Символ из словаря
                    if (transliterated.length() == 1)
                        //Результат транслита длиной в один символ в дальнейшей обработке не нуждается.
                        transliterated = transliterated.toString().toUpperCase();
                    else {
                    /*
                      Нужно обратить внимание на то, что "Ч = Ch", но :
                       1. "чЧЧ = chCHCH";
                       2. "ЧЧч = CHChch;

                      Выходит, что условием "полностью" прописного транслита являются варианты,когда:
                      1. справа прописной символ;
                      2. слева прописной и справа небуквенный символ или нет символов (конец строки)
                    */

                        final boolean isFullUpperCase;

                        if (i + 1 == text.length())
                            isFullUpperCase = prevIsUpperCase; //конец строки
                        else {
                            Character nextChar = text.charAt(i + 1);

                            isFullUpperCase = (prevIsUpperCase && Character.getType(nextChar) != Character.LOWERCASE_LETTER)
                                    || Character.isUpperCase(nextChar);
                        }

                        if (isFullUpperCase)
                            transliterated = transliterated.toUpperCase();
                        else
                            transliterated = Character.toUpperCase(transliterated.charAt(0)) + transliterated.substring(1);

                    }
                    prevIsUpperCase = true;
                }
            } else {
                //Для иных типов general category
                prevIsUpperCase = false;
            }

            sb.append(transliterated);
        }

        return sb.toString();
    }
}
