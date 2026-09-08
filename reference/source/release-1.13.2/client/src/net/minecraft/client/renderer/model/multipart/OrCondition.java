package net.minecraft.client.renderer.model.multipart;

import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.StateContainer;

public class OrCondition implements ICondition {
   private final Iterable<? extends ICondition> field_188127_c;

   public OrCondition(Iterable<? extends ICondition> var1) {
      this.field_188127_c = ☃;
   }

   @Override
   public Predicate<IBlockState> getPredicate(StateContainer<Block, IBlockState> var1) {
      List<Predicate<IBlockState>> ☃ = (List)Streams.stream(this.field_188127_c).map(var1x -> var1x.getPredicate(☃)).collect(Collectors.toList());
      return var1x -> ☃.stream().anyMatch(var1xx -> var1xx.test(var1x));
   }
}
