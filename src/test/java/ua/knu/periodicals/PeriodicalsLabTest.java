package ua.knu.periodicals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ua.knu.periodicals.model.*;
import ua.knu.periodicals.parser.*;
import ua.knu.periodicals.util.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicalsLabTest {

    private File xmlFile;
    private File xsdFile;
    private File xslFile;

    @BeforeEach
    void setUp() {
        xmlFile = new File("src/main/resources/papers.xml");
        xsdFile = new File("src/main/resources/papers.xsd");
        xslFile = new File("src/main/resources/transform.xsl");
    }

    @Test
    void shouldValidateValidXmlSuccessfully() {
        assertTrue(XmlValidator.validate(xmlFile, xsdFile), "Валідний XML має проходити перевірку XSD");
    }

    @Test
    void allThreeParsersShouldReturnIdenticalLists() throws Exception {
        List<Periodical> domList = new DomPeriodicalParser().parse(xmlFile);
        List<Periodical> saxList = new SaxPeriodicalParser().parse(xmlFile);
        List<Periodical> staxList = new StaxPeriodicalParser().parse(xmlFile);

        assertEquals(4, domList.size());
        assertEquals(domList, saxList, "Результати DOM та SAX мають збігатися");
        assertEquals(domList, staxList, "Результати DOM та StAX мають збігатися");
    }

    @Test
    void shouldSortPeriodicalsByVolumeAndTitle() throws Exception {
        List<Periodical> list = new DomPeriodicalParser().parse(xmlFile);

        list.sort(PeriodicalComparators.byVolume());
        assertEquals(8, list.get(0).getChars().getVolume(), "Найменший обсяг має бути першим (8 стор.)");
        assertEquals(200, list.get(3).getChars().getVolume(), "Найбільший обсяг має бути останнім (200 стор.)");

        list.sort(PeriodicalComparators.byTitle());
        assertEquals("Forbes Ukraine", list.get(0).getTitle());
    }

    @Test
    void shouldThrowExceptionWhenNewspaperIsGlossy() {
        Chars invalidChars = new Chars(false, 16, true, "12345");
        assertThrows(IllegalArgumentException.class, () ->
                new Periodical("p99", "Газета не може бути глянцевою", PeriodicalType.NEWSPAPER, false, invalidChars)
        );
    }

    @Test
    void shouldThrowExceptionWhenBookletHasSubscriptionIndex() {
        Chars invalidChars = new Chars(true, 10, true, "12345");
        assertThrows(IllegalArgumentException.class, () ->
                new Periodical("p100", "Буклет не може мати підписного індексу", PeriodicalType.BOOKLET, false, invalidChars)
        );
    }

    @Test
    void shouldTransformXmlUsingXsl(@TempDir Path tempDir) throws Exception {
        File outputFile = tempDir.resolve("output.xml").toFile();
        XslTransformer.transform(xmlFile, xslFile, outputFile);

        assertTrue(outputFile.exists(), "Після XSL трансформації має бути створений вихідний файл");
        assertTrue(outputFile.length() > 0, "Вихідний файл не повинен бути порожнім");
    }
}