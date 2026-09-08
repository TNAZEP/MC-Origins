package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.model.ModelIllager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

public class RenderVindicator extends RenderLiving<EntityMob> {
   private static final ResourceLocation field_191357_a = new ResourceLocation("textures/entity/illager/vindicator.png");

   public RenderVindicator(RenderManager var1) {
      super(☃, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
      this.func_177094_a(new LayerHeldItem(this) {
         @Override
         public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
            if (((EntityVindicator)☃).func_190639_o()) {
               super.func_177141_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
            }
         }

         @Override
         protected void func_191361_a(EnumHandSide var1) {
            ((ModelIllager)this.field_177206_a.func_177087_b()).func_191216_a(☃).func_78794_c(0.0625F);
         }
      });
   }

   public void func_76986_a(EntityMob var1, double var2, double var4, double var6, float var8, float var9) {
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityMob var1) {
      return field_191357_a;
   }

   protected void func_77041_b(EntityMob var1, float var2) {
      float ☃ = 0.9375F;
      GlStateManager.func_179152_a(0.9375F, 0.9375F, 0.9375F);
   }
}
