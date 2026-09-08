package net.minecraft.client.multiplayer.resolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.mojang.blocklist.BlockListSupplier;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Predicate;

public interface AddressCheck {
   boolean isAllowed(ResolvedServerAddress var1);

   boolean isAllowed(ServerAddress var1);

   static AddressCheck createFromService() {
      final ImmutableList<Predicate<String>> â˜ƒ = (ImmutableList)Streams.stream(ServiceLoader.load(BlockListSupplier.class))
         .map(BlockListSupplier::createBlockList)
         .filter(Objects::nonNull)
         .collect(ImmutableList.toImmutableList());
      return new AddressCheck() {
         @Override
         public boolean isAllowed(ResolvedServerAddress var1) {
            String â˜ƒ = â˜ƒ.getHostName();
            String â˜ƒx = â˜ƒ.getHostIp();
            return â˜ƒ.stream().noneMatch(var2x -> var2x.test(â˜ƒ) || var2x.test(â˜ƒ));
         }

         @Override
         public boolean isAllowed(ServerAddress var1) {
            String â˜ƒ = â˜ƒ.getHost();
            return â˜ƒ.stream().noneMatch(var1x -> var1x.test(â˜ƒ));
         }
      };
   }
}
