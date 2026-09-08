package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class CPacketLoginStart implements Packet<INetHandlerLoginServer> {
   private GameProfile field_149305_a;

   public CPacketLoginStart() {
   }

   public CPacketLoginStart(GameProfile var1) {
      this.field_149305_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149305_a = new GameProfile(null, ☃.func_150789_c(16));
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_149305_a.getName());
   }

   public void func_148833_a(INetHandlerLoginServer var1) {
      ☃.func_147316_a(this);
   }

   public GameProfile func_149304_c() {
      return this.field_149305_a;
   }
}
