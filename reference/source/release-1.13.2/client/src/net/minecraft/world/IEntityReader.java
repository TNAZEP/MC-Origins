package net.minecraft.world;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public interface IEntityReader {
   List<Entity> func_175674_a(@Nullable Entity var1, AxisAlignedBB var2, @Nullable Predicate<? super Entity> var3);

   default List<Entity> func_72839_b(@Nullable Entity var1, AxisAlignedBB var2) {
      return this.func_175674_a(☃, ☃, EntitySelectors.field_180132_d);
   }

   default Stream<VoxelShape> func_211155_a(@Nullable Entity var1, VoxelShape var2, Set<Entity> var3) {
      if (☃.func_197766_b()) {
         return Stream.empty();
      } else {
         AxisAlignedBB ☃ = ☃.func_197752_a();
         return this.func_72839_b(☃, ☃.func_186662_g(0.25))
            .stream()
            .filter(var2x -> !☃.contains(var2x) && (☃ == null || !☃.func_184223_x(var2x)))
            .flatMap(
               var2x -> Stream.of(var2x.func_70046_E(), ☃ == null ? null : ☃.func_70114_g(var2x))
                     .filter(Objects::nonNull)
                     .filter(var1x -> var1x.func_72326_a(☃))
                     .map(VoxelShapes::func_197881_a)
            );
      }
   }
}
