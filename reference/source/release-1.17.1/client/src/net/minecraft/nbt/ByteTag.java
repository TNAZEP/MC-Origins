package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 72;
   public static final TagType<ByteTag> TYPE = new TagType<ByteTag>() {
      public ByteTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(72L);
         return ByteTag.valueOf(â˜ƒ.readByte());
      }

      @Override
      public String getName() {
         return "BYTE";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Byte";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   public static final ByteTag ZERO = valueOf((byte)0);
   public static final ByteTag ONE = valueOf((byte)1);
   private final byte data;

   ByteTag(byte var1) {
      this.data = â˜ƒ;
   }

   public static ByteTag valueOf(byte var0) {
      return ByteTag.Cache.cache[128 + â˜ƒ];
   }

   public static ByteTag valueOf(boolean var0) {
      return â˜ƒ ? ONE : ZERO;
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeByte(this.data);
   }

   @Override
   public byte getId() {
      return 1;
   }

   @Override
   public TagType<ByteTag> getType() {
      return TYPE;
   }

   public ByteTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ByteTag && this.data == ((ByteTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      return this.data;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitByte(this);
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
      return (short)this.data;
   }

   @Override
   public byte getAsByte() {
      return this.data;
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
      static final ByteTag[] cache = new ByteTag[256];

      private Cache() {
      }

      static {
         for(int â˜ƒ = 0; â˜ƒ < cache.length; ++â˜ƒ) {
            cache[â˜ƒ] = new ByteTag((byte)(â˜ƒ - 128));
         }
      }
   }
}
