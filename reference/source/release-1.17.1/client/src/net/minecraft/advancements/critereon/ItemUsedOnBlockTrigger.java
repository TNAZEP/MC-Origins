package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUsedOnBlockTrigger extends SimpleCriterionTrigger<ItemUsedOnBlockTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("item_used_on_block");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public ItemUsedOnBlockTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      LocationPredicate â˜ƒ = LocationPredicate.fromJson(â˜ƒ.get("location"));
      ItemPredicate â˜ƒx = ItemPredicate.fromJson(â˜ƒ.get("item"));
      return new ItemUsedOnBlockTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, BlockPos var2, ItemStack var3) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ);
      this.trigger(â˜ƒ, var4x -> var4x.matches(â˜ƒ, â˜ƒ.getLevel(), â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate location;
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, LocationPredicate var2, ItemPredicate var3) {
         super(ItemUsedOnBlockTrigger.ID, â˜ƒ);
         this.location = â˜ƒ;
         this.item = â˜ƒ;
      }

      public static ItemUsedOnBlockTrigger.TriggerInstance itemUsedOnBlock(LocationPredicate.Builder var0, ItemPredicate.Builder var1) {
         return new ItemUsedOnBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ.build(), â˜ƒ.build());
      }

      public boolean matches(BlockState var1, ServerLevel var2, BlockPos var3, ItemStack var4) {
         return !this.location.matches(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5) ? false : this.item.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("location", this.location.serializeToJson());
         â˜ƒ.add("item", this.item.serializeToJson());
         return â˜ƒ;
      }
   }
}
