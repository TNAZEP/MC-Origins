package net.minecraft.realms;

import com.mojang.realmsclient.dto.RealmsServer;
import java.net.InetSocketAddress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
   static final Logger LOGGER = LogManager.getLogger();
   final Screen onlineScreen;
   volatile boolean aborted;
   Connection connection;

   public RealmsConnect(Screen var1) {
      this.onlineScreen = â˜ƒ;
   }

   public void connect(final RealmsServer var1, ServerAddress var2) {
      final Minecraft â˜ƒ = Minecraft.getInstance();
      â˜ƒ.setConnectedToRealms(true);
      NarratorChatListener.INSTANCE.sayNow(new TranslatableComponent("mco.connect.success"));
      final String â˜ƒx = â˜ƒ.getHost();
      final int â˜ƒxx = â˜ƒ.getPort();
      (new Thread("Realms-connect-task") {
            public void run() {
               InetSocketAddress â˜ƒ = null;
   
               try {
                  â˜ƒ = new InetSocketAddress(â˜ƒ, â˜ƒ);
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection = Connection.connectToServer(â˜ƒ, â˜ƒ.options.useNativeTransport());
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection
                     .setListener(new ClientHandshakePacketListenerImpl(RealmsConnect.this.connection, â˜ƒ, RealmsConnect.this.onlineScreen, var0 -> {
                     }));
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection.send(new ClientIntentionPacket(â˜ƒ, â˜ƒ, ConnectionProtocol.LOGIN));
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection.send(new ServerboundHelloPacket(â˜ƒ.getUser().getGameProfile()));
                  â˜ƒ.setCurrentServer(â˜ƒ.toServerData(â˜ƒ));
               } catch (Exception var5x) {
                  â˜ƒ.getClientPackSource().clearServerPack();
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.LOGGER.error("Couldn't connect to world", var5x);
                  String â˜ƒx = var5x.toString();
                  if (â˜ƒ != null) {
                     String â˜ƒxx = â˜ƒ + ":" + â˜ƒ;
                     â˜ƒx = â˜ƒx.replaceAll(â˜ƒxx, "");
                  }
   
                  DisconnectedRealmsScreen â˜ƒx = new DisconnectedRealmsScreen(
                     RealmsConnect.this.onlineScreen, CommonComponents.CONNECT_FAILED, new TranslatableComponent("disconnect.genericReason", â˜ƒx)
                  );
                  â˜ƒ.execute(() -> â˜ƒ.setScreen(â˜ƒ));
               }
            }
         })
         .start();
   }

   public void abort() {
      this.aborted = true;
      if (this.connection != null && this.connection.isConnected()) {
         this.connection.disconnect(new TranslatableComponent("disconnect.genericReason"));
         this.connection.handleDisconnection();
      }
   }

   public void tick() {
      if (this.connection != null) {
         if (this.connection.isConnected()) {
            this.connection.tick();
         } else {
            this.connection.handleDisconnection();
         }
      }
   }
}
