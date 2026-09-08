package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

public class EntityProjectileOwnerFix extends DataFix {
   public EntityProjectileOwnerFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      return this.fixTypeEverywhereTyped("EntityProjectileOwner", â˜ƒ.getType(References.ENTITY), this::updateProjectiles);
   }

   private Typed<?> updateProjectiles(Typed<?> var1) {
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:egg", this::updateOwnerThrowable);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:ender_pearl", this::updateOwnerThrowable);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:experience_bottle", this::updateOwnerThrowable);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:snowball", this::updateOwnerThrowable);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:potion", this::updateOwnerThrowable);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:potion", this::updateItemPotion);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:llama_spit", this::updateOwnerLlamaSpit);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:arrow", this::updateOwnerArrow);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:spectral_arrow", this::updateOwnerArrow);
      return this.updateEntity(â˜ƒ, "minecraft:trident", this::updateOwnerArrow);
   }

   private Dynamic<?> updateOwnerArrow(Dynamic<?> var1) {
      long â˜ƒ = â˜ƒ.get("OwnerUUIDMost").asLong(0L);
      long â˜ƒx = â˜ƒ.get("OwnerUUIDLeast").asLong(0L);
      return this.setUUID(â˜ƒ, â˜ƒ, â˜ƒx).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
   }

   private Dynamic<?> updateOwnerLlamaSpit(Dynamic<?> var1) {
      OptionalDynamic<?> â˜ƒ = â˜ƒ.get("Owner");
      long â˜ƒx = â˜ƒ.get("OwnerUUIDMost").asLong(0L);
      long â˜ƒxx = â˜ƒ.get("OwnerUUIDLeast").asLong(0L);
      return this.setUUID(â˜ƒ, â˜ƒx, â˜ƒxx).remove("Owner");
   }

   private Dynamic<?> updateItemPotion(Dynamic<?> var1) {
      OptionalDynamic<?> â˜ƒ = â˜ƒ.get("Potion");
      return â˜ƒ.set("Item", â˜ƒ.orElseEmptyMap()).remove("Potion");
   }

   private Dynamic<?> updateOwnerThrowable(Dynamic<?> var1) {
      String â˜ƒ = "owner";
      OptionalDynamic<?> â˜ƒx = â˜ƒ.get("owner");
      long â˜ƒxx = â˜ƒx.get("M").asLong(0L);
      long â˜ƒxxx = â˜ƒx.get("L").asLong(0L);
      return this.setUUID(â˜ƒ, â˜ƒxx, â˜ƒxxx).remove("owner");
   }

   private Dynamic<?> setUUID(Dynamic<?> var1, long var2, long var4) {
      String â˜ƒ = "OwnerUUID";
      return â˜ƒ != 0L && â˜ƒ != 0L ? â˜ƒ.set("OwnerUUID", â˜ƒ.createIntList(Arrays.stream(createUUIDArray(â˜ƒ, â˜ƒ)))) : â˜ƒ;
   }

   private static int[] createUUIDArray(long var0, long var2) {
      return new int[]{(int)(â˜ƒ >> 32), (int)â˜ƒ, (int)(â˜ƒ >> 32), (int)â˜ƒ};
   }

   private Typed<?> updateEntity(Typed<?> var1, String var2, Function<Dynamic<?>, Dynamic<?>> var3) {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(References.ENTITY, â˜ƒ);
      Type<?> â˜ƒx = this.getOutputSchema().getChoiceType(References.ENTITY, â˜ƒ);
      return â˜ƒ.updateTyped(DSL.namedChoice(â˜ƒ, â˜ƒ), â˜ƒx, var1x -> var1x.update(DSL.remainderFinder(), â˜ƒ));
   }
}
