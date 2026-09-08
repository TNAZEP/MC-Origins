package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class RidingToPassengers extends DataFix {
   public RidingToPassengers(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Schema ☃ = this.getInputSchema();
      Schema ☃x = this.getOutputSchema();
      Type<?> ☃xx = ☃.getTypeRaw(TypeReferences.field_211298_n);
      Type<?> ☃xxx = ☃x.getTypeRaw(TypeReferences.field_211298_n);
      Type<?> ☃xxxx = ☃.getTypeRaw(TypeReferences.field_211299_o);
      return this.func_206340_a(☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
   }

   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule func_206340_a(
      Schema var1, Schema var2, Type<OldEntityTree> var3, Type<NewEntityTree> var4, Type<Entity> var5
   ) {
      Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> ☃ = DSL.named(
         TypeReferences.field_211298_n.typeName(), DSL.and(DSL.optional(DSL.field("Riding", ☃)), ☃)
      );
      Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> ☃x = DSL.named(
         TypeReferences.field_211298_n.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(☃))), ☃)
      );
      Type<?> ☃xx = ☃.getType(TypeReferences.field_211298_n);
      Type<?> ☃xxx = ☃.getType(TypeReferences.field_211298_n);
      if (!Objects.equals(☃xx, ☃)) {
         throw new IllegalStateException("Old entity type is not what was expected.");
      } else if (!☃xxx.equals(☃x, true, true)) {
         throw new IllegalStateException("New entity type is not what was expected.");
      } else {
         OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> ☃ = DSL.typeFinder(☃);
         OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> ☃x = DSL.typeFinder(☃x);
         OpticFinder<NewEntityTree> ☃xx = DSL.typeFinder(☃);
         Type<?> ☃xxx = ☃.getType(TypeReferences.field_211286_b);
         Type<?> ☃xxxx = ☃.getType(TypeReferences.field_211286_b);
         return TypeRewriteRule.seq(
            this.fixTypeEverywhere(
               "EntityRidingToPassengerFix",
               ☃,
               ☃x,
               var5x -> var6x -> {
                     Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> ☃ = Optional.empty();
                     Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> ☃x = var6x;
      
                     while(true) {
                        Either<List<NewEntityTree>, Unit> ☃xx = DataFixUtils.orElse(
                           ☃.map(
                              var4x -> {
                                 Typed<NewEntityTree> ☃ = (Typed)☃.pointTyped(var5x)
                                    .orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                                 NewEntityTree ☃x = (NewEntityTree)☃.set(☃, var4x)
                                    .getOptional(☃)
                                    .orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                                 return Either.left(ImmutableList.<NewEntityTree>of(☃x));
                              }
                           ),
                           Either.right(DSL.unit())
                        );
                        ☃ = Optional.of(Pair.of(TypeReferences.field_211298_n.typeName(), Pair.of(☃xx, ☃x.getSecond().getSecond())));
                        Optional<OldEntityTree> ☃xxx = ☃x.getSecond().getFirst().left();
                        if (!☃xxx.isPresent()) {
                           return (Pair)☃.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                        }
      
                        ☃x = (Pair)new Typed<>(☃, var5x, (OldEntityTree)☃xxx.get())
                           .getOptional(☃)
                           .orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
                     }
                  }
            ),
            this.writeAndRead("player RootVehicle injecter", ☃xxx, ☃xxxx)
         );
      }
   }
}
