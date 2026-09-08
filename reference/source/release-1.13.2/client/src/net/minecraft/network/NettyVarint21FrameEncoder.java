package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class NettyVarint21FrameEncoder extends MessageToByteEncoder<ByteBuf> {
   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
      int ☃ = ☃.readableBytes();
      int ☃x = PacketBuffer.func_150790_a(☃);
      if (☃x > 3) {
         throw new IllegalArgumentException("unable to fit " + ☃ + " into " + 3);
      } else {
         PacketBuffer ☃ = new PacketBuffer(☃);
         ☃.ensureWritable(☃x + ☃);
         ☃.func_150787_b(☃);
         ☃.writeBytes(☃, ☃.readerIndex(), ☃);
      }
   }
}
