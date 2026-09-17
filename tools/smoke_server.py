#!/usr/bin/env python3
"""Launch an explicit Beta server jar headlessly in a fresh disposable directory.

Only tests startup, console shutdown, save presence and observed class loading.
Does not assert multiplayer, worldgen parity or a completed milestone.
"""
import argparse
import hashlib
import json
from pathlib import Path
import queue
import re
import socket
import struct
import subprocess
import threading
import time


def probe_login(port):
    """Exercise Beta protocol 14 handshake/login, without simulating player actions."""
    def string(value):
        encoded = value.encode('utf-16-be')
        return struct.pack('>h', len(encoded) // 2) + encoded

    with socket.create_connection(('127.0.0.1', port), timeout=10) as connection:
        def read(size):
            data = b''
            while len(data) < size:
                chunk = connection.recv(size - len(data))
                if not chunk:
                    raise ValueError('Server closed the connection during login')
                data += chunk
            return data

        def read_string():
            length = struct.unpack('>h', read(2))[0]
            if not 0 <= length <= 1000:
                raise ValueError('Invalid response string length')
            return read(length * 2).decode('utf-16-be')

        connection.sendall(b'\x02' + string('OriginsProbe'))
        if read(1) != b'\x02' or read_string() != '-':
            raise ValueError('Expected Beta offline handshake challenge (-)')
        connection.sendall(b'\x01' + struct.pack('>i', 14) + string('OriginsProbe')
                           + struct.pack('>qb', 0, 0))
        if read(1) != b'\x01':
            raise ValueError('Expected successful Beta login response')
        entity_id = struct.unpack('>i', read(4))[0]
        read_string()
        seed, dimension = struct.unpack('>qb', read(9))
        return {'result': 'PASS', 'protocol': 14, 'offline_challenge': '-',
                'entity_id': entity_id, 'seed': seed, 'dimension': dimension}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java', required=True, type=Path)
    parser.add_argument('--jar', required=True, type=Path)
    parser.add_argument('--output', required=True, type=Path)
    parser.add_argument('--probe-login', action='store_true')
    parser.add_argument('--level-template', type=Path,
                        help='Copy this level.dat into the disposable world before startup')
    args = parser.parse_args()
    java, jar, output = args.java.resolve(), args.jar.resolve(), args.output.resolve()
    jar_hash = hashlib.sha256(jar.read_bytes()).hexdigest()
    if output.is_relative_to(Path(__file__).resolve().parents[1] / 'reference'):
        parser.error('Runtime writes must remain outside reference/.')
    output.mkdir(parents=True, exist_ok=False)
    template_hash = None
    if args.level_template:
        template = args.level_template.read_bytes()
        template_hash = hashlib.sha256(template).hexdigest()
        (output / 'smoke-world').mkdir()
        (output / 'smoke-world/level.dat').write_bytes(template)
    with socket.socket() as listener:
        listener.bind(('127.0.0.1', 0))
        port = listener.getsockname()[1]
    # There is a port allocation race; an occupied port must fail the smoke.
    (output / 'server.properties').write_text(
        f'server-ip=127.0.0.1\nserver-port={port}\nonline-mode=false\n'
        'level-name=smoke-world\nlevel-seed=8675309\n')
    command = [str(java), '-Xmx512M', '-Djava.awt.headless=true', '-verbose:class',
               '-jar', str(jar), 'nogui']
    runtime = subprocess.check_output([str(java), '-version'], stderr=subprocess.STDOUT,
                                      text=True)
    messages = queue.Queue()
    lines = []
    ready = False
    error = None
    login = None
    process = subprocess.Popen(command, cwd=output, stdin=subprocess.PIPE,
                               stdout=subprocess.PIPE, stderr=subprocess.STDOUT,
                               text=True, bufsize=1)

    def read_output():
        for line in process.stdout:
            messages.put(line)
        messages.put(None)

    reader = threading.Thread(target=read_output, daemon=True)
    reader.start()
    deadline = time.monotonic() + 90
    try:
        while time.monotonic() < deadline:
            try:
                line = messages.get(timeout=0.25)
            except queue.Empty:
                continue
            if line is None:
                break
            lines.append(line)
            if 'Done (' in line and not ready:
                ready = True
                if args.probe_login:
                    login = probe_login(port)
                process.stdin.write('stop\n')
                process.stdin.flush()
                deadline = time.monotonic() + 30
        if process.poll() is None:
            process.wait(timeout=5)
    except (subprocess.TimeoutExpired, OSError, ValueError) as exc:
        error = str(exc)
    finally:
        if process.poll() is None:
            process.kill()
            process.wait()
            error = error or 'Server exceeded startup/shutdown deadline'
        reader.join(timeout=5)
        while not messages.empty():
            line = messages.get_nowait()
            if line is not None:
                lines.append(line)
        process.stdin.close()
        process.stdout.close()
    log = ''.join(lines)
    (output / 'process.log').write_text(log)
    forbidden = re.findall(r'\[Loaded ((?:org\.lwjgl\.|paulscode\.|net\.minecraft\.client\.)\S+)', log)
    passed = (ready and process.returncode == 0 and 'Stopping server' in log
              and (output / 'smoke-world/level.dat').is_file() and not forbidden and not error)
    report = {'result': 'PASS' if passed else 'FAIL', 'jar_sha256': jar_hash,
              'command': command, 'runtime': runtime, 'working_directory': str(output),
              'bind_address': '127.0.0.1', 'port': port, 'seed': 8675309,
              'ready': ready, 'exit_code': process.returncode,
              'stop_logged': 'Stopping server' in log,
              'level_dat_exists': (output / 'smoke-world/level.dat').is_file(),
              'forbidden_loaded_classes': forbidden, 'error': error,
              'login_probe': login,
              'level_template_sha256': template_hash,
              'process_log_sha256': hashlib.sha256(log.encode()).hexdigest(),
              'limits': 'Optional synthetic login only; no graphical client, player actions, '
                        'worldgen, save-content or gameplay parity assertion.'}
    (output / 'result.json').write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps(report, indent=2))
    return 0 if passed else 1


if __name__ == '__main__':
    raise SystemExit(main())
