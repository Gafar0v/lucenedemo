package ru.gafarov.lucenedemo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.StringReader;

@SpringBootApplication
public class LucenedemoApplication {
    

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LucenedemoApplication.class, args);
    }

    /*public static void displayTokens(Analyzer analyzer, String text) throws IOException {
        displayTokens(analyzer.tokenStream("contents", new StringReader(text)));
    }

    public static void displayTokens(TokenStream stream) throws IOException {
        CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        while (stream.incrementToken()) {
            System.out.print("[" + term.toString() + "] ");
        }
        stream.end();
        stream.close();
    }*/

}
