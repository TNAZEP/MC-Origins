package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;

public class VertexFormatElement {
   private final VertexFormatElement.Type type;
   private final VertexFormatElement.Usage usage;
   private final int index;
   private final int count;
   private final int byteSize;

   public VertexFormatElement(int var1, VertexFormatElement.Type var2, VertexFormatElement.Usage var3, int var4) {
      if (this.supportsUsage(â˜ƒ, â˜ƒ)) {
         this.usage = â˜ƒ;
         this.type = â˜ƒ;
         this.index = â˜ƒ;
         this.count = â˜ƒ;
         this.byteSize = â˜ƒ.getSize() * this.count;
      } else {
         throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
      }
   }

   private boolean supportsUsage(int var1, VertexFormatElement.Usage var2) {
      return â˜ƒ == 0 || â˜ƒ == VertexFormatElement.Usage.UV;
   }

   public final VertexFormatElement.Type getType() {
      return this.type;
   }

   public final VertexFormatElement.Usage getUsage() {
      return this.usage;
   }

   public final int getCount() {
      return this.count;
   }

   public final int getIndex() {
      return this.index;
   }

   public String toString() {
      return this.count + "," + this.usage.getName() + "," + this.type.getName();
   }

   public final int getByteSize() {
      return this.byteSize;
   }

   public final boolean isPosition() {
      return this.usage == VertexFormatElement.Usage.POSITION;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         VertexFormatElement â˜ƒ = (VertexFormatElement)â˜ƒ;
         if (this.count != â˜ƒ.count) {
            return false;
         } else if (this.index != â˜ƒ.index) {
            return false;
         } else if (this.type != â˜ƒ.type) {
            return false;
         } else {
            return this.usage == â˜ƒ.usage;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.type.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.usage.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.index;
      return 31 * â˜ƒ + this.count;
   }

   public void setupBufferState(int var1, long var2, int var4) {
      this.usage.setupBufferState(this.count, this.type.getGlType(), â˜ƒ, â˜ƒ, this.index, â˜ƒ);
   }

   public void clearBufferState(int var1) {
      this.usage.clearBufferState(this.index, â˜ƒ);
   }

   public static enum Type {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int size;
      private final String name;
      private final int glType;

      private Type(int var3, String var4, int var5) {
         this.size = â˜ƒ;
         this.name = â˜ƒ;
         this.glType = â˜ƒ;
      }

      public int getSize() {
         return this.size;
      }

      public String getName() {
         return this.name;
      }

      public int getGlType() {
         return this.glType;
      }
   }

   public static enum Usage {
      POSITION("Position", (var0, var1, var2, var3, var5, var6) -> {
         GlStateManager._enableVertexAttribArray(var6);
         GlStateManager._vertexAttribPointer(var6, var0, var1, false, var2, var3);
      }, (var0, var1) -> GlStateManager._disableVertexAttribArray(var1)),
      NORMAL("Normal", (var0, var1, var2, var3, var5, var6) -> {
         GlStateManager._enableVertexAttribArray(var6);
         GlStateManager._vertexAttribPointer(var6, var0, var1, true, var2, var3);
      }, (var0, var1) -> GlStateManager._disableVertexAttribArray(var1)),
      COLOR("Vertex Color", (var0, var1, var2, var3, var5, var6) -> {
         GlStateManager._enableVertexAttribArray(var6);
         GlStateManager._vertexAttribPointer(var6, var0, var1, true, var2, var3);
      }, (var0, var1) -> GlStateManager._disableVertexAttribArray(var1)),
      UV("UV", (var0, var1, var2, var3, var5, var6) -> {
         GlStateManager._enableVertexAttribArray(var6);
         if (var1 == 5126) {
            GlStateManager._vertexAttribPointer(var6, var0, var1, false, var2, var3);
         } else {
            GlStateManager._vertexAttribIPointer(var6, var0, var1, var2, var3);
         }
      }, (var0, var1) -> GlStateManager._disableVertexAttribArray(var1)),
      PADDING("Padding", (var0, var1, var2, var3, var5, var6) -> {
      }, (var0, var1) -> {
      }),
      GENERIC("Generic", (var0, var1, var2, var3, var5, var6) -> {
         GlStateManager._enableVertexAttribArray(var6);
         GlStateManager._vertexAttribPointer(var6, var0, var1, false, var2, var3);
      }, (var0, var1) -> GlStateManager._disableVertexAttribArray(var1));

      private final String name;
      private final VertexFormatElement.Usage.SetupState setupState;
      private final VertexFormatElement.Usage.ClearState clearState;

      private Usage(String var3, VertexFormatElement.Usage.SetupState var4, VertexFormatElement.Usage.ClearState var5) {
         this.name = â˜ƒ;
         this.setupState = â˜ƒ;
         this.clearState = â˜ƒ;
      }

      void setupBufferState(int var1, int var2, int var3, long var4, int var6, int var7) {
         this.setupState.setupBufferState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void clearBufferState(int var1, int var2) {
         this.clearState.clearBufferState(â˜ƒ, â˜ƒ);
      }

      public String getName() {
         return this.name;
      }

      @FunctionalInterface
      interface ClearState {
         void clearBufferState(int var1, int var2);
      }

      @FunctionalInterface
      interface SetupState {
         void setupBufferState(int var1, int var2, int var3, long var4, int var6, int var7);
      }
   }
}
