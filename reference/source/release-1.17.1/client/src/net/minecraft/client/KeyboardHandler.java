package net.minecraft.client;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.ClipboardManager;
import com.mojang.blaze3d.platform.InputConstants;
import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class KeyboardHandler {
   public static final int DEBUG_CRASH_TIME = 10000;
   private final Minecraft minecraft;
   private boolean sendRepeatsToGui;
   private final ClipboardManager clipboardManager = new ClipboardManager();
   private long debugCrashKeyTime = -1L;
   private long debugCrashKeyReportedTime = -1L;
   private long debugCrashKeyReportedCount = -1L;
   private boolean handledDebugKey;

   public KeyboardHandler(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   private boolean handleChunkDebugKeys(int var1) {
      switch(â˜ƒ) {
         case 69:
            this.minecraft.chunkPath = !this.minecraft.chunkPath;
            this.debugFeedback("ChunkPath: {0}", this.minecraft.chunkPath ? "shown" : "hidden");
            return true;
         case 76:
            this.minecraft.smartCull = !this.minecraft.smartCull;
            this.debugFeedback("SmartCull: {0}", this.minecraft.smartCull ? "enabled" : "disabled");
            return true;
         case 85:
            if (Screen.hasShiftDown()) {
               this.minecraft.levelRenderer.killFrustum();
               this.debugFeedback("Killed frustum");
            } else {
               this.minecraft.levelRenderer.captureFrustum();
               this.debugFeedback("Captured frustum");
            }

            return true;
         case 86:
            this.minecraft.chunkVisibility = !this.minecraft.chunkVisibility;
            this.debugFeedback("ChunkVisibility: {0}", this.minecraft.chunkVisibility ? "enabled" : "disabled");
            return true;
         case 87:
            this.minecraft.wireframe = !this.minecraft.wireframe;
            this.debugFeedback("WireFrame: {0}", this.minecraft.wireframe ? "enabled" : "disabled");
            return true;
         default:
            return false;
      }
   }

   private void debugComponent(ChatFormatting var1, Component var2) {
      this.minecraft
         .gui
         .getChat()
         .addMessage(
            new TextComponent("")
               .append(new TranslatableComponent("debug.prefix").withStyle(new ChatFormatting[]{â˜ƒ, ChatFormatting.BOLD}))
               .append(" ")
               .append(â˜ƒ)
         );
   }

   private void debugFeedbackComponent(Component var1) {
      this.debugComponent(ChatFormatting.YELLOW, â˜ƒ);
   }

   private void debugFeedbackTranslated(String var1, Object... var2) {
      this.debugFeedbackComponent(new TranslatableComponent(â˜ƒ, â˜ƒ));
   }

   private void debugWarningTranslated(String var1, Object... var2) {
      this.debugComponent(ChatFormatting.RED, new TranslatableComponent(â˜ƒ, â˜ƒ));
   }

   private void debugFeedback(String var1, Object... var2) {
      this.debugFeedbackComponent(new TextComponent(MessageFormat.format(â˜ƒ, â˜ƒ)));
   }

   private boolean handleDebugKeys(int var1) {
      if (this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L) {
         return true;
      } else {
         switch(â˜ƒ) {
            case 65:
               this.minecraft.levelRenderer.allChanged();
               this.debugFeedbackTranslated("debug.reload_chunks.message");
               return true;
            case 66: {
               boolean â˜ƒ = !this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes();
               this.minecraft.getEntityRenderDispatcher().setRenderHitBoxes(â˜ƒ);
               this.debugFeedbackTranslated(â˜ƒ ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
               return true;
            }
            case 67:
               if (this.minecraft.player.isReducedDebugInfo()) {
                  return false;
               } else {
                  ClientPacketListener â˜ƒx = this.minecraft.player.connection;
                  if (â˜ƒx == null) {
                     return false;
                  }

                  this.debugFeedbackTranslated("debug.copy_location.message");
                  this.setClipboard(
                     String.format(
                        Locale.ROOT,
                        "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f",
                        this.minecraft.player.level.dimension().location(),
                        this.minecraft.player.getX(),
                        this.minecraft.player.getY(),
                        this.minecraft.player.getZ(),
                        this.minecraft.player.getYRot(),
                        this.minecraft.player.getXRot()
                     )
                  );
                  return true;
               }
            case 68:
               if (this.minecraft.gui != null) {
                  this.minecraft.gui.getChat().clearMessages(false);
               }

               return true;
            case 70:
               Option.RENDER_DISTANCE
                  .set(
                     this.minecraft.options,
                     Mth.clamp(
                        (double)(this.minecraft.options.renderDistance + (Screen.hasShiftDown() ? -1 : 1)),
                        Option.RENDER_DISTANCE.getMinValue(),
                        Option.RENDER_DISTANCE.getMaxValue()
                     )
                  );
               this.debugFeedbackTranslated("debug.cycle_renderdistance.message", this.minecraft.options.renderDistance);
               return true;
            case 71: {
               boolean â˜ƒ = this.minecraft.debugRenderer.switchRenderChunkborder();
               this.debugFeedbackTranslated(â˜ƒ ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
               return true;
            }
            case 72:
               this.minecraft.options.advancedItemTooltips = !this.minecraft.options.advancedItemTooltips;
               this.debugFeedbackTranslated(this.minecraft.options.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
               this.minecraft.options.save();
               return true;
            case 73:
               if (!this.minecraft.player.isReducedDebugInfo()) {
                  this.copyRecreateCommand(this.minecraft.player.hasPermissions(2), !Screen.hasShiftDown());
               }

               return true;
            case 76:
               if (this.minecraft.debugClientMetricsStart(this::debugFeedbackComponent)) {
                  this.debugFeedbackTranslated("debug.profiling.start", 10);
               }

               return true;
            case 78:
               if (!this.minecraft.player.hasPermissions(2)) {
                  this.debugFeedbackTranslated("debug.creative_spectator.error");
               } else if (!this.minecraft.player.isSpectator()) {
                  this.minecraft.player.chat("/gamemode spectator");
               } else {
                  this.minecraft
                     .player
                     .chat("/gamemode " + ((GameType)MoreObjects.firstNonNull(this.minecraft.gameMode.getPreviousPlayerMode(), GameType.CREATIVE)).getName());
               }

               return true;
            case 80:
               this.minecraft.options.pauseOnLostFocus = !this.minecraft.options.pauseOnLostFocus;
               this.minecraft.options.save();
               this.debugFeedbackTranslated(this.minecraft.options.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off");
               return true;
            case 81: {
               this.debugFeedbackTranslated("debug.help.message");
               ChatComponent â˜ƒ = this.minecraft.gui.getChat();
               â˜ƒ.addMessage(new TranslatableComponent("debug.reload_chunks.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.show_hitboxes.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.copy_location.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.clear_chat.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.cycle_renderdistance.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.chunk_boundaries.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.advanced_tooltips.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.inspect.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.profiling.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.creative_spectator.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.pause_focus.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.help.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.reload_resourcepacks.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.pause.help"));
               â˜ƒ.addMessage(new TranslatableComponent("debug.gamemodes.help"));
               return true;
            }
            case 84:
               this.debugFeedbackTranslated("debug.reload_resourcepacks.message");
               this.minecraft.reloadResourcePacks();
               return true;
            case 293:
               if (!this.minecraft.player.hasPermissions(2)) {
                  this.debugFeedbackTranslated("debug.gamemodes.error");
               } else {
                  this.minecraft.setScreen(new GameModeSwitcherScreen());
               }

               return true;
            default:
               return false;
         }
      }
   }

   private void copyRecreateCommand(boolean var1, boolean var2) {
      HitResult â˜ƒ = this.minecraft.hitResult;
      if (â˜ƒ != null) {
         switch(â˜ƒ.getType()) {
            case BLOCK:
               BlockPos â˜ƒx = ((BlockHitResult)â˜ƒ).getBlockPos();
               BlockState â˜ƒxx = this.minecraft.player.level.getBlockState(â˜ƒx);
               if (â˜ƒ) {
                  if (â˜ƒ) {
                     this.minecraft.player.connection.getDebugQueryHandler().queryBlockEntityTag(â˜ƒx, var3x -> {
                        this.copyCreateBlockCommand(â˜ƒ, â˜ƒ, var3x);
                        this.debugFeedbackTranslated("debug.inspect.server.block");
                     });
                  } else {
                     BlockEntity â˜ƒxxx = this.minecraft.player.level.getBlockEntity(â˜ƒx);
                     CompoundTag â˜ƒxxxx = â˜ƒxxx != null ? â˜ƒxxx.save(new CompoundTag()) : null;
                     this.copyCreateBlockCommand(â˜ƒxx, â˜ƒx, â˜ƒxxxx);
                     this.debugFeedbackTranslated("debug.inspect.client.block");
                  }
               } else {
                  this.copyCreateBlockCommand(â˜ƒxx, â˜ƒx, null);
                  this.debugFeedbackTranslated("debug.inspect.client.block");
               }
               break;
            case ENTITY:
               Entity â˜ƒx = ((EntityHitResult)â˜ƒ).getEntity();
               ResourceLocation â˜ƒxx = Registry.ENTITY_TYPE.getKey(â˜ƒx.getType());
               if (â˜ƒ) {
                  if (â˜ƒ) {
                     this.minecraft.player.connection.getDebugQueryHandler().queryEntityTag(â˜ƒx.getId(), var3x -> {
                        this.copyCreateEntityCommand(â˜ƒ, â˜ƒ.position(), var3x);
                        this.debugFeedbackTranslated("debug.inspect.server.entity");
                     });
                  } else {
                     CompoundTag â˜ƒxxx = â˜ƒx.saveWithoutId(new CompoundTag());
                     this.copyCreateEntityCommand(â˜ƒxx, â˜ƒx.position(), â˜ƒxxx);
                     this.debugFeedbackTranslated("debug.inspect.client.entity");
                  }
               } else {
                  this.copyCreateEntityCommand(â˜ƒxx, â˜ƒx.position(), null);
                  this.debugFeedbackTranslated("debug.inspect.client.entity");
               }
         }
      }
   }

   private void copyCreateBlockCommand(BlockState var1, BlockPos var2, @Nullable CompoundTag var3) {
      if (â˜ƒ != null) {
         â˜ƒ.remove("x");
         â˜ƒ.remove("y");
         â˜ƒ.remove("z");
         â˜ƒ.remove("id");
      }

      StringBuilder â˜ƒ = new StringBuilder(BlockStateParser.serialize(â˜ƒ));
      if (â˜ƒ != null) {
         â˜ƒ.append(â˜ƒ);
      }

      String â˜ƒ = String.format(Locale.ROOT, "/setblock %d %d %d %s", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ);
      this.setClipboard(â˜ƒ);
   }

   private void copyCreateEntityCommand(ResourceLocation var1, Vec3 var2, @Nullable CompoundTag var3) {
      String â˜ƒ;
      if (â˜ƒ != null) {
         â˜ƒ.remove("UUID");
         â˜ƒ.remove("Pos");
         â˜ƒ.remove("Dimension");
         String â˜ƒx = NbtUtils.toPrettyComponent(â˜ƒ).getString();
         â˜ƒ = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", â˜ƒ.toString(), â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒx);
      } else {
         â˜ƒ = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", â˜ƒ.toString(), â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      }

      this.setClipboard(â˜ƒ);
   }

   public void keyPress(long var1, int var3, int var4, int var5, int var6) {
      if (â˜ƒ == this.minecraft.getWindow().getWindow()) {
         if (this.debugCrashKeyTime > 0L) {
            if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 67)
               || !InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) {
               this.debugCrashKeyTime = -1L;
            }
         } else if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 67)
            && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) {
            this.handledDebugKey = true;
            this.debugCrashKeyTime = Util.getMillis();
            this.debugCrashKeyReportedTime = Util.getMillis();
            this.debugCrashKeyReportedCount = 0L;
         }

         Screen â˜ƒ = this.minecraft.screen;
         if (â˜ƒ == 1 && (!(this.minecraft.screen instanceof ControlsScreen) || ((ControlsScreen)â˜ƒ).lastKeySelection <= Util.getMillis() - 20L)) {
            if (this.minecraft.options.keyFullscreen.matches(â˜ƒ, â˜ƒ)) {
               this.minecraft.getWindow().toggleFullScreen();
               this.minecraft.options.fullscreen = this.minecraft.getWindow().isFullscreen();
               this.minecraft.options.save();
               return;
            }

            if (this.minecraft.options.keyScreenshot.matches(â˜ƒ, â˜ƒ)) {
               if (Screen.hasControlDown()) {
               }

               Screenshot.grab(
                  this.minecraft.gameDirectory,
                  this.minecraft.getMainRenderTarget(),
                  var1x -> this.minecraft.execute(() -> this.minecraft.gui.getChat().addMessage(var1x))
               );
               return;
            }
         }

         if (NarratorChatListener.INSTANCE.isActive()) {
            boolean â˜ƒ = â˜ƒ == null || !(â˜ƒ.getFocused() instanceof EditBox) || !((EditBox)â˜ƒ.getFocused()).canConsumeInput();
            if (â˜ƒ != 0 && â˜ƒ == 66 && Screen.hasControlDown() && â˜ƒ) {
               boolean â˜ƒx = this.minecraft.options.narratorStatus == NarratorStatus.OFF;
               this.minecraft.options.narratorStatus = NarratorStatus.byId(this.minecraft.options.narratorStatus.getId() + 1);
               NarratorChatListener.INSTANCE.updateNarratorStatus(this.minecraft.options.narratorStatus);
               if (â˜ƒ instanceof SimpleOptionsSubScreen) {
                  ((SimpleOptionsSubScreen)â˜ƒ).updateNarratorButton();
               }

               if (â˜ƒx && â˜ƒ != null) {
                  â˜ƒ.narrationEnabled();
               }
            }
         }

         if (â˜ƒ != null) {
            boolean[] â˜ƒ = new boolean[]{false};
            Screen.wrapScreenError(() -> {
               if (â˜ƒ != 1 && (â˜ƒ != 2 || !this.sendRepeatsToGui)) {
                  if (â˜ƒ == 0) {
                     â˜ƒ[0] = â˜ƒ.keyReleased(â˜ƒ, â˜ƒ, â˜ƒ);
                  }
               } else {
                  â˜ƒ.afterKeyboardAction();
                  â˜ƒ[0] = â˜ƒ.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
               }
            }, "keyPressed event handler", â˜ƒ.getClass().getCanonicalName());
            if (â˜ƒ[0]) {
               return;
            }
         }

         if (this.minecraft.screen == null || this.minecraft.screen.passEvents) {
            InputConstants.Key â˜ƒ = InputConstants.getKey(â˜ƒ, â˜ƒ);
            if (â˜ƒ == 0) {
               KeyMapping.set(â˜ƒ, false);
               if (â˜ƒ == 292) {
                  if (this.handledDebugKey) {
                     this.handledDebugKey = false;
                  } else {
                     this.minecraft.options.renderDebug = !this.minecraft.options.renderDebug;
                     this.minecraft.options.renderDebugCharts = this.minecraft.options.renderDebug && Screen.hasShiftDown();
                     this.minecraft.options.renderFpsChart = this.minecraft.options.renderDebug && Screen.hasAltDown();
                  }
               }
            } else {
               if (â˜ƒ == 293 && this.minecraft.gameRenderer != null) {
                  this.minecraft.gameRenderer.togglePostEffect();
               }

               boolean â˜ƒ = false;
               if (this.minecraft.screen == null) {
                  if (â˜ƒ == 256) {
                     boolean â˜ƒx = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292);
                     this.minecraft.pauseGame(â˜ƒx);
                  }

                  â˜ƒ = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292) && this.handleDebugKeys(â˜ƒ);
                  this.handledDebugKey |= â˜ƒ;
                  if (â˜ƒ == 290) {
                     this.minecraft.options.hideGui = !this.minecraft.options.hideGui;
                  }
               }

               if (â˜ƒ) {
                  KeyMapping.set(â˜ƒ, false);
               } else {
                  KeyMapping.set(â˜ƒ, true);
                  KeyMapping.click(â˜ƒ);
               }

               if (this.minecraft.options.renderDebugCharts && â˜ƒ >= 48 && â˜ƒ <= 57) {
                  this.minecraft.debugFpsMeterKeyPress(â˜ƒ - 48);
               }
            }
         }
      }
   }

   private void charTyped(long var1, int var3, int var4) {
      if (â˜ƒ == this.minecraft.getWindow().getWindow()) {
         GuiEventListener â˜ƒ = this.minecraft.screen;
         if (â˜ƒ != null && this.minecraft.getOverlay() == null) {
            if (Character.charCount(â˜ƒ) == 1) {
               Screen.wrapScreenError(() -> â˜ƒ.charTyped((char)â˜ƒ, â˜ƒ), "charTyped event handler", â˜ƒ.getClass().getCanonicalName());
            } else {
               for(char â˜ƒx : Character.toChars(â˜ƒ)) {
                  Screen.wrapScreenError(() -> â˜ƒ.charTyped(â˜ƒ, â˜ƒ), "charTyped event handler", â˜ƒ.getClass().getCanonicalName());
               }
            }
         }
      }
   }

   public void setSendRepeatsToGui(boolean var1) {
      this.sendRepeatsToGui = â˜ƒ;
   }

   public void setup(long var1) {
      InputConstants.setupKeyboardCallbacks(
         â˜ƒ,
         (var1x, var3, var4, var5, var6) -> this.minecraft.execute(() -> this.keyPress(var1x, var3, var4, var5, var6)),
         (var1x, var3, var4) -> this.minecraft.execute(() -> this.charTyped(var1x, var3, var4))
      );
   }

   public String getClipboard() {
      return this.clipboardManager.getClipboard(this.minecraft.getWindow().getWindow(), (var1, var2) -> {
         if (var1 != 65545) {
            this.minecraft.getWindow().defaultErrorCallback(var1, var2);
         }
      });
   }

   public void setClipboard(String var1) {
      if (!â˜ƒ.isEmpty()) {
         this.clipboardManager.setClipboard(this.minecraft.getWindow().getWindow(), â˜ƒ);
      }
   }

   public void tick() {
      if (this.debugCrashKeyTime > 0L) {
         long â˜ƒ = Util.getMillis();
         long â˜ƒx = 10000L - (â˜ƒ - this.debugCrashKeyTime);
         long â˜ƒxx = â˜ƒ - this.debugCrashKeyReportedTime;
         if (â˜ƒx < 0L) {
            if (Screen.hasControlDown()) {
               Blaze3D.youJustLostTheGame();
            }

            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
         }

         if (â˜ƒxx >= 1000L) {
            if (this.debugCrashKeyReportedCount == 0L) {
               this.debugFeedbackTranslated("debug.crash.message");
            } else {
               this.debugWarningTranslated("debug.crash.warning", Mth.ceil((float)â˜ƒx / 1000.0F));
            }

            this.debugCrashKeyReportedTime = â˜ƒ;
            ++this.debugCrashKeyReportedCount;
         }
      }
   }
}
