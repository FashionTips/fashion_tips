package com.bionicuniversity.edu.fashiontips.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copy-Paste from Twiter
 * {@link}: https://github.com/twitter/twitter-text/blob/master/java/src/com/twitter/Regex.java
 */
public final class Regex {
    private static final String HASHTAG_LETTERS = "\\p{L}\\p{M}";
    private static final String HASHTAG_NUMERALS = "\\p{Nd}";
    private static final String HASHTAG_SPECIAL_CHARS = "_" + //underscore
            "\\u200c" + // ZERO WIDTH NON-JOINER (ZWNJ)
            "\\u200d" + // ZERO WIDTH JOINER (ZWJ)
            "\\ua67e" + // CYRILLIC KAVYKA
            "\\u05be" + // HEBREW PUNCTUATION MAQAF
            "\\u05f3" + // HEBREW PUNCTUATION GERESH
            "\\u05f4" + // HEBREW PUNCTUATION GERSHAYIM
            "\\u309b" + // KATAKANA-HIRAGANA VOICED SOUND MARK
            "\\u309c" + // KATAKANA-HIRAGANA SEMI-VOICED SOUND MARK
            "\\u30a0" + // KATAKANA-HIRAGANA DOUBLE HYPHEN
            "\\u30fb" + // KATAKANA MIDDLE DOT
            "\\u3003" + // DITTO MARK
            "\\u0f0b" + // TIBETAN MARK INTERSYLLABIC TSHEG
            "\\u0f0c" + // TIBETAN MARK DELIMITER TSHEG BSTAR
            "\\u00b7";  // MIDDLE DOT
    private static final String HASHTAG_LETTERS_NUMERALS = HASHTAG_LETTERS + HASHTAG_NUMERALS + HASHTAG_SPECIAL_CHARS;
    private static final String HASHTAG_LETTERS_SET = "[" + HASHTAG_LETTERS + "]";
    private static final String HASHTAG_LETTERS_NUMERALS_SET = "[" + HASHTAG_LETTERS_NUMERALS + "]";

    public static final Pattern VALID_HASHTAG = Pattern.compile("(^|[^&" + HASHTAG_LETTERS_NUMERALS + "])(#|\uFF03)(?!\uFE0F|\u20E3)(" + HASHTAG_LETTERS_NUMERALS_SET + "*" + HASHTAG_LETTERS_SET + HASHTAG_LETTERS_NUMERALS_SET + "*)", Pattern.CASE_INSENSITIVE);

    public static List<String> parseHashTags(String text) {
        List<String> hashTagsNames = new ArrayList<>();
        Matcher matcher = VALID_HASHTAG.matcher(text);
        while(matcher.find()){
            hashTagsNames.add(matcher.group().trim());
        }
        return hashTagsNames;
    }
}