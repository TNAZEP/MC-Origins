package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class BlockEntityBlockStateFix extends NamedEntityFix {
   public BlockEntityBlockStateFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "BlockEntityBlockStateFix", References.BLOCK_ENTITY, "minecraft:piston");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      Type<?> â˜ƒ = this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:piston");
      Type<?> â˜ƒx = â˜ƒ.findFieldType("blockState");
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("blockState", â˜ƒx);
      Dynamic<?> â˜ƒxxx = â˜ƒ.get(DSL.remainderFinder());
      int â˜ƒxxxx = â˜ƒxxx.get("blockId").asInt(0);
      â˜ƒxxx = â˜ƒxxx.remove("blockId");
      int â˜ƒxxxxx = â˜ƒxxx.get("blockData").asInt(0) & 15;
      â˜ƒxxx = â˜ƒxxx.remove("blockData");
      Dynamic<?> â˜ƒxxxxxx = BlockStateData.getTag(â˜ƒxxxx << 4 | â˜ƒxxxxx);
      Typed<?> â˜ƒxxxxxxx = (Typed)â˜ƒ.pointTyped(â˜ƒ.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
      return â˜ƒxxxxxxx.set(DSL.remainderFinder(), â˜ƒxxx)
         .set(
            â˜ƒxx,
            (Typed)((Pair)â˜ƒx.readTyped(â˜ƒxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag.")))
               .getFirst()
         );
   }
}
