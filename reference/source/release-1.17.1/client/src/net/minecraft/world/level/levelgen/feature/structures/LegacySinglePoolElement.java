package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class LegacySinglePoolElement extends SinglePoolElement {
   public static final Codec<LegacySinglePoolElement> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(templateCodec(), processorsCodec(), projectionCodec()).apply(var0, LegacySinglePoolElement::new)
   );

   protected LegacySinglePoolElement(
      Either<ResourceLocation, StructureTemplate> var1, Supplier<StructureProcessorList> var2, StructureTemplatePool.Projection var3
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected StructurePlaceSettings getSettings(Rotation var1, BoundingBox var2, boolean var3) {
      StructurePlaceSettings â˜ƒ = super.getSettings(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      â˜ƒ.addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
      return â˜ƒ;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.LEGACY;
   }

   @Override
   public String toString() {
      return "LegacySingle[" + this.template + "]";
   }
}
