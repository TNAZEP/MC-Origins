package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class RedundantChanceTags extends DataFix {
   public RedundantChanceTags(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityRedundantChanceTagsFix", this.getInputSchema().getType(TypeReferences.field_211299_o), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               Dynamic<?> ☃ = var0x;
               if (Objects.equals(var0x.get("HandDropChances"), Optional.of(var0x.createList(Stream.generate(() -> ☃.createFloat(0.0F)).limit(2L))))) {
                  var0x = var0x.remove("HandDropChances");
               }
   
               if (Objects.equals(var0x.get("ArmorDropChances"), Optional.of(var0x.createList(Stream.generate(() -> ☃.createFloat(0.0F)).limit(4L))))) {
                  var0x = var0x.remove("ArmorDropChances");
               }
   
               return var0x;
            })
      );
   }
}
