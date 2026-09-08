package net.minecraft.network;

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

public class LegacyPingHandler extends ChannelInboundHandlerAdapter {
   private static final Logger field_151258_a = LogManager.getLogger();
   private final NetworkSystem field_151257_b;

   public LegacyPingHandler(NetworkSystem var1) {
      this.field_151257_b = ☃;
   }

   @Override
   public void channelRead(ChannelHandlerContext var1, Object var2) throws Exception {
      ByteBuf ☃ = (ByteBuf)☃;
      ☃.markReaderIndex();
      boolean ☃x = true;

      try {
         try {
            if (☃.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress ☃xx = (InetSocketAddress)☃.channel().remoteAddress();
            MinecraftServer ☃xxx = this.field_151257_b.func_151267_d();
            int ☃xxxx = ☃.readableBytes();
            switch(☃xxxx) {
               case 0:
                  field_151258_a.debug("Ping: (<1.3.x) from {}:{}", ☃xx.getAddress(), ☃xx.getPort());
                  String ☃xxxxx = String.format("%s\u00a7%d\u00a7%d", ☃xxx.func_71273_Y(), ☃xxx.func_71233_x(), ☃xxx.func_71275_y());
                  this.func_151256_a(☃, this.func_151255_a(☃xxxxx));
                  break;
               case 1:
                  if (☃.readUnsignedByte() != 1) {
                     return;
                  }

                  field_151258_a.debug("Ping: (1.4-1.5.x) from {}:{}", ☃xx.getAddress(), ☃xx.getPort());
                  String ☃xxxxxx = String.format(
                     "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, ☃xxx.func_71249_w(), ☃xxx.func_71273_Y(), ☃xxx.func_71233_x(), ☃xxx.func_71275_y()
                  );
                  this.func_151256_a(☃, this.func_151255_a(☃xxxxxx));
                  break;
               default:
                  boolean ☃xxxxxx = ☃.readUnsignedByte() == 1;
                  ☃xxxxxx &= ☃.readUnsignedByte() == 250;
                  ☃xxxxxx &= "MC|PingHost".equals(new String(☃.readBytes(☃.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                  int ☃xxxxxxx = ☃.readUnsignedShort();
                  ☃xxxxxx &= ☃.readUnsignedByte() >= 73;
                  ☃xxxxxx &= 3 + ☃.readBytes(☃.readShort() * 2).array().length + 4 == ☃xxxxxxx;
                  ☃xxxxxx &= ☃.readInt() <= 65535;
                  ☃xxxxxx &= ☃.readableBytes() == 0;
                  if (!☃xxxxxx) {
                     return;
                  }

                  field_151258_a.debug("Ping: (1.6) from {}:{}", ☃xx.getAddress(), ☃xx.getPort());
                  String ☃xxxxxx = String.format(
                     "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, ☃xxx.func_71249_w(), ☃xxx.func_71273_Y(), ☃xxx.func_71233_x(), ☃xxx.func_71275_y()
                  );
                  ByteBuf ☃xxxxxxx = this.func_151255_a(☃xxxxxx);

                  try {
                     this.func_151256_a(☃, ☃xxxxxxx);
                  } finally {
                     ☃xxxxxxx.release();
                  }
            }

            ☃.release();
            ☃x = false;
         } catch (RuntimeException var21) {
         }
      } finally {
         if (☃x) {
            ☃.resetReaderIndex();
            ☃.channel().pipeline().remove("legacy_query");
            ☃.fireChannelRead(☃);
         }
      }
   }

   private void func_151256_a(ChannelHandlerContext var1, ByteBuf var2) {
      ☃.pipeline().firstContext().writeAndFlush(☃).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf func_151255_a(String var1) {
      ByteBuf ☃ = Unpooled.buffer();
      ☃.writeByte(255);
      char[] ☃x = ☃.toCharArray();
      ☃.writeShort(☃x.length);

      for(char ☃xx : ☃x) {
         ☃.writeChar(☃xx);
      }

      return ☃;
   }
}
