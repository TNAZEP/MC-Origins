package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;

public abstract class ColorableHierarchicalModel<E extends Entity> extends HierarchicalModel<E> {
   private float r = 1.0F;
   private float g = 1.0F;
   private float b = 1.0F;

   public void setColor(float var1, float var2, float var3) {
      this.r = â˜ƒ;
      this.g = â˜ƒ;
      this.b = â˜ƒ;
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      super.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.r * â˜ƒ, this.g * â˜ƒ, this.b * â˜ƒ, â˜ƒ);
   }
}
