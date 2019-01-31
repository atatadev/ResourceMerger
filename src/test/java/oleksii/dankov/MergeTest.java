package oleksii.dankov;

import oleksii.dankov.cli.CliArgumentHandler;
import oleksii.dankov.merger.ResourceMergerImpl;
import oleksii.dankov.writer.DocumentWriterImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xmlmatchers.namespace.SimpleNamespaceContext;

import javax.xml.transform.Source;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.xmlmatchers.XmlMatchers.equivalentTo;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.XmlMatchers.similarTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.transform.XmlConverters.xml;
import static org.xmlmatchers.xpath.HasXPath.hasXPath;

import static org.xmlmatchers.xpath.XpathReturnType.returningAString;
import static org.hamcrest.Matchers.equalTo;
import static org.xmlmatchers.xpath.XpathReturnType.returningAnXmlNode;

@RunWith(Parameterized.class)
public class MergeTest {


    private final String xmlData;

    public MergeTest(String xml) {
        this.xmlData = xml;
    }

    @Parameterized.Parameters
    public static Collection<String[]> data() throws Exception {
        String[] args = {
                "-libsDir", "src/test/resources/libs",
                "-appRes", "src/test/resources/appres/",
                "-outputDirectory", "tmp/output"
        };
        File generated = new File("tmp/output/values/values.xml");
        CliArgumentHandler argsHandler = CliArgumentHandler.fromArgs(args);
        new App(argsHandler, new DocumentWriterImpl(), new ResourceMergerImpl()).process();
        String xml = new String(Files.readAllBytes(generated.toPath()));
        return Arrays.asList(new String[][] {//
                { xml },
        });
    }

    @Test
    public void hasAllFields() {
        Source xml = the(xmlData);
        assertThat(xml, hasXPath("/resources/string[@name='app_name']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorAccent']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorPrimary']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorPrimaryDark']"));
    }

    @Test
    public void resourcesHasNs1NameSpace() {
        assertTrue(xmlData.contains("xmlns:ns1=\"urn:oasis:names:tc:xliff:document:1.2\""));
    }
}