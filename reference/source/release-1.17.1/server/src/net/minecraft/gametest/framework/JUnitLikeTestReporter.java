package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JUnitLikeTestReporter implements TestReporter {
   private final Document document;
   private final Element testSuite;
   private final Stopwatch stopwatch;
   private final File destination;

   public JUnitLikeTestReporter(File var1) throws ParserConfigurationException {
      this.destination = â˜ƒ;
      this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      this.testSuite = this.document.createElement("testsuite");
      Element â˜ƒ = this.document.createElement("testsuite");
      â˜ƒ.appendChild(this.testSuite);
      this.document.appendChild(â˜ƒ);
      this.testSuite.setAttribute("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
      this.stopwatch = Stopwatch.createStarted();
   }

   private Element createTestCase(GameTestInfo var1, String var2) {
      Element â˜ƒ = this.document.createElement("testcase");
      â˜ƒ.setAttribute("name", â˜ƒ);
      â˜ƒ.setAttribute("classname", â˜ƒ.getStructureName());
      â˜ƒ.setAttribute("time", String.valueOf((double)â˜ƒ.getRunTime() / 1000.0));
      this.testSuite.appendChild(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public void onTestFailed(GameTestInfo var1) {
      String â˜ƒx = â˜ƒ.getTestName();
      String â˜ƒxx = â˜ƒ.getError().getMessage();
      Element â˜ƒ;
      if (â˜ƒ.isRequired()) {
         â˜ƒ = this.document.createElement("failure");
         â˜ƒ.setAttribute("message", â˜ƒxx);
      } else {
         â˜ƒ = this.document.createElement("skipped");
         â˜ƒ.setAttribute("message", â˜ƒxx);
      }

      Element â˜ƒ = this.createTestCase(â˜ƒ, â˜ƒx);
      â˜ƒ.appendChild(â˜ƒ);
   }

   @Override
   public void onTestSuccess(GameTestInfo var1) {
      String â˜ƒ = â˜ƒ.getTestName();
      this.createTestCase(â˜ƒ, â˜ƒ);
   }

   @Override
   public void finish() {
      this.stopwatch.stop();
      this.testSuite.setAttribute("time", String.valueOf((double)this.stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000.0));

      try {
         this.save(this.destination);
      } catch (TransformerException var2) {
         throw new Error("Couldn't save test report", var2);
      }
   }

   public void save(File var1) throws TransformerException {
      TransformerFactory â˜ƒ = TransformerFactory.newInstance();
      Transformer â˜ƒx = â˜ƒ.newTransformer();
      DOMSource â˜ƒxx = new DOMSource(this.document);
      StreamResult â˜ƒxxx = new StreamResult(â˜ƒ);
      â˜ƒx.transform(â˜ƒxx, â˜ƒxxx);
   }
}
