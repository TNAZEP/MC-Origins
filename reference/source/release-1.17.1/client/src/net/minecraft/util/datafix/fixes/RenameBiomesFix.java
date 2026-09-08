package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class RenameBiomesFix extends DataFix {
   private final String name;
   private final Map<String, String> biomes;

   public RenameBiomesFix(Schema var1, boolean var2, String var3, Map<String, String> var4) {
      super(â˜ƒ, â˜ƒ);
      this.biomes = â˜ƒ;
      this.name = â˜ƒ;
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> â˜ƒ = DSL.named(References.BIOME.typeName(), NamespacedSchema.namespacedString());
      if (!Objects.equals(â˜ƒ, this.getInputSchema().getType(References.BIOME))) {
         throw new IllegalStateException("Biome type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.name, â˜ƒ, var1x -> var1xx -> var1xx.mapSecond(var1xxx -> (String)this.biomes.getOrDefault(var1xxx, var1xxx)));
      }
   }
}
