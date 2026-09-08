package net.minecraft.client.multiplayer.resolver;

import com.google.common.net.HostAndPort;
import java.net.IDN;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServerAddress {
   private static final Logger LOGGER = LogManager.getLogger();
   private final HostAndPort hostAndPort;
   private static final ServerAddress INVALID = new ServerAddress(HostAndPort.fromParts("server.invalid", 25565));

   public ServerAddress(String var1, int var2) {
      this(HostAndPort.fromParts(â˜ƒ, â˜ƒ));
   }

   private ServerAddress(HostAndPort var1) {
      this.hostAndPort = â˜ƒ;
   }

   public String getHost() {
      try {
         return IDN.toASCII(this.hostAndPort.getHost());
      } catch (IllegalArgumentException var2) {
         return "";
      }
   }

   public int getPort() {
      return this.hostAndPort.getPort();
   }

   public static ServerAddress parseString(String var0) {
      if (â˜ƒ == null) {
         return INVALID;
      } else {
         try {
            HostAndPort â˜ƒ = HostAndPort.fromString(â˜ƒ).withDefaultPort(25565);
            return â˜ƒ.getHost().isEmpty() ? INVALID : new ServerAddress(â˜ƒ);
         } catch (IllegalArgumentException var2) {
            LOGGER.info("Failed to parse URL {}", â˜ƒ, var2);
            return INVALID;
         }
      }
   }

   public static boolean isValidAddress(String var0) {
      try {
         HostAndPort â˜ƒ = HostAndPort.fromString(â˜ƒ);
         String â˜ƒx = â˜ƒ.getHost();
         if (!â˜ƒx.isEmpty()) {
            IDN.toASCII(â˜ƒx);
            return true;
         }
      } catch (IllegalArgumentException var3) {
      }

      return false;
   }

   static int parsePort(String var0) {
      try {
         return Integer.parseInt(â˜ƒ.trim());
      } catch (Exception var2) {
         return 25565;
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ServerAddress ? this.hostAndPort.equals(((ServerAddress)â˜ƒ).hostAndPort) : false;
      }
   }

   public int hashCode() {
      return this.hostAndPort.hashCode();
   }
}
