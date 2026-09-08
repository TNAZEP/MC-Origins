package net.minecraft.network.protocol.login;

import java.security.PublicKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;

public class ClientboundHelloPacket implements Packet<ClientLoginPacketListener> {
   private final String serverId;
   private final byte[] publicKey;
   private final byte[] nonce;

   public ClientboundHelloPacket(String var1, byte[] var2, byte[] var3) {
      this.serverId = â˜ƒ;
      this.publicKey = â˜ƒ;
      this.nonce = â˜ƒ;
   }

   public ClientboundHelloPacket(FriendlyByteBuf var1) {
      this.serverId = â˜ƒ.readUtf(20);
      this.publicKey = â˜ƒ.readByteArray();
      this.nonce = â˜ƒ.readByteArray();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.serverId);
      â˜ƒ.writeByteArray(this.publicKey);
      â˜ƒ.writeByteArray(this.nonce);
   }

   public void handle(ClientLoginPacketListener var1) {
      â˜ƒ.handleHello(this);
   }

   public String getServerId() {
      return this.serverId;
   }

   public PublicKey getPublicKey() throws CryptException {
      return Crypt.byteToPublicKey(this.publicKey);
   }

   public byte[] getNonce() {
      return this.nonce;
   }
}
