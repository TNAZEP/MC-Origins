package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;

public abstract class EntityRenameFix extends DataFix {
   protected final String name;

   public EntityRenameFix(String var1, Schema var2, boolean var3) {
      super(â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> â˜ƒ = this.getInputSchema().findChoiceType(References.ENTITY);
      TaggedChoiceType<String> â˜ƒx = this.getOutputSchema().findChoiceType(References.ENTITY);
      return this.fixTypeEverywhere(this.name, â˜ƒ, â˜ƒx, var3 -> var4 -> {
            String â˜ƒ = (String)var4.getFirst();
            Type<?> â˜ƒx = (Type)â˜ƒ.types().get(â˜ƒ);
            Pair<String, Typed<?>> â˜ƒxx = this.fix(â˜ƒ, this.getEntity(var4.getSecond(), var3, â˜ƒx));
            Type<?> â˜ƒxxx = (Type)â˜ƒ.types().get(â˜ƒxx.getFirst());
            if (!â˜ƒxxx.equals(â˜ƒxx.getSecond().getType(), true, true)) {
               throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", â˜ƒxxx, â˜ƒxx.getSecond().getType()));
            } else {
               return Pair.of((String)â˜ƒxx.getFirst(), â˜ƒxx.getSecond().getValue());
            }
         });
   }

   private <A> Typed<A> getEntity(Object var1, DynamicOps<?> var2, Type<A> var3) {
      return new Typed<>(â˜ƒ, â˜ƒ, (A)â˜ƒ);
   }

   protected abstract Pair<String, Typed<?>> fix(String var1, Typed<?> var2);
}
