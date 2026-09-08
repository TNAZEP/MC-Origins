package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;

public class MobSpawnerEntityIdentifiersFix extends DataFix {
   public MobSpawnerEntityIdentifiersFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private Dynamic<?> fix(Dynamic<?> var1) {
      if (!"MobSpawner".equals(â˜ƒ.get("id").asString(""))) {
         return â˜ƒ;
      } else {
         Optional<String> â˜ƒ = â˜ƒ.get("EntityId").asString().result();
         if (â˜ƒ.isPresent()) {
            Dynamic<?> â˜ƒx = DataFixUtils.orElse(â˜ƒ.get("SpawnData").result(), â˜ƒ.emptyMap());
            â˜ƒx = â˜ƒx.set("id", â˜ƒx.createString(((String)â˜ƒ.get()).isEmpty() ? "Pig" : (String)â˜ƒ.get()));
            â˜ƒ = â˜ƒ.set("SpawnData", â˜ƒx);
            â˜ƒ = â˜ƒ.remove("EntityId");
         }

         Optional<? extends Stream<? extends Dynamic<?>>> â˜ƒ = â˜ƒ.get("SpawnPotentials").asStreamOpt().result();
         if (â˜ƒ.isPresent()) {
            â˜ƒ = â˜ƒ.set("SpawnPotentials", â˜ƒ.createList(((Stream)â˜ƒ.get()).map(var0 -> {
               Optional<String> â˜ƒ = var0.get("Type").asString().result();
               if (â˜ƒ.isPresent()) {
                  Dynamic<?> â˜ƒx = DataFixUtils.orElse(var0.get("Properties").result(), var0.emptyMap()).set("id", var0.createString((String)â˜ƒ.get()));
                  return var0.set("Entity", â˜ƒx).remove("Type").remove("Properties");
               } else {
                  return var0;
               }
            })));
         }

         return â˜ƒ;
      }
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.UNTAGGED_SPAWNER);
      return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(References.UNTAGGED_SPAWNER), â˜ƒ, var2 -> {
         Dynamic<?> â˜ƒ = var2.get(DSL.remainderFinder());
         â˜ƒ = â˜ƒ.set("id", â˜ƒ.createString("MobSpawner"));
         DataResult<? extends Pair<? extends Typed<?>, ?>> â˜ƒx = â˜ƒ.readTyped(this.fix(â˜ƒ));
         return !â˜ƒx.result().isPresent() ? var2 : (Typed)((Pair)â˜ƒx.result().get()).getFirst();
      });
   }
}
