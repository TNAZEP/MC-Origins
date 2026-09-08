package net.minecraft.client.renderer.debug;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Util;
import net.minecraft.util.math.shapes.VoxelShape;

public class DebugRendererCollisionBox implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_191312_a;
   private double field_195631_b = Double.MIN_VALUE;
   private List<VoxelShape> field_195632_c = Collections.emptyList();

   public DebugRendererCollisionBox(Minecraft var1) {
      this.field_191312_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_191312_a.field_71439_g;
      double ☃x = (double)Util.func_211178_c();
      if (☃x - this.field_195631_b > 1.0E8) {
         this.field_195631_b = ☃x;
         this.field_195632_c = (List)☃.field_70170_p.func_212388_b(☃, ☃.func_174813_aQ().func_186662_g(6.0)).collect(Collectors.toList());
      }

      double ☃ = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃x = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);

      for(VoxelShape ☃xxx : this.field_195632_c) {
         WorldRenderer.func_195470_a(☃xxx, -☃, -☃x, -☃xx, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
