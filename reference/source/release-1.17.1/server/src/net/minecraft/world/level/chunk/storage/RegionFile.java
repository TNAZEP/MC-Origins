package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionFile implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SECTOR_BYTES = 4096;
   @VisibleForTesting
   protected static final int SECTOR_INTS = 1024;
   private static final int CHUNK_HEADER_SIZE = 5;
   private static final int HEADER_OFFSET = 0;
   private static final ByteBuffer PADDING_BUFFER = ByteBuffer.allocateDirect(1);
   private static final String EXTERNAL_FILE_EXTENSION = ".mcc";
   private static final int EXTERNAL_STREAM_FLAG = 128;
   private static final int EXTERNAL_CHUNK_THRESHOLD = 256;
   private static final int CHUNK_NOT_PRESENT = 0;
   private final FileChannel file;
   private final Path externalFileDir;
   final RegionFileVersion version;
   private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
   private final IntBuffer offsets;
   private final IntBuffer timestamps;
   @VisibleForTesting
   protected final RegionBitmap usedSectors = new RegionBitmap();

   public RegionFile(File var1, File var2, boolean var3) throws IOException {
      this(â˜ƒ.toPath(), â˜ƒ.toPath(), RegionFileVersion.VERSION_DEFLATE, â˜ƒ);
   }

   public RegionFile(Path var1, Path var2, RegionFileVersion var3, boolean var4) throws IOException {
      this.version = â˜ƒ;
      if (!Files.isDirectory(â˜ƒ, new LinkOption[0])) {
         throw new IllegalArgumentException("Expected directory, got " + â˜ƒ.toAbsolutePath());
      } else {
         this.externalFileDir = â˜ƒ;
         this.offsets = this.header.asIntBuffer();
         this.offsets.limit(1024);
         this.header.position(4096);
         this.timestamps = this.header.asIntBuffer();
         if (â˜ƒ) {
            this.file = FileChannel.open(â˜ƒ, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
         } else {
            this.file = FileChannel.open(â˜ƒ, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
         }

         this.usedSectors.force(0, 2);
         this.header.position(0);
         int â˜ƒ = this.file.read(this.header, 0L);
         if (â˜ƒ != -1) {
            if (â˜ƒ != 8192) {
               LOGGER.warn("Region file {} has truncated header: {}", â˜ƒ, â˜ƒ);
            }

            long â˜ƒx = Files.size(â˜ƒ);

            for(int â˜ƒxx = 0; â˜ƒxx < 1024; ++â˜ƒxx) {
               int â˜ƒxxx = this.offsets.get(â˜ƒxx);
               if (â˜ƒxxx != 0) {
                  int â˜ƒxxxx = getSectorNumber(â˜ƒxxx);
                  int â˜ƒxxxxx = getNumSectors(â˜ƒxxx);
                  if (â˜ƒxxxx < 2) {
                     LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", â˜ƒ, â˜ƒxx, â˜ƒxxxx);
                     this.offsets.put(â˜ƒxx, 0);
                  } else if (â˜ƒxxxxx == 0) {
                     LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", â˜ƒ, â˜ƒxx);
                     this.offsets.put(â˜ƒxx, 0);
                  } else if ((long)â˜ƒxxxx * 4096L > â˜ƒx) {
                     LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", â˜ƒ, â˜ƒxx, â˜ƒxxxx);
                     this.offsets.put(â˜ƒxx, 0);
                  } else {
                     this.usedSectors.force(â˜ƒxxxx, â˜ƒxxxxx);
                  }
               }
            }
         }
      }
   }

   private Path getExternalChunkPath(ChunkPos var1) {
      String â˜ƒ = "c." + â˜ƒ.x + "." + â˜ƒ.z + ".mcc";
      return this.externalFileDir.resolve(â˜ƒ);
   }

   @Nullable
   public synchronized DataInputStream getChunkDataInputStream(ChunkPos var1) throws IOException {
      int â˜ƒ = this.getOffset(â˜ƒ);
      if (â˜ƒ == 0) {
         return null;
      } else {
         int â˜ƒ = getSectorNumber(â˜ƒ);
         int â˜ƒx = getNumSectors(â˜ƒ);
         int â˜ƒxx = â˜ƒx * 4096;
         ByteBuffer â˜ƒxxx = ByteBuffer.allocate(â˜ƒxx);
         this.file.read(â˜ƒxxx, (long)(â˜ƒ * 4096));
         â˜ƒxxx.flip();
         if (â˜ƒxxx.remaining() < 5) {
            LOGGER.error("Chunk {} header is truncated: expected {} but read {}", â˜ƒ, â˜ƒxx, â˜ƒxxx.remaining());
            return null;
         } else {
            int â˜ƒ = â˜ƒxxx.getInt();
            byte â˜ƒx = â˜ƒxxx.get();
            if (â˜ƒ == 0) {
               LOGGER.warn("Chunk {} is allocated, but stream is missing", â˜ƒ);
               return null;
            } else {
               int â˜ƒ = â˜ƒ - 1;
               if (isExternalStreamChunk(â˜ƒx)) {
                  if (â˜ƒ != 0) {
                     LOGGER.warn("Chunk has both internal and external streams");
                  }

                  return this.createExternalChunkInputStream(â˜ƒ, getExternalChunkVersion(â˜ƒx));
               } else if (â˜ƒ > â˜ƒxxx.remaining()) {
                  LOGGER.error("Chunk {} stream is truncated: expected {} but read {}", â˜ƒ, â˜ƒ, â˜ƒxxx.remaining());
                  return null;
               } else if (â˜ƒ < 0) {
                  LOGGER.error("Declared size {} of chunk {} is negative", â˜ƒ, â˜ƒ);
                  return null;
               } else {
                  return this.createChunkInputStream(â˜ƒ, â˜ƒx, createStream(â˜ƒxxx, â˜ƒ));
               }
            }
         }
      }
   }

   private static int getTimestamp() {
      return (int)(Util.getEpochMillis() / 1000L);
   }

   private static boolean isExternalStreamChunk(byte var0) {
      return (â˜ƒ & 128) != 0;
   }

   private static byte getExternalChunkVersion(byte var0) {
      return (byte)(â˜ƒ & -129);
   }

   @Nullable
   private DataInputStream createChunkInputStream(ChunkPos var1, byte var2, InputStream var3) throws IOException {
      RegionFileVersion â˜ƒ = RegionFileVersion.fromId(â˜ƒ);
      if (â˜ƒ == null) {
         LOGGER.error("Chunk {} has invalid chunk stream version {}", â˜ƒ, â˜ƒ);
         return null;
      } else {
         return new DataInputStream(new BufferedInputStream(â˜ƒ.wrap(â˜ƒ)));
      }
   }

   @Nullable
   private DataInputStream createExternalChunkInputStream(ChunkPos var1, byte var2) throws IOException {
      Path â˜ƒ = this.getExternalChunkPath(â˜ƒ);
      if (!Files.isRegularFile(â˜ƒ, new LinkOption[0])) {
         LOGGER.error("External chunk path {} is not file", â˜ƒ);
         return null;
      } else {
         return this.createChunkInputStream(â˜ƒ, â˜ƒ, Files.newInputStream(â˜ƒ));
      }
   }

   private static ByteArrayInputStream createStream(ByteBuffer var0, int var1) {
      return new ByteArrayInputStream(â˜ƒ.array(), â˜ƒ.position(), â˜ƒ);
   }

   private int packSectorOffset(int var1, int var2) {
      return â˜ƒ << 8 | â˜ƒ;
   }

   private static int getNumSectors(int var0) {
      return â˜ƒ & 0xFF;
   }

   private static int getSectorNumber(int var0) {
      return â˜ƒ >> 8 & 16777215;
   }

   private static int sizeToSectors(int var0) {
      return (â˜ƒ + 4096 - 1) / 4096;
   }

   public boolean doesChunkExist(ChunkPos var1) {
      int â˜ƒ = this.getOffset(â˜ƒ);
      if (â˜ƒ == 0) {
         return false;
      } else {
         int â˜ƒ = getSectorNumber(â˜ƒ);
         int â˜ƒx = getNumSectors(â˜ƒ);
         ByteBuffer â˜ƒxx = ByteBuffer.allocate(5);

         try {
            this.file.read(â˜ƒxx, (long)(â˜ƒ * 4096));
            â˜ƒxx.flip();
            if (â˜ƒxx.remaining() != 5) {
               return false;
            } else {
               int â˜ƒxxx = â˜ƒxx.getInt();
               byte â˜ƒxxxx = â˜ƒxx.get();
               if (isExternalStreamChunk(â˜ƒxxxx)) {
                  if (!RegionFileVersion.isValidVersion(getExternalChunkVersion(â˜ƒxxxx))) {
                     return false;
                  }

                  if (!Files.isRegularFile(this.getExternalChunkPath(â˜ƒ), new LinkOption[0])) {
                     return false;
                  }
               } else {
                  if (!RegionFileVersion.isValidVersion(â˜ƒxxxx)) {
                     return false;
                  }

                  if (â˜ƒxxx == 0) {
                     return false;
                  }

                  int â˜ƒxxx = â˜ƒxxx - 1;
                  if (â˜ƒxxx < 0 || â˜ƒxxx > 4096 * â˜ƒx) {
                     return false;
                  }
               }

               return true;
            }
         } catch (IOException var9) {
            return false;
         }
      }
   }

   public DataOutputStream getChunkDataOutputStream(ChunkPos var1) throws IOException {
      return new DataOutputStream(new BufferedOutputStream(this.version.wrap(new RegionFile.ChunkBuffer(â˜ƒ))));
   }

   public void flush() throws IOException {
      this.file.force(true);
   }

   public void clear(ChunkPos var1) throws IOException {
      int â˜ƒ = getOffsetIndex(â˜ƒ);
      int â˜ƒx = this.offsets.get(â˜ƒ);
      if (â˜ƒx != 0) {
         this.offsets.put(â˜ƒ, 0);
         this.timestamps.put(â˜ƒ, getTimestamp());
         this.writeHeader();
         Files.deleteIfExists(this.getExternalChunkPath(â˜ƒ));
         this.usedSectors.free(getSectorNumber(â˜ƒx), getNumSectors(â˜ƒx));
      }
   }

   protected synchronized void write(ChunkPos var1, ByteBuffer var2) throws IOException {
      int â˜ƒxx = getOffsetIndex(â˜ƒ);
      int â˜ƒxxx = this.offsets.get(â˜ƒxx);
      int â˜ƒxxxx = getSectorNumber(â˜ƒxxx);
      int â˜ƒxxxxx = getNumSectors(â˜ƒxxx);
      int â˜ƒxxxxxx = â˜ƒ.remaining();
      int â˜ƒxxxxxxx = sizeToSectors(â˜ƒxxxxxx);
      int â˜ƒ;
      RegionFile.CommitOp â˜ƒx;
      if (â˜ƒxxxxxxx >= 256) {
         Path â˜ƒxxxxxxxx = this.getExternalChunkPath(â˜ƒ);
         LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxx);
         â˜ƒxxxxxxx = 1;
         â˜ƒ = this.usedSectors.allocate(â˜ƒxxxxxxx);
         â˜ƒx = this.writeToExternalFile(â˜ƒxxxxxxxx, â˜ƒ);
         ByteBuffer â˜ƒxxxxxxxxx = this.createExternalStub();
         this.file.write(â˜ƒxxxxxxxxx, (long)(â˜ƒ * 4096));
      } else {
         â˜ƒ = this.usedSectors.allocate(â˜ƒxxxxxxx);
         â˜ƒx = () -> Files.deleteIfExists(this.getExternalChunkPath(â˜ƒ));
         this.file.write(â˜ƒ, (long)(â˜ƒ * 4096));
      }

      this.offsets.put(â˜ƒxx, this.packSectorOffset(â˜ƒ, â˜ƒxxxxxxx));
      this.timestamps.put(â˜ƒxx, getTimestamp());
      this.writeHeader();
      â˜ƒx.run();
      if (â˜ƒxxxx != 0) {
         this.usedSectors.free(â˜ƒxxxx, â˜ƒxxxxx);
      }
   }

   private ByteBuffer createExternalStub() {
      ByteBuffer â˜ƒ = ByteBuffer.allocate(5);
      â˜ƒ.putInt(1);
      â˜ƒ.put((byte)(this.version.getId() | 128));
      â˜ƒ.flip();
      return â˜ƒ;
   }

   private RegionFile.CommitOp writeToExternalFile(Path var1, ByteBuffer var2) throws IOException {
      Path â˜ƒ = Files.createTempFile(this.externalFileDir, "tmp", null);
      FileChannel â˜ƒx = FileChannel.open(â˜ƒ, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         â˜ƒ.position(5);
         â˜ƒx.write(â˜ƒ);
      } catch (Throwable var8) {
         if (â˜ƒx != null) {
            try {
               â˜ƒx.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }

      return () -> Files.move(â˜ƒ, â˜ƒ, StandardCopyOption.REPLACE_EXISTING);
   }

   private void writeHeader() throws IOException {
      this.header.position(0);
      this.file.write(this.header, 0L);
   }

   private int getOffset(ChunkPos var1) {
      return this.offsets.get(getOffsetIndex(â˜ƒ));
   }

   public boolean hasChunk(ChunkPos var1) {
      return this.getOffset(â˜ƒ) != 0;
   }

   private static int getOffsetIndex(ChunkPos var0) {
      return â˜ƒ.getRegionLocalX() + â˜ƒ.getRegionLocalZ() * 32;
   }

   public void close() throws IOException {
      try {
         this.padToFullSector();
      } finally {
         try {
            this.file.force(true);
         } finally {
            this.file.close();
         }
      }
   }

   private void padToFullSector() throws IOException {
      int â˜ƒ = (int)this.file.size();
      int â˜ƒx = sizeToSectors(â˜ƒ) * 4096;
      if (â˜ƒ != â˜ƒx) {
         ByteBuffer â˜ƒxx = PADDING_BUFFER.duplicate();
         â˜ƒxx.position(0);
         this.file.write(â˜ƒxx, (long)(â˜ƒx - 1));
      }
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final ChunkPos pos;

      public ChunkBuffer(ChunkPos var2) {
         super(8096);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(RegionFile.this.version.getId());
         this.pos = â˜ƒ;
      }

      public void close() throws IOException {
         ByteBuffer â˜ƒ = ByteBuffer.wrap(this.buf, 0, this.count);
         â˜ƒ.putInt(0, this.count - 5 + 1);
         RegionFile.this.write(this.pos, â˜ƒ);
      }
   }

   interface CommitOp {
      void run() throws IOException;
   }
}
