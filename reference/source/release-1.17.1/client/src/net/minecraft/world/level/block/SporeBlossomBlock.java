package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SporeBlossomBlock extends Block {
   private static final VoxelShape SHAPE = Block.box(2.0, 13.0, 2.0, 14.0, 16.0, 14.0);
   private static final int ADD_PARTICLE_ATTEMPTS = 14;
   private static final int PARTICLE_XZ_RADIUS = 10;
   private static final int PARTICLE_Y_MAX = 10;

   public SporeBlossomBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return Block.canSupportCenter(â˜ƒ, â˜ƒ.above(), Direction.DOWN) && !â˜ƒ.isWaterAt(â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.UP && !this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      double â˜ƒxxx = (double)â˜ƒ + â˜ƒ.nextDouble();
      double â˜ƒxxxx = (double)â˜ƒx + 0.7;
      double â˜ƒxxxxx = (double)â˜ƒxx + â˜ƒ.nextDouble();
      â˜ƒ.addParticle(ParticleTypes.FALLING_SPORE_BLOSSOM, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
      BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 14; ++â˜ƒxxxxxxx) {
         â˜ƒxxxxxx.set(â˜ƒ + Mth.nextInt(â˜ƒ, -10, 10), â˜ƒx - â˜ƒ.nextInt(10), â˜ƒxx + Mth.nextInt(â˜ƒ, -10, 10));
         BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
         if (!â˜ƒxxxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxx)) {
            â˜ƒ.addParticle(
               ParticleTypes.SPORE_BLOSSOM_AIR,
               (double)â˜ƒxxxxxx.getX() + â˜ƒ.nextDouble(),
               (double)â˜ƒxxxxxx.getY() + â˜ƒ.nextDouble(),
               (double)â˜ƒxxxxxx.getZ() + â˜ƒ.nextDouble(),
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }
}
