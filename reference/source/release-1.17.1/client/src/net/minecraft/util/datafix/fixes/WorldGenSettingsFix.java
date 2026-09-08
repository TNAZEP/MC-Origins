package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorldGenSettingsFix extends DataFix {
   private static final String VILLAGE = "minecraft:village";
   private static final String DESERT_PYRAMID = "minecraft:desert_pyramid";
   private static final String IGLOO = "minecraft:igloo";
   private static final String JUNGLE_TEMPLE = "minecraft:jungle_pyramid";
   private static final String SWAMP_HUT = "minecraft:swamp_hut";
   private static final String PILLAGER_OUTPOST = "minecraft:pillager_outpost";
   private static final String END_CITY = "minecraft:endcity";
   private static final String WOODLAND_MANSION = "minecraft:mansion";
   private static final String OCEAN_MONUMENT = "minecraft:monument";
   private static final ImmutableMap<String, WorldGenSettingsFix.StructureFeatureConfiguration> DEFAULTS = ImmutableMap.builder()
      .put("minecraft:village", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 10387312))
      .put("minecraft:desert_pyramid", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 14357617))
      .put("minecraft:igloo", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 14357618))
      .put("minecraft:jungle_pyramid", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 14357619))
      .put("minecraft:swamp_hut", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 14357620))
      .put("minecraft:pillager_outpost", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 8, 165745296))
      .put("minecraft:monument", new WorldGenSettingsFix.StructureFeatureConfiguration(32, 5, 10387313))
      .put("minecraft:endcity", new WorldGenSettingsFix.StructureFeatureConfiguration(20, 11, 10387313))
      .put("minecraft:mansion", new WorldGenSettingsFix.StructureFeatureConfiguration(80, 20, 10387319))
      .build();

   public WorldGenSettingsFix(Schema var1) {
      super(â˜ƒ, true);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "WorldGenSettings building",
         this.getInputSchema().getType(References.WORLD_GEN_SETTINGS),
         var0 -> var0.update(DSL.remainderFinder(), WorldGenSettingsFix::fix)
      );
   }

   private static <T> Dynamic<T> noise(long var0, DynamicLike<T> var2, Dynamic<T> var3, Dynamic<T> var4) {
      return â˜ƒ.createMap(
         ImmutableMap.of(
            â˜ƒ.createString("type"),
            â˜ƒ.createString("minecraft:noise"),
            â˜ƒ.createString("biome_source"),
            â˜ƒ,
            â˜ƒ.createString("seed"),
            â˜ƒ.createLong(â˜ƒ),
            â˜ƒ.createString("settings"),
            â˜ƒ
         )
      );
   }

   private static <T> Dynamic<T> vanillaBiomeSource(Dynamic<T> var0, long var1, boolean var3, boolean var4) {
      Builder<Dynamic<T>, Dynamic<T>> â˜ƒ = ImmutableMap.<Dynamic<T>, Dynamic<T>>builder()
         .put(â˜ƒ.createString("type"), â˜ƒ.createString("minecraft:vanilla_layered"))
         .put(â˜ƒ.createString("seed"), â˜ƒ.createLong(â˜ƒ))
         .put(â˜ƒ.createString("large_biomes"), â˜ƒ.createBoolean(â˜ƒ));
      if (â˜ƒ) {
         â˜ƒ.put(â˜ƒ.createString("legacy_biome_init_layer"), â˜ƒ.createBoolean(â˜ƒ));
      }

      return â˜ƒ.createMap(â˜ƒ.build());
   }

   private static <T> Dynamic<T> fix(Dynamic<T> var0) {
      DynamicOps<T> â˜ƒx = â˜ƒ.getOps();
      long â˜ƒxx = â˜ƒ.get("RandomSeed").asLong(0L);
      Optional<String> â˜ƒxxx = â˜ƒ.get("generatorName").asString().map(var0x -> var0x.toLowerCase(Locale.ROOT)).result();
      Optional<String> â˜ƒxxxx = (Optional)â˜ƒ.get("legacy_custom_options")
         .asString()
         .result()
         .map(Optional::of)
         .orElseGet(() -> â˜ƒ.equals(Optional.of("customized")) ? â˜ƒ.get("generatorOptions").asString().result() : Optional.empty());
      boolean â˜ƒxxxxx = false;
      Dynamic<T> â˜ƒ;
      if (â˜ƒxxx.equals(Optional.of("customized"))) {
         â˜ƒ = defaultOverworld(â˜ƒ, â˜ƒxx);
      } else if (!â˜ƒxxx.isPresent()) {
         â˜ƒ = defaultOverworld(â˜ƒ, â˜ƒxx);
      } else {
         String var8 = (String)â˜ƒxxx.get();
         switch(var8) {
            case "flat": {
               OptionalDynamic<T> â˜ƒ = â˜ƒ.get("generatorOptions");
               Map<Dynamic<T>, Dynamic<T>> â˜ƒx = fixFlatStructures(â˜ƒx, â˜ƒ);
               â˜ƒ = â˜ƒ.createMap(
                  ImmutableMap.of(
                     â˜ƒ.createString("type"),
                     â˜ƒ.createString("minecraft:flat"),
                     â˜ƒ.createString("settings"),
                     â˜ƒ.createMap(
                        ImmutableMap.of(
                           â˜ƒ.createString("structures"),
                           â˜ƒ.createMap(â˜ƒx),
                           â˜ƒ.createString("layers"),
                           (Dynamic<?>)â˜ƒ.get("layers")
                              .result()
                              .orElseGet(
                                 () -> â˜ƒ.createList(
                                       Stream.of(
                                          â˜ƒ.createMap(
                                             ImmutableMap.of(
                                                â˜ƒ.createString("height"), â˜ƒ.createInt(1), â˜ƒ.createString("block"), â˜ƒ.createString("minecraft:bedrock")
                                             )
                                          ),
                                          â˜ƒ.createMap(
                                             ImmutableMap.of(
                                                â˜ƒ.createString("height"), â˜ƒ.createInt(2), â˜ƒ.createString("block"), â˜ƒ.createString("minecraft:dirt")
                                             )
                                          ),
                                          â˜ƒ.createMap(
                                             ImmutableMap.of(
                                                â˜ƒ.createString("height"),
                                                â˜ƒ.createInt(1),
                                                â˜ƒ.createString("block"),
                                                â˜ƒ.createString("minecraft:grass_block")
                                             )
                                          )
                                       )
                                    )
                              ),
                           â˜ƒ.createString("biome"),
                           â˜ƒ.createString(â˜ƒ.get("biome").asString("minecraft:plains"))
                        )
                     )
                  )
               );
               break;
            }
            case "debug_all_block_states":
               â˜ƒ = â˜ƒ.createMap(ImmutableMap.of(â˜ƒ.createString("type"), â˜ƒ.createString("minecraft:debug")));
               break;
            case "buffet": {
               OptionalDynamic<T> â˜ƒ = â˜ƒ.get("generatorOptions");
               OptionalDynamic<?> â˜ƒ = â˜ƒ.get("chunk_generator");
               Optional<String> â˜ƒ = â˜ƒ.get("type").asString().result();
               Dynamic<T> â˜ƒ;
               if (Objects.equals(â˜ƒ, Optional.of("minecraft:caves"))) {
                  â˜ƒ = â˜ƒ.createString("minecraft:caves");
                  â˜ƒxxxxx = true;
               } else if (Objects.equals(â˜ƒ, Optional.of("minecraft:floating_islands"))) {
                  â˜ƒ = â˜ƒ.createString("minecraft:floating_islands");
               } else {
                  â˜ƒ = â˜ƒ.createString("minecraft:overworld");
               }

               Dynamic<T> â˜ƒ = (Dynamic)â˜ƒ.get("biome_source")
                  .result()
                  .orElseGet(() -> â˜ƒ.createMap(ImmutableMap.of(â˜ƒ.createString("type"), â˜ƒ.createString("minecraft:fixed"))));
               Dynamic<T> â˜ƒ;
               if (â˜ƒ.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                  String â˜ƒxx = (String)â˜ƒ.get("options")
                     .get("biomes")
                     .asStream()
                     .findFirst()
                     .flatMap(var0x -> var0x.asString().result())
                     .orElse("minecraft:ocean");
                  â˜ƒ = â˜ƒ.remove("options").set("biome", â˜ƒ.createString(â˜ƒxx));
               } else {
                  â˜ƒ = â˜ƒ;
               }

               â˜ƒ = noise(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ);
               break;
            }
            default: {
               boolean â˜ƒ = ((String)â˜ƒxxx.get()).equals("default");
               boolean â˜ƒ = ((String)â˜ƒxxx.get()).equals("default_1_1") || â˜ƒ && â˜ƒ.get("generatorVersion").asInt(0) == 0;
               boolean â˜ƒ = ((String)â˜ƒxxx.get()).equals("amplified");
               boolean â˜ƒ = ((String)â˜ƒxxx.get()).equals("largebiomes");
               â˜ƒ = noise(â˜ƒxx, â˜ƒ, â˜ƒ.createString(â˜ƒ ? "minecraft:amplified" : "minecraft:overworld"), vanillaBiomeSource(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ));
            }
         }
      }

      boolean â˜ƒ = â˜ƒ.get("MapFeatures").asBoolean(true);
      boolean â˜ƒx = â˜ƒ.get("BonusChest").asBoolean(false);
      Builder<T, T> â˜ƒxx = ImmutableMap.builder();
      â˜ƒxx.put(â˜ƒx.createString("seed"), â˜ƒx.createLong(â˜ƒxx));
      â˜ƒxx.put(â˜ƒx.createString("generate_features"), â˜ƒx.createBoolean(â˜ƒ));
      â˜ƒxx.put(â˜ƒx.createString("bonus_chest"), â˜ƒx.createBoolean(â˜ƒx));
      â˜ƒxx.put(â˜ƒx.createString("dimensions"), vanillaLevels(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxxxx));
      â˜ƒxxxx.ifPresent(var2x -> â˜ƒ.put(â˜ƒ.createString("legacy_custom_options"), â˜ƒ.createString(var2x)));
      return new Dynamic<>(â˜ƒx, â˜ƒx.createMap(â˜ƒxx.build()));
   }

   protected static <T> Dynamic<T> defaultOverworld(Dynamic<T> var0, long var1) {
      return noise(â˜ƒ, â˜ƒ, â˜ƒ.createString("minecraft:overworld"), vanillaBiomeSource(â˜ƒ, â˜ƒ, false, false));
   }

   protected static <T> T vanillaLevels(Dynamic<T> var0, long var1, Dynamic<T> var3, boolean var4) {
      DynamicOps<T> â˜ƒ = â˜ƒ.getOps();
      return â˜ƒ.createMap(
         ImmutableMap.of(
            â˜ƒ.createString("minecraft:overworld"),
            â˜ƒ.createMap(
               ImmutableMap.of(
                  â˜ƒ.createString("type"), â˜ƒ.createString("minecraft:overworld" + (â˜ƒ ? "_caves" : "")), â˜ƒ.createString("generator"), â˜ƒ.getValue()
               )
            ),
            â˜ƒ.createString("minecraft:the_nether"),
            â˜ƒ.createMap(
               ImmutableMap.of(
                  â˜ƒ.createString("type"),
                  â˜ƒ.createString("minecraft:the_nether"),
                  â˜ƒ.createString("generator"),
                  noise(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ.createString("minecraft:nether"),
                        â˜ƒ.createMap(
                           ImmutableMap.of(
                              â˜ƒ.createString("type"),
                              â˜ƒ.createString("minecraft:multi_noise"),
                              â˜ƒ.createString("seed"),
                              â˜ƒ.createLong(â˜ƒ),
                              â˜ƒ.createString("preset"),
                              â˜ƒ.createString("minecraft:nether")
                           )
                        )
                     )
                     .getValue()
               )
            ),
            â˜ƒ.createString("minecraft:the_end"),
            â˜ƒ.createMap(
               ImmutableMap.of(
                  â˜ƒ.createString("type"),
                  â˜ƒ.createString("minecraft:the_end"),
                  â˜ƒ.createString("generator"),
                  noise(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ.createString("minecraft:end"),
                        â˜ƒ.createMap(
                           ImmutableMap.of(â˜ƒ.createString("type"), â˜ƒ.createString("minecraft:the_end"), â˜ƒ.createString("seed"), â˜ƒ.createLong(â˜ƒ))
                        )
                     )
                     .getValue()
               )
            )
         )
      );
   }

   private static <T> Map<Dynamic<T>, Dynamic<T>> fixFlatStructures(DynamicOps<T> var0, OptionalDynamic<T> var1) {
      MutableInt â˜ƒ = new MutableInt(32);
      MutableInt â˜ƒx = new MutableInt(3);
      MutableInt â˜ƒxx = new MutableInt(128);
      MutableBoolean â˜ƒxxx = new MutableBoolean(false);
      Map<String, WorldGenSettingsFix.StructureFeatureConfiguration> â˜ƒxxxx = Maps.newHashMap();
      if (!â˜ƒ.result().isPresent()) {
         â˜ƒxxx.setTrue();
         â˜ƒxxxx.put("minecraft:village", DEFAULTS.get("minecraft:village"));
      }

      â˜ƒ.get("structures")
         .flatMap(Dynamic::getMapValues)
         .result()
         .ifPresent(
            var5x -> var5x.forEach(
                  (var5xx, var6x) -> var6x.getMapValues()
                        .result()
                        .ifPresent(
                           var6xx -> var6xx.forEach(
                                 (var6xxx, var7x) -> {
                                    String â˜ƒ = var5xx.asString("");
                                    String â˜ƒx = var6xxx.asString("");
                                    String â˜ƒxx = var7x.asString("");
                                    if ("stronghold".equals(â˜ƒ)) {
                                       â˜ƒ.setTrue();
                                       switch(â˜ƒx) {
                                          case "distance":
                                             â˜ƒ.setValue(getInt(â˜ƒxx, â˜ƒ.getValue(), 1));
                                             return;
                                          case "spread":
                                             â˜ƒ.setValue(getInt(â˜ƒxx, â˜ƒ.getValue(), 1));
                                             return;
                                          case "count":
                                             â˜ƒ.setValue(getInt(â˜ƒxx, â˜ƒ.getValue(), 1));
                                             return;
                                       }
                                    } else {
                                       switch(â˜ƒx) {
                                          case "distance":
                                             switch(â˜ƒ) {
                                                case "village":
                                                   setSpacing(â˜ƒ, "minecraft:village", â˜ƒxx, 9);
                                                   return;
                                                case "biome_1":
                                                   setSpacing(â˜ƒ, "minecraft:desert_pyramid", â˜ƒxx, 9);
                                                   setSpacing(â˜ƒ, "minecraft:igloo", â˜ƒxx, 9);
                                                   setSpacing(â˜ƒ, "minecraft:jungle_pyramid", â˜ƒxx, 9);
                                                   setSpacing(â˜ƒ, "minecraft:swamp_hut", â˜ƒxx, 9);
                                                   setSpacing(â˜ƒ, "minecraft:pillager_outpost", â˜ƒxx, 9);
                                                   return;
                                                case "endcity":
                                                   setSpacing(â˜ƒ, "minecraft:endcity", â˜ƒxx, 1);
                                                   return;
                                                case "mansion":
                                                   setSpacing(â˜ƒ, "minecraft:mansion", â˜ƒxx, 1);
                                                   return;
                                                default:
                                                   return;
                                             }
                                          case "separation":
                                             if ("oceanmonument".equals(â˜ƒ)) {
                                                WorldGenSettingsFix.StructureFeatureConfiguration â˜ƒ = (WorldGenSettingsFix.StructureFeatureConfiguration)â˜ƒ.getOrDefault(
                                                   "minecraft:monument", DEFAULTS.get("minecraft:monument")
                                                );
                                                int â˜ƒx = getInt(â˜ƒxx, â˜ƒ.separation, 1);
                                                â˜ƒ.put(
                                                   "minecraft:monument", new WorldGenSettingsFix.StructureFeatureConfiguration(â˜ƒx, â˜ƒ.separation, â˜ƒ.salt)
                                                );
                                             }
                  
                                             return;
                                          case "spacing":
                                             if ("oceanmonument".equals(â˜ƒ)) {
                                                setSpacing(â˜ƒ, "minecraft:monument", â˜ƒxx, 1);
                                             }
                  
                                             return;
                                       }
                                    }
                                 }
                              )
                        )
               )
         );
      Builder<Dynamic<T>, Dynamic<T>> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.put(
         â˜ƒ.createString("structures"),
         â˜ƒ.createMap(
            (Map<? extends Dynamic<?>, ? extends Dynamic<?>>)â˜ƒxxxx.entrySet()
               .stream()
               .collect(
                  Collectors.toMap(
                     var1x -> â˜ƒ.createString((String)var1x.getKey()),
                     var1x -> ((WorldGenSettingsFix.StructureFeatureConfiguration)var1x.getValue()).serialize(â˜ƒ)
                  )
               )
         )
      );
      if (â˜ƒxxx.isTrue()) {
         â˜ƒ.put(
            â˜ƒ.createString("stronghold"),
            â˜ƒ.createMap(
               ImmutableMap.of(
                  â˜ƒ.createString("distance"),
                  â˜ƒ.createInt(â˜ƒ.getValue()),
                  â˜ƒ.createString("spread"),
                  â˜ƒ.createInt(â˜ƒx.getValue()),
                  â˜ƒ.createString("count"),
                  â˜ƒ.createInt(â˜ƒxx.getValue())
               )
            )
         );
      }

      return â˜ƒ.build();
   }

   private static int getInt(String var0, int var1) {
      return NumberUtils.toInt(â˜ƒ, â˜ƒ);
   }

   private static int getInt(String var0, int var1, int var2) {
      return Math.max(â˜ƒ, getInt(â˜ƒ, â˜ƒ));
   }

   private static void setSpacing(Map<String, WorldGenSettingsFix.StructureFeatureConfiguration> var0, String var1, String var2, int var3) {
      WorldGenSettingsFix.StructureFeatureConfiguration â˜ƒ = (WorldGenSettingsFix.StructureFeatureConfiguration)â˜ƒ.getOrDefault(â˜ƒ, DEFAULTS.get(â˜ƒ));
      int â˜ƒx = getInt(â˜ƒ, â˜ƒ.spacing, â˜ƒ);
      â˜ƒ.put(â˜ƒ, new WorldGenSettingsFix.StructureFeatureConfiguration(â˜ƒx, â˜ƒ.separation, â˜ƒ.salt));
   }

   static final class StructureFeatureConfiguration {
      public static final Codec<WorldGenSettingsFix.StructureFeatureConfiguration> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("spacing").forGetter(var0x -> var0x.spacing),
                  Codec.INT.fieldOf("separation").forGetter(var0x -> var0x.separation),
                  Codec.INT.fieldOf("salt").forGetter(var0x -> var0x.salt)
               )
               .apply(var0, WorldGenSettingsFix.StructureFeatureConfiguration::new)
      );
      final int spacing;
      final int separation;
      final int salt;

      public StructureFeatureConfiguration(int var1, int var2, int var3) {
         this.spacing = â˜ƒ;
         this.separation = â˜ƒ;
         this.salt = â˜ƒ;
      }

      public <T> Dynamic<T> serialize(DynamicOps<T> var1) {
         return new Dynamic<>(â˜ƒ, (T)CODEC.encodeStart(â˜ƒ, this).result().orElse(â˜ƒ.emptyMap()));
      }
   }
}
