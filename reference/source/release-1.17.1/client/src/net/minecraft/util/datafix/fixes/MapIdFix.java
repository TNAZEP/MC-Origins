package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class MapIdFix extends DataFix {
   public MapIdFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.SAVED_DATA);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("data");
      return this.fixTypeEverywhereTyped("Map id fix", â˜ƒ, var1x -> {
         Optional<? extends Typed<?>> â˜ƒ = var1x.getOptionalTyped(â˜ƒ);
         return â˜ƒ.isPresent() ? var1x : var1x.update(DSL.remainderFinder(), var0x -> var0x.createMap(ImmutableMap.of(var0x.createString("data"), var0x)));
      });
   }
}
