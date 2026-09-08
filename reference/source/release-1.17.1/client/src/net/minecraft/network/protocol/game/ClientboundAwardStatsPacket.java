package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;

public class ClientboundAwardStatsPacket implements Packet<ClientGamePacketListener> {
   private final Object2IntMap<Stat<?>> stats;

   public ClientboundAwardStatsPacket(Object2IntMap<Stat<?>> var1) {
      this.stats = â˜ƒ;
   }

   public ClientboundAwardStatsPacket(FriendlyByteBuf var1) {
      this.stats = â˜ƒ.readMap(Object2IntOpenHashMap::new, var0 -> {
         int â˜ƒ = var0.readVarInt();
         int â˜ƒx = var0.readVarInt();
         return readStatCap(Registry.STAT_TYPE.byId(â˜ƒ), â˜ƒx);
      }, FriendlyByteBuf::readVarInt);
   }

   private static <T> Stat<T> readStatCap(StatType<T> var0, int var1) {
      return â˜ƒ.get(â˜ƒ.getRegistry().byId(â˜ƒ));
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAwardStats(this);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeMap(this.stats, (var1x, var2) -> {
         var1x.writeVarInt(Registry.STAT_TYPE.getId(var2.getType()));
         var1x.writeVarInt(this.getStatIdCap(var2));
      }, FriendlyByteBuf::writeVarInt);
   }

   private <T> int getStatIdCap(Stat<T> var1) {
      return â˜ƒ.getType().getRegistry().getId(â˜ƒ.getValue());
   }

   public Map<Stat<?>, Integer> getStats() {
      return this.stats;
   }
}
