package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ObjectiveDisplayName extends DataFix {
   public ObjectiveDisplayName(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> ☃ = DSL.named(TypeReferences.field_211873_t.typeName(), DSL.remainderType());
      if (!Objects.equals(☃, this.getInputSchema().getType(TypeReferences.field_211873_t))) {
         throw new IllegalStateException("Objective type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(
            "ObjectiveDisplayNameFix",
            ☃,
            var0 -> var0x -> var0x.mapSecond(
                     var0xx -> var0xx.update(
                           "DisplayName",
                           var1x -> DataFixUtils.orElse(
                                 var1x.getStringValue()
                                    .map(var0xxx -> ITextComponent.Serializer.func_150696_a(new TextComponentString(var0xxx)))
                                    .map(var0xx::createString),
                                 var1x
                              )
                        )
                  )
         );
      }
   }
}
