package net.minecraft.client.gui.components;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;

public class BossHealthOverlay extends GuiComponent {
   private static final ResourceLocation GUI_BARS_LOCATION = new ResourceLocation("textures/gui/bars.png");
   private static final int BAR_WIDTH = 182;
   private static final int BAR_HEIGHT = 5;
   private static final int OVERLAY_OFFSET = 80;
   private final Minecraft minecraft;
   final Map<UUID, LerpingBossEvent> events = Maps.newLinkedHashMap();

   public BossHealthOverlay(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void render(PoseStack var1) {
      if (!this.events.isEmpty()) {
         int â˜ƒ = this.minecraft.getWindow().getGuiScaledWidth();
         int â˜ƒx = 12;

         for(LerpingBossEvent â˜ƒxx : this.events.values()) {
            int â˜ƒxxx = â˜ƒ / 2 - 91;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
            this.drawBar(â˜ƒ, â˜ƒxxx, â˜ƒx, â˜ƒxx);
            Component â˜ƒxxxx = â˜ƒxx.getName();
            int â˜ƒxxxxx = this.minecraft.font.width(â˜ƒxxxx);
            int â˜ƒxxxxxx = â˜ƒ / 2 - â˜ƒxxxxx / 2;
            int â˜ƒxxxxxxx = â˜ƒx - 9;
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxx, (float)â˜ƒxxxxxx, (float)â˜ƒxxxxxxx, 16777215);
            â˜ƒx += 10 + 9;
            if (â˜ƒx >= this.minecraft.getWindow().getGuiScaledHeight() / 3) {
               break;
            }
         }
      }
   }

   private void drawBar(PoseStack var1, int var2, int var3, BossEvent var4) {
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒ.getColor().ordinal() * 5 * 2, 182, 5);
      if (â˜ƒ.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, 80 + (â˜ƒ.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
      }

      int â˜ƒ = (int)(â˜ƒ.getProgress() * 183.0F);
      if (â˜ƒ > 0) {
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒ.getColor().ordinal() * 5 * 2 + 5, â˜ƒ, 5);
         if (â˜ƒ.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
            this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, 80 + (â˜ƒ.getOverlay().ordinal() - 1) * 5 * 2 + 5, â˜ƒ, 5);
         }
      }
   }

   public void update(ClientboundBossEventPacket var1) {
      â˜ƒ.dispatch(
         new ClientboundBossEventPacket.Handler() {
            @Override
            public void add(
               UUID var1, Component var2, float var3, BossEvent.BossBarColor var4, BossEvent.BossBarOverlay var5, boolean var6, boolean var7, boolean var8
            ) {
               BossHealthOverlay.this.events.put(â˜ƒ, new LerpingBossEvent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
            }
   
            @Override
            public void remove(UUID var1) {
               BossHealthOverlay.this.events.remove(â˜ƒ);
            }
   
            @Override
            public void updateProgress(UUID var1, float var2) {
               ((LerpingBossEvent)BossHealthOverlay.this.events.get(â˜ƒ)).setProgress(â˜ƒ);
            }
   
            @Override
            public void updateName(UUID var1, Component var2) {
               ((LerpingBossEvent)BossHealthOverlay.this.events.get(â˜ƒ)).setName(â˜ƒ);
            }
   
            @Override
            public void updateStyle(UUID var1, BossEvent.BossBarColor var2, BossEvent.BossBarOverlay var3) {
               LerpingBossEvent â˜ƒ = (LerpingBossEvent)BossHealthOverlay.this.events.get(â˜ƒ);
               â˜ƒ.setColor(â˜ƒ);
               â˜ƒ.setOverlay(â˜ƒ);
            }
   
            @Override
            public void updateProperties(UUID var1, boolean var2, boolean var3, boolean var4) {
               LerpingBossEvent â˜ƒ = (LerpingBossEvent)BossHealthOverlay.this.events.get(â˜ƒ);
               â˜ƒ.setDarkenScreen(â˜ƒ);
               â˜ƒ.setPlayBossMusic(â˜ƒ);
               â˜ƒ.setCreateWorldFog(â˜ƒ);
            }
         }
      );
   }

   public void reset() {
      this.events.clear();
   }

   public boolean shouldPlayMusic() {
      if (!this.events.isEmpty()) {
         for(BossEvent â˜ƒ : this.events.values()) {
            if (â˜ƒ.shouldPlayBossMusic()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean shouldDarkenScreen() {
      if (!this.events.isEmpty()) {
         for(BossEvent â˜ƒ : this.events.values()) {
            if (â˜ƒ.shouldDarkenScreen()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean shouldCreateWorldFog() {
      if (!this.events.isEmpty()) {
         for(BossEvent â˜ƒ : this.events.values()) {
            if (â˜ƒ.shouldCreateWorldFog()) {
               return true;
            }
         }
      }

      return false;
   }
}
