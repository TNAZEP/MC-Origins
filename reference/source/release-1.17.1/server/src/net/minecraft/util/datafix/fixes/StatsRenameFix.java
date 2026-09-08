package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Map;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class StatsRenameFix extends DataFix {
   private final String name;
   private final Map<String, String> renames;

   public StatsRenameFix(Schema var1, String var2, Map<String, String> var3) {
      super(â˜ƒ, false);
      this.name = â˜ƒ;
      this.renames = â˜ƒ;
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return TypeRewriteRule.seq(this.createStatRule(), this.createCriteriaRule());
   }

   private TypeRewriteRule createCriteriaRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.OBJECTIVE);
      Type<?> â˜ƒx = this.getInputSchema().getType(References.OBJECTIVE);
      OpticFinder<?> â˜ƒxx = â˜ƒx.findField("CriteriaType");
      TaggedChoiceType<?> â˜ƒxxx = (TaggedChoiceType)â˜ƒxx.type()
         .findChoiceType("type", -1)
         .orElseThrow(() -> new IllegalStateException("Can't find choice type for criteria"));
      Type<?> â˜ƒxxxx = (Type)â˜ƒxxx.types().get("minecraft:custom");
      if (â˜ƒxxxx == null) {
         throw new IllegalStateException("Failed to find custom criterion type variant");
      } else {
         OpticFinder<?> â˜ƒ = DSL.namedChoice("minecraft:custom", â˜ƒxxxx);
         OpticFinder<String> â˜ƒx = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
         return this.fixTypeEverywhereTyped(
            this.name,
            â˜ƒx,
            â˜ƒ,
            var4x -> var4x.updateTyped(
                  â˜ƒ, var3x -> var3x.updateTyped(â˜ƒ, var2x -> var2x.update(â˜ƒ, var1x -> (String)this.renames.getOrDefault(var1x, var1x)))
               )
         );
      }
   }

   private TypeRewriteRule createStatRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.STATS);
      Type<?> â˜ƒx = this.getInputSchema().getType(References.STATS);
      OpticFinder<?> â˜ƒxx = â˜ƒx.findField("stats");
      OpticFinder<?> â˜ƒxxx = â˜ƒxx.type().findField("minecraft:custom");
      OpticFinder<String> â˜ƒxxxx = NamespacedSchema.namespacedString().finder();
      return this.fixTypeEverywhereTyped(
         this.name,
         â˜ƒx,
         â˜ƒ,
         var4x -> var4x.updateTyped(â˜ƒ, var3x -> var3x.updateTyped(â˜ƒ, var2x -> var2x.update(â˜ƒ, var1x -> (String)this.renames.getOrDefault(var1x, var1x))))
      );
   }
}
