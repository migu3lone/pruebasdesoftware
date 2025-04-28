# -*- mode: python ; coding: utf-8 -*-


a = Analysis(
    ['conversor.py'],
    pathex=[],
    binaries=[],
    datas=[('sound.mp3', '.'), ('utp.png', '.'), ('bin/bin.exe', 'bin'), ('octa/octa.exe', 'octa'), ('hexa/hexa.exe', 'hexa'), ('base/base.exe', 'base')],
    hiddenimports=[],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[],
    noarchive=False,
    optimize=0,
)
pyz = PYZ(a.pure)

exe = EXE(
    pyz,
    a.scripts,
    a.binaries,
    a.datas,
    [],
    name='conversor',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=False,
    disable_windowed_traceback=False,
    argv_emulation=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
)
