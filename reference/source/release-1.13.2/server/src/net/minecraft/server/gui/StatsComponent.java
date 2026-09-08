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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;

public class StatsComponent extends JComponent {
   private static final DecimalFormat field_120040_a = Util.func_200696_a(
      new DecimalFormat("########0.000"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private final int[] field_120038_b = new int[256];
   private int field_120039_c;
   private final String[] field_120036_d = new String[11];
   private final MinecraftServer field_120037_e;

   public StatsComponent(MinecraftServer var1) {
      this.field_120037_e = ☃;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      new Timer(500, var1x -> this.func_120034_a()).start();
      this.setBackground(Color.BLACK);
   }

   private void func_120034_a() {
      long ☃ = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.field_120036_d[0] = "Memory use: "
         + ☃ / 1024L / 1024L
         + " mb ("
         + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory()
         + "% free)";
      this.field_120036_d[1] = "Avg tick: " + field_120040_a.format(this.func_120035_a(this.field_120037_e.field_71311_j) * 1.0E-6) + " ms";
      this.field_120038_b[this.field_120039_c++ & 0xFF] = (int)(☃ * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double func_120035_a(long[] var1) {
      long ☃ = 0L;

      for(long ☃x : ☃) {
         ☃ += ☃x;
      }

      return (double)☃ / (double)☃.length;
   }

   public void paint(Graphics var1) {
      ☃.setColor(new Color(16777215));
      ☃.fillRect(0, 0, 456, 246);

      for(int ☃ = 0; ☃ < 256; ++☃) {
         int ☃x = this.field_120038_b[☃ + this.field_120039_c & 0xFF];
         ☃.setColor(new Color(☃x + 28 << 16));
         ☃.fillRect(☃, 100 - ☃x, 1, ☃x);
      }

      ☃.setColor(Color.BLACK);

      for(int ☃ = 0; ☃ < this.field_120036_d.length; ++☃) {
         String ☃x = this.field_120036_d[☃];
         if (☃x != null) {
            ☃.drawString(☃x, 32, 116 + ☃ * 16);
         }
      }
   }
}
