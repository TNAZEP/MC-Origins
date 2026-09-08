package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter {
   private static final Logger field_152732_e = LogManager.getLogger();
   public static final File field_152728_a = new File("banned-ips.txt");
   public static final File field_152729_b = new File("banned-players.txt");
   public static final File field_152730_c = new File("ops.txt");
   public static final File field_152731_d = new File("white-list.txt");

   static List<String> func_152721_a(File var0, Map<String, String[]> var1) throws IOException {
      List<String> ☃ = Files.readLines(☃, StandardCharsets.UTF_8);

      for(String ☃x : ☃) {
         ☃x = ☃x.trim();
         if (!☃x.startsWith("#") && ☃x.length() >= 1) {
            String[] ☃xx = ☃x.split("\\|");
            ☃.put(☃xx[0].toLowerCase(Locale.ROOT), ☃xx);
         }
      }

      return ☃;
   }

   private static void func_152717_a(MinecraftServer var0, Collection<String> var1, ProfileLookupCallback var2) {
      String[] ☃ = (String[])☃.stream().filter(var0x -> !StringUtils.func_151246_b(var0x)).toArray(var0x -> new String[var0x]);
      if (☃.func_71266_T()) {
         ☃.func_152359_aw().findProfilesByNames(☃, Agent.MINECRAFT, ☃);
      } else {
         for(String ☃ : ☃) {
            UUID ☃x = EntityPlayer.func_146094_a(new GameProfile(null, ☃));
            GameProfile ☃xx = new GameProfile(☃x, ☃);
            ☃.onProfileLookupSucceeded(☃xx);
         }
      }
   }

   public static boolean func_152724_a(final MinecraftServer var0) {
      final UserListBans ☃ = new UserListBans(PlayerList.field_152613_a);
      if (field_152729_b.exists() && field_152729_b.isFile()) {
         if (☃.func_152691_c().exists()) {
            try {
               ☃.func_152679_g();
            } catch (FileNotFoundException var6) {
               field_152732_e.warn("Could not load existing file {}", ☃.func_152691_c().getName(), var6);
            }
         }

         try {
            final Map<String, String[]> ☃x = Maps.newHashMap();
            func_152721_a(field_152729_b, ☃x);
            ProfileLookupCallback ☃xx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  ☃.func_152358_ax().func_152649_a(☃);
                  String[] ☃ = (String[])☃.get(☃.getName().toLowerCase(Locale.ROOT));
                  if (☃ == null) {
                     PreYggdrasilConverter.field_152732_e.warn("Could not convert user banlist entry for {}", ☃.getName());
                     throw new PreYggdrasilConverter.ConversionError("Profile not in the conversionlist");
                  } else {
                     Date ☃ = ☃.length > 1 ? PreYggdrasilConverter.func_152713_b(☃[1], null) : null;
                     String ☃x = ☃.length > 2 ? ☃[2] : null;
                     Date ☃xx = ☃.length > 3 ? PreYggdrasilConverter.func_152713_b(☃[3], null) : null;
                     String ☃xxx = ☃.length > 4 ? ☃[4] : null;
                     ☃.func_152687_a(new UserListBansEntry(☃, ☃, ☃x, ☃xx, ☃xxx));
                  }
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2x) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup user banlist entry for {}", ☃.getName(), ☃);
                  if (!(☃ instanceof ProfileNotFoundException)) {
                     throw new PreYggdrasilConverter.ConversionError("Could not request user " + ☃.getName() + " from backend systems", ☃);
                  }
               }
            };
            func_152717_a(☃, ☃x.keySet(), ☃xx);
            ☃.func_152678_f();
            func_152727_c(field_152729_b);
            return true;
         } catch (IOException var4) {
            field_152732_e.warn("Could not read old user banlist to convert it!", var4);
            return false;
         } catch (PreYggdrasilConverter.ConversionError var5) {
            field_152732_e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean func_152722_b(MinecraftServer var0) {
      UserListIPBans ☃ = new UserListIPBans(PlayerList.field_152614_b);
      if (field_152728_a.exists() && field_152728_a.isFile()) {
         if (☃.func_152691_c().exists()) {
            try {
               ☃.func_152679_g();
            } catch (FileNotFoundException var11) {
               field_152732_e.warn("Could not load existing file {}", ☃.func_152691_c().getName(), var11);
            }
         }

         try {
            Map<String, String[]> ☃x = Maps.newHashMap();
            func_152721_a(field_152728_a, ☃x);

            for(String ☃xx : ☃x.keySet()) {
               String[] ☃xxx = (String[])☃x.get(☃xx);
               Date ☃xxxx = ☃xxx.length > 1 ? func_152713_b(☃xxx[1], null) : null;
               String ☃xxxxx = ☃xxx.length > 2 ? ☃xxx[2] : null;
               Date ☃xxxxxx = ☃xxx.length > 3 ? func_152713_b(☃xxx[3], null) : null;
               String ☃xxxxxxx = ☃xxx.length > 4 ? ☃xxx[4] : null;
               ☃.func_152687_a(new UserListIPBansEntry(☃xx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx));
            }

            ☃.func_152678_f();
            func_152727_c(field_152728_a);
            return true;
         } catch (IOException var10) {
            field_152732_e.warn("Could not parse old ip banlist to convert it!", var10);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean func_152718_c(final MinecraftServer var0) {
      final UserListOps ☃ = new UserListOps(PlayerList.field_152615_c);
      if (field_152730_c.exists() && field_152730_c.isFile()) {
         if (☃.func_152691_c().exists()) {
            try {
               ☃.func_152679_g();
            } catch (FileNotFoundException var6) {
               field_152732_e.warn("Could not load existing file {}", ☃.func_152691_c().getName(), var6);
            }
         }

         try {
            List<String> ☃x = Files.readLines(field_152730_c, StandardCharsets.UTF_8);
            ProfileLookupCallback ☃xx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  ☃.func_152358_ax().func_152649_a(☃);
                  ☃.func_152687_a(new UserListOpsEntry(☃, ☃.func_110455_j(), false));
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup oplist entry for {}", ☃.getName(), ☃);
                  if (!(☃ instanceof ProfileNotFoundException)) {
                     throw new PreYggdrasilConverter.ConversionError("Could not request user " + ☃.getName() + " from backend systems", ☃);
                  }
               }
            };
            func_152717_a(☃, ☃x, ☃xx);
            ☃.func_152678_f();
            func_152727_c(field_152730_c);
            return true;
         } catch (IOException var4) {
            field_152732_e.warn("Could not read old oplist to convert it!", var4);
            return false;
         } catch (PreYggdrasilConverter.ConversionError var5) {
            field_152732_e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean func_152710_d(final MinecraftServer var0) {
      final UserListWhitelist ☃ = new UserListWhitelist(PlayerList.field_152616_d);
      if (field_152731_d.exists() && field_152731_d.isFile()) {
         if (☃.func_152691_c().exists()) {
            try {
               ☃.func_152679_g();
            } catch (FileNotFoundException var6) {
               field_152732_e.warn("Could not load existing file {}", ☃.func_152691_c().getName(), var6);
            }
         }

         try {
            List<String> ☃x = Files.readLines(field_152731_d, StandardCharsets.UTF_8);
            ProfileLookupCallback ☃xx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  ☃.func_152358_ax().func_152649_a(☃);
                  ☃.func_152687_a(new UserListWhitelistEntry(☃));
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup user whitelist entry for {}", ☃.getName(), ☃);
                  if (!(☃ instanceof ProfileNotFoundException)) {
                     throw new PreYggdrasilConverter.ConversionError("Could not request user " + ☃.getName() + " from backend systems", ☃);
                  }
               }
            };
            func_152717_a(☃, ☃x, ☃xx);
            ☃.func_152678_f();
            func_152727_c(field_152731_d);
            return true;
         } catch (IOException var4) {
            field_152732_e.warn("Could not read old whitelist to convert it!", var4);
            return false;
         } catch (PreYggdrasilConverter.ConversionError var5) {
            field_152732_e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static String func_187473_a(final MinecraftServer var0, String var1) {
      if (!StringUtils.func_151246_b(☃) && ☃.length() <= 16) {
         GameProfile ☃ = ☃.func_152358_ax().func_152655_a(☃);
         if (☃ != null && ☃.getId() != null) {
            return ☃.getId().toString();
         } else if (!☃.func_71264_H() && ☃.func_71266_T()) {
            final List<GameProfile> ☃ = Lists.<GameProfile>newArrayList();
            ProfileLookupCallback ☃x = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1) {
                  ☃.func_152358_ax().func_152649_a(☃);
                  ☃.add(☃);
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1, Exception var2) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup user whitelist entry for {}", ☃.getName(), ☃);
               }
            };
            func_152717_a(☃, Lists.newArrayList(☃), ☃x);
            return !☃.isEmpty() && ((GameProfile)☃.get(0)).getId() != null ? ((GameProfile)☃.get(0)).getId().toString() : "";
         } else {
            return EntityPlayer.func_146094_a(new GameProfile(null, ☃)).toString();
         }
      } else {
         return ☃;
      }
   }

   public static boolean func_152723_a(final DedicatedServer var0, PropertyManager var1) {
      final File ☃ = func_152725_d(☃);
      final File ☃x = new File(☃.getParentFile(), "playerdata");
      final File ☃xx = new File(☃.getParentFile(), "unknownplayers");
      if (☃.exists() && ☃.isDirectory()) {
         File[] ☃xxx = ☃.listFiles();
         List<String> ☃xxxx = Lists.newArrayList();

         for(File ☃xxxxx : ☃xxx) {
            String ☃xxxxxx = ☃xxxxx.getName();
            if (☃xxxxxx.toLowerCase(Locale.ROOT).endsWith(".dat")) {
               String ☃xxxxxxx = ☃xxxxxx.substring(0, ☃xxxxxx.length() - ".dat".length());
               if (!☃xxxxxxx.isEmpty()) {
                  ☃xxxx.add(☃xxxxxxx);
               }
            }
         }

         try {
            final String[] ☃xxxxx = (String[])☃xxxx.toArray(new String[☃xxxx.size()]);
            ProfileLookupCallback ☃xxxxxx = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile var1) {
                  ☃.func_152358_ax().func_152649_a(☃);
                  UUID ☃ = ☃.getId();
                  if (☃ == null) {
                     throw new PreYggdrasilConverter.ConversionError("Missing UUID for user profile " + ☃.getName());
                  } else {
                     this.func_152743_a(☃, this.func_152744_a(☃), ☃.toString());
                  }
               }

               @Override
               public void onProfileLookupFailed(GameProfile var1, Exception var2x) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup user uuid for {}", ☃.getName(), ☃);
                  if (☃ instanceof ProfileNotFoundException) {
                     String ☃ = this.func_152744_a(☃);
                     this.func_152743_a(☃, ☃, ☃);
                  } else {
                     throw new PreYggdrasilConverter.ConversionError("Could not request user " + ☃.getName() + " from backend systems", ☃);
                  }
               }

               private void func_152743_a(File var1, String var2x, String var3x) {
                  File ☃ = new File(☃, ☃ + ".dat");
                  File ☃x = new File(☃, ☃ + ".dat");
                  PreYggdrasilConverter.func_152711_b(☃);
                  if (!☃.renameTo(☃x)) {
                     throw new PreYggdrasilConverter.ConversionError("Could not convert file for " + ☃);
                  }
               }

               private String func_152744_a(GameProfile var1) {
                  String ☃ = null;

                  for(String ☃x : ☃) {
                     if (☃x != null && ☃x.equalsIgnoreCase(☃.getName())) {
                        ☃ = ☃x;
                        break;
                     }
                  }

                  if (☃ == null) {
                     throw new PreYggdrasilConverter.ConversionError("Could not find the filename for " + ☃.getName() + " anymore");
                  } else {
                     return ☃;
                  }
               }
            };
            func_152717_a(☃, Lists.newArrayList(☃xxxxx), ☃xxxxxx);
            return true;
         } catch (PreYggdrasilConverter.ConversionError var13) {
            field_152732_e.error("Conversion failed, please try again later", var13);
            return false;
         }
      } else {
         return true;
      }
   }

   private static void func_152711_b(File var0) {
      if (☃.exists()) {
         if (!☃.isDirectory()) {
            throw new PreYggdrasilConverter.ConversionError("Can't create directory " + ☃.getName() + " in world save directory.");
         }
      } else if (!☃.mkdirs()) {
         throw new PreYggdrasilConverter.ConversionError("Can't create directory " + ☃.getName() + " in world save directory.");
      }
   }

   public static boolean func_152714_a(PropertyManager var0) {
      boolean ☃ = func_152712_b(☃);
      return ☃ && func_152715_c(☃);
   }

   private static boolean func_152712_b(PropertyManager var0) {
      boolean ☃ = false;
      if (field_152729_b.exists() && field_152729_b.isFile()) {
         ☃ = true;
      }

      boolean ☃ = false;
      if (field_152728_a.exists() && field_152728_a.isFile()) {
         ☃ = true;
      }

      boolean ☃ = false;
      if (field_152730_c.exists() && field_152730_c.isFile()) {
         ☃ = true;
      }

      boolean ☃ = false;
      if (field_152731_d.exists() && field_152731_d.isFile()) {
         ☃ = true;
      }

      if (!☃ && !☃ && !☃ && !☃) {
         return true;
      } else {
         field_152732_e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         field_152732_e.warn("** please remove the following files and restart the server:");
         if (☃) {
            field_152732_e.warn("* {}", field_152729_b.getName());
         }

         if (☃) {
            field_152732_e.warn("* {}", field_152728_a.getName());
         }

         if (☃) {
            field_152732_e.warn("* {}", field_152730_c.getName());
         }

         if (☃) {
            field_152732_e.warn("* {}", field_152731_d.getName());
         }

         return false;
      }
   }

   private static boolean func_152715_c(PropertyManager var0) {
      File ☃ = func_152725_d(☃);
      if (!☃.exists() || !☃.isDirectory() || ☃.list().length <= 0 && ☃.delete()) {
         return true;
      } else {
         field_152732_e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         field_152732_e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         field_152732_e.warn("** please restart the server and if the problem persists, remove the directory '{}'", ☃.getPath());
         return false;
      }
   }

   private static File func_152725_d(PropertyManager var0) {
      String ☃ = ☃.func_73671_a("level-name", "world");
      File ☃x = new File(☃);
      return new File(☃x, "players");
   }

   private static void func_152727_c(File var0) {
      File ☃ = new File(☃.getName() + ".converted");
      ☃.renameTo(☃);
   }

   private static Date func_152713_b(String var0, Date var1) {
      Date ☃;
      try {
         ☃ = UserListEntryBan.field_73698_a.parse(☃);
      } catch (ParseException var4) {
         ☃ = ☃;
      }

      return ☃;
   }

   static class ConversionError extends RuntimeException {
      private ConversionError(String var1, Throwable var2) {
         super(☃, ☃);
      }

      private ConversionError(String var1) {
         super(☃);
      }
   }
}
