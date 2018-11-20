#!/usr/bin/env bash

# the build is triggered by a tag push, and the tag looks like
# a version number: proceed with release
gpg --keyserver hkp://keys.gnupg.net --recv-keys ${GPG_KEYNAME}
mvn versions:set -DnewVersion=${TRAVIS_TAG}
mvn -s .travis/settings.xml -Prelease deploy
