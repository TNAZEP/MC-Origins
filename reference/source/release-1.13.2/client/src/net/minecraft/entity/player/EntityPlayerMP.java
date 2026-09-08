package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ServerRecipeBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerLook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.CooldownTrackerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.ILootContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements IContainerListener {
   private static final Logger field_147102_bM = LogManager.getLogger();
   private String field_71148_cg = "en_US";
   public NetHandlerPlayServer field_71135_a;
   public final MinecraftServer field_71133_b;
   public final PlayerInteractionManager field_71134_c;
   public double field_71131_d;
   public double field_71132_e;
   private final List<Integer> field_71130_g = Lists.newLinkedList();
   private final PlayerAdvancements field_192042_bX;
   private final StatisticsManagerServer field_147103_bO;
   private float field_130068_bO = Float.MIN_VALUE;
   private int field_184852_bV = Integer.MIN_VALUE;
   private int field_184853_bW = Integer.MIN_VALUE;
   private int field_184854_bX = Integer.MIN_VALUE;
   private int field_184855_bY = Integer.MIN_VALUE;
   private int field_184856_bZ = Integer.MIN_VALUE;
   private float field_71149_ch = -1.0E8F;
   private int field_71146_ci = -99999999;
   private boolean field_71147_cj = true;
   private int field_71144_ck = -99999999;
   private int field_147101_bU = 60;
   private EntityPlayer.EnumChatVisibility field_71143_cn;
   private boolean field_71140_co = true;
   private long field_143005_bX = Util.func_211177_b();
   private Entity field_175401_bS;
   private boolean field_184851_cj;
   private boolean field_192040_cp;
   private final ServerRecipeBook field_192041_cq;
   private Vec3d field_193107_ct;
   private int field_193108_cu;
   private boolean field_193109_cv;
   private Vec3d field_193110_cw;
   private int field_71139_cq;
   public boolean field_71137_h;
   public int field_71138_i;
   public boolean field_71136_j;

   public EntityPlayerMP(MinecraftServer var1, WorldServer var2, GameProfile var3, PlayerInteractionManager var4) {
      super(☃, ☃);
      ☃.field_73090_b = this;
      this.field_71134_c = ☃;
      this.field_71133_b = ☃;
      this.field_192041_cq = new ServerRecipeBook(☃.func_199529_aN());
      this.field_147103_bO = ☃.func_184103_al().func_152602_a(this);
      this.field_192042_bX = ☃.func_184103_al().func_192054_h(this);
      this.field_70138_W = 1.0F;
      this.func_205734_a(☃);
   }

   private void func_205734_a(WorldServer var1) {
      BlockPos ☃ = ☃.func_175694_M();
      if (☃.field_73011_w.func_191066_m() && ☃.func_72912_H().func_76077_q() != GameType.ADVENTURE) {
         int ☃x = Math.max(0, this.field_71133_b.func_184108_a(☃));
         int ☃xx = MathHelper.func_76128_c(☃.func_175723_af().func_177729_b((double)☃.func_177958_n(), (double)☃.func_177952_p()));
         if (☃xx < ☃x) {
            ☃x = ☃xx;
         }

         if (☃xx <= 1) {
            ☃x = 1;
         }

         int ☃x = (☃x * 2 + 1) * (☃x * 2 + 1);
         int ☃xx = this.func_205735_q(☃x);
         int ☃xxx = new Random().nextInt(☃x);

         for(int ☃xxxx = 0; ☃xxxx < ☃x; ++☃xxxx) {
            int ☃xxxxx = (☃xxx + ☃xx * ☃xxxx) % ☃x;
            int ☃xxxxxx = ☃xxxxx % (☃x * 2 + 1);
            int ☃xxxxxxx = ☃xxxxx / (☃x * 2 + 1);
            BlockPos ☃xxxxxxxx = ☃.func_201675_m().func_206921_a(☃.func_177958_n() + ☃xxxxxx - ☃x, ☃.func_177952_p() + ☃xxxxxxx - ☃x, false);
            if (☃xxxxxxxx != null) {
               this.func_174828_a(☃xxxxxxxx, 0.0F, 0.0F);
               if (☃.func_195586_b(this, this.func_174813_aQ())) {
                  break;
               }
            }
         }
      } else {
         this.func_174828_a(☃, 0.0F, 0.0F);

         while(!☃.func_195586_b(this, this.func_174813_aQ()) && this.field_70163_u < 255.0) {
            this.func_70107_b(this.field_70165_t, this.field_70163_u + 1.0, this.field_70161_v);
         }
      }
   }

   private int func_205735_q(int var1) {
      return ☃ <= 16 ? ☃ - 1 : 17;
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("playerGameType", 99)) {
         if (this.func_184102_h().func_104056_am()) {
            this.field_71134_c.func_73076_a(this.func_184102_h().func_71265_f());
         } else {
            this.field_71134_c.func_73076_a(GameType.func_77146_a(☃.func_74762_e("playerGameType")));
         }
      }

      if (☃.func_150297_b("enteredNetherPosition", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("enteredNetherPosition");
         this.field_193110_cw = new Vec3d(☃.func_74769_h("x"), ☃.func_74769_h("y"), ☃.func_74769_h("z"));
      }

      this.field_192040_cp = ☃.func_74767_n("seenCredits");
      if (☃.func_150297_b("recipeBook", 10)) {
         this.field_192041_cq.func_192825_a(☃.func_74775_l("recipeBook"));
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("playerGameType", this.field_71134_c.func_73081_b().func_77148_a());
      ☃.func_74757_a("seenCredits", this.field_192040_cp);
      if (this.field_193110_cw != null) {
         NBTTagCompound ☃ = new NBTTagCompound();
         ☃.func_74780_a("x", this.field_193110_cw.field_72450_a);
         ☃.func_74780_a("y", this.field_193110_cw.field_72448_b);
         ☃.func_74780_a("z", this.field_193110_cw.field_72449_c);
         ☃.func_74782_a("enteredNetherPosition", ☃);
      }

      Entity ☃ = this.func_184208_bv();
      Entity ☃x = this.func_184187_bx();
      if (☃x != null && ☃ != this && ☃.func_200601_bK()) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         NBTTagCompound ☃xxx = new NBTTagCompound();
         ☃.func_70039_c(☃xxx);
         ☃xx.func_186854_a("Attach", ☃x.func_110124_au());
         ☃xx.func_74782_a("Entity", ☃xxx);
         ☃.func_74782_a("RootVehicle", ☃xx);
      }

      ☃.func_74782_a("recipeBook", this.field_192041_cq.func_192824_e());
   }

   public void func_195394_a(int var1) {
      float ☃ = (float)this.func_71050_bK();
      float ☃x = (☃ - 1.0F) / ☃;
      this.field_71106_cc = MathHelper.func_76131_a((float)☃ / ☃, 0.0F, ☃x);
      this.field_71144_ck = -1;
   }

   public void func_195399_b(int var1) {
      this.field_71068_ca = ☃;
      this.field_71144_ck = -1;
   }

   @Override
   public void func_82242_a(int var1) {
      super.func_82242_a(☃);
      this.field_71144_ck = -1;
   }

   @Override
   public void func_192024_a(ItemStack var1, int var2) {
      super.func_192024_a(☃, ☃);
      this.field_71144_ck = -1;
   }

   public void func_71116_b() {
      this.field_71070_bA.func_75132_a(this);
   }

   @Override
   public void func_152111_bt() {
      super.func_152111_bt();
      this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTER_COMBAT));
   }

   @Override
   public void func_152112_bu() {
      super.func_152112_bu();
      this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.END_COMBAT));
   }

   @Override
   protected void func_191955_a(IBlockState var1) {
      CriteriaTriggers.field_192124_d.func_192193_a(this, ☃);
   }

   @Override
   protected CooldownTracker func_184815_l() {
      return new CooldownTrackerServer(this);
   }

   @Override
   public void func_70071_h_() {
      this.field_71134_c.func_73075_a();
      --this.field_147101_bU;
      if (this.field_70172_ad > 0) {
         --this.field_70172_ad;
      }

      this.field_71070_bA.func_75142_b();
      if (!this.field_70170_p.field_72995_K && !this.field_71070_bA.func_75145_c(this)) {
         this.func_71053_j();
         this.field_71070_bA = this.field_71069_bz;
      }

      while(!this.field_71130_g.isEmpty()) {
         int ☃ = Math.min(this.field_71130_g.size(), Integer.MAX_VALUE);
         int[] ☃x = new int[☃];
         Iterator<Integer> ☃xx = this.field_71130_g.iterator();
         int ☃xxx = 0;

         while(☃xx.hasNext() && ☃xxx < ☃) {
            ☃x[☃xxx++] = ☃xx.next();
            ☃xx.remove();
         }

         this.field_71135_a.func_147359_a(new SPacketDestroyEntities(☃x));
      }

      Entity ☃ = this.func_175398_C();
      if (☃ != this) {
         if (☃.func_70089_S()) {
            this.func_70080_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
            this.field_71133_b.func_184103_al().func_72358_d(this);
            if (this.func_70093_af()) {
               this.func_175399_e(this);
            }
         } else {
            this.func_175399_e(this);
         }
      }

      CriteriaTriggers.field_193135_v.func_193182_a(this);
      if (this.field_193107_ct != null) {
         CriteriaTriggers.field_193133_t.func_193162_a(this, this.field_193107_ct, this.field_70173_aa - this.field_193108_cu);
      }

      this.field_192042_bX.func_192741_b(this);
   }

   public void func_71127_g() {
      try {
         super.func_70071_h_();

         for(int ☃ = 0; ☃ < this.field_71071_by.func_70302_i_(); ++☃) {
            ItemStack ☃x = this.field_71071_by.func_70301_a(☃);
            if (☃x.func_77973_b().func_77643_m_()) {
               Packet<?> ☃xx = ((ItemMapBase)☃x.func_77973_b()).func_150911_c(☃x, this.field_70170_p, this);
               if (☃xx != null) {
                  this.field_71135_a.func_147359_a(☃xx);
               }
            }
         }

         if (this.func_110143_aJ() != this.field_71149_ch
            || this.field_71146_ci != this.field_71100_bB.func_75116_a()
            || this.field_71100_bB.func_75115_e() == 0.0F != this.field_71147_cj) {
            this.field_71135_a
               .func_147359_a(new SPacketUpdateHealth(this.func_110143_aJ(), this.field_71100_bB.func_75116_a(), this.field_71100_bB.func_75115_e()));
            this.field_71149_ch = this.func_110143_aJ();
            this.field_71146_ci = this.field_71100_bB.func_75116_a();
            this.field_71147_cj = this.field_71100_bB.func_75115_e() == 0.0F;
         }

         if (this.func_110143_aJ() + this.func_110139_bj() != this.field_130068_bO) {
            this.field_130068_bO = this.func_110143_aJ() + this.func_110139_bj();
            this.func_184849_a(ScoreCriteria.field_96638_f, MathHelper.func_76123_f(this.field_130068_bO));
         }

         if (this.field_71100_bB.func_75116_a() != this.field_184852_bV) {
            this.field_184852_bV = this.field_71100_bB.func_75116_a();
            this.func_184849_a(ScoreCriteria.field_186698_h, MathHelper.func_76123_f((float)this.field_184852_bV));
         }

         if (this.func_70086_ai() != this.field_184853_bW) {
            this.field_184853_bW = this.func_70086_ai();
            this.func_184849_a(ScoreCriteria.field_186699_i, MathHelper.func_76123_f((float)this.field_184853_bW));
         }

         if (this.func_70658_aO() != this.field_184854_bX) {
            this.field_184854_bX = this.func_70658_aO();
            this.func_184849_a(ScoreCriteria.field_186700_j, MathHelper.func_76123_f((float)this.field_184854_bX));
         }

         if (this.field_71067_cb != this.field_184856_bZ) {
            this.field_184856_bZ = this.field_71067_cb;
            this.func_184849_a(ScoreCriteria.field_186701_k, MathHelper.func_76123_f((float)this.field_184856_bZ));
         }

         if (this.field_71068_ca != this.field_184855_bY) {
            this.field_184855_bY = this.field_71068_ca;
            this.func_184849_a(ScoreCriteria.field_186702_l, MathHelper.func_76123_f((float)this.field_184855_bY));
         }

         if (this.field_71067_cb != this.field_71144_ck) {
            this.field_71144_ck = this.field_71067_cb;
            this.field_71135_a.func_147359_a(new SPacketSetExperience(this.field_71106_cc, this.field_71067_cb, this.field_71068_ca));
         }

         if (this.field_70173_aa % 20 == 0) {
            CriteriaTriggers.field_192135_o.func_192215_a(this);
         }
      } catch (Throwable var4) {
         CrashReport ☃ = CrashReport.func_85055_a(var4, "Ticking player");
         CrashReportCategory ☃x = ☃.func_85058_a("Player being ticked");
         this.func_85029_a(☃x);
         throw new ReportedException(☃);
      }
   }

   private void func_184849_a(ScoreCriteria var1, int var2) {
      this.func_96123_co().func_197893_a(☃, this.func_195047_I_(), var1x -> var1x.func_96647_c(☃));
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      boolean ☃ = this.field_70170_p.func_82736_K().func_82766_b("showDeathMessages");
      if (☃) {
         ITextComponent ☃x = this.func_110142_aN().func_151521_b();
         this.field_71135_a
            .func_211148_a(
               new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTITY_DIED, ☃x),
               var2x -> {
                  if (!var2x.isSuccess()) {
                     int ☃ = 256;
                     String ☃x = ☃.func_212636_a(256);
                     ITextComponent ☃xx = new TextComponentTranslation(
                        "death.attack.message_too_long", new TextComponentString(☃x).func_211708_a(TextFormatting.YELLOW)
                     );
                     ITextComponent ☃xxx = new TextComponentTranslation("death.attack.even_more_magic", this.func_145748_c_())
                        .func_211710_a(var1x -> var1x.func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ☃)));
                     this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTITY_DIED, ☃xxx));
                  }
               }
            );
         Team ☃xx = this.func_96124_cp();
         if (☃xx == null || ☃xx.func_178771_j() == Team.EnumVisible.ALWAYS) {
            this.field_71133_b.func_184103_al().func_148539_a(☃x);
         } else if (☃xx.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
            this.field_71133_b.func_184103_al().func_177453_a(this, ☃x);
         } else if (☃xx.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
            this.field_71133_b.func_184103_al().func_177452_b(this, ☃x);
         }
      } else {
         this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTITY_DIED));
      }

      this.func_192030_dh();
      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         this.func_190776_cN();
         this.field_71071_by.func_70436_m();
      }

      this.func_96123_co().func_197893_a(ScoreCriteria.field_96642_c, this.func_195047_I_(), Score::func_96648_a);
      EntityLivingBase ☃ = this.func_94060_bK();
      if (☃ != null) {
         this.func_71029_a(StatList.field_199091_i.func_199076_b(☃.func_200600_R()));
         ☃.func_191956_a(this, this.field_70744_aE, ☃);
      }

      this.func_195066_a(StatList.field_188069_A);
      this.func_175145_a(StatList.field_199092_j.func_199076_b(StatList.field_188098_h));
      this.func_175145_a(StatList.field_199092_j.func_199076_b(StatList.field_203284_n));
      this.func_70066_B();
      this.func_70052_a(0, false);
      this.func_110142_aN().func_94549_h();
   }

   @Override
   public void func_191956_a(Entity var1, int var2, DamageSource var3) {
      if (☃ != this) {
         super.func_191956_a(☃, ☃, ☃);
         this.func_85039_t(☃);
         String ☃ = this.func_195047_I_();
         String ☃x = ☃.func_195047_I_();
         this.func_96123_co().func_197893_a(ScoreCriteria.field_96640_e, ☃, Score::func_96648_a);
         if (☃ instanceof EntityPlayer) {
            this.func_195066_a(StatList.field_75932_A);
            this.func_96123_co().func_197893_a(ScoreCriteria.field_96639_d, ☃, Score::func_96648_a);
         } else {
            this.func_195066_a(StatList.field_188070_B);
         }

         this.func_195398_a(☃, ☃x, ScoreCriteria.field_197913_m);
         this.func_195398_a(☃x, ☃, ScoreCriteria.field_197914_n);
         CriteriaTriggers.field_192122_b.func_192211_a(this, ☃, ☃);
      }
   }

   private void func_195398_a(String var1, String var2, ScoreCriteria[] var3) {
      ScorePlayerTeam ☃ = this.func_96123_co().func_96509_i(☃);
      if (☃ != null) {
         int ☃x = ☃.func_178775_l().func_175746_b();
         if (☃x >= 0 && ☃x < ☃.length) {
            this.func_96123_co().func_197893_a(☃[☃x], ☃, Score::func_96648_a);
         }
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         boolean ☃ = this.field_71133_b.func_71262_S() && this.func_175400_cq() && "fall".equals(☃.field_76373_n);
         if (!☃ && this.field_147101_bU > 0 && ☃ != DamageSource.field_76380_i) {
            return false;
         } else {
            if (☃ instanceof EntityDamageSource) {
               Entity ☃ = ☃.func_76346_g();
               if (☃ instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)☃)) {
                  return false;
               }

               if (☃ instanceof EntityArrow) {
                  EntityArrow ☃ = (EntityArrow)☃;
                  Entity ☃x = ☃.func_212360_k();
                  if (☃x instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)☃x)) {
                     return false;
                  }
               }
            }

            return super.func_70097_a(☃, ☃);
         }
      }
   }

   @Override
   public boolean func_96122_a(EntityPlayer var1) {
      return !this.func_175400_cq() ? false : super.func_96122_a(☃);
   }

   private boolean func_175400_cq() {
      return this.field_71133_b.func_71219_W();
   }

   @Nullable
   @Override
   public Entity func_212321_a(DimensionType var1) {
      this.field_184851_cj = true;
      if (this.field_71093_bK == DimensionType.OVERWORLD && ☃ == DimensionType.NETHER) {
         this.field_193110_cw = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      } else if (this.field_71093_bK != DimensionType.NETHER && ☃ != DimensionType.OVERWORLD) {
         this.field_193110_cw = null;
      }

      if (this.field_71093_bK == DimensionType.THE_END && ☃ == DimensionType.THE_END) {
         this.field_70170_p.func_72900_e(this);
         if (!this.field_71136_j) {
            this.field_71136_j = true;
            this.field_71135_a.func_147359_a(new SPacketChangeGameState(4, this.field_192040_cp ? 0.0F : 1.0F));
            this.field_192040_cp = true;
         }

         return this;
      } else {
         if (this.field_71093_bK == DimensionType.OVERWORLD && ☃ == DimensionType.THE_END) {
            ☃ = DimensionType.THE_END;
         }

         this.field_71133_b.func_184103_al().func_187242_a(this, ☃);
         this.field_71135_a.func_147359_a(new SPacketEffect(1032, BlockPos.field_177992_a, 0, false));
         this.field_71144_ck = -1;
         this.field_71149_ch = -1.0F;
         this.field_71146_ci = -1;
         return this;
      }
   }

   @Override
   public boolean func_174827_a(EntityPlayerMP var1) {
      if (☃.func_175149_v()) {
         return this.func_175398_C() == this;
      } else {
         return this.func_175149_v() ? false : super.func_174827_a(☃);
      }
   }

   private void func_147097_b(TileEntity var1) {
      if (☃ != null) {
         SPacketUpdateTileEntity ☃ = ☃.func_189518_D_();
         if (☃ != null) {
            this.field_71135_a.func_147359_a(☃);
         }
      }
   }

   @Override
   public void func_71001_a(Entity var1, int var2) {
      super.func_71001_a(☃, ☃);
      this.field_71070_bA.func_75142_b();
   }

   @Override
   public EntityPlayer.SleepResult func_180469_a(BlockPos var1) {
      EntityPlayer.SleepResult ☃ = super.func_180469_a(☃);
      if (☃ == EntityPlayer.SleepResult.OK) {
         this.func_195066_a(StatList.field_188064_ad);
         Packet<?> ☃x = new SPacketUseBed(this, ☃);
         this.func_71121_q().func_73039_n().func_151247_a(this, ☃x);
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         this.field_71135_a.func_147359_a(☃x);
         CriteriaTriggers.field_192136_p.func_192215_a(this);
      }

      return ☃;
   }

   @Override
   public void func_70999_a(boolean var1, boolean var2, boolean var3) {
      if (this.func_70608_bn()) {
         this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(this, 2));
      }

      super.func_70999_a(☃, ☃, ☃);
      if (this.field_71135_a != null) {
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      }
   }

   @Override
   public boolean func_184205_a(Entity var1, boolean var2) {
      Entity ☃ = this.func_184187_bx();
      if (!super.func_184205_a(☃, ☃)) {
         return false;
      } else {
         Entity ☃ = this.func_184187_bx();
         if (☃ != ☃ && this.field_71135_a != null) {
            this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         }

         return true;
      }
   }

   @Override
   public void func_184210_p() {
      Entity ☃ = this.func_184187_bx();
      super.func_184210_p();
      Entity ☃x = this.func_184187_bx();
      if (☃x != ☃ && this.field_71135_a != null) {
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      }
   }

   @Override
   public boolean func_180431_b(DamageSource var1) {
      return super.func_180431_b(☃) || this.func_184850_K();
   }

   @Override
   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
   }

   @Override
   protected void func_184594_b(BlockPos var1) {
      if (!this.func_175149_v()) {
         super.func_184594_b(☃);
      }
   }

   public void func_71122_b(double var1, boolean var3) {
      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.field_70163_u - 0.2F);
      int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      IBlockState ☃xxxx = this.field_70170_p.func_180495_p(☃xxx);
      if (☃xxxx.func_196958_f()) {
         BlockPos ☃xxxxx = ☃xxx.func_177977_b();
         IBlockState ☃xxxxxx = this.field_70170_p.func_180495_p(☃xxxxx);
         Block ☃xxxxxxx = ☃xxxxxx.func_177230_c();
         if (☃xxxxxxx instanceof BlockFence || ☃xxxxxxx instanceof BlockWall || ☃xxxxxxx instanceof BlockFenceGate) {
            ☃xxx = ☃xxxxx;
            ☃xxxx = ☃xxxxxx;
         }
      }

      super.func_184231_a(☃, ☃, ☃xxxx, ☃xxx);
   }

   @Override
   public void func_175141_a(TileEntitySign var1) {
      ☃.func_145912_a(this);
      this.field_71135_a.func_147359_a(new SPacketSignEditorOpen(☃.func_174877_v()));
   }

   private void func_71117_bO() {
      this.field_71139_cq = this.field_71139_cq % 100 + 1;
   }

   @Override
   public void func_180468_a(IInteractionObject var1) {
      if (☃ instanceof ILootContainer && ((ILootContainer)☃).func_184276_b() != null && this.func_175149_v()) {
         this.func_146105_b(new TextComponentTranslation("container.spectatorCantOpen").func_211708_a(TextFormatting.RED), true);
      } else {
         this.func_71117_bO();
         this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, ☃.func_174875_k(), ☃.func_145748_c_()));
         this.field_71070_bA = ☃.func_174876_a(this.field_71071_by, this);
         this.field_71070_bA.field_75152_c = this.field_71139_cq;
         this.field_71070_bA.func_75132_a(this);
      }
   }

   @Override
   public void func_71007_a(IInventory var1) {
      if (☃ instanceof ILootContainer && ((ILootContainer)☃).func_184276_b() != null && this.func_175149_v()) {
         this.func_146105_b(new TextComponentTranslation("container.spectatorCantOpen").func_211708_a(TextFormatting.RED), true);
      } else {
         if (this.field_71070_bA != this.field_71069_bz) {
            this.func_71053_j();
         }

         if (☃ instanceof ILockableContainer) {
            ILockableContainer ☃ = (ILockableContainer)☃;
            if (☃.func_174893_q_() && !this.func_175146_a(☃.func_174891_i()) && !this.func_175149_v()) {
               this.field_71135_a.func_147359_a(new SPacketChat(new TextComponentTranslation("container.isLocked", ☃.func_145748_c_()), ChatType.GAME_INFO));
               this.field_71135_a
                  .func_147359_a(
                     new SPacketSoundEffect(
                        SoundEvents.field_187654_U, SoundCategory.BLOCKS, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, 1.0F
                     )
                  );
               return;
            }
         }

         this.func_71117_bO();
         if (☃ instanceof IInteractionObject) {
            this.field_71135_a
               .func_147359_a(new SPacketOpenWindow(this.field_71139_cq, ((IInteractionObject)☃).func_174875_k(), ☃.func_145748_c_(), ☃.func_70302_i_()));
            this.field_71070_bA = ((IInteractionObject)☃).func_174876_a(this.field_71071_by, this);
         } else {
            this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "minecraft:container", ☃.func_145748_c_(), ☃.func_70302_i_()));
            this.field_71070_bA = new ContainerChest(this.field_71071_by, ☃, this);
         }

         this.field_71070_bA.field_75152_c = this.field_71139_cq;
         this.field_71070_bA.func_75132_a(this);
      }
   }

   @Override
   public void func_180472_a(IMerchant var1) {
      this.func_71117_bO();
      this.field_71070_bA = new ContainerMerchant(this.field_71071_by, ☃, this.field_70170_p);
      this.field_71070_bA.field_75152_c = this.field_71139_cq;
      this.field_71070_bA.func_75132_a(this);
      IInventory ☃ = ((ContainerMerchant)this.field_71070_bA).func_75174_d();
      ITextComponent ☃x = ☃.func_145748_c_();
      this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "minecraft:villager", ☃x, ☃.func_70302_i_()));
      MerchantRecipeList ☃xx = ☃.func_70934_b(this);
      if (☃xx != null) {
         PacketBuffer ☃xxx = new PacketBuffer(Unpooled.buffer());
         ☃xxx.writeInt(this.field_71139_cq);
         ☃xx.func_151391_a(☃xxx);
         this.field_71135_a.func_147359_a(new SPacketCustomPayload(SPacketCustomPayload.field_209910_a, ☃xxx));
      }
   }

   @Override
   public void func_184826_a(AbstractHorse var1, IInventory var2) {
      if (this.field_71070_bA != this.field_71069_bz) {
         this.func_71053_j();
      }

      this.func_71117_bO();
      this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "EntityHorse", ☃.func_145748_c_(), ☃.func_70302_i_(), ☃.func_145782_y()));
      this.field_71070_bA = new ContainerHorseInventory(this.field_71071_by, ☃, ☃, this);
      this.field_71070_bA.field_75152_c = this.field_71139_cq;
      this.field_71070_bA.func_75132_a(this);
   }

   @Override
   public void func_184814_a(ItemStack var1, EnumHand var2) {
      Item ☃ = ☃.func_77973_b();
      if (☃ == Items.field_151164_bB) {
         PacketBuffer ☃x = new PacketBuffer(Unpooled.buffer());
         ☃x.func_179249_a(☃);
         this.field_71135_a.func_147359_a(new SPacketCustomPayload(SPacketCustomPayload.field_209912_c, ☃x));
      }
   }

   @Override
   public void func_184824_a(TileEntityCommandBlock var1) {
      ☃.func_184252_d(true);
      this.func_147097_b(☃);
   }

   @Override
   public void func_71111_a(Container var1, int var2, ItemStack var3) {
      if (!(☃.func_75139_a(☃) instanceof SlotCrafting)) {
         if (☃ == this.field_71069_bz) {
            CriteriaTriggers.field_192125_e.func_192208_a(this, this.field_71071_by);
         }

         if (!this.field_71137_h) {
            this.field_71135_a.func_147359_a(new SPacketSetSlot(☃.field_75152_c, ☃, ☃));
         }
      }
   }

   public void func_71120_a(Container var1) {
      this.func_71110_a(☃, ☃.func_75138_a());
   }

   @Override
   public void func_71110_a(Container var1, NonNullList<ItemStack> var2) {
      this.field_71135_a.func_147359_a(new SPacketWindowItems(☃.field_75152_c, ☃));
      this.field_71135_a.func_147359_a(new SPacketSetSlot(-1, -1, this.field_71071_by.func_70445_o()));
   }

   @Override
   public void func_71112_a(Container var1, int var2, int var3) {
      this.field_71135_a.func_147359_a(new SPacketWindowProperty(☃.field_75152_c, ☃, ☃));
   }

   @Override
   public void func_175173_a(Container var1, IInventory var2) {
      for(int ☃ = 0; ☃ < ☃.func_174890_g(); ++☃) {
         this.field_71135_a.func_147359_a(new SPacketWindowProperty(☃.field_75152_c, ☃, ☃.func_174887_a_(☃)));
      }
   }

   @Override
   public void func_71053_j() {
      this.field_71135_a.func_147359_a(new SPacketCloseWindow(this.field_71070_bA.field_75152_c));
      this.func_71128_l();
   }

   public void func_71113_k() {
      if (!this.field_71137_h) {
         this.field_71135_a.func_147359_a(new SPacketSetSlot(-1, -1, this.field_71071_by.func_70445_o()));
      }
   }

   public void func_71128_l() {
      this.field_71070_bA.func_75134_a(this);
      this.field_71070_bA = this.field_71069_bz;
   }

   public void func_110430_a(float var1, float var2, boolean var3, boolean var4) {
      if (this.func_184218_aH()) {
         if (☃ >= -1.0F && ☃ <= 1.0F) {
            this.field_70702_br = ☃;
         }

         if (☃ >= -1.0F && ☃ <= 1.0F) {
            this.field_191988_bg = ☃;
         }

         this.field_70703_bu = ☃;
         this.func_70095_a(☃);
      }
   }

   @Override
   public void func_71064_a(Stat<?> var1, int var2) {
      this.field_147103_bO.func_150871_b(this, ☃, ☃);
      this.func_96123_co().func_197893_a(☃, this.func_195047_I_(), var1x -> var1x.func_96649_a(☃));
   }

   @Override
   public void func_175145_a(Stat<?> var1) {
      this.field_147103_bO.func_150873_a(this, ☃, 0);
      this.func_96123_co().func_197893_a(☃, this.func_195047_I_(), Score::func_197891_c);
   }

   @Override
   public int func_195065_a(Collection<IRecipe> var1) {
      return this.field_192041_cq.func_197926_a(☃, this);
   }

   @Override
   public void func_193102_a(ResourceLocation[] var1) {
      List<IRecipe> ☃ = Lists.<IRecipe>newArrayList();

      for(ResourceLocation ☃x : ☃) {
         IRecipe ☃xx = this.field_71133_b.func_199529_aN().func_199517_a(☃x);
         if (☃xx != null) {
            ☃.add(☃xx);
         }
      }

      this.func_195065_a(☃);
   }

   @Override
   public int func_195069_b(Collection<IRecipe> var1) {
      return this.field_192041_cq.func_197925_b(☃, this);
   }

   @Override
   public void func_195068_e(int var1) {
      super.func_195068_e(☃);
      this.field_71144_ck = -1;
   }

   public void func_71123_m() {
      this.field_193109_cv = true;
      this.func_184226_ay();
      if (this.field_71083_bS) {
         this.func_70999_a(true, false, false);
      }
   }

   public boolean func_193105_t() {
      return this.field_193109_cv;
   }

   public void func_71118_n() {
      this.field_71149_ch = -1.0E8F;
   }

   @Override
   public void func_146105_b(ITextComponent var1, boolean var2) {
      this.field_71135_a.func_147359_a(new SPacketChat(☃, ☃ ? ChatType.GAME_INFO : ChatType.CHAT));
   }

   @Override
   protected void func_71036_o() {
      if (!this.field_184627_bm.func_190926_b() && this.func_184587_cr()) {
         this.field_71135_a.func_147359_a(new SPacketEntityStatus(this, (byte)9));
         super.func_71036_o();
      }
   }

   @Override
   public void func_200602_a(EntityAnchorArgument.Type var1, Vec3d var2) {
      super.func_200602_a(☃, ☃);
      this.field_71135_a.func_147359_a(new SPacketPlayerLook(☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c));
   }

   public void func_200618_a(EntityAnchorArgument.Type var1, Entity var2, EntityAnchorArgument.Type var3) {
      Vec3d ☃ = ☃.func_201017_a(☃);
      super.func_200602_a(☃, ☃);
      this.field_71135_a.func_147359_a(new SPacketPlayerLook(☃, ☃, ☃));
   }

   public void func_193104_a(EntityPlayerMP var1, boolean var2) {
      if (☃) {
         this.field_71071_by.func_70455_b(☃.field_71071_by);
         this.func_70606_j(☃.func_110143_aJ());
         this.field_71100_bB = ☃.field_71100_bB;
         this.field_71068_ca = ☃.field_71068_ca;
         this.field_71067_cb = ☃.field_71067_cb;
         this.field_71106_cc = ☃.field_71106_cc;
         this.func_85040_s(☃.func_71037_bA());
         this.field_181016_an = ☃.field_181016_an;
         this.field_181017_ao = ☃.field_181017_ao;
         this.field_181018_ap = ☃.field_181018_ap;
      } else if (this.field_70170_p.func_82736_K().func_82766_b("keepInventory") || ☃.func_175149_v()) {
         this.field_71071_by.func_70455_b(☃.field_71071_by);
         this.field_71068_ca = ☃.field_71068_ca;
         this.field_71067_cb = ☃.field_71067_cb;
         this.field_71106_cc = ☃.field_71106_cc;
         this.func_85040_s(☃.func_71037_bA());
      }

      this.field_175152_f = ☃.field_175152_f;
      this.field_71078_a = ☃.field_71078_a;
      this.func_184212_Q().func_187227_b(field_184827_bp, ☃.func_184212_Q().func_187225_a(field_184827_bp));
      this.field_71144_ck = -1;
      this.field_71149_ch = -1.0F;
      this.field_71146_ci = -1;
      this.field_192041_cq.func_193824_a(☃.field_192041_cq);
      this.field_71130_g.addAll(☃.field_71130_g);
      this.field_192040_cp = ☃.field_192040_cp;
      this.field_193110_cw = ☃.field_193110_cw;
      this.func_192029_h(☃.func_192023_dk());
      this.func_192031_i(☃.func_192025_dl());
   }

   @Override
   protected void func_70670_a(PotionEffect var1) {
      super.func_70670_a(☃);
      this.field_71135_a.func_147359_a(new SPacketEntityEffect(this.func_145782_y(), ☃));
      if (☃.func_188419_a() == MobEffects.field_188424_y) {
         this.field_193108_cu = this.field_70173_aa;
         this.field_193107_ct = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }

      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   @Override
   protected void func_70695_b(PotionEffect var1, boolean var2) {
      super.func_70695_b(☃, ☃);
      this.field_71135_a.func_147359_a(new SPacketEntityEffect(this.func_145782_y(), ☃));
      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   @Override
   protected void func_70688_c(PotionEffect var1) {
      super.func_70688_c(☃);
      this.field_71135_a.func_147359_a(new SPacketRemoveEntityEffect(this.func_145782_y(), ☃.func_188419_a()));
      if (☃.func_188419_a() == MobEffects.field_188424_y) {
         this.field_193107_ct = null;
      }

      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   @Override
   public void func_70634_a(double var1, double var3, double var5) {
      this.field_71135_a.func_147364_a(☃, ☃, ☃, this.field_70177_z, this.field_70125_A);
   }

   @Override
   public void func_71009_b(Entity var1) {
      this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(☃, 4));
   }

   @Override
   public void func_71047_c(Entity var1) {
      this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(☃, 5));
   }

   @Override
   public void func_71016_p() {
      if (this.field_71135_a != null) {
         this.field_71135_a.func_147359_a(new SPacketPlayerAbilities(this.field_71075_bZ));
         this.func_175135_B();
      }
   }

   public WorldServer func_71121_q() {
      return (WorldServer)this.field_70170_p;
   }

   @Override
   public void func_71033_a(GameType var1) {
      this.field_71134_c.func_73076_a(☃);
      this.field_71135_a.func_147359_a(new SPacketChangeGameState(3, (float)☃.func_77148_a()));
      if (☃ == GameType.SPECTATOR) {
         this.func_192030_dh();
         this.func_184210_p();
      } else {
         this.func_175399_e(this);
      }

      this.func_71016_p();
      this.func_175136_bO();
   }

   @Override
   public boolean func_175149_v() {
      return this.field_71134_c.func_73081_b() == GameType.SPECTATOR;
   }

   @Override
   public boolean func_184812_l_() {
      return this.field_71134_c.func_73081_b() == GameType.CREATIVE;
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
      this.func_195395_a(☃, ChatType.SYSTEM);
   }

   public void func_195395_a(ITextComponent var1, ChatType var2) {
      this.field_71135_a
         .func_211148_a(
            new SPacketChat(☃, ☃),
            var3 -> {
               if (!var3.isSuccess() && (☃ == ChatType.GAME_INFO || ☃ == ChatType.SYSTEM)) {
                  int ☃ = 256;
                  String ☃x = ☃.func_212636_a(256);
                  ITextComponent ☃xx = new TextComponentString(☃x).func_211708_a(TextFormatting.YELLOW);
                  this.field_71135_a
                     .func_147359_a(
                        new SPacketChat(
                           new TextComponentTranslation("multiplayer.message_not_delivered", ☃xx).func_211708_a(TextFormatting.RED), ChatType.SYSTEM
                        )
                     );
               }
            }
         );
   }

   public String func_71114_r() {
      String ☃ = this.field_71135_a.field_147371_a.func_74430_c().toString();
      ☃ = ☃.substring(☃.indexOf("/") + 1);
      return ☃.substring(0, ☃.indexOf(":"));
   }

   public void func_147100_a(CPacketClientSettings var1) {
      this.field_71148_cg = ☃.func_149524_c();
      this.field_71143_cn = ☃.func_149523_e();
      this.field_71140_co = ☃.func_149520_f();
      this.func_184212_Q().func_187227_b(field_184827_bp, (byte)☃.func_149521_d());
      this.func_184212_Q().func_187227_b(field_184828_bq, (byte)(☃.func_186991_f() == EnumHandSide.LEFT ? 0 : 1));
   }

   public EntityPlayer.EnumChatVisibility func_147096_v() {
      return this.field_71143_cn;
   }

   public void func_175397_a(String var1, String var2) {
      this.field_71135_a.func_147359_a(new SPacketResourcePackSend(☃, ☃));
   }

   @Override
   protected int func_184840_I() {
      return this.field_71133_b.func_211833_a(this.func_146103_bH());
   }

   public void func_143004_u() {
      this.field_143005_bX = Util.func_211177_b();
   }

   public StatisticsManagerServer func_147099_x() {
      return this.field_147103_bO;
   }

   public ServerRecipeBook func_192037_E() {
      return this.field_192041_cq;
   }

   public void func_152339_d(Entity var1) {
      if (☃ instanceof EntityPlayer) {
         this.field_71135_a.func_147359_a(new SPacketDestroyEntities(☃.func_145782_y()));
      } else {
         this.field_71130_g.add(☃.func_145782_y());
      }
   }

   public void func_184848_d(Entity var1) {
      this.field_71130_g.remove(☃.func_145782_y());
   }

   @Override
   protected void func_175135_B() {
      if (this.func_175149_v()) {
         this.func_175133_bi();
         this.func_82142_c(true);
      } else {
         super.func_175135_B();
      }

      this.func_71121_q().func_73039_n().func_180245_a(this);
   }

   public Entity func_175398_C() {
      return (Entity)(this.field_175401_bS == null ? this : this.field_175401_bS);
   }

   public void func_175399_e(Entity var1) {
      Entity ☃ = this.func_175398_C();
      this.field_175401_bS = (Entity)(☃ == null ? this : ☃);
      if (☃ != this.field_175401_bS) {
         this.field_71135_a.func_147359_a(new SPacketCamera(this.field_175401_bS));
         this.func_70634_a(this.field_175401_bS.field_70165_t, this.field_175401_bS.field_70163_u, this.field_175401_bS.field_70161_v);
      }
   }

   @Override
   protected void func_184173_H() {
      if (this.field_71088_bW > 0 && !this.field_184851_cj) {
         --this.field_71088_bW;
      }
   }

   @Override
   public void func_71059_n(Entity var1) {
      if (this.field_71134_c.func_73081_b() == GameType.SPECTATOR) {
         this.func_175399_e(☃);
      } else {
         super.func_71059_n(☃);
      }
   }

   public long func_154331_x() {
      return this.field_143005_bX;
   }

   @Nullable
   public ITextComponent func_175396_E() {
      return null;
   }

   @Override
   public void func_184609_a(EnumHand var1) {
      super.func_184609_a(☃);
      this.func_184821_cY();
   }

   public boolean func_184850_K() {
      return this.field_184851_cj;
   }

   public void func_184846_L() {
      this.field_184851_cj = false;
   }

   public void func_184847_M() {
      this.func_70052_a(7, true);
   }

   public void func_189103_N() {
      this.func_70052_a(7, true);
      this.func_70052_a(7, false);
   }

   public PlayerAdvancements func_192039_O() {
      return this.field_192042_bX;
   }

   @Nullable
   public Vec3d func_193106_Q() {
      return this.field_193110_cw;
   }

   public void func_200619_a(WorldServer var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_175399_e(this);
      this.func_184210_p();
      if (☃ == this.field_70170_p) {
         this.field_71135_a.func_147364_a(☃, ☃, ☃, ☃, ☃);
      } else {
         WorldServer ☃ = this.func_71121_q();
         this.field_71093_bK = ☃.field_73011_w.func_186058_p();
         this.field_71135_a
            .func_147359_a(new SPacketRespawn(this.field_71093_bK, ☃.func_175659_aa(), ☃.func_72912_H().func_76067_t(), this.field_71134_c.func_73081_b()));
         this.field_71133_b.func_184103_al().func_187243_f(this);
         ☃.func_72973_f(this);
         this.field_70128_L = false;
         this.func_70012_b(☃, ☃, ☃, ☃, ☃);
         if (this.func_70089_S()) {
            ☃.func_72866_a(this, false);
            ☃.func_72838_d(this);
            ☃.func_72866_a(this, false);
         }

         this.func_70029_a(☃);
         this.field_71133_b.func_184103_al().func_72375_a(this, ☃);
         this.field_71135_a.func_147364_a(☃, ☃, ☃, ☃, ☃);
         this.field_71134_c.func_73080_a(☃);
         this.field_71133_b.func_184103_al().func_72354_b(this, ☃);
         this.field_71133_b.func_184103_al().func_72385_f(this);
      }
   }
}
