package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class BlockRotProcessor extends StructureProcessor {
   public static final Codec<BlockRotProcessor> CODEC = Codec.FLOAT
      .fieldOf("integrity")
      .orElse(1.0F)
      .<BlockRotProcessor>xmap(BlockRotProcessor::new, var0 -> var0.integrity)
      .codec();
   private final float integrity;

   public BlockRotProcessor(float var1) {
      this.integrity = â˜ƒ;
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
      return !(this.integrity >= 1.0F) && !(â˜ƒ.nextFloat() <= this.integrity) ? null : â˜ƒ;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLOCK_ROT;
   }
}
