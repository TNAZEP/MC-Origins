package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class EntityPaintingItemFrameDirectionFix extends DataFix {
   private static final int[][] DIRECTIONS = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

   public EntityPaintingItemFrameDirectionFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private Dynamic<?> doFix(Dynamic<?> var1, boolean var2, boolean var3) {
      if ((â˜ƒ || â˜ƒ) && !â˜ƒ.get("Facing").asNumber().result().isPresent()) {
         int â˜ƒ;
         if (â˜ƒ.get("Direction").asNumber().result().isPresent()) {
            â˜ƒ = â˜ƒ.get("Direction").asByte((byte)0) % DIRECTIONS.length;
            int[] â˜ƒx = DIRECTIONS[â˜ƒ];
            â˜ƒ = â˜ƒ.set("TileX", â˜ƒ.createInt(â˜ƒ.get("TileX").asInt(0) + â˜ƒx[0]));
            â˜ƒ = â˜ƒ.set("TileY", â˜ƒ.createInt(â˜ƒ.get("TileY").asInt(0) + â˜ƒx[1]));
            â˜ƒ = â˜ƒ.set("TileZ", â˜ƒ.createInt(â˜ƒ.get("TileZ").asInt(0) + â˜ƒx[2]));
            â˜ƒ = â˜ƒ.remove("Direction");
            if (â˜ƒ && â˜ƒ.get("ItemRotation").asNumber().result().isPresent()) {
               â˜ƒ = â˜ƒ.set("ItemRotation", â˜ƒ.createByte((byte)(â˜ƒ.get("ItemRotation").asByte((byte)0) * 2)));
            }
         } else {
            â˜ƒ = â˜ƒ.get("Dir").asByte((byte)0) % DIRECTIONS.length;
            â˜ƒ = â˜ƒ.remove("Dir");
         }

         â˜ƒ = â˜ƒ.set("Facing", â˜ƒ.createByte((byte)â˜ƒ));
      }

      return â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(References.ENTITY, "Painting");
      OpticFinder<?> â˜ƒx = DSL.namedChoice("Painting", â˜ƒ);
      Type<?> â˜ƒxx = this.getInputSchema().getChoiceType(References.ENTITY, "ItemFrame");
      OpticFinder<?> â˜ƒxxx = DSL.namedChoice("ItemFrame", â˜ƒxx);
      Type<?> â˜ƒxxxx = this.getInputSchema().getType(References.ENTITY);
      TypeRewriteRule â˜ƒxxxxx = this.fixTypeEverywhereTyped(
         "EntityPaintingFix",
         â˜ƒxxxx,
         var3x -> var3x.updateTyped(â˜ƒ, â˜ƒ, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.doFix(var1xx, true, false)))
      );
      TypeRewriteRule â˜ƒxxxxxx = this.fixTypeEverywhereTyped(
         "EntityItemFrameFix",
         â˜ƒxxxx,
         var3x -> var3x.updateTyped(â˜ƒ, â˜ƒ, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.doFix(var1xx, false, true)))
      );
      return TypeRewriteRule.seq(â˜ƒxxxxx, â˜ƒxxxxxx);
   }
}
