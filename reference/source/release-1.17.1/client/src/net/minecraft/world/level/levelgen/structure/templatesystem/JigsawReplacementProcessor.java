package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class JigsawReplacementProcessor extends StructureProcessor {
   public static final Codec<JigsawReplacementProcessor> CODEC = Codec.unit((Supplier<JigsawReplacementProcessor>)(() -> JigsawReplacementProcessor.INSTANCE));
   public static final JigsawReplacementProcessor INSTANCE = new JigsawReplacementProcessor();

   private JigsawReplacementProcessor() {
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
      BlockState â˜ƒ = â˜ƒ.state;
      if (â˜ƒ.is(Blocks.JIGSAW)) {
         String â˜ƒx = â˜ƒ.nbt.getString("final_state");
         BlockStateParser â˜ƒxx = new BlockStateParser(new StringReader(â˜ƒx), false);

         try {
            â˜ƒxx.parse(true);
         } catch (CommandSyntaxException var11) {
            throw new RuntimeException(var11);
         }

         return â˜ƒxx.getState().is(Blocks.STRUCTURE_VOID) ? null : new StructureTemplate.StructureBlockInfo(â˜ƒ.pos, â˜ƒxx.getState(), null);
      } else {
         return â˜ƒ;
      }
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.JIGSAW_REPLACEMENT;
   }
}
