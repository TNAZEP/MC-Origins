package com.mojang.blaze3d.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

public class OpenAlUtil {
   private static final Logger LOGGER = LogManager.getLogger();

   private static String alErrorToString(int var0) {
      switch(â˜ƒ) {
         case 40961:
            return "Invalid name parameter.";
         case 40962:
            return "Invalid enumerated parameter value.";
         case 40963:
            return "Invalid parameter parameter value.";
         case 40964:
            return "Invalid operation.";
         case 40965:
            return "Unable to allocate memory.";
         default:
            return "An unrecognized error occurred.";
      }
   }

   static boolean checkALError(String var0) {
      int â˜ƒ = AL10.alGetError();
      if (â˜ƒ != 0) {
         LOGGER.error("{}: {}", â˜ƒ, alErrorToString(â˜ƒ));
         return true;
      } else {
         return false;
      }
   }

   private static String alcErrorToString(int var0) {
      switch(â˜ƒ) {
         case 40961:
            return "Invalid device.";
         case 40962:
            return "Invalid context.";
         case 40963:
            return "Illegal enum.";
         case 40964:
            return "Invalid value.";
         case 40965:
            return "Unable to allocate memory.";
         default:
            return "An unrecognized error occurred.";
      }
   }

   static boolean checkALCError(long var0, String var2) {
      int â˜ƒ = ALC10.alcGetError(â˜ƒ);
      if (â˜ƒ != 0) {
         LOGGER.error("{}{}: {}", â˜ƒ, â˜ƒ, alcErrorToString(â˜ƒ));
         return true;
      } else {
         return false;
      }
   }

   static int audioFormatToOpenAl(AudioFormat var0) {
      Encoding â˜ƒ = â˜ƒ.getEncoding();
      int â˜ƒx = â˜ƒ.getChannels();
      int â˜ƒxx = â˜ƒ.getSampleSizeInBits();
      if (â˜ƒ.equals(Encoding.PCM_UNSIGNED) || â˜ƒ.equals(Encoding.PCM_SIGNED)) {
         if (â˜ƒx == 1) {
            if (â˜ƒxx == 8) {
               return 4352;
            }

            if (â˜ƒxx == 16) {
               return 4353;
            }
         } else if (â˜ƒx == 2) {
            if (â˜ƒxx == 8) {
               return 4354;
            }

            if (â˜ƒxx == 16) {
               return 4355;
            }
         }
      }

      throw new IllegalArgumentException("Invalid audio format: " + â˜ƒ);
   }
}
