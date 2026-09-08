package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder extends DefaultedVertexConsumer implements BufferVertexConsumer {
   private static final int GROWTH_SIZE = 2097152;
   private static final Logger LOGGER = LogManager.getLogger();
   private ByteBuffer buffer;
   private final List<BufferBuilder.DrawState> drawStates = Lists.<BufferBuilder.DrawState>newArrayList();
   private int lastPoppedStateIndex;
   private int totalRenderedBytes;
   private int nextElementByte;
   private int totalUploadedBytes;
   private int vertices;
   @Nullable
   private VertexFormatElement currentElement;
   private int elementIndex;
   private VertexFormat format;
   private VertexFormat.Mode mode;
   private boolean fastFormat;
   private boolean fullFormat;
   private boolean building;
   @Nullable
   private Vector3f[] sortingPoints;
   private float sortX = Float.NaN;
   private float sortY = Float.NaN;
   private float sortZ = Float.NaN;
   private boolean indexOnly;

   public BufferBuilder(int var1) {
      this.buffer = MemoryTracker.create(â˜ƒ * 6);
   }

   private void ensureVertexCapacity() {
      this.ensureCapacity(this.format.getVertexSize());
   }

   private void ensureCapacity(int var1) {
      if (this.nextElementByte + â˜ƒ > this.buffer.capacity()) {
         int â˜ƒ = this.buffer.capacity();
         int â˜ƒx = â˜ƒ + roundUp(â˜ƒ);
         LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", â˜ƒ, â˜ƒx);
         ByteBuffer â˜ƒxx = MemoryTracker.resize(this.buffer, â˜ƒx);
         â˜ƒxx.rewind();
         this.buffer = â˜ƒxx;
      }
   }

   private static int roundUp(int var0) {
      int â˜ƒ = 2097152;
      if (â˜ƒ == 0) {
         return â˜ƒ;
      } else {
         if (â˜ƒ < 0) {
            â˜ƒ *= -1;
         }

         int â˜ƒ = â˜ƒ % â˜ƒ;
         return â˜ƒ == 0 ? â˜ƒ : â˜ƒ + â˜ƒ - â˜ƒ;
      }
   }

   public void setQuadSortOrigin(float var1, float var2, float var3) {
      if (this.mode == VertexFormat.Mode.QUADS) {
         if (this.sortX != â˜ƒ || this.sortY != â˜ƒ || this.sortZ != â˜ƒ) {
            this.sortX = â˜ƒ;
            this.sortY = â˜ƒ;
            this.sortZ = â˜ƒ;
            if (this.sortingPoints == null) {
               this.sortingPoints = this.makeQuadSortingPoints();
            }
         }
      }
   }

   public BufferBuilder.SortState getSortState() {
      return new BufferBuilder.SortState(this.mode, this.vertices, this.sortingPoints, this.sortX, this.sortY, this.sortZ);
   }

   public void restoreSortState(BufferBuilder.SortState var1) {
      this.buffer.clear();
      this.mode = â˜ƒ.mode;
      this.vertices = â˜ƒ.vertices;
      this.nextElementByte = this.totalRenderedBytes;
      this.sortingPoints = â˜ƒ.sortingPoints;
      this.sortX = â˜ƒ.sortX;
      this.sortY = â˜ƒ.sortY;
      this.sortZ = â˜ƒ.sortZ;
      this.indexOnly = true;
   }

   public void begin(VertexFormat.Mode var1, VertexFormat var2) {
      if (this.building) {
         throw new IllegalStateException("Already building!");
      } else {
         this.building = true;
         this.mode = â˜ƒ;
         this.switchFormat(â˜ƒ);
         this.currentElement = (VertexFormatElement)â˜ƒ.getElements().get(0);
         this.elementIndex = 0;
         this.buffer.clear();
      }
   }

   private void switchFormat(VertexFormat var1) {
      if (this.format != â˜ƒ) {
         this.format = â˜ƒ;
         boolean â˜ƒ = â˜ƒ == DefaultVertexFormat.NEW_ENTITY;
         boolean â˜ƒx = â˜ƒ == DefaultVertexFormat.BLOCK;
         this.fastFormat = â˜ƒ || â˜ƒx;
         this.fullFormat = â˜ƒ;
      }
   }

   private IntConsumer intConsumer(VertexFormat.IndexType var1) {
      switch(â˜ƒ) {
         case BYTE:
            return var1x -> this.buffer.put((byte)var1x);
         case SHORT:
            return var1x -> this.buffer.putShort((short)var1x);
         case INT:
         default:
            return var1x -> this.buffer.putInt(var1x);
      }
   }

   private Vector3f[] makeQuadSortingPoints() {
      FloatBuffer â˜ƒ = this.buffer.asFloatBuffer();
      int â˜ƒx = this.totalRenderedBytes / 4;
      int â˜ƒxx = this.format.getIntegerSize();
      int â˜ƒxxx = â˜ƒxx * this.mode.primitiveStride;
      int â˜ƒxxxx = this.vertices / this.mode.primitiveStride;
      Vector3f[] â˜ƒxxxxx = new Vector3f[â˜ƒxxxx];

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
         float â˜ƒxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + 0);
         float â˜ƒxxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + 1);
         float â˜ƒxxxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + 2);
         float â˜ƒxxxxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + â˜ƒxx * 2 + 0);
         float â˜ƒxxxxxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + â˜ƒxx * 2 + 1);
         float â˜ƒxxxxxxxxxxxx = â˜ƒ.get(â˜ƒx + â˜ƒxxxxxx * â˜ƒxxx + â˜ƒxx * 2 + 2);
         float â˜ƒxxxxxxxxxxxxx = (â˜ƒxxxxxxx + â˜ƒxxxxxxxxxx) / 2.0F;
         float â˜ƒxxxxxxxxxxxxxx = (â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxxx) / 2.0F;
         float â˜ƒxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxxxx) / 2.0F;
         â˜ƒxxxxx[â˜ƒxxxxxx] = new Vector3f(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
      }

      return â˜ƒxxxxx;
   }

   private void putSortedQuadIndices(VertexFormat.IndexType var1) {
      float[] â˜ƒ = new float[this.sortingPoints.length];
      int[] â˜ƒx = new int[this.sortingPoints.length];

      for(int â˜ƒxx = 0; â˜ƒxx < this.sortingPoints.length; â˜ƒx[â˜ƒxx] = â˜ƒxx++) {
         float â˜ƒxxx = this.sortingPoints[â˜ƒxx].x() - this.sortX;
         float â˜ƒxxxx = this.sortingPoints[â˜ƒxx].y() - this.sortY;
         float â˜ƒxxxxx = this.sortingPoints[â˜ƒxx].z() - this.sortZ;
         â˜ƒ[â˜ƒxx] = â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx;
      }

      IntArrays.mergeSort(â˜ƒx, (var1x, var2x) -> Floats.compare(â˜ƒ[var2x], â˜ƒ[var1x]));
      IntConsumer â˜ƒxx = this.intConsumer(â˜ƒ);
      this.buffer.position(this.nextElementByte);

      for(int â˜ƒxxx : â˜ƒx) {
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 0);
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 1);
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 2);
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 2);
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 3);
         â˜ƒxx.accept(â˜ƒxxx * this.mode.primitiveStride + 0);
      }
   }

   public void end() {
      if (!this.building) {
         throw new IllegalStateException("Not building!");
      } else {
         int â˜ƒx = this.mode.indexCount(this.vertices);
         VertexFormat.IndexType â˜ƒxx = VertexFormat.IndexType.least(â˜ƒx);
         boolean â˜ƒ;
         if (this.sortingPoints != null) {
            int â˜ƒxxx = Mth.roundToward(â˜ƒx * â˜ƒxx.bytes, 4);
            this.ensureCapacity(â˜ƒxxx);
            this.putSortedQuadIndices(â˜ƒxx);
            â˜ƒ = false;
            this.nextElementByte += â˜ƒxxx;
            this.totalRenderedBytes += this.vertices * this.format.getVertexSize() + â˜ƒxxx;
         } else {
            â˜ƒ = true;
            this.totalRenderedBytes += this.vertices * this.format.getVertexSize();
         }

         this.building = false;
         this.drawStates.add(new BufferBuilder.DrawState(this.format, this.vertices, â˜ƒx, this.mode, â˜ƒxx, this.indexOnly, â˜ƒ));
         this.vertices = 0;
         this.currentElement = null;
         this.elementIndex = 0;
         this.sortingPoints = null;
         this.sortX = Float.NaN;
         this.sortY = Float.NaN;
         this.sortZ = Float.NaN;
         this.indexOnly = false;
      }
   }

   @Override
   public void putByte(int var1, byte var2) {
      this.buffer.put(this.nextElementByte + â˜ƒ, â˜ƒ);
   }

   @Override
   public void putShort(int var1, short var2) {
      this.buffer.putShort(this.nextElementByte + â˜ƒ, â˜ƒ);
   }

   @Override
   public void putFloat(int var1, float var2) {
      this.buffer.putFloat(this.nextElementByte + â˜ƒ, â˜ƒ);
   }

   @Override
   public void endVertex() {
      if (this.elementIndex != 0) {
         throw new IllegalStateException("Not filled all elements of the vertex");
      } else {
         ++this.vertices;
         this.ensureVertexCapacity();
         if (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP) {
            int â˜ƒ = this.format.getVertexSize();
            this.buffer.position(this.nextElementByte);
            ByteBuffer â˜ƒx = this.buffer.duplicate();
            â˜ƒx.position(this.nextElementByte - â˜ƒ).limit(this.nextElementByte);
            this.buffer.put(â˜ƒx);
            this.nextElementByte += â˜ƒ;
            ++this.vertices;
            this.ensureVertexCapacity();
         }
      }
   }

   @Override
   public void nextElement() {
      ImmutableList<VertexFormatElement> â˜ƒ = this.format.getElements();
      this.elementIndex = (this.elementIndex + 1) % â˜ƒ.size();
      this.nextElementByte += this.currentElement.getByteSize();
      VertexFormatElement â˜ƒx = (VertexFormatElement)â˜ƒ.get(this.elementIndex);
      this.currentElement = â˜ƒx;
      if (â˜ƒx.getUsage() == VertexFormatElement.Usage.PADDING) {
         this.nextElement();
      }

      if (this.defaultColorSet && this.currentElement.getUsage() == VertexFormatElement.Usage.COLOR) {
         BufferVertexConsumer.super.color(this.defaultR, this.defaultG, this.defaultB, this.defaultA);
      }
   }

   @Override
   public VertexConsumer color(int var1, int var2, int var3, int var4) {
      if (this.defaultColorSet) {
         throw new IllegalStateException();
      } else {
         return BufferVertexConsumer.super.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void vertex(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      float var12,
      float var13,
      float var14
   ) {
      if (this.defaultColorSet) {
         throw new IllegalStateException();
      } else if (this.fastFormat) {
         this.putFloat(0, â˜ƒ);
         this.putFloat(4, â˜ƒ);
         this.putFloat(8, â˜ƒ);
         this.putByte(12, (byte)((int)(â˜ƒ * 255.0F)));
         this.putByte(13, (byte)((int)(â˜ƒ * 255.0F)));
         this.putByte(14, (byte)((int)(â˜ƒ * 255.0F)));
         this.putByte(15, (byte)((int)(â˜ƒ * 255.0F)));
         this.putFloat(16, â˜ƒ);
         this.putFloat(20, â˜ƒ);
         int â˜ƒ;
         if (this.fullFormat) {
            this.putShort(24, (short)(â˜ƒ & 65535));
            this.putShort(26, (short)(â˜ƒ >> 16 & 65535));
            â˜ƒ = 28;
         } else {
            â˜ƒ = 24;
         }

         this.putShort(â˜ƒ + 0, (short)(â˜ƒ & 65535));
         this.putShort(â˜ƒ + 2, (short)(â˜ƒ >> 16 & 65535));
         this.putByte(â˜ƒ + 4, BufferVertexConsumer.normalIntValue(â˜ƒ));
         this.putByte(â˜ƒ + 5, BufferVertexConsumer.normalIntValue(â˜ƒ));
         this.putByte(â˜ƒ + 6, BufferVertexConsumer.normalIntValue(â˜ƒ));
         this.nextElementByte += â˜ƒ + 8;
         this.endVertex();
      } else {
         super.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public Pair<BufferBuilder.DrawState, ByteBuffer> popNextBuffer() {
      BufferBuilder.DrawState â˜ƒ = (BufferBuilder.DrawState)this.drawStates.get(this.lastPoppedStateIndex++);
      this.buffer.position(this.totalUploadedBytes);
      this.totalUploadedBytes += Mth.roundToward(â˜ƒ.bufferSize(), 4);
      this.buffer.limit(this.totalUploadedBytes);
      if (this.lastPoppedStateIndex == this.drawStates.size() && this.vertices == 0) {
         this.clear();
      }

      ByteBuffer â˜ƒ = this.buffer.slice();
      this.buffer.clear();
      return Pair.of(â˜ƒ, â˜ƒ);
   }

   public void clear() {
      if (this.totalRenderedBytes != this.totalUploadedBytes) {
         LOGGER.warn("Bytes mismatch {} {}", this.totalRenderedBytes, this.totalUploadedBytes);
      }

      this.discard();
   }

   public void discard() {
      this.totalRenderedBytes = 0;
      this.totalUploadedBytes = 0;
      this.nextElementByte = 0;
      this.drawStates.clear();
      this.lastPoppedStateIndex = 0;
   }

   @Override
   public VertexFormatElement currentElement() {
      if (this.currentElement == null) {
         throw new IllegalStateException("BufferBuilder not started");
      } else {
         return this.currentElement;
      }
   }

   public boolean building() {
      return this.building;
   }

   public static final class DrawState {
      private final VertexFormat format;
      private final int vertexCount;
      private final int indexCount;
      private final VertexFormat.Mode mode;
      private final VertexFormat.IndexType indexType;
      private final boolean indexOnly;
      private final boolean sequentialIndex;

      DrawState(VertexFormat var1, int var2, int var3, VertexFormat.Mode var4, VertexFormat.IndexType var5, boolean var6, boolean var7) {
         this.format = â˜ƒ;
         this.vertexCount = â˜ƒ;
         this.indexCount = â˜ƒ;
         this.mode = â˜ƒ;
         this.indexType = â˜ƒ;
         this.indexOnly = â˜ƒ;
         this.sequentialIndex = â˜ƒ;
      }

      public VertexFormat format() {
         return this.format;
      }

      public int vertexCount() {
         return this.vertexCount;
      }

      public int indexCount() {
         return this.indexCount;
      }

      public VertexFormat.Mode mode() {
         return this.mode;
      }

      public VertexFormat.IndexType indexType() {
         return this.indexType;
      }

      public int vertexBufferSize() {
         return this.vertexCount * this.format.getVertexSize();
      }

      private int indexBufferSize() {
         return this.sequentialIndex ? 0 : this.indexCount * this.indexType.bytes;
      }

      public int bufferSize() {
         return this.vertexBufferSize() + this.indexBufferSize();
      }

      public boolean indexOnly() {
         return this.indexOnly;
      }

      public boolean sequentialIndex() {
         return this.sequentialIndex;
      }
   }

   public static class SortState {
      final VertexFormat.Mode mode;
      final int vertices;
      @Nullable
      final Vector3f[] sortingPoints;
      final float sortX;
      final float sortY;
      final float sortZ;

      SortState(VertexFormat.Mode var1, int var2, @Nullable Vector3f[] var3, float var4, float var5, float var6) {
         this.mode = â˜ƒ;
         this.vertices = â˜ƒ;
         this.sortingPoints = â˜ƒ;
         this.sortX = â˜ƒ;
         this.sortY = â˜ƒ;
         this.sortZ = â˜ƒ;
      }
   }
}
