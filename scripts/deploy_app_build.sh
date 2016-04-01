#!/bin/bash
set -ev

## prepare output path coresponding to build number and branch
OUTPUT_PATH="~/public-html/kassenautomat/${TRAVIS_BUILD_NUMBER}_${TRAVIS_BRANCH}"
if [ "${TRAVIS_PULL_REQUEST}" != "false" ]; then
  # we're building a pull request
  echo "Don't deploy pull requests :)"
  exit 0
fi

echo "Output path: ${OUTPUT_PATH}"
## upload to ssh page
export SSHPASS=$SSH_PASSWD
sshpass -e scp -r ./app/build $SSH_USER@$SSH_DOMAIN:${OUTPUT_PATH}
