package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class SPacketLoginSuccess implements Packet<INetHandlerLoginClient> {
   private GameProfile field_149602_a;

   public SPacketLoginSuccess() {
   }

   public SPacketLoginSuccess(GameProfile var1) {
      this.field_149602_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      String ☃ = ☃.func_150789_c(36);
      String ☃x = ☃.func_150789_c(16);
      UUID ☃xx = UUID.fromString(☃);
      this.field_149602_a = new GameProfile(☃xx, ☃x);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      UUID ☃ = this.field_149602_a.getId();
      ☃.func_180714_a(☃ == null ? "" : ☃.toString());
      ☃.func_180714_a(this.field_149602_a.getName());
   }

   public void func_148833_a(INetHandlerLoginClient var1) {
      ☃.func_147390_a(this);
   }
}
