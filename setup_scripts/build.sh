#!/usr/bin/env sh
# Install clang and other dependencies

./prepare_build.sh

apt-get install --yes libc6-dev-i386 gcc-mingw-w64

apt install --yes gcc g++ zlib1g-dev libmpc-dev libmpc-dev libmpfr-dev libgmp-dev cmake libxml2-dev libssl-dev

# Setup rust
curl --proto '=https' --tlsv1.2 https://sh.rustup.rs -sSf | sh -s -- -y
# snap install rustup --classic
~/.cargo/bin/rustup toolchain install stable-x86_64-pc-windows-gnu
~/.cargo/bin/rustup toolchain install stable-x86_64-apple-darwin
~/.cargo/bin/rustup toolchain install stable

~/.cargo/bin/rustup target add x86_64-pc-windows-gnu
~/.cargo/bin/rustup target add x86_64-apple-darwin


echo '[target.x86_64-pc-windows-gnu]
linker = "/usr/bin/x86_64-w64-mingw32-gcc"
ar = "/usr/bin/x86_64-w64-mingw32-ar"

[target.x86_64-apple-darwin]
linker = "x86_64-apple-darwin14-gcc"
ar = "x86_64-apple-darwin14-ar"' > ~/.cargo/config

echo "CARGO READY"

# Setup Java

# apt update
# apt upgrade

# apt install --yes openjdk-17-jdk


JAVA_HOME=$(echo "$(realpath "$(which java)")" | rev | cut -d'/' -f3- | rev)
export JAVA_HOME
# Windows resources

# include>win
SCRIPT_HOME=$(pwd)

cd "$JAVA_HOME" || exit 1

cd "include" || exit 1

mkdir -p "win32"

cd "win32" || exit 1

wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/java.base/windows/native/include/jni_md.h
old="typedef __int64 jlong;"
new="#ifdef __GNUC__\\ntypedef long long jlong;\\n#else\\ntypedef __int64 jlong;\\n#endif"
sed -i "s|$old|$new|g" ./jni_md.h

wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/java.desktop/windows/native/include/jawt_md.h

# include>win>bridge
mkdir -p "bridge"

cd "bridge" || exit 1

wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/jdk.accessibility/windows/native/include/bridge/AccessBridgeCallbacks.h
wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/jdk.accessibility/windows/native/include/bridge/AccessBridgeCalls.h
wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/jdk.accessibility/windows/native/include/bridge/AccessBridgePackages.h

# Mac resources

# include>darwin
cd "../.."

mkdir -p "darwin"
cd "darwin" || exit 1

wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/java.base/unix/native/include/jni_md.h
wget -q https://raw.githubusercontent.com/openjdk/jdk17u/master/src/java.desktop/macosx/native/include/jawt_md.h

echo "JAVA READY"

# Build

cd "$SCRIPT_HOME/.." || exit 1

~/.cargo/bin/cargo clean

~/.cargo/bin/cargo build --release --target x86_64-pc-windows-gnu

./setup_scripts/osxcross_setup.sh

PATH="$(pwd)/osxcross/target/bin:$PATH" \
CC=o64-clang \
CXX=o64-clang++ \
LIBZ_SYS_STATIC=1 \
~/.cargo/bin/cargo build --release --target x86_64-apple-darwin