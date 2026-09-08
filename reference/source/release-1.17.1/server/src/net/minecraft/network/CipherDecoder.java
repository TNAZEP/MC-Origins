package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class CipherDecoder extends MessageToMessageDecoder<ByteBuf> {
   private final CipherBase cipher;

   public CipherDecoder(Cipher var1) {
      this.cipher = new CipherBase(â˜ƒ);
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      â˜ƒ.add(this.cipher.decipher(â˜ƒ, â˜ƒ));
   }
}
