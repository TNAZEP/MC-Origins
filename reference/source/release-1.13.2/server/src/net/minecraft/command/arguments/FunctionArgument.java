package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class FunctionArgument implements ArgumentType<FunctionArgument.IResult> {
   private static final Collection<String> field_201338_a = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType field_200023_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.function.tag.unknown", var0)
   );
   private static final DynamicCommandExceptionType field_200024_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.function.unknown", var0)
   );

   public static FunctionArgument func_200021_a() {
      return new FunctionArgument();
   }

   public FunctionArgument.IResult parse(StringReader var1) throws CommandSyntaxException {
      if (☃.canRead() && ☃.peek() == '#') {
         ☃.skip();
         ResourceLocation ☃ = ResourceLocation.func_195826_a(☃);
         return var1x -> {
            Tag<FunctionObject> ☃ = var1x.getSource().func_197028_i().func_193030_aL().func_200000_g().func_199910_a(☃);
            if (☃ == null) {
               throw field_200023_a.create(☃.toString());
            } else {
               return ☃.func_199885_a();
            }
         };
      } else {
         ResourceLocation ☃ = ResourceLocation.func_195826_a(☃);
         return var1x -> {
            FunctionObject ☃ = var1x.getSource().func_197028_i().func_193030_aL().func_193058_a(☃);
            if (☃ == null) {
               throw field_200024_b.create(☃.toString());
            } else {
               return Collections.singleton(☃);
            }
         };
      }
   }

   public static Collection<FunctionObject> func_200022_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<FunctionArgument.IResult>getArgument(☃, FunctionArgument.IResult.class).create(☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201338_a;
   }

   public interface IResult {
      Collection<FunctionObject> create(CommandContext<CommandSource> var1) throws CommandSyntaxException;
   }
}
