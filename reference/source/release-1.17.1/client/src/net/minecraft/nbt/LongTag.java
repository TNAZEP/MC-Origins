package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 128;
   public static final TagType<LongTag> TYPE = new TagType<LongTag>() {
      public LongTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(128L);
         return LongTag.valueOf(â˜ƒ.readLong());
      }

      @Override
      public String getName() {
         return "LONG";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Long";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private final long data;

   LongTag(long var1) {
      this.data = â˜ƒ;
   }

   public static LongTag valueOf(long var0) {
      return â˜ƒ >= -128L && â˜ƒ <= 1024L ? LongTag.Cache.cache[(int)â˜ƒ - -128] : new LongTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeLong(this.data);
   }

   @Override
   public byte getId() {
      return 4;
   }

   @Override
   public TagType<LongTag> getType() {
      return TYPE;
   }

   public LongTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof LongTag && this.data == ((LongTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      return (int)(this.data ^ this.data >>> 32);
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitLong(this);
   }

   @Override
   public long getAsLong() {
      return this.data;
   }

   @Override
   public int getAsInt() {
      return (int)(this.data & -1L);
   }

   @Override
   public short getAsShort() {
      return (short)((int)(this.data & 65535L));
   }

   @Override
   public byte getAsByte() {
      return (byte)((int)(this.data & 255L));
   }

   @Override
   public double getAsDouble() {
      return (double)this.data;
   }

   @Override
   public float getAsFloat() {
      return (float)this.data;
   }

   @Override
   public Number getAsNumber() {
      return this.data;
   }

   static class Cache {
      private static final int HIGH = 1024;
      private static final int LOW = -128;
      static final LongTag[] cache = new LongTag[1153];

      private Cache() {
      }

      static {
         for(int â˜ƒ = 0; â˜ƒ < cache.length; ++â˜ƒ) {
            cache[â˜ƒ] = new LongTag((long)(-128 + â˜ƒ));
         }
      }
   }
}
