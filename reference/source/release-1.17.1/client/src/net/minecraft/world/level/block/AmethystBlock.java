package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AmethystBlock extends Block {
   public AmethystBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      if (!â˜ƒ.isClientSide) {
         BlockPos â˜ƒ = â˜ƒ.getBlockPos();
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 0.5F + â˜ƒ.random.nextFloat() * 1.2F);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.5F + â˜ƒ.random.nextFloat() * 1.2F);
      }
   }
}
