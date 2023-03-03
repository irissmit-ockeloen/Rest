package fesma.nl.Export;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordDocument {
    public static String logo = "logo-leaf.png";
    private static final String competence1 = "poi-word-para1.txt";
    private static final String competence2 = "poi-word-para2.txt";
    private static final String competence3 = "poi-word-para3.txt";
    private static final String profile = "poi-word-para4.txt";
    public static String output = "C:\\Users\\DenizvanIerselFesma\\Documents\\rest-with-spring.docx";
    public static String paragraph1;
    public static String paragraph2;
    public static String paragraph3;
    public static String paragraph4;
    XWPFDocument document = new XWPFDocument(); //Create a Document object

    @Test
    public static void main(String[] args) throws Exception {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();

        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        //Specify font formatting
        titleRun.setText("Build Your REST API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("from HTTP fundamentals to API Mastery");
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        Path imagePath = Paths.get(ClassLoader.getSystemResource(logo).toURI());
        imageRun.addPicture(Files.newInputStream(imagePath), XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(), Units.toEMU(50), Units.toEMU(50));

        XWPFParagraph sectionTitle = document.createParagraph();
        XWPFRun sectionTRun = sectionTitle.createRun();
        sectionTRun.setText("What makes a good API?");
        sectionTRun.setColor("00CC44");
        sectionTRun.setBold(true);
        sectionTRun.setFontFamily("Courier");

        //Start the paragraph
        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        String string1 = convertTextFileToString(competence1);
        XWPFRun para1Run = para1.createRun();
        para1Run.setText(string1);

        //Start the paragraph
        XWPFParagraph para2 = document.createParagraph();
        para2.setAlignment(ParagraphAlignment.RIGHT);
        String string2 = convertTextFileToString(competence2);
        XWPFRun para2Run = para2.createRun();
        para2Run.setText(string2);
        para2Run.setItalic(true);

        //Start the paragraph
        XWPFParagraph para3 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.LEFT);
        String string3 = convertTextFileToString(competence3);
        XWPFRun para3Run = para3.createRun();
        para3Run.setText(string3);

        //Start the paragraph
        XWPFParagraph para4 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.LEFT);
        String string4 = convertTextFileToString(profile);
        XWPFRun para4Run = para3.createRun();
        para3Run.setText(string4);


        FileOutputStream out = new FileOutputStream(output);
        document.write(out); //Displaying strings in the output of HTML.
        out.close();
        document.close();
    }

    public static String convertTextFileToString(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            return stream.collect(Collectors.joining(" "));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}