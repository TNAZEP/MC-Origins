package net.minecraft.server.players;

import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SleepStatus {
   private int activePlayers;
   private int sleepingPlayers;

   public boolean areEnoughSleeping(int var1) {
      return this.sleepingPlayers >= this.sleepersNeeded(â˜ƒ);
   }

   public boolean areEnoughDeepSleeping(int var1, List<ServerPlayer> var2) {
      int â˜ƒ = (int)â˜ƒ.stream().filter(Player::isSleepingLongEnough).count();
      return â˜ƒ >= this.sleepersNeeded(â˜ƒ);
   }

   public int sleepersNeeded(int var1) {
      return Math.max(1, Mth.ceil((float)(this.activePlayers * â˜ƒ) / 100.0F));
   }

   public void removeAllSleepers() {
      this.sleepingPlayers = 0;
   }

   public int amountSleeping() {
      return this.sleepingPlayers;
   }

   public boolean update(List<ServerPlayer> var1) {
      int â˜ƒ = this.activePlayers;
      int â˜ƒx = this.sleepingPlayers;
      this.activePlayers = 0;
      this.sleepingPlayers = 0;

      for(ServerPlayer â˜ƒxx : â˜ƒ) {
         if (!â˜ƒxx.isSpectator()) {
            ++this.activePlayers;
            if (â˜ƒxx.isSleeping()) {
               ++this.sleepingPlayers;
            }
         }
      }

      return (â˜ƒx > 0 || this.sleepingPlayers > 0) && (â˜ƒ != this.activePlayers || â˜ƒx != this.sleepingPlayers);
   }
}
