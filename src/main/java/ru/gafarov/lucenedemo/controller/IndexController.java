package ru.gafarov.lucenedemo.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gafarov.lucenedemo.service.IndexService;
import ru.gafarov.lucenedemo.service.TikaService;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @PostMapping("/upload")
    public void addToIndex(@RequestParam("file") MultipartFile file) throws IOException {
        indexService.index(file);
    }
    @PostMapping("/find")
    public void findFiles(@RequestParam("query") String query) throws IOException, ParseException {
        System.out.println(indexService.search(query).stream().map(d->d.get("filename")).toList());
    }
}
