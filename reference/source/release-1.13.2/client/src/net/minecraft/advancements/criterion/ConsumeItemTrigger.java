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
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ConsumeItemTrigger implements ICriterionTrigger<ConsumeItemTrigger.Instance> {
   private static final ResourceLocation field_193149_a = new ResourceLocation("consume_item");
   private final Map<PlayerAdvancements, ConsumeItemTrigger.Listeners> field_193150_b = Maps.<PlayerAdvancements, ConsumeItemTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193149_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> var2) {
      ConsumeItemTrigger.Listeners ☃ = (ConsumeItemTrigger.Listeners)this.field_193150_b.get(☃);
      if (☃ == null) {
         ☃ = new ConsumeItemTrigger.Listeners(☃);
         this.field_193150_b.put(☃, ☃);
      }

      ☃.func_193239_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> var2) {
      ConsumeItemTrigger.Listeners ☃ = (ConsumeItemTrigger.Listeners)this.field_193150_b.get(☃);
      if (☃ != null) {
         ☃.func_193237_b(☃);
         if (☃.func_193238_a()) {
            this.field_193150_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193150_b.remove(☃);
   }

   public ConsumeItemTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      return new ConsumeItemTrigger.Instance(ItemPredicate.func_192492_a(☃.get("item")));
   }

   public void func_193148_a(EntityPlayerMP var1, ItemStack var2) {
      ConsumeItemTrigger.Listeners ☃ = (ConsumeItemTrigger.Listeners)this.field_193150_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193240_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_193194_a;

      public Instance(ItemPredicate var1) {
         super(ConsumeItemTrigger.field_193149_a);
         this.field_193194_a = ☃;
      }

      public static ConsumeItemTrigger.Instance func_203914_c() {
         return new ConsumeItemTrigger.Instance(ItemPredicate.field_192495_a);
      }

      public static ConsumeItemTrigger.Instance func_203913_a(IItemProvider var0) {
         return new ConsumeItemTrigger.Instance(
            new ItemPredicate(
               null,
               ☃.func_199767_j(),
               MinMaxBounds.IntBound.field_211347_e,
               MinMaxBounds.IntBound.field_211347_e,
               new EnchantmentPredicate[0],
               null,
               NBTPredicate.field_193479_a
            )
         );
      }

      public boolean func_193193_a(ItemStack var1) {
         return this.field_193194_a.func_192493_a(☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_193194_a.func_200319_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193241_a;
      private final Set<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> field_193242_b = Sets.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193241_a = ☃;
      }

      public boolean func_193238_a() {
         return this.field_193242_b.isEmpty();
      }

      public void func_193239_a(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> var1) {
         this.field_193242_b.add(☃);
      }

      public void func_193237_b(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> var1) {
         this.field_193242_b.remove(☃);
      }

      public void func_193240_a(ItemStack var1) {
         List<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> ☃x : this.field_193242_b) {
            if (☃x.func_192158_a().func_193193_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193241_a);
            }
         }
      }
   }
}
