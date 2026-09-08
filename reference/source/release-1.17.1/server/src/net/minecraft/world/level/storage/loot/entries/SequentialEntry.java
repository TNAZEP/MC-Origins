package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SequentialEntry extends CompositeEntryBase {
   SequentialEntry(LootPoolEntryContainer[] var1, LootItemCondition[] var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.SEQUENCE;
   }

   @Override
   protected ComposableEntryContainer compose(ComposableEntryContainer[] var1) {
      switch(â˜ƒ.length) {
         case 0:
            return ALWAYS_TRUE;
         case 1:
            return â˜ƒ[0];
         case 2:
            return â˜ƒ[0].and(â˜ƒ[1]);
         default:
            return (var1x, var2) -> {
               for(ComposableEntryContainer â˜ƒ : â˜ƒ) {
                  if (!â˜ƒ.expand(var1x, var2)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }

   public static SequentialEntry.Builder sequential(LootPoolEntryContainer.Builder<?>... var0) {
      return new SequentialEntry.Builder(â˜ƒ);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<SequentialEntry.Builder> {
      private final List<LootPoolEntryContainer> entries = Lists.<LootPoolEntryContainer>newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... var1) {
         for(LootPoolEntryContainer.Builder<?> â˜ƒ : â˜ƒ) {
            this.entries.add(â˜ƒ.build());
         }
      }

      protected SequentialEntry.Builder getThis() {
         return this;
      }

      @Override
      public SequentialEntry.Builder then(LootPoolEntryContainer.Builder<?> var1) {
         this.entries.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootPoolEntryContainer build() {
         return new SequentialEntry((LootPoolEntryContainer[])this.entries.toArray(new LootPoolEntryContainer[0]), this.getConditions());
      }
   }
}
