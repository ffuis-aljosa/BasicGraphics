BasicGraphics
=============

Jednostavna igra, Air Hockey, koja demonstrira iscrtavanje jednostavne 2D grafike u Javi. U igri se dva protivnika takmiče tako što pomjeraju "rekete" horizontalno, odbijajući "lopticu" ("pak"), pokušavajući da je pošalju pored protivničkog reketa, postužući poen. Igra se završava kada jedan od igrača postigne 5 poena. Svaki peti udarac lopte je ubrzava.

Iako je igra igriva, nije sasvim gotova. Mogući dodaci:

1. Ispraviti bug koji se dešava kada lopta udara reket sa strane. Naime, pri svakom udaru, napravljeno je da se lopta odbija vertikalno. Napraviti da, ukoliko lopta udari reket sa strane, odbije se horizontalno.
2. Uvesti mogućnost pauziranja igre: izabiranjem opcije u meniju, pritiskom na dugme PAUSE, kada prozor igre izgubi fokus (http://docs.oracle.com/javase/7/docs/api/java/awt/Component.html#addFocusListener%28java.awt.event.FocusListener%29).
3. Lopta se odbija isključivo pod uglom od 45 stepeni. Napraviti da kretanje reketa pri udaru utiče na ugao odbijanja. U tom slučaju se treba preurediti i ubrzavanje lopte pri svakom petom udaru.
