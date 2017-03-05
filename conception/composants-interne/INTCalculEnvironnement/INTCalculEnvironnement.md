# INT Calcul Environnement #

Ce composant permet de calculer le type d'environnement visible par le joueur. <br/>

![](INTCalculEnvironnement-class.png)

## Configuration ##

Cette classe permet de sotcker toutes les informations de configuration :
<UL>
	<li>EnvironnementLargeur : la largeur de l'environnement</li>
	<li>EnvironnementLongueur : la longueur de l'environnement</li>
	<li>DistanceDeltaOuverture : le delta de distance maximum de placement des ouvertures d'une ligne</li>
</UL>

## IINTCalculEnvironnement ##

Interface exposant les méthodes de calculs nécessaire à la mise à jour de l'environnement.

## INTCalculEnvironnement ##

Implémentation de IINTCalculEnvironnement. C'est ici où la la logique de mise à jour de l'nevironnement est présente.

### Initialisation ###
![](INTCalculEnvironnement-initialisation.png)

### initialisationEnvironnement() ###
Initialise l'attribut _environnement.
![](initialisationEnvironnement.png)

### creationLigne() ###
Créé une nouvelle ligne à l'environnement.

![](INTCalculEnvironnement-creationLigne.png)

## Environnement ##
Cette classe représente de manière schématique l'environnement global du jeu.

### Initialisation ###
![](environnement-initialisation.png)

### ajoutLigne(Ligne) ###
Ajoute une ligne vers l'attribute _lignes.
Met à jour le curseur d'insertion de ligne.
Retourne le dernier index inséré.

![](environnement-ajoutLigne.png)

### getLigneActuelle() ###
Retourne la ligne actulle indiquée par le pointeur.<br/>
![](environnement-getLigneActuelle.png)

### populeBlocs(List<Blocs> blocs, TypeBloc typeBloc) ###
Remplis la liste blocs de blocs associés au type typeBloc. Le nombre d'élements dépend de la largeur de l'environnement de configuration. <br/>
![](INTCalculEnvironnement-populeBlocs.png)

## Ligne ##
Cette classe représente de manière schématique une ligne du jeu.

### Initialisation ###
![](ligne-initialisation.png)

### getOuvertures ###
Retourne les blocs d'ouvertures de la ligne. <br/>
![](ligne-getOuvertures.png)

### getOuverturesIndex ###
Retourve les positions des ouvertures. <br/>
![](ligne-getOuvertureIndex.png)

## TypeLigne ##
Cette énumération permet de préciser le type de la ligne. Ce type sera ensuite utilisé pour l'affichage graphique.

### getTypeAleatoire(int menace) ###
Retourne un type aléatoire suivant la menace renseingée.
![](typeLigne-getTypeAleatoire.png)