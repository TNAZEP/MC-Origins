package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class PistonPushedBlock extends NamedEntityFix {
   public PistonPushedBlock(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntityBlockStateFix", TypeReferences.field_211294_j, "minecraft:piston");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      Type<?> ☃ = this.getOutputSchema().getChoiceType(TypeReferences.field_211294_j, "minecraft:piston");
      Type<?> ☃x = ☃.findFieldType("blockState");
      OpticFinder<?> ☃xx = DSL.fieldFinder("blockState", ☃x);
      Dynamic<?> ☃xxx = ☃.get(DSL.remainderFinder());
      int ☃xxxx = ☃xxx.getInt("blockId");
      ☃xxx = ☃xxx.remove("blockId");
      int ☃xxxxx = ☃xxx.getInt("blockData") & 15;
      ☃xxx = ☃xxx.remove("blockData");
      Dynamic<?> ☃xxxxxx = BlockStateFlatteningMap.func_210049_b(☃xxxx << 4 | ☃xxxxx);
      Typed<?> ☃xxxxxxx = (Typed)☃.pointTyped(☃.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
      return ☃xxxxxxx.set(DSL.remainderFinder(), ☃xxx)
         .set(
            ☃xx,
            (Typed)((Optional)☃x.readTyped(☃xxxxxx).getSecond()).orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag."))
         );
   }
}
