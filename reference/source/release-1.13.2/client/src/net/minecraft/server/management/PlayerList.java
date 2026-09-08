package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketTagsList;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketUpdateRecipes;
import net.minecraft.network.play.server.SPacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldServer;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {
   public static final File field_152613_a = new File("banned-players.json");
   public static final File field_152614_b = new File("banned-ips.json");
   public static final File field_152615_c = new File("ops.json");
   public static final File field_152616_d = new File("whitelist.json");
   private static final Logger field_148546_d = LogManager.getLogger();
   private static final SimpleDateFormat field_72403_e = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer field_72400_f;
   private final List<EntityPlayerMP> field_72404_b = Lists.<EntityPlayerMP>newArrayList();
   private final Map<UUID, EntityPlayerMP> field_177454_f = Maps.newHashMap();
   private final UserListBans field_72401_g = new UserListBans(field_152613_a);
   private final UserListIPBans field_72413_h = new UserListIPBans(field_152614_b);
   private final UserListOps field_72414_i = new UserListOps(field_152615_c);
   private final UserListWhitelist field_72411_j = new UserListWhitelist(field_152616_d);
   private final Map<UUID, StatisticsManagerServer> field_148547_k = Maps.newHashMap();
   private final Map<UUID, PlayerAdvancements> field_192055_p = Maps.newHashMap();
   private IPlayerFileData field_72412_k;
   private boolean field_72409_l;
   protected int field_72405_c;
   private int field_72402_d;
   private GameType field_72410_m;
   private boolean field_72407_n;
   private int field_72408_o;

   public PlayerList(MinecraftServer var1) {
      this.field_72400_f = ☃;
      this.func_152608_h().func_152686_a(true);
      this.func_72363_f().func_152686_a(true);
      this.field_72405_c = 8;
   }

   public void func_72355_a(NetworkManager var1, EntityPlayerMP var2) {
      GameProfile ☃ = ☃.func_146103_bH();
      PlayerProfileCache ☃x = this.field_72400_f.func_152358_ax();
      GameProfile ☃xx = ☃x.func_152652_a(☃.getId());
      String ☃xxx = ☃xx == null ? ☃.getName() : ☃xx.getName();
      ☃x.func_152649_a(☃);
      NBTTagCompound ☃xxxx = this.func_72380_a(☃);
      ☃.func_70029_a(this.field_72400_f.func_71218_a(☃.field_71093_bK));
      ☃.field_71134_c.func_73080_a((WorldServer)☃.field_70170_p);
      String ☃xxxxx = "local";
      if (☃.func_74430_c() != null) {
         ☃xxxxx = ☃.func_74430_c().toString();
      }

      field_148546_d.info(
         "{}[{}] logged in with entity id {} at ({}, {}, {})",
         ☃.func_200200_C_().getString(),
         ☃xxxxx,
         ☃.func_145782_y(),
         ☃.field_70165_t,
         ☃.field_70163_u,
         ☃.field_70161_v
      );
      WorldServer ☃x = this.field_72400_f.func_71218_a(☃.field_71093_bK);
      WorldInfo ☃xx = ☃x.func_72912_H();
      this.func_72381_a(☃, null, ☃x);
      NetHandlerPlayServer ☃xxx = new NetHandlerPlayServer(this.field_72400_f, ☃, ☃);
      ☃xxx.func_147359_a(
         new SPacketJoinGame(
            ☃.func_145782_y(),
            ☃.field_71134_c.func_73081_b(),
            ☃xx.func_76093_s(),
            ☃x.field_73011_w.func_186058_p(),
            ☃x.func_175659_aa(),
            this.func_72352_l(),
            ☃xx.func_76067_t(),
            ☃x.func_82736_K().func_82766_b("reducedDebugInfo")
         )
      );
      ☃xxx.func_147359_a(
         new SPacketCustomPayload(
            SPacketCustomPayload.field_209911_b, new PacketBuffer(Unpooled.buffer()).func_180714_a(this.func_72365_p().getServerModName())
         )
      );
      ☃xxx.func_147359_a(new SPacketServerDifficulty(☃xx.func_176130_y(), ☃xx.func_176123_z()));
      ☃xxx.func_147359_a(new SPacketPlayerAbilities(☃.field_71075_bZ));
      ☃xxx.func_147359_a(new SPacketHeldItemChange(☃.field_71071_by.field_70461_c));
      ☃xxx.func_147359_a(new SPacketUpdateRecipes(this.field_72400_f.func_199529_aN().func_199510_b()));
      ☃xxx.func_147359_a(new SPacketTagsList(this.field_72400_f.func_199731_aO()));
      this.func_187243_f(☃);
      ☃.func_147099_x().func_150877_d();
      ☃.func_192037_E().func_192826_c(☃);
      this.func_96456_a(☃x.func_96441_U(), ☃);
      this.field_72400_f.func_147132_au();
      ITextComponent ☃;
      if (☃.func_146103_bH().getName().equalsIgnoreCase(☃xxx)) {
         ☃ = new TextComponentTranslation("multiplayer.player.joined", ☃.func_145748_c_());
      } else {
         ☃ = new TextComponentTranslation("multiplayer.player.joined.renamed", ☃.func_145748_c_(), ☃xxx);
      }

      this.func_148539_a(☃.func_211708_a(TextFormatting.YELLOW));
      this.func_72377_c(☃);
      ☃xxx.func_147364_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
      this.func_72354_b(☃, ☃x);
      if (!this.field_72400_f.func_147133_T().isEmpty()) {
         ☃.func_175397_a(this.field_72400_f.func_147133_T(), this.field_72400_f.func_175581_ab());
      }

      for(PotionEffect ☃ : ☃.func_70651_bq()) {
         ☃xxx.func_147359_a(new SPacketEntityEffect(☃.func_145782_y(), ☃));
      }

      if (☃xxxx != null && ☃xxxx.func_150297_b("RootVehicle", 10)) {
         NBTTagCompound ☃ = ☃xxxx.func_74775_l("RootVehicle");
         Entity ☃x = AnvilChunkLoader.func_186051_a(☃.func_74775_l("Entity"), ☃x, true);
         if (☃x != null) {
            UUID ☃xx = ☃.func_186857_a("Attach");
            if (☃x.func_110124_au().equals(☃xx)) {
               ☃.func_184205_a(☃x, true);
            } else {
               for(Entity ☃xx : ☃x.func_184182_bu()) {
                  if (☃xx.func_110124_au().equals(☃xx)) {
                     ☃.func_184205_a(☃xx, true);
                     break;
                  }
               }
            }

            if (!☃.func_184218_aH()) {
               field_148546_d.warn("Couldn't reattach entity to player");
               ☃x.func_72973_f(☃x);

               for(Entity ☃xx : ☃x.func_184182_bu()) {
                  ☃x.func_72973_f(☃xx);
               }
            }
         }
      }

      ☃.func_71116_b();
   }

   protected void func_96456_a(ServerScoreboard var1, EntityPlayerMP var2) {
      Set<ScoreObjective> ☃ = Sets.<ScoreObjective>newHashSet();

      for(ScorePlayerTeam ☃x : ☃.func_96525_g()) {
         ☃.field_71135_a.func_147359_a(new SPacketTeams(☃x, 0));
      }

      for(int ☃x = 0; ☃x < 19; ++☃x) {
         ScoreObjective ☃xx = ☃.func_96539_a(☃x);
         if (☃xx != null && !☃.contains(☃xx)) {
            for(Packet<?> ☃xxx : ☃.func_96550_d(☃xx)) {
               ☃.field_71135_a.func_147359_a(☃xxx);
            }

            ☃.add(☃xx);
         }
      }
   }

   public void func_212504_a(WorldServer var1) {
      this.field_72412_k = ☃.func_72860_G().func_75756_e();
      ☃.func_175723_af().func_177737_a(new IBorderListener() {
         @Override
         public void func_177694_a(WorldBorder var1, double var2) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.SET_SIZE));
         }

         @Override
         public void func_177692_a(WorldBorder var1, double var2, double var4, long var6) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.LERP_SIZE));
         }

         @Override
         public void func_177693_a(WorldBorder var1, double var2, double var4) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.SET_CENTER));
         }

         @Override
         public void func_177691_a(WorldBorder var1, int var2) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.SET_WARNING_TIME));
         }

         @Override
         public void func_177690_b(WorldBorder var1, int var2) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.SET_WARNING_BLOCKS));
         }

         @Override
         public void func_177696_b(WorldBorder var1, double var2) {
         }

         @Override
         public void func_177695_c(WorldBorder var1, double var2) {
         }
      });
   }

   public void func_72375_a(EntityPlayerMP var1, @Nullable WorldServer var2) {
      WorldServer ☃ = ☃.func_71121_q();
      if (☃ != null) {
         ☃.func_184164_w().func_72695_c(☃);
      }

      ☃.func_184164_w().func_72683_a(☃);
      ☃.func_72863_F().func_186025_d((int)☃.field_70165_t >> 4, (int)☃.field_70161_v >> 4, true, true);
      if (☃ != null) {
         CriteriaTriggers.field_193134_u.func_193143_a(☃, ☃.field_73011_w.func_186058_p(), ☃.field_73011_w.func_186058_p());
         if (☃.field_73011_w.func_186058_p() == DimensionType.NETHER
            && ☃.field_70170_p.field_73011_w.func_186058_p() == DimensionType.OVERWORLD
            && ☃.func_193106_Q() != null) {
            CriteriaTriggers.field_193131_B.func_193168_a(☃, ☃.func_193106_Q());
         }
      }
   }

   public int func_72372_a() {
      return PlayerChunkMap.func_72686_a(this.func_72395_o());
   }

   @Nullable
   public NBTTagCompound func_72380_a(EntityPlayerMP var1) {
      NBTTagCompound ☃x = this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_72912_H().func_76072_h();
      NBTTagCompound ☃;
      if (☃.func_200200_C_().getString().equals(this.field_72400_f.func_71214_G()) && ☃x != null) {
         ☃ = ☃x;
         ☃.func_70020_e(☃x);
         field_148546_d.debug("loading single player");
      } else {
         ☃ = this.field_72412_k.func_75752_b(☃);
      }

      return ☃;
   }

   protected void func_72391_b(EntityPlayerMP var1) {
      this.field_72412_k.func_75753_a(☃);
      StatisticsManagerServer ☃ = (StatisticsManagerServer)this.field_148547_k.get(☃.func_110124_au());
      if (☃ != null) {
         ☃.func_150883_b();
      }

      PlayerAdvancements ☃ = (PlayerAdvancements)this.field_192055_p.get(☃.func_110124_au());
      if (☃ != null) {
         ☃.func_192749_b();
      }
   }

   public void func_72377_c(EntityPlayerMP var1) {
      this.field_72404_b.add(☃);
      this.field_177454_f.put(☃.func_110124_au(), ☃);
      this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, ☃));
      WorldServer ☃ = this.field_72400_f.func_71218_a(☃.field_71093_bK);

      for(int ☃x = 0; ☃x < this.field_72404_b.size(); ++☃x) {
         ☃.field_71135_a.func_147359_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, (EntityPlayerMP)this.field_72404_b.get(☃x)));
      }

      ☃.func_72838_d(☃);
      this.func_72375_a(☃, null);
      this.field_72400_f.func_201300_aS().func_201383_a(☃);
   }

   public void func_72358_d(EntityPlayerMP var1) {
      ☃.func_71121_q().func_184164_w().func_72685_d(☃);
   }

   public void func_72367_e(EntityPlayerMP var1) {
      WorldServer ☃ = ☃.func_71121_q();
      ☃.func_195066_a(StatList.field_75947_j);
      this.func_72391_b(☃);
      if (☃.func_184218_aH()) {
         Entity ☃x = ☃.func_184208_bv();
         if (☃x.func_200601_bK()) {
            field_148546_d.debug("Removing player mount");
            ☃.func_184210_p();
            ☃.func_72973_f(☃x);

            for(Entity ☃xx : ☃x.func_184182_bu()) {
               ☃.func_72973_f(☃xx);
            }

            ☃.func_72964_e(☃.field_70176_ah, ☃.field_70164_aj).func_76630_e();
         }
      }

      ☃.func_72900_e(☃);
      ☃.func_184164_w().func_72695_c(☃);
      ☃.func_192039_O().func_192745_a();
      this.field_72404_b.remove(☃);
      this.field_72400_f.func_201300_aS().func_201382_b(☃);
      UUID ☃ = ☃.func_110124_au();
      EntityPlayerMP ☃x = (EntityPlayerMP)this.field_177454_f.get(☃);
      if (☃x == ☃) {
         this.field_177454_f.remove(☃);
         this.field_148547_k.remove(☃);
         this.field_192055_p.remove(☃);
      }

      this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.REMOVE_PLAYER, ☃));
   }

   @Nullable
   public ITextComponent func_206258_a(SocketAddress var1, GameProfile var2) {
      if (this.field_72401_g.func_152702_a(☃)) {
         UserListBansEntry ☃ = this.field_72401_g.func_152683_b(☃);
         ITextComponent ☃x = new TextComponentTranslation("multiplayer.disconnect.banned.reason", ☃.func_73686_f());
         if (☃.func_73680_d() != null) {
            ☃x.func_150257_a(new TextComponentTranslation("multiplayer.disconnect.banned.expiration", field_72403_e.format(☃.func_73680_d())));
         }

         return ☃x;
      } else if (!this.func_152607_e(☃)) {
         return new TextComponentTranslation("multiplayer.disconnect.not_whitelisted");
      } else if (this.field_72413_h.func_152708_a(☃)) {
         UserListIPBansEntry ☃ = this.field_72413_h.func_152709_b(☃);
         ITextComponent ☃x = new TextComponentTranslation("multiplayer.disconnect.banned_ip.reason", ☃.func_73686_f());
         if (☃.func_73680_d() != null) {
            ☃x.func_150257_a(new TextComponentTranslation("multiplayer.disconnect.banned_ip.expiration", field_72403_e.format(☃.func_73680_d())));
         }

         return ☃x;
      } else {
         return this.field_72404_b.size() >= this.field_72405_c && !this.func_183023_f(☃)
            ? new TextComponentTranslation("multiplayer.disconnect.server_full")
            : null;
      }
   }

   public EntityPlayerMP func_148545_a(GameProfile var1) {
      UUID ☃ = EntityPlayer.func_146094_a(☃);
      List<EntityPlayerMP> ☃x = Lists.<EntityPlayerMP>newArrayList();

      for(int ☃xx = 0; ☃xx < this.field_72404_b.size(); ++☃xx) {
         EntityPlayerMP ☃xxx = (EntityPlayerMP)this.field_72404_b.get(☃xx);
         if (☃xxx.func_110124_au().equals(☃)) {
            ☃x.add(☃xxx);
         }
      }

      EntityPlayerMP ☃xx = (EntityPlayerMP)this.field_177454_f.get(☃.getId());
      if (☃xx != null && !☃x.contains(☃xx)) {
         ☃x.add(☃xx);
      }

      for(EntityPlayerMP ☃xx : ☃x) {
         ☃xx.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.duplicate_login"));
      }

      PlayerInteractionManager ☃xx;
      if (this.field_72400_f.func_71242_L()) {
         ☃xx = new DemoPlayerInteractionManager(this.field_72400_f.func_71218_a(DimensionType.OVERWORLD));
      } else {
         ☃xx = new PlayerInteractionManager(this.field_72400_f.func_71218_a(DimensionType.OVERWORLD));
      }

      return new EntityPlayerMP(this.field_72400_f, this.field_72400_f.func_71218_a(DimensionType.OVERWORLD), ☃, ☃xx);
   }

   public EntityPlayerMP func_72368_a(EntityPlayerMP var1, DimensionType var2, boolean var3) {
      ☃.func_71121_q().func_73039_n().func_72787_a(☃);
      ☃.func_71121_q().func_73039_n().func_72790_b(☃);
      ☃.func_71121_q().func_184164_w().func_72695_c(☃);
      this.field_72404_b.remove(☃);
      this.field_72400_f.func_71218_a(☃.field_71093_bK).func_72973_f(☃);
      BlockPos ☃x = ☃.func_180470_cg();
      boolean ☃xx = ☃.func_82245_bX();
      ☃.field_71093_bK = ☃;
      PlayerInteractionManager ☃;
      if (this.field_72400_f.func_71242_L()) {
         ☃ = new DemoPlayerInteractionManager(this.field_72400_f.func_71218_a(☃.field_71093_bK));
      } else {
         ☃ = new PlayerInteractionManager(this.field_72400_f.func_71218_a(☃.field_71093_bK));
      }

      EntityPlayerMP ☃ = new EntityPlayerMP(this.field_72400_f, this.field_72400_f.func_71218_a(☃.field_71093_bK), ☃.func_146103_bH(), ☃);
      ☃.field_71135_a = ☃.field_71135_a;
      ☃.func_193104_a(☃, ☃);
      ☃.func_145769_d(☃.func_145782_y());
      ☃.func_184819_a(☃.func_184591_cq());

      for(String ☃x : ☃.func_184216_O()) {
         ☃.func_184211_a(☃x);
      }

      WorldServer ☃x = this.field_72400_f.func_71218_a(☃.field_71093_bK);
      this.func_72381_a(☃, ☃, ☃x);
      if (☃x != null) {
         BlockPos ☃xx = EntityPlayer.func_180467_a(this.field_72400_f.func_71218_a(☃.field_71093_bK), ☃x, ☃xx);
         if (☃xx != null) {
            ☃.func_70012_b(
               (double)((float)☃xx.func_177958_n() + 0.5F),
               (double)((float)☃xx.func_177956_o() + 0.1F),
               (double)((float)☃xx.func_177952_p() + 0.5F),
               0.0F,
               0.0F
            );
            ☃.func_180473_a(☃x, ☃xx);
         } else {
            ☃.field_71135_a.func_147359_a(new SPacketChangeGameState(0, 0.0F));
         }
      }

      ☃x.func_72863_F().func_186025_d((int)☃.field_70165_t >> 4, (int)☃.field_70161_v >> 4, true, true);

      while(!☃x.func_195586_b(☃, ☃.func_174813_aQ()) && ☃.field_70163_u < 256.0) {
         ☃.func_70107_b(☃.field_70165_t, ☃.field_70163_u + 1.0, ☃.field_70161_v);
      }

      ☃.field_71135_a
         .func_147359_a(
            new SPacketRespawn(
               ☃.field_71093_bK, ☃.field_70170_p.func_175659_aa(), ☃.field_70170_p.func_72912_H().func_76067_t(), ☃.field_71134_c.func_73081_b()
            )
         );
      BlockPos ☃x = ☃x.func_175694_M();
      ☃.field_71135_a.func_147364_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
      ☃.field_71135_a.func_147359_a(new SPacketSpawnPosition(☃x));
      ☃.field_71135_a.func_147359_a(new SPacketSetExperience(☃.field_71106_cc, ☃.field_71067_cb, ☃.field_71068_ca));
      this.func_72354_b(☃, ☃x);
      this.func_187243_f(☃);
      ☃x.func_184164_w().func_72683_a(☃);
      ☃x.func_72838_d(☃);
      this.field_72404_b.add(☃);
      this.field_177454_f.put(☃.func_110124_au(), ☃);
      ☃.func_71116_b();
      ☃.func_70606_j(☃.func_110143_aJ());
      return ☃;
   }

   public void func_187243_f(EntityPlayerMP var1) {
      GameProfile ☃ = ☃.func_146103_bH();
      int ☃x = this.field_72400_f.func_211833_a(☃);
      this.func_187245_a(☃, ☃x);
   }

   public void func_187242_a(EntityPlayerMP var1, DimensionType var2) {
      DimensionType ☃ = ☃.field_71093_bK;
      WorldServer ☃x = this.field_72400_f.func_71218_a(☃.field_71093_bK);
      ☃.field_71093_bK = ☃;
      WorldServer ☃xx = this.field_72400_f.func_71218_a(☃.field_71093_bK);
      ☃.field_71135_a
         .func_147359_a(
            new SPacketRespawn(
               ☃.field_71093_bK, ☃.field_70170_p.func_175659_aa(), ☃.field_70170_p.func_72912_H().func_76067_t(), ☃.field_71134_c.func_73081_b()
            )
         );
      this.func_187243_f(☃);
      ☃x.func_72973_f(☃);
      ☃.field_70128_L = false;
      this.func_82448_a(☃, ☃, ☃x, ☃xx);
      this.func_72375_a(☃, ☃x);
      ☃.field_71135_a.func_147364_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
      ☃.field_71134_c.func_73080_a(☃xx);
      ☃.field_71135_a.func_147359_a(new SPacketPlayerAbilities(☃.field_71075_bZ));
      this.func_72354_b(☃, ☃xx);
      this.func_72385_f(☃);

      for(PotionEffect ☃xxx : ☃.func_70651_bq()) {
         ☃.field_71135_a.func_147359_a(new SPacketEntityEffect(☃.func_145782_y(), ☃xxx));
      }
   }

   public void func_82448_a(Entity var1, DimensionType var2, WorldServer var3, WorldServer var4) {
      double ☃ = ☃.field_70165_t;
      double ☃x = ☃.field_70161_v;
      double ☃xx = 8.0;
      float ☃xxx = ☃.field_70177_z;
      ☃.field_72984_F.func_76320_a("moving");
      if (☃.field_71093_bK == DimensionType.NETHER) {
         ☃ = MathHelper.func_151237_a(☃ / 8.0, ☃.func_175723_af().func_177726_b() + 16.0, ☃.func_175723_af().func_177728_d() - 16.0);
         ☃x = MathHelper.func_151237_a(☃x / 8.0, ☃.func_175723_af().func_177736_c() + 16.0, ☃.func_175723_af().func_177733_e() - 16.0);
         ☃.func_70012_b(☃, ☃.field_70163_u, ☃x, ☃.field_70177_z, ☃.field_70125_A);
         if (☃.func_70089_S()) {
            ☃.func_72866_a(☃, false);
         }
      } else if (☃.field_71093_bK == DimensionType.OVERWORLD) {
         ☃ = MathHelper.func_151237_a(☃ * 8.0, ☃.func_175723_af().func_177726_b() + 16.0, ☃.func_175723_af().func_177728_d() - 16.0);
         ☃x = MathHelper.func_151237_a(☃x * 8.0, ☃.func_175723_af().func_177736_c() + 16.0, ☃.func_175723_af().func_177733_e() - 16.0);
         ☃.func_70012_b(☃, ☃.field_70163_u, ☃x, ☃.field_70177_z, ☃.field_70125_A);
         if (☃.func_70089_S()) {
            ☃.func_72866_a(☃, false);
         }
      } else {
         BlockPos ☃;
         if (☃ == DimensionType.THE_END) {
            ☃ = ☃.func_175694_M();
         } else {
            ☃ = ☃.func_180504_m();
         }

         ☃ = (double)☃.func_177958_n();
         ☃.field_70163_u = (double)☃.func_177956_o();
         ☃x = (double)☃.func_177952_p();
         ☃.func_70012_b(☃, ☃.field_70163_u, ☃x, 90.0F, 0.0F);
         if (☃.func_70089_S()) {
            ☃.func_72866_a(☃, false);
         }
      }

      ☃.field_72984_F.func_76319_b();
      if (☃ != DimensionType.THE_END) {
         ☃.field_72984_F.func_76320_a("placing");
         ☃ = (double)MathHelper.func_76125_a((int)☃, -29999872, 29999872);
         ☃x = (double)MathHelper.func_76125_a((int)☃x, -29999872, 29999872);
         if (☃.func_70089_S()) {
            ☃.func_70012_b(☃, ☃.field_70163_u, ☃x, ☃.field_70177_z, ☃.field_70125_A);
            ☃.func_85176_s().func_180266_a(☃, ☃xxx);
            ☃.func_72838_d(☃);
            ☃.func_72866_a(☃, false);
         }

         ☃.field_72984_F.func_76319_b();
      }

      ☃.func_70029_a(☃);
   }

   public void func_72374_b() {
      if (++this.field_72408_o > 600) {
         this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_LATENCY, this.field_72404_b));
         this.field_72408_o = 0;
      }
   }

   public void func_148540_a(Packet<?> var1) {
      for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
         ((EntityPlayerMP)this.field_72404_b.get(☃)).field_71135_a.func_147359_a(☃);
      }
   }

   public void func_148537_a(Packet<?> var1, DimensionType var2) {
      for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
         EntityPlayerMP ☃x = (EntityPlayerMP)this.field_72404_b.get(☃);
         if (☃x.field_71093_bK == ☃) {
            ☃x.field_71135_a.func_147359_a(☃);
         }
      }
   }

   public void func_177453_a(EntityPlayer var1, ITextComponent var2) {
      Team ☃ = ☃.func_96124_cp();
      if (☃ != null) {
         for(String ☃x : ☃.func_96670_d()) {
            EntityPlayerMP ☃xx = this.func_152612_a(☃x);
            if (☃xx != null && ☃xx != ☃) {
               ☃xx.func_145747_a(☃);
            }
         }
      }
   }

   public void func_177452_b(EntityPlayer var1, ITextComponent var2) {
      Team ☃ = ☃.func_96124_cp();
      if (☃ == null) {
         this.func_148539_a(☃);
      } else {
         for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
            EntityPlayerMP ☃x = (EntityPlayerMP)this.field_72404_b.get(☃);
            if (☃x.func_96124_cp() != ☃) {
               ☃x.func_145747_a(☃);
            }
         }
      }
   }

   public String[] func_72369_d() {
      String[] ☃ = new String[this.field_72404_b.size()];

      for(int ☃x = 0; ☃x < this.field_72404_b.size(); ++☃x) {
         ☃[☃x] = ((EntityPlayerMP)this.field_72404_b.get(☃x)).func_146103_bH().getName();
      }

      return ☃;
   }

   public UserListBans func_152608_h() {
      return this.field_72401_g;
   }

   public UserListIPBans func_72363_f() {
      return this.field_72413_h;
   }

   public void func_152605_a(GameProfile var1) {
      this.field_72414_i.func_152687_a(new UserListOpsEntry(☃, this.field_72400_f.func_110455_j(), this.field_72414_i.func_183026_b(☃)));
      EntityPlayerMP ☃ = this.func_177451_a(☃.getId());
      if (☃ != null) {
         this.func_187243_f(☃);
      }
   }

   public void func_152610_b(GameProfile var1) {
      this.field_72414_i.func_152684_c(☃);
      EntityPlayerMP ☃ = this.func_177451_a(☃.getId());
      if (☃ != null) {
         this.func_187243_f(☃);
      }
   }

   private void func_187245_a(EntityPlayerMP var1, int var2) {
      if (☃.field_71135_a != null) {
         byte ☃;
         if (☃ <= 0) {
            ☃ = 24;
         } else if (☃ >= 4) {
            ☃ = 28;
         } else {
            ☃ = (byte)(24 + ☃);
         }

         ☃.field_71135_a.func_147359_a(new SPacketEntityStatus(☃, ☃));
      }

      this.field_72400_f.func_195571_aL().func_197051_a(☃);
   }

   public boolean func_152607_e(GameProfile var1) {
      return !this.field_72409_l || this.field_72414_i.func_152692_d(☃) || this.field_72411_j.func_152692_d(☃);
   }

   public boolean func_152596_g(GameProfile var1) {
      return this.field_72414_i.func_152692_d(☃)
         || this.field_72400_f.func_71264_H()
            && this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_72912_H().func_76086_u()
            && this.field_72400_f.func_71214_G().equalsIgnoreCase(☃.getName())
         || this.field_72407_n;
   }

   @Nullable
   public EntityPlayerMP func_152612_a(String var1) {
      for(EntityPlayerMP ☃ : this.field_72404_b) {
         if (☃.func_146103_bH().getName().equalsIgnoreCase(☃)) {
            return ☃;
         }
      }

      return null;
   }

   public void func_148543_a(@Nullable EntityPlayer var1, double var2, double var4, double var6, double var8, DimensionType var10, Packet<?> var11) {
      for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
         EntityPlayerMP ☃x = (EntityPlayerMP)this.field_72404_b.get(☃);
         if (☃x != ☃ && ☃x.field_71093_bK == ☃) {
            double ☃xx = ☃ - ☃x.field_70165_t;
            double ☃xxx = ☃ - ☃x.field_70163_u;
            double ☃xxxx = ☃ - ☃x.field_70161_v;
            if (☃xx * ☃xx + ☃xxx * ☃xxx + ☃xxxx * ☃xxxx < ☃ * ☃) {
               ☃x.field_71135_a.func_147359_a(☃);
            }
         }
      }
   }

   public void func_72389_g() {
      for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
         this.func_72391_b((EntityPlayerMP)this.field_72404_b.get(☃));
      }
   }

   public UserListWhitelist func_152599_k() {
      return this.field_72411_j;
   }

   public String[] func_152598_l() {
      return this.field_72411_j.func_152685_a();
   }

   public UserListOps func_152603_m() {
      return this.field_72414_i;
   }

   public String[] func_152606_n() {
      return this.field_72414_i.func_152685_a();
   }

   public void func_187244_a() {
   }

   public void func_72354_b(EntityPlayerMP var1, WorldServer var2) {
      WorldBorder ☃ = this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_175723_af();
      ☃.field_71135_a.func_147359_a(new SPacketWorldBorder(☃, SPacketWorldBorder.Action.INITIALIZE));
      ☃.field_71135_a.func_147359_a(new SPacketTimeUpdate(☃.func_82737_E(), ☃.func_72820_D(), ☃.func_82736_K().func_82766_b("doDaylightCycle")));
      BlockPos ☃x = ☃.func_175694_M();
      ☃.field_71135_a.func_147359_a(new SPacketSpawnPosition(☃x));
      if (☃.func_72896_J()) {
         ☃.field_71135_a.func_147359_a(new SPacketChangeGameState(1, 0.0F));
         ☃.field_71135_a.func_147359_a(new SPacketChangeGameState(7, ☃.func_72867_j(1.0F)));
         ☃.field_71135_a.func_147359_a(new SPacketChangeGameState(8, ☃.func_72819_i(1.0F)));
      }
   }

   public void func_72385_f(EntityPlayerMP var1) {
      ☃.func_71120_a(☃.field_71069_bz);
      ☃.func_71118_n();
      ☃.field_71135_a.func_147359_a(new SPacketHeldItemChange(☃.field_71071_by.field_70461_c));
   }

   public int func_72394_k() {
      return this.field_72404_b.size();
   }

   public int func_72352_l() {
      return this.field_72405_c;
   }

   public String[] func_72373_m() {
      return this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_72860_G().func_75756_e().func_75754_f();
   }

   public boolean func_72383_n() {
      return this.field_72409_l;
   }

   public void func_72371_a(boolean var1) {
      this.field_72409_l = ☃;
   }

   public List<EntityPlayerMP> func_72382_j(String var1) {
      List<EntityPlayerMP> ☃ = Lists.<EntityPlayerMP>newArrayList();

      for(EntityPlayerMP ☃x : this.field_72404_b) {
         if (☃x.func_71114_r().equals(☃)) {
            ☃.add(☃x);
         }
      }

      return ☃;
   }

   public int func_72395_o() {
      return this.field_72402_d;
   }

   public MinecraftServer func_72365_p() {
      return this.field_72400_f;
   }

   public NBTTagCompound func_72378_q() {
      return null;
   }

   public void func_152604_a(GameType var1) {
      this.field_72410_m = ☃;
   }

   private void func_72381_a(EntityPlayerMP var1, EntityPlayerMP var2, IWorld var3) {
      if (☃ != null) {
         ☃.field_71134_c.func_73076_a(☃.field_71134_c.func_73081_b());
      } else if (this.field_72410_m != null) {
         ☃.field_71134_c.func_73076_a(this.field_72410_m);
      }

      ☃.field_71134_c.func_73077_b(☃.func_72912_H().func_76077_q());
   }

   public void func_72387_b(boolean var1) {
      this.field_72407_n = ☃;
   }

   public void func_72392_r() {
      for(int ☃ = 0; ☃ < this.field_72404_b.size(); ++☃) {
         ((EntityPlayerMP)this.field_72404_b.get(☃)).field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.server_shutdown"));
      }
   }

   public void func_148544_a(ITextComponent var1, boolean var2) {
      this.field_72400_f.func_145747_a(☃);
      ChatType ☃ = ☃ ? ChatType.SYSTEM : ChatType.CHAT;
      this.func_148540_a(new SPacketChat(☃, ☃));
   }

   public void func_148539_a(ITextComponent var1) {
      this.func_148544_a(☃, true);
   }

   public StatisticsManagerServer func_152602_a(EntityPlayer var1) {
      UUID ☃ = ☃.func_110124_au();
      StatisticsManagerServer ☃x = ☃ == null ? null : (StatisticsManagerServer)this.field_148547_k.get(☃);
      if (☃x == null) {
         File ☃xx = new File(this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_72860_G().func_75765_b(), "stats");
         File ☃xxx = new File(☃xx, ☃ + ".json");
         if (!☃xxx.exists()) {
            File ☃xxxx = new File(☃xx, ☃.func_200200_C_().getString() + ".json");
            if (☃xxxx.exists() && ☃xxxx.isFile()) {
               ☃xxxx.renameTo(☃xxx);
            }
         }

         ☃x = new StatisticsManagerServer(this.field_72400_f, ☃xxx);
         this.field_148547_k.put(☃, ☃x);
      }

      return ☃x;
   }

   public PlayerAdvancements func_192054_h(EntityPlayerMP var1) {
      UUID ☃ = ☃.func_110124_au();
      PlayerAdvancements ☃x = (PlayerAdvancements)this.field_192055_p.get(☃);
      if (☃x == null) {
         File ☃xx = new File(this.field_72400_f.func_71218_a(DimensionType.OVERWORLD).func_72860_G().func_75765_b(), "advancements");
         File ☃xxx = new File(☃xx, ☃ + ".json");
         ☃x = new PlayerAdvancements(this.field_72400_f, ☃xxx, ☃);
         this.field_192055_p.put(☃, ☃x);
      }

      ☃x.func_192739_a(☃);
      return ☃x;
   }

   public void func_152611_a(int var1) {
      this.field_72402_d = ☃;

      for(WorldServer ☃ : this.field_72400_f.func_212370_w()) {
         if (☃ != null) {
            ☃.func_184164_w().func_152622_a(☃);
            ☃.func_73039_n().func_187252_a(☃);
         }
      }
   }

   public List<EntityPlayerMP> func_181057_v() {
      return this.field_72404_b;
   }

   @Nullable
   public EntityPlayerMP func_177451_a(UUID var1) {
      return (EntityPlayerMP)this.field_177454_f.get(☃);
   }

   public boolean func_183023_f(GameProfile var1) {
      return false;
   }

   public void func_193244_w() {
      for(PlayerAdvancements ☃ : this.field_192055_p.values()) {
         ☃.func_193766_b();
      }

      this.func_148540_a(new SPacketTagsList(this.field_72400_f.func_199731_aO()));
      SPacketUpdateRecipes ☃ = new SPacketUpdateRecipes(this.field_72400_f.func_199529_aN().func_199510_b());

      for(EntityPlayerMP ☃x : this.field_72404_b) {
         ☃x.field_71135_a.func_147359_a(☃);
         ☃x.func_192037_E().func_192826_c(☃x);
      }
   }

   public boolean func_206257_x() {
      return this.field_72407_n;
   }
}
