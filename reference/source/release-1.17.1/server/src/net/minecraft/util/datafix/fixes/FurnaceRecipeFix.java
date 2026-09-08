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
import java.util.List;
import java.util.Optional;

public class FurnaceRecipeFix extends DataFix {
   public FurnaceRecipeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.cap(this.getOutputSchema().getTypeRaw(References.RECIPE));
   }

   private <R> TypeRewriteRule cap(Type<R> var1) {
      Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> â˜ƒ = DSL.and(
         DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(â˜ƒ, DSL.intType()), DSL.remainderType()))), DSL.remainderType()
      );
      OpticFinder<?> â˜ƒx = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:furnace"));
      OpticFinder<?> â˜ƒxx = DSL.namedChoice("minecraft:blast_furnace", this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:blast_furnace"));
      OpticFinder<?> â˜ƒxxx = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:smoker"));
      Type<?> â˜ƒxxxx = this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:furnace");
      Type<?> â˜ƒxxxxx = this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:blast_furnace");
      Type<?> â˜ƒxxxxxx = this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:smoker");
      Type<?> â˜ƒxxxxxxx = this.getInputSchema().getType(References.BLOCK_ENTITY);
      Type<?> â˜ƒxxxxxxxx = this.getOutputSchema().getType(References.BLOCK_ENTITY);
      return this.fixTypeEverywhereTyped(
         "FurnaceRecipesFix",
         â˜ƒxxxxxxx,
         â˜ƒxxxxxxxx,
         var9x -> var9x.updateTyped(â˜ƒ, â˜ƒ, var3x -> this.updateFurnaceContents(â˜ƒ, â˜ƒ, var3x))
               .updateTyped(â˜ƒ, â˜ƒ, var3x -> this.updateFurnaceContents(â˜ƒ, â˜ƒ, var3x))
               .updateTyped(â˜ƒ, â˜ƒ, var3x -> this.updateFurnaceContents(â˜ƒ, â˜ƒ, var3x))
      );
   }

   private <R> Typed<?> updateFurnaceContents(Type<R> var1, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> var2, Typed<?> var3) {
      Dynamic<?> â˜ƒ = â˜ƒ.getOrCreate(DSL.remainderFinder());
      int â˜ƒx = â˜ƒ.get("RecipesUsedSize").asInt(0);
      â˜ƒ = â˜ƒ.remove("RecipesUsedSize");
      List<Pair<R, Integer>> â˜ƒxx = Lists.<Pair<R, Integer>>newArrayList();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx; ++â˜ƒxxx) {
         String â˜ƒxxxx = "RecipeLocation" + â˜ƒxxx;
         String â˜ƒxxxxx = "RecipeAmount" + â˜ƒxxx;
         Optional<? extends Dynamic<?>> â˜ƒxxxxxx = â˜ƒ.get(â˜ƒxxxx).result();
         int â˜ƒxxxxxxx = â˜ƒ.get(â˜ƒxxxxx).asInt(0);
         if (â˜ƒxxxxxxx > 0) {
            â˜ƒxxxxxx.ifPresent(var3x -> {
               Optional<? extends Pair<R, ? extends Dynamic<?>>> â˜ƒ = â˜ƒ.read(var3x).result();
               â˜ƒ.ifPresent(var2x -> â˜ƒ.add(Pair.of(var2x.getFirst(), â˜ƒ)));
            });
         }

         â˜ƒ = â˜ƒ.remove(â˜ƒxxxx).remove(â˜ƒxxxxx);
      }

      return â˜ƒ.set(DSL.remainderFinder(), â˜ƒ, Pair.of(Either.left(Pair.of(â˜ƒxx, â˜ƒ.emptyMap())), â˜ƒ));
   }
}
