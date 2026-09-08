package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelSalmon;
import net.minecraft.entity.passive.EntitySalmon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderSalmon extends RenderLiving<EntitySalmon> {
   private static final ResourceLocation field_203776_a = new ResourceLocation("textures/entity/fish/salmon.png");

   public RenderSalmon(RenderManager var1) {
      super(☃, new ModelSalmon(), 0.2F);
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntitySalmon var1) {
      return field_203776_a;
   }

   protected void func_77043_a(EntitySalmon var1, float var2, float var3, float var4) {
      super.func_77043_a(☃, ☃, ☃, ☃);
      float ☃ = 1.0F;
      float ☃x = 1.0F;
      if (!☃.func_70090_H()) {
         ☃ = 1.3F;
         ☃x = 1.7F;
      }

      float ☃ = ☃ * 4.3F * MathHelper.func_76126_a(☃x * 0.6F * ☃);
      GlStateManager.func_179114_b(☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, 0.0F, -0.4F);
      if (!☃.func_70090_H()) {
         GlStateManager.func_179109_b(0.2F, 0.1F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      }
   }
}
