package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;

public class GravityProcessor extends StructureProcessor {
   public static final Codec<GravityProcessor> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Heightmap.Types.CODEC.fieldOf("heightmap").orElse(Heightmap.Types.WORLD_SURFACE_WG).forGetter(var0x -> var0x.heightmap),
               Codec.INT.fieldOf("offset").orElse(0).forGetter(var0x -> var0x.offset)
            )
            .apply(var0, GravityProcessor::new)
   );
   private final Heightmap.Types heightmap;
   private final int offset;

   public GravityProcessor(Heightmap.Types var1, int var2) {
      this.heightmap = â˜ƒ;
      this.offset = â˜ƒ;
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
      Heightmap.Types â˜ƒ;
      if (â˜ƒ instanceof ServerLevel) {
         if (this.heightmap == Heightmap.Types.WORLD_SURFACE_WG) {
            â˜ƒ = Heightmap.Types.WORLD_SURFACE;
         } else if (this.heightmap == Heightmap.Types.OCEAN_FLOOR_WG) {
            â˜ƒ = Heightmap.Types.OCEAN_FLOOR;
         } else {
            â˜ƒ = this.heightmap;
         }
      } else {
         â˜ƒ = this.heightmap;
      }

      int â˜ƒ = â˜ƒ.getHeight(â˜ƒ, â˜ƒ.pos.getX(), â˜ƒ.pos.getZ()) + this.offset;
      int â˜ƒx = â˜ƒ.pos.getY();
      return new StructureTemplate.StructureBlockInfo(new BlockPos(â˜ƒ.pos.getX(), â˜ƒ + â˜ƒx, â˜ƒ.pos.getZ()), â˜ƒ.state, â˜ƒ.nbt);
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.GRAVITY;
   }
}
