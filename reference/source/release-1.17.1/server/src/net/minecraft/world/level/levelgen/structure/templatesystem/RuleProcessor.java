package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class RuleProcessor extends StructureProcessor {
   public static final Codec<RuleProcessor> CODEC = ProcessorRule.CODEC
      .listOf()
      .fieldOf("rules")
      .<RuleProcessor>xmap(RuleProcessor::new, var0 -> var0.rules)
      .codec();
   private final ImmutableList<ProcessorRule> rules;

   public RuleProcessor(List<? extends ProcessorRule> var1) {
      this.rules = ImmutableList.copyOf(â˜ƒ);
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
      Random â˜ƒ = new Random(Mth.getSeed(â˜ƒ.pos));
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.pos);

      for(ProcessorRule â˜ƒxx : this.rules) {
         if (â˜ƒxx.test(â˜ƒ.state, â˜ƒx, â˜ƒ.pos, â˜ƒ.pos, â˜ƒ, â˜ƒ)) {
            return new StructureTemplate.StructureBlockInfo(â˜ƒ.pos, â˜ƒxx.getOutputState(), â˜ƒxx.getOutputTag());
         }
      }

      return â˜ƒ;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.RULE;
   }
}
