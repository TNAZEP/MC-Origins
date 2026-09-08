package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;

public class OperationArgument implements ArgumentType<OperationArgument.IOperation> {
   private static final Collection<String> field_201319_a = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType field_197185_a = new SimpleCommandExceptionType(new TextComponentTranslation("arguments.operation.invalid"));
   private static final SimpleCommandExceptionType field_197186_b = new SimpleCommandExceptionType(new TextComponentTranslation("arguments.operation.div0"));

   public static OperationArgument func_197184_a() {
      return new OperationArgument();
   }

   public static OperationArgument.IOperation func_197179_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.getArgument(☃, OperationArgument.IOperation.class);
   }

   public OperationArgument.IOperation parse(StringReader var1) throws CommandSyntaxException {
      if (!☃.canRead()) {
         throw field_197185_a.create();
      } else {
         int ☃ = ☃.getCursor();

         while(☃.canRead() && ☃.peek() != ' ') {
            ☃.skip();
         }

         return func_197177_a(☃.getString().substring(☃, ☃.getCursor()));
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197008_a(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201319_a;
   }

   private static OperationArgument.IOperation func_197177_a(String var0) throws CommandSyntaxException {
      return (OperationArgument.IOperation)(☃.equals("><") ? (var0x, var1) -> {
         int ☃ = var0x.func_96652_c();
         var0x.func_96647_c(var1.func_96652_c());
         var1.func_96647_c(☃);
      } : func_197182_b(☃));
   }

   private static OperationArgument.Operation func_197182_b(String var0) throws CommandSyntaxException {
      switch(☃) {
         case "=":
            return (var0x, var1) -> var1;
         case "+=":
            return (var0x, var1) -> var0x + var1;
         case "-=":
            return (var0x, var1) -> var0x - var1;
         case "*=":
            return (var0x, var1) -> var0x * var1;
         case "/=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw field_197186_b.create();
               } else {
                  return MathHelper.func_76137_a(var0x, var1);
               }
            };
         case "%=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw field_197186_b.create();
               } else {
                  return MathHelper.func_180184_b(var0x, var1);
               }
            };
         case "<":
            return Math::min;
         case ">":
            return Math::max;
         default:
            throw field_197185_a.create();
      }
   }

   @FunctionalInterface
   public interface IOperation {
      void apply(Score var1, Score var2) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface Operation extends OperationArgument.IOperation {
      int apply(int var1, int var2) throws CommandSyntaxException;

      @Override
      default void apply(Score var1, Score var2) throws CommandSyntaxException {
         ☃.func_96647_c(this.apply(☃.func_96652_c(), ☃.func_96652_c()));
      }
   }
}
