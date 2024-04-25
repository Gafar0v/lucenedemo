package ru.gafarov.lucenedemo.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

@Service
public class TikaService {
    private final Tika tika = new Tika();

    public Reader parseContent(InputStream stream) throws IOException {
        return tika.parse(stream);
    }
    public String getStringContent(InputStream stream) throws TikaException, IOException {
        return tika.parseToString(stream);
    }
}
