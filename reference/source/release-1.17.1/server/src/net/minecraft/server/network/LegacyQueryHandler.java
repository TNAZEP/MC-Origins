package net.minecraft.server.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyQueryHandler extends ChannelInboundHandlerAdapter {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int FAKE_PROTOCOL_VERSION = 127;
   private final ServerConnectionListener serverConnectionListener;

   public LegacyQueryHandler(ServerConnectionListener var1) {
      this.serverConnectionListener = â˜ƒ;
   }

   @Override
   public void channelRead(ChannelHandlerContext var1, Object var2) {
      ByteBuf â˜ƒ = (ByteBuf)â˜ƒ;
      â˜ƒ.markReaderIndex();
      boolean â˜ƒx = true;

      try {
         try {
            if (â˜ƒ.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress â˜ƒxx = (InetSocketAddress)â˜ƒ.channel().remoteAddress();
            MinecraftServer â˜ƒxxx = this.serverConnectionListener.getServer();
            int â˜ƒxxxx = â˜ƒ.readableBytes();
            switch(â˜ƒxxxx) {
               case 0:
                  LOGGER.debug("Ping: (<1.3.x) from {}:{}", â˜ƒxx.getAddress(), â˜ƒxx.getPort());
                  String â˜ƒxxxxx = String.format("%s\u00a7%d\u00a7%d", â˜ƒxxx.getMotd(), â˜ƒxxx.getPlayerCount(), â˜ƒxxx.getMaxPlayers());
                  this.sendFlushAndClose(â˜ƒ, this.createReply(â˜ƒxxxxx));
                  break;
               case 1:
                  if (â˜ƒ.readUnsignedByte() != 1) {
                     return;
                  }

                  LOGGER.debug("Ping: (1.4-1.5.x) from {}:{}", â˜ƒxx.getAddress(), â˜ƒxx.getPort());
                  String â˜ƒxxxxxx = String.format(
                     "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     â˜ƒxxx.getServerVersion(),
                     â˜ƒxxx.getMotd(),
                     â˜ƒxxx.getPlayerCount(),
                     â˜ƒxxx.getMaxPlayers()
                  );
                  this.sendFlushAndClose(â˜ƒ, this.createReply(â˜ƒxxxxxx));
                  break;
               default:
                  boolean â˜ƒxxxxxx = â˜ƒ.readUnsignedByte() == 1;
                  â˜ƒxxxxxx &= â˜ƒ.readUnsignedByte() == 250;
                  â˜ƒxxxxxx &= "MC|PingHost".equals(new String(â˜ƒ.readBytes(â˜ƒ.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                  int â˜ƒxxxxxxx = â˜ƒ.readUnsignedShort();
                  â˜ƒxxxxxx &= â˜ƒ.readUnsignedByte() >= 73;
                  â˜ƒxxxxxx &= 3 + â˜ƒ.readBytes(â˜ƒ.readShort() * 2).array().length + 4 == â˜ƒxxxxxxx;
                  â˜ƒxxxxxx &= â˜ƒ.readInt() <= 65535;
                  â˜ƒxxxxxx &= â˜ƒ.readableBytes() == 0;
                  if (!â˜ƒxxxxxx) {
                     return;
                  }

                  LOGGER.debug("Ping: (1.6) from {}:{}", â˜ƒxx.getAddress(), â˜ƒxx.getPort());
                  String â˜ƒxxxxxx = String.format(
                     "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     â˜ƒxxx.getServerVersion(),
                     â˜ƒxxx.getMotd(),
                     â˜ƒxxx.getPlayerCount(),
                     â˜ƒxxx.getMaxPlayers()
                  );
                  ByteBuf â˜ƒxxxxxxx = this.createReply(â˜ƒxxxxxx);

                  try {
                     this.sendFlushAndClose(â˜ƒ, â˜ƒxxxxxxx);
                  } finally {
                     â˜ƒxxxxxxx.release();
                  }
            }

            â˜ƒ.release();
            â˜ƒx = false;
         } catch (RuntimeException var21) {
         }
      } finally {
         if (â˜ƒx) {
            â˜ƒ.resetReaderIndex();
            â˜ƒ.channel().pipeline().remove("legacy_query");
            â˜ƒ.fireChannelRead(â˜ƒ);
         }
      }
   }

   private void sendFlushAndClose(ChannelHandlerContext var1, ByteBuf var2) {
      â˜ƒ.pipeline().firstContext().writeAndFlush(â˜ƒ).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf createReply(String var1) {
      ByteBuf â˜ƒ = Unpooled.buffer();
      â˜ƒ.writeByte(255);
      char[] â˜ƒx = â˜ƒ.toCharArray();
      â˜ƒ.writeShort(â˜ƒx.length);

      for(char â˜ƒxx : â˜ƒx) {
         â˜ƒ.writeChar(â˜ƒxx);
      }

      return â˜ƒ;
   }
}
