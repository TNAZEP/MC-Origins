package net.minecraft.network.protocol.login;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;

public class ServerboundKeyPacket implements Packet<ServerLoginPacketListener> {
   private final byte[] keybytes;
   private final byte[] nonce;

   public ServerboundKeyPacket(SecretKey var1, PublicKey var2, byte[] var3) throws CryptException {
      this.keybytes = Crypt.encryptUsingKey(â˜ƒ, â˜ƒ.getEncoded());
      this.nonce = Crypt.encryptUsingKey(â˜ƒ, â˜ƒ);
   }

   public ServerboundKeyPacket(FriendlyByteBuf var1) {
      this.keybytes = â˜ƒ.readByteArray();
      this.nonce = â˜ƒ.readByteArray();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByteArray(this.keybytes);
      â˜ƒ.writeByteArray(this.nonce);
   }

   public void handle(ServerLoginPacketListener var1) {
      â˜ƒ.handleKey(this);
   }

   public SecretKey getSecretKey(PrivateKey var1) throws CryptException {
      return Crypt.decryptByteToSecretKey(â˜ƒ, this.keybytes);
   }

   public byte[] getNonce(PrivateKey var1) throws CryptException {
      return Crypt.decryptUsingKey(â˜ƒ, this.nonce);
   }
}
