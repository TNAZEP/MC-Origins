package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 80;
   public static final TagType<ShortTag> TYPE = new TagType<ShortTag>() {
      public ShortTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(80L);
         return ShortTag.valueOf(â˜ƒ.readShort());
      }

      @Override
      public String getName() {
         return "SHORT";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Short";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private final short data;

   ShortTag(short var1) {
      this.data = â˜ƒ;
   }

   public static ShortTag valueOf(short var0) {
      return â˜ƒ >= -128 && â˜ƒ <= 1024 ? ShortTag.Cache.cache[â˜ƒ - -128] : new ShortTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeShort(this.data);
   }

   @Override
   public byte getId() {
      return 2;
   }

   @Override
   public TagType<ShortTag> getType() {
      return TYPE;
   }

   public ShortTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ShortTag && this.data == ((ShortTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      return this.data;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitShort(this);
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
      return this.data;
   }

   @Override
   public byte getAsByte() {
      return (byte)(this.data & 255);
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
      static final ShortTag[] cache = new ShortTag[1153];

      private Cache() {
      }

      static {
         for(int â˜ƒ = 0; â˜ƒ < cache.length; ++â˜ƒ) {
            cache[â˜ƒ] = new ShortTag((short)(-128 + â˜ƒ));
         }
      }
   }
}
