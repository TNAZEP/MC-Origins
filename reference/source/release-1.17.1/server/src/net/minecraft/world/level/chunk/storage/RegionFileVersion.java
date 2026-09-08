package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;

public class RegionFileVersion {
   private static final Int2ObjectMap<RegionFileVersion> VERSIONS = new Int2ObjectOpenHashMap<>();
   public static final RegionFileVersion VERSION_GZIP = register(new RegionFileVersion(1, GZIPInputStream::new, GZIPOutputStream::new));
   public static final RegionFileVersion VERSION_DEFLATE = register(new RegionFileVersion(2, InflaterInputStream::new, DeflaterOutputStream::new));
   public static final RegionFileVersion VERSION_NONE = register(new RegionFileVersion(3, var0 -> var0, var0 -> var0));
   private final int id;
   private final RegionFileVersion.StreamWrapper<InputStream> inputWrapper;
   private final RegionFileVersion.StreamWrapper<OutputStream> outputWrapper;

   private RegionFileVersion(int var1, RegionFileVersion.StreamWrapper<InputStream> var2, RegionFileVersion.StreamWrapper<OutputStream> var3) {
      this.id = â˜ƒ;
      this.inputWrapper = â˜ƒ;
      this.outputWrapper = â˜ƒ;
   }

   private static RegionFileVersion register(RegionFileVersion var0) {
      VERSIONS.put(â˜ƒ.id, â˜ƒ);
      return â˜ƒ;
   }

   @Nullable
   public static RegionFileVersion fromId(int var0) {
      return VERSIONS.get(â˜ƒ);
   }

   public static boolean isValidVersion(int var0) {
      return VERSIONS.containsKey(â˜ƒ);
   }

   public int getId() {
      return this.id;
   }

   public OutputStream wrap(OutputStream var1) throws IOException {
      return (OutputStream)this.outputWrapper.wrap(â˜ƒ);
   }

   public InputStream wrap(InputStream var1) throws IOException {
      return (InputStream)this.inputWrapper.wrap(â˜ƒ);
   }

   @FunctionalInterface
   interface StreamWrapper<O> {
      O wrap(O var1) throws IOException;
   }
}
