package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;

public class EntityTippedArrowFix extends SimplestEntityRenameFix {
   public EntityTippedArrowFix(Schema var1, boolean var2) {
      super("EntityTippedArrowFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected String rename(String var1) {
      return Objects.equals(â˜ƒ, "TippedArrow") ? "Arrow" : â˜ƒ;
   }
}
