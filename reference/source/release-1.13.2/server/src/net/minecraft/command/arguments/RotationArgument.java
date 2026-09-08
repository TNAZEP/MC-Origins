package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentTranslation;

public class RotationArgument implements ArgumentType<ILocationArgument> {
   private static final Collection<String> field_201334_b = Arrays.asList("0 0", "~ ~", "~-5 ~5");
   public static final SimpleCommandExceptionType field_197290_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.rotation.incomplete"));

   public static RotationArgument func_197288_a() {
      return new RotationArgument();
   }

   public static ILocationArgument func_200384_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, ILocationArgument.class);
   }

   public ILocationArgument parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      if (!☃.canRead()) {
         throw field_197290_a.createWithContext(☃);
      } else {
         LocationPart ☃ = LocationPart.func_197308_a(☃, false);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            LocationPart ☃x = LocationPart.func_197308_a(☃, false);
            return new LocationInput(☃x, ☃, new LocationPart(true, 0.0));
         } else {
            ☃.setCursor(☃);
            throw field_197290_a.createWithContext(☃);
         }
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201334_b;
   }
}
