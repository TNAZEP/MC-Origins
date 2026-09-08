package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionType;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class BrewedPotionTrigger implements ICriterionTrigger<BrewedPotionTrigger.Instance> {
   private static final ResourceLocation field_192176_a = new ResourceLocation("brewed_potion");
   private final Map<PlayerAdvancements, BrewedPotionTrigger.Listeners> field_192177_b = Maps.<PlayerAdvancements, BrewedPotionTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192176_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> var2) {
      BrewedPotionTrigger.Listeners ☃ = (BrewedPotionTrigger.Listeners)this.field_192177_b.get(☃);
      if (☃ == null) {
         ☃ = new BrewedPotionTrigger.Listeners(☃);
         this.field_192177_b.put(☃, ☃);
      }

      ☃.func_192349_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> var2) {
      BrewedPotionTrigger.Listeners ☃ = (BrewedPotionTrigger.Listeners)this.field_192177_b.get(☃);
      if (☃ != null) {
         ☃.func_192346_b(☃);
         if (☃.func_192347_a()) {
            this.field_192177_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192177_b.remove(☃);
   }

   public BrewedPotionTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      PotionType ☃ = null;
      if (☃.has("potion")) {
         ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "potion"));
         if (!IRegistry.field_212621_j.func_212607_c(☃x)) {
            throw new JsonSyntaxException("Unknown potion '" + ☃x + "'");
         }

         ☃ = IRegistry.field_212621_j.func_82594_a(☃x);
      }

      return new BrewedPotionTrigger.Instance(☃);
   }

   public void func_192173_a(EntityPlayerMP var1, PotionType var2) {
      BrewedPotionTrigger.Listeners ☃ = (BrewedPotionTrigger.Listeners)this.field_192177_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192348_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final PotionType field_192251_a;

      public Instance(@Nullable PotionType var1) {
         super(BrewedPotionTrigger.field_192176_a);
         this.field_192251_a = ☃;
      }

      public static BrewedPotionTrigger.Instance func_203910_c() {
         return new BrewedPotionTrigger.Instance(null);
      }

      public boolean func_192250_a(PotionType var1) {
         return this.field_192251_a == null || this.field_192251_a == ☃;
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         if (this.field_192251_a != null) {
            ☃.addProperty("potion", IRegistry.field_212621_j.func_177774_c(this.field_192251_a).toString());
         }

         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192350_a;
      private final Set<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> field_192351_b = Sets.<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192350_a = ☃;
      }

      public boolean func_192347_a() {
         return this.field_192351_b.isEmpty();
      }

      public void func_192349_a(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> var1) {
         this.field_192351_b.add(☃);
      }

      public void func_192346_b(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> var1) {
         this.field_192351_b.remove(☃);
      }

      public void func_192348_a(PotionType var1) {
         List<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> ☃x : this.field_192351_b) {
            if (☃x.func_192158_a().func_192250_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192350_a);
            }
         }
      }
   }
}
