package net.minecraft.client.renderer;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class ItemRenderer implements IResourceManagerReloadListener {
   public static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private static final Set<Item> field_195411_c = Sets.<Item>newHashSet(Items.field_190931_a);
   public float field_77023_b;
   private final ItemModelMesher field_175059_m;
   private final TextureManager field_175057_n;
   private final ItemColors field_184395_f;

   public ItemRenderer(TextureManager var1, ModelManager var2, ItemColors var3) {
      this.field_175057_n = ☃;
      this.field_175059_m = new ItemModelMesher(☃);

      for(Item ☃ : IRegistry.field_212630_s) {
         if (!field_195411_c.contains(☃)) {
            this.field_175059_m.func_199311_a(☃, new ModelResourceLocation(IRegistry.field_212630_s.func_177774_c(☃), "inventory"));
         }
      }

      this.field_184395_f = ☃;
   }

   public ItemModelMesher func_175037_a() {
      return this.field_175059_m;
   }

   private void func_191961_a(IBakedModel var1, ItemStack var2) {
      this.func_191967_a(☃, -1, ☃);
   }

   private void func_191965_a(IBakedModel var1, int var2) {
      this.func_191967_a(☃, ☃, ItemStack.field_190927_a);
   }

   private void func_191967_a(IBakedModel var1, int var2, ItemStack var3) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(7, DefaultVertexFormats.field_176599_b);
      Random ☃xx = new Random();
      long ☃xxx = 42L;

      for(EnumFacing ☃xxxx : EnumFacing.values()) {
         ☃xx.setSeed(42L);
         this.func_191970_a(☃x, ☃.func_200117_a(null, ☃xxxx, ☃xx), ☃, ☃);
      }

      ☃xx.setSeed(42L);
      this.func_191970_a(☃x, ☃.func_200117_a(null, null, ☃xx), ☃, ☃);
      ☃.func_78381_a();
   }

   public void func_180454_a(ItemStack var1, IBakedModel var2) {
      if (!☃.func_190926_b()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
         if (☃.func_188618_c()) {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179091_B();
            TileEntityItemStackRenderer.field_147719_a.func_179022_a(☃);
         } else {
            this.func_191961_a(☃, ☃);
            if (☃.func_77962_s()) {
               func_211128_a(this.field_175057_n, () -> this.func_191965_a(☃, -8372020), 8);
            }
         }

         GlStateManager.func_179121_F();
      }
   }

   public static void func_211128_a(TextureManager var0, Runnable var1, int var2) {
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179143_c(514);
      GlStateManager.func_179140_f();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
      ☃.func_110577_a(field_110798_h);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a((float)☃, (float)☃, (float)☃);
      float ☃ = (float)(Util.func_211177_b() % 3000L) / 3000.0F / (float)☃;
      GlStateManager.func_179109_b(☃, 0.0F, 0.0F);
      GlStateManager.func_179114_b(-50.0F, 0.0F, 0.0F, 1.0F);
      ☃.run();
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a((float)☃, (float)☃, (float)☃);
      float ☃x = (float)(Util.func_211177_b() % 4873L) / 4873.0F / (float)☃;
      GlStateManager.func_179109_b(-☃x, 0.0F, 0.0F);
      GlStateManager.func_179114_b(10.0F, 0.0F, 0.0F, 1.0F);
      ☃.run();
      GlStateManager.func_179121_F();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179145_e();
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179132_a(true);
      ☃.func_110577_a(TextureMap.field_110575_b);
   }

   private void func_175038_a(BufferBuilder var1, BakedQuad var2) {
      Vec3i ☃ = ☃.func_178210_d().func_176730_m();
      ☃.func_178975_e((float)☃.func_177958_n(), (float)☃.func_177956_o(), (float)☃.func_177952_p());
   }

   private void func_191969_a(BufferBuilder var1, BakedQuad var2, int var3) {
      ☃.func_178981_a(☃.func_178209_a());
      ☃.func_178968_d(☃);
      this.func_175038_a(☃, ☃);
   }

   private void func_191970_a(BufferBuilder var1, List<BakedQuad> var2, int var3, ItemStack var4) {
      boolean ☃ = ☃ == -1 && !☃.func_190926_b();
      int ☃x = 0;

      for(int ☃xx = ☃.size(); ☃x < ☃xx; ++☃x) {
         BakedQuad ☃xxx = (BakedQuad)☃.get(☃x);
         int ☃xxxx = ☃;
         if (☃ && ☃xxx.func_178212_b()) {
            ☃xxxx = this.field_184395_f.func_186728_a(☃, ☃xxx.func_178211_c());
            ☃xxxx |= -16777216;
         }

         this.func_191969_a(☃, ☃xxx, ☃xxxx);
      }
   }

   public boolean func_175050_a(ItemStack var1) {
      IBakedModel ☃ = this.field_175059_m.func_178089_a(☃);
      return ☃ == null ? false : ☃.func_177556_c();
   }

   public void func_181564_a(ItemStack var1, ItemCameraTransforms.TransformType var2) {
      if (!☃.func_190926_b()) {
         IBakedModel ☃ = this.func_204206_b(☃);
         this.func_184394_a(☃, ☃, ☃, false);
      }
   }

   public IBakedModel func_184393_a(ItemStack var1, @Nullable World var2, @Nullable EntityLivingBase var3) {
      IBakedModel ☃ = this.field_175059_m.func_178089_a(☃);
      Item ☃x = ☃.func_77973_b();
      return !☃x.func_185040_i() ? ☃ : this.func_204207_a(☃, ☃, ☃, ☃);
   }

   public IBakedModel func_204205_b(ItemStack var1, World var2, EntityLivingBase var3) {
      Item ☃x = ☃.func_77973_b();
      IBakedModel ☃;
      if (☃x == Items.field_203184_eO) {
         ☃ = this.field_175059_m.func_178083_a().func_174953_a(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      } else {
         ☃ = this.field_175059_m.func_178089_a(☃);
      }

      return !☃x.func_185040_i() ? ☃ : this.func_204207_a(☃, ☃, ☃, ☃);
   }

   public IBakedModel func_204206_b(ItemStack var1) {
      return this.func_184393_a(☃, null, null);
   }

   private IBakedModel func_204207_a(IBakedModel var1, ItemStack var2, @Nullable World var3, @Nullable EntityLivingBase var4) {
      IBakedModel ☃ = ☃.func_188617_f().func_209581_a(☃, ☃, ☃, ☃);
      return ☃ == null ? this.field_175059_m.func_178083_a().func_174951_a() : ☃;
   }

   public void func_184392_a(ItemStack var1, EntityLivingBase var2, ItemCameraTransforms.TransformType var3, boolean var4) {
      if (!☃.func_190926_b() && ☃ != null) {
         IBakedModel ☃ = this.func_204205_b(☃, ☃.field_70170_p, ☃);
         this.func_184394_a(☃, ☃, ☃, ☃);
      }
   }

   protected void func_184394_a(ItemStack var1, IBakedModel var2, ItemCameraTransforms.TransformType var3, boolean var4) {
      if (!☃.func_190926_b()) {
         this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
         this.field_175057_n.func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179091_B();
         GlStateManager.func_179092_a(516, 0.1F);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179094_E();
         ItemCameraTransforms ☃ = ☃.func_177552_f();
         ItemCameraTransforms.func_188034_a(☃.func_181688_b(☃), ☃);
         if (this.func_183005_a(☃.func_181688_b(☃))) {
            GlStateManager.func_187407_a(GlStateManager.CullFace.FRONT);
         }

         this.func_180454_a(☃, ☃);
         GlStateManager.func_187407_a(GlStateManager.CullFace.BACK);
         GlStateManager.func_179121_F();
         GlStateManager.func_179101_C();
         GlStateManager.func_179084_k();
         this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
         this.field_175057_n.func_110581_b(TextureMap.field_110575_b).func_174935_a();
      }
   }

   private boolean func_183005_a(ItemTransformVec3f var1) {
      return ☃.field_178363_d.func_195899_a() < 0.0F ^ ☃.field_178363_d.func_195900_b() < 0.0F ^ ☃.field_178363_d.func_195902_c() < 0.0F;
   }

   public void func_175042_a(ItemStack var1, int var2, int var3) {
      this.func_191962_a(☃, ☃, ☃, this.func_204206_b(☃));
   }

   protected void func_191962_a(ItemStack var1, int var2, int var3, IBakedModel var4) {
      GlStateManager.func_179094_E();
      this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
      this.field_175057_n.func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
      GlStateManager.func_179091_B();
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_180452_a(☃, ☃, ☃.func_177556_c());
      ☃.func_177552_f().func_181689_a(ItemCameraTransforms.TransformType.GUI);
      this.func_180454_a(☃, ☃);
      GlStateManager.func_179118_c();
      GlStateManager.func_179101_C();
      GlStateManager.func_179140_f();
      GlStateManager.func_179121_F();
      this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
      this.field_175057_n.func_110581_b(TextureMap.field_110575_b).func_174935_a();
   }

   private void func_180452_a(int var1, int var2, boolean var3) {
      GlStateManager.func_179109_b((float)☃, (float)☃, 100.0F + this.field_77023_b);
      GlStateManager.func_179109_b(8.0F, 8.0F, 0.0F);
      GlStateManager.func_179152_a(1.0F, -1.0F, 1.0F);
      GlStateManager.func_179152_a(16.0F, 16.0F, 16.0F);
      if (☃) {
         GlStateManager.func_179145_e();
      } else {
         GlStateManager.func_179140_f();
      }
   }

   public void func_180450_b(ItemStack var1, int var2, int var3) {
      this.func_184391_a(Minecraft.func_71410_x().field_71439_g, ☃, ☃, ☃);
   }

   public void func_184391_a(@Nullable EntityLivingBase var1, ItemStack var2, int var3, int var4) {
      if (!☃.func_190926_b()) {
         this.field_77023_b += 50.0F;

         try {
            this.func_191962_a(☃, ☃, ☃, this.func_184393_a(☃, null, ☃));
         } catch (Throwable var8) {
            CrashReport ☃ = CrashReport.func_85055_a(var8, "Rendering item");
            CrashReportCategory ☃x = ☃.func_85058_a("Item being rendered");
            ☃x.func_189529_a("Item Type", () -> String.valueOf(☃.func_77973_b()));
            ☃x.func_189529_a("Item Damage", () -> String.valueOf(☃.func_77952_i()));
            ☃x.func_189529_a("Item NBT", () -> String.valueOf(☃.func_77978_p()));
            ☃x.func_189529_a("Item Foil", () -> String.valueOf(☃.func_77962_s()));
            throw new ReportedException(☃);
         }

         this.field_77023_b -= 50.0F;
      }
   }

   public void func_175030_a(FontRenderer var1, ItemStack var2, int var3, int var4) {
      this.func_180453_a(☃, ☃, ☃, ☃, null);
   }

   public void func_180453_a(FontRenderer var1, ItemStack var2, int var3, int var4, @Nullable String var5) {
      if (!☃.func_190926_b()) {
         if (☃.func_190916_E() != 1 || ☃ != null) {
            String ☃ = ☃ == null ? String.valueOf(☃.func_190916_E()) : ☃;
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179084_k();
            ☃.func_175063_a(☃, (float)(☃ + 19 - 2 - ☃.func_78256_a(☃)), (float)(☃ + 6 + 3), 16777215);
            GlStateManager.func_179147_l();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }

         if (☃.func_77951_h()) {
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            GlStateManager.func_179118_c();
            GlStateManager.func_179084_k();
            Tessellator ☃ = Tessellator.func_178181_a();
            BufferBuilder ☃x = ☃.func_178180_c();
            float ☃xx = (float)☃.func_77952_i();
            float ☃xxx = (float)☃.func_77958_k();
            float ☃xxxx = Math.max(0.0F, (☃xxx - ☃xx) / ☃xxx);
            int ☃xxxxx = Math.round(13.0F - ☃xx * 13.0F / ☃xxx);
            int ☃xxxxxx = MathHelper.func_181758_c(☃xxxx / 3.0F, 1.0F, 1.0F);
            this.func_181565_a(☃x, ☃ + 2, ☃ + 13, 13, 2, 0, 0, 0, 255);
            this.func_181565_a(☃x, ☃ + 2, ☃ + 13, ☃xxxxx, 1, ☃xxxxxx >> 16 & 0xFF, ☃xxxxxx >> 8 & 0xFF, ☃xxxxxx & 0xFF, 255);
            GlStateManager.func_179147_l();
            GlStateManager.func_179141_d();
            GlStateManager.func_179098_w();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }

         EntityPlayerSP ☃ = Minecraft.func_71410_x().field_71439_g;
         float ☃x = ☃ == null ? 0.0F : ☃.func_184811_cZ().func_185143_a(☃.func_77973_b(), Minecraft.func_71410_x().func_184121_ak());
         if (☃x > 0.0F) {
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            Tessellator ☃xx = Tessellator.func_178181_a();
            BufferBuilder ☃xxx = ☃xx.func_178180_c();
            this.func_181565_a(☃xxx, ☃, ☃ + MathHelper.func_76141_d(16.0F * (1.0F - ☃x)), 16, MathHelper.func_76123_f(16.0F * ☃x), 255, 255, 255, 127);
            GlStateManager.func_179098_w();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }
      }
   }

   private void func_181565_a(BufferBuilder var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      ☃.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      ☃.func_181662_b((double)(☃ + 0), (double)(☃ + 0), 0.0).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b((double)(☃ + 0), (double)(☃ + ☃), 0.0).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b((double)(☃ + ☃), (double)(☃ + ☃), 0.0).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b((double)(☃ + ☃), (double)(☃ + 0), 0.0).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      Tessellator.func_178181_a().func_78381_a();
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_175059_m.func_178085_b();
   }
}
