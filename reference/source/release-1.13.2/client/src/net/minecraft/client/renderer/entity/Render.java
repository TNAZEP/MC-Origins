package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReaderBase;

public abstract class Render<T extends Entity> {
   private static final ResourceLocation field_110778_a = new ResourceLocation("textures/misc/shadow.png");
   protected final RenderManager field_76990_c;
   protected float field_76989_e;
   protected float field_76987_f = 1.0F;
   protected boolean field_188301_f;

   protected Render(RenderManager var1) {
      this.field_76990_c = ☃;
   }

   public void func_188297_a(boolean var1) {
      this.field_188301_f = ☃;
   }

   public boolean func_177071_a(T var1, ICamera var2, double var3, double var5, double var7) {
      AxisAlignedBB ☃ = ☃.func_184177_bl().func_186662_g(0.5);
      if (☃.func_181656_b() || ☃.func_72320_b() == 0.0) {
         ☃ = new AxisAlignedBB(
            ☃.field_70165_t - 2.0, ☃.field_70163_u - 2.0, ☃.field_70161_v - 2.0, ☃.field_70165_t + 2.0, ☃.field_70163_u + 2.0, ☃.field_70161_v + 2.0
         );
      }

      return ☃.func_145770_h(☃, ☃, ☃) && (☃.field_70158_ak || ☃.func_78546_a(☃));
   }

   public void func_76986_a(T var1, double var2, double var4, double var6, float var8, float var9) {
      if (!this.field_188301_f) {
         this.func_177067_a(☃, ☃, ☃, ☃);
      }
   }

   protected int func_188298_c(T var1) {
      ScorePlayerTeam ☃ = (ScorePlayerTeam)☃.func_96124_cp();
      return ☃ != null && ☃.func_178775_l().func_211163_e() != null ? ☃.func_178775_l().func_211163_e() : 16777215;
   }

   protected void func_177067_a(T var1, double var2, double var4, double var6) {
      if (this.func_177070_b(☃)) {
         this.func_147906_a(☃, ☃.func_145748_c_().func_150254_d(), ☃, ☃, ☃, 64);
      }
   }

   protected boolean func_177070_b(T var1) {
      return ☃.func_94059_bO() && ☃.func_145818_k_();
   }

   protected void func_188296_a(T var1, double var2, double var4, double var6, String var8, double var9) {
      this.func_147906_a(☃, ☃, ☃, ☃, ☃, 64);
   }

   @Nullable
   protected abstract ResourceLocation func_110775_a(T var1);

   protected boolean func_180548_c(T var1) {
      ResourceLocation ☃ = this.func_110775_a(☃);
      if (☃ == null) {
         return false;
      } else {
         this.func_110776_a(☃);
         return true;
      }
   }

   public void func_110776_a(ResourceLocation var1) {
      this.field_76990_c.field_78724_e.func_110577_a(☃);
   }

