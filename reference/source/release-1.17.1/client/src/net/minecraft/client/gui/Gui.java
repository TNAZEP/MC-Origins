package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.chat.ChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.chat.StandardChatListener;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import org.apache.commons.lang3.StringUtils;

public class Gui extends GuiComponent {
   private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation("textures/misc/pumpkinblur.png");
   private static final ResourceLocation SPYGLASS_SCOPE_LOCATION = new ResourceLocation("textures/misc/spyglass_scope.png");
   private static final ResourceLocation POWDER_SNOW_OUTLINE_LOCATION = new ResourceLocation("textures/misc/powder_snow_outline.png");
   private static final Component DEMO_EXPIRED_TEXT = new TranslatableComponent("demo.demoExpired");
   private static final int COLOR_WHITE = 16777215;
   private static final float MIN_CROSSHAIR_ATTACK_SPEED = 5.0F;
   private static final int NUM_HEARTS_PER_ROW = 10;
   private static final int LINE_HEIGHT = 10;
   private static final String SPACER = ": ";
   private static final float PORTAL_OVERLAY_ALPHA_MIN = 0.2F;
   private static final int HEART_SIZE = 9;
   private static final int HEART_SEPARATION = 8;
   private final Random random = new Random();
   private final Minecraft minecraft;
   private final ItemRenderer itemRenderer;
   private final ChatComponent chat;
   private int tickCount;
   @Nullable
   private Component overlayMessageString;
   private int overlayMessageTime;
   private boolean animateOverlayMessageColor;
   public float vignetteBrightness = 1.0F;
   private int toolHighlightTimer;
   private ItemStack lastToolHighlight = ItemStack.EMPTY;
   private final DebugScreenOverlay debugScreen;
   private final SubtitleOverlay subtitleOverlay;
   private final SpectatorGui spectatorGui;
   private final PlayerTabOverlay tabList;
   private final BossHealthOverlay bossOverlay;
   private int titleTime;
   @Nullable
   private Component title;
   @Nullable
   private Component subtitle;
   private int titleFadeInTime;
   private int titleStayTime;
   private int titleFadeOutTime;
   private int lastHealth;
   private int displayHealth;
   private long lastHealthTime;
   private long healthBlinkTime;
   private int screenWidth;
   private int screenHeight;
   private final Map<ChatType, List<ChatListener>> chatListeners = Maps.newHashMap();
   private float scopeScale;

   public Gui(Minecraft var1) {
      this.minecraft = â˜ƒ;
      this.itemRenderer = â˜ƒ.getItemRenderer();
      this.debugScreen = new DebugScreenOverlay(â˜ƒ);
      this.spectatorGui = new SpectatorGui(â˜ƒ);
      this.chat = new ChatComponent(â˜ƒ);
      this.tabList = new PlayerTabOverlay(â˜ƒ, this);
      this.bossOverlay = new BossHealthOverlay(â˜ƒ);
      this.subtitleOverlay = new SubtitleOverlay(â˜ƒ);

      for(ChatType â˜ƒ : ChatType.values()) {
         this.chatListeners.put(â˜ƒ, Lists.newArrayList());
      }

      ChatListener â˜ƒ = NarratorChatListener.INSTANCE;
      ((List)this.chatListeners.get(ChatType.CHAT)).add(new StandardChatListener(â˜ƒ));
      ((List)this.chatListeners.get(ChatType.CHAT)).add(â˜ƒ);
      ((List)this.chatListeners.get(ChatType.SYSTEM)).add(new StandardChatListener(â˜ƒ));
      ((List)this.chatListeners.get(ChatType.SYSTEM)).add(â˜ƒ);
      ((List)this.chatListeners.get(ChatType.GAME_INFO)).add(new OverlayChatListener(â˜ƒ));
      this.resetTitleTimes();
   }

   public void resetTitleTimes() {
      this.titleFadeInTime = 10;
      this.titleStayTime = 70;
      this.titleFadeOutTime = 20;
   }

   public void render(PoseStack var1, float var2) {
      this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
      this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
      Font â˜ƒ = this.getFont();
      RenderSystem.enableBlend();
      if (Minecraft.useFancyGraphics()) {
         this.renderVignette(this.minecraft.getCameraEntity());
      } else {
         RenderSystem.enableDepthTest();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.defaultBlendFunc();
      }

      float â˜ƒ = this.minecraft.getDeltaFrameTime();
      this.scopeScale = Mth.lerp(0.5F * â˜ƒ, this.scopeScale, 1.125F);
      if (this.minecraft.options.getCameraType().isFirstPerson()) {
         if (this.minecraft.player.isScoping()) {
            this.renderSpyglassOverlay(this.scopeScale);
         } else {
            this.scopeScale = 0.5F;
            ItemStack â˜ƒx = this.minecraft.player.getInventory().getArmor(3);
            if (â˜ƒx.is(Blocks.CARVED_PUMPKIN.asItem())) {
               this.renderTextureOverlay(PUMPKIN_BLUR_LOCATION, 1.0F);
            }
         }
      }

      if (this.minecraft.player.getTicksFrozen() > 0) {
         this.renderTextureOverlay(POWDER_SNOW_OUTLINE_LOCATION, this.minecraft.player.getPercentFrozen());
      }

      float â˜ƒ = Mth.lerp(â˜ƒ, this.minecraft.player.oPortalTime, this.minecraft.player.portalTime);
      if (â˜ƒ > 0.0F && !this.minecraft.player.hasEffect(MobEffects.CONFUSION)) {
         this.renderPortalOverlay(â˜ƒ);
      }

      if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
         this.spectatorGui.renderHotbar(â˜ƒ, â˜ƒ);
      } else if (!this.minecraft.options.hideGui) {
         this.renderHotbar(â˜ƒ, â˜ƒ);
      }

      if (!this.minecraft.options.hideGui) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
         RenderSystem.enableBlend();
         this.renderCrosshair(â˜ƒ);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.defaultBlendFunc();
         this.minecraft.getProfiler().push("bossHealth");
         this.bossOverlay.render(â˜ƒ);
         this.minecraft.getProfiler().pop();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
         if (this.minecraft.gameMode.canHurtPlayer()) {
            this.renderPlayerHealth(â˜ƒ);
         }

         this.renderVehicleHealth(â˜ƒ);
         RenderSystem.disableBlend();
         int â˜ƒ = this.screenWidth / 2 - 91;
         if (this.minecraft.player.isRidingJumpable()) {
            this.renderJumpMeter(â˜ƒ, â˜ƒ);
         } else if (this.minecraft.gameMode.hasExperience()) {
            this.renderExperienceBar(â˜ƒ, â˜ƒ);
         }

