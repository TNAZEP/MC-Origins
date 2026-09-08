package net.minecraft.util.thread;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ProcessorHandle<Msg> extends AutoCloseable {
   String name();

   void tell(Msg var1);

   default void close() {
   }

   default <Source> CompletableFuture<Source> ask(Function<? super ProcessorHandle<Source>, ? extends Msg> var1) {
      CompletableFuture<Source> â˜ƒ = new CompletableFuture();
      Msg â˜ƒx = (Msg)â˜ƒ.apply(of("ask future procesor handle", â˜ƒ::complete));
      this.tell(â˜ƒx);
      return â˜ƒ;
   }

   default <Source> CompletableFuture<Source> askEither(Function<? super ProcessorHandle<Either<Source, Exception>>, ? extends Msg> var1) {
      CompletableFuture<Source> â˜ƒ = new CompletableFuture();
      Msg â˜ƒx = (Msg)â˜ƒ.apply(of("ask future procesor handle", var1x -> {
         var1x.ifLeft(â˜ƒ::complete);
         var1x.ifRight(â˜ƒ::completeExceptionally);
      }));
      this.tell(â˜ƒx);
      return â˜ƒ;
   }

   static <Msg> ProcessorHandle<Msg> of(final String var0, final Consumer<Msg> var1) {
      return new ProcessorHandle<Msg>() {
         @Override
         public String name() {
            return â˜ƒ;
         }

         @Override
         public void tell(Msg var1x) {
            â˜ƒ.accept(â˜ƒ);
         }

         public String toString() {
            return â˜ƒ;
         }
      };
   }
}
