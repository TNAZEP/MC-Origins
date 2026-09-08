package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityConduit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public class TileEntityConduitRenderer extends TileEntityRenderer<TileEntityConduit> {
   private static final ResourceLocation field_205118_a = new ResourceLocation("textures/entity/conduit/base.png");
   private static final ResourceLocation field_205119_d = new ResourceLocation("textures/entity/conduit/cage.png");
   private static final ResourceLocation field_205120_e = new ResourceLocation("textures/entity/conduit/wind.png");
   private static final ResourceLocation field_205121_f = new ResourceLocation("textures/entity/conduit/wind_vertical.png");
   private static final ResourceLocation field_207746_g = new ResourceLocation("textures/entity/conduit/open_eye.png");
   private static final ResourceLocation field_207747_h = new ResourceLocation("textures/entity/conduit/closed_eye.png");
   private final ModelBase field_205122_g = new TileEntityConduitRenderer.ShellModel();
   private final ModelBase field_205123_h = new TileEntityConduitRenderer.CageModel();
   private final TileEntityConduitRenderer.WindModel field_205124_i = new TileEntityConduitRenderer.WindModel();
   private final TileEntityConduitRenderer.EyeModel field_207748_l = new TileEntityConduitRenderer.EyeModel();

   public void func_199341_a(TileEntityConduit var1, double var2, double var4, double var6, float var8, int var9) {
      float ☃ = (float)☃.field_205041_a + ☃;
      if (!☃.func_205039_c()) {
         float ☃x = ☃.func_205036_a(0.0F);
         this.func_147499_a(field_205118_a);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
         GlStateManager.func_179114_b(☃x, 0.0F, 1.0F, 0.0F);
         this.field_205122_g.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.func_179121_F();
      } else if (☃.func_205039_c()) {
         float ☃ = ☃.func_205036_a(☃) * (180.0F / (float)Math.PI);
         float ☃x = MathHelper.func_76126_a(☃ * 0.1F) / 2.0F + 0.5F;
         ☃x = ☃x * ☃x + ☃x;
         this.func_147499_a(field_205119_d);
         GlStateManager.func_179129_p();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.3F + ☃x * 0.2F, (float)☃ + 0.5F);
         GlStateManager.func_179114_b(☃, 0.5F, 1.0F, 0.5F);
         this.field_205123_h.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.func_179121_F();
         int ☃xx = 3;
         int ☃xxx = ☃.field_205041_a / 3 % TileEntityConduitRenderer.WindModel.field_205078_a;
         this.field_205124_i.func_205077_a(☃xxx);
         int ☃xxxx = ☃.field_205041_a / (3 * TileEntityConduitRenderer.WindModel.field_205078_a) % 3;
         switch(☃xxxx) {
            case 0:
               this.func_147499_a(field_205120_e);
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
               GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
               break;
            case 1:
               this.func_147499_a(field_205121_f);
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
               GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
               break;
            case 2:
               this.func_147499_a(field_205120_e);
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
               GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
               GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
               this.field_205124_i.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GlStateManager.func_179121_F();
         }

         Entity ☃ = Minecraft.func_71410_x().func_175606_aa();
         Vec2f ☃x = Vec2f.field_189974_a;
         if (☃ != null) {
            ☃x = ☃.func_189653_aC();
         }

         if (☃.func_207737_d()) {
            this.func_147499_a(field_207746_g);
         } else {
            this.func_147499_a(field_207747_h);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.3F + ☃x * 0.2F, (float)☃ + 0.5F);
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179114_b(-☃x.field_189983_j, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(☃x.field_189982_i, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
         this.field_207748_l.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.083333336F);
         GlStateManager.func_179121_F();
      }

      super.func_199341_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   static class CageModel extends ModelBase {
      private final ModelRenderer field_205075_a;

      public CageModel() {
         this.field_78090_t = 32;
         this.field_78089_u = 16;
         this.field_205075_a = new ModelRenderer(this, 0, 0);
         this.field_205075_a.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      }

      @Override
      public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.field_205075_a.func_78785_a(☃);
      }
   }

   static class EyeModel extends ModelBase {
      private final ModelRenderer field_207745_a;

      public EyeModel() {
         this.field_78090_t = 8;
         this.field_78089_u = 8;
         this.field_207745_a = new ModelRenderer(this, 0, 0);
         this.field_207745_a.func_78790_a(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.01F);
      }

      @Override
      public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.field_207745_a.func_78785_a(☃);
      }
   }

   static class ShellModel extends ModelBase {
      private final ModelRenderer field_205076_a;

      public ShellModel() {
         this.field_78090_t = 32;
         this.field_78089_u = 16;
         this.field_205076_a = new ModelRenderer(this, 0, 0);
         this.field_205076_a.func_78789_a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      }

      @Override
      public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.field_205076_a.func_78785_a(☃);
      }
   }

   static class WindModel extends ModelBase {
      public static int field_205078_a = 22;
      private final ModelRenderer[] field_205079_b = new ModelRenderer[field_205078_a];
      private int field_205080_c;

      public WindModel() {
         this.field_78090_t = 64;
         this.field_78089_u = 1024;

         for(int ☃ = 0; ☃ < field_205078_a; ++☃) {
            this.field_205079_b[☃] = new ModelRenderer(this, 0, 32 * ☃);
            this.field_205079_b[☃].func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
         }
      }

      @Override
      public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.field_205079_b[this.field_205080_c].func_78785_a(☃);
      }

      public void func_205077_a(int var1) {
         this.field_205080_c = ☃;
      }
   }
}
