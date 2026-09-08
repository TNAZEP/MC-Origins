package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.network.chat.TranslatableComponent;

public class TagParser {
   public static final SimpleCommandExceptionType ERROR_TRAILING_DATA = new SimpleCommandExceptionType(new TranslatableComponent("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType ERROR_EXPECTED_KEY = new SimpleCommandExceptionType(new TranslatableComponent("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType ERROR_EXPECTED_VALUE = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.nbt.expected.value")
   );
   public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_LIST = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("argument.nbt.list.mixed", var0, var1)
   );
   public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_ARRAY = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("argument.nbt.array.mixed", var0, var1)
   );
   public static final DynamicCommandExceptionType ERROR_INVALID_ARRAY = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.nbt.array.invalid", var0)
   );
   public static final char ELEMENT_SEPARATOR = ',';
   public static final char NAME_VALUE_SEPARATOR = ':';
   private static final char LIST_OPEN = '[';
   private static final char LIST_CLOSE = ']';
   private static final char STRUCT_CLOSE = '}';
   private static final char STRUCT_OPEN = '{';
   private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader reader;

   public static CompoundTag parseTag(String var0) throws CommandSyntaxException {
      return new TagParser(new StringReader(â˜ƒ)).readSingleStruct();
   }

   @VisibleForTesting
   CompoundTag readSingleStruct() throws CommandSyntaxException {
      CompoundTag â˜ƒ = this.readStruct();
      this.reader.skipWhitespace();
      if (this.reader.canRead()) {
         throw ERROR_TRAILING_DATA.createWithContext(this.reader);
      } else {
         return â˜ƒ;
      }
   }

   public TagParser(StringReader var1) {
      this.reader = â˜ƒ;
   }

   protected String readKey() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
      } else {
         return this.reader.readString();
      }
   }

   protected Tag readTypedValue() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      int â˜ƒ = this.reader.getCursor();
      if (StringReader.isQuotedStringStart(this.reader.peek())) {
         return StringTag.valueOf(this.reader.readQuotedString());
      } else {
         String â˜ƒ = this.reader.readUnquotedString();
         if (â˜ƒ.isEmpty()) {
            this.reader.setCursor(â˜ƒ);
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
         } else {
            return this.type(â˜ƒ);
         }
      }
   }

   private Tag type(String var1) {
      try {
         if (FLOAT_PATTERN.matcher(â˜ƒ).matches()) {
            return FloatTag.valueOf(Float.parseFloat(â˜ƒ.substring(0, â˜ƒ.length() - 1)));
         }

         if (BYTE_PATTERN.matcher(â˜ƒ).matches()) {
            return ByteTag.valueOf(Byte.parseByte(â˜ƒ.substring(0, â˜ƒ.length() - 1)));
         }

         if (LONG_PATTERN.matcher(â˜ƒ).matches()) {
            return LongTag.valueOf(Long.parseLong(â˜ƒ.substring(0, â˜ƒ.length() - 1)));
         }

         if (SHORT_PATTERN.matcher(â˜ƒ).matches()) {
            return ShortTag.valueOf(Short.parseShort(â˜ƒ.substring(0, â˜ƒ.length() - 1)));
         }

         if (INT_PATTERN.matcher(â˜ƒ).matches()) {
            return IntTag.valueOf(Integer.parseInt(â˜ƒ));
         }

         if (DOUBLE_PATTERN.matcher(â˜ƒ).matches()) {
            return DoubleTag.valueOf(Double.parseDouble(â˜ƒ.substring(0, â˜ƒ.length() - 1)));
         }

         if (DOUBLE_PATTERN_NOSUFFIX.matcher(â˜ƒ).matches()) {
            return DoubleTag.valueOf(Double.parseDouble(â˜ƒ));
         }

         if ("true".equalsIgnoreCase(â˜ƒ)) {
            return ByteTag.ONE;
         }

         if ("false".equalsIgnoreCase(â˜ƒ)) {
            return ByteTag.ZERO;
         }
      } catch (NumberFormatException var3) {
      }

      return StringTag.valueOf(â˜ƒ);
   }

   public Tag readValue() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         char â˜ƒ = this.reader.peek();
         if (â˜ƒ == '{') {
            return this.readStruct();
         } else {
            return â˜ƒ == '[' ? this.readList() : this.readTypedValue();
         }
      }
   }

   protected Tag readList() throws CommandSyntaxException {
      return this.reader.canRead(3) && !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';'
         ? this.readArrayTag()
         : this.readListTag();
   }

   public CompoundTag readStruct() throws CommandSyntaxException {
      this.expect('{');
      CompoundTag â˜ƒ = new CompoundTag();
      this.reader.skipWhitespace();

      while(this.reader.canRead() && this.reader.peek() != '}') {
         int â˜ƒx = this.reader.getCursor();
         String â˜ƒxx = this.readKey();
         if (â˜ƒxx.isEmpty()) {
            this.reader.setCursor(â˜ƒx);
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
         }

         this.expect(':');
         â˜ƒ.put(â˜ƒxx, this.readValue());
         if (!this.hasElementSeparator()) {
            break;
         }

         if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
         }
      }

      this.expect('}');
      return â˜ƒ;
   }

   private Tag readListTag() throws CommandSyntaxException {
      this.expect('[');
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         ListTag â˜ƒ = new ListTag();
         TagType<?> â˜ƒx = null;

         while(this.reader.peek() != ']') {
            int â˜ƒxx = this.reader.getCursor();
            Tag â˜ƒxxx = this.readValue();
            TagType<?> â˜ƒxxxx = â˜ƒxxx.getType();
            if (â˜ƒx == null) {
               â˜ƒx = â˜ƒxxxx;
            } else if (â˜ƒxxxx != â˜ƒx) {
               this.reader.setCursor(â˜ƒxx);
               throw ERROR_INSERT_MIXED_LIST.createWithContext(this.reader, â˜ƒxxxx.getPrettyName(), â˜ƒx.getPrettyName());
            }

            â˜ƒ.add(â˜ƒxxx);
            if (!this.hasElementSeparator()) {
               break;
            }

            if (!this.reader.canRead()) {
               throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
            }
         }

         this.expect(']');
         return â˜ƒ;
      }
   }

   private Tag readArrayTag() throws CommandSyntaxException {
      this.expect('[');
      int â˜ƒ = this.reader.getCursor();
      char â˜ƒx = this.reader.read();
      this.reader.read();
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else if (â˜ƒx == 'B') {
         return new ByteArrayTag(this.readArray(ByteArrayTag.TYPE, ByteTag.TYPE));
      } else if (â˜ƒx == 'L') {
         return new LongArrayTag(this.readArray(LongArrayTag.TYPE, LongTag.TYPE));
      } else if (â˜ƒx == 'I') {
         return new IntArrayTag(this.readArray(IntArrayTag.TYPE, IntTag.TYPE));
      } else {
         this.reader.setCursor(â˜ƒ);
         throw ERROR_INVALID_ARRAY.createWithContext(this.reader, String.valueOf(â˜ƒx));
      }
   }

   private <T extends Number> List<T> readArray(TagType<?> var1, TagType<?> var2) throws CommandSyntaxException {
      List<T> â˜ƒ = Lists.newArrayList();

      while(this.reader.peek() != ']') {
         int â˜ƒx = this.reader.getCursor();
         Tag â˜ƒxx = this.readValue();
         TagType<?> â˜ƒxxx = â˜ƒxx.getType();
         if (â˜ƒxxx != â˜ƒ) {
            this.reader.setCursor(â˜ƒx);
            throw ERROR_INSERT_MIXED_ARRAY.createWithContext(this.reader, â˜ƒxxx.getPrettyName(), â˜ƒ.getPrettyName());
         }

         if (â˜ƒ == ByteTag.TYPE) {
            â˜ƒ.add(((NumericTag)â˜ƒxx).getAsByte());
         } else if (â˜ƒ == LongTag.TYPE) {
            â˜ƒ.add(((NumericTag)â˜ƒxx).getAsLong());
         } else {
            â˜ƒ.add(((NumericTag)â˜ƒxx).getAsInt());
         }

         if (!this.hasElementSeparator()) {
            break;
         }

         if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
         }
      }

      this.expect(']');
      return â˜ƒ;
   }

   private boolean hasElementSeparator() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == ',') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void expect(char var1) throws CommandSyntaxException {
      this.reader.skipWhitespace();
      this.reader.expect(â˜ƒ);
   }
}
