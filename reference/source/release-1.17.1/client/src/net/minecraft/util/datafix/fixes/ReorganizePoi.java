package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ReorganizePoi extends DataFix {
   public ReorganizePoi(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> â˜ƒ = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
      if (!Objects.equals(â˜ƒ, this.getInputSchema().getType(References.POI_CHUNK))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI reorganization", â˜ƒ, var0 -> var0x -> var0x.mapSecond(ReorganizePoi::cap));
      }
   }

   private static <T> Dynamic<T> cap(Dynamic<T> var0) {
      Map<Dynamic<T>, Dynamic<T>> â˜ƒ = Maps.<Dynamic<T>, Dynamic<T>>newHashMap();

      for(int â˜ƒx = 0; â˜ƒx < 16; ++â˜ƒx) {
         String â˜ƒxx = String.valueOf(â˜ƒx);
         Optional<Dynamic<T>> â˜ƒxxx = â˜ƒ.get(â˜ƒxx).result();
         if (â˜ƒxxx.isPresent()) {
            Dynamic<T> â˜ƒxxxx = (Dynamic)â˜ƒxxx.get();
            Dynamic<T> â˜ƒxxxxx = â˜ƒ.createMap(ImmutableMap.of(â˜ƒ.createString("Records"), â˜ƒxxxx));
            â˜ƒ.put(â˜ƒ.createInt(â˜ƒx), â˜ƒxxxxx);
            â˜ƒ = â˜ƒ.remove(â˜ƒxx);
         }
      }

      return â˜ƒ.set("Sections", â˜ƒ.createMap(â˜ƒ));
   }
}
