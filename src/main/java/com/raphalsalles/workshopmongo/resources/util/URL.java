package com.raphalsalles.workshopmongo.resources.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class URL {

    public static String decodeParam(String text){
        return text == null ? null: URLDecoder.decode(text, StandardCharsets.UTF_8);
    }
}
