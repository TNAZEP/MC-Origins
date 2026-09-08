package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketSelectTrade implements Packet<INetHandlerPlayServer> {
   private int field_210354_a;

   public CPacketSelectTrade() {
   }

   public CPacketSelectTrade(int var1) {
      this.field_210354_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_210354_a = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_210354_a);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_210159_a(this);
   }

   public int func_210353_a() {
      return this.field_210354_a;
   }
}
