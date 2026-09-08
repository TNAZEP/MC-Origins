package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.IMultipassModel;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBoat extends Render<EntityBoat> {
   private static final ResourceLocation[] field_110782_f = new ResourceLocation[]{
      new ResourceLocation("textures/entity/boat/oak.png"),
      new ResourceLocation("textures/entity/boat/spruce.png"),
      new ResourceLocation("textures/entity/boat/birch.png"),
      new ResourceLocation("textures/entity/boat/jungle.png"),
      new ResourceLocation("textures/entity/boat/acacia.png"),
      new ResourceLocation("textures/entity/boat/dark_oak.png")
   };
   protected ModelBase field_76998_a = new ModelBoat();

   public RenderBoat(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityBoat var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      this.func_188309_a(☃, ☃, ☃);
      this.func_188311_a(☃, ☃, ☃);
      this.func_180548_c(☃);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      this.field_76998_a.func_78088_a(☃, ☃, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_188311_a(EntityBoat var1, float var2, float var3) {
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      float ☃ = (float)☃.func_70268_h() - ☃;
      float ☃x = ☃.func_70271_g() - ☃;
      if (☃x < 0.0F) {
         ☃x = 0.0F;
      }

      if (☃ > 0.0F) {
         GlStateManager.func_179114_b(MathHelper.func_76126_a(☃) * ☃ * ☃x / 10.0F * (float)☃.func_70267_i(), 1.0F, 0.0F, 0.0F);
      }

      float ☃ = ☃.func_203056_b(☃);
      if (!MathHelper.func_180185_a(☃, 0.0F)) {
         GlStateManager.func_179114_b(☃.func_203056_b(☃), 1.0F, 0.0F, 1.0F);
      }

      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
   }

   public void func_188309_a(double var1, double var3, double var5) {
      GlStateManager.func_179109_b((float)☃, (float)☃ + 0.375F, (float)☃);
   }

   protected ResourceLocation func_110775_a(EntityBoat var1) {
      return field_110782_f[☃.func_184453_r().ordinal()];
   }

   @Override
   public boolean func_188295_H_() {
      return true;
   }

   public void func_188300_b(EntityBoat var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      this.func_188309_a(☃, ☃, ☃);
      this.func_188311_a(☃, ☃, ☃);
      this.func_180548_c(☃);
      ((IMultipassModel)this.field_76998_a).func_187054_b(☃, ☃, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
   }
}
