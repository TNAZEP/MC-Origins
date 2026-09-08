package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SolidBucketItem extends BlockItem implements DispensibleContainerItem {
   private final SoundEvent placeSound;

   public SolidBucketItem(Block var1, SoundEvent var2, Item.Properties var3) {
      super(â˜ƒ, â˜ƒ);
      this.placeSound = â˜ƒ;
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      InteractionResult â˜ƒ = super.useOn(â˜ƒ);
      Player â˜ƒx = â˜ƒ.getPlayer();
      if (â˜ƒ.consumesAction() && â˜ƒx != null && !â˜ƒx.isCreative()) {
         InteractionHand â˜ƒxx = â˜ƒ.getHand();
         â˜ƒx.setItemInHand(â˜ƒxx, Items.BUCKET.getDefaultInstance());
      }

      return â˜ƒ;
   }

   @Override
   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }

   @Override
   protected SoundEvent getPlaceSound(BlockState var1) {
      return this.placeSound;
   }

   @Override
   public boolean emptyContents(@Nullable Player var1, Level var2, BlockPos var3, @Nullable BlockHitResult var4) {
      if (â˜ƒ.isInWorldBounds(â˜ƒ) && â˜ƒ.isEmptyBlock(â˜ƒ)) {
         if (!â˜ƒ.isClientSide) {
            â˜ƒ.setBlock(â˜ƒ, this.getBlock().defaultBlockState(), 3);
         }

         â˜ƒ.playSound(â˜ƒ, â˜ƒ, this.placeSound, SoundSource.BLOCKS, 1.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }
}
