# Git:

Für jedes Feature sollte ein Git-Branch erstellt werden!(1)
![alt text][img_featureBranch]

Jeder Entwickler hat eine lokale Kopie des Repositories.
Nach einer Änderung wird diese ```git add``` auf der staging area abgelegt.
Mit ```git commit -m 'Tolle Änderung'``` werden alle Änderungen auf der staging area in den Repo index aufgenommen, mit dem Titel 'Tolle Änderung'.
Durch ```git push``` werden die Änderungen im Repo auf dem Server, in unserem Fall Github, hoch geladen.
Mit ```git pull``` kann man sich die letzen Änderungen vom Server herunterladen.(2)
![alt text][img_gitWorkflow]

Nach der Fertigstellung von feature_x sollte in Github ein Pullrequest von __branch *feature_x*__ in den __branch *master*__ gestellt werden.
Ggf. muss ein entstandener Merge-Konflikt( git weiß nicht welche Änderung es übernehmen soll) manuell gelöst werden. Um dies möglichst zu verhindern empfiehlt es sich regelmäßig mit ```git merge origin/master``` den aktuellen *master* branch in den localen branch *feature_x* zu mergen.
Wenn diese Änderungen automatisch gemerged werden können sollte automatisch ein neuer Durchlauf auf TravisCI startet. Dieser merged die Änderungen aus *feature_x* probeweise in *master* und kompiliert dies dann und lässt die Test laufen.
Es können mit ```@GithubNutzername``` betreffende Personen genannt werden, sodass diese sich diesen Pullrequest genauer anschauen können.
Wenn der Pullrequest erfolgreich gebaut hat, besichtig und für tauglich empfunden wurde kann er gemerged und damit das __feature_x__ in den *master* branch übernommen werden.

Reference:
  - (1) Git Einstieg: https://rogerdudler.github.io/git-guide/index.de.html
  - (2) Git Workflow: https://fpy.cz/pub/slides/git-workshop/


[img_featureBranch]: https://rogerdudler.github.io/git-guide/img/branches.png "Feature branch"

[img_gitWorkflow]: https://fpy.cz/pub/slides/git-workshop/images/git_workflow.png "Git workflow"  
