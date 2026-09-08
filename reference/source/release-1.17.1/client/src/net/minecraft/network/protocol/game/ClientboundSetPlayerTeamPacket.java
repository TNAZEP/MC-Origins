package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.PlayerTeam;

public class ClientboundSetPlayerTeamPacket implements Packet<ClientGamePacketListener> {
   private static final int METHOD_ADD = 0;
   private static final int METHOD_REMOVE = 1;
   private static final int METHOD_CHANGE = 2;
   private static final int METHOD_JOIN = 3;
   private static final int METHOD_LEAVE = 4;
   private static final int MAX_VISIBILITY_LENGTH = 40;
   private static final int MAX_COLLISION_LENGTH = 40;
   private final int method;
   private final String name;
   private final Collection<String> players;
   private final Optional<ClientboundSetPlayerTeamPacket.Parameters> parameters;

   private ClientboundSetPlayerTeamPacket(String var1, int var2, Optional<ClientboundSetPlayerTeamPacket.Parameters> var3, Collection<String> var4) {
      this.name = â˜ƒ;
      this.method = â˜ƒ;
      this.parameters = â˜ƒ;
      this.players = ImmutableList.copyOf(â˜ƒ);
   }

   public static ClientboundSetPlayerTeamPacket createAddOrModifyPacket(PlayerTeam var0, boolean var1) {
      return new ClientboundSetPlayerTeamPacket(
         â˜ƒ.getName(),
         â˜ƒ ? 0 : 2,
         Optional.of(new ClientboundSetPlayerTeamPacket.Parameters(â˜ƒ)),
         (Collection<String>)(â˜ƒ ? â˜ƒ.getPlayers() : ImmutableList.of())
      );
   }

   public static ClientboundSetPlayerTeamPacket createRemovePacket(PlayerTeam var0) {
      return new ClientboundSetPlayerTeamPacket(â˜ƒ.getName(), 1, Optional.empty(), ImmutableList.of());
   }

   public static ClientboundSetPlayerTeamPacket createPlayerPacket(PlayerTeam var0, String var1, ClientboundSetPlayerTeamPacket.Action var2) {
      return new ClientboundSetPlayerTeamPacket(
         â˜ƒ.getName(), â˜ƒ == ClientboundSetPlayerTeamPacket.Action.ADD ? 3 : 4, Optional.empty(), ImmutableList.of(â˜ƒ)
      );
   }

   public ClientboundSetPlayerTeamPacket(FriendlyByteBuf var1) {
      this.name = â˜ƒ.readUtf(16);
      this.method = â˜ƒ.readByte();
      if (shouldHaveParameters(this.method)) {
         this.parameters = Optional.of(new ClientboundSetPlayerTeamPacket.Parameters(â˜ƒ));
      } else {
         this.parameters = Optional.empty();
      }

      if (shouldHavePlayerList(this.method)) {
         this.players = â˜ƒ.readList(FriendlyByteBuf::readUtf);
      } else {
         this.players = ImmutableList.of();
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.name);
      â˜ƒ.writeByte(this.method);
      if (shouldHaveParameters(this.method)) {
         ((ClientboundSetPlayerTeamPacket.Parameters)this.parameters
               .orElseThrow(() -> new IllegalStateException("Parameters not present, but method is" + this.method)))
            .write(â˜ƒ);
      }

      if (shouldHavePlayerList(this.method)) {
         â˜ƒ.writeCollection(this.players, FriendlyByteBuf::writeUtf);
      }
   }

   private static boolean shouldHavePlayerList(int var0) {
      return â˜ƒ == 0 || â˜ƒ == 3 || â˜ƒ == 4;
   }

   private static boolean shouldHaveParameters(int var0) {
      return â˜ƒ == 0 || â˜ƒ == 2;
   }

   @Nullable
   public ClientboundSetPlayerTeamPacket.Action getPlayerAction() {
      switch(this.method) {
         case 0:
         case 3:
            return ClientboundSetPlayerTeamPacket.Action.ADD;
         case 1:
         case 2:
         default:
            return null;
         case 4:
            return ClientboundSetPlayerTeamPacket.Action.REMOVE;
      }
   }

   @Nullable
   public ClientboundSetPlayerTeamPacket.Action getTeamAction() {
      switch(this.method) {
         case 0:
            return ClientboundSetPlayerTeamPacket.Action.ADD;
         case 1:
            return ClientboundSetPlayerTeamPacket.Action.REMOVE;
         default:
            return null;
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetPlayerTeamPacket(this);
   }

   public String getName() {
      return this.name;
   }

   public Collection<String> getPlayers() {
      return this.players;
   }

   public Optional<ClientboundSetPlayerTeamPacket.Parameters> getParameters() {
      return this.parameters;
   }

   public static enum Action {
      ADD,
      REMOVE;
   }

   public static class Parameters {
      private final Component displayName;
      private final Component playerPrefix;
      private final Component playerSuffix;
      private final String nametagVisibility;
      private final String collisionRule;
      private final ChatFormatting color;
      private final int options;

      public Parameters(PlayerTeam var1) {
         this.displayName = â˜ƒ.getDisplayName();
         this.options = â˜ƒ.packOptions();
         this.nametagVisibility = â˜ƒ.getNameTagVisibility().name;
         this.collisionRule = â˜ƒ.getCollisionRule().name;
         this.color = â˜ƒ.getColor();
         this.playerPrefix = â˜ƒ.getPlayerPrefix();
         this.playerSuffix = â˜ƒ.getPlayerSuffix();
      }

      public Parameters(FriendlyByteBuf var1) {
         this.displayName = â˜ƒ.readComponent();
         this.options = â˜ƒ.readByte();
         this.nametagVisibility = â˜ƒ.readUtf(40);
         this.collisionRule = â˜ƒ.readUtf(40);
         this.color = â˜ƒ.readEnum(ChatFormatting.class);
         this.playerPrefix = â˜ƒ.readComponent();
         this.playerSuffix = â˜ƒ.readComponent();
      }

      public Component getDisplayName() {
         return this.displayName;
      }

      public int getOptions() {
         return this.options;
      }

      public ChatFormatting getColor() {
         return this.color;
      }

      public String getNametagVisibility() {
         return this.nametagVisibility;
      }

      public String getCollisionRule() {
         return this.collisionRule;
      }

      public Component getPlayerPrefix() {
         return this.playerPrefix;
      }

      public Component getPlayerSuffix() {
         return this.playerSuffix;
      }

      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeComponent(this.displayName);
         â˜ƒ.writeByte(this.options);
         â˜ƒ.writeUtf(this.nametagVisibility);
         â˜ƒ.writeUtf(this.collisionRule);
         â˜ƒ.writeEnum(this.color);
         â˜ƒ.writeComponent(this.playerPrefix);
         â˜ƒ.writeComponent(this.playerSuffix);
      }
   }
}
