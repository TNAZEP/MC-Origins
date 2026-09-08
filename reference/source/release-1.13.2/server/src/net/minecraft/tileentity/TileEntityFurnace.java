package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityFurnace extends TileEntityLockable implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickable {
   private static final int[] field_145962_k = new int[]{0};
   private static final int[] field_145959_l = new int[]{2, 1};
   private static final int[] field_145960_m = new int[]{1};
   private NonNullList<ItemStack> field_145957_n = NonNullList.func_191197_a(3, ItemStack.field_190927_a);
   private int field_145956_a;
   private int field_145963_i;
   private int field_174906_k;
   private int field_174905_l;
   private ITextComponent field_145958_o;
   private final Map<ResourceLocation, Integer> field_203901_m = Maps.newHashMap();

   private static void func_201563_a(Map<Item, Integer> var0, Tag<Item> var1, int var2) {
      for(Item ☃ : ☃.func_199885_a()) {
         ☃.put(☃, ☃);
      }
   }

   private static void func_203065_a(Map<Item, Integer> var0, IItemProvider var1, int var2) {
      ☃.put(☃.func_199767_j(), ☃);
   }

   public static Map<Item, Integer> func_201564_p() {
      Map<Item, Integer> ☃ = Maps.newLinkedHashMap();
      func_203065_a(☃, Items.field_151129_at, 20000);
      func_203065_a(☃, Blocks.field_150402_ci, 16000);
      func_203065_a(☃, Items.field_151072_bj, 2400);
      func_203065_a(☃, Items.field_151044_h, 1600);
      func_203065_a(☃, Items.field_196155_l, 1600);
      func_201563_a(☃, ItemTags.field_200038_h, 300);
      func_201563_a(☃, ItemTags.field_199905_b, 300);
      func_201563_a(☃, ItemTags.field_202898_h, 300);
      func_201563_a(☃, ItemTags.field_202899_i, 150);
      func_201563_a(☃, ItemTags.field_212188_k, 300);
      func_201563_a(☃, ItemTags.field_202900_j, 300);
      func_203065_a(☃, Blocks.field_180407_aO, 300);
      func_203065_a(☃, Blocks.field_180404_aQ, 300);
      func_203065_a(☃, Blocks.field_180408_aP, 300);
      func_203065_a(☃, Blocks.field_180403_aR, 300);
      func_203065_a(☃, Blocks.field_180406_aS, 300);
      func_203065_a(☃, Blocks.field_180405_aT, 300);
      func_203065_a(☃, Blocks.field_180390_bo, 300);
      func_203065_a(☃, Blocks.field_180392_bq, 300);
      func_203065_a(☃, Blocks.field_180391_bp, 300);
      func_203065_a(☃, Blocks.field_180386_br, 300);
      func_203065_a(☃, Blocks.field_180385_bs, 300);
      func_203065_a(☃, Blocks.field_180387_bt, 300);
      func_203065_a(☃, Blocks.field_196586_al, 300);
      func_203065_a(☃, Blocks.field_150342_X, 300);
      func_203065_a(☃, Blocks.field_150421_aI, 300);
      func_203065_a(☃, Blocks.field_150486_ae, 300);
      func_203065_a(☃, Blocks.field_150447_bR, 300);
      func_203065_a(☃, Blocks.field_150462_ai, 300);
      func_203065_a(☃, Blocks.field_150453_bW, 300);
      func_201563_a(☃, ItemTags.field_202901_n, 300);
      func_203065_a(☃, Items.field_151031_f, 300);
      func_203065_a(☃, Items.field_151112_aM, 300);
      func_203065_a(☃, Blocks.field_150468_ap, 300);
      func_203065_a(☃, Items.field_151155_ap, 200);
      func_203065_a(☃, Items.field_151038_n, 200);
      func_203065_a(☃, Items.field_151041_m, 200);
      func_203065_a(☃, Items.field_151017_I, 200);
      func_203065_a(☃, Items.field_151053_p, 200);
      func_203065_a(☃, Items.field_151039_o, 200);
      func_201563_a(☃, ItemTags.field_200154_g, 200);
      func_201563_a(☃, ItemTags.field_202902_o, 200);
      func_201563_a(☃, ItemTags.field_199904_a, 100);
      func_201563_a(☃, ItemTags.field_200153_d, 100);
      func_203065_a(☃, Items.field_151055_y, 100);
      func_201563_a(☃, ItemTags.field_200037_g, 100);
      func_203065_a(☃, Items.field_151054_z, 100);
      func_201563_a(☃, ItemTags.field_200035_e, 67);
      func_203065_a(☃, Blocks.field_203216_jz, 4001);
      return ☃;
   }

   public TileEntityFurnace() {
      super(TileEntityType.field_200971_b);
   }

   @Override
   public int func_70302_i_() {
      return this.field_145957_n.size();
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_145957_n) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return this.field_145957_n.get(☃);
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      return ItemStackHelper.func_188382_a(this.field_145957_n, ☃, ☃);
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      return ItemStackHelper.func_188383_a(this.field_145957_n, ☃);
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      ItemStack ☃ = this.field_145957_n.get(☃);
      boolean ☃x = !☃.func_190926_b() && ☃.func_77969_a(☃) && ItemStack.func_77970_a(☃, ☃);
      this.field_145957_n.set(☃, ☃);
      if (☃.func_190916_E() > this.func_70297_j_()) {
         ☃.func_190920_e(this.func_70297_j_());
      }

      if (☃ == 0 && !☃x) {
         this.field_174905_l = this.func_201562_r();
         this.field_174906_k = 0;
         this.func_70296_d();
      }
   }

   @Override
   public ITextComponent func_200200_C_() {
      return (ITextComponent)(this.field_145958_o != null ? this.field_145958_o : new TextComponentTranslation("container.furnace"));
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_145958_o != null;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_145958_o;
   }

   public void func_200225_a(@Nullable ITextComponent var1) {
      this.field_145958_o = ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145957_n = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(☃, this.field_145957_n);
      this.field_145956_a = ☃.func_74765_d("BurnTime");
      this.field_174906_k = ☃.func_74765_d("CookTime");
      this.field_174905_l = ☃.func_74765_d("CookTimeTotal");
      this.field_145963_i = func_145952_a(this.field_145957_n.get(1));
      int ☃ = ☃.func_74765_d("RecipesUsedSize");

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ResourceLocation ☃xx = new ResourceLocation(☃.func_74779_i("RecipeLocation" + ☃x));
         int ☃xxx = ☃.func_74762_e("RecipeAmount" + ☃x);
         this.field_203901_m.put(☃xx, ☃xxx);
      }

      if (☃.func_150297_b("CustomName", 8)) {
         this.field_145958_o = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName"));
      }
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74777_a("BurnTime", (short)this.field_145956_a);
      ☃.func_74777_a("CookTime", (short)this.field_174906_k);
      ☃.func_74777_a("CookTimeTotal", (short)this.field_174905_l);
      ItemStackHelper.func_191282_a(☃, this.field_145957_n);
      ☃.func_74777_a("RecipesUsedSize", (short)this.field_203901_m.size());
      int ☃ = 0;

      for(Entry<ResourceLocation, Integer> ☃x : this.field_203901_m.entrySet()) {
         ☃.func_74778_a("RecipeLocation" + ☃, ((ResourceLocation)☃x.getKey()).toString());
         ☃.func_74768_a("RecipeAmount" + ☃, ☃x.getValue());
         ++☃;
      }

      if (this.field_145958_o != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(this.field_145958_o));
      }

      return ☃;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   private boolean func_145950_i() {
      return this.field_145956_a > 0;
   }

   @Override
   public void func_73660_a() {
      boolean ☃ = this.func_145950_i();
      boolean ☃x = false;
      if (this.func_145950_i()) {
         --this.field_145956_a;
      }

      if (!this.field_145850_b.field_72995_K) {
         ItemStack ☃ = this.field_145957_n.get(1);
         if (this.func_145950_i() || !☃.func_190926_b() && !this.field_145957_n.get(0).func_190926_b()) {
            IRecipe ☃x = this.field_145850_b.func_199532_z().func_199515_b(this, this.field_145850_b);
            if (!this.func_145950_i() && this.func_201566_b(☃x)) {
               this.field_145956_a = func_145952_a(☃);
               this.field_145963_i = this.field_145956_a;
               if (this.func_145950_i()) {
                  ☃x = true;
                  if (!☃.func_190926_b()) {
                     Item ☃xx = ☃.func_77973_b();
                     ☃.func_190918_g(1);
                     if (☃.func_190926_b()) {
                        Item ☃xxx = ☃xx.func_77668_q();
                        this.field_145957_n.set(1, ☃xxx == null ? ItemStack.field_190927_a : new ItemStack(☃xxx));
                     }
                  }
               }
            }

            if (this.func_145950_i() && this.func_201566_b(☃x)) {
               ++this.field_174906_k;
               if (this.field_174906_k == this.field_174905_l) {
                  this.field_174906_k = 0;
                  this.field_174905_l = this.func_201562_r();
                  this.func_201565_c(☃x);
                  ☃x = true;
               }
            } else {
               this.field_174906_k = 0;
            }
         } else if (!this.func_145950_i() && this.field_174906_k > 0) {
            this.field_174906_k = MathHelper.func_76125_a(this.field_174906_k - 2, 0, this.field_174905_l);
         }

         if (☃ != this.func_145950_i()) {
            ☃x = true;
            this.field_145850_b
               .func_180501_a(
                  this.field_174879_c,
                  this.field_145850_b.func_180495_p(this.field_174879_c).func_206870_a(BlockFurnace.field_196325_b, Boolean.valueOf(this.func_145950_i())),
                  3
               );
         }
      }

      if (☃x) {
         this.func_70296_d();
      }
   }

   private int func_201562_r() {
      FurnaceRecipe ☃ = (FurnaceRecipe)this.field_145850_b.func_199532_z().func_199515_b(this, this.field_145850_b);
      return ☃ != null ? ☃.func_201830_h() : 200;
   }

   private boolean func_201566_b(@Nullable IRecipe var1) {
      if (!this.field_145957_n.get(0).func_190926_b() && ☃ != null) {
         ItemStack ☃ = ☃.func_77571_b();
         if (☃.func_190926_b()) {
            return false;
         } else {
            ItemStack ☃ = this.field_145957_n.get(2);
            if (☃.func_190926_b()) {
               return true;
            } else if (!☃.func_77969_a(☃)) {
               return false;
            } else if (☃.func_190916_E() < this.func_70297_j_() && ☃.func_190916_E() < ☃.func_77976_d()) {
               return true;
            } else {
               return ☃.func_190916_E() < ☃.func_77976_d();
            }
         }
      } else {
         return false;
      }
   }

   private void func_201565_c(@Nullable IRecipe var1) {
      if (☃ != null && this.func_201566_b(☃)) {
         ItemStack ☃ = this.field_145957_n.get(0);
         ItemStack ☃x = ☃.func_77571_b();
         ItemStack ☃xx = this.field_145957_n.get(2);
         if (☃xx.func_190926_b()) {
            this.field_145957_n.set(2, ☃x.func_77946_l());
         } else if (☃xx.func_77973_b() == ☃x.func_77973_b()) {
            ☃xx.func_190917_f(1);
         }

         if (!this.field_145850_b.field_72995_K) {
            this.func_201561_a(this.field_145850_b, null, ☃);
         }

         if (☃.func_77973_b() == Blocks.field_196577_ad.func_199767_j()
            && !this.field_145957_n.get(1).func_190926_b()
            && this.field_145957_n.get(1).func_77973_b() == Items.field_151133_ar) {
            this.field_145957_n.set(1, new ItemStack(Items.field_151131_as));
         }

         ☃.func_190918_g(1);
      }
   }

   private static int func_145952_a(ItemStack var0) {
      if (☃.func_190926_b()) {
         return 0;
      } else {
         Item ☃ = ☃.func_77973_b();
         return func_201564_p().getOrDefault(☃, 0);
      }
   }

   public static boolean func_145954_b(ItemStack var0) {
      return func_201564_p().containsKey(☃.func_77973_b());
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
      if (☃ == 2) {
         return false;
      } else if (☃ != 1) {
         return true;
      } else {
         ItemStack ☃ = this.field_145957_n.get(1);
         return func_145954_b(☃) || SlotFurnaceFuel.func_178173_c_(☃) && ☃.func_77973_b() != Items.field_151133_ar;
      }
   }

   @Override
   public int[] func_180463_a(EnumFacing var1) {
      if (☃ == EnumFacing.DOWN) {
         return field_145959_l;
      } else {
         return ☃ == EnumFacing.UP ? field_145962_k : field_145960_m;
      }
   }

   @Override
   public boolean func_180462_a(int var1, ItemStack var2, @Nullable EnumFacing var3) {
      return this.func_94041_b(☃, ☃);
   }

   @Override
   public boolean func_180461_b(int var1, ItemStack var2, EnumFacing var3) {
      if (☃ == EnumFacing.DOWN && ☃ == 1) {
         Item ☃ = ☃.func_77973_b();
         if (☃ != Items.field_151131_as && ☃ != Items.field_151133_ar) {
            return false;
         }
      }

      return true;
   }

   @Override
   public String func_174875_k() {
      return "minecraft:furnace";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerFurnace(☃, this);
   }

   @Override
   public int func_174887_a_(int var1) {
      switch(☃) {
         case 0:
            return this.field_145956_a;
         case 1:
            return this.field_145963_i;
         case 2:
            return this.field_174906_k;
         case 3:
            return this.field_174905_l;
         default:
            return 0;
      }
   }

   @Override
   public void func_174885_b(int var1, int var2) {
      switch(☃) {
         case 0:
            this.field_145956_a = ☃;
            break;
         case 1:
            this.field_145963_i = ☃;
            break;
         case 2:
            this.field_174906_k = ☃;
            break;
         case 3:
            this.field_174905_l = ☃;
      }
   }

   @Override
   public int func_174890_g() {
      return 4;
   }

   @Override
   public void func_174888_l() {
      this.field_145957_n.clear();
   }

   @Override
   public void func_194018_a(RecipeItemHelper var1) {
      for(ItemStack ☃ : this.field_145957_n) {
         ☃.func_194112_a(☃);
      }
   }

   @Override
   public void func_193056_a(IRecipe var1) {
      if (this.field_203901_m.containsKey(☃.func_199560_c())) {
         this.field_203901_m.put(☃.func_199560_c(), this.field_203901_m.get(☃.func_199560_c()) + 1);
      } else {
         this.field_203901_m.put(☃.func_199560_c(), 1);
      }
   }

   @Nullable
   @Override
   public IRecipe func_193055_i() {
      return null;
   }

   public Map<ResourceLocation, Integer> func_203900_q() {
      return this.field_203901_m;
   }

   @Override
   public boolean func_201561_a(World var1, EntityPlayerMP var2, @Nullable IRecipe var3) {
      if (☃ != null) {
         this.func_193056_a(☃);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void func_201560_d(EntityPlayer var1) {
      if (!this.field_145850_b.func_82736_K().func_82766_b("doLimitedCrafting")) {
         List<IRecipe> ☃ = Lists.<IRecipe>newArrayList();

         for(ResourceLocation ☃x : this.field_203901_m.keySet()) {
            IRecipe ☃xx = ☃.field_70170_p.func_199532_z().func_199517_a(☃x);
            if (☃xx != null) {
               ☃.add(☃xx);
            }
         }

         ☃.func_195065_a(☃);
      }

      this.field_203901_m.clear();
   }
}
