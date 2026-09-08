# Versioning and milestone model

`1.0a1`, `1.0a2`, … are discrete architecture milestones. `1.0b1`, `1.0b2`, … are frozen-scope bug-fix and polish milestones. `1.0` is the foundation release; `1.0.1`, `1.0.2`, … are maintenance releases. Repeat with `1.1a1` through `1.1` when deliberate continuation work begins.

A version is a tested checkpoint, not a session number or a claim about semantic compatibility. One milestone can take many sessions. Use numerical milestone ordering so a10 follows a9; do not sort version strings lexicographically. Display the exact owner-facing version. If a tool requires SemVer, document an adapter such as `1.0.0-alpha.1` without changing the public label.

## Working conventions

- Proposed release tags: `v1.0a1`, `v1.0b1`, `v1.0`, `v1.0.1`.
- Untagged builds: target version plus `dev` and a short revision in build metadata; never pretend to be the final milestone.
- Milestone states: Not started, In progress, Blocked, Ready for review, Complete.
- A `Complete` entry includes revision, date, evidence, known limitations and promotion decision.
- Save codec/schema version, protocol identity, and project version are distinct. Do not write the project version into Minecraft DataVersion.

## Promotion

Each milestone must satisfy its own exit checks and [common acceptance gates](ACCEPTANCE.md). Every completed version is buildable, launchable and testable on its declared matrix. Missing hardware checks remain NOT RUN; do not claim the broader platform matrix until qualified.

The proposed a1 bootstrap may use an explicitly recorded legacy game runtime until a2. If that transition cannot leave a1 playable, move the necessary runtime/platform minimum into a1 and revise dependencies rather than declaring a broken alpha complete.

Alphas may be split or extended while preserving dependencies. Keep historical milestone IDs stable once released; add new IDs and update the roadmap. Beta starts only once all 1.0 foundation features are complete. All betas fix defects, improve compatibility/performance or documentation; none introduce continuation content. Add b4 and beyond if the release gates are unmet. Do not force a release to fit the count in this kit.

Patch releases preserve 1.0 scope and published save contracts. A schema change needs an explicit migration and rollback plan, even in an alpha. Releasing or tagging follows the repository's actual release process; a session's completion alone does not publish anything.
