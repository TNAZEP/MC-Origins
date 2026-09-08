package net.minecraft.client.network;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.CPacketHandshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPinger {
   private static final Splitter field_147230_a = Splitter.on('\u0000').limit(6);
   private static final Logger field_147228_b = LogManager.getLogger();
   private final List<NetworkManager> field_147229_c = Collections.synchronizedList(Lists.newArrayList());

   public void func_147224_a(final ServerData var1) throws UnknownHostException {
      ServerAddress ☃ = ServerAddress.func_78860_a(☃.field_78845_b);
      final NetworkManager ☃x = NetworkManager.func_181124_a(InetAddress.getByName(☃.func_78861_a()), ☃.func_78864_b(), false);
      this.field_147229_c.add(☃x);
      ☃.field_78843_d = I18n.func_135052_a("multiplayer.status.pinging");
      ☃.field_78844_e = -1L;
      ☃.field_147412_i = null;
      ☃x.func_150719_a(
         new INetHandlerStatusClient() {
            private boolean field_147403_d;
            private boolean field_183009_e;
            private long field_175092_e;
   
            @Override
            public void func_147397_a(SPacketServerInfo var1x) {
               if (this.field_183009_e) {
                  ☃.func_150718_a(new TextComponentTranslation("multiplayer.status.unrequested"));
               } else {
                  this.field_183009_e = true;
                  ServerStatusResponse ☃ = ☃.func_149294_c();
                  if (☃.func_151317_a() != null) {
                     ☃.field_78843_d = ☃.func_151317_a().func_150254_d();
                  } else {
                     ☃.field_78843_d = "";
                  }
   
                  if (☃.func_151322_c() != null) {
                     ☃.field_82822_g = ☃.func_151322_c().func_151303_a();
                     ☃.field_82821_f = ☃.func_151322_c().func_151304_b();
                  } else {
                     ☃.field_82822_g = I18n.func_135052_a("multiplayer.status.old");
                     ☃.field_82821_f = 0;
                  }
   
                  if (☃.func_151318_b() != null) {
                     ☃.field_78846_c = TextFormatting.GRAY
                        + ""
                        + ☃.func_151318_b().func_151333_b()
                        + ""
                        + TextFormatting.DARK_GRAY
                        + "/"
                        + TextFormatting.GRAY
                        + ☃.func_151318_b().func_151332_a();
                     if (ArrayUtils.isNotEmpty(☃.func_151318_b().func_151331_c())) {
                        StringBuilder ☃ = new StringBuilder();
   
                        for(GameProfile ☃x : ☃.func_151318_b().func_151331_c()) {
                           if (☃.length() > 0) {
                              ☃.append("\n");
                           }
   
                           ☃.append(☃x.getName());
                        }
   
                        if (☃.func_151318_b().func_151331_c().length < ☃.func_151318_b().func_151333_b()) {
                           if (☃.length() > 0) {
                              ☃.append("\n");
                           }
   
                           ☃.append(
                              I18n.func_135052_a("multiplayer.status.and_more", ☃.func_151318_b().func_151333_b() - ☃.func_151318_b().func_151331_c().length)
                           );
                        }
   
                        ☃.field_147412_i = ☃.toString();
                     }
                  } else {
                     ☃.field_78846_c = TextFormatting.DARK_GRAY + I18n.func_135052_a("multiplayer.status.unknown");
                  }
   
                  if (☃.func_151316_d() != null) {
                     String ☃ = ☃.func_151316_d();
                     if (☃.startsWith("data:image/png;base64,")) {
                        ☃.func_147407_a(☃.substring("data:image/png;base64,".length()));
                     } else {
                        ServerPinger.field_147228_b.error("Invalid server icon (unknown format)");
                     }
                  } else {
                     ☃.func_147407_a(null);
                  }
   
                  this.field_175092_e = Util.func_211177_b();
                  ☃.func_179290_a(new CPacketPing(this.field_175092_e));
                  this.field_147403_d = true;
               }
            }
   
            @Override
            public void func_147398_a(SPacketPong var1x) {
               long ☃ = this.field_175092_e;
               long ☃x = Util.func_211177_b();
               ☃.field_78844_e = ☃x - ☃;
               ☃.func_150718_a(new TextComponentTranslation("multiplayer.status.finished"));
            }
   
            @Override
            public void func_147231_a(ITextComponent var1x) {
               if (!this.field_147403_d) {
                  ServerPinger.field_147228_b.error("Can't ping {}: {}", ☃.field_78845_b, ☃.getString());
                  ☃.field_78843_d = TextFormatting.DARK_RED + I18n.func_135052_a("multiplayer.status.cannot_connect");
                  ☃.field_78846_c = "";
                  ServerPinger.this.func_147225_b(☃);
               }
            }
         }
      );

      try {
         ☃x.func_179290_a(new CPacketHandshake(☃.func_78861_a(), ☃.func_78864_b(), EnumConnectionState.STATUS));
         ☃x.func_179290_a(new CPacketServerQuery());
      } catch (Throwable var5) {
         field_147228_b.error(var5);
      }
   }

   private void func_147225_b(final ServerData var1) {
      final ServerAddress ☃ = ServerAddress.func_78860_a(☃.field_78845_b);
      new Bootstrap().group(NetworkManager.field_179295_d.func_179281_c()).handler(new ChannelInitializer<Channel>() {
         @Override
         protected void initChannel(Channel var1x) throws Exception {
            try {
               ☃.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException var3) {
            }

            ☃.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
               @Override
               public void channelActive(ChannelHandlerContext var1x) throws Exception {
                  super.channelActive(☃);
                  ByteBuf ☃ = Unpooled.buffer();

                  try {
                     ☃.writeByte(254);
                     ☃.writeByte(1);
                     ☃.writeByte(250);
                     char[] ☃x = "MC|PingHost".toCharArray();
                     ☃.writeShort(☃x.length);

                     for(char ☃xx : ☃x) {
                        ☃.writeChar(☃xx);
                     }

                     ☃.writeShort(7 + 2 * ☃.func_78861_a().length());
                     ☃.writeByte(127);
                     ☃x = ☃.func_78861_a().toCharArray();
                     ☃.writeShort(☃x.length);

                     for(char ☃xx : ☃x) {
                        ☃.writeChar(☃xx);
                     }

                     ☃.writeInt(☃.func_78864_b());
                     ☃.channel().writeAndFlush(☃).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                  } finally {
                     ☃.release();
                  }
               }

               protected void channelRead0(ChannelHandlerContext var1x, ByteBuf var2x) throws Exception {
                  short ☃ = ☃.readUnsignedByte();
                  if (☃ == 255) {
                     String ☃x = new String(☃.readBytes(☃.readShort() * 2).array(), StandardCharsets.UTF_16BE);
                     String[] ☃xx = Iterables.toArray(ServerPinger.field_147230_a.split(☃x), String.class);
                     if ("\u00a71".equals(☃xx[0])) {
                        int ☃xxx = MathHelper.func_82715_a(☃xx[1], 0);
                        String ☃xxxx = ☃xx[2];
                        String ☃xxxxx = ☃xx[3];
                        int ☃xxxxxx = MathHelper.func_82715_a(☃xx[4], -1);
                        int ☃xxxxxxx = MathHelper.func_82715_a(☃xx[5], -1);
                        ☃.field_82821_f = -1;
                        ☃.field_82822_g = ☃xxxx;
                        ☃.field_78843_d = ☃xxxxx;
                        ☃.field_78846_c = TextFormatting.GRAY + "" + ☃xxxxxx + "" + TextFormatting.DARK_GRAY + "/" + TextFormatting.GRAY + ☃xxxxxxx;
                     }
                  }

                  ☃.close();
               }

               @Override
               public void exceptionCaught(ChannelHandlerContext var1x, Throwable var2x) throws Exception {
                  ☃.close();
               }
            });
         }
      }).channel(NioSocketChannel.class).connect(☃.func_78861_a(), ☃.func_78864_b());
   }

   public void func_147223_a() {
      synchronized(this.field_147229_c) {
         Iterator<NetworkManager> ☃ = this.field_147229_c.iterator();

         while(☃.hasNext()) {
            NetworkManager ☃x = (NetworkManager)☃.next();
            if (☃x.func_150724_d()) {
               ☃x.func_74428_b();
            } else {
               ☃.remove();
               ☃x.func_179293_l();
            }
         }
      }
   }

   public void func_147226_b() {
      synchronized(this.field_147229_c) {
         Iterator<NetworkManager> ☃ = this.field_147229_c.iterator();

         while(☃.hasNext()) {
            NetworkManager ☃x = (NetworkManager)☃.next();
            if (☃x.func_150724_d()) {
               ☃.remove();
               ☃x.func_150718_a(new TextComponentTranslation("multiplayer.status.cancelled"));
            }
         }
      }
   }
}
