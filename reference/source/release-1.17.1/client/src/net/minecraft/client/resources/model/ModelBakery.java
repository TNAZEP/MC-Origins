package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.texture.AtlasSet;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   public static final Material FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/fire_0"));
   public static final Material FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/fire_1"));
   public static final Material LAVA_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/lava_flow"));
   public static final Material WATER_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/water_flow"));
   public static final Material WATER_OVERLAY = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/water_overlay"));
   public static final Material BANNER_BASE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/banner_base"));
   public static final Material SHIELD_BASE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/shield_base"));
   public static final Material NO_PATTERN_SHIELD = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/shield_base_nopattern"));
   public static final int DESTROY_STAGE_COUNT = 10;
   public static final List<ResourceLocation> DESTROY_STAGES = (List<ResourceLocation>)IntStream.range(0, 10)
      .mapToObj(var0 -> new ResourceLocation("block/destroy_stage_" + var0))
      .collect(Collectors.toList());
   public static final List<ResourceLocation> BREAKING_LOCATIONS = (List<ResourceLocation>)DESTROY_STAGES.stream()
      .map(var0 -> new ResourceLocation("textures/" + var0.getPath() + ".png"))
      .collect(Collectors.toList());
   public static final List<RenderType> DESTROY_TYPES = (List<RenderType>)BREAKING_LOCATIONS.stream().map(RenderType::crumbling).collect(Collectors.toList());
   private static final Set<Material> UNREFERENCED_TEXTURES = Util.make(Sets.<Material>newHashSet(), var0 -> {
      var0.add(WATER_FLOW);
      var0.add(LAVA_FLOW);
      var0.add(WATER_OVERLAY);
      var0.add(FIRE_0);
      var0.add(FIRE_1);
      var0.add(BellRenderer.BELL_RESOURCE_LOCATION);
      var0.add(ConduitRenderer.SHELL_TEXTURE);
      var0.add(ConduitRenderer.ACTIVE_SHELL_TEXTURE);
      var0.add(ConduitRenderer.WIND_TEXTURE);
      var0.add(ConduitRenderer.VERTICAL_WIND_TEXTURE);
      var0.add(ConduitRenderer.OPEN_EYE_TEXTURE);
      var0.add(ConduitRenderer.CLOSED_EYE_TEXTURE);
      var0.add(EnchantTableRenderer.BOOK_LOCATION);
      var0.add(BANNER_BASE);
      var0.add(SHIELD_BASE);
      var0.add(NO_PATTERN_SHIELD);

      for(ResourceLocation â˜ƒ : DESTROY_STAGES) {
         var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, â˜ƒ));
      }

      var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET));
      var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE));
      var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS));
      var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS));
      var0.add(new Material(TextureAtlas.LOCATION_BLOCKS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD));
      Sheets.getAllMaterials(var0::add);
   });
   static final int SINGLETON_MODEL_GROUP = -1;
   private static final int INVISIBLE_MODEL_GROUP = 0;
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String BUILTIN_SLASH = "builtin/";
   private static final String BUILTIN_SLASH_GENERATED = "builtin/generated";
   private static final String BUILTIN_BLOCK_ENTITY = "builtin/entity";
   private static final String MISSING_MODEL_NAME = "missing";
   public static final ModelResourceLocation MISSING_MODEL_LOCATION = new ModelResourceLocation("builtin/missing", "missing");
   private static final String MISSING_MODEL_LOCATION_STRING = MISSING_MODEL_LOCATION.toString();
   @VisibleForTesting
   public static final String MISSING_MODEL_MESH = ("{    'textures': {       'particle': '"
         + MissingTextureAtlasSprite.getLocation().getPath()
         + "',       'missingno': '"
         + MissingTextureAtlasSprite.getLocation().getPath()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> BUILTIN_MODELS = Maps.newHashMap(ImmutableMap.of("missing", MISSING_MODEL_MESH));
   private static final Splitter COMMA_SPLITTER = Splitter.on(',');
   private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);
   public static final BlockModel GENERATION_MARKER = Util.make(BlockModel.fromString("{\"gui_light\": \"front\"}"), var0 -> var0.name = "generation marker");
   public static final BlockModel BLOCK_ENTITY_MARKER = Util.make(BlockModel.fromString("{\"gui_light\": \"side\"}"), var0 -> var0.name = "block entity marker");
   private static final StateDefinition<Block, BlockState> ITEM_FRAME_FAKE_DEFINITION = new StateDefinition.Builder<Block, BlockState>(Blocks.AIR)
      .add(BooleanProperty.create("map"))
      .create(Block::defaultBlockState, BlockState::new);
   private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
   private static final Map<ResourceLocation, StateDefinition<Block, BlockState>> STATIC_DEFINITIONS = ImmutableMap.of(
      new ResourceLocation("item_frame"), ITEM_FRAME_FAKE_DEFINITION, new ResourceLocation("glow_item_frame"), ITEM_FRAME_FAKE_DEFINITION
   );
   private final ResourceManager resourceManager;
   @Nullable
   private AtlasSet atlasSet;
   private final BlockColors blockColors;
   private final Set<ResourceLocation> loadingStack = Sets.<ResourceLocation>newHashSet();
   private final BlockModelDefinition.Context context = new BlockModelDefinition.Context();
   private final Map<ResourceLocation, UnbakedModel> unbakedCache = Maps.<ResourceLocation, UnbakedModel>newHashMap();
   private final Map<Triple<ResourceLocation, Transformation, Boolean>, BakedModel> bakedCache = Maps.<Triple<ResourceLocation, Transformation, Boolean>, BakedModel>newHashMap(
      
   );
   private final Map<ResourceLocation, UnbakedModel> topLevelModels = Maps.<ResourceLocation, UnbakedModel>newHashMap();
   private final Map<ResourceLocation, BakedModel> bakedTopLevelModels = Maps.<ResourceLocation, BakedModel>newHashMap();
   private final Map<ResourceLocation, Pair<TextureAtlas, TextureAtlas.Preparations>> atlasPreparations;
   private int nextModelGroup = 1;
   private final Object2IntMap<BlockState> modelGroups = Util.make(new Object2IntOpenHashMap<>(), var0 -> var0.defaultReturnValue(-1));

   public ModelBakery(ResourceManager var1, BlockColors var2, ProfilerFiller var3, int var4) {
      this.resourceManager = â˜ƒ;
      this.blockColors = â˜ƒ;
      â˜ƒ.push("missing_model");

      try {
         this.unbakedCache.put(MISSING_MODEL_LOCATION, this.loadBlockModel(MISSING_MODEL_LOCATION));
         this.loadTopLevel(MISSING_MODEL_LOCATION);
      } catch (IOException var12) {
         LOGGER.error("Error loading missing model, should never happen :(", var12);
         throw new RuntimeException(var12);
      }

      â˜ƒ.popPush("static_definitions");
      STATIC_DEFINITIONS.forEach(
         (var1x, var2x) -> var2x.getPossibleStates().forEach(var2xx -> this.loadTopLevel(BlockModelShaper.stateToModelLocation(var1x, var2xx)))
      );
      â˜ƒ.popPush("blocks");

      for(Block â˜ƒ : Registry.BLOCK) {
         â˜ƒ.getStateDefinition().getPossibleStates().forEach(var1x -> this.loadTopLevel(BlockModelShaper.stateToModelLocation(var1x)));
      }

      â˜ƒ.popPush("items");

      for(ResourceLocation â˜ƒ : Registry.ITEM.keySet()) {
         this.loadTopLevel(new ModelResourceLocation(â˜ƒ, "inventory"));
      }

      â˜ƒ.popPush("special");
      this.loadTopLevel(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      this.loadTopLevel(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
      â˜ƒ.popPush("textures");
      Set<Pair<String, String>> â˜ƒ = Sets.<Pair<String, String>>newLinkedHashSet();
      Set<Material> â˜ƒx = (Set)this.topLevelModels
         .values()
         .stream()
         .flatMap(var2x -> var2x.getMaterials(this::getModel, â˜ƒ).stream())
         .collect(Collectors.toSet());
      â˜ƒx.addAll(UNREFERENCED_TEXTURES);
      â˜ƒ.stream()
         .filter(var0 -> !((String)var0.getSecond()).equals(MISSING_MODEL_LOCATION_STRING))
         .forEach(var0 -> LOGGER.warn("Unable to resolve texture reference: {} in {}", var0.getFirst(), var0.getSecond()));
      Map<ResourceLocation, List<Material>> â˜ƒxx = (Map)â˜ƒx.stream().collect(Collectors.groupingBy(Material::atlasLocation));
      â˜ƒ.popPush("stitching");
      this.atlasPreparations = Maps.<ResourceLocation, Pair<TextureAtlas, TextureAtlas.Preparations>>newHashMap();

      for(Entry<ResourceLocation, List<Material>> â˜ƒxxx : â˜ƒxx.entrySet()) {
         TextureAtlas â˜ƒxxxx = new TextureAtlas((ResourceLocation)â˜ƒxxx.getKey());
         TextureAtlas.Preparations â˜ƒxxxxx = â˜ƒxxxx.prepareToStitch(this.resourceManager, ((List)â˜ƒxxx.getValue()).stream().map(Material::texture), â˜ƒ, â˜ƒ);
         this.atlasPreparations.put((ResourceLocation)â˜ƒxxx.getKey(), Pair.of(â˜ƒxxxx, â˜ƒxxxxx));
      }

      â˜ƒ.pop();
   }

   public AtlasSet uploadTextures(TextureManager var1, ProfilerFiller var2) {
      â˜ƒ.push("atlas");

      for(Pair<TextureAtlas, TextureAtlas.Preparations> â˜ƒ : this.atlasPreparations.values()) {
         TextureAtlas â˜ƒx = â˜ƒ.getFirst();
         TextureAtlas.Preparations â˜ƒxx = â˜ƒ.getSecond();
         â˜ƒx.reload(â˜ƒxx);
         â˜ƒ.register(â˜ƒx.location(), â˜ƒx);
         â˜ƒ.bindForSetup(â˜ƒx.location());
         â˜ƒx.updateFilter(â˜ƒxx);
      }

      this.atlasSet = new AtlasSet((Collection<TextureAtlas>)this.atlasPreparations.values().stream().map(Pair::getFirst).collect(Collectors.toList()));
      â˜ƒ.popPush("baking");
      this.topLevelModels.keySet().forEach(var1x -> {
         BakedModel â˜ƒ = null;

         try {
            â˜ƒ = this.bake(var1x, BlockModelRotation.X0_Y0);
         } catch (Exception var4xx) {
            LOGGER.warn("Unable to bake model: '{}': {}", var1x, var4xx);
         }

         if (â˜ƒ != null) {
            this.bakedTopLevelModels.put(var1x, â˜ƒ);
         }
      });
      â˜ƒ.pop();
      return this.atlasSet;
   }

   private static Predicate<BlockState> predicate(StateDefinition<Block, BlockState> var0, String var1) {
      Map<Property<?>, Comparable<?>> â˜ƒ = Maps.newHashMap();

      for(String â˜ƒx : COMMA_SPLITTER.split(â˜ƒ)) {
         Iterator<String> â˜ƒxx = EQUAL_SPLITTER.split(â˜ƒx).iterator();
         if (â˜ƒxx.hasNext()) {
            String â˜ƒxxx = (String)â˜ƒxx.next();
            Property<?> â˜ƒxxxx = â˜ƒ.getProperty(â˜ƒxxx);
            if (â˜ƒxxxx != null && â˜ƒxx.hasNext()) {
               String â˜ƒxxxxx = (String)â˜ƒxx.next();
               Comparable<?> â˜ƒxxxxxx = getValueHelper(â˜ƒxxxx, â˜ƒxxxxx);
               if (â˜ƒxxxxxx == null) {
                  throw new RuntimeException("Unknown value: '" + â˜ƒxxxxx + "' for blockstate property: '" + â˜ƒxxx + "' " + â˜ƒxxxx.getPossibleValues());
               }

               â˜ƒ.put(â˜ƒxxxx, â˜ƒxxxxxx);
            } else if (!â˜ƒxxx.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + â˜ƒxxx + "'");
            }
         }
      }

      Block â˜ƒx = â˜ƒ.getOwner();
      return var2x -> {
         if (var2x != null && var2x.is(â˜ƒ)) {
            for(Entry<Property<?>, Comparable<?>> â˜ƒ : â˜ƒ.entrySet()) {
               if (!Objects.equals(var2x.getValue((Property)â˜ƒ.getKey()), â˜ƒ.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T getValueHelper(Property<T> var0, String var1) {
      return (T)â˜ƒ.getValue(â˜ƒ).orElse(null);
   }

   public UnbakedModel getModel(ResourceLocation var1) {
      if (this.unbakedCache.containsKey(â˜ƒ)) {
         return (UnbakedModel)this.unbakedCache.get(â˜ƒ);
      } else if (this.loadingStack.contains(â˜ƒ)) {
         throw new IllegalStateException("Circular reference while loading " + â˜ƒ);
      } else {
         this.loadingStack.add(â˜ƒ);
         UnbakedModel â˜ƒ = (UnbakedModel)this.unbakedCache.get(MISSING_MODEL_LOCATION);

         while(!this.loadingStack.isEmpty()) {
            ResourceLocation â˜ƒx = (ResourceLocation)this.loadingStack.iterator().next();

            try {
               if (!this.unbakedCache.containsKey(â˜ƒx)) {
                  this.loadModel(â˜ƒx);
               }
            } catch (ModelBakery.BlockStateDefinitionException var9) {
               LOGGER.warn(var9.getMessage());
               this.unbakedCache.put(â˜ƒx, â˜ƒ);
            } catch (Exception var10) {
               LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", â˜ƒx, â˜ƒ, var10);
               this.unbakedCache.put(â˜ƒx, â˜ƒ);
            } finally {
               this.loadingStack.remove(â˜ƒx);
            }
         }

         return (UnbakedModel)this.unbakedCache.getOrDefault(â˜ƒ, â˜ƒ);
      }
   }

   private void loadModel(ResourceLocation var1) throws Exception {
      if (!(â˜ƒ instanceof ModelResourceLocation)) {
         this.cacheAndQueueDependencies(â˜ƒ, this.loadBlockModel(â˜ƒ));
      } else {
         ModelResourceLocation â˜ƒ = (ModelResourceLocation)â˜ƒ;
         if (Objects.equals(â˜ƒ.getVariant(), "inventory")) {
            ResourceLocation â˜ƒx = new ResourceLocation(â˜ƒ.getNamespace(), "item/" + â˜ƒ.getPath());
            BlockModel â˜ƒxx = this.loadBlockModel(â˜ƒx);
            this.cacheAndQueueDependencies(â˜ƒ, â˜ƒxx);
            this.unbakedCache.put(â˜ƒx, â˜ƒxx);
         } else {
            ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.getNamespace(), â˜ƒ.getPath());
            StateDefinition<Block, BlockState> â˜ƒx = (StateDefinition)Optional.ofNullable((StateDefinition)STATIC_DEFINITIONS.get(â˜ƒ))
               .orElseGet(() -> Registry.BLOCK.get(â˜ƒ).getStateDefinition());
            this.context.setDefinition(â˜ƒx);
            List<Property<?>> â˜ƒxx = ImmutableList.copyOf(this.blockColors.getColoringProperties(â˜ƒx.getOwner()));
            ImmutableList<BlockState> â˜ƒxxx = â˜ƒx.getPossibleStates();
            Map<ModelResourceLocation, BlockState> â˜ƒxxxx = Maps.<ModelResourceLocation, BlockState>newHashMap();
            â˜ƒxxx.forEach(var2x -> â˜ƒ.put(BlockModelShaper.stateToModelLocation(â˜ƒ, var2x), var2x));
            Map<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>> â˜ƒxxxxx = Maps.<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>>newHashMap(
               
            );
            ResourceLocation â˜ƒxxxxxx = new ResourceLocation(â˜ƒ.getNamespace(), "blockstates/" + â˜ƒ.getPath() + ".json");
            UnbakedModel â˜ƒxxxxxxx = (UnbakedModel)this.unbakedCache.get(MISSING_MODEL_LOCATION);
            ModelBakery.ModelGroupKey â˜ƒxxxxxxxx = new ModelBakery.ModelGroupKey(ImmutableList.of(â˜ƒxxxxxxx), ImmutableList.of());
            Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> â˜ƒxxxxxxxxx = Pair.of(â˜ƒxxxxxxx, (Supplier)() -> â˜ƒ);

            try {
               List<Pair<String, BlockModelDefinition>> â˜ƒ;
               try {
                  â˜ƒ = (List)this.resourceManager
                     .getResources(â˜ƒxxxxxx)
                     .stream()
                     .map(
                        var1x -> {
                           try {
                              InputStream â˜ƒ = var1x.getInputStream();
      
                              Pair var3x;
                              try {
                                 var3x = Pair.of(
                                    var1x.getSourceName(), BlockModelDefinition.fromStream(this.context, new InputStreamReader(â˜ƒ, StandardCharsets.UTF_8))
                                 );
                              } catch (Throwable var6xx) {
                                 if (â˜ƒ != null) {
                                    try {
                                       â˜ƒ.close();
                                    } catch (Throwable var5xx) {
                                       var6xx.addSuppressed(var5xx);
                                    }
                                 }
      
                                 throw var6xx;
                              }
      
                              if (â˜ƒ != null) {
                                 â˜ƒ.close();
                              }
      
                              return var3x;
                           } catch (Exception var7xx) {
                              throw new ModelBakery.BlockStateDefinitionException(
                                 String.format(
                                    "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s",
                                    var1x.getLocation(),
                                    var1x.getSourceName(),
                                    var7xx.getMessage()
                                 )
                              );
                           }
                        }
                     )
                     .collect(Collectors.toList());
               } catch (IOException var25) {
                  LOGGER.warn("Exception loading blockstate definition: {}: {}", â˜ƒxxxxxx, var25);
                  return;
               }

               for(Pair<String, BlockModelDefinition> â˜ƒxxxxxxxxxx : â˜ƒ) {
                  BlockModelDefinition â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getSecond();
                  Map<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>> â˜ƒxxxxxxxxxxxxx = Maps.<BlockState, Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>>>newIdentityHashMap(
                     
                  );
                  MultiPart â˜ƒxxxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxxxx.isMultiPart()) {
                     â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getMultiPart();
                     â˜ƒxxx.forEach(var3x -> â˜ƒ.put(var3x, Pair.of(â˜ƒ, (Supplier)() -> ModelBakery.ModelGroupKey.create(var3x, â˜ƒ, â˜ƒ))));
                  } else {
                     â˜ƒxxxxxxxxxxx = null;
                  }

                  â˜ƒxxxxxxxxxxxx.getVariants()
                     .forEach(
                        (var9x, var10x) -> {
                           try {
                              â˜ƒ.stream()
                                 .filter(predicate(â˜ƒ, var9x))
                                 .forEach(
                                    var6x -> {
                                       Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> â˜ƒ = (Pair)â˜ƒ.put(
                                          var6x, Pair.of(var10x, (Supplier)() -> ModelBakery.ModelGroupKey.create(var6x, var10x, â˜ƒ))
                                       );
                                       if (â˜ƒ != null && â˜ƒ.getFirst() != â˜ƒ) {
                                          â˜ƒ.put(var6x, â˜ƒ);
                                          throw new RuntimeException(
                                             "Overlapping definition with: "
                                                + (String)((Entry)â˜ƒ.getVariants()
                                                      .entrySet()
                                                      .stream()
                                                      .filter(var1x -> var1x.getValue() == â˜ƒ.getFirst())
                                                      .findFirst()
                                                      .get())
                                                   .getKey()
                                          );
                                       }
                                    }
                                 );
                           } catch (Exception var12xx) {
                              LOGGER.warn(
                                 "Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}",
                                 â˜ƒ,
                                 â˜ƒ.getFirst(),
                                 var9x,
                                 var12xx.getMessage()
                              );
                           }
                        }
                     );
                  â˜ƒxxxxx.putAll(â˜ƒxxxxxxxxxxxxx);
               }
            } catch (ModelBakery.BlockStateDefinitionException var26) {
               throw var26;
            } catch (Exception var27) {
               throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", â˜ƒxxxxxx, var27));
            } finally {
               Map<ModelBakery.ModelGroupKey, Set<BlockState>> â˜ƒxxxxxxxxxx = Maps.newHashMap();
               â˜ƒxxxx.forEach((var5x, var6x) -> {
                  Pair<UnbakedModel, Supplier<ModelBakery.ModelGroupKey>> â˜ƒ = (Pair)â˜ƒ.get(var6x);
                  if (â˜ƒ == null) {
                     LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", â˜ƒ, var5x);
                     â˜ƒ = â˜ƒ;
                  }

                  this.cacheAndQueueDependencies(var5x, â˜ƒ.getFirst());

                  try {
                     ModelBakery.ModelGroupKey â˜ƒ = (ModelBakery.ModelGroupKey)((Supplier)â˜ƒ.getSecond()).get();
                     ((Set)â˜ƒ.computeIfAbsent(â˜ƒ, var0 -> Sets.newIdentityHashSet())).add(var6x);
                  } catch (Exception var9xx) {
                     LOGGER.warn("Exception evaluating model definition: '{}'", var5x, var9xx);
                  }
               });
               â˜ƒxxxxxxxxxx.forEach((var1x, var2x) -> {
                  Iterator<BlockState> â˜ƒ = var2x.iterator();

                  while(â˜ƒ.hasNext()) {
                     BlockState â˜ƒx = (BlockState)â˜ƒ.next();
                     if (â˜ƒx.getRenderShape() != RenderShape.MODEL) {
                        â˜ƒ.remove();
                        this.modelGroups.put(â˜ƒx, 0);
                     }
                  }

                  if (var2x.size() > 1) {
                     this.registerModelGroup(var2x);
                  }
               });
            }
         }
      }
   }

   private void cacheAndQueueDependencies(ResourceLocation var1, UnbakedModel var2) {
      this.unbakedCache.put(â˜ƒ, â˜ƒ);
      this.loadingStack.addAll(â˜ƒ.getDependencies());
   }

   private void loadTopLevel(ModelResourceLocation var1) {
      UnbakedModel â˜ƒ = this.getModel(â˜ƒ);
      this.unbakedCache.put(â˜ƒ, â˜ƒ);
      this.topLevelModels.put(â˜ƒ, â˜ƒ);
   }

   private void registerModelGroup(Iterable<BlockState> var1) {
      int â˜ƒ = this.nextModelGroup++;
      â˜ƒ.forEach(var2x -> this.modelGroups.put(var2x, â˜ƒ));
   }

   @Nullable
   public BakedModel bake(ResourceLocation var1, ModelState var2) {
      Triple<ResourceLocation, Transformation, Boolean> â˜ƒ = Triple.of(â˜ƒ, â˜ƒ.getRotation(), â˜ƒ.isUvLocked());
      if (this.bakedCache.containsKey(â˜ƒ)) {
         return (BakedModel)this.bakedCache.get(â˜ƒ);
      } else if (this.atlasSet == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         UnbakedModel â˜ƒx = this.getModel(â˜ƒ);
         if (â˜ƒx instanceof BlockModel â˜ƒ && â˜ƒ.getRootModel() == GENERATION_MARKER) {
            return ITEM_MODEL_GENERATOR.generateBlockModel(this.atlasSet::getSprite, â˜ƒ).bake(this, â˜ƒ, this.atlasSet::getSprite, â˜ƒ, â˜ƒ, false);
         }

         BakedModel â˜ƒ = â˜ƒx.bake(this, this.atlasSet::getSprite, â˜ƒ, â˜ƒ);
         this.bakedCache.put(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   private BlockModel loadBlockModel(ResourceLocation var1) throws IOException {
      Reader â˜ƒ = null;
      Resource â˜ƒx = null;

      BlockModel â˜ƒ;
      try {
         String â˜ƒxx = â˜ƒ.getPath();
         if ("builtin/generated".equals(â˜ƒxx)) {
            return GENERATION_MARKER;
         }

         if (!"builtin/entity".equals(â˜ƒxx)) {
            if (â˜ƒxx.startsWith("builtin/")) {
               String â˜ƒxx = â˜ƒxx.substring("builtin/".length());
               String â˜ƒxxx = (String)BUILTIN_MODELS.get(â˜ƒxx);
               if (â˜ƒxxx == null) {
                  throw new FileNotFoundException(â˜ƒ.toString());
               }

               â˜ƒ = new StringReader(â˜ƒxxx);
            } else {
               â˜ƒx = this.resourceManager.getResource(new ResourceLocation(â˜ƒ.getNamespace(), "models/" + â˜ƒ.getPath() + ".json"));
               â˜ƒ = new InputStreamReader(â˜ƒx.getInputStream(), StandardCharsets.UTF_8);
            }

            â˜ƒ = BlockModel.fromStream(â˜ƒ);
            â˜ƒ.name = â˜ƒ.toString();
            return â˜ƒ;
         }

         â˜ƒ = BLOCK_ENTITY_MARKER;
      } finally {
         IOUtils.closeQuietly(â˜ƒ);
         IOUtils.closeQuietly(â˜ƒx);
      }

      return â˜ƒ;
   }

   public Map<ResourceLocation, BakedModel> getBakedTopLevelModels() {
      return this.bakedTopLevelModels;
   }

   public Object2IntMap<BlockState> getModelGroups() {
      return this.modelGroups;
   }

   static class BlockStateDefinitionException extends RuntimeException {
      public BlockStateDefinitionException(String var1) {
         super(â˜ƒ);
      }
   }

   static class ModelGroupKey {
      private final List<UnbakedModel> models;
      private final List<Object> coloringValues;

      public ModelGroupKey(List<UnbakedModel> var1, List<Object> var2) {
         this.models = â˜ƒ;
         this.coloringValues = â˜ƒ;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof ModelBakery.ModelGroupKey)) {
            return false;
         } else {
            ModelBakery.ModelGroupKey â˜ƒ = (ModelBakery.ModelGroupKey)â˜ƒ;
            return Objects.equals(this.models, â˜ƒ.models) && Objects.equals(this.coloringValues, â˜ƒ.coloringValues);
         }
      }

      public int hashCode() {
         return 31 * this.models.hashCode() + this.coloringValues.hashCode();
      }

      public static ModelBakery.ModelGroupKey create(BlockState var0, MultiPart var1, Collection<Property<?>> var2) {
         StateDefinition<Block, BlockState> â˜ƒ = â˜ƒ.getBlock().getStateDefinition();
         List<UnbakedModel> â˜ƒx = (List)â˜ƒ.getSelectors()
            .stream()
            .filter(var2x -> var2x.getPredicate(â˜ƒ).test(â˜ƒ))
            .map(Selector::getVariant)
            .collect(ImmutableList.toImmutableList());
         List<Object> â˜ƒxx = getColoringValues(â˜ƒ, â˜ƒ);
         return new ModelBakery.ModelGroupKey(â˜ƒx, â˜ƒxx);
      }

      public static ModelBakery.ModelGroupKey create(BlockState var0, UnbakedModel var1, Collection<Property<?>> var2) {
         List<Object> â˜ƒ = getColoringValues(â˜ƒ, â˜ƒ);
         return new ModelBakery.ModelGroupKey(ImmutableList.of(â˜ƒ), â˜ƒ);
      }

      private static List<Object> getColoringValues(BlockState var0, Collection<Property<?>> var1) {
         return (List<Object>)â˜ƒ.stream().map(â˜ƒ::getValue).collect(ImmutableList.toImmutableList());
      }
   }
}
