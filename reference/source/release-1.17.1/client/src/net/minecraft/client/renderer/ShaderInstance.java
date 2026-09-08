package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderInstance implements Shader, AutoCloseable {
   private static final String SHADER_PATH = "shaders/core/";
   private static final String SHADER_INCLUDE_PATH = "shaders/include/";
   static final Logger LOGGER = LogManager.getLogger();
   private static final AbstractUniform DUMMY_UNIFORM = new AbstractUniform();
   private static final boolean ALWAYS_REAPPLY = true;
   private static ShaderInstance lastAppliedShader;
   private static int lastProgramId = -1;
   private final Map<String, Object> samplerMap = Maps.newHashMap();
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
   private final Program vertexProgram;
   private final Program fragmentProgram;
   private final VertexFormat vertexFormat;
   @Nullable
   public final Uniform MODEL_VIEW_MATRIX;
   @Nullable
   public final Uniform PROJECTION_MATRIX;
   @Nullable
   public final Uniform TEXTURE_MATRIX;
   @Nullable
   public final Uniform SCREEN_SIZE;
   @Nullable
   public final Uniform COLOR_MODULATOR;
   @Nullable
   public final Uniform LIGHT0_DIRECTION;
   @Nullable
   public final Uniform LIGHT1_DIRECTION;
   @Nullable
   public final Uniform FOG_START;
   @Nullable
   public final Uniform FOG_END;
   @Nullable
   public final Uniform FOG_COLOR;
   @Nullable
   public final Uniform LINE_WIDTH;
   @Nullable
   public final Uniform GAME_TIME;
   @Nullable
   public final Uniform CHUNK_OFFSET;

   public ShaderInstance(ResourceProvider var1, String var2, VertexFormat var3) throws IOException {
      this.name = â˜ƒ;
      this.vertexFormat = â˜ƒ;
      ResourceLocation â˜ƒ = new ResourceLocation("shaders/core/" + â˜ƒ + ".json");
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
               } catch (Exception var25) {
                  ChainedJsonException â˜ƒxxxxxxxx = ChainedJsonException.forException(var25);
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
               } catch (Exception var24) {
                  ChainedJsonException â˜ƒxxxxx = ChainedJsonException.forException(var24);
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
               } catch (Exception var23) {
                  ChainedJsonException â˜ƒxxxxx = ChainedJsonException.forException(var23);
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
         if (this.attributeNames != null) {
            int â˜ƒxx = 0;

            for(String â˜ƒxxx : â˜ƒ.getElementAttributeNames()) {
               Uniform.glBindAttribLocation(this.programId, â˜ƒxx, â˜ƒxxx);
               this.attributes.add(â˜ƒxx);
               ++â˜ƒxx;
            }
         }

         ProgramManager.linkShader(this);
         this.updateLocations();
      } catch (Exception var26) {
         ChainedJsonException â˜ƒxx = ChainedJsonException.forException(var26);
         â˜ƒxx.setFilenameAndFlush(â˜ƒ.getPath());
         throw â˜ƒxx;
      } finally {
         IOUtils.closeQuietly(â˜ƒx);
      }

      this.markDirty();
      this.MODEL_VIEW_MATRIX = this.getUniform("ModelViewMat");
      this.PROJECTION_MATRIX = this.getUniform("ProjMat");
      this.TEXTURE_MATRIX = this.getUniform("TextureMat");
      this.SCREEN_SIZE = this.getUniform("ScreenSize");
      this.COLOR_MODULATOR = this.getUniform("ColorModulator");
      this.LIGHT0_DIRECTION = this.getUniform("Light0_Direction");
      this.LIGHT1_DIRECTION = this.getUniform("Light1_Direction");
      this.FOG_START = this.getUniform("FogStart");
      this.FOG_END = this.getUniform("FogEnd");
      this.FOG_COLOR = this.getUniform("FogColor");
      this.LINE_WIDTH = this.getUniform("LineWidth");
      this.GAME_TIME = this.getUniform("GameTime");
      this.CHUNK_OFFSET = this.getUniform("ChunkOffset");
   }

   private static Program getOrCreate(final ResourceProvider var0, Program.Type var1, String var2) throws IOException {
      Program â˜ƒx = (Program)â˜ƒ.getPrograms().get(â˜ƒ);
      Program â˜ƒ;
      if (â˜ƒx == null) {
         String â˜ƒxx = "shaders/core/" + â˜ƒ + â˜ƒ.getExtension();
         ResourceLocation â˜ƒxxx = new ResourceLocation(â˜ƒxx);
         Resource â˜ƒxxxx = â˜ƒ.getResource(â˜ƒxxx);
         final String â˜ƒxxxxx = FileUtil.getFullResourcePath(â˜ƒxx);

         try {
            â˜ƒ = Program.compileShader(â˜ƒ, â˜ƒ, â˜ƒxxxx.getInputStream(), â˜ƒxxxx.getSourceName(), new GlslPreprocessor() {
               private final Set<String> importedPaths = Sets.newHashSet();

               @Override
               public String applyImport(boolean var1, String var2) {
                  â˜ƒ = FileUtil.normalizeResourcePath((â˜ƒ ? â˜ƒ : "shaders/include/") + â˜ƒ);
                  if (!this.importedPaths.add(â˜ƒ)) {
                     return null;
                  } else {
                     ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ);

                     try {
                        Resource â˜ƒx = â˜ƒ.getResource(â˜ƒ);

                        String var5;
                        try {
                           var5 = IOUtils.toString(â˜ƒx.getInputStream(), StandardCharsets.UTF_8);
                        } catch (Throwable var8x) {
                           if (â˜ƒx != null) {
                              try {
                                 â˜ƒx.close();
                              } catch (Throwable var7) {
                                 var8x.addSuppressed(var7);
                              }
                           }

                           throw var8x;
                        }

                        if (â˜ƒx != null) {
                           â˜ƒx.close();
                        }

                        return var5;
                     } catch (IOException var9) {
                        ShaderInstance.LOGGER.error("Could not open GLSL import {}: {}", â˜ƒ, var9.getMessage());
                        return "#error " + var9.getMessage();
                     }
                  }
               }
            });
         } finally {
            IOUtils.closeQuietly(â˜ƒxxxx);
         }
      } else {
         â˜ƒ = â˜ƒx;
      }

      return â˜ƒ;
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
      lastAppliedShader = null;
      int â˜ƒ = GlStateManager._getActiveTexture();

      for(int â˜ƒx = 0; â˜ƒx < this.samplerLocations.size(); ++â˜ƒx) {
         if (this.samplerMap.get(this.samplerNames.get(â˜ƒx)) != null) {
            GlStateManager._activeTexture(33984 + â˜ƒx);
            GlStateManager._bindTexture(0);
         }
      }

      GlStateManager._activeTexture(â˜ƒ);
   }

   public void apply() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.dirty = false;
      lastAppliedShader = this;
      this.blend.apply();
      if (this.programId != lastProgramId) {
         ProgramManager.glUseProgram(this.programId);
         lastProgramId = this.programId;
      }

      int â˜ƒ = GlStateManager._getActiveTexture();

      for(int â˜ƒx = 0; â˜ƒx < this.samplerLocations.size(); ++â˜ƒx) {
         String â˜ƒxx = (String)this.samplerNames.get(â˜ƒx);
         if (this.samplerMap.get(â˜ƒxx) != null) {
            int â˜ƒxxx = Uniform.glGetUniformLocation(this.programId, â˜ƒxx);
            Uniform.uploadInteger(â˜ƒxxx, â˜ƒx);
            RenderSystem.activeTexture(33984 + â˜ƒx);
            RenderSystem.enableTexture();
            Object â˜ƒxxxx = this.samplerMap.get(â˜ƒxx);
            int â˜ƒxxxxx = -1;
            if (â˜ƒxxxx instanceof RenderTarget) {
               â˜ƒxxxxx = ((RenderTarget)â˜ƒxxxx).getColorTextureId();
            } else if (â˜ƒxxxx instanceof AbstractTexture) {
               â˜ƒxxxxx = ((AbstractTexture)â˜ƒxxxx).getId();
            } else if (â˜ƒxxxx instanceof Integer) {
               â˜ƒxxxxx = (Integer)â˜ƒxxxx;
            }

            if (â˜ƒxxxxx != -1) {
               RenderSystem.bindTexture(â˜ƒxxxxx);
            }
         }
      }

      GlStateManager._activeTexture(â˜ƒ);

      for(Uniform â˜ƒx : this.uniforms) {
         â˜ƒx.upload();
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
         int â˜ƒxx = â˜ƒ.getInt(â˜ƒx);
         this.samplerNames.remove(â˜ƒxx);
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

   public void setSampler(String var1, Object var2) {
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
      this.fragmentProgram.attachToShader(this);
      this.vertexProgram.attachToShader(this);
   }

   public VertexFormat getVertexFormat() {
      return this.vertexFormat;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public int getId() {
      return this.programId;
   }
}
