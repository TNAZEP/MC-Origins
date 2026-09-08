package net.minecraft.command.arguments;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class BlockStateInput implements Predicate<BlockWorldState> {
   private final IBlockState field_197234_a;
   private final Set<IProperty<?>> field_197235_b;
   @Nullable
   private final NBTTagCompound field_197236_c;

   public BlockStateInput(IBlockState var1, Set<IProperty<?>> var2, @Nullable NBTTagCompound var3) {
      this.field_197234_a = ☃;
      this.field_197235_b = ☃;
      this.field_197236_c = ☃;
   }

   public IBlockState func_197231_a() {
      return this.field_197234_a;
   }

   public boolean test(BlockWorldState var1) {
      IBlockState ☃ = ☃.func_177509_a();
      if (☃.func_177230_c() != this.field_197234_a.func_177230_c()) {
         return false;
      } else {
         for(IProperty<?> ☃ : this.field_197235_b) {
            if (☃.func_177229_b(☃) != this.field_197234_a.func_177229_b(☃)) {
               return false;
            }
         }

         if (this.field_197236_c == null) {
            return true;
         } else {
            TileEntity ☃ = ☃.func_177507_b();
            return ☃ != null && NBTUtil.func_181123_a(this.field_197236_c, ☃.func_189515_b(new NBTTagCompound()), true);
         }
      }
   }

   public boolean func_197230_a(WorldServer var1, BlockPos var2, int var3) {
      if (!☃.func_180501_a(☃, this.field_197234_a, ☃)) {
         return false;
      } else {
         if (this.field_197236_c != null) {
            TileEntity ☃ = ☃.func_175625_s(☃);
            if (☃ != null) {
               NBTTagCompound ☃x = this.field_197236_c.func_74737_b();
               ☃x.func_74768_a("x", ☃.func_177958_n());
               ☃x.func_74768_a("y", ☃.func_177956_o());
               ☃x.func_74768_a("z", ☃.func_177952_p());
               ☃.func_145839_a(☃x);
            }
         }

         return true;
      }
   }
}
