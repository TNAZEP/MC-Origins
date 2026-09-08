package net.minecraft.core;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

public class SectionPos extends Vec3i {
   public static final int SECTION_BITS = 4;
   public static final int SECTION_SIZE = 16;
   private static final int SECTION_MASK = 15;
   public static final int SECTION_HALF_SIZE = 8;
   public static final int SECTION_MAX_INDEX = 15;
   private static final int PACKED_X_LENGTH = 22;
   private static final int PACKED_Y_LENGTH = 20;
   private static final int PACKED_Z_LENGTH = 22;
   private static final long PACKED_X_MASK = 4194303L;
   private static final long PACKED_Y_MASK = 1048575L;
   private static final long PACKED_Z_MASK = 4194303L;
   private static final int Y_OFFSET = 0;
   private static final int Z_OFFSET = 20;
   private static final int X_OFFSET = 42;
   private static final int RELATIVE_X_SHIFT = 8;
   private static final int RELATIVE_Y_SHIFT = 0;
   private static final int RELATIVE_Z_SHIFT = 4;

   SectionPos(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static SectionPos of(int var0, int var1, int var2) {
      return new SectionPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static SectionPos of(BlockPos var0) {
      return new SectionPos(blockToSectionCoord(â˜ƒ.getX()), blockToSectionCoord(â˜ƒ.getY()), blockToSectionCoord(â˜ƒ.getZ()));
   }

   public static SectionPos of(ChunkPos var0, int var1) {
      return new SectionPos(â˜ƒ.x, â˜ƒ, â˜ƒ.z);
   }

   public static SectionPos of(Entity var0) {
      return new SectionPos(blockToSectionCoord(â˜ƒ.getBlockX()), blockToSectionCoord(â˜ƒ.getBlockY()), blockToSectionCoord(â˜ƒ.getBlockZ()));
   }

   public static SectionPos of(long var0) {
      return new SectionPos(x(â˜ƒ), y(â˜ƒ), z(â˜ƒ));
   }

   public static SectionPos bottomOf(ChunkAccess var0) {
      return of(â˜ƒ.getPos(), â˜ƒ.getMinSection());
   }

   public static long offset(long var0, Direction var2) {
      return offset(â˜ƒ, â˜ƒ.getStepX(), â˜ƒ.getStepY(), â˜ƒ.getStepZ());
   }

   public static long offset(long var0, int var2, int var3, int var4) {
      return asLong(x(â˜ƒ) + â˜ƒ, y(â˜ƒ) + â˜ƒ, z(â˜ƒ) + â˜ƒ);
   }

   public static int posToSectionCoord(double var0) {
      return blockToSectionCoord(Mth.floor(â˜ƒ));
   }

   public static int blockToSectionCoord(int var0) {
      return â˜ƒ >> 4;
   }

   public static int sectionRelative(int var0) {
      return â˜ƒ & 15;
   }

   public static short sectionRelativePos(BlockPos var0) {
      int â˜ƒ = sectionRelative(â˜ƒ.getX());
      int â˜ƒx = sectionRelative(â˜ƒ.getY());
      int â˜ƒxx = sectionRelative(â˜ƒ.getZ());
      return (short)(â˜ƒ << 8 | â˜ƒxx << 4 | â˜ƒx << 0);
   }

   public static int sectionRelativeX(short var0) {
      return â˜ƒ >>> 8 & 15;
   }

   public static int sectionRelativeY(short var0) {
      return â˜ƒ >>> 0 & 15;
   }

   public static int sectionRelativeZ(short var0) {
      return â˜ƒ >>> 4 & 15;
   }

   public int relativeToBlockX(short var1) {
      return this.minBlockX() + sectionRelativeX(â˜ƒ);
   }

   public int relativeToBlockY(short var1) {
      return this.minBlockY() + sectionRelativeY(â˜ƒ);
   }

   public int relativeToBlockZ(short var1) {
      return this.minBlockZ() + sectionRelativeZ(â˜ƒ);
   }

   public BlockPos relativeToBlockPos(short var1) {
      return new BlockPos(this.relativeToBlockX(â˜ƒ), this.relativeToBlockY(â˜ƒ), this.relativeToBlockZ(â˜ƒ));
   }

   public static int sectionToBlockCoord(int var0) {
      return â˜ƒ << 4;
   }

   public static int sectionToBlockCoord(int var0, int var1) {
      return sectionToBlockCoord(â˜ƒ) + â˜ƒ;
   }

   public static int x(long var0) {
      return (int)(â˜ƒ << 0 >> 42);
   }

   public static int y(long var0) {
      return (int)(â˜ƒ << 44 >> 44);
   }

   public static int z(long var0) {
      return (int)(â˜ƒ << 22 >> 42);
   }

   public int x() {
      return this.getX();
   }

   public int y() {
      return this.getY();
   }

   public int z() {
      return this.getZ();
   }

   public int minBlockX() {
      return sectionToBlockCoord(this.x());
   }

   public int minBlockY() {
      return sectionToBlockCoord(this.y());
   }

   public int minBlockZ() {
      return sectionToBlockCoord(this.z());
   }

   public int maxBlockX() {
      return sectionToBlockCoord(this.x(), 15);
   }

   public int maxBlockY() {
      return sectionToBlockCoord(this.y(), 15);
   }

   public int maxBlockZ() {
      return sectionToBlockCoord(this.z(), 15);
   }

   public static long blockToSection(long var0) {
      return asLong(blockToSectionCoord(BlockPos.getX(â˜ƒ)), blockToSectionCoord(BlockPos.getY(â˜ƒ)), blockToSectionCoord(BlockPos.getZ(â˜ƒ)));
   }

   public static long getZeroNode(long var0) {
      return â˜ƒ & -1048576L;
   }

   public BlockPos origin() {
      return new BlockPos(sectionToBlockCoord(this.x()), sectionToBlockCoord(this.y()), sectionToBlockCoord(this.z()));
   }

   public BlockPos center() {
      int â˜ƒ = 8;
      return this.origin().offset(8, 8, 8);
   }

   public ChunkPos chunk() {
      return new ChunkPos(this.x(), this.z());
   }

   public static long asLong(BlockPos var0) {
      return asLong(blockToSectionCoord(â˜ƒ.getX()), blockToSectionCoord(â˜ƒ.getY()), blockToSectionCoord(â˜ƒ.getZ()));
   }

   public static long asLong(int var0, int var1, int var2) {
      long â˜ƒ = 0L;
      â˜ƒ |= ((long)â˜ƒ & 4194303L) << 42;
      â˜ƒ |= ((long)â˜ƒ & 1048575L) << 0;
      return â˜ƒ | ((long)â˜ƒ & 4194303L) << 20;
   }

   public long asLong() {
      return asLong(this.x(), this.y(), this.z());
   }

   public SectionPos offset(int var1, int var2, int var3) {
      return â˜ƒ == 0 && â˜ƒ == 0 && â˜ƒ == 0 ? this : new SectionPos(this.x() + â˜ƒ, this.y() + â˜ƒ, this.z() + â˜ƒ);
   }

   public Stream<BlockPos> blocksInside() {
      return BlockPos.betweenClosedStream(this.minBlockX(), this.minBlockY(), this.minBlockZ(), this.maxBlockX(), this.maxBlockY(), this.maxBlockZ());
   }

   public static Stream<SectionPos> cube(SectionPos var0, int var1) {
      int â˜ƒ = â˜ƒ.x();
      int â˜ƒx = â˜ƒ.y();
      int â˜ƒxx = â˜ƒ.z();
      return betweenClosedStream(â˜ƒ - â˜ƒ, â˜ƒx - â˜ƒ, â˜ƒxx - â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒx + â˜ƒ, â˜ƒxx + â˜ƒ);
   }

   public static Stream<SectionPos> aroundChunk(ChunkPos var0, int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ.x;
      int â˜ƒx = â˜ƒ.z;
      return betweenClosedStream(â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒx - â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ - 1, â˜ƒx + â˜ƒ);
   }

   public static Stream<SectionPos> betweenClosedStream(final int var0, final int var1, final int var2, final int var3, final int var4, final int var5) {
      return StreamSupport.stream(new AbstractSpliterator<SectionPos>((long)((â˜ƒ - â˜ƒ + 1) * (â˜ƒ - â˜ƒ + 1) * (â˜ƒ - â˜ƒ + 1)), 64) {
         final Cursor3D cursor = new Cursor3D(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

         public boolean tryAdvance(Consumer<? super SectionPos> var1x) {
            if (this.cursor.advance()) {
               â˜ƒ.accept(new SectionPos(this.cursor.nextX(), this.cursor.nextY(), this.cursor.nextZ()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }
}
