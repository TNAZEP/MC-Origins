package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringTag implements Tag {
   private static final int SELF_SIZE_IN_BITS = 288;
   public static final TagType<StringTag> TYPE = new TagType<StringTag>() {
      public StringTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(288L);
         String â˜ƒ = â˜ƒ.readUTF();
         â˜ƒ.accountBits((long)(16 * â˜ƒ.length()));
         return StringTag.valueOf(â˜ƒ);
      }

      @Override
      public String getName() {
         return "STRING";
      }

      @Override
      public String getPrettyName() {
         return "TAG_String";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private static final StringTag EMPTY = new StringTag("");
   private static final char DOUBLE_QUOTE = '"';
   private static final char SINGLE_QUOTE = '\'';
   private static final char ESCAPE = '\\';
   private static final char NOT_SET = '\u0000';
   private final String data;

   private StringTag(String var1) {
      Objects.requireNonNull(â˜ƒ, "Null string not allowed");
      this.data = â˜ƒ;
   }

   public static StringTag valueOf(String var0) {
      return â˜ƒ.isEmpty() ? EMPTY : new StringTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeUTF(this.data);
   }

   @Override
   public byte getId() {
      return 8;
   }

   @Override
   public TagType<StringTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return Tag.super.getAsString();
   }

   public StringTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof StringTag && Objects.equals(this.data, ((StringTag)â˜ƒ).data);
      }
   }

   public int hashCode() {
      return this.data.hashCode();
   }

   @Override
   public String getAsString() {
      return this.data;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitString(this);
   }

   public static String quoteAndEscape(String var0) {
      StringBuilder â˜ƒ = new StringBuilder(" ");
      char â˜ƒx = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length(); ++â˜ƒxx) {
         char â˜ƒxxx = â˜ƒ.charAt(â˜ƒxx);
         if (â˜ƒxxx == '\\') {
            â˜ƒ.append('\\');
         } else if (â˜ƒxxx == '"' || â˜ƒxxx == '\'') {
            if (â˜ƒx == 0) {
               â˜ƒx = (char)(â˜ƒxxx == '"' ? 39 : 34);
            }

            if (â˜ƒx == â˜ƒxxx) {
               â˜ƒ.append('\\');
            }
         }

         â˜ƒ.append(â˜ƒxxx);
      }

      if (â˜ƒx == 0) {
         â˜ƒx = '"';
      }

      â˜ƒ.setCharAt(0, â˜ƒx);
      â˜ƒ.append(â˜ƒx);
      return â˜ƒ.toString();
   }
}
