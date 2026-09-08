package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class InventoryChangeTrigger extends SimpleCriterionTrigger<InventoryChangeTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("inventory_changed");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public InventoryChangeTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "slots", new JsonObject());
      MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("occupied"));
      MinMaxBounds.Ints â˜ƒxx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("full"));
      MinMaxBounds.Ints â˜ƒxxx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("empty"));
      ItemPredicate[] â˜ƒxxxx = ItemPredicate.fromJsonArray(â˜ƒ.get("items"));
      return new InventoryChangeTrigger.TriggerInstance(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   public void trigger(ServerPlayer var1, Inventory var2, ItemStack var3) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxx) {
         ItemStack â˜ƒxxxx = â˜ƒ.getItem(â˜ƒxxx);
         if (â˜ƒxxxx.isEmpty()) {
            ++â˜ƒx;
         } else {
            ++â˜ƒxx;
            if (â˜ƒxxxx.getCount() >= â˜ƒxxxx.getMaxStackSize()) {
               ++â˜ƒ;
            }
         }
      }

      this.trigger(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   private void trigger(ServerPlayer var1, Inventory var2, ItemStack var3, int var4, int var5, int var6) {
      this.trigger(â˜ƒ, var5x -> var5x.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints slotsOccupied;
      private final MinMaxBounds.Ints slotsFull;
      private final MinMaxBounds.Ints slotsEmpty;
      private final ItemPredicate[] predicates;

      public TriggerInstance(EntityPredicate.Composite var1, MinMaxBounds.Ints var2, MinMaxBounds.Ints var3, MinMaxBounds.Ints var4, ItemPredicate[] var5) {
         super(InventoryChangeTrigger.ID, â˜ƒ);
         this.slotsOccupied = â˜ƒ;
         this.slotsFull = â˜ƒ;
         this.slotsEmpty = â˜ƒ;
         this.predicates = â˜ƒ;
      }

      public static InventoryChangeTrigger.TriggerInstance hasItems(ItemPredicate... var0) {
         return new InventoryChangeTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, â˜ƒ
         );
      }

      public static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike... var0) {
         ItemPredicate[] â˜ƒ = new ItemPredicate[â˜ƒ.length];

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            â˜ƒ[â˜ƒx] = new ItemPredicate(
               null,
               ImmutableSet.of(â˜ƒ[â˜ƒx].asItem()),
               MinMaxBounds.Ints.ANY,
               MinMaxBounds.Ints.ANY,
               EnchantmentPredicate.NONE,
               EnchantmentPredicate.NONE,
               null,
               NbtPredicate.ANY
            );
         }

         return hasItems(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (!this.slotsOccupied.isAny() || !this.slotsFull.isAny() || !this.slotsEmpty.isAny()) {
            JsonObject â˜ƒx = new JsonObject();
            â˜ƒx.add("occupied", this.slotsOccupied.serializeToJson());
            â˜ƒx.add("full", this.slotsFull.serializeToJson());
            â˜ƒx.add("empty", this.slotsEmpty.serializeToJson());
            â˜ƒ.add("slots", â˜ƒx);
         }

         if (this.predicates.length > 0) {
            JsonArray â˜ƒ = new JsonArray();

            for(ItemPredicate â˜ƒx : this.predicates) {
               â˜ƒ.add(â˜ƒx.serializeToJson());
            }

            â˜ƒ.add("items", â˜ƒ);
         }

         return â˜ƒ;
      }

      public boolean matches(Inventory var1, ItemStack var2, int var3, int var4, int var5) {
         if (!this.slotsFull.matches(â˜ƒ)) {
            return false;
         } else if (!this.slotsEmpty.matches(â˜ƒ)) {
            return false;
         } else if (!this.slotsOccupied.matches(â˜ƒ)) {
            return false;
         } else {
            int â˜ƒ = this.predicates.length;
            if (â˜ƒ == 0) {
               return true;
            } else if (â˜ƒ != 1) {
               List<ItemPredicate> â˜ƒ = new ObjectArrayList<>(this.predicates);
               int â˜ƒx = â˜ƒ.getContainerSize();

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
                  if (â˜ƒ.isEmpty()) {
                     return true;
                  }

                  ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
                  if (!â˜ƒxxx.isEmpty()) {
                     â˜ƒ.removeIf(var1x -> var1x.matches(â˜ƒ));
                  }
               }

               return â˜ƒ.isEmpty();
            } else {
               return !â˜ƒ.isEmpty() && this.predicates[0].matches(â˜ƒ);
            }
         }
      }
   }
}
