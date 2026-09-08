package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SignItem extends StandingAndWallBlockItem {
   public SignItem(Item.Properties var1, Block var2, Block var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean updateCustomBlockEntityTag(BlockPos var1, Level var2, @Nullable Player var3, ItemStack var4, BlockState var5) {
      boolean â˜ƒ = super.updateCustomBlockEntityTag(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isClientSide && !â˜ƒ && â˜ƒ != null) {
         â˜ƒ.openTextEdit((SignBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ));
      }

      return â˜ƒ;
   }
}
