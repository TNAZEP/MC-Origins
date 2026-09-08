package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SinglePoolElement extends StructurePoolElement {
   private static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(
      SinglePoolElement::encodeTemplate, ResourceLocation.CODEC.map(Either::left)
   );
   public static final Codec<SinglePoolElement> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(templateCodec(), processorsCodec(), projectionCodec()).apply(var0, SinglePoolElement::new)
   );
   protected final Either<ResourceLocation, StructureTemplate> template;
   protected final Supplier<StructureProcessorList> processors;

   private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> var0, DynamicOps<T> var1, T var2) {
      Optional<ResourceLocation> â˜ƒ = â˜ƒ.left();
      return !â˜ƒ.isPresent()
         ? DataResult.error("Can not serialize a runtime pool element")
         : ResourceLocation.CODEC.encode((ResourceLocation)â˜ƒ.get(), â˜ƒ, â˜ƒ);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Supplier<StructureProcessorList>> processorsCodec() {
      return StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(var0 -> var0.processors);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<ResourceLocation, StructureTemplate>> templateCodec() {
      return TEMPLATE_CODEC.fieldOf("location").forGetter(var0 -> var0.template);
   }

   protected SinglePoolElement(Either<ResourceLocation, StructureTemplate> var1, Supplier<StructureProcessorList> var2, StructureTemplatePool.Projection var3) {
      super(â˜ƒ);
      this.template = â˜ƒ;
      this.processors = â˜ƒ;
   }

   public SinglePoolElement(StructureTemplate var1) {
      this(Either.right(â˜ƒ), () -> ProcessorLists.EMPTY, StructureTemplatePool.Projection.RIGID);
   }

   @Override
   public Vec3i getSize(StructureManager var1, Rotation var2) {
      StructureTemplate â˜ƒ = this.getTemplate(â˜ƒ);
      return â˜ƒ.getSize(â˜ƒ);
   }

   private StructureTemplate getTemplate(StructureManager var1) {
      return this.template.map(â˜ƒ::getOrCreate, Function.identity());
   }

   public List<StructureTemplate.StructureBlockInfo> getDataMarkers(StructureManager var1, BlockPos var2, Rotation var3, boolean var4) {
      StructureTemplate â˜ƒ = this.getTemplate(â˜ƒ);
      List<StructureTemplate.StructureBlockInfo> â˜ƒx = â˜ƒ.filterBlocks(â˜ƒ, new StructurePlaceSettings().setRotation(â˜ƒ), Blocks.STRUCTURE_BLOCK, â˜ƒ);
      List<StructureTemplate.StructureBlockInfo> â˜ƒxx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();

      for(StructureTemplate.StructureBlockInfo â˜ƒxxx : â˜ƒx) {
         if (â˜ƒxxx.nbt != null) {
            StructureMode â˜ƒxxxx = StructureMode.valueOf(â˜ƒxxx.nbt.getString("mode"));
            if (â˜ƒxxxx == StructureMode.DATA) {
               â˜ƒxx.add(â˜ƒxxx);
            }
         }
      }

      return â˜ƒxx;
   }

   @Override
   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureManager var1, BlockPos var2, Rotation var3, Random var4) {
      StructureTemplate â˜ƒ = this.getTemplate(â˜ƒ);
      List<StructureTemplate.StructureBlockInfo> â˜ƒx = â˜ƒ.filterBlocks(â˜ƒ, new StructurePlaceSettings().setRotation(â˜ƒ), Blocks.JIGSAW, true);
      Collections.shuffle(â˜ƒx, â˜ƒ);
      return â˜ƒx;
   }

   @Override
   public BoundingBox getBoundingBox(StructureManager var1, BlockPos var2, Rotation var3) {
      StructureTemplate â˜ƒ = this.getTemplate(â˜ƒ);
      return â˜ƒ.getBoundingBox(new StructurePlaceSettings().setRotation(â˜ƒ), â˜ƒ);
   }

   @Override
   public boolean place(
      StructureManager var1,
      WorldGenLevel var2,
      StructureFeatureManager var3,
      ChunkGenerator var4,
      BlockPos var5,
      BlockPos var6,
      Rotation var7,
      BoundingBox var8,
      Random var9,
      boolean var10
   ) {
      StructureTemplate â˜ƒ = this.getTemplate(â˜ƒ);
      StructurePlaceSettings â˜ƒx = this.getSettings(â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.placeInWorld(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, 18)) {
         return false;
      } else {
         for(StructureTemplate.StructureBlockInfo â˜ƒ : StructureTemplate.processBlockInfos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, this.getDataMarkers(â˜ƒ, â˜ƒ, â˜ƒ, false))) {
            this.handleDataMarker(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return true;
      }
   }

   protected StructurePlaceSettings getSettings(Rotation var1, BoundingBox var2, boolean var3) {
      StructurePlaceSettings â˜ƒ = new StructurePlaceSettings();
      â˜ƒ.setBoundingBox(â˜ƒ);
      â˜ƒ.setRotation(â˜ƒ);
      â˜ƒ.setKnownShape(true);
      â˜ƒ.setIgnoreEntities(false);
      â˜ƒ.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      â˜ƒ.setFinalizeEntities(true);
      if (!â˜ƒ) {
         â˜ƒ.addProcessor(JigsawReplacementProcessor.INSTANCE);
      }

      ((StructureProcessorList)this.processors.get()).list().forEach(â˜ƒ::addProcessor);
      this.getProjection().getProcessors().forEach(â˜ƒ::addProcessor);
      return â˜ƒ;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.SINGLE;
   }

   public String toString() {
      return "Single[" + this.template + "]";
   }
}
