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
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.IProperty;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class BlockPredicateArgument implements ArgumentType<BlockPredicateArgument.IResult> {
   private static final Collection<String> field_201331_a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private static final DynamicCommandExceptionType field_199826_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.block.tag.unknown", var0)
   );

   public static BlockPredicateArgument func_199824_a() {
      return new BlockPredicateArgument();
   }

   public BlockPredicateArgument.IResult parse(StringReader var1) throws CommandSyntaxException {
      BlockStateParser ☃ = new BlockStateParser(☃, true).func_197243_a(true);
      if (☃.func_197249_b() != null) {
         BlockPredicateArgument.BlockPredicate ☃x = new BlockPredicateArgument.BlockPredicate(☃.func_197249_b(), ☃.func_197254_a().keySet(), ☃.func_197241_c());
         return var1x -> ☃;
      } else {
         ResourceLocation ☃ = ☃.func_199829_d();
         return var2x -> {
            Tag<Block> ☃ = var2x.func_199717_a().func_199910_a(☃);
            if (☃ == null) {
               throw field_199826_a.create(☃.toString());
            } else {
               return new BlockPredicateArgument.TagPredicate(☃, ☃.func_200139_j(), ☃.func_197241_c());
            }
         };
      }
   }

   public static Predicate<BlockWorldState> func_199825_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<BlockPredicateArgument.IResult>getArgument(☃, BlockPredicateArgument.IResult.class).create(☃.getSource().func_197028_i().func_199731_aO());
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader ☃ = new StringReader(☃.getInput());
      ☃.setCursor(☃.getStart());
      BlockStateParser ☃x = new BlockStateParser(☃, true);

      try {
         ☃x.func_197243_a(true);
      } catch (CommandSyntaxException var6) {
      }

      return ☃x.func_197245_a(☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201331_a;
   }

   static class BlockPredicate implements Predicate<BlockWorldState> {
      private final IBlockState field_199817_a;
      private final Set<IProperty<?>> field_199818_b;
      @Nullable
      private final NBTTagCompound field_199819_c;

      public BlockPredicate(IBlockState var1, Set<IProperty<?>> var2, @Nullable NBTTagCompound var3) {
         this.field_199817_a = ☃;
         this.field_199818_b = ☃;
         this.field_199819_c = ☃;
      }

      public boolean test(BlockWorldState var1) {
         IBlockState ☃ = ☃.func_177509_a();
         if (☃.func_177230_c() != this.field_199817_a.func_177230_c()) {
            return false;
         } else {
            for(IProperty<?> ☃ : this.field_199818_b) {
               if (☃.func_177229_b(☃) != this.field_199817_a.func_177229_b(☃)) {
                  return false;
               }
            }

            if (this.field_199819_c == null) {
               return true;
            } else {
               TileEntity ☃ = ☃.func_177507_b();
               return ☃ != null && NBTUtil.func_181123_a(this.field_199819_c, ☃.func_189515_b(new NBTTagCompound()), true);
            }
         }
      }
   }

   public interface IResult {
      Predicate<BlockWorldState> create(NetworkTagManager var1) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<BlockWorldState> {
      private final Tag<Block> field_199820_a;
      @Nullable
      private final NBTTagCompound field_199821_b;
      private final Map<String, String> field_200133_c;

      private TagPredicate(Tag<Block> var1, Map<String, String> var2, @Nullable NBTTagCompound var3) {
         this.field_199820_a = ☃;
         this.field_200133_c = ☃;
         this.field_199821_b = ☃;
      }

      public boolean test(BlockWorldState var1) {
         IBlockState ☃ = ☃.func_177509_a();
         if (!☃.func_203425_a(this.field_199820_a)) {
            return false;
         } else {
            for(Entry<String, String> ☃ : this.field_200133_c.entrySet()) {
               IProperty<?> ☃x = ☃.func_177230_c().func_176194_O().func_185920_a((String)☃.getKey());
               if (☃x == null) {
                  return false;
               }

               Comparable<?> ☃x = (Comparable)☃x.func_185929_b((String)☃.getValue()).orElse(null);
               if (☃x == null) {
                  return false;
               }

               if (☃.func_177229_b(☃x) != ☃x) {
                  return false;
               }
            }

            if (this.field_199821_b == null) {
               return true;
            } else {
               TileEntity ☃ = ☃.func_177507_b();
               return ☃ != null && NBTUtil.func_181123_a(this.field_199821_b, ☃.func_189515_b(new NBTTagCompound()), true);
            }
         }
      }
   }
}
