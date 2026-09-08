package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CustomNameStringToComponentEntity extends DataFix {
   public CustomNameStringToComponentEntity(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<String> ☃ = DSL.fieldFinder("id", DSL.namespacedString());
      return this.fixTypeEverywhereTyped(
         "EntityCustomNameToComponentFix", this.getInputSchema().getType(TypeReferences.field_211299_o), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> ☃ = var1x.getOptional(☃);
               return ☃.isPresent() && Objects.equals(☃.get(), "minecraft:commandblock_minecart") ? var2 : func_209740_a(var2);
            })
      );
   }

   public static Dynamic<?> func_209740_a(Dynamic<?> var0) {
      String ☃ = ☃.getString("CustomName");
      return ☃.isEmpty() ? ☃.remove("CustomName") : ☃.set("CustomName", ☃.createString(ITextComponent.Serializer.func_150696_a(new TextComponentString(☃))));
   }
}
