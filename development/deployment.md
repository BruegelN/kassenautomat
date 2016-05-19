# Deployment

Die Idee ist, dass die Ergebnisse im Anschluss an den Buildprozess zentral archiviert werden.
Damit diese von allen Beteiligten zugänglich ist es am besten, wenn der Archiv-Server aus dem Internet erreichbar ist.
Wir entschieden und für ein User-Laufwerk an der DHBW, da man auf diese recht simpel über sftp zugreifen kann und es über das Internet erreichbar ist.
Somit entsteht für uns kein zusätzlicher Wartungsaufwand und das Sicherheitsrisiko hält sich dank ```travis encrypt```(1) stark in Grenzen.


Basic push, build and deploy order(2):
![alt text][img_deployment]

Über von TravisCI gesetzen Umgebungsvariablen(3) kann man herausfinden auf welcher branch man sich befindet, wenn der *master* branch erfolgreich gebaut wird sollte die __.apk__ und die __JavaDoc__ auf das User-Laufwerk hochgeladen werden.
```bash
if[!$TRAVIS_PULL_REQUEST || $TRAVIS_BRANCH = "master"];then
  # do the magic deployment
fi
```
Möglicherweise ist es sinnvoll die Testergebnisse immer auf den DHBW Server zu laden!

#### Upload
Mit folgendem Kommando wird der Output von TravisCI auf das User-Laufwerk (http://wwwlehre.dhbw-stuttgart.de/~it14142/kassenautomat/) geladen werden.
sshpass erlaubt mit Nutzername und Passwort per ssh die Daten zu übertragen.
Das sshpass auf dem TravisCI-Server installiert wird in ```.travis.yml```ergänzen:
```yaml
addons:
    apt:
        packages:
            - sshpass
```

Diese privaten Daten werden mithilfe von ```travis encrypt```(1) verschlüsselt, sodass diese nicht im Klartext angegeben werden müssen. Diese Daten werden dann als Umgebungsvariablen gesetzt und erst bei jedem Buildprozess wieder entschlüsselt.
```yaml
env:
  global:
    - secure: KwBSu+wmC632kqnO1Kn2 [...]
    - secure: Ula6twtS6IwLd96      [...]
    - secure: VLVhFxhZumHluGeUx    [...]
    - secure: KTWAUDF7             [...]
```

Und dann mit ```sshpass```(5) uploaden im Script ```deploy_app_build.sh```:
```bash
## upload to ssh page
export SSHPASS=$SSH_PASSWD
sshpass -e scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r ./app/build $SSH_USER@$SSH_DOMAIN:${OUTPUT_PATH}
```

Auf dem User-Laufwerk wird für jeden Build eine neuer Order erstellt bestehend aus Build-Nummer und dem Namen des git branches z.b. ```69_TicketManager_Implementation```.
In jedem Ordner befindet sich die Unterorder:
* apk
* linter
* reports

Im Unterordner ```apk``` befindet sich ```app-debug.apk``` welche für Testzwecke auf dem Handy installiert werden kann.
Die Ergebnisse von Android Lint sind in unter ```linter``` abgelegt. Android Lint ist ein Tool zu statischen Code Analyse von Android.
Unter ```reports``` werden die Testergebnisse der Unit-Test gespeichert.


Reference:
  - (1) travis encrypt: https://docs.travis-ci.com/user/encryption-keys/
  - (2) Github+TravisCI workflow: https://jens-na.github.io/2014/01/22/jekyll-deploy-own-server/
  - (3) Travis enviroment variables: https://docs.travis-ci.com/user/environment-variables/#Default-Environment-Variables
  - (4) always "trust" a given host: https://serverfault.com/questions/330503/scp-without-known-hosts-check
  - (5) Use sshpass for ssh+passwd authentication:https://neemzy.org/articles/deploy-to-your-own-server-through-ssh-with-travis-ci

[img_deployment]: https://jens-na.github.io/images/deploy_workflow.png "Custom deployment"
