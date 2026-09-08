package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;

public class LayerHeldItemWitch implements LayerRenderer<EntityWitch> {
   private final RenderWitch field_177144_a;

   public LayerHeldItemWitch(RenderWitch var1) {
      this.field_177144_a = ☃;
   }

   public void func_177141_a(EntityWitch var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack ☃ = ☃.func_184614_ca();
      if (!☃.func_190926_b()) {
         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179094_E();
         if (this.field_177144_a.func_177087_b().field_78091_s) {
            GlStateManager.func_179109_b(0.0F, 0.625F, 0.0F);
            GlStateManager.func_179114_b(-20.0F, -1.0F, 0.0F, 0.0F);
            float ☃x = 0.5F;
            GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         }

         this.field_177144_a.func_177087_b().func_205073_b().func_78794_c(0.0625F);
         GlStateManager.func_179109_b(-0.0625F, 0.53125F, 0.21875F);
         Item ☃x = ☃.func_77973_b();
         Minecraft ☃xx = Minecraft.func_71410_x();
         if (Block.func_149634_a(☃x).func_176223_P().func_185901_i() == EnumBlockRenderType.ENTITYBLOCK_ANIMATED) {
            GlStateManager.func_179109_b(0.0F, 0.0625F, -0.25F);
            GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-5.0F, 0.0F, 1.0F, 0.0F);
            float ☃xxx = 0.375F;
            GlStateManager.func_179152_a(0.375F, -0.375F, 0.375F);
         } else if (☃x == Items.field_151031_f) {
            GlStateManager.func_179109_b(0.0F, 0.125F, -0.125F);
            GlStateManager.func_179114_b(-45.0F, 0.0F, 1.0F, 0.0F);
            float ☃x = 0.625F;
            GlStateManager.func_179152_a(0.625F, -0.625F, 0.625F);
            GlStateManager.func_179114_b(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-20.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.func_179109_b(0.1875F, 0.1875F, 0.0F);
            float ☃x = 0.875F;
            GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
            GlStateManager.func_179114_b(-20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-60.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-30.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.func_179114_b(-15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(40.0F, 0.0F, 0.0F, 1.0F);
         ☃xx.func_175597_ag().func_178099_a(☃, ☃, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
         GlStateManager.func_179121_F();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
