package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;

public class PostChain implements AutoCloseable {
   private static final String MAIN_RENDER_TARGET = "minecraft:main";
   private final RenderTarget screenTarget;
   private final ResourceManager resourceManager;
   private final String name;
   private final List<PostPass> passes = Lists.<PostPass>newArrayList();
   private final Map<String, RenderTarget> customRenderTargets = Maps.newHashMap();
   private final List<RenderTarget> fullSizedTargets = Lists.<RenderTarget>newArrayList();
   private Matrix4f shaderOrthoMatrix;
   private int screenWidth;
   private int screenHeight;
   private float time;
   private float lastStamp;

   public PostChain(TextureManager var1, ResourceManager var2, RenderTarget var3, ResourceLocation var4) throws IOException, JsonSyntaxException {
      this.resourceManager = â˜ƒ;
      this.screenTarget = â˜ƒ;
      this.time = 0.0F;
      this.lastStamp = 0.0F;
      this.screenWidth = â˜ƒ.viewWidth;
      this.screenHeight = â˜ƒ.viewHeight;
      this.name = â˜ƒ.toString();
      this.updateOrthoMatrix();
      this.load(â˜ƒ, â˜ƒ);
   }

   private void load(TextureManager var1, ResourceLocation var2) throws IOException, JsonSyntaxException {
      Resource â˜ƒ = null;

      try {
         â˜ƒ = this.resourceManager.getResource(â˜ƒ);
         JsonObject â˜ƒx = GsonHelper.parse(new InputStreamReader(â˜ƒ.getInputStream(), StandardCharsets.UTF_8));
         if (GsonHelper.isArrayNode(â˜ƒx, "targets")) {
            JsonArray â˜ƒxx = â˜ƒx.getAsJsonArray("targets");
            int â˜ƒxxx = 0;

            for(JsonElement â˜ƒxxxx : â˜ƒxx) {
               try {
                  this.parseTargetNode(â˜ƒxxxx);
               } catch (Exception var17) {
                  ChainedJsonException â˜ƒxxxxx = ChainedJsonException.forException(var17);
                  â˜ƒxxxxx.prependJsonKey("targets[" + â˜ƒxxx + "]");
                  throw â˜ƒxxxxx;
               }

               ++â˜ƒxxx;
            }
         }

         if (GsonHelper.isArrayNode(â˜ƒx, "passes")) {
            JsonArray â˜ƒx = â˜ƒx.getAsJsonArray("passes");
            int â˜ƒxx = 0;

            for(JsonElement â˜ƒxxx : â˜ƒx) {
               try {
                  this.parsePassNode(â˜ƒ, â˜ƒxxx);
               } catch (Exception var16) {
                  ChainedJsonException â˜ƒxxxx = ChainedJsonException.forException(var16);
                  â˜ƒxxxx.prependJsonKey("passes[" + â˜ƒxx + "]");
                  throw â˜ƒxxxx;
               }

               ++â˜ƒxx;
            }
         }
      } catch (Exception var18) {
         String â˜ƒx;
         if (â˜ƒ != null) {
            â˜ƒx = " (" + â˜ƒ.getSourceName() + ")";
         } else {
            â˜ƒx = "";
         }

         ChainedJsonException â˜ƒx = ChainedJsonException.forException(var18);
         â˜ƒx.setFilenameAndFlush(â˜ƒ.getPath() + â˜ƒx);
         throw â˜ƒx;
      } finally {
         IOUtils.closeQuietly(â˜ƒ);
      }
   }

   private void parseTargetNode(JsonElement var1) throws ChainedJsonException {
      if (GsonHelper.isStringValue(â˜ƒ)) {
         this.addTempTarget(â˜ƒ.getAsString(), this.screenWidth, this.screenHeight);
      } else {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "target");
         String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "name");
         int â˜ƒxx = GsonHelper.getAsInt(â˜ƒ, "width", this.screenWidth);
         int â˜ƒxxx = GsonHelper.getAsInt(â˜ƒ, "height", this.screenHeight);
         if (this.customRenderTargets.containsKey(â˜ƒx)) {
            throw new ChainedJsonException(â˜ƒx + " is already defined");
         }

