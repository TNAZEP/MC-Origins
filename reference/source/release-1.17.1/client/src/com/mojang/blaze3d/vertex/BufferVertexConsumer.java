package com.mojang.blaze3d.vertex;

import net.minecraft.util.Mth;

public interface BufferVertexConsumer extends VertexConsumer {
   VertexFormatElement currentElement();

   void nextElement();

   void putByte(int var1, byte var2);

   void putShort(int var1, short var2);

   void putFloat(int var1, float var2);

   @Override
   default VertexConsumer vertex(double var1, double var3, double var5) {
      if (this.currentElement().getUsage() != VertexFormatElement.Usage.POSITION) {
         return this;
      } else if (this.currentElement().getType() == VertexFormatElement.Type.FLOAT && this.currentElement().getCount() == 3) {
         this.putFloat(0, (float)â˜ƒ);
         this.putFloat(4, (float)â˜ƒ);
         this.putFloat(8, (float)â˜ƒ);
         this.nextElement();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   default VertexConsumer color(int var1, int var2, int var3, int var4) {
      VertexFormatElement â˜ƒ = this.currentElement();
      if (â˜ƒ.getUsage() != VertexFormatElement.Usage.COLOR) {
         return this;
      } else if (â˜ƒ.getType() == VertexFormatElement.Type.UBYTE && â˜ƒ.getCount() == 4) {
         this.putByte(0, (byte)â˜ƒ);
         this.putByte(1, (byte)â˜ƒ);
         this.putByte(2, (byte)â˜ƒ);
         this.putByte(3, (byte)â˜ƒ);
         this.nextElement();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   default VertexConsumer uv(float var1, float var2) {
      VertexFormatElement â˜ƒ = this.currentElement();
      if (â˜ƒ.getUsage() == VertexFormatElement.Usage.UV && â˜ƒ.getIndex() == 0) {
         if (â˜ƒ.getType() == VertexFormatElement.Type.FLOAT && â˜ƒ.getCount() == 2) {
            this.putFloat(0, â˜ƒ);
            this.putFloat(4, â˜ƒ);
            this.nextElement();
            return this;
         } else {
            throw new IllegalStateException();
         }
      } else {
         return this;
      }
   }

   @Override
   default VertexConsumer overlayCoords(int var1, int var2) {
      return this.uvShort((short)â˜ƒ, (short)â˜ƒ, 1);
   }

   @Override
   default VertexConsumer uv2(int var1, int var2) {
      return this.uvShort((short)â˜ƒ, (short)â˜ƒ, 2);
   }

   default VertexConsumer uvShort(short var1, short var2, int var3) {
      VertexFormatElement â˜ƒ = this.currentElement();
      if (â˜ƒ.getUsage() != VertexFormatElement.Usage.UV || â˜ƒ.getIndex() != â˜ƒ) {
         return this;
      } else if (â˜ƒ.getType() == VertexFormatElement.Type.SHORT && â˜ƒ.getCount() == 2) {
         this.putShort(0, â˜ƒ);
         this.putShort(2, â˜ƒ);
         this.nextElement();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   default VertexConsumer normal(float var1, float var2, float var3) {
      VertexFormatElement â˜ƒ = this.currentElement();
      if (â˜ƒ.getUsage() != VertexFormatElement.Usage.NORMAL) {
         return this;
      } else if (â˜ƒ.getType() == VertexFormatElement.Type.BYTE && â˜ƒ.getCount() == 3) {
         this.putByte(0, normalIntValue(â˜ƒ));
         this.putByte(1, normalIntValue(â˜ƒ));
         this.putByte(2, normalIntValue(â˜ƒ));
         this.nextElement();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   static byte normalIntValue(float var0) {
      return (byte)((int)(Mth.clamp(â˜ƒ, -1.0F, 1.0F) * 127.0F) & 0xFF);
   }
}
