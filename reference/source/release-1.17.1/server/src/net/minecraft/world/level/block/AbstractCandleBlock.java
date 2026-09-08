package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractCandleBlock extends Block {
   public static final int LIGHT_PER_CANDLE = 3;
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   protected AbstractCandleBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   protected abstract Iterable<Vec3> getParticleOffsets(BlockState var1);

   public static boolean isLit(BlockState var0) {
      return â˜ƒ.hasProperty(LIT) && (â˜ƒ.is(BlockTags.CANDLES) || â˜ƒ.is(BlockTags.CANDLE_CAKES)) && â˜ƒ.getValue(LIT);
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.isOnFire() && this.canBeLit(â˜ƒ)) {
         setLit(â˜ƒ, â˜ƒ, â˜ƒ.getBlockPos(), true);
      }
   }

   protected boolean canBeLit(BlockState var1) {
      return !â˜ƒ.getValue(LIT);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         this.getParticleOffsets(â˜ƒ).forEach(var3x -> addParticlesAndSound(â˜ƒ, var3x.add((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ()), â˜ƒ));
      }
   }

   private static void addParticlesAndSound(Level var0, Vec3 var1, Random var2) {
      float â˜ƒ = â˜ƒ.nextFloat();
      if (â˜ƒ < 0.3F) {
         â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 0.0, 0.0, 0.0);
         if (â˜ƒ < 0.17F) {
            â˜ƒ.playLocalSound(
               â˜ƒ.x + 0.5,
               â˜ƒ.y + 0.5,
               â˜ƒ.z + 0.5,
               SoundEvents.CANDLE_AMBIENT,
               SoundSource.BLOCKS,
               1.0F + â˜ƒ.nextFloat(),
               â˜ƒ.nextFloat() * 0.7F + 0.3F,
               false
            );
         }
      }

      â˜ƒ.addParticle(ParticleTypes.SMALL_FLAME, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 0.0, 0.0, 0.0);
   }

   public static void extinguish(@Nullable Player var0, BlockState var1, LevelAccessor var2, BlockPos var3) {
      setLit(â˜ƒ, â˜ƒ, â˜ƒ, false);
      if (â˜ƒ.getBlock() instanceof AbstractCandleBlock) {
         ((AbstractCandleBlock)â˜ƒ.getBlock())
            .getParticleOffsets(â˜ƒ)
            .forEach(
               var2x -> â˜ƒ.addParticle(
                     ParticleTypes.SMOKE, (double)â˜ƒ.getX() + var2x.x(), (double)â˜ƒ.getY() + var2x.y(), (double)â˜ƒ.getZ() + var2x.z(), 0.0, 0.1F, 0.0
                  )
            );
      }

      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
      â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_CHANGE, â˜ƒ);
   }

   private static void setLit(LevelAccessor var0, BlockState var1, BlockPos var2, boolean var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(LIT, Boolean.valueOf(â˜ƒ)), 11);
   }
}
