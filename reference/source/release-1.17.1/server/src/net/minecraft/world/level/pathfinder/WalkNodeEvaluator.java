package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WalkNodeEvaluator extends NodeEvaluator {
   public static final double SPACE_BETWEEN_WALL_POSTS = 0.5;
   protected float oldWaterCost;
   private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = new Long2ObjectOpenHashMap();
   private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap<>();

   @Override
   public void prepare(PathNavigationRegion var1, Mob var2) {
      super.prepare(â˜ƒ, â˜ƒ);
      this.oldWaterCost = â˜ƒ.getPathfindingMalus(BlockPathTypes.WATER);
   }

   @Override
   public void done() {
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
      this.pathTypesByPosCache.clear();
      this.collisionCache.clear();
      super.done();
   }

   @Override
   public Node getStart() {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      int â˜ƒx = this.mob.getBlockY();
      BlockState â˜ƒxx = this.level.getBlockState(â˜ƒ.set(this.mob.getX(), (double)â˜ƒx, this.mob.getZ()));
      if (!this.mob.canStandOnFluid(â˜ƒxx.getFluidState().getType())) {
         if (this.canFloat() && this.mob.isInWater()) {
            while(true) {
               if (!â˜ƒxx.is(Blocks.WATER) && â˜ƒxx.getFluidState() != Fluids.WATER.getSource(false)) {
                  --â˜ƒx;
                  break;
               }

               â˜ƒxx = this.level.getBlockState(â˜ƒ.set(this.mob.getX(), (double)(++â˜ƒx), this.mob.getZ()));
            }
         } else if (this.mob.isOnGround()) {
            â˜ƒx = Mth.floor(this.mob.getY() + 0.5);
         } else {
            BlockPos â˜ƒxxx = this.mob.blockPosition();

            while(
               (this.level.getBlockState(â˜ƒxxx).isAir() || this.level.getBlockState(â˜ƒxxx).isPathfindable(this.level, â˜ƒxxx, PathComputationType.LAND))
                  && â˜ƒxxx.getY() > this.mob.level.getMinBuildHeight()
            ) {
               â˜ƒxxx = â˜ƒxxx.below();
            }

            â˜ƒx = â˜ƒxxx.above().getY();
         }
      } else {
         while(this.mob.canStandOnFluid(â˜ƒxx.getFluidState().getType())) {
            â˜ƒxx = this.level.getBlockState(â˜ƒ.set(this.mob.getX(), (double)(++â˜ƒx), this.mob.getZ()));
         }

         --â˜ƒx;
      }

      BlockPos â˜ƒ = this.mob.blockPosition();
      BlockPathTypes â˜ƒx = this.getCachedBlockType(this.mob, â˜ƒ.getX(), â˜ƒx, â˜ƒ.getZ());
      if (this.mob.getPathfindingMalus(â˜ƒx) < 0.0F) {
         AABB â˜ƒxx = this.mob.getBoundingBox();
         if (this.hasPositiveMalus(â˜ƒ.set(â˜ƒxx.minX, (double)â˜ƒx, â˜ƒxx.minZ))
            || this.hasPositiveMalus(â˜ƒ.set(â˜ƒxx.minX, (double)â˜ƒx, â˜ƒxx.maxZ))
            || this.hasPositiveMalus(â˜ƒ.set(â˜ƒxx.maxX, (double)â˜ƒx, â˜ƒxx.minZ))
            || this.hasPositiveMalus(â˜ƒ.set(â˜ƒxx.maxX, (double)â˜ƒx, â˜ƒxx.maxZ))) {
            Node â˜ƒxxx = this.getNode(â˜ƒ);
            â˜ƒxxx.type = this.getBlockPathType(this.mob, â˜ƒxxx.asBlockPos());
            â˜ƒxxx.costMalus = this.mob.getPathfindingMalus(â˜ƒxxx.type);
            return â˜ƒxxx;
         }
      }

      Node â˜ƒ = this.getNode(â˜ƒ.getX(), â˜ƒx, â˜ƒ.getZ());
      â˜ƒ.type = this.getBlockPathType(this.mob, â˜ƒ.asBlockPos());
      â˜ƒ.costMalus = this.mob.getPathfindingMalus(â˜ƒ.type);
      return â˜ƒ;
   }

   private boolean hasPositiveMalus(BlockPos var1) {
      BlockPathTypes â˜ƒ = this.getBlockPathType(this.mob, â˜ƒ);
      return this.mob.getPathfindingMalus(â˜ƒ) >= 0.0F;
   }

   @Override
   public Target getGoal(double var1, double var3, double var5) {
      return new Target(this.getNode(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ)));
   }

   @Override
   public int getNeighbors(Node[] var1, Node var2) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      BlockPathTypes â˜ƒxx = this.getCachedBlockType(this.mob, â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z);
      BlockPathTypes â˜ƒxxx = this.getCachedBlockType(this.mob, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      if (this.mob.getPathfindingMalus(â˜ƒxx) >= 0.0F && â˜ƒxxx != BlockPathTypes.STICKY_HONEY) {
         â˜ƒx = Mth.floor(Math.max(1.0F, this.mob.maxUpStep));
      }

      double â˜ƒ = this.getFloorLevel(new BlockPos(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z));
      Node â˜ƒx = this.findAcceptedNode(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z + 1, â˜ƒx, â˜ƒ, Direction.SOUTH, â˜ƒxxx);
      if (this.isNeighborValid(â˜ƒx, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒx;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z, â˜ƒx, â˜ƒ, Direction.WEST, â˜ƒxxx);
      if (this.isNeighborValid(â˜ƒ, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z, â˜ƒx, â˜ƒ, Direction.EAST, â˜ƒxxx);
      if (this.isNeighborValid(â˜ƒ, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z - 1, â˜ƒx, â˜ƒ, Direction.NORTH, â˜ƒxxx);
      if (this.isNeighborValid(â˜ƒ, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z - 1, â˜ƒx, â˜ƒ, Direction.NORTH, â˜ƒxxx);
      if (this.isDiagonalValid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z - 1, â˜ƒx, â˜ƒ, Direction.NORTH, â˜ƒxxx);
      if (this.isDiagonalValid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x - 1, â˜ƒ.y, â˜ƒ.z + 1, â˜ƒx, â˜ƒ, Direction.SOUTH, â˜ƒxxx);
      if (this.isDiagonalValid(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      Node â˜ƒ = this.findAcceptedNode(â˜ƒ.x + 1, â˜ƒ.y, â˜ƒ.z + 1, â˜ƒx, â˜ƒ, Direction.SOUTH, â˜ƒxxx);
      if (this.isDiagonalValid(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ)) {
         â˜ƒ[â˜ƒ++] = â˜ƒ;
      }

      return â˜ƒ;
   }

   protected boolean isNeighborValid(@Nullable Node var1, Node var2) {
      return â˜ƒ != null && !â˜ƒ.closed && (â˜ƒ.costMalus >= 0.0F || â˜ƒ.costMalus < 0.0F);
   }

   protected boolean isDiagonalValid(Node var1, @Nullable Node var2, @Nullable Node var3, @Nullable Node var4) {
      if (â˜ƒ == null || â˜ƒ == null || â˜ƒ == null) {
         return false;
      } else if (â˜ƒ.closed) {
         return false;
      } else if (â˜ƒ.y > â˜ƒ.y || â˜ƒ.y > â˜ƒ.y) {
         return false;
      } else if (â˜ƒ.type != BlockPathTypes.WALKABLE_DOOR && â˜ƒ.type != BlockPathTypes.WALKABLE_DOOR && â˜ƒ.type != BlockPathTypes.WALKABLE_DOOR) {
         boolean â˜ƒ = â˜ƒ.type == BlockPathTypes.FENCE && â˜ƒ.type == BlockPathTypes.FENCE && (double)this.mob.getBbWidth() < 0.5;
         return â˜ƒ.costMalus >= 0.0F && (â˜ƒ.y < â˜ƒ.y || â˜ƒ.costMalus >= 0.0F || â˜ƒ) && (â˜ƒ.y < â˜ƒ.y || â˜ƒ.costMalus >= 0.0F || â˜ƒ);
      } else {
         return false;
      }
   }

   private boolean canReachWithoutCollision(Node var1) {
      Vec3 â˜ƒ = new Vec3((double)â˜ƒ.x - this.mob.getX(), (double)â˜ƒ.y - this.mob.getY(), (double)â˜ƒ.z - this.mob.getZ());
      AABB â˜ƒx = this.mob.getBoundingBox();
      int â˜ƒxx = Mth.ceil(â˜ƒ.length() / â˜ƒx.getSize());
      â˜ƒ = â˜ƒ.scale((double)(1.0F / (float)â˜ƒxx));

      for(int â˜ƒxxx = 1; â˜ƒxxx <= â˜ƒxx; ++â˜ƒxxx) {
         â˜ƒx = â˜ƒx.move(â˜ƒ);
         if (this.hasCollisions(â˜ƒx)) {
            return false;
         }
      }

      return true;
   }

   protected double getFloorLevel(BlockPos var1) {
      return getFloorLevel(this.level, â˜ƒ);
   }

   public static double getFloorLevel(BlockGetter var0, BlockPos var1) {
      BlockPos â˜ƒ = â˜ƒ.below();
      VoxelShape â˜ƒx = â˜ƒ.getBlockState(â˜ƒ).getCollisionShape(â˜ƒ, â˜ƒ);
      return (double)â˜ƒ.getY() + (â˜ƒx.isEmpty() ? 0.0 : â˜ƒx.max(Direction.Axis.Y));
   }

   protected boolean isAmphibious() {
      return false;
   }

   @Nullable
   protected Node findAcceptedNode(int var1, int var2, int var3, int var4, double var5, Direction var7, BlockPathTypes var8) {
      Node â˜ƒ = null;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();
      double â˜ƒxx = this.getFloorLevel(â˜ƒx.set(â˜ƒ, â˜ƒ, â˜ƒ));
      if (â˜ƒxx - â˜ƒ > 1.125) {
         return null;
      } else {
         BlockPathTypes â˜ƒ = this.getCachedBlockType(this.mob, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒx = this.mob.getPathfindingMalus(â˜ƒ);
         double â˜ƒxx = (double)this.mob.getBbWidth() / 2.0;
         if (â˜ƒx >= 0.0F) {
            â˜ƒ = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.type = â˜ƒ;
            â˜ƒ.costMalus = Math.max(â˜ƒ.costMalus, â˜ƒx);
         }

         if (â˜ƒ == BlockPathTypes.FENCE && â˜ƒ != null && â˜ƒ.costMalus >= 0.0F && !this.canReachWithoutCollision(â˜ƒ)) {
            â˜ƒ = null;
         }

         if (â˜ƒ != BlockPathTypes.WALKABLE && (!this.isAmphibious() || â˜ƒ != BlockPathTypes.WATER)) {
            if ((â˜ƒ == null || â˜ƒ.costMalus < 0.0F)
               && â˜ƒ > 0
               && â˜ƒ != BlockPathTypes.FENCE
               && â˜ƒ != BlockPathTypes.UNPASSABLE_RAIL
               && â˜ƒ != BlockPathTypes.TRAPDOOR
               && â˜ƒ != BlockPathTypes.POWDER_SNOW) {
               â˜ƒ = this.findAcceptedNode(â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ - 1, â˜ƒ, â˜ƒ, â˜ƒ);
               if (â˜ƒ != null && (â˜ƒ.type == BlockPathTypes.OPEN || â˜ƒ.type == BlockPathTypes.WALKABLE) && this.mob.getBbWidth() < 1.0F) {
                  double â˜ƒ = (double)(â˜ƒ - â˜ƒ.getStepX()) + 0.5;
                  double â˜ƒx = (double)(â˜ƒ - â˜ƒ.getStepZ()) + 0.5;
                  AABB â˜ƒxx = new AABB(
                     â˜ƒ - â˜ƒxx,
                     getFloorLevel(this.level, â˜ƒx.set(â˜ƒ, (double)(â˜ƒ + 1), â˜ƒx)) + 0.001,
                     â˜ƒx - â˜ƒxx,
                     â˜ƒ + â˜ƒxx,
                     (double)this.mob.getBbHeight() + getFloorLevel(this.level, â˜ƒx.set((double)â˜ƒ.x, (double)â˜ƒ.y, (double)â˜ƒ.z)) - 0.002,
                     â˜ƒx + â˜ƒxx
                  );
                  if (this.hasCollisions(â˜ƒxx)) {
                     â˜ƒ = null;
                  }
               }
            }

            if (!this.isAmphibious() && â˜ƒ == BlockPathTypes.WATER && !this.canFloat()) {
               if (this.getCachedBlockType(this.mob, â˜ƒ, â˜ƒ - 1, â˜ƒ) != BlockPathTypes.WATER) {
                  return â˜ƒ;
               }

               while(â˜ƒ > this.mob.level.getMinBuildHeight()) {
                  â˜ƒ = this.getCachedBlockType(this.mob, â˜ƒ, --â˜ƒ, â˜ƒ);
                  if (â˜ƒ != BlockPathTypes.WATER) {
                     return â˜ƒ;
                  }

                  â˜ƒ = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
                  â˜ƒ.type = â˜ƒ;
                  â˜ƒ.costMalus = Math.max(â˜ƒ.costMalus, this.mob.getPathfindingMalus(â˜ƒ));
               }
            }

            if (â˜ƒ == BlockPathTypes.OPEN) {
               int â˜ƒ = 0;
               int â˜ƒx = â˜ƒ;

               while(â˜ƒ == BlockPathTypes.OPEN) {
                  if (--â˜ƒ < this.mob.level.getMinBuildHeight()) {
                     Node â˜ƒxx = this.getNode(â˜ƒ, â˜ƒx, â˜ƒ);
                     â˜ƒxx.type = BlockPathTypes.BLOCKED;
                     â˜ƒxx.costMalus = -1.0F;
                     return â˜ƒxx;
                  }

                  if (â˜ƒ++ >= this.mob.getMaxFallDistance()) {
                     Node â˜ƒxx = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
                     â˜ƒxx.type = BlockPathTypes.BLOCKED;
                     â˜ƒxx.costMalus = -1.0F;
                     return â˜ƒxx;
                  }

                  â˜ƒ = this.getCachedBlockType(this.mob, â˜ƒ, â˜ƒ, â˜ƒ);
                  â˜ƒx = this.mob.getPathfindingMalus(â˜ƒ);
                  if (â˜ƒ != BlockPathTypes.OPEN && â˜ƒx >= 0.0F) {
                     â˜ƒ = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
                     â˜ƒ.type = â˜ƒ;
                     â˜ƒ.costMalus = Math.max(â˜ƒ.costMalus, â˜ƒx);
                     break;
                  }

                  if (â˜ƒx < 0.0F) {
                     Node â˜ƒxx = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
                     â˜ƒxx.type = BlockPathTypes.BLOCKED;
                     â˜ƒxx.costMalus = -1.0F;
                     return â˜ƒxx;
                  }
               }
            }

            if (â˜ƒ == BlockPathTypes.FENCE) {
               â˜ƒ = this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
               â˜ƒ.closed = true;
               â˜ƒ.type = â˜ƒ;
               â˜ƒ.costMalus = â˜ƒ.getMalus();
            }

            return â˜ƒ;
         } else {
            return â˜ƒ;
         }
      }
   }

   private boolean hasCollisions(AABB var1) {
      return this.collisionCache.computeIfAbsent(â˜ƒ, var2 -> !this.level.noCollision(this.mob, â˜ƒ));
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4, Mob var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      EnumSet<BlockPathTypes> â˜ƒ = EnumSet.noneOf(BlockPathTypes.class);
      BlockPathTypes â˜ƒx = BlockPathTypes.BLOCKED;
      BlockPos â˜ƒxx = â˜ƒ.blockPosition();
      â˜ƒx = this.getBlockPathTypes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      if (â˜ƒ.contains(BlockPathTypes.FENCE)) {
         return BlockPathTypes.FENCE;
      } else if (â˜ƒ.contains(BlockPathTypes.UNPASSABLE_RAIL)) {
         return BlockPathTypes.UNPASSABLE_RAIL;
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

         return â˜ƒx == BlockPathTypes.OPEN && â˜ƒ.getPathfindingMalus(â˜ƒ) == 0.0F && â˜ƒ <= 1 ? BlockPathTypes.OPEN : â˜ƒ;
      }
   }

   public BlockPathTypes getBlockPathTypes(
      BlockGetter var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      boolean var8,
      boolean var9,
      EnumSet<BlockPathTypes> var10,
      BlockPathTypes var11,
      BlockPos var12
   ) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
               int â˜ƒxxx = â˜ƒ + â˜ƒ;
               int â˜ƒxxxx = â˜ƒx + â˜ƒ;
               int â˜ƒxxxxx = â˜ƒxx + â˜ƒ;
               BlockPathTypes â˜ƒxxxxxx = this.getBlockPathType(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
               â˜ƒxxxxxx = this.evaluateBlockPathType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx);
               if (â˜ƒ == 0 && â˜ƒx == 0 && â˜ƒxx == 0) {
                  â˜ƒ = â˜ƒxxxxxx;
               }

               â˜ƒ.add(â˜ƒxxxxxx);
            }
         }
      }

      return â˜ƒ;
   }

   protected BlockPathTypes evaluateBlockPathType(BlockGetter var1, boolean var2, boolean var3, BlockPos var4, BlockPathTypes var5) {
      if (â˜ƒ == BlockPathTypes.DOOR_WOOD_CLOSED && â˜ƒ && â˜ƒ) {
         â˜ƒ = BlockPathTypes.WALKABLE_DOOR;
      }

      if (â˜ƒ == BlockPathTypes.DOOR_OPEN && !â˜ƒ) {
         â˜ƒ = BlockPathTypes.BLOCKED;
      }

      if (â˜ƒ == BlockPathTypes.RAIL
         && !(â˜ƒ.getBlockState(â˜ƒ).getBlock() instanceof BaseRailBlock)
         && !(â˜ƒ.getBlockState(â˜ƒ.below()).getBlock() instanceof BaseRailBlock)) {
         â˜ƒ = BlockPathTypes.UNPASSABLE_RAIL;
      }

      if (â˜ƒ == BlockPathTypes.LEAVES) {
         â˜ƒ = BlockPathTypes.BLOCKED;
      }

      return â˜ƒ;
   }

   private BlockPathTypes getBlockPathType(Mob var1, BlockPos var2) {
      return this.getCachedBlockType(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   protected BlockPathTypes getCachedBlockType(Mob var1, int var2, int var3, int var4) {
      return (BlockPathTypes)this.pathTypesByPosCache
         .computeIfAbsent(
            BlockPos.asLong(â˜ƒ, â˜ƒ, â˜ƒ),
            var5 -> this.getBlockPathType(
                  this.level, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.entityWidth, this.entityHeight, this.entityDepth, this.canOpenDoors(), this.canPassDoors()
               )
         );
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4) {
      return getBlockPathTypeStatic(â˜ƒ, new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static BlockPathTypes getBlockPathTypeStatic(BlockGetter var0, BlockPos.MutableBlockPos var1) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      BlockPathTypes â˜ƒxxx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ);
      if (â˜ƒxxx == BlockPathTypes.OPEN && â˜ƒx >= â˜ƒ.getMinBuildHeight() + 1) {
         BlockPathTypes â˜ƒxxxx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒx - 1, â˜ƒxx));
         â˜ƒxxx = â˜ƒxxxx != BlockPathTypes.WALKABLE && â˜ƒxxxx != BlockPathTypes.OPEN && â˜ƒxxxx != BlockPathTypes.WATER && â˜ƒxxxx != BlockPathTypes.LAVA
            ? BlockPathTypes.WALKABLE
            : BlockPathTypes.OPEN;
         if (â˜ƒxxxx == BlockPathTypes.DAMAGE_FIRE) {
            â˜ƒxxx = BlockPathTypes.DAMAGE_FIRE;
         }

         if (â˜ƒxxxx == BlockPathTypes.DAMAGE_CACTUS) {
            â˜ƒxxx = BlockPathTypes.DAMAGE_CACTUS;
         }

         if (â˜ƒxxxx == BlockPathTypes.DAMAGE_OTHER) {
            â˜ƒxxx = BlockPathTypes.DAMAGE_OTHER;
         }

         if (â˜ƒxxxx == BlockPathTypes.STICKY_HONEY) {
            â˜ƒxxx = BlockPathTypes.STICKY_HONEY;
         }
      }

      if (â˜ƒxxx == BlockPathTypes.WALKABLE) {
         â˜ƒxxx = checkNeighbourBlocks(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒx, â˜ƒxx), â˜ƒxxx);
      }

      return â˜ƒxxx;
   }

   public static BlockPathTypes checkNeighbourBlocks(BlockGetter var0, BlockPos.MutableBlockPos var1, BlockPathTypes var2) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();

      for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 1; ++â˜ƒxxxxx) {
               if (â˜ƒxxx != 0 || â˜ƒxxxxx != 0) {
                  â˜ƒ.set(â˜ƒ + â˜ƒxxx, â˜ƒx + â˜ƒxxxx, â˜ƒxx + â˜ƒxxxxx);
                  BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒ);
                  if (â˜ƒxxxxxx.is(Blocks.CACTUS)) {
                     return BlockPathTypes.DANGER_CACTUS;
                  }

                  if (â˜ƒxxxxxx.is(Blocks.SWEET_BERRY_BUSH)) {
                     return BlockPathTypes.DANGER_OTHER;
                  }

                  if (isBurningBlock(â˜ƒxxxxxx)) {
                     return BlockPathTypes.DANGER_FIRE;
                  }

                  if (â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER)) {
                     return BlockPathTypes.WATER_BORDER;
                  }
               }
            }
         }
      }

      return â˜ƒ;
   }

   protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      Block â˜ƒx = â˜ƒ.getBlock();
      Material â˜ƒxx = â˜ƒ.getMaterial();
      if (â˜ƒ.isAir()) {
         return BlockPathTypes.OPEN;
      } else if (â˜ƒ.is(BlockTags.TRAPDOORS) || â˜ƒ.is(Blocks.LILY_PAD) || â˜ƒ.is(Blocks.BIG_DRIPLEAF)) {
         return BlockPathTypes.TRAPDOOR;
      } else if (â˜ƒ.is(Blocks.POWDER_SNOW)) {
         return BlockPathTypes.POWDER_SNOW;
      } else if (â˜ƒ.is(Blocks.CACTUS)) {
         return BlockPathTypes.DAMAGE_CACTUS;
      } else if (â˜ƒ.is(Blocks.SWEET_BERRY_BUSH)) {
         return BlockPathTypes.DAMAGE_OTHER;
      } else if (â˜ƒ.is(Blocks.HONEY_BLOCK)) {
         return BlockPathTypes.STICKY_HONEY;
      } else if (â˜ƒ.is(Blocks.COCOA)) {
         return BlockPathTypes.COCOA;
      } else {
         FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
         if (â˜ƒ.is(FluidTags.LAVA)) {
            return BlockPathTypes.LAVA;
         } else if (isBurningBlock(â˜ƒ)) {
            return BlockPathTypes.DAMAGE_FIRE;
         } else if (DoorBlock.isWoodenDoor(â˜ƒ) && !â˜ƒ.getValue(DoorBlock.OPEN)) {
            return BlockPathTypes.DOOR_WOOD_CLOSED;
         } else if (â˜ƒx instanceof DoorBlock && â˜ƒxx == Material.METAL && !â˜ƒ.getValue(DoorBlock.OPEN)) {
            return BlockPathTypes.DOOR_IRON_CLOSED;
         } else if (â˜ƒx instanceof DoorBlock && â˜ƒ.getValue(DoorBlock.OPEN)) {
            return BlockPathTypes.DOOR_OPEN;
         } else if (â˜ƒx instanceof BaseRailBlock) {
            return BlockPathTypes.RAIL;
         } else if (â˜ƒx instanceof LeavesBlock) {
            return BlockPathTypes.LEAVES;
         } else if (!â˜ƒ.is(BlockTags.FENCES) && !â˜ƒ.is(BlockTags.WALLS) && (!(â˜ƒx instanceof FenceGateBlock) || â˜ƒ.getValue(FenceGateBlock.OPEN))) {
            if (!â˜ƒ.isPathfindable(â˜ƒ, â˜ƒ, PathComputationType.LAND)) {
               return BlockPathTypes.BLOCKED;
            } else {
               return â˜ƒ.is(FluidTags.WATER) ? BlockPathTypes.WATER : BlockPathTypes.OPEN;
            }
         } else {
            return BlockPathTypes.FENCE;
         }
      }
   }

   public static boolean isBurningBlock(BlockState var0) {
      return â˜ƒ.is(BlockTags.FIRE) || â˜ƒ.is(Blocks.LAVA) || â˜ƒ.is(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(â˜ƒ) || â˜ƒ.is(Blocks.LAVA_CAULDRON);
   }
}
