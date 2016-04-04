#!/bin/bash
set -ev

APP_BUILD_PATH="./app/build"
APP_LINTER_PATH="${APP_BUILD_PATH}/linter"
## remove auto generated things
rm -rf $APP_BUILD_PATH/generated

## remove intermediates
rm -rf $APP_BUILD_PATH/intermediates

## remove temp
rm -rf $APP_BUILD_PATH/tmp

## remove XML test-results
rm -rf $APP_BUILD_PATH/test-results

## Move apps directily into build-dir
mv $APP_BUILD_PATH/outputs/apk $APP_BUILD_PATH/apk

## Reorder linter results

# first "lint-results-release-fatal" related stuff
mkdir -p $APP_LINTER_PATH/lint-results-release-fatal/lint-results-release-fatal_files
mv $APP_BUILD_PATH/outputs/lint-results-release-fatal.html $APP_LINTER_PATH/lint-results-release-fatal/index.html
mv $APP_BUILD_PATH/outputs/lint-results-release-fatal_files ./app/build/linter/lint-results-release-fatal/

# then regular linter-results
mkdir -p $APP_LINTER_PATH/lint-results/lint-results_files
mv $APP_BUILD_PATH/outputs/lint-results.html $APP_LINTER_PATH/lint-results/index.html
mv $APP_BUILD_PATH/outputs/lint-results_files $APP_LINTER_PATH/lint-results/

## remove output folder because we allread moved the important things elsewhere
rm -rf $APP_BUILD_PATH/outputs
