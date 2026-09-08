package net.minecraft.world.level;

import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CollisionSpliterator extends AbstractSpliterator<VoxelShape> {
   @Nullable
   private final Entity source;
   private final AABB box;
   private final CollisionContext context;
   private final Cursor3D cursor;
   private final BlockPos.MutableBlockPos pos;
   private final VoxelShape entityShape;
   private final CollisionGetter collisionGetter;
   private boolean needsBorderCheck;
   private final BiPredicate<BlockState, BlockPos> predicate;

   public CollisionSpliterator(CollisionGetter var1, @Nullable Entity var2, AABB var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, (var0, var1x) -> true);
   }

   public CollisionSpliterator(CollisionGetter var1, @Nullable Entity var2, AABB var3, BiPredicate<BlockState, BlockPos> var4) {
      super(Long.MAX_VALUE, 1280);
      this.context = â˜ƒ == null ? CollisionContext.empty() : CollisionContext.of(â˜ƒ);
      this.pos = new BlockPos.MutableBlockPos();
      this.entityShape = Shapes.create(â˜ƒ);
      this.collisionGetter = â˜ƒ;
      this.needsBorderCheck = â˜ƒ != null;
      this.source = â˜ƒ;
      this.box = â˜ƒ;
      this.predicate = â˜ƒ;
      int â˜ƒ = Mth.floor(â˜ƒ.minX - 1.0E-7) - 1;
      int â˜ƒx = Mth.floor(â˜ƒ.maxX + 1.0E-7) + 1;
      int â˜ƒxx = Mth.floor(â˜ƒ.minY - 1.0E-7) - 1;
      int â˜ƒxxx = Mth.floor(â˜ƒ.maxY + 1.0E-7) + 1;
      int â˜ƒxxxx = Mth.floor(â˜ƒ.minZ - 1.0E-7) - 1;
      int â˜ƒxxxxx = Mth.floor(â˜ƒ.maxZ + 1.0E-7) + 1;
      this.cursor = new Cursor3D(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒx, â˜ƒxxx, â˜ƒxxxxx);
   }

   public boolean tryAdvance(Consumer<? super VoxelShape> var1) {
      return this.needsBorderCheck && this.worldBorderCheck(â˜ƒ) || this.collisionCheck(â˜ƒ);
   }

   boolean collisionCheck(Consumer<? super VoxelShape> var1) {
      while(this.cursor.advance()) {
         int â˜ƒ = this.cursor.nextX();
         int â˜ƒx = this.cursor.nextY();
         int â˜ƒxx = this.cursor.nextZ();
         int â˜ƒxxx = this.cursor.getNextType();
         if (â˜ƒxxx != 3) {
            BlockGetter â˜ƒxxxx = this.getChunk(â˜ƒ, â˜ƒxx);
            if (â˜ƒxxxx != null) {
               this.pos.set(â˜ƒ, â˜ƒx, â˜ƒxx);
               BlockState â˜ƒxxxxx = â˜ƒxxxx.getBlockState(this.pos);
               if (this.predicate.test(â˜ƒxxxxx, this.pos)
                  && (â˜ƒxxx != 1 || â˜ƒxxxxx.hasLargeCollisionShape())
                  && (â˜ƒxxx != 2 || â˜ƒxxxxx.is(Blocks.MOVING_PISTON))) {
                  VoxelShape â˜ƒxxxxxx = â˜ƒxxxxx.getCollisionShape(this.collisionGetter, this.pos, this.context);
                  if (â˜ƒxxxxxx == Shapes.block()) {
                     if (this.box.intersects((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒ + 1.0, (double)â˜ƒx + 1.0, (double)â˜ƒxx + 1.0)) {
                        â˜ƒ.accept(â˜ƒxxxxxx.move((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx));
                        return true;
                     }
                  } else {
                     VoxelShape â˜ƒxxxxxx = â˜ƒxxxxxx.move((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx);
                     if (Shapes.joinIsNotEmpty(â˜ƒxxxxxx, this.entityShape, BooleanOp.AND)) {
                        â˜ƒ.accept(â˜ƒxxxxxx);
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockGetter getChunk(int var1, int var2) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ);
      return this.collisionGetter.getChunkForCollisions(â˜ƒ, â˜ƒx);
   }

   boolean worldBorderCheck(Consumer<? super VoxelShape> var1) {
      Objects.requireNonNull(this.source);
      this.needsBorderCheck = false;
      WorldBorder â˜ƒ = this.collisionGetter.getWorldBorder();
      AABB â˜ƒx = this.source.getBoundingBox();
      if (!isBoxFullyWithinWorldBorder(â˜ƒ, â˜ƒx)) {
         VoxelShape â˜ƒxx = â˜ƒ.getCollisionShape();
         if (!isOutsideBorder(â˜ƒxx, â˜ƒx) && isCloseToBorder(â˜ƒxx, â˜ƒx)) {
            â˜ƒ.accept(â˜ƒxx);
            return true;
         }
      }

      return false;
   }

   private static boolean isCloseToBorder(VoxelShape var0, AABB var1) {
      return Shapes.joinIsNotEmpty(â˜ƒ, Shapes.create(â˜ƒ.inflate(1.0E-7)), BooleanOp.AND);
   }

   private static boolean isOutsideBorder(VoxelShape var0, AABB var1) {
      return Shapes.joinIsNotEmpty(â˜ƒ, Shapes.create(â˜ƒ.deflate(1.0E-7)), BooleanOp.AND);
   }

   public static boolean isBoxFullyWithinWorldBorder(WorldBorder var0, AABB var1) {
      double â˜ƒ = (double)Mth.floor(â˜ƒ.getMinX());
      double â˜ƒx = (double)Mth.floor(â˜ƒ.getMinZ());
      double â˜ƒxx = (double)Mth.ceil(â˜ƒ.getMaxX());
      double â˜ƒxxx = (double)Mth.ceil(â˜ƒ.getMaxZ());
      return â˜ƒ.minX > â˜ƒ
         && â˜ƒ.minX < â˜ƒxx
         && â˜ƒ.minZ > â˜ƒx
         && â˜ƒ.minZ < â˜ƒxxx
         && â˜ƒ.maxX > â˜ƒ
         && â˜ƒ.maxX < â˜ƒxx
         && â˜ƒ.maxZ > â˜ƒx
         && â˜ƒ.maxZ < â˜ƒxxx;
   }
}
