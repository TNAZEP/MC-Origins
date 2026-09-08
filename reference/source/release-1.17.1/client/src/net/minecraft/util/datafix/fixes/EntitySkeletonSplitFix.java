package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntitySkeletonSplitFix extends SimpleEntityRenameFix {
   public EntitySkeletonSplitFix(Schema var1, boolean var2) {
      super("EntitySkeletonSplitFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2) {
      if (Objects.equals(â˜ƒ, "Skeleton")) {
         int â˜ƒ = â˜ƒ.get("SkeletonType").asInt(0);
         if (â˜ƒ == 1) {
            â˜ƒ = "WitherSkeleton";
         } else if (â˜ƒ == 2) {
            â˜ƒ = "Stray";
         }
      }

      return Pair.of(â˜ƒ, â˜ƒ);
   }
}
