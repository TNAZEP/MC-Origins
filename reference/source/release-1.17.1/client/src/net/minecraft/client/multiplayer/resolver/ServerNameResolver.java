package net.minecraft.client.multiplayer.resolver;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;

public class ServerNameResolver {
   public static final ServerNameResolver DEFAULT = new ServerNameResolver(
      ServerAddressResolver.SYSTEM, ServerRedirectHandler.createDnsSrvRedirectHandler(), AddressCheck.createFromService()
   );
   private final ServerAddressResolver resolver;
   private final ServerRedirectHandler redirectHandler;
   private final AddressCheck addressCheck;

   @VisibleForTesting
   ServerNameResolver(ServerAddressResolver var1, ServerRedirectHandler var2, AddressCheck var3) {
      this.resolver = â˜ƒ;
      this.redirectHandler = â˜ƒ;
      this.addressCheck = â˜ƒ;
   }

   public Optional<ResolvedServerAddress> resolveAddress(ServerAddress var1) {
      Optional<ResolvedServerAddress> â˜ƒ = this.resolver.resolve(â˜ƒ);
      if ((!â˜ƒ.isPresent() || this.addressCheck.isAllowed((ResolvedServerAddress)â˜ƒ.get())) && this.addressCheck.isAllowed(â˜ƒ)) {
         Optional<ServerAddress> â˜ƒx = this.redirectHandler.lookupRedirect(â˜ƒ);
         if (â˜ƒx.isPresent()) {
            â˜ƒ = this.resolver.resolve((ServerAddress)â˜ƒx.get()).filter(this.addressCheck::isAllowed);
         }

         return â˜ƒ;
      } else {
         return Optional.empty();
      }
   }
}