         this.addTempTarget(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   private void parsePassNode(TextureManager var1, JsonElement var2) throws IOException {
      JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "pass");
      String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "name");
      String â˜ƒxx = GsonHelper.getAsString(â˜ƒ, "intarget");
      String â˜ƒxxx = GsonHelper.getAsString(â˜ƒ, "outtarget");
      RenderTarget â˜ƒxxxx = this.getRenderTarget(â˜ƒxx);
      RenderTarget â˜ƒxxxxx = this.getRenderTarget(â˜ƒxxx);
      if (â˜ƒxxxx == null) {
         throw new ChainedJsonException("Input target '" + â˜ƒxx + "' does not exist");
      } else if (â˜ƒxxxxx == null) {
         throw new ChainedJsonException("Output target '" + â˜ƒxxx + "' does not exist");
      } else {
         PostPass â˜ƒ = this.addPass(â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx);
         JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "auxtargets", null);
         if (â˜ƒx != null) {
            int â˜ƒxx = 0;

            for(JsonElement â˜ƒxxx : â˜ƒx) {
               try {
                  JsonObject â˜ƒxxxxxx = GsonHelper.convertToJsonObject(â˜ƒxxx, "auxtarget");
                  String â˜ƒxxxxxxx = GsonHelper.getAsString(â˜ƒxxxxxx, "name");
                  String â˜ƒxxxxxxxx = GsonHelper.getAsString(â˜ƒxxxxxx, "id");
                  boolean â˜ƒxxxx;
                  String â˜ƒxxxxx;
                  if (â˜ƒxxxxxxxx.endsWith(":depth")) {
                     â˜ƒxxxx = true;
                     â˜ƒxxxxx = â˜ƒxxxxxxxx.substring(0, â˜ƒxxxxxxxx.lastIndexOf(58));
                  } else {
                     â˜ƒxxxx = false;
                     â˜ƒxxxxx = â˜ƒxxxxxxxx;
                  }

                  RenderTarget â˜ƒxxxx = this.getRenderTarget(â˜ƒxxxxx);
                  if (â˜ƒxxxx == null) {
                     if (â˜ƒxxxx) {
                        throw new ChainedJsonException("Render target '" + â˜ƒxxxxx + "' can't be used as depth buffer");
                     }

                     ResourceLocation â˜ƒxxxxx = new ResourceLocation("textures/effect/" + â˜ƒxxxxx + ".png");
                     Resource â˜ƒxxxxxx = null;

                     try {
                        â˜ƒxxxxxx = this.resourceManager.getResource(â˜ƒxxxxx);
                     } catch (FileNotFoundException var31) {
                        throw new ChainedJsonException("Render target or texture '" + â˜ƒxxxxx + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly(â˜ƒxxxxxx);
                     }

                     RenderSystem.setShaderTexture(0, â˜ƒxxxxx);
                     â˜ƒ.bindForSetup(â˜ƒxxxxx);
                     AbstractTexture var22 = â˜ƒ.getTexture(â˜ƒxxxxx);
                     int var23 = GsonHelper.getAsInt(â˜ƒxxxxxx, "width");
                     int var24 = GsonHelper.getAsInt(â˜ƒxxxxxx, "height");
                     boolean â˜ƒxxxxxxx = GsonHelper.getAsBoolean(â˜ƒxxxxxx, "bilinear");
                     if (â˜ƒxxxxxxx) {
                        RenderSystem.texParameter(3553, 10241, 9729);
                        RenderSystem.texParameter(3553, 10240, 9729);
                     } else {
                        RenderSystem.texParameter(3553, 10241, 9728);
                        RenderSystem.texParameter(3553, 10240, 9728);
                     }

                     â˜ƒ.addAuxAsset(â˜ƒxxxxxxx, var22::getId, var23, var24);
                  } else if (â˜ƒxxxx) {
                     â˜ƒ.addAuxAsset(â˜ƒxxxxxxx, â˜ƒxxxx::getDepthTextureId, â˜ƒxxxx.width, â˜ƒxxxx.height);
                  } else {
                     â˜ƒ.addAuxAsset(â˜ƒxxxxxxx, â˜ƒxxxx::getColorTextureId, â˜ƒxxxx.width, â˜ƒxxxx.height);
                  }
               } catch (Exception var33) {
                  ChainedJsonException â˜ƒxxxx = ChainedJsonException.forException(var33);
                  â˜ƒxxxx.prependJsonKey("auxtargets[" + â˜ƒxx + "]");
                  throw â˜ƒxxxx;
               }

               ++â˜ƒxx;
            }
         }

         JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "uniforms", null);
         if (â˜ƒ != null) {
            int â˜ƒx = 0;

            for(JsonElement â˜ƒxx : â˜ƒ) {
               try {
                  this.parseUniformNode(â˜ƒxx);
               } catch (Exception var30) {
                  ChainedJsonException â˜ƒxxx = ChainedJsonException.forException(var30);
                  â˜ƒxxx.prependJsonKey("uniforms[" + â˜ƒx + "]");
                  throw â˜ƒxxx;
               }

               ++â˜ƒx;
            }
         }
      }
   }

   private void parseUniformNode(JsonElement var1) throws ChainedJsonException {
      JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "uniform");
      String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "name");
      Uniform â˜ƒxx = ((PostPass)this.passes.get(this.passes.size() - 1)).getEffect().getUniform(â˜ƒx);
      if (â˜ƒxx == null) {
         throw new ChainedJsonException("Uniform '" + â˜ƒx + "' does not exist");
      } else {
         float[] â˜ƒ = new float[4];
         int â˜ƒx = 0;

         for(JsonElement â˜ƒxx : GsonHelper.getAsJsonArray(â˜ƒ, "values")) {
            try {
               â˜ƒ[â˜ƒx] = GsonHelper.convertToFloat(â˜ƒxx, "value");
            } catch (Exception var12) {
               ChainedJsonException â˜ƒxxx = ChainedJsonException.forException(var12);
               â˜ƒxxx.prependJsonKey("values[" + â˜ƒx + "]");
               throw â˜ƒxxx;
            }

            ++â˜ƒx;
         }

         switch(â˜ƒx) {
            case 0:
            default:
               break;
            case 1:
               â˜ƒxx.set(â˜ƒ[0]);
               break;
            case 2:
               â˜ƒxx.set(â˜ƒ[0], â˜ƒ[1]);
               break;
            case 3:
               â˜ƒxx.set(â˜ƒ[0], â˜ƒ[1], â˜ƒ[2]);
               break;
            case 4:
               â˜ƒxx.set(â˜ƒ[0], â˜ƒ[1], â˜ƒ[2], â˜ƒ[3]);
         }
      }
   }

   public RenderTarget getTempTarget(String var1) {
      return (RenderTarget)this.customRenderTargets.get(â˜ƒ);
   }

   public void addTempTarget(String var1, int var2, int var3) {
      RenderTarget â˜ƒ = new TextureTarget(â˜ƒ, â˜ƒ, true, Minecraft.ON_OSX);
      â˜ƒ.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.customRenderTargets.put(â˜ƒ, â˜ƒ);
      if (â˜ƒ == this.screenWidth && â˜ƒ == this.screenHeight) {
         this.fullSizedTargets.add(â˜ƒ);
      }
   }

   public void close() {
      for(RenderTarget â˜ƒ : this.customRenderTargets.values()) {
         â˜ƒ.destroyBuffers();
      }

      for(PostPass â˜ƒ : this.passes) {
         â˜ƒ.close();
      }

      this.passes.clear();
   }

   public PostPass addPass(String var1, RenderTarget var2, RenderTarget var3) throws IOException {
      PostPass â˜ƒ = new PostPass(this.resourceManager, â˜ƒ, â˜ƒ, â˜ƒ);
      this.passes.add(this.passes.size(), â˜ƒ);
      return â˜ƒ;
   }

   private void updateOrthoMatrix() {
      this.shaderOrthoMatrix = Matrix4f.orthographic(0.0F, (float)this.screenTarget.width, (float)this.screenTarget.height, 0.0F, 0.1F, 1000.0F);
   }

   public void resize(int var1, int var2) {
      this.screenWidth = this.screenTarget.width;
      this.screenHeight = this.screenTarget.height;
      this.updateOrthoMatrix();

      for(PostPass â˜ƒ : this.passes) {
         â˜ƒ.setOrthoMatrix(this.shaderOrthoMatrix);
      }

      for(RenderTarget â˜ƒ : this.fullSizedTargets) {
         â˜ƒ.resize(â˜ƒ, â˜ƒ, Minecraft.ON_OSX);
      }
   }

   public void process(float var1) {
      if (â˜ƒ < this.lastStamp) {
         this.time += 1.0F - this.lastStamp;
         this.time += â˜ƒ;
      } else {
         this.time += â˜ƒ - this.lastStamp;
      }

      this.lastStamp = â˜ƒ;

      while(this.time > 20.0F) {
         this.time -= 20.0F;
      }

      for(PostPass â˜ƒ : this.passes) {
         â˜ƒ.process(this.time / 20.0F);
      }
   }

   public final String getName() {
      return this.name;
   }

   private RenderTarget getRenderTarget(String var1) {
      if (â˜ƒ == null) {
         return null;
      } else {
         return â˜ƒ.equals("minecraft:main") ? this.screenTarget : (RenderTarget)this.customRenderTargets.get(â˜ƒ);
      }
   }
}
