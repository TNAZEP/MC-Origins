package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RealmsWorldSlotButton extends Button {
   public static final ResourceLocation SLOT_FRAME_LOCATION = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
   public static final ResourceLocation EMPTY_SLOT_LOCATION = new ResourceLocation("realms", "textures/gui/realms/empty_frame.png");
   public static final ResourceLocation DEFAULT_WORLD_SLOT_1 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_0.png");
   public static final ResourceLocation DEFAULT_WORLD_SLOT_2 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_2.png");
   public static final ResourceLocation DEFAULT_WORLD_SLOT_3 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_3.png");
   private static final Component SLOT_ACTIVE_TOOLTIP = new TranslatableComponent("mco.configure.world.slot.tooltip.active");
   private static final Component SWITCH_TO_MINIGAME_SLOT_TOOLTIP = new TranslatableComponent("mco.configure.world.slot.tooltip.minigame");
   private static final Component SWITCH_TO_WORLD_SLOT_TOOLTIP = new TranslatableComponent("mco.configure.world.slot.tooltip");
   private final Supplier<RealmsServer> serverDataProvider;
   private final Consumer<Component> toolTipSetter;
   private final int slotIndex;
   private int animTick;
   @Nullable
   private RealmsWorldSlotButton.State state;

   public RealmsWorldSlotButton(int var1, int var2, int var3, int var4, Supplier<RealmsServer> var5, Consumer<Component> var6, int var7, Button.OnPress var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, TextComponent.EMPTY, â˜ƒ);
      this.serverDataProvider = â˜ƒ;
      this.slotIndex = â˜ƒ;
      this.toolTipSetter = â˜ƒ;
   }

   @Nullable
   public RealmsWorldSlotButton.State getState() {
      return this.state;
   }

   public void tick() {
      ++this.animTick;
      RealmsServer â˜ƒ = (RealmsServer)this.serverDataProvider.get();
      if (â˜ƒ != null) {
         RealmsWorldOptions â˜ƒxxxxxx = (RealmsWorldOptions)â˜ƒ.slots.get(this.slotIndex);
         boolean â˜ƒxxxxxxx = this.slotIndex == 4;
         boolean â˜ƒx;
         String â˜ƒxx;
         long â˜ƒxxx;
         String â˜ƒxxxx;
         boolean â˜ƒxxxxx;
         if (â˜ƒxxxxxxx) {
            â˜ƒx = â˜ƒ.worldType == RealmsServer.WorldType.MINIGAME;
            â˜ƒxx = "Minigame";
            â˜ƒxxx = (long)â˜ƒ.minigameId;
            â˜ƒxxxx = â˜ƒ.minigameImage;
            â˜ƒxxxxx = â˜ƒ.minigameId == -1;
         } else {
            â˜ƒx = â˜ƒ.activeSlot == this.slotIndex && â˜ƒ.worldType != RealmsServer.WorldType.MINIGAME;
            â˜ƒxx = â˜ƒxxxxxx.getSlotName(this.slotIndex);
            â˜ƒxxx = â˜ƒxxxxxx.templateId;
            â˜ƒxxxx = â˜ƒxxxxxx.templateImage;
            â˜ƒxxxxx = â˜ƒxxxxxx.empty;
         }

         RealmsWorldSlotButton.Action â˜ƒx = getAction(â˜ƒ, â˜ƒx, â˜ƒxxxxxxx);
         Pair<Component, Component> â˜ƒxx = this.getTooltipAndNarration(â˜ƒ, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒx);
         this.state = new RealmsWorldSlotButton.State(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒxx.getFirst());
         this.setMessage(â˜ƒxx.getSecond());
      }
   }

   private static RealmsWorldSlotButton.Action getAction(RealmsServer var0, boolean var1, boolean var2) {
      if (â˜ƒ) {
         if (!â˜ƒ.expired && â˜ƒ.state != RealmsServer.State.UNINITIALIZED) {
            return RealmsWorldSlotButton.Action.JOIN;
         }
      } else {
         if (!â˜ƒ) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }

         if (!â˜ƒ.expired) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }
      }

      return RealmsWorldSlotButton.Action.NOTHING;
   }

   private Pair<Component, Component> getTooltipAndNarration(RealmsServer var1, String var2, boolean var3, boolean var4, RealmsWorldSlotButton.Action var5) {
      if (â˜ƒ == RealmsWorldSlotButton.Action.NOTHING) {
         return Pair.of(null, new TextComponent(â˜ƒ));
      } else {
         Component â˜ƒ;
         if (â˜ƒ) {
            if (â˜ƒ) {
               â˜ƒ = TextComponent.EMPTY;
            } else {
               â˜ƒ = new TextComponent(" ").append(â˜ƒ).append(" ").append(â˜ƒ.minigameName);
            }
         } else {
            â˜ƒ = new TextComponent(" ").append(â˜ƒ);
         }

         Component â˜ƒ;
         if (â˜ƒ == RealmsWorldSlotButton.Action.JOIN) {
            â˜ƒ = SLOT_ACTIVE_TOOLTIP;
         } else {
            â˜ƒ = â˜ƒ ? SWITCH_TO_MINIGAME_SLOT_TOOLTIP : SWITCH_TO_WORLD_SLOT_TOOLTIP;
         }

         Component â˜ƒ = â˜ƒ.copy().append(â˜ƒ);
         return Pair.of(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      if (this.state != null) {
         this.drawSlotFrame(
            â˜ƒ,
            this.x,
            this.y,
            â˜ƒ,
            â˜ƒ,
            this.state.isCurrentlyActiveSlot,
            this.state.slotName,
            this.slotIndex,
            this.state.imageId,
            this.state.image,
            this.state.empty,
            this.state.minigame,
            this.state.action,
            this.state.actionPrompt
         );
      }
   }

   private void drawSlotFrame(
      PoseStack var1,
      int var2,
      int var3,
      int var4,
      int var5,
      boolean var6,
      String var7,
      int var8,
      long var9,
      @Nullable String var11,
      boolean var12,
      boolean var13,
      RealmsWorldSlotButton.Action var14,
      @Nullable Component var15
   ) {
      boolean â˜ƒ = this.isHovered();
      if (this.isMouseOver((double)â˜ƒ, (double)â˜ƒ) && â˜ƒ != null) {
         this.toolTipSetter.accept(â˜ƒ);
      }

      Minecraft â˜ƒ = Minecraft.getInstance();
      TextureManager â˜ƒx = â˜ƒ.getTextureManager();
      if (â˜ƒ) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(â˜ƒ), â˜ƒ);
      } else if (â˜ƒ) {
         RenderSystem.setShaderTexture(0, EMPTY_SLOT_LOCATION);
      } else if (â˜ƒ != null && â˜ƒ != -1L) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(â˜ƒ), â˜ƒ);
      } else if (â˜ƒ == 1) {
         RenderSystem.setShaderTexture(0, DEFAULT_WORLD_SLOT_1);
      } else if (â˜ƒ == 2) {
         RenderSystem.setShaderTexture(0, DEFAULT_WORLD_SLOT_2);
      } else if (â˜ƒ == 3) {
         RenderSystem.setShaderTexture(0, DEFAULT_WORLD_SLOT_3);
      }

      if (â˜ƒ) {
         float â˜ƒ = 0.85F + 0.15F * Mth.cos((float)this.animTick * 0.2F);
         RenderSystem.setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
      } else {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      }

      blit(â˜ƒ, â˜ƒ + 3, â˜ƒ + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      RenderSystem.setShaderTexture(0, SLOT_FRAME_LOCATION);
      boolean â˜ƒ = â˜ƒ && â˜ƒ != RealmsWorldSlotButton.Action.NOTHING;
      if (â˜ƒ) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      } else if (â˜ƒ) {
         RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      }

      blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 80, 80, 80, 80);
      drawCenteredString(â˜ƒ, â˜ƒ.font, â˜ƒ, â˜ƒ + 40, â˜ƒ + 66, 16777215);
   }

   public static enum Action {
      NOTHING,
      SWITCH_SLOT,
      JOIN;
   }

   public static class State {
      final boolean isCurrentlyActiveSlot;
      final String slotName;
      final long imageId;
      final String image;
      public final boolean empty;
      public final boolean minigame;
      public final RealmsWorldSlotButton.Action action;
      @Nullable
      final Component actionPrompt;

      State(
         boolean var1, String var2, long var3, @Nullable String var5, boolean var6, boolean var7, RealmsWorldSlotButton.Action var8, @Nullable Component var9
      ) {
         this.isCurrentlyActiveSlot = â˜ƒ;
         this.slotName = â˜ƒ;
         this.imageId = â˜ƒ;
         this.image = â˜ƒ;
         this.empty = â˜ƒ;
         this.minigame = â˜ƒ;
         this.action = â˜ƒ;
         this.actionPrompt = â˜ƒ;
      }
   }
}
