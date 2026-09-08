package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tags.TagLoader;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerFunctionLibrary implements PreparableReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String FILE_EXTENSION = ".mcfunction";
   private static final int PATH_PREFIX_LENGTH = "functions/".length();
   private static final int PATH_SUFFIX_LENGTH = ".mcfunction".length();
   private volatile Map<ResourceLocation, CommandFunction> functions = ImmutableMap.of();
   private final TagLoader<CommandFunction> tagsLoader = new TagLoader<>(this::getFunction, "tags/functions");
   private volatile TagCollection<CommandFunction> tags = TagCollection.empty();
   private final int functionCompilationLevel;
   private final CommandDispatcher<CommandSourceStack> dispatcher;

   public Optional<CommandFunction> getFunction(ResourceLocation var1) {
      return Optional.ofNullable((CommandFunction)this.functions.get(â˜ƒ));
   }

   public Map<ResourceLocation, CommandFunction> getFunctions() {
      return this.functions;
   }

   public TagCollection<CommandFunction> getTags() {
      return this.tags;
   }

   public Tag<CommandFunction> getTag(ResourceLocation var1) {
      return this.tags.getTagOrEmpty(â˜ƒ);
   }

   public ServerFunctionLibrary(int var1, CommandDispatcher<CommandSourceStack> var2) {
      this.functionCompilationLevel = â˜ƒ;
      this.dispatcher = â˜ƒ;
   }

   @Override
   public CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      CompletableFuture<Map<ResourceLocation, Tag.Builder>> â˜ƒ = CompletableFuture.supplyAsync(() -> this.tagsLoader.load(â˜ƒ), â˜ƒ);
      CompletableFuture<Map<ResourceLocation, CompletableFuture<CommandFunction>>> â˜ƒx = CompletableFuture.supplyAsync(
            () -> â˜ƒ.listResources("functions", var0x -> var0x.endsWith(".mcfunction")), â˜ƒ
         )
         .thenCompose(
            var3x -> {
               Map<ResourceLocation, CompletableFuture<CommandFunction>> â˜ƒ = Maps.newHashMap();
               CommandSourceStack â˜ƒx = new CommandSourceStack(
                  CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, null, this.functionCompilationLevel, "", TextComponent.EMPTY, null, null
               );
      
               for(ResourceLocation â˜ƒxx : var3x) {
                  String â˜ƒxxx = â˜ƒxx.getPath();
                  ResourceLocation â˜ƒxxxx = new ResourceLocation(
                     â˜ƒxx.getNamespace(), â˜ƒxxx.substring(PATH_PREFIX_LENGTH, â˜ƒxxx.length() - PATH_SUFFIX_LENGTH)
                  );
                  â˜ƒ.put(â˜ƒxxxx, CompletableFuture.supplyAsync(() -> {
                     List<String> â˜ƒ = readLines(â˜ƒ, â˜ƒ);
                     return CommandFunction.fromLines(â˜ƒ, this.dispatcher, â˜ƒ, â˜ƒ);
                  }, â˜ƒ));
               }
      
               CompletableFuture<?>[] â˜ƒxx = (CompletableFuture[])â˜ƒ.values().toArray(new CompletableFuture[0]);
               return CompletableFuture.allOf(â˜ƒxx).handle((var1x, var2x) -> â˜ƒ);
            }
         );
      return â˜ƒ.thenCombine(â˜ƒx, Pair::of).thenCompose(â˜ƒ::wait).thenAcceptAsync(var1x -> {
         Map<ResourceLocation, CompletableFuture<CommandFunction>> â˜ƒ = (Map)var1x.getSecond();
         Builder<ResourceLocation, CommandFunction> â˜ƒx = ImmutableMap.builder();
         â˜ƒ.forEach((var1xx, var2x) -> var2x.handle((var2xx, var3x) -> {
               if (var3x != null) {
                  LOGGER.error("Failed to load function {}", var1xx, var3x);
               } else {
                  â˜ƒ.put(var1xx, var2xx);
               }

               return null;
            }).join());
         this.functions = â˜ƒx.build();
         this.tags = this.tagsLoader.build((Map<ResourceLocation, Tag.Builder>)var1x.getFirst());
      }, â˜ƒ);
   }

   private static List<String> readLines(ResourceManager var0, ResourceLocation var1) {
      try {
         Resource â˜ƒ = â˜ƒ.getResource(â˜ƒ);

         List var3;
         try {
            var3 = IOUtils.readLines(â˜ƒ.getInputStream(), StandardCharsets.UTF_8);
         } catch (Throwable var6) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return var3;
      } catch (IOException var7) {
         throw new CompletionException(var7);
      }
   }
}
