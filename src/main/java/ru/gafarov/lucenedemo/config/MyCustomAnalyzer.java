package ru.gafarov.lucenedemo.config;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class MyCustomAnalyzer extends Analyzer{

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String s) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new EnglishPossessiveFilter(source);
        result = new RussianLightStemFilter(result);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, EnglishAnalyzer.getDefaultStopSet());
        result = new StopFilter(result, RussianAnalyzer.getDefaultStopSet());
        result = new PorterStemFilter(result);
        return new Analyzer.TokenStreamComponents(source, result);
    }
}
