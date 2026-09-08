package net.minecraft.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerSpinAttackEffect;
import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.client.renderer.entity.model.ModelPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderPlayer extends RenderLivingBase<AbstractClientPlayer> {
   private float field_205127_a;

   public RenderPlayer(RenderManager var1) {
      this(☃, false);
   }

   public RenderPlayer(RenderManager var1, boolean var2) {
      super(☃, new ModelPlayer(0.0F, ☃), 0.5F);
      this.func_177094_a(new LayerBipedArmor(this));
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerArrow(this));
      this.func_177094_a(new LayerDeadmau5Head(this));
      this.func_177094_a(new LayerCape(this));
      this.func_177094_a(new LayerCustomHead(this.func_177087_b().field_78116_c));
      this.func_177094_a(new LayerElytra(this));
      this.func_177094_a(new LayerEntityOnShoulder(☃));
      this.func_177094_a(new LayerSpinAttackEffect(this));
   }

   public ModelPlayer func_177087_b() {
      return (ModelPlayer)super.func_177087_b();
   }

   public void func_76986_a(AbstractClientPlayer var1, double var2, double var4, double var6, float var8, float var9) {
      if (!☃.func_175144_cb() || this.field_76990_c.field_78734_h == ☃) {
         double ☃ = ☃;
         if (☃.func_70093_af()) {
            ☃ = ☃ - 0.125;
         }

         this.func_177137_d(☃);
         GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
         GlStateManager.func_187440_b(GlStateManager.Profile.PLAYER_SKIN);
      }
   }

   private void func_177137_d(AbstractClientPlayer var1) {
      ModelPlayer ☃ = this.func_177087_b();
      if (☃.func_175149_v()) {
         ☃.func_178719_a(false);
         ☃.field_78116_c.field_78806_j = true;
         ☃.field_178720_f.field_78806_j = true;
      } else {
         ItemStack ☃ = ☃.func_184614_ca();
         ItemStack ☃x = ☃.func_184592_cb();
         ☃.func_178719_a(true);
         ☃.field_178720_f.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.HAT);
         ☃.field_178730_v.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.JACKET);
         ☃.field_178733_c.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.LEFT_PANTS_LEG);
         ☃.field_178731_d.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.RIGHT_PANTS_LEG);
         ☃.field_178734_a.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.LEFT_SLEEVE);
         ☃.field_178732_b.field_78806_j = ☃.func_175148_a(EnumPlayerModelParts.RIGHT_SLEEVE);
         ☃.field_78117_n = ☃.func_70093_af();
         ModelBiped.ArmPose ☃xx = this.func_212499_a(☃, ☃);
         ModelBiped.ArmPose ☃xxx = this.func_212499_a(☃, ☃x);
         if (☃.func_184591_cq() == EnumHandSide.RIGHT) {
            ☃.field_187076_m = ☃xx;
            ☃.field_187075_l = ☃xxx;
         } else {
            ☃.field_187076_m = ☃xxx;
            ☃.field_187075_l = ☃xx;
         }
      }
   }

   public ResourceLocation func_110775_a(AbstractClientPlayer var1) {
      return ☃.func_110306_p();
   }

   protected void func_77041_b(AbstractClientPlayer var1, float var2) {
      float ☃ = 0.9375F;
      GlStateManager.func_179152_a(0.9375F, 0.9375F, 0.9375F);
   }

   protected void func_188296_a(AbstractClientPlayer var1, double var2, double var4, double var6, String var8, double var9) {
      if (☃ < 100.0) {
         Scoreboard ☃ = ☃.func_96123_co();
         ScoreObjective ☃x = ☃.func_96539_a(2);
         if (☃x != null) {
            Score ☃xx = ☃.func_96529_a(☃.func_195047_I_(), ☃x);
            this.func_147906_a(☃, ☃xx.func_96652_c() + " " + ☃x.func_96678_d().func_150254_d(), ☃, ☃, ☃, 64);
            ☃ += (double)((float)this.func_76983_a().field_78288_b * 1.15F * 0.025F);
         }
      }

      super.func_188296_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_177138_b(AbstractClientPlayer var1) {
      float ☃ = 1.0F;
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      float ☃x = 0.0625F;
      ModelPlayer ☃xx = this.func_177087_b();
      this.func_177137_d(☃);
      GlStateManager.func_179147_l();
      ☃xx.field_78095_p = 0.0F;
      ☃xx.field_78117_n = false;
      ☃xx.field_205061_a = 0.0F;
      ☃xx.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, ☃);
      ☃xx.field_178723_h.field_78795_f = 0.0F;
      ☃xx.field_178723_h.func_78785_a(0.0625F);
      ☃xx.field_178732_b.field_78795_f = 0.0F;
      ☃xx.field_178732_b.func_78785_a(0.0625F);
      GlStateManager.func_179084_k();
   }

   public void func_177139_c(AbstractClientPlayer var1) {
      float ☃ = 1.0F;
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      float ☃x = 0.0625F;
      ModelPlayer ☃xx = this.func_177087_b();
      this.func_177137_d(☃);
      GlStateManager.func_179147_l();
      ☃xx.field_78117_n = false;
      ☃xx.field_78095_p = 0.0F;
      ☃xx.field_205061_a = 0.0F;
      ☃xx.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, ☃);
      ☃xx.field_178724_i.field_78795_f = 0.0F;
      ☃xx.field_178724_i.func_78785_a(0.0625F);
      ☃xx.field_178734_a.field_78795_f = 0.0F;
      ☃xx.field_178734_a.func_78785_a(0.0625F);
      GlStateManager.func_179084_k();
   }

   protected void func_77039_a(AbstractClientPlayer var1, double var2, double var4, double var6) {
      if (☃.func_70089_S() && ☃.func_70608_bn()) {
         super.func_77039_a(☃, ☃ + (double)☃.field_71079_bU, ☃ + (double)☃.field_71082_cx, ☃ + (double)☃.field_71089_bV);
      } else {
         super.func_77039_a(☃, ☃, ☃, ☃);
      }
   }

   protected void func_77043_a(AbstractClientPlayer var1, float var2, float var3, float var4) {
      float ☃ = ☃.func_205015_b(☃);
      if (☃.func_70089_S() && ☃.func_70608_bn()) {
         GlStateManager.func_179114_b(☃.func_71051_bG(), 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(this.func_77037_a(☃), 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(270.0F, 0.0F, 1.0F, 0.0F);
      } else if (☃.func_184613_cA()) {
         super.func_77043_a(☃, ☃, ☃, ☃);
         float ☃ = (float)☃.func_184599_cB() + ☃;
         float ☃x = MathHelper.func_76131_a(☃ * ☃ / 100.0F, 0.0F, 1.0F);
         if (!☃.func_204805_cN()) {
            GlStateManager.func_179114_b(☃x * (-90.0F - ☃.field_70125_A), 1.0F, 0.0F, 0.0F);
         }

         Vec3d ☃ = ☃.func_70676_i(☃);
         double ☃x = ☃.field_70159_w * ☃.field_70159_w + ☃.field_70179_y * ☃.field_70179_y;
         double ☃xx = ☃.field_72450_a * ☃.field_72450_a + ☃.field_72449_c * ☃.field_72449_c;
         if (☃x > 0.0 && ☃xx > 0.0) {
            double ☃xxx = (☃.field_70159_w * ☃.field_72450_a + ☃.field_70179_y * ☃.field_72449_c) / (Math.sqrt(☃x) * Math.sqrt(☃xx));
            double ☃xxxx = ☃.field_70159_w * ☃.field_72449_c - ☃.field_70179_y * ☃.field_72450_a;
            GlStateManager.func_179114_b((float)(Math.signum(☃xxxx) * Math.acos(☃xxx)) * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
         }
      } else if (☃ > 0.0F) {
         super.func_77043_a(☃, ☃, ☃, ☃);
         float ☃ = this.func_205126_b(☃.field_70125_A, -90.0F - ☃.field_70125_A, ☃);
         if (!☃.func_203007_ba()) {
            ☃ = this.func_77034_a(this.field_205127_a, 0.0F, 1.0F - ☃);
         }

         GlStateManager.func_179114_b(☃, 1.0F, 0.0F, 0.0F);
         if (☃.func_203007_ba()) {
            this.field_205127_a = ☃;
            GlStateManager.func_179109_b(0.0F, -1.0F, 0.3F);
         }
      } else {
         super.func_77043_a(☃, ☃, ☃, ☃);
      }
   }

   private float func_205126_b(float var1, float var2, float var3) {
      return ☃ + (☃ - ☃) * ☃;
   }

   private ModelBiped.ArmPose func_212499_a(AbstractClientPlayer var1, ItemStack var2) {
      if (☃.func_190926_b()) {
         return ModelBiped.ArmPose.EMPTY;
      } else {
         if (☃.func_184605_cv() > 0) {
            EnumAction ☃ = ☃.func_77975_n();
            if (☃ == EnumAction.BLOCK) {
               return ModelBiped.ArmPose.BLOCK;
            }

            if (☃ == EnumAction.BOW) {
               return ModelBiped.ArmPose.BOW_AND_ARROW;
            }

            if (☃ == EnumAction.SPEAR) {
               return ModelBiped.ArmPose.THROW_SPEAR;
            }
         }

         return ModelBiped.ArmPose.ITEM;
      }
   }
}
