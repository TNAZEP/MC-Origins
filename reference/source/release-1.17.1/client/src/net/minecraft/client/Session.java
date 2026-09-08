package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;

public class Session implements GameSession {
   private final int players;
   private final boolean isRemoteServer;
   private final String difficulty;
   private final String gameMode;
   private final UUID id;

   public Session(ClientLevel var1, LocalPlayer var2, ClientPacketListener var3) {
      this.players = â˜ƒ.getOnlinePlayers().size();
      this.isRemoteServer = !â˜ƒ.getConnection().isMemoryConnection();
      this.difficulty = â˜ƒ.getDifficulty().getKey();
      PlayerInfo â˜ƒ = â˜ƒ.getPlayerInfo(â˜ƒ.getUUID());
      if (â˜ƒ != null) {
         this.gameMode = â˜ƒ.getGameMode().getName();
      } else {
         this.gameMode = "unknown";
      }

      this.id = â˜ƒ.getId();
   }

   @Override
   public int getPlayerCount() {
      return this.players;
   }

   @Override
   public boolean isRemoteServer() {
      return this.isRemoteServer;
   }

   @Override
   public String getDifficulty() {
      return this.difficulty;
   }

   @Override
   public String getGameMode() {
      return this.gameMode;
   }

   @Override
   public UUID getSessionId() {
      return this.id;
   }
}
