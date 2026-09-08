package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class NettyEncryptingDecoder extends MessageToMessageDecoder<ByteBuf> {
   private final NettyEncryptionTranslator field_150509_a;

   public NettyEncryptingDecoder(Cipher var1) {
      this.field_150509_a = new NettyEncryptionTranslator(☃);
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      ☃.add(this.field_150509_a.func_150503_a(☃, ☃));
   }
}
