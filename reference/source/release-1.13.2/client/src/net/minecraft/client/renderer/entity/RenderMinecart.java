package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelMinecart;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
   private static final ResourceLocation field_110804_g = new ResourceLocation("textures/entity/minecart.png");
   protected ModelBase field_77013_a = new ModelMinecart();

   public RenderMinecart(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(T var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      this.func_180548_c(☃);
      long ☃ = (long)☃.func_145782_y() * 493286711L;
      ☃ = ☃ * ☃ * 4392167121L + ☃ * 98761L;
      float ☃x = (((float)(☃ >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float ☃xx = (((float)(☃ >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float ☃xxx = (((float)(☃ >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      GlStateManager.func_179109_b(☃x, ☃xx, ☃xxx);
      double ☃xxxx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxxxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      double ☃xxxxxxx = 0.3F;
      Vec3d ☃xxxxxxxx = ☃.func_70489_a(☃xxxx, ☃xxxxx, ☃xxxxxx);
      float ☃xxxxxxxxx = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      if (☃xxxxxxxx != null) {
         Vec3d ☃xxxxxxxxxx = ☃.func_70495_a(☃xxxx, ☃xxxxx, ☃xxxxxx, 0.3F);
         Vec3d ☃xxxxxxxxxxx = ☃.func_70495_a(☃xxxx, ☃xxxxx, ☃xxxxxx, -0.3F);
         if (☃xxxxxxxxxx == null) {
            ☃xxxxxxxxxx = ☃xxxxxxxx;
         }

         if (☃xxxxxxxxxxx == null) {
            ☃xxxxxxxxxxx = ☃xxxxxxxx;
         }

         ☃ += ☃xxxxxxxx.field_72450_a - ☃xxxx;
         ☃ += (☃xxxxxxxxxx.field_72448_b + ☃xxxxxxxxxxx.field_72448_b) / 2.0 - ☃xxxxx;
         ☃ += ☃xxxxxxxx.field_72449_c - ☃xxxxxx;
         Vec3d ☃xxxxxxxxxx = ☃xxxxxxxxxxx.func_72441_c(-☃xxxxxxxxxx.field_72450_a, -☃xxxxxxxxxx.field_72448_b, -☃xxxxxxxxxx.field_72449_c);
         if (☃xxxxxxxxxx.func_72433_c() != 0.0) {
            ☃xxxxxxxxxx = ☃xxxxxxxxxx.func_72432_b();
            ☃ = (float)(Math.atan2(☃xxxxxxxxxx.field_72449_c, ☃xxxxxxxxxx.field_72450_a) * 180.0 / Math.PI);
            ☃xxxxxxxxx = (float)(Math.atan(☃xxxxxxxxxx.field_72448_b) * 73.0);
         }
      }

      GlStateManager.func_179109_b((float)☃, (float)☃ + 0.375F, (float)☃);
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-☃xxxxxxxxx, 0.0F, 0.0F, 1.0F);
      float ☃ = (float)☃.func_70496_j() - ☃;
      float ☃x = ☃.func_70491_i() - ☃;
      if (☃x < 0.0F) {
         ☃x = 0.0F;
      }

      if (☃ > 0.0F) {
         GlStateManager.func_179114_b(MathHelper.func_76126_a(☃) * ☃ * ☃x / 10.0F * (float)☃.func_70493_k(), 1.0F, 0.0F, 0.0F);
      }

      int ☃ = ☃.func_94099_q();
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      IBlockState ☃ = ☃.func_174897_t();
      if (☃.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
         GlStateManager.func_179094_E();
         this.func_110776_a(TextureMap.field_110575_b);
         float ☃x = 0.75F;
         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
         GlStateManager.func_179109_b(-0.5F, (float)(☃ - 8) / 16.0F, 0.5F);
         this.func_188319_a(☃, ☃, ☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_180548_c(☃);
      }

      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      this.field_77013_a.func_78088_a(☃, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(T var1) {
      return field_110804_g;
   }

   protected void func_188319_a(T var1, float var2, IBlockState var3) {
      GlStateManager.func_179094_E();
      Minecraft.func_71410_x().func_175602_ab().func_175016_a(☃, ☃.func_70013_c());
      GlStateManager.func_179121_F();
   }
}
