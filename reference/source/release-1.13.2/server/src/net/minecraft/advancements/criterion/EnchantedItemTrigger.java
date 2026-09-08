package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantedItemTrigger implements ICriterionTrigger<EnchantedItemTrigger.Instance> {
   private static final ResourceLocation field_192191_a = new ResourceLocation("enchanted_item");
   private final Map<PlayerAdvancements, EnchantedItemTrigger.Listeners> field_192192_b = Maps.<PlayerAdvancements, EnchantedItemTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192191_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> var2) {
      EnchantedItemTrigger.Listeners ☃ = (EnchantedItemTrigger.Listeners)this.field_192192_b.get(☃);
      if (☃ == null) {
         ☃ = new EnchantedItemTrigger.Listeners(☃);
         this.field_192192_b.put(☃, ☃);
      }

      ☃.func_192460_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> var2) {
      EnchantedItemTrigger.Listeners ☃ = (EnchantedItemTrigger.Listeners)this.field_192192_b.get(☃);
      if (☃ != null) {
         ☃.func_192457_b(☃);
         if (☃.func_192458_a()) {
            this.field_192192_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192192_b.remove(☃);
   }

   public EnchantedItemTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ItemPredicate ☃ = ItemPredicate.func_192492_a(☃.get("item"));
      MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("levels"));
      return new EnchantedItemTrigger.Instance(☃, ☃x);
   }

   public void func_192190_a(EntityPlayerMP var1, ItemStack var2, int var3) {
      EnchantedItemTrigger.Listeners ☃ = (EnchantedItemTrigger.Listeners)this.field_192192_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192459_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_192258_a;
      private final MinMaxBounds.IntBound field_192259_b;

      public Instance(ItemPredicate var1, MinMaxBounds.IntBound var2) {
         super(EnchantedItemTrigger.field_192191_a);
         this.field_192258_a = ☃;
         this.field_192259_b = ☃;
      }

      public static EnchantedItemTrigger.Instance func_203918_c() {
         return new EnchantedItemTrigger.Instance(ItemPredicate.field_192495_a, MinMaxBounds.IntBound.field_211347_e);
      }

      public boolean func_192257_a(ItemStack var1, int var2) {
         if (!this.field_192258_a.func_192493_a(☃)) {
            return false;
         } else {
            return this.field_192259_b.func_211339_d(☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_192258_a.func_200319_a());
         ☃.add("levels", this.field_192259_b.func_200321_c());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192461_a;
      private final Set<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> field_192462_b = Sets.<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192461_a = ☃;
      }

      public boolean func_192458_a() {
         return this.field_192462_b.isEmpty();
      }

      public void func_192460_a(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> var1) {
         this.field_192462_b.add(☃);
      }

      public void func_192457_b(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> var1) {
         this.field_192462_b.remove(☃);
      }

      public void func_192459_a(ItemStack var1, int var2) {
         List<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> ☃x : this.field_192462_b) {
            if (☃x.func_192158_a().func_192257_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192461_a);
            }
         }
      }
   }
}
