package ua.knu.periodicals.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ua.knu.periodicals.model.Periodical;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxPeriodicalParser implements PeriodicalParser {
    private static final Logger logger = LoggerFactory.getLogger(SaxPeriodicalParser.class);

    @Override
    public List<Periodical> parse(File xmlFile) throws Exception {
        logger.info("Початок розбору XML за допомогою SAX парсера: {}", xmlFile.getName());
        List<Periodical> periodicals = new ArrayList<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            private Periodical currentPeriodical;
            private StringBuilder currentText = new StringBuilder();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                currentText.setLength(0);
                if ("Periodical".equals(qName)) {
                    currentPeriodical = new Periodical();
                    currentPeriodical.setId(attributes.getValue("id"));
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                currentText.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if ("Periodical".equals(qName)) {
                    currentPeriodical.validateBusinessRules();
                    periodicals.add(currentPeriodical);
                } else if (currentPeriodical != null) {
                    XmlTagPopulator.populateField(currentPeriodical, qName, currentText.toString());
                }
            }
        };

        saxParser.parse(xmlFile, handler);
        logger.info("SAX парсер успішно розібрав {} об'єктів", periodicals.size());
        return periodicals;
    }
}