package net.minecraft.network.protocol.game;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class ClientboundPlayerInfoPacket implements Packet<ClientGamePacketListener> {
   private final ClientboundPlayerInfoPacket.Action action;
   private final List<ClientboundPlayerInfoPacket.PlayerUpdate> entries;

   public ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action var1, ServerPlayer... var2) {
      this.action = â˜ƒ;
      this.entries = Lists.<ClientboundPlayerInfoPacket.PlayerUpdate>newArrayListWithCapacity(â˜ƒ.length);

      for(ServerPlayer â˜ƒ : â˜ƒ) {
         this.entries
            .add(
               new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ.getGameProfile(), â˜ƒ.latency, â˜ƒ.gameMode.getGameModeForPlayer(), â˜ƒ.getTabListDisplayName())
            );
      }
   }

   public ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action var1, Collection<ServerPlayer> var2) {
      this.action = â˜ƒ;
      this.entries = Lists.<ClientboundPlayerInfoPacket.PlayerUpdate>newArrayListWithCapacity(â˜ƒ.size());

      for(ServerPlayer â˜ƒ : â˜ƒ) {
         this.entries
            .add(
               new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ.getGameProfile(), â˜ƒ.latency, â˜ƒ.gameMode.getGameModeForPlayer(), â˜ƒ.getTabListDisplayName())
            );
      }
   }

   public ClientboundPlayerInfoPacket(FriendlyByteBuf var1) {
      this.action = â˜ƒ.readEnum(ClientboundPlayerInfoPacket.Action.class);
      this.entries = â˜ƒ.readList(this.action::read);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.action);
      â˜ƒ.writeCollection(this.entries, this.action::write);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePlayerInfo(this);
   }

   public List<ClientboundPlayerInfoPacket.PlayerUpdate> getEntries() {
      return this.entries;
   }

   public ClientboundPlayerInfoPacket.Action getAction() {
      return this.action;
   }

   @Nullable
   static Component readDisplayName(FriendlyByteBuf var0) {
      return â˜ƒ.readBoolean() ? â˜ƒ.readComponent() : null;
   }

   static void writeDisplayName(FriendlyByteBuf var0, @Nullable Component var1) {
      if (â˜ƒ == null) {
         â˜ƒ.writeBoolean(false);
      } else {
         â˜ƒ.writeBoolean(true);
         â˜ƒ.writeComponent(â˜ƒ);
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.action).add("entries", this.entries).toString();
   }

   public static enum Action {
      ADD_PLAYER {
         @Override
         protected ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1) {
            GameProfile â˜ƒ = new GameProfile(â˜ƒ.readUUID(), â˜ƒ.readUtf(16));
            PropertyMap â˜ƒx = â˜ƒ.getProperties();
            â˜ƒ.readWithCount(var1x -> {
               String â˜ƒ = var1x.readUtf();
               String â˜ƒx = var1x.readUtf();
               if (var1x.readBoolean()) {
                  String â˜ƒxx = var1x.readUtf();
                  â˜ƒ.put(â˜ƒ, new Property(â˜ƒ, â˜ƒx, â˜ƒxx));
               } else {
                  â˜ƒ.put(â˜ƒ, new Property(â˜ƒ, â˜ƒx));
               }
            });
            GameType â˜ƒxx = GameType.byId(â˜ƒ.readVarInt());
            int â˜ƒxxx = â˜ƒ.readVarInt();
            Component â˜ƒxxxx = ClientboundPlayerInfoPacket.readDisplayName(â˜ƒ);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ, â˜ƒxxx, â˜ƒxx, â˜ƒxxxx);
         }

         @Override
         protected void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2) {
            â˜ƒ.writeUUID(â˜ƒ.getProfile().getId());
            â˜ƒ.writeUtf(â˜ƒ.getProfile().getName());
            â˜ƒ.writeCollection(â˜ƒ.getProfile().getProperties().values(), (var0, var1x) -> {
               var0.writeUtf(var1x.getName());
               var0.writeUtf(var1x.getValue());
               if (var1x.hasSignature()) {
                  var0.writeBoolean(true);
                  var0.writeUtf(var1x.getSignature());
               } else {
                  var0.writeBoolean(false);
               }
            });
            â˜ƒ.writeVarInt(â˜ƒ.getGameMode().getId());
            â˜ƒ.writeVarInt(â˜ƒ.getLatency());
            ClientboundPlayerInfoPacket.writeDisplayName(â˜ƒ, â˜ƒ.getDisplayName());
         }
      },
      UPDATE_GAME_MODE {
         @Override
         protected ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1) {
            GameProfile â˜ƒ = new GameProfile(â˜ƒ.readUUID(), null);
            GameType â˜ƒx = GameType.byId(â˜ƒ.readVarInt());
            return new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ, 0, â˜ƒx, null);
         }

         @Override
         protected void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2) {
            â˜ƒ.writeUUID(â˜ƒ.getProfile().getId());
            â˜ƒ.writeVarInt(â˜ƒ.getGameMode().getId());
         }
      },
      UPDATE_LATENCY {
         @Override
         protected ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1) {
            GameProfile â˜ƒ = new GameProfile(â˜ƒ.readUUID(), null);
            int â˜ƒx = â˜ƒ.readVarInt();
            return new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ, â˜ƒx, null, null);
         }

         @Override
         protected void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2) {
            â˜ƒ.writeUUID(â˜ƒ.getProfile().getId());
            â˜ƒ.writeVarInt(â˜ƒ.getLatency());
         }
      },
      UPDATE_DISPLAY_NAME {
         @Override
         protected ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1) {
            GameProfile â˜ƒ = new GameProfile(â˜ƒ.readUUID(), null);
            Component â˜ƒx = ClientboundPlayerInfoPacket.readDisplayName(â˜ƒ);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ, 0, null, â˜ƒx);
         }

         @Override
         protected void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2) {
            â˜ƒ.writeUUID(â˜ƒ.getProfile().getId());
            ClientboundPlayerInfoPacket.writeDisplayName(â˜ƒ, â˜ƒ.getDisplayName());
         }
      },
      REMOVE_PLAYER {
         @Override
         protected ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1) {
            GameProfile â˜ƒ = new GameProfile(â˜ƒ.readUUID(), null);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(â˜ƒ, 0, null, null);
         }

         @Override
         protected void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2) {
            â˜ƒ.writeUUID(â˜ƒ.getProfile().getId());
         }
      };

      protected abstract ClientboundPlayerInfoPacket.PlayerUpdate read(FriendlyByteBuf var1);

      protected abstract void write(FriendlyByteBuf var1, ClientboundPlayerInfoPacket.PlayerUpdate var2);
   }

   public static class PlayerUpdate {
      private final int latency;
      private final GameType gameMode;
      private final GameProfile profile;
      @Nullable
      private final Component displayName;

      public PlayerUpdate(GameProfile var1, int var2, @Nullable GameType var3, @Nullable Component var4) {
         this.profile = â˜ƒ;
         this.latency = â˜ƒ;
         this.gameMode = â˜ƒ;
         this.displayName = â˜ƒ;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public int getLatency() {
         return this.latency;
      }

      public GameType getGameMode() {
         return this.gameMode;
      }

      @Nullable
      public Component getDisplayName() {
         return this.displayName;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("latency", this.latency)
            .add("gameMode", this.gameMode)
            .add("profile", this.profile)
            .add("displayName", this.displayName == null ? null : Component.Serializer.toJson(this.displayName))
            .toString();
      }
   }
}
