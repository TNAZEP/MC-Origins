package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.scores.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final float EYE_BED_OFFSET = 0.1F;
   protected M model;
   protected final List<RenderLayer<T, M>> layers = Lists.<RenderLayer<T, M>>newArrayList();

   public LivingEntityRenderer(EntityRendererProvider.Context var1, M var2, float var3) {
      super(â˜ƒ);
      this.model = â˜ƒ;
      this.shadowRadius = â˜ƒ;
   }

   protected final boolean addLayer(RenderLayer<T, M> var1) {
      return this.layers.add(â˜ƒ);
   }

   @Override
   public M getModel() {
      return this.model;
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      this.model.attackTime = this.getAttackAnim(â˜ƒ, â˜ƒ);
      this.model.riding = â˜ƒ.isPassenger();
      this.model.young = â˜ƒ.isBaby();
      float â˜ƒx = Mth.rotLerp(â˜ƒ, â˜ƒ.yBodyRotO, â˜ƒ.yBodyRot);
      float â˜ƒxx = Mth.rotLerp(â˜ƒ, â˜ƒ.yHeadRotO, â˜ƒ.yHeadRot);
      float â˜ƒxxx = â˜ƒxx - â˜ƒx;
      if (â˜ƒ.isPassenger() && â˜ƒ.getVehicle() instanceof LivingEntity â˜ƒ) {
         â˜ƒx = Mth.rotLerp(â˜ƒ, â˜ƒ.yBodyRotO, â˜ƒ.yBodyRot);
         â˜ƒxxx = â˜ƒxx - â˜ƒx;
         float â˜ƒxxxx = Mth.wrapDegrees(â˜ƒxxx);
         if (â˜ƒxxxx < -85.0F) {
            â˜ƒxxxx = -85.0F;
         }

         if (â˜ƒxxxx >= 85.0F) {
            â˜ƒxxxx = 85.0F;
         }

         â˜ƒx = â˜ƒxx - â˜ƒxxxx;
         if (â˜ƒxxxx * â˜ƒxxxx > 2500.0F) {
            â˜ƒx += â˜ƒxxxx * 0.2F;
         }

         â˜ƒxxx = â˜ƒxx - â˜ƒx;
      }

      float â˜ƒ = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      if (â˜ƒ.getPose() == Pose.SLEEPING) {
         Direction â˜ƒx = â˜ƒ.getBedOrientation();
         if (â˜ƒx != null) {
            float â˜ƒxx = â˜ƒ.getEyeHeight(Pose.STANDING) - 0.1F;
            â˜ƒ.translate((double)((float)(-â˜ƒx.getStepX()) * â˜ƒxx), 0.0, (double)((float)(-â˜ƒx.getStepZ()) * â˜ƒxx));
         }
      }

      float â˜ƒ = this.getBob(â˜ƒ, â˜ƒ);
      this.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      this.scale(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.translate(0.0, -1.501F, 0.0);
      float â˜ƒx = 0.0F;
      float â˜ƒxx = 0.0F;
      if (!â˜ƒ.isPassenger() && â˜ƒ.isAlive()) {
         â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.animationSpeedOld, â˜ƒ.animationSpeed);
         â˜ƒxx = â˜ƒ.animationPosition - â˜ƒ.animationSpeed * (1.0F - â˜ƒ);
         if (â˜ƒ.isBaby()) {
            â˜ƒxx *= 3.0F;
         }

         if (â˜ƒx > 1.0F) {
            â˜ƒx = 1.0F;
         }
      }

      this.model.prepareMobModel(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ);
      this.model.setupAnim(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒ);
      Minecraft â˜ƒ = Minecraft.getInstance();
      boolean â˜ƒx = this.isBodyVisible(â˜ƒ);
      boolean â˜ƒxx = !â˜ƒx && !â˜ƒ.isInvisibleTo(â˜ƒ.player);
      boolean â˜ƒxxx = â˜ƒ.shouldEntityAppearGlowing(â˜ƒ);
      RenderType â˜ƒxxxx = this.getRenderType(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      if (â˜ƒxxxx != null) {
         VertexConsumer â˜ƒxxxxx = â˜ƒ.getBuffer(â˜ƒxxxx);
         int â˜ƒxxxxxx = getOverlayCoords(â˜ƒ, this.getWhiteOverlayProgress(â˜ƒ, â˜ƒ));
         this.model.renderToBuffer(â˜ƒ, â˜ƒxxxxx, â˜ƒ, â˜ƒxxxxxx, 1.0F, 1.0F, 1.0F, â˜ƒxx ? 0.15F : 1.0F);
      }

      if (!â˜ƒ.isSpectator()) {
         for(RenderLayer<T, M> â˜ƒ : this.layers) {
            â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒ);
         }
      }

      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   protected RenderType getRenderType(T var1, boolean var2, boolean var3, boolean var4) {
      ResourceLocation â˜ƒ = this.getTextureLocation(â˜ƒ);
      if (â˜ƒ) {
         return RenderType.itemEntityTranslucentCull(â˜ƒ);
      } else if (â˜ƒ) {
         return this.model.renderType(â˜ƒ);
      } else {
         return â˜ƒ ? RenderType.outline(â˜ƒ) : null;
      }
   }

   public static int getOverlayCoords(LivingEntity var0, float var1) {
      return OverlayTexture.pack(OverlayTexture.u(â˜ƒ), OverlayTexture.v(â˜ƒ.hurtTime > 0 || â˜ƒ.deathTime > 0));
   }

   protected boolean isBodyVisible(T var1) {
      return !â˜ƒ.isInvisible();
   }

   private static float sleepDirectionToRotation(Direction var0) {
      switch(â˜ƒ) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected boolean isShaking(T var1) {
      return â˜ƒ.isFullyFrozen();
   }

   protected void setupRotations(T var1, PoseStack var2, float var3, float var4, float var5) {
      if (this.isShaking(â˜ƒ)) {
         â˜ƒ += (float)(Math.cos((double)â˜ƒ.tickCount * 3.25) * Math.PI * 0.4F);
      }

      Pose â˜ƒ = â˜ƒ.getPose();
      if (â˜ƒ != Pose.SLEEPING) {
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      }

      if (â˜ƒ.deathTime > 0) {
         float â˜ƒ = ((float)â˜ƒ.deathTime + â˜ƒ - 1.0F) / 20.0F * 1.6F;
         â˜ƒ = Mth.sqrt(â˜ƒ);
         if (â˜ƒ > 1.0F) {
            â˜ƒ = 1.0F;
         }

         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒ * this.getFlipDegrees(â˜ƒ)));
      } else if (â˜ƒ.isAutoSpinAttack()) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-90.0F - â˜ƒ.getXRot()));
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(((float)â˜ƒ.tickCount + â˜ƒ) * -75.0F));
      } else if (â˜ƒ == Pose.SLEEPING) {
         Direction â˜ƒ = â˜ƒ.getBedOrientation();
         float â˜ƒx = â˜ƒ != null ? sleepDirectionToRotation(â˜ƒ) : â˜ƒ;
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(this.getFlipDegrees(â˜ƒ)));
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(270.0F));
      } else if (â˜ƒ.hasCustomName() || â˜ƒ instanceof Player) {
         String â˜ƒ = ChatFormatting.stripFormatting(â˜ƒ.getName().getString());
         if (("Dinnerbone".equals(â˜ƒ) || "Grumm".equals(â˜ƒ)) && (!(â˜ƒ instanceof Player) || ((Player)â˜ƒ).isModelPartShown(PlayerModelPart.CAPE))) {
            â˜ƒ.translate(0.0, (double)(â˜ƒ.getBbHeight() + 0.1F), 0.0);
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
         }
      }
   }

   protected float getAttackAnim(T var1, float var2) {
      return â˜ƒ.getAttackAnim(â˜ƒ);
   }

   protected float getBob(T var1, float var2) {
      return (float)â˜ƒ.tickCount + â˜ƒ;
   }

   protected float getFlipDegrees(T var1) {
      return 90.0F;
   }

   protected float getWhiteOverlayProgress(T var1, float var2) {
      return 0.0F;
   }

   protected void scale(T var1, PoseStack var2, float var3) {
   }

   protected boolean shouldShowName(T var1) {
      double â˜ƒ = this.entityRenderDispatcher.distanceToSqr(â˜ƒ);
      float â˜ƒx = â˜ƒ.isDiscrete() ? 32.0F : 64.0F;
      if (â˜ƒ >= (double)(â˜ƒx * â˜ƒx)) {
         return false;
      } else {
         Minecraft â˜ƒ = Minecraft.getInstance();
         LocalPlayer â˜ƒx = â˜ƒ.player;
         boolean â˜ƒxx = !â˜ƒ.isInvisibleTo(â˜ƒx);
         if (â˜ƒ != â˜ƒx) {
            Team â˜ƒxxx = â˜ƒ.getTeam();
            Team â˜ƒxxxx = â˜ƒx.getTeam();
            if (â˜ƒxxx != null) {
               Team.Visibility â˜ƒxxxxx = â˜ƒxxx.getNameTagVisibility();
               switch(â˜ƒxxxxx) {
                  case ALWAYS:
                     return â˜ƒxx;
                  case NEVER:
                     return false;
                  case HIDE_FOR_OTHER_TEAMS:
                     return â˜ƒxxxx == null ? â˜ƒxx : â˜ƒxxx.isAlliedTo(â˜ƒxxxx) && (â˜ƒxxx.canSeeFriendlyInvisibles() || â˜ƒxx);
                  case HIDE_FOR_OWN_TEAM:
                     return â˜ƒxxxx == null ? â˜ƒxx : !â˜ƒxxx.isAlliedTo(â˜ƒxxxx) && â˜ƒxx;
                  default:
                     return true;
               }
            }
         }

         return Minecraft.renderNames() && â˜ƒ != â˜ƒ.getCameraEntity() && â˜ƒxx && !â˜ƒ.isVehicle();
      }
   }
}
