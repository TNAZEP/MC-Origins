package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class EntryGroup extends CompositeEntryBase {
   EntryGroup(LootPoolEntryContainer[] var1, LootItemCondition[] var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.GROUP;
   }

   @Override
   protected ComposableEntryContainer compose(ComposableEntryContainer[] var1) {
      switch(â˜ƒ.length) {
         case 0:
            return ALWAYS_TRUE;
         case 1:
            return â˜ƒ[0];
         case 2:
            ComposableEntryContainer â˜ƒ = â˜ƒ[0];
            ComposableEntryContainer â˜ƒx = â˜ƒ[1];
            return (var2x, var3x) -> {
               â˜ƒ.expand(var2x, var3x);
               â˜ƒ.expand(var2x, var3x);
               return true;
            };
         default:
            return (var1x, var2x) -> {
               for(ComposableEntryContainer â˜ƒ : â˜ƒ) {
                  â˜ƒ.expand(var1x, var2x);
               }

               return true;
            };
      }
   }

   public static EntryGroup.Builder list(LootPoolEntryContainer.Builder<?>... var0) {
      return new EntryGroup.Builder(â˜ƒ);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<EntryGroup.Builder> {
      private final List<LootPoolEntryContainer> entries = Lists.<LootPoolEntryContainer>newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... var1) {
         for(LootPoolEntryContainer.Builder<?> â˜ƒ : â˜ƒ) {
            this.entries.add(â˜ƒ.build());
         }
      }

      protected EntryGroup.Builder getThis() {
         return this;
      }

      @Override
      public EntryGroup.Builder append(LootPoolEntryContainer.Builder<?> var1) {
         this.entries.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootPoolEntryContainer build() {
         return new EntryGroup((LootPoolEntryContainer[])this.entries.toArray(new LootPoolEntryContainer[0]), this.getConditions());
      }
   }
}
