# Source provenance and verification boundary

Planning date: 2026-09-07. The owner's request and the supplied “Design document planning” conversation (conversation ID `6a9ead50-9efc-83ee-8130-2e5483d72743`) are the source of product intent. All nine visible user turns were inspected initially. The first retrieval ended before the final assistant reply; after the owner flagged the discrepancy, that reply was retrieved and its four-version reference selection and source/fixture layout were incorporated. The initial kit's different reference recommendation is corrected. The final no-worldgen-change decision supersedes earlier vertical-expansion discussion. This kit adds engineering defaults, explicitly labeled where they were not settled in that discussion.

## Primary documentation consulted

| Source | Used for |
|---|---|
| [OpenAI AGENTS.md guide](https://developers.openai.com/codex/guides/agents-md) | Root project instructions pointing to persistent planning docs |
| [Gradle compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html) | Java 25 toolchain/runtime support from Gradle 9.1.0 |
| [LWJGL guide](https://www.lwjgl.org/guide) | Primary starting point for the LWJGL 3 platform implementation |
| [GLFW window reference](https://www.glfw.org/docs/latest/group__window.html) | Platform context/profile constraints to verify during a2 |
| [RetroMCP-Java repository](https://github.com/MCPHackers/RetroMCP-Java) | Decompilation tooling and version-support investigation |
| [Minecraft Java Edition 1.17.1 release](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-17-1) | Exact vanilla consumer release |
| [Minecraft Java 1.17 release notes](https://feedback.minecraft.net/hc/en-us/articles/4402626897165-Minecraft-Caves-Cliffs-Part-1-1-17-Java) | Modern rendering reference context, not Beta behavior |
| [Minecraft Java 1.18 release notes](https://feedback.minecraft.net/hc/en-us/articles/4415128577293-Minecraft-Java-Edition-1-18) | Vanilla forward-upgrade/blending behavior to qualify |
| [Mojang DataFixerUpper](https://github.com/Mojang/DataFixerUpper) | Data transformation library context; not a complete ready-made Beta converter |

## Facts still requiring repository evidence

No Minecraft jar/source/fixture was supplied in this workspace. Therefore exact binary hashes, source symbol locations, all NBT field/type/packing details, DataVersion, mapping completeness, protocol pairing, patch reconstruction, platform support and vanilla upgrade results remain implementation-time verification tasks. Do not promote them from planned to proven because this document names the intended target.

The cited library and release documents do not establish that Origins works. The source guide's version selection and milestone ordering are engineering recommendations. The critical compatibility claim will be established by exact-version source inspection plus unmodified vanilla runtime tests, including a paired upgrade control.

Release notes alone cannot specify the entire chunk/level/player schema. Populate `reference/notes/schema-inventory.md` from actual 1.17.1 sources and generated saves before coding the writer. DataFixerUpper is a transformation framework; using its library does not automatically supply all historical Minecraft schemas/fixes or solve Beta-to-1.17.1 mapping.
