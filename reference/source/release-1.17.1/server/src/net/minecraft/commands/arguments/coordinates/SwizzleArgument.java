package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;

public class SwizzleArgument implements ArgumentType<EnumSet<Direction.Axis>> {
   private static final Collection<String> EXAMPLES = Arrays.asList("xyz", "x");
   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType(new TranslatableComponent("arguments.swizzle.invalid"));

   public static SwizzleArgument swizzle() {
      return new SwizzleArgument();
   }

   public static EnumSet<Direction.Axis> getSwizzle(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, EnumSet.class);
   }

   public EnumSet<Direction.Axis> parse(StringReader var1) throws CommandSyntaxException {
      EnumSet<Direction.Axis> â˜ƒ = EnumSet.noneOf(Direction.Axis.class);

      while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
         char â˜ƒ = â˜ƒ.read();

         Direction.Axis var4 = switch(â˜ƒ) {
            case 'x' -> Direction.Axis.X;
            case 'y' -> Direction.Axis.Y;
            case 'z' -> Direction.Axis.Z;
            default -> throw ERROR_INVALID.create();
         };
         if (â˜ƒ.contains(var4)) {
            throw ERROR_INVALID.create();
         }

         â˜ƒ.add(var4);
      }

      return â˜ƒ;
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
