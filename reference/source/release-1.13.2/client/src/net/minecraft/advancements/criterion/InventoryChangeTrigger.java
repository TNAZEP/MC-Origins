package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class InventoryChangeTrigger implements ICriterionTrigger<InventoryChangeTrigger.Instance> {
   private static final ResourceLocation field_192209_a = new ResourceLocation("inventory_changed");
   private final Map<PlayerAdvancements, InventoryChangeTrigger.Listeners> field_192210_b = Maps.<PlayerAdvancements, InventoryChangeTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192209_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> var2) {
      InventoryChangeTrigger.Listeners ☃ = (InventoryChangeTrigger.Listeners)this.field_192210_b.get(☃);
      if (☃ == null) {
         ☃ = new InventoryChangeTrigger.Listeners(☃);
         this.field_192210_b.put(☃, ☃);
      }

      ☃.func_192489_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> var2) {
      InventoryChangeTrigger.Listeners ☃ = (InventoryChangeTrigger.Listeners)this.field_192210_b.get(☃);
      if (☃ != null) {
         ☃.func_192487_b(☃);
         if (☃.func_192488_a()) {
            this.field_192210_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192210_b.remove(☃);
   }

   public InventoryChangeTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      JsonObject ☃ = JsonUtils.func_151218_a(☃, "slots", new JsonObject());
      MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("occupied"));
      MinMaxBounds.IntBound ☃xx = MinMaxBounds.IntBound.func_211344_a(☃.get("full"));
      MinMaxBounds.IntBound ☃xxx = MinMaxBounds.IntBound.func_211344_a(☃.get("empty"));
      ItemPredicate[] ☃xxxx = ItemPredicate.func_192494_b(☃.get("items"));
      return new InventoryChangeTrigger.Instance(☃x, ☃xx, ☃xxx, ☃xxxx);
   }

   public void func_192208_a(EntityPlayerMP var1, InventoryPlayer var2) {
      InventoryChangeTrigger.Listeners ☃ = (InventoryChangeTrigger.Listeners)this.field_192210_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192486_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final MinMaxBounds.IntBound field_192266_a;
      private final MinMaxBounds.IntBound field_192267_b;
      private final MinMaxBounds.IntBound field_192268_c;
      private final ItemPredicate[] field_192269_d;

      public Instance(MinMaxBounds.IntBound var1, MinMaxBounds.IntBound var2, MinMaxBounds.IntBound var3, ItemPredicate[] var4) {
         super(InventoryChangeTrigger.field_192209_a);
         this.field_192266_a = ☃;
         this.field_192267_b = ☃;
         this.field_192268_c = ☃;
         this.field_192269_d = ☃;
      }

      public static InventoryChangeTrigger.Instance func_203923_a(ItemPredicate... var0) {
         return new InventoryChangeTrigger.Instance(
            MinMaxBounds.IntBound.field_211347_e, MinMaxBounds.IntBound.field_211347_e, MinMaxBounds.IntBound.field_211347_e, ☃
         );
      }

      public static InventoryChangeTrigger.Instance func_203922_a(IItemProvider... var0) {
         ItemPredicate[] ☃ = new ItemPredicate[☃.length];

         for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
            ☃[☃x] = new ItemPredicate(
               null,
               ☃[☃x].func_199767_j(),
               MinMaxBounds.IntBound.field_211347_e,
               MinMaxBounds.IntBound.field_211347_e,
               new EnchantmentPredicate[0],
               null,
               NBTPredicate.field_193479_a
            );
         }

         return func_203923_a(☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         if (!this.field_192266_a.func_211335_c() || !this.field_192267_b.func_211335_c() || !this.field_192268_c.func_211335_c()) {
            JsonObject ☃x = new JsonObject();
            ☃x.add("occupied", this.field_192266_a.func_200321_c());
            ☃x.add("full", this.field_192267_b.func_200321_c());
            ☃x.add("empty", this.field_192268_c.func_200321_c());
            ☃.add("slots", ☃x);
         }

         if (this.field_192269_d.length > 0) {
            JsonArray ☃ = new JsonArray();

            for(ItemPredicate ☃x : this.field_192269_d) {
               ☃.add(☃x.func_200319_a());
            }

            ☃.add("items", ☃);
         }

         return ☃;
      }

      public boolean func_192265_a(InventoryPlayer var1) {
         int ☃ = 0;
         int ☃x = 0;
         int ☃xx = 0;
         List<ItemPredicate> ☃xxx = Lists.<ItemPredicate>newArrayList(this.field_192269_d);

         for(int ☃xxxx = 0; ☃xxxx < ☃.func_70302_i_(); ++☃xxxx) {
            ItemStack ☃xxxxx = ☃.func_70301_a(☃xxxx);
            if (☃xxxxx.func_190926_b()) {
               ++☃x;
            } else {
               ++☃xx;
               if (☃xxxxx.func_190916_E() >= ☃xxxxx.func_77976_d()) {
                  ++☃;
               }

               Iterator<ItemPredicate> ☃xxxxx = ☃xxx.iterator();

               while(☃xxxxx.hasNext()) {
                  ItemPredicate ☃xxxxxx = (ItemPredicate)☃xxxxx.next();
                  if (☃xxxxxx.func_192493_a(☃xxxxx)) {
                     ☃xxxxx.remove();
                  }
               }
            }
         }

         if (!this.field_192267_b.func_211339_d(☃)) {
            return false;
         } else if (!this.field_192268_c.func_211339_d(☃x)) {
            return false;
         } else if (!this.field_192266_a.func_211339_d(☃xx)) {
            return false;
         } else {
            return ☃xxx.isEmpty();
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192490_a;
      private final Set<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> field_192491_b = Sets.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192490_a = ☃;
      }

      public boolean func_192488_a() {
         return this.field_192491_b.isEmpty();
      }

      public void func_192489_a(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> var1) {
         this.field_192491_b.add(☃);
      }

      public void func_192487_b(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> var1) {
         this.field_192491_b.remove(☃);
      }

      public void func_192486_a(InventoryPlayer var1) {
         List<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> ☃x : this.field_192491_b) {
            if (☃x.func_192158_a().func_192265_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192490_a);
            }
         }
      }
   }
}
