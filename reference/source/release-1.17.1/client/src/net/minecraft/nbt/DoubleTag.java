package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.Mth;

public class DoubleTag extends NumericTag {
   private static final int SELF_SIZE_IN_BITS = 128;
   public static final DoubleTag ZERO = new DoubleTag(0.0);
   public static final TagType<DoubleTag> TYPE = new TagType<DoubleTag>() {
      public DoubleTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(128L);
         return DoubleTag.valueOf(â˜ƒ.readDouble());
      }

      @Override
      public String getName() {
         return "DOUBLE";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Double";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   private final double data;

   private DoubleTag(double var1) {
      this.data = â˜ƒ;
   }

   public static DoubleTag valueOf(double var0) {
      return â˜ƒ == 0.0 ? ZERO : new DoubleTag(â˜ƒ);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeDouble(this.data);
   }

   @Override
   public byte getId() {
      return 6;
   }

   @Override
   public TagType<DoubleTag> getType() {
      return TYPE;
   }

   public DoubleTag copy() {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof DoubleTag && this.data == ((DoubleTag)â˜ƒ).data;
      }
   }

   public int hashCode() {
      long â˜ƒ = Double.doubleToLongBits(this.data);
      return (int)(â˜ƒ ^ â˜ƒ >>> 32);
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitDouble(this);
   }

   @Override
   public long getAsLong() {
      return (long)Math.floor(this.data);
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
      return this.data;
   }

   @Override
   public float getAsFloat() {
      return (float)this.data;
   }

   @Override
   public Number getAsNumber() {
      return this.data;
   }
}
