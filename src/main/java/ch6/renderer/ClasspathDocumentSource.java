package ch6.renderer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Artyom Zheltyshev on 10.05.2026
 */
public class ClasspathDocumentSource implements DocumentSource {

    private final String resourceName;
    private final String baseUri;

    public ClasspathDocumentSource(String resourceName, String baseUri) {
        this.resourceName = resourceName;
        this.baseUri = baseUri;
    }

    @Override
    public Document loadDocument() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
            return Jsoup.parse(is, StandardCharsets.UTF_8.name(), baseUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
