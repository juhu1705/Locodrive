#!/usr/bin/env sh

git clone https://github.com/tpoechtrager/osxcross
cd osxcross || exit 1
wget -nc https://s3.dockerproject.org/darwin/v2/MacOSX10.10.sdk.tar.xz
mv MacOSX10.10.sdk.tar.xz tarballs/
sed -i -e 's|-march=native||g' build_clang.sh wrapper/build_wrapper.sh
UNATTENDED=yes OSX_VERSION_MIN=10.7 ./build.sh
./build_gcc.sh
mkdir -p /usr/local/osx-ndk-x86
mv target/* /usr/local/osx-ndk-x86
cd ..