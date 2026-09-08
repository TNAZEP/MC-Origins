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
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class CuredZombieVillagerTrigger implements ICriterionTrigger<CuredZombieVillagerTrigger.Instance> {
   private static final ResourceLocation field_192186_a = new ResourceLocation("cured_zombie_villager");
   private final Map<PlayerAdvancements, CuredZombieVillagerTrigger.Listeners> field_192187_b = Maps.<PlayerAdvancements, CuredZombieVillagerTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192186_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> var2) {
      CuredZombieVillagerTrigger.Listeners ☃ = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(☃);
      if (☃ == null) {
         ☃ = new CuredZombieVillagerTrigger.Listeners(☃);
         this.field_192187_b.put(☃, ☃);
      }

      ☃.func_192360_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> var2) {
      CuredZombieVillagerTrigger.Listeners ☃ = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(☃);
      if (☃ != null) {
         ☃.func_192358_b(☃);
         if (☃.func_192359_a()) {
            this.field_192187_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192187_b.remove(☃);
   }

   public CuredZombieVillagerTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate ☃ = EntityPredicate.func_192481_a(☃.get("zombie"));
      EntityPredicate ☃x = EntityPredicate.func_192481_a(☃.get("villager"));
      return new CuredZombieVillagerTrigger.Instance(☃, ☃x);
   }

   public void func_192183_a(EntityPlayerMP var1, EntityZombie var2, EntityVillager var3) {
      CuredZombieVillagerTrigger.Listeners ☃ = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192361_a(☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192255_a;
      private final EntityPredicate field_192256_b;

      public Instance(EntityPredicate var1, EntityPredicate var2) {
         super(CuredZombieVillagerTrigger.field_192186_a);
         this.field_192255_a = ☃;
         this.field_192256_b = ☃;
      }

      public static CuredZombieVillagerTrigger.Instance func_203916_c() {
         return new CuredZombieVillagerTrigger.Instance(EntityPredicate.field_192483_a, EntityPredicate.field_192483_a);
      }

      public boolean func_192254_a(EntityPlayerMP var1, EntityZombie var2, EntityVillager var3) {
         if (!this.field_192255_a.func_192482_a(☃, ☃)) {
            return false;
         } else {
            return this.field_192256_b.func_192482_a(☃, ☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("zombie", this.field_192255_a.func_204006_a());
         ☃.add("villager", this.field_192256_b.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192362_a;
      private final Set<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>> field_192363_b = Sets.<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192362_a = ☃;
      }

      public boolean func_192359_a() {
         return this.field_192363_b.isEmpty();
      }

      public void func_192360_a(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> var1) {
         this.field_192363_b.add(☃);
      }

      public void func_192358_b(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> var1) {
         this.field_192363_b.remove(☃);
      }

      public void func_192361_a(EntityPlayerMP var1, EntityZombie var2, EntityVillager var3) {
         List<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> ☃x : this.field_192363_b) {
            if (☃x.func_192158_a().func_192254_a(☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192362_a);
            }
         }
      }
   }
}
