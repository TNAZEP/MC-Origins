package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementList {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<ResourceLocation, Advancement> advancements = Maps.<ResourceLocation, Advancement>newHashMap();
   private final Set<Advancement> roots = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> tasks = Sets.<Advancement>newLinkedHashSet();
   private AdvancementList.Listener listener;

   private void remove(Advancement var1) {
      for(Advancement â˜ƒ : â˜ƒ.getChildren()) {
         this.remove(â˜ƒ);
      }

      LOGGER.info("Forgot about advancement {}", â˜ƒ.getId());
      this.advancements.remove(â˜ƒ.getId());
      if (â˜ƒ.getParent() == null) {
         this.roots.remove(â˜ƒ);
         if (this.listener != null) {
            this.listener.onRemoveAdvancementRoot(â˜ƒ);
         }
      } else {
         this.tasks.remove(â˜ƒ);
         if (this.listener != null) {
            this.listener.onRemoveAdvancementTask(â˜ƒ);
         }
      }
   }

   public void remove(Set<ResourceLocation> var1) {
      for(ResourceLocation â˜ƒ : â˜ƒ) {
         Advancement â˜ƒx = (Advancement)this.advancements.get(â˜ƒ);
         if (â˜ƒx == null) {
            LOGGER.warn("Told to remove advancement {} but I don't know what that is", â˜ƒ);
         } else {
            this.remove(â˜ƒx);
         }
      }
   }

   public void add(Map<ResourceLocation, Advancement.Builder> var1) {
      Map<ResourceLocation, Advancement.Builder> â˜ƒ = Maps.<ResourceLocation, Advancement.Builder>newHashMap(â˜ƒ);

      while(!â˜ƒ.isEmpty()) {
         boolean â˜ƒx = false;
         Iterator<Entry<ResourceLocation, Advancement.Builder>> â˜ƒxx = â˜ƒ.entrySet().iterator();

         while(â˜ƒxx.hasNext()) {
            Entry<ResourceLocation, Advancement.Builder> â˜ƒxxx = (Entry)â˜ƒxx.next();
            ResourceLocation â˜ƒxxxx = (ResourceLocation)â˜ƒxxx.getKey();
            Advancement.Builder â˜ƒxxxxx = (Advancement.Builder)â˜ƒxxx.getValue();
            if (â˜ƒxxxxx.canBuild(this.advancements::get)) {
               Advancement â˜ƒxxxxxx = â˜ƒxxxxx.build(â˜ƒxxxx);
               this.advancements.put(â˜ƒxxxx, â˜ƒxxxxxx);
               â˜ƒx = true;
               â˜ƒxx.remove();
               if (â˜ƒxxxxxx.getParent() == null) {
                  this.roots.add(â˜ƒxxxxxx);
                  if (this.listener != null) {
                     this.listener.onAddAdvancementRoot(â˜ƒxxxxxx);
                  }
               } else {
                  this.tasks.add(â˜ƒxxxxxx);
                  if (this.listener != null) {
                     this.listener.onAddAdvancementTask(â˜ƒxxxxxx);
                  }
               }
            }
         }

         if (!â˜ƒx) {
            for(Entry<ResourceLocation, Advancement.Builder> â˜ƒxxx : â˜ƒ.entrySet()) {
               LOGGER.error("Couldn't load advancement {}: {}", â˜ƒxxx.getKey(), â˜ƒxxx.getValue());
            }
            break;
         }
      }

      LOGGER.info("Loaded {} advancements", this.advancements.size());
   }

   public void clear() {
      this.advancements.clear();
      this.roots.clear();
      this.tasks.clear();
      if (this.listener != null) {
         this.listener.onAdvancementsCleared();
      }
   }

   public Iterable<Advancement> getRoots() {
      return this.roots;
   }

   public Collection<Advancement> getAllAdvancements() {
      return this.advancements.values();
   }

   @Nullable
   public Advancement get(ResourceLocation var1) {
      return (Advancement)this.advancements.get(â˜ƒ);
   }

   public void setListener(@Nullable AdvancementList.Listener var1) {
      this.listener = â˜ƒ;
      if (â˜ƒ != null) {
         for(Advancement â˜ƒ : this.roots) {
            â˜ƒ.onAddAdvancementRoot(â˜ƒ);
         }

         for(Advancement â˜ƒ : this.tasks) {
            â˜ƒ.onAddAdvancementTask(â˜ƒ);
         }
      }
   }

   public interface Listener {
      void onAddAdvancementRoot(Advancement var1);

      void onRemoveAdvancementRoot(Advancement var1);

      void onAddAdvancementTask(Advancement var1);

      void onRemoveAdvancementTask(Advancement var1);

      void onAdvancementsCleared();
   }
}
