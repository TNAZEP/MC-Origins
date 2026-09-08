package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class PoiTypeRename extends DataFix {
   public PoiTypeRename(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> â˜ƒ = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
      if (!Objects.equals(â˜ƒ, this.getInputSchema().getType(References.POI_CHUNK))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rename", â˜ƒ, var1x -> var1xx -> var1xx.mapSecond(this::cap));
      }
   }

   private <T> Dynamic<T> cap(Dynamic<T> var1) {
      return â˜ƒ.update(
         "Sections",
         var1x -> var1x.updateMapValues(
               var1xx -> var1xx.mapSecond(var1xxx -> var1xxx.update("Records", var1xxxx -> DataFixUtils.orElse(this.renameRecords(var1xxxx), var1xxxx)))
            )
      );
   }

   private <T> Optional<Dynamic<T>> renameRecords(Dynamic<T> var1) {
      return â˜ƒ.asStreamOpt()
         .<Dynamic<T>>map(
            var2 -> â˜ƒ.createList(
                  var2.map(
                     var1x -> var1x.update(
                           "type", var1xx -> DataFixUtils.orElse(var1xx.asString().map(this::rename).map(var1xx::createString).result(), var1xx)
                        )
                  )
               )
         )
         .result();
   }

   protected abstract String rename(String var1);
}
