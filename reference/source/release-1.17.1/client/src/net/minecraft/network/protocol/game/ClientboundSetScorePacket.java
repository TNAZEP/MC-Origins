package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.ServerScoreboard;

public class ClientboundSetScorePacket implements Packet<ClientGamePacketListener> {
   private final String owner;
   @Nullable
   private final String objectiveName;
   private final int score;
   private final ServerScoreboard.Method method;

   public ClientboundSetScorePacket(ServerScoreboard.Method var1, @Nullable String var2, String var3, int var4) {
      if (â˜ƒ != ServerScoreboard.Method.REMOVE && â˜ƒ == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.owner = â˜ƒ;
         this.objectiveName = â˜ƒ;
         this.score = â˜ƒ;
         this.method = â˜ƒ;
      }
   }

   public ClientboundSetScorePacket(FriendlyByteBuf var1) {
      this.owner = â˜ƒ.readUtf(40);
      this.method = â˜ƒ.readEnum(ServerScoreboard.Method.class);
      String â˜ƒ = â˜ƒ.readUtf(16);
      this.objectiveName = Objects.equals(â˜ƒ, "") ? null : â˜ƒ;
      if (this.method != ServerScoreboard.Method.REMOVE) {
         this.score = â˜ƒ.readVarInt();
      } else {
         this.score = 0;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.owner);
      â˜ƒ.writeEnum(this.method);
      â˜ƒ.writeUtf(this.objectiveName == null ? "" : this.objectiveName);
      if (this.method != ServerScoreboard.Method.REMOVE) {
         â˜ƒ.writeVarInt(this.score);
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetScore(this);
   }

   public String getOwner() {
      return this.owner;
   }

   @Nullable
   public String getObjectiveName() {
      return this.objectiveName;
   }

   public int getScore() {
      return this.score;
   }

   public ServerScoreboard.Method getMethod() {
      return this.method;
   }
}
