package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class NewVillageFix extends DataFix {
   public NewVillageFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      CompoundListType<String, ?> â˜ƒ = DSL.compoundList(DSL.string(), this.getInputSchema().getType(References.STRUCTURE_FEATURE));
      OpticFinder<? extends List<? extends Pair<String, ?>>> â˜ƒx = â˜ƒ.finder();
      return this.cap(â˜ƒ);
   }

   private <SF> TypeRewriteRule cap(CompoundListType<String, SF> var1) {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = this.getInputSchema().getType(References.STRUCTURE_FEATURE);
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("Level");
      OpticFinder<?> â˜ƒxxx = â˜ƒxx.type().findField("Structures");
      OpticFinder<?> â˜ƒxxxx = â˜ƒxxx.type().findField("Starts");
      OpticFinder<List<Pair<String, SF>>> â˜ƒxxxxx = â˜ƒ.finder();
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "NewVillageFix",
            â˜ƒ,
            var4x -> var4x.updateTyped(
                  â˜ƒ,
                  var3x -> var3x.updateTyped(
                        â˜ƒ,
                        var2x -> var2x.updateTyped(
                                 â˜ƒ,
                                 var1x -> var1x.update(
                                       â˜ƒ,
                                       var0x -> (List)var0x.stream()
                                             .filter(var0xx -> !Objects.equals(var0xx.getFirst(), "Village"))
                                             .map(var0xx -> var0xx.mapFirst(var0xxx -> var0xxx.equals("New_Village") ? "Village" : var0xxx))
                                             .collect(Collectors.toList())
                                    )
                              )
                              .update(DSL.remainderFinder(), var0x -> var0x.update("References", var0xx -> {
                                    Optional<? extends Dynamic<?>> â˜ƒ = var0xx.get("New_Village").result();
                                    return DataFixUtils.orElse(â˜ƒ.map(var1x -> var0xx.remove("New_Village").set("Village", var1x)), var0xx).remove("Village");
                                 }))
                     )
               )
         ),
         this.fixTypeEverywhereTyped(
            "NewVillageStartFix",
            â˜ƒx,
            var0 -> var0.update(
                  DSL.remainderFinder(),
                  var0x -> var0x.update(
                        "id",
                        var0xx -> Objects.equals(NamespacedSchema.ensureNamespaced(var0xx.asString("")), "minecraft:new_village")
                              ? var0xx.createString("minecraft:village")
                              : var0xx
                     )
               )
         )
      );
   }
}
