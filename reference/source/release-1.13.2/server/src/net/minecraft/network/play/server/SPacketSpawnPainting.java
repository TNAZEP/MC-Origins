package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;

public class SPacketSpawnPainting implements Packet<INetHandlerPlayClient> {
   private int field_148973_a;
   private UUID field_186896_b;
   private BlockPos field_179838_b;
   private EnumFacing field_179839_c;
   private int field_148968_f;

   public SPacketSpawnPainting() {
   }

   public SPacketSpawnPainting(EntityPainting var1) {
      this.field_148973_a = ☃.func_145782_y();
      this.field_186896_b = ☃.func_110124_au();
      this.field_179838_b = ☃.func_174857_n();
      this.field_179839_c = ☃.field_174860_b;
      this.field_148968_f = IRegistry.field_212620_i.func_148757_b(☃.field_70522_e);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148973_a = ☃.func_150792_a();
      this.field_186896_b = ☃.func_179253_g();
      this.field_148968_f = ☃.func_150792_a();
      this.field_179838_b = ☃.func_179259_c();
      this.field_179839_c = EnumFacing.func_176731_b(☃.readUnsignedByte());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_148973_a);
      ☃.func_179252_a(this.field_186896_b);
      ☃.func_150787_b(this.field_148968_f);
      ☃.func_179255_a(this.field_179838_b);
      ☃.writeByte(this.field_179839_c.func_176736_b());
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147288_a(this);
   }
}
