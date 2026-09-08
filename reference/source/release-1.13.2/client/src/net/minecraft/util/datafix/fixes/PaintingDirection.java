package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.datafix.TypeReferences;

public class PaintingDirection extends DataFix {
   private static final int[][] field_210992_a = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

   public PaintingDirection(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   private Dynamic<?> func_209748_a(Dynamic<?> var1, boolean var2, boolean var3) {
      if ((☃ || ☃) && !☃.get("Facing").flatMap(Dynamic::getNumberValue).isPresent()) {
         int ☃;
         if (☃.get("Direction").flatMap(Dynamic::getNumberValue).isPresent()) {
            ☃ = ☃.getByte("Direction") % field_210992_a.length;
            int[] ☃x = field_210992_a[☃];
            ☃ = ☃.set("TileX", ☃.createInt(☃.getInt("TileX") + ☃x[0]));
            ☃ = ☃.set("TileY", ☃.createInt(☃.getInt("TileY") + ☃x[1]));
            ☃ = ☃.set("TileZ", ☃.createInt(☃.getInt("TileZ") + ☃x[2]));
            ☃ = ☃.remove("Direction");
            if (☃ && ☃.get("ItemRotation").flatMap(Dynamic::getNumberValue).isPresent()) {
               ☃ = ☃.set("ItemRotation", ☃.createByte((byte)(☃.getByte("ItemRotation") * 2)));
            }
         } else {
            ☃ = ☃.getByte("Dir") % field_210992_a.length;
            ☃ = ☃.remove("Dir");
         }

         ☃ = ☃.set("Facing", ☃.createByte((byte)☃));
      }

      return ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getChoiceType(TypeReferences.field_211299_o, "Painting");
      OpticFinder<?> ☃x = DSL.namedChoice("Painting", ☃);
      Type<?> ☃xx = this.getInputSchema().getChoiceType(TypeReferences.field_211299_o, "ItemFrame");
      OpticFinder<?> ☃xxx = DSL.namedChoice("ItemFrame", ☃xx);
      Type<?> ☃xxxx = this.getInputSchema().getType(TypeReferences.field_211299_o);
      TypeRewriteRule ☃xxxxx = this.fixTypeEverywhereTyped(
         "EntityPaintingFix",
         ☃xxxx,
         var3x -> var3x.updateTyped(☃, ☃, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.func_209748_a(var1xx, true, false)))
      );
      TypeRewriteRule ☃xxxxxx = this.fixTypeEverywhereTyped(
         "EntityItemFrameFix",
         ☃xxxx,
         var3x -> var3x.updateTyped(☃, ☃, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.func_209748_a(var1xx, false, true)))
      );
      return TypeRewriteRule.seq(☃xxxxx, ☃xxxxxx);
   }
}
