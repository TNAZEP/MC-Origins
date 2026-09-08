package net.minecraft.client.gui.components;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class PlayerTabOverlay extends GuiComponent {
   private static final Ordering<PlayerInfo> PLAYER_ORDERING = Ordering.from(new PlayerTabOverlay.PlayerInfoComparator());
   public static final int MAX_ROWS_PER_COL = 20;
   public static final int HEART_EMPTY_CONTAINER = 16;
   public static final int HEART_EMPTY_CONTAINER_BLINKING = 25;
   public static final int HEART_FULL = 52;
   public static final int HEART_HALF_FULL = 61;
   public static final int HEART_GOLDEN_FULL = 160;
   public static final int HEART_GOLDEN_HALF_FULL = 169;
   public static final int HEART_GHOST_FULL = 70;
   public static final int HEART_GHOST_HALF_FULL = 79;
   private final Minecraft minecraft;
   private final Gui gui;
   @Nullable
   private Component footer;
   @Nullable
   private Component header;
   private long visibilityId;
   private boolean visible;

   public PlayerTabOverlay(Minecraft var1, Gui var2) {
      this.minecraft = â˜ƒ;
      this.gui = â˜ƒ;
   }

   public Component getNameForDisplay(PlayerInfo var1) {
      return â˜ƒ.getTabListDisplayName() != null
         ? this.decorateName(â˜ƒ, â˜ƒ.getTabListDisplayName().copy())
         : this.decorateName(â˜ƒ, PlayerTeam.formatNameForTeam(â˜ƒ.getTeam(), new TextComponent(â˜ƒ.getProfile().getName())));
   }

   private Component decorateName(PlayerInfo var1, MutableComponent var2) {
      return â˜ƒ.getGameMode() == GameType.SPECTATOR ? â˜ƒ.withStyle(ChatFormatting.ITALIC) : â˜ƒ;
   }

   public void setVisible(boolean var1) {
      if (â˜ƒ && !this.visible) {
         this.visibilityId = Util.getMillis();
      }

      this.visible = â˜ƒ;
   }

   public void render(PoseStack var1, int var2, Scoreboard var3, @Nullable Objective var4) {
      ClientPacketListener â˜ƒ = this.minecraft.player.connection;
      List<PlayerInfo> â˜ƒx = PLAYER_ORDERING.sortedCopy(â˜ƒ.getOnlinePlayers());
      int â˜ƒxx = 0;
      int â˜ƒxxx = 0;

      for(PlayerInfo â˜ƒxxxx : â˜ƒx) {
         int â˜ƒxxxxx = this.minecraft.font.width(this.getNameForDisplay(â˜ƒxxxx));
         â˜ƒxx = Math.max(â˜ƒxx, â˜ƒxxxxx);
         if (â˜ƒ != null && â˜ƒ.getRenderType() != ObjectiveCriteria.RenderType.HEARTS) {
            â˜ƒxxxxx = this.minecraft.font.width(" " + â˜ƒ.getOrCreatePlayerScore(â˜ƒxxxx.getProfile().getName(), â˜ƒ).getScore());
            â˜ƒxxx = Math.max(â˜ƒxxx, â˜ƒxxxxx);
         }
      }

      â˜ƒx = â˜ƒx.subList(0, Math.min(â˜ƒx.size(), 80));
      int â˜ƒxxxx = â˜ƒx.size();
      int â˜ƒxxxxx = â˜ƒxxxx;

      int â˜ƒ;
      for(â˜ƒ = 1; â˜ƒxxxxx > 20; â˜ƒxxxxx = (â˜ƒxxxx + â˜ƒ - 1) / â˜ƒ) {
         ++â˜ƒ;
      }

      boolean â˜ƒxxxxxxx = this.minecraft.isLocalServer() || this.minecraft.getConnection().getConnection().isEncrypted();
      int â˜ƒxxxxxx;
      if (â˜ƒ != null) {
         if (â˜ƒ.getRenderType() == ObjectiveCriteria.RenderType.HEARTS) {
            â˜ƒxxxxxx = 90;
         } else {
            â˜ƒxxxxxx = â˜ƒxxx;
         }
      } else {
         â˜ƒxxxxxx = 0;
      }

      int â˜ƒxxxxxx = Math.min(â˜ƒ * ((â˜ƒxxxxxxx ? 9 : 0) + â˜ƒxx + â˜ƒxxxxxx + 13), â˜ƒ - 50) / â˜ƒ;
      int â˜ƒxxxxxxx = â˜ƒ / 2 - (â˜ƒxxxxxx * â˜ƒ + (â˜ƒ - 1) * 5) / 2;
      int â˜ƒxxxxxxxx = 10;
      int â˜ƒxxxxxxxxx = â˜ƒxxxxxx * â˜ƒ + (â˜ƒ - 1) * 5;
      List<FormattedCharSequence> â˜ƒxxxxxxxxxx = null;
      if (this.header != null) {
         â˜ƒxxxxxxxxxx = this.minecraft.font.split(this.header, â˜ƒ - 50);

         for(FormattedCharSequence â˜ƒxxxxxxxxxxx : â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxxxx = Math.max(â˜ƒxxxxxxxxx, this.minecraft.font.width(â˜ƒxxxxxxxxxxx));
         }
      }

      List<FormattedCharSequence> â˜ƒxxxxxx = null;
      if (this.footer != null) {
         â˜ƒxxxxxx = this.minecraft.font.split(this.footer, â˜ƒ - 50);

         for(FormattedCharSequence â˜ƒxxxxxxx : â˜ƒxxxxxx) {
            â˜ƒxxxxxxxxx = Math.max(â˜ƒxxxxxxxxx, this.minecraft.font.width(â˜ƒxxxxxxx));
         }
      }

      if (â˜ƒxxxxxxxxxx != null) {
         fill(â˜ƒ, â˜ƒ / 2 - â˜ƒxxxxxxxxx / 2 - 1, â˜ƒxxxxxxxx - 1, â˜ƒ / 2 + â˜ƒxxxxxxxxx / 2 + 1, â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxx.size() * 9, Integer.MIN_VALUE);

         for(FormattedCharSequence â˜ƒxxxxxx : â˜ƒxxxxxxxxxx) {
            int â˜ƒxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxx);
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxxxx, (float)(â˜ƒ / 2 - â˜ƒxxxxxxx / 2), (float)â˜ƒxxxxxxxx, -1);
            â˜ƒxxxxxxxx += 9;
         }

         ++â˜ƒxxxxxxxx;
      }

      fill(â˜ƒ, â˜ƒ / 2 - â˜ƒxxxxxxxxx / 2 - 1, â˜ƒxxxxxxxx - 1, â˜ƒ / 2 + â˜ƒxxxxxxxxx / 2 + 1, â˜ƒxxxxxxxx + â˜ƒxxxxx * 9, Integer.MIN_VALUE);
      int â˜ƒxxxxxx = this.minecraft.options.getBackgroundColor(553648127);

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxx) {
         int â˜ƒxxxxxxxx = â˜ƒxxxxxxx / â˜ƒxxxxx;
         int â˜ƒxxxxxxxxx = â˜ƒxxxxxxx % â˜ƒxxxxx;
         int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxxx * 5;
         int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx + â˜ƒxxxxxxxxx * 9;
         fill(â˜ƒ, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxx + â˜ƒxxxxxx, â˜ƒxxxxxxxxxxx + 8, â˜ƒxxxxxx);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         if (â˜ƒxxxxxxx < â˜ƒx.size()) {
            PlayerInfo â˜ƒxxxxxxxxxxxx = (PlayerInfo)â˜ƒx.get(â˜ƒxxxxxxx);
            GameProfile â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getProfile();
            if (â˜ƒxxxxxxx) {
               Player â˜ƒxxxxxxxxxxxxxx = this.minecraft.level.getPlayerByUUID(â˜ƒxxxxxxxxxxxxx.getId());
               boolean â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx != null
                  && â˜ƒxxxxxxxxxxxxxx.isModelPartShown(PlayerModelPart.CAPE)
                  && ("Dinnerbone".equals(â˜ƒxxxxxxxxxxxxx.getName()) || "Grumm".equals(â˜ƒxxxxxxxxxxxxx.getName()));
               RenderSystem.setShaderTexture(0, â˜ƒxxxxxxxxxxxx.getSkinLocation());
               int â˜ƒxxxxxxxxxxxxxxxx = 8 + (â˜ƒxxxxxxxxxxxxxxx ? 8 : 0);
               int â˜ƒxxxxxxxxxxxxxxxxx = 8 * (â˜ƒxxxxxxxxxxxxxxx ? -1 : 1);
               GuiComponent.blit(â˜ƒ, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, 8, 8, 8.0F, (float)â˜ƒxxxxxxxxxxxxxxxx, 8, â˜ƒxxxxxxxxxxxxxxxxx, 64, 64);
               if (â˜ƒxxxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxxxxx.isModelPartShown(PlayerModelPart.HAT)) {
                  int â˜ƒxxxxxxxxxxxxxxxxxx = 8 + (â˜ƒxxxxxxxxxxxxxxx ? 8 : 0);
                  int â˜ƒxxxxxxxxxxxxxxxxxxx = 8 * (â˜ƒxxxxxxxxxxxxxxx ? -1 : 1);
                  GuiComponent.blit(â˜ƒ, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, 8, 8, 40.0F, (float)â˜ƒxxxxxxxxxxxxxxxxxx, 8, â˜ƒxxxxxxxxxxxxxxxxxxx, 64, 64);
               }

               â˜ƒxxxxxxxxxx += 9;
            }

            this.minecraft
               .font
               .drawShadow(
                  â˜ƒ,
                  this.getNameForDisplay(â˜ƒxxxxxxxxxxxx),
                  (float)â˜ƒxxxxxxxxxx,
                  (float)â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx.getGameMode() == GameType.SPECTATOR ? -1862270977 : -1
               );
            if (â˜ƒ != null && â˜ƒxxxxxxxxxxxx.getGameMode() != GameType.SPECTATOR) {
               int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx + â˜ƒxx + 1;
               int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxx;
               if (â˜ƒxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxx > 5) {
                  this.renderTablistScore(â˜ƒ, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx.getName(), â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒ);
               }
            }

            this.renderPingIcon(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx - (â˜ƒxxxxxxx ? 9 : 0), â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
         }
      }

      if (â˜ƒxxxxxx != null) {
         â˜ƒxxxxxxxx += â˜ƒxxxxx * 9 + 1;
         fill(â˜ƒ, â˜ƒ / 2 - â˜ƒxxxxxxxxx / 2 - 1, â˜ƒxxxxxxxx - 1, â˜ƒ / 2 + â˜ƒxxxxxxxxx / 2 + 1, â˜ƒxxxxxxxx + â˜ƒxxxxxx.size() * 9, Integer.MIN_VALUE);

         for(FormattedCharSequence â˜ƒxxxxxxx : â˜ƒxxxxxx) {
            int â˜ƒxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxx);
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxxxxx, (float)(â˜ƒ / 2 - â˜ƒxxxxxxxx / 2), (float)â˜ƒxxxxxxxx, -1);
            â˜ƒxxxxxxxx += 9;
         }
      }
   }

   protected void renderPingIcon(PoseStack var1, int var2, int var3, int var4, PlayerInfo var5) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
      int â˜ƒx = 0;
      int â˜ƒ;
      if (â˜ƒ.getLatency() < 0) {
         â˜ƒ = 5;
      } else if (â˜ƒ.getLatency() < 150) {
         â˜ƒ = 0;
      } else if (â˜ƒ.getLatency() < 300) {
         â˜ƒ = 1;
      } else if (â˜ƒ.getLatency() < 600) {
         â˜ƒ = 2;
      } else if (â˜ƒ.getLatency() < 1000) {
         â˜ƒ = 3;
      } else {
         â˜ƒ = 4;
      }

      this.setBlitOffset(this.getBlitOffset() + 100);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒ - 11, â˜ƒ, 0, 176 + â˜ƒ * 8, 10, 8);
      this.setBlitOffset(this.getBlitOffset() - 100);
   }

   private void renderTablistScore(Objective var1, int var2, String var3, int var4, int var5, PlayerInfo var6, PoseStack var7) {
      int â˜ƒ = â˜ƒ.getScoreboard().getOrCreatePlayerScore(â˜ƒ, â˜ƒ).getScore();
      if (â˜ƒ.getRenderType() == ObjectiveCriteria.RenderType.HEARTS) {
         RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
         long â˜ƒx = Util.getMillis();
         if (this.visibilityId == â˜ƒ.getRenderVisibilityId()) {
            if (â˜ƒ < â˜ƒ.getLastHealth()) {
               â˜ƒ.setLastHealthTime(â˜ƒx);
               â˜ƒ.setHealthBlinkTime((long)(this.gui.getGuiTicks() + 20));
            } else if (â˜ƒ > â˜ƒ.getLastHealth()) {
               â˜ƒ.setLastHealthTime(â˜ƒx);
               â˜ƒ.setHealthBlinkTime((long)(this.gui.getGuiTicks() + 10));
            }
         }

         if (â˜ƒx - â˜ƒ.getLastHealthTime() > 1000L || this.visibilityId != â˜ƒ.getRenderVisibilityId()) {
            â˜ƒ.setLastHealth(â˜ƒ);
            â˜ƒ.setDisplayHealth(â˜ƒ);
            â˜ƒ.setLastHealthTime(â˜ƒx);
         }

         â˜ƒ.setRenderVisibilityId(this.visibilityId);
         â˜ƒ.setLastHealth(â˜ƒ);
         int â˜ƒx = Mth.ceil((float)Math.max(â˜ƒ, â˜ƒ.getDisplayHealth()) / 2.0F);
         int â˜ƒxx = Math.max(Mth.ceil((float)(â˜ƒ / 2)), Math.max(Mth.ceil((float)(â˜ƒ.getDisplayHealth() / 2)), 10));
         boolean â˜ƒxxx = â˜ƒ.getHealthBlinkTime() > (long)this.gui.getGuiTicks() && (â˜ƒ.getHealthBlinkTime() - (long)this.gui.getGuiTicks()) / 3L % 2L == 1L;
         if (â˜ƒx > 0) {
            int â˜ƒxxxx = Mth.floor(Math.min((float)(â˜ƒ - â˜ƒ - 4) / (float)â˜ƒxx, 9.0F));
            if (â˜ƒxxxx > 3) {
               for(int â˜ƒxxxxx = â˜ƒx; â˜ƒxxxxx < â˜ƒxx; ++â˜ƒxxxxx) {
                  this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, â˜ƒxxx ? 25 : 16, 0, 9, 9);
               }

               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒx; ++â˜ƒxxxxx) {
                  this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, â˜ƒxxx ? 25 : 16, 0, 9, 9);
                  if (â˜ƒxxx) {
                     if (â˜ƒxxxxx * 2 + 1 < â˜ƒ.getDisplayHealth()) {
                        this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, 70, 0, 9, 9);
                     }

                     if (â˜ƒxxxxx * 2 + 1 == â˜ƒ.getDisplayHealth()) {
                        this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, 79, 0, 9, 9);
                     }
                  }

                  if (â˜ƒxxxxx * 2 + 1 < â˜ƒ) {
                     this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, â˜ƒxxxxx >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (â˜ƒxxxxx * 2 + 1 == â˜ƒ) {
                     this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx * â˜ƒxxxx, â˜ƒ, â˜ƒxxxxx >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float â˜ƒxxxx = Mth.clamp((float)â˜ƒ / 20.0F, 0.0F, 1.0F);
               int â˜ƒxxxxx = (int)((1.0F - â˜ƒxxxx) * 255.0F) << 16 | (int)(â˜ƒxxxx * 255.0F) << 8;
               String â˜ƒxxxxxx = (float)â˜ƒ / 2.0F + "";
               if (â˜ƒ - this.minecraft.font.width(â˜ƒxxxxxx + "hp") >= â˜ƒ) {
                  â˜ƒxxxxxx = â˜ƒxxxxxx + "hp";
               }

               this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxxxx, (float)((â˜ƒ + â˜ƒ) / 2 - this.minecraft.font.width(â˜ƒxxxxxx) / 2), (float)â˜ƒ, â˜ƒxxxxx);
            }
         }
      } else {
         String â˜ƒ = "" + ChatFormatting.YELLOW + â˜ƒ;
         this.minecraft.font.drawShadow(â˜ƒ, â˜ƒ, (float)(â˜ƒ - this.minecraft.font.width(â˜ƒ)), (float)â˜ƒ, 16777215);
      }
   }

   public void setFooter(@Nullable Component var1) {
      this.footer = â˜ƒ;
   }

   public void setHeader(@Nullable Component var1) {
      this.header = â˜ƒ;
   }

   public void reset() {
      this.header = null;
      this.footer = null;
   }

   static class PlayerInfoComparator implements Comparator<PlayerInfo> {
      public int compare(PlayerInfo var1, PlayerInfo var2) {
         PlayerTeam â˜ƒ = â˜ƒ.getTeam();
         PlayerTeam â˜ƒx = â˜ƒ.getTeam();
         return ComparisonChain.start()
            .compareTrueFirst(â˜ƒ.getGameMode() != GameType.SPECTATOR, â˜ƒ.getGameMode() != GameType.SPECTATOR)
            .compare(â˜ƒ != null ? â˜ƒ.getName() : "", â˜ƒx != null ? â˜ƒx.getName() : "")
            .compare(â˜ƒ.getProfile().getName(), â˜ƒ.getProfile().getName(), String::compareToIgnoreCase)
            .result();
      }
   }
}
