package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;

public class ScoreHolderArgument implements ArgumentType<ScoreHolderArgument.INameProvider> {
   public static final SuggestionProvider<CommandSource> field_201326_a = (var0, var1) -> {
      StringReader ☃ = new StringReader(var1.getInput());
      ☃.setCursor(var1.getStart());
      EntitySelectorParser ☃x = new EntitySelectorParser(☃);

      try {
         ☃x.func_201345_m();
      } catch (CommandSyntaxException var5) {
      }

      return ☃x.func_201993_a(var1, var1x -> ISuggestionProvider.func_197005_b(var0.getSource().func_197011_j(), var1x));
   };
   private static final Collection<String> field_201327_b = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType field_197215_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.scoreHolder.empty"));
   private final boolean field_197216_b;

   public ScoreHolderArgument(boolean var1) {
      this.field_197216_b = ☃;
   }

   public static String func_197211_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return (String)func_197213_b(☃, ☃).iterator().next();
   }

   public static Collection<String> func_197213_b(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return func_197210_a(☃, ☃, Collections::emptyList);
   }

   public static Collection<String> func_211707_c(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return func_197210_a(☃, ☃, ☃.getSource().func_197028_i().func_200251_aP()::func_96526_d);
   }

   public static Collection<String> func_197210_a(CommandContext<CommandSource> var0, String var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
      Collection<String> ☃ = ☃.<ScoreHolderArgument.INameProvider>getArgument(☃, ScoreHolderArgument.INameProvider.class).getNames(☃.getSource(), ☃);
      if (☃.isEmpty()) {
         throw EntityArgument.field_197101_d.create();
      } else {
         return ☃;
      }
   }

   public static ScoreHolderArgument func_197209_a() {
      return new ScoreHolderArgument(false);
   }

   public static ScoreHolderArgument func_197214_b() {
      return new ScoreHolderArgument(true);
   }

   public ScoreHolderArgument.INameProvider parse(StringReader var1) throws CommandSyntaxException {
      if (☃.canRead() && ☃.peek() == '@') {
         EntitySelectorParser ☃ = new EntitySelectorParser(☃);
         EntitySelector ☃x = ☃.func_201345_m();
         if (!this.field_197216_b && ☃x.func_197346_a() > 1) {
            throw EntityArgument.field_197098_a.create();
         } else {
            return new ScoreHolderArgument.NameProvider(☃x);
         }
      } else {
         int ☃ = ☃.getCursor();

         while(☃.canRead() && ☃.peek() != ' ') {
            ☃.skip();
         }

         String ☃x = ☃.getString().substring(☃, ☃.getCursor());
         if (☃x.equals("*")) {
            return (var0, var1x) -> {
               Collection<String> ☃ = (Collection)var1x.get();
               if (☃.isEmpty()) {
                  throw field_197215_a.create();
               } else {
                  return ☃;
               }
            };
         } else {
            Collection<String> ☃x = Collections.singleton(☃x);
            return (var1x, var2x) -> ☃;
         }
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201327_b;
   }

   @FunctionalInterface
   public interface INameProvider {
      Collection<String> getNames(CommandSource var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
   }

   public static class NameProvider implements ScoreHolderArgument.INameProvider {
      private final EntitySelector field_197205_a;

      public NameProvider(EntitySelector var1) {
         this.field_197205_a = ☃;
      }

      @Override
      public Collection<String> getNames(CommandSource var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
         List<? extends Entity> ☃ = this.field_197205_a.func_197341_b(☃);
         if (☃.isEmpty()) {
            throw EntityArgument.field_197101_d.create();
         } else {
            List<String> ☃ = Lists.newArrayList();

            for(Entity ☃x : ☃) {
               ☃.add(☃x.func_195047_I_());
            }

            return ☃;
         }
      }
   }

   public static class Serializer implements IArgumentSerializer<ScoreHolderArgument> {
      public void func_197072_a(ScoreHolderArgument var1, PacketBuffer var2) {
         byte ☃ = 0;
         if (☃.field_197216_b) {
            ☃ = (byte)(☃ | 1);
         }

         ☃.writeByte(☃);
      }

      public ScoreHolderArgument func_197071_b(PacketBuffer var1) {
         byte ☃ = ☃.readByte();
         boolean ☃x = (☃ & 1) != 0;
         return new ScoreHolderArgument(☃x);
      }

      public void func_212244_a(ScoreHolderArgument var1, JsonObject var2) {
         ☃.addProperty("amount", ☃.field_197216_b ? "multiple" : "single");
      }
   }
}
