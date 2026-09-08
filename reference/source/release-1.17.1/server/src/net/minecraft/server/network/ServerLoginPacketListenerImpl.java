package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.channel.ChannelFuture;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLoginPacketListenerImpl implements ServerLoginPacketListener {
   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
   static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_TICKS_BEFORE_LOGIN = 600;
   private static final Random RANDOM = new Random();
   private final byte[] nonce = new byte[4];
   final MinecraftServer server;
   public final Connection connection;
   ServerLoginPacketListenerImpl.State state = ServerLoginPacketListenerImpl.State.HELLO;
   private int tick;
   @Nullable
   GameProfile gameProfile;
   private final String serverId = "";
   @Nullable
   private ServerPlayer delayedAcceptPlayer;

   public ServerLoginPacketListenerImpl(MinecraftServer var1, Connection var2) {
      this.server = â˜ƒ;
      this.connection = â˜ƒ;
      RANDOM.nextBytes(this.nonce);
   }

   public void tick() {
      if (this.state == ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT) {
         this.handleAcceptedLogin();
      } else if (this.state == ServerLoginPacketListenerImpl.State.DELAY_ACCEPT) {
         ServerPlayer â˜ƒ = this.server.getPlayerList().getPlayer(this.gameProfile.getId());
         if (â˜ƒ == null) {
            this.state = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
            this.placeNewPlayer(this.delayedAcceptPlayer);
            this.delayedAcceptPlayer = null;
         }
      }

      if (this.tick++ == 600) {
         this.disconnect(new TranslatableComponent("multiplayer.disconnect.slow_login"));
      }
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }

   public void disconnect(Component var1) {
      try {
         LOGGER.info("Disconnecting {}: {}", this.getUserName(), â˜ƒ.getString());
         this.connection.send(new ClientboundLoginDisconnectPacket(â˜ƒ));
         this.connection.disconnect(â˜ƒ);
      } catch (Exception var3) {
         LOGGER.error("Error whilst disconnecting player", var3);
      }
   }

   public void handleAcceptedLogin() {
      if (!this.gameProfile.isComplete()) {
         this.gameProfile = this.createFakeProfile(this.gameProfile);
      }

      Component â˜ƒ = this.server.getPlayerList().canPlayerLogin(this.connection.getRemoteAddress(), this.gameProfile);
      if (â˜ƒ != null) {
         this.disconnect(â˜ƒ);
      } else {
         this.state = ServerLoginPacketListenerImpl.State.ACCEPTED;
         if (this.server.getCompressionThreshold() >= 0 && !this.connection.isMemoryConnection()) {
            this.connection
               .send(
                  new ClientboundLoginCompressionPacket(this.server.getCompressionThreshold()),
                  var1x -> this.connection.setupCompression(this.server.getCompressionThreshold(), true)
               );
         }

         this.connection.send(new ClientboundGameProfilePacket(this.gameProfile));
         ServerPlayer â˜ƒ = this.server.getPlayerList().getPlayer(this.gameProfile.getId());

         try {
            ServerPlayer â˜ƒx = this.server.getPlayerList().getPlayerForLogin(this.gameProfile);
            if (â˜ƒ != null) {
               this.state = ServerLoginPacketListenerImpl.State.DELAY_ACCEPT;
               this.delayedAcceptPlayer = â˜ƒx;
            } else {
               this.placeNewPlayer(â˜ƒx);
            }
         } catch (Exception var5) {
            Component â˜ƒx = new TranslatableComponent("multiplayer.disconnect.invalid_player_data");
            this.connection.send(new ClientboundDisconnectPacket(â˜ƒx));
            this.connection.disconnect(â˜ƒx);
         }
      }
   }

   private void placeNewPlayer(ServerPlayer var1) {
      this.server.getPlayerList().placeNewPlayer(this.connection, â˜ƒ);
   }

   @Override
   public void onDisconnect(Component var1) {
      LOGGER.info("{} lost connection: {}", this.getUserName(), â˜ƒ.getString());
   }

   public String getUserName() {
      return this.gameProfile != null ? this.gameProfile + " (" + this.connection.getRemoteAddress() + ")" : String.valueOf(this.connection.getRemoteAddress());
   }

   @Override
   public void handleHello(ServerboundHelloPacket var1) {
      Validate.validState(this.state == ServerLoginPacketListenerImpl.State.HELLO, "Unexpected hello packet");
      this.gameProfile = â˜ƒ.getGameProfile();
      if (this.server.usesAuthentication() && !this.connection.isMemoryConnection()) {
         this.state = ServerLoginPacketListenerImpl.State.KEY;
         this.connection.send(new ClientboundHelloPacket("", this.server.getKeyPair().getPublic().getEncoded(), this.nonce));
      } else {
         this.state = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
      }
   }

   @Override
   public void handleKey(ServerboundKeyPacket var1) {
      Validate.validState(this.state == ServerLoginPacketListenerImpl.State.KEY, "Unexpected key packet");
      PrivateKey â˜ƒ = this.server.getKeyPair().getPrivate();

      final String â˜ƒ;
      try {
         if (!Arrays.equals(this.nonce, â˜ƒ.getNonce(â˜ƒ))) {
            throw new IllegalStateException("Protocol error");
         }

         SecretKey â˜ƒx = â˜ƒ.getSecretKey(â˜ƒ);
         Cipher â˜ƒxx = Crypt.getCipher(2, â˜ƒx);
         Cipher â˜ƒxxx = Crypt.getCipher(1, â˜ƒx);
         â˜ƒ = new BigInteger(Crypt.digestData("", this.server.getKeyPair().getPublic(), â˜ƒx)).toString(16);
         this.state = ServerLoginPacketListenerImpl.State.AUTHENTICATING;
         this.connection.setEncryptionKey(â˜ƒxx, â˜ƒxxx);
      } catch (CryptException var7) {
         throw new IllegalStateException("Protocol error", var7);
      }

      Thread â˜ƒx = new Thread("User Authenticator #" + UNIQUE_THREAD_ID.incrementAndGet()) {
         public void run() {
            GameProfile â˜ƒ = ServerLoginPacketListenerImpl.this.gameProfile;

            try {
               ServerLoginPacketListenerImpl.this.gameProfile = ServerLoginPacketListenerImpl.this.server
                  .getSessionService()
                  .hasJoinedServer(new GameProfile(null, â˜ƒ.getName()), â˜ƒ, this.getAddress());
               if (ServerLoginPacketListenerImpl.this.gameProfile != null) {
                  ServerLoginPacketListenerImpl.LOGGER
                     .info(
                        "UUID of player {} is {}",
                        ServerLoginPacketListenerImpl.this.gameProfile.getName(),
                        ServerLoginPacketListenerImpl.this.gameProfile.getId()
                     );
                  ServerLoginPacketListenerImpl.this.state = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
               } else if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
                  ServerLoginPacketListenerImpl.LOGGER.warn("Failed to verify username but will let them in anyway!");
                  ServerLoginPacketListenerImpl.this.gameProfile = ServerLoginPacketListenerImpl.this.createFakeProfile(â˜ƒ);
                  ServerLoginPacketListenerImpl.this.state = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
               } else {
                  ServerLoginPacketListenerImpl.this.disconnect(new TranslatableComponent("multiplayer.disconnect.unverified_username"));
                  ServerLoginPacketListenerImpl.LOGGER.error("Username '{}' tried to join with an invalid session", â˜ƒ.getName());
               }
            } catch (AuthenticationUnavailableException var3x) {
               if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
                  ServerLoginPacketListenerImpl.LOGGER.warn("Authentication servers are down but will let them in anyway!");
                  ServerLoginPacketListenerImpl.this.gameProfile = ServerLoginPacketListenerImpl.this.createFakeProfile(â˜ƒ);
                  ServerLoginPacketListenerImpl.this.state = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
               } else {
                  ServerLoginPacketListenerImpl.this.disconnect(new TranslatableComponent("multiplayer.disconnect.authservers_down"));
                  ServerLoginPacketListenerImpl.LOGGER.error("Couldn't verify username because servers are unavailable");
               }
            }
         }

         @Nullable
         private InetAddress getAddress() {
            SocketAddress â˜ƒ = ServerLoginPacketListenerImpl.this.connection.getRemoteAddress();
            return ServerLoginPacketListenerImpl.this.server.getPreventProxyConnections() && â˜ƒ instanceof InetSocketAddress
               ? ((InetSocketAddress)â˜ƒ).getAddress()
               : null;
         }
      };
      â˜ƒx.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      â˜ƒx.start();
   }

   @Override
   public void handleCustomQueryPacket(ServerboundCustomQueryPacket var1) {
      this.disconnect(new TranslatableComponent("multiplayer.disconnect.unexpected_query_response"));
   }

   protected GameProfile createFakeProfile(GameProfile var1) {
      UUID â˜ƒ = Player.createPlayerUUID(â˜ƒ.getName());
      return new GameProfile(â˜ƒ, â˜ƒ.getName());
   }

   static enum State {
      HELLO,
      KEY,
      AUTHENTICATING,
      NEGOTIATING,
      READY_TO_ACCEPT,
      DELAY_ACCEPT,
      ACCEPTED;
   }
}
