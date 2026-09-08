package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class ShearsDispenseItemBehavior extends OptionalDispenseItemBehavior {
   @Override
   protected ItemStack execute(BlockSource var1, ItemStack var2) {
      Level â˜ƒ = â˜ƒ.getLevel();
      if (!â˜ƒ.isClientSide()) {
         BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
         this.setSuccess(tryShearBeehive((ServerLevel)â˜ƒ, â˜ƒx) || tryShearLivingEntity((ServerLevel)â˜ƒ, â˜ƒx));
         if (this.isSuccess() && â˜ƒ.hurt(1, â˜ƒ.getRandom(), null)) {
            â˜ƒ.setCount(0);
         }
      }

      return â˜ƒ;
   }

   private static boolean tryShearBeehive(ServerLevel var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.is(BlockTags.BEEHIVES)) {
         int â˜ƒx = â˜ƒ.getValue(BeehiveBlock.HONEY_LEVEL);
         if (â˜ƒx >= 5) {
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            BeehiveBlock.dropHoneycomb(â˜ƒ, â˜ƒ);
            ((BeehiveBlock)â˜ƒ.getBlock()).releaseBeesAndResetHoneyLevel(â˜ƒ, â˜ƒ, â˜ƒ, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
            â˜ƒ.gameEvent(null, GameEvent.SHEAR, â˜ƒ);
            return true;
         }
      }

      return false;
   }

   private static boolean tryShearLivingEntity(ServerLevel var0, BlockPos var1) {
      for(LivingEntity â˜ƒ : â˜ƒ.getEntitiesOfClass(LivingEntity.class, new AABB(â˜ƒ), EntitySelector.NO_SPECTATORS)) {
         if (â˜ƒ instanceof Shearable â˜ƒx && â˜ƒx.readyForShearing()) {
            â˜ƒx.shear(SoundSource.BLOCKS);
            â˜ƒ.gameEvent(null, GameEvent.SHEAR, â˜ƒ);
            return true;
         }
      }

      return false;
   }
}
