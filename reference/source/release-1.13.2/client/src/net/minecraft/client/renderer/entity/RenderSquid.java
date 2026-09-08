package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelSquid;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving<EntitySquid> {
   private static final ResourceLocation field_110901_a = new ResourceLocation("textures/entity/squid.png");

   public RenderSquid(RenderManager var1) {
      super(☃, new ModelSquid(), 0.7F);
   }

   protected ResourceLocation func_110775_a(EntitySquid var1) {
      return field_110901_a;
   }

   protected void func_77043_a(EntitySquid var1, float var2, float var3, float var4) {
      float ☃ = ☃.field_70862_e + (☃.field_70861_d - ☃.field_70862_e) * ☃;
      float ☃x = ☃.field_70860_g + (☃.field_70859_f - ☃.field_70860_g) * ☃;
      GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(☃x, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, -1.2F, 0.0F);
   }

   protected float func_77044_a(EntitySquid var1, float var2) {
      return ☃.field_70865_by + (☃.field_70866_j - ☃.field_70865_by) * ☃;
   }
}
