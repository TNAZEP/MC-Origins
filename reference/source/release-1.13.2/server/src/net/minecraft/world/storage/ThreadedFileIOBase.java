package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.util.DefaultUncaughtExceptionHandlerWithName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadedFileIOBase implements Runnable {
   private static final Logger field_199622_a = LogManager.getLogger();
   private static final ThreadedFileIOBase field_75741_a = new ThreadedFileIOBase();
   private final List<IThreadedFileIO> field_75739_b = Collections.synchronizedList(Lists.newArrayList());
   private volatile long field_75740_c;
   private volatile long field_75737_d;
   private volatile boolean field_75738_e;

   private ThreadedFileIOBase() {
      Thread ☃ = new Thread(this, "File IO Thread");
      ☃.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(field_199622_a));
      ☃.setPriority(1);
      ☃.start();
   }

   public static ThreadedFileIOBase func_178779_a() {
      return field_75741_a;
   }

   public void run() {
      while(true) {
         this.func_75736_b();
      }
   }

   private void func_75736_b() {
      for(int ☃ = 0; ☃ < this.field_75739_b.size(); ++☃) {
         IThreadedFileIO ☃xx = (IThreadedFileIO)this.field_75739_b.get(☃);
         boolean ☃x;
         synchronized(☃xx) {
            ☃x = ☃xx.func_75814_c();
         }

         if (!☃x) {
            this.field_75739_b.remove(☃--);
            ++this.field_75737_d;
         }

         try {
            Thread.sleep(this.field_75738_e ? 0L : 10L);
         } catch (InterruptedException var7) {
            var7.printStackTrace();
         }
      }

      if (this.field_75739_b.isEmpty()) {
         try {
            Thread.sleep(25L);
         } catch (InterruptedException var6) {
            var6.printStackTrace();
         }
      }
   }

   public void func_75735_a(IThreadedFileIO var1) {
      if (!this.field_75739_b.contains(☃)) {
         ++this.field_75740_c;
         this.field_75739_b.add(☃);
      }
   }

   public void func_75734_a() throws InterruptedException {
      this.field_75738_e = true;

      while(this.field_75740_c != this.field_75737_d) {
         Thread.sleep(10L);
      }

      this.field_75738_e = false;
   }
}
