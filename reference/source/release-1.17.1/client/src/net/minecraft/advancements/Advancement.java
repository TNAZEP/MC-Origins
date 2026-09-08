package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
   private final Advancement parent;
   private final DisplayInfo display;
   private final AdvancementRewards rewards;
   private final ResourceLocation id;
   private final Map<String, Criterion> criteria;
   private final String[][] requirements;
   private final Set<Advancement> children = Sets.<Advancement>newLinkedHashSet();
   private final Component chatComponent;

   public Advancement(
      ResourceLocation var1, @Nullable Advancement var2, @Nullable DisplayInfo var3, AdvancementRewards var4, Map<String, Criterion> var5, String[][] var6
   ) {
      this.id = â˜ƒ;
      this.display = â˜ƒ;
      this.criteria = ImmutableMap.copyOf(â˜ƒ);
      this.parent = â˜ƒ;
      this.rewards = â˜ƒ;
      this.requirements = â˜ƒ;
      if (â˜ƒ != null) {
         â˜ƒ.addChild(this);
      }

      if (â˜ƒ == null) {
         this.chatComponent = new TextComponent(â˜ƒ.toString());
      } else {
         Component â˜ƒ = â˜ƒ.getTitle();
         ChatFormatting â˜ƒx = â˜ƒ.getFrame().getChatColor();
         Component â˜ƒxx = ComponentUtils.mergeStyles(â˜ƒ.copy(), Style.EMPTY.withColor(â˜ƒx)).append("\n").append(â˜ƒ.getDescription());
         Component â˜ƒxxx = â˜ƒ.copy().withStyle(var1x -> var1x.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, â˜ƒ)));
         this.chatComponent = ComponentUtils.wrapInSquareBrackets(â˜ƒxxx).withStyle(â˜ƒx);
      }
   }

   public Advancement.Builder deconstruct() {
      return new Advancement.Builder(this.parent == null ? null : this.parent.getId(), this.display, this.rewards, this.criteria, this.requirements);
   }

   @Nullable
   public Advancement getParent() {
      return this.parent;
   }

   @Nullable
   public DisplayInfo getDisplay() {
      return this.display;
   }

   public AdvancementRewards getRewards() {
      return this.rewards;
   }

   public String toString() {
      return "SimpleAdvancement{id="
         + this.getId()
         + ", parent="
         + (this.parent == null ? "null" : this.parent.getId())
         + ", display="
         + this.display
         + ", rewards="
         + this.rewards
         + ", criteria="
         + this.criteria
         + ", requirements="
         + Arrays.deepToString(this.requirements)
         + "}";
   }

   public Iterable<Advancement> getChildren() {
      return this.children;
   }

   public Map<String, Criterion> getCriteria() {
      return this.criteria;
   }

   public int getMaxCriteraRequired() {
      return this.requirements.length;
   }

   public void addChild(Advancement var1) {
      this.children.add(â˜ƒ);
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Advancement)) {
         return false;
      } else {
         Advancement â˜ƒ = (Advancement)â˜ƒ;
         return this.id.equals(â˜ƒ.id);
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public String[][] getRequirements() {
      return this.requirements;
   }

   public Component getChatComponent() {
      return this.chatComponent;
   }

   public static class Builder {
      private ResourceLocation parentId;
      private Advancement parent;
      private DisplayInfo display;
      private AdvancementRewards rewards = AdvancementRewards.EMPTY;
      private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
      private String[][] requirements;
      private RequirementsStrategy requirementsStrategy = RequirementsStrategy.AND;

      Builder(@Nullable ResourceLocation var1, @Nullable DisplayInfo var2, AdvancementRewards var3, Map<String, Criterion> var4, String[][] var5) {
         this.parentId = â˜ƒ;
         this.display = â˜ƒ;
         this.rewards = â˜ƒ;
         this.criteria = â˜ƒ;
         this.requirements = â˜ƒ;
      }

      private Builder() {
      }

      public static Advancement.Builder advancement() {
         return new Advancement.Builder();
      }

      public Advancement.Builder parent(Advancement var1) {
         this.parent = â˜ƒ;
         return this;
      }

      public Advancement.Builder parent(ResourceLocation var1) {
         this.parentId = â˜ƒ;
         return this;
      }

      public Advancement.Builder display(
         ItemStack var1, Component var2, Component var3, @Nullable ResourceLocation var4, FrameType var5, boolean var6, boolean var7, boolean var8
      ) {
         return this.display(new DisplayInfo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }

      public Advancement.Builder display(
         ItemLike var1, Component var2, Component var3, @Nullable ResourceLocation var4, FrameType var5, boolean var6, boolean var7, boolean var8
      ) {
         return this.display(new DisplayInfo(new ItemStack(â˜ƒ.asItem()), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }

      public Advancement.Builder display(DisplayInfo var1) {
         this.display = â˜ƒ;
         return this;
      }

      public Advancement.Builder rewards(AdvancementRewards.Builder var1) {
         return this.rewards(â˜ƒ.build());
      }

      public Advancement.Builder rewards(AdvancementRewards var1) {
         this.rewards = â˜ƒ;
         return this;
      }

      public Advancement.Builder addCriterion(String var1, CriterionTriggerInstance var2) {
         return this.addCriterion(â˜ƒ, new Criterion(â˜ƒ));
      }

      public Advancement.Builder addCriterion(String var1, Criterion var2) {
         if (this.criteria.containsKey(â˜ƒ)) {
            throw new IllegalArgumentException("Duplicate criterion " + â˜ƒ);
         } else {
            this.criteria.put(â˜ƒ, â˜ƒ);
            return this;
         }
      }

      public Advancement.Builder requirements(RequirementsStrategy var1) {
         this.requirementsStrategy = â˜ƒ;
         return this;
      }

      public Advancement.Builder requirements(String[][] var1) {
         this.requirements = â˜ƒ;
         return this;
      }

      public boolean canBuild(Function<ResourceLocation, Advancement> var1) {
         if (this.parentId == null) {
            return true;
         } else {
            if (this.parent == null) {
               this.parent = (Advancement)â˜ƒ.apply(this.parentId);
            }

            return this.parent != null;
         }
      }

      public Advancement build(ResourceLocation var1) {
         if (!this.canBuild(var0 -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.requirements == null) {
               this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
            }

            return new Advancement(â˜ƒ, this.parent, this.display, this.rewards, this.criteria, this.requirements);
         }
      }

      public Advancement save(Consumer<Advancement> var1, String var2) {
         Advancement â˜ƒ = this.build(new ResourceLocation(â˜ƒ));
         â˜ƒ.accept(â˜ƒ);
         return â˜ƒ;
      }

      public JsonObject serializeToJson() {
         if (this.requirements == null) {
            this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
         }

         JsonObject â˜ƒ = new JsonObject();
         if (this.parent != null) {
            â˜ƒ.addProperty("parent", this.parent.getId().toString());
         } else if (this.parentId != null) {
            â˜ƒ.addProperty("parent", this.parentId.toString());
         }

         if (this.display != null) {
            â˜ƒ.add("display", this.display.serializeToJson());
         }

         â˜ƒ.add("rewards", this.rewards.serializeToJson());
         JsonObject â˜ƒ = new JsonObject();

         for(Entry<String, Criterion> â˜ƒx : this.criteria.entrySet()) {
            â˜ƒ.add((String)â˜ƒx.getKey(), ((Criterion)â˜ƒx.getValue()).serializeToJson());
         }

         â˜ƒ.add("criteria", â˜ƒ);
         JsonArray â˜ƒx = new JsonArray();

         for(String[] â˜ƒxx : this.requirements) {
            JsonArray â˜ƒxxx = new JsonArray();

            for(String â˜ƒxxxx : â˜ƒxx) {
               â˜ƒxxx.add(â˜ƒxxxx);
            }

            â˜ƒx.add(â˜ƒxxx);
         }

         â˜ƒ.add("requirements", â˜ƒx);
         return â˜ƒ;
      }

      public void serializeToNetwork(FriendlyByteBuf var1) {
         if (this.parentId == null) {
            â˜ƒ.writeBoolean(false);
         } else {
            â˜ƒ.writeBoolean(true);
            â˜ƒ.writeResourceLocation(this.parentId);
         }

         if (this.display == null) {
            â˜ƒ.writeBoolean(false);
         } else {
            â˜ƒ.writeBoolean(true);
            this.display.serializeToNetwork(â˜ƒ);
         }

         Criterion.serializeToNetwork(this.criteria, â˜ƒ);
         â˜ƒ.writeVarInt(this.requirements.length);

         for(String[] â˜ƒ : this.requirements) {
            â˜ƒ.writeVarInt(â˜ƒ.length);

            for(String â˜ƒx : â˜ƒ) {
               â˜ƒ.writeUtf(â˜ƒx);
            }
         }
      }

      public String toString() {
         return "Task Advancement{parentId="
            + this.parentId
            + ", display="
            + this.display
            + ", rewards="
            + this.rewards
            + ", criteria="
            + this.criteria
            + ", requirements="
            + Arrays.deepToString(this.requirements)
            + "}";
      }

      public static Advancement.Builder fromJson(JsonObject var0, DeserializationContext var1) {
         ResourceLocation â˜ƒ = â˜ƒ.has("parent") ? new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "parent")) : null;
         DisplayInfo â˜ƒx = â˜ƒ.has("display") ? DisplayInfo.fromJson(GsonHelper.getAsJsonObject(â˜ƒ, "display")) : null;
         AdvancementRewards â˜ƒxx = â˜ƒ.has("rewards") ? AdvancementRewards.deserialize(GsonHelper.getAsJsonObject(â˜ƒ, "rewards")) : AdvancementRewards.EMPTY;
         Map<String, Criterion> â˜ƒxxx = Criterion.criteriaFromJson(GsonHelper.getAsJsonObject(â˜ƒ, "criteria"), â˜ƒ);
         if (â˜ƒxxx.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "requirements", new JsonArray());
            String[][] â˜ƒx = new String[â˜ƒ.size()][];

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
               JsonArray â˜ƒxxx = GsonHelper.convertToJsonArray(â˜ƒ.get(â˜ƒxx), "requirements[" + â˜ƒxx + "]");
               â˜ƒx[â˜ƒxx] = new String[â˜ƒxxx.size()];

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.size(); ++â˜ƒxxxx) {
                  â˜ƒx[â˜ƒxx][â˜ƒxxxx] = GsonHelper.convertToString(â˜ƒxxx.get(â˜ƒxxxx), "requirements[" + â˜ƒxx + "][" + â˜ƒxxxx + "]");
               }
            }

            if (â˜ƒx.length == 0) {
               â˜ƒx = new String[â˜ƒxxx.size()][];
               int â˜ƒxx = 0;

               for(String â˜ƒxxx : â˜ƒxxx.keySet()) {
                  â˜ƒx[â˜ƒxx++] = new String[]{â˜ƒxxx};
               }
            }

            for(String[] â˜ƒxx : â˜ƒx) {
               if (â˜ƒxx.length == 0 && â˜ƒxxx.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for(String â˜ƒxxx : â˜ƒxx) {
                  if (!â˜ƒxxx.containsKey(â˜ƒxxx)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + â˜ƒxxx + "'");
                  }
               }
            }

            for(String â˜ƒxx : â˜ƒxxx.keySet()) {
               boolean â˜ƒxxx = false;

               for(String[] â˜ƒxxxx : â˜ƒx) {
                  if (ArrayUtils.contains(â˜ƒxxxx, â˜ƒxx)) {
                     â˜ƒxxx = true;
                     break;
                  }
               }

               if (!â˜ƒxxx) {
                  throw new JsonSyntaxException(
                     "Criterion '" + â˜ƒxx + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
                  );
               }
            }

            return new Advancement.Builder(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒx);
         }
      }

      public static Advancement.Builder fromNetwork(FriendlyByteBuf var0) {
         ResourceLocation â˜ƒ = â˜ƒ.readBoolean() ? â˜ƒ.readResourceLocation() : null;
         DisplayInfo â˜ƒx = â˜ƒ.readBoolean() ? DisplayInfo.fromNetwork(â˜ƒ) : null;
         Map<String, Criterion> â˜ƒxx = Criterion.criteriaFromNetwork(â˜ƒ);
         String[][] â˜ƒxxx = new String[â˜ƒ.readVarInt()][];

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.length; ++â˜ƒxxxx) {
            â˜ƒxxx[â˜ƒxxxx] = new String[â˜ƒ.readVarInt()];

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx[â˜ƒxxxx].length; ++â˜ƒxxxxx) {
               â˜ƒxxx[â˜ƒxxxx][â˜ƒxxxxx] = â˜ƒ.readUtf();
            }
         }

         return new Advancement.Builder(â˜ƒ, â˜ƒx, AdvancementRewards.EMPTY, â˜ƒxx, â˜ƒxxx);
      }

      public Map<String, Criterion> getCriteria() {
         return this.criteria;
      }
   }
}
