package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemEntityRenderer extends EntityRenderer<ItemEntity> {
   private static final float ITEM_BUNDLE_OFFSET_SCALE = 0.15F;
   private static final int ITEM_COUNT_FOR_5_BUNDLE = 48;
   private static final int ITEM_COUNT_FOR_4_BUNDLE = 32;
   private static final int ITEM_COUNT_FOR_3_BUNDLE = 16;
   private static final int ITEM_COUNT_FOR_2_BUNDLE = 1;
   private static final float FLAT_ITEM_BUNDLE_OFFSET_X = 0.0F;
   private static final float FLAT_ITEM_BUNDLE_OFFSET_Y = 0.0F;
   private static final float FLAT_ITEM_BUNDLE_OFFSET_Z = 0.09375F;
   private final ItemRenderer itemRenderer;
   private final Random random = new Random();

   public ItemEntityRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.itemRenderer = â˜ƒ.getItemRenderer();
      this.shadowRadius = 0.15F;
      this.shadowStrength = 0.75F;
   }

   private int getRenderAmount(ItemStack var1) {
      int â˜ƒ = 1;
      if (â˜ƒ.getCount() > 48) {
         â˜ƒ = 5;
      } else if (â˜ƒ.getCount() > 32) {
         â˜ƒ = 4;
      } else if (â˜ƒ.getCount() > 16) {
         â˜ƒ = 3;
      } else if (â˜ƒ.getCount() > 1) {
         â˜ƒ = 2;
      }

      return â˜ƒ;
   }

   public void render(ItemEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      ItemStack â˜ƒ = â˜ƒ.getItem();
      int â˜ƒx = â˜ƒ.isEmpty() ? 187 : Item.getId(â˜ƒ.getItem()) + â˜ƒ.getDamageValue();
      this.random.setSeed((long)â˜ƒx);
      BakedModel â˜ƒxx = this.itemRenderer.getModel(â˜ƒ, â˜ƒ.level, null, â˜ƒ.getId());
      boolean â˜ƒxxx = â˜ƒxx.isGui3d();
      int â˜ƒxxxx = this.getRenderAmount(â˜ƒ);
      float â˜ƒxxxxx = 0.25F;
      float â˜ƒxxxxxx = Mth.sin(((float)â˜ƒ.getAge() + â˜ƒ) / 10.0F + â˜ƒ.bobOffs) * 0.1F + 0.1F;
      float â˜ƒxxxxxxx = â˜ƒxx.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y();
      â˜ƒ.translate(0.0, (double)(â˜ƒxxxxxx + 0.25F * â˜ƒxxxxxxx), 0.0);
      float â˜ƒxxxxxxxx = â˜ƒ.getSpin(â˜ƒ);
      â˜ƒ.mulPose(Vector3f.YP.rotation(â˜ƒxxxxxxxx));
      float â˜ƒxxxxxxxxx = â˜ƒxx.getTransforms().ground.scale.x();
      float â˜ƒxxxxxxxxxx = â˜ƒxx.getTransforms().ground.scale.y();
      float â˜ƒxxxxxxxxxxx = â˜ƒxx.getTransforms().ground.scale.z();
      if (!â˜ƒxxx) {
         float â˜ƒxxxxxxxxxxxx = -0.0F * (float)(â˜ƒxxxx - 1) * 0.5F * â˜ƒxxxxxxxxx;
         float â˜ƒxxxxxxxxxxxxx = -0.0F * (float)(â˜ƒxxxx - 1) * 0.5F * â˜ƒxxxxxxxxxx;
         float â˜ƒxxxxxxxxxxxxxx = -0.09375F * (float)(â˜ƒxxxx - 1) * 0.5F * â˜ƒxxxxxxxxxxx;
         â˜ƒ.translate((double)â˜ƒxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxxx);
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒxxxx; ++â˜ƒ) {
         â˜ƒ.pushPose();
         if (â˜ƒ > 0) {
            if (â˜ƒxxx) {
               float â˜ƒx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float â˜ƒxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float â˜ƒxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               â˜ƒ.translate((double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒxxx);
            } else {
               float â˜ƒx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float â˜ƒxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               â˜ƒ.translate((double)â˜ƒx, (double)â˜ƒxx, 0.0);
            }
         }

         this.itemRenderer.render(â˜ƒ, ItemTransforms.TransformType.GROUND, false, â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒxx);
         â˜ƒ.popPose();
         if (!â˜ƒxxx) {
            â˜ƒ.translate((double)(0.0F * â˜ƒxxxxxxxxx), (double)(0.0F * â˜ƒxxxxxxxxxx), (double)(0.09375F * â˜ƒxxxxxxxxxxx));
         }
      }

      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(ItemEntity var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
