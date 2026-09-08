package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class NettyEncryptingEncoder extends MessageToByteEncoder<ByteBuf> {
   private final NettyEncryptionTranslator field_150750_a;

   public NettyEncryptingEncoder(Cipher var1) {
      this.field_150750_a = new NettyEncryptionTranslator(☃);
   }

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
      this.field_150750_a.func_150504_a(☃, ☃);
   }
}
