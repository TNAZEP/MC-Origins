package net.minecraft.client;

import java.nio.ByteBuffer;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

public class KeyboardListener {
   private final Minecraft field_197972_a;
   private boolean field_197973_b;
   private long field_197974_c = -1L;
   private long field_204871_d = -1L;
   private long field_204872_e = -1L;
   private boolean field_197975_d;
   private final ByteBuffer field_211563_g = ByteBuffer.allocateDirect(1024);

   public KeyboardListener(Minecraft var1) {
      this.field_197972_a = ☃;
   }

   private void func_197964_a(String var1, Object... var2) {
      this.field_197972_a
         .field_71456_v
         .func_146158_b()
         .func_146227_a(
            new TextComponentString("")
               .func_150257_a(new TextComponentTranslation("debug.prefix").func_211709_a(new TextFormatting[]{TextFormatting.YELLOW, TextFormatting.BOLD}))
               .func_150258_a(" ")
               .func_150257_a(new TextComponentTranslation(☃, ☃))
         );
   }

   private void func_204869_b(String var1, Object... var2) {
      this.field_197972_a
         .field_71456_v
         .func_146158_b()
         .func_146227_a(
            new TextComponentString("")
               .func_150257_a(new TextComponentTranslation("debug.prefix").func_211709_a(new TextFormatting[]{TextFormatting.RED, TextFormatting.BOLD}))
               .func_150258_a(" ")
               .func_150257_a(new TextComponentTranslation(☃, ☃))
         );
   }

