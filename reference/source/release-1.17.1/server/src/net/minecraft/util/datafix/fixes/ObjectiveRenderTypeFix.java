package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ObjectiveRenderTypeFix extends DataFix {
   public ObjectiveRenderTypeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private static ObjectiveCriteria.RenderType getRenderType(String var0) {
      return â˜ƒ.equals("health") ? ObjectiveCriteria.RenderType.HEARTS : ObjectiveCriteria.RenderType.INTEGER;
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.OBJECTIVE);
      return this.fixTypeEverywhereTyped("ObjectiveRenderTypeFix", â˜ƒ, var0 -> var0.update(DSL.remainderFinder(), var0x -> {
            Optional<String> â˜ƒ = var0x.get("RenderType").asString().result();
            if (!â˜ƒ.isPresent()) {
               String â˜ƒx = var0x.get("CriteriaName").asString("");
               ObjectiveCriteria.RenderType â˜ƒxx = getRenderType(â˜ƒx);
               return var0x.set("RenderType", var0x.createString(â˜ƒxx.getId()));
            } else {
               return var0x;
            }
         }));
   }
}
