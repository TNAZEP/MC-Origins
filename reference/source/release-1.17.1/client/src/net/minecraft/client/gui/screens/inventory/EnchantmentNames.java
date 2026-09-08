package net.minecraft.client.gui.screens.inventory;

import java.util.Random;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class EnchantmentNames {
   private static final ResourceLocation ALT_FONT = new ResourceLocation("minecraft", "alt");
   private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);
   private static final EnchantmentNames INSTANCE = new EnchantmentNames();
   private final Random random = new Random();
   private final String[] words = new String[]{
      "the",
      "elder",
      "scrolls",
      "klaatu",
      "berata",
      "niktu",
      "xyzzy",
      "bless",
      "curse",
      "light",
      "darkness",
      "fire",
      "air",
      "earth",
      "water",
      "hot",
      "dry",
      "cold",
      "wet",
      "ignite",
      "snuff",
      "embiggen",
      "twist",
      "shorten",
      "stretch",
      "fiddle",
      "destroy",
      "imbue",
      "galvanize",
      "enchant",
      "free",
      "limited",
      "range",
      "of",
      "towards",
      "inside",
      "sphere",
      "cube",
      "self",
      "other",
      "ball",
      "mental",
      "physical",
      "grow",
      "shrink",
      "demon",
      "elemental",
      "spirit",
      "animal",
      "creature",
      "beast",
      "humanoid",
      "undead",
      "fresh",
      "stale",
      "phnglui",
      "mglwnafh",
      "cthulhu",
      "rlyeh",
      "wgahnagl",
      "fhtagn",
      "baguette"
   };

   private EnchantmentNames() {
   }

   public static EnchantmentNames getInstance() {
      return INSTANCE;
   }

   public FormattedText getRandomName(Font var1, int var2) {
      StringBuilder â˜ƒ = new StringBuilder();
      int â˜ƒx = this.random.nextInt(2) + 3;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         if (â˜ƒxx != 0) {
            â˜ƒ.append(" ");
         }

         â˜ƒ.append(Util.getRandom((String[])this.words, this.random));
      }

      return â˜ƒ.getSplitter().headByWidth(new TextComponent(â˜ƒ.toString()).withStyle(ROOT_STYLE), â˜ƒ, Style.EMPTY);
   }

   public void initSeed(long var1) {
      this.random.setSeed(â˜ƒ);
   }
}
