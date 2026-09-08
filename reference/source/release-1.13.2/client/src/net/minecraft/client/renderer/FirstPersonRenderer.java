package net.minecraft.client.renderer;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;

public class FirstPersonRenderer {
   private static final ResourceLocation field_110931_c = new ResourceLocation("textures/map/map_background.png");
   private static final ResourceLocation field_110929_d = new ResourceLocation("textures/misc/underwater.png");
   private final Minecraft field_78455_a;
   private ItemStack field_187467_d = ItemStack.field_190927_a;
   private ItemStack field_187468_e = ItemStack.field_190927_a;
   private float field_187469_f;
   private float field_187470_g;
   private float field_187471_h;
   private float field_187472_i;
   private final RenderManager field_178111_g;
   private final ItemRenderer field_178112_h;

   public FirstPersonRenderer(Minecraft var1) {
      this.field_78455_a = ☃;
      this.field_178111_g = ☃.func_175598_ae();
      this.field_178112_h = ☃.func_175599_af();
   }

   public void func_178099_a(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3) {
      this.func_187462_a(☃, ☃, ☃, false);
   }

   public void func_187462_a(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3, boolean var4) {
      if (!☃.func_190926_b()) {
         Item ☃ = ☃.func_77973_b();
         Block ☃x = Block.func_149634_a(☃);
         GlStateManager.func_179094_E();
         boolean ☃xx = this.field_178112_h.func_175050_a(☃) && ☃x.func_180664_k() == BlockRenderLayer.TRANSLUCENT;
         if (☃xx) {
            GlStateManager.func_179132_a(false);
         }

         this.field_178112_h.func_184392_a(☃, ☃, ☃, ☃);
         if (☃xx) {
            GlStateManager.func_179132_a(true);
         }

         GlStateManager.func_179121_F();
      }
   }

   private void func_178101_a(float var1, float var2) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(☃, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(☃, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GlStateManager.func_179121_F();
   }

   private void func_187464_b() {
      AbstractClientPlayer ☃ = this.field_78455_a.field_71439_g;
      int ☃x = this.field_78455_a.field_71441_e.func_175626_b(new BlockPos(☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e(), ☃.field_70161_v), 0);
      float ☃xx = (float)(☃x & 65535);
      float ☃xxx = (float)(☃x >> 16);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, ☃xx, ☃xxx);
   }

