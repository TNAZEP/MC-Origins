package net.minecraft.command.arguments;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityArgument implements ArgumentType<EntitySelector> {
   private static final Collection<String> field_201310_f = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType field_197098_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.entity.toomany"));
   public static final SimpleCommandExceptionType field_197099_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.player.toomany"));
   public static final SimpleCommandExceptionType field_197100_c = new SimpleCommandExceptionType(new TextComponentTranslation("argument.player.entities"));
   public static final SimpleCommandExceptionType field_197101_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.notfound.entity")
   );
   public static final SimpleCommandExceptionType field_197102_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.notfound.player")
   );
   public static final SimpleCommandExceptionType field_210323_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.selector.not_allowed")
   );
   private final boolean field_197103_f;
   private final boolean field_197104_g;

   protected EntityArgument(boolean var1, boolean var2) {
      this.field_197103_f = ☃;
      this.field_197104_g = ☃;
   }

   public static EntityArgument func_197086_a() {
      return new EntityArgument(true, false);
   }

   public static Entity func_197088_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<EntitySelector>getArgument(☃, EntitySelector.class).func_197340_a(☃.getSource());
   }

   public static EntityArgument func_197093_b() {
      return new EntityArgument(false, false);
   }

   public static Collection<? extends Entity> func_197097_b(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      Collection<? extends Entity> ☃ = func_197087_c(☃, ☃);
      if (☃.isEmpty()) {
         throw field_197101_d.create();
      } else {
         return ☃;
      }
   }

   public static Collection<? extends Entity> func_197087_c(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<EntitySelector>getArgument(☃, EntitySelector.class).func_197341_b(☃.getSource());
   }

   public static Collection<EntityPlayerMP> func_201309_d(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<EntitySelector>getArgument(☃, EntitySelector.class).func_197342_d(☃.getSource());
   }

   public static EntityArgument func_197096_c() {
      return new EntityArgument(true, true);
   }

   public static EntityPlayerMP func_197089_d(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<EntitySelector>getArgument(☃, EntitySelector.class).func_197347_c(☃.getSource());
   }

   public static EntityArgument func_197094_d() {
      return new EntityArgument(false, true);
   }

   public static Collection<EntityPlayerMP> func_197090_e(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      List<EntityPlayerMP> ☃ = ☃.<EntitySelector>getArgument(☃, EntitySelector.class).func_197342_d(☃.getSource());
      if (☃.isEmpty()) {
         throw field_197102_e.create();
      } else {
         return ☃;
      }
   }

   public EntitySelector parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = 0;
      EntitySelectorParser ☃x = new EntitySelectorParser(☃);
      EntitySelector ☃xx = ☃x.func_201345_m();
      if (☃xx.func_197346_a() > 1 && this.field_197103_f) {
         if (this.field_197104_g) {
            ☃.setCursor(0);
            throw field_197099_b.createWithContext(☃);
         } else {
            ☃.setCursor(0);
            throw field_197098_a.createWithContext(☃);
         }
      } else if (☃xx.func_197351_b() && this.field_197104_g && !☃xx.func_197352_c()) {
         ☃.setCursor(0);
         throw field_197100_c.createWithContext(☃);
      } else {
         return ☃xx;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (☃.getSource() instanceof ISuggestionProvider) {
         StringReader ☃ = new StringReader(☃.getInput());
         ☃.setCursor(☃.getStart());
         ISuggestionProvider ☃x = (ISuggestionProvider)☃.getSource();
         EntitySelectorParser ☃xx = new EntitySelectorParser(☃, ☃x.func_197034_c(2));

         try {
            ☃xx.func_201345_m();
         } catch (CommandSyntaxException var7) {
         }

         return ☃xx.func_201993_a(☃, var2x -> {
            Collection<String> ☃ = ☃.func_197011_j();
            Iterable<String> ☃x = (Iterable<String>)(this.field_197104_g ? ☃ : Iterables.concat(☃, ☃.func_211270_p()));
            ISuggestionProvider.func_197005_b(☃x, var2x);
         });
      } else {
         return Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201310_f;
   }

   public static class Serializer implements IArgumentSerializer<EntityArgument> {
      public void func_197072_a(EntityArgument var1, PacketBuffer var2) {
         byte ☃ = 0;
         if (☃.field_197103_f) {
            ☃ = (byte)(☃ | 1);
         }

         if (☃.field_197104_g) {
            ☃ = (byte)(☃ | 2);
         }

         ☃.writeByte(☃);
      }

      public EntityArgument func_197071_b(PacketBuffer var1) {
         byte ☃ = ☃.readByte();
         return new EntityArgument((☃ & 1) != 0, (☃ & 2) != 0);
      }

      public void func_212244_a(EntityArgument var1, JsonObject var2) {
         ☃.addProperty("amount", ☃.field_197103_f ? "single" : "multiple");
         ☃.addProperty("type", ☃.field_197104_g ? "players" : "entities");
      }
   }
}
