package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.ModelChicken;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderChicken extends RenderLiving<EntityChicken> {
   private static final ResourceLocation field_110920_a = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(RenderManager var1) {
      super(☃, new ModelChicken(), 0.3F);
   }

   protected ResourceLocation func_110775_a(EntityChicken var1) {
      return field_110920_a;
   }

   protected float func_77044_a(EntityChicken var1, float var2) {
      float ☃ = ☃.field_70888_h + (☃.field_70886_e - ☃.field_70888_h) * ☃;
      float ☃x = ☃.field_70884_g + (☃.field_70883_f - ☃.field_70884_g) * ☃;
      return (MathHelper.func_76126_a(☃) + 1.0F) * ☃x;
   }
}
