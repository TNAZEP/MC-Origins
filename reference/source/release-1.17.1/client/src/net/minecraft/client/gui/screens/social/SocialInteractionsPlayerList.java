package net.minecraft.client.gui.screens.social;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.multiplayer.PlayerInfo;

public class SocialInteractionsPlayerList extends ContainerObjectSelectionList<PlayerEntry> {
   private final SocialInteractionsScreen socialInteractionsScreen;
   private final List<PlayerEntry> players = Lists.<PlayerEntry>newArrayList();
   @Nullable
   private String filter;

   public SocialInteractionsPlayerList(SocialInteractionsScreen var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.socialInteractionsScreen = â˜ƒ;
      this.setRenderBackground(false);
      this.setRenderTopAndBottom(false);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      double â˜ƒ = this.minecraft.getWindow().getGuiScale();
      RenderSystem.enableScissor(
         (int)((double)this.getRowLeft() * â˜ƒ),
         (int)((double)(this.height - this.y1) * â˜ƒ),
         (int)((double)(this.getScrollbarPosition() + 6) * â˜ƒ),
         (int)((double)(this.height - (this.height - this.y1) - this.y0 - 4) * â˜ƒ)
      );
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.disableScissor();
   }

   public void updatePlayerList(Collection<UUID> var1, double var2) {
      this.players.clear();

      for(UUID â˜ƒ : â˜ƒ) {
         PlayerInfo â˜ƒx = this.minecraft.player.connection.getPlayerInfo(â˜ƒ);
         if (â˜ƒx != null) {
            this.players
               .add(
                  new PlayerEntry(this.minecraft, this.socialInteractionsScreen, â˜ƒx.getProfile().getId(), â˜ƒx.getProfile().getName(), â˜ƒx::getSkinLocation)
               );
         }
      }

      this.updateFilteredPlayers();
      this.players.sort((var0, var1x) -> var0.getPlayerName().compareToIgnoreCase(var1x.getPlayerName()));
      this.replaceEntries(this.players);
      this.setScrollAmount(â˜ƒ);
   }

   private void updateFilteredPlayers() {
      if (this.filter != null) {
         this.players.removeIf(var1 -> !var1.getPlayerName().toLowerCase(Locale.ROOT).contains(this.filter));
         this.replaceEntries(this.players);
      }
   }

   public void setFilter(String var1) {
      this.filter = â˜ƒ;
   }

   public boolean isEmpty() {
      return this.players.isEmpty();
   }

   public void addPlayer(PlayerInfo var1, SocialInteractionsScreen.Page var2) {
      UUID â˜ƒ = â˜ƒ.getProfile().getId();

      for(PlayerEntry â˜ƒx : this.players) {
         if (â˜ƒx.getPlayerId().equals(â˜ƒ)) {
            â˜ƒx.setRemoved(false);
            return;
         }
      }

      if ((â˜ƒ == SocialInteractionsScreen.Page.ALL || this.minecraft.getPlayerSocialManager().shouldHideMessageFrom(â˜ƒ))
         && (Strings.isNullOrEmpty(this.filter) || â˜ƒ.getProfile().getName().toLowerCase(Locale.ROOT).contains(this.filter))) {
         PlayerEntry â˜ƒx = new PlayerEntry(
            this.minecraft, this.socialInteractionsScreen, â˜ƒ.getProfile().getId(), â˜ƒ.getProfile().getName(), â˜ƒ::getSkinLocation
         );
         this.addEntry(â˜ƒx);
         this.players.add(â˜ƒx);
      }
   }

   public void removePlayer(UUID var1) {
      for(PlayerEntry â˜ƒ : this.players) {
         if (â˜ƒ.getPlayerId().equals(â˜ƒ)) {
            â˜ƒ.setRemoved(true);
            return;
         }
      }
   }
}
