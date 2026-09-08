package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public abstract class BlockRenameFixWithJigsaw extends BlockRenameFix {
   private final String name;

   public BlockRenameFixWithJigsaw(Schema var1, String var2) {
      super(â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TypeReference â˜ƒ = References.BLOCK_ENTITY;
      String â˜ƒx = "minecraft:jigsaw";
      OpticFinder<?> â˜ƒxx = DSL.namedChoice("minecraft:jigsaw", this.getInputSchema().getChoiceType(â˜ƒ, "minecraft:jigsaw"));
      TypeRewriteRule â˜ƒxxx = this.fixTypeEverywhereTyped(
         this.name + " for jigsaw state",
         this.getInputSchema().getType(â˜ƒ),
         this.getOutputSchema().getType(â˜ƒ),
         var3x -> var3x.updateTyped(
               â˜ƒ,
               this.getOutputSchema().getChoiceType(â˜ƒ, "minecraft:jigsaw"),
               var1x -> var1x.update(
                     DSL.remainderFinder(), var1xx -> var1xx.update("final_state", var2x -> DataFixUtils.orElse(var2x.asString().result().map(var1xxx -> {
                              int â˜ƒ = var1xxx.indexOf(91);
                              int â˜ƒx = var1xxx.indexOf(123);
                              int â˜ƒxx = var1xxx.length();
                              if (â˜ƒ > 0) {
                                 â˜ƒxx = Math.min(â˜ƒxx, â˜ƒ);
                              }
         
                              if (â˜ƒx > 0) {
                                 â˜ƒxx = Math.min(â˜ƒxx, â˜ƒx);
                              }
         
                              String â˜ƒ = var1xxx.substring(0, â˜ƒxx);
                              String â˜ƒx = this.fixBlock(â˜ƒ);
                              return â˜ƒx + var1xxx.substring(â˜ƒxx);
                           }).map(var1xx::createString), var2x))
                  )
            )
      );
      return TypeRewriteRule.seq(super.makeRule(), â˜ƒxxx);
   }

   public static DataFix create(Schema var0, String var1, final Function<String, String> var2) {
      return new BlockRenameFixWithJigsaw(â˜ƒ, â˜ƒ) {
         @Override
         protected String fixBlock(String var1) {
            return (String)â˜ƒ.apply(â˜ƒ);
         }
      };
   }
}
