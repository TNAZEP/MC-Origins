package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkullBlockRenderer implements BlockEntityRenderer<SkullBlockEntity> {
   private final Map<SkullBlock.Type, SkullModelBase> modelByType;
   private static final Map<SkullBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.<SkullBlock.Type, ResourceLocation>newHashMap(), var0 -> {
      var0.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
      var0.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
      var0.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
      var0.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
      var0.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
      var0.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultSkin());
   });

   public static Map<SkullBlock.Type, SkullModelBase> createSkullRenderers(EntityModelSet var0) {
      Builder<SkullBlock.Type, SkullModelBase> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.put(SkullBlock.Types.SKELETON, new SkullModel(â˜ƒ.bakeLayer(ModelLayers.SKELETON_SKULL)));
      â˜ƒ.put(SkullBlock.Types.WITHER_SKELETON, new SkullModel(â˜ƒ.bakeLayer(ModelLayers.WITHER_SKELETON_SKULL)));
      â˜ƒ.put(SkullBlock.Types.PLAYER, new SkullModel(â˜ƒ.bakeLayer(ModelLayers.PLAYER_HEAD)));
      â˜ƒ.put(SkullBlock.Types.ZOMBIE, new SkullModel(â˜ƒ.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
      â˜ƒ.put(SkullBlock.Types.CREEPER, new SkullModel(â˜ƒ.bakeLayer(ModelLayers.CREEPER_HEAD)));
      â˜ƒ.put(SkullBlock.Types.DRAGON, new DragonHeadModel(â˜ƒ.bakeLayer(ModelLayers.DRAGON_SKULL)));
      return â˜ƒ.build();
   }

   public SkullBlockRenderer(BlockEntityRendererProvider.Context var1) {
      this.modelByType = createSkullRenderers(â˜ƒ.getModelSet());
   }

   public void render(SkullBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      float â˜ƒ = â˜ƒ.getMouthAnimation(â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.getBlockState();
      boolean â˜ƒxx = â˜ƒx.getBlock() instanceof WallSkullBlock;
      Direction â˜ƒxxx = â˜ƒxx ? â˜ƒx.getValue(WallSkullBlock.FACING) : null;
      float â˜ƒxxxx = 22.5F * (float)(â˜ƒxx ? (2 + â˜ƒxxx.get2DDataValue()) * 4 : â˜ƒx.getValue(SkullBlock.ROTATION));
      SkullBlock.Type â˜ƒxxxxx = ((AbstractSkullBlock)â˜ƒx.getBlock()).getType();
      SkullModelBase â˜ƒxxxxxx = (SkullModelBase)this.modelByType.get(â˜ƒxxxxx);
      RenderType â˜ƒxxxxxxx = getRenderType(â˜ƒxxxxx, â˜ƒ.getOwnerProfile());
      renderSkull(â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxx);
   }

   public static void renderSkull(
      @Nullable Direction var0, float var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, SkullModelBase var6, RenderType var7
   ) {
      â˜ƒ.pushPose();
      if (â˜ƒ == null) {
         â˜ƒ.translate(0.5, 0.0, 0.5);
      } else {
         float â˜ƒ = 0.25F;
         â˜ƒ.translate((double)(0.5F - (float)â˜ƒ.getStepX() * 0.25F), 0.25, (double)(0.5F - (float)â˜ƒ.getStepZ() * 0.25F));
      }

      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(â˜ƒ);
      â˜ƒ.setupAnim(â˜ƒ, â˜ƒ, 0.0F);
      â˜ƒ.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
   }

   public static RenderType getRenderType(SkullBlock.Type var0, @Nullable GameProfile var1) {
      ResourceLocation â˜ƒ = (ResourceLocation)SKIN_BY_TYPE.get(â˜ƒ);
      if (â˜ƒ == SkullBlock.Types.PLAYER && â˜ƒ != null) {
         Minecraft â˜ƒx = Minecraft.getInstance();
         Map<Type, MinecraftProfileTexture> â˜ƒxx = â˜ƒx.getSkinManager().getInsecureSkinInformation(â˜ƒ);
         return â˜ƒxx.containsKey(Type.SKIN)
            ? RenderType.entityTranslucent(â˜ƒx.getSkinManager().registerTexture((MinecraftProfileTexture)â˜ƒxx.get(Type.SKIN), Type.SKIN))
            : RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(â˜ƒ)));
      } else {
         return RenderType.entityCutoutNoCullZOffset(â˜ƒ);
      }
   }
}
