package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public final class BinomialDistributionGenerator implements NumberProvider {
   final NumberProvider n;
   final NumberProvider p;

   BinomialDistributionGenerator(NumberProvider var1, NumberProvider var2) {
      this.n = â˜ƒ;
      this.p = â˜ƒ;
   }

   @Override
   public LootNumberProviderType getType() {
      return NumberProviders.BINOMIAL;
   }

   @Override
   public int getInt(LootContext var1) {
      int â˜ƒ = this.n.getInt(â˜ƒ);
      float â˜ƒx = this.p.getFloat(â˜ƒ);
      Random â˜ƒxx = â˜ƒ.getRandom();
      int â˜ƒxxx = 0;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ; ++â˜ƒxxxx) {
         if (â˜ƒxx.nextFloat() < â˜ƒx) {
            ++â˜ƒxxx;
         }
      }

      return â˜ƒxxx;
   }

   @Override
   public float getFloat(LootContext var1) {
      return (float)this.getInt(â˜ƒ);
   }

   public static BinomialDistributionGenerator binomial(int var0, float var1) {
      return new BinomialDistributionGenerator(ConstantValue.exactly((float)â˜ƒ), ConstantValue.exactly(â˜ƒ));
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Sets.<LootContextParam<?>>union(this.n.getReferencedContextParams(), this.p.getReferencedContextParams());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BinomialDistributionGenerator> {
      public BinomialDistributionGenerator deserialize(JsonObject var1, JsonDeserializationContext var2) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "n", â˜ƒ, NumberProvider.class);
         NumberProvider â˜ƒx = GsonHelper.getAsObject(â˜ƒ, "p", â˜ƒ, NumberProvider.class);
         return new BinomialDistributionGenerator(â˜ƒ, â˜ƒx);
      }

      public void serialize(JsonObject var1, BinomialDistributionGenerator var2, JsonSerializationContext var3) {
         â˜ƒ.add("n", â˜ƒ.serialize(â˜ƒ.n));
         â˜ƒ.add("p", â˜ƒ.serialize(â˜ƒ.p));
      }
   }
}
