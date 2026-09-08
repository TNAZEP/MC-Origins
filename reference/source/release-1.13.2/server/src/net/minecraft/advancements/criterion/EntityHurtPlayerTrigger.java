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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EntityHurtPlayerTrigger implements ICriterionTrigger<EntityHurtPlayerTrigger.Instance> {
   private static final ResourceLocation field_192201_a = new ResourceLocation("entity_hurt_player");
   private final Map<PlayerAdvancements, EntityHurtPlayerTrigger.Listeners> field_192202_b = Maps.<PlayerAdvancements, EntityHurtPlayerTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192201_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> var2) {
      EntityHurtPlayerTrigger.Listeners ☃ = (EntityHurtPlayerTrigger.Listeners)this.field_192202_b.get(☃);
      if (☃ == null) {
         ☃ = new EntityHurtPlayerTrigger.Listeners(☃);
         this.field_192202_b.put(☃, ☃);
      }

      ☃.func_192477_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> var2) {
      EntityHurtPlayerTrigger.Listeners ☃ = (EntityHurtPlayerTrigger.Listeners)this.field_192202_b.get(☃);
      if (☃ != null) {
         ☃.func_192475_b(☃);
         if (☃.func_192476_a()) {
            this.field_192202_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192202_b.remove(☃);
   }

   public EntityHurtPlayerTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      DamagePredicate ☃ = DamagePredicate.func_192364_a(☃.get("damage"));
      return new EntityHurtPlayerTrigger.Instance(☃);
   }

   public void func_192200_a(EntityPlayerMP var1, DamageSource var2, float var3, float var4, boolean var5) {
      EntityHurtPlayerTrigger.Listeners ☃ = (EntityHurtPlayerTrigger.Listeners)this.field_192202_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192478_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final DamagePredicate field_192264_a;

      public Instance(DamagePredicate var1) {
         super(EntityHurtPlayerTrigger.field_192201_a);
         this.field_192264_a = ☃;
      }

      public static EntityHurtPlayerTrigger.Instance func_203921_a(DamagePredicate.Builder var0) {
         return new EntityHurtPlayerTrigger.Instance(☃.func_203970_b());
      }

      public boolean func_192263_a(EntityPlayerMP var1, DamageSource var2, float var3, float var4, boolean var5) {
         return this.field_192264_a.func_192365_a(☃, ☃, ☃, ☃, ☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("damage", this.field_192264_a.func_203977_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192479_a;
      private final Set<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> field_192480_b = Sets.<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192479_a = ☃;
      }

      public boolean func_192476_a() {
         return this.field_192480_b.isEmpty();
      }

      public void func_192477_a(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> var1) {
         this.field_192480_b.add(☃);
      }

      public void func_192475_b(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> var1) {
         this.field_192480_b.remove(☃);
      }

      public void func_192478_a(EntityPlayerMP var1, DamageSource var2, float var3, float var4, boolean var5) {
         List<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> ☃x : this.field_192480_b) {
            if (☃x.func_192158_a().func_192263_a(☃, ☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192479_a);
            }
         }
      }
   }
}
