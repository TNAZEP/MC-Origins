package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends Render<Entity> {
   public RenderEntity(RenderManager var1) {
      super(☃);
   }

   @Override
   public void func_76986_a(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      func_76978_a(☃.func_174813_aQ(), ☃ - ☃.field_70142_S, ☃ - ☃.field_70137_T, ☃ - ☃.field_70136_U);
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   @Override
   protected ResourceLocation func_110775_a(Entity var1) {
      return null;
   }
}
