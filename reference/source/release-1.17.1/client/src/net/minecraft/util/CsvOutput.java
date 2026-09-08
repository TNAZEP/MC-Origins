package net.minecraft.util;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class CsvOutput {
   private static final String LINE_SEPARATOR = "\r\n";
   private static final String FIELD_SEPARATOR = ",";
   private final Writer output;
   private final int columnCount;

   CsvOutput(Writer var1, List<String> var2) throws IOException {
      this.output = â˜ƒ;
      this.columnCount = â˜ƒ.size();
      this.writeLine(â˜ƒ.stream());
   }

   public static CsvOutput.Builder builder() {
      return new CsvOutput.Builder();
   }

   public void writeRow(Object... var1) throws IOException {
      if (â˜ƒ.length != this.columnCount) {
         throw new IllegalArgumentException("Invalid number of columns, expected " + this.columnCount + ", but got " + â˜ƒ.length);
      } else {
         this.writeLine(Stream.of(â˜ƒ));
      }
   }

   private void writeLine(Stream<?> var1) throws IOException {
      this.output.write((String)â˜ƒ.map(CsvOutput::getStringValue).collect(Collectors.joining(",")) + "\r\n");
   }

   private static String getStringValue(@Nullable Object var0) {
      return StringEscapeUtils.escapeCsv(â˜ƒ != null ? â˜ƒ.toString() : "[null]");
   }

   public static class Builder {
      private final List<String> headers = Lists.newArrayList();

      public CsvOutput.Builder addColumn(String var1) {
         this.headers.add(â˜ƒ);
         return this;
      }

      public CsvOutput build(Writer var1) throws IOException {
         return new CsvOutput(â˜ƒ, this.headers);
      }
   }
}
