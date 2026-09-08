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
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.CryptManager;
import net.minecraft.util.ITickable;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<?>> {
   private static final Logger field_150735_g = LogManager.getLogger();
   public static final Marker field_150740_a = MarkerManager.getMarker("NETWORK");
   public static final Marker field_150738_b = MarkerManager.getMarker("NETWORK_PACKETS", field_150740_a);
   public static final AttributeKey<EnumConnectionState> field_150739_c = AttributeKey.valueOf("protocol");
   public static final LazyLoadBase<NioEventLoopGroup> field_179295_d = new LazyLoadBase<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build())
   );
   public static final LazyLoadBase<EpollEventLoopGroup> field_181125_e = new LazyLoadBase<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build())
   );
   public static final LazyLoadBase<DefaultEventLoopGroup> field_179296_e = new LazyLoadBase<>(
      () -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build())
   );
   private final EnumPacketDirection field_179294_g;
   private final Queue<NetworkManager.QueuedPacket> field_150745_j = Queues.<NetworkManager.QueuedPacket>newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
   private Channel field_150746_k;
   private SocketAddress field_150743_l;
   private INetHandler field_150744_m;
   private ITextComponent field_150742_o;
   private boolean field_152463_r;
   private boolean field_179297_n;
   private int field_211394_q;
   private int field_211395_r;
   private float field_211396_s;
   private float field_211397_t;
   private int field_211398_u;
   private boolean field_211399_v;

   public NetworkManager(EnumPacketDirection var1) {
      this.field_179294_g = ☃;
   }

   @Override
   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(☃);
      this.field_150746_k = ☃.channel();
      this.field_150743_l = this.field_150746_k.remoteAddress();

      try {
         this.func_150723_a(EnumConnectionState.HANDSHAKING);
      } catch (Throwable var3) {
         field_150735_g.fatal(var3);
      }
   }

   public void func_150723_a(EnumConnectionState var1) {
      this.field_150746_k.attr(field_150739_c).set(☃);
      this.field_150746_k.config().setAutoRead(true);
      field_150735_g.debug("Enabled auto read");
   }

   @Override
   public void channelInactive(ChannelHandlerContext var1) throws Exception {
      this.func_150718_a(new TextComponentTranslation("disconnect.endOfStream"));
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
      if (☃ instanceof SkipableEncoderException) {
         field_150735_g.debug("Skipping packet due to errors", ☃.getCause());
      } else {
         boolean ☃ = !this.field_211399_v;
         this.field_211399_v = true;
         if (this.field_150746_k.isOpen()) {
            if (☃ instanceof TimeoutException) {
               field_150735_g.debug("Timeout", ☃);
               this.func_150718_a(new TextComponentTranslation("disconnect.timeout"));
            } else {
               ITextComponent ☃x = new TextComponentTranslation("disconnect.genericReason", "Internal Exception: " + ☃);
               if (☃) {
                  field_150735_g.debug("Failed to sent packet", ☃);
                  this.func_201058_a(new SPacketDisconnect(☃x), var2x -> this.func_150718_a(☃));
                  this.func_150721_g();
               } else {
                  field_150735_g.debug("Double fault", ☃);
                  this.func_150718_a(☃x);
               }
            }
         }
      }
   }

   protected void channelRead0(ChannelHandlerContext var1, Packet<?> var2) throws Exception {
      if (this.field_150746_k.isOpen()) {
         try {
            func_197664_a(☃, this.field_150744_m);
         } catch (ThreadQuickExitException var4) {
         }

         ++this.field_211394_q;
      }
   }

   private static <T extends INetHandler> void func_197664_a(Packet<T> var0, INetHandler var1) {
      ☃.func_148833_a((T)☃);
   }

   public void func_150719_a(INetHandler var1) {
      Validate.notNull(☃, "packetListener");
      field_150735_g.debug("Set listener of {} to {}", this, ☃);
      this.field_150744_m = ☃;
   }

   public void func_179290_a(Packet<?> var1) {
      this.func_201058_a(☃, null);
   }

   public void func_201058_a(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      if (this.func_150724_d()) {
         this.func_150733_h();
         this.func_150732_b(☃, ☃);
      } else {
         this.field_181680_j.writeLock().lock();

         try {
            this.field_150745_j.add(new NetworkManager.QueuedPacket(☃, ☃));
         } finally {
            this.field_181680_j.writeLock().unlock();
         }
      }
   }

   private void func_150732_b(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      EnumConnectionState ☃ = EnumConnectionState.func_150752_a(☃);
      EnumConnectionState ☃x = (EnumConnectionState)this.field_150746_k.attr(field_150739_c).get();
      ++this.field_211395_r;
      if (☃x != ☃) {
         field_150735_g.debug("Disabled auto read");
         this.field_150746_k.config().setAutoRead(false);
      }

      if (this.field_150746_k.eventLoop().inEventLoop()) {
         if (☃ != ☃x) {
            this.func_150723_a(☃);
         }

         ChannelFuture ☃ = this.field_150746_k.writeAndFlush(☃);
         if (☃ != null) {
            ☃.addListener(☃);
         }

         ☃.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.field_150746_k.eventLoop().execute(() -> {
            if (☃ != ☃) {
               this.func_150723_a(☃);
            }

            ChannelFuture ☃ = this.field_150746_k.writeAndFlush(☃);
            if (☃ != null) {
               ☃.addListener(☃);
            }

            ☃.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
         });
      }
   }

   private void func_150733_h() {
      if (this.field_150746_k != null && this.field_150746_k.isOpen()) {
         this.field_181680_j.readLock().lock();

         try {
            while(!this.field_150745_j.isEmpty()) {
               NetworkManager.QueuedPacket ☃ = (NetworkManager.QueuedPacket)this.field_150745_j.poll();
               this.func_150732_b(☃.field_150774_a, ☃.field_201049_b);
            }
         } finally {
            this.field_181680_j.readLock().unlock();
         }
      }
   }

   public void func_74428_b() {
      this.func_150733_h();
      if (this.field_150744_m instanceof ITickable) {
         ((ITickable)this.field_150744_m).func_73660_a();
      }

      if (this.field_150746_k != null) {
         this.field_150746_k.flush();
      }

      if (this.field_211398_u++ % 20 == 0) {
         this.field_211397_t = this.field_211397_t * 0.75F + (float)this.field_211395_r * 0.25F;
         this.field_211396_s = this.field_211396_s * 0.75F + (float)this.field_211394_q * 0.25F;
         this.field_211395_r = 0;
         this.field_211394_q = 0;
      }
   }

   public SocketAddress func_74430_c() {
      return this.field_150743_l;
   }

   public void func_150718_a(ITextComponent var1) {
      if (this.field_150746_k.isOpen()) {
         this.field_150746_k.close().awaitUninterruptibly();
         this.field_150742_o = ☃;
      }
   }

   public boolean func_150731_c() {
      return this.field_150746_k instanceof LocalChannel || this.field_150746_k instanceof LocalServerChannel;
   }

   public static NetworkManager func_181124_a(InetAddress var0, int var1, boolean var2) {
      final NetworkManager ☃xx = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      Class<? extends SocketChannel> ☃;
      LazyLoadBase<? extends EventLoopGroup> ☃x;
      if (Epoll.isAvailable() && ☃) {
         ☃ = EpollSocketChannel.class;
         ☃x = field_181125_e;
      } else {
         ☃ = NioSocketChannel.class;
         ☃x = field_179295_d;
      }

      new Bootstrap()
         .group(☃x.func_179281_c())
         .handler(
            new ChannelInitializer<Channel>() {
               @Override
               protected void initChannel(Channel var1) throws Exception {
                  try {
                     ☃.config().setOption(ChannelOption.TCP_NODELAY, true);
                  } catch (ChannelException var3x) {
                  }
      
                  ☃.pipeline()
                     .addLast("timeout", new ReadTimeoutHandler(30))
                     .addLast("splitter", new NettyVarint21FrameDecoder())
                     .addLast("decoder", new NettyPacketDecoder(EnumPacketDirection.CLIENTBOUND))
                     .addLast("prepender", new NettyVarint21FrameEncoder())
                     .addLast("encoder", new NettyPacketEncoder(EnumPacketDirection.SERVERBOUND))
                     .addLast("packet_handler", ☃);
               }
            }
         )
         .channel(☃)
         .connect(☃, ☃)
         .syncUninterruptibly();
      return ☃xx;
   }

   public static NetworkManager func_150722_a(SocketAddress var0) {
      final NetworkManager ☃ = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      new Bootstrap().group(field_179296_e.func_179281_c()).handler(new ChannelInitializer<Channel>() {
         @Override
         protected void initChannel(Channel var1x) throws Exception {
            ☃.pipeline().addLast("packet_handler", ☃);
         }
      }).channel(LocalChannel.class).connect(☃).syncUninterruptibly();
      return ☃;
   }

   public void func_150727_a(SecretKey var1) {
      this.field_152463_r = true;
      this.field_150746_k.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.func_151229_a(2, ☃)));
      this.field_150746_k.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.func_151229_a(1, ☃)));
   }

   public boolean func_179292_f() {
      return this.field_152463_r;
   }

   public boolean func_150724_d() {
      return this.field_150746_k != null && this.field_150746_k.isOpen();
   }

   public boolean func_179291_h() {
      return this.field_150746_k == null;
   }

   public INetHandler func_150729_e() {
      return this.field_150744_m;
   }

   @Nullable
   public ITextComponent func_150730_f() {
      return this.field_150742_o;
   }

   public void func_150721_g() {
      this.field_150746_k.config().setAutoRead(false);
   }

   public void func_179289_a(int var1) {
      if (☃ >= 0) {
         if (this.field_150746_k.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder)this.field_150746_k.pipeline().get("decompress")).func_179303_a(☃);
         } else {
            this.field_150746_k.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(☃));
         }

         if (this.field_150746_k.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder)this.field_150746_k.pipeline().get("compress")).func_179299_a(☃);
         } else {
            this.field_150746_k.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(☃));
         }
      } else {
         if (this.field_150746_k.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            this.field_150746_k.pipeline().remove("decompress");
         }

         if (this.field_150746_k.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            this.field_150746_k.pipeline().remove("compress");
         }
      }
   }

   public void func_179293_l() {
      if (this.field_150746_k != null && !this.field_150746_k.isOpen()) {
         if (this.field_179297_n) {
            field_150735_g.warn("handleDisconnection() called twice");
         } else {
            this.field_179297_n = true;
            if (this.func_150730_f() != null) {
               this.func_150729_e().func_147231_a(this.func_150730_f());
            } else if (this.func_150729_e() != null) {
               this.func_150729_e().func_147231_a(new TextComponentTranslation("multiplayer.disconnect.generic"));
            }
         }
      }
   }

   public float func_211393_m() {
      return this.field_211396_s;
   }

   public float func_211390_n() {
      return this.field_211397_t;
   }

   static class QueuedPacket {
      private final Packet<?> field_150774_a;
      @Nullable
      private final GenericFutureListener<? extends Future<? super Void>> field_201049_b;

      public QueuedPacket(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
         this.field_150774_a = ☃;
         this.field_201049_b = ☃;
      }
   }
}
