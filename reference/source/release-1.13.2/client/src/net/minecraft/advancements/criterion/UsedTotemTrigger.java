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

public class UsedTotemTrigger implements ICriterionTrigger<UsedTotemTrigger.Instance> {
   private static final ResourceLocation field_193188_a = new ResourceLocation("used_totem");
   private final Map<PlayerAdvancements, UsedTotemTrigger.Listeners> field_193189_b = Maps.<PlayerAdvancements, UsedTotemTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193188_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<UsedTotemTrigger.Instance> var2) {
      UsedTotemTrigger.Listeners ☃ = (UsedTotemTrigger.Listeners)this.field_193189_b.get(☃);
      if (☃ == null) {
         ☃ = new UsedTotemTrigger.Listeners(☃);
         this.field_193189_b.put(☃, ☃);
      }

      ☃.func_193508_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<UsedTotemTrigger.Instance> var2) {
      UsedTotemTrigger.Listeners ☃ = (UsedTotemTrigger.Listeners)this.field_193189_b.get(☃);
      if (☃ != null) {
         ☃.func_193506_b(☃);
         if (☃.func_193507_a()) {
            this.field_193189_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193189_b.remove(☃);
   }

   public UsedTotemTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ItemPredicate ☃ = ItemPredicate.func_192492_a(☃.get("item"));
      return new UsedTotemTrigger.Instance(☃);
   }

   public void func_193187_a(EntityPlayerMP var1, ItemStack var2) {
      UsedTotemTrigger.Listeners ☃ = (UsedTotemTrigger.Listeners)this.field_193189_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193509_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_193219_a;

      public Instance(ItemPredicate var1) {
         super(UsedTotemTrigger.field_193188_a);
         this.field_193219_a = ☃;
      }

      public static UsedTotemTrigger.Instance func_203941_a(IItemProvider var0) {
         return new UsedTotemTrigger.Instance(ItemPredicate.Builder.func_200309_a().func_200308_a(☃).func_200310_b());
      }

      public boolean func_193218_a(ItemStack var1) {
         return this.field_193219_a.func_192493_a(☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_193219_a.func_200319_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193510_a;
      private final Set<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> field_193511_b = Sets.<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193510_a = ☃;
      }

      public boolean func_193507_a() {
         return this.field_193511_b.isEmpty();
      }

      public void func_193508_a(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> var1) {
         this.field_193511_b.add(☃);
      }

      public void func_193506_b(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> var1) {
         this.field_193511_b.remove(☃);
      }

      public void func_193509_a(ItemStack var1) {
         List<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> ☃x : this.field_193511_b) {
            if (☃x.func_192158_a().func_193218_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193510_a);
            }
         }
      }
   }
}
