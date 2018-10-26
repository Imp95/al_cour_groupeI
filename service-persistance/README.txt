Service de persistance...
Avec Symfony 4.1

Prérequis,

Apache web serveur,
PHP 7.1 minimum, 
MySQL,
Composer

aller à la racine et faire composer update pour dl les dépendances

creer un VHOST qui cible le dossier racine

configurer le .env

______________________________

creer la bdd,
importer le schéma

________________________________

fichier brouillon, sera restructuré plus tard......

quand vous poolez, copier le .env.dist en .env
_____________________________________
commandes utiles.....

composer update
php bin/console doctrine:database:create
php bin/console doctrine:schema:update --force

php bin/console doctrine:fixtures:load