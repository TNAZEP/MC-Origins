package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class BlockEntityBannerColor extends NamedEntityFix {
   public BlockEntityBannerColor(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntityBannerColorFix", TypeReferences.field_211294_j, "minecraft:banner");
   }

   public Dynamic<?> func_209643_a(Dynamic<?> var1) {
      ☃ = ☃.update("Base", var0 -> var0.createInt(15 - var0.getNumberValue(0).intValue()));
      return ☃.update(
         "Patterns",
         var0 -> DataFixUtils.orElse(
               var0.getStream()
                  .map(var0x -> var0x.map(var0xx -> var0xx.update("Color", var0xxx -> var0xxx.createInt(15 - var0xxx.getNumberValue(0).intValue()))))
                  .map(var0::createList),
               var0
            )
      );
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), this::func_209643_a);
   }
}
