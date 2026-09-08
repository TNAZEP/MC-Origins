package net.minecraft.client.color.block;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MaterialColor;

public class BlockColors {
   private static final int DEFAULT = -1;
   private final IdMapper<BlockColor> blockColors = new IdMapper<>(32);
   private final Map<Block, Set<Property<?>>> coloringStates = Maps.newHashMap();

   public static BlockColors createDefault() {
      BlockColors â˜ƒ = new BlockColors();
      â˜ƒ.register(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null
               ? BiomeColors.getAverageGrassColor(var1, var0x.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? var2.below() : var2)
               : -1,
         Blocks.LARGE_FERN,
         Blocks.TALL_GRASS
      );
      â˜ƒ.addColoringState(DoublePlantBlock.HALF, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
      â˜ƒ.register(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.getAverageGrassColor(var1, var2) : GrassColor.get(0.5, 1.0),
         Blocks.GRASS_BLOCK,
         Blocks.FERN,
         Blocks.GRASS,
         Blocks.POTTED_FERN
      );
      â˜ƒ.register((var0x, var1, var2, var3) -> FoliageColor.getEvergreenColor(), Blocks.SPRUCE_LEAVES);
      â˜ƒ.register((var0x, var1, var2, var3) -> FoliageColor.getBirchColor(), Blocks.BIRCH_LEAVES);
      â˜ƒ.register(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.getAverageFoliageColor(var1, var2) : FoliageColor.getDefaultColor(),
         Blocks.OAK_LEAVES,
         Blocks.JUNGLE_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.VINE
      );
      â˜ƒ.register(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.getAverageWaterColor(var1, var2) : -1,
         Blocks.WATER,
         Blocks.BUBBLE_COLUMN,
         Blocks.WATER_CAULDRON
      );
      â˜ƒ.register((var0x, var1, var2, var3) -> RedStoneWireBlock.getColorForPower(var0x.getValue(RedStoneWireBlock.POWER)), Blocks.REDSTONE_WIRE);
      â˜ƒ.addColoringState(RedStoneWireBlock.POWER, Blocks.REDSTONE_WIRE);
      â˜ƒ.register((var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.getAverageGrassColor(var1, var2) : -1, Blocks.SUGAR_CANE);
      â˜ƒ.register((var0x, var1, var2, var3) -> 14731036, Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
      â˜ƒ.register((var0x, var1, var2, var3) -> {
         int â˜ƒ = var0x.getValue(StemBlock.AGE);
         int â˜ƒx = â˜ƒ * 32;
         int â˜ƒxx = 255 - â˜ƒ * 8;
         int â˜ƒxxx = â˜ƒ * 4;
         return â˜ƒx << 16 | â˜ƒxx << 8 | â˜ƒxxx;
      }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
      â˜ƒ.addColoringState(StemBlock.AGE, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
      â˜ƒ.register((var0x, var1, var2, var3) -> var1 != null && var2 != null ? 2129968 : 7455580, Blocks.LILY_PAD);
      return â˜ƒ;
   }

   public int getColor(BlockState var1, Level var2, BlockPos var3) {
      BlockColor â˜ƒ = this.blockColors.byId(Registry.BLOCK.getId(â˜ƒ.getBlock()));
      if (â˜ƒ != null) {
         return â˜ƒ.getColor(â˜ƒ, null, null, 0);
      } else {
         MaterialColor â˜ƒ = â˜ƒ.getMapColor(â˜ƒ, â˜ƒ);
         return â˜ƒ != null ? â˜ƒ.col : -1;
      }
   }

   public int getColor(BlockState var1, @Nullable BlockAndTintGetter var2, @Nullable BlockPos var3, int var4) {
      BlockColor â˜ƒ = this.blockColors.byId(Registry.BLOCK.getId(â˜ƒ.getBlock()));
      return â˜ƒ == null ? -1 : â˜ƒ.getColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void register(BlockColor var1, Block... var2) {
      for(Block â˜ƒ : â˜ƒ) {
         this.blockColors.addMapping(â˜ƒ, Registry.BLOCK.getId(â˜ƒ));
      }
   }

   private void addColoringStates(Set<Property<?>> var1, Block... var2) {
      for(Block â˜ƒ : â˜ƒ) {
         this.coloringStates.put(â˜ƒ, â˜ƒ);
      }
   }

   private void addColoringState(Property<?> var1, Block... var2) {
      this.addColoringStates(ImmutableSet.of(â˜ƒ), â˜ƒ);
   }

   public Set<Property<?>> getColoringProperties(Block var1) {
      return (Set<Property<?>>)this.coloringStates.getOrDefault(â˜ƒ, ImmutableSet.of());
   }
}
