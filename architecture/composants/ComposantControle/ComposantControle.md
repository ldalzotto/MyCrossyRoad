# ComposantControle #

----------

## Messages Associées ##

<table border = "1">
	<tr>
		<th>Message</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Initialisation controle</td>
		<td>Initialise et met en place le processus de contrôle</td>
	</tr>
</table>

![](composant-messages.png)

## Entités Associées ##


<table border = "1">
	<tr>
		<th>Entité</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Joueur</td>
		<td>Modèle de Joueur.</td>
	</tr>
</table>

![](composant-entite.png)

## Initialisation ##

### Message : Initialisation controle ###

Lorsque **ComposantControle** reçoit un message **Initialisation controle**, le processus de contrôle est mis en place puis injecté dans le contexte LibGDX :

![](composant-initialisation-controle.png)

## Mise à jour Composant ##

Lorsque **ComposantControle** est mis à jour, des actions sont déclanchées en fonction de la touche appuyée :

![](composant-mise-a-jour.png)

## Périmètre ##