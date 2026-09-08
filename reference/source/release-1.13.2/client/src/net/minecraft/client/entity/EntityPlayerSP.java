package net.minecraft.client.entity;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.client.audio.IAmbientSoundHandler;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.UnderwaterAmbientSoundHandler;
import net.minecraft.client.audio.UnderwaterAmbientSounds;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEditCommandBlockMinecart;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiEditStructure;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class EntityPlayerSP extends AbstractClientPlayer {
   public final NetHandlerPlayClient field_71174_a;
   private final StatisticsManager field_146108_bO;
   private final RecipeBookClient field_192036_cb;
   private final List<IAmbientSoundHandler> field_204232_cf = Lists.<IAmbientSoundHandler>newArrayList();
   private int field_184845_bX = 0;
   private double field_175172_bI;
   private double field_175166_bJ;
   private double field_175167_bK;
   private float field_175164_bL;
   private float field_175165_bM;
   private boolean field_184841_cd;
   private boolean field_175170_bN;
   private boolean field_175171_bO;
   private int field_175168_bP;
   private boolean field_175169_bQ;
   private String field_142022_ce;
   public MovementInput field_71158_b;
   protected Minecraft field_71159_c;
   protected int field_71156_d;
   public int field_71157_e;
   public float field_71154_f;
   public float field_71155_g;
   public float field_71163_h;
   public float field_71164_i;
   private int field_110320_a;
   private float field_110321_bQ;
   public float field_71086_bY;
   public float field_71080_cy;
   private boolean field_184842_cm;
   private EnumHand field_184843_cn;
   private boolean field_184844_co;
   private boolean field_189811_cr = true;
   private int field_189812_cs;
   private boolean field_189813_ct;
   private int field_203720_cz;

   public EntityPlayerSP(Minecraft var1, World var2, NetHandlerPlayClient var3, StatisticsManager var4, RecipeBookClient var5) {
      super(☃, ☃.func_175105_e());
      this.field_71174_a = ☃;
      this.field_146108_bO = ☃;
      this.field_192036_cb = ☃;
      this.field_71159_c = ☃;
      this.field_71093_bK = DimensionType.OVERWORLD;
      this.field_204232_cf.add(new UnderwaterAmbientSoundHandler(this, ☃.func_147118_V()));
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return false;
   }

   @Override
   public void func_70691_i(float var1) {
   }

   @Override
   public boolean func_184205_a(Entity var1, boolean var2) {
      if (!super.func_184205_a(☃, ☃)) {
         return false;
      } else {
         if (☃ instanceof EntityMinecart) {
            this.field_71159_c.func_147118_V().func_147682_a(new MovingSoundMinecartRiding(this, (EntityMinecart)☃));
         }

         if (☃ instanceof EntityBoat) {
            this.field_70126_B = ☃.field_70177_z;
            this.field_70177_z = ☃.field_70177_z;
            this.func_70034_d(☃.field_70177_z);
         }

         return true;
      }
   }

   @Override
   public void func_184210_p() {
      super.func_184210_p();
      this.field_184844_co = false;
   }

   @Override
   public float func_195050_f(float var1) {
      return this.field_70125_A;
   }

   @Override
   public float func_195046_g(float var1) {
      return this.func_184218_aH() ? super.func_195046_g(☃) : this.field_70177_z;
   }

   @Override
   public void func_70071_h_() {
      if (this.field_70170_p.func_175667_e(new BlockPos(this.field_70165_t, 0.0, this.field_70161_v))) {
         super.func_70071_h_();
         if (this.func_184218_aH()) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(this.field_70177_z, this.field_70125_A, this.field_70122_E));
            this.field_71174_a
               .func_147297_a(new CPacketInput(this.field_70702_br, this.field_191988_bg, this.field_71158_b.field_78901_c, this.field_71158_b.field_78899_d));
            Entity ☃ = this.func_184208_bv();
            if (☃ != this && ☃.func_184186_bw()) {
               this.field_71174_a.func_147297_a(new CPacketVehicleMove(☃));
            }
         } else {
            this.func_175161_p();
         }

         for(IAmbientSoundHandler ☃ : this.field_204232_cf) {
            ☃.func_204253_a();
         }
      }
   }

   private void func_175161_p() {
      boolean ☃ = this.func_70051_ag();
      if (☃ != this.field_175171_bO) {
         if (☃) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SPRINTING));
         } else {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SPRINTING));
         }

         this.field_175171_bO = ☃;
      }

      boolean ☃ = this.func_70093_af();
      if (☃ != this.field_175170_bN) {
         if (☃) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING));
         } else {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING));
         }

         this.field_175170_bN = ☃;
      }

      if (this.func_175160_A()) {
         AxisAlignedBB ☃ = this.func_174813_aQ();
         double ☃x = this.field_70165_t - this.field_175172_bI;
         double ☃xx = ☃.field_72338_b - this.field_175166_bJ;
         double ☃xxx = this.field_70161_v - this.field_175167_bK;
         double ☃xxxx = (double)(this.field_70177_z - this.field_175164_bL);
         double ☃xxxxx = (double)(this.field_70125_A - this.field_175165_bM);
         ++this.field_175168_bP;
         boolean ☃xxxxxx = ☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx > 9.0E-4 || this.field_175168_bP >= 20;
         boolean ☃xxxxxxx = ☃xxxx != 0.0 || ☃xxxxx != 0.0;
         if (this.func_184218_aH()) {
            this.field_71174_a
               .func_147297_a(
                  new CPacketPlayer.PositionRotation(this.field_70159_w, -999.0, this.field_70179_y, this.field_70177_z, this.field_70125_A, this.field_70122_E)
               );
            ☃xxxxxx = false;
         } else if (☃xxxxxx && ☃xxxxxxx) {
            this.field_71174_a
               .func_147297_a(
                  new CPacketPlayer.PositionRotation(
                     this.field_70165_t, ☃.field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E
                  )
               );
         } else if (☃xxxxxx) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Position(this.field_70165_t, ☃.field_72338_b, this.field_70161_v, this.field_70122_E));
         } else if (☃xxxxxxx) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(this.field_70177_z, this.field_70125_A, this.field_70122_E));
         } else if (this.field_184841_cd != this.field_70122_E) {
            this.field_71174_a.func_147297_a(new CPacketPlayer(this.field_70122_E));
         }

         if (☃xxxxxx) {
            this.field_175172_bI = this.field_70165_t;
            this.field_175166_bJ = ☃.field_72338_b;
            this.field_175167_bK = this.field_70161_v;
            this.field_175168_bP = 0;
         }

         if (☃xxxxxxx) {
            this.field_175164_bL = this.field_70177_z;
            this.field_175165_bM = this.field_70125_A;
         }

         this.field_184841_cd = this.field_70122_E;
         this.field_189811_cr = this.field_71159_c.field_71474_y.field_189989_R;
      }
   }

   @Nullable
   @Override
   public EntityItem func_71040_bB(boolean var1) {
      CPacketPlayerDigging.Action ☃ = ☃ ? CPacketPlayerDigging.Action.DROP_ALL_ITEMS : CPacketPlayerDigging.Action.DROP_ITEM;
      this.field_71174_a.func_147297_a(new CPacketPlayerDigging(☃, BlockPos.field_177992_a, EnumFacing.DOWN));
      this.field_71071_by
         .func_70298_a(
            this.field_71071_by.field_70461_c,
            ☃ && !this.field_71071_by.func_70448_g().func_190926_b() ? this.field_71071_by.func_70448_g().func_190916_E() : 1
         );
      return null;
   }

   @Override
   protected ItemStack func_184816_a(EntityItem var1) {
      return ItemStack.field_190927_a;
   }

   public void func_71165_d(String var1) {
      this.field_71174_a.func_147297_a(new CPacketChatMessage(☃));
   }

   @Override
   public void func_184609_a(EnumHand var1) {
      super.func_184609_a(☃);
      this.field_71174_a.func_147297_a(new CPacketAnimation(☃));
   }

   @Override
   public void func_71004_bE() {
      this.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
   }

   @Override
   protected void func_70665_d(DamageSource var1, float var2) {
      if (!this.func_180431_b(☃)) {
         this.func_70606_j(this.func_110143_aJ() - ☃);
      }
   }

   @Override
   public void func_71053_j() {
      this.field_71174_a.func_147297_a(new CPacketCloseWindow(this.field_71070_bA.field_75152_c));
      this.func_175159_q();
   }

   public void func_175159_q() {
      this.field_71071_by.func_70437_b(ItemStack.field_190927_a);
      super.func_71053_j();
      this.field_71159_c.func_147108_a(null);
   }

   public void func_71150_b(float var1) {
      if (this.field_175169_bQ) {
         float ☃ = this.func_110143_aJ() - ☃;
         if (☃ <= 0.0F) {
            this.func_70606_j(☃);
            if (☃ < 0.0F) {
               this.field_70172_ad = this.field_70771_an / 2;
            }
         } else {
            this.field_110153_bc = ☃;
            this.func_70606_j(this.func_110143_aJ());
            this.field_70172_ad = this.field_70771_an;
            this.func_70665_d(DamageSource.field_76377_j, ☃);
            this.field_70738_aO = 10;
            this.field_70737_aN = this.field_70738_aO;
         }
      } else {
         this.func_70606_j(☃);
         this.field_175169_bQ = true;
      }
   }

   @Override
   public void func_71016_p() {
      this.field_71174_a.func_147297_a(new CPacketPlayerAbilities(this.field_71075_bZ));
   }

   @Override
   public boolean func_175144_cb() {
      return true;
   }

   protected void func_110318_g() {
      this.field_71174_a
         .func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_RIDING_JUMP, MathHelper.func_76141_d(this.func_110319_bJ() * 100.0F)));
   }

   public void func_175163_u() {
      this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.OPEN_INVENTORY));
   }

   public void func_175158_f(String var1) {
      this.field_142022_ce = ☃;
   }

   public String func_142021_k() {
      return this.field_142022_ce;
   }

   public StatisticsManager func_146107_m() {
      return this.field_146108_bO;
   }

   public RecipeBookClient func_199507_B() {
      return this.field_192036_cb;
   }

   public void func_193103_a(IRecipe var1) {
      if (this.field_192036_cb.func_194076_e(☃)) {
         this.field_192036_cb.func_194074_f(☃);
         this.field_71174_a.func_147297_a(new CPacketRecipeInfo(☃));
      }
   }

   @Override
   protected int func_184840_I() {
      return this.field_184845_bX;
   }

   public void func_184839_n(int var1) {
      this.field_184845_bX = ☃;
   }

   @Override
   public void func_146105_b(ITextComponent var1, boolean var2) {
      if (☃) {
         this.field_71159_c.field_71456_v.func_175188_a(☃, false);
      } else {
         this.field_71159_c.field_71456_v.func_146158_b().func_146227_a(☃);
      }
   }

   @Override
   protected boolean func_145771_j(double var1, double var3, double var5) {
      if (this.field_70145_X) {
         return false;
      } else {
         BlockPos ☃ = new BlockPos(☃, ☃, ☃);
         double ☃x = ☃ - (double)☃.func_177958_n();
         double ☃xx = ☃ - (double)☃.func_177952_p();
         if (this.func_205027_h(☃)) {
            int ☃xxx = -1;
            double ☃xxxx = 9999.0;
            if (this.func_207402_f(☃.func_177976_e()) && ☃x < ☃xxxx) {
               ☃xxxx = ☃x;
               ☃xxx = 0;
            }

            if (this.func_207402_f(☃.func_177974_f()) && 1.0 - ☃x < ☃xxxx) {
               ☃xxxx = 1.0 - ☃x;
               ☃xxx = 1;
            }

            if (this.func_207402_f(☃.func_177978_c()) && ☃xx < ☃xxxx) {
               ☃xxxx = ☃xx;
               ☃xxx = 4;
            }

            if (this.func_207402_f(☃.func_177968_d()) && 1.0 - ☃xx < ☃xxxx) {
               ☃xxxx = 1.0 - ☃xx;
               ☃xxx = 5;
            }

            float ☃xxx = 0.1F;
            if (☃xxx == 0) {
               this.field_70159_w = -0.1F;
            }

            if (☃xxx == 1) {
               this.field_70159_w = 0.1F;
            }

            if (☃xxx == 4) {
               this.field_70179_y = -0.1F;
            }

            if (☃xxx == 5) {
               this.field_70179_y = 0.1F;
            }
         }

         return false;
      }
   }

   private boolean func_205027_h(BlockPos var1) {
      if (this.func_203007_ba()) {
         return !this.func_207401_g(☃);
      } else {
         return !this.func_207402_f(☃);
      }
   }

   @Override
   public void func_70031_b(boolean var1) {
      super.func_70031_b(☃);
      this.field_71157_e = 0;
   }

   public void func_71152_a(float var1, int var2, int var3) {
      this.field_71106_cc = ☃;
      this.field_71067_cb = ☃;
      this.field_71068_ca = ☃;
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
      this.field_71159_c.field_71456_v.func_146158_b().func_146227_a(☃);
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ >= 24 && ☃ <= 28) {
         this.func_184839_n(☃ - 24);
      } else {
         super.func_70103_a(☃);
      }
   }

   @Override
   public void func_184185_a(SoundEvent var1, float var2, float var3) {
      this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃, this.func_184176_by(), ☃, ☃, false);
   }

   @Override
   public boolean func_70613_aW() {
      return true;
   }

   @Override
   public void func_184598_c(EnumHand var1) {
      ItemStack ☃ = this.func_184586_b(☃);
      if (!☃.func_190926_b() && !this.func_184587_cr()) {
         super.func_184598_c(☃);
         this.field_184842_cm = true;
         this.field_184843_cn = ☃;
      }
   }

   @Override
   public boolean func_184587_cr() {
      return this.field_184842_cm;
   }

   @Override
   public void func_184602_cy() {
      super.func_184602_cy();
      this.field_184842_cm = false;
   }

   @Override
   public EnumHand func_184600_cs() {
      return this.field_184843_cn;
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      super.func_184206_a(☃);
      if (field_184621_as.equals(☃)) {
         boolean ☃ = (this.field_70180_af.func_187225_a(field_184621_as) & 1) > 0;
         EnumHand ☃x = (this.field_70180_af.func_187225_a(field_184621_as) & 2) > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
         if (☃ && !this.field_184842_cm) {
            this.func_184598_c(☃x);
         } else if (!☃ && this.field_184842_cm) {
            this.func_184602_cy();
         }
      }

      if (field_184240_ax.equals(☃) && this.func_184613_cA() && !this.field_189813_ct) {
         this.field_71159_c.func_147118_V().func_147682_a(new ElytraSound(this));
      }
   }

   public boolean func_110317_t() {
      Entity ☃ = this.func_184187_bx();
      return this.func_184218_aH() && ☃ instanceof IJumpingMount && ((IJumpingMount)☃).func_184776_b();
   }

   public float func_110319_bJ() {
      return this.field_110321_bQ;
   }

   @Override
   public void func_175141_a(TileEntitySign var1) {
      this.field_71159_c.func_147108_a(new GuiEditSign(☃));
   }

   @Override
   public void func_184809_a(CommandBlockBaseLogic var1) {
      this.field_71159_c.func_147108_a(new GuiEditCommandBlockMinecart(☃));
   }

   @Override
   public void func_184824_a(TileEntityCommandBlock var1) {
      this.field_71159_c.func_147108_a(new GuiCommandBlock(☃));
   }

   @Override
   public void func_189807_a(TileEntityStructure var1) {
      this.field_71159_c.func_147108_a(new GuiEditStructure(☃));
   }

   @Override
   public void func_184814_a(ItemStack var1, EnumHand var2) {
      Item ☃ = ☃.func_77973_b();
      if (☃ == Items.field_151099_bA) {
         this.field_71159_c.func_147108_a(new GuiScreenBook(this, ☃, true, ☃));
      }
   }

   @Override
   public void func_71007_a(IInventory var1) {
      String ☃ = ☃ instanceof IInteractionObject ? ((IInteractionObject)☃).func_174875_k() : "minecraft:container";
      if ("minecraft:chest".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiChest(this.field_71071_by, ☃));
      } else if ("minecraft:hopper".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiHopper(this.field_71071_by, ☃));
      } else if ("minecraft:furnace".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiFurnace(this.field_71071_by, ☃));
      } else if ("minecraft:brewing_stand".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiBrewingStand(this.field_71071_by, ☃));
      } else if ("minecraft:beacon".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiBeacon(this.field_71071_by, ☃));
      } else if ("minecraft:dispenser".equals(☃) || "minecraft:dropper".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiDispenser(this.field_71071_by, ☃));
      } else if ("minecraft:shulker_box".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiShulkerBox(this.field_71071_by, ☃));
      } else {
         this.field_71159_c.func_147108_a(new GuiChest(this.field_71071_by, ☃));
      }
   }

   @Override
   public void func_184826_a(AbstractHorse var1, IInventory var2) {
      this.field_71159_c.func_147108_a(new GuiScreenHorseInventory(this.field_71071_by, ☃, ☃));
   }

   @Override
   public void func_180468_a(IInteractionObject var1) {
      String ☃ = ☃.func_174875_k();
      if ("minecraft:crafting_table".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiCrafting(this.field_71071_by, this.field_70170_p));
      } else if ("minecraft:enchanting_table".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiEnchantment(this.field_71071_by, this.field_70170_p, ☃));
      } else if ("minecraft:anvil".equals(☃)) {
         this.field_71159_c.func_147108_a(new GuiRepair(this.field_71071_by, this.field_70170_p));
      }
   }

   @Override
   public void func_180472_a(IMerchant var1) {
      this.field_71159_c.func_147108_a(new GuiMerchant(this.field_71071_by, ☃, this.field_70170_p));
   }

   @Override
   public void func_71009_b(Entity var1) {
      this.field_71159_c.field_71452_i.func_199282_a(☃, Particles.field_197614_g);
   }

   @Override
   public void func_71047_c(Entity var1) {
      this.field_71159_c.field_71452_i.func_199282_a(☃, Particles.field_197622_o);
   }

   @Override
   public boolean func_70093_af() {
      boolean ☃ = this.field_71158_b != null && this.field_71158_b.field_78899_d;
      return ☃ && !this.field_71083_bS;
   }

   @Override
   public void func_70626_be() {
      super.func_70626_be();
      if (this.func_175160_A()) {
         this.field_70702_br = this.field_71158_b.field_78902_a;
         this.field_191988_bg = this.field_71158_b.field_192832_b;
         this.field_70703_bu = this.field_71158_b.field_78901_c;
         this.field_71163_h = this.field_71154_f;
         this.field_71164_i = this.field_71155_g;
         this.field_71155_g = (float)((double)this.field_71155_g + (double)(this.field_70125_A - this.field_71155_g) * 0.5);
         this.field_71154_f = (float)((double)this.field_71154_f + (double)(this.field_70177_z - this.field_71154_f) * 0.5);
      }
   }

   protected boolean func_175160_A() {
      return this.field_71159_c.func_175606_aa() == this;
   }

   @Override
   public void func_70636_d() {
      ++this.field_71157_e;
      if (this.field_71156_d > 0) {
         --this.field_71156_d;
      }

      this.field_71080_cy = this.field_71086_bY;
      if (this.field_71087_bX) {
         if (this.field_71159_c.field_71462_r != null && !this.field_71159_c.field_71462_r.func_73868_f()) {
            if (this.field_71159_c.field_71462_r instanceof GuiContainer) {
               this.func_71053_j();
            }

            this.field_71159_c.func_147108_a(null);
         }

         if (this.field_71086_bY == 0.0F) {
            this.field_71159_c
               .func_147118_V()
               .func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187814_ei, this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
         }

         this.field_71086_bY += 0.0125F;
         if (this.field_71086_bY >= 1.0F) {
            this.field_71086_bY = 1.0F;
         }

         this.field_71087_bX = false;
      } else if (this.func_70644_a(MobEffects.field_76431_k) && this.func_70660_b(MobEffects.field_76431_k).func_76459_b() > 60) {
         this.field_71086_bY += 0.006666667F;
         if (this.field_71086_bY > 1.0F) {
            this.field_71086_bY = 1.0F;
         }
      } else {
         if (this.field_71086_bY > 0.0F) {
            this.field_71086_bY -= 0.05F;
         }

         if (this.field_71086_bY < 0.0F) {
            this.field_71086_bY = 0.0F;
         }
      }

      if (this.field_71088_bW > 0) {
         --this.field_71088_bW;
      }

      boolean ☃ = this.field_71158_b.field_78901_c;
      boolean ☃x = this.field_71158_b.field_78899_d;
      float ☃xx = 0.8F;
      boolean ☃xxx = this.field_71158_b.field_192832_b >= 0.8F;
      this.field_71158_b.func_78898_a();
      this.field_71159_c.func_193032_ao().func_193293_a(this.field_71158_b);
      if (this.func_184587_cr() && !this.func_184218_aH()) {
         this.field_71158_b.field_78902_a *= 0.2F;
         this.field_71158_b.field_192832_b *= 0.2F;
         this.field_71156_d = 0;
      }

      boolean ☃ = false;
      if (this.field_189812_cs > 0) {
         --this.field_189812_cs;
         ☃ = true;
         this.field_71158_b.field_78901_c = true;
      }

      AxisAlignedBB ☃ = this.func_174813_aQ();
      this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35, ☃.field_72338_b + 0.5, this.field_70161_v + (double)this.field_70130_N * 0.35);
      this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35, ☃.field_72338_b + 0.5, this.field_70161_v - (double)this.field_70130_N * 0.35);
      this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35, ☃.field_72338_b + 0.5, this.field_70161_v - (double)this.field_70130_N * 0.35);
      this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35, ☃.field_72338_b + 0.5, this.field_70161_v + (double)this.field_70130_N * 0.35);
      boolean ☃x = (float)this.func_71024_bL().func_75116_a() > 6.0F || this.field_71075_bZ.field_75101_c;
      if ((this.field_70122_E || this.func_204231_K())
         && !☃x
         && !☃xxx
         && this.field_71158_b.field_192832_b >= 0.8F
         && !this.func_70051_ag()
         && ☃x
         && !this.func_184587_cr()
         && !this.func_70644_a(MobEffects.field_76440_q)) {
         if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
            this.field_71156_d = 7;
         } else {
            this.func_70031_b(true);
         }
      }

      if (!this.func_70051_ag()
         && (!this.func_70090_H() || this.func_204231_K())
         && this.field_71158_b.field_192832_b >= 0.8F
         && ☃x
         && !this.func_184587_cr()
         && !this.func_70644_a(MobEffects.field_76440_q)
         && this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
         this.func_70031_b(true);
      }

      if (this.func_70051_ag()) {
         boolean ☃ = this.field_71158_b.field_192832_b < 0.8F || !☃x;
         boolean ☃x = ☃ || this.field_70123_F || this.func_70090_H() && !this.func_204231_K();
         if (this.func_203007_ba()) {
            if (!this.field_70122_E && !this.field_71158_b.field_78899_d && ☃ || !this.func_70090_H()) {
               this.func_70031_b(false);
            }
         } else if (☃x) {
            this.func_70031_b(false);
         }
      }

      if (this.field_71075_bZ.field_75101_c) {
         if (this.field_71159_c.field_71442_b.func_178887_k()) {
            if (!this.field_71075_bZ.field_75100_b) {
               this.field_71075_bZ.field_75100_b = true;
               this.func_71016_p();
            }
         } else if (!☃ && this.field_71158_b.field_78901_c && !☃) {
            if (this.field_71101_bC == 0) {
               this.field_71101_bC = 7;
            } else if (!this.func_203007_ba()) {
               this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
               this.func_71016_p();
               this.field_71101_bC = 0;
            }
         }
      }

      if (this.field_71158_b.field_78901_c
         && !☃
         && !this.field_70122_E
         && this.field_70181_x < 0.0
         && !this.func_184613_cA()
         && !this.field_71075_bZ.field_75100_b) {
         ItemStack ☃ = this.func_184582_a(EntityEquipmentSlot.CHEST);
         if (☃.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(☃)) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_FALL_FLYING));
         }
      }

      this.field_189813_ct = this.func_184613_cA();
      if (this.func_70090_H() && this.field_71158_b.field_78899_d) {
         this.func_203010_cG();
      }

      if (this.func_208600_a(FluidTags.field_206959_a)) {
         int ☃ = this.func_175149_v() ? 10 : 1;
         this.field_203720_cz = MathHelper.func_76125_a(this.field_203720_cz + ☃, 0, 600);
      } else if (this.field_203720_cz > 0) {
         this.func_208600_a(FluidTags.field_206959_a);
         this.field_203720_cz = MathHelper.func_76125_a(this.field_203720_cz - 10, 0, 600);
      }

      if (this.field_71075_bZ.field_75100_b && this.func_175160_A()) {
         if (this.field_71158_b.field_78899_d) {
            this.field_71158_b.field_78902_a = (float)((double)this.field_71158_b.field_78902_a / 0.3);
            this.field_71158_b.field_192832_b = (float)((double)this.field_71158_b.field_192832_b / 0.3);
            this.field_70181_x -= (double)(this.field_71075_bZ.func_75093_a() * 3.0F);
         }

         if (this.field_71158_b.field_78901_c) {
            this.field_70181_x += (double)(this.field_71075_bZ.func_75093_a() * 3.0F);
         }
      }

      if (this.func_110317_t()) {
         IJumpingMount ☃ = (IJumpingMount)this.func_184187_bx();
         if (this.field_110320_a < 0) {
            ++this.field_110320_a;
            if (this.field_110320_a == 0) {
               this.field_110321_bQ = 0.0F;
            }
         }

         if (☃ && !this.field_71158_b.field_78901_c) {
            this.field_110320_a = -10;
            ☃.func_110206_u(MathHelper.func_76141_d(this.func_110319_bJ() * 100.0F));
            this.func_110318_g();
         } else if (!☃ && this.field_71158_b.field_78901_c) {
            this.field_110320_a = 0;
            this.field_110321_bQ = 0.0F;
         } else if (☃) {
            ++this.field_110320_a;
            if (this.field_110320_a < 10) {
               this.field_110321_bQ = (float)this.field_110320_a * 0.1F;
            } else {
               this.field_110321_bQ = 0.8F + 2.0F / (float)(this.field_110320_a - 9) * 0.1F;
            }
         }
      } else {
         this.field_110321_bQ = 0.0F;
      }

      super.func_70636_d();
      if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
         this.field_71075_bZ.field_75100_b = false;
         this.func_71016_p();
      }
   }

   @Override
   public void func_70098_U() {
      super.func_70098_U();
      this.field_184844_co = false;
      if (this.func_184187_bx() instanceof EntityBoat) {
         EntityBoat ☃ = (EntityBoat)this.func_184187_bx();
         ☃.func_184442_a(
            this.field_71158_b.field_187257_e, this.field_71158_b.field_187258_f, this.field_71158_b.field_187255_c, this.field_71158_b.field_187256_d
         );
         this.field_184844_co |= this.field_71158_b.field_187257_e
            || this.field_71158_b.field_187258_f
            || this.field_71158_b.field_187255_c
            || this.field_71158_b.field_187256_d;
      }
   }

   public boolean func_184838_M() {
      return this.field_184844_co;
   }

   @Nullable
   @Override
   public PotionEffect func_184596_c(@Nullable Potion var1) {
      if (☃ == MobEffects.field_76431_k) {
         this.field_71080_cy = 0.0F;
         this.field_71086_bY = 0.0F;
      }

      return super.func_184596_c(☃);
   }

   @Override
   public void func_70091_d(MoverType var1, double var2, double var4, double var6) {
      double ☃ = this.field_70165_t;
      double ☃x = this.field_70161_v;
      super.func_70091_d(☃, ☃, ☃, ☃);
      this.func_189810_i((float)(this.field_70165_t - ☃), (float)(this.field_70161_v - ☃x));
   }

   public boolean func_189809_N() {
      return this.field_189811_cr;
   }

   protected void func_189810_i(float var1, float var2) {
      if (this.func_189809_N()) {
         if (this.field_189812_cs <= 0 && this.field_70122_E && !this.func_70093_af() && !this.func_184218_aH()) {
            Vec2f ☃ = this.field_71158_b.func_190020_b();
            if (☃.field_189982_i != 0.0F || ☃.field_189983_j != 0.0F) {
               Vec3d ☃x = new Vec3d(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
               Vec3d ☃xx = new Vec3d(this.field_70165_t + (double)☃, this.func_174813_aQ().field_72338_b, this.field_70161_v + (double)☃);
               Vec3d ☃xxx = new Vec3d((double)☃, 0.0, (double)☃);
               float ☃xxxx = this.func_70689_ay();
               float ☃xxxxx = (float)☃xxx.func_189985_c();
               if (☃xxxxx <= 0.001F) {
                  float ☃xxxxxx = ☃xxxx * ☃.field_189982_i;
                  float ☃xxxxxxx = ☃xxxx * ☃.field_189983_j;
                  float ☃xxxxxxxx = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0));
                  float ☃xxxxxxxxx = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0));
                  ☃xxx = new Vec3d(
                     (double)(☃xxxxxx * ☃xxxxxxxxx - ☃xxxxxxx * ☃xxxxxxxx), ☃xxx.field_72448_b, (double)(☃xxxxxxx * ☃xxxxxxxxx + ☃xxxxxx * ☃xxxxxxxx)
                  );
                  ☃xxxxx = (float)☃xxx.func_189985_c();
                  if (☃xxxxx <= 0.001F) {
                     return;
                  }
               }

               float ☃x = (float)MathHelper.func_181161_i((double)☃xxxxx);
               Vec3d ☃xx = ☃xxx.func_186678_a((double)☃x);
               Vec3d ☃xxx = this.func_189651_aD();
               float ☃xxxx = (float)(☃xxx.field_72450_a * ☃xx.field_72450_a + ☃xxx.field_72449_c * ☃xx.field_72449_c);
               if (!(☃xxxx < -0.15F)) {
                  BlockPos ☃xxxxx = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72337_e, this.field_70161_v);
                  IBlockState ☃xxxxxx = this.field_70170_p.func_180495_p(☃xxxxx);
                  if (☃xxxxxx.func_196952_d(this.field_70170_p, ☃xxxxx).func_197766_b()) {
                     ☃xxxxx = ☃xxxxx.func_177984_a();
                     IBlockState ☃xxxxxxx = this.field_70170_p.func_180495_p(☃xxxxx);
                     if (☃xxxxxxx.func_196952_d(this.field_70170_p, ☃xxxxx).func_197766_b()) {
                        float ☃xxxxxxxx = 7.0F;
                        float ☃xxxxxxxxx = 1.2F;
                        if (this.func_70644_a(MobEffects.field_76430_j)) {
                           ☃xxxxxxxxx += (float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.75F;
                        }

                        float ☃xxxxxxxx = Math.max(☃xxxx * 7.0F, 1.0F / ☃x);
                        Vec3d ☃xxxxxxxxx = ☃xx.func_178787_e(☃xx.func_186678_a((double)☃xxxxxxxx));
                        float ☃xxxxxxxxxx = this.field_70130_N;
                        float ☃xxxxxxxxxxx = this.field_70131_O;
                        AxisAlignedBB ☃xxxxxxxxxxxx = new AxisAlignedBB(☃x, ☃xxxxxxxxx.func_72441_c(0.0, (double)☃xxxxxxxxxxx, 0.0))
                           .func_72314_b((double)☃xxxxxxxxxx, 0.0, (double)☃xxxxxxxxxx);
                        Vec3d var19 = ☃x.func_72441_c(0.0, 0.51F, 0.0);
                        ☃xxxxxxxxx = ☃xxxxxxxxx.func_72441_c(0.0, 0.51F, 0.0);
                        Vec3d ☃xxxxxxxxxxxxx = ☃xx.func_72431_c(new Vec3d(0.0, 1.0, 0.0));
                        Vec3d ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_186678_a((double)(☃xxxxxxxxxx * 0.5F));
                        Vec3d ☃xxxxxxxxxxxxxxx = var19.func_178788_d(☃xxxxxxxxxxxxxx);
                        Vec3d ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxx.func_178788_d(☃xxxxxxxxxxxxxx);
                        Vec3d ☃xxxxxxxxxxxxxxxxx = var19.func_178787_e(☃xxxxxxxxxxxxxx);
                        Vec3d ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxx.func_178787_e(☃xxxxxxxxxxxxxx);
                        Iterator<AxisAlignedBB> ☃xxxxxxxxxxxxxxxxxxx = this.field_70170_p
                           .func_212388_b(this, ☃xxxxxxxxxxxx)
                           .flatMap(var0 -> var0.func_197756_d().stream())
                           .iterator();
                        float ☃xxxxxxxxxxxxxxxxxxxx = Float.MIN_VALUE;

                        while(☃xxxxxxxxxxxxxxxxxxx.hasNext()) {
                           AxisAlignedBB ☃xxxxxxxxxxxxxxxxxxxxx = (AxisAlignedBB)☃xxxxxxxxxxxxxxxxxxx.next();
                           if (☃xxxxxxxxxxxxxxxxxxxxx.func_189973_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx)
                              || ☃xxxxxxxxxxxxxxxxxxxxx.func_189973_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx)) {
                              ☃xxxxxxxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxxxxxxxxx.field_72337_e;
                              Vec3d ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxx.func_189972_c();
                              BlockPos ☃xxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(☃xxxxxxxxxxxxxxxxxxxxxx);

                              for(int ☃xxxxxxxxxxxxxxxxxxxxxxxx = 1; (float)☃xxxxxxxxxxxxxxxxxxxxxxxx < ☃xxxxxxxxx; ++☃xxxxxxxxxxxxxxxxxxxxxxxx) {
                                 BlockPos ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxx.func_177981_b(☃xxxxxxxxxxxxxxxxxxxxxxxx);
                                 IBlockState ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
                                 VoxelShape ☃xxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (!(☃xxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx.func_196952_d(this.field_70170_p, ☃xxxxxxxxxxxxxxxxxxxxxxxxxx))
                                    .func_197766_b()) {
                                    ☃xxxxxxxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxxxxxxxxxxxxx.func_197758_c(EnumFacing.Axis.Y)
                                       + (float)☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_177956_o();
                                    if ((double)☃xxxxxxxxxxxxxxxxxxxx - this.func_174813_aQ().field_72338_b > (double)☃xxxxxxxxx) {
                                       return;
                                    }
                                 }

                                 if (☃xxxxxxxxxxxxxxxxxxxxxxxx > 1) {
                                    ☃xxxxx = ☃xxxxx.func_177984_a();
                                    IBlockState ☃xxxxxxxxxxxxxxxxxxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxxx);
                                    if (!☃xxxxxxxxxxxxxxxxxxxxxxxxx.func_196952_d(this.field_70170_p, ☃xxxxx).func_197766_b()) {
                                       return;
                                    }
                                 }
                              }
                              break;
                           }
                        }

                        if (☃xxxxxxxxxxxxxxxxxxxx != Float.MIN_VALUE) {
                           float ☃xxxxxxxxxxxxxxxxxxxxx = (float)((double)☃xxxxxxxxxxxxxxxxxxxx - this.func_174813_aQ().field_72338_b);
                           if (!(☃xxxxxxxxxxxxxxxxxxxxx <= 0.5F) && !(☃xxxxxxxxxxxxxxxxxxxxx > ☃xxxxxxxxx)) {
                              this.field_189812_cs = 1;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public float func_203719_J() {
      if (!this.func_208600_a(FluidTags.field_206959_a)) {
         return 0.0F;
      } else {
         float ☃ = 600.0F;
         float ☃x = 100.0F;
         if ((float)this.field_203720_cz >= 600.0F) {
            return 1.0F;
         } else {
            float ☃ = MathHelper.func_76131_a((float)this.field_203720_cz / 100.0F, 0.0F, 1.0F);
            float ☃x = (float)this.field_203720_cz < 100.0F ? 0.0F : MathHelper.func_76131_a(((float)this.field_203720_cz - 100.0F) / 500.0F, 0.0F, 1.0F);
            return ☃ * 0.6F + ☃x * 0.39999998F;
         }
      }
   }

   @Override
   public boolean func_204231_K() {
      return this.field_204230_bP;
   }

   @Override
   protected boolean func_204229_de() {
      boolean ☃ = this.field_204230_bP;
      boolean ☃x = super.func_204229_de();
      if (this.func_175149_v()) {
         return this.field_204230_bP;
      } else {
         if (!☃ && ☃x) {
            this.field_70170_p
               .func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_204326_e, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
            this.field_71159_c.func_147118_V().func_147682_a(new UnderwaterAmbientSounds.UnderWaterSound(this));
         }

         if (☃ && !☃x) {
            this.field_70170_p
               .func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_204327_f, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
         }

         return this.field_204230_bP;
      }
   }
}
