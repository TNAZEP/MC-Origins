package net.minecraft.world.level.levelgen.structure;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoundingBox {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<BoundingBox> CODEC = Codec.INT_STREAM
      .<BoundingBox>comapFlatMap(
         var0 -> Util.fixedSize(var0, 6).map(var0x -> new BoundingBox(var0x[0], var0x[1], var0x[2], var0x[3], var0x[4], var0x[5])),
         var0 -> IntStream.of(new int[]{var0.minX, var0.minY, var0.minZ, var0.maxX, var0.maxY, var0.maxZ})
      )
      .stable();
   private int minX;
   private int minY;
   private int minZ;
   private int maxX;
   private int maxY;
   private int maxZ;

   public BoundingBox(BlockPos var1) {
      this(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public BoundingBox(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.minX = â˜ƒ;
      this.minY = â˜ƒ;
      this.minZ = â˜ƒ;
      this.maxX = â˜ƒ;
      this.maxY = â˜ƒ;
      this.maxZ = â˜ƒ;
      if (â˜ƒ < â˜ƒ || â˜ƒ < â˜ƒ || â˜ƒ < â˜ƒ) {
         String â˜ƒ = "Invalid bounding box data, inverted bounds for: " + this;
         if (SharedConstants.IS_RUNNING_IN_IDE) {
            throw new IllegalStateException(â˜ƒ);
         }

         LOGGER.error(â˜ƒ);
         this.minX = Math.min(â˜ƒ, â˜ƒ);
         this.minY = Math.min(â˜ƒ, â˜ƒ);
         this.minZ = Math.min(â˜ƒ, â˜ƒ);
         this.maxX = Math.max(â˜ƒ, â˜ƒ);
         this.maxY = Math.max(â˜ƒ, â˜ƒ);
         this.maxZ = Math.max(â˜ƒ, â˜ƒ);
      }
   }

   public static BoundingBox fromCorners(Vec3i var0, Vec3i var1) {
      return new BoundingBox(
         Math.min(â˜ƒ.getX(), â˜ƒ.getX()),
         Math.min(â˜ƒ.getY(), â˜ƒ.getY()),
         Math.min(â˜ƒ.getZ(), â˜ƒ.getZ()),
         Math.max(â˜ƒ.getX(), â˜ƒ.getX()),
         Math.max(â˜ƒ.getY(), â˜ƒ.getY()),
         Math.max(â˜ƒ.getZ(), â˜ƒ.getZ())
      );
   }

   public static BoundingBox infinite() {
      return new BoundingBox(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public static BoundingBox orientBox(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Direction var9) {
      switch(â˜ƒ) {
         case SOUTH:
         default:
            return new BoundingBox(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ);
         case NORTH:
            return new BoundingBox(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ - â˜ƒ + 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ);
         case WEST:
            return new BoundingBox(â˜ƒ - â˜ƒ + 1 + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ);
         case EAST:
            return new BoundingBox(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ, â˜ƒ + â˜ƒ - 1 + â˜ƒ);
      }
   }

   public boolean intersects(BoundingBox var1) {
      return this.maxX >= â˜ƒ.minX && this.minX <= â˜ƒ.maxX && this.maxZ >= â˜ƒ.minZ && this.minZ <= â˜ƒ.maxZ && this.maxY >= â˜ƒ.minY && this.minY <= â˜ƒ.maxY;
   }

   public boolean intersects(int var1, int var2, int var3, int var4) {
      return this.maxX >= â˜ƒ && this.minX <= â˜ƒ && this.maxZ >= â˜ƒ && this.minZ <= â˜ƒ;
   }

   public static Optional<BoundingBox> encapsulatingPositions(Iterable<BlockPos> var0) {
      Iterator<BlockPos> â˜ƒ = â˜ƒ.iterator();
      if (!â˜ƒ.hasNext()) {
         return Optional.empty();
      } else {
         BoundingBox â˜ƒ = new BoundingBox((BlockPos)â˜ƒ.next());
         â˜ƒ.forEachRemaining(â˜ƒ::encapsulate);
         return Optional.of(â˜ƒ);
      }
   }

   public static Optional<BoundingBox> encapsulatingBoxes(Iterable<BoundingBox> var0) {
      Iterator<BoundingBox> â˜ƒ = â˜ƒ.iterator();
      if (!â˜ƒ.hasNext()) {
         return Optional.empty();
      } else {
         BoundingBox â˜ƒ = (BoundingBox)â˜ƒ.next();
         BoundingBox â˜ƒx = new BoundingBox(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ);
         â˜ƒ.forEachRemaining(â˜ƒx::encapsulate);
         return Optional.of(â˜ƒx);
      }
   }

   public BoundingBox encapsulate(BoundingBox var1) {
      this.minX = Math.min(this.minX, â˜ƒ.minX);
      this.minY = Math.min(this.minY, â˜ƒ.minY);
      this.minZ = Math.min(this.minZ, â˜ƒ.minZ);
      this.maxX = Math.max(this.maxX, â˜ƒ.maxX);
      this.maxY = Math.max(this.maxY, â˜ƒ.maxY);
      this.maxZ = Math.max(this.maxZ, â˜ƒ.maxZ);
      return this;
   }

   public BoundingBox encapsulate(BlockPos var1) {
      this.minX = Math.min(this.minX, â˜ƒ.getX());
      this.minY = Math.min(this.minY, â˜ƒ.getY());
      this.minZ = Math.min(this.minZ, â˜ƒ.getZ());
      this.maxX = Math.max(this.maxX, â˜ƒ.getX());
      this.maxY = Math.max(this.maxY, â˜ƒ.getY());
      this.maxZ = Math.max(this.maxZ, â˜ƒ.getZ());
      return this;
   }

   public BoundingBox inflate(int var1) {
      this.minX -= â˜ƒ;
      this.minY -= â˜ƒ;
      this.minZ -= â˜ƒ;
      this.maxX += â˜ƒ;
      this.maxY += â˜ƒ;
      this.maxZ += â˜ƒ;
      return this;
   }

   public BoundingBox move(int var1, int var2, int var3) {
      this.minX += â˜ƒ;
      this.minY += â˜ƒ;
      this.minZ += â˜ƒ;
      this.maxX += â˜ƒ;
      this.maxY += â˜ƒ;
      this.maxZ += â˜ƒ;
      return this;
   }

   public BoundingBox move(Vec3i var1) {
      return this.move(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public BoundingBox moved(int var1, int var2, int var3) {
      return new BoundingBox(this.minX + â˜ƒ, this.minY + â˜ƒ, this.minZ + â˜ƒ, this.maxX + â˜ƒ, this.maxY + â˜ƒ, this.maxZ + â˜ƒ);
   }

   public boolean isInside(Vec3i var1) {
      return â˜ƒ.getX() >= this.minX
         && â˜ƒ.getX() <= this.maxX
         && â˜ƒ.getZ() >= this.minZ
         && â˜ƒ.getZ() <= this.maxZ
         && â˜ƒ.getY() >= this.minY
         && â˜ƒ.getY() <= this.maxY;
   }

   public Vec3i getLength() {
      return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
   }

   public int getXSpan() {
      return this.maxX - this.minX + 1;
   }

   public int getYSpan() {
      return this.maxY - this.minY + 1;
   }

   public int getZSpan() {
      return this.maxZ - this.minZ + 1;
   }

   public BlockPos getCenter() {
      return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
   }

   public void forAllCorners(Consumer<BlockPos> var1) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      â˜ƒ.accept(â˜ƒ.set(this.maxX, this.maxY, this.maxZ));
      â˜ƒ.accept(â˜ƒ.set(this.minX, this.maxY, this.maxZ));
      â˜ƒ.accept(â˜ƒ.set(this.maxX, this.minY, this.maxZ));
      â˜ƒ.accept(â˜ƒ.set(this.minX, this.minY, this.maxZ));
      â˜ƒ.accept(â˜ƒ.set(this.maxX, this.maxY, this.minZ));
      â˜ƒ.accept(â˜ƒ.set(this.minX, this.maxY, this.minZ));
      â˜ƒ.accept(â˜ƒ.set(this.maxX, this.minY, this.minZ));
      â˜ƒ.accept(â˜ƒ.set(this.minX, this.minY, this.minZ));
   }

   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("minX", this.minX)
         .add("minY", this.minY)
         .add("minZ", this.minZ)
         .add("maxX", this.maxX)
         .add("maxY", this.maxY)
         .add("maxZ", this.maxZ)
         .toString();
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof BoundingBox)) {
         return false;
      } else {
         BoundingBox â˜ƒ = (BoundingBox)â˜ƒ;
         return this.minX == â˜ƒ.minX
            && this.minY == â˜ƒ.minY
            && this.minZ == â˜ƒ.minZ
            && this.maxX == â˜ƒ.maxX
            && this.maxY == â˜ƒ.maxY
            && this.maxZ == â˜ƒ.maxZ;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
   }

   public int minX() {
      return this.minX;
   }

   public int minY() {
      return this.minY;
   }

   public int minZ() {
      return this.minZ;
   }

   public int maxX() {
      return this.maxX;
   }

   public int maxY() {
      return this.maxY;
   }

   public int maxZ() {
      return this.maxZ;
   }
}
