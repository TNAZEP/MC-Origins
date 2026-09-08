package net.minecraft.world.level.pathfinder;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FlyNodeEvaluator extends WalkNodeEvaluator {
   private final Long2ObjectMap<BlockPathTypes> pathTypeByPosCache = new Long2ObjectOpenHashMap();

   @Override
   public void prepare(PathNavigationRegion var1, Mob var2) {
      super.prepare(â˜ƒ, â˜ƒ);
      this.pathTypeByPosCache.clear();
      this.oldWaterCost = â˜ƒ.getPathfindingMalus(BlockPathTypes.WATER);
   }

   @Override
   public void done() {
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
      this.pathTypeByPosCache.clear();
      super.done();
   }

   @Override
   public Node getStart() {
      int â˜ƒ;
      if (this.canFloat() && this.mob.isInWater()) {
         â˜ƒ = this.mob.getBlockY();
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos(this.mob.getX(), (double)â˜ƒ, this.mob.getZ());

         for(BlockState â˜ƒxx = this.level.getBlockState(â˜ƒx); â˜ƒxx.is(Blocks.WATER); â˜ƒxx = this.level.getBlockState(â˜ƒx)) {
            â˜ƒx.set(this.mob.getX(), (double)(++â˜ƒ), this.mob.getZ());
         }
      } else {
         â˜ƒ = Mth.floor(this.mob.getY() + 0.5);
      }

      BlockPos â˜ƒ = this.mob.blockPosition();
      BlockPathTypes â˜ƒx = this.getCachedBlockPathType(â˜ƒ.getX(), â˜ƒ, â˜ƒ.getZ());
      if (this.mob.getPathfindingMalus(â˜ƒx) < 0.0F) {
         for(BlockPos â˜ƒxx : ImmutableSet.of(
            new BlockPos(this.mob.getBoundingBox().minX, (double)â˜ƒ, this.mob.getBoundingBox().minZ),
            new BlockPos(this.mob.getBoundingBox().minX, (double)â˜ƒ, this.mob.getBoundingBox().maxZ),
            new BlockPos(this.mob.getBoundingBox().maxX, (double)â˜ƒ, this.mob.getBoundingBox().minZ),
            new BlockPos(this.mob.getBoundingBox().maxX, (double)â˜ƒ, this.mob.getBoundingBox().maxZ)
         )) {
            BlockPathTypes â˜ƒxxx = this.getCachedBlockPathType(â˜ƒ.getX(), â˜ƒ, â˜ƒ.getZ());
            if (this.mob.getPathfindingMalus(â˜ƒxxx) >= 0.0F) {
               return super.getNode(â˜ƒxx.getX(), â˜ƒxx.getY(), â˜ƒxx.getZ());
            }
         }
      }

      return super.getNode(â˜ƒ.getX(), â˜ƒ, â˜ƒ.getZ());
   }

   @Override
   public Target getGoal(double var1, double var3, double var5) {
      return new Target(super.getNode(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ)));
   }

   @Override
   public int getNeighbors(Node[] var1, Node var2) {
      int â˜ƒ = 0;
      Node â˜ƒx = this.getNode(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒx)) {
         â˜ƒ[â˜ƒ++] = â˜ƒx;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z);
      if (this.isOpen(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z);
      if (this.isOpen(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y - 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y + 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y + 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y - 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y - 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y - 1, â˜ƒ.z);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x, â˜ƒ.y - 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y + 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y + 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)
         )
       {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y + 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y + 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)
         )
       {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y - 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x + 1, â˜ƒ.y - 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)
         )
       {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y - 1, â˜ƒ.z - 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.getNode(â˜ƒ.x - 1, â˜ƒ.y - 1, â˜ƒ.z + 1);
      if (this.isOpen(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒx) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ) && this.hasMalus(â˜ƒ)
         )
       {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      return â˜ƒ;
   }

   private boolean hasMalus(@Nullable Node var1) {
      return â˜ƒ != null && â˜ƒ.costMalus >= 0.0F;
   }

   private boolean isOpen(@Nullable Node var1) {
      return â˜ƒ != null && !â˜ƒ.closed;
   }

   @Nullable
   @Override
   protected Node getNode(int var1, int var2, int var3) {
      Node â˜ƒ = null;
      BlockPathTypes â˜ƒx = this.getCachedBlockPathType(â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒxx = this.mob.getPathfindingMalus(â˜ƒx);
      if (â˜ƒxx >= 0.0F) {
         â˜ƒ = super.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.type = â˜ƒx;
         â˜ƒ.costMalus = Math.max(â˜ƒ.costMalus, â˜ƒxx);
         if (â˜ƒx == BlockPathTypes.WALKABLE) {
            ++â˜ƒ.costMalus;
         }
      }

      return â˜ƒ;
   }

   private BlockPathTypes getCachedBlockPathType(int var1, int var2, int var3) {
      return (BlockPathTypes)this.pathTypeByPosCache
         .computeIfAbsent(
            BlockPos.asLong(â˜ƒ, â˜ƒ, â˜ƒ),
            var4 -> this.getBlockPathType(
                  this.level, â˜ƒ, â˜ƒ, â˜ƒ, this.mob, this.entityWidth, this.entityHeight, this.entityDepth, this.canOpenDoors(), this.canPassDoors()
               )
         );
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4, Mob var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      EnumSet<BlockPathTypes> â˜ƒ = EnumSet.noneOf(BlockPathTypes.class);
      BlockPathTypes â˜ƒx = BlockPathTypes.BLOCKED;
      BlockPos â˜ƒxx = â˜ƒ.blockPosition();
      â˜ƒx = super.getBlockPathTypes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      if (â˜ƒ.contains(BlockPathTypes.FENCE)) {
         return BlockPathTypes.FENCE;
      } else {
         BlockPathTypes â˜ƒ = BlockPathTypes.BLOCKED;

         for(BlockPathTypes â˜ƒx : â˜ƒ) {
            if (â˜ƒ.getPathfindingMalus(â˜ƒx) < 0.0F) {
               return â˜ƒx;
            }

            if (â˜ƒ.getPathfindingMalus(â˜ƒx) >= â˜ƒ.getPathfindingMalus(â˜ƒ)) {
               â˜ƒ = â˜ƒx;
            }
         }

         return â˜ƒx == BlockPathTypes.OPEN && â˜ƒ.getPathfindingMalus(â˜ƒ) == 0.0F ? BlockPathTypes.OPEN : â˜ƒ;
      }
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      BlockPathTypes â˜ƒx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒ, â˜ƒ));
      if (â˜ƒx == BlockPathTypes.OPEN && â˜ƒ >= â˜ƒ.getMinBuildHeight() + 1) {
         BlockPathTypes â˜ƒxx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒ - 1, â˜ƒ));
         if (â˜ƒxx == BlockPathTypes.DAMAGE_FIRE || â˜ƒxx == BlockPathTypes.LAVA) {
            â˜ƒx = BlockPathTypes.DAMAGE_FIRE;
         } else if (â˜ƒxx == BlockPathTypes.DAMAGE_CACTUS) {
            â˜ƒx = BlockPathTypes.DAMAGE_CACTUS;
         } else if (â˜ƒxx == BlockPathTypes.DAMAGE_OTHER) {
            â˜ƒx = BlockPathTypes.DAMAGE_OTHER;
         } else if (â˜ƒxx == BlockPathTypes.COCOA) {
            â˜ƒx = BlockPathTypes.COCOA;
         } else if (â˜ƒxx == BlockPathTypes.FENCE) {
            â˜ƒx = BlockPathTypes.FENCE;
         } else {
            â˜ƒx = â˜ƒxx != BlockPathTypes.WALKABLE && â˜ƒxx != BlockPathTypes.OPEN && â˜ƒxx != BlockPathTypes.WATER
               ? BlockPathTypes.WALKABLE
               : BlockPathTypes.OPEN;
         }
      }

      if (â˜ƒx == BlockPathTypes.WALKABLE || â˜ƒx == BlockPathTypes.OPEN) {
         â˜ƒx = checkNeighbourBlocks(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒx);
      }

      return â˜ƒx;
   }
}
