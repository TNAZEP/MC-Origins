package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancements {
   private static final Logger field_192753_a = LogManager.getLogger();
   private static final Gson field_192754_b = new GsonBuilder()
      .registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer())
      .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
      .setPrettyPrinting()
      .create();
   private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> field_192755_c = new TypeToken<Map<ResourceLocation, AdvancementProgress>>() {
   };
   private final MinecraftServer field_192756_d;
   private final File field_192757_e;
   private final Map<Advancement, AdvancementProgress> field_192758_f = Maps.<Advancement, AdvancementProgress>newLinkedHashMap();
   private final Set<Advancement> field_192759_g = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> field_192760_h = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> field_192761_i = Sets.<Advancement>newLinkedHashSet();
   private EntityPlayerMP field_192762_j;
   @Nullable
   private Advancement field_194221_k;
   private boolean field_192763_k = true;

   public PlayerAdvancements(MinecraftServer var1, File var2, EntityPlayerMP var3) {
      this.field_192756_d = ☃;
      this.field_192757_e = ☃;
      this.field_192762_j = ☃;
      this.func_192740_f();
   }

   public void func_192739_a(EntityPlayerMP var1) {
      this.field_192762_j = ☃;
   }

   public void func_192745_a() {
      for(ICriterionTrigger<?> ☃ : CriteriaTriggers.func_192120_a()) {
         ☃.func_192167_a(this);
      }
   }

   public void func_193766_b() {
      this.func_192745_a();
      this.field_192758_f.clear();
      this.field_192759_g.clear();
      this.field_192760_h.clear();
      this.field_192761_i.clear();
      this.field_192763_k = true;
      this.field_194221_k = null;
      this.func_192740_f();
   }

   private void func_192751_c() {
      for(Advancement ☃ : this.field_192756_d.func_191949_aK().func_195438_b()) {
         this.func_193764_b(☃);
      }
   }

   private void func_192752_d() {
      List<Advancement> ☃ = Lists.<Advancement>newArrayList();

      for(Entry<Advancement, AdvancementProgress> ☃x : this.field_192758_f.entrySet()) {
         if (((AdvancementProgress)☃x.getValue()).func_192105_a()) {
            ☃.add(☃x.getKey());
            this.field_192761_i.add(☃x.getKey());
         }
      }

      for(Advancement ☃x : ☃) {
         this.func_192742_b(☃x);
      }
   }

   private void func_192748_e() {
      for(Advancement ☃ : this.field_192756_d.func_191949_aK().func_195438_b()) {
         if (☃.func_192073_f().isEmpty()) {
            this.func_192750_a(☃, "");
            ☃.func_192072_d().func_192113_a(this.field_192762_j);
         }
      }
   }

   private void func_192740_f() {
      if (this.field_192757_e.isFile()) {
         try {
            JsonReader ☃ = new JsonReader(new StringReader(Files.toString(this.field_192757_e, StandardCharsets.UTF_8)));
            Throwable var2 = null;

            try {
               ☃.setLenient(false);
               Dynamic<JsonElement> ☃x = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(☃));
               if (!☃x.get("DataVersion").flatMap(Dynamic::getNumberValue).isPresent()) {
                  ☃x = ☃x.set("DataVersion", ☃x.createInt(1343));
               }

               ☃x = this.field_192756_d.func_195563_aC().update(DataFixTypes.ADVANCEMENTS, ☃x, ☃x.getInt("DataVersion"), 1631);
               ☃x = ☃x.remove("DataVersion");
               Map<ResourceLocation, AdvancementProgress> ☃x = (Map)field_192754_b.getAdapter(field_192755_c).fromJsonTree(☃x.getValue());
               if (☃x == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<ResourceLocation, AdvancementProgress>> ☃x = ☃x.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for(Entry<ResourceLocation, AdvancementProgress> ☃xx : (List)☃x.collect(Collectors.toList())) {
                  Advancement ☃xxx = this.field_192756_d.func_191949_aK().func_192778_a((ResourceLocation)☃xx.getKey());
                  if (☃xxx == null) {
                     field_192753_a.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", ☃xx.getKey(), this.field_192757_e);
                  } else {
                     this.func_192743_a(☃xxx, (AdvancementProgress)☃xx.getValue());
                  }
               }
            } catch (Throwable var18) {
               var2 = var18;
               throw var18;
            } finally {
               if (☃ != null) {
                  if (var2 != null) {
                     try {
                        ☃.close();
                     } catch (Throwable var17) {
                        var2.addSuppressed(var17);
                     }
                  } else {
                     ☃.close();
                  }
               }
            }
         } catch (JsonParseException var20) {
            field_192753_a.error("Couldn't parse player advancements in {}", this.field_192757_e, var20);
         } catch (IOException var21) {
            field_192753_a.error("Couldn't access player advancements in {}", this.field_192757_e, var21);
         }
      }

      this.func_192748_e();
      this.func_192752_d();
      this.func_192751_c();
   }

   public void func_192749_b() {
      Map<ResourceLocation, AdvancementProgress> ☃ = Maps.<ResourceLocation, AdvancementProgress>newHashMap();

      for(Entry<Advancement, AdvancementProgress> ☃x : this.field_192758_f.entrySet()) {
         AdvancementProgress ☃xx = (AdvancementProgress)☃x.getValue();
         if (☃xx.func_192108_b()) {
            ☃.put(((Advancement)☃x.getKey()).func_192067_g(), ☃xx);
         }
      }

      if (this.field_192757_e.getParentFile() != null) {
         this.field_192757_e.getParentFile().mkdirs();
      }

      try {
         Files.write(field_192754_b.toJson(☃), this.field_192757_e, StandardCharsets.UTF_8);
      } catch (IOException var5) {
         field_192753_a.error("Couldn't save player advancements to {}", this.field_192757_e, var5);
      }
   }

   public boolean func_192750_a(Advancement var1, String var2) {
      boolean ☃ = false;
      AdvancementProgress ☃x = this.func_192747_a(☃);
      boolean ☃xx = ☃x.func_192105_a();
      if (☃x.func_192109_a(☃)) {
         this.func_193765_c(☃);
         this.field_192761_i.add(☃);
         ☃ = true;
         if (!☃xx && ☃x.func_192105_a()) {
            ☃.func_192072_d().func_192113_a(this.field_192762_j);
            if (☃.func_192068_c() != null
               && ☃.func_192068_c().func_193220_i()
               && this.field_192762_j.field_70170_p.func_82736_K().func_82766_b("announceAdvancements")) {
               this.field_192756_d
                  .func_184103_al()
                  .func_148539_a(
                     new TextComponentTranslation(
                        "chat.type.advancement." + ☃.func_192068_c().func_192291_d().func_192307_a(), this.field_192762_j.func_145748_c_(), ☃.func_193123_j()
                     )
                  );
            }
         }
      }

      if (☃x.func_192105_a()) {
         this.func_192742_b(☃);
      }

      return ☃;
   }

   public boolean func_192744_b(Advancement var1, String var2) {
      boolean ☃ = false;
      AdvancementProgress ☃x = this.func_192747_a(☃);
      if (☃x.func_192101_b(☃)) {
         this.func_193764_b(☃);
         this.field_192761_i.add(☃);
         ☃ = true;
      }

      if (!☃x.func_192108_b()) {
         this.func_192742_b(☃);
      }

      return ☃;
   }

   private void func_193764_b(Advancement var1) {
      AdvancementProgress ☃ = this.func_192747_a(☃);
      if (!☃.func_192105_a()) {
         for(Entry<String, Criterion> ☃x : ☃.func_192073_f().entrySet()) {
            CriterionProgress ☃xx = ☃.func_192106_c((String)☃x.getKey());
            if (☃xx != null && !☃xx.func_192151_a()) {
               ICriterionInstance ☃xxx = ((Criterion)☃x.getValue()).func_192143_a();
               if (☃xxx != null) {
                  ICriterionTrigger<ICriterionInstance> ☃xxxx = CriteriaTriggers.func_192119_a(☃xxx.func_192244_a());
                  if (☃xxxx != null) {
                     ☃xxxx.func_192165_a(this, new ICriterionTrigger.Listener<>(☃xxx, ☃, (String)☃x.getKey()));
                  }
               }
            }
         }
      }
   }

   private void func_193765_c(Advancement var1) {
      AdvancementProgress ☃ = this.func_192747_a(☃);

      for(Entry<String, Criterion> ☃x : ☃.func_192073_f().entrySet()) {
         CriterionProgress ☃xx = ☃.func_192106_c((String)☃x.getKey());
         if (☃xx != null && (☃xx.func_192151_a() || ☃.func_192105_a())) {
            ICriterionInstance ☃xxx = ((Criterion)☃x.getValue()).func_192143_a();
            if (☃xxx != null) {
               ICriterionTrigger<ICriterionInstance> ☃xxxx = CriteriaTriggers.func_192119_a(☃xxx.func_192244_a());
               if (☃xxxx != null) {
                  ☃xxxx.func_192164_b(this, new ICriterionTrigger.Listener<>(☃xxx, ☃, (String)☃x.getKey()));
               }
            }
         }
      }
   }

   public void func_192741_b(EntityPlayerMP var1) {
      if (this.field_192763_k || !this.field_192760_h.isEmpty() || !this.field_192761_i.isEmpty()) {
         Map<ResourceLocation, AdvancementProgress> ☃ = Maps.<ResourceLocation, AdvancementProgress>newHashMap();
         Set<Advancement> ☃x = Sets.<Advancement>newLinkedHashSet();
         Set<ResourceLocation> ☃xx = Sets.<ResourceLocation>newLinkedHashSet();

         for(Advancement ☃xxx : this.field_192761_i) {
            if (this.field_192759_g.contains(☃xxx)) {
               ☃.put(☃xxx.func_192067_g(), this.field_192758_f.get(☃xxx));
            }
         }

         for(Advancement ☃xxx : this.field_192760_h) {
            if (this.field_192759_g.contains(☃xxx)) {
               ☃x.add(☃xxx);
            } else {
               ☃xx.add(☃xxx.func_192067_g());
            }
         }

         if (this.field_192763_k || !☃.isEmpty() || !☃x.isEmpty() || !☃xx.isEmpty()) {
            ☃.field_71135_a.func_147359_a(new SPacketAdvancementInfo(this.field_192763_k, ☃x, ☃xx, ☃));
            this.field_192760_h.clear();
            this.field_192761_i.clear();
         }
      }

      this.field_192763_k = false;
   }

   public void func_194220_a(@Nullable Advancement var1) {
      Advancement ☃ = this.field_194221_k;
      if (☃ != null && ☃.func_192070_b() == null && ☃.func_192068_c() != null) {
         this.field_194221_k = ☃;
      } else {
         this.field_194221_k = null;
      }

      if (☃ != this.field_194221_k) {
         this.field_192762_j
            .field_71135_a
            .func_147359_a(new SPacketSelectAdvancementsTab(this.field_194221_k == null ? null : this.field_194221_k.func_192067_g()));
      }
   }

   public AdvancementProgress func_192747_a(Advancement var1) {
      AdvancementProgress ☃ = (AdvancementProgress)this.field_192758_f.get(☃);
      if (☃ == null) {
         ☃ = new AdvancementProgress();
         this.func_192743_a(☃, ☃);
      }

      return ☃;
   }

   private void func_192743_a(Advancement var1, AdvancementProgress var2) {
      ☃.func_192099_a(☃.func_192073_f(), ☃.func_192074_h());
      this.field_192758_f.put(☃, ☃);
   }

   private void func_192742_b(Advancement var1) {
      boolean ☃ = this.func_192738_c(☃);
      boolean ☃x = this.field_192759_g.contains(☃);
      if (☃ && !☃x) {
         this.field_192759_g.add(☃);
         this.field_192760_h.add(☃);
         if (this.field_192758_f.containsKey(☃)) {
            this.field_192761_i.add(☃);
         }
      } else if (!☃ && ☃x) {
         this.field_192759_g.remove(☃);
         this.field_192760_h.add(☃);
      }

      if (☃ != ☃x && ☃.func_192070_b() != null) {
         this.func_192742_b(☃.func_192070_b());
      }

      for(Advancement ☃ : ☃.func_192069_e()) {
         this.func_192742_b(☃);
      }
   }

   private boolean func_192738_c(Advancement var1) {
      for(int ☃ = 0; ☃ != null && ☃ <= 2; ++☃) {
         if (☃ == 0 && this.func_192746_d(☃)) {
            return true;
         }

         if (☃.func_192068_c() == null) {
            return false;
         }

         AdvancementProgress ☃x = this.func_192747_a(☃);
         if (☃x.func_192105_a()) {
            return true;
         }

         if (☃.func_192068_c().func_193224_j()) {
            return false;
         }

         ☃ = ☃.func_192070_b();
      }

      return false;
   }

   private boolean func_192746_d(Advancement var1) {
      AdvancementProgress ☃ = this.func_192747_a(☃);
      if (☃.func_192105_a()) {
         return true;
      } else {
         for(Advancement ☃ : ☃.func_192069_e()) {
            if (this.func_192746_d(☃)) {
               return true;
            }
         }

         return false;
      }
   }
}
