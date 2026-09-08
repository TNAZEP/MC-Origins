package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandshakePacketListenerImpl implements ClientLoginPacketListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft minecraft;
   @Nullable
   private final Screen parent;
   private final Consumer<Component> updateStatus;
   private final Connection connection;
   private GameProfile localGameProfile;

   public ClientHandshakePacketListenerImpl(Connection var1, Minecraft var2, @Nullable Screen var3, Consumer<Component> var4) {
      this.connection = â˜ƒ;
      this.minecraft = â˜ƒ;
      this.parent = â˜ƒ;
      this.updateStatus = â˜ƒ;
   }

   @Override
   public void handleHello(ClientboundHelloPacket var1) {
      Cipher â˜ƒ;
      Cipher â˜ƒ;
      String â˜ƒ;
      ServerboundKeyPacket â˜ƒ;
      try {
         SecretKey â˜ƒ = Crypt.generateSecretKey();
         PublicKey â˜ƒx = â˜ƒ.getPublicKey();
         â˜ƒ = new BigInteger(Crypt.digestData(â˜ƒ.getServerId(), â˜ƒx, â˜ƒ)).toString(16);
         â˜ƒ = Crypt.getCipher(2, â˜ƒ);
         â˜ƒ = Crypt.getCipher(1, â˜ƒ);
         â˜ƒ = new ServerboundKeyPacket(â˜ƒ, â˜ƒx, â˜ƒ.getNonce());
      } catch (CryptException var8) {
         throw new IllegalStateException("Protocol error", var8);
      }

      this.updateStatus.accept(new TranslatableComponent("connect.authorizing"));
      HttpUtil.DOWNLOAD_EXECUTOR.submit((Runnable)(() -> {
         Component â˜ƒ = this.authenticateServer(â˜ƒ);
         if (â˜ƒ != null) {
            if (this.minecraft.getCurrentServer() == null || !this.minecraft.getCurrentServer().isLan()) {
               this.connection.disconnect(â˜ƒ);
               return;
            }

            LOGGER.warn(â˜ƒ.getString());
         }

         this.updateStatus.accept(new TranslatableComponent("connect.encrypting"));
         this.connection.send(â˜ƒ, var3x -> this.connection.setEncryptionKey(â˜ƒ, â˜ƒ));
      }));
   }

   @Nullable
   private Component authenticateServer(String var1) {
      try {
         this.getMinecraftSessionService().joinServer(this.minecraft.getUser().getGameProfile(), this.minecraft.getUser().getAccessToken(), â˜ƒ);
         return null;
      } catch (AuthenticationUnavailableException var3) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.serversUnavailable"));
      } catch (InvalidCredentialsException var4) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.invalidSession"));
      } catch (InsufficientPrivilegesException var5) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.insufficientPrivileges"));
      } catch (AuthenticationException var6) {
         return new TranslatableComponent("disconnect.loginFailedInfo", var6.getMessage());
      }
   }

   private MinecraftSessionService getMinecraftSessionService() {
      return this.minecraft.getMinecraftSessionService();
   }

   @Override
   public void handleGameProfile(ClientboundGameProfilePacket var1) {
      this.updateStatus.accept(new TranslatableComponent("connect.joining"));
      this.localGameProfile = â˜ƒ.getGameProfile();
      this.connection.setProtocol(ConnectionProtocol.PLAY);
      this.connection.setListener(new ClientPacketListener(this.minecraft, this.parent, this.connection, this.localGameProfile));
   }

   @Override
   public void onDisconnect(Component var1) {
      if (this.parent != null && this.parent instanceof RealmsScreen) {
         this.minecraft.setScreen(new DisconnectedRealmsScreen(this.parent, CommonComponents.CONNECT_FAILED, â˜ƒ));
      } else {
         this.minecraft.setScreen(new DisconnectedScreen(this.parent, CommonComponents.CONNECT_FAILED, â˜ƒ));
      }
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }

   @Override
   public void handleDisconnect(ClientboundLoginDisconnectPacket var1) {
      this.connection.disconnect(â˜ƒ.getReason());
   }

   @Override
   public void handleCompression(ClientboundLoginCompressionPacket var1) {
      if (!this.connection.isMemoryConnection()) {
         this.connection.setupCompression(â˜ƒ.getCompressionThreshold(), false);
      }
   }

   @Override
   public void handleCustomQuery(ClientboundCustomQueryPacket var1) {
      this.updateStatus.accept(new TranslatableComponent("connect.negotiating"));
      this.connection.send(new ServerboundCustomQueryPacket(â˜ƒ.getTransactionId(), null));
   }
}
