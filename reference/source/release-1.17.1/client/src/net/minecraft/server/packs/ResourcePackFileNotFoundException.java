package net.minecraft.server.packs;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException {
   public ResourcePackFileNotFoundException(File var1, String var2) {
      super(String.format("'%s' in ResourcePack '%s'", â˜ƒ, â˜ƒ));
   }
}
