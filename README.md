# Lin Mansouri Projet Cpoo5 24 25

## Installation et lancement
1. Installer Gradle sur la machine.
2. Cloner ce dépôt.
3. Dans le répertoire du projet, 
Pour compiler : 

- ./gradlew build

Pour exécuter :

- ./gradlew run 

Pour lancer les tests :

- ./gradlew test

Pour nettoyer :

- ./gradlew clean

## Présentation du projet

Deux modules disponibles à l'initialisation :

- Corpus Analyzer : Analyse et séparations en n-grammes dans un JSON d'un corpus donné.

- Layout Evaluator : Evaluation d'une disposition clavier sur un JSON contenant les n-grammes donné par le Corpus Analyzer.

### Corpus Analyzer

3 choix de corpus à analyser :

- français
- anglais
- code java

Chaque textes d'un corpus est traité dans un thread différent et retourne à la fin un JSON avec les n-grammes.

### Layout Evaluator

3 choix de layout à évaluer : 

- azerty
- qwerty 
- devorak_fr

Analyse des n-grammes selon les critères de score donnée par le projet (SFB, LSB, ciseaux, alternances, roulements ...).
Retourne un score sous forme de float.


### Auteurs

[@FUJIZZ](https://github.com/FUJIZZ) 

[@ZairKSM](https://github.com/ZairKSM)
