package ru.gafarov.lucenedemo.config;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {
    @Value("${index.path}")
    private String pathToIndex;

    @Bean
    public IndexWriterConfig indexWriterConfig() {
        Analyzer analyzer = new MyCustomAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return config;
    }
    @Bean
    public Directory directory() throws IOException {
        return FSDirectory.open(Paths.get(pathToIndex));
    }
    @Bean
    public IndexWriter indexWriter(Directory directory, IndexWriterConfig indexWriterConfig) throws IOException {
        return new IndexWriter(directory, indexWriterConfig);
    }

}
