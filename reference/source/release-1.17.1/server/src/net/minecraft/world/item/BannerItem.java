package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.apache.commons.lang3.Validate;

public class BannerItem extends StandingAndWallBlockItem {
   private static final String PATTERN_PREFIX = "block.minecraft.banner.";

   public BannerItem(Block var1, Block var2, Item.Properties var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      Validate.isInstanceOf(AbstractBannerBlock.class, â˜ƒ);
      Validate.isInstanceOf(AbstractBannerBlock.class, â˜ƒ);
   }

   public static void appendHoverTextFromBannerBlockEntityTag(ItemStack var0, List<Component> var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
      if (â˜ƒ != null && â˜ƒ.contains("Patterns")) {
         ListTag â˜ƒx = â˜ƒ.getList("Patterns", 10);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size() && â˜ƒxx < 6; ++â˜ƒxx) {
            CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
            DyeColor â˜ƒxxxx = DyeColor.byId(â˜ƒxxx.getInt("Color"));
            BannerPattern â˜ƒxxxxx = BannerPattern.byHash(â˜ƒxxx.getString("Pattern"));
            if (â˜ƒxxxxx != null) {
               â˜ƒ.add(new TranslatableComponent("block.minecraft.banner." + â˜ƒxxxxx.getFilename() + "." + â˜ƒxxxx.getName()).withStyle(ChatFormatting.GRAY));
            }
         }
      }
   }

   public DyeColor getColor() {
      return ((AbstractBannerBlock)this.getBlock()).getColor();
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      appendHoverTextFromBannerBlockEntityTag(â˜ƒ, â˜ƒ);
   }
}
