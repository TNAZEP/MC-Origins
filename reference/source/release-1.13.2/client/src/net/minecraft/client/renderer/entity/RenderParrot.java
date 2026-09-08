package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.ModelParrot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderParrot extends RenderLiving<EntityParrot> {
   public static final ResourceLocation[] field_192862_a = new ResourceLocation[]{
      new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_green.png"),
      new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_grey.png")
   };

   public RenderParrot(RenderManager var1) {
      super(☃, new ModelParrot(), 0.3F);
   }

   protected ResourceLocation func_110775_a(EntityParrot var1) {
      return field_192862_a[☃.func_191998_ds()];
   }

   public float func_77044_a(EntityParrot var1, float var2) {
      return this.func_192861_b(☃, ☃);
   }

   private float func_192861_b(EntityParrot var1, float var2) {
      float ☃ = ☃.field_192011_bE + (☃.field_192008_bB - ☃.field_192011_bE) * ☃;
      float ☃x = ☃.field_192010_bD + (☃.field_192009_bC - ☃.field_192010_bD) * ☃;
      return (MathHelper.func_76126_a(☃) + 1.0F) * ☃x;
   }
}
