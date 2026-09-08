package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketInput implements Packet<INetHandlerPlayServer> {
   private float field_149624_a;
   private float field_192621_b;
   private boolean field_149623_c;
   private boolean field_149621_d;

   public CPacketInput() {
   }

   public CPacketInput(float var1, float var2, boolean var3, boolean var4) {
      this.field_149624_a = ☃;
      this.field_192621_b = ☃;
      this.field_149623_c = ☃;
      this.field_149621_d = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149624_a = ☃.readFloat();
      this.field_192621_b = ☃.readFloat();
      byte ☃ = ☃.readByte();
      this.field_149623_c = (☃ & 1) > 0;
      this.field_149621_d = (☃ & 2) > 0;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeFloat(this.field_149624_a);
      ☃.writeFloat(this.field_192621_b);
      byte ☃ = 0;
      if (this.field_149623_c) {
         ☃ = (byte)(☃ | 1);
      }

      if (this.field_149621_d) {
         ☃ = (byte)(☃ | 2);
      }

      ☃.writeByte(☃);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147358_a(this);
   }

   public float func_149620_c() {
      return this.field_149624_a;
   }

   public float func_192620_b() {
      return this.field_192621_b;
   }

   public boolean func_149618_e() {
      return this.field_149623_c;
   }

   public boolean func_149617_f() {
      return this.field_149621_d;
   }
}