         if (this.minecraft.options.heldItemTooltips && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            this.renderSelectedItemName(â˜ƒ);
         } else if (this.minecraft.player.isSpectator()) {
            this.spectatorGui.renderTooltip(â˜ƒ);
         }
      }

      if (this.minecraft.player.getSleepTimer() > 0) {
         this.minecraft.getProfiler().push("sleep");
         RenderSystem.disableDepthTest();
         float â˜ƒ = (float)this.minecraft.player.getSleepTimer();
         float â˜ƒx = â˜ƒ / 100.0F;
         if (â˜ƒx > 1.0F) {
            â˜ƒx = 1.0F - (â˜ƒ - 100.0F) / 10.0F;
         }

         int â˜ƒ = (int)(220.0F * â˜ƒx) << 24 | 1052704;
         fill(â˜ƒ, 0, 0, this.screenWidth, this.screenHeight, â˜ƒ);
         RenderSystem.enableDepthTest();
         this.minecraft.getProfiler().pop();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.minecraft.isDemo()) {
         this.renderDemoOverlay(â˜ƒ);
      }

      this.renderEffects(â˜ƒ);
      if (this.minecraft.options.renderDebug) {
         this.debugScreen.render(â˜ƒ);
      }

      if (!this.minecraft.options.hideGui) {
         if (this.overlayMessageString != null && this.overlayMessageTime > 0) {
            this.minecraft.getProfiler().push("overlayMessage");
            float â˜ƒ = (float)this.overlayMessageTime - â˜ƒ;
            int â˜ƒx = (int)(â˜ƒ * 255.0F / 20.0F);
            if (â˜ƒx > 255) {
               â˜ƒx = 255;
            }

            if (â˜ƒx > 8) {
               â˜ƒ.pushPose();
               â˜ƒ.translate((double)(this.screenWidth / 2), (double)(this.screenHeight - 68), 0.0);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               int â˜ƒ = 16777215;
               if (this.animateOverlayMessageColor) {
                  â˜ƒ = Mth.hsvToRgb(â˜ƒ / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int â˜ƒ = â˜ƒx << 24 & 0xFF000000;
               int â˜ƒx = â˜ƒ.width(this.overlayMessageString);
               this.drawBackdrop(â˜ƒ, â˜ƒ, -4, â˜ƒx, 16777215 | â˜ƒ);
               â˜ƒ.draw(â˜ƒ, this.overlayMessageString, (float)(-â˜ƒx / 2), -4.0F, â˜ƒ | â˜ƒ);
               RenderSystem.disableBlend();
               â˜ƒ.popPose();
            }

            this.minecraft.getProfiler().pop();
         }

         if (this.title != null && this.titleTime > 0) {
            this.minecraft.getProfiler().push("titleAndSubtitle");
            float â˜ƒ = (float)this.titleTime - â˜ƒ;
            int â˜ƒx = 255;
            if (this.titleTime > this.titleFadeOutTime + this.titleStayTime) {
               float â˜ƒxx = (float)(this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime) - â˜ƒ;
               â˜ƒx = (int)(â˜ƒxx * 255.0F / (float)this.titleFadeInTime);
            }

            if (this.titleTime <= this.titleFadeOutTime) {
               â˜ƒx = (int)(â˜ƒ * 255.0F / (float)this.titleFadeOutTime);
            }

            â˜ƒx = Mth.clamp(â˜ƒx, 0, 255);
            if (â˜ƒx > 8) {
               â˜ƒ.pushPose();
               â˜ƒ.translate((double)(this.screenWidth / 2), (double)(this.screenHeight / 2), 0.0);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               â˜ƒ.pushPose();
               â˜ƒ.scale(4.0F, 4.0F, 4.0F);
               int â˜ƒ = â˜ƒx << 24 & 0xFF000000;
               int â˜ƒx = â˜ƒ.width(this.title);
               this.drawBackdrop(â˜ƒ, â˜ƒ, -10, â˜ƒx, 16777215 | â˜ƒ);
               â˜ƒ.drawShadow(â˜ƒ, this.title, (float)(-â˜ƒx / 2), -10.0F, 16777215 | â˜ƒ);
               â˜ƒ.popPose();
               if (this.subtitle != null) {
                  â˜ƒ.pushPose();
                  â˜ƒ.scale(2.0F, 2.0F, 2.0F);
                  int â˜ƒxx = â˜ƒ.width(this.subtitle);
                  this.drawBackdrop(â˜ƒ, â˜ƒ, 5, â˜ƒxx, 16777215 | â˜ƒ);
                  â˜ƒ.drawShadow(â˜ƒ, this.subtitle, (float)(-â˜ƒxx / 2), 5.0F, 16777215 | â˜ƒ);
                  â˜ƒ.popPose();
               }

               RenderSystem.disableBlend();
               â˜ƒ.popPose();
            }

            this.minecraft.getProfiler().pop();
         }

         this.subtitleOverlay.render(â˜ƒ);
         Scoreboard â˜ƒ = this.minecraft.level.getScoreboard();
         Objective â˜ƒx = null;
         PlayerTeam â˜ƒxx = â˜ƒ.getPlayersTeam(this.minecraft.player.getScoreboardName());
         if (â˜ƒxx != null) {
            int â˜ƒxxx = â˜ƒxx.getColor().getId();
            if (â˜ƒxxx >= 0) {
               â˜ƒx = â˜ƒ.getDisplayObjective(3 + â˜ƒxxx);
            }
         }

         Objective â˜ƒ = â˜ƒx != null ? â˜ƒx : â˜ƒ.getDisplayObjective(1);
         if (â˜ƒ != null) {
            this.displayScoreboardSidebar(â˜ƒ, â˜ƒ);
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, (double)(this.screenHeight - 48), 0.0);
         this.minecraft.getProfiler().push("chat");
         this.chat.render(â˜ƒ, this.tickCount);
         this.minecraft.getProfiler().pop();
         â˜ƒ.popPose();
         â˜ƒ = â˜ƒ.getDisplayObjective(0);
         if (!this.minecraft.options.keyPlayerList.isDown()
            || this.minecraft.isLocalServer() && this.minecraft.player.connection.getOnlinePlayers().size() <= 1 && â˜ƒ == null) {
            this.tabList.setVisible(false);
         } else {
            this.tabList.setVisible(true);
            this.tabList.render(â˜ƒ, this.screenWidth, â˜ƒ, â˜ƒ);
         }
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void drawBackdrop(PoseStack var1, Font var2, int var3, int var4, int var5) {
      int â˜ƒ = this.minecraft.options.getBackgroundColor(0.0F);
      if (â˜ƒ != 0) {
         int â˜ƒx = -â˜ƒ / 2;
         fill(â˜ƒ, â˜ƒx - 2, â˜ƒ - 2, â˜ƒx + â˜ƒ + 2, â˜ƒ + 9 + 2, FastColor.ARGB32.multiply(â˜ƒ, â˜ƒ));
      }
   }

   private void renderCrosshair(PoseStack var1) {
      Options â˜ƒ = this.minecraft.options;
      if (â˜ƒ.getCameraType().isFirstPerson()) {
         if (this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR || this.canRenderCrosshairForSpectator(this.minecraft.hitResult)) {
            if (â˜ƒ.renderDebug && !â˜ƒ.hideGui && !this.minecraft.player.isReducedDebugInfo() && !â˜ƒ.reducedDebugInfo) {
               Camera â˜ƒx = this.minecraft.gameRenderer.getMainCamera();
               PoseStack â˜ƒxx = RenderSystem.getModelViewStack();
               â˜ƒxx.pushPose();
               â˜ƒxx.translate((double)(this.screenWidth / 2), (double)(this.screenHeight / 2), (double)this.getBlitOffset());
               â˜ƒxx.mulPose(Vector3f.XN.rotationDegrees(â˜ƒx.getXRot()));
               â˜ƒxx.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx.getYRot()));
               â˜ƒxx.scale(-1.0F, -1.0F, -1.0F);
               RenderSystem.applyModelViewMatrix();
               RenderSystem.renderCrosshair(10);
               â˜ƒxx.popPose();
               RenderSystem.applyModelViewMatrix();
            } else {
               RenderSystem.blendFuncSeparate(
                  GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                  GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                  GlStateManager.SourceFactor.ONE,
                  GlStateManager.DestFactor.ZERO
               );
               int â˜ƒx = 15;
               this.blit(â˜ƒ, (this.screenWidth - 15) / 2, (this.screenHeight - 15) / 2, 0, 0, 15, 15);
               if (this.minecraft.options.attackIndicator == AttackIndicatorStatus.CROSSHAIR) {
                  float â˜ƒxx = this.minecraft.player.getAttackStrengthScale(0.0F);
                  boolean â˜ƒxxx = false;
                  if (this.minecraft.crosshairPickEntity != null && this.minecraft.crosshairPickEntity instanceof LivingEntity && â˜ƒxx >= 1.0F) {
                     â˜ƒxxx = this.minecraft.player.getCurrentItemAttackStrengthDelay() > 5.0F;
                     â˜ƒxxx &= this.minecraft.crosshairPickEntity.isAlive();
                  }

                  int â˜ƒxx = this.screenHeight / 2 - 7 + 16;
                  int â˜ƒxxx = this.screenWidth / 2 - 8;
                  if (â˜ƒxxx) {
                     this.blit(â˜ƒ, â˜ƒxxx, â˜ƒxx, 68, 94, 16, 16);
                  } else if (â˜ƒxx < 1.0F) {
                     int â˜ƒxx = (int)(â˜ƒxx * 17.0F);
                     this.blit(â˜ƒ, â˜ƒxxx, â˜ƒxx, 36, 94, 16, 4);
                     this.blit(â˜ƒ, â˜ƒxxx, â˜ƒxx, 52, 94, â˜ƒxx, 4);
                  }
               }
            }
         }
      }
   }

   private boolean canRenderCrosshairForSpectator(HitResult var1) {
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ.getType() == HitResult.Type.ENTITY) {
         return ((EntityHitResult)â˜ƒ).getEntity() instanceof MenuProvider;
      } else if (â˜ƒ.getType() == HitResult.Type.BLOCK) {
         BlockPos â˜ƒ = ((BlockHitResult)â˜ƒ).getBlockPos();
         Level â˜ƒx = this.minecraft.level;
         return â˜ƒx.getBlockState(â˜ƒ).getMenuProvider(â˜ƒx, â˜ƒ) != null;
      } else {
         return false;
      }
   }

   protected void renderEffects(PoseStack var1) {
      Collection<MobEffectInstance> â˜ƒ = this.minecraft.player.getActiveEffects();
      if (!â˜ƒ.isEmpty()) {
         RenderSystem.enableBlend();
         int â˜ƒx = 0;
         int â˜ƒxx = 0;
         MobEffectTextureManager â˜ƒxxx = this.minecraft.getMobEffectTextures();
         List<Runnable> â˜ƒxxxx = Lists.newArrayListWithExpectedSize(â˜ƒ.size());
         RenderSystem.setShaderTexture(0, AbstractContainerScreen.INVENTORY_LOCATION);

         for(MobEffectInstance â˜ƒxxxxx : Ordering.natural().reverse().sortedCopy(â˜ƒ)) {
            MobEffect â˜ƒxxxxxx = â˜ƒxxxxx.getEffect();
            if (â˜ƒxxxxx.showIcon()) {
               int â˜ƒxxxxxxx = this.screenWidth;
               int â˜ƒxxxxxxxx = 1;
               if (this.minecraft.isDemo()) {
                  â˜ƒxxxxxxxx += 15;
               }

               if (â˜ƒxxxxxx.isBeneficial()) {
                  ++â˜ƒx;
                  â˜ƒxxxxxxx -= 25 * â˜ƒx;
               } else {
                  ++â˜ƒxx;
                  â˜ƒxxxxxxx -= 25 * â˜ƒxx;
                  â˜ƒxxxxxxxx += 26;
               }

               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               float â˜ƒxxxxxxx = 1.0F;
               if (â˜ƒxxxxx.isAmbient()) {
                  this.blit(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 165, 166, 24, 24);
               } else {
                  this.blit(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 141, 166, 24, 24);
                  if (â˜ƒxxxxx.getDuration() <= 200) {
                     int â˜ƒxxxxxxx = 10 - â˜ƒxxxxx.getDuration() / 20;
                     â˜ƒxxxxxxx = Mth.clamp((float)â˜ƒxxxxx.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + Mth.cos((float)â˜ƒxxxxx.getDuration() * (float) Math.PI / 5.0F) * Mth.clamp((float)â˜ƒxxxxxxx / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               TextureAtlasSprite â˜ƒxxxxxxx = â˜ƒxxx.get(â˜ƒxxxxxx);
               int â˜ƒxxxxxxxx = â˜ƒxxxxxxx;
               int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx;
               float â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx;
               â˜ƒxxxx.add((Runnable)() -> {
                  RenderSystem.setShaderTexture(0, â˜ƒ.atlas().location());
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
                  blit(â˜ƒ, â˜ƒ + 3, â˜ƒ + 3, this.getBlitOffset(), 18, 18, â˜ƒ);
               });
            }
         }

         â˜ƒxxxx.forEach(Runnable::run);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void renderHotbar(float var1, PoseStack var2) {
      Player â˜ƒ = this.getCameraPlayer();
      if (â˜ƒ != null) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
         ItemStack â˜ƒx = â˜ƒ.getOffhandItem();
         HumanoidArm â˜ƒxx = â˜ƒ.getMainArm().getOpposite();
         int â˜ƒxxx = this.screenWidth / 2;
         int â˜ƒxxxx = this.getBlitOffset();
         int â˜ƒxxxxx = 182;
         int â˜ƒxxxxxx = 91;
         this.setBlitOffset(-90);
         this.blit(â˜ƒ, â˜ƒxxx - 91, this.screenHeight - 22, 0, 0, 182, 22);
         this.blit(â˜ƒ, â˜ƒxxx - 91 - 1 + â˜ƒ.getInventory().selected * 20, this.screenHeight - 22 - 1, 0, 22, 24, 22);
         if (!â˜ƒx.isEmpty()) {
            if (â˜ƒxx == HumanoidArm.LEFT) {
               this.blit(â˜ƒ, â˜ƒxxx - 91 - 29, this.screenHeight - 23, 24, 22, 29, 24);
            } else {
               this.blit(â˜ƒ, â˜ƒxxx + 91, this.screenHeight - 23, 53, 22, 29, 24);
            }
         }

         this.setBlitOffset(â˜ƒxxxx);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         int â˜ƒx = 1;

         for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
            int â˜ƒxxx = â˜ƒxxx - 90 + â˜ƒxx * 20 + 2;
            int â˜ƒxxxx = this.screenHeight - 16 - 3;
            this.renderSlot(â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ.getInventory().items.get(â˜ƒxx), â˜ƒx++);
         }

         if (!â˜ƒx.isEmpty()) {
            int â˜ƒxx = this.screenHeight - 16 - 3;
            if (â˜ƒxx == HumanoidArm.LEFT) {
               this.renderSlot(â˜ƒxxx - 91 - 26, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒx++);
            } else {
               this.renderSlot(â˜ƒxxx + 91 + 10, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒx++);
            }
         }

         if (this.minecraft.options.attackIndicator == AttackIndicatorStatus.HOTBAR) {
            float â˜ƒxx = this.minecraft.player.getAttackStrengthScale(0.0F);
            if (â˜ƒxx < 1.0F) {
               int â˜ƒxxx = this.screenHeight - 20;
               int â˜ƒxxxx = â˜ƒxxx + 91 + 6;
               if (â˜ƒxx == HumanoidArm.RIGHT) {
                  â˜ƒxxxx = â˜ƒxxx - 91 - 22;
               }

               RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
               int â˜ƒxxx = (int)(â˜ƒxx * 19.0F);
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒxxx, 0, 94, 18, 18);
               this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒxxx + 18 - â˜ƒxxx, 18, 112 - â˜ƒxxx, 18, â˜ƒxxx);
            }
         }

         RenderSystem.disableBlend();
      }
   }

   public void renderJumpMeter(PoseStack var1, int var2) {
      this.minecraft.getProfiler().push("jumpBar");
      RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
      float â˜ƒ = this.minecraft.player.getJumpRidingScale();
      int â˜ƒx = 182;
      int â˜ƒxx = (int)(â˜ƒ * 183.0F);
      int â˜ƒxxx = this.screenHeight - 32 + 3;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒxxx, 0, 84, 182, 5);
      if (â˜ƒxx > 0) {
         this.blit(â˜ƒ, â˜ƒ, â˜ƒxxx, 0, 89, â˜ƒxx, 5);
      }

      this.minecraft.getProfiler().pop();
   }

   public void renderExperienceBar(PoseStack var1, int var2) {
      this.minecraft.getProfiler().push("expBar");
      RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
      int â˜ƒ = this.minecraft.player.getXpNeededForNextLevel();
      if (â˜ƒ > 0) {
         int â˜ƒx = 182;
         int â˜ƒxx = (int)(this.minecraft.player.experienceProgress * 183.0F);
         int â˜ƒxxx = this.screenHeight - 32 + 3;
         this.blit(â˜ƒ, â˜ƒ, â˜ƒxxx, 0, 64, 182, 5);
         if (â˜ƒxx > 0) {
            this.blit(â˜ƒ, â˜ƒ, â˜ƒxxx, 0, 69, â˜ƒxx, 5);
         }
      }

      this.minecraft.getProfiler().pop();
      if (this.minecraft.player.experienceLevel > 0) {
         this.minecraft.getProfiler().push("expLevel");
         String â˜ƒ = this.minecraft.player.experienceLevel + "";
         int â˜ƒx = (this.screenWidth - this.getFont().width(â˜ƒ)) / 2;
         int â˜ƒxx = this.screenHeight - 31 - 4;
         this.getFont().draw(â˜ƒ, â˜ƒ, (float)(â˜ƒx + 1), (float)â˜ƒxx, 0);
         this.getFont().draw(â˜ƒ, â˜ƒ, (float)(â˜ƒx - 1), (float)â˜ƒxx, 0);
         this.getFont().draw(â˜ƒ, â˜ƒ, (float)â˜ƒx, (float)(â˜ƒxx + 1), 0);
         this.getFont().draw(â˜ƒ, â˜ƒ, (float)â˜ƒx, (float)(â˜ƒxx - 1), 0);
         this.getFont().draw(â˜ƒ, â˜ƒ, (float)â˜ƒx, (float)â˜ƒxx, 8453920);
         this.minecraft.getProfiler().pop();
      }
   }

   public void renderSelectedItemName(PoseStack var1) {
      this.minecraft.getProfiler().push("selectedItemName");
      if (this.toolHighlightTimer > 0 && !this.lastToolHighlight.isEmpty()) {
         MutableComponent â˜ƒ = new TextComponent("").append(this.lastToolHighlight.getHoverName()).withStyle(this.lastToolHighlight.getRarity().color);
         if (this.lastToolHighlight.hasCustomHoverName()) {
            â˜ƒ.withStyle(ChatFormatting.ITALIC);
         }

         int â˜ƒ = this.getFont().width(â˜ƒ);
         int â˜ƒx = (this.screenWidth - â˜ƒ) / 2;
         int â˜ƒxx = this.screenHeight - 59;
         if (!this.minecraft.gameMode.canHurtPlayer()) {
            â˜ƒxx += 14;
         }

         int â˜ƒ = (int)((float)this.toolHighlightTimer * 256.0F / 10.0F);
         if (â˜ƒ > 255) {
            â˜ƒ = 255;
         }

         if (â˜ƒ > 0) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            fill(â˜ƒ, â˜ƒx - 2, â˜ƒxx - 2, â˜ƒx + â˜ƒ + 2, â˜ƒxx + 9 + 2, this.minecraft.options.getBackgroundColor(0));
            this.getFont().drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒx, (float)â˜ƒxx, 16777215 + (â˜ƒ << 24));
            RenderSystem.disableBlend();
         }
      }

      this.minecraft.getProfiler().pop();
   }

   public void renderDemoOverlay(PoseStack var1) {
      this.minecraft.getProfiler().push("demo");
      Component â˜ƒ;
      if (this.minecraft.level.getGameTime() >= 120500L) {
         â˜ƒ = DEMO_EXPIRED_TEXT;
      } else {
         â˜ƒ = new TranslatableComponent("demo.remainingTime", StringUtil.formatTickDuration((int)(120500L - this.minecraft.level.getGameTime())));
      }

      int â˜ƒ = this.getFont().width(â˜ƒ);
      this.getFont().drawShadow(â˜ƒ, â˜ƒ, (float)(this.screenWidth - â˜ƒ - 10), 5.0F, 16777215);
      this.minecraft.getProfiler().pop();
   }

   private void displayScoreboardSidebar(PoseStack var1, Objective var2) {
      Scoreboard â˜ƒ = â˜ƒ.getScoreboard();
      Collection<Score> â˜ƒx = â˜ƒ.getPlayerScores(â˜ƒ);
      List<Score> â˜ƒxx = (List)â˜ƒx.stream().filter(var0 -> var0.getOwner() != null && !var0.getOwner().startsWith("#")).collect(Collectors.toList());
      if (â˜ƒxx.size() > 15) {
         â˜ƒx = Lists.<Score>newArrayList(Iterables.skip(â˜ƒxx, â˜ƒx.size() - 15));
      } else {
         â˜ƒx = â˜ƒxx;
      }

      List<Pair<Score, Component>> â˜ƒ = Lists.<Pair<Score, Component>>newArrayListWithCapacity(â˜ƒx.size());
      Component â˜ƒx = â˜ƒ.getDisplayName();
      int â˜ƒxx = this.getFont().width(â˜ƒx);
      int â˜ƒxxx = â˜ƒxx;
      int â˜ƒxxxx = this.getFont().width(": ");

      for(Score â˜ƒxxxxx : â˜ƒx) {
         PlayerTeam â˜ƒxxxxxx = â˜ƒ.getPlayersTeam(â˜ƒxxxxx.getOwner());
         Component â˜ƒxxxxxxx = PlayerTeam.formatNameForTeam(â˜ƒxxxxxx, new TextComponent(â˜ƒxxxxx.getOwner()));
         â˜ƒ.add(Pair.of(â˜ƒxxxxx, â˜ƒxxxxxxx));
         â˜ƒxxx = Math.max(â˜ƒxxx, this.getFont().width(â˜ƒxxxxxxx) + â˜ƒxxxx + this.getFont().width(Integer.toString(â˜ƒxxxxx.getScore())));
      }

      int â˜ƒxxxxx = â˜ƒx.size() * 9;
      int â˜ƒxxxxxx = this.screenHeight / 2 + â˜ƒxxxxx / 3;
      int â˜ƒxxxxxxx = 3;
      int â˜ƒxxxxxxxx = this.screenWidth - â˜ƒxxx - 3;
      int â˜ƒxxxxxxxxx = 0;
      int â˜ƒxxxxxxxxxx = this.minecraft.options.getBackgroundColor(0.3F);
      int â˜ƒxxxxxxxxxxx = this.minecraft.options.getBackgroundColor(0.4F);

      for(Pair<Score, Component> â˜ƒxxxxxxxxxxxx : â˜ƒ) {
         ++â˜ƒxxxxxxxxx;
         Score â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getFirst();
         Component â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getSecond();
         String â˜ƒxxxxxxxxxxxxxxx = "" + ChatFormatting.RED + â˜ƒxxxxxxxxxxxxx.getScore();
         int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxx - â˜ƒxxxxxxxxx * 9;
         int â˜ƒxxxxxxxxxxxxxxxxx = this.screenWidth - 3 + 2;
         fill(â˜ƒ, â˜ƒxxxxxxxx - 2, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx + 9, â˜ƒxxxxxxxxxx);
         this.getFont().draw(â˜ƒ, â˜ƒxxxxxxxxxxxxxx, (float)â˜ƒxxxxxxxx, (float)â˜ƒxxxxxxxxxxxxxxxx, -1);
         this.getFont().draw(â˜ƒ, â˜ƒxxxxxxxxxxxxxxx, (float)(â˜ƒxxxxxxxxxxxxxxxxx - this.getFont().width(â˜ƒxxxxxxxxxxxxxxx)), (float)â˜ƒxxxxxxxxxxxxxxxx, -1);
         if (â˜ƒxxxxxxxxx == â˜ƒx.size()) {
            fill(â˜ƒ, â˜ƒxxxxxxxx - 2, â˜ƒxxxxxxxxxxxxxxxx - 9 - 1, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxx);
            fill(â˜ƒ, â˜ƒxxxxxxxx - 2, â˜ƒxxxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx);
            this.getFont().draw(â˜ƒ, â˜ƒx, (float)(â˜ƒxxxxxxxx + â˜ƒxxx / 2 - â˜ƒxx / 2), (float)(â˜ƒxxxxxxxxxxxxxxxx - 9), -1);
         }
      }
   }

   private Player getCameraPlayer() {
      return !(this.minecraft.getCameraEntity() instanceof Player) ? null : (Player)this.minecraft.getCameraEntity();
   }

   private LivingEntity getPlayerVehicleWithHealth() {
      Player â˜ƒ = this.getCameraPlayer();
      if (â˜ƒ != null) {
         Entity â˜ƒx = â˜ƒ.getVehicle();
         if (â˜ƒx == null) {
            return null;
         }

         if (â˜ƒx instanceof LivingEntity) {
            return (LivingEntity)â˜ƒx;
         }
      }

      return null;
   }

   private int getVehicleMaxHearts(LivingEntity var1) {
      if (â˜ƒ != null && â˜ƒ.showVehicleHealth()) {
         float â˜ƒ = â˜ƒ.getMaxHealth();
         int â˜ƒx = (int)(â˜ƒ + 0.5F) / 2;
         if (â˜ƒx > 30) {
            â˜ƒx = 30;
         }

         return â˜ƒx;
      } else {
         return 0;
      }
   }

   private int getVisibleVehicleHeartRows(int var1) {
      return (int)Math.ceil((double)â˜ƒ / 10.0);
   }

   private void renderPlayerHealth(PoseStack var1) {
      Player â˜ƒ = this.getCameraPlayer();
      if (â˜ƒ != null) {
         int â˜ƒx = Mth.ceil(â˜ƒ.getHealth());
         boolean â˜ƒxx = this.healthBlinkTime > (long)this.tickCount && (this.healthBlinkTime - (long)this.tickCount) / 3L % 2L == 1L;
         long â˜ƒxxx = Util.getMillis();
         if (â˜ƒx < this.lastHealth && â˜ƒ.invulnerableTime > 0) {
            this.lastHealthTime = â˜ƒxxx;
            this.healthBlinkTime = (long)(this.tickCount + 20);
         } else if (â˜ƒx > this.lastHealth && â˜ƒ.invulnerableTime > 0) {
            this.lastHealthTime = â˜ƒxxx;
            this.healthBlinkTime = (long)(this.tickCount + 10);
         }

         if (â˜ƒxxx - this.lastHealthTime > 1000L) {
            this.lastHealth = â˜ƒx;
            this.displayHealth = â˜ƒx;
            this.lastHealthTime = â˜ƒxxx;
         }

         this.lastHealth = â˜ƒx;
         int â˜ƒx = this.displayHealth;
         this.random.setSeed((long)(this.tickCount * 312871));
         FoodData â˜ƒxx = â˜ƒ.getFoodData();
         int â˜ƒxxx = â˜ƒxx.getFoodLevel();
         int â˜ƒxxxx = this.screenWidth / 2 - 91;
         int â˜ƒxxxxx = this.screenWidth / 2 + 91;
         int â˜ƒxxxxxx = this.screenHeight - 39;
         float â˜ƒxxxxxxx = Math.max((float)â˜ƒ.getAttributeValue(Attributes.MAX_HEALTH), (float)Math.max(â˜ƒx, â˜ƒx));
         int â˜ƒxxxxxxxx = Mth.ceil(â˜ƒ.getAbsorptionAmount());
         int â˜ƒxxxxxxxxx = Mth.ceil((â˜ƒxxxxxxx + (float)â˜ƒxxxxxxxx) / 2.0F / 10.0F);
         int â˜ƒxxxxxxxxxx = Math.max(10 - (â˜ƒxxxxxxxxx - 2), 3);
         int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx - (â˜ƒxxxxxxxxx - 1) * â˜ƒxxxxxxxxxx - 10;
         int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx - 10;
         int â˜ƒxxxxxxxxxxxxx = â˜ƒ.getArmorValue();
         int â˜ƒxxxxxxxxxxxxxx = -1;
         if (â˜ƒ.hasEffect(MobEffects.REGENERATION)) {
            â˜ƒxxxxxxxxxxxxxx = this.tickCount % Mth.ceil(â˜ƒxxxxxxx + 5.0F);
         }

         this.minecraft.getProfiler().push("armor");

         for(int â˜ƒx = 0; â˜ƒx < 10; ++â˜ƒx) {
            if (â˜ƒxxxxxxxxxxxxx > 0) {
               int â˜ƒxx = â˜ƒxxxx + â˜ƒx * 8;
               if (â˜ƒx * 2 + 1 < â˜ƒxxxxxxxxxxxxx) {
                  this.blit(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxxxxxx, 34, 9, 9, 9);
               }

               if (â˜ƒx * 2 + 1 == â˜ƒxxxxxxxxxxxxx) {
                  this.blit(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxxxxxx, 25, 9, 9, 9);
               }

               if (â˜ƒx * 2 + 1 > â˜ƒxxxxxxxxxxxxx) {
                  this.blit(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxxxxxx, 16, 9, 9, 9);
               }
            }
         }

         this.minecraft.getProfiler().popPush("health");
         this.renderHearts(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒx, â˜ƒxxxxxxxx, â˜ƒxx);
         LivingEntity â˜ƒx = this.getPlayerVehicleWithHealth();
         int â˜ƒxx = this.getVehicleMaxHearts(â˜ƒx);
         if (â˜ƒxx == 0) {
            this.minecraft.getProfiler().popPush("food");

            for(int â˜ƒxxx = 0; â˜ƒxxx < 10; ++â˜ƒxxx) {
               int â˜ƒxxxx = â˜ƒxxxxxx;
               int â˜ƒxxxxx = 16;
               int â˜ƒxxxxxx = 0;
               if (â˜ƒ.hasEffect(MobEffects.HUNGER)) {
                  â˜ƒxxxxx += 36;
                  â˜ƒxxxxxx = 13;
               }

               if (â˜ƒ.getFoodData().getSaturationLevel() <= 0.0F && this.tickCount % (â˜ƒxxx * 3 + 1) == 0) {
                  â˜ƒxxxx = â˜ƒxxxxxx + (this.random.nextInt(3) - 1);
               }

               int â˜ƒxxxx = â˜ƒxxxxx - â˜ƒxxx * 8 - 9;
               this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒxxxx, 16 + â˜ƒxxxxxx * 9, 27, 9, 9);
               if (â˜ƒxxx * 2 + 1 < â˜ƒxxx) {
                  this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒxxxx, â˜ƒxxxxx + 36, 27, 9, 9);
               }

               if (â˜ƒxxx * 2 + 1 == â˜ƒxxx) {
                  this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒxxxx, â˜ƒxxxxx + 45, 27, 9, 9);
               }
            }

            â˜ƒxxxxxxxxxxxx -= 10;
         }

         this.minecraft.getProfiler().popPush("air");
         int â˜ƒx = â˜ƒ.getMaxAirSupply();
         int â˜ƒxx = Math.min(â˜ƒ.getAirSupply(), â˜ƒx);
         if (â˜ƒ.isEyeInFluid(FluidTags.WATER) || â˜ƒxx < â˜ƒx) {
            int â˜ƒxxx = this.getVisibleVehicleHeartRows(â˜ƒxx) - 1;
            â˜ƒxxxxxxxxxxxx -= â˜ƒxxx * 10;
            int â˜ƒxxxx = Mth.ceil((double)(â˜ƒxx - 2) * 10.0 / (double)â˜ƒx);
            int â˜ƒxxxxx = Mth.ceil((double)â˜ƒxx * 10.0 / (double)â˜ƒx) - â˜ƒxxxx;

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxx + â˜ƒxxxxx; ++â˜ƒxxxxxx) {
               if (â˜ƒxxxxxx < â˜ƒxxxx) {
                  this.blit(â˜ƒ, â˜ƒxxxxx - â˜ƒxxxxxx * 8 - 9, â˜ƒxxxxxxxxxxxx, 16, 18, 9, 9);
               } else {
                  this.blit(â˜ƒ, â˜ƒxxxxx - â˜ƒxxxxxx * 8 - 9, â˜ƒxxxxxxxxxxxx, 25, 18, 9, 9);
               }
            }
         }

         this.minecraft.getProfiler().pop();
      }
   }

   private void renderHearts(PoseStack var1, Player var2, int var3, int var4, int var5, int var6, float var7, int var8, int var9, int var10, boolean var11) {
      Gui.HeartType â˜ƒ = Gui.HeartType.forPlayer(â˜ƒ);
      int â˜ƒx = 9 * (â˜ƒ.level.getLevelData().isHardcore() ? 5 : 0);
      int â˜ƒxx = Mth.ceil((double)â˜ƒ / 2.0);
      int â˜ƒxxx = Mth.ceil((double)â˜ƒ / 2.0);
      int â˜ƒxxxx = â˜ƒxx * 2;

      for(int â˜ƒxxxxx = â˜ƒxx + â˜ƒxxx - 1; â˜ƒxxxxx >= 0; --â˜ƒxxxxx) {
         int â˜ƒxxxxxx = â˜ƒxxxxx / 10;
         int â˜ƒxxxxxxx = â˜ƒxxxxx % 10;
         int â˜ƒxxxxxxxx = â˜ƒ + â˜ƒxxxxxxx * 8;
         int â˜ƒxxxxxxxxx = â˜ƒ - â˜ƒxxxxxx * â˜ƒ;
         if (â˜ƒ + â˜ƒ <= 4) {
            â˜ƒxxxxxxxxx += this.random.nextInt(2);
         }

         if (â˜ƒxxxxx < â˜ƒxx && â˜ƒxxxxx == â˜ƒ) {
            â˜ƒxxxxxxxxx -= 2;
         }

         this.renderHeart(â˜ƒ, Gui.HeartType.CONTAINER, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒx, â˜ƒ, false);
         int â˜ƒxxxxxx = â˜ƒxxxxx * 2;
         boolean â˜ƒxxxxxxx = â˜ƒxxxxx >= â˜ƒxx;
         if (â˜ƒxxxxxxx) {
            int â˜ƒxxxxxxxx = â˜ƒxxxxxx - â˜ƒxxxx;
            if (â˜ƒxxxxxxxx < â˜ƒ) {
               boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx + 1 == â˜ƒ;
               this.renderHeart(â˜ƒ, â˜ƒ == Gui.HeartType.WITHERED ? â˜ƒ : Gui.HeartType.ABSORBING, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒx, false, â˜ƒxxxxxxxxx);
            }
         }

         if (â˜ƒ && â˜ƒxxxxxx < â˜ƒ) {
            boolean â˜ƒxxxxxx = â˜ƒxxxxxx + 1 == â˜ƒ;
            this.renderHeart(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒx, true, â˜ƒxxxxxx);
         }

         if (â˜ƒxxxxxx < â˜ƒ) {
            boolean â˜ƒxxxxxx = â˜ƒxxxxxx + 1 == â˜ƒ;
            this.renderHeart(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒx, false, â˜ƒxxxxxx);
         }
      }
   }

   private void renderHeart(PoseStack var1, Gui.HeartType var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getX(â˜ƒ, â˜ƒ), â˜ƒ, 9, 9);
   }

   private void renderVehicleHealth(PoseStack var1) {
      LivingEntity â˜ƒ = this.getPlayerVehicleWithHealth();
      if (â˜ƒ != null) {
         int â˜ƒx = this.getVehicleMaxHearts(â˜ƒ);
         if (â˜ƒx != 0) {
            int â˜ƒxx = (int)Math.ceil((double)â˜ƒ.getHealth());
            this.minecraft.getProfiler().popPush("mountHealth");
            int â˜ƒxxx = this.screenHeight - 39;
            int â˜ƒxxxx = this.screenWidth / 2 + 91;
            int â˜ƒxxxxx = â˜ƒxxx;
            int â˜ƒxxxxxx = 0;

            for(boolean â˜ƒxxxxxxx = false; â˜ƒx > 0; â˜ƒxxxxxx += 20) {
               int â˜ƒxxxxxxxx = Math.min(â˜ƒx, 10);
               â˜ƒx -= â˜ƒxxxxxxxx;

               for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxx = 52;
                  int â˜ƒxxxxxxxxxxx = 0;
                  int â˜ƒxxxxxxxxxxxx = â˜ƒxxxx - â˜ƒxxxxxxxxx * 8 - 9;
                  this.blit(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxx, 52 + â˜ƒxxxxxxxxxxx * 9, 9, 9, 9);
                  if (â˜ƒxxxxxxxxx * 2 + 1 + â˜ƒxxxxxx < â˜ƒxx) {
                     this.blit(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxx, 88, 9, 9, 9);
                  }

                  if (â˜ƒxxxxxxxxx * 2 + 1 + â˜ƒxxxxxx == â˜ƒxx) {
                     this.blit(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxx, 97, 9, 9, 9);
                  }
               }

               â˜ƒxxxxx -= 10;
            }
         }
      }
   }

   private void renderTextureOverlay(ResourceLocation var1, float var2) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
      RenderSystem.setShaderTexture(0, â˜ƒ);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒx.vertex(0.0, (double)this.screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
      â˜ƒx.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
      â˜ƒ.end();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderSpyglassOverlay(float var1) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, SPYGLASS_SCOPE_LOCATION);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      float â˜ƒxx = (float)Math.min(this.screenWidth, this.screenHeight);
      float â˜ƒxxx = Math.min((float)this.screenWidth / â˜ƒxx, (float)this.screenHeight / â˜ƒxx) * â˜ƒ;
      float â˜ƒxxxx = â˜ƒxx * â˜ƒxxx;
      float â˜ƒxxxxx = â˜ƒxx * â˜ƒxxx;
      float â˜ƒxxxxxx = ((float)this.screenWidth - â˜ƒxxxx) / 2.0F;
      float â˜ƒxxxxxxx = ((float)this.screenHeight - â˜ƒxxxxx) / 2.0F;
      float â˜ƒxxxxxxxx = â˜ƒxxxxxx + â˜ƒxxxx;
      float â˜ƒxxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxx;
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒx.vertex((double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxxxx, -90.0).uv(0.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxxxx, (double)â˜ƒxxxxxxxxx, -90.0).uv(1.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxxxx, (double)â˜ƒxxxxxxx, -90.0).uv(1.0F, 0.0F).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx, -90.0).uv(0.0F, 0.0F).endVertex();
      â˜ƒ.end();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.disableTexture();
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      â˜ƒx.vertex(0.0, (double)this.screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex(0.0, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex(0.0, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex(0.0, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex(0.0, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex(0.0, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxxxx, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)â˜ƒxxxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒx.vertex((double)â˜ƒxxxxxxxx, (double)â˜ƒxxxxxxx, -90.0).color(0, 0, 0, 255).endVertex();
      â˜ƒ.end();
      RenderSystem.enableTexture();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void updateVignetteBrightness(Entity var1) {
      if (â˜ƒ != null) {
         float â˜ƒ = Mth.clamp(1.0F - â˜ƒ.getBrightness(), 0.0F, 1.0F);
         this.vignetteBrightness = (float)((double)this.vignetteBrightness + (double)(â˜ƒ - this.vignetteBrightness) * 0.01);
      }
   }

   private void renderVignette(Entity var1) {
      WorldBorder â˜ƒ = this.minecraft.level.getWorldBorder();
      float â˜ƒx = (float)â˜ƒ.getDistanceToBorder(â˜ƒ);
      double â˜ƒxx = Math.min(â˜ƒ.getLerpSpeed() * (double)â˜ƒ.getWarningTime() * 1000.0, Math.abs(â˜ƒ.getLerpTarget() - â˜ƒ.getSize()));
      double â˜ƒxxx = Math.max((double)â˜ƒ.getWarningBlocks(), â˜ƒxx);
      if ((double)â˜ƒx < â˜ƒxxx) {
         â˜ƒx = 1.0F - (float)((double)â˜ƒx / â˜ƒxxx);
      } else {
         â˜ƒx = 0.0F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(
         GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      if (â˜ƒx > 0.0F) {
         â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
         RenderSystem.setShaderColor(0.0F, â˜ƒx, â˜ƒx, 1.0F);
      } else {
         float â˜ƒ = this.vignetteBrightness;
         â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
         RenderSystem.setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
      }

      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, VIGNETTE_LOCATION);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒx.vertex(0.0, (double)this.screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
      â˜ƒx.vertex((double)this.screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
      â˜ƒx.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
      â˜ƒ.end();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
   }

   private void renderPortalOverlay(float var1) {
      if (â˜ƒ < 1.0F) {
         â˜ƒ *= â˜ƒ;
         â˜ƒ *= â˜ƒ;
         â˜ƒ = â˜ƒ * 0.8F + 0.2F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      TextureAtlasSprite â˜ƒ = this.minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(Blocks.NETHER_PORTAL.defaultBlockState());
      float â˜ƒx = â˜ƒ.getU0();
      float â˜ƒxx = â˜ƒ.getV0();
      float â˜ƒxxx = â˜ƒ.getU1();
      float â˜ƒxxxx = â˜ƒ.getV1();
      Tesselator â˜ƒxxxxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxxxxx = â˜ƒxxxxx.getBuilder();
      â˜ƒxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒxxxxxx.vertex(0.0, (double)this.screenHeight, -90.0).uv(â˜ƒx, â˜ƒxxxx).endVertex();
      â˜ƒxxxxxx.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).uv(â˜ƒxxx, â˜ƒxxxx).endVertex();
      â˜ƒxxxxxx.vertex((double)this.screenWidth, 0.0, -90.0).uv(â˜ƒxxx, â˜ƒxx).endVertex();
      â˜ƒxxxxxx.vertex(0.0, 0.0, -90.0).uv(â˜ƒx, â˜ƒxx).endVertex();
      â˜ƒxxxxx.end();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderSlot(int var1, int var2, float var3, Player var4, ItemStack var5, int var6) {
      if (!â˜ƒ.isEmpty()) {
         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         float â˜ƒx = (float)â˜ƒ.getPopTime() - â˜ƒ;
         if (â˜ƒx > 0.0F) {
            float â˜ƒxx = 1.0F + â˜ƒx / 5.0F;
            â˜ƒ.pushPose();
            â˜ƒ.translate((double)(â˜ƒ + 8), (double)(â˜ƒ + 12), 0.0);
            â˜ƒ.scale(1.0F / â˜ƒxx, (â˜ƒxx + 1.0F) / 2.0F, 1.0F);
            â˜ƒ.translate((double)(-(â˜ƒ + 8)), (double)(-(â˜ƒ + 12)), 0.0);
            RenderSystem.applyModelViewMatrix();
         }

         this.itemRenderer.renderAndDecorateItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         if (â˜ƒx > 0.0F) {
            â˜ƒ.popPose();
            RenderSystem.applyModelViewMatrix();
         }

         this.itemRenderer.renderGuiItemDecorations(this.minecraft.font, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void tick() {
      if (this.overlayMessageTime > 0) {
         --this.overlayMessageTime;
      }

      if (this.titleTime > 0) {
         --this.titleTime;
         if (this.titleTime <= 0) {
            this.title = null;
            this.subtitle = null;
         }
      }

      ++this.tickCount;
      Entity â˜ƒ = this.minecraft.getCameraEntity();
      if (â˜ƒ != null) {
         this.updateVignetteBrightness(â˜ƒ);
      }

      if (this.minecraft.player != null) {
         ItemStack â˜ƒ = this.minecraft.player.getInventory().getSelected();
         if (â˜ƒ.isEmpty()) {
            this.toolHighlightTimer = 0;
         } else if (this.lastToolHighlight.isEmpty()
            || !â˜ƒ.is(this.lastToolHighlight.getItem())
            || !â˜ƒ.getHoverName().equals(this.lastToolHighlight.getHoverName())) {
            this.toolHighlightTimer = 40;
         } else if (this.toolHighlightTimer > 0) {
            --this.toolHighlightTimer;
         }

         this.lastToolHighlight = â˜ƒ;
      }
   }

   public void setNowPlaying(Component var1) {
      this.setOverlayMessage(new TranslatableComponent("record.nowPlaying", â˜ƒ), true);
   }

   public void setOverlayMessage(Component var1, boolean var2) {
      this.overlayMessageString = â˜ƒ;
      this.overlayMessageTime = 60;
      this.animateOverlayMessageColor = â˜ƒ;
   }

   public void setTimes(int var1, int var2, int var3) {
      if (â˜ƒ >= 0) {
         this.titleFadeInTime = â˜ƒ;
      }

      if (â˜ƒ >= 0) {
         this.titleStayTime = â˜ƒ;
      }

      if (â˜ƒ >= 0) {
         this.titleFadeOutTime = â˜ƒ;
      }

      if (this.titleTime > 0) {
         this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
      }
   }

   public void setSubtitle(Component var1) {
      this.subtitle = â˜ƒ;
   }

   public void setTitle(Component var1) {
      this.title = â˜ƒ;
      this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
   }

   public void clear() {
      this.title = null;
      this.subtitle = null;
      this.titleTime = 0;
   }

   public UUID guessChatUUID(Component var1) {
      String â˜ƒ = StringDecomposer.getPlainText(â˜ƒ);
      String â˜ƒx = StringUtils.substringBetween(â˜ƒ, "<", ">");
      return â˜ƒx == null ? Util.NIL_UUID : this.minecraft.getPlayerSocialManager().getDiscoveredUUID(â˜ƒx);
   }

   public void handleChat(ChatType var1, Component var2, UUID var3) {
      if (!this.minecraft.isBlocked(â˜ƒ)) {
         if (!this.minecraft.options.hideMatchedNames || !this.minecraft.isBlocked(this.guessChatUUID(â˜ƒ))) {
            for(ChatListener â˜ƒ : (List)this.chatListeners.get(â˜ƒ)) {
               â˜ƒ.handle(â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   public ChatComponent getChat() {
      return this.chat;
   }

   public int getGuiTicks() {
      return this.tickCount;
   }

   public Font getFont() {
      return this.minecraft.font;
   }

   public SpectatorGui getSpectatorGui() {
      return this.spectatorGui;
   }

   public PlayerTabOverlay getTabList() {
      return this.tabList;
   }

   public void onDisconnected() {
      this.tabList.reset();
      this.bossOverlay.reset();
      this.minecraft.getToasts().clear();
      this.minecraft.options.renderDebug = false;
      this.chat.clearMessages(true);
   }

   public BossHealthOverlay getBossOverlay() {
      return this.bossOverlay;
   }

   public void clearCache() {
      this.debugScreen.clearChunkCache();
   }

   static enum HeartType {
      CONTAINER(0, false),
      NORMAL(2, true),
      POISIONED(4, true),
      WITHERED(6, true),
      ABSORBING(8, false),
      FROZEN(9, false);

      private final int index;
      private final boolean canBlink;

      private HeartType(int var3, boolean var4) {
         this.index = â˜ƒ;
         this.canBlink = â˜ƒ;
      }

      public int getX(boolean var1, boolean var2) {
         int â˜ƒ;
         if (this == CONTAINER) {
            â˜ƒ = â˜ƒ ? 1 : 0;
         } else {
            int â˜ƒ = â˜ƒ ? 1 : 0;
            int â˜ƒx = this.canBlink && â˜ƒ ? 2 : 0;
            â˜ƒ = â˜ƒ + â˜ƒx;
         }

         return 16 + (this.index * 2 + â˜ƒ) * 9;
      }

      static Gui.HeartType forPlayer(Player var0) {
         Gui.HeartType â˜ƒ;
         if (â˜ƒ.hasEffect(MobEffects.POISON)) {
            â˜ƒ = POISIONED;
         } else if (â˜ƒ.hasEffect(MobEffects.WITHER)) {
            â˜ƒ = WITHERED;
         } else if (â˜ƒ.isFullyFrozen()) {
            â˜ƒ = FROZEN;
         } else {
            â˜ƒ = NORMAL;
         }

         return â˜ƒ;
      }
   }
}
