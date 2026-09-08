package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FishingRodHookedTrigger implements ICriterionTrigger<FishingRodHookedTrigger.Instance> {
   private static final ResourceLocation field_204821_a = new ResourceLocation("fishing_rod_hooked");
   private final Map<PlayerAdvancements, FishingRodHookedTrigger.Listeners> field_204822_b = Maps.<PlayerAdvancements, FishingRodHookedTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_204821_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> var2) {
      FishingRodHookedTrigger.Listeners ☃ = (FishingRodHookedTrigger.Listeners)this.field_204822_b.get(☃);
      if (☃ == null) {
         ☃ = new FishingRodHookedTrigger.Listeners(☃);
         this.field_204822_b.put(☃, ☃);
      }

      ☃.func_204858_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> var2) {
      FishingRodHookedTrigger.Listeners ☃ = (FishingRodHookedTrigger.Listeners)this.field_204822_b.get(☃);
      if (☃ != null) {
         ☃.func_204861_b(☃);
         if (☃.func_204860_a()) {
            this.field_204822_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_204822_b.remove(☃);
   }

   public FishingRodHookedTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ItemPredicate ☃ = ItemPredicate.func_192492_a(☃.get("rod"));
      EntityPredicate ☃x = EntityPredicate.func_192481_a(☃.get("entity"));
      ItemPredicate ☃xx = ItemPredicate.func_192492_a(☃.get("item"));
      return new FishingRodHookedTrigger.Instance(☃, ☃x, ☃xx);
   }

   public void func_204820_a(EntityPlayerMP var1, ItemStack var2, EntityFishHook var3, Collection<ItemStack> var4) {
      FishingRodHookedTrigger.Listeners ☃ = (FishingRodHookedTrigger.Listeners)this.field_204822_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_204859_a(☃, ☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_204831_a;
      private final EntityPredicate field_204832_b;
      private final ItemPredicate field_204833_c;

      public Instance(ItemPredicate var1, EntityPredicate var2, ItemPredicate var3) {
         super(FishingRodHookedTrigger.field_204821_a);
         this.field_204831_a = ☃;
         this.field_204832_b = ☃;
         this.field_204833_c = ☃;
      }

      public static FishingRodHookedTrigger.Instance func_204829_a(ItemPredicate var0, EntityPredicate var1, ItemPredicate var2) {
         return new FishingRodHookedTrigger.Instance(☃, ☃, ☃);
      }

      public boolean func_204830_a(EntityPlayerMP var1, ItemStack var2, EntityFishHook var3, Collection<ItemStack> var4) {
         if (!this.field_204831_a.func_192493_a(☃)) {
            return false;
         } else if (!this.field_204832_b.func_192482_a(☃, ☃.field_146043_c)) {
            return false;
         } else {
            if (this.field_204833_c != ItemPredicate.field_192495_a) {
               boolean ☃ = false;
               if (☃.field_146043_c instanceof EntityItem) {
                  EntityItem ☃x = (EntityItem)☃.field_146043_c;
                  if (this.field_204833_c.func_192493_a(☃x.func_92059_d())) {
                     ☃ = true;
                  }
               }

               for(ItemStack ☃ : ☃) {
                  if (this.field_204833_c.func_192493_a(☃)) {
                     ☃ = true;
                     break;
                  }
               }

               if (!☃) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("rod", this.field_204831_a.func_200319_a());
         ☃.add("entity", this.field_204832_b.func_204006_a());
         ☃.add("item", this.field_204833_c.func_200319_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_204862_a;
      private final Set<ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance>> field_204863_b = Sets.<ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_204862_a = ☃;
      }

      public boolean func_204860_a() {
         return this.field_204863_b.isEmpty();
      }

      public void func_204858_a(ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> var1) {
         this.field_204863_b.add(☃);
      }

      public void func_204861_b(ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> var1) {
         this.field_204863_b.remove(☃);
      }

      public void func_204859_a(EntityPlayerMP var1, ItemStack var2, EntityFishHook var3, Collection<ItemStack> var4) {
         List<ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> ☃x : this.field_204863_b) {
            if (☃x.func_192158_a().func_204830_a(☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<FishingRodHookedTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_204862_a);
            }
         }
      }
   }
}
