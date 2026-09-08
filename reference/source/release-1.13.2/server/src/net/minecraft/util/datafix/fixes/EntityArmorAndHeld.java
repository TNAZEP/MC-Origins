package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class EntityArmorAndHeld extends DataFix {
   public EntityArmorAndHeld(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.func_206323_b(this.getInputSchema().getTypeRaw(TypeReferences.field_211295_k));
   }

   private <IS> TypeRewriteRule func_206323_b(Type<IS> var1) {
      Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> ☃ = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(☃))), DSL.remainderType());
      Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> ☃x = DSL.and(
         DSL.optional(DSL.field("ArmorItems", DSL.list(☃))), DSL.optional(DSL.field("HandItems", DSL.list(☃))), DSL.remainderType()
      );
      OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> ☃xx = DSL.typeFinder(☃);
      OpticFinder<List<IS>> ☃xxx = DSL.fieldFinder("Equipment", DSL.list(☃));
      return this.fixTypeEverywhereTyped(
         "EntityEquipmentToArmorAndHandFix",
         this.getInputSchema().getType(TypeReferences.field_211299_o),
         this.getOutputSchema().getType(TypeReferences.field_211299_o),
         var4x -> {
            Either<List<IS>, Unit> ☃ = Either.right(DSL.unit());
            Either<List<IS>, Unit> ☃x = Either.right(DSL.unit());
            Dynamic<?> ☃xx = var4x.getOrCreate(DSL.remainderFinder());
            Optional<List<IS>> ☃xxx = var4x.getOptional(☃);
            if (☃xxx.isPresent()) {
               List<IS> ☃xxxx = (List)☃xxx.get();
               IS ☃xxxxx = (IS)((Optional)☃.read(☃xx.emptyMap()).getSecond())
                  .orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack."));
               if (!☃xxxx.isEmpty()) {
                  ☃ = Either.left(Lists.<Object>newArrayList(☃xxxx.get(0), ☃xxxxx));
               }
   
               if (☃xxxx.size() > 1) {
                  List<IS> ☃xxxx = Lists.<IS>newArrayList(☃xxxxx, ☃xxxxx, ☃xxxxx, ☃xxxxx);
   
                  for(int ☃xxxxx = 1; ☃xxxxx < Math.min(☃xxxx.size(), 5); ++☃xxxxx) {
                     ☃xxxx.set(☃xxxxx - 1, ☃xxxx.get(☃xxxxx));
                  }
   
                  ☃x = Either.left(☃xxxx);
               }
            }
   
            Dynamic<?> ☃ = ☃xx;
            Optional<? extends Stream<? extends Dynamic<?>>> ☃x = ☃xx.get("DropChances").flatMap(Dynamic::getStream);
            if (☃x.isPresent()) {
               Iterator<? extends Dynamic<?>> ☃xx = Stream.concat((Stream)☃x.get(), Stream.generate(() -> ☃.createInt(0))).iterator();
               float ☃xxx = ((Dynamic)☃xx.next()).getNumberValue(0).floatValue();
               if (!☃xx.get("HandDropChances").isPresent()) {
                  Dynamic<?> ☃xxxx = ☃xx.emptyMap().merge(☃xx.createFloat(☃xxx)).merge(☃xx.createFloat(0.0F));
                  ☃xx = ☃xx.set("HandDropChances", ☃xxxx);
               }
   
               if (!☃xx.get("ArmorDropChances").isPresent()) {
                  Dynamic<?> ☃xx = ☃xx.emptyMap()
                     .merge(☃xx.createFloat(((Dynamic)☃xx.next()).getNumberValue(0).floatValue()))
                     .merge(☃xx.createFloat(((Dynamic)☃xx.next()).getNumberValue(0).floatValue()))
                     .merge(☃xx.createFloat(((Dynamic)☃xx.next()).getNumberValue(0).floatValue()))
                     .merge(☃xx.createFloat(((Dynamic)☃xx.next()).getNumberValue(0).floatValue()));
                  ☃xx = ☃xx.set("ArmorDropChances", ☃xx);
               }
   
               ☃xx = ☃xx.remove("DropChances");
            }
   
            return var4x.set(☃, ☃, Pair.of(☃, Pair.of(☃x, ☃xx)));
         }
      );
   }
}
