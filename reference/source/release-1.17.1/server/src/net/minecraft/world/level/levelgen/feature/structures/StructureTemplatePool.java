package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureTemplatePool {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SIZE_UNSET = Integer.MIN_VALUE;
   public static final Codec<StructureTemplatePool> DIRECT_CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ResourceLocation.CODEC.fieldOf("name").forGetter(StructureTemplatePool::getName),
               ResourceLocation.CODEC.fieldOf("fallback").forGetter(StructureTemplatePool::getFallback),
               Codec.mapPair(StructurePoolElement.CODEC.fieldOf("element"), Codec.intRange(1, 150).fieldOf("weight"))
                  .codec()
                  .listOf()
                  .fieldOf("elements")
                  .forGetter(var0x -> var0x.rawTemplates)
            )
            .apply(var0, StructureTemplatePool::new)
   );
   public static final Codec<Supplier<StructureTemplatePool>> CODEC = RegistryFileCodec.create(Registry.TEMPLATE_POOL_REGISTRY, DIRECT_CODEC);
   private final ResourceLocation name;
   private final List<Pair<StructurePoolElement, Integer>> rawTemplates;
   private final List<StructurePoolElement> templates;
   private final ResourceLocation fallback;
   private int maxSize = Integer.MIN_VALUE;

   public StructureTemplatePool(ResourceLocation var1, ResourceLocation var2, List<Pair<StructurePoolElement, Integer>> var3) {
      this.name = â˜ƒ;
      this.rawTemplates = â˜ƒ;
      this.templates = Lists.<StructurePoolElement>newArrayList();

      for(Pair<StructurePoolElement, Integer> â˜ƒ : â˜ƒ) {
         StructurePoolElement â˜ƒx = â˜ƒ.getFirst();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getSecond(); ++â˜ƒxx) {
            this.templates.add(â˜ƒx);
         }
      }

      this.fallback = â˜ƒ;
   }

   public StructureTemplatePool(
      ResourceLocation var1,
      ResourceLocation var2,
      List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> var3,
      StructureTemplatePool.Projection var4
   ) {
      this.name = â˜ƒ;
      this.rawTemplates = Lists.<Pair<StructurePoolElement, Integer>>newArrayList();
      this.templates = Lists.<StructurePoolElement>newArrayList();

      for(Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer> â˜ƒ : â˜ƒ) {
         StructurePoolElement â˜ƒx = (StructurePoolElement)((Function)â˜ƒ.getFirst()).apply(â˜ƒ);
         this.rawTemplates.add(Pair.of(â˜ƒx, (Integer)â˜ƒ.getSecond()));

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getSecond(); ++â˜ƒxx) {
            this.templates.add(â˜ƒx);
         }
      }

      this.fallback = â˜ƒ;
   }

   public int getMaxSize(StructureManager var1) {
      if (this.maxSize == Integer.MIN_VALUE) {
         this.maxSize = this.templates
            .stream()
            .filter(var0 -> var0 != EmptyPoolElement.INSTANCE)
            .mapToInt(var1x -> var1x.getBoundingBox(â˜ƒ, BlockPos.ZERO, Rotation.NONE).getYSpan())
            .max()
            .orElse(0);
      }

      return this.maxSize;
   }

   public ResourceLocation getFallback() {
      return this.fallback;
   }

   public StructurePoolElement getRandomTemplate(Random var1) {
      return (StructurePoolElement)this.templates.get(â˜ƒ.nextInt(this.templates.size()));
   }

   public List<StructurePoolElement> getShuffledTemplates(Random var1) {
      return ImmutableList.copyOf(ObjectArrays.shuffle((StructurePoolElement[])this.templates.toArray(new StructurePoolElement[0]), â˜ƒ));
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public int size() {
      return this.templates.size();
   }

   public static enum Projection implements StringRepresentable {
      TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))),
      RIGID("rigid", ImmutableList.of());

      public static final Codec<StructureTemplatePool.Projection> CODEC = StringRepresentable.fromEnum(
         StructureTemplatePool.Projection::values, StructureTemplatePool.Projection::byName
      );
      private static final Map<String, StructureTemplatePool.Projection> BY_NAME = (Map<String, StructureTemplatePool.Projection>)Arrays.stream(values())
         .collect(Collectors.toMap(StructureTemplatePool.Projection::getName, var0 -> var0));
      private final String name;
      private final ImmutableList<StructureProcessor> processors;

      private Projection(String var3, ImmutableList<StructureProcessor> var4) {
         this.name = â˜ƒ;
         this.processors = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static StructureTemplatePool.Projection byName(String var0) {
         return (StructureTemplatePool.Projection)BY_NAME.get(â˜ƒ);
      }

      public ImmutableList<StructureProcessor> getProcessors() {
         return this.processors;
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }
   }
}
