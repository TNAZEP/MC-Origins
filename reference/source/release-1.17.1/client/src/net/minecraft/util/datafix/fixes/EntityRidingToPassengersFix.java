package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EntityRidingToPassengersFix extends DataFix {
   public EntityRidingToPassengersFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      Schema â˜ƒx = this.getOutputSchema();
      Type<?> â˜ƒxx = â˜ƒ.getTypeRaw(References.ENTITY_TREE);
      Type<?> â˜ƒxxx = â˜ƒx.getTypeRaw(References.ENTITY_TREE);
      Type<?> â˜ƒxxxx = â˜ƒ.getTypeRaw(References.ENTITY);
      return this.cap(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule cap(
      Schema var1, Schema var2, Type<OldEntityTree> var3, Type<NewEntityTree> var4, Type<Entity> var5
   ) {
      Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> â˜ƒ = DSL.named(
         References.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field("Riding", â˜ƒ)), â˜ƒ)
      );
      Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> â˜ƒx = DSL.named(
         References.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(â˜ƒ))), â˜ƒ)
      );
      Type<?> â˜ƒxx = â˜ƒ.getType(References.ENTITY_TREE);
      Type<?> â˜ƒxxx = â˜ƒ.getType(References.ENTITY_TREE);
      if (!Objects.equals(â˜ƒxx, â˜ƒ)) {
         throw new IllegalStateException("Old entity type is not what was expected.");
      } else if (!â˜ƒxxx.equals(â˜ƒx, true, true)) {
         throw new IllegalStateException("New entity type is not what was expected.");
      } else {
         OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> â˜ƒ = DSL.typeFinder(â˜ƒ);
         OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> â˜ƒx = DSL.typeFinder(â˜ƒx);
         OpticFinder<NewEntityTree> â˜ƒxx = DSL.typeFinder(â˜ƒ);
         Type<?> â˜ƒxxx = â˜ƒ.getType(References.PLAYER);
         Type<?> â˜ƒxxxx = â˜ƒ.getType(References.PLAYER);
         return TypeRewriteRule.seq(
            this.fixTypeEverywhere(
               "EntityRidingToPassengerFix",
               â˜ƒ,
               â˜ƒx,
               var5x -> var6x -> {
                     Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> â˜ƒ = Optional.empty();
                     Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> â˜ƒx = var6x;
      
                     while(true) {
                        Either<List<NewEntityTree>, Unit> â˜ƒxx = DataFixUtils.orElse(
                           â˜ƒ.map(
                              var4x -> {
                                 Typed<NewEntityTree> â˜ƒ = (Typed)â˜ƒ.pointTyped(var5x)
                                    .orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                                 NewEntityTree â˜ƒx = (NewEntityTree)â˜ƒ.set(â˜ƒ, var4x)
                                    .getOptional(â˜ƒ)
                                    .orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                                 return Either.left(ImmutableList.<NewEntityTree>of(â˜ƒx));
                              }
                           ),
                           Either.right(DSL.unit())
                        );
                        â˜ƒ = Optional.of(Pair.of(References.ENTITY_TREE.typeName(), Pair.of(â˜ƒxx, â˜ƒx.getSecond().getSecond())));
                        Optional<OldEntityTree> â˜ƒxxx = â˜ƒx.getSecond().getFirst().left();
                        if (!â˜ƒxxx.isPresent()) {
                           return (Pair)â˜ƒ.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                        }
      
                        â˜ƒx = (Pair)new Typed<>(â˜ƒ, var5x, (OldEntityTree)â˜ƒxxx.get())
                           .getOptional(â˜ƒ)
                           .orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
                     }
                  }
            ),
            this.writeAndRead("player RootVehicle injecter", â˜ƒxxx, â˜ƒxxxx)
         );
      }
   }
}
