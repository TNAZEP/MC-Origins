package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class Varint21LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
   private static final int MAX_BYTES = 3;

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) {
      int â˜ƒ = â˜ƒ.readableBytes();
      int â˜ƒx = FriendlyByteBuf.getVarIntSize(â˜ƒ);
      if (â˜ƒx > 3) {
         throw new IllegalArgumentException("unable to fit " + â˜ƒ + " into 3");
      } else {
         FriendlyByteBuf â˜ƒ = new FriendlyByteBuf(â˜ƒ);
         â˜ƒ.ensureWritable(â˜ƒx + â˜ƒ);
         â˜ƒ.writeVarInt(â˜ƒ);
         â˜ƒ.writeBytes(â˜ƒ, â˜ƒ.readerIndex(), â˜ƒ);
      }
   }
}
