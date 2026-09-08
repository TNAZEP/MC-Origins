package net.minecraft.world.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndCrystalTowerFeature;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.EndSpikes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager {
   private static final Logger field_186107_a = LogManager.getLogger();
   private static final Predicate<Entity> field_186108_b = EntitySelectors.field_94557_a.and(EntitySelectors.func_188443_a(0.0, 128.0, 0.0, 192.0));
   private final BossInfoServer field_186109_c = (BossInfoServer)new BossInfoServer(
         new TextComponentTranslation("entity.minecraft.ender_dragon"), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS
      )
      .func_186742_b(true)
      .func_186743_c(true);
   private final WorldServer field_186110_d;
   private final List<Integer> field_186111_e = Lists.newArrayList();
   private final BlockPattern field_186112_f;
   private int field_186113_g;
   private int field_186114_h;
   private int field_186115_i;
   private int field_186116_j;
   private boolean field_186117_k;
   private boolean field_186118_l;
   private UUID field_186119_m;
   private boolean field_186120_n = true;
   private BlockPos field_186121_o;
   private DragonSpawnState field_186122_p;
   private int field_186123_q;
   private List<EntityEnderCrystal> field_186124_r;

   public DragonFightManager(WorldServer var1, NBTTagCompound var2) {
      this.field_186110_d = ☃;
      if (☃.func_150297_b("DragonKilled", 99)) {
         if (☃.func_186855_b("DragonUUID")) {
            this.field_186119_m = ☃.func_186857_a("DragonUUID");
         }

         this.field_186117_k = ☃.func_74767_n("DragonKilled");
         this.field_186118_l = ☃.func_74767_n("PreviouslyKilled");
         if (☃.func_74767_n("IsRespawning")) {
            this.field_186122_p = DragonSpawnState.START;
         }

         if (☃.func_150297_b("ExitPortalLocation", 10)) {
            this.field_186121_o = NBTUtil.func_186861_c(☃.func_74775_l("ExitPortalLocation"));
         }
      } else {
         this.field_186117_k = true;
         this.field_186118_l = true;
      }

      if (☃.func_150297_b("Gateways", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("Gateways", 3);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_186111_e.add(☃.func_186858_c(☃x));
         }
      } else {
         this.field_186111_e.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.field_186111_e, new Random(☃.func_72905_C()));
      }

      this.field_186112_f = FactoryBlockPattern.func_177660_a()
         .func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .func_177659_a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
         .func_177659_a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
         .func_177662_a('#', BlockWorldState.func_177510_a(BlockMatcher.func_177642_a(Blocks.field_150357_h)))
         .func_177661_b();
   }

   public NBTTagCompound func_186088_a() {
      NBTTagCompound ☃ = new NBTTagCompound();
      if (this.field_186119_m != null) {
         ☃.func_186854_a("DragonUUID", this.field_186119_m);
      }

      ☃.func_74757_a("DragonKilled", this.field_186117_k);
      ☃.func_74757_a("PreviouslyKilled", this.field_186118_l);
      if (this.field_186121_o != null) {
         ☃.func_74782_a("ExitPortalLocation", NBTUtil.func_186859_a(this.field_186121_o));
      }

      NBTTagList ☃ = new NBTTagList();

      for(int ☃x : this.field_186111_e) {
         ☃.add((INBTBase)(new NBTTagInt(☃x)));
      }

      ☃.func_74782_a("Gateways", ☃);
      return ☃;
   }

   public void func_186105_b() {
      this.field_186109_c.func_186758_d(!this.field_186117_k);
      if (++this.field_186116_j >= 20) {
         this.func_186100_j();
         this.field_186116_j = 0;
      }

      DragonFightManager.LoadManager ☃ = new DragonFightManager.LoadManager();
      if (!this.field_186109_c.func_186757_c().isEmpty()) {
         if (this.field_186120_n && ☃.func_210824_a()) {
            this.func_210827_g();
            this.field_186120_n = false;
         }

         if (this.field_186122_p != null) {
            if (this.field_186124_r == null && ☃.func_210824_a()) {
               this.field_186122_p = null;
               this.func_186106_e();
            }

            this.field_186122_p.func_186079_a(this.field_186110_d, this, this.field_186124_r, this.field_186123_q++, this.field_186121_o);
         }

         if (!this.field_186117_k) {
            if ((this.field_186119_m == null || ++this.field_186113_g >= 1200) && ☃.func_210824_a()) {
               this.func_210828_h();
               this.field_186113_g = 0;
            }

            if (++this.field_186115_i >= 100 && ☃.func_210824_a()) {
               this.func_186101_k();
               this.field_186115_i = 0;
            }
         }
      }
   }

   private void func_210827_g() {
      field_186107_a.info("Scanning for legacy world dragon fight...");
      boolean ☃ = this.func_186104_g();
      if (☃) {
         field_186107_a.info("Found that the dragon has been killed in this world already.");
         this.field_186118_l = true;
      } else {
         field_186107_a.info("Found that the dragon has not yet been killed in this world.");
         this.field_186118_l = false;
         this.func_186094_a(false);
      }

      List<EntityDragon> ☃ = this.field_186110_d.func_175644_a(EntityDragon.class, EntitySelectors.field_94557_a);
      if (☃.isEmpty()) {
         this.field_186117_k = true;
      } else {
         EntityDragon ☃ = (EntityDragon)☃.get(0);
         this.field_186119_m = ☃.func_110124_au();
         field_186107_a.info("Found that there's a dragon still alive ({})", ☃);
         this.field_186117_k = false;
         if (!☃) {
            field_186107_a.info("But we didn't have a portal, let's remove it.");
            ☃.func_70106_y();
            this.field_186119_m = null;
         }
      }

      if (!this.field_186118_l && this.field_186117_k) {
         this.field_186117_k = false;
      }
   }

   private void func_210828_h() {
      List<EntityDragon> ☃ = this.field_186110_d.func_175644_a(EntityDragon.class, EntitySelectors.field_94557_a);
      if (☃.isEmpty()) {
         field_186107_a.debug("Haven't seen the dragon, respawning it");
         this.func_192445_m();
      } else {
         field_186107_a.debug("Haven't seen our dragon, but found another one to use.");
         this.field_186119_m = ((EntityDragon)☃.get(0)).func_110124_au();
      }
   }

   protected void func_186095_a(DragonSpawnState var1) {
      if (this.field_186122_p == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.field_186123_q = 0;
         if (☃ == DragonSpawnState.END) {
            this.field_186122_p = null;
            this.field_186117_k = false;
            EntityDragon ☃ = this.func_192445_m();

            for(EntityPlayerMP ☃x : this.field_186109_c.func_186757_c()) {
               CriteriaTriggers.field_192133_m.func_192229_a(☃x, ☃);
            }
         } else {
            this.field_186122_p = ☃;
         }
      }
   }

   private boolean func_186104_g() {
      for(int ☃ = -8; ☃ <= 8; ++☃) {
         for(int ☃x = -8; ☃x <= 8; ++☃x) {
            Chunk ☃xx = this.field_186110_d.func_72964_e(☃, ☃x);

            for(TileEntity ☃xxx : ☃xx.func_177434_r().values()) {
               if (☃xxx instanceof TileEntityEndPortal) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.PatternHelper func_186091_h() {
      for(int ☃ = -8; ☃ <= 8; ++☃) {
         for(int ☃x = -8; ☃x <= 8; ++☃x) {
            Chunk ☃xx = this.field_186110_d.func_72964_e(☃, ☃x);

            for(TileEntity ☃xxx : ☃xx.func_177434_r().values()) {
               if (☃xxx instanceof TileEntityEndPortal) {
                  BlockPattern.PatternHelper ☃xxxx = this.field_186112_f.func_177681_a(this.field_186110_d, ☃xxx.func_174877_v());
                  if (☃xxxx != null) {
                     BlockPos ☃xxxxx = ☃xxxx.func_177670_a(3, 3, 3).func_177508_d();
                     if (this.field_186121_o == null && ☃xxxxx.func_177958_n() == 0 && ☃xxxxx.func_177952_p() == 0) {
                        this.field_186121_o = ☃xxxxx;
                     }

                     return ☃xxxx;
                  }
               }
            }
         }
      }

      int ☃ = this.field_186110_d.func_205770_a(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.field_186139_a).func_177956_o();

      for(int ☃x = ☃; ☃x >= 0; --☃x) {
         BlockPattern.PatternHelper ☃xx = this.field_186112_f
            .func_177681_a(
               this.field_186110_d, new BlockPos(EndPodiumFeature.field_186139_a.func_177958_n(), ☃x, EndPodiumFeature.field_186139_a.func_177952_p())
            );
         if (☃xx != null) {
            if (this.field_186121_o == null) {
               this.field_186121_o = ☃xx.func_177670_a(3, 3, 3).func_177508_d();
            }

            return ☃xx;
         }
      }

      return null;
   }

   private boolean func_210832_a(int var1, int var2, int var3, int var4) {
      if (this.func_210830_b(☃, ☃, ☃, ☃)) {
         return true;
      } else {
         this.func_210831_c(☃, ☃, ☃, ☃);
         return false;
      }
   }

   private boolean func_210830_b(int var1, int var2, int var3, int var4) {
      boolean ☃ = true;

      for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
         for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
            Chunk ☃xxx = this.field_186110_d.func_72964_e(☃x, ☃xx);
            ☃ &= ☃xxx.func_201589_g() == ChunkStatus.POSTPROCESSED;
         }
      }

      return ☃;
   }

   private void func_210831_c(int var1, int var2, int var3, int var4) {
      for(int ☃ = ☃ - 1; ☃ <= ☃ + 1; ++☃) {
         this.field_186110_d.func_72964_e(☃, ☃ - 1);
         this.field_186110_d.func_72964_e(☃, ☃ + 1);
      }

      for(int ☃ = ☃ - 1; ☃ <= ☃ + 1; ++☃) {
         this.field_186110_d.func_72964_e(☃ - 1, ☃);
         this.field_186110_d.func_72964_e(☃ + 1, ☃);
      }
   }

   private void func_186100_j() {
      Set<EntityPlayerMP> ☃ = Sets.<EntityPlayerMP>newHashSet();

      for(EntityPlayerMP ☃x : this.field_186110_d.func_175661_b(EntityPlayerMP.class, field_186108_b)) {
         this.field_186109_c.func_186760_a(☃x);
         ☃.add(☃x);
      }

      Set<EntityPlayerMP> ☃x = Sets.<EntityPlayerMP>newHashSet(this.field_186109_c.func_186757_c());
      ☃x.removeAll(☃);

      for(EntityPlayerMP ☃xx : ☃x) {
         this.field_186109_c.func_186761_b(☃xx);
      }
   }

   private void func_186101_k() {
      this.field_186115_i = 0;
      this.field_186114_h = 0;

      for(EndCrystalTowerFeature.EndSpike ☃ : EndSpikes.func_202466_a(this.field_186110_d)) {
         this.field_186114_h += this.field_186110_d.func_72872_a(EntityEnderCrystal.class, ☃.func_186153_f()).size();
      }

      field_186107_a.debug("Found {} end crystals still alive", this.field_186114_h);
   }

   public void func_186096_a(EntityDragon var1) {
      if (☃.func_110124_au().equals(this.field_186119_m)) {
         this.field_186109_c.func_186735_a(0.0F);
         this.field_186109_c.func_186758_d(false);
         this.func_186094_a(true);
         this.func_186097_l();
         if (!this.field_186118_l) {
            this.field_186110_d
               .func_175656_a(
                  this.field_186110_d.func_205770_a(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.field_186139_a), Blocks.field_150380_bt.func_176223_P()
               );
         }

         this.field_186118_l = true;
         this.field_186117_k = true;
      }
   }

   private void func_186097_l() {
      if (!this.field_186111_e.isEmpty()) {
         int ☃ = this.field_186111_e.remove(this.field_186111_e.size() - 1);
         int ☃x = (int)(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)☃)));
         int ☃xx = (int)(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)☃)));
         this.func_186089_a(new BlockPos(☃x, 75, ☃xx));
      }
   }

   private void func_186089_a(BlockPos var1) {
      this.field_186110_d.func_175718_b(3000, ☃, 0);
      Feature.field_202299_as
         .func_212245_a(this.field_186110_d, this.field_186110_d.func_72863_F().func_201711_g(), new Random(), ☃, new EndGatewayConfig(false));
   }

   private void func_186094_a(boolean var1) {
      EndPodiumFeature ☃ = new EndPodiumFeature(☃);
      if (this.field_186121_o == null) {
         this.field_186121_o = this.field_186110_d.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.field_186139_a).func_177977_b();

         while(
            this.field_186110_d.func_180495_p(this.field_186121_o).func_177230_c() == Blocks.field_150357_h
               && this.field_186121_o.func_177956_o() > this.field_186110_d.func_181545_F()
         ) {
            this.field_186121_o = this.field_186121_o.func_177977_b();
         }
      }

      ☃.func_212245_a(this.field_186110_d, this.field_186110_d.func_72863_F().func_201711_g(), new Random(), this.field_186121_o, IFeatureConfig.field_202429_e);
   }

   private EntityDragon func_192445_m() {
      this.field_186110_d.func_175726_f(new BlockPos(0, 128, 0));
      EntityDragon ☃ = new EntityDragon(this.field_186110_d);
      ☃.func_184670_cT().func_188758_a(PhaseType.field_188741_a);
      ☃.func_70012_b(0.0, 128.0, 0.0, this.field_186110_d.field_73012_v.nextFloat() * 360.0F, 0.0F);
      this.field_186110_d.func_72838_d(☃);
      this.field_186119_m = ☃.func_110124_au();
      return ☃;
   }

   public void func_186099_b(EntityDragon var1) {
      if (☃.func_110124_au().equals(this.field_186119_m)) {
         this.field_186109_c.func_186735_a(☃.func_110143_aJ() / ☃.func_110138_aP());
         this.field_186113_g = 0;
         if (☃.func_145818_k_()) {
            this.field_186109_c.func_186739_a(☃.func_145748_c_());
         }
      }
   }

   public int func_186092_c() {
      return this.field_186114_h;
   }

   public void func_186090_a(EntityEnderCrystal var1, DamageSource var2) {
      if (this.field_186122_p != null && this.field_186124_r.contains(☃)) {
         field_186107_a.debug("Aborting respawn sequence");
         this.field_186122_p = null;
         this.field_186123_q = 0;
         this.func_186087_f();
         this.func_186094_a(true);
      } else {
         this.func_186101_k();
         Entity ☃ = this.field_186110_d.func_175733_a(this.field_186119_m);
         if (☃ instanceof EntityDragon) {
            ((EntityDragon)☃).func_184672_a(☃, new BlockPos(☃), ☃);
         }
      }
   }

   public boolean func_186102_d() {
      return this.field_186118_l;
   }

   public void func_186106_e() {
      if (this.field_186117_k && this.field_186122_p == null) {
         BlockPos ☃ = this.field_186121_o;
         if (☃ == null) {
            field_186107_a.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.PatternHelper ☃x = this.func_186091_h();
            if (☃x == null) {
               field_186107_a.debug("Couldn't find a portal, so we made one.");
               this.func_186094_a(true);
            } else {
               field_186107_a.debug("Found the exit portal & temporarily using it.");
            }

            ☃ = this.field_186121_o;
         }

         List<EntityEnderCrystal> ☃ = Lists.<EntityEnderCrystal>newArrayList();
         BlockPos ☃x = ☃.func_177981_b(1);

         for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
            List<EntityEnderCrystal> ☃xxx = this.field_186110_d.func_72872_a(EntityEnderCrystal.class, new AxisAlignedBB(☃x.func_177967_a(☃xx, 2)));
            if (☃xxx.isEmpty()) {
               return;
            }

            ☃.addAll(☃xxx);
         }

         field_186107_a.debug("Found all crystals, respawning dragon.");
         this.func_186093_a(☃);
      }
   }

   private void func_186093_a(List<EntityEnderCrystal> var1) {
      if (this.field_186117_k && this.field_186122_p == null) {
         for(BlockPattern.PatternHelper ☃ = this.func_186091_h(); ☃ != null; ☃ = this.func_186091_h()) {
            for(int ☃x = 0; ☃x < this.field_186112_f.func_177684_c(); ++☃x) {
               for(int ☃xx = 0; ☃xx < this.field_186112_f.func_177685_b(); ++☃xx) {
                  for(int ☃xxx = 0; ☃xxx < this.field_186112_f.func_185922_a(); ++☃xxx) {
                     BlockWorldState ☃xxxx = ☃.func_177670_a(☃x, ☃xx, ☃xxx);
                     if (☃xxxx.func_177509_a().func_177230_c() == Blocks.field_150357_h || ☃xxxx.func_177509_a().func_177230_c() == Blocks.field_150384_bq) {
                        this.field_186110_d.func_175656_a(☃xxxx.func_177508_d(), Blocks.field_150377_bs.func_176223_P());
                     }
                  }
               }
            }
         }

         this.field_186122_p = DragonSpawnState.START;
         this.field_186123_q = 0;
         this.func_186094_a(false);
         this.field_186124_r = ☃;
      }
   }

   public void func_186087_f() {
      for(EndCrystalTowerFeature.EndSpike ☃ : EndSpikes.func_202466_a(this.field_186110_d)) {
         for(EntityEnderCrystal ☃x : this.field_186110_d.func_72872_a(EntityEnderCrystal.class, ☃.func_186153_f())) {
            ☃x.func_184224_h(false);
            ☃x.func_184516_a(null);
         }
      }
   }

   class LoadManager {
      private DragonFightManager.LoadState field_210826_b = DragonFightManager.LoadState.UNKNOWN;

      private LoadManager() {
      }

      private boolean func_210824_a() {
         if (this.field_210826_b == DragonFightManager.LoadState.UNKNOWN) {
            this.field_210826_b = DragonFightManager.this.func_210832_a(-8, 8, -8, 8)
               ? DragonFightManager.LoadState.LOADED
               : DragonFightManager.LoadState.NOT_LOADED;
         }

         return this.field_210826_b == DragonFightManager.LoadState.LOADED;
      }
   }

   static enum LoadState {
      UNKNOWN,
      NOT_LOADED,
      LOADED;
   }
}
