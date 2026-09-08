package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class SavedDataVillageCropFix extends DataFix {
   public SavedDataVillageCropFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead(
         "SavedDataVillageCropFix",
         this.getInputSchema().getType(References.STRUCTURE_FEATURE),
         this.getOutputSchema().getType(References.STRUCTURE_FEATURE),
         this::fixTag
      );
   }

   private <T> Dynamic<T> fixTag(Dynamic<T> var1) {
      return â˜ƒ.update("Children", SavedDataVillageCropFix::updateChildren);
   }

   private static <T> Dynamic<T> updateChildren(Dynamic<T> var0) {
      return (Dynamic<T>)â˜ƒ.asStreamOpt().map(SavedDataVillageCropFix::updateChildren).map(â˜ƒ::createList).result().orElse(â˜ƒ);
   }

   private static Stream<? extends Dynamic<?>> updateChildren(Stream<? extends Dynamic<?>> var0) {
      return â˜ƒ.map(var0x -> {
         String â˜ƒ = var0x.get("id").asString("");
         if ("ViF".equals(â˜ƒ)) {
            return updateSingleField(var0x);
         } else {
            return "ViDF".equals(â˜ƒ) ? updateDoubleField(var0x) : var0x;
         }
      });
   }

   private static <T> Dynamic<T> updateSingleField(Dynamic<T> var0) {
      â˜ƒ = updateCrop(â˜ƒ, "CA");
      return updateCrop(â˜ƒ, "CB");
   }

   private static <T> Dynamic<T> updateDoubleField(Dynamic<T> var0) {
      â˜ƒ = updateCrop(â˜ƒ, "CA");
      â˜ƒ = updateCrop(â˜ƒ, "CB");
      â˜ƒ = updateCrop(â˜ƒ, "CC");
      return updateCrop(â˜ƒ, "CD");
   }

   private static <T> Dynamic<T> updateCrop(Dynamic<T> var0, String var1) {
      return â˜ƒ.get(â˜ƒ).asNumber().result().isPresent() ? â˜ƒ.set(â˜ƒ, BlockStateData.getTag(â˜ƒ.get(â˜ƒ).asInt(0) << 4)) : â˜ƒ;
   }
}
