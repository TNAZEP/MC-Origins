package net.minecraft.network.chat;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class KeybindComponent extends BaseComponent {
   private static Function<String, Supplier<Component>> keyResolver = var0 -> () -> new TextComponent(var0);
   private final String name;
   private Supplier<Component> nameResolver;

   public KeybindComponent(String var1) {
      this.name = â˜ƒ;
   }

   public static void setKeyResolver(Function<String, Supplier<Component>> var0) {
      keyResolver = â˜ƒ;
   }

   private Component getNestedComponent() {
      if (this.nameResolver == null) {
         this.nameResolver = (Supplier)keyResolver.apply(this.name);
      }

      return (Component)this.nameResolver.get();
   }

   @Override
   public <T> Optional<T> visitSelf(FormattedText.ContentConsumer<T> var1) {
      return this.getNestedComponent().visit(â˜ƒ);
   }

   @Override
   public <T> Optional<T> visitSelf(FormattedText.StyledContentConsumer<T> var1, Style var2) {
      return this.getNestedComponent().visit(â˜ƒ, â˜ƒ);
   }

   public KeybindComponent plainCopy() {
      return new KeybindComponent(this.name);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof KeybindComponent)) {
         return false;
      } else {
         KeybindComponent â˜ƒ = (KeybindComponent)â˜ƒ;
         return this.name.equals(â˜ƒ.name) && super.equals(â˜ƒ);
      }
   }

   @Override
   public String toString() {
      return "KeybindComponent{keybind='" + this.name + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
   }

   public String getName() {
      return this.name;
   }
}
