package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class SPacketEncryptionRequest implements Packet<INetHandlerLoginClient> {
   private String field_149612_a;
   private PublicKey field_149610_b;
   private byte[] field_149611_c;

   public SPacketEncryptionRequest() {
   }

   public SPacketEncryptionRequest(String var1, PublicKey var2, byte[] var3) {
      this.field_149612_a = ☃;
      this.field_149610_b = ☃;
      this.field_149611_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149612_a = ☃.func_150789_c(20);
      this.field_149610_b = CryptManager.func_75896_a(☃.func_179251_a());
      this.field_149611_c = ☃.func_179251_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_149612_a);
      ☃.func_179250_a(this.field_149610_b.getEncoded());
      ☃.func_179250_a(this.field_149611_c);
   }

   public void func_148833_a(INetHandlerLoginClient var1) {
      ☃.func_147389_a(this);
   }
}
