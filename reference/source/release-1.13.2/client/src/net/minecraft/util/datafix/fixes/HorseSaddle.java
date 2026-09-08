package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class HorseSaddle extends NamedEntityFix {
   public HorseSaddle(Schema var1, boolean var2) {
      super(☃, ☃, "EntityHorseSaddleFix", TypeReferences.field_211299_o, "EntityHorse");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      OpticFinder<Pair<String, String>> ☃ = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      Type<?> ☃x = this.getInputSchema().getTypeRaw(TypeReferences.field_211295_k);
      OpticFinder<?> ☃xx = DSL.fieldFinder("SaddleItem", ☃x);
      Optional<? extends Typed<?>> ☃xxx = ☃.getOptionalTyped(☃xx);
      Dynamic<?> ☃xxxx = ☃.get(DSL.remainderFinder());
      if (!☃xxx.isPresent() && ☃xxxx.getBoolean("Saddle")) {
         Typed<?> ☃xxxxx = (Typed)☃x.pointTyped(☃.getOps()).orElseThrow(IllegalStateException::new);
         ☃xxxxx = ☃xxxxx.set(☃, Pair.of(TypeReferences.field_211301_q.typeName(), "minecraft:saddle"));
         Dynamic<?> ☃xxxxxx = ☃xxxx.emptyMap();
         ☃xxxxxx = ☃xxxxxx.set("Count", ☃xxxxxx.createByte((byte)1));
         ☃xxxxxx = ☃xxxxxx.set("Damage", ☃xxxxxx.createShort((short)0));
         ☃xxxxx = ☃xxxxx.set(DSL.remainderFinder(), ☃xxxxxx);
         ☃xxxx.remove("Saddle");
         return ☃.set(☃xx, ☃xxxxx).set(DSL.remainderFinder(), ☃xxxx);
      } else {
         return ☃;
      }
   }
}
