package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderDrowned;
import net.minecraft.client.renderer.entity.model.ModelDrowned;
import net.minecraft.entity.monster.EntityDrowned;
import net.minecraft.util.ResourceLocation;

public class LayerDrownedOuter implements LayerRenderer<EntityDrowned> {
   private static final ResourceLocation field_204721_a = new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
   private final RenderDrowned field_204722_b;
   private final ModelDrowned field_204723_c = new ModelDrowned(0.25F, 0.0F, 64, 64);

   public LayerDrownedOuter(RenderDrowned var1) {
      this.field_204722_b = ☃;
   }

   public void func_177141_a(EntityDrowned var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!☃.func_82150_aj()) {
         this.field_204723_c.func_178686_a(this.field_204722_b.func_177087_b());
         this.field_204723_c.func_78086_a(☃, ☃, ☃, ☃);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_204722_b.func_110776_a(field_204721_a);
         this.field_204723_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_177142_b() {
      return true;
   }
}
