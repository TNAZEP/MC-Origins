package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorchBlock extends Block {
   protected static final int AABB_STANDING_OFFSET = 2;
   protected static final VoxelShape AABB = Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
   protected final ParticleOptions flameParticle;

   protected TorchBlock(BlockBehaviour.Properties var1, ParticleOptions var2) {
      super(â˜ƒ);
      this.flameParticle = â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return AABB;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN && !this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return canSupportCenter(â˜ƒ, â˜ƒ.below(), Direction.UP);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒx = (double)â˜ƒ.getY() + 0.7;
      double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
      â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
      â˜ƒ.addParticle(this.flameParticle, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
   }
}
