package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class CauldronRenameFix extends DataFix {
   public CauldronRenameFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private static Dynamic<?> fix(Dynamic<?> var0) {
      Optional<String> â˜ƒ = â˜ƒ.get("Name").asString().result();
      if (â˜ƒ.equals(Optional.of("minecraft:cauldron"))) {
         Dynamic<?> â˜ƒx = â˜ƒ.get("Properties").orElseEmptyMap();
         return â˜ƒx.get("level").asString("0").equals("0") ? â˜ƒ.remove("Properties") : â˜ƒ.set("Name", â˜ƒ.createString("minecraft:water_cauldron"));
      } else {
         return â˜ƒ;
      }
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "cauldron_rename_fix", this.getInputSchema().getType(References.BLOCK_STATE), var0 -> var0.update(DSL.remainderFinder(), CauldronRenameFix::fix)
      );
   }
}
