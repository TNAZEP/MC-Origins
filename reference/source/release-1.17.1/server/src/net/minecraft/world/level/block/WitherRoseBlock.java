package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WitherRoseBlock extends FlowerBlock {
   public WitherRoseBlock(MobEffect var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ, 8, â˜ƒ);
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return super.mayPlaceOn(â˜ƒ, â˜ƒ, â˜ƒ) || â˜ƒ.is(Blocks.NETHERRACK) || â˜ƒ.is(Blocks.SOUL_SAND) || â˜ƒ.is(Blocks.SOUL_SOIL);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      VoxelShape â˜ƒ = this.getShape(â˜ƒ, â˜ƒ, â˜ƒ, CollisionContext.empty());
      Vec3 â˜ƒx = â˜ƒ.bounds().getCenter();
      double â˜ƒxx = (double)â˜ƒ.getX() + â˜ƒx.x;
      double â˜ƒxxx = (double)â˜ƒ.getZ() + â˜ƒx.z;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 3; ++â˜ƒxxxx) {
         if (â˜ƒ.nextBoolean()) {
            â˜ƒ.addParticle(
               ParticleTypes.SMOKE,
               â˜ƒxx + â˜ƒ.nextDouble() / 5.0,
               (double)â˜ƒ.getY() + (0.5 - â˜ƒ.nextDouble()),
               â˜ƒxxx + â˜ƒ.nextDouble() / 5.0,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.getDifficulty() != Difficulty.PEACEFUL) {
         if (â˜ƒ instanceof LivingEntity â˜ƒ && !â˜ƒ.isInvulnerableTo(DamageSource.WITHER)) {
            â˜ƒ.addEffect(new MobEffectInstance(MobEffects.WITHER, 40));
         }
      }
   }
}
