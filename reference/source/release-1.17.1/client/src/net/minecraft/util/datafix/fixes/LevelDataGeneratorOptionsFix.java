package net.minecraft.util.datafix.fixes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;

public class LevelDataGeneratorOptionsFix extends DataFix {
   static final Map<String, String> MAP = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("0", "minecraft:ocean");
      var0.put("1", "minecraft:plains");
      var0.put("2", "minecraft:desert");
      var0.put("3", "minecraft:mountains");
      var0.put("4", "minecraft:forest");
      var0.put("5", "minecraft:taiga");
      var0.put("6", "minecraft:swamp");
      var0.put("7", "minecraft:river");
      var0.put("8", "minecraft:nether");
      var0.put("9", "minecraft:the_end");
      var0.put("10", "minecraft:frozen_ocean");
      var0.put("11", "minecraft:frozen_river");
      var0.put("12", "minecraft:snowy_tundra");
      var0.put("13", "minecraft:snowy_mountains");
      var0.put("14", "minecraft:mushroom_fields");
      var0.put("15", "minecraft:mushroom_field_shore");
      var0.put("16", "minecraft:beach");
      var0.put("17", "minecraft:desert_hills");
      var0.put("18", "minecraft:wooded_hills");
      var0.put("19", "minecraft:taiga_hills");
      var0.put("20", "minecraft:mountain_edge");
      var0.put("21", "minecraft:jungle");
      var0.put("22", "minecraft:jungle_hills");
      var0.put("23", "minecraft:jungle_edge");
      var0.put("24", "minecraft:deep_ocean");
      var0.put("25", "minecraft:stone_shore");
      var0.put("26", "minecraft:snowy_beach");
      var0.put("27", "minecraft:birch_forest");
      var0.put("28", "minecraft:birch_forest_hills");
      var0.put("29", "minecraft:dark_forest");
      var0.put("30", "minecraft:snowy_taiga");
      var0.put("31", "minecraft:snowy_taiga_hills");
      var0.put("32", "minecraft:giant_tree_taiga");
      var0.put("33", "minecraft:giant_tree_taiga_hills");
      var0.put("34", "minecraft:wooded_mountains");
      var0.put("35", "minecraft:savanna");
      var0.put("36", "minecraft:savanna_plateau");
      var0.put("37", "minecraft:badlands");
      var0.put("38", "minecraft:wooded_badlands_plateau");
      var0.put("39", "minecraft:badlands_plateau");
      var0.put("40", "minecraft:small_end_islands");
      var0.put("41", "minecraft:end_midlands");
      var0.put("42", "minecraft:end_highlands");
      var0.put("43", "minecraft:end_barrens");
      var0.put("44", "minecraft:warm_ocean");
      var0.put("45", "minecraft:lukewarm_ocean");
      var0.put("46", "minecraft:cold_ocean");
      var0.put("47", "minecraft:deep_warm_ocean");
      var0.put("48", "minecraft:deep_lukewarm_ocean");
      var0.put("49", "minecraft:deep_cold_ocean");
      var0.put("50", "minecraft:deep_frozen_ocean");
      var0.put("127", "minecraft:the_void");
      var0.put("129", "minecraft:sunflower_plains");
      var0.put("130", "minecraft:desert_lakes");
      var0.put("131", "minecraft:gravelly_mountains");
      var0.put("132", "minecraft:flower_forest");
      var0.put("133", "minecraft:taiga_mountains");
      var0.put("134", "minecraft:swamp_hills");
      var0.put("140", "minecraft:ice_spikes");
      var0.put("149", "minecraft:modified_jungle");
      var0.put("151", "minecraft:modified_jungle_edge");
      var0.put("155", "minecraft:tall_birch_forest");
      var0.put("156", "minecraft:tall_birch_hills");
      var0.put("157", "minecraft:dark_forest_hills");
      var0.put("158", "minecraft:snowy_taiga_mountains");
      var0.put("160", "minecraft:giant_spruce_taiga");
      var0.put("161", "minecraft:giant_spruce_taiga_hills");
      var0.put("162", "minecraft:modified_gravelly_mountains");
      var0.put("163", "minecraft:shattered_savanna");
      var0.put("164", "minecraft:shattered_savanna_plateau");
      var0.put("165", "minecraft:eroded_badlands");
      var0.put("166", "minecraft:modified_wooded_badlands_plateau");
      var0.put("167", "minecraft:modified_badlands_plateau");
   });
   public static final String GENERATOR_OPTIONS = "generatorOptions";

   public LevelDataGeneratorOptionsFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.LEVEL);
      return this.fixTypeEverywhereTyped(
         "LevelDataGeneratorOptionsFix", this.getInputSchema().getType(References.LEVEL), â˜ƒ, var1x -> (Typed)var1x.write().flatMap(var1xx -> {
               Optional<String> â˜ƒx = var1xx.get("generatorOptions").asString().result();
               Dynamic<?> â˜ƒ;
               if ("flat".equalsIgnoreCase(var1xx.get("generatorName").asString(""))) {
                  String â˜ƒxx = (String)â˜ƒx.orElse("");
                  â˜ƒ = var1xx.set("generatorOptions", convert(â˜ƒxx, var1xx.getOps()));
               } else if ("buffet".equalsIgnoreCase(var1xx.get("generatorName").asString("")) && â˜ƒx.isPresent()) {
                  Dynamic<JsonElement> â˜ƒ = new Dynamic<>(JsonOps.INSTANCE, GsonHelper.parse((String)â˜ƒx.get(), true));
                  â˜ƒ = var1xx.set("generatorOptions", â˜ƒ.convert(var1xx.getOps()));
               } else {
                  â˜ƒ = var1xx;
               }
   
               return â˜ƒ.readTyped(â˜ƒ);
            }).map(Pair::getFirst).result().orElseThrow(() -> new IllegalStateException("Could not read new level type."))
      );
   }

   private static <T> Dynamic<T> convert(String var0, DynamicOps<T> var1) {
      Iterator<String> â˜ƒx = Splitter.on(';').split(â˜ƒ).iterator();
      String â˜ƒxx = "minecraft:plains";
      Map<String, Map<String, String>> â˜ƒxxx = Maps.newHashMap();
      List<Pair<Integer, String>> â˜ƒ;
      if (!â˜ƒ.isEmpty() && â˜ƒx.hasNext()) {
         â˜ƒ = getLayersInfoFromString((String)â˜ƒx.next());
         if (!â˜ƒ.isEmpty()) {
            if (â˜ƒx.hasNext()) {
               â˜ƒxx = (String)MAP.getOrDefault(â˜ƒx.next(), "minecraft:plains");
            }

            if (â˜ƒx.hasNext()) {
               String[] â˜ƒxxxx = ((String)â˜ƒx.next()).toLowerCase(Locale.ROOT).split(",");

               for(String â˜ƒxxxxx : â˜ƒxxxx) {
                  String[] â˜ƒxxxxxx = â˜ƒxxxxx.split("\\(", 2);
                  if (!â˜ƒxxxxxx[0].isEmpty()) {
                     â˜ƒxxx.put(â˜ƒxxxxxx[0], Maps.newHashMap());
                     if (â˜ƒxxxxxx.length > 1 && â˜ƒxxxxxx[1].endsWith(")") && â˜ƒxxxxxx[1].length() > 1) {
                        String[] â˜ƒxxxxxxx = â˜ƒxxxxxx[1].substring(0, â˜ƒxxxxxx[1].length() - 1).split(" ");

                        for(String â˜ƒxxxxxxxx : â˜ƒxxxxxxx) {
                           String[] â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.split("=", 2);
                           if (â˜ƒxxxxxxxxx.length == 2) {
                              ((Map)â˜ƒxxx.get(â˜ƒxxxxxx[0])).put(â˜ƒxxxxxxxxx[0], â˜ƒxxxxxxxxx[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               â˜ƒxxx.put("village", Maps.newHashMap());
            }
         }
      } else {
         â˜ƒ = Lists.<Pair<Integer, String>>newArrayList();
         â˜ƒ.add(Pair.of(1, "minecraft:bedrock"));
         â˜ƒ.add(Pair.of(2, "minecraft:dirt"));
         â˜ƒ.add(Pair.of(1, "minecraft:grass_block"));
         â˜ƒxxx.put("village", Maps.newHashMap());
      }

      T â˜ƒ = â˜ƒ.createList(
         â˜ƒ.stream()
            .map(
               var1x -> â˜ƒ.createMap(
                     ImmutableMap.of(
                        â˜ƒ.createString("height"), â˜ƒ.createInt(var1x.getFirst()), â˜ƒ.createString("block"), â˜ƒ.createString((String)var1x.getSecond())
                     )
                  )
            )
      );
      T â˜ƒx = â˜ƒ.createMap(
         (Map<T, T>)â˜ƒxxx.entrySet()
            .stream()
            .map(
               var1x -> Pair.of(
                     â˜ƒ.createString(((String)var1x.getKey()).toLowerCase(Locale.ROOT)),
                     â˜ƒ.createMap(
                        (Map<T, T>)((Map)var1x.getValue())
                           .entrySet()
                           .stream()
                           .map(var1xx -> Pair.of(â˜ƒ.createString((String)var1xx.getKey()), â˜ƒ.createString((String)var1xx.getValue())))
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
                     )
                  )
            )
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
      );
      return new Dynamic<>(
         â˜ƒ,
         â˜ƒ.createMap(
            ImmutableMap.of(â˜ƒ.createString("layers"), â˜ƒ, â˜ƒ.createString("biome"), â˜ƒ.createString(â˜ƒxx), â˜ƒ.createString("structures"), â˜ƒx)
         )
      );
   }

   @Nullable
   private static Pair<Integer, String> getLayerInfoFromString(String var0) {
      String[] â˜ƒx = â˜ƒ.split("\\*", 2);
      int â˜ƒ;
      if (â˜ƒx.length == 2) {
         try {
            â˜ƒ = Integer.parseInt(â˜ƒx[0]);
         } catch (NumberFormatException var4) {
            return null;
         }
      } else {
         â˜ƒ = 1;
      }

      String â˜ƒ = â˜ƒx[â˜ƒx.length - 1];
      return Pair.of(â˜ƒ, â˜ƒ);
   }

   private static List<Pair<Integer, String>> getLayersInfoFromString(String var0) {
      List<Pair<Integer, String>> â˜ƒ = Lists.<Pair<Integer, String>>newArrayList();
      String[] â˜ƒx = â˜ƒ.split(",");

      for(String â˜ƒxx : â˜ƒx) {
         Pair<Integer, String> â˜ƒxxx = getLayerInfoFromString(â˜ƒxx);
         if (â˜ƒxxx == null) {
            return Collections.emptyList();
         }

         â˜ƒ.add(â˜ƒxxx);
      }

      return â˜ƒ;
   }
}
