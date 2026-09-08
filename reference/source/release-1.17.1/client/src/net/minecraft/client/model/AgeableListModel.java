package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class AgeableListModel<E extends Entity> extends EntityModel<E> {
   private final boolean scaleHead;
   private final float babyYHeadOffset;
   private final float babyZHeadOffset;
   private final float babyHeadScale;
   private final float babyBodyScale;
   private final float bodyYOffset;

   protected AgeableListModel(boolean var1, float var2, float var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 2.0F, 2.0F, 24.0F);
   }

   protected AgeableListModel(boolean var1, float var2, float var3, float var4, float var5, float var6) {
      this(RenderType::entityCutoutNoCull, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected AgeableListModel(Function<ResourceLocation, RenderType> var1, boolean var2, float var3, float var4, float var5, float var6, float var7) {
      super(â˜ƒ);
      this.scaleHead = â˜ƒ;
      this.babyYHeadOffset = â˜ƒ;
      this.babyZHeadOffset = â˜ƒ;
      this.babyHeadScale = â˜ƒ;
      this.babyBodyScale = â˜ƒ;
      this.bodyYOffset = â˜ƒ;
   }

   protected AgeableListModel() {
      this(false, 5.0F, 2.0F);
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      if (this.young) {
         â˜ƒ.pushPose();
         if (this.scaleHead) {
            float â˜ƒ = 1.5F / this.babyHeadScale;
            â˜ƒ.scale(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.translate(0.0, (double)(this.babyYHeadOffset / 16.0F), (double)(this.babyZHeadOffset / 16.0F));
         this.headParts().forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         â˜ƒ.popPose();
         â˜ƒ.pushPose();
         float â˜ƒ = 1.0F / this.babyBodyScale;
         â˜ƒ.scale(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.translate(0.0, (double)(this.bodyYOffset / 16.0F), 0.0);
         this.bodyParts().forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         â˜ƒ.popPose();
      } else {
         this.headParts().forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         this.bodyParts().forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }

   protected abstract Iterable<ModelPart> headParts();

   protected abstract Iterable<ModelPart> bodyParts();
}
