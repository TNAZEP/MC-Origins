package net.minecraft.commands.arguments.item;

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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPredicateArgument implements ArgumentType<ItemPredicateArgument.Result> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.item.tag.unknown", var0)
   );

   public static ItemPredicateArgument itemPredicate() {
      return new ItemPredicateArgument();
   }

   public ItemPredicateArgument.Result parse(StringReader var1) throws CommandSyntaxException {
      ItemParser â˜ƒ = new ItemParser(â˜ƒ, true).parse();
      if (â˜ƒ.getItem() != null) {
         ItemPredicateArgument.ItemPredicate â˜ƒx = new ItemPredicateArgument.ItemPredicate(â˜ƒ.getItem(), â˜ƒ.getNbt());
         return var1x -> â˜ƒ;
      } else {
         ResourceLocation â˜ƒ = â˜ƒ.getTag();
         return var2x -> {
            Tag<Item> â˜ƒ = var2x.getSource()
               .getServer()
               .getTags()
               .getTagOrThrow(Registry.ITEM_REGISTRY, â˜ƒ, var0x -> ERROR_UNKNOWN_TAG.create(var0x.toString()));
            return new ItemPredicateArgument.TagPredicate(â˜ƒ, â˜ƒ.getNbt());
         };
      }
   }

   public static Predicate<ItemStack> getItemPredicate(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<ItemPredicateArgument.Result>getArgument(â˜ƒ, ItemPredicateArgument.Result.class).create(â˜ƒ);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
      â˜ƒ.setCursor(â˜ƒ.getStart());
      ItemParser â˜ƒx = new ItemParser(â˜ƒ, true);

      try {
         â˜ƒx.parse();
      } catch (CommandSyntaxException var6) {
      }

      return â˜ƒx.fillSuggestions(â˜ƒ, ItemTags.getAllTags());
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static class ItemPredicate implements Predicate<ItemStack> {
      private final Item item;
      @Nullable
      private final CompoundTag nbt;

      public ItemPredicate(Item var1, @Nullable CompoundTag var2) {
         this.item = â˜ƒ;
         this.nbt = â˜ƒ;
      }

      public boolean test(ItemStack var1) {
         return â˜ƒ.is(this.item) && NbtUtils.compareNbt(this.nbt, â˜ƒ.getTag(), true);
      }
   }

   public interface Result {
      Predicate<ItemStack> create(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<ItemStack> {
      private final Tag<Item> tag;
      @Nullable
      private final CompoundTag nbt;

      public TagPredicate(Tag<Item> var1, @Nullable CompoundTag var2) {
         this.tag = â˜ƒ;
         this.nbt = â˜ƒ;
      }

      public boolean test(ItemStack var1) {
         return â˜ƒ.is(this.tag) && NbtUtils.compareNbt(this.nbt, â˜ƒ.getTag(), true);
      }
   }
}
