package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlockNameFlatteningFix extends DataFix {
   public BlockNameFlatteningFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.BLOCK_NAME);
      Type<?> â˜ƒx = this.getOutputSchema().getType(References.BLOCK_NAME);
      Type<Pair<String, Either<Integer, String>>> â˜ƒxx = DSL.named(
         References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString())
      );
      Type<Pair<String, String>> â˜ƒxxx = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
      if (Objects.equals(â˜ƒ, â˜ƒxx) && Objects.equals(â˜ƒx, â˜ƒxxx)) {
         return this.fixTypeEverywhere(
            "BlockNameFlatteningFix",
            â˜ƒxx,
            â˜ƒxxx,
            var0 -> var0x -> var0x.mapSecond(
                     var0xx -> var0xx.map(BlockStateData::upgradeBlock, var0xxx -> BlockStateData.upgradeBlock(NamespacedSchema.ensureNamespaced(var0xxx)))
                  )
         );
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}
