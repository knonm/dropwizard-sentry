#!/usr/bin/env bash

if [ "${TRAVIS_EVENT_TYPE}" == push ] &&
    echo "${TRAVIS_TAG}" | egrep '^[0-9]+\.[0-9]+\.[0-9]+(-[0-9]+)?$'
then
    # the build is triggered by a tag push, and the tag looks like
    # a version number: proceed with release
    gpg --keyserver hkp://keys.gnupg.net --recv-keys ${GPG_KEYNAME}
    mvn versions:set -DnewVersion=${TRAVIS_TAG}
    mvn -s .travis/settings.xml -Prelease deploy
else
    # this is a regular build
    mvn install
fi
