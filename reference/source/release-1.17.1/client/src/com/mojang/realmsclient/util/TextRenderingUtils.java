package com.mojang.realmsclient.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextRenderingUtils {
   private TextRenderingUtils() {
   }

   @VisibleForTesting
   protected static List<String> lineBreak(String var0) {
      return Arrays.asList(â˜ƒ.split("\\n"));
   }

   public static List<TextRenderingUtils.Line> decompose(String var0, TextRenderingUtils.LineSegment... var1) {
      return decompose(â˜ƒ, Arrays.asList(â˜ƒ));
   }

   private static List<TextRenderingUtils.Line> decompose(String var0, List<TextRenderingUtils.LineSegment> var1) {
      List<String> â˜ƒ = lineBreak(â˜ƒ);
      return insertLinks(â˜ƒ, â˜ƒ);
   }

   private static List<TextRenderingUtils.Line> insertLinks(List<String> var0, List<TextRenderingUtils.LineSegment> var1) {
      int â˜ƒ = 0;
      List<TextRenderingUtils.Line> â˜ƒx = Lists.<TextRenderingUtils.Line>newArrayList();

      for(String â˜ƒxx : â˜ƒ) {
         List<TextRenderingUtils.LineSegment> â˜ƒxxx = Lists.<TextRenderingUtils.LineSegment>newArrayList();

         for(String â˜ƒxxxx : split(â˜ƒxx, "%link")) {
            if ("%link".equals(â˜ƒxxxx)) {
               â˜ƒxxx.add((TextRenderingUtils.LineSegment)â˜ƒ.get(â˜ƒ++));
            } else {
               â˜ƒxxx.add(TextRenderingUtils.LineSegment.text(â˜ƒxxxx));
            }
         }

         â˜ƒx.add(new TextRenderingUtils.Line(â˜ƒxxx));
      }

      return â˜ƒx;
   }

   public static List<String> split(String var0, String var1) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Delimiter cannot be the empty string");
      } else {
         List<String> â˜ƒ = Lists.newArrayList();

         int â˜ƒ;
         int â˜ƒ;
         for(â˜ƒ = 0; (â˜ƒ = â˜ƒ.indexOf(â˜ƒ, â˜ƒ)) != -1; â˜ƒ = â˜ƒ + â˜ƒ.length()) {
            if (â˜ƒ > â˜ƒ) {
               â˜ƒ.add(â˜ƒ.substring(â˜ƒ, â˜ƒ));
            }

            â˜ƒ.add(â˜ƒ);
         }

         if (â˜ƒ < â˜ƒ.length()) {
            â˜ƒ.add(â˜ƒ.substring(â˜ƒ));
         }

         return â˜ƒ;
      }
   }

   public static class Line {
      public final List<TextRenderingUtils.LineSegment> segments;

      Line(TextRenderingUtils.LineSegment... var1) {
         this(Arrays.asList(â˜ƒ));
      }

      Line(List<TextRenderingUtils.LineSegment> var1) {
         this.segments = â˜ƒ;
      }

      public String toString() {
         return "Line{segments=" + this.segments + "}";
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            TextRenderingUtils.Line â˜ƒ = (TextRenderingUtils.Line)â˜ƒ;
            return Objects.equals(this.segments, â˜ƒ.segments);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.segments});
      }
   }

   public static class LineSegment {
      private final String fullText;
      private final String linkTitle;
      private final String linkUrl;

      private LineSegment(String var1) {
         this.fullText = â˜ƒ;
         this.linkTitle = null;
         this.linkUrl = null;
      }

      private LineSegment(String var1, String var2, String var3) {
         this.fullText = â˜ƒ;
         this.linkTitle = â˜ƒ;
         this.linkUrl = â˜ƒ;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            TextRenderingUtils.LineSegment â˜ƒ = (TextRenderingUtils.LineSegment)â˜ƒ;
            return Objects.equals(this.fullText, â˜ƒ.fullText) && Objects.equals(this.linkTitle, â˜ƒ.linkTitle) && Objects.equals(this.linkUrl, â˜ƒ.linkUrl);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.fullText, this.linkTitle, this.linkUrl});
      }

      public String toString() {
         return "Segment{fullText='" + this.fullText + "', linkTitle='" + this.linkTitle + "', linkUrl='" + this.linkUrl + "'}";
      }

      public String renderedText() {
         return this.isLink() ? this.linkTitle : this.fullText;
      }

      public boolean isLink() {
         return this.linkTitle != null;
      }

      public String getLinkUrl() {
         if (!this.isLink()) {
            throw new IllegalStateException("Not a link: " + this);
         } else {
            return this.linkUrl;
         }
      }

      public static TextRenderingUtils.LineSegment link(String var0, String var1) {
         return new TextRenderingUtils.LineSegment(null, â˜ƒ, â˜ƒ);
      }

      @VisibleForTesting
      protected static TextRenderingUtils.LineSegment text(String var0) {
         return new TextRenderingUtils.LineSegment(â˜ƒ);
      }
   }
}
