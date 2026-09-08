package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockPredicateArgument implements ArgumentType<BlockPredicateArgument.Result> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.block.tag.unknown", var0)
   );

   public static BlockPredicateArgument blockPredicate() {
      return new BlockPredicateArgument();
   }

   public BlockPredicateArgument.Result parse(StringReader var1) throws CommandSyntaxException {
      BlockStateParser â˜ƒ = new BlockStateParser(â˜ƒ, true).parse(true);
      if (â˜ƒ.getState() != null) {
         BlockPredicateArgument.BlockPredicate â˜ƒx = new BlockPredicateArgument.BlockPredicate(â˜ƒ.getState(), â˜ƒ.getProperties().keySet(), â˜ƒ.getNbt());
         return var1x -> â˜ƒ;
      } else {
         ResourceLocation â˜ƒ = â˜ƒ.getTag();
         return var2x -> {
            Tag<Block> â˜ƒ = var2x.getTagOrThrow(Registry.BLOCK_REGISTRY, â˜ƒ, var0x -> ERROR_UNKNOWN_TAG.create(var0x.toString()));
            return new BlockPredicateArgument.TagPredicate(â˜ƒ, â˜ƒ.getVagueProperties(), â˜ƒ.getNbt());
         };
      }
   }

   public static Predicate<BlockInWorld> getBlockPredicate(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<BlockPredicateArgument.Result>getArgument(â˜ƒ, BlockPredicateArgument.Result.class).create(â˜ƒ.getSource().getServer().getTags());
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
      â˜ƒ.setCursor(â˜ƒ.getStart());
      BlockStateParser â˜ƒx = new BlockStateParser(â˜ƒ, true);

      try {
         â˜ƒx.parse(true);
      } catch (CommandSyntaxException var6) {
      }

      return â˜ƒx.fillSuggestions(â˜ƒ, BlockTags.getAllTags());
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static class BlockPredicate implements Predicate<BlockInWorld> {
      private final BlockState state;
      private final Set<Property<?>> properties;
      @Nullable
      private final CompoundTag nbt;

      public BlockPredicate(BlockState var1, Set<Property<?>> var2, @Nullable CompoundTag var3) {
         this.state = â˜ƒ;
         this.properties = â˜ƒ;
         this.nbt = â˜ƒ;
      }

      public boolean test(BlockInWorld var1) {
         BlockState â˜ƒ = â˜ƒ.getState();
         if (!â˜ƒ.is(this.state.getBlock())) {
            return false;
         } else {
            for(Property<?> â˜ƒ : this.properties) {
               if (â˜ƒ.getValue(â˜ƒ) != this.state.getValue(â˜ƒ)) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity â˜ƒ = â˜ƒ.getEntity();
               return â˜ƒ != null && NbtUtils.compareNbt(this.nbt, â˜ƒ.save(new CompoundTag()), true);
            }
         }
      }
   }

   public interface Result {
      Predicate<BlockInWorld> create(TagContainer var1) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<BlockInWorld> {
      private final Tag<Block> tag;
      @Nullable
      private final CompoundTag nbt;
      private final Map<String, String> vagueProperties;

      TagPredicate(Tag<Block> var1, Map<String, String> var2, @Nullable CompoundTag var3) {
         this.tag = â˜ƒ;
         this.vagueProperties = â˜ƒ;
         this.nbt = â˜ƒ;
      }

      public boolean test(BlockInWorld var1) {
         BlockState â˜ƒ = â˜ƒ.getState();
         if (!â˜ƒ.is(this.tag)) {
            return false;
         } else {
            for(Entry<String, String> â˜ƒ : this.vagueProperties.entrySet()) {
               Property<?> â˜ƒx = â˜ƒ.getBlock().getStateDefinition().getProperty((String)â˜ƒ.getKey());
               if (â˜ƒx == null) {
                  return false;
               }

               Comparable<?> â˜ƒx = (Comparable)â˜ƒx.getValue((String)â˜ƒ.getValue()).orElse(null);
               if (â˜ƒx == null) {
                  return false;
               }

               if (â˜ƒ.getValue(â˜ƒx) != â˜ƒx) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity â˜ƒ = â˜ƒ.getEntity();
               return â˜ƒ != null && NbtUtils.compareNbt(this.nbt, â˜ƒ.save(new CompoundTag()), true);
            }
         }
      }
   }
}
