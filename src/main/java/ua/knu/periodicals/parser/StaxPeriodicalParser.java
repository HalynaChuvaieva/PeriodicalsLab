package ua.knu.periodicals.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.knu.periodicals.model.Periodical;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxPeriodicalParser implements PeriodicalParser {
    private static final Logger logger = LoggerFactory.getLogger(StaxPeriodicalParser.class);

    @Override
    public List<Periodical> parse(File xmlFile) throws Exception {
        logger.info("Початок розбору XML за допомогою StAX парсера: {}", xmlFile.getName());
        List<Periodical> periodicals = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (FileInputStream fis = new FileInputStream(xmlFile)) {
            XMLEventReader reader = factory.createXMLEventReader(fis);
            Periodical currentPeriodical = null;
            String currentTag = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    currentTag = startElement.getName().getLocalPart();
                    if ("Periodical".equals(currentTag)) {
                        currentPeriodical = new Periodical();
                        var idAttr = startElement.getAttributeByName(new QName("id"));
                        if (idAttr != null) {
                            currentPeriodical.setId(idAttr.getValue());
                        }
                    }
                } else if (event.isCharacters() && currentTag != null && currentPeriodical != null) {
                    String data = event.asCharacters().getData();
                    if (!data.isBlank()) {
                        XmlTagPopulator.populateField(currentPeriodical, currentTag, data);
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if ("Periodical".equals(endElement.getName().getLocalPart()) && currentPeriodical != null) {
                        currentPeriodical.validateBusinessRules();
                        periodicals.add(currentPeriodical);
                        currentPeriodical = null;
                    }
                    currentTag = null;
                }
            }
        }
        logger.info("StAX парсер успішно розібрав {} об'єктів", periodicals.size());
        return periodicals;
    }
}