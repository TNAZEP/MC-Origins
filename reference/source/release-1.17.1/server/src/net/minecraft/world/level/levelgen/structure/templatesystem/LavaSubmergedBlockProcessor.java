package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LavaSubmergedBlockProcessor extends StructureProcessor {
   public static final Codec<LavaSubmergedBlockProcessor> CODEC = Codec.unit(
      (Supplier<LavaSubmergedBlockProcessor>)(() -> LavaSubmergedBlockProcessor.INSTANCE)
   );
   public static final LavaSubmergedBlockProcessor INSTANCE = new LavaSubmergedBlockProcessor();

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
      BlockPos â˜ƒ = â˜ƒ.pos;
      boolean â˜ƒx = â˜ƒ.getBlockState(â˜ƒ).is(Blocks.LAVA);
      return â˜ƒx && !Block.isShapeFullBlock(â˜ƒ.state.getShape(â˜ƒ, â˜ƒ))
         ? new StructureTemplate.StructureBlockInfo(â˜ƒ, Blocks.LAVA.defaultBlockState(), â˜ƒ.nbt)
         : â˜ƒ;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.LAVA_SUBMERGED_BLOCK;
   }
}
