package net.minecraft.world.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadItem extends StandingAndWallBlockItem {
   public static final String TAG_SKULL_OWNER = "SkullOwner";

   public PlayerHeadItem(Block var1, Block var2, Item.Properties var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Component getName(ItemStack var1) {
      if (â˜ƒ.is(Items.PLAYER_HEAD) && â˜ƒ.hasTag()) {
         String â˜ƒ = null;
         CompoundTag â˜ƒx = â˜ƒ.getTag();
         if (â˜ƒx.contains("SkullOwner", 8)) {
            â˜ƒ = â˜ƒx.getString("SkullOwner");
         } else if (â˜ƒx.contains("SkullOwner", 10)) {
            CompoundTag â˜ƒ = â˜ƒx.getCompound("SkullOwner");
            if (â˜ƒ.contains("Name", 8)) {
               â˜ƒ = â˜ƒ.getString("Name");
            }
         }

         if (â˜ƒ != null) {
            return new TranslatableComponent(this.getDescriptionId() + ".named", â˜ƒ);
         }
      }

      return super.getName(â˜ƒ);
   }

   @Override
   public void verifyTagAfterLoad(CompoundTag var1) {
      super.verifyTagAfterLoad(â˜ƒ);
      if (â˜ƒ.contains("SkullOwner", 8) && !StringUtils.isBlank(â˜ƒ.getString("SkullOwner"))) {
         GameProfile â˜ƒ = new GameProfile(null, â˜ƒ.getString("SkullOwner"));
         SkullBlockEntity.updateGameprofile(â˜ƒ, var1x -> â˜ƒ.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), var1x)));
      }
   }
}
