package net.minecraft.client.gui.toasts;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;

public class SystemToast implements IToast {
   private final SystemToast.Type field_193659_c;
   private String field_193660_d;
   private String field_193661_e;
   private long field_193662_f;
   private boolean field_193663_g;

   public SystemToast(SystemToast.Type var1, ITextComponent var2, @Nullable ITextComponent var3) {
      this.field_193659_c = ☃;
      this.field_193660_d = ☃.getString();
      this.field_193661_e = ☃ == null ? null : ☃.getString();
   }

   @Override
   public IToast.Visibility func_193653_a(GuiToast var1, long var2) {
      if (this.field_193663_g) {
         this.field_193662_f = ☃;
         this.field_193663_g = false;
      }

      ☃.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      ☃.func_73729_b(0, 0, 0, 64, 160, 32);
      if (this.field_193661_e == null) {
         ☃.func_192989_b().field_71466_p.func_211126_b(this.field_193660_d, 18.0F, 12.0F, -256);
      } else {
         ☃.func_192989_b().field_71466_p.func_211126_b(this.field_193660_d, 18.0F, 7.0F, -256);
         ☃.func_192989_b().field_71466_p.func_211126_b(this.field_193661_e, 18.0F, 18.0F, -1);
      }

      return ☃ - this.field_193662_f < 5000L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
   }

   public void func_193656_a(ITextComponent var1, @Nullable ITextComponent var2) {
      this.field_193660_d = ☃.getString();
      this.field_193661_e = ☃ == null ? null : ☃.getString();
      this.field_193663_g = true;
   }

   public SystemToast.Type func_193652_b() {
      return this.field_193659_c;
   }

   public static void func_193657_a(GuiToast var0, SystemToast.Type var1, ITextComponent var2, @Nullable ITextComponent var3) {
      SystemToast ☃ = ☃.func_192990_a(SystemToast.class, ☃);
      if (☃ == null) {
         ☃.func_192988_a(new SystemToast(☃, ☃, ☃));
      } else {
         ☃.func_193656_a(☃, ☃);
      }
   }

   public static enum Type {
      TUTORIAL_HINT,
      NARRATOR_TOGGLE,
      WORLD_BACKUP;
   }
}
