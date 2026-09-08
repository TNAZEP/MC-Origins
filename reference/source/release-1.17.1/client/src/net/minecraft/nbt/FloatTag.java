package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.Mth;

public class FloatTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 96;
   public static final FloatTag ZERO = new FloatTag(0.0F);
   public static final TagType<FloatTag> TYPE = new TagType<FloatTag>() {
      public FloatTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(96L);
         return FloatTag.valueOf(â˜ƒ.readFloat());
      }

      @Override
      public String getName() {
         return "FLOAT";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Float";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private final float data;

   private FloatTag(float var1) {
      this.data = â˜ƒ;
   }

   public static FloatTag valueOf(float var0) {
      return â˜ƒ == 0.0F ? ZERO : new FloatTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeFloat(this.data);
   }

   @Override
   public byte getId() {
      return 5;
   }

   @Override
   public TagType<FloatTag> getType() {
      return TYPE;
   }

   public FloatTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof FloatTag && this.data == ((FloatTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this.data);
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitFloat(this);
   }

   @Override
   public long getAsLong() {
      return (long)this.data;
   }

   @Override
   public int getAsInt() {
      return Mth.floor(this.data);
   }

   @Override
   public short getAsShort() {
      return (short)(Mth.floor(this.data) & 65535);
   }

   @Override
   public byte getAsByte() {
      return (byte)(Mth.floor(this.data) & 0xFF);
   }

   @Override
   public double getAsDouble() {
      return (double)this.data;
   }

   @Override
   public float getAsFloat() {
      return this.data;
   }

   @Override
   public Number getAsNumber() {
      return this.data;
   }
}
