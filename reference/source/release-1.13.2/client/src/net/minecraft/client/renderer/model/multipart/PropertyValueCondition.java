package net.minecraft.client.renderer.model.multipart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;

public class PropertyValueCondition implements ICondition {
   private static final Splitter field_188124_c = Splitter.on('|').omitEmptyStrings();
   private final String field_188125_d;
   private final String field_188126_e;

   public PropertyValueCondition(String var1, String var2) {
      this.field_188125_d = ☃;
      this.field_188126_e = ☃;
   }

   @Override
   public Predicate<IBlockState> getPredicate(StateContainer<Block, IBlockState> var1) {
      IProperty<?> ☃ = ☃.func_185920_a(this.field_188125_d);
      if (☃ == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.field_188125_d, ☃.func_177622_c().toString()));
      } else {
         String ☃ = this.field_188126_e;
         boolean ☃x = !☃.isEmpty() && ☃.charAt(0) == '!';
         if (☃x) {
            ☃ = ☃.substring(1);
         }

         List<String> ☃ = field_188124_c.splitToList(☃);
         if (☃.isEmpty()) {
            throw new RuntimeException(
               String.format("Empty value '%s' for property '%s' on '%s'", this.field_188126_e, this.field_188125_d, ☃.func_177622_c().toString())
            );
         } else {
            Predicate<IBlockState> ☃;
            if (☃.size() == 1) {
               ☃ = this.func_212485_a(☃, ☃, ☃);
            } else {
               List<Predicate<IBlockState>> ☃ = (List)☃.stream().map(var3x -> this.func_212485_a(☃, ☃, var3x)).collect(Collectors.toList());
               ☃ = var1x -> ☃.stream().anyMatch(var1xx -> var1xx.test(var1x));
            }

            return ☃x ? ☃.negate() : ☃;
         }
      }
   }

   private Predicate<IBlockState> func_212485_a(StateContainer<Block, IBlockState> var1, IProperty<?> var2, String var3) {
      Optional<?> ☃ = ☃.func_185929_b(☃);
      if (!☃.isPresent()) {
         throw new RuntimeException(
            String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", ☃, this.field_188125_d, ☃.func_177622_c().toString(), this.field_188126_e)
         );
      } else {
         return var2x -> var2x.func_177229_b(☃).equals(☃.get());
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.field_188125_d).add("value", this.field_188126_e).toString();
   }
}
