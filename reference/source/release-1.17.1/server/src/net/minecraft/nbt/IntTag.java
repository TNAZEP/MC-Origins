package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 96;
   public static final TagType<IntTag> TYPE = new TagType<IntTag>() {
      public IntTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(96L);
         return IntTag.valueOf(â˜ƒ.readInt());
      }

      @Override
      public String getName() {
         return "INT";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Int";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private final int data;

   IntTag(int var1) {
      this.data = â˜ƒ;
   }

   public static IntTag valueOf(int var0) {
      return â˜ƒ >= -128 && â˜ƒ <= 1024 ? IntTag.Cache.cache[â˜ƒ - -128] : new IntTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeInt(this.data);
   }

   @Override
   public byte getId() {
      return 3;
   }

   @Override
   public TagType<IntTag> getType() {
      return TYPE;
   }

   public IntTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof IntTag && this.data == ((IntTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      return this.data;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitInt(this);
   }

   @Override
   public long getAsLong() {
      return (long)this.data;
   }

   @Override
   public int getAsInt() {
      return this.data;
   }

   @Override
   public short getAsShort() {
      return (short)(this.data & 65535);
   }

   @Override
   public byte getAsByte() {
      return (byte)(this.data & 0xFF);
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
      static final IntTag[] cache = new IntTag[1153];

      private Cache() {
      }

      static {
         for(int â˜ƒ = 0; â˜ƒ < cache.length; ++â˜ƒ) {
            cache[â˜ƒ] = new IntTag(-128 + â˜ƒ);
         }
      }
   }
}
