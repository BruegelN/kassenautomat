#!/bin/bash
set -ev

## prepare output path coresponding to build number and branch
OUTPUT_PATH="${SSH_BASE_PATH}/${TRAVIS_BUILD_NUMBER}-${TRAVIS_BRANCH}"
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
  # we're on a pull request thus append '-PR' to output path
  OUTPUT_PATH="${OUTPUT_PATH}-PR"
fi

echo "Output path: ${OUTPUT_PATH}"
## upload to ssh page
export SSHPASS=$SSH_PASSWD
sshpass -e scp -r ./app/build $SSH_USER@$SSH_DOMAIN:$OUTPUT_PATH
