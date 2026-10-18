package ua.knu.periodicals.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.knu.periodicals.model.Periodical;
import ua.knu.periodicals.parser.*;
import ua.knu.periodicals.util.*;

import java.io.File;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/main/resources/papers.xml");
            File xsdFile = new File("src/main/resources/papers.xsd");
            File xslFile = new File("src/main/resources/transform.xsl");
            File outputXml = new File("src/main/resources/papers_grouped.xml");

            // валідація XML за XSD
            boolean isValid = XmlValidator.validate(xmlFile, xsdFile);
            if (!isValid) {
                logger.warn("Програму зупинено через невалідний XML.");
                return;
            }

            // розбір парсерами DOM, SAX, StAX
            PeriodicalParser domParser = new DomPeriodicalParser();
            PeriodicalParser saxParser = new SaxPeriodicalParser();
            PeriodicalParser staxParser = new StaxPeriodicalParser();

            List<Periodical> listFromDom = domParser.parse(xmlFile);
            saxParser.parse(xmlFile);
            staxParser.parse(xmlFile);

            // сортування через Comparator
            logger.info("--- Сортування видань за обсягом сторінок ---");
            listFromDom.sort(PeriodicalComparators.byVolume());
            listFromDom.forEach(p -> logger.info(p.toString()));

            // XSL-перетворення
            XslTransformer.transform(xmlFile, xslFile, outputXml);

        } catch (Exception e) {
            logger.error("Критична помилка під час виконання програми", e);
        }
    }
}