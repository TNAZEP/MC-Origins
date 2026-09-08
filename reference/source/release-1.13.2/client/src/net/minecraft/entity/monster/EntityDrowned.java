package net.minecraft.entity.monster;

import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTurtle;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityDrowned extends EntityZombie implements IRangedAttackMob {
   private boolean field_204718_bx;
   protected final PathNavigateSwimmer field_204716_a;
   protected final PathNavigateGround field_204717_b;

   public EntityDrowned(World var1) {
      super(EntityType.field_204724_o, ☃);
      this.field_70138_W = 1.0F;
      this.field_70765_h = new EntityDrowned.MoveHelper(this);
      this.func_184644_a(PathNodeType.WATER, 0.0F);
      this.field_204716_a = new PathNavigateSwimmer(this, ☃);
      this.field_204717_b = new PathNavigateGround(this, ☃);
   }

   @Override
   protected void func_175456_n() {
      this.field_70714_bg.func_75776_a(1, new EntityDrowned.AIGoToWater(this, 1.0));
      this.field_70714_bg.func_75776_a(2, new EntityDrowned.AITridentAttack(this, 1.0, 40, 10.0F));
      this.field_70714_bg.func_75776_a(2, new EntityDrowned.AIAttack(this, 1.0, false));
      this.field_70714_bg.func_75776_a(5, new EntityDrowned.AIGoToBeach(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityDrowned.AISwimUp(this, 1.0, this.field_70170_p.func_181545_F()));
      this.field_70714_bg.func_75776_a(7, new EntityAIWander(this, 1.0));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, EntityDrowned.class));
      this.field_70715_bh
         .func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, new EntityDrowned.AttackTargetPredicate(this)));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
      this.field_70715_bh.func_75776_a(5, new EntityAINearestAttackableTarget(this, EntityTurtle.class, 10, true, false, EntityTurtle.field_203029_bx));
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      return super.func_175447_b(☃);
   }

   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (this.func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && this.field_70146_Z.nextFloat() < 0.03F) {
         this.func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_205157_eZ));
         this.field_82174_bp[EntityEquipmentSlot.OFFHAND.func_188454_b()] = 2.0F;
      }

      return ☃;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      Biome ☃ = ☃.func_180494_b(new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v));
      if (☃ != Biomes.field_76781_i && ☃ != Biomes.field_76777_m) {
         return this.field_70146_Z.nextInt(40) == 0 && this.func_204712_dC() && super.func_205020_a(☃, ☃);
      } else {
         return this.field_70146_Z.nextInt(15) == 0 && super.func_205020_a(☃, ☃);
      }
   }

   private boolean func_204712_dC() {
      return this.func_174813_aQ().field_72338_b < (double)(this.field_70170_p.func_181545_F() - 5);
   }

   @Override
   protected boolean func_204900_dz() {
      return false;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_204770_aM;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return this.func_70090_H() ? SoundEvents.field_204775_aZ : SoundEvents.field_204774_aY;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return this.func_70090_H() ? SoundEvents.field_204779_bd : SoundEvents.field_204778_bc;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return this.func_70090_H() ? SoundEvents.field_204777_bb : SoundEvents.field_204776_ba;
   }

   @Override
   protected SoundEvent func_190731_di() {
      return SoundEvents.field_204781_bf;
   }

   @Override
   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_204782_bg;
   }

   @Override
   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }

   @Override
   protected void func_180481_a(DifficultyInstance var1) {
      if ((double)this.field_70146_Z.nextFloat() > 0.9) {
         int ☃ = this.field_70146_Z.nextInt(16);
         if (☃ < 10) {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_203184_eO));
         } else {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151112_aM));
         }
      }
   }

   @Override
   protected boolean func_208003_a(ItemStack var1, ItemStack var2, EntityEquipmentSlot var3) {
      if (☃.func_77973_b() == Items.field_205157_eZ) {
         return false;
      } else if (☃.func_77973_b() == Items.field_203184_eO) {
         if (☃.func_77973_b() == Items.field_203184_eO) {
            return ☃.func_77952_i() < ☃.func_77952_i();
         } else {
            return false;
         }
      } else {
         return ☃.func_77973_b() == Items.field_203184_eO ? true : super.func_208003_a(☃, ☃, ☃);
      }
   }

   @Override
   protected boolean func_204703_dA() {
      return false;
   }

   @Override
   public boolean func_205019_a(IWorldReaderBase var1) {
      return ☃.func_195587_c(this, this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ());
   }

   public boolean func_204714_e(@Nullable EntityLivingBase var1) {
      if (☃ != null) {
         return !this.field_70170_p.func_72935_r() || ☃.func_70090_H();
      } else {
         return false;
      }
   }

   @Override
   public boolean func_96092_aw() {
      return !this.func_203007_ba();
   }

   private boolean func_204715_dF() {
      if (this.field_204718_bx) {
         return true;
      } else {
         EntityLivingBase ☃ = this.func_70638_az();
         return ☃ != null && ☃.func_70090_H();
      }
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_70613_aW() && this.func_70090_H() && this.func_204715_dF()) {
         this.func_191958_b(☃, ☃, ☃, 0.01F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.9F;
         this.field_70181_x *= 0.9F;
         this.field_70179_y *= 0.9F;
      } else {
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   @Override
   public void func_205343_av() {
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70613_aW() && this.func_70090_H() && this.func_204715_dF()) {
            this.field_70699_by = this.field_204716_a;
            this.func_204711_a(true);
         } else {
            this.field_70699_by = this.field_204717_b;
            this.func_204711_a(false);
         }
      }
   }

   protected boolean func_204710_dB() {
      Path ☃ = this.func_70661_as().func_75505_d();
      if (☃ != null) {
         PathPoint ☃x = ☃.func_189964_i();
         if (☃x != null) {
            double ☃xx = this.func_70092_e((double)☃x.field_75839_a, (double)☃x.field_75837_b, (double)☃x.field_75838_c);
            if (☃xx < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      EntityTrident ☃ = new EntityTrident(this.field_70170_p, this, new ItemStack(Items.field_203184_eO));
      double ☃x = ☃.field_70165_t - this.field_70165_t;
      double ☃xx = ☃.func_174813_aQ().field_72338_b + (double)(☃.field_70131_O / 3.0F) - ☃.field_70163_u;
      double ☃xxx = ☃.field_70161_v - this.field_70161_v;
      double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx);
      ☃.func_70186_c(☃x, ☃xx + ☃xxxx * 0.2F, ☃xxx, 1.6F, (float)(14 - this.field_70170_p.func_175659_aa().func_151525_a() * 4));
      this.func_184185_a(SoundEvents.field_204780_be, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(☃);
   }

   public void func_204713_s(boolean var1) {
      this.field_204718_bx = ☃;
   }

   static class AIAttack extends EntityAIZombieAttack {
      private final EntityDrowned field_204726_g;

      public AIAttack(EntityDrowned var1, double var2, boolean var4) {
         super(☃, ☃, ☃);
         this.field_204726_g = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return super.func_75250_a() && this.field_204726_g.func_204714_e(this.field_204726_g.func_70638_az());
      }

      @Override
      public boolean func_75253_b() {
         return super.func_75253_b() && this.field_204726_g.func_204714_e(this.field_204726_g.func_70638_az());
      }
   }

   static class AIGoToBeach extends EntityAIMoveToBlock {
      private final EntityDrowned field_204727_f;

      public AIGoToBeach(EntityDrowned var1, double var2) {
         super(☃, ☃, 8, 2);
         this.field_204727_f = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return super.func_75250_a()
            && !this.field_204727_f.field_70170_p.func_72935_r()
            && this.field_204727_f.func_70090_H()
            && this.field_204727_f.field_70163_u >= (double)(this.field_204727_f.field_70170_p.func_181545_F() - 3);
      }

      @Override
      public boolean func_75253_b() {
         return super.func_75253_b();
      }

      @Override
      protected boolean func_179488_a(IWorldReaderBase var1, BlockPos var2) {
         BlockPos ☃ = ☃.func_177984_a();
         return ☃.func_175623_d(☃) && ☃.func_175623_d(☃.func_177984_a()) ? ☃.func_180495_p(☃).func_185896_q() : false;
      }

      @Override
      public void func_75249_e() {
         this.field_204727_f.func_204713_s(false);
         this.field_204727_f.field_70699_by = this.field_204727_f.field_204717_b;
         super.func_75249_e();
      }

      @Override
      public void func_75251_c() {
         super.func_75251_c();
      }
   }

   static class AIGoToWater extends EntityAIBase {
      private final EntityCreature field_204730_a;
      private double field_204731_b;
      private double field_204732_c;
      private double field_204733_d;
      private final double field_204734_e;
      private final World field_204735_f;

      public AIGoToWater(EntityCreature var1, double var2) {
         this.field_204730_a = ☃;
         this.field_204734_e = ☃;
         this.field_204735_f = ☃.field_70170_p;
         this.func_75248_a(1);
      }

      @Override
      public boolean func_75250_a() {
         if (!this.field_204735_f.func_72935_r()) {
            return false;
         } else if (this.field_204730_a.func_70090_H()) {
            return false;
         } else {
            Vec3d ☃ = this.func_204729_f();
            if (☃ == null) {
               return false;
            } else {
               this.field_204731_b = ☃.field_72450_a;
               this.field_204732_c = ☃.field_72448_b;
               this.field_204733_d = ☃.field_72449_c;
               return true;
            }
         }
      }

      @Override
      public boolean func_75253_b() {
         return !this.field_204730_a.func_70661_as().func_75500_f();
      }

      @Override
      public void func_75249_e() {
         this.field_204730_a.func_70661_as().func_75492_a(this.field_204731_b, this.field_204732_c, this.field_204733_d, this.field_204734_e);
      }

      @Nullable
      private Vec3d func_204729_f() {
         Random ☃ = this.field_204730_a.func_70681_au();
         BlockPos ☃x = new BlockPos(this.field_204730_a.field_70165_t, this.field_204730_a.func_174813_aQ().field_72338_b, this.field_204730_a.field_70161_v);

         for(int ☃xx = 0; ☃xx < 10; ++☃xx) {
            BlockPos ☃xxx = ☃x.func_177982_a(☃.nextInt(20) - 10, 2 - ☃.nextInt(8), ☃.nextInt(20) - 10);
            if (this.field_204735_f.func_180495_p(☃xxx).func_177230_c() == Blocks.field_150355_j) {
               return new Vec3d((double)☃xxx.func_177958_n(), (double)☃xxx.func_177956_o(), (double)☃xxx.func_177952_p());
            }
         }

         return null;
      }
   }

   static class AISwimUp extends EntityAIBase {
      private final EntityDrowned field_204736_a;
      private final double field_204737_b;
      private final int field_204738_c;
      private boolean field_204739_d;

      public AISwimUp(EntityDrowned var1, double var2, int var4) {
         this.field_204736_a = ☃;
         this.field_204737_b = ☃;
         this.field_204738_c = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return !this.field_204736_a.field_70170_p.func_72935_r()
            && this.field_204736_a.func_70090_H()
            && this.field_204736_a.field_70163_u < (double)(this.field_204738_c - 2);
      }

      @Override
      public boolean func_75253_b() {
         return this.func_75250_a() && !this.field_204739_d;
      }

      @Override
      public void func_75246_d() {
         if (this.field_204736_a.field_70163_u < (double)(this.field_204738_c - 1)
            && (this.field_204736_a.func_70661_as().func_75500_f() || this.field_204736_a.func_204710_dB())) {
            Vec3d ☃ = RandomPositionGenerator.func_75464_a(
               this.field_204736_a, 4, 8, new Vec3d(this.field_204736_a.field_70165_t, (double)(this.field_204738_c - 1), this.field_204736_a.field_70161_v)
            );
            if (☃ == null) {
               this.field_204739_d = true;
               return;
            }

            this.field_204736_a.func_70661_as().func_75492_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, this.field_204737_b);
         }
      }

      @Override
      public void func_75249_e() {
         this.field_204736_a.func_204713_s(true);
         this.field_204739_d = false;
      }

      @Override
      public void func_75251_c() {
         this.field_204736_a.func_204713_s(false);
      }
   }

   static class AITridentAttack extends EntityAIAttackRanged {
      private final EntityDrowned field_204728_a;

      public AITridentAttack(IRangedAttackMob var1, double var2, int var4, float var5) {
         super(☃, ☃, ☃, ☃);
         this.field_204728_a = (EntityDrowned)☃;
      }

      @Override
      public boolean func_75250_a() {
         return super.func_75250_a() && this.field_204728_a.func_184614_ca().func_77973_b() == Items.field_203184_eO;
      }

      @Override
      public void func_75249_e() {
         super.func_75249_e();
         this.field_204728_a.func_184724_a(true);
      }

      @Override
      public void func_75251_c() {
         super.func_75251_c();
         this.field_204728_a.func_184724_a(false);
      }
   }

   static class AttackTargetPredicate implements Predicate<EntityPlayer> {
      private final EntityDrowned field_204740_a;

      public AttackTargetPredicate(EntityDrowned var1) {
         this.field_204740_a = ☃;
      }

      public boolean test(@Nullable EntityPlayer var1) {
         return this.field_204740_a.func_204714_e(☃);
      }
   }

   static class MoveHelper extends EntityMoveHelper {
      private final EntityDrowned field_204725_i;

      public MoveHelper(EntityDrowned var1) {
         super(☃);
         this.field_204725_i = ☃;
      }

      @Override
      public void func_75641_c() {
         EntityLivingBase ☃ = this.field_204725_i.func_70638_az();
         if (this.field_204725_i.func_204715_dF() && this.field_204725_i.func_70090_H()) {
            if (☃ != null && ☃.field_70163_u > this.field_204725_i.field_70163_u || this.field_204725_i.field_204718_bx) {
               this.field_204725_i.field_70181_x += 0.002;
            }

            if (this.field_188491_h != EntityMoveHelper.Action.MOVE_TO || this.field_204725_i.func_70661_as().func_75500_f()) {
               this.field_204725_i.func_70659_e(0.0F);
               return;
            }

            double ☃x = this.field_75646_b - this.field_204725_i.field_70165_t;
            double ☃xx = this.field_75647_c - this.field_204725_i.field_70163_u;
            double ☃xxx = this.field_75644_d - this.field_204725_i.field_70161_v;
            double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx);
            ☃xx /= ☃xxxx;
            float ☃xxxxx = (float)(MathHelper.func_181159_b(☃xxx, ☃x) * 180.0F / (float)Math.PI) - 90.0F;
            this.field_204725_i.field_70177_z = this.func_75639_a(this.field_204725_i.field_70177_z, ☃xxxxx, 90.0F);
            this.field_204725_i.field_70761_aq = this.field_204725_i.field_70177_z;
            float ☃xxxxxx = (float)(this.field_75645_e * this.field_204725_i.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            this.field_204725_i.func_70659_e(this.field_204725_i.func_70689_ay() + (☃xxxxxx - this.field_204725_i.func_70689_ay()) * 0.125F);
            this.field_204725_i.field_70181_x += (double)this.field_204725_i.func_70689_ay() * ☃xx * 0.1;
            this.field_204725_i.field_70159_w += (double)this.field_204725_i.func_70689_ay() * ☃x * 0.005;
            this.field_204725_i.field_70179_y += (double)this.field_204725_i.func_70689_ay() * ☃xxx * 0.005;
         } else {
            if (!this.field_204725_i.field_70122_E) {
               this.field_204725_i.field_70181_x -= 0.008;
            }

            super.func_75641_c();
         }
      }
   }
}
