package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CryingObsidianBlock extends Block {
   public CryingObsidianBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(5) == 0) {
         Direction â˜ƒ = Direction.getRandom(â˜ƒ);
         if (â˜ƒ != Direction.UP) {
            BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            if (!â˜ƒ.canOcclude() || !â˜ƒxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ.getOpposite())) {
               double â˜ƒxxx = â˜ƒ.getStepX() == 0 ? â˜ƒ.nextDouble() : 0.5 + (double)â˜ƒ.getStepX() * 0.6;
               double â˜ƒxxxx = â˜ƒ.getStepY() == 0 ? â˜ƒ.nextDouble() : 0.5 + (double)â˜ƒ.getStepY() * 0.6;
               double â˜ƒxxxxx = â˜ƒ.getStepZ() == 0 ? â˜ƒ.nextDouble() : 0.5 + (double)â˜ƒ.getStepZ() * 0.6;
               â˜ƒ.addParticle(
                  ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double)â˜ƒ.getX() + â˜ƒxxx, (double)â˜ƒ.getY() + â˜ƒxxxx, (double)â˜ƒ.getZ() + â˜ƒxxxxx, 0.0, 0.0, 0.0
               );
            }
         }
      }
   }
}
