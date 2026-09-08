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

public class FilledBucketTrigger implements ICriterionTrigger<FilledBucketTrigger.Instance> {
   private static final ResourceLocation field_204818_a = new ResourceLocation("filled_bucket");
   private final Map<PlayerAdvancements, FilledBucketTrigger.Listeners> field_204819_b = Maps.<PlayerAdvancements, FilledBucketTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_204818_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<FilledBucketTrigger.Instance> var2) {
      FilledBucketTrigger.Listeners ☃ = (FilledBucketTrigger.Listeners)this.field_204819_b.get(☃);
      if (☃ == null) {
         ☃ = new FilledBucketTrigger.Listeners(☃);
         this.field_204819_b.put(☃, ☃);
      }

      ☃.func_204852_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<FilledBucketTrigger.Instance> var2) {
      FilledBucketTrigger.Listeners ☃ = (FilledBucketTrigger.Listeners)this.field_204819_b.get(☃);
      if (☃ != null) {
         ☃.func_204855_b(☃);
         if (☃.func_204853_a()) {
            this.field_204819_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_204819_b.remove(☃);
   }

   public FilledBucketTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ItemPredicate ☃ = ItemPredicate.func_192492_a(☃.get("item"));
      return new FilledBucketTrigger.Instance(☃);
   }

   public void func_204817_a(EntityPlayerMP var1, ItemStack var2) {
      FilledBucketTrigger.Listeners ☃ = (FilledBucketTrigger.Listeners)this.field_204819_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_204854_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_204828_a;

      public Instance(ItemPredicate var1) {
         super(FilledBucketTrigger.field_204818_a);
         this.field_204828_a = ☃;
      }

      public static FilledBucketTrigger.Instance func_204827_a(ItemPredicate var0) {
         return new FilledBucketTrigger.Instance(☃);
      }

      public boolean func_204826_a(ItemStack var1) {
         return this.field_204828_a.func_192493_a(☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("item", this.field_204828_a.func_200319_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_204856_a;
      private final Set<ICriterionTrigger.Listener<FilledBucketTrigger.Instance>> field_204857_b = Sets.<ICriterionTrigger.Listener<FilledBucketTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_204856_a = ☃;
      }

      public boolean func_204853_a() {
         return this.field_204857_b.isEmpty();
      }

      public void func_204852_a(ICriterionTrigger.Listener<FilledBucketTrigger.Instance> var1) {
         this.field_204857_b.add(☃);
      }

      public void func_204855_b(ICriterionTrigger.Listener<FilledBucketTrigger.Instance> var1) {
         this.field_204857_b.remove(☃);
      }

      public void func_204854_a(ItemStack var1) {
         List<ICriterionTrigger.Listener<FilledBucketTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<FilledBucketTrigger.Instance> ☃x : this.field_204857_b) {
            if (☃x.func_192158_a().func_204826_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<FilledBucketTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<FilledBucketTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_204856_a);
            }
         }
      }
   }
}
