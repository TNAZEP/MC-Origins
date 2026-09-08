package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.client.renderer.entity.model.ModelCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderCreeper extends RenderLiving<EntityCreeper> {
   private static final ResourceLocation field_110830_f = new ResourceLocation("textures/entity/creeper/creeper.png");

   public RenderCreeper(RenderManager var1) {
      super(☃, new ModelCreeper(), 0.5F);
      this.func_177094_a(new LayerCreeperCharge(this));
   }

   protected void func_77041_b(EntityCreeper var1, float var2) {
      float ☃ = ☃.func_70831_j(☃);
      float ☃x = 1.0F + MathHelper.func_76126_a(☃ * 100.0F) * ☃ * 0.01F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      ☃ *= ☃;
      ☃ *= ☃;
      float ☃xx = (1.0F + ☃ * 0.4F) * ☃x;
      float ☃xxx = (1.0F + ☃ * 0.1F) / ☃x;
      GlStateManager.func_179152_a(☃xx, ☃xxx, ☃xx);
   }

   protected int func_77030_a(EntityCreeper var1, float var2, float var3) {
      float ☃ = ☃.func_70831_j(☃);
      if ((int)(☃ * 10.0F) % 2 == 0) {
         return 0;
      } else {
         int ☃ = (int)(☃ * 0.2F * 255.0F);
         ☃ = MathHelper.func_76125_a(☃, 0, 255);
         return ☃ << 24 | 822083583;
      }
   }

   protected ResourceLocation func_110775_a(EntityCreeper var1) {
      return field_110830_f;
   }
}
