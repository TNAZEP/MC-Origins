package net.minecraft.server.level;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;

public final class PlayerMap {
   private final Object2BooleanMap<ServerPlayer> players = new Object2BooleanOpenHashMap<>();

   public Stream<ServerPlayer> getPlayers(long var1) {
      return this.players.keySet().stream();
   }

   public void addPlayer(long var1, ServerPlayer var3, boolean var4) {
      this.players.put(â˜ƒ, â˜ƒ);
   }

   public void removePlayer(long var1, ServerPlayer var3) {
      this.players.removeBoolean(â˜ƒ);
   }

   public void ignorePlayer(ServerPlayer var1) {
      this.players.replace(â˜ƒ, true);
   }

   public void unIgnorePlayer(ServerPlayer var1) {
      this.players.replace(â˜ƒ, false);
   }

   public boolean ignoredOrUnknown(ServerPlayer var1) {
      return this.players.getOrDefault(â˜ƒ, true);
   }

   public boolean ignored(ServerPlayer var1) {
      return this.players.getBoolean(â˜ƒ);
   }

   public void updatePlayer(long var1, long var3, ServerPlayer var5) {
   }
}
