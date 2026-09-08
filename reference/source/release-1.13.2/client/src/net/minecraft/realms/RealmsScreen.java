package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.util.UUIDTypeAdapter;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class RealmsScreen extends RealmsGuiEventListener {
   public static final int SKIN_HEAD_U = 8;
   public static final int SKIN_HEAD_V = 8;
   public static final int SKIN_HEAD_WIDTH = 8;
   public static final int SKIN_HEAD_HEIGHT = 8;
   public static final int SKIN_HAT_U = 40;
   public static final int SKIN_HAT_V = 8;
   public static final int SKIN_HAT_WIDTH = 8;
   public static final int SKIN_HAT_HEIGHT = 8;
   public static final int SKIN_TEX_WIDTH = 64;
   public static final int SKIN_TEX_HEIGHT = 64;
   private Minecraft minecraft;
   public int width;
   public int height;
   private final GuiScreenRealmsProxy proxy = new GuiScreenRealmsProxy(this);

   public GuiScreenRealmsProxy getProxy() {
      return this.proxy;
   }

   public void init() {
   }

   public void init(Minecraft var1, int var2, int var3) {
      this.minecraft = ☃;
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4) {
      this.proxy.func_154325_a(☃, ☃, ☃, ☃);
   }

   public int draw(String var1, int var2, int var3, int var4, boolean var5) {
      return this.proxy.func_209208_b(☃, ☃, ☃, ☃, ☃);
   }

   public void drawString(String var1, int var2, int var3, int var4) {
      this.drawString(☃, ☃, ☃, ☃, true);
   }

   public void drawString(String var1, int var2, int var3, int var4, boolean var5) {
      this.proxy.func_207734_a(☃, ☃, ☃, ☃, false);
   }

   public void blit(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.func_73729_b(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      Gui.func_152125_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      Gui.func_146110_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void fillGradient(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.func_73733_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void renderBackground() {
      this.proxy.func_146276_q_();
   }

   public boolean isPauseScreen() {
      return this.proxy.func_73868_f();
   }

   public void renderBackground(int var1) {
      this.proxy.func_146270_b(☃);
   }

   public void render(int var1, int var2, float var3) {
      for(int ☃ = 0; ☃ < this.proxy.func_154320_j().size(); ++☃) {
         ((RealmsButton)this.proxy.func_154320_j().get(☃)).render(☃, ☃, ☃);
      }
   }

   public void renderTooltip(ItemStack var1, int var2, int var3) {
      this.proxy.func_146285_a(☃, ☃, ☃);
   }

   public void renderTooltip(String var1, int var2, int var3) {
      this.proxy.func_146279_a(☃, ☃, ☃);
   }

   public void renderTooltip(List<String> var1, int var2, int var3) {
      this.proxy.func_146283_a(☃, ☃, ☃);
   }

   public static void bindFace(String var0, String var1) {
      ResourceLocation ☃ = AbstractClientPlayer.func_110311_f(☃);
      if (☃ == null) {
         ☃ = DefaultPlayerSkin.func_177334_a(UUIDTypeAdapter.fromString(☃));
      }

      AbstractClientPlayer.func_110304_a(☃, ☃);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(☃);
   }

   public static void bind(String var0) {
      ResourceLocation ☃ = new ResourceLocation(☃);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(☃);
   }

   public void tick() {
   }

   public int width() {
      return this.proxy.field_146294_l;
   }

   public int height() {
      return this.proxy.field_146295_m;
   }

   public ListenableFuture<Object> threadSafeSetScreen(RealmsScreen var1) {
      return this.minecraft.func_152344_a(() -> Realms.setScreen(☃));
   }

   public int fontLineHeight() {
      return this.proxy.func_154329_h();
   }

   public int fontWidth(String var1) {
      return this.proxy.func_207731_c(☃);
   }

   public void fontDrawShadow(String var1, int var2, int var3, int var4) {
      this.proxy.func_207728_b(☃, ☃, ☃, ☃);
   }

   public List<String> fontSplit(String var1, int var2) {
      return this.proxy.func_154323_a(☃, ☃);
   }

   public void childrenClear() {
      this.proxy.func_207735_j();
   }

   public void addWidget(RealmsGuiEventListener var1) {
      this.proxy.func_207730_a(☃);
   }

   public void removeWidget(RealmsGuiEventListener var1) {
      this.proxy.func_207733_b(☃);
   }

   public boolean hasWidget(RealmsGuiEventListener var1) {
      return this.proxy.func_212332_c(☃);
   }

   public void buttonsAdd(RealmsButton var1) {
      this.proxy.func_154327_a(☃);
   }

   public List<RealmsButton> buttons() {
      return this.proxy.func_154320_j();
   }

   protected void buttonsClear() {
      this.proxy.func_207729_m();
   }

   protected void focusOn(RealmsGuiEventListener var1) {
      this.proxy.func_205725_b(☃.getProxy());
   }

   public void focusNext() {
      this.proxy.func_207714_t();
   }

   public RealmsEditBox newEditBox(int var1, int var2, int var3, int var4, int var5) {
      return new RealmsEditBox(☃, ☃, ☃, ☃, ☃);
   }

   public void confirmResult(boolean var1, int var2) {
   }

   public static String getLocalizedString(String var0) {
      return I18n.func_135052_a(☃);
   }

   public static String getLocalizedString(String var0, Object... var1) {
      return I18n.func_135052_a(☃, ☃);
   }

   public List<String> getLocalizedStringWithLineWidth(String var1, int var2) {
      return this.minecraft.field_71466_p.func_78271_c(I18n.func_135052_a(☃), ☃);
   }

   public RealmsAnvilLevelStorageSource getLevelStorageSource() {
      return new RealmsAnvilLevelStorageSource(Minecraft.func_71410_x().func_71359_d());
   }

   public void removed() {
   }

   protected void removeButton(RealmsButton var1) {
      this.proxy.func_207732_b(☃);
   }

   protected void setKeyboardHandlerSendRepeatsToGui(boolean var1) {
      this.minecraft.field_195559_v.func_197967_a(☃);
   }

   protected boolean isKeyDown(int var1) {
      return InputMappings.func_197956_a(☃);
   }
}