   private void func_187458_c(float var1) {
      EntityPlayerSP ☃ = this.field_78455_a.field_71439_g;
      float ☃x = ☃.field_71164_i + (☃.field_71155_g - ☃.field_71164_i) * ☃;
      float ☃xx = ☃.field_71163_h + (☃.field_71154_f - ☃.field_71163_h) * ☃;
      GlStateManager.func_179114_b((☃.field_70125_A - ☃x) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b((☃.field_70177_z - ☃xx) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   private float func_178100_c(float var1) {
      float ☃ = 1.0F - ☃ / 45.0F + 0.1F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      return -MathHelper.func_76134_b(☃ * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void func_187466_c() {
      if (!this.field_78455_a.field_71439_g.func_82150_aj()) {
         GlStateManager.func_179129_p();
         GlStateManager.func_179094_E();
         GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
         this.func_187455_a(EnumHandSide.RIGHT);
         this.func_187455_a(EnumHandSide.LEFT);
         GlStateManager.func_179121_F();
         GlStateManager.func_179089_o();
      }
   }

   private void func_187455_a(EnumHandSide var1) {
      this.field_78455_a.func_110434_K().func_110577_a(this.field_78455_a.field_71439_g.func_110306_p());
      Render<AbstractClientPlayer> ☃ = this.field_178111_g.func_78713_a(this.field_78455_a.field_71439_g);
      RenderPlayer ☃x = (RenderPlayer)☃;
      GlStateManager.func_179094_E();
      float ☃xx = ☃ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      GlStateManager.func_179114_b(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(☃xx * -41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179109_b(☃xx * 0.3F, -1.1F, 0.45F);
      if (☃ == EnumHandSide.RIGHT) {
         ☃x.func_177138_b(this.field_78455_a.field_71439_g);
      } else {
         ☃x.func_177139_c(this.field_78455_a.field_71439_g);
      }

      GlStateManager.func_179121_F();
   }

   private void func_187465_a(float var1, EnumHandSide var2, float var3, ItemStack var4) {
      float ☃ = ☃ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      GlStateManager.func_179109_b(☃ * 0.125F, -0.125F, 0.0F);
      if (!this.field_78455_a.field_71439_g.func_82150_aj()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179114_b(☃ * 10.0F, 0.0F, 0.0F, 1.0F);
         this.func_187456_a(☃, ☃, ☃);
         GlStateManager.func_179121_F();
      }

      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(☃ * 0.51F, -0.08F + ☃ * -1.2F, -0.75F);
      float ☃ = MathHelper.func_76129_c(☃);
      float ☃x = MathHelper.func_76126_a(☃ * (float) Math.PI);
      float ☃xx = -0.5F * ☃x;
      float ☃xxx = 0.4F * MathHelper.func_76126_a(☃ * (float) (Math.PI * 2));
      float ☃xxxx = -0.3F * MathHelper.func_76126_a(☃ * (float) Math.PI);
      GlStateManager.func_179109_b(☃ * ☃xx, ☃xxx - 0.3F * ☃x, ☃xxxx);
      GlStateManager.func_179114_b(☃x * -45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(☃ * ☃x * -30.0F, 0.0F, 1.0F, 0.0F);
      this.func_187461_a(☃);
      GlStateManager.func_179121_F();
   }

   private void func_187463_a(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76129_c(☃);
      float ☃x = -0.2F * MathHelper.func_76126_a(☃ * (float) Math.PI);
      float ☃xx = -0.4F * MathHelper.func_76126_a(☃ * (float) Math.PI);
      GlStateManager.func_179109_b(0.0F, -☃x / 2.0F, ☃xx);
      float ☃xxx = this.func_178100_c(☃);
      GlStateManager.func_179109_b(0.0F, 0.04F + ☃ * -1.2F + ☃xxx * -0.5F, -0.72F);
      GlStateManager.func_179114_b(☃xxx * -85.0F, 1.0F, 0.0F, 0.0F);
      this.func_187466_c();
      float ☃xxxx = MathHelper.func_76126_a(☃ * (float) Math.PI);
      GlStateManager.func_179114_b(☃xxxx * 20.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      this.func_187461_a(this.field_187467_d);
   }

   private void func_187461_a(ItemStack var1) {
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179152_a(0.38F, 0.38F, 0.38F);
      GlStateManager.func_179140_f();
      this.field_78455_a.func_110434_K().func_110577_a(field_110931_c);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179109_b(-0.5F, -0.5F, 0.0F);
      GlStateManager.func_179152_a(0.0078125F, 0.0078125F, 0.0078125F);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(-7.0, 135.0, 0.0).func_187315_a(0.0, 1.0).func_181675_d();
      ☃x.func_181662_b(135.0, 135.0, 0.0).func_187315_a(1.0, 1.0).func_181675_d();
      ☃x.func_181662_b(135.0, -7.0, 0.0).func_187315_a(1.0, 0.0).func_181675_d();
      ☃x.func_181662_b(-7.0, -7.0, 0.0).func_187315_a(0.0, 0.0).func_181675_d();
      ☃.func_78381_a();
      MapData ☃xx = ItemMap.func_195950_a(☃, this.field_78455_a.field_71441_e);
      if (☃xx != null) {
         this.field_78455_a.field_71460_t.func_147701_i().func_148250_a(☃xx, false);
      }

      GlStateManager.func_179145_e();
   }

   private void func_187456_a(float var1, float var2, EnumHandSide var3) {
      boolean ☃ = ☃ != EnumHandSide.LEFT;
      float ☃x = ☃ ? 1.0F : -1.0F;
      float ☃xx = MathHelper.func_76129_c(☃);
      float ☃xxx = -0.3F * MathHelper.func_76126_a(☃xx * (float) Math.PI);
      float ☃xxxx = 0.4F * MathHelper.func_76126_a(☃xx * (float) (Math.PI * 2));
      float ☃xxxxx = -0.4F * MathHelper.func_76126_a(☃ * (float) Math.PI);
      GlStateManager.func_179109_b(☃x * (☃xxx + 0.64000005F), ☃xxxx + -0.6F + ☃ * -0.6F, ☃xxxxx + -0.71999997F);
      GlStateManager.func_179114_b(☃x * 45.0F, 0.0F, 1.0F, 0.0F);
      float ☃xxxxxx = MathHelper.func_76126_a(☃ * ☃ * (float) Math.PI);
      float ☃xxxxxxx = MathHelper.func_76126_a(☃xx * (float) Math.PI);
      GlStateManager.func_179114_b(☃x * ☃xxxxxxx * 70.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃x * ☃xxxxxx * -20.0F, 0.0F, 0.0F, 1.0F);
      AbstractClientPlayer ☃xxxxxxxx = this.field_78455_a.field_71439_g;
      this.field_78455_a.func_110434_K().func_110577_a(☃xxxxxxxx.func_110306_p());
      GlStateManager.func_179109_b(☃x * -1.0F, 3.6F, 3.5F);
      GlStateManager.func_179114_b(☃x * 120.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(200.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(☃x * -135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(☃x * 5.6F, 0.0F, 0.0F);
      RenderPlayer ☃xxxxxxxxx = (RenderPlayer)this.field_178111_g.<AbstractClientPlayer>func_78713_a(☃xxxxxxxx);
      GlStateManager.func_179129_p();
      if (☃) {
         ☃xxxxxxxxx.func_177138_b(☃xxxxxxxx);
      } else {
         ☃xxxxxxxxx.func_177139_c(☃xxxxxxxx);
      }

      GlStateManager.func_179089_o();
   }

   private void func_187454_a(float var1, EnumHandSide var2, ItemStack var3) {
      float ☃ = (float)this.field_78455_a.field_71439_g.func_184605_cv() - ☃ + 1.0F;
      float ☃x = ☃ / (float)☃.func_77988_m();
      if (☃x < 0.8F) {
         float ☃xx = MathHelper.func_76135_e(MathHelper.func_76134_b(☃ / 4.0F * (float) Math.PI) * 0.1F);
         GlStateManager.func_179109_b(0.0F, ☃xx, 0.0F);
      }

      float ☃ = 1.0F - (float)Math.pow((double)☃x, 27.0);
      int ☃x = ☃ == EnumHandSide.RIGHT ? 1 : -1;
      GlStateManager.func_179109_b(☃ * 0.6F * (float)☃x, ☃ * -0.5F, ☃ * 0.0F);
      GlStateManager.func_179114_b((float)☃x * ☃ * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃ * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b((float)☃x * ☃ * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   private void func_187453_a(EnumHandSide var1, float var2) {
      int ☃ = ☃ == EnumHandSide.RIGHT ? 1 : -1;
      float ☃x = MathHelper.func_76126_a(☃ * ☃ * (float) Math.PI);
      GlStateManager.func_179114_b((float)☃ * (45.0F + ☃x * -20.0F), 0.0F, 1.0F, 0.0F);
      float ☃xx = MathHelper.func_76126_a(MathHelper.func_76129_c(☃) * (float) Math.PI);
      GlStateManager.func_179114_b((float)☃ * ☃xx * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(☃xx * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b((float)☃ * -45.0F, 0.0F, 1.0F, 0.0F);
   }

   private void func_187459_b(EnumHandSide var1, float var2) {
      int ☃ = ☃ == EnumHandSide.RIGHT ? 1 : -1;
      GlStateManager.func_179109_b((float)☃ * 0.56F, -0.52F + ☃ * -0.6F, -0.72F);
   }

   public void func_78440_a(float var1) {
      AbstractClientPlayer ☃ = this.field_78455_a.field_71439_g;
      float ☃x = ☃.func_70678_g(☃);
      EnumHand ☃xx = MoreObjects.firstNonNull(☃.field_184622_au, EnumHand.MAIN_HAND);
      float ☃xxx = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      float ☃xxxx = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃;
      boolean ☃xxxxx = true;
      boolean ☃xxxxxx = true;
      if (☃.func_184587_cr()) {
         ItemStack ☃xxxxxxx = ☃.func_184607_cu();
         if (☃xxxxxxx.func_77973_b() == Items.field_151031_f) {
            ☃xxxxx = ☃.func_184600_cs() == EnumHand.MAIN_HAND;
            ☃xxxxxx = !☃xxxxx;
         }
      }

      this.func_178101_a(☃xxx, ☃xxxx);
      this.func_187464_b();
      this.func_187458_c(☃);
      GlStateManager.func_179091_B();
      if (☃xxxxx) {
         float ☃ = ☃xx == EnumHand.MAIN_HAND ? ☃x : 0.0F;
         float ☃x = 1.0F - (this.field_187470_g + (this.field_187469_f - this.field_187470_g) * ☃);
         this.func_187457_a(☃, ☃, ☃xxx, EnumHand.MAIN_HAND, ☃, this.field_187467_d, ☃x);
      }

      if (☃xxxxxx) {
         float ☃ = ☃xx == EnumHand.OFF_HAND ? ☃x : 0.0F;
         float ☃x = 1.0F - (this.field_187472_i + (this.field_187471_h - this.field_187472_i) * ☃);
         this.func_187457_a(☃, ☃, ☃xxx, EnumHand.OFF_HAND, ☃, this.field_187468_e, ☃x);
      }

      GlStateManager.func_179101_C();
      RenderHelper.func_74518_a();
   }

   public void func_187457_a(AbstractClientPlayer var1, float var2, float var3, EnumHand var4, float var5, ItemStack var6, float var7) {
      boolean ☃ = ☃ == EnumHand.MAIN_HAND;
      EnumHandSide ☃x = ☃ ? ☃.func_184591_cq() : ☃.func_184591_cq().func_188468_a();
      GlStateManager.func_179094_E();
      if (☃.func_190926_b()) {
         if (☃ && !☃.func_82150_aj()) {
            this.func_187456_a(☃, ☃, ☃x);
         }
      } else if (☃.func_77973_b() == Items.field_151098_aY) {
         if (☃ && this.field_187468_e.func_190926_b()) {
            this.func_187463_a(☃, ☃, ☃);
         } else {
            this.func_187465_a(☃, ☃x, ☃, ☃);
         }
      } else {
         boolean ☃ = ☃x == EnumHandSide.RIGHT;
         if (☃.func_184587_cr() && ☃.func_184605_cv() > 0 && ☃.func_184600_cs() == ☃) {
            int ☃x = ☃ ? 1 : -1;
            switch(☃.func_77975_n()) {
               case NONE:
                  this.func_187459_b(☃x, ☃);
                  break;
               case EAT:
               case DRINK:
                  this.func_187454_a(☃, ☃x, ☃);
                  this.func_187459_b(☃x, ☃);
                  break;
               case BLOCK:
                  this.func_187459_b(☃x, ☃);
                  break;
               case BOW:
                  this.func_187459_b(☃x, ☃);
                  GlStateManager.func_179109_b((float)☃x * -0.2785682F, 0.18344387F, 0.15731531F);
                  GlStateManager.func_179114_b(-13.935F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.func_179114_b((float)☃x * 35.3F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.func_179114_b((float)☃x * -9.785F, 0.0F, 0.0F, 1.0F);
                  float ☃xx = (float)☃.func_77988_m() - ((float)this.field_78455_a.field_71439_g.func_184605_cv() - ☃ + 1.0F);
                  float ☃xxx = ☃xx / 20.0F;
                  ☃xxx = (☃xxx * ☃xxx + ☃xxx * 2.0F) / 3.0F;
                  if (☃xxx > 1.0F) {
                     ☃xxx = 1.0F;
                  }

                  if (☃xxx > 0.1F) {
                     float ☃xx = MathHelper.func_76126_a((☃xx - 0.1F) * 1.3F);
                     float ☃xxx = ☃xxx - 0.1F;
                     float ☃xxxx = ☃xx * ☃xxx;
                     GlStateManager.func_179109_b(☃xxxx * 0.0F, ☃xxxx * 0.004F, ☃xxxx * 0.0F);
                  }

                  GlStateManager.func_179109_b(☃xxx * 0.0F, ☃xxx * 0.0F, ☃xxx * 0.04F);
                  GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F + ☃xxx * 0.2F);
                  GlStateManager.func_179114_b((float)☃x * 45.0F, 0.0F, -1.0F, 0.0F);
                  break;
               case SPEAR:
                  this.func_187459_b(☃x, ☃);
                  GlStateManager.func_179109_b((float)☃x * -0.5F, 0.7F, 0.1F);
                  GlStateManager.func_179114_b(-55.0F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.func_179114_b((float)☃x * 35.3F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.func_179114_b((float)☃x * -9.785F, 0.0F, 0.0F, 1.0F);
                  float ☃xx = (float)☃.func_77988_m() - ((float)this.field_78455_a.field_71439_g.func_184605_cv() - ☃ + 1.0F);
                  float ☃xxx = ☃xx / 10.0F;
                  if (☃xxx > 1.0F) {
                     ☃xxx = 1.0F;
                  }

                  if (☃xxx > 0.1F) {
                     float ☃xx = MathHelper.func_76126_a((☃xx - 0.1F) * 1.3F);
                     float ☃xxx = ☃xxx - 0.1F;
                     float ☃xxxx = ☃xx * ☃xxx;
                     GlStateManager.func_179109_b(☃xxxx * 0.0F, ☃xxxx * 0.004F, ☃xxxx * 0.0F);
                  }

                  GlStateManager.func_179109_b(0.0F, 0.0F, ☃xxx * 0.2F);
                  GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F + ☃xxx * 0.2F);
                  GlStateManager.func_179114_b((float)☃x * 45.0F, 0.0F, -1.0F, 0.0F);
            }
         } else if (☃.func_204805_cN()) {
            this.func_187459_b(☃x, ☃);
            int ☃ = ☃ ? 1 : -1;
            GlStateManager.func_179109_b((float)☃ * -0.4F, 0.8F, 0.3F);
            GlStateManager.func_179114_b((float)☃ * 65.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b((float)☃ * -85.0F, 0.0F, 0.0F, 1.0F);
         } else {
            float ☃ = -0.4F * MathHelper.func_76126_a(MathHelper.func_76129_c(☃) * (float) Math.PI);
            float ☃x = 0.2F * MathHelper.func_76126_a(MathHelper.func_76129_c(☃) * (float) (Math.PI * 2));
            float ☃xx = -0.2F * MathHelper.func_76126_a(☃ * (float) Math.PI);
            int ☃xxx = ☃ ? 1 : -1;
            GlStateManager.func_179109_b((float)☃xxx * ☃, ☃x, ☃xx);
            this.func_187459_b(☃x, ☃);
            this.func_187453_a(☃x, ☃);
         }

         this.func_187462_a(
            ☃, ☃, ☃ ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !☃
         );
      }

      GlStateManager.func_179121_F();
   }

   public void func_78447_b(float var1) {
      GlStateManager.func_179118_c();
      if (this.field_78455_a.field_71439_g.func_70094_T()) {
         IBlockState ☃ = this.field_78455_a.field_71441_e.func_180495_p(new BlockPos(this.field_78455_a.field_71439_g));
         EntityPlayer ☃x = this.field_78455_a.field_71439_g;

         for(int ☃xx = 0; ☃xx < 8; ++☃xx) {
            double ☃xxx = ☃x.field_70165_t + (double)(((float)((☃xx >> 0) % 2) - 0.5F) * ☃x.field_70130_N * 0.8F);
            double ☃xxxx = ☃x.field_70163_u + (double)(((float)((☃xx >> 1) % 2) - 0.5F) * 0.1F);
            double ☃xxxxx = ☃x.field_70161_v + (double)(((float)((☃xx >> 2) % 2) - 0.5F) * ☃x.field_70130_N * 0.8F);
            BlockPos ☃xxxxxx = new BlockPos(☃xxx, ☃xxxx + (double)☃x.func_70047_e(), ☃xxxxx);
            IBlockState ☃xxxxxxx = this.field_78455_a.field_71441_e.func_180495_p(☃xxxxxx);
            if (☃xxxxxxx.func_191058_s()) {
               ☃ = ☃xxxxxxx;
            }
         }

         if (☃.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
            this.func_178108_a(this.field_78455_a.func_175602_ab().func_175023_a().func_178122_a(☃));
         }
      }

      if (!this.field_78455_a.field_71439_g.func_175149_v()) {
         if (this.field_78455_a.field_71439_g.func_208600_a(FluidTags.field_206959_a)) {
            this.func_78448_c(☃);
         }

         if (this.field_78455_a.field_71439_g.func_70027_ad()) {
            this.func_78442_d();
         }
      }

      GlStateManager.func_179141_d();
   }

   private void func_178108_a(TextureAtlasSprite var1) {
      this.field_78455_a.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      float ☃xx = 0.1F;
      GlStateManager.func_179131_c(0.1F, 0.1F, 0.1F, 0.5F);
      GlStateManager.func_179094_E();
      float ☃xxx = -1.0F;
      float ☃xxxx = 1.0F;
      float ☃xxxxx = -1.0F;
      float ☃xxxxxx = 1.0F;
      float ☃xxxxxxx = -0.5F;
      float ☃xxxxxxxx = ☃.func_94209_e();
      float ☃xxxxxxxxx = ☃.func_94212_f();
      float ☃xxxxxxxxxx = ☃.func_94206_g();
      float ☃xxxxxxxxxxx = ☃.func_94210_h();
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(-1.0, -1.0, -0.5).func_187315_a((double)☃xxxxxxxxx, (double)☃xxxxxxxxxxx).func_181675_d();
      ☃x.func_181662_b(1.0, -1.0, -0.5).func_187315_a((double)☃xxxxxxxx, (double)☃xxxxxxxxxxx).func_181675_d();
      ☃x.func_181662_b(1.0, 1.0, -0.5).func_187315_a((double)☃xxxxxxxx, (double)☃xxxxxxxxxx).func_181675_d();
      ☃x.func_181662_b(-1.0, 1.0, -0.5).func_187315_a((double)☃xxxxxxxxx, (double)☃xxxxxxxxxx).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_78448_c(float var1) {
      this.field_78455_a.func_110434_K().func_110577_a(field_110929_d);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      float ☃xx = this.field_78455_a.field_71439_g.func_70013_c();
      GlStateManager.func_179131_c(☃xx, ☃xx, ☃xx, 0.1F);
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179094_E();
      float ☃xxx = 4.0F;
      float ☃xxxx = -1.0F;
      float ☃xxxxx = 1.0F;
      float ☃xxxxxx = -1.0F;
      float ☃xxxxxxx = 1.0F;
      float ☃xxxxxxxx = -0.5F;
      float ☃xxxxxxxxx = -this.field_78455_a.field_71439_g.field_70177_z / 64.0F;
      float ☃xxxxxxxxxx = this.field_78455_a.field_71439_g.field_70125_A / 64.0F;
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(-1.0, -1.0, -0.5).func_187315_a((double)(4.0F + ☃xxxxxxxxx), (double)(4.0F + ☃xxxxxxxxxx)).func_181675_d();
      ☃x.func_181662_b(1.0, -1.0, -0.5).func_187315_a((double)(0.0F + ☃xxxxxxxxx), (double)(4.0F + ☃xxxxxxxxxx)).func_181675_d();
      ☃x.func_181662_b(1.0, 1.0, -0.5).func_187315_a((double)(0.0F + ☃xxxxxxxxx), (double)(0.0F + ☃xxxxxxxxxx)).func_181675_d();
      ☃x.func_181662_b(-1.0, 1.0, -0.5).func_187315_a((double)(4.0F + ☃xxxxxxxxx), (double)(0.0F + ☃xxxxxxxxxx)).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179084_k();
   }

   private void func_78442_d() {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.9F);
      GlStateManager.func_179143_c(519);
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      float ☃xx = 1.0F;

      for(int ☃xxx = 0; ☃xxx < 2; ++☃xxx) {
         GlStateManager.func_179094_E();
         TextureAtlasSprite ☃xxxx = this.field_78455_a.func_147117_R().func_195424_a(ModelBakery.field_207764_b);
         this.field_78455_a.func_110434_K().func_110577_a(TextureMap.field_110575_b);
         float ☃xxxxx = ☃xxxx.func_94209_e();
         float ☃xxxxxx = ☃xxxx.func_94212_f();
         float ☃xxxxxxx = ☃xxxx.func_94206_g();
         float ☃xxxxxxxx = ☃xxxx.func_94210_h();
         float ☃xxxxxxxxx = -0.5F;
         float ☃xxxxxxxxxx = 0.5F;
         float ☃xxxxxxxxxxx = -0.5F;
         float ☃xxxxxxxxxxxx = 0.5F;
         float ☃xxxxxxxxxxxxx = -0.5F;
         GlStateManager.func_179109_b((float)(-(☃xxx * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         GlStateManager.func_179114_b((float)(☃xxx * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃x.func_181662_b(-0.5, -0.5, -0.5).func_187315_a((double)☃xxxxxx, (double)☃xxxxxxxx).func_181675_d();
         ☃x.func_181662_b(0.5, -0.5, -0.5).func_187315_a((double)☃xxxxx, (double)☃xxxxxxxx).func_181675_d();
         ☃x.func_181662_b(0.5, 0.5, -0.5).func_187315_a((double)☃xxxxx, (double)☃xxxxxxx).func_181675_d();
         ☃x.func_181662_b(-0.5, 0.5, -0.5).func_187315_a((double)☃xxxxxx, (double)☃xxxxxxx).func_181675_d();
         ☃.func_78381_a();
         GlStateManager.func_179121_F();
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179143_c(515);
   }

   public void func_78441_a() {
      this.field_187470_g = this.field_187469_f;
      this.field_187472_i = this.field_187471_h;
      EntityPlayerSP ☃ = this.field_78455_a.field_71439_g;
      ItemStack ☃x = ☃.func_184614_ca();
      ItemStack ☃xx = ☃.func_184592_cb();
      if (☃.func_184838_M()) {
         this.field_187469_f = MathHelper.func_76131_a(this.field_187469_f - 0.4F, 0.0F, 1.0F);
         this.field_187471_h = MathHelper.func_76131_a(this.field_187471_h - 0.4F, 0.0F, 1.0F);
      } else {
         float ☃ = ☃.func_184825_o(1.0F);
         this.field_187469_f += MathHelper.func_76131_a((Objects.equals(this.field_187467_d, ☃x) ? ☃ * ☃ * ☃ : 0.0F) - this.field_187469_f, -0.4F, 0.4F);
         this.field_187471_h += MathHelper.func_76131_a((float)(Objects.equals(this.field_187468_e, ☃xx) ? 1 : 0) - this.field_187471_h, -0.4F, 0.4F);
      }

      if (this.field_187469_f < 0.1F) {
         this.field_187467_d = ☃x;
      }

      if (this.field_187471_h < 0.1F) {
         this.field_187468_e = ☃xx;
      }
   }

   public void func_187460_a(EnumHand var1) {
      if (☃ == EnumHand.MAIN_HAND) {
         this.field_187469_f = 0.0F;
      } else {
         this.field_187471_h = 0.0F;
      }
   }
}
