package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSprite<EntityPotion> {
   public RenderPotion(RenderManager var1, ItemRenderer var2) {
      super(☃, Items.field_151068_bn, ☃);
   }

   public ItemStack func_177082_d(EntityPotion var1) {
      return ☃.func_184543_l();
   }
}
