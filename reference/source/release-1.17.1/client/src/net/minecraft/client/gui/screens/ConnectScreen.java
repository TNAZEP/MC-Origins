package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectScreen extends Screen {
   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
   static final Logger LOGGER = LogManager.getLogger();
   private static final long NARRATION_DELAY_MS = 2000L;
   public static final Component UNKNOWN_HOST_MESSAGE = new TranslatableComponent(
      "disconnect.genericReason", new TranslatableComponent("disconnect.unknownHost")
   );
   @Nullable
   volatile Connection connection;
   volatile boolean aborted;
   final Screen parent;
   private Component status = new TranslatableComponent("connect.connecting");
   private long lastNarration = -1L;

   private ConnectScreen(Screen var1) {
      super(NarratorChatListener.NO_TITLE);
      this.parent = â˜ƒ;
   }

   public static void startConnecting(Screen var0, Minecraft var1, ServerAddress var2, @Nullable ServerData var3) {
      ConnectScreen â˜ƒ = new ConnectScreen(â˜ƒ);
      â˜ƒ.clearLevel();
      â˜ƒ.setCurrentServer(â˜ƒ);
      â˜ƒ.setScreen(â˜ƒ);
      â˜ƒ.connect(â˜ƒ, â˜ƒ);
   }

   private void connect(final Minecraft var1, final ServerAddress var2) {
      LOGGER.info("Connecting to {}, {}", â˜ƒ.getHost(), â˜ƒ.getPort());
      Thread â˜ƒ = new Thread("Server Connector #" + UNIQUE_THREAD_ID.incrementAndGet()) {
         public void run() {
            InetSocketAddress â˜ƒ = null;

            try {
               if (ConnectScreen.this.aborted) {
                  return;
               }

               Optional<InetSocketAddress> â˜ƒx = ServerNameResolver.DEFAULT.resolveAddress(â˜ƒ).map(ResolvedServerAddress::asInetSocketAddress);
               if (ConnectScreen.this.aborted) {
                  return;
               }

               if (!â˜ƒx.isPresent()) {
                  â˜ƒ.execute(
                     () -> â˜ƒ.setScreen(new DisconnectedScreen(ConnectScreen.this.parent, CommonComponents.CONNECT_FAILED, ConnectScreen.UNKNOWN_HOST_MESSAGE))
                  );
                  return;
               }

               â˜ƒ = (InetSocketAddress)â˜ƒx.get();
               ConnectScreen.this.connection = Connection.connectToServer(â˜ƒ, â˜ƒ.options.useNativeTransport());
               ConnectScreen.this.connection
                  .setListener(
                     new ClientHandshakePacketListenerImpl(ConnectScreen.this.connection, â˜ƒ, ConnectScreen.this.parent, ConnectScreen.this::updateStatus)
                  );
               ConnectScreen.this.connection.send(new ClientIntentionPacket(â˜ƒ.getHostName(), â˜ƒ.getPort(), ConnectionProtocol.LOGIN));
               ConnectScreen.this.connection.send(new ServerboundHelloPacket(â˜ƒ.getUser().getGameProfile()));
            } catch (Exception var4) {
               if (ConnectScreen.this.aborted) {
                  return;
               }

               ConnectScreen.LOGGER.error("Couldn't connect to server", var4);
               String â˜ƒx = â˜ƒ == null ? var4.toString() : var4.toString().replaceAll(â˜ƒ.getHostName() + ":" + â˜ƒ.getPort(), "");
               â˜ƒ.execute(
                  () -> â˜ƒ.setScreen(
                        new DisconnectedScreen(
                           ConnectScreen.this.parent, CommonComponents.CONNECT_FAILED, new TranslatableComponent("disconnect.genericReason", â˜ƒ)
                        )
                     )
               );
            }
         }
      };
      â˜ƒ.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      â˜ƒ.start();
   }

   private void updateStatus(Component var1) {
      this.status = â˜ƒ;
   }

   @Override
   public void tick() {
      if (this.connection != null) {
         if (this.connection.isConnected()) {
            this.connection.tick();
         } else {
            this.connection.handleDisconnection();
         }
      }
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   protected void init() {
      this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, CommonComponents.GUI_CANCEL, var1 -> {
         this.aborted = true;
         if (this.connection != null) {
            this.connection.disconnect(new TranslatableComponent("connect.aborted"));
         }

         this.minecraft.setScreen(this.parent);
      }));
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      long â˜ƒ = Util.getMillis();
      if (â˜ƒ - this.lastNarration > 2000L) {
         this.lastNarration = â˜ƒ;
         NarratorChatListener.INSTANCE.sayNow(new TranslatableComponent("narrator.joining"));
      }

      drawCenteredString(â˜ƒ, this.font, this.status, this.width / 2, this.height / 2 - 50, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
