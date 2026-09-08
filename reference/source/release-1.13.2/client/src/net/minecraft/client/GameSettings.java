package net.minecraft.client;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.mojang.datafixers.DataFixTypes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.renderer.VideoMode;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackInfoClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameSettings {
   private static final Logger field_151454_ax = LogManager.getLogger();
   private static final Gson field_151450_ay = new Gson();
   private static final Type field_151449_az = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   public static final Splitter field_189990_a = Splitter.on(':');
   private static final String[] field_74364_ag = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] field_98303_au = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   private static final String[] field_181149_aW = new String[]{"options.off", "options.clouds.fast", "options.clouds.fancy"};
   private static final String[] field_186713_aK = new String[]{"options.off", "options.attack.crosshair", "options.attack.hotbar"};
   public static final String[] field_193632_b = new String[]{
      "options.narrator.off", "options.narrator.all", "options.narrator.chat", "options.narrator.system"
   };
   public double field_74341_c = 0.5;
   public boolean field_74338_d;
   public int field_151451_c = -1;
   public boolean field_74336_f = true;
   public boolean field_151448_g = true;
   public int field_74350_i = 120;
   public int field_74345_l = 2;
   public boolean field_74347_j = true;
   public int field_74348_k = 2;
   public List<String> field_151453_l = Lists.newArrayList();
   public List<String> field_183018_l = Lists.newArrayList();
   public EntityPlayer.EnumChatVisibility field_74343_n = EntityPlayer.EnumChatVisibility.FULL;
   public boolean field_74344_o = true;
   public boolean field_74359_p = true;
   public boolean field_74358_q = true;
   public double field_74357_r = 1.0;
   public boolean field_74355_t = true;
   public boolean field_74353_u;
   @Nullable
   public String field_198019_u;
   public boolean field_74352_v = true;
   public boolean field_178881_t = true;
   public boolean field_178879_v;
   public boolean field_80005_w;
   public boolean field_82882_x;
   public boolean field_82881_y = true;
   private final Set<EnumPlayerModelParts> field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
   public boolean field_85185_A;
   public EnumHandSide field_186715_A = EnumHandSide.RIGHT;
   public int field_92118_B;
   public int field_92119_C;
   public boolean field_92117_D = true;
   public double field_96691_E = 1.0;
   public double field_96692_F = 1.0;
   public double field_96693_G = 0.44366196F;
   public double field_96694_H = 1.0;
   public int field_151442_I = 4;
   private final Map<SoundCategory, Float> field_186714_aM = Maps.newEnumMap(SoundCategory.class);
   public boolean field_181150_U = true;
   public boolean field_181151_V = true;
   public int field_186716_M = 1;
   public boolean field_189422_N;
   public boolean field_186717_N;
   public boolean field_183509_X = true;
   public boolean field_189989_R = true;
   public TutorialSteps field_193631_S = TutorialSteps.MOVEMENT;
   public boolean field_198018_T = true;
   public int field_205217_U = 2;
   public double field_208033_V = 1.0;
   public int field_209231_W = 1;
   public KeyBinding field_74351_w = new KeyBinding("key.forward", 87, "key.categories.movement");
   public KeyBinding field_74370_x = new KeyBinding("key.left", 65, "key.categories.movement");
   public KeyBinding field_74368_y = new KeyBinding("key.back", 83, "key.categories.movement");
   public KeyBinding field_74366_z = new KeyBinding("key.right", 68, "key.categories.movement");
   public KeyBinding field_74314_A = new KeyBinding("key.jump", 32, "key.categories.movement");
   public KeyBinding field_74311_E = new KeyBinding("key.sneak", 340, "key.categories.movement");
   public KeyBinding field_151444_V = new KeyBinding("key.sprint", 341, "key.categories.movement");
   public KeyBinding field_151445_Q = new KeyBinding("key.inventory", 69, "key.categories.inventory");
   public KeyBinding field_186718_X = new KeyBinding("key.swapHands", 70, "key.categories.inventory");
   public KeyBinding field_74316_C = new KeyBinding("key.drop", 81, "key.categories.inventory");
   public KeyBinding field_74313_G = new KeyBinding("key.use", InputMappings.Type.MOUSE, 1, "key.categories.gameplay");
   public KeyBinding field_74312_F = new KeyBinding("key.attack", InputMappings.Type.MOUSE, 0, "key.categories.gameplay");
   public KeyBinding field_74322_I = new KeyBinding("key.pickItem", InputMappings.Type.MOUSE, 2, "key.categories.gameplay");
   public KeyBinding field_74310_D = new KeyBinding("key.chat", 84, "key.categories.multiplayer");
   public KeyBinding field_74321_H = new KeyBinding("key.playerlist", 258, "key.categories.multiplayer");
   public KeyBinding field_74323_J = new KeyBinding("key.command", 47, "key.categories.multiplayer");
   public KeyBinding field_151447_Z = new KeyBinding("key.screenshot", 291, "key.categories.misc");
   public KeyBinding field_151457_aa = new KeyBinding("key.togglePerspective", 294, "key.categories.misc");
   public KeyBinding field_151458_ab = new KeyBinding("key.smoothCamera", -1, "key.categories.misc");
   public KeyBinding field_152395_am = new KeyBinding("key.fullscreen", 300, "key.categories.misc");
   public KeyBinding field_178883_an = new KeyBinding("key.spectatorOutlines", -1, "key.categories.misc");
   public KeyBinding field_194146_ao = new KeyBinding("key.advancements", 76, "key.categories.misc");
   public KeyBinding[] field_151456_ac = new KeyBinding[]{
      new KeyBinding("key.hotbar.1", 49, "key.categories.inventory"),
      new KeyBinding("key.hotbar.2", 50, "key.categories.inventory"),
      new KeyBinding("key.hotbar.3", 51, "key.categories.inventory"),
      new KeyBinding("key.hotbar.4", 52, "key.categories.inventory"),
      new KeyBinding("key.hotbar.5", 53, "key.categories.inventory"),
      new KeyBinding("key.hotbar.6", 54, "key.categories.inventory"),
      new KeyBinding("key.hotbar.7", 55, "key.categories.inventory"),
      new KeyBinding("key.hotbar.8", 56, "key.categories.inventory"),
      new KeyBinding("key.hotbar.9", 57, "key.categories.inventory")
   };
   public KeyBinding field_193629_ap = new KeyBinding("key.saveToolbarActivator", 67, "key.categories.creative");
   public KeyBinding field_193630_aq = new KeyBinding("key.loadToolbarActivator", 88, "key.categories.creative");
   public KeyBinding[] field_74324_K = ArrayUtils.addAll(
      new KeyBinding[]{
         this.field_74312_F,
         this.field_74313_G,
         this.field_74351_w,
         this.field_74370_x,
         this.field_74368_y,
         this.field_74366_z,
         this.field_74314_A,
         this.field_74311_E,
         this.field_151444_V,
         this.field_74316_C,
         this.field_151445_Q,
         this.field_74310_D,
         this.field_74321_H,
         this.field_74322_I,
         this.field_74323_J,
         this.field_151447_Z,
         this.field_151457_aa,
         this.field_151458_ab,
         this.field_152395_am,
         this.field_178883_an,
         this.field_186718_X,
         this.field_193629_ap,
         this.field_193630_aq,
         this.field_194146_ao
      },
      this.field_151456_ac
   );
   protected Minecraft field_74317_L;
   private File field_74354_ai;
   public EnumDifficulty field_74318_M = EnumDifficulty.NORMAL;
   public boolean field_74319_N;
   public int field_74320_O;
   public boolean field_74330_P;
   public boolean field_74329_Q;
   public boolean field_181657_aC;
   public String field_74332_R = "";
   public boolean field_74326_T;
   public boolean field_74325_U;
   public double field_74334_X = 70.0;
   public double field_74333_Y;
   public float field_151452_as;
   public int field_74335_Z;
   public int field_74362_aa;
   public int field_192571_R;
   public String field_74363_ab = "en_us";
   public boolean field_211842_aO;

   public GameSettings(Minecraft var1, File var2) {
      this.field_74317_L = ☃;
      this.field_74354_ai = new File(☃, "options.txt");
      if (☃.func_147111_S() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         GameSettings.Options.RENDER_DISTANCE.func_148263_a(32.0F);
      } else {
         GameSettings.Options.RENDER_DISTANCE.func_148263_a(16.0F);
      }

      this.field_151451_c = ☃.func_147111_S() ? 12 : 8;
      this.func_74300_a();
   }

   public GameSettings() {
   }

   public void func_198014_a(KeyBinding var1, InputMappings.Input var2) {
      ☃.func_197979_b(☃);
      this.func_74303_b();
   }

   public void func_198016_a(GameSettings.Options var1, double var2) {
      if (☃ == GameSettings.Options.SENSITIVITY) {
         this.field_74341_c = ☃;
      }

      if (☃ == GameSettings.Options.FOV) {
         this.field_74334_X = ☃;
      }

      if (☃ == GameSettings.Options.GAMMA) {
         this.field_74333_Y = ☃;
      }

      if (☃ == GameSettings.Options.FRAMERATE_LIMIT) {
         this.field_74350_i = (int)☃;
      }

      if (☃ == GameSettings.Options.CHAT_OPACITY) {
         this.field_74357_r = ☃;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (☃ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         this.field_96694_H = ☃;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (☃ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         this.field_96693_G = ☃;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (☃ == GameSettings.Options.CHAT_WIDTH) {
         this.field_96692_F = ☃;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (☃ == GameSettings.Options.CHAT_SCALE) {
         this.field_96691_E = ☃;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (☃ == GameSettings.Options.MIPMAP_LEVELS) {
         int ☃ = this.field_151442_I;
         this.field_151442_I = (int)☃;
         if ((double)☃ != ☃) {
            this.field_74317_L.func_147117_R().func_147633_a(this.field_151442_I);
            this.field_74317_L.func_110434_K().func_110577_a(TextureMap.field_110575_b);
            this.field_74317_L.func_147117_R().func_174937_a(false, this.field_151442_I > 0);
            this.field_74317_L.func_175603_A();
         }
      }

      if (☃ == GameSettings.Options.RENDER_DISTANCE) {
         this.field_151451_c = (int)☃;
         this.field_74317_L.field_71438_f.func_174979_m();
      }

      if (☃ == GameSettings.Options.BIOME_BLEND_RADIUS) {
         this.field_205217_U = MathHelper.func_76125_a((int)☃, 0, 7);
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (☃ == GameSettings.Options.FULLSCREEN_RESOLUTION) {
         this.field_74317_L.field_195558_d.func_198104_b((int)☃);
      }

      if (☃ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY) {
         this.field_208033_V = ☃;
      }
   }

   public void func_74306_a(GameSettings.Options var1, int var2) {
      if (☃ == GameSettings.Options.RENDER_DISTANCE) {
         this.func_198016_a(☃, MathHelper.func_151237_a((double)(this.field_151451_c + ☃), ☃.func_198007_e(), ☃.func_198009_f()));
      }

      if (☃ == GameSettings.Options.MAIN_HAND) {
         this.field_186715_A = this.field_186715_A.func_188468_a();
      }

      if (☃ == GameSettings.Options.INVERT_MOUSE) {
         this.field_74338_d = !this.field_74338_d;
      }

      if (☃ == GameSettings.Options.GUI_SCALE) {
         this.field_74335_Z = Integer.remainderUnsigned(this.field_74335_Z + ☃, this.field_74317_L.field_195558_d.func_198078_c(0) + 1);
      }

      if (☃ == GameSettings.Options.PARTICLES) {
         this.field_74362_aa = (this.field_74362_aa + ☃) % 3;
      }

      if (☃ == GameSettings.Options.VIEW_BOBBING) {
         this.field_74336_f = !this.field_74336_f;
      }

      if (☃ == GameSettings.Options.RENDER_CLOUDS) {
         this.field_74345_l = (this.field_74345_l + ☃) % 3;
      }

      if (☃ == GameSettings.Options.FORCE_UNICODE_FONT) {
         this.field_211842_aO = !this.field_211842_aO;
         this.field_74317_L.func_211500_ak().func_211825_a(this.field_211842_aO);
      }

      if (☃ == GameSettings.Options.FBO_ENABLE) {
         this.field_151448_g = !this.field_151448_g;
      }

      if (☃ == GameSettings.Options.GRAPHICS) {
         this.field_74347_j = !this.field_74347_j;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (☃ == GameSettings.Options.AMBIENT_OCCLUSION) {
         this.field_74348_k = (this.field_74348_k + ☃) % 3;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (☃ == GameSettings.Options.CHAT_VISIBILITY) {
         this.field_74343_n = EntityPlayer.EnumChatVisibility.func_151426_a((this.field_74343_n.func_151428_a() + ☃) % 3);
      }

      if (☃ == GameSettings.Options.CHAT_COLOR) {
         this.field_74344_o = !this.field_74344_o;
      }

      if (☃ == GameSettings.Options.CHAT_LINKS) {
         this.field_74359_p = !this.field_74359_p;
      }

      if (☃ == GameSettings.Options.CHAT_LINKS_PROMPT) {
         this.field_74358_q = !this.field_74358_q;
      }

      if (☃ == GameSettings.Options.SNOOPER_ENABLED) {
         this.field_74355_t = !this.field_74355_t;
      }

      if (☃ == GameSettings.Options.TOUCHSCREEN) {
         this.field_85185_A = !this.field_85185_A;
      }

      if (☃ == GameSettings.Options.USE_FULLSCREEN) {
         this.field_74353_u = !this.field_74353_u;
         if (this.field_74317_L.field_195558_d.func_198113_j() != this.field_74353_u) {
            this.field_74317_L.field_195558_d.func_198077_g();
         }
      }

      if (☃ == GameSettings.Options.ENABLE_VSYNC) {
         this.field_74352_v = !this.field_74352_v;
         this.field_74317_L.field_195558_d.func_209548_c();
      }

      if (☃ == GameSettings.Options.USE_VBO) {
         this.field_178881_t = !this.field_178881_t;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (☃ == GameSettings.Options.REDUCED_DEBUG_INFO) {
         this.field_178879_v = !this.field_178879_v;
      }

      if (☃ == GameSettings.Options.ENTITY_SHADOWS) {
         this.field_181151_V = !this.field_181151_V;
      }

      if (☃ == GameSettings.Options.ATTACK_INDICATOR) {
         this.field_186716_M = (this.field_186716_M + ☃) % 3;
      }

      if (☃ == GameSettings.Options.SHOW_SUBTITLES) {
         this.field_186717_N = !this.field_186717_N;
      }

      if (☃ == GameSettings.Options.REALMS_NOTIFICATIONS) {
         this.field_183509_X = !this.field_183509_X;
      }

      if (☃ == GameSettings.Options.AUTO_JUMP) {
         this.field_189989_R = !this.field_189989_R;
      }

      if (☃ == GameSettings.Options.AUTO_SUGGESTIONS) {
         this.field_198018_T = !this.field_198018_T;
      }

      if (☃ == GameSettings.Options.NARRATOR) {
         if (NarratorChatListener.field_193643_a.func_193640_a()) {
            this.field_192571_R = (this.field_192571_R + ☃) % field_193632_b.length;
         } else {
            this.field_192571_R = 0;
         }

         NarratorChatListener.field_193643_a.func_193641_a(this.field_192571_R);
      }

      this.func_74303_b();
   }

   public double func_198015_a(GameSettings.Options var1) {
      if (☃ == GameSettings.Options.BIOME_BLEND_RADIUS) {
         return (double)this.field_205217_U;
      } else if (☃ == GameSettings.Options.FOV) {
         return this.field_74334_X;
      } else if (☃ == GameSettings.Options.GAMMA) {
         return this.field_74333_Y;
      } else if (☃ == GameSettings.Options.SATURATION) {
         return (double)this.field_151452_as;
      } else if (☃ == GameSettings.Options.SENSITIVITY) {
         return this.field_74341_c;
      } else if (☃ == GameSettings.Options.CHAT_OPACITY) {
         return this.field_74357_r;
      } else if (☃ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         return this.field_96694_H;
      } else if (☃ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         return this.field_96693_G;
      } else if (☃ == GameSettings.Options.CHAT_SCALE) {
         return this.field_96691_E;
      } else if (☃ == GameSettings.Options.CHAT_WIDTH) {
         return this.field_96692_F;
      } else if (☃ == GameSettings.Options.FRAMERATE_LIMIT) {
         return (double)this.field_74350_i;
      } else if (☃ == GameSettings.Options.MIPMAP_LEVELS) {
         return (double)this.field_151442_I;
      } else if (☃ == GameSettings.Options.RENDER_DISTANCE) {
         return (double)this.field_151451_c;
      } else if (☃ == GameSettings.Options.FULLSCREEN_RESOLUTION) {
         return (double)this.field_74317_L.field_195558_d.func_198090_e();
      } else {
         return ☃ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY ? this.field_208033_V : 0.0;
      }
   }

   public boolean func_74308_b(GameSettings.Options var1) {
      switch(☃) {
         case INVERT_MOUSE:
            return this.field_74338_d;
         case VIEW_BOBBING:
            return this.field_74336_f;
         case FBO_ENABLE:
            return this.field_151448_g;
         case CHAT_COLOR:
            return this.field_74344_o;
         case CHAT_LINKS:
            return this.field_74359_p;
         case CHAT_LINKS_PROMPT:
            return this.field_74358_q;
         case SNOOPER_ENABLED:
            if (this.field_74355_t) {
            }

            return false;
         case USE_FULLSCREEN:
            return this.field_74353_u;
         case ENABLE_VSYNC:
            return this.field_74352_v;
         case USE_VBO:
            return this.field_178881_t;
         case TOUCHSCREEN:
            return this.field_85185_A;
         case FORCE_UNICODE_FONT:
            return this.field_211842_aO;
         case REDUCED_DEBUG_INFO:
            return this.field_178879_v;
         case ENTITY_SHADOWS:
            return this.field_181151_V;
         case SHOW_SUBTITLES:
            return this.field_186717_N;
         case REALMS_NOTIFICATIONS:
            return this.field_183509_X;
         case ENABLE_WEAK_ATTACKS:
            return this.field_189422_N;
         case AUTO_JUMP:
            return this.field_189989_R;
         case AUTO_SUGGESTIONS:
            return this.field_198018_T;
         default:
            return false;
      }
   }

   private static String func_74299_a(String[] var0, int var1) {
      if (☃ < 0 || ☃ >= ☃.length) {
         ☃ = 0;
      }

      return I18n.func_135052_a(☃[☃]);
   }

   public String func_74297_c(GameSettings.Options var1) {
      String ☃ = I18n.func_135052_a(☃.func_74378_d()) + ": ";
      if (☃.func_74380_a()) {
         double ☃x = this.func_198015_a(☃);
         double ☃xx = ☃.func_198008_a(☃x);
         if (☃ == GameSettings.Options.SENSITIVITY) {
            if (☃xx == 0.0) {
               return ☃ + I18n.func_135052_a("options.sensitivity.min");
            } else {
               return ☃xx == 1.0 ? ☃ + I18n.func_135052_a("options.sensitivity.max") : ☃ + (int)(☃xx * 200.0) + "%";
            }
         } else if (☃ == GameSettings.Options.BIOME_BLEND_RADIUS) {
            if (☃xx == 0.0) {
               return ☃ + I18n.func_135052_a("options.off");
            } else {
               int ☃x = this.field_205217_U * 2 + 1;
               return ☃ + ☃x + "x" + ☃x;
            }
         } else if (☃ == GameSettings.Options.FOV) {
            if (☃x == 70.0) {
               return ☃ + I18n.func_135052_a("options.fov.min");
            } else {
               return ☃x == 110.0 ? ☃ + I18n.func_135052_a("options.fov.max") : ☃ + (int)☃x;
            }
         } else if (☃ == GameSettings.Options.FRAMERATE_LIMIT) {
            return ☃x == ☃.field_148272_O ? ☃ + I18n.func_135052_a("options.framerateLimit.max") : ☃ + I18n.func_135052_a("options.framerate", (int)☃x);
         } else if (☃ == GameSettings.Options.RENDER_CLOUDS) {
            return ☃x == ☃.field_148271_N ? ☃ + I18n.func_135052_a("options.cloudHeight.min") : ☃ + ((int)☃x + 128);
         } else if (☃ == GameSettings.Options.GAMMA) {
            if (☃xx == 0.0) {
               return ☃ + I18n.func_135052_a("options.gamma.min");
            } else {
               return ☃xx == 1.0 ? ☃ + I18n.func_135052_a("options.gamma.max") : ☃ + "+" + (int)(☃xx * 100.0) + "%";
            }
         } else if (☃ == GameSettings.Options.SATURATION) {
            return ☃ + (int)(☃xx * 400.0) + "%";
         } else if (☃ == GameSettings.Options.CHAT_OPACITY) {
            return ☃ + (int)(☃xx * 90.0 + 10.0) + "%";
         } else if (☃ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
            return ☃ + GuiNewChat.func_194816_c(☃xx) + "px";
         } else if (☃ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
            return ☃ + GuiNewChat.func_194816_c(☃xx) + "px";
         } else if (☃ == GameSettings.Options.CHAT_WIDTH) {
            return ☃ + GuiNewChat.func_194814_b(☃xx) + "px";
         } else if (☃ == GameSettings.Options.RENDER_DISTANCE) {
            return ☃ + I18n.func_135052_a("options.chunks", (int)☃x);
         } else if (☃ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY) {
            return ☃xx == 1.0 ? ☃ + I18n.func_135052_a("options.mouseWheelSensitivity.default") : ☃ + "+" + (int)☃xx + "." + (int)(☃xx * 10.0) % 10;
         } else if (☃ == GameSettings.Options.MIPMAP_LEVELS) {
            return ☃x == 0.0 ? ☃ + I18n.func_135052_a("options.off") : ☃ + (int)☃x;
         } else if (☃ == GameSettings.Options.FULLSCREEN_RESOLUTION) {
            return ☃x == 0.0 ? ☃ + I18n.func_135052_a("options.fullscreen.current") : ☃ + this.field_74317_L.field_195558_d.func_198088_a((int)☃x - 1);
         } else {
            return ☃xx == 0.0 ? ☃ + I18n.func_135052_a("options.off") : ☃ + (int)(☃xx * 100.0) + "%";
         }
      } else if (☃.func_74382_b()) {
         boolean ☃ = this.func_74308_b(☃);
         return ☃ ? ☃ + I18n.func_135052_a("options.on") : ☃ + I18n.func_135052_a("options.off");
      } else if (☃ == GameSettings.Options.MAIN_HAND) {
         return ☃ + this.field_186715_A;
      } else if (☃ == GameSettings.Options.GUI_SCALE) {
         return ☃ + (this.field_74335_Z == 0 ? I18n.func_135052_a("options.guiScale.auto") : this.field_74335_Z);
      } else if (☃ == GameSettings.Options.CHAT_VISIBILITY) {
         return ☃ + I18n.func_135052_a(this.field_74343_n.func_151429_b());
      } else if (☃ == GameSettings.Options.PARTICLES) {
         return ☃ + func_74299_a(field_74364_ag, this.field_74362_aa);
      } else if (☃ == GameSettings.Options.AMBIENT_OCCLUSION) {
         return ☃ + func_74299_a(field_98303_au, this.field_74348_k);
      } else if (☃ == GameSettings.Options.RENDER_CLOUDS) {
         return ☃ + func_74299_a(field_181149_aW, this.field_74345_l);
      } else if (☃ == GameSettings.Options.GRAPHICS) {
         if (this.field_74347_j) {
            return ☃ + I18n.func_135052_a("options.graphics.fancy");
         } else {
            String ☃ = "options.graphics.fast";
            return ☃ + I18n.func_135052_a("options.graphics.fast");
         }
      } else if (☃ == GameSettings.Options.ATTACK_INDICATOR) {
         return ☃ + func_74299_a(field_186713_aK, this.field_186716_M);
      } else if (☃ == GameSettings.Options.NARRATOR) {
         return NarratorChatListener.field_193643_a.func_193640_a()
            ? ☃ + func_74299_a(field_193632_b, this.field_192571_R)
            : ☃ + I18n.func_135052_a("options.narrator.notavailable");
      } else {
         return ☃;
      }
   }

   public void func_74300_a() {
      try {
         if (!this.field_74354_ai.exists()) {
            return;
         }

         this.field_186714_aM.clear();
         List<String> ☃ = IOUtils.readLines(new FileInputStream(this.field_74354_ai));
         NBTTagCompound ☃x = new NBTTagCompound();

         for(String ☃xx : ☃) {
            try {
               Iterator<String> ☃xxx = field_189990_a.omitEmptyStrings().limit(2).split(☃xx).iterator();
               ☃x.func_74778_a((String)☃xxx.next(), (String)☃xxx.next());
            } catch (Exception var10) {
               field_151454_ax.warn("Skipping bad option: {}", ☃xx);
            }
         }

         ☃x = this.func_189988_a(☃x);

         for(String ☃xx : ☃x.func_150296_c()) {
            String ☃xxx = ☃x.func_74779_i(☃xx);

            try {
               if ("mouseSensitivity".equals(☃xx)) {
                  this.field_74341_c = (double)this.func_74305_a(☃xxx);
               }

               if ("fov".equals(☃xx)) {
                  this.field_74334_X = (double)(this.func_74305_a(☃xxx) * 40.0F + 70.0F);
               }

               if ("gamma".equals(☃xx)) {
                  this.field_74333_Y = (double)this.func_74305_a(☃xxx);
               }

               if ("saturation".equals(☃xx)) {
                  this.field_151452_as = this.func_74305_a(☃xxx);
               }

               if ("invertYMouse".equals(☃xx)) {
                  this.field_74338_d = "true".equals(☃xxx);
               }

               if ("renderDistance".equals(☃xx)) {
                  this.field_151451_c = Integer.parseInt(☃xxx);
               }

               if ("guiScale".equals(☃xx)) {
                  this.field_74335_Z = Integer.parseInt(☃xxx);
               }

               if ("particles".equals(☃xx)) {
                  this.field_74362_aa = Integer.parseInt(☃xxx);
               }

               if ("bobView".equals(☃xx)) {
                  this.field_74336_f = "true".equals(☃xxx);
               }

               if ("maxFps".equals(☃xx)) {
                  this.field_74350_i = Integer.parseInt(☃xxx);
               }

               if ("fboEnable".equals(☃xx)) {
                  this.field_151448_g = "true".equals(☃xxx);
               }

               if ("difficulty".equals(☃xx)) {
                  this.field_74318_M = EnumDifficulty.func_151523_a(Integer.parseInt(☃xxx));
               }

               if ("fancyGraphics".equals(☃xx)) {
                  this.field_74347_j = "true".equals(☃xxx);
               }

               if ("tutorialStep".equals(☃xx)) {
                  this.field_193631_S = TutorialSteps.func_193307_a(☃xxx);
               }

               if ("ao".equals(☃xx)) {
                  if ("true".equals(☃xxx)) {
                     this.field_74348_k = 2;
                  } else if ("false".equals(☃xxx)) {
                     this.field_74348_k = 0;
                  } else {
                     this.field_74348_k = Integer.parseInt(☃xxx);
                  }
               }

               if ("renderClouds".equals(☃xx)) {
                  if ("true".equals(☃xxx)) {
                     this.field_74345_l = 2;
                  } else if ("false".equals(☃xxx)) {
                     this.field_74345_l = 0;
                  } else if ("fast".equals(☃xxx)) {
                     this.field_74345_l = 1;
                  }
               }

               if ("attackIndicator".equals(☃xx)) {
                  if ("0".equals(☃xxx)) {
                     this.field_186716_M = 0;
                  } else if ("1".equals(☃xxx)) {
                     this.field_186716_M = 1;
                  } else if ("2".equals(☃xxx)) {
                     this.field_186716_M = 2;
                  }
               }

               if ("resourcePacks".equals(☃xx)) {
                  this.field_151453_l = JsonUtils.func_193840_a(field_151450_ay, ☃xxx, field_151449_az);
                  if (this.field_151453_l == null) {
                     this.field_151453_l = Lists.newArrayList();
                  }
               }

               if ("incompatibleResourcePacks".equals(☃xx)) {
                  this.field_183018_l = JsonUtils.func_193840_a(field_151450_ay, ☃xxx, field_151449_az);
                  if (this.field_183018_l == null) {
                     this.field_183018_l = Lists.newArrayList();
                  }
               }

               if ("lastServer".equals(☃xx)) {
                  this.field_74332_R = ☃xxx;
               }

               if ("lang".equals(☃xx)) {
                  this.field_74363_ab = ☃xxx;
               }

               if ("chatVisibility".equals(☃xx)) {
                  this.field_74343_n = EntityPlayer.EnumChatVisibility.func_151426_a(Integer.parseInt(☃xxx));
               }

               if ("chatColors".equals(☃xx)) {
                  this.field_74344_o = "true".equals(☃xxx);
               }

               if ("chatLinks".equals(☃xx)) {
                  this.field_74359_p = "true".equals(☃xxx);
               }

               if ("chatLinksPrompt".equals(☃xx)) {
                  this.field_74358_q = "true".equals(☃xxx);
               }

               if ("chatOpacity".equals(☃xx)) {
                  this.field_74357_r = (double)this.func_74305_a(☃xxx);
               }

               if ("snooperEnabled".equals(☃xx)) {
                  this.field_74355_t = "true".equals(☃xxx);
               }

               if ("fullscreen".equals(☃xx)) {
                  this.field_74353_u = "true".equals(☃xxx);
               }

               if ("fullscreenResolution".equals(☃xx)) {
                  this.field_198019_u = ☃xxx;
               }

               if ("enableVsync".equals(☃xx)) {
                  this.field_74352_v = "true".equals(☃xxx);
               }

               if ("useVbo".equals(☃xx)) {
                  this.field_178881_t = "true".equals(☃xxx);
               }

               if ("hideServerAddress".equals(☃xx)) {
                  this.field_80005_w = "true".equals(☃xxx);
               }

               if ("advancedItemTooltips".equals(☃xx)) {
                  this.field_82882_x = "true".equals(☃xxx);
               }

               if ("pauseOnLostFocus".equals(☃xx)) {
                  this.field_82881_y = "true".equals(☃xxx);
               }

               if ("touchscreen".equals(☃xx)) {
                  this.field_85185_A = "true".equals(☃xxx);
               }

               if ("overrideHeight".equals(☃xx)) {
                  this.field_92119_C = Integer.parseInt(☃xxx);
               }

               if ("overrideWidth".equals(☃xx)) {
                  this.field_92118_B = Integer.parseInt(☃xxx);
               }

               if ("heldItemTooltips".equals(☃xx)) {
                  this.field_92117_D = "true".equals(☃xxx);
               }

               if ("chatHeightFocused".equals(☃xx)) {
                  this.field_96694_H = (double)this.func_74305_a(☃xxx);
               }

               if ("chatHeightUnfocused".equals(☃xx)) {
                  this.field_96693_G = (double)this.func_74305_a(☃xxx);
               }

               if ("chatScale".equals(☃xx)) {
                  this.field_96691_E = (double)this.func_74305_a(☃xxx);
               }

               if ("chatWidth".equals(☃xx)) {
                  this.field_96692_F = (double)this.func_74305_a(☃xxx);
               }

               if ("mipmapLevels".equals(☃xx)) {
                  this.field_151442_I = Integer.parseInt(☃xxx);
               }

               if ("forceUnicodeFont".equals(☃xx)) {
                  this.field_211842_aO = "true".equals(☃xxx);
               }

               if ("reducedDebugInfo".equals(☃xx)) {
                  this.field_178879_v = "true".equals(☃xxx);
               }

               if ("useNativeTransport".equals(☃xx)) {
                  this.field_181150_U = "true".equals(☃xxx);
               }

               if ("entityShadows".equals(☃xx)) {
                  this.field_181151_V = "true".equals(☃xxx);
               }

               if ("mainHand".equals(☃xx)) {
                  this.field_186715_A = "left".equals(☃xxx) ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
               }

               if ("showSubtitles".equals(☃xx)) {
                  this.field_186717_N = "true".equals(☃xxx);
               }

               if ("realmsNotifications".equals(☃xx)) {
                  this.field_183509_X = "true".equals(☃xxx);
               }

               if ("enableWeakAttacks".equals(☃xx)) {
                  this.field_189422_N = "true".equals(☃xxx);
               }

               if ("autoJump".equals(☃xx)) {
                  this.field_189989_R = "true".equals(☃xxx);
               }

               if ("narrator".equals(☃xx)) {
                  this.field_192571_R = Integer.parseInt(☃xxx);
               }

               if ("autoSuggestions".equals(☃xx)) {
                  this.field_198018_T = "true".equals(☃xxx);
               }

               if ("biomeBlendRadius".equals(☃xx)) {
                  this.field_205217_U = Integer.parseInt(☃xxx);
               }

               if ("mouseWheelSensitivity".equals(☃xx)) {
                  this.field_208033_V = (double)this.func_74305_a(☃xxx);
               }

               if ("glDebugVerbosity".equals(☃xx)) {
                  this.field_209231_W = Integer.parseInt(☃xxx);
               }

               for(KeyBinding ☃xxxx : this.field_74324_K) {
                  if (☃xx.equals("key_" + ☃xxxx.func_151464_g())) {
                     ☃xxxx.func_197979_b(InputMappings.func_197955_a(☃xxx));
                  }
               }

               for(SoundCategory ☃xxxx : SoundCategory.values()) {
                  if (☃xx.equals("soundCategory_" + ☃xxxx.func_187948_a())) {
                     this.field_186714_aM.put(☃xxxx, this.func_74305_a(☃xxx));
                  }
               }

               for(EnumPlayerModelParts ☃xxxx : EnumPlayerModelParts.values()) {
                  if (☃xx.equals("modelPart_" + ☃xxxx.func_179329_c())) {
                     this.func_178878_a(☃xxxx, "true".equals(☃xxx));
                  }
               }
            } catch (Exception var11) {
               field_151454_ax.warn("Skipping bad option: {}:{}", ☃xx, ☃xxx);
            }
         }

         KeyBinding.func_74508_b();
      } catch (Exception var12) {
         field_151454_ax.error("Failed to load options", var12);
      }
   }

   private NBTTagCompound func_189988_a(NBTTagCompound var1) {
      int ☃ = 0;

      try {
         ☃ = Integer.parseInt(☃.func_74779_i("version"));
      } catch (RuntimeException var4) {
      }

      return NBTUtil.func_210822_a(this.field_74317_L.func_184126_aj(), DataFixTypes.OPTIONS, ☃, ☃);
   }

   private float func_74305_a(String var1) {
      if ("true".equals(☃)) {
         return 1.0F;
      } else {
         return "false".equals(☃) ? 0.0F : Float.parseFloat(☃);
      }
   }

   public void func_74303_b() {
      PrintWriter ☃ = null;

      try {
         ☃ = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.field_74354_ai), StandardCharsets.UTF_8));
         ☃.println("version:1631");
         ☃.println("invertYMouse:" + this.field_74338_d);
         ☃.println("mouseSensitivity:" + this.field_74341_c);
         ☃.println("fov:" + (this.field_74334_X - 70.0) / 40.0);
         ☃.println("gamma:" + this.field_74333_Y);
         ☃.println("saturation:" + this.field_151452_as);
         ☃.println("renderDistance:" + this.field_151451_c);
         ☃.println("guiScale:" + this.field_74335_Z);
         ☃.println("particles:" + this.field_74362_aa);
         ☃.println("bobView:" + this.field_74336_f);
         ☃.println("maxFps:" + this.field_74350_i);
         ☃.println("fboEnable:" + this.field_151448_g);
         ☃.println("difficulty:" + this.field_74318_M.func_151525_a());
         ☃.println("fancyGraphics:" + this.field_74347_j);
         ☃.println("ao:" + this.field_74348_k);
         ☃.println("biomeBlendRadius:" + this.field_205217_U);
         switch(this.field_74345_l) {
            case 0:
               ☃.println("renderClouds:false");
               break;
            case 1:
               ☃.println("renderClouds:fast");
               break;
            case 2:
               ☃.println("renderClouds:true");
         }

         ☃.println("resourcePacks:" + field_151450_ay.toJson(this.field_151453_l));
         ☃.println("incompatibleResourcePacks:" + field_151450_ay.toJson(this.field_183018_l));
         ☃.println("lastServer:" + this.field_74332_R);
         ☃.println("lang:" + this.field_74363_ab);
         ☃.println("chatVisibility:" + this.field_74343_n.func_151428_a());
         ☃.println("chatColors:" + this.field_74344_o);
         ☃.println("chatLinks:" + this.field_74359_p);
         ☃.println("chatLinksPrompt:" + this.field_74358_q);
         ☃.println("chatOpacity:" + this.field_74357_r);
         ☃.println("snooperEnabled:" + this.field_74355_t);
         ☃.println("fullscreen:" + this.field_74353_u);
         if (this.field_74317_L.field_195558_d.func_198106_d().isPresent()) {
            ☃.println("fullscreenResolution:" + ((VideoMode)this.field_74317_L.field_195558_d.func_198106_d().get()).func_198066_g());
         }

         ☃.println("enableVsync:" + this.field_74352_v);
         ☃.println("useVbo:" + this.field_178881_t);
         ☃.println("hideServerAddress:" + this.field_80005_w);
         ☃.println("advancedItemTooltips:" + this.field_82882_x);
         ☃.println("pauseOnLostFocus:" + this.field_82881_y);
         ☃.println("touchscreen:" + this.field_85185_A);
         ☃.println("overrideWidth:" + this.field_92118_B);
         ☃.println("overrideHeight:" + this.field_92119_C);
         ☃.println("heldItemTooltips:" + this.field_92117_D);
         ☃.println("chatHeightFocused:" + this.field_96694_H);
         ☃.println("chatHeightUnfocused:" + this.field_96693_G);
         ☃.println("chatScale:" + this.field_96691_E);
         ☃.println("chatWidth:" + this.field_96692_F);
         ☃.println("mipmapLevels:" + this.field_151442_I);
         ☃.println("forceUnicodeFont:" + this.field_211842_aO);
         ☃.println("reducedDebugInfo:" + this.field_178879_v);
         ☃.println("useNativeTransport:" + this.field_181150_U);
         ☃.println("entityShadows:" + this.field_181151_V);
         ☃.println("mainHand:" + (this.field_186715_A == EnumHandSide.LEFT ? "left" : "right"));
         ☃.println("attackIndicator:" + this.field_186716_M);
         ☃.println("showSubtitles:" + this.field_186717_N);
         ☃.println("realmsNotifications:" + this.field_183509_X);
         ☃.println("enableWeakAttacks:" + this.field_189422_N);
         ☃.println("autoJump:" + this.field_189989_R);
         ☃.println("narrator:" + this.field_192571_R);
         ☃.println("tutorialStep:" + this.field_193631_S.func_193308_a());
         ☃.println("autoSuggestions:" + this.field_198018_T);
         ☃.println("mouseWheelSensitivity:" + this.field_208033_V);
         ☃.println("glDebugVerbosity:" + this.field_209231_W);

         for(KeyBinding ☃x : this.field_74324_K) {
            ☃.println("key_" + ☃x.func_151464_g() + ":" + ☃x.func_197982_m());
         }

         for(SoundCategory ☃x : SoundCategory.values()) {
            ☃.println("soundCategory_" + ☃x.func_187948_a() + ":" + this.func_186711_a(☃x));
         }

         for(EnumPlayerModelParts ☃x : EnumPlayerModelParts.values()) {
            ☃.println("modelPart_" + ☃x.func_179329_c() + ":" + this.field_178882_aU.contains(☃x));
         }
      } catch (Exception var9) {
         field_151454_ax.error("Failed to save options", var9);
      } finally {
         IOUtils.closeQuietly(☃);
      }

      this.func_82879_c();
   }

   public float func_186711_a(SoundCategory var1) {
      return this.field_186714_aM.containsKey(☃) ? this.field_186714_aM.get(☃) : 1.0F;
   }

   public void func_186712_a(SoundCategory var1, float var2) {
      this.field_74317_L.func_147118_V().func_184399_a(☃, ☃);
      this.field_186714_aM.put(☃, ☃);
   }

   public void func_82879_c() {
      if (this.field_74317_L.field_71439_g != null) {
         int ☃ = 0;

         for(EnumPlayerModelParts ☃x : this.field_178882_aU) {
            ☃ |= ☃x.func_179327_a();
         }

         this.field_74317_L
            .field_71439_g
            .field_71174_a
            .func_147297_a(new CPacketClientSettings(this.field_74363_ab, this.field_151451_c, this.field_74343_n, this.field_74344_o, ☃, this.field_186715_A));
      }
   }

   public Set<EnumPlayerModelParts> func_178876_d() {
      return ImmutableSet.copyOf(this.field_178882_aU);
   }

   public void func_178878_a(EnumPlayerModelParts var1, boolean var2) {
      if (☃) {
         this.field_178882_aU.add(☃);
      } else {
         this.field_178882_aU.remove(☃);
      }

      this.func_82879_c();
   }

   public void func_178877_a(EnumPlayerModelParts var1) {
      if (this.func_178876_d().contains(☃)) {
         this.field_178882_aU.remove(☃);
      } else {
         this.field_178882_aU.add(☃);
      }

      this.func_82879_c();
   }

   public int func_181147_e() {
      return this.field_151451_c >= 4 ? this.field_74345_l : 0;
   }

   public boolean func_181148_f() {
      return this.field_181150_U;
   }

   public void func_198017_a(ResourcePackList<ResourcePackInfoClient> var1) {
      ☃.func_198983_a();
      Set<ResourcePackInfoClient> ☃ = Sets.<ResourcePackInfoClient>newLinkedHashSet();
      Iterator<String> ☃x = this.field_151453_l.iterator();

      while(☃x.hasNext()) {
         String ☃xx = (String)☃x.next();
         ResourcePackInfoClient ☃xxx = ☃.func_198981_a(☃xx);
         if (☃xxx == null && !☃xx.startsWith("file/")) {
            ☃xxx = ☃.func_198981_a("file/" + ☃xx);
         }

         if (☃xxx == null) {
            field_151454_ax.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", ☃xx);
            ☃x.remove();
         } else if (!☃xxx.func_195791_d().func_198968_a() && !this.field_183018_l.contains(☃xx)) {
            field_151454_ax.warn("Removed resource pack {} from options because it is no longer compatible", ☃xx);
            ☃x.remove();
         } else if (☃xxx.func_195791_d().func_198968_a() && this.field_183018_l.contains(☃xx)) {
            field_151454_ax.info("Removed resource pack {} from incompatibility list because it's now compatible", ☃xx);
            this.field_183018_l.remove(☃xx);
         } else {
            ☃.add(☃xxx);
         }
      }

      ☃.func_198985_a(☃);
   }

   public static enum Options {
      INVERT_MOUSE("options.invertMouse", false, true),
      SENSITIVITY("options.sensitivity", true, false),
      FOV("options.fov", true, false, 30.0, 110.0, 1.0F),
      GAMMA("options.gamma", true, false),
      SATURATION("options.saturation", true, false),
      RENDER_DISTANCE("options.renderDistance", true, false, 2.0, 16.0, 1.0F),
      VIEW_BOBBING("options.viewBobbing", false, true),
      FRAMERATE_LIMIT("options.framerateLimit", true, false, 10.0, 260.0, 10.0F),
      FBO_ENABLE("options.fboEnable", false, true),
      RENDER_CLOUDS("options.renderClouds", false, false),
      GRAPHICS("options.graphics", false, false),
      AMBIENT_OCCLUSION("options.ao", false, false),
      GUI_SCALE("options.guiScale", false, false),
      PARTICLES("options.particles", false, false),
      CHAT_VISIBILITY("options.chat.visibility", false, false),
      CHAT_COLOR("options.chat.color", false, true),
      CHAT_LINKS("options.chat.links", false, true),
      CHAT_OPACITY("options.chat.opacity", true, false),
      CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
      SNOOPER_ENABLED("options.snooper", false, true),
      FULLSCREEN_RESOLUTION("options.fullscreen.resolution", true, false, 0.0, 0.0, 1.0F),
      USE_FULLSCREEN("options.fullscreen", false, true),
      ENABLE_VSYNC("options.vsync", false, true),
      USE_VBO("options.vbo", false, true),
      TOUCHSCREEN("options.touchscreen", false, true),
      CHAT_SCALE("options.chat.scale", true, false),
      CHAT_WIDTH("options.chat.width", true, false),
      CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
      CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
      MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0, 4.0, 1.0F),
      FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
      REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
      ENTITY_SHADOWS("options.entityShadows", false, true),
      MAIN_HAND("options.mainHand", false, false),
      ATTACK_INDICATOR("options.attackIndicator", false, false),
      ENABLE_WEAK_ATTACKS("options.enableWeakAttacks", false, true),
      SHOW_SUBTITLES("options.showSubtitles", false, true),
      REALMS_NOTIFICATIONS("options.realmsNotifications", false, true),
      AUTO_JUMP("options.autoJump", false, true),
      NARRATOR("options.narrator", false, false),
      AUTO_SUGGESTIONS("options.autoSuggestCommands", false, true),
      BIOME_BLEND_RADIUS("options.biomeBlendRadius", true, false, 0.0, 7.0, 1.0F),
      MOUSE_WHEEL_SENSITIVITY("options.mouseWheelSensitivity", true, false, 1.0, 10.0, 0.5F);

      private final boolean field_74385_A;
      private final boolean field_74386_B;
      private final String field_74387_C;
      private final float field_148270_M;
      private double field_148271_N;
      private double field_148272_O;

      public static GameSettings.Options func_74379_a(int var0) {
         for(GameSettings.Options ☃ : values()) {
            if (☃.func_74381_c() == ☃) {
               return ☃;
            }
         }

         return null;
      }

      private Options(String var3, boolean var4, boolean var5) {
         this(☃, ☃, ☃, 0.0, 1.0, 0.0F);
      }

      private Options(String var3, boolean var4, boolean var5, double var6, double var8, float var10) {
         this.field_74387_C = ☃;
         this.field_74385_A = ☃;
         this.field_74386_B = ☃;
         this.field_148271_N = ☃;
         this.field_148272_O = ☃;
         this.field_148270_M = ☃;
      }

      public boolean func_74380_a() {
         return this.field_74385_A;
      }

      public boolean func_74382_b() {
         return this.field_74386_B;
      }

      public int func_74381_c() {
         return this.ordinal();
      }

      public String func_74378_d() {
         return this.field_74387_C;
      }

      public double func_198007_e() {
         return this.field_148271_N;
      }

      public double func_198009_f() {
         return this.field_148272_O;
      }

      public void func_148263_a(float var1) {
         this.field_148272_O = (double)☃;
      }

      public double func_198008_a(double var1) {
         return MathHelper.func_151237_a((this.func_198011_c(☃) - this.field_148271_N) / (this.field_148272_O - this.field_148271_N), 0.0, 1.0);
      }

      public double func_198004_b(double var1) {
         return this.func_198011_c(this.field_148271_N + (this.field_148272_O - this.field_148271_N) * MathHelper.func_151237_a(☃, 0.0, 1.0));
      }

      public double func_198011_c(double var1) {
         ☃ = this.func_198006_d(☃);
         return MathHelper.func_151237_a(☃, this.field_148271_N, this.field_148272_O);
      }

      private double func_198006_d(double var1) {
         if (this.field_148270_M > 0.0F) {
            ☃ = (double)(this.field_148270_M * (float)Math.round(☃ / (double)this.field_148270_M));
         }

         return ☃;
      }
   }
}
