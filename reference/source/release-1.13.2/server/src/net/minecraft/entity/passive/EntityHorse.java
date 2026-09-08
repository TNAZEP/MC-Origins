package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityHorse extends AbstractHorse {
   private static final UUID field_184786_bD = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final DataParameter<Integer> field_184789_bG = EntityDataManager.func_187226_a(EntityHorse.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184791_bI = EntityDataManager.func_187226_a(EntityHorse.class, DataSerializers.field_187192_b);
   private static final String[] field_110268_bz = new String[]{
      "textures/entity/horse/horse_white.png",
      "textures/entity/horse/horse_creamy.png",
      "textures/entity/horse/horse_chestnut.png",
      "textures/entity/horse/horse_brown.png",
      "textures/entity/horse/horse_black.png",
      "textures/entity/horse/horse_gray.png",
      "textures/entity/horse/horse_darkbrown.png"
   };
   private static final String[] field_110269_bA = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
   private static final String[] field_110291_bB = new String[]{
      null,
      "textures/entity/horse/horse_markings_white.png",
      "textures/entity/horse/horse_markings_whitefield.png",
      "textures/entity/horse/horse_markings_whitedots.png",
      "textures/entity/horse/horse_markings_blackdots.png"
   };
   private static final String[] field_110292_bC = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
   private String field_110286_bQ;
   private final String[] field_110280_bR = new String[3];

   public EntityHorse(World var1) {
      super(EntityType.field_200762_B, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184789_bG, 0);
      this.field_70180_af.func_187214_a(field_184791_bI, HorseArmorType.NONE.func_188579_a());
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Variant", this.func_110202_bQ());
      if (!this.field_110296_bG.func_70301_a(1).func_190926_b()) {
         ☃.func_74782_a("ArmorItem", this.field_110296_bG.func_70301_a(1).func_77955_b(new NBTTagCompound()));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_110235_q(☃.func_74762_e("Variant"));
      if (☃.func_150297_b("ArmorItem", 10)) {
         ItemStack ☃ = ItemStack.func_199557_a(☃.func_74775_l("ArmorItem"));
         if (!☃.func_190926_b() && HorseArmorType.func_188577_b(☃.func_77973_b())) {
            this.field_110296_bG.func_70299_a(1, ☃);
         }
      }

      this.func_110232_cE();
   }

   public void func_110235_q(int var1) {
      this.field_70180_af.func_187227_b(field_184789_bG, ☃);
      this.func_110230_cF();
   }

   public int func_110202_bQ() {
      return this.field_70180_af.func_187225_a(field_184789_bG);
   }

   private void func_110230_cF() {
      this.field_110286_bQ = null;
   }

   @Override
   protected void func_110232_cE() {
      super.func_110232_cE();
      this.func_146086_d(this.field_110296_bG.func_70301_a(1));
   }

   public void func_146086_d(ItemStack var1) {
      HorseArmorType ☃ = HorseArmorType.func_188580_a(☃);
      this.field_70180_af.func_187227_b(field_184791_bI, ☃.func_188579_a());
      this.func_110230_cF();
      if (!this.field_70170_p.field_72995_K) {
         this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_188479_b(field_184786_bD);
         int ☃x = ☃.func_188578_c();
         if (☃x != 0) {
            this.func_110148_a(SharedMonsterAttributes.field_188791_g)
               .func_111121_a(new AttributeModifier(field_184786_bD, "Horse armor bonus", (double)☃x, 0).func_111168_a(false));
         }
      }
   }

   public HorseArmorType func_184783_dl() {
      return HorseArmorType.func_188575_a(this.field_70180_af.func_187225_a(field_184791_bI));
   }

   @Override
   public void func_76316_a(IInventory var1) {
      HorseArmorType ☃ = this.func_184783_dl();
      super.func_76316_a(☃);
      HorseArmorType ☃x = this.func_184783_dl();
      if (this.field_70173_aa > 20 && ☃ != ☃x && ☃x != HorseArmorType.NONE) {
         this.func_184185_a(SoundEvents.field_187702_cm, 0.5F, 1.0F);
      }
   }

   @Override
   protected void func_190680_a(SoundType var1) {
      super.func_190680_a(☃);
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_184185_a(SoundEvents.field_187705_cn, ☃.func_185843_a() * 0.6F, ☃.func_185847_b());
      }
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.func_110267_cL());
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(this.func_110203_cN());
      this.func_110148_a(field_110271_bv).func_111128_a(this.func_110245_cM());
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K && this.field_70180_af.func_187223_a()) {
         this.field_70180_af.func_187230_e();
         this.func_110230_cF();
      }
   }

   @Override
   protected SoundEvent func_184639_G() {
      super.func_184639_G();
      return SoundEvents.field_187696_ck;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      super.func_184615_bR();
      return SoundEvents.field_187708_co;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      super.func_184601_bQ(☃);
      return SoundEvents.field_187717_cr;
   }

   @Override
   protected SoundEvent func_184785_dv() {
      super.func_184785_dv();
      return SoundEvents.field_187699_cl;
   }

   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186396_D;
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      boolean ☃x = !☃.func_190926_b();
      if (☃x && ☃.func_77973_b() instanceof ItemSpawnEgg) {
         return super.func_184645_a(☃, ☃);
      } else {
         if (!this.func_70631_g_()) {
            if (this.func_110248_bS() && ☃.func_70093_af()) {
               this.func_110199_f(☃);
               return true;
            }

            if (this.func_184207_aI()) {
               return super.func_184645_a(☃, ☃);
            }
         }

         if (☃x) {
            if (this.func_190678_b(☃, ☃)) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }

               return true;
            }

            if (☃.func_111282_a(☃, this, ☃)) {
               return true;
            }

            if (!this.func_110248_bS()) {
               this.func_190687_dF();
               return true;
            }

            boolean ☃ = HorseArmorType.func_188580_a(☃) != HorseArmorType.NONE;
            boolean ☃x = !this.func_70631_g_() && !this.func_110257_ck() && ☃.func_77973_b() == Items.field_151141_av;
            if (☃ || ☃x) {
               this.func_110199_f(☃);
               return true;
            }
         }

         if (this.func_70631_g_()) {
            return super.func_184645_a(☃, ☃);
         } else {
            this.func_110237_h(☃);
            return true;
         }
      }
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      if (☃ == this) {
         return false;
      } else if (!(☃ instanceof EntityDonkey) && !(☃ instanceof EntityHorse)) {
         return false;
      } else {
         return this.func_110200_cJ() && ((AbstractHorse)☃).func_110200_cJ();
      }
   }

   @Override
   public EntityAgeable func_90011_a(EntityAgeable var1) {
      AbstractHorse ☃;
      if (☃ instanceof EntityDonkey) {
         ☃ = new EntityMule(this.field_70170_p);
      } else {
         EntityHorse ☃x = (EntityHorse)☃;
         ☃ = new EntityHorse(this.field_70170_p);
         int ☃xx = this.field_70146_Z.nextInt(9);
         int ☃;
         if (☃xx < 4) {
            ☃ = this.func_110202_bQ() & 0xFF;
         } else if (☃xx < 8) {
            ☃ = ☃x.func_110202_bQ() & 0xFF;
         } else {
            ☃ = this.field_70146_Z.nextInt(7);
         }

         int ☃ = this.field_70146_Z.nextInt(5);
         if (☃ < 2) {
            ☃ |= this.func_110202_bQ() & 0xFF00;
         } else if (☃ < 4) {
            ☃ |= ☃x.func_110202_bQ() & 0xFF00;
         } else {
            ☃ |= this.field_70146_Z.nextInt(5) << 8 & 0xFF00;
         }

         ((EntityHorse)☃).func_110235_q(☃);
      }

      this.func_190681_a(☃, ☃);
      return ☃;
   }

   @Override
   public boolean func_190677_dK() {
      return true;
   }

   @Override
   public boolean func_190682_f(ItemStack var1) {
      return HorseArmorType.func_188577_b(☃.func_77973_b());
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      int ☃;
      if (☃ instanceof EntityHorse.GroupData) {
         ☃ = ((EntityHorse.GroupData)☃).field_190885_a;
      } else {
         ☃ = this.field_70146_Z.nextInt(7);
         ☃ = new EntityHorse.GroupData(☃);
      }

      this.func_110235_q(☃ | this.field_70146_Z.nextInt(5) << 8);
      return ☃;
   }

   public static class GroupData implements IEntityLivingData {
      public int field_190885_a;

      public GroupData(int var1) {
         this.field_190885_a = ☃;
      }
   }
}
