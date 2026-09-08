package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.EffectProgram;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EffectInstance implements Effect, AutoCloseable {
   private static final String EFFECT_SHADER_PATH = "shaders/program/";
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AbstractUniform DUMMY_UNIFORM = new AbstractUniform();
   private static final boolean ALWAYS_REAPPLY = true;
   private static EffectInstance lastAppliedEffect;
   private static int lastProgramId = -1;
   private final Map<String, IntSupplier> samplerMap = Maps.newHashMap();
   private final List<String> samplerNames = Lists.newArrayList();
   private final List<Integer> samplerLocations = Lists.newArrayList();
   private final List<Uniform> uniforms = Lists.<Uniform>newArrayList();
   private final List<Integer> uniformLocations = Lists.newArrayList();
   private final Map<String, Uniform> uniformMap = Maps.newHashMap();
   private final int programId;
   private final String name;
   private boolean dirty;
   private final BlendMode blend;
   private final List<Integer> attributes;
   private final List<String> attributeNames;
   private final EffectProgram vertexProgram;
   private final EffectProgram fragmentProgram;

   public EffectInstance(ResourceManager var1, String var2) throws IOException {
      ResourceLocation â˜ƒ = new ResourceLocation("shaders/program/" + â˜ƒ + ".json");
      this.name = â˜ƒ;
      Resource â˜ƒx = null;

      try {
         â˜ƒx = â˜ƒ.getResource(â˜ƒ);
         JsonObject â˜ƒxx = GsonHelper.parse(new InputStreamReader(â˜ƒx.getInputStream(), StandardCharsets.UTF_8));
         String â˜ƒxxx = GsonHelper.getAsString(â˜ƒxx, "vertex");
         String â˜ƒxxxx = GsonHelper.getAsString(â˜ƒxx, "fragment");
         JsonArray â˜ƒxxxxx = GsonHelper.getAsJsonArray(â˜ƒxx, "samplers", null);
         if (â˜ƒxxxxx != null) {
            int â˜ƒxxxxxx = 0;

            for(JsonElement â˜ƒxxxxxxx : â˜ƒxxxxx) {
               try {
                  this.parseSamplerNode(â˜ƒxxxxxxx);
               } catch (Exception var24) {
                  ChainedJsonException â˜ƒxxxxxxxx = ChainedJsonException.forException(var24);
                  â˜ƒxxxxxxxx.prependJsonKey("samplers[" + â˜ƒxxxxxx + "]");
                  throw â˜ƒxxxxxxxx;
               }

               ++â˜ƒxxxxxx;
            }
         }

         JsonArray â˜ƒxx = GsonHelper.getAsJsonArray(â˜ƒxx, "attributes", null);
         if (â˜ƒxx != null) {
            int â˜ƒxxx = 0;
            this.attributes = Lists.newArrayListWithCapacity(â˜ƒxx.size());
            this.attributeNames = Lists.newArrayListWithCapacity(â˜ƒxx.size());

            for(JsonElement â˜ƒxxxx : â˜ƒxx) {
               try {
                  this.attributeNames.add(GsonHelper.convertToString(â˜ƒxxxx, "attribute"));
               } catch (Exception var23) {
                  ChainedJsonException â˜ƒxxxxx = ChainedJsonException.forException(var23);
                  â˜ƒxxxxx.prependJsonKey("attributes[" + â˜ƒxxx + "]");
                  throw â˜ƒxxxxx;
               }

               ++â˜ƒxxx;
            }
         } else {
            this.attributes = null;
            this.attributeNames = null;
         }

         JsonArray â˜ƒxx = GsonHelper.getAsJsonArray(â˜ƒxx, "uniforms", null);
         if (â˜ƒxx != null) {
            int â˜ƒxxx = 0;

            for(JsonElement â˜ƒxxxx : â˜ƒxx) {
               try {
                  this.parseUniformNode(â˜ƒxxxx);
               } catch (Exception var22) {
                  ChainedJsonException â˜ƒxxxxx = ChainedJsonException.forException(var22);
                  â˜ƒxxxxx.prependJsonKey("uniforms[" + â˜ƒxxx + "]");
                  throw â˜ƒxxxxx;
               }

               ++â˜ƒxxx;
            }
         }

         this.blend = parseBlendNode(GsonHelper.getAsJsonObject(â˜ƒxx, "blend", null));
         this.vertexProgram = getOrCreate(â˜ƒ, Program.Type.VERTEX, â˜ƒxxx);
         this.fragmentProgram = getOrCreate(â˜ƒ, Program.Type.FRAGMENT, â˜ƒxxxx);
         this.programId = ProgramManager.createProgram();
         ProgramManager.linkShader(this);
         this.updateLocations();
         if (this.attributeNames != null) {
            for(String â˜ƒxx : this.attributeNames) {
               int â˜ƒxxx = Uniform.glGetAttribLocation(this.programId, â˜ƒxx);
               this.attributes.add(â˜ƒxxx);
            }
         }
      } catch (Exception var25) {
         String â˜ƒxx;
         if (â˜ƒx != null) {
            â˜ƒxx = " (" + â˜ƒx.getSourceName() + ")";
         } else {
            â˜ƒxx = "";
         }

         ChainedJsonException â˜ƒxx = ChainedJsonException.forException(var25);
         â˜ƒxx.setFilenameAndFlush(â˜ƒ.getPath() + â˜ƒxx);
         throw â˜ƒxx;
      } finally {
         IOUtils.closeQuietly(â˜ƒx);
      }

      this.markDirty();
   }

   public static EffectProgram getOrCreate(ResourceManager var0, Program.Type var1, String var2) throws IOException {
      Program â˜ƒ = (Program)â˜ƒ.getPrograms().get(â˜ƒ);
      if (â˜ƒ != null && !(â˜ƒ instanceof EffectProgram)) {
         throw new InvalidClassException("Program is not of type EffectProgram");
      } else {
         EffectProgram â˜ƒ;
         if (â˜ƒ == null) {
            ResourceLocation â˜ƒx = new ResourceLocation("shaders/program/" + â˜ƒ + â˜ƒ.getExtension());
            Resource â˜ƒxx = â˜ƒ.getResource(â˜ƒx);

            try {
               â˜ƒ = EffectProgram.compileShader(â˜ƒ, â˜ƒ, â˜ƒxx.getInputStream(), â˜ƒxx.getSourceName());
            } finally {
               IOUtils.closeQuietly(â˜ƒxx);
            }
         } else {
            â˜ƒ = (EffectProgram)â˜ƒ;
         }

         return â˜ƒ;
      }
   }

   public static BlendMode parseBlendNode(JsonObject var0) {
      if (â˜ƒ == null) {
         return new BlendMode();
      } else {
         int â˜ƒ = 32774;
         int â˜ƒx = 1;
         int â˜ƒxx = 0;
         int â˜ƒxxx = 1;
         int â˜ƒxxxx = 0;
         boolean â˜ƒxxxxx = true;
         boolean â˜ƒxxxxxx = false;
         if (GsonHelper.isStringValue(â˜ƒ, "func")) {
            â˜ƒ = BlendMode.stringToBlendFunc(â˜ƒ.get("func").getAsString());
            if (â˜ƒ != 32774) {
               â˜ƒxxxxx = false;
            }
         }

         if (GsonHelper.isStringValue(â˜ƒ, "srcrgb")) {
            â˜ƒx = BlendMode.stringToBlendFactor(â˜ƒ.get("srcrgb").getAsString());
            if (â˜ƒx != 1) {
               â˜ƒxxxxx = false;
            }
         }

         if (GsonHelper.isStringValue(â˜ƒ, "dstrgb")) {
            â˜ƒxx = BlendMode.stringToBlendFactor(â˜ƒ.get("dstrgb").getAsString());
            if (â˜ƒxx != 0) {
               â˜ƒxxxxx = false;
            }
         }

         if (GsonHelper.isStringValue(â˜ƒ, "srcalpha")) {
            â˜ƒxxx = BlendMode.stringToBlendFactor(â˜ƒ.get("srcalpha").getAsString());
            if (â˜ƒxxx != 1) {
               â˜ƒxxxxx = false;
            }

            â˜ƒxxxxxx = true;
         }

         if (GsonHelper.isStringValue(â˜ƒ, "dstalpha")) {
            â˜ƒxxxx = BlendMode.stringToBlendFactor(â˜ƒ.get("dstalpha").getAsString());
            if (â˜ƒxxxx != 0) {
               â˜ƒxxxxx = false;
            }

            â˜ƒxxxxxx = true;
         }

         if (â˜ƒxxxxx) {
            return new BlendMode();
         } else {
            return â˜ƒxxxxxx ? new BlendMode(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ) : new BlendMode(â˜ƒx, â˜ƒxx, â˜ƒ);
         }
      }
   }

   public void close() {
      for(Uniform â˜ƒ : this.uniforms) {
         â˜ƒ.close();
      }

      ProgramManager.releaseProgram(this);
   }

   public void clear() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ProgramManager.glUseProgram(0);
      lastProgramId = -1;
      lastAppliedEffect = null;

      for(int â˜ƒ = 0; â˜ƒ < this.samplerLocations.size(); ++â˜ƒ) {
         if (this.samplerMap.get(this.samplerNames.get(â˜ƒ)) != null) {
            GlStateManager._activeTexture(33984 + â˜ƒ);
            GlStateManager._disableTexture();
            GlStateManager._bindTexture(0);
         }
      }
   }

   public void apply() {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      this.dirty = false;
      lastAppliedEffect = this;
      this.blend.apply();
      if (this.programId != lastProgramId) {
         ProgramManager.glUseProgram(this.programId);
         lastProgramId = this.programId;
      }

      for(int â˜ƒ = 0; â˜ƒ < this.samplerLocations.size(); ++â˜ƒ) {
         String â˜ƒx = (String)this.samplerNames.get(â˜ƒ);
         IntSupplier â˜ƒxx = (IntSupplier)this.samplerMap.get(â˜ƒx);
         if (â˜ƒxx != null) {
            RenderSystem.activeTexture(33984 + â˜ƒ);
            RenderSystem.enableTexture();
            int â˜ƒxxx = â˜ƒxx.getAsInt();
            if (â˜ƒxxx != -1) {
               RenderSystem.bindTexture(â˜ƒxxx);
               Uniform.uploadInteger(this.samplerLocations.get(â˜ƒ), â˜ƒ);
            }
         }
      }

      for(Uniform â˜ƒ : this.uniforms) {
         â˜ƒ.upload();
      }
   }

   @Override
   public void markDirty() {
      this.dirty = true;
   }

   @Nullable
   public Uniform getUniform(String var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return (Uniform)this.uniformMap.get(â˜ƒ);
   }

   public AbstractUniform safeGetUniform(String var1) {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      Uniform â˜ƒ = this.getUniform(â˜ƒ);
      return (AbstractUniform)(â˜ƒ == null ? DUMMY_UNIFORM : â˜ƒ);
   }

   private void updateLocations() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      IntList â˜ƒ = new IntArrayList();

      for(int â˜ƒx = 0; â˜ƒx < this.samplerNames.size(); ++â˜ƒx) {
         String â˜ƒxx = (String)this.samplerNames.get(â˜ƒx);
         int â˜ƒxxx = Uniform.glGetUniformLocation(this.programId, â˜ƒxx);
         if (â˜ƒxxx == -1) {
            LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, â˜ƒxx);
            this.samplerMap.remove(â˜ƒxx);
            â˜ƒ.add(â˜ƒx);
         } else {
            this.samplerLocations.add(â˜ƒxxx);
         }
      }

      for(int â˜ƒx = â˜ƒ.size() - 1; â˜ƒx >= 0; --â˜ƒx) {
         this.samplerNames.remove(â˜ƒ.getInt(â˜ƒx));
      }

      for(Uniform â˜ƒx : this.uniforms) {
         String â˜ƒxx = â˜ƒx.getName();
         int â˜ƒxxx = Uniform.glGetUniformLocation(this.programId, â˜ƒxx);
         if (â˜ƒxxx == -1) {
            LOGGER.warn("Shader {} could not find uniform named {} in the specified shader program.", this.name, â˜ƒxx);
         } else {
            this.uniformLocations.add(â˜ƒxxx);
            â˜ƒx.setLocation(â˜ƒxxx);
            this.uniformMap.put(â˜ƒxx, â˜ƒx);
         }
      }
   }

   private void parseSamplerNode(JsonElement var1) {
      JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "sampler");
      String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "name");
      if (!GsonHelper.isStringValue(â˜ƒ, "file")) {
         this.samplerMap.put(â˜ƒx, null);
         this.samplerNames.add(â˜ƒx);
      } else {
         this.samplerNames.add(â˜ƒx);
      }
   }

   public void setSampler(String var1, IntSupplier var2) {
      if (this.samplerMap.containsKey(â˜ƒ)) {
         this.samplerMap.remove(â˜ƒ);
      }

      this.samplerMap.put(â˜ƒ, â˜ƒ);
      this.markDirty();
   }

   private void parseUniformNode(JsonElement var1) throws ChainedJsonException {
      JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "uniform");
      String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "name");
      int â˜ƒxx = Uniform.getTypeFromString(GsonHelper.getAsString(â˜ƒ, "type"));
      int â˜ƒxxx = GsonHelper.getAsInt(â˜ƒ, "count");
      float[] â˜ƒxxxx = new float[Math.max(â˜ƒxxx, 16)];
      JsonArray â˜ƒxxxxx = GsonHelper.getAsJsonArray(â˜ƒ, "values");
      if (â˜ƒxxxxx.size() != â˜ƒxxx && â˜ƒxxxxx.size() > 1) {
         throw new ChainedJsonException("Invalid amount of values specified (expected " + â˜ƒxxx + ", found " + â˜ƒxxxxx.size() + ")");
      } else {
         int â˜ƒ = 0;

         for(JsonElement â˜ƒx : â˜ƒxxxxx) {
            try {
               â˜ƒxxxx[â˜ƒ] = GsonHelper.convertToFloat(â˜ƒx, "value");
            } catch (Exception var13) {
               ChainedJsonException â˜ƒxx = ChainedJsonException.forException(var13);
               â˜ƒxx.prependJsonKey("values[" + â˜ƒ + "]");
               throw â˜ƒxx;
            }

            ++â˜ƒ;
         }

         if (â˜ƒxxx > 1 && â˜ƒxxxxx.size() == 1) {
            while(â˜ƒ < â˜ƒxxx) {
               â˜ƒxxxx[â˜ƒ] = â˜ƒxxxx[0];
               ++â˜ƒ;
            }
         }

         int â˜ƒx = â˜ƒxxx > 1 && â˜ƒxxx <= 4 && â˜ƒxx < 8 ? â˜ƒxxx - 1 : 0;
         Uniform â˜ƒxx = new Uniform(â˜ƒx, â˜ƒxx + â˜ƒx, â˜ƒxxx, this);
         if (â˜ƒxx <= 3) {
            â˜ƒxx.setSafe((int)â˜ƒxxxx[0], (int)â˜ƒxxxx[1], (int)â˜ƒxxxx[2], (int)â˜ƒxxxx[3]);
         } else if (â˜ƒxx <= 7) {
            â˜ƒxx.setSafe(â˜ƒxxxx[0], â˜ƒxxxx[1], â˜ƒxxxx[2], â˜ƒxxxx[3]);
         } else {
            â˜ƒxx.set(â˜ƒxxxx);
         }

         this.uniforms.add(â˜ƒxx);
      }
   }

   @Override
   public Program getVertexProgram() {
      return this.vertexProgram;
   }

   @Override
   public Program getFragmentProgram() {
      return this.fragmentProgram;
   }

   @Override
   public void attachToProgram() {
      this.fragmentProgram.attachToEffect(this);
      this.vertexProgram.attachToEffect(this);
   }

   public String getName() {
      return this.name;
   }

   @Override
   public int getId() {
      return this.programId;
   }
}
