package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;

public class UuidArgument implements ArgumentType<UUID> {
   public static final SimpleCommandExceptionType ERROR_INVALID_UUID = new SimpleCommandExceptionType(new TranslatableComponent("argument.uuid.invalid"));
   private static final Collection<String> EXAMPLES = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
   private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("^([-A-Fa-f0-9]+)");

   public static UUID getUuid(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, UUID.class);
   }

   public static UuidArgument uuid() {
      return new UuidArgument();
   }

   public UUID parse(StringReader var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.getRemaining();
      Matcher â˜ƒx = ALLOWED_CHARACTERS.matcher(â˜ƒ);
      if (â˜ƒx.find()) {
         String â˜ƒxx = â˜ƒx.group(1);

         try {
            UUID â˜ƒxxx = UUID.fromString(â˜ƒxx);
            â˜ƒ.setCursor(â˜ƒ.getCursor() + â˜ƒxx.length());
            return â˜ƒxxx;
         } catch (IllegalArgumentException var6) {
         }
      }

      throw ERROR_INVALID_UUID.create();
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
