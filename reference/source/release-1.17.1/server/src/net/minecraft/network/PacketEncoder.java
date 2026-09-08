package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Marker MARKER = MarkerManager.getMarker("PACKET_SENT", Connection.PACKET_MARKER);
   private final PacketFlow flow;

   public PacketEncoder(PacketFlow var1) {
      this.flow = â˜ƒ;
   }

   protected void encode(ChannelHandlerContext var1, Packet<?> var2, ByteBuf var3) throws Exception {
      ConnectionProtocol â˜ƒ = (ConnectionProtocol)â˜ƒ.channel().attr(Connection.ATTRIBUTE_PROTOCOL).get();
      if (â˜ƒ == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + â˜ƒ);
      } else {
         Integer â˜ƒ = â˜ƒ.getPacketId(this.flow, â˜ƒ);
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MARKER, "OUT: [{}:{}] {}", â˜ƒ.channel().attr(Connection.ATTRIBUTE_PROTOCOL).get(), â˜ƒ, â˜ƒ.getClass().getName());
         }

         if (â˜ƒ == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            FriendlyByteBuf â˜ƒ = new FriendlyByteBuf(â˜ƒ);
            â˜ƒ.writeVarInt(â˜ƒ);

            try {
               int â˜ƒx = â˜ƒ.writerIndex();
               â˜ƒ.write(â˜ƒ);
               int â˜ƒxx = â˜ƒ.writerIndex() - â˜ƒx;
               if (â˜ƒxx > 8388608) {
                  throw new IllegalArgumentException("Packet too big (is " + â˜ƒxx + ", should be less than 8388608): " + â˜ƒ);
               }
            } catch (Throwable var9) {
               LOGGER.error(var9);
               if (â˜ƒ.isSkippable()) {
                  throw new SkipPacketException(var9);
               } else {
                  throw var9;
               }
            }
         }
      }
   }
}
