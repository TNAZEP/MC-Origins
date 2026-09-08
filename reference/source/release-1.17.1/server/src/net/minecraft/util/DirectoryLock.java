package net.minecraft.util;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DirectoryLock implements AutoCloseable {
   public static final String LOCK_FILE = "session.lock";
   private final FileChannel lockFile;
   private final FileLock lock;
   private static final ByteBuffer DUMMY;

   public static DirectoryLock create(Path var0) throws IOException {
      Path â˜ƒ = â˜ƒ.resolve("session.lock");
      if (!Files.isDirectory(â˜ƒ, new LinkOption[0])) {
         Files.createDirectories(â˜ƒ);
      }

      FileChannel â˜ƒ = FileChannel.open(â˜ƒ, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         â˜ƒ.write(DUMMY.duplicate());
         â˜ƒ.force(true);
         FileLock â˜ƒx = â˜ƒ.tryLock();
         if (â˜ƒx == null) {
            throw DirectoryLock.LockException.alreadyLocked(â˜ƒ);
         } else {
            return new DirectoryLock(â˜ƒ, â˜ƒx);
         }
      } catch (IOException var6) {
         try {
            â˜ƒ.close();
         } catch (IOException var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }
   }

   private DirectoryLock(FileChannel var1, FileLock var2) {
      this.lockFile = â˜ƒ;
      this.lock = â˜ƒ;
   }

   public void close() throws IOException {
      try {
         if (this.lock.isValid()) {
            this.lock.release();
         }
      } finally {
         if (this.lockFile.isOpen()) {
            this.lockFile.close();
         }
      }
   }

   public boolean isValid() {
      return this.lock.isValid();
   }

   public static boolean isLocked(Path var0) throws IOException {
      Path â˜ƒ = â˜ƒ.resolve("session.lock");

      try {
         FileChannel â˜ƒx = FileChannel.open(â˜ƒ, StandardOpenOption.WRITE);

         boolean var4;
         try {
            FileLock â˜ƒxx = â˜ƒx.tryLock();

            try {
               var4 = â˜ƒxx == null;
            } catch (Throwable var8) {
               if (â˜ƒxx != null) {
                  try {
                     â˜ƒxx.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (â˜ƒxx != null) {
               â˜ƒxx.close();
            }
         } catch (Throwable var9) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var9.addSuppressed(var6);
               }
            }

            throw var9;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return var4;
      } catch (AccessDeniedException var10) {
         return true;
      } catch (NoSuchFileException var11) {
         return false;
      }
   }

   static {
      byte[] â˜ƒ = "\u2603".getBytes(Charsets.UTF_8);
      DUMMY = ByteBuffer.allocateDirect(â˜ƒ.length);
      DUMMY.put(â˜ƒ);
      DUMMY.flip();
   }

   public static class LockException extends IOException {
      private LockException(Path var1, String var2) {
         super(â˜ƒ.toAbsolutePath() + ": " + â˜ƒ);
      }

      public static DirectoryLock.LockException alreadyLocked(Path var0) {
         return new DirectoryLock.LockException(â˜ƒ, "already locked (possibly by other Minecraft instance?)");
      }
   }
}
