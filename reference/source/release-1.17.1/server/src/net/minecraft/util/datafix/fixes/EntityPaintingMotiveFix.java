package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public class EntityPaintingMotiveFix extends NamedEntityFix {
   private static final Map<String, String> MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("donkeykong", "donkey_kong");
      var0.put("burningskull", "burning_skull");
      var0.put("skullandroses", "skull_and_roses");
   });

   public EntityPaintingMotiveFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "EntityPaintingMotiveFix", References.ENTITY, "minecraft:painting");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      Optional<String> â˜ƒ = â˜ƒ.get("Motive").asString().result();
      if (â˜ƒ.isPresent()) {
         String â˜ƒx = ((String)â˜ƒ.get()).toLowerCase(Locale.ROOT);
         return â˜ƒ.set("Motive", â˜ƒ.createString(new ResourceLocation((String)MAP.getOrDefault(â˜ƒx, â˜ƒx)).toString()));
      } else {
         return â˜ƒ;
      }
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
