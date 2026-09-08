package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
   private static final Logger field_151275_b = LogManager.getLogger();
   public static final LazyLoadBase<NioEventLoopGroup> field_151276_c = new LazyLoadBase<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build())
   );
   public static final LazyLoadBase<EpollEventLoopGroup> field_181141_b = new LazyLoadBase<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build())
   );
   private final MinecraftServer field_151273_d;
   public volatile boolean field_151277_a;
   private final List<ChannelFuture> field_151274_e = Collections.synchronizedList(Lists.newArrayList());
   private final List<NetworkManager> field_151272_f = Collections.synchronizedList(Lists.newArrayList());

   public NetworkSystem(MinecraftServer var1) {
      this.field_151273_d = ☃;
      this.field_151277_a = true;
   }

   public void func_151265_a(@Nullable InetAddress var1, int var2) throws IOException {
      synchronized(this.field_151274_e) {
         Class<? extends ServerSocketChannel> ☃;
         LazyLoadBase<? extends EventLoopGroup> ☃x;
         if (Epoll.isAvailable() && this.field_151273_d.func_181035_ah()) {
            ☃ = EpollServerSocketChannel.class;
            ☃x = field_181141_b;
            field_151275_b.info("Using epoll channel type");
         } else {
            ☃ = NioServerSocketChannel.class;
            ☃x = field_151276_c;
            field_151275_b.info("Using default channel type");
         }

         this.field_151274_e
            .add(
               new ServerBootstrap()
                  .channel(☃)
                  .childHandler(
                     new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel var1) throws Exception {
                           try {
                              ☃.config().setOption(ChannelOption.TCP_NODELAY, true);
                           } catch (ChannelException var3) {
                           }
            
                           ☃.pipeline()
                              .addLast("timeout", new ReadTimeoutHandler(30))
                              .addLast("legacy_query", new LegacyPingHandler(NetworkSystem.this))
                              .addLast("splitter", new NettyVarint21FrameDecoder())
                              .addLast("decoder", new NettyPacketDecoder(EnumPacketDirection.SERVERBOUND))
                              .addLast("prepender", new NettyVarint21FrameEncoder())
                              .addLast("encoder", new NettyPacketEncoder(EnumPacketDirection.CLIENTBOUND));
                           NetworkManager ☃ = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                           NetworkSystem.this.field_151272_f.add(☃);
                           ☃.pipeline().addLast("packet_handler", ☃);
                           ☃.func_150719_a(new NetHandlerHandshakeTCP(NetworkSystem.this.field_151273_d, ☃));
                        }
                     }
                  )
                  .group(☃x.func_179281_c())
                  .localAddress(☃, ☃)
                  .bind()
                  .syncUninterruptibly()
            );
      }
   }

   public SocketAddress func_151270_a() {
      ChannelFuture ☃;
      synchronized(this.field_151274_e) {
         ☃ = new ServerBootstrap().channel(LocalServerChannel.class).childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel var1) throws Exception {
               NetworkManager ☃ = new NetworkManager(EnumPacketDirection.SERVERBOUND);
               ☃.func_150719_a(new NetHandlerHandshakeMemory(NetworkSystem.this.field_151273_d, ☃));
               NetworkSystem.this.field_151272_f.add(☃);
               ☃.pipeline().addLast("packet_handler", ☃);
            }
         }).group(field_151276_c.func_179281_c()).localAddress(LocalAddress.ANY).bind().syncUninterruptibly();
         this.field_151274_e.add(☃);
      }

      return ☃.channel().localAddress();
   }

   public void func_151268_b() {
      this.field_151277_a = false;

      for(ChannelFuture ☃ : this.field_151274_e) {
         try {
            ☃.channel().close().sync();
         } catch (InterruptedException var4) {
            field_151275_b.error("Interrupted whilst closing channel");
         }
      }
   }

   public void func_151269_c() {
      synchronized(this.field_151272_f) {
         Iterator<NetworkManager> ☃ = this.field_151272_f.iterator();

         while(☃.hasNext()) {
            NetworkManager ☃x = (NetworkManager)☃.next();
            if (!☃x.func_179291_h()) {
               if (☃x.func_150724_d()) {
                  try {
                     ☃x.func_74428_b();
                  } catch (Exception var8) {
                     if (☃x.func_150731_c()) {
                        CrashReport ☃xx = CrashReport.func_85055_a(var8, "Ticking memory connection");
                        CrashReportCategory ☃xxx = ☃xx.func_85058_a("Ticking connection");
                        ☃xxx.func_189529_a("Connection", ☃x::toString);
                        throw new ReportedException(☃xx);
                     }

                     field_151275_b.warn("Failed to handle packet for {}", ☃x.func_74430_c(), var8);
                     ITextComponent ☃xx = new TextComponentString("Internal server error");
                     ☃x.func_201058_a(new SPacketDisconnect(☃xx), var2x -> ☃.func_150718_a(☃));
                     ☃x.func_150721_g();
                  }
               } else {
                  ☃.remove();
                  ☃x.func_179293_l();
               }
            }
         }
      }
   }

   public MinecraftServer func_151267_d() {
      return this.field_151273_d;
   }
}
