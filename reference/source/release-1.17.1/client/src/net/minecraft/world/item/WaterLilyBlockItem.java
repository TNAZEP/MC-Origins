package net.minecraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class WaterLilyBlockItem extends BlockItem {
   public WaterLilyBlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      return InteractionResult.PASS;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      BlockHitResult â˜ƒ = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, ClipContext.Fluid.SOURCE_ONLY);
      BlockHitResult â˜ƒx = â˜ƒ.withPosition(â˜ƒ.getBlockPos().above());
      InteractionResult â˜ƒxx = super.useOn(new UseOnContext(â˜ƒ, â˜ƒ, â˜ƒx));
      return new InteractionResultHolder<>(â˜ƒxx, â˜ƒ.getItemInHand(â˜ƒ));
   }
}
