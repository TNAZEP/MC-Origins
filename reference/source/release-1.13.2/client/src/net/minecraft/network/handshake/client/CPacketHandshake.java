package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class CPacketHandshake implements Packet<INetHandlerHandshakeServer> {
   private int field_149600_a;
   private String field_149598_b;
   private int field_149599_c;
   private EnumConnectionState field_149597_d;

   public CPacketHandshake() {
   }

   public CPacketHandshake(String var1, int var2, EnumConnectionState var3) {
      this.field_149600_a = 404;
      this.field_149598_b = ☃;
      this.field_149599_c = ☃;
      this.field_149597_d = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149600_a = ☃.func_150792_a();
      this.field_149598_b = ☃.func_150789_c(255);
      this.field_149599_c = ☃.readUnsignedShort();
      this.field_149597_d = EnumConnectionState.func_150760_a(☃.func_150792_a());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149600_a);
      ☃.func_180714_a(this.field_149598_b);
      ☃.writeShort(this.field_149599_c);
      ☃.func_150787_b(this.field_149597_d.func_150759_c());
   }

   public void func_148833_a(INetHandlerHandshakeServer var1) {
      ☃.func_147383_a(this);
   }

   public EnumConnectionState func_149594_c() {
      return this.field_149597_d;
   }

   public int func_149595_d() {
      return this.field_149600_a;
   }
}
