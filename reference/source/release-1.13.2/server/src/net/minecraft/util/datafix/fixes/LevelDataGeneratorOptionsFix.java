package net.minecraft.util.datafix.fixes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.JsonOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;

public class LevelDataGeneratorOptionsFix extends DataFix {
   static final Map<String, String> field_210553_a = Util.func_200696_a(Maps.newHashMap(), var0 -> {
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

   public LevelDataGeneratorOptionsFix(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getOutputSchema().getType(TypeReferences.field_211285_a);
      return this.fixTypeEverywhereTyped("LevelDataGeneratorOptionsFix", this.getInputSchema().getType(TypeReferences.field_211285_a), ☃, var1x -> {
         Dynamic<?> ☃x = var1x.write();
         Optional<String> ☃xx = ☃x.get("generatorOptions").flatMap(Dynamic::getStringValue);
         Dynamic<?> ☃;
         if ("flat".equalsIgnoreCase(☃x.getString("generatorName"))) {
            String ☃xxx = (String)☃xx.orElse("");
            ☃ = ☃x.set("generatorOptions", func_210549_a(☃xxx, ☃x.getOps()));
         } else if ("buffet".equalsIgnoreCase(☃x.getString("generatorName")) && ☃xx.isPresent()) {
            Dynamic<JsonElement> ☃ = new Dynamic<>(JsonOps.INSTANCE, JsonUtils.func_212746_a((String)☃xx.get(), true));
            ☃ = ☃x.set("generatorOptions", ☃.convert(☃x.getOps()));
         } else {
            ☃ = ☃x;
         }

         return (Typed)((Optional)☃.readTyped(☃).getSecond()).orElseThrow(() -> new IllegalStateException("Could not read new level type."));
      });
   }

   private static <T> Dynamic<T> func_210549_a(String var0, DynamicOps<T> var1) {
      Iterator<String> ☃x = Splitter.on(';').split(☃).iterator();
      String ☃xx = "minecraft:plains";
      Map<String, Map<String, String>> ☃xxx = Maps.newHashMap();
      List<Pair<Integer, String>> ☃;
      if (!☃.isEmpty() && ☃x.hasNext()) {
         ☃ = func_210552_b((String)☃x.next());
         if (!☃.isEmpty()) {
            if (☃x.hasNext()) {
               ☃xx = (String)field_210553_a.getOrDefault(☃x.next(), "minecraft:plains");
            }

            if (☃x.hasNext()) {
               String[] ☃xxxx = ((String)☃x.next()).toLowerCase(Locale.ROOT).split(",");

               for(String ☃xxxxx : ☃xxxx) {
                  String[] ☃xxxxxx = ☃xxxxx.split("\\(", 2);
                  if (!☃xxxxxx[0].isEmpty()) {
                     ☃xxx.put(☃xxxxxx[0], Maps.newHashMap());
                     if (☃xxxxxx.length > 1 && ☃xxxxxx[1].endsWith(")") && ☃xxxxxx[1].length() > 1) {
                        String[] ☃xxxxxxx = ☃xxxxxx[1].substring(0, ☃xxxxxx[1].length() - 1).split(" ");

                        for(String ☃xxxxxxxx : ☃xxxxxxx) {
                           String[] ☃xxxxxxxxx = ☃xxxxxxxx.split("=", 2);
                           if (☃xxxxxxxxx.length == 2) {
                              ((Map)☃xxx.get(☃xxxxxx[0])).put(☃xxxxxxxxx[0], ☃xxxxxxxxx[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               ☃xxx.put("village", Maps.newHashMap());
            }
         }
      } else {
         ☃ = Lists.<Pair<Integer, String>>newArrayList();
         ☃.add(Pair.of(1, "minecraft:bedrock"));
         ☃.add(Pair.of(2, "minecraft:dirt"));
         ☃.add(Pair.of(1, "minecraft:grass_block"));
         ☃xxx.put("village", Maps.newHashMap());
      }

      T ☃ = ☃.createList(
         ☃.stream()
            .map(
               var1x -> ☃.createMap(
                     ImmutableMap.of(
                        ☃.createString("height"), ☃.createInt(var1x.getFirst()), ☃.createString("block"), ☃.createString((String)var1x.getSecond())
                     )
                  )
            )
      );
      T ☃x = ☃.createMap(
         (Map<T, T>)☃xxx.entrySet()
            .stream()
            .map(
               var1x -> Pair.of(
                     ☃.createString(((String)var1x.getKey()).toLowerCase(Locale.ROOT)),
                     ☃.createMap(
                        (Map<T, T>)((Map)var1x.getValue())
                           .entrySet()
                           .stream()
                           .map(var1xx -> Pair.of(☃.createString((String)var1xx.getKey()), ☃.createString((String)var1xx.getValue())))
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
                     )
                  )
            )
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
      );
      return new Dynamic<>(
         ☃, ☃.createMap(ImmutableMap.of(☃.createString("layers"), ☃, ☃.createString("biome"), ☃.createString(☃xx), ☃.createString("structures"), ☃x))
      );
   }

   @Nullable
   private static Pair<Integer, String> func_210548_a(String var0) {
      String[] ☃x = ☃.split("\\*", 2);
      int ☃;
      if (☃x.length == 2) {
         try {
            ☃ = Integer.parseInt(☃x[0]);
         } catch (NumberFormatException var4) {
            return null;
         }
      } else {
         ☃ = 1;
      }

      String ☃ = ☃x[☃x.length - 1];
      return Pair.of(☃, ☃);
   }

   private static List<Pair<Integer, String>> func_210552_b(String var0) {
      List<Pair<Integer, String>> ☃ = Lists.<Pair<Integer, String>>newArrayList();
      String[] ☃x = ☃.split(",");

      for(String ☃xx : ☃x) {
         Pair<Integer, String> ☃xxx = func_210548_a(☃xx);
         if (☃xxx == null) {
            return Collections.emptyList();
         }

         ☃.add(☃xxx);
      }

      return ☃;
   }
}
