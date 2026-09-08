package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;

public class BlockAgeProcessor extends StructureProcessor {
   public static final Codec<BlockAgeProcessor> CODEC = Codec.FLOAT
      .fieldOf("mossiness")
      .<BlockAgeProcessor>xmap(BlockAgeProcessor::new, var0 -> var0.mossiness)
      .codec();
   private static final float PROBABILITY_OF_REPLACING_FULL_BLOCK = 0.5F;
   private static final float PROBABILITY_OF_REPLACING_STAIRS = 0.5F;
   private static final float PROBABILITY_OF_REPLACING_OBSIDIAN = 0.15F;
   private static final BlockState[] NON_MOSSY_REPLACEMENTS = new BlockState[]{
      Blocks.STONE_SLAB.defaultBlockState(), Blocks.STONE_BRICK_SLAB.defaultBlockState()
   };
   private final float mossiness;

   public BlockAgeProcessor(float var1) {
      this.mossiness = â˜ƒ;
   }

   @Nullable
   @Override
   public StructureTemplate.StructureBlockInfo processBlock(
      LevelReader var1,
      BlockPos var2,
      BlockPos var3,
      StructureTemplate.StructureBlockInfo var4,
      StructureTemplate.StructureBlockInfo var5,
      StructurePlaceSettings var6
   ) {
      Random â˜ƒ = â˜ƒ.getRandom(â˜ƒ.pos);
      BlockState â˜ƒx = â˜ƒ.state;
      BlockPos â˜ƒxx = â˜ƒ.pos;
      BlockState â˜ƒxxx = null;
      if (â˜ƒx.is(Blocks.STONE_BRICKS) || â˜ƒx.is(Blocks.STONE) || â˜ƒx.is(Blocks.CHISELED_STONE_BRICKS)) {
         â˜ƒxxx = this.maybeReplaceFullStoneBlock(â˜ƒ);
      } else if (â˜ƒx.is(BlockTags.STAIRS)) {
         â˜ƒxxx = this.maybeReplaceStairs(â˜ƒ, â˜ƒ.state);
      } else if (â˜ƒx.is(BlockTags.SLABS)) {
         â˜ƒxxx = this.maybeReplaceSlab(â˜ƒ);
      } else if (â˜ƒx.is(BlockTags.WALLS)) {
         â˜ƒxxx = this.maybeReplaceWall(â˜ƒ);
      } else if (â˜ƒx.is(Blocks.OBSIDIAN)) {
         â˜ƒxxx = this.maybeReplaceObsidian(â˜ƒ);
      }

      return â˜ƒxxx != null ? new StructureTemplate.StructureBlockInfo(â˜ƒxx, â˜ƒxxx, â˜ƒ.nbt) : â˜ƒ;
   }

   @Nullable
   private BlockState maybeReplaceFullStoneBlock(Random var1) {
      if (â˜ƒ.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] â˜ƒ = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), getRandomFacingStairs(â˜ƒ, Blocks.STONE_BRICK_STAIRS)};
         BlockState[] â˜ƒx = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), getRandomFacingStairs(â˜ƒ, Blocks.MOSSY_STONE_BRICK_STAIRS)};
         return this.getRandomBlock(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }

   @Nullable
   private BlockState maybeReplaceStairs(Random var1, BlockState var2) {
      Direction â˜ƒ = â˜ƒ.getValue(StairBlock.FACING);
      Half â˜ƒx = â˜ƒ.getValue(StairBlock.HALF);
      if (â˜ƒ.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] â˜ƒ = new BlockState[]{
            Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, â˜ƒ).setValue(StairBlock.HALF, â˜ƒx),
            Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState()
         };
         return this.getRandomBlock(â˜ƒ, NON_MOSSY_REPLACEMENTS, â˜ƒ);
      }
   }

   @Nullable
   private BlockState maybeReplaceSlab(Random var1) {
      return â˜ƒ.nextFloat() < this.mossiness ? Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState() : null;
   }

   @Nullable
   private BlockState maybeReplaceWall(Random var1) {
      return â˜ƒ.nextFloat() < this.mossiness ? Blocks.MOSSY_STONE_BRICK_WALL.defaultBlockState() : null;
   }

   @Nullable
   private BlockState maybeReplaceObsidian(Random var1) {
      return â˜ƒ.nextFloat() < 0.15F ? Blocks.CRYING_OBSIDIAN.defaultBlockState() : null;
   }

   private static BlockState getRandomFacingStairs(Random var0, Block var1) {
      return â˜ƒ.defaultBlockState()
         .setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ))
         .setValue(StairBlock.HALF, Half.values()[â˜ƒ.nextInt(Half.values().length)]);
   }

   private BlockState getRandomBlock(Random var1, BlockState[] var2, BlockState[] var3) {
      return â˜ƒ.nextFloat() < this.mossiness ? getRandomBlock(â˜ƒ, â˜ƒ) : getRandomBlock(â˜ƒ, â˜ƒ);
   }

   private static BlockState getRandomBlock(Random var0, BlockState[] var1) {
      return â˜ƒ[â˜ƒ.nextInt(â˜ƒ.length)];
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLOCK_AGE;
   }
}
