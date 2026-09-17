import com.sun.source.tree.*;
import com.sun.source.util.*;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/** Compiler-resolved source renames. TSV: declaring class, member name, replacement, optional method parameter count.
 * Usage: sourceRoot classpath mapping.tsv [--apply]. Does not change references or compile output.
 */
public final class RenameJavaSymbols {
    private static final class Edit {
        final int start, end;
        final String value;
        Edit(int start, int end, String value) { this.start = start; this.end = end; this.value = value; }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4 || (args.length == 4 && !args[3].equals("--apply")))
            throw new IllegalArgumentException("sourceRoot classpath mapping.tsv [--apply]");
        final Map<String, String> requested = new LinkedHashMap<>();
        for (String line : Files.readAllLines(Paths.get(args[2]), StandardCharsets.UTF_8)) {
            if (line.trim().isEmpty() || line.startsWith("#")) continue;
            String[] row = line.split("\t");
            if ((row.length != 3 && row.length != 4) || !row[2].matches("[A-Za-z_$][A-Za-z0-9_$]*"))
                throw new IllegalArgumentException("Invalid mapping: " + line);
            if (row.length == 4 && !row[3].matches("[0-9]+")) throw new IllegalArgumentException("Invalid arity: " + line);
            String prior = requested.put(row[0] + "." + row[1] + (row.length == 4 ? "#" + row[3] : ""), row[2]);
            if (prior != null && !prior.equals(row[2])) throw new IllegalArgumentException("Conflicting mapping: " + line);
        }
        List<File> sources;
        try (java.util.stream.Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
            sources = paths.filter(p -> p.toString().endsWith(".java")).sorted().map(Path::toFile).collect(Collectors.toList());
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("Run with a JDK including tools.jar");
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager files = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8)) {
            JavacTask task = (JavacTask)compiler.getTask(null, files, diagnostics,
                Arrays.asList("-proc:none", "-classpath", args[1]), null, files.getJavaFileObjectsFromFiles(sources));
            List<CompilationUnitTree> units = new ArrayList<>();
            for (CompilationUnitTree unit : task.parse()) units.add(unit);
            task.analyze();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) throw new IllegalStateException(diagnostic.toString());
            }
            final Trees trees = Trees.instance(task);
            final Elements elements = task.getElements();
            final Map<Element, String> names = new HashMap<>();
            final List<ExecutableElement> methods = new ArrayList<>();
            final Set<String> found = new HashSet<>();
            for (CompilationUnitTree unit : units) new TreePathScanner<Void, Void>() {
                private void declaration() {
                    Element element = trees.getElement(getCurrentPath());
                    if (element == null) return;
                    if (element.getKind() == ElementKind.METHOD) methods.add((ExecutableElement)element);
                    if (element.getEnclosingElement() instanceof TypeElement) {
                        String key = ((TypeElement)element.getEnclosingElement()).getQualifiedName() + "." + element.getSimpleName();
                        if (element instanceof ExecutableElement) {
                            String specific = key + "#" + ((ExecutableElement)element).getParameters().size();
                            if (requested.containsKey(specific)) key = specific;
                        }
                        if (requested.containsKey(key)) { names.put(element, requested.get(key)); found.add(key); }
                    }
                }
                public Void visitVariable(VariableTree tree, Void unused) { declaration(); return super.visitVariable(tree, unused); }
                public Void visitMethod(MethodTree tree, Void unused) { declaration(); return super.visitMethod(tree, unused); }
            }.scan(unit, null);
            if (!found.containsAll(requested.keySet())) {
                Set<String> missing = new TreeSet<>(requested.keySet()); missing.removeAll(found);
                throw new IllegalArgumentException("No source declaration for: " + missing);
            }
            Map<Element, String> explicit = new HashMap<>(names);
            for (ExecutableElement method : methods) for (Map.Entry<Element, String> rename : explicit.entrySet()) {
                if (rename.getKey().getKind() == ElementKind.METHOD
                    && elements.overrides(method, (ExecutableElement)rename.getKey(), (TypeElement)method.getEnclosingElement())) {
                    String old = names.put(method, rename.getValue());
                    if (old != null && !old.equals(rename.getValue())) throw new IllegalArgumentException("Conflicting override: " + method);
                }
            }
            // A derived-only rename would silently stop overriding its base method.
            for (Map.Entry<Element, String> rename : names.entrySet()) {
                if (rename.getKey().getKind() != ElementKind.METHOD) continue;
                ExecutableElement method = (ExecutableElement)rename.getKey();
                for (ExecutableElement base : methods) {
                    if (elements.overrides(method, base, (TypeElement)method.getEnclosingElement())
                        && !rename.getValue().equals(names.get(base)))
                        throw new IllegalArgumentException("Rename the base contract too: " + base.getEnclosingElement() + "." + base);
                }
            }
            final Map<Path, String> results = new LinkedHashMap<>();
            int changes = 0;
            for (final CompilationUnitTree unit : units) {
                final String source = unit.getSourceFile().getCharContent(true).toString();
                final TreeMap<Integer, Edit> edits = new TreeMap<>(Collections.reverseOrder());
                new TreePathScanner<Void, Void>() {
                    private String replacement() { return names.get(trees.getElement(getCurrentPath())); }
                    private int start(Tree tree) { return (int)trees.getSourcePositions().getStartPosition(unit, tree); }
                    private int end(Tree tree) { return (int)trees.getSourcePositions().getEndPosition(unit, tree); }
                    private void edit(int from, int to, String value) {
                        if (from < 0 || to < from || to > source.length()) throw new IllegalStateException("Invalid source position");
                        Edit prior = edits.put(from, new Edit(from, to, value));
                        if (prior != null && (prior.end != to || !prior.value.equals(value))) throw new IllegalStateException("Overlapping edit");
                    }
                    public Void visitIdentifier(IdentifierTree tree, Void unused) {
                        String value = replacement();
                        if (value != null) edit(start(tree), end(tree), value);
                        return super.visitIdentifier(tree, unused);
                    }
                    public Void visitMemberSelect(MemberSelectTree tree, Void unused) {
                        String value = replacement();
                        if (value != null) edit(end(tree) - tree.getIdentifier().length(), end(tree), value);
                        return super.visitMemberSelect(tree, unused);
                    }
                    public Void visitMethod(MethodTree tree, Void unused) {
                        String value = replacement();
                        if (value != null) {
                            int from = start(tree);
                            int to = tree.getBody() == null ? end(tree) : start(tree.getBody());
                            java.util.regex.Matcher match = java.util.regex.Pattern.compile("\\b" + tree.getName() + "\\s*\\(").matcher(source.substring(from, to));
                            if (!match.find()) throw new IllegalStateException("Method declaration not found: " + tree.getName());
                            edit(from + match.start(), from + match.start() + tree.getName().length(), value);
                        }
                        return super.visitMethod(tree, unused);
                    }
                    public Void visitVariable(VariableTree tree, Void unused) {
                        String value = replacement();
                        if (value != null) {
                            int from = start(tree), to = tree.getInitializer() == null ? end(tree) : start(tree.getInitializer());
                            java.util.regex.Matcher match = java.util.regex.Pattern.compile("\\b" + tree.getName() + "\\b").matcher(source.substring(from, to));
                            int position = -1;
                            while (match.find()) position = from + match.start();
                            if (position < 0) throw new IllegalStateException("Field declaration not found: " + tree.getName());
                            edit(position, position + tree.getName().length(), value);
                        }
                        return super.visitVariable(tree, unused);
                    }
                }.scan(unit, null);
                if (edits.isEmpty()) continue;
                StringBuilder updated = new StringBuilder(source);
                int previous = source.length();
                for (Edit edit : edits.values()) {
                    if (edit.end > previous) throw new IllegalStateException("Intersecting edits");
                    updated.replace(edit.start, edit.end, edit.value); previous = edit.start;
                }
                results.put(Paths.get(unit.getSourceFile().toUri()), updated.toString());
                changes += edits.size();
            }
            if (args.length == 4) for (Map.Entry<Path, String> result : results.entrySet())
                Files.write(result.getKey(), result.getValue().getBytes(StandardCharsets.UTF_8));
            System.out.println((args.length == 4 ? "Applied" : "Preview") + ": " + requested.size()
                + " scoped mappings; " + changes + " references/declarations in " + results.size() + " files");
        }
    }
}
