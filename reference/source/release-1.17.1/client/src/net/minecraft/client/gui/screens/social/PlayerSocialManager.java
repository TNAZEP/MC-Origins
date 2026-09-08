package net.minecraft.client.gui.screens.social;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;

public class PlayerSocialManager {
   private final Minecraft minecraft;
   private final Set<UUID> hiddenPlayers = Sets.newHashSet();
   private final SocialInteractionsService service;
   private final Map<String, UUID> discoveredNamesToUUID = Maps.newHashMap();

   public PlayerSocialManager(Minecraft var1, SocialInteractionsService var2) {
      this.minecraft = â˜ƒ;
      this.service = â˜ƒ;
   }

   public void hidePlayer(UUID var1) {
      this.hiddenPlayers.add(â˜ƒ);
   }

   public void showPlayer(UUID var1) {
      this.hiddenPlayers.remove(â˜ƒ);
   }

   public boolean shouldHideMessageFrom(UUID var1) {
      return this.isHidden(â˜ƒ) || this.isBlocked(â˜ƒ);
   }

   public boolean isHidden(UUID var1) {
      return this.hiddenPlayers.contains(â˜ƒ);
   }

   public boolean isBlocked(UUID var1) {
      return this.service.isBlockedPlayer(â˜ƒ);
   }

   public Set<UUID> getHiddenPlayers() {
      return this.hiddenPlayers;
   }

   public UUID getDiscoveredUUID(String var1) {
      return (UUID)this.discoveredNamesToUUID.getOrDefault(â˜ƒ, Util.NIL_UUID);
   }

   public void addPlayer(PlayerInfo var1) {
      GameProfile â˜ƒ = â˜ƒ.getProfile();
      if (â˜ƒ.isComplete()) {
         this.discoveredNamesToUUID.put(â˜ƒ.getName(), â˜ƒ.getId());
      }

      Screen â˜ƒx = this.minecraft.screen;
      if (â˜ƒx instanceof SocialInteractionsScreen â˜ƒ) {
         â˜ƒ.onAddPlayer(â˜ƒ);
      }
   }

   public void removePlayer(UUID var1) {
      Screen â˜ƒx = this.minecraft.screen;
      if (â˜ƒx instanceof SocialInteractionsScreen â˜ƒ) {
         â˜ƒ.onRemovePlayer(â˜ƒ);
      }
   }
}
