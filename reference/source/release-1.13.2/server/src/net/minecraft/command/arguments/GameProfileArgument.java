package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;

public class GameProfileArgument implements ArgumentType<GameProfileArgument.IProfileProvider> {
   private static final Collection<String> field_201311_b = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
   public static final SimpleCommandExceptionType field_197111_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.player.unknown"));

   public static Collection<GameProfile> func_197109_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<GameProfileArgument.IProfileProvider>getArgument(☃, GameProfileArgument.IProfileProvider.class).getNames(☃.getSource());
   }

   public static GameProfileArgument func_197108_a() {
      return new GameProfileArgument();
   }

   public GameProfileArgument.IProfileProvider parse(StringReader var1) throws CommandSyntaxException {
      if (☃.canRead() && ☃.peek() == '@') {
         EntitySelectorParser ☃ = new EntitySelectorParser(☃);
         EntitySelector ☃x = ☃.func_201345_m();
         if (☃x.func_197351_b()) {
            throw EntityArgument.field_197100_c.create();
         } else {
            return new GameProfileArgument.ProfileProvider(☃x);
         }
      } else {
         int ☃ = ☃.getCursor();

         while(☃.canRead() && ☃.peek() != ' ') {
            ☃.skip();
         }

         String ☃x = ☃.getString().substring(☃, ☃.getCursor());
         return var1x -> {
            GameProfile ☃ = var1x.func_197028_i().func_152358_ax().func_152655_a(☃);
            if (☃ == null) {
               throw field_197111_a.create();
            } else {
               return Collections.singleton(☃);
            }
         };
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (☃.getSource() instanceof ISuggestionProvider) {
         StringReader ☃ = new StringReader(☃.getInput());
         ☃.setCursor(☃.getStart());
         EntitySelectorParser ☃x = new EntitySelectorParser(☃);

         try {
            ☃x.func_201345_m();
         } catch (CommandSyntaxException var6) {
         }

         return ☃x.func_201993_a(☃, var1x -> ISuggestionProvider.func_197005_b(((ISuggestionProvider)☃.getSource()).func_197011_j(), var1x));
      } else {
         return Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201311_b;
   }

   @FunctionalInterface
   public interface IProfileProvider {
      Collection<GameProfile> getNames(CommandSource var1) throws CommandSyntaxException;
   }

   public static class ProfileProvider implements GameProfileArgument.IProfileProvider {
      private final EntitySelector field_197106_a;

      public ProfileProvider(EntitySelector var1) {
         this.field_197106_a = ☃;
      }

      @Override
      public Collection<GameProfile> getNames(CommandSource var1) throws CommandSyntaxException {
         List<EntityPlayerMP> ☃ = this.field_197106_a.func_197342_d(☃);
         if (☃.isEmpty()) {
            throw EntityArgument.field_197102_e.create();
         } else {
            List<GameProfile> ☃ = Lists.<GameProfile>newArrayList();

            for(EntityPlayerMP ☃x : ☃) {
               ☃.add(☃x.func_146103_bH());
            }

            return ☃;
         }
      }
   }
}
