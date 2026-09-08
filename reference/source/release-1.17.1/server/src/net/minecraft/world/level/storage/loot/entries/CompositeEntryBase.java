package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public abstract class CompositeEntryBase extends LootPoolEntryContainer {
   protected final LootPoolEntryContainer[] children;
   private final ComposableEntryContainer composedChildren;

   protected CompositeEntryBase(LootPoolEntryContainer[] var1, LootItemCondition[] var2) {
      super(â˜ƒ);
      this.children = â˜ƒ;
      this.composedChildren = this.compose(â˜ƒ);
   }

   @Override
   public void validate(ValidationContext var1) {
      super.validate(â˜ƒ);
      if (this.children.length == 0) {
         â˜ƒ.reportProblem("Empty children list");
      }

      for(int â˜ƒ = 0; â˜ƒ < this.children.length; ++â˜ƒ) {
         this.children[â˜ƒ].validate(â˜ƒ.forChild(".entry[" + â˜ƒ + "]"));
      }
   }

   protected abstract ComposableEntryContainer compose(ComposableEntryContainer[] var1);

   @Override
   public final boolean expand(LootContext var1, Consumer<LootPoolEntry> var2) {
      return !this.canRun(â˜ƒ) ? false : this.composedChildren.expand(â˜ƒ, â˜ƒ);
   }

   public static <T extends CompositeEntryBase> LootPoolEntryContainer.Serializer<T> createSerializer(
      final CompositeEntryBase.CompositeEntryConstructor<T> var0
   ) {
      return new LootPoolEntryContainer.Serializer<T>() {
         public void serializeCustom(JsonObject var1, T var2, JsonSerializationContext var3) {
            â˜ƒ.add("children", â˜ƒ.serialize(â˜ƒ.children));
         }

         public final T deserializeCustom(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
            LootPoolEntryContainer[] â˜ƒ = (LootPoolEntryContainer[])GsonHelper.getAsObject(â˜ƒ, "children", â˜ƒ, LootPoolEntryContainer[].class);
            return â˜ƒ.create(â˜ƒ, â˜ƒ);
         }
      };
   }

   @FunctionalInterface
   public interface CompositeEntryConstructor<T extends CompositeEntryBase> {
      T create(LootPoolEntryContainer[] var1, LootItemCondition[] var2);
   }
}
