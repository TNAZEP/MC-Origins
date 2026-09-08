package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityCatSplitFix extends SimpleEntityRenameFix {
   public EntityCatSplitFix(Schema var1, boolean var2) {
      super("EntityCatSplitFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2) {
      if (Objects.equals("minecraft:ocelot", â˜ƒ)) {
         int â˜ƒ = â˜ƒ.get("CatType").asInt(0);
         if (â˜ƒ == 0) {
            String â˜ƒx = â˜ƒ.get("Owner").asString("");
            String â˜ƒxx = â˜ƒ.get("OwnerUUID").asString("");
            if (â˜ƒx.length() > 0 || â˜ƒxx.length() > 0) {
               â˜ƒ.set("Trusting", â˜ƒ.createBoolean(true));
            }
         } else if (â˜ƒ > 0 && â˜ƒ < 4) {
            â˜ƒ = â˜ƒ.set("CatType", â˜ƒ.createInt(â˜ƒ));
            â˜ƒ = â˜ƒ.set("OwnerUUID", â˜ƒ.createString(â˜ƒ.get("OwnerUUID").asString("")));
            return Pair.of("minecraft:cat", â˜ƒ);
         }
      }

      return Pair.of(â˜ƒ, â˜ƒ);
   }
}
