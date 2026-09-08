package net.minecraft.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityElderGuardian extends EntityGuardian {
   public EntityElderGuardian(World var1) {
      super(EntityType.field_200800_n, ☃);
      this.func_70105_a(this.field_70130_N * 2.35F, this.field_70131_O * 2.35F);
      this.func_110163_bv();
      if (this.field_175481_bq != null) {
         this.field_175481_bq.func_179479_b(400);
      }
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3F);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186441_w;
   }

   @Override
   public int func_175464_ck() {
      return 60;
   }

   public void func_190767_di() {
      this.field_175485_bl = 1.0F;
      this.field_175486_bm = this.field_175485_bl;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return this.func_203005_aq() ? SoundEvents.field_187512_aB : SoundEvents.field_187513_aC;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return this.func_203005_aq() ? SoundEvents.field_187517_aG : SoundEvents.field_187518_aH;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return this.func_203005_aq() ? SoundEvents.field_187515_aE : SoundEvents.field_187516_aF;
   }

   @Override
   protected SoundEvent func_190765_dj() {
      return SoundEvents.field_191240_aK;
   }

   @Override
   protected void func_70619_bc() {
      super.func_70619_bc();
      int ☃ = 1200;
      if ((this.field_70173_aa + this.func_145782_y()) % 1200 == 0) {
         Potion ☃x = MobEffects.field_76419_f;
         List<EntityPlayerMP> ☃xx = this.field_70170_p
            .func_175661_b(EntityPlayerMP.class, var1x -> this.func_70068_e(var1x) < 2500.0 && var1x.field_71134_c.func_180239_c());
         int ☃xxx = 2;
         int ☃xxxx = 6000;
         int ☃xxxxx = 1200;

         for(EntityPlayerMP ☃xxxxxx : ☃xx) {
            if (!☃xxxxxx.func_70644_a(☃x) || ☃xxxxxx.func_70660_b(☃x).func_76458_c() < 2 || ☃xxxxxx.func_70660_b(☃x).func_76459_b() < 1200) {
               ☃xxxxxx.field_71135_a.func_147359_a(new SPacketChangeGameState(10, 0.0F));
               ☃xxxxxx.func_195064_c(new PotionEffect(☃x, 6000, 2));
            }
         }
      }

      if (!this.func_110175_bO()) {
         this.func_175449_a(new BlockPos(this), 16);
      }
   }
}
