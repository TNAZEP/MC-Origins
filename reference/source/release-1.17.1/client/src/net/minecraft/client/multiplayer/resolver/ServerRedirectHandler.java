package net.minecraft.client.multiplayer.resolver;

import java.util.Hashtable;
import java.util.Optional;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface ServerRedirectHandler {
   Logger LOGGER = LogManager.getLogger();
   ServerRedirectHandler EMPTY = var0 -> Optional.empty();

   Optional<ServerAddress> lookupRedirect(ServerAddress var1);

   static ServerRedirectHandler createDnsSrvRedirectHandler() {
      DirContext â˜ƒ;
      try {
         String â˜ƒ = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable<String, String> â˜ƒx = new Hashtable();
         â˜ƒx.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         â˜ƒx.put("java.naming.provider.url", "dns:");
         â˜ƒx.put("com.sun.jndi.dns.timeout.retries", "1");
         â˜ƒ = new InitialDirContext(â˜ƒx);
      } catch (Throwable var3) {
         LOGGER.error("Failed to initialize SRV redirect resolved, some servers might not work", var3);
         return EMPTY;
      }

      return var1x -> {
         if (var1x.getPort() == 25565) {
            try {
               Attributes â˜ƒ = â˜ƒ.getAttributes("_minecraft._tcp." + var1x.getHost(), new String[]{"SRV"});
               Attribute â˜ƒx = â˜ƒ.get("srv");
               if (â˜ƒx != null) {
                  String[] â˜ƒxx = â˜ƒx.get().toString().split(" ", 4);
                  return Optional.of(new ServerAddress(â˜ƒxx[3], ServerAddress.parsePort(â˜ƒxx[2])));
               }
            } catch (Throwable var5) {
            }
         }

         return Optional.empty();
      };
   }
}
