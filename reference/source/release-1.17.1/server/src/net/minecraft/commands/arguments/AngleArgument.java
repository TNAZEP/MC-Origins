package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class AngleArgument implements ArgumentType<AngleArgument.SingleAngle> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0", "~", "~-5");
   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(new TranslatableComponent("argument.angle.incomplete"));
   public static final SimpleCommandExceptionType ERROR_INVALID_ANGLE = new SimpleCommandExceptionType(new TranslatableComponent("argument.angle.invalid"));

   public static AngleArgument angle() {
      return new AngleArgument();
   }

   public static float getAngle(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.<AngleArgument.SingleAngle>getArgument(â˜ƒ, AngleArgument.SingleAngle.class).getAngle(â˜ƒ.getSource());
   }

   public AngleArgument.SingleAngle parse(StringReader var1) throws CommandSyntaxException {
      if (!â˜ƒ.canRead()) {
         throw ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      } else {
         boolean â˜ƒ = WorldCoordinate.isRelative(â˜ƒ);
         float â˜ƒx = â˜ƒ.canRead() && â˜ƒ.peek() != ' ' ? â˜ƒ.readFloat() : 0.0F;
         if (!Float.isNaN(â˜ƒx) && !Float.isInfinite(â˜ƒx)) {
            return new AngleArgument.SingleAngle(â˜ƒx, â˜ƒ);
         } else {
            throw ERROR_INVALID_ANGLE.createWithContext(â˜ƒ);
         }
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static final class SingleAngle {
      private final float angle;
      private final boolean isRelative;

      SingleAngle(float var1, boolean var2) {
         this.angle = â˜ƒ;
         this.isRelative = â˜ƒ;
      }

      public float getAngle(CommandSourceStack var1) {
         return Mth.wrapDegrees(this.isRelative ? this.angle + â˜ƒ.getRotation().y : this.angle);
      }
   }
}
