package ua.knu.periodicals.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XslTransformer {
    private static final Logger logger = LoggerFactory.getLogger(XslTransformer.class);

    public static void transform(File xmlFile, File xslFile, File outputXmlFile) throws Exception {
        logger.info("Початок XSL трансформації файлу '{}'", xmlFile.getName());
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
        transformer.transform(new StreamSource(xmlFile), new StreamResult(outputXmlFile));
        logger.info("Трансформацію завершено. Результат збережено у '{}'", outputXmlFile.getPath());
    }
}