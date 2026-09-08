package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.MapData;

public class RenderItemFrame extends Render<EntityItemFrame> {
   private static final ResourceLocation field_110789_a = new ResourceLocation("textures/map/map_background.png");
   private static final ModelResourceLocation field_209585_f = new ModelResourceLocation("item_frame", "map=false");
   private static final ModelResourceLocation field_209586_g = new ModelResourceLocation("item_frame", "map=true");
   private final Minecraft field_147917_g = Minecraft.func_71410_x();
   private final ItemRenderer field_177074_h;

   public RenderItemFrame(RenderManager var1, ItemRenderer var2) {
      super(☃);
      this.field_177074_h = ☃;
   }

   public void func_76986_a(EntityItemFrame var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      BlockPos ☃ = ☃.func_174857_n();
      double ☃x = (double)☃.func_177958_n() - ☃.field_70165_t + ☃;
      double ☃xx = (double)☃.func_177956_o() - ☃.field_70163_u + ☃;
      double ☃xxx = (double)☃.func_177952_p() - ☃.field_70161_v + ☃;
      GlStateManager.func_179137_b(☃x + 0.5, ☃xx + 0.5, ☃xxx + 0.5);
      GlStateManager.func_179114_b(☃.field_70125_A, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F - ☃.field_70177_z, 0.0F, 1.0F, 0.0F);
      this.field_76990_c.field_78724_e.func_110577_a(TextureMap.field_110575_b);
      BlockRendererDispatcher ☃xxxx = this.field_147917_g.func_175602_ab();
      ModelManager ☃xxxxx = ☃xxxx.func_175023_a().func_178126_b();
      ModelResourceLocation ☃xxxxxx = ☃.func_82335_i().func_77973_b() == Items.field_151098_aY ? field_209586_g : field_209585_f;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      ☃xxxx.func_175019_b().func_178262_a(☃xxxxx.func_174953_a(☃xxxxxx), 1.0F, 1.0F, 1.0F, 1.0F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179145_e();
      if (☃.func_82335_i().func_77973_b() == Items.field_151098_aY) {
         GlStateManager.func_179123_a();
         RenderHelper.func_74519_b();
      }

      GlStateManager.func_179109_b(0.0F, 0.0F, 0.4375F);
      this.func_82402_b(☃);
      if (☃.func_82335_i().func_77973_b() == Items.field_151098_aY) {
         RenderHelper.func_74518_a();
         GlStateManager.func_179099_b();
      }

      GlStateManager.func_179145_e();
      GlStateManager.func_179121_F();
      this.func_177067_a(☃, ☃ + (double)((float)☃.field_174860_b.func_82601_c() * 0.3F), ☃ - 0.25, ☃ + (double)((float)☃.field_174860_b.func_82599_e() * 0.3F));
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntityItemFrame var1) {
      return null;
   }

   private void func_82402_b(EntityItemFrame var1) {
      ItemStack ☃ = ☃.func_82335_i();
      if (!☃.func_190926_b()) {
         GlStateManager.func_179094_E();
         boolean ☃x = ☃.func_77973_b() == Items.field_151098_aY;
         int ☃xx = ☃x ? ☃.func_82333_j() % 4 * 2 : ☃.func_82333_j();
         GlStateManager.func_179114_b((float)☃xx * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (☃x) {
            GlStateManager.func_179140_f();
            this.field_76990_c.field_78724_e.func_110577_a(field_110789_a);
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
            float ☃xxx = 0.0078125F;
            GlStateManager.func_179152_a(0.0078125F, 0.0078125F, 0.0078125F);
            GlStateManager.func_179109_b(-64.0F, -64.0F, 0.0F);
            MapData ☃xxxx = ItemMap.func_195950_a(☃, ☃.field_70170_p);
            GlStateManager.func_179109_b(0.0F, 0.0F, -1.0F);
            if (☃xxxx != null) {
               this.field_147917_g.field_71460_t.func_147701_i().func_148250_a(☃xxxx, true);
            }
         } else {
            GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
            this.field_177074_h.func_181564_a(☃, ItemCameraTransforms.TransformType.FIXED);
         }

         GlStateManager.func_179121_F();
      }
   }

   protected void func_177067_a(EntityItemFrame var1, double var2, double var4, double var6) {
      if (Minecraft.func_71382_s() && !☃.func_82335_i().func_190926_b() && ☃.func_82335_i().func_82837_s() && this.field_76990_c.field_147941_i == ☃) {
         double ☃ = ☃.func_70068_e(this.field_76990_c.field_78734_h);
         float ☃x = ☃.func_70093_af() ? 32.0F : 64.0F;
         if (!(☃ >= (double)(☃x * ☃x))) {
            String ☃xx = ☃.func_82335_i().func_200301_q().func_150254_d();
            this.func_147906_a(☃, ☃xx, ☃, ☃, ☃, 64);
         }
      }
   }
}
