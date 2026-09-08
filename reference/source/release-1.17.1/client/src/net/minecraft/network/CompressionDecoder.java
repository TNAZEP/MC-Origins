package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class CompressionDecoder extends ByteToMessageDecoder {
   public static final int MAXIMUM_COMPRESSED_LENGTH = 2097152;
   public static final int MAXIMUM_UNCOMPRESSED_LENGTH = 8388608;
   private final Inflater inflater;
   private int threshold;
   private boolean validateDecompressed;

   public CompressionDecoder(int var1, boolean var2) {
      this.threshold = â˜ƒ;
      this.validateDecompressed = â˜ƒ;
      this.inflater = new Inflater();
   }

   @Override
   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      if (â˜ƒ.readableBytes() != 0) {
         FriendlyByteBuf â˜ƒ = new FriendlyByteBuf(â˜ƒ);
         int â˜ƒx = â˜ƒ.readVarInt();
         if (â˜ƒx == 0) {
            â˜ƒ.add(â˜ƒ.readBytes(â˜ƒ.readableBytes()));
         } else {
            if (this.validateDecompressed) {
               if (â˜ƒx < this.threshold) {
                  throw new DecoderException("Badly compressed packet - size of " + â˜ƒx + " is below server threshold of " + this.threshold);
               }

               if (â˜ƒx > 8388608) {
                  throw new DecoderException("Badly compressed packet - size of " + â˜ƒx + " is larger than protocol maximum of 8388608");
               }
            }

            byte[] â˜ƒ = new byte[â˜ƒ.readableBytes()];
            â˜ƒ.readBytes(â˜ƒ);
            this.inflater.setInput(â˜ƒ);
            byte[] â˜ƒx = new byte[â˜ƒx];
            this.inflater.inflate(â˜ƒx);
            â˜ƒ.add(Unpooled.wrappedBuffer(â˜ƒx));
            this.inflater.reset();
         }
      }
   }

   public void setThreshold(int var1, boolean var2) {
      this.threshold = â˜ƒ;
      this.validateDecompressed = â˜ƒ;
   }
}
