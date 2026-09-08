package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Connection extends SimpleChannelInboundHandler<Packet<?>> {
   private static final float AVERAGE_PACKETS_SMOOTHING = 0.75F;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Marker ROOT_MARKER = MarkerManager.getMarker("NETWORK");
   public static final Marker PACKET_MARKER = MarkerManager.getMarker("NETWORK_PACKETS", ROOT_MARKER);
   public static final AttributeKey<ConnectionProtocol> ATTRIBUTE_PROTOCOL = AttributeKey.valueOf("protocol");
   public static final LazyLoadedValue<NioEventLoopGroup> NETWORK_WORKER_GROUP = new LazyLoadedValue<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build())
   );
   public static final LazyLoadedValue<EpollEventLoopGroup> NETWORK_EPOLL_WORKER_GROUP = new LazyLoadedValue<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build())
   );
   public static final LazyLoadedValue<DefaultEventLoopGroup> LOCAL_WORKER_GROUP = new LazyLoadedValue<>(
      () -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build())
   );
   private final PacketFlow receiving;
   private final Queue<Connection.PacketHolder> queue = Queues.<Connection.PacketHolder>newConcurrentLinkedQueue();
   private Channel channel;
   private SocketAddress address;
   private PacketListener packetListener;
   private Component disconnectedReason;
   private boolean encrypted;
   private boolean disconnectionHandled;
   private int receivedPackets;
   private int sentPackets;
   private float averageReceivedPackets;
   private float averageSentPackets;
   private int tickCount;
   private boolean handlingFault;

   public Connection(PacketFlow var1) {
      this.receiving = â˜ƒ;
   }

   @Override
   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(â˜ƒ);
      this.channel = â˜ƒ.channel();
      this.address = this.channel.remoteAddress();

      try {
         this.setProtocol(ConnectionProtocol.HANDSHAKING);
      } catch (Throwable var3) {
         LOGGER.fatal(var3);
      }
   }

   public void setProtocol(ConnectionProtocol var1) {
      this.channel.attr(ATTRIBUTE_PROTOCOL).set(â˜ƒ);
      this.channel.config().setAutoRead(true);
      LOGGER.debug("Enabled auto read");
   }

   @Override
   public void channelInactive(ChannelHandlerContext var1) {
      this.disconnect(new TranslatableComponent("disconnect.endOfStream"));
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
      if (â˜ƒ instanceof SkipPacketException) {
         LOGGER.debug("Skipping packet due to errors", â˜ƒ.getCause());
      } else {
         boolean â˜ƒ = !this.handlingFault;
         this.handlingFault = true;
         if (this.channel.isOpen()) {
            if (â˜ƒ instanceof TimeoutException) {
               LOGGER.debug("Timeout", â˜ƒ);
               this.disconnect(new TranslatableComponent("disconnect.timeout"));
            } else {
               Component â˜ƒx = new TranslatableComponent("disconnect.genericReason", "Internal Exception: " + â˜ƒ);
               if (â˜ƒ) {
                  LOGGER.debug("Failed to sent packet", â˜ƒ);
                  ConnectionProtocol â˜ƒxx = this.getCurrentProtocol();
                  Packet<?> â˜ƒxxx = (Packet<?>)(â˜ƒxx == ConnectionProtocol.LOGIN
                     ? new ClientboundLoginDisconnectPacket(â˜ƒx)
                     : new ClientboundDisconnectPacket(â˜ƒx));
                  this.send(â˜ƒxxx, var2x -> this.disconnect(â˜ƒ));
                  this.setReadOnly();
               } else {
                  LOGGER.debug("Double fault", â˜ƒ);
                  this.disconnect(â˜ƒx);
               }
            }
         }
      }
   }

   protected void channelRead0(ChannelHandlerContext var1, Packet<?> var2) {
      if (this.channel.isOpen()) {
         try {
            genericsFtw(â˜ƒ, this.packetListener);
         } catch (RunningOnDifferentThreadException var4) {
         } catch (ClassCastException var5) {
            LOGGER.error("Received {} that couldn't be processed", â˜ƒ.getClass(), var5);
            this.disconnect(new TranslatableComponent("multiplayer.disconnect.invalid_packet"));
         }

         ++this.receivedPackets;
      }
   }

   private static <T extends PacketListener> void genericsFtw(Packet<T> var0, PacketListener var1) {
      â˜ƒ.handle((T)â˜ƒ);
   }

   public void setListener(PacketListener var1) {
      Validate.notNull(â˜ƒ, "packetListener");
      this.packetListener = â˜ƒ;
   }

   public void send(Packet<?> var1) {
      this.send(â˜ƒ, null);
   }

   public void send(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      if (this.isConnected()) {
         this.flushQueue();
         this.sendPacket(â˜ƒ, â˜ƒ);
      } else {
         this.queue.add(new Connection.PacketHolder(â˜ƒ, â˜ƒ));
      }
   }

   private void sendPacket(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      ConnectionProtocol â˜ƒ = ConnectionProtocol.getProtocolForPacket(â˜ƒ);
      ConnectionProtocol â˜ƒx = this.getCurrentProtocol();
      ++this.sentPackets;
      if (â˜ƒx != â˜ƒ) {
         LOGGER.debug("Disabled auto read");
         this.channel.config().setAutoRead(false);
      }

      if (this.channel.eventLoop().inEventLoop()) {
         this.doSendPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
      } else {
         this.channel.eventLoop().execute(() -> this.doSendPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }

   private void doSendPacket(
      Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2, ConnectionProtocol var3, ConnectionProtocol var4
   ) {
      if (â˜ƒ != â˜ƒ) {
         this.setProtocol(â˜ƒ);
      }

      ChannelFuture â˜ƒ = this.channel.writeAndFlush(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.addListener(â˜ƒ);
      }

      â˜ƒ.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
   }

   private ConnectionProtocol getCurrentProtocol() {
      return (ConnectionProtocol)this.channel.attr(ATTRIBUTE_PROTOCOL).get();
   }

   private void flushQueue() {
      if (this.channel != null && this.channel.isOpen()) {
         synchronized(this.queue) {
            Connection.PacketHolder â˜ƒ;
            while((â˜ƒ = (Connection.PacketHolder)this.queue.poll()) != null) {
               this.sendPacket(â˜ƒ.packet, â˜ƒ.listener);
            }
         }
      }
   }

   public void tick() {
      this.flushQueue();
      if (this.packetListener instanceof ServerLoginPacketListenerImpl) {
         ((ServerLoginPacketListenerImpl)this.packetListener).tick();
      }

      if (this.packetListener instanceof ServerGamePacketListenerImpl) {
         ((ServerGamePacketListenerImpl)this.packetListener).tick();
      }

      if (!this.isConnected() && !this.disconnectionHandled) {
         this.handleDisconnection();
      }

      if (this.channel != null) {
         this.channel.flush();
      }

      if (this.tickCount++ % 20 == 0) {
         this.tickSecond();
      }
   }

   protected void tickSecond() {
      this.averageSentPackets = Mth.lerp(0.75F, (float)this.sentPackets, this.averageSentPackets);
      this.averageReceivedPackets = Mth.lerp(0.75F, (float)this.receivedPackets, this.averageReceivedPackets);
      this.sentPackets = 0;
      this.receivedPackets = 0;
   }

   public SocketAddress getRemoteAddress() {
      return this.address;
   }

   public void disconnect(Component var1) {
      if (this.channel.isOpen()) {
         this.channel.close().awaitUninterruptibly();
         this.disconnectedReason = â˜ƒ;
      }
   }

   public boolean isMemoryConnection() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   public PacketFlow getReceiving() {
      return this.receiving;
   }

   public PacketFlow getSending() {
      return this.receiving.getOpposite();
   }

   public static Connection connectToServer(InetSocketAddress var0, boolean var1) {
      final Connection â˜ƒxx = new Connection(PacketFlow.CLIENTBOUND);
      Class<? extends SocketChannel> â˜ƒ;
      LazyLoadedValue<? extends EventLoopGroup> â˜ƒx;
      if (Epoll.isAvailable() && â˜ƒ) {
         â˜ƒ = EpollSocketChannel.class;
         â˜ƒx = NETWORK_EPOLL_WORKER_GROUP;
      } else {
         â˜ƒ = NioSocketChannel.class;
         â˜ƒx = NETWORK_WORKER_GROUP;
      }

      new Bootstrap()
         .group(â˜ƒx.get())
         .handler(
            new ChannelInitializer<Channel>() {
               @Override
               protected void initChannel(Channel var1) {
                  try {
                     â˜ƒ.config().setOption(ChannelOption.TCP_NODELAY, true);
                  } catch (ChannelException var3) {
                  }
      
                  â˜ƒ.pipeline()
                     .addLast("timeout", new ReadTimeoutHandler(30))
                     .addLast("splitter", new Varint21FrameDecoder())
                     .addLast("decoder", new PacketDecoder(PacketFlow.CLIENTBOUND))
                     .addLast("prepender", new Varint21LengthFieldPrepender())
                     .addLast("encoder", new PacketEncoder(PacketFlow.SERVERBOUND))
                     .addLast("packet_handler", â˜ƒ);
               }
            }
         )
         .channel(â˜ƒ)
         .connect(â˜ƒ.getAddress(), â˜ƒ.getPort())
         .syncUninterruptibly();
      return â˜ƒxx;
   }

   public static Connection connectToLocalServer(SocketAddress var0) {
      final Connection â˜ƒ = new Connection(PacketFlow.CLIENTBOUND);
      new Bootstrap().group(LOCAL_WORKER_GROUP.get()).handler(new ChannelInitializer<Channel>() {
         @Override
         protected void initChannel(Channel var1x) {
            â˜ƒ.pipeline().addLast("packet_handler", â˜ƒ);
         }
      }).channel(LocalChannel.class).connect(â˜ƒ).syncUninterruptibly();
      return â˜ƒ;
   }

   public void setEncryptionKey(Cipher var1, Cipher var2) {
      this.encrypted = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new CipherDecoder(â˜ƒ));
      this.channel.pipeline().addBefore("prepender", "encrypt", new CipherEncoder(â˜ƒ));
   }

   public boolean isEncrypted() {
      return this.encrypted;
   }

   public boolean isConnected() {
      return this.channel != null && this.channel.isOpen();
   }

   public boolean isConnecting() {
      return this.channel == null;
   }

   public PacketListener getPacketListener() {
      return this.packetListener;
   }

   @Nullable
   public Component getDisconnectedReason() {
      return this.disconnectedReason;
   }

   public void setReadOnly() {
      this.channel.config().setAutoRead(false);
   }

   public void setupCompression(int var1, boolean var2) {
      if (â˜ƒ >= 0) {
         if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
            ((CompressionDecoder)this.channel.pipeline().get("decompress")).setThreshold(â˜ƒ, â˜ƒ);
         } else {
            this.channel.pipeline().addBefore("decoder", "decompress", new CompressionDecoder(â˜ƒ, â˜ƒ));
         }

         if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
            ((CompressionEncoder)this.channel.pipeline().get("compress")).setThreshold(â˜ƒ);
         } else {
            this.channel.pipeline().addBefore("encoder", "compress", new CompressionEncoder(â˜ƒ));
         }
      } else {
         if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
            this.channel.pipeline().remove("decompress");
         }

         if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
            this.channel.pipeline().remove("compress");
         }
      }
   }

   public void handleDisconnection() {
      if (this.channel != null && !this.channel.isOpen()) {
         if (this.disconnectionHandled) {
            LOGGER.warn("handleDisconnection() called twice");
         } else {
            this.disconnectionHandled = true;
            if (this.getDisconnectedReason() != null) {
               this.getPacketListener().onDisconnect(this.getDisconnectedReason());
            } else if (this.getPacketListener() != null) {
               this.getPacketListener().onDisconnect(new TranslatableComponent("multiplayer.disconnect.generic"));
            }
         }
      }
   }

   public float getAverageReceivedPackets() {
      return this.averageReceivedPackets;
   }

   public float getAverageSentPackets() {
      return this.averageSentPackets;
   }

   static class PacketHolder {
      final Packet<?> packet;
      @Nullable
      final GenericFutureListener<? extends Future<? super Void>> listener;

      public PacketHolder(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
         this.packet = â˜ƒ;
         this.listener = â˜ƒ;
      }
   }
}
