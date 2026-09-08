package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.TypeReferences;

public class PaintingMotive extends NamedEntityFix {
   private static final Map<String, String> field_201154_a = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("donkeykong", "donkey_kong");
      var0.put("burningskull", "burning_skull");
      var0.put("skullandroses", "skull_and_roses");
   });

   public PaintingMotive(Schema var1, boolean var2) {
      super(☃, ☃, "EntityPaintingMotiveFix", TypeReferences.field_211299_o, "minecraft:painting");
   }

   public Dynamic<?> func_209652_a(Dynamic<?> var1) {
      Optional<String> ☃ = ☃.get("Motive").flatMap(Dynamic::getStringValue);
      if (☃.isPresent()) {
         String ☃x = ((String)☃.get()).toLowerCase(Locale.ROOT);
         return ☃.set("Motive", ☃.createString(new ResourceLocation((String)field_201154_a.getOrDefault(☃x, ☃x)).toString()));
      } else {
         return ☃;
      }
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), this::func_209652_a);
   }
}
