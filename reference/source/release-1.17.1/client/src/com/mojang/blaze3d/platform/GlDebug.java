package com.mojang.blaze3d.platform;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;

public class GlDebug {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int CIRCULAR_LOG_SIZE = 10;
   private static final Queue<GlDebug.LogEntry> MESSAGE_BUFFER = EvictingQueue.create(10);
   @Nullable
   private static volatile GlDebug.LogEntry lastEntry;
   private static final List<Integer> DEBUG_LEVELS = ImmutableList.of(37190, 37191, 37192, 33387);
   private static final List<Integer> DEBUG_LEVELS_ARB = ImmutableList.of(37190, 37191, 37192);
   private static boolean debugEnabled;

   private static String printUnknownToken(int var0) {
      return "Unknown (0x" + Integer.toHexString(â˜ƒ).toUpperCase() + ")";
   }

   public static String sourceToString(int var0) {
      switch(â˜ƒ) {
         case 33350:
            return "API";
         case 33351:
            return "WINDOW SYSTEM";
         case 33352:
            return "SHADER COMPILER";
         case 33353:
            return "THIRD PARTY";
         case 33354:
            return "APPLICATION";
         case 33355:
            return "OTHER";
         default:
            return printUnknownToken(â˜ƒ);
      }
   }

   public static String typeToString(int var0) {
      switch(â˜ƒ) {
         case 33356:
            return "ERROR";
         case 33357:
            return "DEPRECATED BEHAVIOR";
         case 33358:
            return "UNDEFINED BEHAVIOR";
         case 33359:
            return "PORTABILITY";
         case 33360:
            return "PERFORMANCE";
         case 33361:
            return "OTHER";
         case 33384:
            return "MARKER";
         default:
            return printUnknownToken(â˜ƒ);
      }
   }

   public static String severityToString(int var0) {
      switch(â˜ƒ) {
         case 33387:
            return "NOTIFICATION";
         case 37190:
            return "HIGH";
         case 37191:
            return "MEDIUM";
         case 37192:
            return "LOW";
         default:
            return printUnknownToken(â˜ƒ);
      }
   }

   private static void printDebugLog(int var0, int var1, int var2, int var3, int var4, long var5, long var7) {
      String â˜ƒx = GLDebugMessageCallback.getMessage(â˜ƒ, â˜ƒ);
      GlDebug.LogEntry â˜ƒ;
      synchronized(MESSAGE_BUFFER) {
         â˜ƒ = lastEntry;
         if (â˜ƒ != null && â˜ƒ.isSame(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx)) {
            ++â˜ƒ.count;
         } else {
            â˜ƒ = new GlDebug.LogEntry(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            MESSAGE_BUFFER.add(â˜ƒ);
            lastEntry = â˜ƒ;
         }
      }

      LOGGER.info("OpenGL debug message: {}", â˜ƒ);
   }

   public static List<String> getLastOpenGlDebugMessages() {
      synchronized(MESSAGE_BUFFER) {
         List<String> â˜ƒ = Lists.newArrayListWithCapacity(MESSAGE_BUFFER.size());

         for(GlDebug.LogEntry â˜ƒx : MESSAGE_BUFFER) {
            â˜ƒ.add(â˜ƒx + " x " + â˜ƒx.count);
         }

         return â˜ƒ;
      }
   }

   public static boolean isDebugEnabled() {
      return debugEnabled;
   }

   public static void enableDebugCallback(int var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (â˜ƒ > 0) {
         GLCapabilities â˜ƒ = GL.getCapabilities();
         if (â˜ƒ.GL_KHR_debug) {
            debugEnabled = true;
            GL11.glEnable(37600);
            if (â˜ƒ) {
               GL11.glEnable(33346);
            }

            for(int â˜ƒx = 0; â˜ƒx < DEBUG_LEVELS.size(); ++â˜ƒx) {
               boolean â˜ƒxx = â˜ƒx < â˜ƒ;
               KHRDebug.glDebugMessageControl(4352, 4352, DEBUG_LEVELS.get(â˜ƒx), (int[])null, â˜ƒxx);
            }

            KHRDebug.glDebugMessageCallback(GLX.make(GLDebugMessageCallback.create(GlDebug::printDebugLog), DebugMemoryUntracker::untrack), 0L);
         } else if (â˜ƒ.GL_ARB_debug_output) {
            debugEnabled = true;
            if (â˜ƒ) {
               GL11.glEnable(33346);
            }

            for(int â˜ƒ = 0; â˜ƒ < DEBUG_LEVELS_ARB.size(); ++â˜ƒ) {
               boolean â˜ƒx = â˜ƒ < â˜ƒ;
               ARBDebugOutput.glDebugMessageControlARB(4352, 4352, DEBUG_LEVELS_ARB.get(â˜ƒ), (int[])null, â˜ƒx);
            }

            ARBDebugOutput.glDebugMessageCallbackARB(GLX.make(GLDebugMessageARBCallback.create(GlDebug::printDebugLog), DebugMemoryUntracker::untrack), 0L);
         }
      }
   }

   static class LogEntry {
      private final int id;
      private final int source;
      private final int type;
      private final int severity;
      private final String message;
      int count = 1;

      LogEntry(int var1, int var2, int var3, int var4, String var5) {
         this.id = â˜ƒ;
         this.source = â˜ƒ;
         this.type = â˜ƒ;
         this.severity = â˜ƒ;
         this.message = â˜ƒ;
      }

      boolean isSame(int var1, int var2, int var3, int var4, String var5) {
         return â˜ƒ == this.type && â˜ƒ == this.source && â˜ƒ == this.id && â˜ƒ == this.severity && â˜ƒ.equals(this.message);
      }

      public String toString() {
         return "id="
            + this.id
            + ", source="
            + GlDebug.sourceToString(this.source)
            + ", type="
            + GlDebug.typeToString(this.type)
            + ", severity="
            + GlDebug.severityToString(this.severity)
            + ", message='"
            + this.message
            + "'";
      }
   }
}
