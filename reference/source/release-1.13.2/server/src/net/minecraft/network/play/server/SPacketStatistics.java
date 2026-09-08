package net.minecraft.network.play.server;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.registry.IRegistry;

public class SPacketStatistics implements Packet<INetHandlerPlayClient> {
   private Object2IntMap<Stat<?>> field_148976_a;

   public SPacketStatistics() {
   }

   public SPacketStatistics(Object2IntMap<Stat<?>> var1) {
      this.field_148976_a = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147293_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      int ☃ = ☃.func_150792_a();
      this.field_148976_a = new Object2IntOpenHashMap<>(☃);

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.func_197684_a(IRegistry.field_212634_w.func_148754_a(☃.func_150792_a()), ☃);
      }
   }

   private <T> void func_197684_a(StatType<T> var1, PacketBuffer var2) {
      int ☃ = ☃.func_150792_a();
      int ☃x = ☃.func_150792_a();
      this.field_148976_a.put(☃.func_199076_b(☃.func_199080_a().func_148754_a(☃)), ☃x);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_148976_a.size());

      for(Entry<Stat<?>> ☃ : this.field_148976_a.object2IntEntrySet()) {
         Stat<?> ☃x = (Stat)☃.getKey();
         ☃.func_150787_b(IRegistry.field_212634_w.func_148757_b(☃x.func_197921_a()));
         ☃.func_150787_b(this.func_197683_a(☃x));
         ☃.func_150787_b(☃.getIntValue());
      }
   }

   private <T> int func_197683_a(Stat<T> var1) {
      return ☃.func_197921_a().func_199080_a().func_148757_b(☃.func_197920_b());
   }
}
