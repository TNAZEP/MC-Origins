package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.stream.Collectors;

public class VertexFormat {
   private final ImmutableList<VertexFormatElement> elements;
   private final ImmutableMap<String, VertexFormatElement> elementMapping;
   private final IntList offsets = new IntArrayList();
   private final int vertexSize;
   private int vertexArrayObject;
   private int vertexBufferObject;
   private int indexBufferObject;

   public VertexFormat(ImmutableMap<String, VertexFormatElement> var1) {
      this.elementMapping = â˜ƒ;
      this.elements = â˜ƒ.values().asList();
      int â˜ƒ = 0;

      for(VertexFormatElement â˜ƒx : â˜ƒ.values()) {
         this.offsets.add(â˜ƒ);
         â˜ƒ += â˜ƒx.getByteSize();
      }

      this.vertexSize = â˜ƒ;
   }

   public String toString() {
      return "format: "
         + this.elementMapping.size()
         + " elements: "
         + (String)this.elementMapping.entrySet().stream().map(Object::toString).collect(Collectors.joining(" "));
   }

   public int getIntegerSize() {
      return this.getVertexSize() / 4;
   }

   public int getVertexSize() {
      return this.vertexSize;
   }

   public ImmutableList<VertexFormatElement> getElements() {
      return this.elements;
   }

   public ImmutableList<String> getElementAttributeNames() {
      return this.elementMapping.keySet().asList();
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         VertexFormat â˜ƒ = (VertexFormat)â˜ƒ;
         return this.vertexSize != â˜ƒ.vertexSize ? false : this.elementMapping.equals(â˜ƒ.elementMapping);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.elementMapping.hashCode();
   }

   public void setupBufferState() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::_setupBufferState);
      } else {
         this._setupBufferState();
      }
   }

   private void _setupBufferState() {
      int â˜ƒ = this.getVertexSize();
      List<VertexFormatElement> â˜ƒx = this.getElements();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
         ((VertexFormatElement)â˜ƒx.get(â˜ƒxx)).setupBufferState(â˜ƒxx, (long)this.offsets.getInt(â˜ƒxx), â˜ƒ);
      }
   }

   public void clearBufferState() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::_clearBufferState);
      } else {
         this._clearBufferState();
      }
   }

   private void _clearBufferState() {
      ImmutableList<VertexFormatElement> â˜ƒ = this.getElements();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         VertexFormatElement â˜ƒxx = (VertexFormatElement)â˜ƒ.get(â˜ƒx);
         â˜ƒxx.clearBufferState(â˜ƒx);
      }
   }

   public int getOrCreateVertexArrayObject() {
      if (this.vertexArrayObject == 0) {
         this.vertexArrayObject = GlStateManager._glGenVertexArrays();
      }

      return this.vertexArrayObject;
   }

   public int getOrCreateVertexBufferObject() {
      if (this.vertexBufferObject == 0) {
         this.vertexBufferObject = GlStateManager._glGenBuffers();
      }

      return this.vertexBufferObject;
   }

   public int getOrCreateIndexBufferObject() {
      if (this.indexBufferObject == 0) {
         this.indexBufferObject = GlStateManager._glGenBuffers();
      }

      return this.indexBufferObject;
   }

   public static enum IndexType {
      BYTE(5121, 1),
      SHORT(5123, 2),
      INT(5125, 4);

      public final int asGLType;
      public final int bytes;

      private IndexType(int var3, int var4) {
         this.asGLType = â˜ƒ;
         this.bytes = â˜ƒ;
      }

      public static VertexFormat.IndexType least(int var0) {
         if ((â˜ƒ & -65536) != 0) {
            return INT;
         } else {
            return (â˜ƒ & 0xFF00) != 0 ? SHORT : BYTE;
         }
      }
   }

   public static enum Mode {
      LINES(4, 2, 2),
      LINE_STRIP(5, 2, 1),
      DEBUG_LINES(1, 2, 2),
      DEBUG_LINE_STRIP(3, 2, 1),
      TRIANGLES(4, 3, 3),
      TRIANGLE_STRIP(5, 3, 1),
      TRIANGLE_FAN(6, 3, 1),
      QUADS(4, 4, 4);

      public final int asGLMode;
      public final int primitiveLength;
      public final int primitiveStride;

      private Mode(int var3, int var4, int var5) {
         this.asGLMode = â˜ƒ;
         this.primitiveLength = â˜ƒ;
         this.primitiveStride = â˜ƒ;
      }

      public int indexCount(int var1) {
         return switch(this) {
            case LINE_STRIP, DEBUG_LINES, DEBUG_LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN -> â˜ƒ;
            case LINES, QUADS -> â˜ƒ / 4 * 6;
            default -> 0;
         };
      }
   }
}
