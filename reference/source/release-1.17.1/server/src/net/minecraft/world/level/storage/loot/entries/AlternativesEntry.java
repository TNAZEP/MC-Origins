package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public class AlternativesEntry extends CompositeEntryBase {
   AlternativesEntry(LootPoolEntryContainer[] var1, LootItemCondition[] var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.ALTERNATIVES;
   }

   @Override
   protected ComposableEntryContainer compose(ComposableEntryContainer[] var1) {
      switch(â˜ƒ.length) {
         case 0:
            return ALWAYS_FALSE;
         case 1:
            return â˜ƒ[0];
         case 2:
            return â˜ƒ[0].or(â˜ƒ[1]);
         default:
            return (var1x, var2) -> {
               for(ComposableEntryContainer â˜ƒ : â˜ƒ) {
                  if (â˜ƒ.expand(var1x, var2)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }

   @Override
   public void validate(ValidationContext var1) {
      super.validate(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.children.length - 1; ++â˜ƒ) {
         if (ArrayUtils.isEmpty((Object[])this.children[â˜ƒ].conditions)) {
            â˜ƒ.reportProblem("Unreachable entry!");
         }
      }
   }

   public static AlternativesEntry.Builder alternatives(LootPoolEntryContainer.Builder<?>... var0) {
      return new AlternativesEntry.Builder(â˜ƒ);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<AlternativesEntry.Builder> {
      private final List<LootPoolEntryContainer> entries = Lists.<LootPoolEntryContainer>newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... var1) {
         for(LootPoolEntryContainer.Builder<?> â˜ƒ : â˜ƒ) {
            this.entries.add(â˜ƒ.build());
         }
      }

      protected AlternativesEntry.Builder getThis() {
         return this;
      }

      @Override
      public AlternativesEntry.Builder otherwise(LootPoolEntryContainer.Builder<?> var1) {
         this.entries.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootPoolEntryContainer build() {
         return new AlternativesEntry((LootPoolEntryContainer[])this.entries.toArray(new LootPoolEntryContainer[0]), this.getConditions());
      }
   }
}
