package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Objects;

public class EntityMinecartIdentifiersFix extends DataFix {
   private static final List<String> MINECART_BY_ID = Lists.newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace");

   public EntityMinecartIdentifiersFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> â˜ƒ = this.getInputSchema().findChoiceType(References.ENTITY);
      TaggedChoiceType<String> â˜ƒx = this.getOutputSchema().findChoiceType(References.ENTITY);
      return this.fixTypeEverywhere(
         "EntityMinecartIdentifiersFix",
         â˜ƒ,
         â˜ƒx,
         var2x -> var3 -> {
               if (!Objects.equals(var3.getFirst(), "Minecart")) {
                  return var3;
               } else {
                  Typed<? extends Pair<String, ?>> â˜ƒx = (Typed)â˜ƒ.point(var2x, "Minecart", var3.getSecond()).orElseThrow(IllegalStateException::new);
                  Dynamic<?> â˜ƒxx = â˜ƒx.getOrCreate(DSL.remainderFinder());
                  int â˜ƒxxx = â˜ƒxx.get("Type").asInt(0);
                  String â˜ƒ;
                  if (â˜ƒxxx > 0 && â˜ƒxxx < MINECART_BY_ID.size()) {
                     â˜ƒ = (String)MINECART_BY_ID.get(â˜ƒxxx);
                  } else {
                     â˜ƒ = "MinecartRideable";
                  }
   
                  return Pair.of(
                     â˜ƒ,
                     (DataResult)â˜ƒx.write()
                        .map(var2xx -> ((Type)â˜ƒ.types().get(â˜ƒ)).read(var2xx))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
                  );
               }
            }
      );
   }
}
