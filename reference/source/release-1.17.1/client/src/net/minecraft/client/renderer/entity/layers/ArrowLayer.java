package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;

public class ArrowLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
   private final EntityRenderDispatcher dispatcher;

   public ArrowLayer(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
      super(â˜ƒ);
      this.dispatcher = â˜ƒ.getEntityRenderDispatcher();
   }

   @Override
   protected int numStuck(T var1) {
      return â˜ƒ.getArrowCount();
   }

   @Override
   protected void renderStuckItem(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8) {
      float â˜ƒ = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      Arrow â˜ƒx = new Arrow(â˜ƒ.level, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      â˜ƒx.setYRot((float)(Math.atan2((double)â˜ƒ, (double)â˜ƒ) * 180.0F / (float)Math.PI));
      â˜ƒx.setXRot((float)(Math.atan2((double)â˜ƒ, (double)â˜ƒ) * 180.0F / (float)Math.PI));
      â˜ƒx.yRotO = â˜ƒx.getYRot();
      â˜ƒx.xRotO = â˜ƒx.getXRot();
      this.dispatcher.render(â˜ƒx, 0.0, 0.0, 0.0, 0.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
