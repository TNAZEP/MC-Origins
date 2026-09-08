package net.minecraft.server.level;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class ServerBossEvent extends BossEvent {
   private final Set<ServerPlayer> players = Sets.<ServerPlayer>newHashSet();
   private final Set<ServerPlayer> unmodifiablePlayers = Collections.unmodifiableSet(this.players);
   private boolean visible = true;

   public ServerBossEvent(Component var1, BossEvent.BossBarColor var2, BossEvent.BossBarOverlay var3) {
      super(Mth.createInsecureUUID(), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void setProgress(float var1) {
      if (â˜ƒ != this.progress) {
         super.setProgress(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdateProgressPacket);
      }
   }

   @Override
   public void setColor(BossEvent.BossBarColor var1) {
      if (â˜ƒ != this.color) {
         super.setColor(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
      }
   }

   @Override
   public void setOverlay(BossEvent.BossBarOverlay var1) {
      if (â˜ƒ != this.overlay) {
         super.setOverlay(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
      }
   }

   @Override
   public BossEvent setDarkenScreen(boolean var1) {
      if (â˜ƒ != this.darkenScreen) {
         super.setDarkenScreen(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
      }

      return this;
   }

   @Override
   public BossEvent setPlayBossMusic(boolean var1) {
      if (â˜ƒ != this.playBossMusic) {
         super.setPlayBossMusic(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
      }

      return this;
   }

   @Override
   public BossEvent setCreateWorldFog(boolean var1) {
      if (â˜ƒ != this.createWorldFog) {
         super.setCreateWorldFog(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
      }

      return this;
   }

   @Override
   public void setName(Component var1) {
      if (!Objects.equal(â˜ƒ, this.name)) {
         super.setName(â˜ƒ);
         this.broadcast(ClientboundBossEventPacket::createUpdateNamePacket);
      }
   }

   private void broadcast(Function<BossEvent, ClientboundBossEventPacket> var1) {
      if (this.visible) {
         ClientboundBossEventPacket â˜ƒ = (ClientboundBossEventPacket)â˜ƒ.apply(this);

         for(ServerPlayer â˜ƒx : this.players) {
            â˜ƒx.connection.send(â˜ƒ);
         }
      }
   }

   public void addPlayer(ServerPlayer var1) {
      if (this.players.add(â˜ƒ) && this.visible) {
         â˜ƒ.connection.send(ClientboundBossEventPacket.createAddPacket(this));
      }
   }

   public void removePlayer(ServerPlayer var1) {
      if (this.players.remove(â˜ƒ) && this.visible) {
         â˜ƒ.connection.send(ClientboundBossEventPacket.createRemovePacket(this.getId()));
      }
   }

   public void removeAllPlayers() {
      if (!this.players.isEmpty()) {
         for(ServerPlayer â˜ƒ : Lists.newArrayList(this.players)) {
            this.removePlayer(â˜ƒ);
         }
      }
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean var1) {
      if (â˜ƒ != this.visible) {
         this.visible = â˜ƒ;

         for(ServerPlayer â˜ƒ : this.players) {
            â˜ƒ.connection.send(â˜ƒ ? ClientboundBossEventPacket.createAddPacket(this) : ClientboundBossEventPacket.createRemovePacket(this.getId()));
         }
      }
   }

   public Collection<ServerPlayer> getPlayers() {
      return this.unmodifiablePlayers;
   }
}
