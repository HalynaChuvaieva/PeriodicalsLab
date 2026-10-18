package ua.knu.periodicals.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import ua.knu.periodicals.model.Periodical;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomPeriodicalParser implements PeriodicalParser {
    private static final Logger logger = LoggerFactory.getLogger(DomPeriodicalParser.class);

    @Override
    public List<Periodical> parse(File xmlFile) throws Exception {
        logger.info("Початок розбору XML за допомогою DOM парсера: {}", xmlFile.getName());
        List<Periodical> periodicals = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("Periodical");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            Periodical periodical = new Periodical();
            periodical.setId(element.getAttribute("id"));

            parseChildNodes(element.getChildNodes(), periodical);
            periodical.validateBusinessRules();
            periodicals.add(periodical);
        }
        logger.info("DOM парсер успішно розібрав {} об'єктів", periodicals.size());
        return periodicals;
    }

    private void parseChildNodes(NodeList children, Periodical periodical) {
        for (int j = 0; j < children.getLength(); j++) {
            Node node = children.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if ("Chars".equals(node.getNodeName())) {
                    parseChildNodes(node.getChildNodes(), periodical);
                } else {
                    XmlTagPopulator.populateField(periodical, node.getNodeName(), node.getTextContent());
                }
            }
        }
    }
}