package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class Varint21FrameDecoder extends ByteToMessageDecoder {
   @Override
   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) {
      â˜ƒ.markReaderIndex();
      byte[] â˜ƒ = new byte[3];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         if (!â˜ƒ.isReadable()) {
            â˜ƒ.resetReaderIndex();
            return;
         }

         â˜ƒ[â˜ƒx] = â˜ƒ.readByte();
         if (â˜ƒ[â˜ƒx] >= 0) {
            FriendlyByteBuf â˜ƒxx = new FriendlyByteBuf(Unpooled.wrappedBuffer(â˜ƒ));

            try {
               int â˜ƒxxx = â˜ƒxx.readVarInt();
               if (â˜ƒ.readableBytes() >= â˜ƒxxx) {
                  â˜ƒ.add(â˜ƒ.readBytes(â˜ƒxxx));
                  return;
               }

               â˜ƒ.resetReaderIndex();
            } finally {
               â˜ƒxx.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
