package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class PistonStructureResolver {
   public static final int MAX_PUSH_DEPTH = 12;
   private final Level level;
   private final BlockPos pistonPos;
   private final boolean extending;
   private final BlockPos startPos;
   private final Direction pushDirection;
   private final List<BlockPos> toPush = Lists.<BlockPos>newArrayList();
   private final List<BlockPos> toDestroy = Lists.<BlockPos>newArrayList();
   private final Direction pistonDirection;

   public PistonStructureResolver(Level var1, BlockPos var2, Direction var3, boolean var4) {
      this.level = â˜ƒ;
      this.pistonPos = â˜ƒ;
      this.pistonDirection = â˜ƒ;
      this.extending = â˜ƒ;
      if (â˜ƒ) {
         this.pushDirection = â˜ƒ;
         this.startPos = â˜ƒ.relative(â˜ƒ);
      } else {
         this.pushDirection = â˜ƒ.getOpposite();
         this.startPos = â˜ƒ.relative(â˜ƒ, 2);
      }
   }

   public boolean resolve() {
      this.toPush.clear();
      this.toDestroy.clear();
      BlockState â˜ƒ = this.level.getBlockState(this.startPos);
      if (!PistonBaseBlock.isPushable(â˜ƒ, this.level, this.startPos, this.pushDirection, false, this.pistonDirection)) {
         if (this.extending && â˜ƒ.getPistonPushReaction() == PushReaction.DESTROY) {
            this.toDestroy.add(this.startPos);
            return true;
         } else {
            return false;
         }
      } else if (!this.addBlockLine(this.startPos, this.pushDirection)) {
         return false;
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.toPush.size(); ++â˜ƒ) {
            BlockPos â˜ƒx = (BlockPos)this.toPush.get(â˜ƒ);
            if (isSticky(this.level.getBlockState(â˜ƒx)) && !this.addBranchingBlocks(â˜ƒx)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean isSticky(BlockState var0) {
      return â˜ƒ.is(Blocks.SLIME_BLOCK) || â˜ƒ.is(Blocks.HONEY_BLOCK);
   }

   private static boolean canStickToEachOther(BlockState var0, BlockState var1) {
      if (â˜ƒ.is(Blocks.HONEY_BLOCK) && â˜ƒ.is(Blocks.SLIME_BLOCK)) {
         return false;
      } else if (â˜ƒ.is(Blocks.SLIME_BLOCK) && â˜ƒ.is(Blocks.HONEY_BLOCK)) {
         return false;
      } else {
         return isSticky(â˜ƒ) || isSticky(â˜ƒ);
      }
   }

   private boolean addBlockLine(BlockPos var1, Direction var2) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (â˜ƒ.isAir()) {
         return true;
      } else if (!PistonBaseBlock.isPushable(â˜ƒ, this.level, â˜ƒ, this.pushDirection, false, â˜ƒ)) {
         return true;
      } else if (â˜ƒ.equals(this.pistonPos)) {
         return true;
      } else if (this.toPush.contains(â˜ƒ)) {
         return true;
      } else {
         int â˜ƒ = 1;
         if (â˜ƒ + this.toPush.size() > 12) {
            return false;
         } else {
            while(isSticky(â˜ƒ)) {
               BlockPos â˜ƒ = â˜ƒ.relative(this.pushDirection.getOpposite(), â˜ƒ);
               BlockState â˜ƒx = â˜ƒ;
               â˜ƒ = this.level.getBlockState(â˜ƒ);
               if (â˜ƒ.isAir()
                  || !canStickToEachOther(â˜ƒx, â˜ƒ)
                  || !PistonBaseBlock.isPushable(â˜ƒ, this.level, â˜ƒ, this.pushDirection, false, this.pushDirection.getOpposite())
                  || â˜ƒ.equals(this.pistonPos)) {
                  break;
               }

               if (++â˜ƒ + this.toPush.size() > 12) {
                  return false;
               }
            }

            int â˜ƒ = 0;

            for(int â˜ƒx = â˜ƒ - 1; â˜ƒx >= 0; --â˜ƒx) {
               this.toPush.add(â˜ƒ.relative(this.pushDirection.getOpposite(), â˜ƒx));
               ++â˜ƒ;
            }

            int â˜ƒx = 1;

            while(true) {
               BlockPos â˜ƒxx = â˜ƒ.relative(this.pushDirection, â˜ƒx);
               int â˜ƒxxx = this.toPush.indexOf(â˜ƒxx);
               if (â˜ƒxxx > -1) {
                  this.reorderListAtCollision(â˜ƒ, â˜ƒxxx);

                  for(int â˜ƒxxxx = 0; â˜ƒxxxx <= â˜ƒxxx + â˜ƒ; ++â˜ƒxxxx) {
                     BlockPos â˜ƒxxxxx = (BlockPos)this.toPush.get(â˜ƒxxxx);
                     if (isSticky(this.level.getBlockState(â˜ƒxxxxx)) && !this.addBranchingBlocks(â˜ƒxxxxx)) {
                        return false;
                     }
                  }

                  return true;
               }

               â˜ƒ = this.level.getBlockState(â˜ƒxx);
               if (â˜ƒ.isAir()) {
                  return true;
               }

               if (!PistonBaseBlock.isPushable(â˜ƒ, this.level, â˜ƒxx, this.pushDirection, true, this.pushDirection) || â˜ƒxx.equals(this.pistonPos)) {
                  return false;
               }

               if (â˜ƒ.getPistonPushReaction() == PushReaction.DESTROY) {
                  this.toDestroy.add(â˜ƒxx);
                  return true;
               }

               if (this.toPush.size() >= 12) {
                  return false;
               }

               this.toPush.add(â˜ƒxx);
               ++â˜ƒ;
               ++â˜ƒx;
            }
         }
      }
   }

   private void reorderListAtCollision(int var1, int var2) {
      List<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
      List<BlockPos> â˜ƒx = Lists.<BlockPos>newArrayList();
      List<BlockPos> â˜ƒxx = Lists.<BlockPos>newArrayList();
      â˜ƒ.addAll(this.toPush.subList(0, â˜ƒ));
      â˜ƒx.addAll(this.toPush.subList(this.toPush.size() - â˜ƒ, this.toPush.size()));
      â˜ƒxx.addAll(this.toPush.subList(â˜ƒ, this.toPush.size() - â˜ƒ));
      this.toPush.clear();
      this.toPush.addAll(â˜ƒ);
      this.toPush.addAll(â˜ƒx);
      this.toPush.addAll(â˜ƒxx);
   }

   private boolean addBranchingBlocks(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);

      for(Direction â˜ƒx : Direction.values()) {
         if (â˜ƒx.getAxis() != this.pushDirection.getAxis()) {
            BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
            BlockState â˜ƒxxx = this.level.getBlockState(â˜ƒxx);
            if (canStickToEachOther(â˜ƒxxx, â˜ƒ) && !this.addBlockLine(â˜ƒxx, â˜ƒx)) {
               return false;
            }
         }
      }

      return true;
   }

   public Direction getPushDirection() {
      return this.pushDirection;
   }

   public List<BlockPos> getToPush() {
      return this.toPush;
   }

   public List<BlockPos> getToDestroy() {
      return this.toDestroy;
   }
}
