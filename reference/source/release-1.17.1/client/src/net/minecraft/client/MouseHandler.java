package net.minecraft.client;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.InputConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.lwjgl.glfw.GLFWDropCallback;

public class MouseHandler {
   private final Minecraft minecraft;
   private boolean isLeftPressed;
   private boolean isMiddlePressed;
   private boolean isRightPressed;
   private double xpos;
   private double ypos;
   private int fakeRightMouse;
   private int activeButton = -1;
   private boolean ignoreFirstMove = true;
   private int clickDepth;
   private double mousePressedTime;
   private final SmoothDouble smoothTurnX = new SmoothDouble();
   private final SmoothDouble smoothTurnY = new SmoothDouble();
   private double accumulatedDX;
   private double accumulatedDY;
   private double accumulatedScroll;
   private double lastMouseEventTime = Double.MIN_VALUE;
   private boolean mouseGrabbed;

   public MouseHandler(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   private void onPress(long var1, int var3, int var4, int var5) {
      if (â˜ƒ == this.minecraft.getWindow().getWindow()) {
         boolean â˜ƒ = â˜ƒ == 1;
         if (Minecraft.ON_OSX && â˜ƒ == 0) {
            if (â˜ƒ) {
               if ((â˜ƒ & 2) == 2) {
                  â˜ƒ = 1;
                  ++this.fakeRightMouse;
               }
            } else if (this.fakeRightMouse > 0) {
               â˜ƒ = 1;
               --this.fakeRightMouse;
            }
         }

         int â˜ƒ = â˜ƒ;
         if (â˜ƒ) {
            if (this.minecraft.options.touchscreen && this.clickDepth++ > 0) {
               return;
            }

            this.activeButton = â˜ƒ;
            this.mousePressedTime = Blaze3D.getTime();
         } else if (this.activeButton != -1) {
            if (this.minecraft.options.touchscreen && --this.clickDepth > 0) {
               return;
            }

            this.activeButton = -1;
         }

         boolean[] â˜ƒ = new boolean[]{false};
         if (this.minecraft.getOverlay() == null) {
            if (this.minecraft.screen == null) {
               if (!this.mouseGrabbed && â˜ƒ) {
                  this.grabMouse();
               }
            } else {
               double â˜ƒx = this.xpos * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
               double â˜ƒxx = this.ypos * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
               Screen â˜ƒxxx = this.minecraft.screen;
               if (â˜ƒ) {
                  â˜ƒxxx.afterMouseAction();
                  Screen.wrapScreenError(() -> â˜ƒ[0] = â˜ƒ.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ), "mouseClicked event handler", â˜ƒxxx.getClass().getCanonicalName());
               } else {
                  Screen.wrapScreenError(() -> â˜ƒ[0] = â˜ƒ.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ), "mouseReleased event handler", â˜ƒxxx.getClass().getCanonicalName());
               }
            }
         }

         if (!â˜ƒ[0] && (this.minecraft.screen == null || this.minecraft.screen.passEvents) && this.minecraft.getOverlay() == null) {
            if (â˜ƒ == 0) {
               this.isLeftPressed = â˜ƒ;
            } else if (â˜ƒ == 2) {
               this.isMiddlePressed = â˜ƒ;
            } else if (â˜ƒ == 1) {
               this.isRightPressed = â˜ƒ;
            }

            KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(â˜ƒ), â˜ƒ);
            if (â˜ƒ) {
               if (this.minecraft.player.isSpectator() && â˜ƒ == 2) {
                  this.minecraft.gui.getSpectatorGui().onMouseMiddleClick();
               } else {
                  KeyMapping.click(InputConstants.Type.MOUSE.getOrCreate(â˜ƒ));
               }
            }
         }
      }
   }

   private void onScroll(long var1, double var3, double var5) {
      if (â˜ƒ == Minecraft.getInstance().getWindow().getWindow()) {
         double â˜ƒ = (this.minecraft.options.discreteMouseScroll ? Math.signum(â˜ƒ) : â˜ƒ) * this.minecraft.options.mouseWheelSensitivity;
         if (this.minecraft.getOverlay() == null) {
            if (this.minecraft.screen != null) {
               double â˜ƒx = this.xpos * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
               double â˜ƒxx = this.ypos * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
               this.minecraft.screen.mouseScrolled(â˜ƒx, â˜ƒxx, â˜ƒ);
               this.minecraft.screen.afterMouseAction();
            } else if (this.minecraft.player != null) {
               if (this.accumulatedScroll != 0.0 && Math.signum(â˜ƒ) != Math.signum(this.accumulatedScroll)) {
                  this.accumulatedScroll = 0.0;
               }

               this.accumulatedScroll += â˜ƒ;
               float â˜ƒx = (float)((int)this.accumulatedScroll);
               if (â˜ƒx == 0.0F) {
                  return;
               }

               this.accumulatedScroll -= (double)â˜ƒx;
               if (this.minecraft.player.isSpectator()) {
                  if (this.minecraft.gui.getSpectatorGui().isMenuActive()) {
                     this.minecraft.gui.getSpectatorGui().onMouseScrolled((double)(-â˜ƒx));
                  } else {
                     float â˜ƒx = Mth.clamp(this.minecraft.player.getAbilities().getFlyingSpeed() + â˜ƒx * 0.005F, 0.0F, 0.2F);
                     this.minecraft.player.getAbilities().setFlyingSpeed(â˜ƒx);
                  }
               } else {
                  this.minecraft.player.getInventory().swapPaint((double)â˜ƒx);
               }
            }
         }
      }
   }

   private void onDrop(long var1, List<Path> var3) {
      if (this.minecraft.screen != null) {
         this.minecraft.screen.onFilesDrop(â˜ƒ);
      }
   }

   public void setup(long var1) {
      InputConstants.setupMouseCallbacks(
         â˜ƒ,
         (var1x, var3, var5) -> this.minecraft.execute(() -> this.onMove(var1x, var3, var5)),
         (var1x, var3, var4, var5) -> this.minecraft.execute(() -> this.onPress(var1x, var3, var4, var5)),
         (var1x, var3, var5) -> this.minecraft.execute(() -> this.onScroll(var1x, var3, var5)),
         (var1x, var3, var4) -> {
            Path[] â˜ƒ = new Path[var3];
   
            for(int â˜ƒx = 0; â˜ƒx < var3; ++â˜ƒx) {
               â˜ƒ[â˜ƒx] = Paths.get(GLFWDropCallback.getName(var4, â˜ƒx));
            }
   
            this.minecraft.execute(() -> this.onDrop(var1x, Arrays.asList(â˜ƒ)));
         }
      );
   }

   private void onMove(long var1, double var3, double var5) {
      if (â˜ƒ == Minecraft.getInstance().getWindow().getWindow()) {
         if (this.ignoreFirstMove) {
            this.xpos = â˜ƒ;
            this.ypos = â˜ƒ;
            this.ignoreFirstMove = false;
         }

         Screen â˜ƒ = this.minecraft.screen;
         if (â˜ƒ != null && this.minecraft.getOverlay() == null) {
            double â˜ƒx = â˜ƒ * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
            double â˜ƒxx = â˜ƒ * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
            Screen.wrapScreenError(() -> â˜ƒ.mouseMoved(â˜ƒ, â˜ƒ), "mouseMoved event handler", â˜ƒ.getClass().getCanonicalName());
            if (this.activeButton != -1 && this.mousePressedTime > 0.0) {
               double â˜ƒxxx = (â˜ƒ - this.xpos) * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
               double â˜ƒxxxx = (â˜ƒ - this.ypos)
                  * (double)this.minecraft.getWindow().getGuiScaledHeight()
                  / (double)this.minecraft.getWindow().getScreenHeight();
               Screen.wrapScreenError(
                  () -> â˜ƒ.mouseDragged(â˜ƒ, â˜ƒ, this.activeButton, â˜ƒ, â˜ƒ), "mouseDragged event handler", â˜ƒ.getClass().getCanonicalName()
               );
            }

            â˜ƒ.afterMouseMove();
         }

         this.minecraft.getProfiler().push("mouse");
         if (this.isMouseGrabbed() && this.minecraft.isWindowActive()) {
            this.accumulatedDX += â˜ƒ - this.xpos;
            this.accumulatedDY += â˜ƒ - this.ypos;
         }

         this.turnPlayer();
         this.xpos = â˜ƒ;
         this.ypos = â˜ƒ;
         this.minecraft.getProfiler().pop();
      }
   }

   public void turnPlayer() {
      double â˜ƒ = Blaze3D.getTime();
      double â˜ƒx = â˜ƒ - this.lastMouseEventTime;
      this.lastMouseEventTime = â˜ƒ;
      if (this.isMouseGrabbed() && this.minecraft.isWindowActive()) {
         double â˜ƒxxxx = this.minecraft.options.sensitivity * 0.6F + 0.2F;
         double â˜ƒxxxxx = â˜ƒxxxx * â˜ƒxxxx * â˜ƒxxxx;
         double â˜ƒxxxxxx = â˜ƒxxxxx * 8.0;
         double â˜ƒxx;
         double â˜ƒxxx;
         if (this.minecraft.options.smoothCamera) {
            double â˜ƒxxxxxxx = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * â˜ƒxxxxxx, â˜ƒx * â˜ƒxxxxxx);
            double â˜ƒxxxxxxxx = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * â˜ƒxxxxxx, â˜ƒx * â˜ƒxxxxxx);
            â˜ƒxx = â˜ƒxxxxxxx;
            â˜ƒxxx = â˜ƒxxxxxxxx;
         } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
            this.smoothTurnX.reset();
            this.smoothTurnY.reset();
            â˜ƒxx = this.accumulatedDX * â˜ƒxxxxx;
            â˜ƒxxx = this.accumulatedDY * â˜ƒxxxxx;
         } else {
            this.smoothTurnX.reset();
            this.smoothTurnY.reset();
            â˜ƒxx = this.accumulatedDX * â˜ƒxxxxxx;
            â˜ƒxxx = this.accumulatedDY * â˜ƒxxxxxx;
         }

         this.accumulatedDX = 0.0;
         this.accumulatedDY = 0.0;
         int â˜ƒxx = 1;
         if (this.minecraft.options.invertYMouse) {
            â˜ƒxx = -1;
         }

         this.minecraft.getTutorial().onMouse(â˜ƒxx, â˜ƒxxx);
         if (this.minecraft.player != null) {
            this.minecraft.player.turn(â˜ƒxx, â˜ƒxxx * (double)â˜ƒxx);
         }
      } else {
         this.accumulatedDX = 0.0;
         this.accumulatedDY = 0.0;
      }
   }

   public boolean isLeftPressed() {
      return this.isLeftPressed;
   }

   public boolean isMiddlePressed() {
      return this.isMiddlePressed;
   }

   public boolean isRightPressed() {
      return this.isRightPressed;
   }

   public double xpos() {
      return this.xpos;
   }

   public double ypos() {
      return this.ypos;
   }

   public void setIgnoreFirstMove() {
      this.ignoreFirstMove = true;
   }

   public boolean isMouseGrabbed() {
      return this.mouseGrabbed;
   }

   public void grabMouse() {
      if (this.minecraft.isWindowActive()) {
         if (!this.mouseGrabbed) {
            if (!Minecraft.ON_OSX) {
               KeyMapping.setAll();
            }

            this.mouseGrabbed = true;
            this.xpos = (double)(this.minecraft.getWindow().getScreenWidth() / 2);
            this.ypos = (double)(this.minecraft.getWindow().getScreenHeight() / 2);
            InputConstants.grabOrReleaseMouse(this.minecraft.getWindow().getWindow(), 212995, this.xpos, this.ypos);
            this.minecraft.setScreen(null);
            this.minecraft.missTime = 10000;
            this.ignoreFirstMove = true;
         }
      }
   }

   public void releaseMouse() {
      if (this.mouseGrabbed) {
         this.mouseGrabbed = false;
         this.xpos = (double)(this.minecraft.getWindow().getScreenWidth() / 2);
         this.ypos = (double)(this.minecraft.getWindow().getScreenHeight() / 2);
         InputConstants.grabOrReleaseMouse(this.minecraft.getWindow().getWindow(), 212993, this.xpos, this.ypos);
      }
   }

   public void cursorEntered() {
      this.ignoreFirstMove = true;
   }
}
