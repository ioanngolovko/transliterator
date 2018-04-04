package ru.ioann.transliterator.util;

import ru.ioann.transliterator.enums.Language;

import static ru.ioann.transliterator.enums.Language.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Можно было это заливать из какого-нибудь файла в H2, например.
 * Повесить листенер на старт приложения, сделать репу на Entity с тремя-четырьмя полями.
 * Но в рамках задания это избыточно.
 */
public final class Dictionary {

    /**
     * Класс утилитный. Поэтому запрещаем, как наследование, так и инстанциирование.
     */
    private Dictionary() {
        throw new AssertionError();
    }

    private static final Map<Character, String> RU_DICTIONARY ;
    private static final Map<Character, String> UA_DICTIONARY;
    private static final Map<Language, Map<Character, String>> DICTIONARIES = new HashMap<>();

    static {
        Map<Character, String> ruDictionary = new HashMap<>();
        ruDictionary.put('а', "a");
        ruDictionary.put('б', "b");
        ruDictionary.put('в', "v");
        ruDictionary.put('г', "g");
        ruDictionary.put('д', "d");
        ruDictionary.put('е', "e");
        ruDictionary.put('ё', "yo");
        ruDictionary.put('ж', "zh");
        ruDictionary.put('з', "z");
        ruDictionary.put('и', "i");
        ruDictionary.put('й', "j");
        ruDictionary.put('к', "k");
        ruDictionary.put('л', "l");
        ruDictionary.put('м', "m");
        ruDictionary.put('н', "n");
        ruDictionary.put('о', "o");
        ruDictionary.put('п', "p");
        ruDictionary.put('р', "r");
        ruDictionary.put('с', "s");
        ruDictionary.put('т', "t");
        ruDictionary.put('у', "u");
        ruDictionary.put('ф', "f");
        ruDictionary.put('х', "x");
        ruDictionary.put('ц', "cz");
        ruDictionary.put('ч', "ch");
        ruDictionary.put('ш', "sh");
        ruDictionary.put('щ', "shh");
        ruDictionary.put('ъ', "``");
        ruDictionary.put('ы', "y`");
        ruDictionary.put('ь', "`");
        ruDictionary.put('э', "e`");
        ruDictionary.put('ю', "yu");
        ruDictionary.put('я', "ya");


        // Право, мы ведь не собираемся модифицировать это барахло
        // за пределами статического инициализатора.
        RU_DICTIONARY = Collections.unmodifiableMap(ruDictionary);
    }

    static {
        Map<Character, String> uaDictionary = new HashMap<>();

        uaDictionary.put('а', "a");
        uaDictionary.put('б', "b");
        uaDictionary.put('в', "v");
        uaDictionary.put('г', "h*");
        uaDictionary.put('ґ', "g");
        uaDictionary.put('д', "d");
        uaDictionary.put('е', "e");
        uaDictionary.put('є', "ie");
        uaDictionary.put('ж', "zh");
        uaDictionary.put('з', "z");
        uaDictionary.put('и', "y");
        uaDictionary.put('і', "i");
        uaDictionary.put('ї', "i");
        uaDictionary.put('й', "i");
        uaDictionary.put('к', "k");
        uaDictionary.put('л', "l");
        uaDictionary.put('м', "m");
        uaDictionary.put('н', "n");
        uaDictionary.put('о', "o");
        uaDictionary.put('п', "p");
        uaDictionary.put('р', "r");
        uaDictionary.put('с', "s");
        uaDictionary.put('т', "t");
        uaDictionary.put('у', "u");
        uaDictionary.put('ф', "f");
        uaDictionary.put('х', "kh");
        uaDictionary.put('ц', "ts");
        uaDictionary.put('ч', "ch");
        uaDictionary.put('ш', "sh");
        uaDictionary.put('щ', "shch");
        uaDictionary.put('ь', "");
        uaDictionary.put('ю', "iu");
        uaDictionary.put('я', "ia");
        uaDictionary.put('’', "");

        UA_DICTIONARY = Collections.unmodifiableMap(uaDictionary);
    }

    static {
        DICTIONARIES.put(RU, RU_DICTIONARY);
        DICTIONARIES.put(UA, UA_DICTIONARY);
    }

    public static Map<Character, String> getDictionary(Language language) {
        return DICTIONARIES.get(language);
    }
}
