package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public abstract class SimpleEntityRenameFix extends EntityRenameFix {
   public SimpleEntityRenameFix(String var1, Schema var2, boolean var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Typed<?>> fix(String var1, Typed<?> var2) {
      Pair<String, Dynamic<?>> â˜ƒ = this.getNewNameAndTag(â˜ƒ, â˜ƒ.getOrCreate(DSL.remainderFinder()));
      return Pair.of((String)â˜ƒ.getFirst(), â˜ƒ.set(DSL.remainderFinder(), â˜ƒ.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2);
}
