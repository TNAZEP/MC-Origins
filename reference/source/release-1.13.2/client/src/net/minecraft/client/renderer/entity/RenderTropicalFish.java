package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerTropicalFishPattern;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelTropicalFishA;
import net.minecraft.client.renderer.entity.model.ModelTropicalFishB;
import net.minecraft.entity.passive.EntityTropicalFish;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderTropicalFish extends RenderLiving<EntityTropicalFish> {
   private final ModelTropicalFishA field_204246_a = new ModelTropicalFishA();
   private final ModelTropicalFishB field_204247_j = new ModelTropicalFishB();

   public RenderTropicalFish(RenderManager var1) {
      super(☃, new ModelTropicalFishA(), 0.15F);
      this.func_177094_a(new LayerTropicalFishPattern(this));
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntityTropicalFish var1) {
      return ☃.func_204218_dG();
   }

   public void func_76986_a(EntityTropicalFish var1, double var2, double var4, double var6, float var8, float var9) {
      this.field_77045_g = (ModelBase)(☃.func_204217_dE() == 0 ? this.field_204246_a : this.field_204247_j);
      float[] ☃ = ☃.func_204219_dC();
      GlStateManager.func_179124_c(☃[0], ☃[1], ☃[2]);
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected void func_77043_a(EntityTropicalFish var1, float var2, float var3, float var4) {
      super.func_77043_a(☃, ☃, ☃, ☃);
      float ☃ = 4.3F * MathHelper.func_76126_a(0.6F * ☃);
      GlStateManager.func_179114_b(☃, 0.0F, 1.0F, 0.0F);
      if (!☃.func_70090_H()) {
         GlStateManager.func_179109_b(0.2F, 0.1F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      }
   }
}
