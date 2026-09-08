package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public class SkeletonSplit extends EntityRenameHelper {
   public SkeletonSplit(Schema var1, boolean var2) {
      super("EntitySkeletonSplitFix", ☃, ☃);
   }

   @Override
   protected Pair<String, Dynamic<?>> func_209758_a(String var1, Dynamic<?> var2) {
      if (Objects.equals(☃, "Skeleton")) {
         int ☃ = ☃.getInt("SkeletonType");
         if (☃ == 1) {
            ☃ = "WitherSkeleton";
         } else if (☃ == 2) {
            ☃ = "Stray";
         }
      }

      return Pair.of(☃, ☃);
   }
}
