package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityProperties implements Packet<INetHandlerPlayClient> {
   private int field_149445_a;
   private final List<SPacketEntityProperties.Snapshot> field_149444_b = Lists.<SPacketEntityProperties.Snapshot>newArrayList();

   public SPacketEntityProperties() {
   }

   public SPacketEntityProperties(int var1, Collection<IAttributeInstance> var2) {
      this.field_149445_a = ☃;

      for(IAttributeInstance ☃ : ☃) {
         this.field_149444_b.add(new SPacketEntityProperties.Snapshot(☃.func_111123_a().func_111108_a(), ☃.func_111125_b(), ☃.func_111122_c()));
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149445_a = ☃.func_150792_a();
      int ☃ = ☃.readInt();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         String ☃xx = ☃.func_150789_c(64);
         double ☃xxx = ☃.readDouble();
         List<AttributeModifier> ☃xxxx = Lists.<AttributeModifier>newArrayList();
         int ☃xxxxx = ☃.func_150792_a();

         for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxxx; ++☃xxxxxx) {
            UUID ☃xxxxxxx = ☃.func_179253_g();
            ☃xxxx.add(new AttributeModifier(☃xxxxxxx, "Unknown synced attribute modifier", ☃.readDouble(), ☃.readByte()));
         }

         this.field_149444_b.add(new SPacketEntityProperties.Snapshot(☃xx, ☃xxx, ☃xxxx));
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149445_a);
      ☃.writeInt(this.field_149444_b.size());

      for(SPacketEntityProperties.Snapshot ☃ : this.field_149444_b) {
         ☃.func_180714_a(☃.func_151409_a());
         ☃.writeDouble(☃.func_151410_b());
         ☃.func_150787_b(☃.func_151408_c().size());

         for(AttributeModifier ☃x : ☃.func_151408_c()) {
            ☃.func_179252_a(☃x.func_111167_a());
            ☃.writeDouble(☃x.func_111164_d());
            ☃.writeByte(☃x.func_111169_c());
         }
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147290_a(this);
   }

   public class Snapshot {
      private final String field_151412_b;
      private final double field_151413_c;
      private final Collection<AttributeModifier> field_151411_d;

      public Snapshot(String var2, double var3, Collection<AttributeModifier> var5) {
         this.field_151412_b = ☃;
         this.field_151413_c = ☃;
         this.field_151411_d = ☃;
      }

      public String func_151409_a() {
         return this.field_151412_b;
      }

      public double func_151410_b() {
         return this.field_151413_c;
      }

      public Collection<AttributeModifier> func_151408_c() {
         return this.field_151411_d;
      }
   }
}
