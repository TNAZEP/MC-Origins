package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;

public abstract class Option {
   protected static final int OPTIONS_TOOLTIP_WIDTH = 200;
   public static final ProgressOption BIOME_BLEND_RADIUS = new ProgressOption(
      "options.biomeBlendRadius", 0.0, 7.0, 1.0F, var0 -> (double)var0.biomeBlendRadius, (var0, var1) -> {
         var0.biomeBlendRadius = Mth.clamp((int)var1.doubleValue(), 0, 7);
         Minecraft.getInstance().levelRenderer.allChanged();
      }, (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         int â˜ƒx = (int)â˜ƒ * 2 + 1;
         return var1.genericValueLabel(new TranslatableComponent("options.biomeBlendRadius." + â˜ƒx));
      }
   );
   public static final ProgressOption CHAT_HEIGHT_FOCUSED = new ProgressOption(
      "options.chat.height.focused", 0.0, 1.0, 0.0F, var0 -> var0.chatHeightFocused, (var0, var1) -> {
         var0.chatHeightFocused = var1;
         Minecraft.getInstance().gui.getChat().rescaleChat();
      }, (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         return var1.pixelValueLabel(ChatComponent.getHeight(â˜ƒ));
      }
   );
   public static final ProgressOption CHAT_HEIGHT_UNFOCUSED = new ProgressOption(
      "options.chat.height.unfocused", 0.0, 1.0, 0.0F, var0 -> var0.chatHeightUnfocused, (var0, var1) -> {
         var0.chatHeightUnfocused = var1;
         Minecraft.getInstance().gui.getChat().rescaleChat();
      }, (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         return var1.pixelValueLabel(ChatComponent.getHeight(â˜ƒ));
      }
   );
   public static final ProgressOption CHAT_OPACITY = new ProgressOption("options.chat.opacity", 0.0, 1.0, 0.0F, var0 -> var0.chatOpacity, (var0, var1) -> {
      var0.chatOpacity = var1;
      Minecraft.getInstance().gui.getChat().rescaleChat();
   }, (var0, var1) -> {
      double â˜ƒ = var1.toPct(var1.get(var0));
      return var1.percentValueLabel(â˜ƒ * 0.9 + 0.1);
   });
   public static final ProgressOption CHAT_SCALE = new ProgressOption("options.chat.scale", 0.0, 1.0, 0.0F, var0 -> var0.chatScale, (var0, var1) -> {
      var0.chatScale = var1;
      Minecraft.getInstance().gui.getChat().rescaleChat();
   }, (var0, var1) -> {
      double â˜ƒ = var1.toPct(var1.get(var0));
      return (Component)(â˜ƒ == 0.0 ? CommonComponents.optionStatus(var1.getCaption(), false) : var1.percentValueLabel(â˜ƒ));
   });
   public static final ProgressOption CHAT_WIDTH = new ProgressOption("options.chat.width", 0.0, 1.0, 0.0F, var0 -> var0.chatWidth, (var0, var1) -> {
      var0.chatWidth = var1;
      Minecraft.getInstance().gui.getChat().rescaleChat();
   }, (var0, var1) -> {
      double â˜ƒ = var1.toPct(var1.get(var0));
      return var1.pixelValueLabel(ChatComponent.getWidth(â˜ƒ));
   });
   public static final ProgressOption CHAT_LINE_SPACING = new ProgressOption(
      "options.chat.line_spacing",
      0.0,
      1.0,
      0.0F,
      var0 -> var0.chatLineSpacing,
      (var0, var1) -> var0.chatLineSpacing = var1,
      (var0, var1) -> var1.percentValueLabel(var1.toPct(var1.get(var0)))
   );
   public static final ProgressOption CHAT_DELAY = new ProgressOption(
      "options.chat.delay_instant", 0.0, 6.0, 0.1F, var0 -> var0.chatDelay, (var0, var1) -> var0.chatDelay = var1, (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         return â˜ƒ <= 0.0 ? new TranslatableComponent("options.chat.delay_none") : new TranslatableComponent("options.chat.delay", String.format("%.1f", â˜ƒ));
      }
   );
   public static final ProgressOption FOV = new ProgressOption("options.fov", 30.0, 110.0, 1.0F, var0 -> var0.fov, (var0, var1) -> {
      var0.fov = var1;
      Minecraft.getInstance().levelRenderer.needsUpdate();
   }, (var0, var1) -> {
      double â˜ƒ = var1.get(var0);
      if (â˜ƒ == 70.0) {
         return var1.genericValueLabel(new TranslatableComponent("options.fov.min"));
      } else {
         return â˜ƒ == var1.getMaxValue() ? var1.genericValueLabel(new TranslatableComponent("options.fov.max")) : var1.genericValueLabel((int)â˜ƒ);
      }
   });
   private static final Component ACCESSIBILITY_TOOLTIP_FOV_EFFECT = new TranslatableComponent("options.fovEffectScale.tooltip");
   public static final ProgressOption FOV_EFFECTS_SCALE = new ProgressOption(
      "options.fovEffectScale",
      0.0,
      1.0,
      0.0F,
      var0 -> Math.pow((double)var0.fovEffectScale, 2.0),
      (var0, var1) -> var0.fovEffectScale = (float)Math.sqrt(var1),
      (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         return â˜ƒ == 0.0 ? var1.genericValueLabel(CommonComponents.OPTION_OFF) : var1.percentValueLabel(â˜ƒ);
      },
      var0 -> var0.font.split(ACCESSIBILITY_TOOLTIP_FOV_EFFECT, 200)
   );
   private static final Component ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT = new TranslatableComponent("options.screenEffectScale.tooltip");
   public static final ProgressOption SCREEN_EFFECTS_SCALE = new ProgressOption(
      "options.screenEffectScale",
      0.0,
      1.0,
      0.0F,
      var0 -> (double)var0.screenEffectScale,
      (var0, var1) -> var0.screenEffectScale = var1.floatValue(),
      (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         return â˜ƒ == 0.0 ? var1.genericValueLabel(CommonComponents.OPTION_OFF) : var1.percentValueLabel(â˜ƒ);
      },
      var0 -> var0.font.split(ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT, 200)
   );
   public static final ProgressOption FRAMERATE_LIMIT = new ProgressOption(
      "options.framerateLimit",
      10.0,
      260.0,
      10.0F,
      var0 -> (double)var0.framerateLimit,
      (var0, var1) -> {
         var0.framerateLimit = (int)var1.doubleValue();
         Minecraft.getInstance().getWindow().setFramerateLimit(var0.framerateLimit);
      },
      (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         return â˜ƒ == var1.getMaxValue()
            ? var1.genericValueLabel(new TranslatableComponent("options.framerateLimit.max"))
            : var1.genericValueLabel(new TranslatableComponent("options.framerate", (int)â˜ƒ));
      }
   );
   public static final ProgressOption GAMMA = new ProgressOption(
      "options.gamma", 0.0, 1.0, 0.0F, var0 -> var0.gamma, (var0, var1) -> var0.gamma = var1, (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         if (â˜ƒ == 0.0) {
            return var1.genericValueLabel(new TranslatableComponent("options.gamma.min"));
         } else {
            return â˜ƒ == 1.0 ? var1.genericValueLabel(new TranslatableComponent("options.gamma.max")) : var1.percentAddValueLabel((int)(â˜ƒ * 100.0));
         }
      }
   );
   public static final ProgressOption MIPMAP_LEVELS = new ProgressOption(
      "options.mipmapLevels", 0.0, 4.0, 1.0F, var0 -> (double)var0.mipmapLevels, (var0, var1) -> var0.mipmapLevels = (int)var1.doubleValue(), (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         return (Component)(â˜ƒ == 0.0 ? CommonComponents.optionStatus(var1.getCaption(), false) : var1.genericValueLabel((int)â˜ƒ));
      }
   );
   public static final ProgressOption MOUSE_WHEEL_SENSITIVITY = new LogaritmicProgressOption(
      "options.mouseWheelSensitivity",
      0.01,
      10.0,
      0.01F,
      var0 -> var0.mouseWheelSensitivity,
      (var0, var1) -> var0.mouseWheelSensitivity = var1,
      (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         return var1.genericValueLabel(new TextComponent(String.format("%.2f", var1.toValue(â˜ƒ))));
      }
   );
   public static final CycleOption<Boolean> RAW_MOUSE_INPUT = CycleOption.createOnOff(
      "options.rawMouseInput", var0 -> var0.rawMouseInput, (var0, var1, var2) -> {
         var0.rawMouseInput = var2;
         Window â˜ƒ = Minecraft.getInstance().getWindow();
         if (â˜ƒ != null) {
            â˜ƒ.updateRawMouseInput(var2);
         }
      }
   );
   public static final ProgressOption RENDER_DISTANCE = new ProgressOption(
      "options.renderDistance", 2.0, 16.0, 1.0F, var0 -> (double)var0.renderDistance, (var0, var1) -> {
         var0.renderDistance = (int)var1.doubleValue();
         Minecraft.getInstance().levelRenderer.needsUpdate();
      }, (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         return var1.genericValueLabel(new TranslatableComponent("options.chunks", (int)â˜ƒ));
      }
   );
   public static final ProgressOption ENTITY_DISTANCE_SCALING = new ProgressOption(
      "options.entityDistanceScaling",
      0.5,
      5.0,
      0.25F,
      var0 -> (double)var0.entityDistanceScaling,
      (var0, var1) -> var0.entityDistanceScaling = (float)var1.doubleValue(),
      (var0, var1) -> {
         double â˜ƒ = var1.get(var0);
         return var1.percentValueLabel(â˜ƒ);
      }
   );
   public static final ProgressOption SENSITIVITY = new ProgressOption(
      "options.sensitivity", 0.0, 1.0, 0.0F, var0 -> var0.sensitivity, (var0, var1) -> var0.sensitivity = var1, (var0, var1) -> {
         double â˜ƒ = var1.toPct(var1.get(var0));
         if (â˜ƒ == 0.0) {
            return var1.genericValueLabel(new TranslatableComponent("options.sensitivity.min"));
         } else {
            return â˜ƒ == 1.0 ? var1.genericValueLabel(new TranslatableComponent("options.sensitivity.max")) : var1.percentValueLabel(2.0 * â˜ƒ);
         }
      }
   );
   public static final ProgressOption TEXT_BACKGROUND_OPACITY = new ProgressOption(
      "options.accessibility.text_background_opacity", 0.0, 1.0, 0.0F, var0 -> var0.textBackgroundOpacity, (var0, var1) -> {
         var0.textBackgroundOpacity = var1;
         Minecraft.getInstance().gui.getChat().rescaleChat();
      }, (var0, var1) -> var1.percentValueLabel(var1.toPct(var1.get(var0)))
   );
   public static final CycleOption<AmbientOcclusionStatus> AMBIENT_OCCLUSION = CycleOption.create(
      "options.ao", AmbientOcclusionStatus.values(), var0 -> new TranslatableComponent(var0.getKey()), var0 -> var0.ambientOcclusion, (var0, var1, var2) -> {
         var0.ambientOcclusion = var2;
         Minecraft.getInstance().levelRenderer.allChanged();
      }
   );
   public static final CycleOption<AttackIndicatorStatus> ATTACK_INDICATOR = CycleOption.create(
      "options.attackIndicator",
      AttackIndicatorStatus.values(),
      var0 -> new TranslatableComponent(var0.getKey()),
      var0 -> var0.attackIndicator,
      (var0, var1, var2) -> var0.attackIndicator = var2
   );
   public static final CycleOption<ChatVisiblity> CHAT_VISIBILITY = CycleOption.create(
      "options.chat.visibility",
      ChatVisiblity.values(),
      var0 -> new TranslatableComponent(var0.getKey()),
      var0 -> var0.chatVisibility,
      (var0, var1, var2) -> var0.chatVisibility = var2
   );
   private static final Component GRAPHICS_TOOLTIP_FAST = new TranslatableComponent("options.graphics.fast.tooltip");
   private static final Component GRAPHICS_TOOLTIP_FABULOUS = new TranslatableComponent(
      "options.graphics.fabulous.tooltip", new TranslatableComponent("options.graphics.fabulous").withStyle(ChatFormatting.ITALIC)
   );
   private static final Component GRAPHICS_TOOLTIP_FANCY = new TranslatableComponent("options.graphics.fancy.tooltip");
   public static final CycleOption<GraphicsStatus> GRAPHICS = CycleOption.create(
         "options.graphics",
         Arrays.asList(GraphicsStatus.values()),
         (List)Stream.of(GraphicsStatus.values()).filter(var0 -> var0 != GraphicsStatus.FABULOUS).collect(Collectors.toList()),
         () -> Minecraft.getInstance().getGpuWarnlistManager().isSkippingFabulous(),
         var0 -> {
            MutableComponent â˜ƒ = new TranslatableComponent(var0.getKey());
            return var0 == GraphicsStatus.FABULOUS ? â˜ƒ.withStyle(ChatFormatting.ITALIC) : â˜ƒ;
         },
         var0 -> var0.graphicsMode,
         (var0, var1, var2) -> {
            Minecraft â˜ƒ = Minecraft.getInstance();
            GpuWarnlistManager â˜ƒx = â˜ƒ.getGpuWarnlistManager();
            if (var2 == GraphicsStatus.FABULOUS && â˜ƒx.willShowWarning()) {
               â˜ƒx.showWarning();
            } else {
               var0.graphicsMode = var2;
               â˜ƒ.levelRenderer.allChanged();
            }
         }
      )
      .setTooltip(var0 -> {
         List<FormattedCharSequence> â˜ƒ = var0.font.split(GRAPHICS_TOOLTIP_FAST, 200);
         List<FormattedCharSequence> â˜ƒx = var0.font.split(GRAPHICS_TOOLTIP_FANCY, 200);
         List<FormattedCharSequence> â˜ƒxx = var0.font.split(GRAPHICS_TOOLTIP_FABULOUS, 200);
         return var3x -> {
            switch(var3x) {
               case FANCY:
                  return â˜ƒ;
               case FAST:
                  return â˜ƒ;
               case FABULOUS:
                  return â˜ƒ;
               default:
                  return ImmutableList.of();
            }
         };
      });
   public static final CycleOption GUI_SCALE = CycleOption.create(
      "options.guiScale",
      (Supplier)(() -> (List)IntStream.rangeClosed(0, Minecraft.getInstance().getWindow().calculateScale(0, Minecraft.getInstance().isEnforceUnicode()))
            .boxed()
            .collect(Collectors.toList())),
      var0 -> (Component)(var0 == 0 ? new TranslatableComponent("options.guiScale.auto") : new TextComponent(Integer.toString(var0))),
      var0 -> var0.guiScale,
      (var0, var1, var2) -> var0.guiScale = var2
   );
   public static final CycleOption<HumanoidArm> MAIN_HAND = CycleOption.create(
      "options.mainHand", HumanoidArm.values(), HumanoidArm::getName, var0 -> var0.mainHand, (var0, var1, var2) -> {
         var0.mainHand = var2;
         var0.broadcastOptions();
      }
   );
   public static final CycleOption<NarratorStatus> NARRATOR = CycleOption.create(
      "options.narrator",
      NarratorStatus.values(),
      var0 -> (Component)(NarratorChatListener.INSTANCE.isActive() ? var0.getName() : new TranslatableComponent("options.narrator.notavailable")),
      var0 -> var0.narratorStatus,
      (var0, var1, var2) -> {
         var0.narratorStatus = var2;
         NarratorChatListener.INSTANCE.updateNarratorStatus(var2);
      }
   );
   public static final CycleOption<ParticleStatus> PARTICLES = CycleOption.create(
      "options.particles",
      ParticleStatus.values(),
      var0 -> new TranslatableComponent(var0.getKey()),
      var0 -> var0.particles,
      (var0, var1, var2) -> var0.particles = var2
   );
   public static final CycleOption<CloudStatus> RENDER_CLOUDS = CycleOption.create(
      "options.renderClouds", CloudStatus.values(), var0 -> new TranslatableComponent(var0.getKey()), var0 -> var0.renderClouds, (var0, var1, var2) -> {
         var0.renderClouds = var2;
         if (Minecraft.useShaderTransparency()) {
            RenderTarget â˜ƒ = Minecraft.getInstance().levelRenderer.getCloudsTarget();
            if (â˜ƒ != null) {
               â˜ƒ.clear(Minecraft.ON_OSX);
            }
         }
      }
   );
   public static final CycleOption<Boolean> TEXT_BACKGROUND = CycleOption.createBinaryOption(
      "options.accessibility.text_background",
      new TranslatableComponent("options.accessibility.text_background.chat"),
      new TranslatableComponent("options.accessibility.text_background.everywhere"),
      var0 -> var0.backgroundForChatOnly,
      (var0, var1, var2) -> var0.backgroundForChatOnly = var2
   );
   private static final Component CHAT_TOOLTIP_HIDE_MATCHED_NAMES = new TranslatableComponent("options.hideMatchedNames.tooltip");
   public static final CycleOption<Boolean> AUTO_JUMP = CycleOption.createOnOff(
      "options.autoJump", var0 -> var0.autoJump, (var0, var1, var2) -> var0.autoJump = var2
   );
   public static final CycleOption<Boolean> AUTO_SUGGESTIONS = CycleOption.createOnOff(
      "options.autoSuggestCommands", var0 -> var0.autoSuggestions, (var0, var1, var2) -> var0.autoSuggestions = var2
   );
   public static final CycleOption<Boolean> CHAT_COLOR = CycleOption.createOnOff(
      "options.chat.color", var0 -> var0.chatColors, (var0, var1, var2) -> var0.chatColors = var2
   );
   public static final CycleOption<Boolean> HIDE_MATCHED_NAMES = CycleOption.createOnOff(
      "options.hideMatchedNames", CHAT_TOOLTIP_HIDE_MATCHED_NAMES, var0 -> var0.hideMatchedNames, (var0, var1, var2) -> var0.hideMatchedNames = var2
   );
   public static final CycleOption<Boolean> CHAT_LINKS = CycleOption.createOnOff(
      "options.chat.links", var0 -> var0.chatLinks, (var0, var1, var2) -> var0.chatLinks = var2
   );
   public static final CycleOption<Boolean> CHAT_LINKS_PROMPT = CycleOption.createOnOff(
      "options.chat.links.prompt", var0 -> var0.chatLinksPrompt, (var0, var1, var2) -> var0.chatLinksPrompt = var2
   );
   public static final CycleOption<Boolean> DISCRETE_MOUSE_SCROLL = CycleOption.createOnOff(
      "options.discrete_mouse_scroll", var0 -> var0.discreteMouseScroll, (var0, var1, var2) -> var0.discreteMouseScroll = var2
   );
   public static final CycleOption<Boolean> ENABLE_VSYNC = CycleOption.createOnOff("options.vsync", var0 -> var0.enableVsync, (var0, var1, var2) -> {
      var0.enableVsync = var2;
      if (Minecraft.getInstance().getWindow() != null) {
         Minecraft.getInstance().getWindow().updateVsync(var0.enableVsync);
      }
   });
   public static final CycleOption<Boolean> ENTITY_SHADOWS = CycleOption.createOnOff(
      "options.entityShadows", var0 -> var0.entityShadows, (var0, var1, var2) -> var0.entityShadows = var2
   );
   public static final CycleOption<Boolean> FORCE_UNICODE_FONT = CycleOption.createOnOff(
      "options.forceUnicodeFont", var0 -> var0.forceUnicodeFont, (var0, var1, var2) -> {
         var0.forceUnicodeFont = var2;
         Minecraft â˜ƒ = Minecraft.getInstance();
         if (â˜ƒ.getWindow() != null) {
            â˜ƒ.selectMainFont(var2);
            â˜ƒ.resizeDisplay();
         }
      }
   );
   public static final CycleOption<Boolean> INVERT_MOUSE = CycleOption.createOnOff(
      "options.invertMouse", var0 -> var0.invertYMouse, (var0, var1, var2) -> var0.invertYMouse = var2
   );
   public static final CycleOption<Boolean> REALMS_NOTIFICATIONS = CycleOption.createOnOff(
      "options.realmsNotifications", var0 -> var0.realmsNotifications, (var0, var1, var2) -> var0.realmsNotifications = var2
   );
   public static final CycleOption<Boolean> REDUCED_DEBUG_INFO = CycleOption.createOnOff(
      "options.reducedDebugInfo", var0 -> var0.reducedDebugInfo, (var0, var1, var2) -> var0.reducedDebugInfo = var2
   );
   public static final CycleOption<Boolean> SHOW_SUBTITLES = CycleOption.createOnOff(
      "options.showSubtitles", var0 -> var0.showSubtitles, (var0, var1, var2) -> var0.showSubtitles = var2
   );
   public static final CycleOption<Boolean> SNOOPER_ENABLED = CycleOption.createOnOff("options.snooper", var0 -> {
      if (var0.snooperEnabled) {
      }

      return false;
   }, (var0, var1, var2) -> var0.snooperEnabled = var2);
   private static final Component MOVEMENT_TOGGLE = new TranslatableComponent("options.key.toggle");
   private static final Component MOVEMENT_HOLD = new TranslatableComponent("options.key.hold");
   public static final CycleOption<Boolean> TOGGLE_CROUCH = CycleOption.createBinaryOption(
      "key.sneak", MOVEMENT_TOGGLE, MOVEMENT_HOLD, var0 -> var0.toggleCrouch, (var0, var1, var2) -> var0.toggleCrouch = var2
   );
   public static final CycleOption<Boolean> TOGGLE_SPRINT = CycleOption.createBinaryOption(
      "key.sprint", MOVEMENT_TOGGLE, MOVEMENT_HOLD, var0 -> var0.toggleSprint, (var0, var1, var2) -> var0.toggleSprint = var2
   );
   public static final CycleOption<Boolean> TOUCHSCREEN = CycleOption.createOnOff(
      "options.touchscreen", var0 -> var0.touchscreen, (var0, var1, var2) -> var0.touchscreen = var2
   );
   public static final CycleOption<Boolean> USE_FULLSCREEN = CycleOption.createOnOff("options.fullscreen", var0 -> var0.fullscreen, (var0, var1, var2) -> {
      var0.fullscreen = var2;
      Minecraft â˜ƒ = Minecraft.getInstance();
      if (â˜ƒ.getWindow() != null && â˜ƒ.getWindow().isFullscreen() != var0.fullscreen) {
         â˜ƒ.getWindow().toggleFullScreen();
         var0.fullscreen = â˜ƒ.getWindow().isFullscreen();
      }
   });
   public static final CycleOption<Boolean> VIEW_BOBBING = CycleOption.createOnOff(
      "options.viewBobbing", var0 -> var0.bobView, (var0, var1, var2) -> var0.bobView = var2
   );
   private static final Component ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND = new TranslatableComponent("options.darkMojangStudiosBackgroundColor.tooltip");
   public static final CycleOption<Boolean> DARK_MOJANG_STUDIOS_BACKGROUND_COLOR = CycleOption.createOnOff(
      "options.darkMojangStudiosBackgroundColor",
      ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND,
      var0 -> var0.darkMojangStudiosBackground,
      (var0, var1, var2) -> var0.darkMojangStudiosBackground = var2
   );
   private final Component caption;

   public Option(String var1) {
      this.caption = new TranslatableComponent(â˜ƒ);
   }

   public abstract AbstractWidget createButton(Options var1, int var2, int var3, int var4);

   protected Component getCaption() {
      return this.caption;
   }

   protected Component pixelValueLabel(int var1) {
      return new TranslatableComponent("options.pixel_value", this.getCaption(), â˜ƒ);
   }

   protected Component percentValueLabel(double var1) {
      return new TranslatableComponent("options.percent_value", this.getCaption(), (int)(â˜ƒ * 100.0));
   }

   protected Component percentAddValueLabel(int var1) {
      return new TranslatableComponent("options.percent_add_value", this.getCaption(), â˜ƒ);
   }

   protected Component genericValueLabel(Component var1) {
      return new TranslatableComponent("options.generic_value", this.getCaption(), â˜ƒ);
   }

   protected Component genericValueLabel(int var1) {
      return this.genericValueLabel(new TextComponent(Integer.toString(â˜ƒ)));
   }
}
