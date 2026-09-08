package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class BlockStateFlattenStructures extends DataFix {
   public BlockStateFlattenStructures(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "BlockStateStructureTemplateFix",
         this.getInputSchema().getType(TypeReferences.field_211296_l),
         var0 -> var0.update(DSL.remainderFinder(), BlockStateFlatteningMap::func_210050_a)
      );
   }
}
