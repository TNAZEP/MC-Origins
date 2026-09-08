package net.minecraft.client.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleItemPickup;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.renderer.debug.DebugRendererNeighborsUpdate;
import net.minecraft.client.renderer.debug.DebugRendererWorldGenAttempts;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.NBTQueryManager;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketCommandList;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketNBTQueryResponse;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketStopSound;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTagsList;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateRecipes;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stat;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityConduit;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private static final Logger field_147301_d = LogManager.getLogger();
   private final NetworkManager field_147302_e;
   private final GameProfile field_175107_d;
   private final GuiScreen field_147307_j;
   private Minecraft field_147299_f;
   private WorldClient field_147300_g;
   private boolean field_147309_h;
   private final Map<UUID, NetworkPlayerInfo> field_147310_i = Maps.newHashMap();
   private final ClientAdvancementManager field_191983_k;
   private final ClientSuggestionProvider field_195516_l;
   private NetworkTagManager field_199725_m = new NetworkTagManager();
   private final NBTQueryManager field_211524_l = new NBTQueryManager(this);
   private final Random field_147306_l = new Random();
   private CommandDispatcher<ISuggestionProvider> field_195517_n = new CommandDispatcher<>();
   private final RecipeManager field_199528_o = new RecipeManager();

   public NetHandlerPlayClient(Minecraft var1, GuiScreen var2, NetworkManager var3, GameProfile var4) {
      this.field_147299_f = ☃;
      this.field_147307_j = ☃;
      this.field_147302_e = ☃;
      this.field_175107_d = ☃;
      this.field_191983_k = new ClientAdvancementManager(☃);
      this.field_195516_l = new ClientSuggestionProvider(this, ☃);
   }

   public ClientSuggestionProvider func_195513_b() {
      return this.field_195516_l;
   }

   public void func_147296_c() {
      this.field_147300_g = null;
   }

   public RecipeManager func_199526_e() {
      return this.field_199528_o;
   }

   @Override
   public void func_147282_a(SPacketJoinGame var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71442_b = new PlayerControllerMP(this.field_147299_f, this);
      this.field_147300_g = new WorldClient(
         this,
         new WorldSettings(0L, ☃.func_149198_e(), false, ☃.func_149195_d(), ☃.func_149196_i()),
         ☃.func_212642_e(),
         ☃.func_149192_g(),
         this.field_147299_f.field_71424_I
      );
      this.field_147299_f.field_71474_y.field_74318_M = ☃.func_149192_g();
      this.field_147299_f.func_71403_a(this.field_147300_g);
      this.field_147299_f.field_71439_g.field_71093_bK = ☃.func_212642_e();
      this.field_147299_f.func_147108_a(new GuiDownloadTerrain());
      this.field_147299_f.field_71439_g.func_145769_d(☃.func_149197_c());
      this.field_147299_f.field_71439_g.func_175150_k(☃.func_179744_h());
      this.field_147299_f.field_71442_b.func_78746_a(☃.func_149198_e());
      this.field_147299_f.field_71474_y.func_82879_c();
      this.field_147302_e
         .func_179290_a(
            new CPacketCustomPayload(
               CPacketCustomPayload.field_210344_a, new PacketBuffer(Unpooled.buffer()).func_180714_a(ClientBrandRetriever.getClientModName())
            )
         );
   }

   @Override
   public void func_147235_a(SPacketSpawnObject var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      double ☃ = ☃.func_186880_c();
      double ☃x = ☃.func_186882_d();
      double ☃xx = ☃.func_186881_e();
      Entity ☃xxx = null;
      if (☃.func_148993_l() == 10) {
         ☃xxx = EntityMinecart.func_184263_a(this.field_147300_g, ☃, ☃x, ☃xx, EntityMinecart.Type.func_184955_a(☃.func_149009_m()));
      } else if (☃.func_148993_l() == 90) {
         Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149009_m());
         if (☃ instanceof EntityPlayer) {
            ☃xxx = new EntityFishHook(this.field_147300_g, (EntityPlayer)☃, ☃, ☃x, ☃xx);
         }

         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 60) {
         ☃xxx = new EntityTippedArrow(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 91) {
         ☃xxx = new EntitySpectralArrow(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 94) {
         ☃xxx = new EntityTrident(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 61) {
         ☃xxx = new EntitySnowball(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 68) {
         ☃xxx = new EntityLlamaSpit(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
      } else if (☃.func_148993_l() == 71) {
         ☃xxx = new EntityItemFrame(this.field_147300_g, new BlockPos(☃, ☃x, ☃xx), EnumFacing.func_82600_a(☃.func_149009_m()));
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 77) {
         ☃xxx = new EntityLeashKnot(this.field_147300_g, new BlockPos(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃x), MathHelper.func_76128_c(☃xx)));
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 65) {
         ☃xxx = new EntityEnderPearl(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 72) {
         ☃xxx = new EntityEnderEye(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 76) {
         ☃xxx = new EntityFireworkRocket(this.field_147300_g, ☃, ☃x, ☃xx, ItemStack.field_190927_a);
      } else if (☃.func_148993_l() == 63) {
         ☃xxx = new EntityLargeFireball(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 93) {
         ☃xxx = new EntityDragonFireball(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 64) {
         ☃xxx = new EntitySmallFireball(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 66) {
         ☃xxx = new EntityWitherSkull(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 67) {
         ☃xxx = new EntityShulkerBullet(
            this.field_147300_g, ☃, ☃x, ☃xx, (double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0
         );
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 62) {
         ☃xxx = new EntityEgg(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 79) {
         ☃xxx = new EntityEvokerFangs(this.field_147300_g, ☃, ☃x, ☃xx, 0.0F, 0, null);
      } else if (☃.func_148993_l() == 73) {
         ☃xxx = new EntityPotion(this.field_147300_g, ☃, ☃x, ☃xx, ItemStack.field_190927_a);
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 75) {
         ☃xxx = new EntityExpBottle(this.field_147300_g, ☃, ☃x, ☃xx);
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 1) {
         ☃xxx = new EntityBoat(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 50) {
         ☃xxx = new EntityTNTPrimed(this.field_147300_g, ☃, ☃x, ☃xx, null);
      } else if (☃.func_148993_l() == 78) {
         ☃xxx = new EntityArmorStand(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 51) {
         ☃xxx = new EntityEnderCrystal(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 2) {
         ☃xxx = new EntityItem(this.field_147300_g, ☃, ☃x, ☃xx);
      } else if (☃.func_148993_l() == 70) {
         ☃xxx = new EntityFallingBlock(this.field_147300_g, ☃, ☃x, ☃xx, Block.func_196257_b(☃.func_149009_m()));
         ☃.func_149002_g(0);
      } else if (☃.func_148993_l() == 3) {
         ☃xxx = new EntityAreaEffectCloud(this.field_147300_g, ☃, ☃x, ☃xx);
      }

      if (☃xxx != null) {
         EntityTracker.func_187254_a(☃xxx, ☃, ☃x, ☃xx);
         ☃xxx.field_70125_A = (float)(☃.func_149008_j() * 360) / 256.0F;
         ☃xxx.field_70177_z = (float)(☃.func_149006_k() * 360) / 256.0F;
         Entity[] ☃ = ☃xxx.func_70021_al();
         if (☃ != null) {
            int ☃x = ☃.func_149001_c() - ☃xxx.func_145782_y();

            for(Entity ☃xx : ☃) {
               ☃xx.func_145769_d(☃xx.func_145782_y() + ☃x);
            }
         }

         ☃xxx.func_145769_d(☃.func_149001_c());
         ☃xxx.func_184221_a(☃.func_186879_b());
         this.field_147300_g.func_73027_a(☃.func_149001_c(), ☃xxx);
         if (☃.func_149009_m() > 0) {
            if (☃.func_148993_l() == 60 || ☃.func_148993_l() == 91 || ☃.func_148993_l() == 94) {
               Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149009_m() - 1);
               if (☃ instanceof EntityLivingBase && ☃xxx instanceof EntityArrow) {
                  EntityArrow ☃x = (EntityArrow)☃xxx;
                  ☃x.func_212361_a(☃);
                  if (☃ instanceof EntityPlayer) {
                     ☃x.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
                     if (((EntityPlayer)☃).field_71075_bZ.field_75098_d) {
                        ☃x.field_70251_a = EntityArrow.PickupStatus.CREATIVE_ONLY;
                     }
                  }
               }
            }

            ☃xxx.func_70016_h((double)☃.func_149010_g() / 8000.0, (double)☃.func_149004_h() / 8000.0, (double)☃.func_148999_i() / 8000.0);
         }
      }
   }

   @Override
   public void func_147286_a(SPacketSpawnExperienceOrb var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      double ☃ = ☃.func_186885_b();
      double ☃x = ☃.func_186886_c();
      double ☃xx = ☃.func_186884_d();
      Entity ☃xxx = new EntityXPOrb(this.field_147300_g, ☃, ☃x, ☃xx, ☃.func_148986_g());
      EntityTracker.func_187254_a(☃xxx, ☃, ☃x, ☃xx);
      ☃xxx.field_70177_z = 0.0F;
      ☃xxx.field_70125_A = 0.0F;
      ☃xxx.func_145769_d(☃.func_148985_c());
      this.field_147300_g.func_73027_a(☃.func_148985_c(), ☃xxx);
   }

   @Override
   public void func_147292_a(SPacketSpawnGlobalEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      double ☃ = ☃.func_186888_b();
      double ☃x = ☃.func_186889_c();
      double ☃xx = ☃.func_186887_d();
      Entity ☃xxx = null;
      if (☃.func_149053_g() == 1) {
         ☃xxx = new EntityLightningBolt(this.field_147300_g, ☃, ☃x, ☃xx, false);
      }

      if (☃xxx != null) {
         EntityTracker.func_187254_a(☃xxx, ☃, ☃x, ☃xx);
         ☃xxx.field_70177_z = 0.0F;
         ☃xxx.field_70125_A = 0.0F;
         ☃xxx.func_145769_d(☃.func_149052_c());
         this.field_147300_g.func_72942_c(☃xxx);
      }
   }

   @Override
   public void func_147288_a(SPacketSpawnPainting var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPainting ☃ = new EntityPainting(this.field_147300_g, ☃.func_179837_b(), ☃.func_179836_c(), ☃.func_201063_e());
      ☃.func_184221_a(☃.func_186895_b());
      this.field_147300_g.func_73027_a(☃.func_148965_c(), ☃);
   }

   @Override
   public void func_147244_a(SPacketEntityVelocity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149412_c());
      if (☃ != null) {
         ☃.func_70016_h((double)☃.func_149411_d() / 8000.0, (double)☃.func_149410_e() / 8000.0, (double)☃.func_149409_f() / 8000.0);
      }
   }

   @Override
   public void func_147284_a(SPacketEntityMetadata var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149375_d());
      if (☃ != null && ☃.func_149376_c() != null) {
         ☃.func_184212_Q().func_187218_a(☃.func_149376_c());
      }
   }

   @Override
   public void func_147237_a(SPacketSpawnPlayer var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      double ☃ = ☃.func_186898_d();
      double ☃x = ☃.func_186897_e();
      double ☃xx = ☃.func_186899_f();
      float ☃xxx = (float)(☃.func_148941_i() * 360) / 256.0F;
      float ☃xxxx = (float)(☃.func_148945_j() * 360) / 256.0F;
      EntityOtherPlayerMP ☃xxxxx = new EntityOtherPlayerMP(this.field_147299_f.field_71441_e, this.func_175102_a(☃.func_179819_c()).func_178845_a());
      ☃xxxxx.field_70169_q = ☃;
      ☃xxxxx.field_70142_S = ☃;
      ☃xxxxx.field_70167_r = ☃x;
      ☃xxxxx.field_70137_T = ☃x;
      ☃xxxxx.field_70166_s = ☃xx;
      ☃xxxxx.field_70136_U = ☃xx;
      EntityTracker.func_187254_a(☃xxxxx, ☃, ☃x, ☃xx);
      ☃xxxxx.func_70080_a(☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
      this.field_147300_g.func_73027_a(☃.func_148943_d(), ☃xxxxx);
      List<EntityDataManager.DataEntry<?>> ☃xxxxxx = ☃.func_148944_c();
      if (☃xxxxxx != null) {
         ☃xxxxx.func_184212_Q().func_187218_a(☃xxxxxx);
      }
   }

   @Override
   public void func_147275_a(SPacketEntityTeleport var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149451_c());
      if (☃ != null) {
         double ☃x = ☃.func_186982_b();
         double ☃xx = ☃.func_186983_c();
         double ☃xxx = ☃.func_186981_d();
         EntityTracker.func_187254_a(☃, ☃x, ☃xx, ☃xxx);
         if (!☃.func_184186_bw()) {
            float ☃xxxx = (float)(☃.func_149450_g() * 360) / 256.0F;
            float ☃xxxxx = (float)(☃.func_149447_h() * 360) / 256.0F;
            if (!(Math.abs(☃.field_70165_t - ☃x) >= 0.03125)
               && !(Math.abs(☃.field_70163_u - ☃xx) >= 0.015625)
               && !(Math.abs(☃.field_70161_v - ☃xxx) >= 0.03125)) {
               ☃.func_180426_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃xxxx, ☃xxxxx, 0, true);
            } else {
               ☃.func_180426_a(☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, 3, true);
            }

            ☃.field_70122_E = ☃.func_179697_g();
         }
      }
   }

   @Override
   public void func_147257_a(SPacketHeldItemChange var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (InventoryPlayer.func_184435_e(☃.func_149385_c())) {
         this.field_147299_f.field_71439_g.field_71071_by.field_70461_c = ☃.func_149385_c();
      }
   }

   @Override
   public void func_147259_a(SPacketEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = ☃.func_149065_a(this.field_147300_g);
      if (☃ != null) {
         ☃.field_70118_ct += (long)☃.func_186952_a();
         ☃.field_70117_cu += (long)☃.func_186953_b();
         ☃.field_70116_cv += (long)☃.func_186951_c();
         double ☃x = (double)☃.field_70118_ct / 4096.0;
         double ☃xx = (double)☃.field_70117_cu / 4096.0;
         double ☃xxx = (double)☃.field_70116_cv / 4096.0;
         if (!☃.func_184186_bw()) {
            float ☃xxxx = ☃.func_149060_h() ? (float)(☃.func_149066_f() * 360) / 256.0F : ☃.field_70177_z;
            float ☃xxxxx = ☃.func_149060_h() ? (float)(☃.func_149063_g() * 360) / 256.0F : ☃.field_70125_A;
            ☃.func_180426_a(☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, 3, false);
            ☃.field_70122_E = ☃.func_179742_g();
         }
      }
   }

   @Override
   public void func_147267_a(SPacketEntityHeadLook var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = ☃.func_149381_a(this.field_147300_g);
      if (☃ != null) {
         float ☃x = (float)(☃.func_149380_c() * 360) / 256.0F;
         ☃.func_208000_a(☃x, 3);
      }
   }

   @Override
   public void func_147238_a(SPacketDestroyEntities var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);

      for(int ☃ = 0; ☃ < ☃.func_149098_c().length; ++☃) {
         this.field_147300_g.func_73028_b(☃.func_149098_c()[☃]);
      }
   }

   @Override
   public void func_184330_a(SPacketPlayerPosLook var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      double ☃x = ☃.func_148932_c();
      double ☃xx = ☃.func_148928_d();
      double ☃xxx = ☃.func_148933_e();
      float ☃xxxx = ☃.func_148931_f();
      float ☃xxxxx = ☃.func_148930_g();
      if (☃.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X)) {
         ☃x += ☃.field_70165_t;
      } else {
         ☃.field_70159_w = 0.0;
      }

      if (☃.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
         ☃xx += ☃.field_70163_u;
      } else {
         ☃.field_70181_x = 0.0;
      }

      if (☃.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
         ☃xxx += ☃.field_70161_v;
      } else {
         ☃.field_70179_y = 0.0;
      }

      if (☃.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
         ☃xxxxx += ☃.field_70125_A;
      }

      if (☃.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
         ☃xxxx += ☃.field_70177_z;
      }

      ☃.func_70080_a(☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
      this.field_147302_e.func_179290_a(new CPacketConfirmTeleport(☃.func_186965_f()));
      this.field_147302_e
         .func_179290_a(
            new CPacketPlayer.PositionRotation(☃.field_70165_t, ☃.func_174813_aQ().field_72338_b, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A, false)
         );
      if (!this.field_147309_h) {
         this.field_147299_f.field_71439_g.field_70169_q = this.field_147299_f.field_71439_g.field_70165_t;
         this.field_147299_f.field_71439_g.field_70167_r = this.field_147299_f.field_71439_g.field_70163_u;
         this.field_147299_f.field_71439_g.field_70166_s = this.field_147299_f.field_71439_g.field_70161_v;
         this.field_147309_h = true;
         this.field_147299_f.func_147108_a(null);
      }
   }

   @Override
   public void func_147287_a(SPacketMultiBlockChange var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);

      for(SPacketMultiBlockChange.BlockUpdateData ☃ : ☃.func_179844_a()) {
         this.field_147300_g.func_195597_b(☃.func_180090_a(), ☃.func_180088_c());
      }
   }

   @Override
   public void func_147263_a(SPacketChunkData var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      int ☃ = ☃.func_149273_e();
      int ☃x = ☃.func_149271_f();
      Chunk ☃xx = this.field_147300_g.func_72863_F().func_212474_a(☃, ☃x, ☃.func_186946_a(), ☃.func_149276_g(), ☃.func_149274_i());
      this.field_147300_g.func_147458_c(☃ << 4, 0, ☃x << 4, (☃ << 4) + 15, 256, (☃x << 4) + 15);
      if (!☃.func_149274_i() || !(this.field_147300_g.field_73011_w instanceof OverworldDimension)) {
         ☃xx.func_76613_n();
      }

      for(NBTTagCompound ☃ : ☃.func_189554_f()) {
         BlockPos ☃x = new BlockPos(☃.func_74762_e("x"), ☃.func_74762_e("y"), ☃.func_74762_e("z"));
         TileEntity ☃xx = this.field_147300_g.func_175625_s(☃x);
         if (☃xx != null) {
            ☃xx.func_145839_a(☃);
         }
      }
   }

   @Override
   public void func_184326_a(SPacketUnloadChunk var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      int ☃ = ☃.func_186940_a();
      int ☃x = ☃.func_186941_b();
      this.field_147300_g.func_72863_F().func_73234_b(☃, ☃x);
      this.field_147300_g.func_147458_c(☃ << 4, 0, ☃x << 4, (☃ << 4) + 15, 256, (☃x << 4) + 15);
   }

   @Override
   public void func_147234_a(SPacketBlockChange var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147300_g.func_195597_b(☃.func_179827_b(), ☃.func_197685_a());
   }

   @Override
   public void func_147253_a(SPacketDisconnect var1) {
      this.field_147302_e.func_150718_a(☃.func_149165_c());
   }

   @Override
   public void func_147231_a(ITextComponent var1) {
      this.field_147299_f.func_71403_a(null);
      if (this.field_147307_j != null) {
         if (this.field_147307_j instanceof GuiScreenRealmsProxy) {
            this.field_147299_f
               .func_147108_a(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.field_147307_j).func_154321_a(), "disconnect.lost", ☃).getProxy());
         } else {
            this.field_147299_f.func_147108_a(new GuiDisconnected(this.field_147307_j, "disconnect.lost", ☃));
         }
      } else {
         this.field_147299_f.func_147108_a(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", ☃));
      }
   }

   public void func_147297_a(Packet<?> var1) {
      this.field_147302_e.func_179290_a(☃);
   }

   @Override
   public void func_147246_a(SPacketCollectItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149354_c());
      EntityLivingBase ☃x = (EntityLivingBase)this.field_147300_g.func_73045_a(☃.func_149353_d());
      if (☃x == null) {
         ☃x = this.field_147299_f.field_71439_g;
      }

      if (☃ != null) {
         if (☃ instanceof EntityXPOrb) {
            this.field_147300_g
               .func_184134_a(
                  ☃.field_70165_t,
                  ☃.field_70163_u,
                  ☃.field_70161_v,
                  SoundEvents.field_187604_bf,
                  SoundCategory.PLAYERS,
                  0.1F,
                  (this.field_147306_l.nextFloat() - this.field_147306_l.nextFloat()) * 0.35F + 0.9F,
                  false
               );
         } else {
            this.field_147300_g
               .func_184134_a(
                  ☃.field_70165_t,
                  ☃.field_70163_u,
                  ☃.field_70161_v,
                  SoundEvents.field_187638_cR,
                  SoundCategory.PLAYERS,
                  0.2F,
                  (this.field_147306_l.nextFloat() - this.field_147306_l.nextFloat()) * 1.4F + 2.0F,
                  false
               );
         }

         if (☃ instanceof EntityItem) {
            ((EntityItem)☃).func_92059_d().func_190920_e(☃.func_191208_c());
         }

         this.field_147299_f.field_71452_i.func_78873_a(new ParticleItemPickup(this.field_147300_g, ☃, ☃x, 0.5F));
         this.field_147300_g.func_73028_b(☃.func_149354_c());
      }
   }

   @Override
   public void func_147251_a(SPacketChat var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71456_v.func_191742_a(☃.func_192590_c(), ☃.func_148915_c());
   }

   @Override
   public void func_147279_a(SPacketAnimation var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_148978_c());
      if (☃ != null) {
         if (☃.func_148977_d() == 0) {
            EntityLivingBase ☃x = (EntityLivingBase)☃;
            ☃x.func_184609_a(EnumHand.MAIN_HAND);
         } else if (☃.func_148977_d() == 3) {
            EntityLivingBase ☃x = (EntityLivingBase)☃;
            ☃x.func_184609_a(EnumHand.OFF_HAND);
         } else if (☃.func_148977_d() == 1) {
            ☃.func_70057_ab();
         } else if (☃.func_148977_d() == 2) {
            EntityPlayer ☃x = (EntityPlayer)☃;
            ☃x.func_70999_a(false, false, false);
         } else if (☃.func_148977_d() == 4) {
            this.field_147299_f.field_71452_i.func_199282_a(☃, Particles.field_197614_g);
         } else if (☃.func_148977_d() == 5) {
            this.field_147299_f.field_71452_i.func_199282_a(☃, Particles.field_197622_o);
         }
      }
   }

   @Override
   public void func_147278_a(SPacketUseBed var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      ☃.func_149091_a(this.field_147300_g).func_180469_a(☃.func_179798_a());
   }

   @Override
   public void func_147281_a(SPacketSpawnMob var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      double ☃ = ☃.func_186891_e();
      double ☃x = ☃.func_186892_f();
      double ☃xx = ☃.func_186893_g();
      float ☃xxx = (float)(☃.func_149028_l() * 360) / 256.0F;
      float ☃xxxx = (float)(☃.func_149030_m() * 360) / 256.0F;
      EntityLivingBase ☃xxxxx = (EntityLivingBase)EntityType.func_200717_a(☃.func_149025_e(), this.field_147299_f.field_71441_e);
      if (☃xxxxx != null) {
         EntityTracker.func_187254_a(☃xxxxx, ☃, ☃x, ☃xx);
         ☃xxxxx.field_70761_aq = (float)(☃.func_149032_n() * 360) / 256.0F;
         ☃xxxxx.field_70759_as = (float)(☃.func_149032_n() * 360) / 256.0F;
         Entity[] ☃xxxxxx = ☃xxxxx.func_70021_al();
         if (☃xxxxxx != null) {
            int ☃xxxxxxx = ☃.func_149024_d() - ☃xxxxx.func_145782_y();

            for(Entity ☃xxxxxxxx : ☃xxxxxx) {
               ☃xxxxxxxx.func_145769_d(☃xxxxxxxx.func_145782_y() + ☃xxxxxxx);
            }
         }

         ☃xxxxx.func_145769_d(☃.func_149024_d());
         ☃xxxxx.func_184221_a(☃.func_186890_c());
         ☃xxxxx.func_70080_a(☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
         ☃xxxxx.field_70159_w = (double)((float)☃.func_149026_i() / 8000.0F);
         ☃xxxxx.field_70181_x = (double)((float)☃.func_149033_j() / 8000.0F);
         ☃xxxxx.field_70179_y = (double)((float)☃.func_149031_k() / 8000.0F);
         this.field_147300_g.func_73027_a(☃.func_149024_d(), ☃xxxxx);
         List<EntityDataManager.DataEntry<?>> ☃xxxxxx = ☃.func_149027_c();
         if (☃xxxxxx != null) {
            ☃xxxxx.func_184212_Q().func_187218_a(☃xxxxxx);
         }
      } else {
         field_147301_d.warn("Skipping Entity with id {}", ☃.func_149025_e());
      }
   }

   @Override
   public void func_147285_a(SPacketTimeUpdate var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_82738_a(☃.func_149366_c());
      this.field_147299_f.field_71441_e.func_72877_b(☃.func_149365_d());
   }

   @Override
   public void func_147271_a(SPacketSpawnPosition var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_180473_a(☃.func_179800_a(), true);
      this.field_147299_f.field_71441_e.func_72912_H().func_176143_a(☃.func_179800_a());
   }

   @Override
   public void func_184328_a(SPacketSetPassengers var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_186972_b());
      if (☃ == null) {
         field_147301_d.warn("Received passengers for unknown entity");
      } else {
         boolean ☃ = ☃.func_184215_y(this.field_147299_f.field_71439_g);
         ☃.func_184226_ay();

         for(int ☃x : ☃.func_186971_a()) {
            Entity ☃xx = this.field_147300_g.func_73045_a(☃x);
            if (☃xx != null) {
               ☃xx.func_184205_a(☃, true);
               if (☃xx == this.field_147299_f.field_71439_g && !☃) {
                  this.field_147299_f
                     .field_71456_v
                     .func_110326_a(I18n.func_135052_a("mount.onboard", this.field_147299_f.field_71474_y.field_74311_E.func_197978_k()), false);
               }
            }
         }
      }
   }

   @Override
   public void func_147243_a(SPacketEntityAttach var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149403_d());
      Entity ☃x = this.field_147300_g.func_73045_a(☃.func_149402_e());
      if (☃ instanceof EntityLiving) {
         if (☃x != null) {
            ((EntityLiving)☃).func_110162_b(☃x, false);
         } else {
            ((EntityLiving)☃).func_110160_i(false, false);
         }
      }
   }

   @Override
   public void func_147236_a(SPacketEntityStatus var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = ☃.func_149161_a(this.field_147300_g);
      if (☃ != null) {
         if (☃.func_149160_c() == 21) {
            this.field_147299_f.func_147118_V().func_147682_a(new GuardianSound((EntityGuardian)☃));
         } else if (☃.func_149160_c() == 35) {
            int ☃x = 40;
            this.field_147299_f.field_71452_i.func_199281_a(☃, Particles.field_197604_O, 30);
            this.field_147300_g
               .func_184134_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_191263_gW, ☃.func_184176_by(), 1.0F, 1.0F, false);
            if (☃ == this.field_147299_f.field_71439_g) {
               this.field_147299_f.field_71460_t.func_190565_a(new ItemStack(Items.field_190929_cY));
            }
         } else {
            ☃.func_70103_a(☃.func_149160_c());
         }
      }
   }

   @Override
   public void func_147249_a(SPacketUpdateHealth var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_71150_b(☃.func_149332_c());
      this.field_147299_f.field_71439_g.func_71024_bL().func_75114_a(☃.func_149330_d());
      this.field_147299_f.field_71439_g.func_71024_bL().func_75119_b(☃.func_149331_e());
   }

   @Override
   public void func_147295_a(SPacketSetExperience var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_71152_a(☃.func_149397_c(), ☃.func_149396_d(), ☃.func_149395_e());
   }

   @Override
   public void func_147280_a(SPacketRespawn var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      DimensionType ☃ = ☃.func_212643_b();
      if (☃ != this.field_147299_f.field_71439_g.field_71093_bK) {
         this.field_147309_h = false;
         Scoreboard ☃x = this.field_147300_g.func_96441_U();
         this.field_147300_g = new WorldClient(
            this,
            new WorldSettings(0L, ☃.func_149083_e(), false, this.field_147299_f.field_71441_e.func_72912_H().func_76093_s(), ☃.func_149080_f()),
            ☃.func_212643_b(),
            ☃.func_149081_d(),
            this.field_147299_f.field_71424_I
         );
         this.field_147300_g.func_96443_a(☃x);
         this.field_147299_f.func_71403_a(this.field_147300_g);
         this.field_147299_f.field_71439_g.field_71093_bK = ☃;
         this.field_147299_f.func_147108_a(new GuiDownloadTerrain());
      }

      this.field_147299_f.func_212315_a(☃.func_212643_b());
      this.field_147299_f.field_71442_b.func_78746_a(☃.func_149083_e());
   }

   @Override
   public void func_147283_a(SPacketExplosion var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Explosion ☃ = new Explosion(
         this.field_147299_f.field_71441_e, null, ☃.func_149148_f(), ☃.func_149143_g(), ☃.func_149145_h(), ☃.func_149146_i(), ☃.func_149150_j()
      );
      ☃.func_77279_a(true);
      this.field_147299_f.field_71439_g.field_70159_w += (double)☃.func_149149_c();
      this.field_147299_f.field_71439_g.field_70181_x += (double)☃.func_149144_d();
      this.field_147299_f.field_71439_g.field_70179_y += (double)☃.func_149147_e();
   }

   @Override
   public void func_147265_a(SPacketOpenWindow var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayerSP ☃ = this.field_147299_f.field_71439_g;
      if ("minecraft:container".equals(☃.func_148902_e())) {
         ☃.func_71007_a(new InventoryBasic(☃.func_179840_c(), ☃.func_148898_f()));
         ☃.field_71070_bA.field_75152_c = ☃.func_148901_c();
      } else if ("minecraft:villager".equals(☃.func_148902_e())) {
         ☃.func_180472_a(new NpcMerchant(☃, ☃.func_179840_c()));
         ☃.field_71070_bA.field_75152_c = ☃.func_148901_c();
      } else if ("EntityHorse".equals(☃.func_148902_e())) {
         Entity ☃ = this.field_147300_g.func_73045_a(☃.func_148897_h());
         if (☃ instanceof AbstractHorse) {
            ☃.func_184826_a((AbstractHorse)☃, new ContainerHorseChest(☃.func_179840_c(), ☃.func_148898_f()));
            ☃.field_71070_bA.field_75152_c = ☃.func_148901_c();
         }
      } else if (!☃.func_148900_g()) {
         ☃.func_180468_a(new LocalBlockIntercommunication(☃.func_148902_e(), ☃.func_179840_c()));
         ☃.field_71070_bA.field_75152_c = ☃.func_148901_c();
      } else {
         IInventory ☃ = new ContainerLocalMenu(☃.func_148902_e(), ☃.func_179840_c(), ☃.func_148898_f());
         ☃.func_71007_a(☃);
         ☃.field_71070_bA.field_75152_c = ☃.func_148901_c();
      }
   }

   @Override
   public void func_147266_a(SPacketSetSlot var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      ItemStack ☃x = ☃.func_149174_e();
      int ☃xx = ☃.func_149173_d();
      this.field_147299_f.func_193032_ao().func_193301_a(☃x);
      if (☃.func_149175_c() == -1) {
         ☃.field_71071_by.func_70437_b(☃x);
      } else if (☃.func_149175_c() == -2) {
         ☃.field_71071_by.func_70299_a(☃xx, ☃x);
      } else {
         boolean ☃ = false;
         if (this.field_147299_f.field_71462_r instanceof GuiContainerCreative) {
            GuiContainerCreative ☃x = (GuiContainerCreative)this.field_147299_f.field_71462_r;
            ☃ = ☃x.func_147056_g() != ItemGroup.field_78036_m.func_78021_a();
         }

         if (☃.func_149175_c() == 0 && ☃.func_149173_d() >= 36 && ☃xx < 45) {
            if (!☃x.func_190926_b()) {
               ItemStack ☃ = ☃.field_71069_bz.func_75139_a(☃xx).func_75211_c();
               if (☃.func_190926_b() || ☃.func_190916_E() < ☃x.func_190916_E()) {
                  ☃x.func_190915_d(5);
               }
            }

            ☃.field_71069_bz.func_75141_a(☃xx, ☃x);
         } else if (☃.func_149175_c() == ☃.field_71070_bA.field_75152_c && (☃.func_149175_c() != 0 || !☃)) {
            ☃.field_71070_bA.func_75141_a(☃xx, ☃x);
         }
      }
   }

   @Override
   public void func_147239_a(SPacketConfirmTransaction var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Container ☃ = null;
      EntityPlayer ☃x = this.field_147299_f.field_71439_g;
      if (☃.func_148889_c() == 0) {
         ☃ = ☃x.field_71069_bz;
      } else if (☃.func_148889_c() == ☃x.field_71070_bA.field_75152_c) {
         ☃ = ☃x.field_71070_bA;
      }

      if (☃ != null && !☃.func_148888_e()) {
         this.func_147297_a(new CPacketConfirmTransaction(☃.func_148889_c(), ☃.func_148890_d(), true));
      }
   }

   @Override
   public void func_147241_a(SPacketWindowItems var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      if (☃.func_148911_c() == 0) {
         ☃.field_71069_bz.func_190896_a(☃.func_148910_d());
      } else if (☃.func_148911_c() == ☃.field_71070_bA.field_75152_c) {
         ☃.field_71070_bA.func_190896_a(☃.func_148910_d());
      }
   }

   @Override
   public void func_147268_a(SPacketSignEditorOpen var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      TileEntity ☃ = this.field_147300_g.func_175625_s(☃.func_179777_a());
      if (!(☃ instanceof TileEntitySign)) {
         ☃ = new TileEntitySign();
         ☃.func_145834_a(this.field_147300_g);
         ☃.func_174878_a(☃.func_179777_a());
      }

      this.field_147299_f.field_71439_g.func_175141_a((TileEntitySign)☃);
   }

   @Override
   public void func_147273_a(SPacketUpdateTileEntity var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (this.field_147299_f.field_71441_e.func_175667_e(☃.func_179823_a())) {
         TileEntity ☃ = this.field_147299_f.field_71441_e.func_175625_s(☃.func_179823_a());
         int ☃x = ☃.func_148853_f();
         boolean ☃xx = ☃x == 2 && ☃ instanceof TileEntityCommandBlock;
         if (☃x == 1 && ☃ instanceof TileEntityMobSpawner
            || ☃xx
            || ☃x == 3 && ☃ instanceof TileEntityBeacon
            || ☃x == 4 && ☃ instanceof TileEntitySkull
            || ☃x == 6 && ☃ instanceof TileEntityBanner
            || ☃x == 7 && ☃ instanceof TileEntityStructure
            || ☃x == 8 && ☃ instanceof TileEntityEndGateway
            || ☃x == 9 && ☃ instanceof TileEntitySign
            || ☃x == 10 && ☃ instanceof TileEntityShulkerBox
            || ☃x == 11 && ☃ instanceof TileEntityBed
            || ☃x == 5 && ☃ instanceof TileEntityConduit) {
            ☃.func_145839_a(☃.func_148857_g());
         }

         if (☃xx && this.field_147299_f.field_71462_r instanceof GuiCommandBlock) {
            ((GuiCommandBlock)this.field_147299_f.field_71462_r).func_184075_a();
         }
      }
   }

   @Override
   public void func_147245_a(SPacketWindowProperty var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      if (☃.field_71070_bA != null && ☃.field_71070_bA.field_75152_c == ☃.func_149182_c()) {
         ☃.field_71070_bA.func_75137_b(☃.func_149181_d(), ☃.func_149180_e());
      }
   }

   @Override
   public void func_147242_a(SPacketEntityEquipment var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149389_d());
      if (☃ != null) {
         ☃.func_184201_a(☃.func_186969_c(), ☃.func_149390_c());
      }
   }

   @Override
   public void func_147276_a(SPacketCloseWindow var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_175159_q();
   }

   @Override
   public void func_147261_a(SPacketBlockAction var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_175641_c(☃.func_179825_a(), ☃.func_148868_c(), ☃.func_148869_g(), ☃.func_148864_h());
   }

   @Override
   public void func_147294_a(SPacketBlockBreakAnim var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_175715_c(☃.func_148845_c(), ☃.func_179821_b(), ☃.func_148846_g());
   }

   @Override
   public void func_147252_a(SPacketChangeGameState var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      int ☃x = ☃.func_149138_c();
      float ☃xx = ☃.func_149137_d();
      int ☃xxx = MathHelper.func_76141_d(☃xx + 0.5F);
      if (☃x >= 0 && ☃x < SPacketChangeGameState.field_149142_a.length && SPacketChangeGameState.field_149142_a[☃x] != null) {
         ☃.func_146105_b(new TextComponentTranslation(SPacketChangeGameState.field_149142_a[☃x]), false);
      }

      if (☃x == 1) {
         this.field_147300_g.func_72912_H().func_76084_b(true);
         this.field_147300_g.func_72894_k(0.0F);
      } else if (☃x == 2) {
         this.field_147300_g.func_72912_H().func_76084_b(false);
         this.field_147300_g.func_72894_k(1.0F);
      } else if (☃x == 3) {
         this.field_147299_f.field_71442_b.func_78746_a(GameType.func_77146_a(☃xxx));
      } else if (☃x == 4) {
         if (☃xxx == 0) {
            this.field_147299_f.field_71439_g.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            this.field_147299_f.func_147108_a(new GuiDownloadTerrain());
         } else if (☃xxx == 1) {
            this.field_147299_f
               .func_147108_a(
                  new GuiWinGame(
                     true,
                     () -> this.field_147299_f.field_71439_g.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN))
                  )
               );
         }
      } else if (☃x == 5) {
         GameSettings ☃ = this.field_147299_f.field_71474_y;
         if (☃xx == 0.0F) {
            this.field_147299_f.func_147108_a(new GuiScreenDemo());
         } else if (☃xx == 101.0F) {
            this.field_147299_f
               .field_71456_v
               .func_146158_b()
               .func_146227_a(
                  new TextComponentTranslation(
                     "demo.help.movement",
                     ☃.field_74351_w.func_197978_k(),
                     ☃.field_74370_x.func_197978_k(),
                     ☃.field_74368_y.func_197978_k(),
                     ☃.field_74366_z.func_197978_k()
                  )
               );
         } else if (☃xx == 102.0F) {
            this.field_147299_f.field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("demo.help.jump", ☃.field_74314_A.func_197978_k()));
         } else if (☃xx == 103.0F) {
            this.field_147299_f
               .field_71456_v
               .func_146158_b()
               .func_146227_a(new TextComponentTranslation("demo.help.inventory", ☃.field_151445_Q.func_197978_k()));
         } else if (☃xx == 104.0F) {
            this.field_147299_f.field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("demo.day.6", ☃.field_151447_Z.func_197978_k()));
         }
      } else if (☃x == 6) {
         this.field_147300_g
            .func_184148_a(
               ☃, ☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e(), ☃.field_70161_v, SoundEvents.field_187734_u, SoundCategory.PLAYERS, 0.18F, 0.45F
            );
      } else if (☃x == 7) {
         this.field_147300_g.func_72894_k(☃xx);
      } else if (☃x == 8) {
         this.field_147300_g.func_147442_i(☃xx);
      } else if (☃x == 9) {
         this.field_147300_g
            .func_184148_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_203830_gs, SoundCategory.NEUTRAL, 1.0F, 1.0F);
      } else if (☃x == 10) {
         this.field_147300_g.func_195594_a(Particles.field_197621_n, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, 0.0, 0.0, 0.0);
         this.field_147300_g
            .func_184148_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_187514_aD, SoundCategory.HOSTILE, 1.0F, 1.0F);
      }
   }

   @Override
   public void func_147264_a(SPacketMaps var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      MapItemRenderer ☃ = this.field_147299_f.field_71460_t.func_147701_i();
      String ☃x = "map_" + ☃.func_149188_c();
      MapData ☃xx = ItemMap.func_195953_a(this.field_147299_f.field_71441_e, ☃x);
      if (☃xx == null) {
         ☃xx = new MapData(☃x);
         if (☃.func_191205_a(☃x) != null) {
            MapData ☃xxx = ☃.func_191207_a(☃.func_191205_a(☃x));
            if (☃xxx != null) {
               ☃xx = ☃xxx;
            }
         }

         this.field_147299_f.field_71441_e.func_212409_a(DimensionType.OVERWORLD, ☃x, ☃xx);
      }

      ☃.func_179734_a(☃xx);
      ☃.func_148246_a(☃xx);
   }

   @Override
   public void func_147277_a(SPacketEffect var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (☃.func_149244_c()) {
         this.field_147299_f.field_71441_e.func_175669_a(☃.func_149242_d(), ☃.func_179746_d(), ☃.func_149241_e());
      } else {
         this.field_147299_f.field_71441_e.func_175718_b(☃.func_149242_d(), ☃.func_179746_d(), ☃.func_149241_e());
      }
   }

   @Override
   public void func_191981_a(SPacketAdvancementInfo var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_191983_k.func_192799_a(☃);
   }

   @Override
   public void func_194022_a(SPacketSelectAdvancementsTab var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      ResourceLocation ☃ = ☃.func_194154_a();
      if (☃ == null) {
         this.field_191983_k.func_194230_a(null, false);
      } else {
         Advancement ☃ = this.field_191983_k.func_194229_a().func_192084_a(☃);
         this.field_191983_k.func_194230_a(☃, false);
      }
   }

   @Override
   public void func_195511_a(SPacketCommandList var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_195517_n = new CommandDispatcher<>(☃.func_197693_a());
   }

   @Override
   public void func_195512_a(SPacketStopSound var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.func_147118_V().func_195478_a(☃.func_197703_a(), ☃.func_197704_b());
   }

   @Override
   public void func_195510_a(SPacketTabComplete var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_195516_l.func_197015_a(☃.func_197689_a(), ☃.func_197687_b());
   }

   @Override
   public void func_199525_a(SPacketUpdateRecipes var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_199528_o.func_199518_d();

      for(IRecipe ☃ : ☃.func_199616_a()) {
         this.field_199528_o.func_199509_a(☃);
      }

      SearchTree<RecipeList> ☃ = (SearchTree)this.field_147299_f.<RecipeList>func_193987_a(SearchTreeManager.field_194012_b);
      ☃.func_199550_b();
      RecipeBookClient ☃x = this.field_147299_f.field_71439_g.func_199507_B();
      ☃x.func_199644_c();
      ☃x.func_199642_d().forEach(☃::func_194043_a);
      ☃.func_194040_a();
   }

   @Override
   public void func_200232_a(SPacketPlayerLook var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Vec3d ☃ = ☃.func_200531_a(this.field_147300_g);
      if (☃ != null) {
         this.field_147299_f.field_71439_g.func_200602_a(☃.func_201064_a(), ☃);
      }
   }

   @Override
   public void func_211522_a(SPacketNBTQueryResponse var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (!this.field_211524_l.func_211548_a(☃.func_211713_b(), ☃.func_211712_c())) {
         field_147301_d.debug("Got unhandled response to tag query {}", ☃.func_211713_b());
      }
   }

   @Override
   public void func_147293_a(SPacketStatistics var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);

      for(Entry<Stat<?>, Integer> ☃ : ☃.func_148974_c().entrySet()) {
         Stat<?> ☃x = (Stat)☃.getKey();
         int ☃xx = ☃.getValue();
         this.field_147299_f.field_71439_g.func_146107_m().func_150873_a(this.field_147299_f.field_71439_g, ☃x, ☃xx);
      }

      if (this.field_147299_f.field_71462_r instanceof IProgressMeter) {
         ((IProgressMeter)this.field_147299_f.field_71462_r).func_193026_g();
      }
   }

   @Override
   public void func_191980_a(SPacketRecipeBook var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      RecipeBookClient ☃ = this.field_147299_f.field_71439_g.func_199507_B();
      ☃.func_192813_a(☃.func_192593_c());
      ☃.func_192810_b(☃.func_192594_d());
      ☃.func_202881_c(☃.func_202492_e());
      ☃.func_202882_d(☃.func_202493_f());
      SPacketRecipeBook.State ☃x = ☃.func_194151_e();
      switch(☃x) {
         case REMOVE:
            for(ResourceLocation ☃xx : ☃.func_192595_a()) {
               IRecipe ☃xxx = this.field_199528_o.func_199517_a(☃xx);
               if (☃xxx != null) {
                  ☃.func_193831_b(☃xxx);
               }
            }
            break;
         case INIT:
            for(ResourceLocation ☃xx : ☃.func_192595_a()) {
               IRecipe ☃xxx = this.field_199528_o.func_199517_a(☃xx);
               if (☃xxx != null) {
                  ☃.func_194073_a(☃xxx);
               }
            }

            for(ResourceLocation ☃xx : ☃.func_193644_b()) {
               IRecipe ☃xxx = this.field_199528_o.func_199517_a(☃xx);
               if (☃xxx != null) {
                  ☃.func_193825_e(☃xxx);
               }
            }
            break;
         case ADD:
            for(ResourceLocation ☃xx : ☃.func_192595_a()) {
               IRecipe ☃xxx = this.field_199528_o.func_199517_a(☃xx);
               if (☃xxx != null) {
                  ☃.func_194073_a(☃xxx);
                  ☃.func_193825_e(☃xxx);
                  RecipeToast.func_193665_a(this.field_147299_f.func_193033_an(), ☃xxx);
               }
            }
      }

      ☃.func_199642_d().forEach(var1x -> var1x.func_194214_a(☃));
      if (this.field_147299_f.field_71462_r instanceof IRecipeShownListener) {
         ((IRecipeShownListener)this.field_147299_f.field_71462_r).func_192043_J_();
      }
   }

   @Override
   public void func_147260_a(SPacketEntityEffect var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149426_d());
      if (☃ instanceof EntityLivingBase) {
         Potion ☃x = Potion.func_188412_a(☃.func_149427_e());
         if (☃x != null) {
            PotionEffect ☃xx = new PotionEffect(☃x, ☃.func_180755_e(), ☃.func_149428_f(), ☃.func_186984_g(), ☃.func_179707_f(), ☃.func_205527_h());
            ☃xx.func_100012_b(☃.func_149429_c());
            ((EntityLivingBase)☃).func_195064_c(☃xx);
         }
      }
   }

   @Override
   public void func_199723_a(SPacketTagsList var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_199725_m = ☃.func_199858_a();
      if (!this.field_147302_e.func_150731_c()) {
         BlockTags.func_199895_a(this.field_199725_m.func_199717_a());
         ItemTags.func_199902_a(this.field_199725_m.func_199715_b());
         FluidTags.func_206953_a(this.field_199725_m.func_205704_c());
      }
   }

   @Override
   public void func_175098_a(SPacketCombatEvent var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (☃.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         Entity ☃ = this.field_147300_g.func_73045_a(☃.field_179774_b);
         if (☃ == this.field_147299_f.field_71439_g) {
            this.field_147299_f.func_147108_a(new GuiGameOver(☃.field_179773_e));
         }
      }
   }

   @Override
   public void func_175101_a(SPacketServerDifficulty var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_72912_H().func_176144_a(☃.func_179831_b());
      this.field_147299_f.field_71441_e.func_72912_H().func_180783_e(☃.func_179830_a());
   }

   @Override
   public void func_175094_a(SPacketCamera var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = ☃.func_179780_a(this.field_147300_g);
      if (☃ != null) {
         this.field_147299_f.func_175607_a(☃);
      }
   }

   @Override
   public void func_175093_a(SPacketWorldBorder var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      ☃.func_179788_a(this.field_147300_g.func_175723_af());
   }

   @Override
   public void func_175099_a(SPacketTitle var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      SPacketTitle.Type ☃ = ☃.func_179807_a();
      String ☃x = null;
      String ☃xx = null;
      String ☃xxx = ☃.func_179805_b() != null ? ☃.func_179805_b().func_150254_d() : "";
      switch(☃) {
         case TITLE:
            ☃x = ☃xxx;
            break;
         case SUBTITLE:
            ☃xx = ☃xxx;
            break;
         case ACTIONBAR:
            this.field_147299_f.field_71456_v.func_110326_a(☃xxx, false);
            return;
         case RESET:
            this.field_147299_f.field_71456_v.func_175178_a("", "", -1, -1, -1);
            this.field_147299_f.field_71456_v.func_175177_a();
            return;
      }

      this.field_147299_f.field_71456_v.func_175178_a(☃x, ☃xx, ☃.func_179806_c(), ☃.func_179804_d(), ☃.func_179803_e());
   }

   @Override
   public void func_175096_a(SPacketPlayerListHeaderFooter var1) {
      this.field_147299_f.field_71456_v.func_175181_h().func_175244_b(☃.func_179700_a().func_150254_d().isEmpty() ? null : ☃.func_179700_a());
      this.field_147299_f.field_71456_v.func_175181_h().func_175248_a(☃.func_179701_b().func_150254_d().isEmpty() ? null : ☃.func_179701_b());
   }

   @Override
   public void func_147262_a(SPacketRemoveEntityEffect var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = ☃.func_186967_a(this.field_147300_g);
      if (☃ instanceof EntityLivingBase) {
         ((EntityLivingBase)☃).func_184596_c(☃.func_186968_a());
      }
   }

   @Override
   public void func_147256_a(SPacketPlayerListItem var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);

      for(SPacketPlayerListItem.AddPlayerData ☃ : ☃.func_179767_a()) {
         if (☃.func_179768_b() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
            this.field_147310_i.remove(☃.func_179962_a().getId());
         } else {
            NetworkPlayerInfo ☃x = (NetworkPlayerInfo)this.field_147310_i.get(☃.func_179962_a().getId());
            if (☃.func_179768_b() == SPacketPlayerListItem.Action.ADD_PLAYER) {
               ☃x = new NetworkPlayerInfo(☃);
               this.field_147310_i.put(☃x.func_178845_a().getId(), ☃x);
            }

            if (☃x != null) {
               switch(☃.func_179768_b()) {
                  case ADD_PLAYER:
                     ☃x.func_178839_a(☃.func_179960_c());
                     ☃x.func_178838_a(☃.func_179963_b());
                     ☃x.func_178859_a(☃.func_179961_d());
                     break;
                  case UPDATE_GAME_MODE:
                     ☃x.func_178839_a(☃.func_179960_c());
                     break;
                  case UPDATE_LATENCY:
                     ☃x.func_178838_a(☃.func_179963_b());
                     break;
                  case UPDATE_DISPLAY_NAME:
                     ☃x.func_178859_a(☃.func_179961_d());
               }
            }
         }
      }
   }

   @Override
   public void func_147272_a(SPacketKeepAlive var1) {
      this.func_147297_a(new CPacketKeepAlive(☃.func_149134_c()));
   }

   @Override
   public void func_147270_a(SPacketPlayerAbilities var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      EntityPlayer ☃ = this.field_147299_f.field_71439_g;
      ☃.field_71075_bZ.field_75100_b = ☃.func_149106_d();
      ☃.field_71075_bZ.field_75098_d = ☃.func_149103_f();
      ☃.field_71075_bZ.field_75102_a = ☃.func_149112_c();
      ☃.field_71075_bZ.field_75101_c = ☃.func_149105_e();
      ☃.field_71075_bZ.func_195931_a((double)☃.func_149101_g());
      ☃.field_71075_bZ.func_82877_b(☃.func_149107_h());
   }

   @Override
   public void func_184327_a(SPacketSoundEffect var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f
         .field_71441_e
         .func_184148_a(
            this.field_147299_f.field_71439_g,
            ☃.func_149207_d(),
            ☃.func_149211_e(),
            ☃.func_149210_f(),
            ☃.func_186978_a(),
            ☃.func_186977_b(),
            ☃.func_149208_g(),
            ☃.func_149209_h()
         );
   }

   @Override
   public void func_184329_a(SPacketCustomSound var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f
         .func_147118_V()
         .func_147682_a(
            new SimpleSound(
               ☃.func_197698_a(),
               ☃.func_186929_b(),
               ☃.func_186927_f(),
               ☃.func_186928_g(),
               false,
               0,
               ISound.AttenuationType.LINEAR,
               (float)☃.func_186932_c(),
               (float)☃.func_186926_d(),
               (float)☃.func_186925_e()
            )
         );
   }

   @Override
   public void func_175095_a(SPacketResourcePackSend var1) {
      String ☃ = ☃.func_179783_a();
      String ☃x = ☃.func_179784_b();
      if (this.func_189688_b(☃)) {
         if (☃.startsWith("level://")) {
            try {
               String ☃xx = URLDecoder.decode(☃.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File ☃xxx = new File(this.field_147299_f.field_71412_D, "saves");
               File ☃xxxx = new File(☃xxx, ☃xx);
               if (☃xxxx.isFile()) {
                  this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
                  Futures.addCallback(this.field_147299_f.func_195541_I().func_195741_a(☃xxxx), this.func_189686_f());
                  return;
               }
            } catch (UnsupportedEncodingException var7) {
            }

            this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         } else {
            ServerData ☃xx = this.field_147299_f.func_147104_D();
            if (☃xx != null && ☃xx.func_152586_b() == ServerData.ServerResourceMode.ENABLED) {
               this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
               Futures.addCallback(this.field_147299_f.func_195541_I().func_195744_a(☃, ☃x), this.func_189686_f());
            } else if (☃xx != null && ☃xx.func_152586_b() != ServerData.ServerResourceMode.PROMPT) {
               this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
            } else {
               this.field_147299_f.func_152344_a(() -> this.field_147299_f.func_147108_a(new GuiYesNo((var3x, var4x) -> {
                     this.field_147299_f = Minecraft.func_71410_x();
                     ServerData ☃ = this.field_147299_f.func_147104_D();
                     if (var3x) {
                        if (☃ != null) {
                           ☃.func_152584_a(ServerData.ServerResourceMode.ENABLED);
                        }

                        this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
                        Futures.addCallback(this.field_147299_f.func_195541_I().func_195744_a(☃, ☃), this.func_189686_f());
                     } else {
                        if (☃ != null) {
                           ☃.func_152584_a(ServerData.ServerResourceMode.DISABLED);
                        }

                        this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
                     }

                     ServerList.func_147414_b(☃);
                     this.field_147299_f.func_147108_a(null);
                  }, I18n.func_135052_a("multiplayer.texturePrompt.line1"), I18n.func_135052_a("multiplayer.texturePrompt.line2"), 0)));
            }
         }
      }
   }

   private boolean func_189688_b(String var1) {
      try {
         URI ☃ = new URI(☃);
         String ☃x = ☃.getScheme();
         boolean ☃xx = "level".equals(☃x);
         if (!"http".equals(☃x) && !"https".equals(☃x) && !☃xx) {
            throw new URISyntaxException(☃, "Wrong protocol");
         } else if (!☃xx || !☃.contains("..") && ☃.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(☃, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var5) {
         this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         return false;
      }
   }

   private FutureCallback<Object> func_189686_f() {
      return new FutureCallback<Object>() {
         @Override
         public void onSuccess(@Nullable Object var1) {
            NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
         }

         @Override
         public void onFailure(Throwable var1) {
            NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         }
      };
   }

   @Override
   public void func_184325_a(SPacketUpdateBossInfo var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      this.field_147299_f.field_71456_v.func_184046_j().func_184055_a(☃);
   }

   @Override
   public void func_184324_a(SPacketCooldown var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (☃.func_186922_b() == 0) {
         this.field_147299_f.field_71439_g.func_184811_cZ().func_185142_b(☃.func_186920_a());
      } else {
         this.field_147299_f.field_71439_g.func_184811_cZ().func_185145_a(☃.func_186920_a(), ☃.func_186922_b());
      }
   }

   @Override
   public void func_184323_a(SPacketMoveVehicle var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147299_f.field_71439_g.func_184208_bv();
      if (☃ != this.field_147299_f.field_71439_g && ☃.func_184186_bw()) {
         ☃.func_70080_a(☃.func_186957_a(), ☃.func_186955_b(), ☃.func_186956_c(), ☃.func_186959_d(), ☃.func_186958_e());
         this.field_147302_e.func_179290_a(new CPacketVehicleMove(☃));
      }
   }

   @Override
   public void func_147240_a(SPacketCustomPayload var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      ResourceLocation ☃ = ☃.func_149169_c();
      PacketBuffer ☃x = null;

      try {
         ☃x = ☃.func_180735_b();
         if (SPacketCustomPayload.field_209910_a.equals(☃)) {
            try {
               int ☃xx = ☃x.readInt();
               GuiScreen ☃xxx = this.field_147299_f.field_71462_r;
               if (☃xxx instanceof GuiMerchant && ☃xx == this.field_147299_f.field_71439_g.field_71070_bA.field_75152_c) {
                  IMerchant ☃xxxx = ((GuiMerchant)☃xxx).func_147035_g();
                  MerchantRecipeList ☃xxxxx = MerchantRecipeList.func_151390_b(☃x);
                  ☃xxxx.func_70930_a(☃xxxxx);
               }
            } catch (IOException var13) {
               field_147301_d.error("Couldn't load trade info", var13);
            }
         } else if (SPacketCustomPayload.field_209911_b.equals(☃)) {
            this.field_147299_f.field_71439_g.func_175158_f(☃x.func_150789_c(32767));
         } else if (SPacketCustomPayload.field_209912_c.equals(☃)) {
            EnumHand ☃xx = ☃x.func_179257_a(EnumHand.class);
            ItemStack ☃xxx = ☃xx == EnumHand.OFF_HAND ? this.field_147299_f.field_71439_g.func_184592_cb() : this.field_147299_f.field_71439_g.func_184614_ca();
            if (☃xxx.func_77973_b() == Items.field_151164_bB) {
               this.field_147299_f.func_147108_a(new GuiScreenBook(this.field_147299_f.field_71439_g, ☃xxx, false, ☃xx));
            }
         } else if (SPacketCustomPayload.field_209913_d.equals(☃)) {
            int ☃xx = ☃x.readInt();
            float ☃xxx = ☃x.readFloat();
            Path ☃xxxx = Path.func_186311_b(☃x);
            this.field_147299_f.field_184132_p.field_188286_a.func_188289_a(☃xx, ☃xxxx, ☃xxx);
         } else if (SPacketCustomPayload.field_209914_e.equals(☃)) {
            long ☃xx = ☃x.func_179260_f();
            BlockPos ☃xxx = ☃x.func_179259_c();
            ((DebugRendererNeighborsUpdate)this.field_147299_f.field_184132_p.field_191557_f).func_191553_a(☃xx, ☃xxx);
         } else if (SPacketCustomPayload.field_209915_f.equals(☃)) {
            BlockPos ☃xx = ☃x.func_179259_c();
            int ☃xxx = ☃x.readInt();
            List<BlockPos> ☃xxxx = Lists.<BlockPos>newArrayList();
            List<Float> ☃xxxxx = Lists.newArrayList();

            for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxx; ++☃xxxxxx) {
               ☃xxxx.add(☃x.func_179259_c());
               ☃xxxxx.add(☃x.readFloat());
            }

            this.field_147299_f.field_184132_p.field_201747_g.func_201742_a(☃xx, ☃xxxx, ☃xxxxx);
         } else if (SPacketCustomPayload.field_209916_g.equals(☃)) {
            int ☃xx = ☃x.readInt();
            MutableBoundingBox ☃xxx = new MutableBoundingBox(☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt());
            int ☃xxxx = ☃x.readInt();
            List<MutableBoundingBox> ☃xxxxx = Lists.<MutableBoundingBox>newArrayList();
            List<Boolean> ☃xxxxxx = Lists.newArrayList();

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxx; ++☃xxxxxxx) {
               ☃xxxxx.add(new MutableBoundingBox(☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt(), ☃x.readInt()));
               ☃xxxxxx.add(☃x.readBoolean());
            }

            this.field_147299_f.field_184132_p.field_201748_h.func_201729_a(☃xxx, ☃xxxxx, ☃xxxxxx, ☃xx);
         } else if (SPacketCustomPayload.field_209917_h.equals(☃)) {
            ((DebugRendererWorldGenAttempts)this.field_147299_f.field_184132_p.field_201750_j)
               .func_201734_a(☃x.func_179259_c(), ☃x.readFloat(), ☃x.readFloat(), ☃x.readFloat(), ☃x.readFloat(), ☃x.readFloat());
            field_147301_d.warn("Unknown custom packed identifier: {}", ☃);
         } else {
            field_147301_d.warn("Unknown custom packed identifier: {}", ☃);
         }
      } finally {
         if (☃x != null) {
            ☃x.release();
         }
      }
   }

   @Override
   public void func_147291_a(SPacketScoreboardObjective var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Scoreboard ☃ = this.field_147300_g.func_96441_U();
      String ☃x = ☃.func_149339_c();
      if (☃.func_149338_e() == 0) {
         ☃.func_199868_a(☃x, ScoreCriteria.field_96641_b, ☃.func_149337_d(), ☃.func_199856_d());
      } else if (☃.func_197900_b(☃x)) {
         ScoreObjective ☃ = ☃.func_96518_b(☃x);
         if (☃.func_149338_e() == 1) {
            ☃.func_96519_k(☃);
         } else if (☃.func_149338_e() == 2) {
            ☃.func_199866_a(☃.func_199856_d());
            ☃.func_199864_a(☃.func_149337_d());
         }
      }
   }

   @Override
   public void func_147250_a(SPacketUpdateScore var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Scoreboard ☃ = this.field_147300_g.func_96441_U();
      String ☃x = ☃.func_149321_d();
      switch(☃.func_197701_d()) {
         case CHANGE:
            ScoreObjective ☃xx = ☃.func_197899_c(☃x);
            Score ☃xxx = ☃.func_96529_a(☃.func_149324_c(), ☃xx);
            ☃xxx.func_96647_c(☃.func_149323_e());
            break;
         case REMOVE:
            ☃.func_178822_d(☃.func_149324_c(), ☃.func_96518_b(☃x));
      }
   }

   @Override
   public void func_147254_a(SPacketDisplayObjective var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Scoreboard ☃ = this.field_147300_g.func_96441_U();
      String ☃x = ☃.func_149370_d();
      ScoreObjective ☃xx = ☃x == null ? null : ☃.func_197899_c(☃x);
      ☃.func_96530_a(☃.func_149371_c(), ☃xx);
   }

   @Override
   public void func_147247_a(SPacketTeams var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Scoreboard ☃x = this.field_147300_g.func_96441_U();
      ScorePlayerTeam ☃;
      if (☃.func_149307_h() == 0) {
         ☃ = ☃x.func_96527_f(☃.func_149312_c());
      } else {
         ☃ = ☃x.func_96508_e(☃.func_149312_c());
      }

      if (☃.func_149307_h() == 0 || ☃.func_149307_h() == 2) {
         ☃.func_96664_a(☃.func_149306_d());
         ☃.func_178774_a(☃.func_200537_f());
         ☃.func_98298_a(☃.func_149308_i());
         Team.EnumVisible ☃ = Team.EnumVisible.func_178824_a(☃.func_179814_i());
         if (☃ != null) {
            ☃.func_178772_a(☃);
         }

         Team.CollisionRule ☃ = Team.CollisionRule.func_186686_a(☃.func_186975_j());
         if (☃ != null) {
            ☃.func_186682_a(☃);
         }

         ☃.func_207408_a(☃.func_207507_i());
         ☃.func_207409_b(☃.func_207508_j());
      }

      if (☃.func_149307_h() == 0 || ☃.func_149307_h() == 3) {
         for(String ☃ : ☃.func_149310_g()) {
            ☃x.func_197901_a(☃, ☃);
         }
      }

      if (☃.func_149307_h() == 4) {
         for(String ☃ : ☃.func_149310_g()) {
            ☃x.func_96512_b(☃, ☃);
         }
      }

      if (☃.func_149307_h() == 1) {
         ☃x.func_96511_d(☃);
      }
   }

   @Override
   public void func_147289_a(SPacketParticles var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      if (☃.func_149222_k() == 0) {
         double ☃ = (double)(☃.func_149227_j() * ☃.func_149221_g());
         double ☃x = (double)(☃.func_149227_j() * ☃.func_149224_h());
         double ☃xx = (double)(☃.func_149227_j() * ☃.func_149223_i());

         try {
            this.field_147300_g.func_195590_a(☃.func_197699_j(), ☃.func_179750_b(), ☃.func_149220_d(), ☃.func_149226_e(), ☃.func_149225_f(), ☃, ☃x, ☃xx);
         } catch (Throwable var17) {
            field_147301_d.warn("Could not spawn particle effect {}", ☃.func_197699_j());
         }
      } else {
         for(int ☃ = 0; ☃ < ☃.func_149222_k(); ++☃) {
            double ☃x = this.field_147306_l.nextGaussian() * (double)☃.func_149221_g();
            double ☃xx = this.field_147306_l.nextGaussian() * (double)☃.func_149224_h();
            double ☃xxx = this.field_147306_l.nextGaussian() * (double)☃.func_149223_i();
            double ☃xxxx = this.field_147306_l.nextGaussian() * (double)☃.func_149227_j();
            double ☃xxxxx = this.field_147306_l.nextGaussian() * (double)☃.func_149227_j();
            double ☃xxxxxx = this.field_147306_l.nextGaussian() * (double)☃.func_149227_j();

            try {
               this.field_147300_g
                  .func_195590_a(
                     ☃.func_197699_j(), ☃.func_179750_b(), ☃.func_149220_d() + ☃x, ☃.func_149226_e() + ☃xx, ☃.func_149225_f() + ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx
                  );
            } catch (Throwable var16) {
               field_147301_d.warn("Could not spawn particle effect {}", ☃.func_197699_j());
               return;
            }
         }
      }
   }

   @Override
   public void func_147290_a(SPacketEntityProperties var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Entity ☃ = this.field_147300_g.func_73045_a(☃.func_149442_c());
      if (☃ != null) {
         if (!(☃ instanceof EntityLivingBase)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + ☃ + ")");
         } else {
            AbstractAttributeMap ☃x = ((EntityLivingBase)☃).func_110140_aT();

            for(SPacketEntityProperties.Snapshot ☃xx : ☃.func_149441_d()) {
               IAttributeInstance ☃xxx = ☃x.func_111152_a(☃xx.func_151409_a());
               if (☃xxx == null) {
                  ☃xxx = ☃x.func_111150_b(new RangedAttribute(null, ☃xx.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
               }

               ☃xxx.func_111128_a(☃xx.func_151410_b());
               ☃xxx.func_142049_d();

               for(AttributeModifier ☃xxx : ☃xx.func_151408_c()) {
                  ☃xxx.func_111121_a(☃xxx);
               }
            }
         }
      }
   }

   @Override
   public void func_194307_a(SPacketPlaceGhostRecipe var1) {
      PacketThreadUtil.func_180031_a(☃, this, this.field_147299_f);
      Container ☃ = this.field_147299_f.field_71439_g.field_71070_bA;
      if (☃.field_75152_c == ☃.func_194313_b() && ☃.func_75129_b(this.field_147299_f.field_71439_g)) {
         IRecipe ☃x = this.field_199528_o.func_199517_a(☃.func_199615_a());
         if (☃x != null) {
            if (this.field_147299_f.field_71462_r instanceof IRecipeShownListener) {
               GuiRecipeBook ☃xx = ((IRecipeShownListener)this.field_147299_f.field_71462_r).func_194310_f();
               ☃xx.func_193951_a(☃x, ☃.field_75151_b);
            } else if (this.field_147299_f.field_71462_r instanceof GuiFurnace) {
               ((GuiFurnace)this.field_147299_f.field_71462_r).field_201557_v.func_193951_a(☃x, ☃.field_75151_b);
            }
         }
      }
   }

   public NetworkManager func_147298_b() {
      return this.field_147302_e;
   }

   public Collection<NetworkPlayerInfo> func_175106_d() {
      return this.field_147310_i.values();
   }

   public NetworkPlayerInfo func_175102_a(UUID var1) {
      return (NetworkPlayerInfo)this.field_147310_i.get(☃);
   }

   @Nullable
   public NetworkPlayerInfo func_175104_a(String var1) {
      for(NetworkPlayerInfo ☃ : this.field_147310_i.values()) {
         if (☃.func_178845_a().getName().equals(☃)) {
            return ☃;
         }
      }

      return null;
   }

   public GameProfile func_175105_e() {
      return this.field_175107_d;
   }

   public ClientAdvancementManager func_191982_f() {
      return this.field_191983_k;
   }

   public CommandDispatcher<ISuggestionProvider> func_195515_i() {
      return this.field_195517_n;
   }

   public WorldClient func_195514_j() {
      return this.field_147300_g;
   }

   public NetworkTagManager func_199724_l() {
      return this.field_199725_m;
   }

   public NBTQueryManager func_211523_k() {
      return this.field_211524_l;
   }
}
