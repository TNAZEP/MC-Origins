package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class HeightmapRenamingFix extends DataFix {
   public HeightmapRenamingFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("Level");
      return this.fixTypeEverywhereTyped("HeightmapRenamingFix", â˜ƒ, var2x -> var2x.updateTyped(â˜ƒ, var1x -> var1x.update(DSL.remainderFinder(), this::fix)));
   }

   private Dynamic<?> fix(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("Heightmaps").result();
      if (!â˜ƒ.isPresent()) {
         return â˜ƒ;
      } else {
         Dynamic<?> â˜ƒ = (Dynamic)â˜ƒ.get();
         Optional<? extends Dynamic<?>> â˜ƒx = â˜ƒ.get("LIQUID").result();
         if (â˜ƒx.isPresent()) {
            â˜ƒ = â˜ƒ.remove("LIQUID");
            â˜ƒ = â˜ƒ.set("WORLD_SURFACE_WG", (Dynamic<?>)â˜ƒx.get());
         }

         Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("SOLID").result();
         if (â˜ƒ.isPresent()) {
            â˜ƒ = â˜ƒ.remove("SOLID");
            â˜ƒ = â˜ƒ.set("OCEAN_FLOOR_WG", (Dynamic<?>)â˜ƒ.get());
            â˜ƒ = â˜ƒ.set("OCEAN_FLOOR", (Dynamic<?>)â˜ƒ.get());
         }

         Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("LIGHT").result();
         if (â˜ƒ.isPresent()) {
            â˜ƒ = â˜ƒ.remove("LIGHT");
            â˜ƒ = â˜ƒ.set("LIGHT_BLOCKING", (Dynamic<?>)â˜ƒ.get());
         }

         Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("RAIN").result();
         if (â˜ƒ.isPresent()) {
            â˜ƒ = â˜ƒ.remove("RAIN");
            â˜ƒ = â˜ƒ.set("MOTION_BLOCKING", (Dynamic<?>)â˜ƒ.get());
            â˜ƒ = â˜ƒ.set("MOTION_BLOCKING_NO_LEAVES", (Dynamic<?>)â˜ƒ.get());
         }

         return â˜ƒ.set("Heightmaps", â˜ƒ);
      }
   }
}
