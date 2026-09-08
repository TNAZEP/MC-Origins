package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;

public class EntityPredicate {
   public static final EntityPredicate field_192483_a = new EntityPredicate(
      EntityTypePredicate.field_209371_a,
      DistancePredicate.field_193423_a,
      LocationPredicate.field_193455_a,
      MobEffectsPredicate.field_193473_a,
      NBTPredicate.field_193479_a
   );
   public static final EntityPredicate[] field_204851_b = new EntityPredicate[0];
   private final EntityTypePredicate field_192484_b;
   private final DistancePredicate field_192485_c;
   private final LocationPredicate field_193435_d;
   private final MobEffectsPredicate field_193436_e;
   private final NBTPredicate field_193437_f;

   private EntityPredicate(EntityTypePredicate var1, DistancePredicate var2, LocationPredicate var3, MobEffectsPredicate var4, NBTPredicate var5) {
      this.field_192484_b = ☃;
      this.field_192485_c = ☃;
      this.field_193435_d = ☃;
      this.field_193436_e = ☃;
      this.field_193437_f = ☃;
   }

   public boolean func_192482_a(EntityPlayerMP var1, @Nullable Entity var2) {
      if (this == field_192483_a) {
         return true;
      } else if (☃ == null) {
         return false;
      } else if (!this.field_192484_b.func_209368_a(☃.func_200600_R())) {
         return false;
      } else if (!this.field_192485_c.func_193422_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v)) {
         return false;
      } else if (!this.field_193435_d.func_193452_a(☃.func_71121_q(), ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v)) {
         return false;
      } else if (!this.field_193436_e.func_193469_a(☃)) {
         return false;
      } else {
         return this.field_193437_f.func_193475_a(☃);
      }
   }

   public static EntityPredicate func_192481_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "entity");
         EntityTypePredicate ☃x = EntityTypePredicate.func_209370_a(☃.get("type"));
         DistancePredicate ☃xx = DistancePredicate.func_193421_a(☃.get("distance"));
         LocationPredicate ☃xxx = LocationPredicate.func_193454_a(☃.get("location"));
         MobEffectsPredicate ☃xxxx = MobEffectsPredicate.func_193471_a(☃.get("effects"));
         NBTPredicate ☃xxxxx = NBTPredicate.func_193476_a(☃.get("nbt"));
         return new EntityPredicate.Builder()
            .func_209366_a(☃x)
            .func_203997_a(☃xx)
            .func_203999_a(☃xxx)
            .func_209367_a(☃xxxx)
            .func_209365_a(☃xxxxx)
            .func_204000_b();
      } else {
         return field_192483_a;
      }
   }

   public static EntityPredicate[] func_204849_b(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonArray ☃ = JsonUtils.func_151207_m(☃, "entities");
         EntityPredicate[] ☃x = new EntityPredicate[☃.size()];

         for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
            ☃x[☃xx] = func_192481_a(☃.get(☃xx));
         }

         return ☃x;
      } else {
         return field_204851_b;
      }
   }

   public JsonElement func_204006_a() {
      if (this == field_192483_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         ☃.add("type", this.field_192484_b.func_209369_a());
         ☃.add("distance", this.field_192485_c.func_203994_a());
         ☃.add("location", this.field_193435_d.func_204009_a());
         ☃.add("effects", this.field_193436_e.func_204013_b());
         ☃.add("nbt", this.field_193437_f.func_200322_a());
         return ☃;
      }
   }

   public static JsonElement func_204850_a(EntityPredicate[] var0) {
      if (☃ == field_204851_b) {
         return JsonNull.INSTANCE;
      } else {
         JsonArray ☃ = new JsonArray();

         for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
            JsonElement ☃xx = ☃[☃x].func_204006_a();
            if (!☃xx.isJsonNull()) {
               ☃.add(☃xx);
            }
         }

         return ☃;
      }
   }

   public static class Builder {
      private EntityTypePredicate field_204001_a = EntityTypePredicate.field_209371_a;
      private DistancePredicate field_204002_b = DistancePredicate.field_193423_a;
      private LocationPredicate field_204003_c = LocationPredicate.field_193455_a;
      private MobEffectsPredicate field_204004_d = MobEffectsPredicate.field_193473_a;
      private NBTPredicate field_204005_e = NBTPredicate.field_193479_a;

      public static EntityPredicate.Builder func_203996_a() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder func_203998_a(EntityType<?> var1) {
         this.field_204001_a = new EntityTypePredicate(☃);
         return this;
      }

      public EntityPredicate.Builder func_209366_a(EntityTypePredicate var1) {
         this.field_204001_a = ☃;
         return this;
      }

      public EntityPredicate.Builder func_203997_a(DistancePredicate var1) {
         this.field_204002_b = ☃;
         return this;
      }

      public EntityPredicate.Builder func_203999_a(LocationPredicate var1) {
         this.field_204003_c = ☃;
         return this;
      }

      public EntityPredicate.Builder func_209367_a(MobEffectsPredicate var1) {
         this.field_204004_d = ☃;
         return this;
      }

      public EntityPredicate.Builder func_209365_a(NBTPredicate var1) {
         this.field_204005_e = ☃;
         return this;
      }

      public EntityPredicate func_204000_b() {
         return this.field_204001_a == EntityTypePredicate.field_209371_a
               && this.field_204002_b == DistancePredicate.field_193423_a
               && this.field_204003_c == LocationPredicate.field_193455_a
               && this.field_204004_d == MobEffectsPredicate.field_193473_a
               && this.field_204005_e == NBTPredicate.field_193479_a
            ? EntityPredicate.field_192483_a
            : new EntityPredicate(this.field_204001_a, this.field_204002_b, this.field_204003_c, this.field_204004_d, this.field_204005_e);
      }
   }
}