   private boolean func_197962_c(int var1) {
      if (this.field_197974_c > 0L && this.field_197974_c < Util.func_211177_b() - 100L) {
         return true;
      } else {
         switch(☃) {
            case 65:
               this.field_197972_a.field_71438_f.func_72712_a();
               this.func_197964_a("debug.reload_chunks.message");
               return true;
            case 66: {
               boolean ☃ = !this.field_197972_a.func_175598_ae().func_178634_b();
               this.field_197972_a.func_175598_ae().func_178629_b(☃);
               this.func_197964_a(☃ ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
               return true;
            }
            case 67:
               if (this.field_197972_a.field_71439_g.func_175140_cp()) {
                  return false;
               }

               this.func_197964_a("debug.copy_location.message");
               this.func_197960_a(
                  String.format(
                     Locale.ROOT,
                     "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f",
                     DimensionType.func_212678_a(this.field_197972_a.field_71439_g.field_70170_p.field_73011_w.func_186058_p()),
                     this.field_197972_a.field_71439_g.field_70165_t,
                     this.field_197972_a.field_71439_g.field_70163_u,
                     this.field_197972_a.field_71439_g.field_70161_v,
                     this.field_197972_a.field_71439_g.field_70177_z,
                     this.field_197972_a.field_71439_g.field_70125_A
                  )
               );
               return true;
            case 68:
               if (this.field_197972_a.field_71456_v != null) {
                  this.field_197972_a.field_71456_v.func_146158_b().func_146231_a(false);
               }

               return true;
            case 69:
            case 74:
            case 75:
            case 76:
            case 77:
            case 79:
            case 82:
            case 83:
            default:
               return false;
            case 70:
               this.field_197972_a.field_71474_y.func_74306_a(GameSettings.Options.RENDER_DISTANCE, GuiScreen.func_146272_n() ? -1 : 1);
               this.func_197964_a("debug.cycle_renderdistance.message", this.field_197972_a.field_71474_y.field_151451_c);
               return true;
            case 71: {
               boolean ☃ = this.field_197972_a.field_184132_p.func_190075_b();
               this.func_197964_a(☃ ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
               return true;
            }
            case 72:
               this.field_197972_a.field_71474_y.field_82882_x = !this.field_197972_a.field_71474_y.field_82882_x;
               this.func_197964_a(this.field_197972_a.field_71474_y.field_82882_x ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
               this.field_197972_a.field_71474_y.func_74303_b();
               return true;
            case 73:
               if (!this.field_197972_a.field_71439_g.func_175140_cp()) {
                  this.func_211556_a(this.field_197972_a.field_71439_g.func_211513_k(2), !GuiScreen.func_146272_n());
               }

               return true;
            case 78:
               if (!this.field_197972_a.field_71439_g.func_211513_k(2)) {
                  this.func_197964_a("debug.creative_spectator.error");
               } else if (this.field_197972_a.field_71439_g.func_184812_l_()) {
                  this.field_197972_a.field_71439_g.func_71165_d("/gamemode spectator");
               } else if (this.field_197972_a.field_71439_g.func_175149_v()) {
                  this.field_197972_a.field_71439_g.func_71165_d("/gamemode creative");
               }

               return true;
            case 80:
               this.field_197972_a.field_71474_y.field_82881_y = !this.field_197972_a.field_71474_y.field_82881_y;
               this.field_197972_a.field_71474_y.func_74303_b();
               this.func_197964_a(this.field_197972_a.field_71474_y.field_82881_y ? "debug.pause_focus.on" : "debug.pause_focus.off");
               return true;
            case 81: {
               this.func_197964_a("debug.help.message");
               GuiNewChat ☃ = this.field_197972_a.field_71456_v.func_146158_b();
               ☃.func_146227_a(new TextComponentTranslation("debug.reload_chunks.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.show_hitboxes.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.copy_location.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.clear_chat.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.cycle_renderdistance.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.chunk_boundaries.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.advanced_tooltips.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.inspect.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.creative_spectator.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.pause_focus.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.help.help"));
               ☃.func_146227_a(new TextComponentTranslation("debug.reload_resourcepacks.help"));
               return true;
            }
            case 84:
               this.func_197964_a("debug.reload_resourcepacks.message");
               this.field_197972_a.func_110436_a();
               return true;
         }
      }
   }

   private void func_211556_a(boolean var1, boolean var2) {
      if (this.field_197972_a.field_71476_x != null) {
         switch(this.field_197972_a.field_71476_x.field_72313_a) {
            case BLOCK:
               BlockPos ☃ = this.field_197972_a.field_71476_x.func_178782_a();
               IBlockState ☃x = this.field_197972_a.field_71439_g.field_70170_p.func_180495_p(☃);
               if (☃) {
                  if (☃) {
                     this.field_197972_a.field_71439_g.field_71174_a.func_211523_k().func_211547_a(☃, var3x -> {
                        this.func_211558_a(☃, ☃, var3x);
                        this.func_197964_a("debug.inspect.server.block");
                     });
                  } else {
                     TileEntity ☃xx = this.field_197972_a.field_71439_g.field_70170_p.func_175625_s(☃);
                     NBTTagCompound ☃xxx = ☃xx != null ? ☃xx.func_189515_b(new NBTTagCompound()) : null;
                     this.func_211558_a(☃x, ☃, ☃xxx);
                     this.func_197964_a("debug.inspect.client.block");
                  }
               } else {
                  this.func_211558_a(☃x, ☃, null);
                  this.func_197964_a("debug.inspect.client.block");
               }
               break;
            case ENTITY:
               Entity ☃ = this.field_197972_a.field_71476_x.field_72308_g;
               if (☃ == null) {
                  return;
               }

               ResourceLocation ☃ = IRegistry.field_212629_r.func_177774_c(☃.func_200600_R());
               Vec3d ☃x = new Vec3d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
               if (☃) {
                  if (☃) {
                     this.field_197972_a.field_71439_g.field_71174_a.func_211523_k().func_211549_a(☃.func_145782_y(), var3x -> {
                        this.func_211557_a(☃, ☃, var3x);
                        this.func_197964_a("debug.inspect.server.entity");
                     });
                  } else {
                     NBTTagCompound ☃xx = ☃.func_189511_e(new NBTTagCompound());
                     this.func_211557_a(☃, ☃x, ☃xx);
                     this.func_197964_a("debug.inspect.client.entity");
                  }
               } else {
                  this.func_211557_a(☃, ☃x, null);
                  this.func_197964_a("debug.inspect.client.entity");
               }
         }
      }
   }

   private void func_211558_a(IBlockState var1, BlockPos var2, @Nullable NBTTagCompound var3) {
      if (☃ != null) {
         ☃.func_82580_o("x");
         ☃.func_82580_o("y");
         ☃.func_82580_o("z");
         ☃.func_82580_o("id");
      }

      String ☃ = BlockStateParser.func_197247_a(☃, ☃);
      String ☃x = String.format(Locale.ROOT, "/setblock %d %d %d %s", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃);
      this.func_197960_a(☃x);
   }

   private void func_211557_a(ResourceLocation var1, Vec3d var2, @Nullable NBTTagCompound var3) {
      String ☃;
      if (☃ != null) {
         ☃.func_82580_o("UUIDMost");
         ☃.func_82580_o("UUIDLeast");
         ☃.func_82580_o("Pos");
         ☃.func_82580_o("Dimension");
         String ☃x = ☃.func_197637_c().getString();
         ☃ = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", ☃.toString(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃x);
      } else {
         ☃ = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", ☃.toString(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
      }

      this.func_197960_a(☃);
   }

   public void func_197961_a(long var1, int var3, int var4, int var5, int var6) {
      if (☃ == this.field_197972_a.field_195558_d.func_198092_i()) {
         if (this.field_197974_c > 0L) {
            if (!InputMappings.func_197956_a(67) || !InputMappings.func_197956_a(292)) {
               this.field_197974_c = -1L;
            }
         } else if (InputMappings.func_197956_a(67) && InputMappings.func_197956_a(292)) {
            this.field_197975_d = true;
            this.field_197974_c = Util.func_211177_b();
            this.field_204871_d = Util.func_211177_b();
            this.field_204872_e = 0L;
         }

         IGuiEventListener ☃ = this.field_197972_a.field_71462_r;
         if (☃ == 1 && (!(this.field_197972_a.field_71462_r instanceof GuiControls) || ((GuiControls)☃).field_152177_g <= Util.func_211177_b() - 20L)) {
            if (this.field_197972_a.field_71474_y.field_152395_am.func_197976_a(☃, ☃)) {
               this.field_197972_a.field_195558_d.func_198077_g();
               return;
            }

            if (this.field_197972_a.field_71474_y.field_151447_Z.func_197976_a(☃, ☃)) {
               if (GuiScreen.func_146271_m()) {
               }

               ScreenShotHelper.func_148260_a(
                  this.field_197972_a.field_71412_D,
                  this.field_197972_a.field_195558_d.func_198109_k(),
                  this.field_197972_a.field_195558_d.func_198091_l(),
                  this.field_197972_a.func_147110_a(),
                  var1x -> this.field_197972_a.func_152344_a(() -> this.field_197972_a.field_71456_v.func_146158_b().func_146227_a(var1x))
               );
               return;
            }
         }

         if (☃ != null) {
            boolean[] ☃ = new boolean[]{false};
            GuiScreen.func_195121_a(() -> {
               if (☃ != 1 && (☃ != 2 || !this.field_197973_b)) {
                  if (☃ == 0) {
                     ☃[0] = ☃.keyReleased(☃, ☃, ☃);
                  }
               } else {
                  ☃[0] = ☃.keyPressed(☃, ☃, ☃);
               }
            }, "keyPressed event handler", ☃.getClass().getCanonicalName());
            if (☃[0]) {
               return;
            }
         }

         if (this.field_197972_a.field_71462_r == null || this.field_197972_a.field_71462_r.field_146291_p) {
            InputMappings.Input ☃ = InputMappings.func_197954_a(☃, ☃);
            if (☃ == 0) {
               KeyBinding.func_197980_a(☃, false);
               if (☃ == 292) {
                  if (this.field_197975_d) {
                     this.field_197975_d = false;
                  } else {
                     this.field_197972_a.field_71474_y.field_74330_P = !this.field_197972_a.field_71474_y.field_74330_P;
                     this.field_197972_a.field_71474_y.field_74329_Q = this.field_197972_a.field_71474_y.field_74330_P && GuiScreen.func_146272_n();
                     this.field_197972_a.field_71474_y.field_181657_aC = this.field_197972_a.field_71474_y.field_74330_P && GuiScreen.func_175283_s();
                  }
               }
            } else {
               if (☃ == 66 && GuiScreen.func_146271_m()) {
                  this.field_197972_a.field_71474_y.func_74306_a(GameSettings.Options.NARRATOR, 1);
                  if (☃ instanceof ScreenChatOptions) {
                     ((ScreenChatOptions)☃).func_193024_a();
                  }
               }

               if (☃ == 293 && this.field_197972_a.field_71460_t != null) {
                  this.field_197972_a.field_71460_t.func_175071_c();
               }

               boolean ☃ = false;
               if (this.field_197972_a.field_71462_r == null) {
                  if (☃ == 256) {
                     this.field_197972_a.func_71385_j();
                  }

                  ☃ = InputMappings.func_197956_a(292) && this.func_197962_c(☃);
                  this.field_197975_d |= ☃;
                  if (☃ == 290) {
                     this.field_197972_a.field_71474_y.field_74319_N = !this.field_197972_a.field_71474_y.field_74319_N;
                  }
               }

               if (☃) {
                  KeyBinding.func_197980_a(☃, false);
               } else {
                  KeyBinding.func_197980_a(☃, true);
                  KeyBinding.func_197981_a(☃);
               }

               if (this.field_197972_a.field_71474_y.field_74329_Q) {
                  if (☃ == 48) {
                     this.field_197972_a.func_71383_b(0);
                  }

                  for(int ☃ = 0; ☃ < 9; ++☃) {
                     if (☃ == 49 + ☃) {
                        this.field_197972_a.func_71383_b(☃ + 1);
                     }
                  }
               }
            }
         }
      }
   }

   private void func_197963_a(long var1, int var3, int var4) {
      if (☃ == this.field_197972_a.field_195558_d.func_198092_i()) {
         IGuiEventListener ☃ = this.field_197972_a.field_71462_r;
         if (☃ != null) {
            if (Character.charCount(☃) == 1) {
               GuiScreen.func_195121_a(() -> ☃.charTyped((char)☃, ☃), "charTyped event handler", ☃.getClass().getCanonicalName());
            } else {
               for(char ☃x : Character.toChars(☃)) {
                  GuiScreen.func_195121_a(() -> ☃.charTyped(☃, ☃), "charTyped event handler", ☃.getClass().getCanonicalName());
               }
            }
         }
      }
   }

   public void func_197967_a(boolean var1) {
      this.field_197973_b = ☃;
   }

   public void func_197968_a(long var1) {
      GLFW.glfwSetKeyCallback(☃, this::func_197961_a);
      GLFW.glfwSetCharModsCallback(☃, this::func_197963_a);
   }

   public String func_197965_a() {
      GLFWErrorCallback ☃ = GLFW.glfwSetErrorCallback((var1x, var2x) -> {
         if (var1x != 65545) {
            this.field_197972_a.field_195558_d.func_198084_a(var1x, var2x);
         }
      });
      String ☃x = GLFW.glfwGetClipboardString(this.field_197972_a.field_195558_d.func_198092_i());
      GLFW.glfwSetErrorCallback(☃).free();
      return ☃x == null ? "" : ☃x;
   }

   private void func_211559_a(ByteBuffer var1, String var2) {
      MemoryUtil.memUTF8(☃, true, ☃);
      GLFW.glfwSetClipboardString(this.field_197972_a.field_195558_d.func_198092_i(), ☃);
   }

   public void func_197960_a(String var1) {
      int ☃ = MemoryUtil.memLengthUTF8(☃, true);
      if (☃ < this.field_211563_g.capacity()) {
         this.func_211559_a(this.field_211563_g, ☃);
         this.field_211563_g.clear();
      } else {
         ByteBuffer ☃ = ByteBuffer.allocateDirect(☃);
         this.func_211559_a(☃, ☃);
      }
   }

   public void func_204870_b() {
      if (this.field_197974_c > 0L) {
         long ☃ = Util.func_211177_b();
         long ☃x = 10000L - (☃ - this.field_197974_c);
         long ☃xx = ☃ - this.field_204871_d;
         if (☃x < 0L) {
            if (GuiScreen.func_146271_m()) {
               MemoryUtil.memSet(0L, 0, 1L);
            }

            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
         }

         if (☃xx >= 1000L) {
            if (this.field_204872_e == 0L) {
               this.func_197964_a("debug.crash.message");
            } else {
               this.func_204869_b("debug.crash.warning", MathHelper.func_76123_f((float)☃x / 1000.0F));
            }

            this.field_204871_d = ☃;
            ++this.field_204872_e;
         }
      }
   }
}
