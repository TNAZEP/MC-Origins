package com.mojang.realmsclient.client;

import com.mojang.realmsclient.dto.BackupList;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.PendingInvitesList;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsDescriptionDto;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.dto.RealmsServerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.RealmsWorldResetDto;
import com.mojang.realmsclient.dto.ServerActivityList;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsHttpException;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsClient {
   public static RealmsClient.Environment currentEnvironment = RealmsClient.Environment.PRODUCTION;
   private static boolean initialized;
   private static final Logger LOGGER = LogManager.getLogger();
   private final String sessionId;
   private final String username;
   private final Minecraft minecraft;
   private static final String WORLDS_RESOURCE_PATH = "worlds";
   private static final String INVITES_RESOURCE_PATH = "invites";
   private static final String MCO_RESOURCE_PATH = "mco";
   private static final String SUBSCRIPTION_RESOURCE = "subscriptions";
   private static final String ACTIVITIES_RESOURCE = "activities";
   private static final String OPS_RESOURCE = "ops";
   private static final String REGIONS_RESOURCE = "regions/ping/stat";
   private static final String TRIALS_RESOURCE = "trial";
   private static final String PATH_INITIALIZE = "/$WORLD_ID/initialize";
   private static final String PATH_GET_ACTIVTIES = "/$WORLD_ID";
   private static final String PATH_GET_LIVESTATS = "/liveplayerlist";
   private static final String PATH_GET_SUBSCRIPTION = "/$WORLD_ID";
   private static final String PATH_OP = "/$WORLD_ID/$PROFILE_UUID";
   private static final String PATH_PUT_INTO_MINIGAMES_MODE = "/minigames/$MINIGAME_ID/$WORLD_ID";
   private static final String PATH_AVAILABLE = "/available";
   private static final String PATH_TEMPLATES = "/templates/$WORLD_TYPE";
   private static final String PATH_WORLD_JOIN = "/v1/$ID/join/pc";
   private static final String PATH_WORLD_GET = "/$ID";
   private static final String PATH_WORLD_INVITES = "/$WORLD_ID";
   private static final String PATH_WORLD_UNINVITE = "/$WORLD_ID/invite/$UUID";
   private static final String PATH_PENDING_INVITES_COUNT = "/count/pending";
   private static final String PATH_PENDING_INVITES = "/pending";
   private static final String PATH_ACCEPT_INVITE = "/accept/$INVITATION_ID";
   private static final String PATH_REJECT_INVITE = "/reject/$INVITATION_ID";
   private static final String PATH_UNINVITE_MYSELF = "/$WORLD_ID";
   private static final String PATH_WORLD_UPDATE = "/$WORLD_ID";
   private static final String PATH_SLOT = "/$WORLD_ID/slot/$SLOT_ID";
   private static final String PATH_WORLD_OPEN = "/$WORLD_ID/open";
   private static final String PATH_WORLD_CLOSE = "/$WORLD_ID/close";
   private static final String PATH_WORLD_RESET = "/$WORLD_ID/reset";
   private static final String PATH_DELETE_WORLD = "/$WORLD_ID";
   private static final String PATH_WORLD_BACKUPS = "/$WORLD_ID/backups";
   private static final String PATH_WORLD_DOWNLOAD = "/$WORLD_ID/slot/$SLOT_ID/download";
   private static final String PATH_WORLD_UPLOAD = "/$WORLD_ID/backups/upload";
   private static final String PATH_CLIENT_COMPATIBLE = "/client/compatible";
   private static final String PATH_TOS_AGREED = "/tos/agreed";
   private static final String PATH_NEWS = "/v1/news";
   private static final String PATH_STAGE_AVAILABLE = "/stageAvailable";
   private static final GuardedSerializer GSON = new GuardedSerializer();

   public static RealmsClient create() {
      Minecraft â˜ƒ = Minecraft.getInstance();
      String â˜ƒx = â˜ƒ.getUser().getName();
      String â˜ƒxx = â˜ƒ.getUser().getSessionId();
      if (!initialized) {
         initialized = true;
         String â˜ƒxxx = System.getenv("realms.environment");
         if (â˜ƒxxx == null) {
            â˜ƒxxx = System.getProperty("realms.environment");
         }

         if (â˜ƒxxx != null) {
            if ("LOCAL".equals(â˜ƒxxx)) {
               switchToLocal();
            } else if ("STAGE".equals(â˜ƒxxx)) {
               switchToStage();
            }
         }
      }

      return new RealmsClient(â˜ƒxx, â˜ƒx, â˜ƒ);
   }

   public static void switchToStage() {
      currentEnvironment = RealmsClient.Environment.STAGE;
   }

   public static void switchToProd() {
      currentEnvironment = RealmsClient.Environment.PRODUCTION;
   }

   public static void switchToLocal() {
      currentEnvironment = RealmsClient.Environment.LOCAL;
   }

   public RealmsClient(String var1, String var2, Minecraft var3) {
      this.sessionId = â˜ƒ;
      this.username = â˜ƒ;
      this.minecraft = â˜ƒ;
      RealmsClientConfig.setProxy(â˜ƒ.getProxy());
   }

   public RealmsServerList listWorlds() throws RealmsServiceException {
      String â˜ƒ = this.url("worlds");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return RealmsServerList.parse(â˜ƒx);
   }

   public RealmsServer getOwnWorld(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$ID".replace("$ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return RealmsServer.parse(â˜ƒx);
   }

   public ServerActivityList getActivity(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("activities" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return ServerActivityList.parse(â˜ƒx);
   }

   public RealmsServerPlayerLists getLiveStats() throws RealmsServiceException {
      String â˜ƒ = this.url("activities/liveplayerlist");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return RealmsServerPlayerLists.parse(â˜ƒx);
   }

   public RealmsServerAddress join(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/v1/$ID/join/pc".replace("$ID", â˜ƒ + ""));
      String â˜ƒx = this.execute(Request.get(â˜ƒ, 5000, 30000));
      return RealmsServerAddress.parse(â˜ƒx);
   }

   public void initializeWorld(long var1, String var3, String var4) throws RealmsServiceException {
      RealmsDescriptionDto â˜ƒ = new RealmsDescriptionDto(â˜ƒ, â˜ƒ);
      String â˜ƒx = this.url("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒxx = GSON.toJson(â˜ƒ);
      this.execute(Request.post(â˜ƒx, â˜ƒxx, 5000, 10000));
   }

   public Boolean mcoEnabled() throws RealmsServiceException {
      String â˜ƒ = this.url("mco/available");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return Boolean.valueOf(â˜ƒx);
   }

   public Boolean stageAvailable() throws RealmsServiceException {
      String â˜ƒ = this.url("mco/stageAvailable");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return Boolean.valueOf(â˜ƒx);
   }

   public RealmsClient.CompatibleVersionResponse clientCompatible() throws RealmsServiceException {
      String â˜ƒ = this.url("mco/client/compatible");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));

      try {
         return RealmsClient.CompatibleVersionResponse.valueOf(â˜ƒx);
      } catch (IllegalArgumentException var5) {
         throw new RealmsServiceException(500, "Could not check compatible version, got response: " + â˜ƒx, -1, "");
      }
   }

   public void uninvite(long var1, String var3) throws RealmsServiceException {
      String â˜ƒ = this.url("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$UUID", â˜ƒ));
      this.execute(Request.delete(â˜ƒ));
   }

   public void uninviteMyselfFrom(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      this.execute(Request.delete(â˜ƒ));
   }

   public RealmsServer invite(long var1, String var3) throws RealmsServiceException {
      PlayerInfo â˜ƒ = new PlayerInfo();
      â˜ƒ.setName(â˜ƒ);
      String â˜ƒx = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒxx = this.execute(Request.post(â˜ƒx, GSON.toJson(â˜ƒ)));
      return RealmsServer.parse(â˜ƒxx);
   }

   public BackupList backupsFor(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return BackupList.parse(â˜ƒx);
   }

   public void update(long var1, String var3, String var4) throws RealmsServiceException {
      RealmsDescriptionDto â˜ƒ = new RealmsDescriptionDto(â˜ƒ, â˜ƒ);
      String â˜ƒx = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      this.execute(Request.post(â˜ƒx, GSON.toJson(â˜ƒ)));
   }

   public void updateSlot(long var1, int var3, RealmsWorldOptions var4) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$SLOT_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = â˜ƒ.toJson();
      this.execute(Request.post(â˜ƒ, â˜ƒx));
   }

   public boolean switchSlot(long var1, int var3) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$SLOT_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.put(â˜ƒ, ""));
      return Boolean.valueOf(â˜ƒx);
   }

   public void restoreWorld(long var1, String var3) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(â˜ƒ)), "backupId=" + â˜ƒ);
      this.execute(Request.put(â˜ƒ, "", 40000, 600000));
   }

   public WorldTemplatePaginatedList fetchWorldTemplates(int var1, int var2, RealmsServer.WorldType var3) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", â˜ƒ.toString()), String.format("page=%d&pageSize=%d", â˜ƒ, â˜ƒ));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return WorldTemplatePaginatedList.parse(â˜ƒx);
   }

   public Boolean putIntoMinigameMode(long var1, String var3) throws RealmsServiceException {
      String â˜ƒ = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", â˜ƒ).replace("$WORLD_ID", String.valueOf(â˜ƒ));
      String â˜ƒx = this.url("worlds" + â˜ƒ);
      return Boolean.valueOf(this.execute(Request.put(â˜ƒx, "")));
   }

   public Ops op(long var1, String var3) throws RealmsServiceException {
      String â˜ƒ = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$PROFILE_UUID", â˜ƒ);
      String â˜ƒx = this.url("ops" + â˜ƒ);
      return Ops.parse(this.execute(Request.post(â˜ƒx, "")));
   }

   public Ops deop(long var1, String var3) throws RealmsServiceException {
      String â˜ƒ = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$PROFILE_UUID", â˜ƒ);
      String â˜ƒx = this.url("ops" + â˜ƒ);
      return Ops.parse(this.execute(Request.delete(â˜ƒx)));
   }

   public Boolean open(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.put(â˜ƒ, ""));
      return Boolean.valueOf(â˜ƒx);
   }

   public Boolean close(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.put(â˜ƒ, ""));
      return Boolean.valueOf(â˜ƒx);
   }

   public Boolean resetWorldWithSeed(long var1, WorldGenerationInfo var3) throws RealmsServiceException {
      RealmsWorldResetDto â˜ƒ = new RealmsWorldResetDto(â˜ƒ.getSeed(), -1L, â˜ƒ.getLevelType().getDtoIndex(), â˜ƒ.shouldGenerateStructures());
      String â˜ƒx = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒxx = this.execute(Request.post(â˜ƒx, GSON.toJson(â˜ƒ), 30000, 80000));
      return Boolean.valueOf(â˜ƒxx);
   }

   public Boolean resetWorldWithTemplate(long var1, String var3) throws RealmsServiceException {
      RealmsWorldResetDto â˜ƒ = new RealmsWorldResetDto(null, Long.valueOf(â˜ƒ), -1, false);
      String â˜ƒx = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒxx = this.execute(Request.post(â˜ƒx, GSON.toJson(â˜ƒ), 30000, 80000));
      return Boolean.valueOf(â˜ƒxx);
   }

   public Subscription subscriptionFor(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return Subscription.parse(â˜ƒx);
   }

   public int pendingInvitesCount() throws RealmsServiceException {
      return this.pendingInvites().pendingInvites.size();
   }

   public PendingInvitesList pendingInvites() throws RealmsServiceException {
      String â˜ƒ = this.url("invites/pending");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      PendingInvitesList â˜ƒxx = PendingInvitesList.parse(â˜ƒx);
      â˜ƒxx.pendingInvites.removeIf(this::isBlocked);
      return â˜ƒxx;
   }

   private boolean isBlocked(PendingInvite var1) {
      try {
         UUID â˜ƒ = UUID.fromString(â˜ƒ.worldOwnerUuid);
         return this.minecraft.getPlayerSocialManager().isBlocked(â˜ƒ);
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   public void acceptInvitation(String var1) throws RealmsServiceException {
      String â˜ƒ = this.url("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", â˜ƒ));
      this.execute(Request.put(â˜ƒ, ""));
   }

   public WorldDownload requestDownloadInfo(long var1, int var3) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(â˜ƒ)).replace("$SLOT_ID", String.valueOf(â˜ƒ)));
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return WorldDownload.parse(â˜ƒx);
   }

   @Nullable
   public UploadInfo requestUploadInfo(long var1, @Nullable String var3) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      return UploadInfo.parse(this.execute(Request.put(â˜ƒ, UploadInfo.createRequest(â˜ƒ))));
   }

   public void rejectInvitation(String var1) throws RealmsServiceException {
      String â˜ƒ = this.url("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", â˜ƒ));
      this.execute(Request.put(â˜ƒ, ""));
   }

   public void agreeToTos() throws RealmsServiceException {
      String â˜ƒ = this.url("mco/tos/agreed");
      this.execute(Request.post(â˜ƒ, ""));
   }

   public RealmsNews getNews() throws RealmsServiceException {
      String â˜ƒ = this.url("mco/v1/news");
      String â˜ƒx = this.execute(Request.get(â˜ƒ, 5000, 10000));
      return RealmsNews.parse(â˜ƒx);
   }

   public void sendPingResults(PingResult var1) throws RealmsServiceException {
      String â˜ƒ = this.url("regions/ping/stat");
      this.execute(Request.post(â˜ƒ, GSON.toJson(â˜ƒ)));
   }

   public Boolean trialAvailable() throws RealmsServiceException {
      String â˜ƒ = this.url("trial");
      String â˜ƒx = this.execute(Request.get(â˜ƒ));
      return Boolean.valueOf(â˜ƒx);
   }

   public void deleteWorld(long var1) throws RealmsServiceException {
      String â˜ƒ = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(â˜ƒ)));
      this.execute(Request.delete(â˜ƒ));
   }

   @Nullable
   private String url(String var1) {
      return this.url(â˜ƒ, null);
   }

   @Nullable
   private String url(String var1, @Nullable String var2) {
      try {
         return new URI(currentEnvironment.protocol, currentEnvironment.baseUrl, "/" + â˜ƒ, â˜ƒ, null).toASCIIString();
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private String execute(Request<?> var1) throws RealmsServiceException {
      â˜ƒ.cookie("sid", this.sessionId);
      â˜ƒ.cookie("user", this.username);
      â˜ƒ.cookie("version", SharedConstants.getCurrentVersion().getName());

      try {
         int â˜ƒ = â˜ƒ.responseCode();
         if (â˜ƒ != 503 && â˜ƒ != 277) {
            String â˜ƒx = â˜ƒ.text();
            if (â˜ƒ >= 200 && â˜ƒ < 300) {
               return â˜ƒx;
            } else if (â˜ƒ == 401) {
               String â˜ƒx = â˜ƒ.getHeader("WWW-Authenticate");
               LOGGER.info("Could not authorize you against Realms server: {}", â˜ƒx);
               throw new RealmsServiceException(â˜ƒ, â˜ƒx, -1, â˜ƒx);
            } else if (â˜ƒx != null && â˜ƒx.length() != 0) {
               RealmsError â˜ƒx = RealmsError.create(â˜ƒx);
               LOGGER.error("Realms http code: {} -  error code: {} -  message: {} - raw body: {}", â˜ƒ, â˜ƒx.getErrorCode(), â˜ƒx.getErrorMessage(), â˜ƒx);
               throw new RealmsServiceException(â˜ƒ, â˜ƒx, â˜ƒx);
            } else {
               LOGGER.error("Realms error code: {} message: {}", â˜ƒ, â˜ƒx);
               throw new RealmsServiceException(â˜ƒ, â˜ƒx, â˜ƒ, "");
            }
         } else {
            int â˜ƒ = â˜ƒ.getRetryAfterHeader();
            throw new RetryCallException(â˜ƒ, â˜ƒ);
         }
      } catch (RealmsHttpException var5) {
         throw new RealmsServiceException(500, "Could not connect to Realms: " + var5.getMessage(), -1, "");
      }
   }

   public static enum CompatibleVersionResponse {
      COMPATIBLE,
      OUTDATED,
      OTHER;
   }

   public static enum Environment {
      PRODUCTION("pc.realms.minecraft.net", "https"),
      STAGE("pc-stage.realms.minecraft.net", "https"),
      LOCAL("localhost:8080", "http");

      public String baseUrl;
      public String protocol;

      private Environment(String var3, String var4) {
         this.baseUrl = â˜ƒ;
         this.protocol = â˜ƒ;
      }
   }
}
