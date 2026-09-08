package net.minecraft.server.rcon.thread;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconThread extends GenericThread {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ServerSocket socket;
   private final String rconPassword;
   private final List<RconClient> clients = Lists.<RconClient>newArrayList();
   private final ServerInterface serverInterface;

   private RconThread(ServerInterface var1, ServerSocket var2, String var3) {
      super("RCON Listener");
      this.serverInterface = â˜ƒ;
      this.socket = â˜ƒ;
      this.rconPassword = â˜ƒ;
   }

   private void clearClients() {
      this.clients.removeIf(var0 -> !var0.isRunning());
   }

   public void run() {
      try {
         while(this.running) {
            try {
               Socket â˜ƒ = this.socket.accept();
               RconClient â˜ƒx = new RconClient(this.serverInterface, this.rconPassword, â˜ƒ);
               â˜ƒx.start();
               this.clients.add(â˜ƒx);
               this.clearClients();
            } catch (SocketTimeoutException var7) {
               this.clearClients();
            } catch (IOException var8) {
               if (this.running) {
                  LOGGER.info("IO exception: ", var8);
               }
            }
         }
      } finally {
         this.closeSocket(this.socket);
      }
   }

   @Nullable
   public static RconThread create(ServerInterface var0) {
      DedicatedServerProperties â˜ƒ = â˜ƒ.getProperties();
      String â˜ƒx = â˜ƒ.getServerIp();
      if (â˜ƒx.isEmpty()) {
         â˜ƒx = "0.0.0.0";
      }

      int â˜ƒ = â˜ƒ.rconPort;
      if (0 < â˜ƒ && 65535 >= â˜ƒ) {
         String â˜ƒx = â˜ƒ.rconPassword;
         if (â˜ƒx.isEmpty()) {
            LOGGER.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
         } else {
            try {
               ServerSocket â˜ƒx = new ServerSocket(â˜ƒ, 0, InetAddress.getByName(â˜ƒx));
               â˜ƒx.setSoTimeout(500);
               RconThread â˜ƒxx = new RconThread(â˜ƒ, â˜ƒx, â˜ƒx);
               if (!â˜ƒxx.start()) {
                  return null;
               } else {
                  LOGGER.info("RCON running on {}:{}", â˜ƒx, â˜ƒ);
                  return â˜ƒxx;
               }
            } catch (IOException var7) {
               LOGGER.warn("Unable to initialise RCON on {}:{}", â˜ƒx, â˜ƒ, var7);
               return null;
            }
         }
      } else {
         LOGGER.warn("Invalid rcon port {} found in server.properties, rcon disabled!", â˜ƒ);
         return null;
      }
   }

   @Override
   public void stop() {
      this.running = false;
      this.closeSocket(this.socket);
      super.stop();

      for(RconClient â˜ƒ : this.clients) {
         if (â˜ƒ.isRunning()) {
            â˜ƒ.stop();
         }
      }

      this.clients.clear();
   }

   private void closeSocket(ServerSocket var1) {
      LOGGER.debug("closeSocket: {}", â˜ƒ);

      try {
         â˜ƒ.close();
      } catch (IOException var3) {
         LOGGER.warn("Failed to close socket", var3);
      }
   }
}
