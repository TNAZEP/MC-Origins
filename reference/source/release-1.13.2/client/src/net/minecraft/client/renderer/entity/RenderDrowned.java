package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerDrownedOuter;
import net.minecraft.client.renderer.entity.model.ModelDrowned;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderDrowned extends RenderZombie {
   private static final ResourceLocation field_204720_a = new ResourceLocation("textures/entity/zombie/drowned.png");
   private float field_208407_j;

   public RenderDrowned(RenderManager var1) {
      super(☃, new ModelDrowned(0.0F, 0.0F, 64, 64));
      this.func_177094_a(new LayerDrownedOuter(this));
   }

   @Override
   protected LayerBipedArmor func_209265_c() {
      return new LayerBipedArmor(this) {
         @Override
         protected void func_177177_a() {
            this.field_177189_c = new ModelDrowned(0.5F, true);
            this.field_177186_d = new ModelDrowned(1.0F, true);
         }
      };
   }

   @Nullable
   @Override
   protected ResourceLocation func_110775_a(EntityZombie var1) {
      return field_204720_a;
   }

   @Override
   protected void func_77043_a(EntityZombie var1, float var2, float var3, float var4) {
      float ☃ = ☃.func_205015_b(☃);
      super.func_77043_a(☃, ☃, ☃, ☃);
      if (☃ > 0.0F) {
         float ☃x = this.func_208406_b(☃.field_70125_A, -10.0F - ☃.field_70125_A, ☃);
         if (!☃.func_203007_ba()) {
            ☃x = this.func_77034_a(this.field_208407_j, 0.0F, 1.0F - ☃);
         }

         GlStateManager.func_179114_b(☃x, 1.0F, 0.0F, 0.0F);
         if (☃.func_203007_ba()) {
            this.field_208407_j = ☃x;
         }
      }
   }

   private float func_208406_b(float var1, float var2, float var3) {
      return ☃ + (☃ - ☃) * ☃;
   }
}
