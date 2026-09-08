package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class UniformGenerator implements NumberProvider {
   final NumberProvider min;
   final NumberProvider max;

   UniformGenerator(NumberProvider var1, NumberProvider var2) {
      this.min = â˜ƒ;
      this.max = â˜ƒ;
   }

   @Override
   public LootNumberProviderType getType() {
      return NumberProviders.UNIFORM;
   }

   public static UniformGenerator between(float var0, float var1) {
      return new UniformGenerator(ConstantValue.exactly(â˜ƒ), ConstantValue.exactly(â˜ƒ));
   }

   @Override
   public int getInt(LootContext var1) {
      return Mth.nextInt(â˜ƒ.getRandom(), this.min.getInt(â˜ƒ), this.max.getInt(â˜ƒ));
   }

   @Override
   public float getFloat(LootContext var1) {
      return Mth.nextFloat(â˜ƒ.getRandom(), this.min.getFloat(â˜ƒ), this.max.getFloat(â˜ƒ));
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Sets.<LootContextParam<?>>union(this.min.getReferencedContextParams(), this.max.getReferencedContextParams());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<UniformGenerator> {
      public UniformGenerator deserialize(JsonObject var1, JsonDeserializationContext var2) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "min", â˜ƒ, NumberProvider.class);
         NumberProvider â˜ƒx = GsonHelper.getAsObject(â˜ƒ, "max", â˜ƒ, NumberProvider.class);
         return new UniformGenerator(â˜ƒ, â˜ƒx);
      }

      public void serialize(JsonObject var1, UniformGenerator var2, JsonSerializationContext var3) {
         â˜ƒ.add("min", â˜ƒ.serialize(â˜ƒ.min));
         â˜ƒ.add("max", â˜ƒ.serialize(â˜ƒ.max));
      }
   }
}
