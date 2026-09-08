package net.minecraft.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;

public class StatsComponent extends JComponent {
   private static final DecimalFormat DECIMAL_FORMAT = Util.make(
      new DecimalFormat("########0.000"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private final int[] values = new int[256];
   private int vp;
   private final String[] msgs = new String[11];
   private final MinecraftServer server;
   private final Timer timer;

   public StatsComponent(MinecraftServer var1) {
      this.server = â˜ƒ;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      this.timer = new Timer(500, var1x -> this.tick());
      this.timer.start();
      this.setBackground(Color.BLACK);
   }

   private void tick() {
      long â˜ƒ = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.msgs[0] = "Memory use: " + â˜ƒ / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.msgs[1] = "Avg tick: " + DECIMAL_FORMAT.format(this.getAverage(this.server.tickTimes) * 1.0E-6) + " ms";
      this.values[this.vp++ & 0xFF] = (int)(â˜ƒ * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double getAverage(long[] var1) {
      long â˜ƒ = 0L;

      for(long â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒx;
      }

      return (double)â˜ƒ / (double)â˜ƒ.length;
   }

   public void paint(Graphics var1) {
      â˜ƒ.setColor(new Color(16777215));
      â˜ƒ.fillRect(0, 0, 456, 246);

      for(int â˜ƒ = 0; â˜ƒ < 256; ++â˜ƒ) {
         int â˜ƒx = this.values[â˜ƒ + this.vp & 0xFF];
         â˜ƒ.setColor(new Color(â˜ƒx + 28 << 16));
         â˜ƒ.fillRect(â˜ƒ, 100 - â˜ƒx, 1, â˜ƒx);
      }

      â˜ƒ.setColor(Color.BLACK);

      for(int â˜ƒ = 0; â˜ƒ < this.msgs.length; ++â˜ƒ) {
         String â˜ƒx = this.msgs[â˜ƒ];
         if (â˜ƒx != null) {
            â˜ƒ.drawString(â˜ƒx, 32, 116 + â˜ƒ * 16);
         }
      }
   }

   public void close() {
      this.timer.stop();
   }
}
