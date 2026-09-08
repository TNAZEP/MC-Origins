package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EntityEquipmentToArmorAndHandFix extends DataFix {
   public EntityEquipmentToArmorAndHandFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.cap(this.getInputSchema().getTypeRaw(References.ITEM_STACK));
   }

   private <IS> TypeRewriteRule cap(Type<IS> var1) {
      Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> â˜ƒ = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(â˜ƒ))), DSL.remainderType());
      Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> â˜ƒx = DSL.and(
         DSL.optional(DSL.field("ArmorItems", DSL.list(â˜ƒ))), DSL.optional(DSL.field("HandItems", DSL.list(â˜ƒ))), DSL.remainderType()
      );
      OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> â˜ƒxx = DSL.typeFinder(â˜ƒ);
      OpticFinder<List<IS>> â˜ƒxxx = DSL.fieldFinder("Equipment", DSL.list(â˜ƒ));
      return this.fixTypeEverywhereTyped(
         "EntityEquipmentToArmorAndHandFix",
         this.getInputSchema().getType(References.ENTITY),
         this.getOutputSchema().getType(References.ENTITY),
         var4x -> {
            Either<List<IS>, Unit> â˜ƒ = Either.right(DSL.unit());
            Either<List<IS>, Unit> â˜ƒx = Either.right(DSL.unit());
            Dynamic<?> â˜ƒxx = var4x.getOrCreate(DSL.remainderFinder());
            Optional<List<IS>> â˜ƒxxx = var4x.getOptional(â˜ƒ);
            if (â˜ƒxxx.isPresent()) {
               List<IS> â˜ƒxxxx = (List)â˜ƒxxx.get();
               IS â˜ƒxxxxx = (IS)((Pair)â˜ƒ.read(â˜ƒxx.emptyMap())
                     .result()
                     .orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack.")))
                  .getFirst();
               if (!â˜ƒxxxx.isEmpty()) {
                  â˜ƒ = Either.left(Lists.<Object>newArrayList(â˜ƒxxxx.get(0), â˜ƒxxxxx));
               }
   
               if (â˜ƒxxxx.size() > 1) {
                  List<IS> â˜ƒxxxx = Lists.<IS>newArrayList(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx);
   
                  for(int â˜ƒxxxxx = 1; â˜ƒxxxxx < Math.min(â˜ƒxxxx.size(), 5); ++â˜ƒxxxxx) {
                     â˜ƒxxxx.set(â˜ƒxxxxx - 1, â˜ƒxxxx.get(â˜ƒxxxxx));
                  }
   
                  â˜ƒx = Either.left(â˜ƒxxxx);
               }
            }
   
            Dynamic<?> â˜ƒ = â˜ƒxx;
            Optional<? extends Stream<? extends Dynamic<?>>> â˜ƒx = â˜ƒxx.get("DropChances").asStreamOpt().result();
            if (â˜ƒx.isPresent()) {
               Iterator<? extends Dynamic<?>> â˜ƒxx = Stream.concat((Stream)â˜ƒx.get(), Stream.generate(() -> â˜ƒ.createInt(0))).iterator();
               float â˜ƒxxx = ((Dynamic)â˜ƒxx.next()).asFloat(0.0F);
               if (!â˜ƒxx.get("HandDropChances").result().isPresent()) {
                  Dynamic<?> â˜ƒxxxx = â˜ƒxx.createList(Stream.of(â˜ƒxxx, 0.0F).map(â˜ƒxx::createFloat));
                  â˜ƒxx = â˜ƒxx.set("HandDropChances", â˜ƒxxxx);
               }
   
               if (!â˜ƒxx.get("ArmorDropChances").result().isPresent()) {
                  Dynamic<?> â˜ƒxx = â˜ƒxx.createList(
                     Stream.of(
                           ((Dynamic)â˜ƒxx.next()).asFloat(0.0F),
                           ((Dynamic)â˜ƒxx.next()).asFloat(0.0F),
                           ((Dynamic)â˜ƒxx.next()).asFloat(0.0F),
                           ((Dynamic)â˜ƒxx.next()).asFloat(0.0F)
                        )
                        .map(â˜ƒxx::createFloat)
                  );
                  â˜ƒxx = â˜ƒxx.set("ArmorDropChances", â˜ƒxx);
               }
   
               â˜ƒxx = â˜ƒxx.remove("DropChances");
            }
   
            return var4x.set(â˜ƒ, â˜ƒ, Pair.of(â˜ƒ, Pair.of(â˜ƒx, â˜ƒxx)));
         }
      );
   }
}
