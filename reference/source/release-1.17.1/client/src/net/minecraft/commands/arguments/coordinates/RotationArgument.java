package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;

public class RotationArgument implements ArgumentType<Coordinates> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "~-5 ~5");
   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(new TranslatableComponent("argument.rotation.incomplete"));

   public static RotationArgument rotation() {
      return new RotationArgument();
   }

   public static Coordinates getRotation(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, Coordinates.class);
   }

   public Coordinates parse(StringReader var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();
      if (!â˜ƒ.canRead()) {
         throw ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      } else {
         WorldCoordinate â˜ƒ = WorldCoordinate.parseDouble(â˜ƒ, false);
         if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
            â˜ƒ.skip();
            WorldCoordinate â˜ƒx = WorldCoordinate.parseDouble(â˜ƒ, false);
            return new WorldCoordinates(â˜ƒx, â˜ƒ, new WorldCoordinate(true, 0.0));
         } else {
            â˜ƒ.setCursor(â˜ƒ);
            throw ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
         }
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
