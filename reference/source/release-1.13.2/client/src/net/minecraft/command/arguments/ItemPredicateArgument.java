package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class ItemPredicateArgument implements ArgumentType<ItemPredicateArgument.IResult> {
   private static final Collection<String> field_201340_a = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private static final DynamicCommandExceptionType field_199849_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.item.tag.unknown", var0)
   );

   public static ItemPredicateArgument func_199846_a() {
      return new ItemPredicateArgument();
   }

   public ItemPredicateArgument.IResult parse(StringReader var1) throws CommandSyntaxException {
      ItemParser ☃ = new ItemParser(☃, true).func_197327_f();
      if (☃.func_197326_b() != null) {
         ItemPredicateArgument.ItemPredicate ☃x = new ItemPredicateArgument.ItemPredicate(☃.func_197326_b(), ☃.func_197325_c());
         return var1x -> ☃;
      } else {
         ResourceLocation ☃ = ☃.func_199835_d();
         return var2x -> {
            Tag<Item> ☃ = var2x.getSource().func_197028_i().func_199731_aO().func_199715_b().func_199910_a(☃);
            if (☃ == null) {
               throw field_199849_a.create(☃.toString());
            } else {
               return new ItemPredicateArgument.TagPredicate(☃, ☃.func_197325_c());
            }
         };
      }
   }

   public static Predicate<ItemStack> func_199847_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<ItemPredicateArgument.IResult>getArgument(☃, ItemPredicateArgument.IResult.class).create(☃);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader ☃ = new StringReader(☃.getInput());
      ☃.setCursor(☃.getStart());
      ItemParser ☃x = new ItemParser(☃, true);

      try {
         ☃x.func_197327_f();
      } catch (CommandSyntaxException var6) {
      }

      return ☃x.func_197329_a(☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201340_a;
   }

   public interface IResult {
      Predicate<ItemStack> create(CommandContext<CommandSource> var1) throws CommandSyntaxException;
   }

   static class ItemPredicate implements Predicate<ItemStack> {
      private final Item field_199841_a;
      @Nullable
      private final NBTTagCompound field_199842_b;

      public ItemPredicate(Item var1, @Nullable NBTTagCompound var2) {
         this.field_199841_a = ☃;
         this.field_199842_b = ☃;
      }

      public boolean test(ItemStack var1) {
         return ☃.func_77973_b() == this.field_199841_a && NBTUtil.func_181123_a(this.field_199842_b, ☃.func_77978_p(), true);
      }
   }

   static class TagPredicate implements Predicate<ItemStack> {
      private final Tag<Item> field_199843_a;
      @Nullable
      private final NBTTagCompound field_199844_b;

      public TagPredicate(Tag<Item> var1, @Nullable NBTTagCompound var2) {
         this.field_199843_a = ☃;
         this.field_199844_b = ☃;
      }

      public boolean test(ItemStack var1) {
         return this.field_199843_a.func_199685_a_(☃.func_77973_b()) && NBTUtil.func_181123_a(this.field_199844_b, ☃.func_77978_p(), true);
      }
   }
}
