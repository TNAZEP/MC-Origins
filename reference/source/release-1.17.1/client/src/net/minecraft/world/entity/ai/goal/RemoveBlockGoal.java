package net.minecraft.world.entity.ai.goal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;

public class RemoveBlockGoal extends MoveToBlockGoal {
   private final Block blockToRemove;
   private final Mob removerMob;
   private int ticksSinceReachedGoal;
   private static final int WAIT_AFTER_BLOCK_FOUND = 20;

   public RemoveBlockGoal(Block var1, PathfinderMob var2, double var3, int var5) {
      super(â˜ƒ, â˜ƒ, 24, â˜ƒ);
      this.blockToRemove = â˜ƒ;
      this.removerMob = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      if (!this.removerMob.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         return false;
      } else if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else if (this.tryFindBlock()) {
         this.nextStartTick = 20;
         return true;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);
         return false;
      }
   }

   private boolean tryFindBlock() {
      return this.blockPos != null && this.isValidTarget(this.mob.level, this.blockPos) ? true : this.findNearestBlock();
   }

   @Override
   public void stop() {
      super.stop();
      this.removerMob.fallDistance = 1.0F;
   }

   @Override
   public void start() {
      super.start();
      this.ticksSinceReachedGoal = 0;
   }

   public void playDestroyProgressSound(LevelAccessor var1, BlockPos var2) {
   }

   public void playBreakSound(Level var1, BlockPos var2) {
   }

   @Override
   public void tick() {
      super.tick();
      Level â˜ƒ = this.removerMob.level;
      BlockPos â˜ƒx = this.removerMob.blockPosition();
      BlockPos â˜ƒxx = this.getPosWithBlock(â˜ƒx, â˜ƒ);
      Random â˜ƒxxx = this.removerMob.getRandom();
      if (this.isReachedTarget() && â˜ƒxx != null) {
         if (this.ticksSinceReachedGoal > 0) {
            Vec3 â˜ƒxxxx = this.removerMob.getDeltaMovement();
            this.removerMob.setDeltaMovement(â˜ƒxxxx.x, 0.3, â˜ƒxxxx.z);
            if (!â˜ƒ.isClientSide) {
               double â˜ƒxxxxx = 0.08;
               ((ServerLevel)â˜ƒ)
                  .sendParticles(
                     new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.EGG)),
                     (double)â˜ƒxx.getX() + 0.5,
                     (double)â˜ƒxx.getY() + 0.7,
                     (double)â˜ƒxx.getZ() + 0.5,
                     3,
                     ((double)â˜ƒxxx.nextFloat() - 0.5) * 0.08,
                     ((double)â˜ƒxxx.nextFloat() - 0.5) * 0.08,
                     ((double)â˜ƒxxx.nextFloat() - 0.5) * 0.08,
                     0.15F
                  );
            }
         }

         if (this.ticksSinceReachedGoal % 2 == 0) {
            Vec3 â˜ƒxxxx = this.removerMob.getDeltaMovement();
            this.removerMob.setDeltaMovement(â˜ƒxxxx.x, -0.3, â˜ƒxxxx.z);
            if (this.ticksSinceReachedGoal % 6 == 0) {
               this.playDestroyProgressSound(â˜ƒ, this.blockPos);
            }
         }

         if (this.ticksSinceReachedGoal > 60) {
            â˜ƒ.removeBlock(â˜ƒxx, false);
            if (!â˜ƒ.isClientSide) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx < 20; ++â˜ƒxxxx) {
                  double â˜ƒxxxxx = â˜ƒxxx.nextGaussian() * 0.02;
                  double â˜ƒxxxxxx = â˜ƒxxx.nextGaussian() * 0.02;
                  double â˜ƒxxxxxxx = â˜ƒxxx.nextGaussian() * 0.02;
                  ((ServerLevel)â˜ƒ)
                     .sendParticles(
                        ParticleTypes.POOF,
                        (double)â˜ƒxx.getX() + 0.5,
                        (double)â˜ƒxx.getY(),
                        (double)â˜ƒxx.getZ() + 0.5,
                        1,
                        â˜ƒxxxxx,
                        â˜ƒxxxxxx,
                        â˜ƒxxxxxxx,
                        0.15F
                     );
               }

               this.playBreakSound(â˜ƒ, â˜ƒxx);
            }
         }

         ++this.ticksSinceReachedGoal;
      }
   }

   @Nullable
   private BlockPos getPosWithBlock(BlockPos var1, BlockGetter var2) {
      if (â˜ƒ.getBlockState(â˜ƒ).is(this.blockToRemove)) {
         return â˜ƒ;
      } else {
         BlockPos[] â˜ƒ = new BlockPos[]{â˜ƒ.below(), â˜ƒ.west(), â˜ƒ.east(), â˜ƒ.north(), â˜ƒ.south(), â˜ƒ.below().below()};

         for(BlockPos â˜ƒx : â˜ƒ) {
            if (â˜ƒ.getBlockState(â˜ƒx).is(this.blockToRemove)) {
               return â˜ƒx;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
      ChunkAccess â˜ƒ = â˜ƒ.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()), ChunkStatus.FULL, false);
      if (â˜ƒ == null) {
         return false;
      } else {
         return â˜ƒ.getBlockState(â˜ƒ).is(this.blockToRemove) && â˜ƒ.getBlockState(â˜ƒ.above()).isAir() && â˜ƒ.getBlockState(â˜ƒ.above(2)).isAir();
      }
   }
}
