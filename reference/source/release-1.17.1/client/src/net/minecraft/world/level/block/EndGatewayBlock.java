package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class EndGatewayBlock extends BaseEntityBlock {
   protected EndGatewayBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new TheEndGatewayBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return createTickerHelper(
         â˜ƒ, BlockEntityType.END_GATEWAY, â˜ƒ.isClientSide ? TheEndGatewayBlockEntity::beamAnimationTick : TheEndGatewayBlockEntity::teleportTick
      );
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof TheEndGatewayBlockEntity) {
         int â˜ƒx = ((TheEndGatewayBlockEntity)â˜ƒ).getParticleAmount();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
            double â˜ƒxxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
            double â˜ƒxxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
            double â˜ƒxxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
            double â˜ƒxxxxxx = (â˜ƒ.nextDouble() - 0.5) * 0.5;
            double â˜ƒxxxxxxx = (â˜ƒ.nextDouble() - 0.5) * 0.5;
            double â˜ƒxxxxxxxx = (â˜ƒ.nextDouble() - 0.5) * 0.5;
            int â˜ƒxxxxxxxxx = â˜ƒ.nextInt(2) * 2 - 1;
            if (â˜ƒ.nextBoolean()) {
               â˜ƒxxxxx = (double)â˜ƒ.getZ() + 0.5 + 0.25 * (double)â˜ƒxxxxxxxxx;
               â˜ƒxxxxxxxx = (double)(â˜ƒ.nextFloat() * 2.0F * (float)â˜ƒxxxxxxxxx);
            } else {
               â˜ƒxxx = (double)â˜ƒ.getX() + 0.5 + 0.25 * (double)â˜ƒxxxxxxxxx;
               â˜ƒxxxxxx = (double)(â˜ƒ.nextFloat() * 2.0F * (float)â˜ƒxxxxxxxxx);
            }

            â˜ƒ.addParticle(ParticleTypes.PORTAL, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
         }
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return ItemStack.EMPTY;
   }

   @Override
   public boolean canBeReplaced(BlockState var1, Fluid var2) {
      return false;
   }
}
