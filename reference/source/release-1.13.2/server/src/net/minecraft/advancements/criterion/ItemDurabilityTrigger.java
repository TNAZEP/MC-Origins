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

public class ItemDurabilityTrigger implements ICriterionTrigger<ItemDurabilityTrigger.Instance> {
   private static final ResourceLocation field_193159_a = new ResourceLocation("item_durability_changed");
   private final Map<PlayerAdvancements, ItemDurabilityTrigger.Listeners> field_193160_b = Maps.<PlayerAdvancements, ItemDurabilityTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_193159_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> var2) {
      ItemDurabilityTrigger.Listeners ☃ = (ItemDurabilityTrigger.Listeners)this.field_193160_b.get(☃);
      if (☃ == null) {
         ☃ = new ItemDurabilityTrigger.Listeners(☃);
         this.field_193160_b.put(☃, ☃);
      }

      ☃.func_193440_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> var2) {
      ItemDurabilityTrigger.Listeners ☃ = (ItemDurabilityTrigger.Listeners)this.field_193160_b.get(☃);
      if (☃ != null) {
         ☃.func_193438_b(☃);
         if (☃.func_193439_a()) {
            this.field_193160_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193160_b.remove(☃);
   }

   public ItemDurabilityTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ItemPredicate ☃ = ItemPredicate.func_192492_a(☃.get("item"));
      MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("durability"));
      MinMaxBounds.IntBound ☃xx = MinMaxBounds.IntBound.func_211344_a(☃.get("delta"));
      return new ItemDurabilityTrigger.Instance(☃, ☃x, ☃xx);
   }

   public void func_193158_a(EntityPlayerMP var1, ItemStack var2, int var3) {
      ItemDurabilityTrigger.Listeners ☃ = (ItemDurabilityTrigger.Listeners)this.field_193160_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193441_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_193198_a;
      private final MinMaxBounds.IntBound field_193199_b;
      private final MinMaxBounds.IntBound field_193200_c;

      public Instance(ItemPredicate var1, MinMaxBounds.IntBound var2, MinMaxBounds.IntBound var3) {
         super(ItemDurabilityTrigger.field_193159_a);
         this.field_193198_a = ☃;
         this.field_193199_b = ☃;
         this.field_193200_c = ☃;
      }

      public static ItemDurabilityTrigger.Instance func_211182_a(ItemPredicate var0, MinMaxBounds.IntBound var1) {
         return new ItemDurabilityTrigger.Instance(☃, ☃, MinMaxBounds.IntBound.field_211347_e);
      }

      public boolean func_193197_a(ItemStack var1, int var2) {
         if (!this.field_193198_a.func_192493_a(☃)) {
            return false;
         } else if (!this.field_193199_b.func_211339_d(☃.func_77958_k() - ☃)) {
            return false;
         } else {
            return this.field_193200_c.func_211339_d(☃.func_77952_i() - ☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_193198_a.func_200319_a());
         ☃.add("durability", this.field_193199_b.func_200321_c());
         ☃.add("delta", this.field_193200_c.func_200321_c());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193442_a;
      private final Set<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>> field_193443_b = Sets.<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193442_a = ☃;
      }

      public boolean func_193439_a() {
         return this.field_193443_b.isEmpty();
      }

      public void func_193440_a(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> var1) {
         this.field_193443_b.add(☃);
      }

      public void func_193438_b(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> var1) {
         this.field_193443_b.remove(☃);
      }

      public void func_193441_a(ItemStack var1, int var2) {
         List<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> ☃x : this.field_193443_b) {
            if (☃x.func_192158_a().func_193197_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193442_a);
            }
         }
      }
   }
}
