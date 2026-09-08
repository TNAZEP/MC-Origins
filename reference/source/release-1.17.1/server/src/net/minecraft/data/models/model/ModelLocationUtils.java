package net.minecraft.data.models.model;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModelLocationUtils {
   @Deprecated
   public static ResourceLocation decorateBlockModelLocation(String var0) {
      return new ResourceLocation("minecraft", "block/" + â˜ƒ);
   }

   public static ResourceLocation decorateItemModelLocation(String var0) {
      return new ResourceLocation("minecraft", "item/" + â˜ƒ);
   }

   public static ResourceLocation getModelLocation(Block var0, String var1) {
      ResourceLocation â˜ƒ = Registry.BLOCK.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "block/" + â˜ƒ.getPath() + â˜ƒ);
   }

   public static ResourceLocation getModelLocation(Block var0) {
      ResourceLocation â˜ƒ = Registry.BLOCK.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "block/" + â˜ƒ.getPath());
   }

   public static ResourceLocation getModelLocation(Item var0) {
      ResourceLocation â˜ƒ = Registry.ITEM.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "item/" + â˜ƒ.getPath());
   }

   public static ResourceLocation getModelLocation(Item var0, String var1) {
      ResourceLocation â˜ƒ = Registry.ITEM.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "item/" + â˜ƒ.getPath() + â˜ƒ);
   }
}
