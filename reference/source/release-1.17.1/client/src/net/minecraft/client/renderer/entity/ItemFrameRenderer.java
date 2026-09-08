package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;

public class ItemFrameRenderer<T extends ItemFrame> extends EntityRenderer<T> {
   private static final ModelResourceLocation FRAME_LOCATION = new ModelResourceLocation("item_frame", "map=false");
   private static final ModelResourceLocation MAP_FRAME_LOCATION = new ModelResourceLocation("item_frame", "map=true");
   private static final ModelResourceLocation GLOW_FRAME_LOCATION = new ModelResourceLocation("glow_item_frame", "map=false");
   private static final ModelResourceLocation GLOW_MAP_FRAME_LOCATION = new ModelResourceLocation("glow_item_frame", "map=true");
   public static final int GLOW_FRAME_BRIGHTNESS = 5;
   public static final int BRIGHT_MAP_LIGHT_ADJUSTMENT = 30;
   private final Minecraft minecraft = Minecraft.getInstance();
   private final ItemRenderer itemRenderer;

   public ItemFrameRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.itemRenderer = â˜ƒ.getItemRenderer();
   }

   protected int getBlockLightLevel(T var1, BlockPos var2) {
      return â˜ƒ.getType() == EntityType.GLOW_ITEM_FRAME ? Math.max(5, super.getBlockLightLevel(â˜ƒ, â˜ƒ)) : super.getBlockLightLevel(â˜ƒ, â˜ƒ);
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.pushPose();
      Direction â˜ƒ = â˜ƒ.getDirection();
      Vec3 â˜ƒx = this.getRenderOffset(â˜ƒ, â˜ƒ);
      â˜ƒ.translate(-â˜ƒx.x(), -â˜ƒx.y(), -â˜ƒx.z());
      double â˜ƒxx = 0.46875;
      â˜ƒ.translate((double)â˜ƒ.getStepX() * 0.46875, (double)â˜ƒ.getStepY() * 0.46875, (double)â˜ƒ.getStepZ() * 0.46875);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ.getXRot()));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ.getYRot()));
      boolean â˜ƒxxx = â˜ƒ.isInvisible();
      ItemStack â˜ƒxxxx = â˜ƒ.getItem();
      if (!â˜ƒxxx) {
         BlockRenderDispatcher â˜ƒxxxxx = this.minecraft.getBlockRenderer();
         ModelManager â˜ƒxxxxxx = â˜ƒxxxxx.getBlockModelShaper().getModelManager();
         ModelResourceLocation â˜ƒxxxxxxx = this.getFrameModelResourceLoc(â˜ƒ, â˜ƒxxxx);
         â˜ƒ.pushPose();
         â˜ƒ.translate(-0.5, -0.5, -0.5);
         â˜ƒxxxxx.getModelRenderer()
            .renderModel(
               â˜ƒ.last(), â˜ƒ.getBuffer(Sheets.solidBlockSheet()), null, â˜ƒxxxxxx.getModel(â˜ƒxxxxxxx), 1.0F, 1.0F, 1.0F, â˜ƒ, OverlayTexture.NO_OVERLAY
            );
         â˜ƒ.popPose();
      }

      if (!â˜ƒxxxx.isEmpty()) {
         boolean â˜ƒ = â˜ƒxxxx.is(Items.FILLED_MAP);
         if (â˜ƒxxx) {
            â˜ƒ.translate(0.0, 0.0, 0.5);
         } else {
            â˜ƒ.translate(0.0, 0.0, 0.4375);
         }

         int â˜ƒ = â˜ƒ ? â˜ƒ.getRotation() % 4 * 2 : â˜ƒ.getRotation();
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒ * 360.0F / 8.0F));
         if (â˜ƒ) {
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            float â˜ƒx = 0.0078125F;
            â˜ƒ.scale(0.0078125F, 0.0078125F, 0.0078125F);
            â˜ƒ.translate(-64.0, -64.0, 0.0);
            Integer â˜ƒxx = MapItem.getMapId(â˜ƒxxxx);
            MapItemSavedData â˜ƒxxx = MapItem.getSavedData(â˜ƒxx, â˜ƒ.level);
            â˜ƒ.translate(0.0, 0.0, -1.0);
            if (â˜ƒxxx != null) {
               int â˜ƒxxxx = this.getLightVal(â˜ƒ, 15728850, â˜ƒ);
               this.minecraft.gameRenderer.getMapRenderer().render(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx, true, â˜ƒxxxx);
            }
         } else {
            int â˜ƒ = this.getLightVal(â˜ƒ, 15728880, â˜ƒ);
            â˜ƒ.scale(0.5F, 0.5F, 0.5F);
            this.itemRenderer.renderStatic(â˜ƒxxxx, ItemTransforms.TransformType.FIXED, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ, â˜ƒ, â˜ƒ.getId());
         }
      }

      â˜ƒ.popPose();
   }

   private int getLightVal(T var1, int var2, int var3) {
      return â˜ƒ.getType() == EntityType.GLOW_ITEM_FRAME ? â˜ƒ : â˜ƒ;
   }

   private ModelResourceLocation getFrameModelResourceLoc(T var1, ItemStack var2) {
      boolean â˜ƒ = â˜ƒ.getType() == EntityType.GLOW_ITEM_FRAME;
      if (â˜ƒ.is(Items.FILLED_MAP)) {
         return â˜ƒ ? GLOW_MAP_FRAME_LOCATION : MAP_FRAME_LOCATION;
      } else {
         return â˜ƒ ? GLOW_FRAME_LOCATION : FRAME_LOCATION;
      }
   }

   public Vec3 getRenderOffset(T var1, float var2) {
      return new Vec3((double)((float)â˜ƒ.getDirection().getStepX() * 0.3F), -0.25, (double)((float)â˜ƒ.getDirection().getStepZ() * 0.3F));
   }

   public ResourceLocation getTextureLocation(T var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   protected boolean shouldShowName(T var1) {
      if (Minecraft.renderNames() && !â˜ƒ.getItem().isEmpty() && â˜ƒ.getItem().hasCustomHoverName() && this.entityRenderDispatcher.crosshairPickEntity == â˜ƒ) {
         double â˜ƒ = this.entityRenderDispatcher.distanceToSqr(â˜ƒ);
         float â˜ƒx = â˜ƒ.isDiscrete() ? 32.0F : 64.0F;
         return â˜ƒ < (double)(â˜ƒx * â˜ƒx);
      } else {
         return false;
      }
   }

   protected void renderNameTag(T var1, Component var2, PoseStack var3, MultiBufferSource var4, int var5) {
      super.renderNameTag(â˜ƒ, â˜ƒ.getItem().getHoverName(), â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
