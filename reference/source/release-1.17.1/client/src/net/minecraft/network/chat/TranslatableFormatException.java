package net.minecraft.network.chat;

public class TranslatableFormatException extends IllegalArgumentException {
   public TranslatableFormatException(TranslatableComponent var1, String var2) {
      super(String.format("Error parsing: %s: %s", â˜ƒ, â˜ƒ));
   }

   public TranslatableFormatException(TranslatableComponent var1, int var2) {
      super(String.format("Invalid index %d requested for %s", â˜ƒ, â˜ƒ));
   }

   public TranslatableFormatException(TranslatableComponent var1, Throwable var2) {
      super(String.format("Error while parsing: %s", â˜ƒ), â˜ƒ);
   }
}
