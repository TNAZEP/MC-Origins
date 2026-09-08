package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlockEntityCustomNameToComponentFix extends DataFix {
   public BlockEntityCustomNameToComponentFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<String> â˜ƒ = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
      return this.fixTypeEverywhereTyped(
         "BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(References.BLOCK_ENTITY), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> â˜ƒ = var1x.getOptional(â˜ƒ);
               return â˜ƒ.isPresent() && Objects.equals(â˜ƒ.get(), "minecraft:command_block") ? var2 : EntityCustomNameToComponentFix.fixTagCustomName(var2);
            })
      );
   }
}
