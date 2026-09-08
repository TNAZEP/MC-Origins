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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PlayerHurtEntityTrigger implements ICriterionTrigger<PlayerHurtEntityTrigger.Instance> {
   private static final ResourceLocation field_192222_a = new ResourceLocation("player_hurt_entity");
   private final Map<PlayerAdvancements, PlayerHurtEntityTrigger.Listeners> field_192223_b = Maps.<PlayerAdvancements, PlayerHurtEntityTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192222_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> var2) {
      PlayerHurtEntityTrigger.Listeners ☃ = (PlayerHurtEntityTrigger.Listeners)this.field_192223_b.get(☃);
      if (☃ == null) {
         ☃ = new PlayerHurtEntityTrigger.Listeners(☃);
         this.field_192223_b.put(☃, ☃);
      }

      ☃.func_192522_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> var2) {
      PlayerHurtEntityTrigger.Listeners ☃ = (PlayerHurtEntityTrigger.Listeners)this.field_192223_b.get(☃);
      if (☃ != null) {
         ☃.func_192519_b(☃);
         if (☃.func_192520_a()) {
            this.field_192223_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192223_b.remove(☃);
   }

   public PlayerHurtEntityTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      DamagePredicate ☃ = DamagePredicate.func_192364_a(☃.get("damage"));
      EntityPredicate ☃x = EntityPredicate.func_192481_a(☃.get("entity"));
      return new PlayerHurtEntityTrigger.Instance(☃, ☃x);
   }

   public void func_192220_a(EntityPlayerMP var1, Entity var2, DamageSource var3, float var4, float var5, boolean var6) {
      PlayerHurtEntityTrigger.Listeners ☃ = (PlayerHurtEntityTrigger.Listeners)this.field_192223_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192521_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final DamagePredicate field_192279_a;
      private final EntityPredicate field_192280_b;

      public Instance(DamagePredicate var1, EntityPredicate var2) {
         super(PlayerHurtEntityTrigger.field_192222_a);
         this.field_192279_a = ☃;
         this.field_192280_b = ☃;
      }

      public static PlayerHurtEntityTrigger.Instance func_203936_a(DamagePredicate.Builder var0) {
         return new PlayerHurtEntityTrigger.Instance(☃.func_203970_b(), EntityPredicate.field_192483_a);
      }

      public boolean func_192278_a(EntityPlayerMP var1, Entity var2, DamageSource var3, float var4, float var5, boolean var6) {
         if (!this.field_192279_a.func_192365_a(☃, ☃, ☃, ☃, ☃)) {
            return false;
         } else {
            return this.field_192280_b.func_192482_a(☃, ☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("damage", this.field_192279_a.func_203977_a());
         ☃.add("entity", this.field_192280_b.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192523_a;
      private final Set<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>> field_192524_b = Sets.<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192523_a = ☃;
      }

      public boolean func_192520_a() {
         return this.field_192524_b.isEmpty();
      }

      public void func_192522_a(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> var1) {
         this.field_192524_b.add(☃);
      }

      public void func_192519_b(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> var1) {
         this.field_192524_b.remove(☃);
      }

      public void func_192521_a(EntityPlayerMP var1, Entity var2, DamageSource var3, float var4, float var5, boolean var6) {
         List<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> ☃x : this.field_192524_b) {
            if (☃x.func_192158_a().func_192278_a(☃, ☃, ☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192523_a);
            }
         }
      }
   }
}
