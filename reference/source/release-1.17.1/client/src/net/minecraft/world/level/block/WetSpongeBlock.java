package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WetSpongeBlock extends Block {
   protected WetSpongeBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (â˜ƒ.dimensionType().ultraWarm()) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.SPONGE.defaultBlockState(), 3);
         â˜ƒ.levelEvent(2009, â˜ƒ, 0);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + â˜ƒ.getRandom().nextFloat() * 0.2F) * 0.7F);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      Direction â˜ƒ = Direction.getRandom(â˜ƒ);
      if (â˜ƒ != Direction.UP) {
         BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         if (!â˜ƒ.canOcclude() || !â˜ƒxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ.getOpposite())) {
            double â˜ƒxxx = (double)â˜ƒ.getX();
            double â˜ƒxxxx = (double)â˜ƒ.getY();
            double â˜ƒxxxxx = (double)â˜ƒ.getZ();
            if (â˜ƒ == Direction.DOWN) {
               â˜ƒxxxx -= 0.05;
               â˜ƒxxx += â˜ƒ.nextDouble();
               â˜ƒxxxxx += â˜ƒ.nextDouble();
            } else {
               â˜ƒxxxx += â˜ƒ.nextDouble() * 0.8;
               if (â˜ƒ.getAxis() == Direction.Axis.X) {
                  â˜ƒxxxxx += â˜ƒ.nextDouble();
                  if (â˜ƒ == Direction.EAST) {
                     ++â˜ƒxxx;
                  } else {
                     â˜ƒxxx += 0.05;
                  }
               } else {
                  â˜ƒxxx += â˜ƒ.nextDouble();
                  if (â˜ƒ == Direction.SOUTH) {
                     ++â˜ƒxxxxx;
                  } else {
                     â˜ƒxxxxx += 0.05;
                  }
               }
            }

            â˜ƒ.addParticle(ParticleTypes.DRIPPING_WATER, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }
}
