package ru.gafarov.lucenedemo.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gafarov.lucenedemo.config.MyCustomAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    @Value("${index.path}")
    private String pathToIndex;
    @Autowired
    private IndexWriter indexWriter;
    @Autowired
    private TikaService tikaService;
    private Analyzer analyzer = new MyCustomAnalyzer();

    public void index(MultipartFile multipartFile) throws IOException {
        indexFile(indexWriter, multipartFile);
    }

    private int indexFile(IndexWriter writer, MultipartFile path) {
        try {
            Document doc = getDocument(path);
            writer.addDocument(doc);
            return writer.getDocStats().numDocs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Document getDocument(MultipartFile multipartFile) throws Exception {
        Document doc = new Document();
        doc.add(new TextField("contents", tikaService.parseContent(multipartFile.getInputStream())));
        doc.add(new StringField("filename", multipartFile.getOriginalFilename(), Field.Store.YES));
        return doc;
    }

    public List<Document> search(String stringQuery) throws IOException, ParseException {
        Query query = new QueryParser("contents", analyzer).parse(stringQuery);
        IndexReader indexReader = DirectoryReader.open(indexWriter);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, 10);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs)
            documents.add(searcher.doc(scoreDoc.doc));

        return documents;
    }
}
