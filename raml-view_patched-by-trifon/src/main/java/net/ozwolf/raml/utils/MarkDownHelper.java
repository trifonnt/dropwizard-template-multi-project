package net.ozwolf.raml.utils;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

public class MarkDownHelper {
    public static String fromMarkDown(String source){
        if (source == null) return null;
        try {
            return new Markdown4jProcessor().process(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
