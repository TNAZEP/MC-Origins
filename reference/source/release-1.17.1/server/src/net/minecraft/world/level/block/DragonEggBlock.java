package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonEggBlock extends FallingBlock {
   protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public DragonEggBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      this.teleport(â˜ƒ, â˜ƒ, â˜ƒ);
      return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
   }

   @Override
   public void attack(BlockState var1, Level var2, BlockPos var3, Player var4) {
      this.teleport(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void teleport(BlockState var1, Level var2, BlockPos var3) {
      for(int â˜ƒ = 0; â˜ƒ < 1000; ++â˜ƒ) {
         BlockPos â˜ƒx = â˜ƒ.offset(
            â˜ƒ.random.nextInt(16) - â˜ƒ.random.nextInt(16), â˜ƒ.random.nextInt(8) - â˜ƒ.random.nextInt(8), â˜ƒ.random.nextInt(16) - â˜ƒ.random.nextInt(16)
         );
         if (â˜ƒ.getBlockState(â˜ƒx).isAir()) {
            if (â˜ƒ.isClientSide) {
               for(int â˜ƒxx = 0; â˜ƒxx < 128; ++â˜ƒxx) {
                  double â˜ƒxxx = â˜ƒ.random.nextDouble();
                  float â˜ƒxxxx = (â˜ƒ.random.nextFloat() - 0.5F) * 0.2F;
                  float â˜ƒxxxxx = (â˜ƒ.random.nextFloat() - 0.5F) * 0.2F;
                  float â˜ƒxxxxxx = (â˜ƒ.random.nextFloat() - 0.5F) * 0.2F;
                  double â˜ƒxxxxxxx = Mth.lerp(â˜ƒxxx, (double)â˜ƒx.getX(), (double)â˜ƒ.getX()) + (â˜ƒ.random.nextDouble() - 0.5) + 0.5;
                  double â˜ƒxxxxxxxx = Mth.lerp(â˜ƒxxx, (double)â˜ƒx.getY(), (double)â˜ƒ.getY()) + â˜ƒ.random.nextDouble() - 0.5;
                  double â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒxxx, (double)â˜ƒx.getZ(), (double)â˜ƒ.getZ()) + (â˜ƒ.random.nextDouble() - 0.5) + 0.5;
                  â˜ƒ.addParticle(ParticleTypes.PORTAL, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, (double)â˜ƒxxxx, (double)â˜ƒxxxxx, (double)â˜ƒxxxxxx);
               }
            } else {
               â˜ƒ.setBlock(â˜ƒx, â˜ƒ, 2);
               â˜ƒ.removeBlock(â˜ƒ, false);
            }

            return;
         }
      }
   }

   @Override
   protected int getDelayAfterPlace() {
      return 5;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
