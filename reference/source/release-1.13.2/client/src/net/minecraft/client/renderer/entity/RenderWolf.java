package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.client.renderer.entity.model.ModelWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving<EntityWolf> {
   private static final ResourceLocation field_110917_a = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation field_110915_f = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation field_110916_g = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public RenderWolf(RenderManager var1) {
      super(☃, new ModelWolf(), 0.5F);
      this.func_177094_a(new LayerWolfCollar(this));
   }

   protected float func_77044_a(EntityWolf var1, float var2) {
      return ☃.func_70920_v();
   }

   public void func_76986_a(EntityWolf var1, double var2, double var4, double var6, float var8, float var9) {
      if (☃.func_70921_u()) {
         float ☃ = ☃.func_70013_c() * ☃.func_70915_j(☃);
         GlStateManager.func_179124_c(☃, ☃, ☃);
      }

      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityWolf var1) {
      if (☃.func_70909_n()) {
         return field_110915_f;
      } else {
         return ☃.func_70919_bu() ? field_110916_g : field_110917_a;
      }
   }
}
