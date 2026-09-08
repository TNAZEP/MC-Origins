package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityTropicalFish extends AbstractGroupFish {
   private static final DataParameter<Integer> field_204223_b = EntityDataManager.func_187226_a(EntityTropicalFish.class, DataSerializers.field_187192_b);
   private static final ResourceLocation[] field_204224_c = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_a.png"), new ResourceLocation("textures/entity/fish/tropical_b.png")
   };
   private static final ResourceLocation[] field_204225_bx = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png")
   };
   private static final ResourceLocation[] field_204226_by = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png")
   };
   public static final int[] field_204227_bz = new int[]{
      func_204214_a(EntityTropicalFish.Type.STRIPEY, EnumDyeColor.ORANGE, EnumDyeColor.GRAY),
      func_204214_a(EntityTropicalFish.Type.FLOPPER, EnumDyeColor.GRAY, EnumDyeColor.GRAY),
      func_204214_a(EntityTropicalFish.Type.FLOPPER, EnumDyeColor.GRAY, EnumDyeColor.BLUE),
      func_204214_a(EntityTropicalFish.Type.CLAYFISH, EnumDyeColor.WHITE, EnumDyeColor.GRAY),
      func_204214_a(EntityTropicalFish.Type.SUNSTREAK, EnumDyeColor.BLUE, EnumDyeColor.GRAY),
      func_204214_a(EntityTropicalFish.Type.KOB, EnumDyeColor.ORANGE, EnumDyeColor.WHITE),
      func_204214_a(EntityTropicalFish.Type.SPOTTY, EnumDyeColor.PINK, EnumDyeColor.LIGHT_BLUE),
      func_204214_a(EntityTropicalFish.Type.BLOCKFISH, EnumDyeColor.PURPLE, EnumDyeColor.YELLOW),
      func_204214_a(EntityTropicalFish.Type.CLAYFISH, EnumDyeColor.WHITE, EnumDyeColor.RED),
      func_204214_a(EntityTropicalFish.Type.SPOTTY, EnumDyeColor.WHITE, EnumDyeColor.YELLOW),
      func_204214_a(EntityTropicalFish.Type.GLITTER, EnumDyeColor.WHITE, EnumDyeColor.GRAY),
      func_204214_a(EntityTropicalFish.Type.CLAYFISH, EnumDyeColor.WHITE, EnumDyeColor.ORANGE),
      func_204214_a(EntityTropicalFish.Type.DASHER, EnumDyeColor.CYAN, EnumDyeColor.PINK),
      func_204214_a(EntityTropicalFish.Type.BRINELY, EnumDyeColor.LIME, EnumDyeColor.LIGHT_BLUE),
      func_204214_a(EntityTropicalFish.Type.BETTY, EnumDyeColor.RED, EnumDyeColor.WHITE),
      func_204214_a(EntityTropicalFish.Type.SNOOPER, EnumDyeColor.GRAY, EnumDyeColor.RED),
      func_204214_a(EntityTropicalFish.Type.BLOCKFISH, EnumDyeColor.RED, EnumDyeColor.WHITE),
      func_204214_a(EntityTropicalFish.Type.FLOPPER, EnumDyeColor.WHITE, EnumDyeColor.YELLOW),
      func_204214_a(EntityTropicalFish.Type.KOB, EnumDyeColor.RED, EnumDyeColor.WHITE),
      func_204214_a(EntityTropicalFish.Type.SUNSTREAK, EnumDyeColor.GRAY, EnumDyeColor.WHITE),
      func_204214_a(EntityTropicalFish.Type.DASHER, EnumDyeColor.CYAN, EnumDyeColor.YELLOW),
      func_204214_a(EntityTropicalFish.Type.FLOPPER, EnumDyeColor.YELLOW, EnumDyeColor.YELLOW)
   };
   private boolean field_204228_bA = true;

   private static int func_204214_a(EntityTropicalFish.Type var0, EnumDyeColor var1, EnumDyeColor var2) {
      return ☃.func_212550_a() & 0xFF | (☃.func_212551_b() & 0xFF) << 8 | (☃.func_196059_a() & 0xFF) << 16 | (☃.func_196059_a() & 0xFF) << 24;
   }

   public EntityTropicalFish(World var1) {
      super(EntityType.field_204262_at, ☃);
      this.func_70105_a(0.5F, 0.4F);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_204223_b, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Variant", this.func_204221_dB());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_204215_a(☃.func_74762_e("Variant"));
   }

   public void func_204215_a(int var1) {
      this.field_70180_af.func_187227_b(field_204223_b, ☃);
   }

   @Override
   public boolean func_204209_c(int var1) {
      return !this.field_204228_bA;
   }

   public int func_204221_dB() {
      return this.field_70180_af.func_187225_a(field_204223_b);
   }

   @Override
   protected void func_204211_f(ItemStack var1) {
      super.func_204211_f(☃);
      NBTTagCompound ☃ = ☃.func_196082_o();
      ☃.func_74768_a("BucketVariantTag", this.func_204221_dB());
   }

   @Override
   protected ItemStack func_203707_dx() {
      return new ItemStack(Items.field_204272_aO);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_204311_aI;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_204411_iV;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_204412_iW;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_204414_iY;
   }

   @Override
   protected SoundEvent func_203701_dz() {
      return SoundEvents.field_204413_iX;
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (☃ != null && ☃.func_150297_b("BucketVariantTag", 3)) {
         this.func_204215_a(☃.func_74762_e("BucketVariantTag"));
         return ☃;
      } else {
         int ☃;
         int ☃x;
         int ☃xx;
         int ☃xxx;
         if (☃ instanceof EntityTropicalFish.GroupData) {
            EntityTropicalFish.GroupData ☃xxxx = (EntityTropicalFish.GroupData)☃;
            ☃ = ☃xxxx.field_204263_a;
            ☃x = ☃xxxx.field_204264_b;
            ☃xx = ☃xxxx.field_204265_c;
            ☃xxx = ☃xxxx.field_204266_d;
         } else if ((double)this.field_70146_Z.nextFloat() < 0.9) {
            int ☃ = field_204227_bz[this.field_70146_Z.nextInt(field_204227_bz.length)];
            ☃ = ☃ & 0xFF;
            ☃x = (☃ & 0xFF00) >> 8;
            ☃xx = (☃ & 0xFF0000) >> 16;
            ☃xxx = (☃ & 0xFF000000) >> 24;
            ☃ = new EntityTropicalFish.GroupData(this, ☃, ☃x, ☃xx, ☃xxx);
         } else {
            this.field_204228_bA = false;
            ☃ = this.field_70146_Z.nextInt(2);
            ☃x = this.field_70146_Z.nextInt(6);
            ☃xx = this.field_70146_Z.nextInt(15);
            ☃xxx = this.field_70146_Z.nextInt(15);
         }

         this.func_204215_a(☃ | ☃x << 8 | ☃xx << 16 | ☃xxx << 24);
         return ☃;
      }
   }

   static class GroupData extends AbstractGroupFish.GroupData {
      private final int field_204263_a;
      private final int field_204264_b;
      private final int field_204265_c;
      private final int field_204266_d;

      private GroupData(EntityTropicalFish var1, int var2, int var3, int var4, int var5) {
         super(☃);
         this.field_204263_a = ☃;
         this.field_204264_b = ☃;
         this.field_204265_c = ☃;
         this.field_204266_d = ☃;
      }
   }

   static enum Type {
      KOB(0, 0),
      SUNSTREAK(0, 1),
      SNOOPER(0, 2),
      DASHER(0, 3),
      BRINELY(0, 4),
      SPOTTY(0, 5),
      FLOPPER(1, 0),
      STRIPEY(1, 1),
      GLITTER(1, 2),
      BLOCKFISH(1, 3),
      BETTY(1, 4),
      CLAYFISH(1, 5);

      private final int field_212552_m;
      private final int field_212553_n;
      private static final EntityTropicalFish.Type[] field_212554_o = values();

      private Type(int var3, int var4) {
         this.field_212552_m = ☃;
         this.field_212553_n = ☃;
      }

      public int func_212550_a() {
         return this.field_212552_m;
      }

      public int func_212551_b() {
         return this.field_212553_n;
      }
   }
}
