package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class BlockEntityKeepPacked extends NamedEntityFix {
   public BlockEntityKeepPacked(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntityKeepPacked", TypeReferences.field_211294_j, "DUMMY");
   }

   private static Dynamic<?> func_209645_a(Dynamic<?> var0) {
      return ☃.set("keepPacked", ☃.createBoolean(true));
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), BlockEntityKeepPacked::func_209645_a);
   }
}
