package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class NettyCompressionEncoder extends MessageToByteEncoder<ByteBuf> {
   private final byte[] field_179302_a = new byte[8192];
   private final Deflater field_179300_b;
   private int field_179301_c;

   public NettyCompressionEncoder(int var1) {
      this.field_179301_c = ☃;
      this.field_179300_b = new Deflater();
   }

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
      int ☃ = ☃.readableBytes();
      PacketBuffer ☃x = new PacketBuffer(☃);
      if (☃ < this.field_179301_c) {
         ☃x.func_150787_b(0);
         ☃x.writeBytes(☃);
      } else {
         byte[] ☃ = new byte[☃];
         ☃.readBytes(☃);
         ☃x.func_150787_b(☃.length);
         this.field_179300_b.setInput(☃, 0, ☃);
         this.field_179300_b.finish();

         while(!this.field_179300_b.finished()) {
            int ☃x = this.field_179300_b.deflate(this.field_179302_a);
            ☃x.writeBytes(this.field_179302_a, 0, ☃x);
         }

         this.field_179300_b.reset();
      }
   }

   public void func_179299_a(int var1) {
      this.field_179301_c = ☃;
   }
}
