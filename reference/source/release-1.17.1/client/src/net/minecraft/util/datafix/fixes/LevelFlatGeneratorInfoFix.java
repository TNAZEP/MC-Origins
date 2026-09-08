package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.math.NumberUtils;

public class LevelFlatGeneratorInfoFix extends DataFix {
   private static final String GENERATOR_OPTIONS = "generatorOptions";
   @VisibleForTesting
   static final String DEFAULT = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
   private static final Splitter SPLITTER = Splitter.on(';').limit(5);
   private static final Splitter LAYER_SPLITTER = Splitter.on(',');
   private static final Splitter OLD_AMOUNT_SPLITTER = Splitter.on('x').limit(2);
   private static final Splitter AMOUNT_SPLITTER = Splitter.on('*').limit(2);
   private static final Splitter BLOCK_SPLITTER = Splitter.on(':').limit(3);

   public LevelFlatGeneratorInfoFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelFlatGeneratorInfoFix", this.getInputSchema().getType(References.LEVEL), var1 -> var1.update(DSL.remainderFinder(), this::fix)
      );
   }

   private Dynamic<?> fix(Dynamic<?> var1) {
      return â˜ƒ.get("generatorName").asString("").equalsIgnoreCase("flat")
         ? â˜ƒ.update("generatorOptions", var1x -> DataFixUtils.orElse(var1x.asString().map(this::fixString).map(var1x::createString).result(), var1x))
         : â˜ƒ;
   }

   @VisibleForTesting
   String fixString(String var1) {
      if (â˜ƒ.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> â˜ƒxx = SPLITTER.split(â˜ƒ).iterator();
         String â˜ƒxxx = (String)â˜ƒxx.next();
         int â˜ƒ;
         String â˜ƒx;
         if (â˜ƒxx.hasNext()) {
            â˜ƒ = NumberUtils.toInt(â˜ƒxxx, 0);
            â˜ƒx = (String)â˜ƒxx.next();
         } else {
            â˜ƒ = 0;
            â˜ƒx = â˜ƒxxx;
         }

         if (â˜ƒ >= 0 && â˜ƒ <= 3) {
            StringBuilder â˜ƒ = new StringBuilder();
            Splitter â˜ƒx = â˜ƒ < 3 ? OLD_AMOUNT_SPLITTER : AMOUNT_SPLITTER;
            â˜ƒ.append((String)StreamSupport.stream(LAYER_SPLITTER.split(â˜ƒx).spliterator(), false).map(var2x -> {
               List<String> â˜ƒxx = â˜ƒ.splitToList(var2x);
               int â˜ƒ;
               String â˜ƒx;
               if (â˜ƒxx.size() == 2) {
                  â˜ƒ = NumberUtils.toInt((String)â˜ƒxx.get(0));
                  â˜ƒx = (String)â˜ƒxx.get(1);
               } else {
                  â˜ƒ = 1;
                  â˜ƒx = (String)â˜ƒxx.get(0);
               }

               List<String> â˜ƒ = BLOCK_SPLITTER.splitToList(â˜ƒx);
               int â˜ƒx = ((String)â˜ƒ.get(0)).equals("minecraft") ? 1 : 0;
               String â˜ƒxx = (String)â˜ƒ.get(â˜ƒx);
               int â˜ƒxxx = â˜ƒ == 3 ? EntityBlockStateFix.getBlockId("minecraft:" + â˜ƒxx) : NumberUtils.toInt(â˜ƒxx, 0);
               int â˜ƒxxxx = â˜ƒx + 1;
               int â˜ƒxxxxx = â˜ƒ.size() > â˜ƒxxxx ? NumberUtils.toInt((String)â˜ƒ.get(â˜ƒxxxx), 0) : 0;
               return (â˜ƒ == 1 ? "" : â˜ƒ + "*") + BlockStateData.getTag(â˜ƒxxx << 4 | â˜ƒxxxxx).get("Name").asString("");
            }).collect(Collectors.joining(",")));

            while(â˜ƒxx.hasNext()) {
               â˜ƒ.append(';').append((String)â˜ƒxx.next());
            }

            return â˜ƒ.toString();
         } else {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
         }
      }
   }
}
