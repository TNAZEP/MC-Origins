package net.minecraft.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import net.minecraft.tags.TagContainer;
import net.minecraft.tags.TagManager;
import net.minecraft.util.Unit;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;

public class ServerResources implements AutoCloseable {
   private static final CompletableFuture<Unit> DATA_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
   private final ReloadableResourceManager resources = new SimpleReloadableResourceManager(PackType.SERVER_DATA);
   private final Commands commands;
   private final RecipeManager recipes = new RecipeManager();
   private final TagManager tagManager;
   private final PredicateManager predicateManager = new PredicateManager();
   private final LootTables lootTables = new LootTables(this.predicateManager);
   private final ItemModifierManager itemModifierManager = new ItemModifierManager(this.predicateManager, this.lootTables);
   private final ServerAdvancementManager advancements = new ServerAdvancementManager(this.predicateManager);
   private final ServerFunctionLibrary functionLibrary;

   public ServerResources(RegistryAccess var1, Commands.CommandSelection var2, int var3) {
      this.tagManager = new TagManager(â˜ƒ);
      this.commands = new Commands(â˜ƒ);
      this.functionLibrary = new ServerFunctionLibrary(â˜ƒ, this.commands.getDispatcher());
      this.resources.registerReloadListener(this.tagManager);
      this.resources.registerReloadListener(this.predicateManager);
      this.resources.registerReloadListener(this.recipes);
      this.resources.registerReloadListener(this.lootTables);
      this.resources.registerReloadListener(this.itemModifierManager);
      this.resources.registerReloadListener(this.functionLibrary);
      this.resources.registerReloadListener(this.advancements);
   }

   public ServerFunctionLibrary getFunctionLibrary() {
      return this.functionLibrary;
   }

   public PredicateManager getPredicateManager() {
      return this.predicateManager;
   }

   public LootTables getLootTables() {
      return this.lootTables;
   }

   public ItemModifierManager getItemModifierManager() {
      return this.itemModifierManager;
   }

   public TagContainer getTags() {
      return this.tagManager.getTags();
   }

   public RecipeManager getRecipeManager() {
      return this.recipes;
   }

   public Commands getCommands() {
      return this.commands;
   }

   public ServerAdvancementManager getAdvancements() {
      return this.advancements;
   }

   public ResourceManager getResourceManager() {
      return this.resources;
   }

   public static CompletableFuture<ServerResources> loadResources(
      List<PackResources> var0, RegistryAccess var1, Commands.CommandSelection var2, int var3, Executor var4, Executor var5
   ) {
      ServerResources â˜ƒ = new ServerResources(â˜ƒ, â˜ƒ, â˜ƒ);
      CompletableFuture<Unit> â˜ƒx = â˜ƒ.resources.reload(â˜ƒ, â˜ƒ, â˜ƒ, DATA_RELOAD_INITIAL_TASK);
      return â˜ƒx.whenComplete((var1x, var2x) -> {
         if (var2x != null) {
            â˜ƒ.close();
         }
      }).thenApply(var1x -> â˜ƒ);
   }

   public void updateGlobals() {
      this.tagManager.getTags().bindToGlobal();
   }

   public void close() {
      this.resources.close();
   }
}
