package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.model.ModelPig;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class LayerSaddle implements LayerRenderer<EntityPig> {
   private static final ResourceLocation field_177158_a = new ResourceLocation("textures/entity/pig/pig_saddle.png");
   private final RenderPig field_177156_b;
   private final ModelPig field_177157_c = new ModelPig(0.5F);

   public LayerSaddle(RenderPig var1) {
      this.field_177156_b = ☃;
   }

   public void func_177141_a(EntityPig var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.func_70901_n()) {
         this.field_177156_b.func_110776_a(field_177158_a);
         this.field_177157_c.func_178686_a(this.field_177156_b.func_177087_b());
         this.field_177157_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
