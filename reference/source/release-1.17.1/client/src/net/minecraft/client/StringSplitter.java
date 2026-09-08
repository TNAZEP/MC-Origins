package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class StringSplitter {
   final StringSplitter.WidthProvider widthProvider;

   public StringSplitter(StringSplitter.WidthProvider var1) {
      this.widthProvider = â˜ƒ;
   }

   public float stringWidth(@Nullable String var1) {
      if (â˜ƒ == null) {
         return 0.0F;
      } else {
         MutableFloat â˜ƒ = new MutableFloat();
         StringDecomposer.iterateFormatted(â˜ƒ, Style.EMPTY, (var2x, var3, var4) -> {
            â˜ƒ.add(this.widthProvider.getWidth(var4, var3));
            return true;
         });
         return â˜ƒ.floatValue();
      }
   }

   public float stringWidth(FormattedText var1) {
      MutableFloat â˜ƒ = new MutableFloat();
      StringDecomposer.iterateFormatted(â˜ƒ, Style.EMPTY, (var2x, var3, var4) -> {
         â˜ƒ.add(this.widthProvider.getWidth(var4, var3));
         return true;
      });
      return â˜ƒ.floatValue();
   }

   public float stringWidth(FormattedCharSequence var1) {
      MutableFloat â˜ƒ = new MutableFloat();
      â˜ƒ.accept((var2x, var3, var4) -> {
         â˜ƒ.add(this.widthProvider.getWidth(var4, var3));
         return true;
      });
      return â˜ƒ.floatValue();
   }

   public int plainIndexAtWidth(String var1, int var2, Style var3) {
      StringSplitter.WidthLimitedCharSink â˜ƒ = new StringSplitter.WidthLimitedCharSink((float)â˜ƒ);
      StringDecomposer.iterate(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ.getPosition();
   }

   public String plainHeadByWidth(String var1, int var2, Style var3) {
      return â˜ƒ.substring(0, this.plainIndexAtWidth(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public String plainTailByWidth(String var1, int var2, Style var3) {
      MutableFloat â˜ƒ = new MutableFloat();
      MutableInt â˜ƒx = new MutableInt(â˜ƒ.length());
      StringDecomposer.iterateBackwards(â˜ƒ, â˜ƒ, (var4x, var5x, var6) -> {
         float â˜ƒ = â˜ƒ.addAndGet(this.widthProvider.getWidth(var6, var5x));
         if (â˜ƒ > (float)â˜ƒ) {
            return false;
         } else {
            â˜ƒ.setValue(var4x);
            return true;
         }
      });
      return â˜ƒ.substring(â˜ƒx.intValue());
   }

   public int formattedIndexByWidth(String var1, int var2, Style var3) {
      StringSplitter.WidthLimitedCharSink â˜ƒ = new StringSplitter.WidthLimitedCharSink((float)â˜ƒ);
      StringDecomposer.iterateFormatted(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ.getPosition();
   }

   @Nullable
   public Style componentStyleAtWidth(FormattedText var1, int var2) {
      StringSplitter.WidthLimitedCharSink â˜ƒ = new StringSplitter.WidthLimitedCharSink((float)â˜ƒ);
      return (Style)â˜ƒ.visit((var1x, var2x) -> StringDecomposer.iterateFormatted(var2x, var1x, â˜ƒ) ? Optional.empty() : Optional.of(var1x), Style.EMPTY)
         .orElse(null);
   }

   @Nullable
   public Style componentStyleAtWidth(FormattedCharSequence var1, int var2) {
      StringSplitter.WidthLimitedCharSink â˜ƒ = new StringSplitter.WidthLimitedCharSink((float)â˜ƒ);
      MutableObject<Style> â˜ƒx = new MutableObject<>();
      â˜ƒ.accept((var2x, var3x, var4x) -> {
         if (!â˜ƒ.accept(var2x, var3x, var4x)) {
            â˜ƒ.setValue(var3x);
            return false;
         } else {
            return true;
         }
      });
      return â˜ƒx.getValue();
   }

   public String formattedHeadByWidth(String var1, int var2, Style var3) {
      return â˜ƒ.substring(0, this.formattedIndexByWidth(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public FormattedText headByWidth(FormattedText var1, int var2, Style var3) {
      final StringSplitter.WidthLimitedCharSink â˜ƒ = new StringSplitter.WidthLimitedCharSink((float)â˜ƒ);
      return (FormattedText)â˜ƒ.visit(new FormattedText.StyledContentConsumer<FormattedText>() {
         private final ComponentCollector collector = new ComponentCollector();

         @Override
         public Optional<FormattedText> accept(Style var1, String var2) {
            â˜ƒ.resetPosition();
            if (!StringDecomposer.iterateFormatted(â˜ƒ, â˜ƒ, â˜ƒ)) {
               String â˜ƒ = â˜ƒ.substring(0, â˜ƒ.getPosition());
               if (!â˜ƒ.isEmpty()) {
                  this.collector.append(FormattedText.of(â˜ƒ, â˜ƒ));
               }

               return Optional.of(this.collector.getResultOrEmpty());
            } else {
               if (!â˜ƒ.isEmpty()) {
                  this.collector.append(FormattedText.of(â˜ƒ, â˜ƒ));
               }

               return Optional.empty();
            }
         }
      }, â˜ƒ).orElse(â˜ƒ);
   }

   public int findLineBreak(String var1, int var2, Style var3) {
      StringSplitter.LineBreakFinder â˜ƒ = new StringSplitter.LineBreakFinder((float)â˜ƒ);
      StringDecomposer.iterateFormatted(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ.getSplitPosition();
   }

   public static int getWordPosition(String var0, int var1, int var2, boolean var3) {
      int â˜ƒ = â˜ƒ;
      boolean â˜ƒx = â˜ƒ < 0;
      int â˜ƒxx = Math.abs(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
         if (â˜ƒx) {
            while(â˜ƒ && â˜ƒ > 0 && (â˜ƒ.charAt(â˜ƒ - 1) == ' ' || â˜ƒ.charAt(â˜ƒ - 1) == '\n')) {
               --â˜ƒ;
            }

            while(â˜ƒ > 0 && â˜ƒ.charAt(â˜ƒ - 1) != ' ' && â˜ƒ.charAt(â˜ƒ - 1) != '\n') {
               --â˜ƒ;
            }
         } else {
            int â˜ƒxxxx = â˜ƒ.length();
            int â˜ƒxxxxx = â˜ƒ.indexOf(32, â˜ƒ);
            int â˜ƒxxxxxx = â˜ƒ.indexOf(10, â˜ƒ);
            if (â˜ƒxxxxx == -1 && â˜ƒxxxxxx == -1) {
               â˜ƒ = -1;
            } else if (â˜ƒxxxxx != -1 && â˜ƒxxxxxx != -1) {
               â˜ƒ = Math.min(â˜ƒxxxxx, â˜ƒxxxxxx);
            } else if (â˜ƒxxxxx != -1) {
               â˜ƒ = â˜ƒxxxxx;
            } else {
               â˜ƒ = â˜ƒxxxxxx;
            }

            if (â˜ƒ == -1) {
               â˜ƒ = â˜ƒxxxx;
            } else {
               while(â˜ƒ && â˜ƒ < â˜ƒxxxx && (â˜ƒ.charAt(â˜ƒ) == ' ' || â˜ƒ.charAt(â˜ƒ) == '\n')) {
                  ++â˜ƒ;
               }
            }
         }
      }

      return â˜ƒ;
   }

   public void splitLines(String var1, int var2, Style var3, boolean var4, StringSplitter.LinePosConsumer var5) {
      int â˜ƒ = 0;
      int â˜ƒx = â˜ƒ.length();

      StringSplitter.LineBreakFinder â˜ƒ;
      for(Style â˜ƒxx = â˜ƒ; â˜ƒ < â˜ƒx; â˜ƒxx = â˜ƒ.getSplitStyle()) {
         â˜ƒ = new StringSplitter.LineBreakFinder((float)â˜ƒ);
         boolean â˜ƒxxx = StringDecomposer.iterateFormatted(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
         if (â˜ƒxxx) {
            â˜ƒ.accept(â˜ƒxx, â˜ƒ, â˜ƒx);
            break;
         }

         int â˜ƒxxx = â˜ƒ.getSplitPosition();
         char â˜ƒxxxx = â˜ƒ.charAt(â˜ƒxxx);
         int â˜ƒxxxxx = â˜ƒxxxx != '\n' && â˜ƒxxxx != ' ' ? â˜ƒxxx : â˜ƒxxx + 1;
         â˜ƒ.accept(â˜ƒxx, â˜ƒ, â˜ƒ ? â˜ƒxxxxx : â˜ƒxxx);
         â˜ƒ = â˜ƒxxxxx;
      }
   }

   public List<FormattedText> splitLines(String var1, int var2, Style var3) {
      List<FormattedText> â˜ƒ = Lists.<FormattedText>newArrayList();
      this.splitLines(â˜ƒ, â˜ƒ, â˜ƒ, false, (var2x, var3x, var4x) -> â˜ƒ.add(FormattedText.of(â˜ƒ.substring(var3x, var4x), var2x)));
      return â˜ƒ;
   }

   public List<FormattedText> splitLines(FormattedText var1, int var2, Style var3) {
      List<FormattedText> â˜ƒ = Lists.<FormattedText>newArrayList();
      this.splitLines(â˜ƒ, â˜ƒ, â˜ƒ, (var1x, var2x) -> â˜ƒ.add(var1x));
      return â˜ƒ;
   }

   public List<FormattedText> splitLines(FormattedText var1, int var2, Style var3, FormattedText var4) {
      List<FormattedText> â˜ƒ = Lists.<FormattedText>newArrayList();
      this.splitLines(â˜ƒ, â˜ƒ, â˜ƒ, (var2x, var3x) -> â˜ƒ.add(var3x ? FormattedText.composite(â˜ƒ, var2x) : var2x));
      return â˜ƒ;
   }

   public void splitLines(FormattedText var1, int var2, Style var3, BiConsumer<FormattedText, Boolean> var4) {
      List<StringSplitter.LineComponent> â˜ƒ = Lists.<StringSplitter.LineComponent>newArrayList();
      â˜ƒ.visit((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            â˜ƒ.add(new StringSplitter.LineComponent(var2x, var1x));
         }

         return Optional.empty();
      }, â˜ƒ);
      StringSplitter.FlatComponents â˜ƒx = new StringSplitter.FlatComponents(â˜ƒ);
      boolean â˜ƒxx = true;
      boolean â˜ƒxxx = false;
      boolean â˜ƒxxxx = false;

      while(â˜ƒxx) {
         â˜ƒxx = false;
         StringSplitter.LineBreakFinder â˜ƒxxxxx = new StringSplitter.LineBreakFinder((float)â˜ƒ);

         for(StringSplitter.LineComponent â˜ƒxxxxxx : â˜ƒx.parts) {
            boolean â˜ƒxxxxxxx = StringDecomposer.iterateFormatted(â˜ƒxxxxxx.contents, 0, â˜ƒxxxxxx.style, â˜ƒ, â˜ƒxxxxx);
            if (!â˜ƒxxxxxxx) {
               int â˜ƒxxxxxxxx = â˜ƒxxxxx.getSplitPosition();
               Style â˜ƒxxxxxxxxx = â˜ƒxxxxx.getSplitStyle();
               char â˜ƒxxxxxxxxxx = â˜ƒx.charAt(â˜ƒxxxxxxxx);
               boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx == '\n';
               boolean â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx || â˜ƒxxxxxxxxxx == ' ';
               â˜ƒxxx = â˜ƒxxxxxxxxxxx;
               FormattedText â˜ƒxxxxxxxxxxxxx = â˜ƒx.splitAt(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx ? 1 : 0, â˜ƒxxxxxxxxx);
               â˜ƒ.accept(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxx);
               â˜ƒxxxx = !â˜ƒxxxxxxxxxxx;
               â˜ƒxx = true;
               break;
            }

            â˜ƒxxxxx.addToOffset(â˜ƒxxxxxx.contents.length());
         }
      }

      FormattedText â˜ƒxxxxx = â˜ƒx.getRemainder();
      if (â˜ƒxxxxx != null) {
         â˜ƒ.accept(â˜ƒxxxxx, â˜ƒxxxx);
      } else if (â˜ƒxxx) {
         â˜ƒ.accept(FormattedText.EMPTY, false);
      }
   }

   static class FlatComponents {
      final List<StringSplitter.LineComponent> parts;
      private String flatParts;

      public FlatComponents(List<StringSplitter.LineComponent> var1) {
         this.parts = â˜ƒ;
         this.flatParts = (String)â˜ƒ.stream().map(var0 -> var0.contents).collect(Collectors.joining());
      }

      public char charAt(int var1) {
         return this.flatParts.charAt(â˜ƒ);
      }

      public FormattedText splitAt(int var1, int var2, Style var3) {
         ComponentCollector â˜ƒ = new ComponentCollector();
         ListIterator<StringSplitter.LineComponent> â˜ƒx = this.parts.listIterator();
         int â˜ƒxx = â˜ƒ;
         boolean â˜ƒxxx = false;

         while(â˜ƒx.hasNext()) {
            StringSplitter.LineComponent â˜ƒxxxx = (StringSplitter.LineComponent)â˜ƒx.next();
            String â˜ƒxxxxx = â˜ƒxxxx.contents;
            int â˜ƒxxxxxx = â˜ƒxxxxx.length();
            if (!â˜ƒxxx) {
               if (â˜ƒxx > â˜ƒxxxxxx) {
                  â˜ƒ.append(â˜ƒxxxx);
                  â˜ƒx.remove();
                  â˜ƒxx -= â˜ƒxxxxxx;
               } else {
                  String â˜ƒxxxxxxx = â˜ƒxxxxx.substring(0, â˜ƒxx);
                  if (!â˜ƒxxxxxxx.isEmpty()) {
                     â˜ƒ.append(FormattedText.of(â˜ƒxxxxxxx, â˜ƒxxxx.style));
                  }

                  â˜ƒxx += â˜ƒ;
                  â˜ƒxxx = true;
               }
            }

            if (â˜ƒxxx) {
               if (â˜ƒxx <= â˜ƒxxxxxx) {
                  String â˜ƒxxxx = â˜ƒxxxxx.substring(â˜ƒxx);
                  if (â˜ƒxxxx.isEmpty()) {
                     â˜ƒx.remove();
                  } else {
                     â˜ƒx.set(new StringSplitter.LineComponent(â˜ƒxxxx, â˜ƒ));
                  }
                  break;
               }

               â˜ƒx.remove();
               â˜ƒxx -= â˜ƒxxxxxx;
            }
         }

         this.flatParts = this.flatParts.substring(â˜ƒ + â˜ƒ);
         return â˜ƒ.getResultOrEmpty();
      }

      @Nullable
      public FormattedText getRemainder() {
         ComponentCollector â˜ƒ = new ComponentCollector();
         this.parts.forEach(â˜ƒ::append);
         this.parts.clear();
         return â˜ƒ.getResult();
      }
   }

   class LineBreakFinder implements FormattedCharSink {
      private final float maxWidth;
      private int lineBreak = -1;
      private Style lineBreakStyle = Style.EMPTY;
      private boolean hadNonZeroWidthChar;
      private float width;
      private int lastSpace = -1;
      private Style lastSpaceStyle = Style.EMPTY;
      private int nextChar;
      private int offset;

      public LineBreakFinder(float var2) {
         this.maxWidth = Math.max(â˜ƒ, 1.0F);
      }

      @Override
      public boolean accept(int var1, Style var2, int var3) {
         int â˜ƒ = â˜ƒ + this.offset;
         switch(â˜ƒ) {
            case 10:
               return this.finishIteration(â˜ƒ, â˜ƒ);
            case 32:
               this.lastSpace = â˜ƒ;
               this.lastSpaceStyle = â˜ƒ;
            default:
               float â˜ƒx = StringSplitter.this.widthProvider.getWidth(â˜ƒ, â˜ƒ);
               this.width += â˜ƒx;
               if (!this.hadNonZeroWidthChar || !(this.width > this.maxWidth)) {
                  this.hadNonZeroWidthChar |= â˜ƒx != 0.0F;
                  this.nextChar = â˜ƒ + Character.charCount(â˜ƒ);
                  return true;
               } else {
                  return this.lastSpace != -1 ? this.finishIteration(this.lastSpace, this.lastSpaceStyle) : this.finishIteration(â˜ƒ, â˜ƒ);
               }
         }
      }

      private boolean finishIteration(int var1, Style var2) {
         this.lineBreak = â˜ƒ;
         this.lineBreakStyle = â˜ƒ;
         return false;
      }

      private boolean lineBreakFound() {
         return this.lineBreak != -1;
      }

      public int getSplitPosition() {
         return this.lineBreakFound() ? this.lineBreak : this.nextChar;
      }

      public Style getSplitStyle() {
         return this.lineBreakStyle;
      }

      public void addToOffset(int var1) {
         this.offset += â˜ƒ;
      }
   }

   static class LineComponent implements FormattedText {
      final String contents;
      final Style style;

      public LineComponent(String var1, Style var2) {
         this.contents = â˜ƒ;
         this.style = â˜ƒ;
      }

      @Override
      public <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1) {
         return â˜ƒ.accept(this.contents);
      }

      @Override
      public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2) {
         return â˜ƒ.accept(this.style.applyTo(â˜ƒ), this.contents);
      }
   }

   @FunctionalInterface
   public interface LinePosConsumer {
      void accept(Style var1, int var2, int var3);
   }

   class WidthLimitedCharSink implements FormattedCharSink {
      private float maxWidth;
      private int position;

      public WidthLimitedCharSink(float var2) {
         this.maxWidth = â˜ƒ;
      }

      @Override
      public boolean accept(int var1, Style var2, int var3) {
         this.maxWidth -= StringSplitter.this.widthProvider.getWidth(â˜ƒ, â˜ƒ);
         if (this.maxWidth >= 0.0F) {
            this.position = â˜ƒ + Character.charCount(â˜ƒ);
            return true;
         } else {
            return false;
         }
      }

      public int getPosition() {
         return this.position;
      }

      public void resetPosition() {
         this.position = 0;
      }
   }

   @FunctionalInterface
   public interface WidthProvider {
      float getWidth(int var1, Style var2);
   }
}
