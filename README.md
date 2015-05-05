## TpSirM1MIAGE_Ierlomenko-Kinfack-Haidara

##Compte-rendu des tps de sir

                                                                  Iermolenko Mariia
                                                                  Kinfack Mariane
                                                                  Haidara Abdoul Wahab
  
###Partie JPA Servlet Angular
Nous avons décidé d'utiliser JHIPSTER pour nous générer notre l'environnement de développement. C'est un framework génial a utilisé.

###La reponse de NoSQL
1) Quelles sont les limites d'une base donnees orientees document?

Elles sont limitees par document a 4Mo!

Elles sont limitees au niveau des transactions. Pour la modification de plusieurs objets dependant les uns des autres, les bases orientees document n'offrent pas les garanties qu'on retrouve avec le relationnel.

Les proprietes ACID ainsi que les contraintes d'integrite ne sont generalement pas implementees. Ainsi, c'est la partie applicative qui gere ces proprietes.



--------------------------------------------

2) Quelles sont les types de donnees stockes dans Redis? Que peut on faire comme types de requetes?

Redis est une base de donnees “cles/valeurs”.
Redis permet de stocker des types simples : String, Set, List, Map et OrderedSet.

Redis dispose de nombreuses commandes, lui permettant de manipuler differents types de donnees : 
– Les chaines de caracteres (du simple SET jusqu’a la possibilite de remplacer une portion d’une valeur via SETRANGE); 
– Les listes triees (sorted sets : a chaque valeur un score est attribue, et la liste est maintenue ordonnee suivant ce score) ; 
– Les listes non triees (sets, des donnees en vrac, sans ordre garanti, mais sur lesquelles la plupart des operations se feront en temps constant) 
– Les piles (lists) ; 
– Les hashs (hashes, permettant de stocker des « sous-cles ») ;

2 types de requetes dans Redis :
- Celles qui recherchent des objets sans parent : elles sont eventually consistent. Ce type de requete est le plus rapide.
- Celles qui recherchent des objets qui ont un parent commun : elles sont strongly consistent. En revanche, ce type de requete est plus long.

