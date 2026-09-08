package net.minecraft.client.multiplayer;

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
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.status.ClientStatusPacketListener;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatusPinger {
   static final Splitter SPLITTER = Splitter.on('\u0000').limit(6);
   static final Logger LOGGER = LogManager.getLogger();
   private static final Component CANT_CONNECT_MESSAGE = new TranslatableComponent("multiplayer.status.cannot_connect").withStyle(ChatFormatting.DARK_RED);
   private final List<Connection> connections = Collections.synchronizedList(Lists.newArrayList());

   public void pingServer(final ServerData var1, final Runnable var2) throws UnknownHostException {
      ServerAddress â˜ƒ = ServerAddress.parseString(â˜ƒ.ip);
      Optional<InetSocketAddress> â˜ƒx = ServerNameResolver.DEFAULT.resolveAddress(â˜ƒ).map(ResolvedServerAddress::asInetSocketAddress);
      if (!â˜ƒx.isPresent()) {
         this.onPingFailed(ConnectScreen.UNKNOWN_HOST_MESSAGE, â˜ƒ);
      } else {
         final InetSocketAddress â˜ƒ = (InetSocketAddress)â˜ƒx.get();
         final Connection â˜ƒx = Connection.connectToServer(â˜ƒ, false);
         this.connections.add(â˜ƒx);
         â˜ƒ.motd = new TranslatableComponent("multiplayer.status.pinging");
         â˜ƒ.ping = -1L;
         â˜ƒ.playerList = null;
         â˜ƒx.setListener(
            new ClientStatusPacketListener() {
               private boolean success;
               private boolean receivedPing;
               private long pingStart;
   
               @Override
               public void handleStatusResponse(ClientboundStatusResponsePacket var1x) {
                  if (this.receivedPing) {
                     â˜ƒ.disconnect(new TranslatableComponent("multiplayer.status.unrequested"));
                  } else {
                     this.receivedPing = true;
                     ServerStatus â˜ƒ = â˜ƒ.getStatus();
                     if (â˜ƒ.getDescription() != null) {
                        â˜ƒ.motd = â˜ƒ.getDescription();
                     } else {
                        â˜ƒ.motd = TextComponent.EMPTY;
                     }
   
                     if (â˜ƒ.getVersion() != null) {
                        â˜ƒ.version = new TextComponent(â˜ƒ.getVersion().getName());
                        â˜ƒ.protocol = â˜ƒ.getVersion().getProtocol();
                     } else {
                        â˜ƒ.version = new TranslatableComponent("multiplayer.status.old");
                        â˜ƒ.protocol = 0;
                     }
   
                     if (â˜ƒ.getPlayers() != null) {
                        â˜ƒ.status = ServerStatusPinger.formatPlayerCount(â˜ƒ.getPlayers().getNumPlayers(), â˜ƒ.getPlayers().getMaxPlayers());
                        List<Component> â˜ƒ = Lists.<Component>newArrayList();
                        if (ArrayUtils.isNotEmpty(â˜ƒ.getPlayers().getSample())) {
                           for(GameProfile â˜ƒx : â˜ƒ.getPlayers().getSample()) {
                              â˜ƒ.add(new TextComponent(â˜ƒx.getName()));
                           }
   
                           if (â˜ƒ.getPlayers().getSample().length < â˜ƒ.getPlayers().getNumPlayers()) {
                              â˜ƒ.add(
                                 new TranslatableComponent(
                                    "multiplayer.status.and_more", â˜ƒ.getPlayers().getNumPlayers() - â˜ƒ.getPlayers().getSample().length
                                 )
                              );
                           }
   
                           â˜ƒ.playerList = â˜ƒ;
                        }
                     } else {
                        â˜ƒ.status = new TranslatableComponent("multiplayer.status.unknown").withStyle(ChatFormatting.DARK_GRAY);
                     }
   
                     String â˜ƒ = null;
                     if (â˜ƒ.getFavicon() != null) {
                        String â˜ƒx = â˜ƒ.getFavicon();
                        if (â˜ƒx.startsWith("data:image/png;base64,")) {
                           â˜ƒ = â˜ƒx.substring("data:image/png;base64,".length());
                        } else {
                           ServerStatusPinger.LOGGER.error("Invalid server icon (unknown format)");
                        }
                     }
   
                     if (!Objects.equals(â˜ƒ, â˜ƒ.getIconB64())) {
                        â˜ƒ.setIconB64(â˜ƒ);
                        â˜ƒ.run();
                     }
   
                     this.pingStart = Util.getMillis();
                     â˜ƒ.send(new ServerboundPingRequestPacket(this.pingStart));
                     this.success = true;
                  }
               }
   
               @Override
               public void handlePongResponse(ClientboundPongResponsePacket var1x) {
                  long â˜ƒ = this.pingStart;
                  long â˜ƒx = Util.getMillis();
                  â˜ƒ.ping = â˜ƒx - â˜ƒ;
                  â˜ƒ.disconnect(new TranslatableComponent("multiplayer.status.finished"));
               }
   
               @Override
               public void onDisconnect(Component var1x) {
                  if (!this.success) {
                     ServerStatusPinger.this.onPingFailed(â˜ƒ, â˜ƒ);
                     ServerStatusPinger.this.pingLegacyServer(â˜ƒ, â˜ƒ);
                  }
               }
   
               @Override
               public Connection getConnection() {
                  return â˜ƒ;
               }
            }
         );

         try {
            â˜ƒx.send(new ClientIntentionPacket(â˜ƒ.getHost(), â˜ƒ.getPort(), ConnectionProtocol.STATUS));
            â˜ƒx.send(new ServerboundStatusRequestPacket());
         } catch (Throwable var8) {
            LOGGER.error(var8);
         }
      }
   }

   void onPingFailed(Component var1, ServerData var2) {
      LOGGER.error("Can't ping {}: {}", â˜ƒ.ip, â˜ƒ.getString());
      â˜ƒ.motd = CANT_CONNECT_MESSAGE;
      â˜ƒ.status = TextComponent.EMPTY;
   }

   void pingLegacyServer(final InetSocketAddress var1, final ServerData var2) {
      new Bootstrap().group(Connection.NETWORK_WORKER_GROUP.get()).handler(new ChannelInitializer<Channel>() {
         @Override
         protected void initChannel(Channel var1x) {
            try {
               â˜ƒ.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException var3) {
            }

            â˜ƒ.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
               @Override
               public void channelActive(ChannelHandlerContext var1x) throws Exception {
                  super.channelActive(â˜ƒ);
                  ByteBuf â˜ƒ = Unpooled.buffer();

                  try {
                     â˜ƒ.writeByte(254);
                     â˜ƒ.writeByte(1);
                     â˜ƒ.writeByte(250);
                     char[] â˜ƒx = "MC|PingHost".toCharArray();
                     â˜ƒ.writeShort(â˜ƒx.length);

                     for(char â˜ƒxx : â˜ƒx) {
                        â˜ƒ.writeChar(â˜ƒxx);
                     }

                     â˜ƒ.writeShort(7 + 2 * â˜ƒ.getHostName().length());
                     â˜ƒ.writeByte(127);
                     â˜ƒx = â˜ƒ.getHostName().toCharArray();
                     â˜ƒ.writeShort(â˜ƒx.length);

                     for(char â˜ƒxx : â˜ƒx) {
                        â˜ƒ.writeChar(â˜ƒxx);
                     }

                     â˜ƒ.writeInt(â˜ƒ.getPort());
                     â˜ƒ.channel().writeAndFlush(â˜ƒ).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                  } finally {
                     â˜ƒ.release();
                  }
               }

               protected void channelRead0(ChannelHandlerContext var1x, ByteBuf var2x) {
                  short â˜ƒ = â˜ƒ.readUnsignedByte();
                  if (â˜ƒ == 255) {
                     String â˜ƒx = new String(â˜ƒ.readBytes(â˜ƒ.readShort() * 2).array(), StandardCharsets.UTF_16BE);
                     String[] â˜ƒxx = Iterables.toArray(ServerStatusPinger.SPLITTER.split(â˜ƒx), String.class);
                     if ("\u00a71".equals(â˜ƒxx[0])) {
                        int â˜ƒxxx = Mth.getInt(â˜ƒxx[1], 0);
                        String â˜ƒxxxx = â˜ƒxx[2];
                        String â˜ƒxxxxx = â˜ƒxx[3];
                        int â˜ƒxxxxxx = Mth.getInt(â˜ƒxx[4], -1);
                        int â˜ƒxxxxxxx = Mth.getInt(â˜ƒxx[5], -1);
                        â˜ƒ.protocol = -1;
                        â˜ƒ.version = new TextComponent(â˜ƒxxxx);
                        â˜ƒ.motd = new TextComponent(â˜ƒxxxxx);
                        â˜ƒ.status = ServerStatusPinger.formatPlayerCount(â˜ƒxxxxxx, â˜ƒxxxxxxx);
                     }
                  }

                  â˜ƒ.close();
               }

               @Override
               public void exceptionCaught(ChannelHandlerContext var1x, Throwable var2x) {
                  â˜ƒ.close();
               }
            });
         }
      }).channel(NioSocketChannel.class).connect(â˜ƒ.getAddress(), â˜ƒ.getPort());
   }

   static Component formatPlayerCount(int var0, int var1) {
      return new TextComponent(Integer.toString(â˜ƒ))
         .append(new TextComponent("/").withStyle(ChatFormatting.DARK_GRAY))
         .append(Integer.toString(â˜ƒ))
         .withStyle(ChatFormatting.GRAY);
   }

   public void tick() {
      synchronized(this.connections) {
         Iterator<Connection> â˜ƒ = this.connections.iterator();

         while(â˜ƒ.hasNext()) {
            Connection â˜ƒx = (Connection)â˜ƒ.next();
            if (â˜ƒx.isConnected()) {
               â˜ƒx.tick();
            } else {
               â˜ƒ.remove();
               â˜ƒx.handleDisconnection();
            }
         }
      }
   }

   public void removeAll() {
      synchronized(this.connections) {
         Iterator<Connection> â˜ƒ = this.connections.iterator();

         while(â˜ƒ.hasNext()) {
            Connection â˜ƒx = (Connection)â˜ƒ.next();
            if (â˜ƒx.isConnected()) {
               â˜ƒ.remove();
               â˜ƒx.disconnect(new TranslatableComponent("multiplayer.status.cancelled"));
            }
         }
      }
   }
}
