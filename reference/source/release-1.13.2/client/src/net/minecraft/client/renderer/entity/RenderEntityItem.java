package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderEntityItem extends Render<EntityItem> {
   private final ItemRenderer field_177080_a;
   private final Random field_177079_e = new Random();

   public RenderEntityItem(RenderManager var1, ItemRenderer var2) {
      super(☃);
      this.field_177080_a = ☃;
      this.field_76989_e = 0.15F;
      this.field_76987_f = 0.75F;
   }

   private int func_177077_a(EntityItem var1, double var2, double var4, double var6, float var8, IBakedModel var9) {
      ItemStack ☃ = ☃.func_92059_d();
      Item ☃x = ☃.func_77973_b();
      if (☃x == null) {
         return 0;
      } else {
         boolean ☃ = ☃.func_177556_c();
         int ☃x = this.func_177078_a(☃);
         float ☃xx = 0.25F;
         float ☃xxx = MathHelper.func_76126_a(((float)☃.func_174872_o() + ☃) / 10.0F + ☃.field_70290_d) * 0.1F + 0.1F;
         float ☃xxxx = ☃.func_177552_f().func_181688_b(ItemCameraTransforms.TransformType.GROUND).field_178363_d.func_195900_b();
         GlStateManager.func_179109_b((float)☃, (float)☃ + ☃xxx + 0.25F * ☃xxxx, (float)☃);
         if (☃ || this.field_76990_c.field_78733_k != null) {
            float ☃xxxxx = (((float)☃.func_174872_o() + ☃) / 20.0F + ☃.field_70290_d) * (180.0F / (float)Math.PI);
            GlStateManager.func_179114_b(☃xxxxx, 0.0F, 1.0F, 0.0F);
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         return ☃x;
      }
   }

   private int func_177078_a(ItemStack var1) {
      int ☃ = 1;
      if (☃.func_190916_E() > 48) {
         ☃ = 5;
      } else if (☃.func_190916_E() > 32) {
         ☃ = 4;
      } else if (☃.func_190916_E() > 16) {
         ☃ = 3;
      } else if (☃.func_190916_E() > 1) {
         ☃ = 2;
      }

      return ☃;
   }

   public void func_76986_a(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      ItemStack ☃ = ☃.func_92059_d();
      int ☃x = ☃.func_190926_b() ? 187 : Item.func_150891_b(☃.func_77973_b()) + ☃.func_77952_i();
      this.field_177079_e.setSeed((long)☃x);
      boolean ☃xx = false;
      if (this.func_180548_c(☃)) {
         this.field_76990_c.field_78724_e.func_110581_b(this.func_110775_a(☃)).func_174936_b(false, false);
         ☃xx = true;
      }

      GlStateManager.func_179091_B();
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179147_l();
      RenderHelper.func_74519_b();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179094_E();
      IBakedModel ☃ = this.field_177080_a.func_184393_a(☃, ☃.field_70170_p, null);
      int ☃x = this.func_177077_a(☃, ☃, ☃, ☃, ☃, ☃);
      float ☃xx = ☃.func_177552_f().field_181699_o.field_178363_d.func_195899_a();
      float ☃xxx = ☃.func_177552_f().field_181699_o.field_178363_d.func_195900_b();
      float ☃xxxx = ☃.func_177552_f().field_181699_o.field_178363_d.func_195902_c();
      boolean ☃xxxxx = ☃.func_177556_c();
      if (!☃xxxxx) {
         float ☃xxxxxx = -0.0F * (float)(☃x - 1) * 0.5F * ☃xx;
         float ☃xxxxxxx = -0.0F * (float)(☃x - 1) * 0.5F * ☃xxx;
         float ☃xxxxxxxx = -0.09375F * (float)(☃x - 1) * 0.5F * ☃xxxx;
         GlStateManager.func_179109_b(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
      }

      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      for(int ☃ = 0; ☃ < ☃x; ++☃) {
         if (☃xxxxx) {
            GlStateManager.func_179094_E();
            if (☃ > 0) {
               float ☃x = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float ☃xx = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float ☃xxx = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               GlStateManager.func_179109_b(☃x, ☃xx, ☃xxx);
            }

            ☃.func_177552_f().func_181689_a(ItemCameraTransforms.TransformType.GROUND);
            this.field_177080_a.func_180454_a(☃, ☃);
            GlStateManager.func_179121_F();
         } else {
            GlStateManager.func_179094_E();
            if (☃ > 0) {
               float ☃x = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float ☃xx = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               GlStateManager.func_179109_b(☃x, ☃xx, 0.0F);
            }

            ☃.func_177552_f().func_181689_a(ItemCameraTransforms.TransformType.GROUND);
            this.field_177080_a.func_180454_a(☃, ☃);
            GlStateManager.func_179121_F();
            GlStateManager.func_179109_b(0.0F * ☃xx, 0.0F * ☃xxx, 0.09375F * ☃xxxx);
         }
      }

      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179101_C();
      GlStateManager.func_179084_k();
      this.func_180548_c(☃);
      if (☃xx) {
         this.field_76990_c.field_78724_e.func_110581_b(this.func_110775_a(☃)).func_174935_a();
      }

      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityItem var1) {
      return TextureMap.field_110575_b;
   }
}
