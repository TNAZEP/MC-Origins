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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TameAnimalTrigger implements ICriterionTrigger<TameAnimalTrigger.Instance> {
   private static final ResourceLocation field_193179_a = new ResourceLocation("tame_animal");
   private final Map<PlayerAdvancements, TameAnimalTrigger.Listeners> field_193180_b = Maps.<PlayerAdvancements, TameAnimalTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193179_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> var2) {
      TameAnimalTrigger.Listeners ☃ = (TameAnimalTrigger.Listeners)this.field_193180_b.get(☃);
      if (☃ == null) {
         ☃ = new TameAnimalTrigger.Listeners(☃);
         this.field_193180_b.put(☃, ☃);
      }

      ☃.func_193496_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> var2) {
      TameAnimalTrigger.Listeners ☃ = (TameAnimalTrigger.Listeners)this.field_193180_b.get(☃);
      if (☃ != null) {
         ☃.func_193494_b(☃);
         if (☃.func_193495_a()) {
            this.field_193180_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193180_b.remove(☃);
   }

   public TameAnimalTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate ☃ = EntityPredicate.func_192481_a(☃.get("entity"));
      return new TameAnimalTrigger.Instance(☃);
   }

   public void func_193178_a(EntityPlayerMP var1, EntityAnimal var2) {
      TameAnimalTrigger.Listeners ☃ = (TameAnimalTrigger.Listeners)this.field_193180_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193497_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_193217_a;

      public Instance(EntityPredicate var1) {
         super(TameAnimalTrigger.field_193179_a);
         this.field_193217_a = ☃;
      }

      public static TameAnimalTrigger.Instance func_203938_c() {
         return new TameAnimalTrigger.Instance(EntityPredicate.field_192483_a);
      }

      public boolean func_193216_a(EntityPlayerMP var1, EntityAnimal var2) {
         return this.field_193217_a.func_192482_a(☃, ☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("entity", this.field_193217_a.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193498_a;
      private final Set<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> field_193499_b = Sets.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193498_a = ☃;
      }

      public boolean func_193495_a() {
         return this.field_193499_b.isEmpty();
      }

      public void func_193496_a(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> var1) {
         this.field_193499_b.add(☃);
      }

      public void func_193494_b(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> var1) {
         this.field_193499_b.remove(☃);
      }

      public void func_193497_a(EntityPlayerMP var1, EntityAnimal var2) {
         List<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> ☃x : this.field_193499_b) {
            if (☃x.func_192158_a().func_193216_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193498_a);
            }
         }
      }
   }
}
