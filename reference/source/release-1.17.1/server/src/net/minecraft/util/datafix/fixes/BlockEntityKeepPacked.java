package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class BlockEntityKeepPacked extends NamedEntityFix {
   public BlockEntityKeepPacked(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "BlockEntityKeepPacked", References.BLOCK_ENTITY, "DUMMY");
   }

   private static Dynamic<?> fixTag(Dynamic<?> var0) {
      return â˜ƒ.set("keepPacked", â˜ƒ.createBoolean(true));
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), BlockEntityKeepPacked::fixTag);
   }
}
