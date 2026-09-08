package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {
   private final byte[] encodeBuf = new byte[8192];
   private final Deflater deflater;
   private int threshold;

   public CompressionEncoder(int var1) {
      this.threshold = â˜ƒ;
      this.deflater = new Deflater();
   }

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) {
      int â˜ƒ = â˜ƒ.readableBytes();
      FriendlyByteBuf â˜ƒx = new FriendlyByteBuf(â˜ƒ);
      if (â˜ƒ < this.threshold) {
         â˜ƒx.writeVarInt(0);
         â˜ƒx.writeBytes(â˜ƒ);
      } else {
         byte[] â˜ƒ = new byte[â˜ƒ];
         â˜ƒ.readBytes(â˜ƒ);
         â˜ƒx.writeVarInt(â˜ƒ.length);
         this.deflater.setInput(â˜ƒ, 0, â˜ƒ);
         this.deflater.finish();

         while(!this.deflater.finished()) {
            int â˜ƒx = this.deflater.deflate(this.encodeBuf);
            â˜ƒx.writeBytes(this.encodeBuf, 0, â˜ƒx);
         }

         this.deflater.reset();
      }
   }

   public int getThreshold() {
      return this.threshold;
   }

   public void setThreshold(int var1) {
      this.threshold = â˜ƒ;
   }
}
