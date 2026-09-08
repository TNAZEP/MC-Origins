package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SavedDataFeaturePoolElementFix extends DataFix {
   private static final Pattern INDEX_PATTERN = Pattern.compile("\\[(\\d+)\\]");
   private static final Set<String> PIECE_TYPE = Sets.newHashSet(
      "minecraft:jigsaw", "minecraft:nvi", "minecraft:pcp", "minecraft:bastionremnant", "minecraft:runtime"
   );
   private static final Set<String> FEATURES = Sets.newHashSet("minecraft:tree", "minecraft:flower", "minecraft:block_pile", "minecraft:random_patch");

   public SavedDataFeaturePoolElementFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead(
         "SavedDataFeaturePoolElementFix",
         this.getInputSchema().getType(References.STRUCTURE_FEATURE),
         this.getOutputSchema().getType(References.STRUCTURE_FEATURE),
         SavedDataFeaturePoolElementFix::fixTag
      );
   }

   private static <T> Dynamic<T> fixTag(Dynamic<T> var0) {
      return â˜ƒ.update("Children", SavedDataFeaturePoolElementFix::updateChildren);
   }

   private static <T> Dynamic<T> updateChildren(Dynamic<T> var0) {
      return (Dynamic<T>)â˜ƒ.asStreamOpt().map(SavedDataFeaturePoolElementFix::updateChildren).map(â˜ƒ::createList).result().orElse(â˜ƒ);
   }

   private static Stream<? extends Dynamic<?>> updateChildren(Stream<? extends Dynamic<?>> var0) {
      return â˜ƒ.map(
         var0x -> {
            String â˜ƒ = var0x.get("id").asString("");
            if (!PIECE_TYPE.contains(â˜ƒ)) {
               return var0x;
            } else {
               OptionalDynamic<?> â˜ƒ = var0x.get("pool_element");
               if (!â˜ƒ.get("element_type").asString("").equals("minecraft:feature_pool_element")) {
                  return var0x;
               } else {
                  return !â˜ƒ.get("feature").get("name").result().isPresent()
                     ? var0x
                     : var0x.update("pool_element", var0xx -> var0xx.update("feature", SavedDataFeaturePoolElementFix::fixFeature));
               }
            }
         }
      );
   }

   private static <T> OptionalDynamic<T> get(Dynamic<T> var0, String... var1) {
      if (â˜ƒ.length == 0) {
         throw new IllegalArgumentException("Missing path");
      } else {
         OptionalDynamic<T> â˜ƒ = â˜ƒ.get(â˜ƒ[0]);

         for(int â˜ƒx = 1; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            String â˜ƒxx = â˜ƒ[â˜ƒx];
            Matcher â˜ƒxxx = INDEX_PATTERN.matcher(â˜ƒxx);
            if (â˜ƒxxx.matches()) {
               int â˜ƒxxxx = Integer.parseInt(â˜ƒxxx.group(1));
               List<? extends Dynamic<T>> â˜ƒxxxxx = â˜ƒ.asList(Function.identity());
               if (â˜ƒxxxx >= 0 && â˜ƒxxxx < â˜ƒxxxxx.size()) {
                  â˜ƒ = new OptionalDynamic<>(â˜ƒ.getOps(), DataResult.success((Dynamic<T>)â˜ƒxxxxx.get(â˜ƒxxxx)));
               } else {
                  â˜ƒ = new OptionalDynamic<>(â˜ƒ.getOps(), DataResult.error("Missing id:" + â˜ƒxxxx));
               }
            } else {
               â˜ƒ = â˜ƒ.get(â˜ƒxx);
            }
         }

         return â˜ƒ;
      }
   }

   @VisibleForTesting
   protected static Dynamic<?> fixFeature(Dynamic<?> var0) {
      Optional<String> â˜ƒ = getReplacement(
         get(â˜ƒ, "type").asString(""),
         get(â˜ƒ, "name").asString(""),
         get(â˜ƒ, "config", "state_provider", "type").asString(""),
         get(â˜ƒ, "config", "state_provider", "state", "Name").asString(""),
         get(â˜ƒ, "config", "state_provider", "entries", "[0]", "data", "Name").asString(""),
         get(â˜ƒ, "config", "foliage_placer", "type").asString(""),
         get(â˜ƒ, "config", "leaves_provider", "state", "Name").asString("")
      );
      return â˜ƒ.isPresent() ? â˜ƒ.createString((String)â˜ƒ.get()) : â˜ƒ;
   }

   private static Optional<String> getReplacement(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
      String â˜ƒ;
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ = â˜ƒ;
      } else {
         if (â˜ƒ.isEmpty()) {
            return Optional.empty();
         }

         if ("minecraft:normal_tree".equals(â˜ƒ)) {
            â˜ƒ = "minecraft:tree";
         } else {
            â˜ƒ = â˜ƒ;
         }
      }

      if (FEATURES.contains(â˜ƒ)) {
         if ("minecraft:random_patch".equals(â˜ƒ)) {
            if ("minecraft:simple_state_provider".equals(â˜ƒ)) {
               if ("minecraft:sweet_berry_bush".equals(â˜ƒ)) {
                  return Optional.of("minecraft:patch_berry_bush");
               }

               if ("minecraft:cactus".equals(â˜ƒ)) {
                  return Optional.of("minecraft:patch_cactus");
               }
            } else if ("minecraft:weighted_state_provider".equals(â˜ƒ) && ("minecraft:grass".equals(â˜ƒ) || "minecraft:fern".equals(â˜ƒ))) {
               return Optional.of("minecraft:patch_taiga_grass");
            }
         } else if ("minecraft:block_pile".equals(â˜ƒ)) {
            if (!"minecraft:simple_state_provider".equals(â˜ƒ) && !"minecraft:rotated_block_provider".equals(â˜ƒ)) {
               if ("minecraft:weighted_state_provider".equals(â˜ƒ)) {
                  if ("minecraft:packed_ice".equals(â˜ƒ) || "minecraft:blue_ice".equals(â˜ƒ)) {
                     return Optional.of("minecraft:pile_ice");
                  }

                  if ("minecraft:jack_o_lantern".equals(â˜ƒ) || "minecraft:pumpkin".equals(â˜ƒ)) {
                     return Optional.of("minecraft:pile_pumpkin");
                  }
               }
            } else {
               if ("minecraft:hay_block".equals(â˜ƒ)) {
                  return Optional.of("minecraft:pile_hay");
               }

               if ("minecraft:melon".equals(â˜ƒ)) {
                  return Optional.of("minecraft:pile_melon");
               }

               if ("minecraft:snow".equals(â˜ƒ)) {
                  return Optional.of("minecraft:pile_snow");
               }
            }
         } else {
            if ("minecraft:flower".equals(â˜ƒ)) {
               return Optional.of("minecraft:flower_plain");
            }

            if ("minecraft:tree".equals(â˜ƒ)) {
               if ("minecraft:acacia_foliage_placer".equals(â˜ƒ)) {
                  return Optional.of("minecraft:acacia");
               }

               if ("minecraft:blob_foliage_placer".equals(â˜ƒ) && "minecraft:oak_leaves".equals(â˜ƒ)) {
                  return Optional.of("minecraft:oak");
               }

               if ("minecraft:pine_foliage_placer".equals(â˜ƒ)) {
                  return Optional.of("minecraft:pine");
               }

               if ("minecraft:spruce_foliage_placer".equals(â˜ƒ)) {
                  return Optional.of("minecraft:spruce");
               }
            }
         }
      }

      return Optional.empty();
   }
}
