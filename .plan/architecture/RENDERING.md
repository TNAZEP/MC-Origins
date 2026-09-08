# Platform and rendering design

## Platform layer

Introduce services for window/context lifecycle, framebuffer size, keyboard and text input, relative mouse/cursor, focus, clipboard, monotonic timing, and audio. GLFW belongs inside the platform implementation. Preserve Beta's event consumption order, repeat behavior, mouse sensitivity, GUI scaling and pause/focus semantics. Text input and physical key events must remain distinguishable.

Port sound/music lifecycle, listener/source positioning, resource loading and shutdown as part of LWJGL 3. Do not omit audio because the window renders. Validate high-DPI framebuffer versus window coordinates and platform-specific launch requirements. Native initialization errors should identify the missing capability without leaving locks or worlds dirty.

## Rendering API

Use explicit resources and descriptions: device capabilities, buffers, textures, samplers, meshes, pipelines/materials, render targets/passes, draw commands, camera matrices and uniforms. Define ownership, destruction, upload lifetimes and thread confinement. Commands must identify the resources they consume; implicit global GL state must not leak above the backend.

Vulkan-friendly means no exposed GL object IDs, bind-to-edit API, global matrix stack or synchronous readback requirement in the game-facing contract. It does not require implementing Vulkan, a general render graph, descriptor allocators, or multi-queue scheduling in 1.0. Review the abstraction with a written mapping of its operations to a plausible explicit backend.

**Working capability baseline:** OpenGL 3.3 Core feature set / suitable GLSL, subject to the a2 platform spike. Use a valid supported core context on each platform and query actual capabilities. Confirm macOS context/profile requirements from GLFW documentation; do not depend on a compatibility context for final support.

## Migration sequence

1. Isolate platform calls and rendering submissions, keeping the current output measurable.
2. Introduce a small backend boundary and a complete basic shader path sufficient for the a2 supported smoke scenarios.
3. Complete the Beta scene renderer in a3: terrain/chunk rebuilds, entities, block entities, held/dropped items, particles, GUI/fonts, sky/clouds, sun/moon/stars, water/lava, fog, weather, selection outlines, damage overlay and translucent passes.
4. Remove the legacy path after scene parity is established; no fixed-function fallback in the final artifacts.

A temporary legacy adapter is allowed only inside an unfinished transition or an explicitly recorded early-alpha platform limit. It must not make a completed milestone unlaunchable on that milestone's declared matrix. If LWJGL 3 and the usable shader bootstrap cannot be separated, expand a2 to the necessary minimum before promotion.

## Visual contract

Preserve Beta textures, geometry, face culling, light brightness, ambient-occlusion option, tinting, alpha cutouts, blending/sorting, fog curve, camera/FOV/bobbing, GUI scale and animation cadence. Replicate fixed-function effects in shaders rather than choosing aesthetically similar modern defaults. Define color-space assumptions explicitly; accidental sRGB changes alter the image.

Use fixed-camera captures with controlled time, weather, resources and settings. Exact comparison is appropriate on a stable deterministic reference path; cross-driver image tolerances must be calibrated from repeated baseline captures and documented before acceptance. A numeric image score alone cannot excuse incorrect transparency or missing geometry.
