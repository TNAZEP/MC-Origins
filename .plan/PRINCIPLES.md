# Guiding principles

1. **Beta is the spec.** Preserve observable movement, combat, redstone, mobs, updates, RNG, recipes, inventory behavior, UI, sound, dimensions, rendering and networking semantics. Historical quirks are not automatically bugs to fix.
2. **Modernization is internal.** Java 25, LWJGL 3, shaders, states and persistence may change implementation; they do not authorize balance changes or modern gameplay rules.
3. **One canonical codebase early.** Share genuinely common logic and protocol definitions. Keep presentation and process lifecycle at the edges. Preserve known SP/MP differences through explicit policies, not accidental duplicated worlds.
4. **Singleplayer stays local in 1.0.** It does not become a local dedicated/integrated server. Keep lifecycle and transport boundaries suitable for a future conversion.
5. **Generation stays Beta.** Do not rescale terrain, move sea level, adjust noise/RNG/population order, add biomes, or populate the extra height. Storage capacity and gameplay bounds are separate concerns.
6. **Persist stable identities.** Namespaced identifiers and real state definitions replace foundational numeric IDs. Beta IDs remain explicit compatibility adapters. Modern state names never select modern behavior implicitly.
7. **Compatibility is demonstrated.** A `.mca` extension or DataVersion is not proof of vanilla playability. Preserve meaningful world data and compare against an actual vanilla upgrade control.
8. **Bounded, usable milestones.** Every completed alpha/beta builds, launches and passes its applicable regression gates. A large milestone may require many sessions.
9. **Evidence survives the session.** Persist current work, next steps, verification and unresolved problems every session. No invented pass results or hidden blockers.
10. **Small abstractions with clear ownership.** Avoid global client access in shared code, GL-shaped rendering wrappers, sweeping style rewrites, speculative feature frameworks, and timing-sensitive concurrency changes.

An unavoidable behavioral difference requires a minimized reproducer, explanation of alternatives, impact, regression coverage and a decision in [DEVIATIONS](status/DEVIATIONS.md). Architectural convenience alone is not justification. An owner decision is needed to relax a locked product requirement; ordinary implementation choices do not need repeated confirmation.
