package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractUUIDFix extends DataFix {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected TypeReference typeReference;

   public AbstractUUIDFix(Schema var1, TypeReference var2) {
      super(â˜ƒ, false);
      this.typeReference = â˜ƒ;
   }

   protected Typed<?> updateNamedChoice(Typed<?> var1, String var2, Function<Dynamic<?>, Dynamic<?>> var3) {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(this.typeReference, â˜ƒ);
      Type<?> â˜ƒx = this.getOutputSchema().getChoiceType(this.typeReference, â˜ƒ);
      return â˜ƒ.updateTyped(DSL.namedChoice(â˜ƒ, â˜ƒ), â˜ƒx, var1x -> var1x.update(DSL.remainderFinder(), â˜ƒ));
   }

   protected static Optional<Dynamic<?>> replaceUUIDString(Dynamic<?> var0, String var1, String var2) {
      return createUUIDFromString(â˜ƒ, â˜ƒ).map(var3 -> â˜ƒ.remove(â˜ƒ).set(â˜ƒ, var3));
   }

   protected static Optional<Dynamic<?>> replaceUUIDMLTag(Dynamic<?> var0, String var1, String var2) {
      return â˜ƒ.get(â˜ƒ).result().flatMap(AbstractUUIDFix::createUUIDFromML).map(var3 -> â˜ƒ.remove(â˜ƒ).set(â˜ƒ, var3));
   }

   protected static Optional<Dynamic<?>> replaceUUIDLeastMost(Dynamic<?> var0, String var1, String var2) {
      String â˜ƒ = â˜ƒ + "Most";
      String â˜ƒx = â˜ƒ + "Least";
      return createUUIDFromLongs(â˜ƒ, â˜ƒ, â˜ƒx).map(var4x -> â˜ƒ.remove(â˜ƒ).remove(â˜ƒ).set(â˜ƒ, var4x));
   }

   protected static Optional<Dynamic<?>> createUUIDFromString(Dynamic<?> var0, String var1) {
      return â˜ƒ.get(â˜ƒ).result().flatMap(var1x -> {
         String â˜ƒ = var1x.asString(null);
         if (â˜ƒ != null) {
            try {
               UUID â˜ƒx = UUID.fromString(â˜ƒ);
               return createUUIDTag(â˜ƒ, â˜ƒx.getMostSignificantBits(), â˜ƒx.getLeastSignificantBits());
            } catch (IllegalArgumentException var4) {
            }
         }

         return Optional.empty();
      });
   }

   protected static Optional<Dynamic<?>> createUUIDFromML(Dynamic<?> var0) {
      return createUUIDFromLongs(â˜ƒ, "M", "L");
   }

   protected static Optional<Dynamic<?>> createUUIDFromLongs(Dynamic<?> var0, String var1, String var2) {
      long â˜ƒ = â˜ƒ.get(â˜ƒ).asLong(0L);
      long â˜ƒx = â˜ƒ.get(â˜ƒ).asLong(0L);
      return â˜ƒ != 0L && â˜ƒx != 0L ? createUUIDTag(â˜ƒ, â˜ƒ, â˜ƒx) : Optional.empty();
   }

   protected static Optional<Dynamic<?>> createUUIDTag(Dynamic<?> var0, long var1, long var3) {
      return Optional.of(â˜ƒ.createIntList(Arrays.stream(new int[]{(int)(â˜ƒ >> 32), (int)â˜ƒ, (int)(â˜ƒ >> 32), (int)â˜ƒ})));
   }
}
