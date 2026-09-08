package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class FallingBlock extends Block implements Fallable {
   public FallingBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, this.getDelayAfterPlace());
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, this.getDelayAfterPlace());
      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (isFree(â˜ƒ.getBlockState(â˜ƒ.below())) && â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight()) {
         FallingBlockEntity â˜ƒ = new FallingBlockEntity(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, â˜ƒ.getBlockState(â˜ƒ));
         this.falling(â˜ƒ);
         â˜ƒ.addFreshEntity(â˜ƒ);
      }
   }

   protected void falling(FallingBlockEntity var1) {
   }

   protected int getDelayAfterPlace() {
      return 2;
   }

   public static boolean isFree(BlockState var0) {
      Material â˜ƒ = â˜ƒ.getMaterial();
      return â˜ƒ.isAir() || â˜ƒ.is(BlockTags.FIRE) || â˜ƒ.isLiquid() || â˜ƒ.isReplaceable();
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(16) == 0) {
         BlockPos â˜ƒ = â˜ƒ.below();
         if (isFree(â˜ƒ.getBlockState(â˜ƒ))) {
            double â˜ƒx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
            double â˜ƒxx = (double)â˜ƒ.getY() - 0.05;
            double â˜ƒxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
            â˜ƒ.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, â˜ƒ), â˜ƒx, â˜ƒxx, â˜ƒxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   public int getDustColor(BlockState var1, BlockGetter var2, BlockPos var3) {
      return -16777216;
   }
}
