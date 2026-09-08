package net.minecraft.network;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ServerRecipePlacer;
import net.minecraft.item.crafting.ServerRecipePlacerFurnace;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEditBook;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketNBTQueryEntity;
import net.minecraft.network.play.client.CPacketNBTQueryTileEntity;
import net.minecraft.network.play.client.CPacketPickItem;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRenameItem;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSelectTrade;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateBeacon;
import net.minecraft.network.play.client.CPacketUpdateCommandBlock;
import net.minecraft.network.play.client.CPacketUpdateCommandMinecart;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUpdateStructureBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketNBTQueryResponse;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
   private static final Logger field_147370_c = LogManager.getLogger();
   public final NetworkManager field_147371_a;
   private final MinecraftServer field_147367_d;
   public EntityPlayerMP field_147369_b;
   private int field_147368_e;
   private long field_194402_f;
   private boolean field_194403_g;
   private long field_194404_h;
   private int field_147374_l;
   private int field_147375_m;
   private final IntHashMap<Short> field_147372_n = new IntHashMap();
   private double field_184349_l;
   private double field_184350_m;
   private double field_184351_n;
   private double field_184352_o;
   private double field_184353_p;
   private double field_184354_q;
   private Entity field_184355_r;
   private double field_184356_s;
   private double field_184357_t;
   private double field_184358_u;
   private double field_184359_v;
   private double field_184360_w;
   private double field_184361_x;
   private Vec3d field_184362_y;
   private int field_184363_z;
   private int field_184343_A;
   private boolean field_184344_B;
   private int field_147365_f;
   private boolean field_184345_D;
   private int field_184346_E;
   private int field_184347_F;
   private int field_184348_G;

   public NetHandlerPlayServer(MinecraftServer var1, NetworkManager var2, EntityPlayerMP var3) {
      this.field_147367_d = ☃;
      this.field_147371_a = ☃;
      ☃.func_150719_a(this);
      this.field_147369_b = ☃;
      ☃.field_71135_a = this;
   }

   @Override
   public void func_73660_a() {
      this.func_184342_d();
      this.field_147369_b.func_71127_g();
      this.field_147369_b
         .func_70080_a(this.field_184349_l, this.field_184350_m, this.field_184351_n, this.field_147369_b.field_70177_z, this.field_147369_b.field_70125_A);
      ++this.field_147368_e;
      this.field_184348_G = this.field_184347_F;
      if (this.field_184344_B) {
         if (++this.field_147365_f > 80) {
            field_147370_c.warn("{} was kicked for floating too long!", this.field_147369_b.func_200200_C_().getString());
            this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.flying"));
            return;
         }
      } else {
         this.field_184344_B = false;
         this.field_147365_f = 0;
      }

      this.field_184355_r = this.field_147369_b.func_184208_bv();
      if (this.field_184355_r != this.field_147369_b && this.field_184355_r.func_184179_bs() == this.field_147369_b) {
         this.field_184356_s = this.field_184355_r.field_70165_t;
         this.field_184357_t = this.field_184355_r.field_70163_u;
         this.field_184358_u = this.field_184355_r.field_70161_v;
         this.field_184359_v = this.field_184355_r.field_70165_t;
         this.field_184360_w = this.field_184355_r.field_70163_u;
         this.field_184361_x = this.field_184355_r.field_70161_v;
         if (this.field_184345_D && this.field_147369_b.func_184208_bv().func_184179_bs() == this.field_147369_b) {
            if (++this.field_184346_E > 80) {
               field_147370_c.warn("{} was kicked for floating a vehicle too long!", this.field_147369_b.func_200200_C_().getString());
               this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.flying"));
               return;
            }
         } else {
            this.field_184345_D = false;
            this.field_184346_E = 0;
         }
      } else {
         this.field_184355_r = null;
         this.field_184345_D = false;
         this.field_184346_E = 0;
      }

      this.field_147367_d.field_71304_b.func_76320_a("keepAlive");
      long ☃ = Util.func_211177_b();
      if (☃ - this.field_194402_f >= 15000L) {
         if (this.field_194403_g) {
            this.func_194028_b(new TextComponentTranslation("disconnect.timeout"));
         } else {
            this.field_194403_g = true;
            this.field_194402_f = ☃;
            this.field_194404_h = ☃;
            this.func_147359_a(new SPacketKeepAlive(this.field_194404_h));
         }
      }

      this.field_147367_d.field_71304_b.func_76319_b();
      if (this.field_147374_l > 0) {
         --this.field_147374_l;
      }

      if (this.field_147375_m > 0) {
         --this.field_147375_m;
      }

      if (this.field_147369_b.func_154331_x() > 0L
         && this.field_147367_d.func_143007_ar() > 0
         && Util.func_211177_b() - this.field_147369_b.func_154331_x() > (long)(this.field_147367_d.func_143007_ar() * 1000 * 60)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.idling"));
      }
   }

   public void func_184342_d() {
      this.field_184349_l = this.field_147369_b.field_70165_t;
      this.field_184350_m = this.field_147369_b.field_70163_u;
      this.field_184351_n = this.field_147369_b.field_70161_v;
      this.field_184352_o = this.field_147369_b.field_70165_t;
      this.field_184353_p = this.field_147369_b.field_70163_u;
      this.field_184354_q = this.field_147369_b.field_70161_v;
   }

   public NetworkManager func_147362_b() {
      return this.field_147371_a;
   }

   public void func_194028_b(ITextComponent var1) {
      this.field_147371_a.func_201058_a(new SPacketDisconnect(☃), var2 -> this.field_147371_a.func_150718_a(☃));
      this.field_147371_a.func_150721_g();
      Futures.getUnchecked(this.field_147367_d.func_152344_a(this.field_147371_a::func_179293_l));
   }

   @Override
   public void func_147358_a(CPacketInput var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_110430_a(☃.func_149620_c(), ☃.func_192620_b(), ☃.func_149618_e(), ☃.func_149617_f());
   }

   private static boolean func_183006_b(CPacketPlayer var0) {
      if (Doubles.isFinite(☃.func_186997_a(0.0))
         && Doubles.isFinite(☃.func_186996_b(0.0))
         && Doubles.isFinite(☃.func_187000_c(0.0))
         && Floats.isFinite(☃.func_186998_b(0.0F))
         && Floats.isFinite(☃.func_186999_a(0.0F))) {
         return Math.abs(☃.func_186997_a(0.0)) > 3.0E7 || Math.abs(☃.func_186996_b(0.0)) > 3.0E7 || Math.abs(☃.func_187000_c(0.0)) > 3.0E7;
      } else {
         return true;
      }
   }

   private static boolean func_184341_b(CPacketVehicleMove var0) {
      return !Doubles.isFinite(☃.func_187004_a())
         || !Doubles.isFinite(☃.func_187002_b())
         || !Doubles.isFinite(☃.func_187003_c())
         || !Floats.isFinite(☃.func_187005_e())
         || !Floats.isFinite(☃.func_187006_d());
   }

   @Override
   public void func_184338_a(CPacketVehicleMove var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (func_184341_b(☃)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_vehicle_movement"));
      } else {
         Entity ☃ = this.field_147369_b.func_184208_bv();
         if (☃ != this.field_147369_b && ☃.func_184179_bs() == this.field_147369_b && ☃ == this.field_184355_r) {
            WorldServer ☃x = this.field_147369_b.func_71121_q();
            double ☃xx = ☃.field_70165_t;
            double ☃xxx = ☃.field_70163_u;
            double ☃xxxx = ☃.field_70161_v;
            double ☃xxxxx = ☃.func_187004_a();
            double ☃xxxxxx = ☃.func_187002_b();
            double ☃xxxxxxx = ☃.func_187003_c();
            float ☃xxxxxxxx = ☃.func_187006_d();
            float ☃xxxxxxxxx = ☃.func_187005_e();
            double ☃xxxxxxxxxx = ☃xxxxx - this.field_184356_s;
            double ☃xxxxxxxxxxx = ☃xxxxxx - this.field_184357_t;
            double ☃xxxxxxxxxxxx = ☃xxxxxxx - this.field_184358_u;
            double ☃xxxxxxxxxxxxx = ☃.field_70159_w * ☃.field_70159_w + ☃.field_70181_x * ☃.field_70181_x + ☃.field_70179_y * ☃.field_70179_y;
            double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx;
            if (☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx > 100.0
               && (!this.field_147367_d.func_71264_H() || !this.field_147367_d.func_71214_G().equals(☃.func_200200_C_().getString()))) {
               field_147370_c.warn(
                  "{} (vehicle of {}) moved too quickly! {},{},{}",
                  ☃.func_200200_C_().getString(),
                  this.field_147369_b.func_200200_C_().getString(),
                  ☃xxxxxxxxxx,
                  ☃xxxxxxxxxxx,
                  ☃xxxxxxxxxxxx
               );
               this.field_147371_a.func_179290_a(new SPacketMoveVehicle(☃));
               return;
            }

            boolean ☃x = ☃x.func_195586_b(☃, ☃.func_174813_aQ().func_186664_h(0.0625));
            ☃xxxxxxxxxx = ☃xxxxx - this.field_184359_v;
            ☃xxxxxxxxxxx = ☃xxxxxx - this.field_184360_w - 1.0E-6;
            ☃xxxxxxxxxxxx = ☃xxxxxxx - this.field_184361_x;
            ☃.func_70091_d(MoverType.PLAYER, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
            ☃xxxxxxxxxx = ☃xxxxx - ☃.field_70165_t;
            ☃xxxxxxxxxxx = ☃xxxxxx - ☃.field_70163_u;
            if (☃xxxxxxxxxxx > -0.5 || ☃xxxxxxxxxxx < 0.5) {
               ☃xxxxxxxxxxx = 0.0;
            }

            ☃xxxxxxxxxxxx = ☃xxxxxxx - ☃.field_70161_v;
            ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx;
            boolean ☃x = false;
            if (☃xxxxxxxxxxxxxx > 0.0625) {
               ☃x = true;
               field_147370_c.warn("{} moved wrongly!", ☃.func_200200_C_().getString());
            }

            ☃.func_70080_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
            boolean ☃x = ☃x.func_195586_b(☃, ☃.func_174813_aQ().func_186664_h(0.0625));
            if (☃x && (☃x || !☃x)) {
               ☃.func_70080_a(☃xx, ☃xxx, ☃xxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
               this.field_147371_a.func_179290_a(new SPacketMoveVehicle(☃));
               return;
            }

            this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
            this.field_147369_b
               .func_71000_j(this.field_147369_b.field_70165_t - ☃xx, this.field_147369_b.field_70163_u - ☃xxx, this.field_147369_b.field_70161_v - ☃xxxx);
            this.field_184345_D = ☃xxxxxxxxxxx >= -0.03125
               && !this.field_147367_d.func_71231_X()
               && !☃x.func_72829_c(☃.func_174813_aQ().func_186662_g(0.0625).func_72321_a(0.0, -0.55, 0.0));
            this.field_184359_v = ☃.field_70165_t;
            this.field_184360_w = ☃.field_70163_u;
            this.field_184361_x = ☃.field_70161_v;
         }
      }
   }

   @Override
   public void func_184339_a(CPacketConfirmTeleport var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (☃.func_186987_a() == this.field_184363_z) {
         this.field_147369_b
            .func_70080_a(
               this.field_184362_y.field_72450_a,
               this.field_184362_y.field_72448_b,
               this.field_184362_y.field_72449_c,
               this.field_147369_b.field_70177_z,
               this.field_147369_b.field_70125_A
            );
         this.field_184352_o = this.field_184362_y.field_72450_a;
         this.field_184353_p = this.field_184362_y.field_72448_b;
         this.field_184354_q = this.field_184362_y.field_72449_c;
         if (this.field_147369_b.func_184850_K()) {
            this.field_147369_b.func_184846_L();
         }

         this.field_184362_y = null;
      }
   }

   @Override
   public void func_191984_a(CPacketRecipeInfo var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (☃.func_194156_a() == CPacketRecipeInfo.Purpose.SHOWN) {
         IRecipe ☃ = this.field_147367_d.func_199529_aN().func_199517_a(☃.func_199619_b());
         if (☃ != null) {
            this.field_147369_b.func_192037_E().func_194074_f(☃);
         }
      } else if (☃.func_194156_a() == CPacketRecipeInfo.Purpose.SETTINGS) {
         this.field_147369_b.func_192037_E().func_192813_a(☃.func_192624_c());
         this.field_147369_b.func_192037_E().func_192810_b(☃.func_192625_d());
         this.field_147369_b.func_192037_E().func_202881_c(☃.func_202496_e());
         this.field_147369_b.func_192037_E().func_202882_d(☃.func_202497_f());
      }
   }

   @Override
   public void func_194027_a(CPacketSeenAdvancements var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (☃.func_194162_b() == CPacketSeenAdvancements.Action.OPENED_TAB) {
         ResourceLocation ☃ = ☃.func_194165_c();
         Advancement ☃x = this.field_147367_d.func_191949_aK().func_192778_a(☃);
         if (☃x != null) {
            this.field_147369_b.func_192039_O().func_194220_a(☃x);
         }
      }
   }

   @Override
   public void func_195518_a(CPacketTabComplete var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      StringReader ☃ = new StringReader(☃.func_197707_b());
      if (☃.canRead() && ☃.peek() == '/') {
         ☃.skip();
      }

      ParseResults<CommandSource> ☃ = this.field_147367_d.func_195571_aL().func_197054_a().parse(☃, this.field_147369_b.func_195051_bN());
      this.field_147367_d
         .func_195571_aL()
         .func_197054_a()
         .getCompletionSuggestions(☃)
         .thenAccept(var2x -> this.field_147371_a.func_179290_a(new SPacketTabComplete(☃.func_197709_a(), var2x)));
   }

   @Override
   public void func_210153_a(CPacketUpdateCommandBlock var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (!this.field_147367_d.func_82356_Z()) {
         this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notEnabled"));
      } else if (!this.field_147369_b.func_195070_dx()) {
         this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notAllowed"));
      } else {
         CommandBlockBaseLogic ☃ = null;
         TileEntityCommandBlock ☃x = null;
         BlockPos ☃xx = ☃.func_210361_a();
         TileEntity ☃xxx = this.field_147369_b.field_70170_p.func_175625_s(☃xx);
         if (☃xxx instanceof TileEntityCommandBlock) {
            ☃x = (TileEntityCommandBlock)☃xxx;
            ☃ = ☃x.func_145993_a();
         }

         String ☃ = ☃.func_210359_b();
         boolean ☃x = ☃.func_210363_c();
         if (☃ != null) {
            EnumFacing ☃xx = this.field_147369_b.field_70170_p.func_180495_p(☃xx).func_177229_b(BlockCommandBlock.field_185564_a);
            switch(☃.func_210360_f()) {
               case SEQUENCE:
                  IBlockState ☃xxx = Blocks.field_185777_dd.func_176223_P();
                  this.field_147369_b
                     .field_70170_p
                     .func_180501_a(
                        ☃xx,
                        ☃xxx.func_206870_a(BlockCommandBlock.field_185564_a, ☃xx)
                           .func_206870_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(☃.func_210364_d())),
                        2
                     );
                  break;
               case AUTO:
                  IBlockState ☃xxxx = Blocks.field_185776_dc.func_176223_P();
                  this.field_147369_b
                     .field_70170_p
                     .func_180501_a(
                        ☃xx,
                        ☃xxxx.func_206870_a(BlockCommandBlock.field_185564_a, ☃xx)
                           .func_206870_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(☃.func_210364_d())),
                        2
                     );
                  break;
               case REDSTONE:
               default:
                  IBlockState ☃xxxxx = Blocks.field_150483_bI.func_176223_P();
                  this.field_147369_b
                     .field_70170_p
                     .func_180501_a(
                        ☃xx,
                        ☃xxxxx.func_206870_a(BlockCommandBlock.field_185564_a, ☃xx)
                           .func_206870_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(☃.func_210364_d())),
                        2
                     );
            }

            ☃xxx.func_145829_t();
            this.field_147369_b.field_70170_p.func_175690_a(☃xx, ☃xxx);
            ☃.func_145752_a(☃);
            ☃.func_175573_a(☃x);
            if (!☃x) {
               ☃.func_145750_b(null);
            }

            ☃x.func_184253_b(☃.func_210362_e());
            ☃.func_145756_e();
            if (!StringUtils.func_151246_b(☃)) {
               this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.setCommand.success", ☃));
            }
         }
      }
   }

   @Override
   public void func_210158_a(CPacketUpdateCommandMinecart var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (!this.field_147367_d.func_82356_Z()) {
         this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notEnabled"));
      } else if (!this.field_147369_b.func_195070_dx()) {
         this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notAllowed"));
      } else {
         CommandBlockBaseLogic ☃ = ☃.func_210371_a(this.field_147369_b.field_70170_p);
         if (☃ != null) {
            ☃.func_145752_a(☃.func_210372_a());
            ☃.func_175573_a(☃.func_210373_b());
            if (!☃.func_210373_b()) {
               ☃.func_145750_b(null);
            }

            ☃.func_145756_e();
            this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.setCommand.success", ☃.func_210372_a()));
         }
      }
   }

   @Override
   public void func_210152_a(CPacketPickItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.field_71071_by.func_184430_d(☃.func_210349_a());
      this.field_147369_b
         .field_71135_a
         .func_147359_a(
            new SPacketSetSlot(
               -2,
               this.field_147369_b.field_71071_by.field_70461_c,
               this.field_147369_b.field_71071_by.func_70301_a(this.field_147369_b.field_71071_by.field_70461_c)
            )
         );
      this.field_147369_b
         .field_71135_a
         .func_147359_a(new SPacketSetSlot(-2, ☃.func_210349_a(), this.field_147369_b.field_71071_by.func_70301_a(☃.func_210349_a())));
      this.field_147369_b.field_71135_a.func_147359_a(new SPacketHeldItemChange(this.field_147369_b.field_71071_by.field_70461_c));
   }

   @Override
   public void func_210155_a(CPacketRenameItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.field_71070_bA instanceof ContainerRepair) {
         ContainerRepair ☃ = (ContainerRepair)this.field_147369_b.field_71070_bA;
         String ☃x = SharedConstants.func_71565_a(☃.func_210351_a());
         if (☃x.length() <= 35) {
            ☃.func_82850_a(☃x);
         }
      }
   }

   @Override
   public void func_210154_a(CPacketUpdateBeacon var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.field_71070_bA instanceof ContainerBeacon) {
         ContainerBeacon ☃ = (ContainerBeacon)this.field_147369_b.field_71070_bA;
         Slot ☃x = ☃.func_75139_a(0);
         if (☃x.func_75216_d()) {
            ☃x.func_75209_a(1);
            IInventory ☃xx = ☃.func_180611_e();
            ☃xx.func_174885_b(1, ☃.func_210355_a());
            ☃xx.func_174885_b(2, ☃.func_210356_b());
            ☃xx.func_70296_d();
         }
      }
   }

   @Override
   public void func_210157_a(CPacketUpdateStructureBlock var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_195070_dx()) {
         BlockPos ☃ = ☃.func_210380_a();
         IBlockState ☃x = this.field_147369_b.field_70170_p.func_180495_p(☃);
         TileEntity ☃xx = this.field_147369_b.field_70170_p.func_175625_s(☃);
         if (☃xx instanceof TileEntityStructure) {
            TileEntityStructure ☃xxx = (TileEntityStructure)☃xx;
            ☃xxx.func_184405_a(☃.func_210378_c());
            ☃xxx.func_184404_a(☃.func_210377_d());
            ☃xxx.func_184414_b(☃.func_210383_e());
            ☃xxx.func_184409_c(☃.func_210385_f());
            ☃xxx.func_184411_a(☃.func_210386_g());
            ☃xxx.func_184408_a(☃.func_210379_h());
            ☃xxx.func_184410_b(☃.func_210388_i());
            ☃xxx.func_184406_a(☃.func_210389_j());
            ☃xxx.func_189703_e(☃.func_210390_k());
            ☃xxx.func_189710_f(☃.func_210387_l());
            ☃xxx.func_189718_a(☃.func_210382_m());
            ☃xxx.func_189725_a(☃.func_210381_n());
            if (☃xxx.func_208404_d()) {
               String ☃xxxx = ☃xxx.func_189715_d();
               if (☃.func_210384_b() == TileEntityStructure.UpdateCommand.SAVE_AREA) {
                  if (☃xxx.func_184419_m()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.save_success", ☃xxxx), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.save_failure", ☃xxxx), false);
                  }
               } else if (☃.func_210384_b() == TileEntityStructure.UpdateCommand.LOAD_AREA) {
                  if (!☃xxx.func_189709_F()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_not_found", ☃xxxx), false);
                  } else if (☃xxx.func_184412_n()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_success", ☃xxxx), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_prepare", ☃xxxx), false);
                  }
               } else if (☃.func_210384_b() == TileEntityStructure.UpdateCommand.SCAN_AREA) {
                  if (☃xxx.func_184417_l()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.size_success", ☃xxxx), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.size_failure"), false);
                  }
               }
            } else {
               this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.invalid_structure_name", ☃.func_210377_d()), false);
            }

            ☃xxx.func_70296_d();
            this.field_147369_b.field_70170_p.func_184138_a(☃, ☃x, ☃x, 3);
         }
      }
   }

   @Override
   public void func_210159_a(CPacketSelectTrade var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      int ☃ = ☃.func_210353_a();
      Container ☃x = this.field_147369_b.field_71070_bA;
      if (☃x instanceof ContainerMerchant) {
         ((ContainerMerchant)☃x).func_75175_c(☃);
      }
   }

   @Override
   public void func_210156_a(CPacketEditBook var1) {
      ItemStack ☃ = ☃.func_210346_a();
      if (!☃.func_190926_b()) {
         if (ItemWritableBook.func_150930_a(☃.func_77978_p())) {
            ItemStack ☃x = this.field_147369_b.func_184586_b(☃.func_212644_d());
            if (!☃x.func_190926_b()) {
               if (☃.func_77973_b() == Items.field_151099_bA && ☃x.func_77973_b() == Items.field_151099_bA) {
                  if (☃.func_210345_b()) {
                     ItemStack ☃xx = new ItemStack(Items.field_151164_bB);
                     ☃xx.func_77983_a("author", new NBTTagString(this.field_147369_b.func_200200_C_().getString()));
                     ☃xx.func_77983_a("title", new NBTTagString(☃.func_77978_p().func_74779_i("title")));
                     NBTTagList ☃xxx = ☃.func_77978_p().func_150295_c("pages", 8);

                     for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
                        String ☃xxxxx = ☃xxx.func_150307_f(☃xxxx);
                        ITextComponent ☃xxxxxx = new TextComponentString(☃xxxxx);
                        ☃xxxxx = ITextComponent.Serializer.func_150696_a(☃xxxxxx);
                        ☃xxx.set(☃xxxx, (INBTBase)(new NBTTagString(☃xxxxx)));
                     }

                     ☃xx.func_77983_a("pages", ☃xxx);
                     EntityEquipmentSlot ☃xxxx = ☃.func_212644_d() == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND;
                     this.field_147369_b.func_184201_a(☃xxxx, ☃xx);
                  } else {
                     ☃x.func_77983_a("pages", ☃.func_77978_p().func_150295_c("pages", 8));
                  }
               }
            }
         }
      }
   }

   @Override
   public void func_211526_a(CPacketNBTQueryEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_211513_k(2)) {
         Entity ☃ = this.field_147369_b.func_71121_q().func_73045_a(☃.func_211720_c());
         if (☃ != null) {
            NBTTagCompound ☃x = ☃.func_189511_e(new NBTTagCompound());
            this.field_147369_b.field_71135_a.func_147359_a(new SPacketNBTQueryResponse(☃.func_211721_b(), ☃x));
         }
      }
   }

   @Override
   public void func_211525_a(CPacketNBTQueryTileEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_211513_k(2)) {
         TileEntity ☃ = this.field_147369_b.func_71121_q().func_175625_s(☃.func_211717_c());
         NBTTagCompound ☃x = ☃ != null ? ☃.func_189515_b(new NBTTagCompound()) : null;
         this.field_147369_b.field_71135_a.func_147359_a(new SPacketNBTQueryResponse(☃.func_211716_b(), ☃x));
      }
   }

   @Override
   public void func_147347_a(CPacketPlayer var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (func_183006_b(☃)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_player_movement"));
      } else {
         WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
         if (!this.field_147369_b.field_71136_j) {
            if (this.field_147368_e == 0) {
               this.func_184342_d();
            }

            if (this.field_184362_y != null) {
               if (this.field_147368_e - this.field_184343_A > 20) {
                  this.field_184343_A = this.field_147368_e;
                  this.func_147364_a(
                     this.field_184362_y.field_72450_a,
                     this.field_184362_y.field_72448_b,
                     this.field_184362_y.field_72449_c,
                     this.field_147369_b.field_70177_z,
                     this.field_147369_b.field_70125_A
                  );
               }
            } else {
               this.field_184343_A = this.field_147368_e;
               if (this.field_147369_b.func_184218_aH()) {
                  this.field_147369_b
                     .func_70080_a(
                        this.field_147369_b.field_70165_t,
                        this.field_147369_b.field_70163_u,
                        this.field_147369_b.field_70161_v,
                        ☃.func_186999_a(this.field_147369_b.field_70177_z),
                        ☃.func_186998_b(this.field_147369_b.field_70125_A)
                     );
                  this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
               } else {
                  double ☃x = this.field_147369_b.field_70165_t;
                  double ☃xx = this.field_147369_b.field_70163_u;
                  double ☃xxx = this.field_147369_b.field_70161_v;
                  double ☃xxxx = this.field_147369_b.field_70163_u;
                  double ☃xxxxx = ☃.func_186997_a(this.field_147369_b.field_70165_t);
                  double ☃xxxxxx = ☃.func_186996_b(this.field_147369_b.field_70163_u);
                  double ☃xxxxxxx = ☃.func_187000_c(this.field_147369_b.field_70161_v);
                  float ☃xxxxxxxx = ☃.func_186999_a(this.field_147369_b.field_70177_z);
                  float ☃xxxxxxxxx = ☃.func_186998_b(this.field_147369_b.field_70125_A);
                  double ☃xxxxxxxxxx = ☃xxxxx - this.field_184349_l;
                  double ☃xxxxxxxxxxx = ☃xxxxxx - this.field_184350_m;
                  double ☃xxxxxxxxxxxx = ☃xxxxxxx - this.field_184351_n;
                  double ☃xxxxxxxxxxxxx = this.field_147369_b.field_70159_w * this.field_147369_b.field_70159_w
                     + this.field_147369_b.field_70181_x * this.field_147369_b.field_70181_x
                     + this.field_147369_b.field_70179_y * this.field_147369_b.field_70179_y;
                  double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx;
                  if (this.field_147369_b.func_70608_bn()) {
                     if (☃xxxxxxxxxxxxxx > 1.0) {
                        this.func_147364_a(
                           this.field_147369_b.field_70165_t,
                           this.field_147369_b.field_70163_u,
                           this.field_147369_b.field_70161_v,
                           ☃.func_186999_a(this.field_147369_b.field_70177_z),
                           ☃.func_186998_b(this.field_147369_b.field_70125_A)
                        );
                     }
                  } else {
                     ++this.field_184347_F;
                     int ☃x = this.field_184347_F - this.field_184348_G;
                     if (☃x > 5) {
                        field_147370_c.debug(
                           "{} is sending move packets too frequently ({} packets since last tick)", this.field_147369_b.func_200200_C_().getString(), ☃x
                        );
                        ☃x = 1;
                     }

                     if (!this.field_147369_b.func_184850_K()
                        && (
                           !this.field_147369_b.func_71121_q().func_82736_K().func_82766_b("disableElytraMovementCheck")
                              || !this.field_147369_b.func_184613_cA()
                        )) {
                        float ☃x = this.field_147369_b.func_184613_cA() ? 300.0F : 100.0F;
                        if (☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx > (double)(☃x * (float)☃x)
                           && (
                              !this.field_147367_d.func_71264_H() || !this.field_147367_d.func_71214_G().equals(this.field_147369_b.func_146103_bH().getName())
                           )) {
                           field_147370_c.warn(
                              "{} moved too quickly! {},{},{}", this.field_147369_b.func_200200_C_().getString(), ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx
                           );
                           this.func_147364_a(
                              this.field_147369_b.field_70165_t,
                              this.field_147369_b.field_70163_u,
                              this.field_147369_b.field_70161_v,
                              this.field_147369_b.field_70177_z,
                              this.field_147369_b.field_70125_A
                           );
                           return;
                        }
                     }

                     boolean ☃x = ☃.func_195586_b(this.field_147369_b, this.field_147369_b.func_174813_aQ().func_186664_h(0.0625));
                     ☃xxxxxxxxxx = ☃xxxxx - this.field_184352_o;
                     ☃xxxxxxxxxxx = ☃xxxxxx - this.field_184353_p;
                     ☃xxxxxxxxxxxx = ☃xxxxxxx - this.field_184354_q;
                     if (this.field_147369_b.field_70122_E && !☃.func_149465_i() && ☃xxxxxxxxxxx > 0.0) {
                        this.field_147369_b.func_70664_aZ();
                     }

                     this.field_147369_b.func_70091_d(MoverType.PLAYER, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
                     this.field_147369_b.field_70122_E = ☃.func_149465_i();
                     ☃xxxxxxxxxx = ☃xxxxx - this.field_147369_b.field_70165_t;
                     ☃xxxxxxxxxxx = ☃xxxxxx - this.field_147369_b.field_70163_u;
                     if (☃xxxxxxxxxxx > -0.5 || ☃xxxxxxxxxxx < 0.5) {
                        ☃xxxxxxxxxxx = 0.0;
                     }

                     ☃xxxxxxxxxxxx = ☃xxxxxxx - this.field_147369_b.field_70161_v;
                     ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx;
                     boolean ☃x = false;
                     if (!this.field_147369_b.func_184850_K()
                        && ☃xxxxxxxxxxxxxx > 0.0625
                        && !this.field_147369_b.func_70608_bn()
                        && !this.field_147369_b.field_71134_c.func_73083_d()
                        && this.field_147369_b.field_71134_c.func_73081_b() != GameType.SPECTATOR) {
                        ☃x = true;
                        field_147370_c.warn("{} moved wrongly!", this.field_147369_b.func_200200_C_().getString());
                     }

                     this.field_147369_b.func_70080_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
                     this.field_147369_b
                        .func_71000_j(this.field_147369_b.field_70165_t - ☃x, this.field_147369_b.field_70163_u - ☃xx, this.field_147369_b.field_70161_v - ☃xxx);
                     if (!this.field_147369_b.field_70145_X && !this.field_147369_b.func_70608_bn()) {
                        boolean ☃x = ☃.func_195586_b(this.field_147369_b, this.field_147369_b.func_174813_aQ().func_186664_h(0.0625));
                        if (☃x && (☃x || !☃x)) {
                           this.func_147364_a(☃x, ☃xx, ☃xxx, ☃xxxxxxxx, ☃xxxxxxxxx);
                           return;
                        }
                     }

                     this.field_184344_B = ☃xxxxxxxxxxx >= -0.03125;
                     this.field_184344_B &= !this.field_147367_d.func_71231_X() && !this.field_147369_b.field_71075_bZ.field_75101_c;
                     this.field_184344_B &= !this.field_147369_b.func_70644_a(MobEffects.field_188424_y)
                        && !this.field_147369_b.func_184613_cA()
                        && !☃.func_72829_c(this.field_147369_b.func_174813_aQ().func_186662_g(0.0625).func_72321_a(0.0, -0.55, 0.0));
                     this.field_147369_b.field_70122_E = ☃.func_149465_i();
                     this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
                     this.field_147369_b.func_71122_b(this.field_147369_b.field_70163_u - ☃xxxx, ☃.func_149465_i());
                     this.field_184352_o = this.field_147369_b.field_70165_t;
                     this.field_184353_p = this.field_147369_b.field_70163_u;
                     this.field_184354_q = this.field_147369_b.field_70161_v;
                  }
               }
            }
         }
      }
   }

   public void func_147364_a(double var1, double var3, double var5, float var7, float var8) {
      this.func_175089_a(☃, ☃, ☃, ☃, ☃, Collections.emptySet());
   }

   public void func_175089_a(double var1, double var3, double var5, float var7, float var8, Set<SPacketPlayerPosLook.EnumFlags> var9) {
      double ☃ = ☃.contains(SPacketPlayerPosLook.EnumFlags.X) ? this.field_147369_b.field_70165_t : 0.0;
      double ☃x = ☃.contains(SPacketPlayerPosLook.EnumFlags.Y) ? this.field_147369_b.field_70163_u : 0.0;
      double ☃xx = ☃.contains(SPacketPlayerPosLook.EnumFlags.Z) ? this.field_147369_b.field_70161_v : 0.0;
      float ☃xxx = ☃.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT) ? this.field_147369_b.field_70177_z : 0.0F;
      float ☃xxxx = ☃.contains(SPacketPlayerPosLook.EnumFlags.X_ROT) ? this.field_147369_b.field_70125_A : 0.0F;
      this.field_184362_y = new Vec3d(☃, ☃, ☃);
      if (++this.field_184363_z == Integer.MAX_VALUE) {
         this.field_184363_z = 0;
      }

      this.field_184343_A = this.field_147368_e;
      this.field_147369_b.func_70080_a(☃, ☃, ☃, ☃, ☃);
      this.field_147369_b.field_71135_a.func_147359_a(new SPacketPlayerPosLook(☃ - ☃, ☃ - ☃x, ☃ - ☃xx, ☃ - ☃xxx, ☃ - ☃xxxx, ☃, this.field_184363_z));
   }

   @Override
   public void func_147345_a(CPacketPlayerDigging var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      BlockPos ☃x = ☃.func_179715_a();
      this.field_147369_b.func_143004_u();
      switch(☃.func_180762_c()) {
         case SWAP_HELD_ITEMS:
            if (!this.field_147369_b.func_175149_v()) {
               ItemStack ☃xx = this.field_147369_b.func_184586_b(EnumHand.OFF_HAND);
               this.field_147369_b.func_184611_a(EnumHand.OFF_HAND, this.field_147369_b.func_184586_b(EnumHand.MAIN_HAND));
               this.field_147369_b.func_184611_a(EnumHand.MAIN_HAND, ☃xx);
            }

            return;
         case DROP_ITEM:
            if (!this.field_147369_b.func_175149_v()) {
               this.field_147369_b.func_71040_bB(false);
            }

            return;
         case DROP_ALL_ITEMS:
            if (!this.field_147369_b.func_175149_v()) {
               this.field_147369_b.func_71040_bB(true);
            }

            return;
         case RELEASE_USE_ITEM:
            this.field_147369_b.func_184597_cx();
            return;
         case START_DESTROY_BLOCK:
         case ABORT_DESTROY_BLOCK:
         case STOP_DESTROY_BLOCK:
            double ☃xx = this.field_147369_b.field_70165_t - ((double)☃x.func_177958_n() + 0.5);
            double ☃xxx = this.field_147369_b.field_70163_u - ((double)☃x.func_177956_o() + 0.5) + 1.5;
            double ☃xxxx = this.field_147369_b.field_70161_v - ((double)☃x.func_177952_p() + 0.5);
            double ☃xxxxx = ☃xx * ☃xx + ☃xxx * ☃xxx + ☃xxxx * ☃xxxx;
            if (☃xxxxx > 36.0) {
               return;
            } else if (☃x.func_177956_o() >= this.field_147367_d.func_71207_Z()) {
               return;
            } else {
               if (☃.func_180762_c() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                  if (!this.field_147367_d.func_175579_a(☃, ☃x, this.field_147369_b) && ☃.func_175723_af().func_177746_a(☃x)) {
                     this.field_147369_b.field_71134_c.func_180784_a(☃x, ☃.func_179714_b());
                  } else {
                     this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(☃, ☃x));
                  }
               } else {
                  if (☃.func_180762_c() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                     this.field_147369_b.field_71134_c.func_180785_a(☃x);
                  } else if (☃.func_180762_c() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                     this.field_147369_b.field_71134_c.func_180238_e();
                  }

                  if (!☃.func_180495_p(☃x).func_196958_f()) {
                     this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(☃, ☃x));
                  }
               }

               return;
            }
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   @Override
   public void func_184337_a(CPacketPlayerTryUseItemOnBlock var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      EnumHand ☃x = ☃.func_187022_c();
      ItemStack ☃xx = this.field_147369_b.func_184586_b(☃x);
      BlockPos ☃xxx = ☃.func_187023_a();
      EnumFacing ☃xxxx = ☃.func_187024_b();
      this.field_147369_b.func_143004_u();
      if (☃xxx.func_177956_o() < this.field_147367_d.func_71207_Z() - 1 || ☃xxxx != EnumFacing.UP && ☃xxx.func_177956_o() < this.field_147367_d.func_71207_Z()) {
         if (this.field_184362_y == null
            && this.field_147369_b.func_70092_e((double)☃xxx.func_177958_n() + 0.5, (double)☃xxx.func_177956_o() + 0.5, (double)☃xxx.func_177952_p() + 0.5)
               < 64.0
            && !this.field_147367_d.func_175579_a(☃, ☃xxx, this.field_147369_b)
            && ☃.func_175723_af().func_177746_a(☃xxx)) {
            this.field_147369_b
               .field_71134_c
               .func_187251_a(this.field_147369_b, ☃, ☃xx, ☃x, ☃xxx, ☃xxxx, ☃.func_187026_d(), ☃.func_187025_e(), ☃.func_187020_f());
         }
      } else {
         ITextComponent ☃ = new TextComponentTranslation("build.tooHigh", this.field_147367_d.func_71207_Z()).func_211708_a(TextFormatting.RED);
         this.field_147369_b.field_71135_a.func_147359_a(new SPacketChat(☃, ChatType.GAME_INFO));
      }

      this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(☃, ☃xxx));
      this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(☃, ☃xxx.func_177972_a(☃xxxx)));
   }

   @Override
   public void func_147346_a(CPacketPlayerTryUseItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      EnumHand ☃x = ☃.func_187028_a();
      ItemStack ☃xx = this.field_147369_b.func_184586_b(☃x);
      this.field_147369_b.func_143004_u();
      if (!☃xx.func_190926_b()) {
         this.field_147369_b.field_71134_c.func_187250_a(this.field_147369_b, ☃, ☃xx, ☃x);
      }
   }

   @Override
   public void func_175088_a(CPacketSpectate var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_175149_v()) {
         Entity ☃ = null;

         for(WorldServer ☃x : this.field_147367_d.func_212370_w()) {
            ☃ = ☃.func_179727_a(☃x);
            if (☃ != null) {
               break;
            }
         }

         if (☃ != null) {
            this.field_147369_b
               .func_200619_a((WorldServer)☃.field_70170_p, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
         }
      }
   }

   @Override
   public void func_175086_a(CPacketResourcePackStatus var1) {
   }

   @Override
   public void func_184340_a(CPacketSteerBoat var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      Entity ☃ = this.field_147369_b.func_184187_bx();
      if (☃ instanceof EntityBoat) {
         ((EntityBoat)☃).func_184445_a(☃.func_187012_a(), ☃.func_187014_b());
      }
   }

   @Override
   public void func_147231_a(ITextComponent var1) {
      field_147370_c.info("{} lost connection: {}", this.field_147369_b.func_200200_C_().getString(), ☃.getString());
      this.field_147367_d.func_147132_au();
      this.field_147367_d
         .func_184103_al()
         .func_148539_a(new TextComponentTranslation("multiplayer.player.left", this.field_147369_b.func_145748_c_()).func_211708_a(TextFormatting.YELLOW));
      this.field_147369_b.func_71123_m();
      this.field_147367_d.func_184103_al().func_72367_e(this.field_147369_b);
      if (this.field_147367_d.func_71264_H() && this.field_147369_b.func_200200_C_().getString().equals(this.field_147367_d.func_71214_G())) {
         field_147370_c.info("Stopping singleplayer server as player logged out");
         this.field_147367_d.func_71263_m();
      }
   }

   public void func_147359_a(Packet<?> var1) {
      this.func_211148_a(☃, null);
   }

   public void func_211148_a(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      if (☃ instanceof SPacketChat) {
         SPacketChat ☃ = (SPacketChat)☃;
         EntityPlayer.EnumChatVisibility ☃x = this.field_147369_b.func_147096_v();
         if (☃x == EntityPlayer.EnumChatVisibility.HIDDEN && ☃.func_192590_c() != ChatType.GAME_INFO) {
            return;
         }

         if (☃x == EntityPlayer.EnumChatVisibility.SYSTEM && !☃.func_148916_d()) {
            return;
         }
      }

      try {
         this.field_147371_a.func_201058_a(☃, ☃);
      } catch (Throwable var6) {
         CrashReport ☃ = CrashReport.func_85055_a(var6, "Sending packet");
         CrashReportCategory ☃x = ☃.func_85058_a("Packet being sent");
         ☃x.func_189529_a("Packet class", () -> ☃.getClass().getCanonicalName());
         throw new ReportedException(☃);
      }
   }

   @Override
   public void func_147355_a(CPacketHeldItemChange var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (☃.func_149614_c() >= 0 && ☃.func_149614_c() < InventoryPlayer.func_70451_h()) {
         this.field_147369_b.field_71071_by.field_70461_c = ☃.func_149614_c();
         this.field_147369_b.func_143004_u();
      } else {
         field_147370_c.warn("{} tried to set an invalid carried item", this.field_147369_b.func_200200_C_().getString());
      }
   }

   @Override
   public void func_147354_a(CPacketChatMessage var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
         this.func_147359_a(new SPacketChat(new TextComponentTranslation("chat.cannotSend").func_211708_a(TextFormatting.RED)));
      } else {
         this.field_147369_b.func_143004_u();
         String ☃ = ☃.func_149439_c();
         ☃ = org.apache.commons.lang3.StringUtils.normalizeSpace(☃);

         for(int ☃x = 0; ☃x < ☃.length(); ++☃x) {
            if (!SharedConstants.func_71566_a(☃.charAt(☃x))) {
               this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.illegal_characters"));
               return;
            }
         }

         if (☃.startsWith("/")) {
            this.func_147361_d(☃);
         } else {
            ITextComponent ☃x = new TextComponentTranslation("chat.type.text", this.field_147369_b.func_145748_c_(), ☃);
            this.field_147367_d.func_184103_al().func_148544_a(☃x, false);
         }

         this.field_147374_l += 20;
         if (this.field_147374_l > 200 && !this.field_147367_d.func_184103_al().func_152596_g(this.field_147369_b.func_146103_bH())) {
            this.func_194028_b(new TextComponentTranslation("disconnect.spam"));
         }
      }
   }

   private void func_147361_d(String var1) {
      this.field_147367_d.func_195571_aL().func_197059_a(this.field_147369_b.func_195051_bN(), ☃);
   }

   @Override
   public void func_175087_a(CPacketAnimation var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      this.field_147369_b.func_184609_a(☃.func_187018_a());
   }

   @Override
   public void func_147357_a(CPacketEntityAction var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      switch(☃.func_180764_b()) {
         case START_SNEAKING:
            this.field_147369_b.func_70095_a(true);
            break;
         case STOP_SNEAKING:
            this.field_147369_b.func_70095_a(false);
            break;
         case START_SPRINTING:
            this.field_147369_b.func_70031_b(true);
            break;
         case STOP_SPRINTING:
            this.field_147369_b.func_70031_b(false);
            break;
         case STOP_SLEEPING:
            if (this.field_147369_b.func_70608_bn()) {
               this.field_147369_b.func_70999_a(false, true, true);
               this.field_184362_y = new Vec3d(this.field_147369_b.field_70165_t, this.field_147369_b.field_70163_u, this.field_147369_b.field_70161_v);
            }
            break;
         case START_RIDING_JUMP:
            if (this.field_147369_b.func_184187_bx() instanceof IJumpingMount) {
               IJumpingMount ☃ = (IJumpingMount)this.field_147369_b.func_184187_bx();
               int ☃x = ☃.func_149512_e();
               if (☃.func_184776_b() && ☃x > 0) {
                  ☃.func_184775_b(☃x);
               }
            }
            break;
         case STOP_RIDING_JUMP:
            if (this.field_147369_b.func_184187_bx() instanceof IJumpingMount) {
               IJumpingMount ☃ = (IJumpingMount)this.field_147369_b.func_184187_bx();
               ☃.func_184777_r_();
            }
            break;
         case OPEN_INVENTORY:
            if (this.field_147369_b.func_184187_bx() instanceof AbstractHorse) {
               ((AbstractHorse)this.field_147369_b.func_184187_bx()).func_110199_f(this.field_147369_b);
            }
            break;
         case START_FALL_FLYING:
            if (!this.field_147369_b.field_70122_E
               && this.field_147369_b.field_70181_x < 0.0
               && !this.field_147369_b.func_184613_cA()
               && !this.field_147369_b.func_70090_H()) {
               ItemStack ☃ = this.field_147369_b.func_184582_a(EntityEquipmentSlot.CHEST);
               if (☃.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(☃)) {
                  this.field_147369_b.func_184847_M();
               }
            } else {
               this.field_147369_b.func_189103_N();
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void func_147340_a(CPacketUseEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      Entity ☃x = ☃.func_149564_a(☃);
      this.field_147369_b.func_143004_u();
      if (☃x != null) {
         boolean ☃xx = this.field_147369_b.func_70685_l(☃x);
         double ☃xxx = 36.0;
         if (!☃xx) {
            ☃xxx = 9.0;
         }

         if (this.field_147369_b.func_70068_e(☃x) < ☃xxx) {
            if (☃.func_149565_c() == CPacketUseEntity.Action.INTERACT) {
               EnumHand ☃xx = ☃.func_186994_b();
               this.field_147369_b.func_190775_a(☃x, ☃xx);
            } else if (☃.func_149565_c() == CPacketUseEntity.Action.INTERACT_AT) {
               EnumHand ☃xx = ☃.func_186994_b();
               ☃x.func_184199_a(this.field_147369_b, ☃.func_179712_b(), ☃xx);
            } else if (☃.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
               if (☃x instanceof EntityItem || ☃x instanceof EntityXPOrb || ☃x instanceof EntityArrow || ☃x == this.field_147369_b) {
                  this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked"));
                  this.field_147367_d.func_71236_h("Player " + this.field_147369_b.func_200200_C_().getString() + " tried to attack an invalid entity");
                  return;
               }

               this.field_147369_b.func_71059_n(☃x);
            }
         }
      }
   }

   @Override
   public void func_147342_a(CPacketClientStatus var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      CPacketClientStatus.State ☃ = ☃.func_149435_c();
      switch(☃) {
         case PERFORM_RESPAWN:
            if (this.field_147369_b.field_71136_j) {
               this.field_147369_b.field_71136_j = false;
               this.field_147369_b = this.field_147367_d.func_184103_al().func_72368_a(this.field_147369_b, DimensionType.OVERWORLD, true);
               CriteriaTriggers.field_193134_u.func_193143_a(this.field_147369_b, DimensionType.THE_END, DimensionType.OVERWORLD);
            } else {
               if (this.field_147369_b.func_110143_aJ() > 0.0F) {
                  return;
               }

               this.field_147369_b = this.field_147367_d.func_184103_al().func_72368_a(this.field_147369_b, DimensionType.OVERWORLD, false);
               if (this.field_147367_d.func_71199_h()) {
                  this.field_147369_b.func_71033_a(GameType.SPECTATOR);
                  this.field_147369_b.func_71121_q().func_82736_K().func_82764_b("spectatorsGenerateChunks", "false", this.field_147367_d);
               }
            }
            break;
         case REQUEST_STATS:
            this.field_147369_b.func_147099_x().func_150876_a(this.field_147369_b);
      }
   }

   @Override
   public void func_147356_a(CPacketCloseWindow var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_71128_l();
   }

   @Override
   public void func_147351_a(CPacketClickWindow var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (this.field_147369_b.field_71070_bA.field_75152_c == ☃.func_149548_c() && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)) {
         if (this.field_147369_b.func_175149_v()) {
            NonNullList<ItemStack> ☃ = NonNullList.func_191196_a();

            for(int ☃x = 0; ☃x < this.field_147369_b.field_71070_bA.field_75151_b.size(); ++☃x) {
               ☃.add(((Slot)this.field_147369_b.field_71070_bA.field_75151_b.get(☃x)).func_75211_c());
            }

            this.field_147369_b.func_71110_a(this.field_147369_b.field_71070_bA, ☃);
         } else {
            ItemStack ☃ = this.field_147369_b.field_71070_bA.func_184996_a(☃.func_149544_d(), ☃.func_149543_e(), ☃.func_186993_f(), this.field_147369_b);
            if (ItemStack.func_77989_b(☃.func_149546_g(), ☃)) {
               this.field_147369_b.field_71135_a.func_147359_a(new SPacketConfirmTransaction(☃.func_149548_c(), ☃.func_149547_f(), true));
               this.field_147369_b.field_71137_h = true;
               this.field_147369_b.field_71070_bA.func_75142_b();
               this.field_147369_b.func_71113_k();
               this.field_147369_b.field_71137_h = false;
            } else {
               this.field_147372_n.func_76038_a(this.field_147369_b.field_71070_bA.field_75152_c, ☃.func_149547_f());
               this.field_147369_b.field_71135_a.func_147359_a(new SPacketConfirmTransaction(☃.func_149548_c(), ☃.func_149547_f(), false));
               this.field_147369_b.field_71070_bA.func_75128_a(this.field_147369_b, false);
               NonNullList<ItemStack> ☃ = NonNullList.func_191196_a();

               for(int ☃x = 0; ☃x < this.field_147369_b.field_71070_bA.field_75151_b.size(); ++☃x) {
                  ItemStack ☃xx = ((Slot)this.field_147369_b.field_71070_bA.field_75151_b.get(☃x)).func_75211_c();
                  ☃.add(☃xx.func_190926_b() ? ItemStack.field_190927_a : ☃xx);
               }

               this.field_147369_b.func_71110_a(this.field_147369_b.field_71070_bA, ☃);
            }
         }
      }
   }

   @Override
   public void func_194308_a(CPacketPlaceRecipe var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (!this.field_147369_b.func_175149_v()
         && this.field_147369_b.field_71070_bA.field_75152_c == ☃.func_194318_a()
         && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)) {
         IRecipe ☃ = this.field_147367_d.func_199529_aN().func_199517_a(☃.func_199618_b());
         if (this.field_147369_b.field_71070_bA instanceof ContainerFurnace) {
            new ServerRecipePlacerFurnace().func_194327_a(this.field_147369_b, ☃, ☃.func_194319_c());
         } else {
            new ServerRecipePlacer().func_194327_a(this.field_147369_b, ☃, ☃.func_194319_c());
         }
      }
   }

   @Override
   public void func_147338_a(CPacketEnchantItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (this.field_147369_b.field_71070_bA.field_75152_c == ☃.func_149539_c()
         && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)
         && !this.field_147369_b.func_175149_v()) {
         this.field_147369_b.field_71070_bA.func_75140_a(this.field_147369_b, ☃.func_149537_d());
         this.field_147369_b.field_71070_bA.func_75142_b();
      }
   }

   @Override
   public void func_147344_a(CPacketCreativeInventoryAction var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.field_71134_c.func_73083_d()) {
         boolean ☃ = ☃.func_149627_c() < 0;
         ItemStack ☃x = ☃.func_149625_d();
         NBTTagCompound ☃xx = ☃x.func_179543_a("BlockEntityTag");
         if (!☃x.func_190926_b() && ☃xx != null && ☃xx.func_74764_b("x") && ☃xx.func_74764_b("y") && ☃xx.func_74764_b("z")) {
            BlockPos ☃xxx = new BlockPos(☃xx.func_74762_e("x"), ☃xx.func_74762_e("y"), ☃xx.func_74762_e("z"));
            TileEntity ☃xxxx = this.field_147369_b.field_70170_p.func_175625_s(☃xxx);
            if (☃xxxx != null) {
               NBTTagCompound ☃xxxxx = ☃xxxx.func_189515_b(new NBTTagCompound());
               ☃xxxxx.func_82580_o("x");
               ☃xxxxx.func_82580_o("y");
               ☃xxxxx.func_82580_o("z");
               ☃x.func_77983_a("BlockEntityTag", ☃xxxxx);
            }
         }

         boolean ☃ = ☃.func_149627_c() >= 1 && ☃.func_149627_c() <= 45;
         boolean ☃x = ☃x.func_190926_b() || ☃x.func_77952_i() >= 0 && ☃x.func_190916_E() <= 64 && !☃x.func_190926_b();
         if (☃ && ☃x) {
            if (☃x.func_190926_b()) {
               this.field_147369_b.field_71069_bz.func_75141_a(☃.func_149627_c(), ItemStack.field_190927_a);
            } else {
               this.field_147369_b.field_71069_bz.func_75141_a(☃.func_149627_c(), ☃x);
            }

            this.field_147369_b.field_71069_bz.func_75128_a(this.field_147369_b, true);
         } else if (☃ && ☃x && this.field_147375_m < 200) {
            this.field_147375_m += 20;
            EntityItem ☃ = this.field_147369_b.func_71019_a(☃x, true);
            if (☃ != null) {
               ☃.func_70288_d();
            }
         }
      }
   }

   @Override
   public void func_147339_a(CPacketConfirmTransaction var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      Short ☃ = (Short)this.field_147372_n.func_76041_a(this.field_147369_b.field_71070_bA.field_75152_c);
      if (☃ != null
         && ☃.func_149533_d() == ☃
         && this.field_147369_b.field_71070_bA.field_75152_c == ☃.func_149532_c()
         && !this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)
         && !this.field_147369_b.func_175149_v()) {
         this.field_147369_b.field_71070_bA.func_75128_a(this.field_147369_b, true);
      }
   }

   @Override
   public void func_147343_a(CPacketUpdateSign var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      WorldServer ☃ = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      BlockPos ☃x = ☃.func_179722_a();
      if (☃.func_175667_e(☃x)) {
         IBlockState ☃xx = ☃.func_180495_p(☃x);
         TileEntity ☃xxx = ☃.func_175625_s(☃x);
         if (!(☃xxx instanceof TileEntitySign)) {
            return;
         }

         TileEntitySign ☃xx = (TileEntitySign)☃xxx;
         if (!☃xx.func_145914_a() || ☃xx.func_145911_b() != this.field_147369_b) {
            this.field_147367_d.func_71236_h("Player " + this.field_147369_b.func_200200_C_().getString() + " just tried to change non-editable sign");
            return;
         }

         String[] ☃xx = ☃.func_187017_b();

         for(int ☃xxx = 0; ☃xxx < ☃xx.length; ++☃xxx) {
            ☃xx.func_212365_a(☃xxx, new TextComponentString(TextFormatting.func_110646_a(☃xx[☃xxx])));
         }

         ☃xx.func_70296_d();
         ☃.func_184138_a(☃x, ☃xx, ☃xx, 3);
      }
   }

   @Override
   public void func_147353_a(CPacketKeepAlive var1) {
      if (this.field_194403_g && ☃.func_149460_c() == this.field_194404_h) {
         int ☃ = (int)(Util.func_211177_b() - this.field_194402_f);
         this.field_147369_b.field_71138_i = (this.field_147369_b.field_71138_i * 3 + ☃) / 4;
         this.field_194403_g = false;
      } else if (!this.field_147369_b.func_200200_C_().getString().equals(this.field_147367_d.func_71214_G())) {
         this.func_194028_b(new TextComponentTranslation("disconnect.timeout"));
      }
   }

   @Override
   public void func_147348_a(CPacketPlayerAbilities var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.field_71075_bZ.field_75100_b = ☃.func_149488_d() && this.field_147369_b.field_71075_bZ.field_75101_c;
   }

   @Override
   public void func_147352_a(CPacketClientSettings var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_147100_a(☃);
   }

   @Override
   public void func_147349_a(CPacketCustomPayload var1) {
   }
}
