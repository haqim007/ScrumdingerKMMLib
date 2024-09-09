#!/bin/bash

cd ~/haqim/practices/kmm/ScrumdingerKMMLib
./gradlew clean
./gradlew build
./gradlew generateMRcommonMain
./gradlew podPublishXCFramework

cd ~/haqim/practices/ios/Scrumdinger
pod deintegrate
pod install --repo-update

cd ~/haqim/practices/kmm/ScrumdingerKMMLib
