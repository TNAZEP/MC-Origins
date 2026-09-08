package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ConsumeItemTrigger extends SimpleCriterionTrigger<ConsumeItemTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("consume_item");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public ConsumeItemTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      return new ConsumeItemTrigger.TriggerInstance(â˜ƒ, ItemPredicate.fromJson(â˜ƒ.get("item")));
   }

   public void trigger(ServerPlayer var1, ItemStack var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2) {
         super(ConsumeItemTrigger.ID, â˜ƒ);
         this.item = â˜ƒ;
      }

      public static ConsumeItemTrigger.TriggerInstance usedItem() {
         return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY, ItemPredicate.ANY);
      }

      public static ConsumeItemTrigger.TriggerInstance usedItem(ItemPredicate var0) {
         return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ);
      }

      public static ConsumeItemTrigger.TriggerInstance usedItem(ItemLike var0) {
         return new ConsumeItemTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY,
            new ItemPredicate(
               null,
               ImmutableSet.of(â˜ƒ.asItem()),
               MinMaxBounds.Ints.ANY,
               MinMaxBounds.Ints.ANY,
               EnchantmentPredicate.NONE,
               EnchantmentPredicate.NONE,
               null,
               NbtPredicate.ANY
            )
         );
      }

      public boolean matches(ItemStack var1) {
         return this.item.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("item", this.item.serializeToJson());
         return â˜ƒ;
      }
   }
}
