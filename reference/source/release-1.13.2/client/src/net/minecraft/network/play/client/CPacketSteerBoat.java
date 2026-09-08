package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketSteerBoat implements Packet<INetHandlerPlayServer> {
   private boolean field_187015_a;
   private boolean field_187016_b;

   public CPacketSteerBoat() {
   }

   public CPacketSteerBoat(boolean var1, boolean var2) {
      this.field_187015_a = ☃;
      this.field_187016_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_187015_a = ☃.readBoolean();
      this.field_187016_b = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeBoolean(this.field_187015_a);
      ☃.writeBoolean(this.field_187016_b);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_184340_a(this);
   }

   public boolean func_187012_a() {
      return this.field_187015_a;
   }

   public boolean func_187014_b() {
      return this.field_187016_b;
   }
}
