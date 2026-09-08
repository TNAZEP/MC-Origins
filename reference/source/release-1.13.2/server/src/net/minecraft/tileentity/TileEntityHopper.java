package net.minecraft.tileentity;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityHopper extends TileEntityLockableLoot implements IHopper, ITickable {
   private NonNullList<ItemStack> field_145900_a = NonNullList.func_191197_a(5, ItemStack.field_190927_a);
   private int field_145901_j = -1;
   private long field_190578_g;

   public TileEntityHopper() {
      super(TileEntityType.field_200987_r);
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145900_a = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(☃)) {
         ItemStackHelper.func_191283_b(☃, this.field_145900_a);
      }

      if (☃.func_150297_b("CustomName", 8)) {
         this.func_200226_a(ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName")));
      }

      this.field_145901_j = ☃.func_74762_e("TransferCooldown");
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (!this.func_184282_c(☃)) {
         ItemStackHelper.func_191282_a(☃, this.field_145900_a);
      }

      ☃.func_74768_a("TransferCooldown", this.field_145901_j);
      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(☃));
      }

      return ☃;
   }

   @Override
   public int func_70302_i_() {
      return this.field_145900_a.size();
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      this.func_184281_d(null);
      return ItemStackHelper.func_188382_a(this.func_190576_q(), ☃, ☃);
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      this.func_184281_d(null);
      this.func_190576_q().set(☃, ☃);
      if (☃.func_190916_E() > this.func_70297_j_()) {
         ☃.func_190920_e(this.func_70297_j_());
      }
   }

   @Override
   public ITextComponent func_200200_C_() {
      return (ITextComponent)(this.field_190577_o != null ? this.field_190577_o : new TextComponentTranslation("container.hopper"));
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public void func_73660_a() {
      if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
         --this.field_145901_j;
         this.field_190578_g = this.field_145850_b.func_82737_E();
         if (!this.func_145888_j()) {
            this.func_145896_c(0);
            this.func_200109_a(() -> func_145891_a(this));
         }
      }
   }

   private boolean func_200109_a(Supplier<Boolean> var1) {
      if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
         if (!this.func_145888_j() && this.func_195044_w().func_177229_b(BlockHopper.field_176429_b)) {
            boolean ☃ = false;
            if (!this.func_152104_k()) {
               ☃ = this.func_145883_k();
            }

            if (!this.func_152105_l()) {
               ☃ |= ☃.get();
            }

            if (☃) {
               this.func_145896_c(8);
               this.func_70296_d();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean func_152104_k() {
      for(ItemStack ☃ : this.field_145900_a) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean func_191420_l() {
      return this.func_152104_k();
   }

   private boolean func_152105_l() {
      for(ItemStack ☃ : this.field_145900_a) {
         if (☃.func_190926_b() || ☃.func_190916_E() != ☃.func_77976_d()) {
            return false;
         }
      }

      return true;
   }

   private boolean func_145883_k() {
      IInventory ☃ = this.func_145895_l();
      if (☃ == null) {
         return false;
      } else {
         EnumFacing ☃ = ((EnumFacing)this.func_195044_w().func_177229_b(BlockHopper.field_176430_a)).func_176734_d();
         if (this.func_174919_a(☃, ☃)) {
            return false;
         } else {
            for(int ☃ = 0; ☃ < this.func_70302_i_(); ++☃) {
               if (!this.func_70301_a(☃).func_190926_b()) {
                  ItemStack ☃x = this.func_70301_a(☃).func_77946_l();
                  ItemStack ☃xx = func_174918_a(this, ☃, this.func_70298_a(☃, 1), ☃);
                  if (☃xx.func_190926_b()) {
                     ☃.func_70296_d();
                     return true;
                  }

                  this.func_70299_a(☃, ☃x);
               }
            }

            return false;
         }
      }
   }

   private boolean func_174919_a(IInventory var1, EnumFacing var2) {
      if (☃ instanceof ISidedInventory) {
         ISidedInventory ☃ = (ISidedInventory)☃;
         int[] ☃x = ☃.func_180463_a(☃);

         for(int ☃xx : ☃x) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (☃xxx.func_190926_b() || ☃xxx.func_190916_E() != ☃xxx.func_77976_d()) {
               return false;
            }
         }
      } else {
         int ☃ = ☃.func_70302_i_();

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            ItemStack ☃xx = ☃.func_70301_a(☃x);
            if (☃xx.func_190926_b() || ☃xx.func_190916_E() != ☃xx.func_77976_d()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean func_174917_b(IInventory var0, EnumFacing var1) {
      if (☃ instanceof ISidedInventory) {
         ISidedInventory ☃ = (ISidedInventory)☃;
         int[] ☃x = ☃.func_180463_a(☃);

         for(int ☃xx : ☃x) {
            if (!☃.func_70301_a(☃xx).func_190926_b()) {
               return false;
            }
         }
      } else {
         int ☃ = ☃.func_70302_i_();

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            if (!☃.func_70301_a(☃x).func_190926_b()) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean func_145891_a(IHopper var0) {
      IInventory ☃ = func_145884_b(☃);
      if (☃ != null) {
         EnumFacing ☃x = EnumFacing.DOWN;
         if (func_174917_b(☃, ☃x)) {
            return false;
         }

         if (☃ instanceof ISidedInventory) {
            ISidedInventory ☃x = (ISidedInventory)☃;
            int[] ☃xx = ☃x.func_180463_a(☃x);

            for(int ☃xxx : ☃xx) {
               if (func_174915_a(☃, ☃, ☃xxx, ☃x)) {
                  return true;
               }
            }
         } else {
            int ☃x = ☃.func_70302_i_();

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               if (func_174915_a(☃, ☃, ☃xx, ☃x)) {
                  return true;
               }
            }
         }
      } else {
         for(EntityItem ☃ : func_200115_c(☃)) {
            if (func_200114_a(☃, ☃)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean func_174915_a(IHopper var0, IInventory var1, int var2, EnumFacing var3) {
      ItemStack ☃ = ☃.func_70301_a(☃);
      if (!☃.func_190926_b() && func_174921_b(☃, ☃, ☃, ☃)) {
         ItemStack ☃x = ☃.func_77946_l();
         ItemStack ☃xx = func_174918_a(☃, ☃, ☃.func_70298_a(☃, 1), null);
         if (☃xx.func_190926_b()) {
            ☃.func_70296_d();
            return true;
         }

         ☃.func_70299_a(☃, ☃x);
      }

      return false;
   }

   public static boolean func_200114_a(IInventory var0, EntityItem var1) {
      boolean ☃ = false;
      ItemStack ☃x = ☃.func_92059_d().func_77946_l();
      ItemStack ☃xx = func_174918_a(null, ☃, ☃x, null);
      if (☃xx.func_190926_b()) {
         ☃ = true;
         ☃.func_70106_y();
      } else {
         ☃.func_92058_a(☃xx);
      }

      return ☃;
   }

   public static ItemStack func_174918_a(@Nullable IInventory var0, IInventory var1, ItemStack var2, @Nullable EnumFacing var3) {
      if (☃ instanceof ISidedInventory && ☃ != null) {
         ISidedInventory ☃ = (ISidedInventory)☃;
         int[] ☃x = ☃.func_180463_a(☃);

         for(int ☃xx = 0; ☃xx < ☃x.length && !☃.func_190926_b(); ++☃xx) {
            ☃ = func_174916_c(☃, ☃, ☃, ☃x[☃xx], ☃);
         }
      } else {
         int ☃ = ☃.func_70302_i_();

         for(int ☃x = 0; ☃x < ☃ && !☃.func_190926_b(); ++☃x) {
            ☃ = func_174916_c(☃, ☃, ☃, ☃x, ☃);
         }
      }

      return ☃;
   }

   private static boolean func_174920_a(IInventory var0, ItemStack var1, int var2, @Nullable EnumFacing var3) {
      if (!☃.func_94041_b(☃, ☃)) {
         return false;
      } else {
         return !(☃ instanceof ISidedInventory) || ((ISidedInventory)☃).func_180462_a(☃, ☃, ☃);
      }
   }

   private static boolean func_174921_b(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      return !(☃ instanceof ISidedInventory) || ((ISidedInventory)☃).func_180461_b(☃, ☃, ☃);
   }

   private static ItemStack func_174916_c(@Nullable IInventory var0, IInventory var1, ItemStack var2, int var3, @Nullable EnumFacing var4) {
      ItemStack ☃ = ☃.func_70301_a(☃);
      if (func_174920_a(☃, ☃, ☃, ☃)) {
         boolean ☃x = false;
         boolean ☃xx = ☃.func_191420_l();
         if (☃.func_190926_b()) {
            ☃.func_70299_a(☃, ☃);
            ☃ = ItemStack.field_190927_a;
            ☃x = true;
         } else if (func_145894_a(☃, ☃)) {
            int ☃x = ☃.func_77976_d() - ☃.func_190916_E();
            int ☃xx = Math.min(☃.func_190916_E(), ☃x);
            ☃.func_190918_g(☃xx);
            ☃.func_190917_f(☃xx);
            ☃x = ☃xx > 0;
         }

         if (☃x) {
            if (☃xx && ☃ instanceof TileEntityHopper) {
               TileEntityHopper ☃x = (TileEntityHopper)☃;
               if (!☃x.func_174914_o()) {
                  int ☃xx = 0;
                  if (☃ instanceof TileEntityHopper) {
                     TileEntityHopper ☃xxx = (TileEntityHopper)☃;
                     if (☃x.field_190578_g >= ☃xxx.field_190578_g) {
                        ☃xx = 1;
                     }
                  }

                  ☃x.func_145896_c(8 - ☃xx);
               }
            }

            ☃.func_70296_d();
         }
      }

      return ☃;
   }

   @Nullable
   private IInventory func_145895_l() {
      EnumFacing ☃ = this.func_195044_w().func_177229_b(BlockHopper.field_176430_a);
      return func_195484_a(this.func_145831_w(), this.field_174879_c.func_177972_a(☃));
   }

   @Nullable
   public static IInventory func_145884_b(IHopper var0) {
      return func_145893_b(☃.func_145831_w(), ☃.func_96107_aA(), ☃.func_96109_aB() + 1.0, ☃.func_96108_aC());
   }

   public static List<EntityItem> func_200115_c(IHopper var0) {
      return (List<EntityItem>)☃.func_200100_i()
         .func_197756_d()
         .stream()
         .flatMap(
            var1 -> ☃.func_145831_w()
                  .func_175647_a(
                     EntityItem.class,
                     var1.func_72317_d(☃.func_96107_aA() - 0.5, ☃.func_96109_aB() - 0.5, ☃.func_96108_aC() - 0.5),
                     EntitySelectors.field_94557_a
                  )
                  .stream()
         )
         .collect(Collectors.toList());
   }

   @Nullable
   public static IInventory func_195484_a(World var0, BlockPos var1) {
      return func_145893_b(☃, (double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5);
   }

   @Nullable
   public static IInventory func_145893_b(World var0, double var1, double var3, double var5) {
      IInventory ☃ = null;
      BlockPos ☃x = new BlockPos(☃, ☃, ☃);
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      Block ☃xxx = ☃xx.func_177230_c();
      if (☃xxx.func_149716_u()) {
         TileEntity ☃xxxx = ☃.func_175625_s(☃x);
         if (☃xxxx instanceof IInventory) {
            ☃ = (IInventory)☃xxxx;
            if (☃ instanceof TileEntityChest && ☃xxx instanceof BlockChest) {
               ☃ = ((BlockChest)☃xxx).func_196309_a(☃xx, ☃, ☃x, true);
            }
         }
      }

      if (☃ == null) {
         List<Entity> ☃ = ☃.func_175674_a(null, new AxisAlignedBB(☃ - 0.5, ☃ - 0.5, ☃ - 0.5, ☃ + 0.5, ☃ + 0.5, ☃ + 0.5), EntitySelectors.field_96566_b);
         if (!☃.isEmpty()) {
            ☃ = (IInventory)☃.get(☃.field_73012_v.nextInt(☃.size()));
         }
      }

      return ☃;
   }

   private static boolean func_145894_a(ItemStack var0, ItemStack var1) {
      if (☃.func_77973_b() != ☃.func_77973_b()) {
         return false;
      } else if (☃.func_77952_i() != ☃.func_77952_i()) {
         return false;
      } else if (☃.func_190916_E() > ☃.func_77976_d()) {
         return false;
      } else {
         return ItemStack.func_77970_a(☃, ☃);
      }
   }

   @Override
   public double func_96107_aA() {
      return (double)this.field_174879_c.func_177958_n() + 0.5;
   }

   @Override
   public double func_96109_aB() {
      return (double)this.field_174879_c.func_177956_o() + 0.5;
   }

   @Override
   public double func_96108_aC() {
      return (double)this.field_174879_c.func_177952_p() + 0.5;
   }

   private void func_145896_c(int var1) {
      this.field_145901_j = ☃;
   }

   private boolean func_145888_j() {
      return this.field_145901_j > 0;
   }

   private boolean func_174914_o() {
      return this.field_145901_j > 8;
   }

   @Override
   public String func_174875_k() {
      return "minecraft:hopper";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      this.func_184281_d(☃);
      return new ContainerHopper(☃, this, ☃);
   }

   @Override
   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_145900_a;
   }

   @Override
   protected void func_199721_a(NonNullList<ItemStack> var1) {
      this.field_145900_a = ☃;
   }

   public void func_200113_a(Entity var1) {
      if (☃ instanceof EntityItem) {
         BlockPos ☃ = this.func_174877_v();
         if (VoxelShapes.func_197879_c(
            VoxelShapes.func_197881_a(☃.func_174813_aQ().func_72317_d((double)(-☃.func_177958_n()), (double)(-☃.func_177956_o()), (double)(-☃.func_177952_p()))),
            this.func_200100_i(),
            IBooleanFunction.AND
         )) {
            this.func_200109_a(() -> func_200114_a(this, (EntityItem)☃));
         }
      }
   }
}
