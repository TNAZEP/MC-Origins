package net.minecraft.world.inventory;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ContainerLevelAccess {
   ContainerLevelAccess NULL = new ContainerLevelAccess() {
      @Override
      public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> var1) {
         return Optional.empty();
      }
   };

   static ContainerLevelAccess create(final Level var0, final BlockPos var1) {
      return new ContainerLevelAccess() {
         @Override
         public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> var1x) {
            return Optional.of(â˜ƒ.apply(â˜ƒ, â˜ƒ));
         }
      };
   }

   <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> var1);

   default <T> T evaluate(BiFunction<Level, BlockPos, T> var1, T var2) {
      return (T)this.evaluate(â˜ƒ).orElse(â˜ƒ);
   }

   default void execute(BiConsumer<Level, BlockPos> var1) {
      this.evaluate((var1x, var2) -> {
         â˜ƒ.accept(var1x, var2);
         return Optional.empty();
      });
   }
}
