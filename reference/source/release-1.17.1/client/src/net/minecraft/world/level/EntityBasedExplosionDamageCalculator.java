package net.minecraft.world.level;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class EntityBasedExplosionDamageCalculator extends ExplosionDamageCalculator {
   private final Entity source;

   public EntityBasedExplosionDamageCalculator(Entity var1) {
      this.source = â˜ƒ;
   }

   @Override
   public Optional<Float> getBlockExplosionResistance(Explosion var1, BlockGetter var2, BlockPos var3, BlockState var4, FluidState var5) {
      return super.getBlockExplosionResistance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).map(var6 -> this.source.getBlockExplosionResistance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var6));
   }

   @Override
   public boolean shouldBlockExplode(Explosion var1, BlockGetter var2, BlockPos var3, BlockState var4, float var5) {
      return this.source.shouldBlockExplode(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
