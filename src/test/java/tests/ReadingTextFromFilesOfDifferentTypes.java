package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.xlstest.XLS;
import io.qameta.allure.selenide.AllureSelenide;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Test;
import utils.Files;

import java.io.File;
import java.io.IOException;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Files.readTextFromPath;
import static utils.Files.readXlsxFromPath;
import static utils.Zip.unzip;

public class ReadingTextFromFilesOfDifferentTypes {

    String txtPath = "./src/test/resources/files/txtFile.txt",
            pdfPath = "./src/test/resources/files/pdfFile.pdf",
            xlsPath = "./src/test/resources/files/xlsFile.xls",
            xlsxPath = "./src/test/resources/files/xlsxFile.xlsx",
            docPath = "./src/test/resources/files/docFile.doc",
            docxPath = "./src/test/resources/files/docxFile.docx",
            zipPath = "./src/test/resources/files/zipFile.zip",
            unzipFilePath = "./src/test/resources/files/unzip/",
            unzipTxtPath = "./src/test/resources/files/unzip/txtFile.txt",
            zipPassword = "",
            expectedText = "This is text from file",
            expectedTextFromPdf = "1. Простая часть дз";

    @Test
    public void textFromTxt() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        String actualData = readTextFromPath(txtPath);
        assertThat(actualData, containsString(expectedText));
    }

    @Test
    public void textFromPdf() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        PDF pdf = new PDF(new File(pdfPath));
        assertThat(pdf, containsText(expectedTextFromPdf));
    }

    @Test
    public void textFromXls() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        XLS xls = new XLS(new File(xlsPath));
        assertThat(xls, XLS.containsText(expectedText));
    }

    @Test
    public void textFromXlsx() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        String actualData = readXlsxFromPath(xlsxPath);
        assertThat(actualData, containsString(expectedText));
    }

    @Test
    public void textFromZip() throws ZipException, IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        unzip(zipPath, unzipFilePath, zipPassword);
        String actualData = readTextFromPath(unzipTxtPath);
        assertThat(actualData, containsString(expectedText));
    }

    @Test
    public void readFromDoc() throws IOException {
        String actualData = Files.readFromDoc(docPath);
        assertThat(actualData, containsString(expectedText));
    }

    @Test
    public void readFromDocx() throws IOException {
        String actualData = Files.readFromDocx(docxPath);
        assertThat(actualData, containsString(expectedText));
    }
}
