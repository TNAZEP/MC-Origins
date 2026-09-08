package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.command.CommandSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;

public class SwizzleArgument implements ArgumentType<EnumSet<EnumFacing.Axis>> {
   private static final Collection<String> field_201335_a = Arrays.asList("xyz", "x");
   private static final SimpleCommandExceptionType field_197294_a = new SimpleCommandExceptionType(new TextComponentTranslation("arguments.swizzle.invalid"));

   public static SwizzleArgument func_197293_a() {
      return new SwizzleArgument();
   }

   public static EnumSet<EnumFacing.Axis> func_197291_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, EnumSet.class);
   }

   public EnumSet<EnumFacing.Axis> parse(StringReader var1) throws CommandSyntaxException {
      EnumSet<EnumFacing.Axis> ☃ = EnumSet.noneOf(EnumFacing.Axis.class);

      while(☃.canRead() && ☃.peek() != ' ') {
         char ☃xx = ☃.read();
         EnumFacing.Axis ☃x;
         switch(☃xx) {
            case 'x':
               ☃x = EnumFacing.Axis.X;
               break;
            case 'y':
               ☃x = EnumFacing.Axis.Y;
               break;
            case 'z':
               ☃x = EnumFacing.Axis.Z;
               break;
            default:
               throw field_197294_a.create();
         }

         if (☃.contains(☃x)) {
            throw field_197294_a.create();
         }

         ☃.add(☃x);
      }

      return ☃;
   }

   @Override
   public Collection<String> getExamples() {
      return field_201335_a;
   }
}