   private void func_76977_a(Entity var1, double var2, double var4, double var6, float var8) {
      GlStateManager.func_179140_f();
      TextureMap ☃ = Minecraft.func_71410_x().func_147117_R();
      TextureAtlasSprite ☃x = ☃.func_195424_a(ModelBakery.field_207763_a);
      TextureAtlasSprite ☃xx = ☃.func_195424_a(ModelBakery.field_207764_b);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      float ☃xxx = ☃.field_70130_N * 1.4F;
      GlStateManager.func_179152_a(☃xxx, ☃xxx, ☃xxx);
      Tessellator ☃xxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxx = ☃xxxx.func_178180_c();
      float ☃xxxxxx = 0.5F;
      float ☃xxxxxxx = 0.0F;
      float ☃xxxxxxxx = ☃.field_70131_O / ☃xxx;
      float ☃xxxxxxxxx = (float)(☃.field_70163_u - ☃.func_174813_aQ().field_72338_b);
      GlStateManager.func_179114_b(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, 0.0F, -0.3F + (float)((int)☃xxxxxxxx) * 0.02F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      float ☃xxxxxxxxxx = 0.0F;
      int ☃xxxxxxxxxxx = 0;
      ☃xxxxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);

      while(☃xxxxxxxx > 0.0F) {
         TextureAtlasSprite ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx % 2 == 0 ? ☃x : ☃xx;
         this.func_110776_a(TextureMap.field_110575_b);
         float ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_94209_e();
         float ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_94206_g();
         float ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_94212_f();
         float ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_94210_h();
         if (☃xxxxxxxxxxx / 2 % 2 == 0) {
            float ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx;
            ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx;
            ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx;
         }

         ☃xxxxx.func_181662_b((double)(☃xxxxxx - 0.0F), (double)(0.0F - ☃xxxxxxxxx), (double)☃xxxxxxxxxx)
            .func_187315_a((double)☃xxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxx)
            .func_181675_d();
         ☃xxxxx.func_181662_b((double)(-☃xxxxxx - 0.0F), (double)(0.0F - ☃xxxxxxxxx), (double)☃xxxxxxxxxx)
            .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxx)
            .func_181675_d();
         ☃xxxxx.func_181662_b((double)(-☃xxxxxx - 0.0F), (double)(1.4F - ☃xxxxxxxxx), (double)☃xxxxxxxxxx)
            .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxx)
            .func_181675_d();
         ☃xxxxx.func_181662_b((double)(☃xxxxxx - 0.0F), (double)(1.4F - ☃xxxxxxxxx), (double)☃xxxxxxxxxx)
            .func_187315_a((double)☃xxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxx)
            .func_181675_d();
         ☃xxxxxxxx -= 0.45F;
         ☃xxxxxxxxx -= 0.45F;
         ☃xxxxxx *= 0.9F;
         ☃xxxxxxxxxx += 0.03F;
         ++☃xxxxxxxxxxx;
      }

      ☃xxxx.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179145_e();
   }

   private void func_76975_c(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      this.field_76990_c.field_78724_e.func_110577_a(field_110778_a);
      IWorldReaderBase ☃ = this.func_76982_b();
      GlStateManager.func_179132_a(false);
      float ☃x = this.field_76989_e;
      if (☃ instanceof EntityLiving) {
         EntityLiving ☃xx = (EntityLiving)☃;
         ☃x *= ☃xx.func_70603_bj();
         if (☃xx.func_70631_g_()) {
            ☃x *= 0.5F;
         }
      }

      double ☃ = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃x = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      int ☃xxx = MathHelper.func_76128_c(☃ - (double)☃x);
      int ☃xxxx = MathHelper.func_76128_c(☃ + (double)☃x);
      int ☃xxxxx = MathHelper.func_76128_c(☃x - (double)☃x);
      int ☃xxxxxx = MathHelper.func_76128_c(☃x);
      int ☃xxxxxxx = MathHelper.func_76128_c(☃xx - (double)☃x);
      int ☃xxxxxxxx = MathHelper.func_76128_c(☃xx + (double)☃x);
      double ☃xxxxxxxxx = ☃ - ☃;
      double ☃xxxxxxxxxx = ☃ - ☃x;
      double ☃xxxxxxxxxxx = ☃ - ☃xx;
      Tessellator ☃xxxxxxxxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_178180_c();
      ☃xxxxxxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);

      for(BlockPos ☃xxxxxxxxxxxxxx : BlockPos.func_177975_b(new BlockPos(☃xxx, ☃xxxxx, ☃xxxxxxx), new BlockPos(☃xxxx, ☃xxxxxx, ☃xxxxxxxx))) {
         IBlockState ☃xxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxxx.func_177977_b());
         if (☃xxxxxxxxxxxxxxx.func_185901_i() != EnumBlockRenderType.INVISIBLE && ☃.func_201696_r(☃xxxxxxxxxxxxxx) > 3) {
            this.func_188299_a(☃xxxxxxxxxxxxxxx, ☃, ☃, ☃, ☃xxxxxxxxxxxxxx, ☃, ☃x, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx);
         }
      }

      ☃xxxxxxxxxxxx.func_78381_a();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
   }

   private IWorldReaderBase func_76982_b() {
      return this.field_76990_c.field_78722_g;
   }

   private void func_188299_a(
      IBlockState var1, double var2, double var4, double var6, BlockPos var8, float var9, float var10, double var11, double var13, double var15
   ) {
      if (☃.func_185917_h()) {
         VoxelShape ☃ = ☃.func_196954_c(this.func_76982_b(), ☃.func_177977_b());
         if (!☃.func_197766_b()) {
            Tessellator ☃x = Tessellator.func_178181_a();
            BufferBuilder ☃xx = ☃x.func_178180_c();
            double ☃xxx = ((double)☃ - (☃ - ((double)☃.func_177956_o() + ☃)) / 2.0) * 0.5 * (double)this.func_76982_b().func_205052_D(☃);
            if (!(☃xxx < 0.0)) {
               if (☃xxx > 1.0) {
                  ☃xxx = 1.0;
               }

               AxisAlignedBB ☃xxxx = ☃.func_197752_a();
               double ☃xxxxx = (double)☃.func_177958_n() + ☃xxxx.field_72340_a + ☃;
               double ☃xxxxxx = (double)☃.func_177958_n() + ☃xxxx.field_72336_d + ☃;
               double ☃xxxxxxx = (double)☃.func_177956_o() + ☃xxxx.field_72338_b + ☃ + 0.015625;
               double ☃xxxxxxxx = (double)☃.func_177952_p() + ☃xxxx.field_72339_c + ☃;
               double ☃xxxxxxxxx = (double)☃.func_177952_p() + ☃xxxx.field_72334_f + ☃;
               float ☃xxxxxxxxxx = (float)((☃ - ☃xxxxx) / 2.0 / (double)☃ + 0.5);
               float ☃xxxxxxxxxxx = (float)((☃ - ☃xxxxxx) / 2.0 / (double)☃ + 0.5);
               float ☃xxxxxxxxxxxx = (float)((☃ - ☃xxxxxxxx) / 2.0 / (double)☃ + 0.5);
               float ☃xxxxxxxxxxxxx = (float)((☃ - ☃xxxxxxxxx) / 2.0 / (double)☃ + 0.5);
               ☃xx.func_181662_b(☃xxxxx, ☃xxxxxxx, ☃xxxxxxxx)
                  .func_187315_a((double)☃xxxxxxxxxx, (double)☃xxxxxxxxxxxx)
                  .func_181666_a(1.0F, 1.0F, 1.0F, (float)☃xxx)
                  .func_181675_d();
               ☃xx.func_181662_b(☃xxxxx, ☃xxxxxxx, ☃xxxxxxxxx)
                  .func_187315_a((double)☃xxxxxxxxxx, (double)☃xxxxxxxxxxxxx)
                  .func_181666_a(1.0F, 1.0F, 1.0F, (float)☃xxx)
                  .func_181675_d();
               ☃xx.func_181662_b(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxxx)
                  .func_187315_a((double)☃xxxxxxxxxxx, (double)☃xxxxxxxxxxxxx)
                  .func_181666_a(1.0F, 1.0F, 1.0F, (float)☃xxx)
                  .func_181675_d();
               ☃xx.func_181662_b(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx)
                  .func_187315_a((double)☃xxxxxxxxxxx, (double)☃xxxxxxxxxxxx)
                  .func_181666_a(1.0F, 1.0F, 1.0F, (float)☃xxx)
                  .func_181675_d();
            }
         }
      }
   }

   public static void func_76978_a(AxisAlignedBB var0, double var1, double var3, double var5) {
      GlStateManager.func_179090_x();
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      ☃x.func_178969_c(☃, ☃, ☃);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181708_h);
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72339_c).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72339_c).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(☃.field_72336_d, ☃.field_72338_b, ☃.field_72334_f).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
      ☃.func_78381_a();
      ☃x.func_178969_c(0.0, 0.0, 0.0);
      GlStateManager.func_179098_w();
   }

   public void func_76979_b(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      if (this.field_76990_c.field_78733_k != null) {
         if (this.field_76990_c.field_78733_k.field_181151_V && this.field_76989_e > 0.0F && !☃.func_82150_aj() && this.field_76990_c.func_178627_a()) {
            double ☃ = this.field_76990_c.func_78714_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
            float ☃x = (float)((1.0 - ☃ / 256.0) * (double)this.field_76987_f);
            if (☃x > 0.0F) {
               this.func_76975_c(☃, ☃, ☃, ☃, ☃x, ☃);
            }
         }

         if (☃.func_90999_ad() && (!(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).func_175149_v())) {
            this.func_76977_a(☃, ☃, ☃, ☃, ☃);
         }
      }
   }

   public FontRenderer func_76983_a() {
      return this.field_76990_c.func_78716_a();
   }

   protected void func_147906_a(T var1, String var2, double var3, double var5, double var7, int var9) {
      double ☃ = ☃.func_70068_e(this.field_76990_c.field_78734_h);
      if (!(☃ > (double)(☃ * ☃))) {
         boolean ☃x = ☃.func_70093_af();
         float ☃xx = this.field_76990_c.field_78735_i;
         float ☃xxx = this.field_76990_c.field_78732_j;
         boolean ☃xxxx = this.field_76990_c.field_78733_k.field_74320_O == 2;
         float ☃xxxxx = ☃.field_70131_O + 0.5F - (☃x ? 0.25F : 0.0F);
         int ☃xxxxxx = "deadmau5".equals(☃) ? -10 : 0;
         GameRenderer.func_189692_a(this.func_76983_a(), ☃, (float)☃, (float)☃ + ☃xxxxx, (float)☃, ☃xxxxxx, ☃xx, ☃xxx, ☃xxxx, ☃x);
      }
   }

   public RenderManager func_177068_d() {
      return this.field_76990_c;
   }

   public boolean func_188295_H_() {
      return false;
   }

   public void func_188300_b(T var1, double var2, double var4, double var6, float var8, float var9) {
   }
}
