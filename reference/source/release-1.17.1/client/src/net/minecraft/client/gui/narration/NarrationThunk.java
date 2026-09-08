package net.minecraft.client.gui.narration;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;

public class NarrationThunk<T> {
   private final T contents;
   private final BiConsumer<Consumer<String>, T> converter;
   public static final NarrationThunk<?> EMPTY = new NarrationThunk((T)Unit.INSTANCE, (var0, var1) -> {
   });

   private NarrationThunk(T var1, BiConsumer<Consumer<String>, T> var2) {
      this.contents = â˜ƒ;
      this.converter = â˜ƒ;
   }

   public static NarrationThunk<?> from(String var0) {
      return new NarrationThunk((T)â˜ƒ, Consumer::accept);
   }

   public static NarrationThunk<?> from(Component var0) {
      return new NarrationThunk<>(â˜ƒ, (var0x, var1) -> var0x.accept(var1.getContents()));
   }

   public static NarrationThunk<?> from(List<Component> var0) {
      return new NarrationThunk((T)â˜ƒ, (var1, var2) -> â˜ƒ.stream().map(Component::getString).forEach(var1));
   }

   public void getText(Consumer<String> var1) {
      this.converter.accept(â˜ƒ, this.contents);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof NarrationThunk)) {
         return false;
      } else {
         NarrationThunk<?> â˜ƒ = (NarrationThunk)â˜ƒ;
         return â˜ƒ.converter == this.converter && â˜ƒ.contents.equals(this.contents);
      }
   }

   public int hashCode() {
      int â˜ƒ = this.contents.hashCode();
      return 31 * â˜ƒ + this.converter.hashCode();
   }
}
