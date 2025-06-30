package com.aos.AOSBE.AIConfigs;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.writer.FileDocumentWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyTika {
        private final Resource resource;

        MyTika(@Value("classpath:/word-sample.docx")
                             Resource resource) {
            this.resource = resource;
        }

        List<Document> loadText() {
            TikaDocumentReader tikaDocumentReader = new TikaDocumentReader( this.resource);
            return tikaDocumentReader.read();
        }

   void writeDocuments(List<Document> documents) {
        FileDocumentWriter writer = new FileDocumentWriter("output.txt", true, MetadataMode.ALL, false);
        writer.accept(documents);
    }
}
