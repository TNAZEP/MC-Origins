package net.minecraft.command.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;

public class ArgumentSerializer<T extends ArgumentType<?>> implements IArgumentSerializer<T> {
   private final Supplier<T> field_197075_a;

   public ArgumentSerializer(Supplier<T> var1) {
      this.field_197075_a = ☃;
   }

   @Override
   public void func_197072_a(T var1, PacketBuffer var2) {
   }

   @Override
   public T func_197071_b(PacketBuffer var1) {
      return (T)this.field_197075_a.get();
   }

   @Override
   public void func_212244_a(T var1, JsonObject var2) {
   }
}
