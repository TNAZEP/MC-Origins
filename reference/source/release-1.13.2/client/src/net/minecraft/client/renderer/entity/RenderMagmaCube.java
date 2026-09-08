package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelMagmaCube;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
   private static final ResourceLocation field_110873_a = new ResourceLocation("textures/entity/slime/magmacube.png");

   public RenderMagmaCube(RenderManager var1) {
      super(☃, new ModelMagmaCube(), 0.25F);
   }

   protected ResourceLocation func_110775_a(EntityMagmaCube var1) {
      return field_110873_a;
   }

   protected void func_77041_b(EntityMagmaCube var1, float var2) {
      int ☃ = ☃.func_70809_q();
      float ☃x = (☃.field_70812_c + (☃.field_70811_b - ☃.field_70812_c) * ☃) / ((float)☃ * 0.5F + 1.0F);
      float ☃xx = 1.0F / (☃x + 1.0F);
      GlStateManager.func_179152_a(☃xx * (float)☃, 1.0F / ☃xx * (float)☃, ☃xx * (float)☃);
   }
}
