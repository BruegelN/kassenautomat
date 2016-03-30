# Deployment

Die Idee ist, dass die Ergebnisse im Anschluss an den Buildprozess zentral archiviert werden.
Damit diese von allen Beteiligten zugänglich ist es am besten, wenn der Archiv-Server aus dem Internet erreichbar ist.
Wir entschieden und für ein User-Laufwerk an der DHBW, da man auf diese recht simpel über sftp zugreifen kann und es über das Internet erreichbar ist.
Somit entsteht für uns kein zusätzlicher Wartungsaufwand und das Sicherheitsrisiko hält sich dank ```travis encrypt```(1) stark in Grenzen.


Basic push, build and deploy order(2):
![alt text][img_deployment]

Über von TravisCI gesetzen Umgebungsvariablen(3) kann man herausfinden auf welcher branch man sich befindet, wenn der *master* branch erfolgreich gebaut wird sollte die __.apk__ und die __JavaDoc__ auf das User-Laufwerk hochgeladen werden.
```bash
if[!$TRAVIS_PULL_REQUEST || $TRAVIS_BRANCH == "master"];then
  # do the magic deployment
fi
```
Möglicherweise ist es sinnvoll die Testergebnisse immer auf den DHBW Server zu laden!

#### Upload (TODO)
Mit folgendem Kommando soll der Output von TravisCI auf das User-Laufwerk geladen werden, wobei Nutzername, Passwort (evtl. Host und Pfad) mit ```travis encrypt```(1) verschlüsselt werden sollten.

In ```.travis.yml```ergänzen:
```ruby
addons:
    apt:
        packages:
            - sshpass```

Und dann mit ```sshpass```(5) uploaden:
```bash
- export SSHPASS=meinVerschlüsseltesPasswort
- sshpass -e scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null /path/to/output/which/should/be/uploaded USERNAME@nimwen.dhbw-stuttgart.de:~/public-html/kassenautomat/$TRAVIS_BUILD_NUMBER/```

Reference:
  - (1) travis encrypt: https://docs.travis-ci.com/user/encryption-keys/
  - (2) Github+TravisCI workflow: https://jens-na.github.io/2014/01/22/jekyll-deploy-own-server/
  - (3) Travis enviroment variables: https://docs.travis-ci.com/user/environment-variables/#Default-Environment-Variables
  - (4) always "trust" a given host: https://serverfault.com/questions/330503/scp-without-known-hosts-check
  - (5) Use sshpass for ssh+passwd authentication:https://neemzy.org/articles/deploy-to-your-own-server-through-ssh-with-travis-ci
  -

[img_deployment]: https://jens-na.github.io/images/deploy_workflow.png "Custom deployment"
