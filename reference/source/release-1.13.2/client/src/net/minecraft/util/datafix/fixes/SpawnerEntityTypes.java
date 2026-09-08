package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class SpawnerEntityTypes extends DataFix {
   public SpawnerEntityTypes(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   private Dynamic<?> func_209659_a(Dynamic<?> var1) {
      if (!"MobSpawner".equals(☃.getString("id"))) {
         return ☃;
      } else {
         Optional<String> ☃ = ☃.get("EntityId").flatMap(Dynamic::getStringValue);
         if (☃.isPresent()) {
            Dynamic<?> ☃x = DataFixUtils.orElse(☃.get("SpawnData"), ☃.emptyMap());
            ☃x = ☃x.set("id", ☃x.createString(((String)☃.get()).isEmpty() ? "Pig" : (String)☃.get()));
            ☃ = ☃.set("SpawnData", ☃x);
            ☃ = ☃.remove("EntityId");
         }

         Optional<? extends Stream<? extends Dynamic<?>>> ☃ = ☃.get("SpawnPotentials").flatMap(Dynamic::getStream);
         if (☃.isPresent()) {
            ☃ = ☃.set("SpawnPotentials", ☃.createList(((Stream)☃.get()).map(var0 -> {
               Optional<String> ☃ = var0.get("Type").flatMap(Dynamic::getStringValue);
               if (☃.isPresent()) {
                  Dynamic<?> ☃x = DataFixUtils.orElse(var0.get("Properties"), var0.emptyMap()).set("id", var0.createString((String)☃.get()));
                  return var0.set("Entity", ☃x).remove("Type").remove("Properties");
               } else {
                  return var0;
               }
            })));
         }

         return ☃;
      }
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getOutputSchema().getType(TypeReferences.field_211302_r);
      return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(TypeReferences.field_211302_r), ☃, var2 -> {
         Dynamic<?> ☃ = var2.get(DSL.remainderFinder());
         ☃ = ☃.set("id", ☃.createString("MobSpawner"));
         Pair<?, ? extends Optional<? extends Typed<?>>> ☃x = ☃.readTyped(this.func_209659_a(☃));
         return !((Optional)☃x.getSecond()).isPresent() ? var2 : (Typed)((Optional)☃x.getSecond()).get();
      });
   }
}
