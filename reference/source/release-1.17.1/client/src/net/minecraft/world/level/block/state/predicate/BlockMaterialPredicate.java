package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockMaterialPredicate implements Predicate<BlockState> {
   private static final BlockMaterialPredicate AIR = new BlockMaterialPredicate(Material.AIR) {
      @Override
      public boolean test(@Nullable BlockState var1) {
         return â˜ƒ != null && â˜ƒ.isAir();
      }
   };
   private final Material material;

   BlockMaterialPredicate(Material var1) {
      this.material = â˜ƒ;
   }

   public static BlockMaterialPredicate forMaterial(Material var0) {
      return â˜ƒ == Material.AIR ? AIR : new BlockMaterialPredicate(â˜ƒ);
   }

   public boolean test(@Nullable BlockState var1) {
      return â˜ƒ != null && â˜ƒ.getMaterial() == this.material;
   }
}
