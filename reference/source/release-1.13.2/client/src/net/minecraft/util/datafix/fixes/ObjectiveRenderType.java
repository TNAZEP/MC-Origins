package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.datafix.TypeReferences;

public class ObjectiveRenderType extends DataFix {
   public ObjectiveRenderType(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   private static ScoreCriteria.RenderType func_211858_a(String var0) {
      return ☃.equals("health") ? ScoreCriteria.RenderType.HEARTS : ScoreCriteria.RenderType.INTEGER;
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> ☃ = DSL.named(TypeReferences.field_211873_t.typeName(), DSL.remainderType());
      if (!Objects.equals(☃, this.getInputSchema().getType(TypeReferences.field_211873_t))) {
         throw new IllegalStateException("Objective type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("ObjectiveRenderTypeFix", ☃, var0 -> var0x -> var0x.mapSecond(var0xx -> {
                  Optional<String> ☃ = var0xx.get("RenderType").flatMap(Dynamic::getStringValue);
                  if (!☃.isPresent()) {
                     String ☃x = var0xx.getString("CriteriaName");
                     ScoreCriteria.RenderType ☃xx = func_211858_a(☃x);
                     return var0xx.set("RenderType", var0xx.createString(☃xx.func_211838_a()));
                  } else {
                     return var0xx;
                  }
               }));
      }
   }
}
