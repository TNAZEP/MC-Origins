package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private static final Logger field_150798_a = LogManager.getLogger();
   private static final Marker field_150797_b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.field_150738_b);
   private final EnumPacketDirection field_152500_c;

   public NettyPacketEncoder(EnumPacketDirection var1) {
      this.field_152500_c = ☃;
   }

   protected void encode(ChannelHandlerContext var1, Packet<?> var2, ByteBuf var3) throws Exception {
      EnumConnectionState ☃ = (EnumConnectionState)☃.channel().attr(NetworkManager.field_150739_c).get();
      if (☃ == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + ☃);
      } else {
         Integer ☃ = ☃.func_179246_a(this.field_152500_c, ☃);
         if (field_150798_a.isDebugEnabled()) {
            field_150798_a.debug(field_150797_b, "OUT: [{}:{}] {}", ☃.channel().attr(NetworkManager.field_150739_c).get(), ☃, ☃.getClass().getName());
         }

         if (☃ == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            PacketBuffer ☃ = new PacketBuffer(☃);
            ☃.func_150787_b(☃);

            try {
               ☃.func_148840_b(☃);
            } catch (Throwable var8) {
               field_150798_a.error(var8);
               if (☃.func_211402_a()) {
                  throw new SkipableEncoderException(var8);
               } else {
                  throw var8;
               }
            }
         }
      }
   }
}
