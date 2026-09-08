package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class CatSitOnBlockGoal extends MoveToBlockGoal {
   private final Cat cat;

   public CatSitOnBlockGoal(Cat var1, double var2) {
      super(â˜ƒ, â˜ƒ, 8);
      this.cat = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return this.cat.isTame() && !this.cat.isOrderedToSit() && super.canUse();
   }

   @Override
   public void start() {
      super.start();
      this.cat.setInSittingPose(false);
   }

   @Override
   public void stop() {
      super.stop();
      this.cat.setInSittingPose(false);
   }

   @Override
   public void tick() {
      super.tick();
      this.cat.setInSittingPose(this.isReachedTarget());
   }

   @Override
   protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
      if (!â˜ƒ.isEmptyBlock(â˜ƒ.above())) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ.is(Blocks.CHEST)) {
            return ChestBlockEntity.getOpenCount(â˜ƒ, â˜ƒ) < 1;
         } else {
            return â˜ƒ.is(Blocks.FURNACE) && â˜ƒ.getValue(FurnaceBlock.LIT)
               ? true
               : â˜ƒ.is(BlockTags.BEDS, var0 -> var0.getOptionalValue(BedBlock.PART).map(var0x -> var0x != BedPart.HEAD).orElse(true));
         }
      }
   }
}
