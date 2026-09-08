package net.minecraft.server.gui;

import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftServerGui extends JComponent {
   private static final Font field_164249_a = new Font("Monospaced", 0, 12);
   private static final Logger field_164248_b = LogManager.getLogger();
   private final DedicatedServer field_120021_b;
   private Thread field_206932_d;

   public static void func_120016_a(final DedicatedServer var0) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
      }

      MinecraftServerGui ☃ = new MinecraftServerGui(☃);
      JFrame ☃x = new JFrame("Minecraft server");
      ☃x.add(☃);
      ☃x.pack();
      ☃x.setLocationRelativeTo(null);
      ☃x.setVisible(true);
      ☃x.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            ☃.func_71263_m();

            while(!☃.func_71241_aa()) {
               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var3) {
                  var3.printStackTrace();
               }
            }

            System.exit(0);
         }
      });
      ☃.func_206931_a();
   }

   public MinecraftServerGui(DedicatedServer var1) {
      this.field_120021_b = ☃;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.func_120018_d(), "Center");
         this.add(this.func_120019_b(), "West");
      } catch (Exception var3) {
         field_164248_b.error("Couldn't build server GUI", var3);
      }
   }

   private JComponent func_120019_b() throws Exception {
      JPanel ☃ = new JPanel(new BorderLayout());
      ☃.add(new StatsComponent(this.field_120021_b), "North");
      ☃.add(this.func_120020_c(), "Center");
      ☃.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return ☃;
   }

   private JComponent func_120020_c() throws Exception {
      JList<?> ☃ = new PlayerListComponent(this.field_120021_b);
      JScrollPane ☃x = new JScrollPane(☃, 22, 30);
      ☃x.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return ☃x;
   }

   private JComponent func_120018_d() throws Exception {
      JPanel ☃ = new JPanel(new BorderLayout());
      JTextArea ☃x = new JTextArea();
      JScrollPane ☃xx = new JScrollPane(☃x, 22, 30);
      ☃x.setEditable(false);
      ☃x.setFont(field_164249_a);
      JTextField ☃xxx = new JTextField();
      ☃xxx.addActionListener(var2x -> {
         String ☃ = ☃.getText().trim();
         if (!☃.isEmpty()) {
            this.field_120021_b.func_195581_a(☃, this.field_120021_b.func_195573_aM());
         }

         ☃.setText("");
      });
      ☃x.addFocusListener(new FocusAdapter() {
         public void focusGained(FocusEvent var1) {
         }
      });
      ☃.add(☃xx, "Center");
      ☃.add(☃xxx, "South");
      ☃.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.field_206932_d = new Thread(() -> {
         String ☃;
         while((☃ = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.func_164247_a(☃, ☃, ☃);
         }
      });
      this.field_206932_d.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_164248_b));
      this.field_206932_d.setDaemon(true);
      return ☃;
   }

   public void func_206931_a() {
      this.field_206932_d.start();
   }

   public void func_164247_a(JTextArea var1, JScrollPane var2, String var3) {
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> this.func_164247_a(☃, ☃, ☃));
      } else {
         Document ☃ = ☃.getDocument();
         JScrollBar ☃x = ☃.getVerticalScrollBar();
         boolean ☃xx = false;
         if (☃.getViewport().getView() == ☃) {
            ☃xx = (double)☃x.getValue() + ☃x.getSize().getHeight() + (double)(field_164249_a.getSize() * 4) > (double)☃x.getMaximum();
         }

         try {
            ☃.insertString(☃.getLength(), ☃, null);
         } catch (BadLocationException var8) {
         }

         if (☃xx) {
            ☃x.setValue(Integer.MAX_VALUE);
         }
      }
   }
}
