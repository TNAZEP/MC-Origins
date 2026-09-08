package net.minecraft.commands.arguments;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class ComponentArgument implements ArgumentType<Component> {
   private static final Collection<String> EXAMPLES = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
   public static final DynamicCommandExceptionType ERROR_INVALID_JSON = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.component.invalid", var0)
   );

   private ComponentArgument() {
   }

   public static Component getComponent(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, Component.class);
   }

   public static ComponentArgument textComponent() {
      return new ComponentArgument();
   }

   public Component parse(StringReader var1) throws CommandSyntaxException {
      try {
         Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ);
         if (â˜ƒ == null) {
            throw ERROR_INVALID_JSON.createWithContext(â˜ƒ, "empty");
         } else {
            return â˜ƒ;
         }
      } catch (JsonParseException var4) {
         String â˜ƒ = var4.getCause() != null ? var4.getCause().getMessage() : var4.getMessage();
         throw ERROR_INVALID_JSON.createWithContext(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
