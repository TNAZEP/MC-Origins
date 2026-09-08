package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class CustomNameStringToComponentFixTileEntity extends DataFix {
   public CustomNameStringToComponentFixTileEntity(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<String> ☃ = DSL.fieldFinder("id", DSL.namespacedString());
      return this.fixTypeEverywhereTyped(
         "BlockEntityCustomNameToComponentFix",
         this.getInputSchema().getType(TypeReferences.field_211294_j),
         var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> ☃ = var1x.getOptional(☃);
               return ☃.isPresent() && Objects.equals(☃.get(), "minecraft:command_block") ? var2 : CustomNameStringToComponentEntity.func_209740_a(var2);
            })
      );
   }
}
