package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.model.ModelElytra;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerElytra implements LayerRenderer<EntityLivingBase> {
   private static final ResourceLocation field_188355_a = new ResourceLocation("textures/entity/elytra.png");
   protected final RenderLivingBase<?> field_188356_b;
   private final ModelElytra field_188357_c = new ModelElytra();

   public LayerElytra(RenderLivingBase<?> var1) {
      this.field_188356_b = ☃;
   }

   @Override
   public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack ☃ = ☃.func_184582_a(EntityEquipmentSlot.CHEST);
      if (☃.func_77973_b() == Items.field_185160_cR) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         if (☃ instanceof AbstractClientPlayer) {
            AbstractClientPlayer ☃x = (AbstractClientPlayer)☃;
            if (☃x.func_184833_s() && ☃x.func_184834_t() != null) {
               this.field_188356_b.func_110776_a(☃x.func_184834_t());
            } else if (☃x.func_152122_n() && ☃x.func_110303_q() != null && ☃x.func_175148_a(EnumPlayerModelParts.CAPE)) {
               this.field_188356_b.func_110776_a(☃x.func_110303_q());
            } else {
               this.field_188356_b.func_110776_a(field_188355_a);
            }
         } else {
            this.field_188356_b.func_110776_a(field_188355_a);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
         this.field_188357_c.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         this.field_188357_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         if (☃.func_77948_v()) {
            LayerArmorBase.func_188364_a(this.field_188356_b, ☃, this.field_188357_c, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
