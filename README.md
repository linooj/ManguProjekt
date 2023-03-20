**Dokumentatsioon**

Vajalikud asjad
- Java versioon
- IntelliJ
- Liisi Nõojärve kontakti kui midagi ei tööta

Kuidas jooksutada

1. Projekti avades vajuta paremal ääres Gradle nuppu
2. (Tee järgmine samm nii ***Server*** kui ***Client*** ) Vajuta Tasks -> build -> (topelt klikk) build
3. Projekti vaates vajuta Server -> src -> main -> java -> server -> ning jooksuta Main faili
4. Projekti vaates vajuta Client -> desktop -> src -> com.pigeon.game -> ning jooksuta DesktopLauncher faili

Kuidas mäng töötab
- Vajutades nooli saad linnuga ringi liikuda
- ESC nupp on ka päris bänger


**IDEE**- flappy bird type mäng tuviga. 

- Space bar on liikumiseks ehk õhus püsimiseks, 

- takistusteks on vastu lendavad lennukid/teised linnud/majad. 

- punktide kogumiseks korjata lendavaid saiaviile

- kahekesi mängimiseks tuleks split screen ja liikumiseks enter nupp

- AI komponendiks oleks vastasmängija, kellega punktide nimel võistelda

- mõelda, kas teha 1 elu v 3ga

- mida rohkem punkte e saia tuvi sööb, seda paksemaks ta muutub ja kiiremini alla vajub

**LISAIDEED PRAKTIKUMIST**

- Space bar asemel võib-olla teha üles-alla ja edasi-tagasi liikumine;

- Lisada power-up'id (mis nt viivad ettepoole)/shield'id vms;

**PROJEKTIPLAAN**

SPRINT 1 (nädal 2-4)
- [ ] Frameworki valimine
- [ ] Prototype client
- [ ] Prototype server
- [ ] Algne tuvi/asseti liigutus
- [ ] Connection between client and server


SPRINT 2 (4-6)
- [ ] takistusteks majad/puud/(lennuk) , mille vastu minnes põrkud tagasi (ja kaotad elu)?
- [ ] taust
- [ ] **töötab multiplayer** - split screen

SPRINT 3 (6-8)
- [ ] lennukite implementation
- [ ] punktide kogumine- saiaviilud
- [ ] AI vastase simple implementation 

SPRINT 4 (8-10)
- [ ] ava screen
- [ ] AI vastane kogub võimalikult palju punkte

SPRINT 5 (10-12)
- [ ] game lobby, kus saad valida erinevate tuvide vahel
- [ ] visuaali parandamine
- [ ] **töötab kooli serveriga**

SPRINT 6 (12-14)
- [ ] heli lisamine
- [ ] bug fixes/testing

SPRINT 7 (14-16)
- [ ] testimine/ bug fixes
- **lõpu demo**
