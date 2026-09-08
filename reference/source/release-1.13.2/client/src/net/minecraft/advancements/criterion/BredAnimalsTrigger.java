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
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class BredAnimalsTrigger implements ICriterionTrigger<BredAnimalsTrigger.Instance> {
   private static final ResourceLocation field_192171_a = new ResourceLocation("bred_animals");
   private final Map<PlayerAdvancements, BredAnimalsTrigger.Listeners> field_192172_b = Maps.<PlayerAdvancements, BredAnimalsTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192171_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> var2) {
      BredAnimalsTrigger.Listeners ☃ = (BredAnimalsTrigger.Listeners)this.field_192172_b.get(☃);
      if (☃ == null) {
         ☃ = new BredAnimalsTrigger.Listeners(☃);
         this.field_192172_b.put(☃, ☃);
      }

      ☃.func_192343_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> var2) {
      BredAnimalsTrigger.Listeners ☃ = (BredAnimalsTrigger.Listeners)this.field_192172_b.get(☃);
      if (☃ != null) {
         ☃.func_192340_b(☃);
         if (☃.func_192341_a()) {
            this.field_192172_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192172_b.remove(☃);
   }

   public BredAnimalsTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate ☃ = EntityPredicate.func_192481_a(☃.get("parent"));
      EntityPredicate ☃x = EntityPredicate.func_192481_a(☃.get("partner"));
      EntityPredicate ☃xx = EntityPredicate.func_192481_a(☃.get("child"));
      return new BredAnimalsTrigger.Instance(☃, ☃x, ☃xx);
   }

   public void func_192168_a(EntityPlayerMP var1, EntityAnimal var2, EntityAnimal var3, @Nullable EntityAgeable var4) {
      BredAnimalsTrigger.Listeners ☃ = (BredAnimalsTrigger.Listeners)this.field_192172_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192342_a(☃, ☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192247_a;
      private final EntityPredicate field_192248_b;
      private final EntityPredicate field_192249_c;

      public Instance(EntityPredicate var1, EntityPredicate var2, EntityPredicate var3) {
         super(BredAnimalsTrigger.field_192171_a);
         this.field_192247_a = ☃;
         this.field_192248_b = ☃;
         this.field_192249_c = ☃;
      }

      public static BredAnimalsTrigger.Instance func_203908_c() {
         return new BredAnimalsTrigger.Instance(EntityPredicate.field_192483_a, EntityPredicate.field_192483_a, EntityPredicate.field_192483_a);
      }

      public static BredAnimalsTrigger.Instance func_203909_a(EntityPredicate.Builder var0) {
         return new BredAnimalsTrigger.Instance(☃.func_204000_b(), EntityPredicate.field_192483_a, EntityPredicate.field_192483_a);
      }

      public boolean func_192246_a(EntityPlayerMP var1, EntityAnimal var2, EntityAnimal var3, @Nullable EntityAgeable var4) {
         if (!this.field_192249_c.func_192482_a(☃, ☃)) {
            return false;
         } else {
            return this.field_192247_a.func_192482_a(☃, ☃) && this.field_192248_b.func_192482_a(☃, ☃)
               || this.field_192247_a.func_192482_a(☃, ☃) && this.field_192248_b.func_192482_a(☃, ☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("parent", this.field_192247_a.func_204006_a());
         ☃.add("partner", this.field_192248_b.func_204006_a());
         ☃.add("child", this.field_192249_c.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192344_a;
      private final Set<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> field_192345_b = Sets.<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192344_a = ☃;
      }

      public boolean func_192341_a() {
         return this.field_192345_b.isEmpty();
      }

      public void func_192343_a(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> var1) {
         this.field_192345_b.add(☃);
      }

      public void func_192340_b(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> var1) {
         this.field_192345_b.remove(☃);
      }

      public void func_192342_a(EntityPlayerMP var1, EntityAnimal var2, EntityAnimal var3, @Nullable EntityAgeable var4) {
         List<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> ☃x : this.field_192345_b) {
            if (☃x.func_192158_a().func_192246_a(☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192344_a);
            }
         }
      }
   }
}
