package net.minecraft.server.gui;

import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
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
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftServerGui extends JComponent {
   private static final Font MONOSPACED = new Font("Monospaced", 0, 12);
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String TITLE = "Minecraft server";
   private static final String SHUTDOWN_TITLE = "Minecraft server - shutting down!";
   private final DedicatedServer server;
   private Thread logAppenderThread;
   private final Collection<Runnable> finalizers = Lists.newArrayList();
   final AtomicBoolean isClosing = new AtomicBoolean();

   public static MinecraftServerGui showFrameFor(final DedicatedServer var0) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
      }

      final JFrame â˜ƒ = new JFrame("Minecraft server");
      final MinecraftServerGui â˜ƒx = new MinecraftServerGui(â˜ƒ);
      â˜ƒ.setDefaultCloseOperation(2);
      â˜ƒ.add(â˜ƒx);
      â˜ƒ.pack();
      â˜ƒ.setLocationRelativeTo(null);
      â˜ƒ.setVisible(true);
      â˜ƒ.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1x) {
            if (!â˜ƒ.isClosing.getAndSet(true)) {
               â˜ƒ.setTitle("Minecraft server - shutting down!");
               â˜ƒ.halt(true);
               â˜ƒ.runFinalizers();
            }
         }
      });
      â˜ƒx.addFinalizer(â˜ƒ::dispose);
      â˜ƒx.start();
      return â˜ƒx;
   }

   private MinecraftServerGui(DedicatedServer var1) {
      this.server = â˜ƒ;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.buildChatPanel(), "Center");
         this.add(this.buildInfoPanel(), "West");
      } catch (Exception var3) {
         LOGGER.error("Couldn't build server GUI", var3);
      }
   }

   public void addFinalizer(Runnable var1) {
      this.finalizers.add(â˜ƒ);
   }

   private JComponent buildInfoPanel() {
      JPanel â˜ƒ = new JPanel(new BorderLayout());
      StatsComponent â˜ƒx = new StatsComponent(this.server);
      this.finalizers.add(â˜ƒx::close);
      â˜ƒ.add(â˜ƒx, "North");
      â˜ƒ.add(this.buildPlayerPanel(), "Center");
      â˜ƒ.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return â˜ƒ;
   }

   private JComponent buildPlayerPanel() {
      JList<?> â˜ƒ = new PlayerListComponent(this.server);
      JScrollPane â˜ƒx = new JScrollPane(â˜ƒ, 22, 30);
      â˜ƒx.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return â˜ƒx;
   }

   private JComponent buildChatPanel() {
      JPanel â˜ƒ = new JPanel(new BorderLayout());
      JTextArea â˜ƒx = new JTextArea();
      JScrollPane â˜ƒxx = new JScrollPane(â˜ƒx, 22, 30);
      â˜ƒx.setEditable(false);
      â˜ƒx.setFont(MONOSPACED);
      JTextField â˜ƒxxx = new JTextField();
      â˜ƒxxx.addActionListener(var2x -> {
         String â˜ƒ = â˜ƒ.getText().trim();
         if (!â˜ƒ.isEmpty()) {
            this.server.handleConsoleInput(â˜ƒ, this.server.createCommandSourceStack());
         }

         â˜ƒ.setText("");
      });
      â˜ƒx.addFocusListener(new FocusAdapter() {
         public void focusGained(FocusEvent var1) {
         }
      });
      â˜ƒ.add(â˜ƒxx, "Center");
      â˜ƒ.add(â˜ƒxxx, "South");
      â˜ƒ.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.logAppenderThread = new Thread(() -> {
         String â˜ƒ;
         while((â˜ƒ = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.print(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      });
      this.logAppenderThread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      this.logAppenderThread.setDaemon(true);
      return â˜ƒ;
   }

   public void start() {
      this.logAppenderThread.start();
   }

   public void close() {
      if (!this.isClosing.getAndSet(true)) {
         this.runFinalizers();
      }
   }

   void runFinalizers() {
      this.finalizers.forEach(Runnable::run);
   }

   public void print(JTextArea var1, JScrollPane var2, String var3) {
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> this.print(â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         Document â˜ƒ = â˜ƒ.getDocument();
         JScrollBar â˜ƒx = â˜ƒ.getVerticalScrollBar();
         boolean â˜ƒxx = false;
         if (â˜ƒ.getViewport().getView() == â˜ƒ) {
            â˜ƒxx = (double)â˜ƒx.getValue() + â˜ƒx.getSize().getHeight() + (double)(MONOSPACED.getSize() * 4) > (double)â˜ƒx.getMaximum();
         }

         try {
            â˜ƒ.insertString(â˜ƒ.getLength(), â˜ƒ, null);
         } catch (BadLocationException var8) {
         }

         if (â˜ƒxx) {
            â˜ƒx.setValue(Integer.MAX_VALUE);
         }
      }
   }
}
