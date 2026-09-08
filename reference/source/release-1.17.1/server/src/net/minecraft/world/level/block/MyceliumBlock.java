package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumBlock extends SpreadingSnowyDirtBlock {
   public MyceliumBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      super.animateTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.nextInt(10) == 0) {
         â˜ƒ.addParticle(
            ParticleTypes.MYCELIUM, (double)â˜ƒ.getX() + â˜ƒ.nextDouble(), (double)â˜ƒ.getY() + 1.1, (double)â˜ƒ.getZ() + â˜ƒ.nextDouble(), 0.0, 0.0, 0.0
         );
      }
   }
}
