#!/usr/bin/env python3
"""Run practical artifact checks in disposable worlds; graphical checks are opt-in."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tempfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', type=Path, required=True)
    parser.add_argument('--resources', type=Path, help='Explicit prepared Beta resources directory for the isolated graphical run')
    parser.add_argument('--graphical-only', action='store_true', help='Run only the graphical scenario after headless checks passed')
    parser.add_argument('--graphical', action='store_true', help='Open a real client window; needs prepared libraries/natives')
    args = parser.parse_args()
    root = Path(__file__).resolve().parents[1]
    os.chdir(root)
    output_root = root / 'run/smoke'
    output_root.mkdir(parents=True, exist_ok=True)
    output = Path(tempfile.mkdtemp(prefix='game-checks-', dir=output_root))
    classes = output / 'classes'
    classes.mkdir()
    java = str(args.java_home.resolve() / 'bin/java')
    javac = str(args.java_home.resolve() / 'bin/javac')
    client = root / 'build/libs/Minecraft.jar'
    server = root / 'build/libs/Minecraft-server.jar'
    report = {'result': 'FAIL', 'commands': [], 'artifacts': {
        str(p.relative_to(root)): hashlib.sha256(p.read_bytes()).hexdigest() for p in (client, server)}}
    print('Evidence:', output, flush=True)

    def run(name, command):
        with (output / (name + '.log')).open('w') as log:
            result = subprocess.run(command, stdout=log, stderr=subprocess.STDOUT, text=True, timeout=240)
        report['commands'].append({'name': name, 'argv': command, 'exit_code': result.returncode})
        print(name, 'PASS' if result.returncode == 0 else 'FAIL', flush=True)
        result.check_returncode()

    try:
        if not args.graphical_only:
            sources = ['PacketFlowSmokeTest', 'ItemStackSmokeTest', 'WorldSaveSmokeTest', 'LocalWorldSmokeTest', 'MultiplayerSmokeTest']
            run('compile', [javac, '-cp', str(server), '-d', str(classes)] + [f'tools/{s}.java' for s in sources])
            for artifact, label in [(client, 'client'), (server, 'server')]:
                run('packets-' + label, [java, '-cp', os.pathsep.join([str(classes), str(artifact)]), 'net.minecraft.src.PacketFlowSmokeTest'])
            cp = os.pathsep.join([str(classes), str(server)])
            for name in ['ItemStackSmokeTest', 'WorldSaveSmokeTest', 'LocalWorldSmokeTest']:
                run(name, [java, '-Xmx512m', '-cp', cp, ('' if name == 'WorldSaveSmokeTest' else 'net.minecraft.src.') + name])
            run('multiplayer', [java, '-Xmx512m', '-cp', cp, 'net.minecraft.src.MultiplayerSmokeTest', java, str(server)])
        if args.graphical or args.graphical_only:
            libraries = str(root / 'build/client-libraries/*')
            cp = os.pathsep.join([str(classes), str(client), libraries])
            run('compile-graphical', [javac, '-cp', cp, '-d', str(classes), 'tools/ClientPlaySmokeTest.java'])
            native = str(root / 'build/natives/linux')
            resource_options = ['-Dorigins.smokeResources=' + str(args.resources.resolve())] if args.resources else []
            run('graphical', [java, '-Xmx768m'] + resource_options + [ '-Djava.library.path=' + native, '-Dorg.lwjgl.librarypath=' + native,
                              '-cp', cp, 'net.minecraft.src.ClientPlaySmokeTest', java, str(server)])
        report['result'] = 'PASS'
    finally:
        report['graphical_requested'] = args.graphical or args.graphical_only
        (output / 'result.json').write_text(json.dumps(report, indent=2) + '\n')


if __name__ == '__main__':
    main()
