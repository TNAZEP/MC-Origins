package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class BlockStateFlattenVillageCrops extends DataFix {
   public BlockStateFlattenVillageCrops(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead(
         "SavedDataVillageCropFix",
         this.getInputSchema().getType(TypeReferences.field_211303_s),
         this.getOutputSchema().getType(TypeReferences.field_211303_s),
         this::func_209677_a
      );
   }

   private <T> Dynamic<T> func_209677_a(Dynamic<T> var1) {
      return ☃.update("Children", BlockStateFlattenVillageCrops::func_210590_b);
   }

   private static <T> Dynamic<T> func_210590_b(Dynamic<T> var0) {
      return (Dynamic<T>)☃.getStream().map(BlockStateFlattenVillageCrops::func_210586_a).map(☃::createList).orElse(☃);
   }

   private static Stream<? extends Dynamic<?>> func_210586_a(Stream<? extends Dynamic<?>> var0) {
      return ☃.map(var0x -> {
         String ☃ = var0x.getString("id");
         if ("ViF".equals(☃)) {
            return func_210588_c(var0x);
         } else {
            return "ViDF".equals(☃) ? func_210589_d(var0x) : var0x;
         }
      });
   }

   private static <T> Dynamic<T> func_210588_c(Dynamic<T> var0) {
      ☃ = func_209676_a(☃, "CA");
      return func_209676_a(☃, "CB");
   }

   private static <T> Dynamic<T> func_210589_d(Dynamic<T> var0) {
      ☃ = func_209676_a(☃, "CA");
      ☃ = func_209676_a(☃, "CB");
      ☃ = func_209676_a(☃, "CC");
      return func_209676_a(☃, "CD");
   }

   private static <T> Dynamic<T> func_209676_a(Dynamic<T> var0, String var1) {
      return ☃.get(☃).flatMap(Dynamic::getNumberValue).isPresent() ? ☃.set(☃, BlockStateFlatteningMap.func_210049_b(☃.getInt(☃) << 4)) : ☃;
   }
}
