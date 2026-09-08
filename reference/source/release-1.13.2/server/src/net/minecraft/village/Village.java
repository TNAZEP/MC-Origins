package net.minecraft.village;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathType;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Village {
   private World field_75586_a;
   private final List<VillageDoorInfo> field_75584_b = Lists.<VillageDoorInfo>newArrayList();
   private BlockPos field_75585_c = BlockPos.field_177992_a;
   private BlockPos field_75582_d = BlockPos.field_177992_a;
   private int field_75583_e;
   private int field_75580_f;
   private int field_75581_g;
   private int field_75588_h;
   private int field_82694_i;
   private final Map<String, Integer> field_82693_j = Maps.newHashMap();
   private final List<Village.VillageAggressor> field_75589_i = Lists.<Village.VillageAggressor>newArrayList();
   private int field_75587_j;

   public Village() {
   }

   public Village(World var1) {
      this.field_75586_a = ☃;
   }

   public void func_82691_a(World var1) {
      this.field_75586_a = ☃;
   }

   public void func_75560_a(int var1) {
      this.field_75581_g = ☃;
      this.func_75557_k();
      this.func_75565_j();
      if (☃ % 20 == 0) {
         this.func_75572_i();
      }

      if (☃ % 30 == 0) {
         this.func_75579_h();
      }

      int ☃ = this.field_75588_h / 10;
      if (this.field_75587_j < ☃ && this.field_75584_b.size() > 20 && this.field_75586_a.field_73012_v.nextInt(7000) == 0) {
         Entity ☃x = this.func_208059_f(this.field_75582_d);
         if (☃x != null) {
            ++this.field_75587_j;
         }
      }
   }

   @Nullable
   private Entity func_208059_f(BlockPos var1) {
      for(int ☃ = 0; ☃ < 10; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(
            this.field_75586_a.field_73012_v.nextInt(16) - 8, this.field_75586_a.field_73012_v.nextInt(6) - 3, this.field_75586_a.field_73012_v.nextInt(16) - 8
         );
         if (this.func_179866_a(☃x)) {
            EntityIronGolem ☃xx = EntityType.field_200757_aw.func_210761_b(this.field_75586_a, null, null, null, ☃x, false, false);
            if (☃xx != null) {
               if (☃xx.func_205020_a(this.field_75586_a, false) && ☃xx.func_205019_a(this.field_75586_a)) {
                  this.field_75586_a.func_72838_d(☃xx);
                  return ☃xx;
               }

               ☃xx.func_70106_y();
            }
         }
      }

      return null;
   }

   private void func_75579_h() {
      List<EntityIronGolem> ☃ = this.field_75586_a
         .func_72872_a(
            EntityIronGolem.class,
            new AxisAlignedBB(
               (double)(this.field_75582_d.func_177958_n() - this.field_75583_e),
               (double)(this.field_75582_d.func_177956_o() - 4),
               (double)(this.field_75582_d.func_177952_p() - this.field_75583_e),
               (double)(this.field_75582_d.func_177958_n() + this.field_75583_e),
               (double)(this.field_75582_d.func_177956_o() + 4),
               (double)(this.field_75582_d.func_177952_p() + this.field_75583_e)
            )
         );
      this.field_75587_j = ☃.size();
   }

   private void func_75572_i() {
      List<EntityVillager> ☃ = this.field_75586_a
         .func_72872_a(
            EntityVillager.class,
            new AxisAlignedBB(
               (double)(this.field_75582_d.func_177958_n() - this.field_75583_e),
               (double)(this.field_75582_d.func_177956_o() - 4),
               (double)(this.field_75582_d.func_177952_p() - this.field_75583_e),
               (double)(this.field_75582_d.func_177958_n() + this.field_75583_e),
               (double)(this.field_75582_d.func_177956_o() + 4),
               (double)(this.field_75582_d.func_177952_p() + this.field_75583_e)
            )
         );
      this.field_75588_h = ☃.size();
      if (this.field_75588_h == 0) {
         this.field_82693_j.clear();
      }
   }

   public BlockPos func_180608_a() {
      return this.field_75582_d;
   }

   public int func_75568_b() {
      return this.field_75583_e;
   }

   public int func_75567_c() {
      return this.field_75584_b.size();
   }

   public int func_75561_d() {
      return this.field_75581_g - this.field_75580_f;
   }

   public int func_75562_e() {
      return this.field_75588_h;
   }

   public boolean func_179866_a(BlockPos var1) {
      return this.field_75582_d.func_177951_i(☃) < (double)(this.field_75583_e * this.field_75583_e);
   }

   public List<VillageDoorInfo> func_75558_f() {
      return this.field_75584_b;
   }

   public VillageDoorInfo func_179865_b(BlockPos var1) {
      VillageDoorInfo ☃ = null;
      int ☃x = Integer.MAX_VALUE;

      for(VillageDoorInfo ☃xx : this.field_75584_b) {
         int ☃xxx = ☃xx.func_179848_a(☃);
         if (☃xxx < ☃x) {
            ☃ = ☃xx;
            ☃x = ☃xxx;
         }
      }

      return ☃;
   }

   public VillageDoorInfo func_179863_c(BlockPos var1) {
      VillageDoorInfo ☃ = null;
      int ☃x = Integer.MAX_VALUE;

      for(VillageDoorInfo ☃xx : this.field_75584_b) {
         int ☃xxx = ☃xx.func_179848_a(☃);
         if (☃xxx > 256) {
            ☃xxx *= 1000;
         } else {
            ☃xxx = ☃xx.func_75468_f();
         }

         if (☃xxx < ☃x) {
            BlockPos ☃xxx = ☃xx.func_179852_d();
            EnumFacing ☃xxxx = ☃xx.func_188567_j();
            if (this.field_75586_a.func_180495_p(☃xxx.func_177967_a(☃xxxx, 1)).func_196957_g(this.field_75586_a, ☃xxx.func_177967_a(☃xxxx, 1), PathType.LAND)
               && this.field_75586_a
                  .func_180495_p(☃xxx.func_177967_a(☃xxxx, -1))
                  .func_196957_g(this.field_75586_a, ☃xxx.func_177967_a(☃xxxx, -1), PathType.LAND)
               && this.field_75586_a
                  .func_180495_p(☃xxx.func_177984_a().func_177967_a(☃xxxx, 1))
                  .func_196957_g(this.field_75586_a, ☃xxx.func_177984_a().func_177967_a(☃xxxx, 1), PathType.LAND)
               && this.field_75586_a
                  .func_180495_p(☃xxx.func_177984_a().func_177967_a(☃xxxx, -1))
                  .func_196957_g(this.field_75586_a, ☃xxx.func_177984_a().func_177967_a(☃xxxx, -1), PathType.LAND)) {
               ☃ = ☃xx;
               ☃x = ☃xxx;
            }
         }
      }

      return ☃;
   }

   @Nullable
   public VillageDoorInfo func_179864_e(BlockPos var1) {
      if (this.field_75582_d.func_177951_i(☃) > (double)(this.field_75583_e * this.field_75583_e)) {
         return null;
      } else {
         for(VillageDoorInfo ☃ : this.field_75584_b) {
            if (☃.func_179852_d().func_177958_n() == ☃.func_177958_n()
               && ☃.func_179852_d().func_177952_p() == ☃.func_177952_p()
               && Math.abs(☃.func_179852_d().func_177956_o() - ☃.func_177956_o()) <= 1) {
               return ☃;
            }
         }

         return null;
      }
   }

   public void func_75576_a(VillageDoorInfo var1) {
      this.field_75584_b.add(☃);
      this.field_75585_c = this.field_75585_c.func_177971_a(☃.func_179852_d());
      this.func_75573_l();
      this.field_75580_f = ☃.func_75473_b();
   }

   public boolean func_75566_g() {
      return this.field_75584_b.isEmpty();
   }

   public void func_75575_a(EntityLivingBase var1) {
      for(Village.VillageAggressor ☃ : this.field_75589_i) {
         if (☃.field_75592_a == ☃) {
            ☃.field_75590_b = this.field_75581_g;
            return;
         }
      }

      this.field_75589_i.add(new Village.VillageAggressor(☃, this.field_75581_g));
   }

   @Nullable
   public EntityLivingBase func_75571_b(EntityLivingBase var1) {
      double ☃ = Double.MAX_VALUE;
      Village.VillageAggressor ☃x = null;

      for(int ☃xx = 0; ☃xx < this.field_75589_i.size(); ++☃xx) {
         Village.VillageAggressor ☃xxx = (Village.VillageAggressor)this.field_75589_i.get(☃xx);
         double ☃xxxx = ☃xxx.field_75592_a.func_70068_e(☃);
         if (!(☃xxxx > ☃)) {
            ☃x = ☃xxx;
            ☃ = ☃xxxx;
         }
      }

      return ☃x == null ? null : ☃x.field_75592_a;
   }

   public EntityPlayer func_82685_c(EntityLivingBase var1) {
      double ☃ = Double.MAX_VALUE;
      EntityPlayer ☃x = null;

      for(String ☃xx : this.field_82693_j.keySet()) {
         if (this.func_82687_d(☃xx)) {
            EntityPlayer ☃xxx = this.field_75586_a.func_72924_a(☃xx);
            if (☃xxx != null) {
               double ☃xxxx = ☃xxx.func_70068_e(☃);
               if (!(☃xxxx > ☃)) {
                  ☃x = ☃xxx;
                  ☃ = ☃xxxx;
               }
            }
         }
      }

      return ☃x;
   }

   private void func_75565_j() {
      Iterator<Village.VillageAggressor> ☃ = this.field_75589_i.iterator();

      while(☃.hasNext()) {
         Village.VillageAggressor ☃x = (Village.VillageAggressor)☃.next();
         if (!☃x.field_75592_a.func_70089_S() || Math.abs(this.field_75581_g - ☃x.field_75590_b) > 300) {
            ☃.remove();
         }
      }
   }

   private void func_75557_k() {
      boolean ☃ = false;
      boolean ☃x = this.field_75586_a.field_73012_v.nextInt(50) == 0;
      Iterator<VillageDoorInfo> ☃xx = this.field_75584_b.iterator();

      while(☃xx.hasNext()) {
         VillageDoorInfo ☃xxx = (VillageDoorInfo)☃xx.next();
         if (☃x) {
            ☃xxx.func_75466_d();
         }

         if (!this.func_179860_f(☃xxx.func_179852_d()) || Math.abs(this.field_75581_g - ☃xxx.func_75473_b()) > 1200) {
            this.field_75585_c = this.field_75585_c.func_177973_b(☃xxx.func_179852_d());
            ☃ = true;
            ☃xxx.func_179853_a(true);
            ☃xx.remove();
         }
      }

      if (☃) {
         this.func_75573_l();
      }
   }

   private boolean func_179860_f(BlockPos var1) {
      IBlockState ☃ = this.field_75586_a.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      if (☃x instanceof BlockDoor) {
         return ☃.func_185904_a() == Material.field_151575_d;
      } else {
         return false;
      }
   }

   private void func_75573_l() {
      int ☃ = this.field_75584_b.size();
      if (☃ == 0) {
         this.field_75582_d = BlockPos.field_177992_a;
         this.field_75583_e = 0;
      } else {
         this.field_75582_d = new BlockPos(
            this.field_75585_c.func_177958_n() / ☃, this.field_75585_c.func_177956_o() / ☃, this.field_75585_c.func_177952_p() / ☃
         );
         int ☃ = 0;

         for(VillageDoorInfo ☃x : this.field_75584_b) {
            ☃ = Math.max(☃x.func_179848_a(this.field_75582_d), ☃);
         }

         this.field_75583_e = Math.max(32, (int)Math.sqrt((double)☃) + 1);
      }
   }

   public int func_82684_a(String var1) {
      Integer ☃ = (Integer)this.field_82693_j.get(☃);
      return ☃ == null ? 0 : ☃;
   }

   public int func_82688_a(String var1, int var2) {
      int ☃ = this.func_82684_a(☃);
      int ☃x = MathHelper.func_76125_a(☃ + ☃, -30, 10);
      this.field_82693_j.put(☃, ☃x);
      return ☃x;
   }

   public boolean func_82687_d(String var1) {
      return this.func_82684_a(☃) <= -15;
   }

   public void func_82690_a(NBTTagCompound var1) {
      this.field_75588_h = ☃.func_74762_e("PopSize");
      this.field_75583_e = ☃.func_74762_e("Radius");
      this.field_75587_j = ☃.func_74762_e("Golems");
      this.field_75580_f = ☃.func_74762_e("Stable");
      this.field_75581_g = ☃.func_74762_e("Tick");
      this.field_82694_i = ☃.func_74762_e("MTick");
      this.field_75582_d = new BlockPos(☃.func_74762_e("CX"), ☃.func_74762_e("CY"), ☃.func_74762_e("CZ"));
      this.field_75585_c = new BlockPos(☃.func_74762_e("ACX"), ☃.func_74762_e("ACY"), ☃.func_74762_e("ACZ"));
      NBTTagList ☃ = ☃.func_150295_c("Doors", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         VillageDoorInfo ☃xxx = new VillageDoorInfo(
            new BlockPos(☃xx.func_74762_e("X"), ☃xx.func_74762_e("Y"), ☃xx.func_74762_e("Z")),
            ☃xx.func_74762_e("IDX"),
            ☃xx.func_74762_e("IDZ"),
            ☃xx.func_74762_e("TS")
         );
         this.field_75584_b.add(☃xxx);
      }

      NBTTagList ☃x = ☃.func_150295_c("Players", 10);

      for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
         NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
         if (☃xxx.func_74764_b("UUID") && this.field_75586_a != null && this.field_75586_a.func_73046_m() != null) {
            PlayerProfileCache ☃xxxx = this.field_75586_a.func_73046_m().func_152358_ax();
            GameProfile ☃xxxxx = ☃xxxx.func_152652_a(UUID.fromString(☃xxx.func_74779_i("UUID")));
            if (☃xxxxx != null) {
               this.field_82693_j.put(☃xxxxx.getName(), ☃xxx.func_74762_e("S"));
            }
         } else {
            this.field_82693_j.put(☃xxx.func_74779_i("Name"), ☃xxx.func_74762_e("S"));
         }
      }
   }

   public void func_82689_b(NBTTagCompound var1) {
      ☃.func_74768_a("PopSize", this.field_75588_h);
      ☃.func_74768_a("Radius", this.field_75583_e);
      ☃.func_74768_a("Golems", this.field_75587_j);
      ☃.func_74768_a("Stable", this.field_75580_f);
      ☃.func_74768_a("Tick", this.field_75581_g);
      ☃.func_74768_a("MTick", this.field_82694_i);
      ☃.func_74768_a("CX", this.field_75582_d.func_177958_n());
      ☃.func_74768_a("CY", this.field_75582_d.func_177956_o());
      ☃.func_74768_a("CZ", this.field_75582_d.func_177952_p());
      ☃.func_74768_a("ACX", this.field_75585_c.func_177958_n());
      ☃.func_74768_a("ACY", this.field_75585_c.func_177956_o());
      ☃.func_74768_a("ACZ", this.field_75585_c.func_177952_p());
      NBTTagList ☃ = new NBTTagList();

      for(VillageDoorInfo ☃x : this.field_75584_b) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         ☃xx.func_74768_a("X", ☃x.func_179852_d().func_177958_n());
         ☃xx.func_74768_a("Y", ☃x.func_179852_d().func_177956_o());
         ☃xx.func_74768_a("Z", ☃x.func_179852_d().func_177952_p());
         ☃xx.func_74768_a("IDX", ☃x.func_179847_f());
         ☃xx.func_74768_a("IDZ", ☃x.func_179855_g());
         ☃xx.func_74768_a("TS", ☃x.func_75473_b());
         ☃.add((INBTBase)☃xx);
      }

      ☃.func_74782_a("Doors", ☃);
      NBTTagList ☃x = new NBTTagList();

      for(String ☃xx : this.field_82693_j.keySet()) {
         NBTTagCompound ☃xxx = new NBTTagCompound();
         PlayerProfileCache ☃xxxx = this.field_75586_a.func_73046_m().func_152358_ax();

         try {
            GameProfile ☃xxxxx = ☃xxxx.func_152655_a(☃xx);
            if (☃xxxxx != null) {
               ☃xxx.func_74778_a("UUID", ☃xxxxx.getId().toString());
               ☃xxx.func_74768_a("S", this.field_82693_j.get(☃xx));
               ☃x.add((INBTBase)☃xxx);
            }
         } catch (RuntimeException var9) {
         }
      }

      ☃.func_74782_a("Players", ☃x);
   }

   public void func_82692_h() {
      this.field_82694_i = this.field_75581_g;
   }

   public boolean func_82686_i() {
      return this.field_82694_i == 0 || this.field_75581_g - this.field_82694_i >= 3600;
   }

   public void func_82683_b(int var1) {
      for(String ☃ : this.field_82693_j.keySet()) {
         this.func_82688_a(☃, ☃);
      }
   }

   class VillageAggressor {
      public EntityLivingBase field_75592_a;
      public int field_75590_b;

      VillageAggressor(EntityLivingBase var2, int var3) {
         this.field_75592_a = ☃;
         this.field_75590_b = ☃;
      }
   }
}
