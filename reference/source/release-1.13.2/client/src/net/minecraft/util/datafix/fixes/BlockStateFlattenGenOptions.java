package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.util.datafix.TypeReferences;
import org.apache.commons.lang3.math.NumberUtils;

public class BlockStateFlattenGenOptions extends DataFix {
   private static final Splitter field_199181_a = Splitter.on(';').limit(5);
   private static final Splitter field_199182_b = Splitter.on(',');
   private static final Splitter field_199183_c = Splitter.on('x').limit(2);
   private static final Splitter field_199184_d = Splitter.on('*').limit(2);
   private static final Splitter field_199185_e = Splitter.on(':').limit(3);

   public BlockStateFlattenGenOptions(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelFlatGeneratorInfoFix",
         this.getInputSchema().getType(TypeReferences.field_211285_a),
         var1 -> var1.update(DSL.remainderFinder(), this::func_209636_a)
      );
   }

   private Dynamic<?> func_209636_a(Dynamic<?> var1) {
      return ☃.getString("generatorName").equalsIgnoreCase("flat")
         ? ☃.update("generatorOptions", var1x -> DataFixUtils.orElse(var1x.getStringValue().map(this::func_199180_a).map(var1x::createString), var1x))
         : ☃;
   }

   @VisibleForTesting
   String func_199180_a(String var1) {
      if (☃.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> ☃xx = field_199181_a.split(☃).iterator();
         String ☃xxx = (String)☃xx.next();
         int ☃;
         String ☃x;
         if (☃xx.hasNext()) {
            ☃ = NumberUtils.toInt(☃xxx, 0);
            ☃x = (String)☃xx.next();
         } else {
            ☃ = 0;
            ☃x = ☃xxx;
         }

         if (☃ >= 0 && ☃ <= 3) {
            StringBuilder ☃ = new StringBuilder();
            Splitter ☃x = ☃ < 3 ? field_199183_c : field_199184_d;
            ☃.append((String)StreamSupport.stream(field_199182_b.split(☃x).spliterator(), false).map(var2x -> {
               List<String> ☃xx = ☃.splitToList(var2x);
               int ☃;
               String ☃x;
               if (☃xx.size() == 2) {
                  ☃ = NumberUtils.toInt((String)☃xx.get(0));
                  ☃x = (String)☃xx.get(1);
               } else {
                  ☃ = 1;
                  ☃x = (String)☃xx.get(0);
               }

               List<String> ☃ = field_199185_e.splitToList(☃x);
               int ☃x = ((String)☃.get(0)).equals("minecraft") ? 1 : 0;
               String ☃xx = (String)☃.get(☃x);
               int ☃xxx = ☃ == 3 ? BlockStateFlatternEntities.func_199171_a("minecraft:" + ☃xx) : NumberUtils.toInt(☃xx, 0);
               int ☃xxxx = ☃x + 1;
               int ☃xxxxx = ☃.size() > ☃xxxx ? NumberUtils.toInt((String)☃.get(☃xxxx), 0) : 0;
               return (☃ == 1 ? "" : ☃ + "*") + BlockStateFlatteningMap.func_210049_b(☃xxx << 4 | ☃xxxxx).getString("Name");
            }).collect(Collectors.joining(",")));

            while(☃xx.hasNext()) {
               ☃.append(';').append((String)☃xx.next());
            }

            return ☃.toString();
         } else {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
         }
      }
   }
}
