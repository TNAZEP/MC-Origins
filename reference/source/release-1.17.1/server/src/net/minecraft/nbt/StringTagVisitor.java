package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class StringTagVisitor implements TagVisitor {
   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
   private final StringBuilder builder = new StringBuilder();

   public String visit(Tag var1) {
      â˜ƒ.accept(this);
      return this.builder.toString();
   }

   @Override
   public void visitString(StringTag var1) {
      this.builder.append(StringTag.quoteAndEscape(â˜ƒ.getAsString()));
   }

   @Override
   public void visitByte(ByteTag var1) {
      this.builder.append(â˜ƒ.getAsNumber()).append('b');
   }

   @Override
   public void visitShort(ShortTag var1) {
      this.builder.append(â˜ƒ.getAsNumber()).append('s');
   }

   @Override
   public void visitInt(IntTag var1) {
      this.builder.append(â˜ƒ.getAsNumber());
   }

   @Override
   public void visitLong(LongTag var1) {
      this.builder.append(â˜ƒ.getAsNumber()).append('L');
   }

   @Override
   public void visitFloat(FloatTag var1) {
      this.builder.append(â˜ƒ.getAsFloat()).append('f');
   }

   @Override
   public void visitDouble(DoubleTag var1) {
      this.builder.append(â˜ƒ.getAsDouble()).append('d');
   }

   @Override
   public void visitByteArray(ByteArrayTag var1) {
      this.builder.append("[B;");
      byte[] â˜ƒ = â˜ƒ.getAsByteArray();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         if (â˜ƒx != 0) {
            this.builder.append(',');
         }

         this.builder.append(â˜ƒ[â˜ƒx]).append('B');
      }

      this.builder.append(']');
   }

   @Override
   public void visitIntArray(IntArrayTag var1) {
      this.builder.append("[I;");
      int[] â˜ƒ = â˜ƒ.getAsIntArray();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         if (â˜ƒx != 0) {
            this.builder.append(',');
         }

         this.builder.append(â˜ƒ[â˜ƒx]);
      }

      this.builder.append(']');
   }

   @Override
   public void visitLongArray(LongArrayTag var1) {
      this.builder.append("[L;");
      long[] â˜ƒ = â˜ƒ.getAsLongArray();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         if (â˜ƒx != 0) {
            this.builder.append(',');
         }

         this.builder.append(â˜ƒ[â˜ƒx]).append('L');
      }

      this.builder.append(']');
   }

   @Override
   public void visitList(ListTag var1) {
      this.builder.append('[');

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         if (â˜ƒ != 0) {
            this.builder.append(',');
         }

         this.builder.append(new StringTagVisitor().visit(â˜ƒ.get(â˜ƒ)));
      }

      this.builder.append(']');
   }

   @Override
   public void visitCompound(CompoundTag var1) {
      this.builder.append('{');
      List<String> â˜ƒ = Lists.newArrayList(â˜ƒ.getAllKeys());
      Collections.sort(â˜ƒ);

      for(String â˜ƒx : â˜ƒ) {
         if (this.builder.length() != 1) {
            this.builder.append(',');
         }

         this.builder.append(handleEscape(â˜ƒx)).append(':').append(new StringTagVisitor().visit(â˜ƒ.get(â˜ƒx)));
      }

      this.builder.append('}');
   }

   protected static String handleEscape(String var0) {
      return SIMPLE_VALUE.matcher(â˜ƒ).matches() ? â˜ƒ : StringTag.quoteAndEscape(â˜ƒ);
   }

   @Override
   public void visitEnd(EndTag var1) {
      this.builder.append("END");
   }
}
