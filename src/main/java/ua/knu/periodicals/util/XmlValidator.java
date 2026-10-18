package ua.knu.periodicals.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XmlValidator {
    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public static boolean validate(File xmlFile, File xsdFile) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            logger.info("XML-документ '{}' успішно пройшов валідацію за XSD '{}'", xmlFile.getName(), xsdFile.getName());
            return true;
        } catch (Exception e) {
            logger.error("Помилка валідації XML: {}", e.getMessage());
            return false;
        }
    }
}