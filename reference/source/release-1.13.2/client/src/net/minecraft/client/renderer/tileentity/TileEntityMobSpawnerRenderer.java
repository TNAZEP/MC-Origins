package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer<TileEntityMobSpawner> {
   public void func_199341_a(TileEntityMobSpawner var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃, (float)☃ + 0.5F);
      func_147517_a(☃.func_145881_a(), ☃, ☃, ☃, ☃);
      GlStateManager.func_179121_F();
   }

   public static void func_147517_a(MobSpawnerBaseLogic var0, double var1, double var3, double var5, float var7) {
      Entity ☃ = ☃.func_184994_d();
      if (☃ != null) {
         float ☃x = 0.53125F;
         float ☃xx = Math.max(☃.field_70130_N, ☃.field_70131_O);
         if ((double)☃xx > 1.0) {
            ☃x /= ☃xx;
         }

         GlStateManager.func_179109_b(0.0F, 0.4F, 0.0F);
         GlStateManager.func_179114_b((float)(☃.func_177223_e() + (☃.func_177222_d() - ☃.func_177223_e()) * (double)☃) * 10.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.0F, -0.2F, 0.0F);
         GlStateManager.func_179114_b(-30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179152_a(☃x, ☃x, ☃x);
         ☃.func_70012_b(☃, ☃, ☃, 0.0F, 0.0F);
         Minecraft.func_71410_x().func_175598_ae().func_188391_a(☃, 0.0, 0.0, 0.0, 0.0F, ☃, false);
      }
   }
}
