package net.minecraft.client.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.FileUtils;

public class GuiWorldEdit extends GuiScreen {
   private GuiButton field_195327_a;
   private final GuiYesNoCallback field_184858_a;
   private GuiTextField field_184859_f;
   private final String field_184860_g;

   public GuiWorldEdit(GuiYesNoCallback var1, String var2) {
      this.field_184858_a = ☃;
      this.field_184860_g = ☃;
   }

   @Override
   public void func_73876_c() {
      this.field_184859_f.func_146178_a();
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      GuiButton ☃ = this.func_189646_b(
         new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 24 + 5, I18n.func_135052_a("selectWorld.edit.resetIcon")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               ISaveFormat ☃ = GuiWorldEdit.this.field_146297_k.func_71359_d();
               FileUtils.deleteQuietly(☃.func_186352_b(GuiWorldEdit.this.field_184860_g, "icon.png"));
               this.field_146124_l = false;
            }
         }
      );
      this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 48 + 5, I18n.func_135052_a("selectWorld.edit.openFolder")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            ISaveFormat ☃ = GuiWorldEdit.this.field_146297_k.func_71359_d();
            Util.func_110647_a().func_195641_a(☃.func_186352_b(GuiWorldEdit.this.field_184860_g, "icon.png").getParentFile());
         }
      });
      this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72 + 5, I18n.func_135052_a("selectWorld.edit.backup")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            ISaveFormat ☃ = GuiWorldEdit.this.field_146297_k.func_71359_d();
            GuiWorldEdit.func_200212_a(☃, GuiWorldEdit.this.field_184860_g);
            GuiWorldEdit.this.field_184858_a.confirmResult(false, 0);
         }
      });
      this.func_189646_b(
         new GuiButton(6, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 5, I18n.func_135052_a("selectWorld.edit.backupFolder")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               ISaveFormat ☃ = GuiWorldEdit.this.field_146297_k.func_71359_d();
               Path ☃x = ☃.func_197712_e();
   
               try {
                  Files.createDirectories(Files.exists(☃x, new LinkOption[0]) ? ☃x.toRealPath() : ☃x);
               } catch (IOException var8) {
                  throw new RuntimeException(var8);
               }
   
               Util.func_110647_a().func_195641_a(☃x.toFile());
            }
         }
      );
      this.func_189646_b(
         new GuiButton(7, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 5, I18n.func_135052_a("selectWorld.edit.optimize")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiWorldEdit.this.field_146297_k
                  .func_147108_a(
                     new GuiConfirmBackup(
                        GuiWorldEdit.this,
                        var1x -> {
                           if (var1x) {
                              GuiWorldEdit.func_200212_a(GuiWorldEdit.this.field_146297_k.func_71359_d(), GuiWorldEdit.this.field_184860_g);
                           }
            
                           GuiWorldEdit.this.field_146297_k
                              .func_147108_a(
                                 new GuiOptimizeWorld(
                                    GuiWorldEdit.this.field_184858_a, GuiWorldEdit.this.field_184860_g, GuiWorldEdit.this.field_146297_k.func_71359_d()
                                 )
                              );
                        },
                        I18n.func_135052_a("optimizeWorld.confirm.title"),
                        I18n.func_135052_a("optimizeWorld.confirm.description")
                     )
                  );
            }
         }
      );
      this.field_195327_a = this.func_189646_b(
         new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 144 + 5, 98, 20, I18n.func_135052_a("selectWorld.edit.save")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiWorldEdit.this.func_195317_h();
            }
         }
      );
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 + 2, this.field_146295_m / 4 + 144 + 5, 98, 20, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiWorldEdit.this.field_184858_a.confirmResult(false, 0);
         }
      });
      ☃.field_146124_l = this.field_146297_k.func_71359_d().func_186352_b(this.field_184860_g, "icon.png").isFile();
      ISaveFormat ☃x = this.field_146297_k.func_71359_d();
      WorldInfo ☃xx = ☃x.func_75803_c(this.field_184860_g);
      String ☃xxx = ☃xx == null ? "" : ☃xx.func_76065_j();
      this.field_184859_f = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 100, 53, 200, 20);
      this.field_184859_f.func_146195_b(true);
      this.field_184859_f.func_146180_a(☃xxx);
      this.field_195124_j.add(this.field_184859_f);
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_184859_f.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.field_184859_f.func_146180_a(☃);
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   private void func_195317_h() {
      ISaveFormat ☃ = this.field_146297_k.func_71359_d();
      ☃.func_75806_a(this.field_184860_g, this.field_184859_f.func_146179_b().trim());
      this.field_184858_a.confirmResult(true, 0);
   }

   public static void func_200212_a(ISaveFormat var0, String var1) {
      GuiToast ☃ = Minecraft.func_71410_x().func_193033_an();
      long ☃x = 0L;
      IOException ☃xx = null;

      try {
         ☃x = ☃.func_197713_h(☃);
      } catch (IOException var8) {
         ☃xx = var8;
      }

      ITextComponent ☃xxx;
      ITextComponent ☃xxxx;
      if (☃xx != null) {
         ☃xxx = new TextComponentTranslation("selectWorld.edit.backupFailed");
         ☃xxxx = new TextComponentString(☃xx.getMessage());
      } else {
         ☃xxx = new TextComponentTranslation("selectWorld.edit.backupCreated", ☃);
         ☃xxxx = new TextComponentTranslation("selectWorld.edit.backupSize", MathHelper.func_76143_f((double)☃x / 1048576.0));
      }

      ☃.func_192988_a(new SystemToast(SystemToast.Type.WORLD_BACKUP, ☃xxx, ☃xxxx));
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (this.field_184859_f.charTyped(☃, ☃)) {
         this.field_195327_a.field_146124_l = !this.field_184859_f.func_146179_b().trim().isEmpty();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.field_184859_f.keyPressed(☃, ☃, ☃)) {
         this.field_195327_a.field_146124_l = !this.field_184859_f.func_146179_b().trim().isEmpty();
         return true;
      } else if (☃ != 257 && ☃ != 335) {
         return false;
      } else {
         this.func_195317_h();
         return true;
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("selectWorld.edit.title"), this.field_146294_l / 2, 20, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.enterName"), this.field_146294_l / 2 - 100, 40, 10526880);
      this.field_184859_f.func_195608_a(☃, ☃, ☃);
      super.func_73863_a(☃, ☃, ☃);
   }
}
