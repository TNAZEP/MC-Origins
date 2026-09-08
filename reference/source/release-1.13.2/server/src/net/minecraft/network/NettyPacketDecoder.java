package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketDecoder extends ByteToMessageDecoder {
   private static final Logger field_150800_a = LogManager.getLogger();
   private static final Marker field_150799_b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.field_150738_b);
   private final EnumPacketDirection field_152499_c;

   public NettyPacketDecoder(EnumPacketDirection var1) {
      this.field_152499_c = ☃;
   }

   @Override
   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      if (☃.readableBytes() != 0) {
         PacketBuffer ☃ = new PacketBuffer(☃);
         int ☃x = ☃.func_150792_a();
         Packet<?> ☃xx = ((EnumConnectionState)☃.channel().attr(NetworkManager.field_150739_c).get()).func_179244_a(this.field_152499_c, ☃x);
         if (☃xx == null) {
            throw new IOException("Bad packet id " + ☃x);
         } else {
            ☃xx.func_148837_a(☃);
            if (☃.readableBytes() > 0) {
               throw new IOException(
                  "Packet "
                     + ((EnumConnectionState)☃.channel().attr(NetworkManager.field_150739_c).get()).func_150759_c()
                     + "/"
                     + ☃x
                     + " ("
                     + ☃xx.getClass().getSimpleName()
                     + ") was larger than I expected, found "
                     + ☃.readableBytes()
                     + " bytes extra whilst reading packet "
                     + ☃x
               );
            } else {
               ☃.add(☃xx);
               if (field_150800_a.isDebugEnabled()) {
                  field_150800_a.debug(field_150799_b, " IN: [{}:{}] {}", ☃.channel().attr(NetworkManager.field_150739_c).get(), ☃x, ☃xx.getClass().getName());
               }
            }
         }
      }
   }
}
