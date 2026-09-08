package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.ModelShulker;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderShulker extends RenderLiving<EntityShulker> {
   public static final ResourceLocation field_204402_a = new ResourceLocation("textures/entity/shulker/shulker.png");
   public static final ResourceLocation[] field_188342_a = new ResourceLocation[]{
      new ResourceLocation("textures/entity/shulker/shulker_white.png"),
      new ResourceLocation("textures/entity/shulker/shulker_orange.png"),
      new ResourceLocation("textures/entity/shulker/shulker_magenta.png"),
      new ResourceLocation("textures/entity/shulker/shulker_light_blue.png"),
      new ResourceLocation("textures/entity/shulker/shulker_yellow.png"),
      new ResourceLocation("textures/entity/shulker/shulker_lime.png"),
      new ResourceLocation("textures/entity/shulker/shulker_pink.png"),
      new ResourceLocation("textures/entity/shulker/shulker_gray.png"),
      new ResourceLocation("textures/entity/shulker/shulker_light_gray.png"),
      new ResourceLocation("textures/entity/shulker/shulker_cyan.png"),
      new ResourceLocation("textures/entity/shulker/shulker_purple.png"),
      new ResourceLocation("textures/entity/shulker/shulker_blue.png"),
      new ResourceLocation("textures/entity/shulker/shulker_brown.png"),
      new ResourceLocation("textures/entity/shulker/shulker_green.png"),
      new ResourceLocation("textures/entity/shulker/shulker_red.png"),
      new ResourceLocation("textures/entity/shulker/shulker_black.png")
   };

   public RenderShulker(RenderManager var1) {
      super(☃, new ModelShulker(), 0.0F);
      this.func_177094_a(new RenderShulker.HeadLayer());
   }

   public ModelShulker func_177087_b() {
      return (ModelShulker)super.func_177087_b();
   }

   public void func_76986_a(EntityShulker var1, double var2, double var4, double var6, float var8, float var9) {
      int ☃ = ☃.func_184693_dc();
      if (☃ > 0 && ☃.func_184697_de()) {
         BlockPos ☃x = ☃.func_184699_da();
         BlockPos ☃xx = ☃.func_184692_dd();
         double ☃xxx = (double)((float)☃ - ☃) / 6.0;
         ☃xxx *= ☃xxx;
         double ☃xxxx = (double)(☃x.func_177958_n() - ☃xx.func_177958_n()) * ☃xxx;
         double ☃xxxxx = (double)(☃x.func_177956_o() - ☃xx.func_177956_o()) * ☃xxx;
         double ☃xxxxxx = (double)(☃x.func_177952_p() - ☃xx.func_177952_p()) * ☃xxx;
         super.func_76986_a(☃, ☃ - ☃xxxx, ☃ - ☃xxxxx, ☃ - ☃xxxxxx, ☃, ☃);
      } else {
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public boolean func_177071_a(EntityShulker var1, ICamera var2, double var3, double var5, double var7) {
      if (super.func_177071_a(☃, ☃, ☃, ☃, ☃)) {
         return true;
      } else {
         if (☃.func_184693_dc() > 0 && ☃.func_184697_de()) {
            BlockPos ☃ = ☃.func_184692_dd();
            BlockPos ☃x = ☃.func_184699_da();
            Vec3d ☃xx = new Vec3d((double)☃x.func_177958_n(), (double)☃x.func_177956_o(), (double)☃x.func_177952_p());
            Vec3d ☃xxx = new Vec3d((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p());
            if (☃.func_78546_a(
               new AxisAlignedBB(☃xxx.field_72450_a, ☃xxx.field_72448_b, ☃xxx.field_72449_c, ☃xx.field_72450_a, ☃xx.field_72448_b, ☃xx.field_72449_c)
            )) {
               return true;
            }
         }

         return false;
      }
   }

   protected ResourceLocation func_110775_a(EntityShulker var1) {
      return ☃.func_190769_dn() == null ? field_204402_a : field_188342_a[☃.func_190769_dn().func_196059_a()];
   }

   protected void func_77043_a(EntityShulker var1, float var2, float var3, float var4) {
      super.func_77043_a(☃, ☃, ☃, ☃);
      switch(☃.func_184696_cZ()) {
         case DOWN:
         default:
            break;
         case EAST:
            GlStateManager.func_179109_b(0.5F, 0.5F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
            break;
         case WEST:
            GlStateManager.func_179109_b(-0.5F, 0.5F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            break;
         case NORTH:
            GlStateManager.func_179109_b(0.0F, 0.5F, -0.5F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            break;
         case SOUTH:
            GlStateManager.func_179109_b(0.0F, 0.5F, 0.5F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
            break;
         case UP:
            GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      }
   }

   protected void func_77041_b(EntityShulker var1, float var2) {
      float ☃ = 0.999F;
      GlStateManager.func_179152_a(0.999F, 0.999F, 0.999F);
   }

   class HeadLayer implements LayerRenderer<EntityShulker> {
      private HeadLayer() {
      }

      public void func_177141_a(EntityShulker var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         GlStateManager.func_179094_E();
         switch(☃.func_184696_cZ()) {
            case DOWN:
            default:
               break;
            case EAST:
               GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179109_b(1.0F, -1.0F, 0.0F);
               GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
               break;
            case WEST:
               GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179109_b(-1.0F, -1.0F, 0.0F);
               GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
               break;
            case NORTH:
               GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179109_b(0.0F, -1.0F, -1.0F);
               break;
            case SOUTH:
               GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179109_b(0.0F, -1.0F, 1.0F);
               break;
            case UP:
               GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179109_b(0.0F, -2.0F, 0.0F);
         }

         ModelRenderer ☃ = RenderShulker.this.func_177087_b().func_205067_c();
         ☃.field_78796_g = ☃ * (float) (Math.PI / 180.0);
         ☃.field_78795_f = ☃ * (float) (Math.PI / 180.0);
         EnumDyeColor ☃x = ☃.func_190769_dn();
         if (☃x == null) {
            RenderShulker.this.func_110776_a(RenderShulker.field_204402_a);
         } else {
            RenderShulker.this.func_110776_a(RenderShulker.field_188342_a[☃x.func_196059_a()]);
         }

         ☃.func_78785_a(☃);
         GlStateManager.func_179121_F();
      }

      @Override
      public boolean func_177142_b() {
         return false;
      }
   }
}
