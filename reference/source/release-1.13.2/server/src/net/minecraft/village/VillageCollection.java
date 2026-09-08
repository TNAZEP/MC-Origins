package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldSavedData;

public class VillageCollection extends WorldSavedData {
   private World field_75556_a;
   private final List<BlockPos> field_75554_b = Lists.<BlockPos>newArrayList();
   private final List<VillageDoorInfo> field_75555_c = Lists.<VillageDoorInfo>newArrayList();
   private final List<Village> field_75552_d = Lists.<Village>newArrayList();
   private int field_75553_e;

   public VillageCollection(String var1) {
      super(☃);
   }

   public VillageCollection(World var1) {
      super(func_176062_a(☃.field_73011_w));
      this.field_75556_a = ☃;
      this.func_76185_a();
   }

   public void func_82566_a(World var1) {
      this.field_75556_a = ☃;

      for(Village ☃ : this.field_75552_d) {
         ☃.func_82691_a(☃);
      }
   }

   public void func_176060_a(BlockPos var1) {
      if (this.field_75554_b.size() <= 64) {
         if (!this.func_176057_e(☃)) {
            this.field_75554_b.add(☃);
         }
      }
   }

   public void func_75544_a() {
      ++this.field_75553_e;

      for(Village ☃ : this.field_75552_d) {
         ☃.func_75560_a(this.field_75553_e);
      }

      this.func_75549_c();
      this.func_75543_d();
      this.func_75545_e();
      if (this.field_75553_e % 400 == 0) {
         this.func_76185_a();
      }
   }

   private void func_75549_c() {
      Iterator<Village> ☃ = this.field_75552_d.iterator();

      while(☃.hasNext()) {
         Village ☃x = (Village)☃.next();
         if (☃x.func_75566_g()) {
            ☃.remove();
            this.func_76185_a();
         }
      }
   }

   public List<Village> func_75540_b() {
      return this.field_75552_d;
   }

   public Village func_176056_a(BlockPos var1, int var2) {
      Village ☃ = null;
      double ☃x = Float.MAX_VALUE;

      for(Village ☃xx : this.field_75552_d) {
         double ☃xxx = ☃xx.func_180608_a().func_177951_i(☃);
         if (!(☃xxx >= ☃x)) {
            float ☃xxxx = (float)(☃ + ☃xx.func_75568_b());
            if (!(☃xxx > (double)(☃xxxx * ☃xxxx))) {
               ☃ = ☃xx;
               ☃x = ☃xxx;
            }
         }
      }

      return ☃;
   }

   private void func_75543_d() {
      if (!this.field_75554_b.isEmpty()) {
         this.func_180609_b((BlockPos)this.field_75554_b.remove(0));
      }
   }

   private void func_75545_e() {
      for(int ☃ = 0; ☃ < this.field_75555_c.size(); ++☃) {
         VillageDoorInfo ☃x = (VillageDoorInfo)this.field_75555_c.get(☃);
         Village ☃xx = this.func_176056_a(☃x.func_179852_d(), 32);
         if (☃xx == null) {
            ☃xx = new Village(this.field_75556_a);
            this.field_75552_d.add(☃xx);
            this.func_76185_a();
         }

         ☃xx.func_75576_a(☃x);
      }

      this.field_75555_c.clear();
   }

   private void func_180609_b(BlockPos var1) {
      int ☃ = 16;
      int ☃x = 4;
      int ☃xx = 16;
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxx = -16; ☃xxxx < 16; ++☃xxxx) {
         for(int ☃xxxxx = -4; ☃xxxxx < 4; ++☃xxxxx) {
            for(int ☃xxxxxx = -16; ☃xxxxxx < 16; ++☃xxxxxx) {
               ☃xxx.func_189533_g(☃).func_196234_d(☃xxxx, ☃xxxxx, ☃xxxxxx);
               IBlockState ☃xxxxxxx = this.field_75556_a.func_180495_p(☃xxx);
               if (this.func_195928_a(☃xxxxxxx)) {
                  VillageDoorInfo ☃xxxxxxxx = this.func_176055_c(☃xxx);
                  if (☃xxxxxxxx == null) {
                     this.func_195927_a(☃xxxxxxx, ☃xxx);
                  } else {
                     ☃xxxxxxxx.func_179849_a(this.field_75553_e);
                  }
               }
            }
         }
      }
   }

   @Nullable
   private VillageDoorInfo func_176055_c(BlockPos var1) {
      for(VillageDoorInfo ☃ : this.field_75555_c) {
         if (☃.func_179852_d().func_177958_n() == ☃.func_177958_n()
            && ☃.func_179852_d().func_177952_p() == ☃.func_177952_p()
            && Math.abs(☃.func_179852_d().func_177956_o() - ☃.func_177956_o()) <= 1) {
            return ☃;
         }
      }

      for(Village ☃ : this.field_75552_d) {
         VillageDoorInfo ☃x = ☃.func_179864_e(☃);
         if (☃x != null) {
            return ☃x;
         }
      }

      return null;
   }

   private void func_195927_a(IBlockState var1, BlockPos var2) {
      EnumFacing ☃ = ☃.func_177229_b(BlockDoor.field_176520_a);
      EnumFacing ☃x = ☃.func_176734_d();
      int ☃xx = this.func_176061_a(☃, ☃, 5);
      int ☃xxx = this.func_176061_a(☃, ☃x, ☃xx + 1);
      if (☃xx != ☃xxx) {
         this.field_75555_c.add(new VillageDoorInfo(☃, ☃xx < ☃xxx ? ☃ : ☃x, this.field_75553_e));
      }
   }

   private int func_176061_a(BlockPos var1, EnumFacing var2, int var3) {
      int ☃ = 0;

      for(int ☃x = 1; ☃x <= 5; ++☃x) {
         if (this.field_75556_a.func_175678_i(☃.func_177967_a(☃, ☃x))) {
            if (++☃ >= ☃) {
               return ☃;
            }
         }
      }

      return ☃;
   }

   private boolean func_176057_e(BlockPos var1) {
      for(BlockPos ☃ : this.field_75554_b) {
         if (☃.equals(☃)) {
            return true;
         }
      }

      return false;
   }

   private boolean func_195928_a(IBlockState var1) {
      return ☃.func_177230_c() instanceof BlockDoor && ☃.func_185904_a() == Material.field_151575_d;
   }

   @Override
   public void func_76184_a(NBTTagCompound var1) {
      this.field_75553_e = ☃.func_74762_e("Tick");
      NBTTagList ☃ = ☃.func_150295_c("Villages", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         Village ☃xxx = new Village();
         ☃xxx.func_82690_a(☃xx);
         this.field_75552_d.add(☃xxx);
      }
   }

   @Override
   public NBTTagCompound func_189551_b(NBTTagCompound var1) {
      ☃.func_74768_a("Tick", this.field_75553_e);
      NBTTagList ☃ = new NBTTagList();

      for(Village ☃x : this.field_75552_d) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         ☃x.func_82689_b(☃xx);
         ☃.add((INBTBase)☃xx);
      }

      ☃.func_74782_a("Villages", ☃);
      return ☃;
   }

   public static String func_176062_a(Dimension var0) {
      return "villages" + ☃.func_186058_p().func_186067_c();
   }
}
