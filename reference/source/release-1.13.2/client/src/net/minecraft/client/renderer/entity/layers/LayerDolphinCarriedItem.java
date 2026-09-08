package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class LayerDolphinCarriedItem implements LayerRenderer<EntityLivingBase> {
   protected final RenderLivingBase<?> field_205130_a;
   private final ItemRenderer field_205131_b;

   public LayerDolphinCarriedItem(RenderLivingBase<?> var1) {
      this.field_205130_a = ☃;
      this.field_205131_b = Minecraft.func_71410_x().func_175599_af();
   }

   @Override
   public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      boolean ☃ = ☃.func_184591_cq() == EnumHandSide.RIGHT;
      ItemStack ☃x = ☃ ? ☃.func_184592_cb() : ☃.func_184614_ca();
      ItemStack ☃xx = ☃ ? ☃.func_184614_ca() : ☃.func_184592_cb();
      if (!☃x.func_190926_b() || !☃xx.func_190926_b()) {
         this.func_205129_a(☃, ☃xx);
      }
   }

   private void func_205129_a(EntityLivingBase var1, ItemStack var2) {
      if (!☃.func_190926_b()) {
         if (!☃.func_190926_b()) {
            Item ☃ = ☃.func_77973_b();
            Block ☃x = Block.func_149634_a(☃);
            GlStateManager.func_179094_E();
            boolean ☃xx = this.field_205131_b.func_175050_a(☃) && ☃x.func_180664_k() == BlockRenderLayer.TRANSLUCENT;
            if (☃xx) {
               GlStateManager.func_179132_a(false);
            }

            float ☃ = 1.0F;
            float ☃x = -1.0F;
            float ☃xx = MathHelper.func_76135_e(☃.field_70125_A) / 60.0F;
            if (☃.field_70125_A < 0.0F) {
               GlStateManager.func_179109_b(0.0F, 1.0F - ☃xx * 0.5F, -1.0F + ☃xx * 0.5F);
            } else {
               GlStateManager.func_179109_b(0.0F, 1.0F + ☃xx * 0.8F, -1.0F + ☃xx * 0.2F);
            }

            this.field_205131_b.func_184392_a(☃, ☃, ItemCameraTransforms.TransformType.GROUND, false);
            if (☃xx) {
               GlStateManager.func_179132_a(true);
            }

            GlStateManager.func_179121_F();
         }
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
