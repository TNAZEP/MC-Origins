package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractBannerBlock extends BaseEntityBlock {
   private final DyeColor color;

   protected AbstractBannerBlock(DyeColor var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.color = â˜ƒ;
   }

   @Override
   public boolean isPossibleToRespawnInThis() {
      return true;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new BannerBlockEntity(â˜ƒ, â˜ƒ, this.color);
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      if (â˜ƒ.hasCustomHoverName()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof BannerBlockEntity) {
            ((BannerBlockEntity)â˜ƒ).setCustomName(â˜ƒ.getHoverName());
         }
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof BannerBlockEntity ? ((BannerBlockEntity)â˜ƒ).getItem() : super.getCloneItemStack(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public DyeColor getColor() {
      return this.color;
   }
}
