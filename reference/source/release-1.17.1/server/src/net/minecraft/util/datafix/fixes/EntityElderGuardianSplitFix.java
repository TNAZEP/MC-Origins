package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityElderGuardianSplitFix extends SimpleEntityRenameFix {
   public EntityElderGuardianSplitFix(Schema var1, boolean var2) {
      super("EntityElderGuardianSplitFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2) {
      return Pair.of(Objects.equals(â˜ƒ, "Guardian") && â˜ƒ.get("Elder").asBoolean(false) ? "ElderGuardian" : â˜ƒ, â˜ƒ);
   }
}
