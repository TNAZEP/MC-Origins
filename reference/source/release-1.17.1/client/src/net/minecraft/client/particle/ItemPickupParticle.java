package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

public class ItemPickupParticle extends Particle {
   private static final int LIFE_TIME = 3;
   private final RenderBuffers renderBuffers;
   private final Entity itemEntity;
   private final Entity target;
   private int life;
   private final EntityRenderDispatcher entityRenderDispatcher;

   public ItemPickupParticle(EntityRenderDispatcher var1, RenderBuffers var2, ClientLevel var3, Entity var4, Entity var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getDeltaMovement());
   }

   private ItemPickupParticle(EntityRenderDispatcher var1, RenderBuffers var2, ClientLevel var3, Entity var4, Entity var5, Vec3 var6) {
      super(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      this.renderBuffers = â˜ƒ;
      this.itemEntity = this.getSafeCopy(â˜ƒ);
      this.target = â˜ƒ;
      this.entityRenderDispatcher = â˜ƒ;
   }

   private Entity getSafeCopy(Entity var1) {
      return (Entity)(!(â˜ƒ instanceof ItemEntity) ? â˜ƒ : ((ItemEntity)â˜ƒ).copy());
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   @Override
   public void render(VertexConsumer var1, Camera var2, float var3) {
      float â˜ƒ = ((float)this.life + â˜ƒ) / 3.0F;
      â˜ƒ *= â˜ƒ;
      double â˜ƒx = Mth.lerp((double)â˜ƒ, this.target.xOld, this.target.getX());
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, this.target.yOld, this.target.getY()) + 0.5;
      double â˜ƒxxx = Mth.lerp((double)â˜ƒ, this.target.zOld, this.target.getZ());
      double â˜ƒxxxx = Mth.lerp((double)â˜ƒ, this.itemEntity.getX(), â˜ƒx);
      double â˜ƒxxxxx = Mth.lerp((double)â˜ƒ, this.itemEntity.getY(), â˜ƒxx);
      double â˜ƒxxxxxx = Mth.lerp((double)â˜ƒ, this.itemEntity.getZ(), â˜ƒxxx);
      MultiBufferSource.BufferSource â˜ƒxxxxxxx = this.renderBuffers.bufferSource();
      Vec3 â˜ƒxxxxxxxx = â˜ƒ.getPosition();
      this.entityRenderDispatcher
         .render(
            this.itemEntity,
            â˜ƒxxxx - â˜ƒxxxxxxxx.x(),
            â˜ƒxxxxx - â˜ƒxxxxxxxx.y(),
            â˜ƒxxxxxx - â˜ƒxxxxxxxx.z(),
            this.itemEntity.getYRot(),
            â˜ƒ,
            new PoseStack(),
            â˜ƒxxxxxxx,
            this.entityRenderDispatcher.getPackedLightCoords(this.itemEntity, â˜ƒ)
         );
      â˜ƒxxxxxxx.endBatch();
   }

   @Override
   public void tick() {
      ++this.life;
      if (this.life == 3) {
         this.remove();
      }
   }
}
