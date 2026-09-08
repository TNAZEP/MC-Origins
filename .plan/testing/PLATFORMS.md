# Platform qualification

The proposed final desktop matrix is Windows x86-64, Linux x86-64, macOS x86-64 and macOS arm64, with Java 25 and a suitable OpenGL core context. This is a test target, not a claim of current support. Record concrete OS releases, GPUs/drivers, JDK build and native classifiers in a2, and complete the claimed matrix in b2. Extra architectures need a separate qualification entry.

For each claimed client platform test clean launch; shader/context capability detection; local play; remote join; input/text/key repeat; pointer lock; high-DPI GUI targeting; fullscreen/windowed switch; resize; focus/pause; audio; save/quit; crash cleanup; and fresh Prism import. Validate platform-specific JVM flags and GLFW thread requirements against the actual pinned library/runtime.

For each claimed dedicated-server platform test launch without a display, client graphics/audio dependencies or native initialization; shutdown/save/restart; two clients; resource cleanup and error reporting. Do not turn an unavailable desktop into an automatic server-only release claim.

Keep historical reference games on a working, recorded runtime/platform appropriate to their binaries. Their inability to run on a modern host is an environment limitation to solve or document, not evidence that Origins matches them. Use reference recordings from a qualified environment if local baseline execution is unavailable.

## Performance and soak protocol

Establish scene/world/settings and budgets from actual baseline measurements. Measure warm and cold startup, chunk generation/load, median/p95/p99 frame and server tick time, heap/RSS trend, and save duration. Use repeated runs and retain raw samples. Proposed b2 minimum soak: two hours of repeated travel, unload/reload, inventory/entity interactions and save cycles, then a clean restart and state verification. Extend when leaks or instability emerge.

No supported platform may be marked PASS from a compile-only check. Missing hardware evidence is NOT RUN and remains visible in the release gate. Resolve support-matrix changes explicitly before advertising release support.
