package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityBeacon extends TileEntityLockable implements ISidedInventory, ITickable {
   public static final Potion[][] field_146009_a = new Potion[][]{
      {MobEffects.field_76424_c, MobEffects.field_76422_e},
      {MobEffects.field_76429_m, MobEffects.field_76430_j},
      {MobEffects.field_76420_g},
      {MobEffects.field_76428_l}
   };
   private static final Set<Potion> field_184280_f = (Set<Potion>)Arrays.stream(field_146009_a).flatMap(Arrays::stream).collect(Collectors.toSet());
   private final List<TileEntityBeacon.BeamSegment> field_174909_f = Lists.<TileEntityBeacon.BeamSegment>newArrayList();
   private long field_146016_i;
   private float field_146014_j;
   private boolean field_146015_k;
   private boolean field_205737_j;
   private int field_146012_l = -1;
   @Nullable
   private Potion field_146013_m;
   @Nullable
   private Potion field_146010_n;
   private ItemStack field_146011_o = ItemStack.field_190927_a;
   private ITextComponent field_146008_p;

   public TileEntityBeacon() {
      super(TileEntityType.field_200984_o);
   }

   @Override
   public void func_73660_a() {
      if (this.field_145850_b.func_82737_E() % 80L == 0L) {
         this.func_174908_m();
         if (this.field_146015_k) {
            this.func_205736_a(SoundEvents.field_206939_L);
         }
      }

      if (!this.field_145850_b.field_72995_K && this.field_146015_k != this.field_205737_j) {
         this.field_205737_j = this.field_146015_k;
         this.func_205736_a(this.field_146015_k ? SoundEvents.field_206938_K : SoundEvents.field_206940_M);
      }
   }

   public void func_174908_m() {
      if (this.field_145850_b != null) {
         this.func_146003_y();
         this.func_146000_x();
      }
   }

   public void func_205736_a(SoundEvent var1) {
      this.field_145850_b.func_184133_a(null, this.field_174879_c, ☃, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   private void func_146000_x() {
      if (this.field_146015_k && this.field_146012_l > 0 && !this.field_145850_b.field_72995_K && this.field_146013_m != null) {
         double ☃ = (double)(this.field_146012_l * 10 + 10);
         int ☃x = 0;
         if (this.field_146012_l >= 4 && this.field_146013_m == this.field_146010_n) {
            ☃x = 1;
         }

         int ☃ = (9 + this.field_146012_l * 2) * 20;
         int ☃x = this.field_174879_c.func_177958_n();
         int ☃xx = this.field_174879_c.func_177956_o();
         int ☃xxx = this.field_174879_c.func_177952_p();
         AxisAlignedBB ☃xxxx = new AxisAlignedBB((double)☃x, (double)☃xx, (double)☃xxx, (double)(☃x + 1), (double)(☃xx + 1), (double)(☃xxx + 1))
            .func_186662_g(☃)
            .func_72321_a(0.0, (double)this.field_145850_b.func_72800_K(), 0.0);
         List<EntityPlayer> ☃xxxxx = this.field_145850_b.func_72872_a(EntityPlayer.class, ☃xxxx);

         for(EntityPlayer ☃xxxxxx : ☃xxxxx) {
            ☃xxxxxx.func_195064_c(new PotionEffect(this.field_146013_m, ☃, ☃x, true, true));
         }

         if (this.field_146012_l >= 4 && this.field_146013_m != this.field_146010_n && this.field_146010_n != null) {
            for(EntityPlayer ☃xxxxxx : ☃xxxxx) {
               ☃xxxxxx.func_195064_c(new PotionEffect(this.field_146010_n, ☃, 0, true, true));
            }
         }
      }
   }

   private void func_146003_y() {
      int ☃ = this.field_174879_c.func_177958_n();
      int ☃x = this.field_174879_c.func_177956_o();
      int ☃xx = this.field_174879_c.func_177952_p();
      int ☃xxx = this.field_146012_l;
      this.field_146012_l = 0;
      this.field_174909_f.clear();
      this.field_146015_k = true;
      TileEntityBeacon.BeamSegment ☃xxxx = new TileEntityBeacon.BeamSegment(EnumDyeColor.WHITE.func_193349_f());
      this.field_174909_f.add(☃xxxx);
      boolean ☃xxxxx = true;
      BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxx = ☃x + 1; ☃xxxxxxx < 256; ++☃xxxxxxx) {
         IBlockState ☃xxxxxxxxx = this.field_145850_b.func_180495_p(☃xxxxxx.func_181079_c(☃, ☃xxxxxxx, ☃xx));
         Block ☃xxxxxxxxxx = ☃xxxxxxxxx.func_177230_c();
         float[] ☃xxxxxxxx;
         if (☃xxxxxxxxxx instanceof BlockStainedGlass) {
            ☃xxxxxxxx = ((BlockStainedGlass)☃xxxxxxxxxx).func_196457_d().func_193349_f();
         } else {
            if (!(☃xxxxxxxxxx instanceof BlockStainedGlassPane)) {
               if (☃xxxxxxxxx.func_200016_a(this.field_145850_b, ☃xxxxxx) >= 15 && ☃xxxxxxxxxx != Blocks.field_150357_h) {
                  this.field_146015_k = false;
                  this.field_174909_f.clear();
                  break;
               }

               ☃xxxx.func_177262_a();
               continue;
            }

            ☃xxxxxxxx = ((BlockStainedGlassPane)☃xxxxxxxxxx).func_196419_d().func_193349_f();
         }

         if (!☃xxxxx) {
            ☃xxxxxxxx = new float[]{
               (☃xxxx.func_177263_b()[0] + ☃xxxxxxxx[0]) / 2.0F,
               (☃xxxx.func_177263_b()[1] + ☃xxxxxxxx[1]) / 2.0F,
               (☃xxxx.func_177263_b()[2] + ☃xxxxxxxx[2]) / 2.0F
            };
         }

         if (Arrays.equals(☃xxxxxxxx, ☃xxxx.func_177263_b())) {
            ☃xxxx.func_177262_a();
         } else {
            ☃xxxx = new TileEntityBeacon.BeamSegment(☃xxxxxxxx);
            this.field_174909_f.add(☃xxxx);
         }

         ☃xxxxx = false;
      }

      if (this.field_146015_k) {
         for(int ☃xxxxxxx = 1; ☃xxxxxxx <= 4; this.field_146012_l = ☃xxxxxxx++) {
            int ☃xxxxxxxx = ☃x - ☃xxxxxxx;
            if (☃xxxxxxxx < 0) {
               break;
            }

            boolean ☃xxxxxxxx = true;

            for(int ☃xxxxxxxxx = ☃ - ☃xxxxxxx; ☃xxxxxxxxx <= ☃ + ☃xxxxxxx && ☃xxxxxxxx; ++☃xxxxxxxxx) {
               for(int ☃xxxxxxxxxx = ☃xx - ☃xxxxxxx; ☃xxxxxxxxxx <= ☃xx + ☃xxxxxxx; ++☃xxxxxxxxxx) {
                  Block ☃xxxxxxxxxxx = this.field_145850_b.func_180495_p(new BlockPos(☃xxxxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx)).func_177230_c();
                  if (☃xxxxxxxxxxx != Blocks.field_150475_bE
                     && ☃xxxxxxxxxxx != Blocks.field_150340_R
                     && ☃xxxxxxxxxxx != Blocks.field_150484_ah
                     && ☃xxxxxxxxxxx != Blocks.field_150339_S) {
                     ☃xxxxxxxx = false;
                     break;
                  }
               }
            }

            if (!☃xxxxxxxx) {
               break;
            }
         }

         if (this.field_146012_l == 0) {
            this.field_146015_k = false;
         }
      }

      if (!this.field_145850_b.field_72995_K && ☃xxx < this.field_146012_l) {
         for(EntityPlayerMP ☃xxxxxxx : this.field_145850_b
            .func_72872_a(
               EntityPlayerMP.class,
               new AxisAlignedBB((double)☃, (double)☃x, (double)☃xx, (double)☃, (double)(☃x - 4), (double)☃xx).func_72314_b(10.0, 5.0, 10.0)
            )) {
            CriteriaTriggers.field_192131_k.func_192180_a(☃xxxxxxx, this);
         }
      }
   }

   public List<TileEntityBeacon.BeamSegment> func_174907_n() {
      return this.field_174909_f;
   }

   public float func_146002_i() {
      if (!this.field_146015_k) {
         return 0.0F;
      } else {
         int ☃ = (int)(this.field_145850_b.func_82737_E() - this.field_146016_i);
         this.field_146016_i = this.field_145850_b.func_82737_E();
         if (☃ > 1) {
            this.field_146014_j -= (float)☃ / 40.0F;
            if (this.field_146014_j < 0.0F) {
               this.field_146014_j = 0.0F;
            }
         }

         this.field_146014_j += 0.025F;
         if (this.field_146014_j > 1.0F) {
            this.field_146014_j = 1.0F;
         }

         return this.field_146014_j;
      }
   }

   public int func_191979_s() {
      return this.field_146012_l;
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 3, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   @Override
   public double func_145833_n() {
      return 65536.0;
   }

   @Nullable
   private static Potion func_184279_f(int var0) {
      Potion ☃ = Potion.func_188412_a(☃);
      return field_184280_f.contains(☃) ? ☃ : null;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_146013_m = func_184279_f(☃.func_74762_e("Primary"));
      this.field_146010_n = func_184279_f(☃.func_74762_e("Secondary"));
      this.field_146012_l = ☃.func_74762_e("Levels");
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74768_a("Primary", Potion.func_188409_a(this.field_146013_m));
      ☃.func_74768_a("Secondary", Potion.func_188409_a(this.field_146010_n));
      ☃.func_74768_a("Levels", this.field_146012_l);
      return ☃;
   }

   @Override
   public int func_70302_i_() {
      return 1;
   }

   @Override
   public boolean func_191420_l() {
      return this.field_146011_o.func_190926_b();
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return ☃ == 0 ? this.field_146011_o : ItemStack.field_190927_a;
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      if (☃ != 0 || this.field_146011_o.func_190926_b()) {
         return ItemStack.field_190927_a;
      } else if (☃ >= this.field_146011_o.func_190916_E()) {
         ItemStack ☃ = this.field_146011_o;
         this.field_146011_o = ItemStack.field_190927_a;
         return ☃;
      } else {
         return this.field_146011_o.func_77979_a(☃);
      }
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      if (☃ == 0) {
         ItemStack ☃ = this.field_146011_o;
         this.field_146011_o = ItemStack.field_190927_a;
         return ☃;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      if (☃ == 0) {
         this.field_146011_o = ☃;
      }
   }

   @Override
   public ITextComponent func_200200_C_() {
      return (ITextComponent)(this.field_146008_p != null ? this.field_146008_p : new TextComponentTranslation("container.beacon"));
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_146008_p != null;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_146008_p;
   }

   public void func_200227_a(@Nullable ITextComponent var1) {
      this.field_146008_p = ☃;
   }

   @Override
   public int func_70297_j_() {
      return 1;
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return !(
            ☃.func_70092_e(
                  (double)this.field_174879_c.func_177958_n() + 0.5,
                  (double)this.field_174879_c.func_177956_o() + 0.5,
                  (double)this.field_174879_c.func_177952_p() + 0.5
               )
               > 64.0
         );
      }
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
   }

   @Override
   public boolean func_94041_b(int var1, ItemStack var2) {
      return ☃.func_77973_b() == Items.field_151166_bC
         || ☃.func_77973_b() == Items.field_151045_i
         || ☃.func_77973_b() == Items.field_151043_k
         || ☃.func_77973_b() == Items.field_151042_j;
   }

   @Override
   public String func_174875_k() {
      return "minecraft:beacon";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerBeacon(☃, this);
   }

   @Override
   public int func_174887_a_(int var1) {
      switch(☃) {
         case 0:
            return this.field_146012_l;
         case 1:
            return Potion.func_188409_a(this.field_146013_m);
         case 2:
            return Potion.func_188409_a(this.field_146010_n);
         default:
            return 0;
      }
   }

   @Override
   public void func_174885_b(int var1, int var2) {
      switch(☃) {
         case 0:
            this.field_146012_l = ☃;
            break;
         case 1:
            this.field_146013_m = func_184279_f(☃);
            break;
         case 2:
            this.field_146010_n = func_184279_f(☃);
      }

      if (!this.field_145850_b.field_72995_K && ☃ == 1 && this.field_146015_k) {
         this.func_205736_a(SoundEvents.field_206941_N);
      }
   }

   @Override
   public int func_174890_g() {
      return 3;
   }

   @Override
   public void func_174888_l() {
      this.field_146011_o = ItemStack.field_190927_a;
   }

   @Override
   public boolean func_145842_c(int var1, int var2) {
      if (☃ == 1) {
         this.func_174908_m();
         return true;
      } else {
         return super.func_145842_c(☃, ☃);
      }
   }

   @Override
   public int[] func_180463_a(EnumFacing var1) {
      return new int[0];
   }

   @Override
   public boolean func_180462_a(int var1, ItemStack var2, @Nullable EnumFacing var3) {
      return false;
   }

   @Override
   public boolean func_180461_b(int var1, ItemStack var2, EnumFacing var3) {
      return false;
   }

   public static class BeamSegment {
      private final float[] field_177266_a;
      private int field_177265_b;

      public BeamSegment(float[] var1) {
         this.field_177266_a = ☃;
         this.field_177265_b = 1;
      }

      protected void func_177262_a() {
         ++this.field_177265_b;
      }

      public float[] func_177263_b() {
         return this.field_177266_a;
      }

      public int func_177264_c() {
         return this.field_177265_b;
      }
   }
}
