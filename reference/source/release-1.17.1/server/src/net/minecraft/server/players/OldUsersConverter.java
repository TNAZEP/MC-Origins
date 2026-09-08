package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldUsersConverter {
   static final Logger LOGGER = LogManager.getLogger();
   public static final File OLD_IPBANLIST = new File("banned-ips.txt");
   public static final File OLD_USERBANLIST = new File("banned-players.txt");
   public static final File OLD_OPLIST = new File("ops.txt");
   public static final File OLD_WHITELIST = new File("white-list.txt");

   static List<String> readOldListFormat(File var0, Map<String, String[]> var1) throws IOException {
      List<String> â˜ƒ = Files.readLines(â˜ƒ, StandardCharsets.UTF_8);

      for(String â˜ƒx : â˜ƒ) {
         â˜ƒx = â˜ƒx.trim();
         if (!â˜ƒx.startsWith("#") && â˜ƒx.length() >= 1) {
            String[] â˜ƒxx = â˜ƒx.split("\\|");
            â˜ƒ.put(â˜ƒxx[0].toLowerCase(Locale.ROOT), â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   private static void lookupPlayers(MinecraftServer var0, Collection<String> var1, ProfileLookupCallback var2) {
      String[] â˜ƒ = (String[])â˜ƒ.stream().filter(var0x -> !StringUtil.isNullOrEmpty(var0x)).toArray(var0x -> new String[var0x]);
      if (â˜ƒ.usesAuthentication()) {
         â˜ƒ.getProfileRepository().findProfilesByNames(â˜ƒ, Agent.MINECRAFT, â˜ƒ);
      } else {
         for(String â˜ƒ : â˜ƒ) {
            UUID â˜ƒx = Player.createPlayerUUID(new GameProfile(null, â˜ƒ));
            GameProfile â˜ƒxx = new GameProfile(â˜ƒx, â˜ƒ);
            â˜ƒ.onProfileLookupSucceeded(â˜ƒxx);
         }
      }
   }

   public static boolean convertUserBanlist(final MinecraftServer var0) {
      final UserBanList â˜ƒ = new UserBanList(PlayerList.USERBANLIST_FILE);
      if (OLD_USERBANLIST.exists() && OLD_USERBANLIST.isFile()) {
         if (â˜ƒ.getFile().exists()) {
            try {
               â˜ƒ.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", â˜ƒ.getFile().getName(), var6);
            }
         }

         try {
            final Map<String, String[]> â˜ƒx = Maps.newHashMap();
            readOldListFormat(OLD_USERBANLIST, â˜ƒx);
            ProfileLookupCallback â˜ƒxx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  â˜ƒ.getProfileCache().add(â˜ƒ);
                  String[] â˜ƒ = (String[])â˜ƒ.get(â˜ƒ.getName().toLowerCase(Locale.ROOT));
                  if (â˜ƒ == null) {
                     OldUsersConverter.LOGGER.warn("Could not convert user banlist entry for {}", â˜ƒ.getName());
                     throw new OldUsersConverter.ConversionError("Profile not in the conversionlist");
                  } else {
                     Date â˜ƒ = â˜ƒ.length > 1 ? OldUsersConverter.parseDate(â˜ƒ[1], null) : null;
                     String â˜ƒx = â˜ƒ.length > 2 ? â˜ƒ[2] : null;
                     Date â˜ƒxx = â˜ƒ.length > 3 ? OldUsersConverter.parseDate(â˜ƒ[3], null) : null;
                     String â˜ƒxxx = â˜ƒ.length > 4 ? â˜ƒ[4] : null;
                     â˜ƒ.add(new UserBanListEntry(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
                  }
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2x) {
                  OldUsersConverter.LOGGER.warn("Could not lookup user banlist entry for {}", â˜ƒ.getName(), â˜ƒ);
                  if (!(â˜ƒ instanceof ProfileNotFoundException)) {
                     throw new OldUsersConverter.ConversionError("Could not request user " + â˜ƒ.getName() + " from backend systems", â˜ƒ);
                  }
               }
            };
            lookupPlayers(â˜ƒ, â˜ƒx.keySet(), â˜ƒxx);
            â˜ƒ.save();
            renameOldFile(OLD_USERBANLIST);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old user banlist to convert it!", var4);
            return false;
         } catch (OldUsersConverter.ConversionError var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertIpBanlist(MinecraftServer var0) {
      IpBanList â˜ƒ = new IpBanList(PlayerList.IPBANLIST_FILE);
      if (OLD_IPBANLIST.exists() && OLD_IPBANLIST.isFile()) {
         if (â˜ƒ.getFile().exists()) {
            try {
               â˜ƒ.load();
            } catch (IOException var11) {
               LOGGER.warn("Could not load existing file {}", â˜ƒ.getFile().getName(), var11);
            }
         }

         try {
            Map<String, String[]> â˜ƒx = Maps.newHashMap();
            readOldListFormat(OLD_IPBANLIST, â˜ƒx);

            for(String â˜ƒxx : â˜ƒx.keySet()) {
               String[] â˜ƒxxx = (String[])â˜ƒx.get(â˜ƒxx);
               Date â˜ƒxxxx = â˜ƒxxx.length > 1 ? parseDate(â˜ƒxxx[1], null) : null;
               String â˜ƒxxxxx = â˜ƒxxx.length > 2 ? â˜ƒxxx[2] : null;
               Date â˜ƒxxxxxx = â˜ƒxxx.length > 3 ? parseDate(â˜ƒxxx[3], null) : null;
               String â˜ƒxxxxxxx = â˜ƒxxx.length > 4 ? â˜ƒxxx[4] : null;
               â˜ƒ.add(new IpBanListEntry(â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
            }

            â˜ƒ.save();
            renameOldFile(OLD_IPBANLIST);
            return true;
         } catch (IOException var10) {
            LOGGER.warn("Could not parse old ip banlist to convert it!", var10);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertOpsList(final MinecraftServer var0) {
      final ServerOpList â˜ƒ = new ServerOpList(PlayerList.OPLIST_FILE);
      if (OLD_OPLIST.exists() && OLD_OPLIST.isFile()) {
         if (â˜ƒ.getFile().exists()) {
            try {
               â˜ƒ.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", â˜ƒ.getFile().getName(), var6);
            }
         }

         try {
            List<String> â˜ƒx = Files.readLines(OLD_OPLIST, StandardCharsets.UTF_8);
            ProfileLookupCallback â˜ƒxx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  â˜ƒ.getProfileCache().add(â˜ƒ);
                  â˜ƒ.add(new ServerOpListEntry(â˜ƒ, â˜ƒ.getOperatorUserPermissionLevel(), false));
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  OldUsersConverter.LOGGER.warn("Could not lookup oplist entry for {}", â˜ƒ.getName(), â˜ƒ);
                  if (!(â˜ƒ instanceof ProfileNotFoundException)) {
                     throw new OldUsersConverter.ConversionError("Could not request user " + â˜ƒ.getName() + " from backend systems", â˜ƒ);
                  }
               }
            };
            lookupPlayers(â˜ƒ, â˜ƒx, â˜ƒxx);
            â˜ƒ.save();
            renameOldFile(OLD_OPLIST);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old oplist to convert it!", var4);
            return false;
         } catch (OldUsersConverter.ConversionError var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertWhiteList(final MinecraftServer var0) {
      final UserWhiteList â˜ƒ = new UserWhiteList(PlayerList.WHITELIST_FILE);
      if (OLD_WHITELIST.exists() && OLD_WHITELIST.isFile()) {
         if (â˜ƒ.getFile().exists()) {
            try {
               â˜ƒ.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", â˜ƒ.getFile().getName(), var6);
            }
         }

         try {
            List<String> â˜ƒx = Files.readLines(OLD_WHITELIST, StandardCharsets.UTF_8);
            ProfileLookupCallback â˜ƒxx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  â˜ƒ.getProfileCache().add(â˜ƒ);
                  â˜ƒ.add(new UserWhiteListEntry(â˜ƒ));
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  OldUsersConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", â˜ƒ.getName(), â˜ƒ);
                  if (!(â˜ƒ instanceof ProfileNotFoundException)) {
                     throw new OldUsersConverter.ConversionError("Could not request user " + â˜ƒ.getName() + " from backend systems", â˜ƒ);
                  }
               }
            };
            lookupPlayers(â˜ƒ, â˜ƒx, â˜ƒxx);
            â˜ƒ.save();
            renameOldFile(OLD_WHITELIST);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old whitelist to convert it!", var4);
            return false;
         } catch (OldUsersConverter.ConversionError var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   public static UUID convertMobOwnerIfNecessary(final MinecraftServer var0, String var1) {
      if (!StringUtil.isNullOrEmpty(â˜ƒ) && â˜ƒ.length() <= 16) {
         Optional<UUID> â˜ƒ = â˜ƒ.getProfileCache().get(â˜ƒ).map(GameProfile::getId);
         if (â˜ƒ.isPresent()) {
            return (UUID)â˜ƒ.get();
         } else if (!â˜ƒ.isSingleplayer() && â˜ƒ.usesAuthentication()) {
            final List<GameProfile> â˜ƒ = Lists.<GameProfile>newArrayList();
            ProfileLookupCallback â˜ƒx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1) {
                  â˜ƒ.getProfileCache().add(â˜ƒ);
                  â˜ƒ.add(â˜ƒ);
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1, Exception var2) {
                  OldUsersConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", â˜ƒ.getName(), â˜ƒ);
               }
            };
            lookupPlayers(â˜ƒ, Lists.newArrayList(â˜ƒ), â˜ƒx);
            return !â˜ƒ.isEmpty() && ((GameProfile)â˜ƒ.get(0)).getId() != null ? ((GameProfile)â˜ƒ.get(0)).getId() : null;
         } else {
            return Player.createPlayerUUID(new GameProfile(null, â˜ƒ));
         }
      } else {
         try {
            return UUID.fromString(â˜ƒ);
         } catch (IllegalArgumentException var5) {
            return null;
         }
      }
   }

   public static boolean convertPlayers(final DedicatedServer var0) {
      final File â˜ƒ = getWorldPlayersDirectory(â˜ƒ);
      final File â˜ƒx = new File(â˜ƒ.getParentFile(), "playerdata");
      final File â˜ƒxx = new File(â˜ƒ.getParentFile(), "unknownplayers");
      if (â˜ƒ.exists() && â˜ƒ.isDirectory()) {
         File[] â˜ƒxxx = â˜ƒ.listFiles();
         List<String> â˜ƒxxxx = Lists.newArrayList();

         for(File â˜ƒxxxxx : â˜ƒxxx) {
            String â˜ƒxxxxxx = â˜ƒxxxxx.getName();
            if (â˜ƒxxxxxx.toLowerCase(Locale.ROOT).endsWith(".dat")) {
               String â˜ƒxxxxxxx = â˜ƒxxxxxx.substring(0, â˜ƒxxxxxx.length() - ".dat".length());
               if (!â˜ƒxxxxxxx.isEmpty()) {
                  â˜ƒxxxx.add(â˜ƒxxxxxxx);
               }
            }
         }

         try {
            final String[] â˜ƒxxxxx = (String[])â˜ƒxxxx.toArray(new String[â˜ƒxxxx.size()]);
            ProfileLookupCallback â˜ƒxxxxxx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  â˜ƒ.getProfileCache().add(â˜ƒ);
                  UUID â˜ƒ = â˜ƒ.getId();
                  if (â˜ƒ == null) {
                     throw new OldUsersConverter.ConversionError("Missing UUID for user profile " + â˜ƒ.getName());
                  } else {
                     this.movePlayerFile(â˜ƒ, this.getFileNameForProfile(â˜ƒ), â˜ƒ.toString());
                  }
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2x) {
                  OldUsersConverter.LOGGER.warn("Could not lookup user uuid for {}", â˜ƒ.getName(), â˜ƒ);
                  if (â˜ƒ instanceof ProfileNotFoundException) {
                     String â˜ƒ = this.getFileNameForProfile(â˜ƒ);
                     this.movePlayerFile(â˜ƒ, â˜ƒ, â˜ƒ);
                  } else {
                     throw new OldUsersConverter.ConversionError("Could not request user " + â˜ƒ.getName() + " from backend systems", â˜ƒ);
                  }
               }

               private void movePlayerFile(File var1x, String var2x, String var3x) {
                  File â˜ƒ = new File(â˜ƒ, â˜ƒ + ".dat");
                  File â˜ƒx = new File(â˜ƒ, â˜ƒ + ".dat");
                  OldUsersConverter.ensureDirectoryExists(â˜ƒ);
                  if (!â˜ƒ.renameTo(â˜ƒx)) {
                     throw new OldUsersConverter.ConversionError("Could not convert file for " + â˜ƒ);
                  }
               }

               private String getFileNameForProfile(GameProfile var1x) {
                  String â˜ƒ = null;

                  for(String â˜ƒx : â˜ƒ) {
                     if (â˜ƒx != null && â˜ƒx.equalsIgnoreCase(â˜ƒ.getName())) {
                        â˜ƒ = â˜ƒx;
                        break;
                     }
                  }

                  if (â˜ƒ == null) {
                     throw new OldUsersConverter.ConversionError("Could not find the filename for " + â˜ƒ.getName() + " anymore");
                  } else {
                     return â˜ƒ;
                  }
               }
            };
            lookupPlayers(â˜ƒ, Lists.newArrayList(â˜ƒxxxxx), â˜ƒxxxxxx);
            return true;
         } catch (OldUsersConverter.ConversionError var12) {
            LOGGER.error("Conversion failed, please try again later", var12);
            return false;
         }
      } else {
         return true;
      }
   }

   static void ensureDirectoryExists(File var0) {
      if (â˜ƒ.exists()) {
         if (!â˜ƒ.isDirectory()) {
            throw new OldUsersConverter.ConversionError("Can't create directory " + â˜ƒ.getName() + " in world save directory.");
         }
      } else if (!â˜ƒ.mkdirs()) {
         throw new OldUsersConverter.ConversionError("Can't create directory " + â˜ƒ.getName() + " in world save directory.");
      }
   }

   public static boolean serverReadyAfterUserconversion(MinecraftServer var0) {
      boolean â˜ƒ = areOldUserlistsRemoved();
      return â˜ƒ && areOldPlayersConverted(â˜ƒ);
   }

   private static boolean areOldUserlistsRemoved() {
      boolean â˜ƒ = false;
      if (OLD_USERBANLIST.exists() && OLD_USERBANLIST.isFile()) {
         â˜ƒ = true;
      }

      boolean â˜ƒ = false;
      if (OLD_IPBANLIST.exists() && OLD_IPBANLIST.isFile()) {
         â˜ƒ = true;
      }

      boolean â˜ƒ = false;
      if (OLD_OPLIST.exists() && OLD_OPLIST.isFile()) {
         â˜ƒ = true;
      }

      boolean â˜ƒ = false;
      if (OLD_WHITELIST.exists() && OLD_WHITELIST.isFile()) {
         â˜ƒ = true;
      }

      if (!â˜ƒ && !â˜ƒ && !â˜ƒ && !â˜ƒ) {
         return true;
      } else {
         LOGGER.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         LOGGER.warn("** please remove the following files and restart the server:");
         if (â˜ƒ) {
            LOGGER.warn("* {}", OLD_USERBANLIST.getName());
         }

         if (â˜ƒ) {
            LOGGER.warn("* {}", OLD_IPBANLIST.getName());
         }

         if (â˜ƒ) {
            LOGGER.warn("* {}", OLD_OPLIST.getName());
         }

         if (â˜ƒ) {
            LOGGER.warn("* {}", OLD_WHITELIST.getName());
         }

         return false;
      }
   }

   private static boolean areOldPlayersConverted(MinecraftServer var0) {
      File â˜ƒ = getWorldPlayersDirectory(â˜ƒ);
      if (!â˜ƒ.exists() || !â˜ƒ.isDirectory() || â˜ƒ.list().length <= 0 && â˜ƒ.delete()) {
         return true;
      } else {
         LOGGER.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         LOGGER.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         LOGGER.warn("** please restart the server and if the problem persists, remove the directory '{}'", â˜ƒ.getPath());
         return false;
      }
   }

   private static File getWorldPlayersDirectory(MinecraftServer var0) {
      return â˜ƒ.getWorldPath(LevelResource.PLAYER_OLD_DATA_DIR).toFile();
   }

   private static void renameOldFile(File var0) {
      File â˜ƒ = new File(â˜ƒ.getName() + ".converted");
      â˜ƒ.renameTo(â˜ƒ);
   }

   static Date parseDate(String var0, Date var1) {
      Date â˜ƒ;
      try {
         â˜ƒ = BanListEntry.DATE_FORMAT.parse(â˜ƒ);
      } catch (ParseException var4) {
         â˜ƒ = â˜ƒ;
      }

      return â˜ƒ;
   }

   static class ConversionError extends RuntimeException {
      ConversionError(String var1, Throwable var2) {
         super(â˜ƒ, â˜ƒ);
      }

      ConversionError(String var1) {
         super(â˜ƒ);
      }
   }
}
