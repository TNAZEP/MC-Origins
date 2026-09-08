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
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class VillagerTradeTrigger implements ICriterionTrigger<VillagerTradeTrigger.Instance> {
   private static final ResourceLocation field_192237_a = new ResourceLocation("villager_trade");
   private final Map<PlayerAdvancements, VillagerTradeTrigger.Listeners> field_192238_b = Maps.<PlayerAdvancements, VillagerTradeTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192237_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> var2) {
      VillagerTradeTrigger.Listeners ☃ = (VillagerTradeTrigger.Listeners)this.field_192238_b.get(☃);
      if (☃ == null) {
         ☃ = new VillagerTradeTrigger.Listeners(☃);
         this.field_192238_b.put(☃, ☃);
      }

      ☃.func_192540_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> var2) {
      VillagerTradeTrigger.Listeners ☃ = (VillagerTradeTrigger.Listeners)this.field_192238_b.get(☃);
      if (☃ != null) {
         ☃.func_192538_b(☃);
         if (☃.func_192539_a()) {
            this.field_192238_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192238_b.remove(☃);
   }

   public VillagerTradeTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate ☃ = EntityPredicate.func_192481_a(☃.get("villager"));
      ItemPredicate ☃x = ItemPredicate.func_192492_a(☃.get("item"));
      return new VillagerTradeTrigger.Instance(☃, ☃x);
   }

   public void func_192234_a(EntityPlayerMP var1, EntityVillager var2, ItemStack var3) {
      VillagerTradeTrigger.Listeners ☃ = (VillagerTradeTrigger.Listeners)this.field_192238_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192537_a(☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192286_a;
      private final ItemPredicate field_192287_b;

      public Instance(EntityPredicate var1, ItemPredicate var2) {
         super(VillagerTradeTrigger.field_192237_a);
         this.field_192286_a = ☃;
         this.field_192287_b = ☃;
      }

      public static VillagerTradeTrigger.Instance func_203939_c() {
         return new VillagerTradeTrigger.Instance(EntityPredicate.field_192483_a, ItemPredicate.field_192495_a);
      }

      public boolean func_192285_a(EntityPlayerMP var1, EntityVillager var2, ItemStack var3) {
         if (!this.field_192286_a.func_192482_a(☃, ☃)) {
            return false;
         } else {
            return this.field_192287_b.func_192493_a(☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_192287_b.func_200319_a());
         ☃.add("villager", this.field_192286_a.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192541_a;
      private final Set<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> field_192542_b = Sets.<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192541_a = ☃;
      }

      public boolean func_192539_a() {
         return this.field_192542_b.isEmpty();
      }

      public void func_192540_a(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> var1) {
         this.field_192542_b.add(☃);
      }

      public void func_192538_b(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> var1) {
         this.field_192542_b.remove(☃);
      }

      public void func_192537_a(EntityPlayerMP var1, EntityVillager var2, ItemStack var3) {
         List<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> ☃x : this.field_192542_b) {
            if (☃x.func_192158_a().func_192285_a(☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192541_a);
            }
         }
      }
   }
}
