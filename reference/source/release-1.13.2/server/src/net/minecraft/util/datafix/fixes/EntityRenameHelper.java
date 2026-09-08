package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;

public abstract class EntityRenameHelper extends EntityRename {
   public EntityRenameHelper(String var1, Schema var2, boolean var3) {
      super(☃, ☃, ☃);
   }

   @Override
   protected Pair<String, Typed<?>> func_209149_a(String var1, Typed<?> var2) {
      Pair<String, Dynamic<?>> ☃ = this.func_209758_a(☃, ☃.getOrCreate(DSL.remainderFinder()));
      return Pair.of(☃.getFirst(), ☃.set(DSL.remainderFinder(), ☃.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> func_209758_a(String var1, Dynamic<?> var2);
}
