package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class NettyCompressionDecoder extends ByteToMessageDecoder {
   private final Inflater field_179305_a;
   private int field_179304_b;

   public NettyCompressionDecoder(int var1) {
      this.field_179304_b = ☃;
      this.field_179305_a = new Inflater();
   }

   @Override
   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      if (☃.readableBytes() != 0) {
         PacketBuffer ☃ = new PacketBuffer(☃);
         int ☃x = ☃.func_150792_a();
         if (☃x == 0) {
            ☃.add(☃.readBytes(☃.readableBytes()));
         } else {
            if (☃x < this.field_179304_b) {
               throw new DecoderException("Badly compressed packet - size of " + ☃x + " is below server threshold of " + this.field_179304_b);
            }

            if (☃x > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + ☃x + " is larger than protocol maximum of " + 2097152);
            }

            byte[] ☃ = new byte[☃.readableBytes()];
            ☃.readBytes(☃);
            this.field_179305_a.setInput(☃);
            byte[] ☃x = new byte[☃x];
            this.field_179305_a.inflate(☃x);
            ☃.add(Unpooled.wrappedBuffer(☃x));
            this.field_179305_a.reset();
         }
      }
   }

   public void func_179303_a(int var1) {
      this.field_179304_b = ☃;
   }
}
