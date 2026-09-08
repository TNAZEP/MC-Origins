package net.minecraft.world.level.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadBlock extends SkullBlock {
   protected PlayerHeadBlock(BlockBehaviour.Properties var1) {
      super(SkullBlock.Types.PLAYER, â˜ƒ);
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      super.setPlacedBy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof SkullBlockEntity â˜ƒ) {
         GameProfile â˜ƒxx = null;
         if (â˜ƒ.hasTag()) {
            CompoundTag â˜ƒxxx = â˜ƒ.getTag();
            if (â˜ƒxxx.contains("SkullOwner", 10)) {
               â˜ƒxx = NbtUtils.readGameProfile(â˜ƒxxx.getCompound("SkullOwner"));
            } else if (â˜ƒxxx.contains("SkullOwner", 8) && !StringUtils.isBlank(â˜ƒxxx.getString("SkullOwner"))) {
               â˜ƒxx = new GameProfile(null, â˜ƒxxx.getString("SkullOwner"));
            }
         }

         â˜ƒ.setOwner(â˜ƒxx);
      }
   }
}
