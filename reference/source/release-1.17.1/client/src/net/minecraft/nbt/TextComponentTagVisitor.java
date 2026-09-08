package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextComponentTagVisitor implements TagVisitor {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int INLINE_LIST_THRESHOLD = 8;
   private static final ByteCollection INLINE_ELEMENT_TYPES = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
   private static final ChatFormatting SYNTAX_HIGHLIGHTING_KEY = ChatFormatting.AQUA;
   private static final ChatFormatting SYNTAX_HIGHLIGHTING_STRING = ChatFormatting.GREEN;
   private static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER = ChatFormatting.GOLD;
   private static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = ChatFormatting.RED;
   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String NAME_VALUE_SEPARATOR = String.valueOf(':');
   private static final String ELEMENT_SEPARATOR = String.valueOf(',');
   private static final String LIST_OPEN = "[";
   private static final String LIST_CLOSE = "]";
   private static final String LIST_TYPE_SEPARATOR = ";";
   private static final String ELEMENT_SPACING = " ";
   private static final String STRUCT_OPEN = "{";
   private static final String STRUCT_CLOSE = "}";
   private static final String NEWLINE = "\n";
   private final String indentation;
   private final int depth;
   private Component result;

   public TextComponentTagVisitor(String var1, int var2) {
      this.indentation = â˜ƒ;
      this.depth = â˜ƒ;
   }

   public Component visit(Tag var1) {
      â˜ƒ.accept(this);
      return this.result;
   }

   @Override
   public void visitString(StringTag var1) {
      String â˜ƒ = StringTag.quoteAndEscape(â˜ƒ.getAsString());
      String â˜ƒx = â˜ƒ.substring(0, 1);
      Component â˜ƒxx = new TextComponent(â˜ƒ.substring(1, â˜ƒ.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_STRING);
      this.result = new TextComponent(â˜ƒx).append(â˜ƒxx).append(â˜ƒx);
   }

   @Override
   public void visitByte(ByteTag var1) {
      Component â˜ƒ = new TextComponent("b").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsNumber())).append(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitShort(ShortTag var1) {
      Component â˜ƒ = new TextComponent("s").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsNumber())).append(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitInt(IntTag var1) {
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsNumber())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitLong(LongTag var1) {
      Component â˜ƒ = new TextComponent("L").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsNumber())).append(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitFloat(FloatTag var1) {
      Component â˜ƒ = new TextComponent("f").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsFloat())).append(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitDouble(DoubleTag var1) {
      Component â˜ƒ = new TextComponent("d").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      this.result = new TextComponent(String.valueOf(â˜ƒ.getAsDouble())).append(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   @Override
   public void visitByteArray(ByteArrayTag var1) {
      Component â˜ƒ = new TextComponent("B").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      MutableComponent â˜ƒx = new TextComponent("[").append(â˜ƒ).append(";");
      byte[] â˜ƒxx = â˜ƒ.getAsByteArray();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
         MutableComponent â˜ƒxxxx = new TextComponent(String.valueOf(â˜ƒxx[â˜ƒxxx])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
         â˜ƒx.append(" ").append(â˜ƒxxxx).append(â˜ƒ);
         if (â˜ƒxxx != â˜ƒxx.length - 1) {
            â˜ƒx.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒx.append("]");
      this.result = â˜ƒx;
   }

   @Override
   public void visitIntArray(IntArrayTag var1) {
      Component â˜ƒ = new TextComponent("I").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      MutableComponent â˜ƒx = new TextComponent("[").append(â˜ƒ).append(";");
      int[] â˜ƒxx = â˜ƒ.getAsIntArray();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
         â˜ƒx.append(" ").append(new TextComponent(String.valueOf(â˜ƒxx[â˜ƒxxx])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER));
         if (â˜ƒxxx != â˜ƒxx.length - 1) {
            â˜ƒx.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒx.append("]");
      this.result = â˜ƒx;
   }

   @Override
   public void visitLongArray(LongArrayTag var1) {
      Component â˜ƒ = new TextComponent("L").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      MutableComponent â˜ƒx = new TextComponent("[").append(â˜ƒ).append(";");
      long[] â˜ƒxx = â˜ƒ.getAsLongArray();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
         Component â˜ƒxxxx = new TextComponent(String.valueOf(â˜ƒxx[â˜ƒxxx])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
         â˜ƒx.append(" ").append(â˜ƒxxxx).append(â˜ƒ);
         if (â˜ƒxxx != â˜ƒxx.length - 1) {
            â˜ƒx.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒx.append("]");
      this.result = â˜ƒx;
   }

   @Override
   public void visitList(ListTag var1) {
      if (â˜ƒ.isEmpty()) {
         this.result = new TextComponent("[]");
      } else if (INLINE_ELEMENT_TYPES.contains(â˜ƒ.getElementType()) && â˜ƒ.size() <= 8) {
         String â˜ƒ = ELEMENT_SEPARATOR + " ";
         MutableComponent â˜ƒx = new TextComponent("[");

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            if (â˜ƒxx != 0) {
               â˜ƒx.append(â˜ƒ);
            }

            â˜ƒx.append(new TextComponentTagVisitor(this.indentation, this.depth).visit(â˜ƒ.get(â˜ƒxx)));
         }

         â˜ƒx.append("]");
         this.result = â˜ƒx;
      } else {
         MutableComponent â˜ƒ = new TextComponent("[");
         if (!this.indentation.isEmpty()) {
            â˜ƒ.append("\n");
         }

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
            MutableComponent â˜ƒx = new TextComponent(Strings.repeat(this.indentation, this.depth + 1));
            â˜ƒx.append(new TextComponentTagVisitor(this.indentation, this.depth + 1).visit(â˜ƒ.get(â˜ƒ)));
            if (â˜ƒ != â˜ƒ.size() - 1) {
               â˜ƒx.append(ELEMENT_SEPARATOR).append(this.indentation.isEmpty() ? " " : "\n");
            }

            â˜ƒ.append(â˜ƒx);
         }

         if (!this.indentation.isEmpty()) {
            â˜ƒ.append("\n").append(Strings.repeat(this.indentation, this.depth));
         }

         â˜ƒ.append("]");
         this.result = â˜ƒ;
      }
   }

   @Override
   public void visitCompound(CompoundTag var1) {
      if (â˜ƒ.isEmpty()) {
         this.result = new TextComponent("{}");
      } else {
         MutableComponent â˜ƒ = new TextComponent("{");
         Collection<String> â˜ƒx = â˜ƒ.getAllKeys();
         if (LOGGER.isDebugEnabled()) {
            List<String> â˜ƒxx = Lists.newArrayList(â˜ƒ.getAllKeys());
            Collections.sort(â˜ƒxx);
            â˜ƒx = â˜ƒxx;
         }

         if (!this.indentation.isEmpty()) {
            â˜ƒ.append("\n");
         }

         MutableComponent â˜ƒ;
         for(Iterator<String> â˜ƒ = â˜ƒx.iterator(); â˜ƒ.hasNext(); â˜ƒ.append(â˜ƒ)) {
            String â˜ƒx = (String)â˜ƒ.next();
            â˜ƒ = new TextComponent(Strings.repeat(this.indentation, this.depth + 1))
               .append(handleEscapePretty(â˜ƒx))
               .append(NAME_VALUE_SEPARATOR)
               .append(" ")
               .append(new TextComponentTagVisitor(this.indentation, this.depth + 1).visit(â˜ƒ.get(â˜ƒx)));
            if (â˜ƒ.hasNext()) {
               â˜ƒ.append(ELEMENT_SEPARATOR).append(this.indentation.isEmpty() ? " " : "\n");
            }
         }

         if (!this.indentation.isEmpty()) {
            â˜ƒ.append("\n").append(Strings.repeat(this.indentation, this.depth));
         }

         â˜ƒ.append("}");
         this.result = â˜ƒ;
      }
   }

   protected static Component handleEscapePretty(String var0) {
      if (SIMPLE_VALUE.matcher(â˜ƒ).matches()) {
         return new TextComponent(â˜ƒ).withStyle(SYNTAX_HIGHLIGHTING_KEY);
      } else {
         String â˜ƒ = StringTag.quoteAndEscape(â˜ƒ);
         String â˜ƒx = â˜ƒ.substring(0, 1);
         Component â˜ƒxx = new TextComponent(â˜ƒ.substring(1, â˜ƒ.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_KEY);
         return new TextComponent(â˜ƒx).append(â˜ƒxx).append(â˜ƒx);
      }
   }

   @Override
   public void visitEnd(EndTag var1) {
      this.result = TextComponent.EMPTY;
   }
}
