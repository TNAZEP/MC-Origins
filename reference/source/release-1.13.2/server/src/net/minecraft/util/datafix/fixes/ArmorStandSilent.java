package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class ArmorStandSilent extends NamedEntityFix {
   public ArmorStandSilent(Schema var1, boolean var2) {
      super(☃, ☃, "EntityArmorStandSilentFix", TypeReferences.field_211299_o, "ArmorStand");
   }

   public Dynamic<?> func_209650_a(Dynamic<?> var1) {
      return ☃.getBoolean("Silent") && !☃.getBoolean("Marker") ? ☃.remove("Silent") : ☃;
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), this::func_209650_a);
   }
}
