package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.model.ModelIllager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderIllusionIllager extends RenderLiving<EntityMob> {
   private static final ResourceLocation field_193121_a = new ResourceLocation("textures/entity/illager/illusioner.png");

   public RenderIllusionIllager(RenderManager var1) {
      super(☃, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
      this.func_177094_a(new LayerHeldItem(this) {
         @Override
         public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
            if (((EntityIllusionIllager)☃).func_193082_dl() || ((EntityIllusionIllager)☃).func_193096_dj()) {
               super.func_177141_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
            }
         }

         @Override
         protected void func_191361_a(EnumHandSide var1) {
            ((ModelIllager)this.field_177206_a.func_177087_b()).func_191216_a(☃).func_78794_c(0.0625F);
         }
      });
      ((ModelIllager)this.func_177087_b()).func_205062_a().field_78806_j = true;
   }

   protected ResourceLocation func_110775_a(EntityMob var1) {
      return field_193121_a;
   }

   protected void func_77041_b(EntityMob var1, float var2) {
      float ☃ = 0.9375F;
      GlStateManager.func_179152_a(0.9375F, 0.9375F, 0.9375F);
   }

   public void func_76986_a(EntityMob var1, double var2, double var4, double var6, float var8, float var9) {
      if (☃.func_82150_aj()) {
         Vec3d[] ☃ = ((EntityIllusionIllager)☃).func_193098_a(☃);
         float ☃x = this.func_77044_a(☃, ☃);

         for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
            super.func_76986_a(
               ☃,
               ☃ + ☃[☃xx].field_72450_a + (double)MathHelper.func_76134_b((float)☃xx + ☃x * 0.5F) * 0.025,
               ☃ + ☃[☃xx].field_72448_b + (double)MathHelper.func_76134_b((float)☃xx + ☃x * 0.75F) * 0.0125,
               ☃ + ☃[☃xx].field_72449_c + (double)MathHelper.func_76134_b((float)☃xx + ☃x * 0.7F) * 0.025,
               ☃,
               ☃
            );
         }
      } else {
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   protected boolean func_193115_c(EntityMob var1) {
      return true;
   }
}
