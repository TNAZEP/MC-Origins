package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySheep extends EntityAnimal {
   private static final DataParameter<Byte> field_184774_bv = EntityDataManager.func_187226_a(EntitySheep.class, DataSerializers.field_187191_a);
   private final InventoryCrafting field_90016_e = new InventoryCrafting(new Container() {
      @Override
      public boolean func_75145_c(EntityPlayer var1) {
         return false;
      }
   }, 2, 1);
   private static final Map<EnumDyeColor, IItemProvider> field_200206_bz = Util.func_200696_a(Maps.newEnumMap(EnumDyeColor.class), var0 -> {
      var0.put(EnumDyeColor.WHITE, Blocks.field_196556_aL);
      var0.put(EnumDyeColor.ORANGE, Blocks.field_196557_aM);
      var0.put(EnumDyeColor.MAGENTA, Blocks.field_196558_aN);
      var0.put(EnumDyeColor.LIGHT_BLUE, Blocks.field_196559_aO);
      var0.put(EnumDyeColor.YELLOW, Blocks.field_196560_aP);
      var0.put(EnumDyeColor.LIME, Blocks.field_196561_aQ);
      var0.put(EnumDyeColor.PINK, Blocks.field_196562_aR);
      var0.put(EnumDyeColor.GRAY, Blocks.field_196563_aS);
      var0.put(EnumDyeColor.LIGHT_GRAY, Blocks.field_196564_aT);
      var0.put(EnumDyeColor.CYAN, Blocks.field_196565_aU);
      var0.put(EnumDyeColor.PURPLE, Blocks.field_196566_aV);
      var0.put(EnumDyeColor.BLUE, Blocks.field_196567_aW);
      var0.put(EnumDyeColor.BROWN, Blocks.field_196568_aX);
      var0.put(EnumDyeColor.GREEN, Blocks.field_196569_aY);
      var0.put(EnumDyeColor.RED, Blocks.field_196570_aZ);
      var0.put(EnumDyeColor.BLACK, Blocks.field_196602_ba);
   });
   private static final Map<EnumDyeColor, float[]> field_175514_bm = Maps.newEnumMap(
      (Map)Arrays.stream(EnumDyeColor.values()).collect(Collectors.toMap(var0 -> var0, EntitySheep::func_192020_c))
   );
   private int field_70899_e;
   private EntityAIEatGrass field_146087_bs;

   private static float[] func_192020_c(EnumDyeColor var0) {
      if (☃ == EnumDyeColor.WHITE) {
         return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
      } else {
         float[] ☃ = ☃.func_193349_f();
         float ☃x = 0.75F;
         return new float[]{☃[0] * 0.75F, ☃[1] * 0.75F, ☃[2] * 0.75F};
      }
   }

   public static float[] func_175513_a(EnumDyeColor var0) {
      return (float[])field_175514_bm.get(☃);
   }

   public EntitySheep(World var1) {
      super(EntityType.field_200737_ac, ☃);
      this.func_70105_a(0.9F, 1.3F);
   }

   @Override
   protected void func_184651_r() {
      this.field_146087_bs = new EntityAIEatGrass(this);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.25));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.1, Ingredient.func_199804_a(Items.field_151015_O), false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.1));
      this.field_70714_bg.func_75776_a(5, this.field_146087_bs);
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   @Override
   protected void func_70619_bc() {
      this.field_70899_e = this.field_146087_bs.func_151499_f();
      super.func_70619_bc();
   }

   @Override
   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.field_70899_e = Math.max(0, this.field_70899_e - 1);
      }

      super.func_70636_d();
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23F);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184774_bv, (byte)0);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      if (this.func_70892_o()) {
         return LootTableList.field_186403_K;
      } else {
         switch(this.func_175509_cj()) {
            case WHITE:
            default:
               return LootTableList.field_186404_L;
            case ORANGE:
               return LootTableList.field_186405_M;
            case MAGENTA:
               return LootTableList.field_186406_N;
            case LIGHT_BLUE:
               return LootTableList.field_186407_O;
            case YELLOW:
               return LootTableList.field_186408_P;
            case LIME:
               return LootTableList.field_186409_Q;
            case PINK:
               return LootTableList.field_186410_R;
            case GRAY:
               return LootTableList.field_186411_S;
            case LIGHT_GRAY:
               return LootTableList.field_197738_Y;
            case CYAN:
               return LootTableList.field_186413_U;
            case PURPLE:
               return LootTableList.field_186414_V;
            case BLUE:
               return LootTableList.field_186415_W;
            case BROWN:
               return LootTableList.field_186416_X;
            case GREEN:
               return LootTableList.field_186417_Y;
            case RED:
               return LootTableList.field_186418_Z;
            case BLACK:
               return LootTableList.field_186376_aa;
         }
      }
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 10) {
         this.field_70899_e = 40;
      } else {
         super.func_70103_a(☃);
      }
   }

   public float func_70894_j(float var1) {
      if (this.field_70899_e <= 0) {
         return 0.0F;
      } else if (this.field_70899_e >= 4 && this.field_70899_e <= 36) {
         return 1.0F;
      } else {
         return this.field_70899_e < 4 ? ((float)this.field_70899_e - ☃) / 4.0F : -((float)(this.field_70899_e - 40) - ☃) / 4.0F;
      }
   }

   public float func_70890_k(float var1) {
      if (this.field_70899_e > 4 && this.field_70899_e <= 36) {
         float ☃ = ((float)(this.field_70899_e - 4) - ☃) / 32.0F;
         return (float) (Math.PI / 5) + 0.21991149F * MathHelper.func_76126_a(☃ * 28.7F);
      } else {
         return this.field_70899_e > 0 ? (float) (Math.PI / 5) : this.field_70125_A * (float) (Math.PI / 180.0);
      }
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151097_aZ && !this.func_70892_o() && !this.func_70631_g_()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_70893_e(true);
            int ☃x = 1 + this.field_70146_Z.nextInt(3);

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               EntityItem ☃xxx = this.func_199702_a((IItemProvider)field_200206_bz.get(this.func_175509_cj()), 1);
               if (☃xxx != null) {
                  ☃xxx.field_70181_x += (double)(this.field_70146_Z.nextFloat() * 0.05F);
                  ☃xxx.field_70159_w += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
                  ☃xxx.field_70179_y += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
               }
            }
         }

         ☃.func_77972_a(1, ☃);
         this.func_184185_a(SoundEvents.field_187763_eJ, 1.0F, 1.0F);
      }

      return super.func_184645_a(☃, ☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("Sheared", this.func_70892_o());
      ☃.func_74774_a("Color", (byte)this.func_175509_cj().func_196059_a());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70893_e(☃.func_74767_n("Sheared"));
      this.func_175512_b(EnumDyeColor.func_196056_a(☃.func_74771_c("Color")));
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187757_eG;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187761_eI;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187759_eH;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187765_eK, 0.15F, 1.0F);
   }

   public EnumDyeColor func_175509_cj() {
      return EnumDyeColor.func_196056_a(this.field_70180_af.func_187225_a(field_184774_bv) & 15);
   }

   public void func_175512_b(EnumDyeColor var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184774_bv);
      this.field_70180_af.func_187227_b(field_184774_bv, (byte)(☃ & 240 | ☃.func_196059_a() & 15));
   }

   public boolean func_70892_o() {
      return (this.field_70180_af.func_187225_a(field_184774_bv) & 16) != 0;
   }

   public void func_70893_e(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184774_bv);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184774_bv, (byte)(☃ | 16));
      } else {
         this.field_70180_af.func_187227_b(field_184774_bv, (byte)(☃ & -17));
      }
   }

   public static EnumDyeColor func_175510_a(Random var0) {
      int ☃ = ☃.nextInt(100);
      if (☃ < 5) {
         return EnumDyeColor.BLACK;
      } else if (☃ < 10) {
         return EnumDyeColor.GRAY;
      } else if (☃ < 15) {
         return EnumDyeColor.LIGHT_GRAY;
      } else if (☃ < 18) {
         return EnumDyeColor.BROWN;
      } else {
         return ☃.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE;
      }
   }

   public EntitySheep func_90011_a(EntityAgeable var1) {
      EntitySheep ☃ = (EntitySheep)☃;
      EntitySheep ☃x = new EntitySheep(this.field_70170_p);
      ☃x.func_175512_b(this.func_175511_a(this, ☃));
      return ☃x;
   }

   @Override
   public void func_70615_aA() {
      this.func_70893_e(false);
      if (this.func_70631_g_()) {
         this.func_110195_a(60);
      }
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      this.func_175512_b(func_175510_a(this.field_70170_p.field_73012_v));
      return ☃;
   }

   private EnumDyeColor func_175511_a(EntityAnimal var1, EntityAnimal var2) {
      EnumDyeColor ☃x = ((EntitySheep)☃).func_175509_cj();
      EnumDyeColor ☃xx = ((EntitySheep)☃).func_175509_cj();
      this.field_90016_e.func_70299_a(0, new ItemStack(ItemDye.func_195961_a(☃x)));
      this.field_90016_e.func_70299_a(1, new ItemStack(ItemDye.func_195961_a(☃xx)));
      ItemStack ☃xxx = ☃.field_70170_p.func_199532_z().func_199514_a(this.field_90016_e, ((EntitySheep)☃).field_70170_p);
      Item ☃xxxx = ☃xxx.func_77973_b();
      EnumDyeColor ☃;
      if (☃xxxx instanceof ItemDye) {
         ☃ = ((ItemDye)☃xxxx).func_195962_g();
      } else {
         ☃ = this.field_70170_p.field_73012_v.nextBoolean() ? ☃x : ☃xx;
      }

      return ☃;
   }

   @Override
   public float func_70047_e() {
      return 0.95F * this.field_70131_O;
   }
}
