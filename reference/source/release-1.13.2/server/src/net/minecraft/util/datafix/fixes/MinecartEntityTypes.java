package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class MinecartEntityTypes extends DataFix {
   private static final List<String> field_188222_a = Lists.newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace");

   public MinecartEntityTypes(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> ☃ = this.getInputSchema().findChoiceType(TypeReferences.field_211299_o);
      TaggedChoiceType<String> ☃x = this.getOutputSchema().findChoiceType(TypeReferences.field_211299_o);
      return this.fixTypeEverywhere(
         "EntityMinecartIdentifiersFix",
         ☃,
         ☃x,
         var2x -> var3 -> {
               if (!Objects.equals(var3.getFirst(), "Minecart")) {
                  return var3;
               } else {
                  Typed<? extends Pair<String, ?>> ☃x = (Typed)☃.point(var2x, "Minecart", var3.getSecond()).orElseThrow(IllegalStateException::new);
                  Dynamic<?> ☃xx = ☃x.getOrCreate(DSL.remainderFinder());
                  int ☃xxx = ☃xx.getInt("Type");
                  String ☃;
                  if (☃xxx > 0 && ☃xxx < field_188222_a.size()) {
                     ☃ = (String)field_188222_a.get(☃xxx);
                  } else {
                     ☃ = "MinecartRideable";
                  }
   
                  return Pair.of(
                     ☃,
                     ((Optional)((Type)☃.types().get(☃)).read(☃x.write()).getSecond())
                        .orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
                  );
               }
            }
      );
   }
}
