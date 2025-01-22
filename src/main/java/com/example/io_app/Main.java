package com.example.io_app;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DOMAIN.Session;
import com.example.io_app.DOMAIN.SessionManager;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        //############################ TWORZENIE FILMÓW ############################//

        Film f1 = new Film(
                "Skazani na Shawshank",
                142,
                "Historia niesłusznie skazanego bankiera Andy'ego Dufresne'a, który spędza lata w więzieniu Shawshank, nawiązując przyjaźń z Redem i odnajdując nadzieję.",
                "Dramat",
                "Dla dorosłych",
                "Frank Darabont",
                "Angielski"
        );

        Film f2 = new Film(
                "Zielona mila",
                189,
                "Opowieść o strażniku więziennym Paulu Edgecombie i jego niezwykłej relacji z więźniem Johnem Coffeyem, posiadającym tajemnicze moce uzdrawiania.",
                "Dramat",
                "Dla dorosłych",
                "Frank Darabont",
                "Angielski"
        );

        Film f3 = new Film(
                "Nietykalni",
                112,
                "Sparaliżowany milioner Philippe zatrudnia jako opiekuna Drissa, młodego chłopaka z przedmieścia, co prowadzi do niezwykłej przyjaźni.",
                "Biograficzny, Dramat, Komedia",
                "Dla dorosłych",
                "Olivier Nakache, Éric Toledano",
                "Francuski"
        );

        Film f4 = new Film(
                "Ojciec chrzestny",
                175,
                "Saga rodziny mafijnej Corleone, skupiająca się na przejęciu władzy przez Michaela Corleone po zamachu na jego ojca, don Vito Corleone.",
                "Dramat, Gangsterski",
                "Dla dorosłych",
                "Francis Ford Coppola",
                "Angielski"
        );

        Film f5 = new Film(
                "Dwunastu gniewnych ludzi",
                96,
                "Dwunastu przysięgłych debatuje nad winą młodego chłopaka oskarżonego o morderstwo, kwestionując dowody i własne uprzedzenia.",
                "Dramat sądowy",
                "Dla dorosłych",
                "Sidney Lumet",
                "Angielski"
        );

        Film f6 = new Film(
                "Forrest Gump",
                142,
                "Forrest Gump, mężczyzna o niskim ilorazie inteligencji, przypadkowo staje się świadkiem i uczestnikiem kluczowych wydarzeń historycznych USA.",
                "Dramat, Komedia",
                "Dla dorosłych",
                "Robert Zemeckis",
                "Angielski"
        );

        Film f7 = new Film(
                "Lot nad kukułczym gniazdem",
                133,
                "Randle McMurphy trafia do szpitala psychiatrycznego, gdzie buntuje się przeciwko despotycznej siostrze Ratched, inspirując innych pacjentów.",
                "Dramat, Psychologiczny",
                "Dla dorosłych",
                "Miloš Forman",
                "Angielski"
        );

        Film f8 = new Film(
                "Ojciec chrzestny II",
                202,
                "Kontynuacja sagi rodziny Corleone, ukazująca młodość Vita Corleone oraz umacnianie władzy przez jego syna, Michaela.",
                "Dramat, Gangsterski",
                "Dla dorosłych",
                "Francis Ford Coppola",
                "Angielski"
        );

        Film f9 = new Film(
                "Władca Pierścieni: Powrót króla",
                201,
                "Finałowa bitwa o Śródziemie, podczas gdy Frodo i Sam kontynuują niebezpieczną misję zniszczenia Jedynego Pierścienia.",
                "Fantasy, Przygodowy",
                "Dla młodzieży",
                "Peter Jackson",
                "Angielski"
        );

        Film f10 = new Film(
                "Lista Schindlera",
                195,
                "Prawdziwa historia Oskara Schindlera, który podczas II wojny światowej uratował ponad tysiąc Żydów przed Holokaustem.",
                "Dramat, Wojenny",
                "Dla dorosłych",
                "Steven Spielberg",
                "Angielski"
        );

        Film f11 = new Film(
                "Pulp Fiction",
                154,
                "Przeplatające się historie gangsterów, boksera i pary drobnych złodziei w Los Angeles.",
                "Gangsterski",
                "Dla dorosłych",
                "Quentin Tarantino",
                "Angielski"
        );

        Film f12 = new Film(
                "Życie jest piękne",
                116,
                "Guido, żydowski właściciel księgarni, używa humoru, aby chronić swojego syna przed okrucieństwami obozu koncentracyjnego.",
                "Dramat, Komedia, Wojenny",
                "Dla dorosłych",
                "Roberto Benigni",
                "Włoski"
        );

        Film f13 = new Film(
                "Władca Pierścieni: Dwie wieże",
                179,
                "Drużyna Pierścienia zostaje podzielona, a Frodo i Sam kontynuują podróż do Mordoru z pomocą Golluma.",
                "Fantasy, Przygodowy",
                "Dla młodzieży",
                "Peter Jackson",
                "Angielski"
        );

        Film f14 = new Film(
                "Siedem",
                127,
                "Dwaj detektywi ścigają seryjnego mordercę, który dokonuje zbrodni inspirowanych siedmioma grzechami głównymi.",
                "Thriller, Kryminał",
                "Dla dorosłych",
                "David Fincher",
                "Angielski"
        );

        Film f15 = new Film(
                "Władca Pierścieni: Drużyna Pierścienia",
                178,
                "Frodo Baggins wyrusza w niebezpieczną podróż, aby zniszczyć Jedyny Pierścień i ocalić Śródziemie.",
                "Fantasy, Przygodowy",
                "Dla młodzieży",
                "Peter Jackson",
                "Angielski"
        );

        Film f16 = new Film(
                "Podziemny krąg",
                139,
                "Młody mężczyzna (Edward Norton) cierpiący na bezsenność spotyka charyzmatycznego sprzedawcę mydła, Tylera Durdena (Brad Pitt). Razem zakładają tajny klub walki, który szybko przekształca się w anarchistyczny ruch.",
                "Dramat, Thriller",
                "Dla dorosłych",
                "David Fincher",
                "Angielski"
        );

        Film f17 = new Film(
                "Chłopiec w pasiastej piżamie",
                94,
                "Ośmioletni Bruno, syn nazistowskiego oficera, zaprzyjaźnia się z żydowskim chłopcem Shmuelem, więźniem obozu koncentracyjnego, co prowadzi do tragicznych konsekwencji.",
                "Dramat, Wojenny",
                "Dla młodzieży",
                "Mark Herman",
                "Angielski"
        );

        Film f18 = new Film(
                "Gladiator",
                155,
                "Rzymski generał Maximus Decimus Meridius (Russell Crowe) zostaje zdradzony i zmuszony do walki jako gladiator, dążąc do zemsty na cesarzu Kommodusie (Joaquin Phoenix).",
                "Dramat, Historyczny",
                "Dla dorosłych",
                "Ridley Scott",
                "Angielski"
        );

        Film f19 = new Film(
                "Leon zawodowiec",
                110,
                "Leon (Jean Reno), profesjonalny zabójca, opiekuje się 12-letnią Mathildą (Natalie Portman), której rodzina została zamordowana przez skorumpowanego agenta DEA (Gary Oldman).",
                "Dramat, Kryminał",
                "Dla dorosłych",
                "Luc Besson",
                "Angielski"
        );

        Film f20 = new Film(
                "Siedem",
                127,
                "Detektywi William Somerset (Morgan Freeman) i David Mills (Brad Pitt) tropią seryjnego mordercę, który dokonuje zbrodni inspirowanych siedmioma grzechami głównymi.",
                "Kryminał, Thriller",
                "Dla dorosłych",
                "David Fincher",
                "Angielski"
        );

        Film f21 = new Film(
                "Bunt",
                128,
                "Samuraj Hanshiro Tsugumo (Tatsuya Nakadai) prosi o pozwolenie na popełnienie seppuku na terenie posiadłości klanu Ii, co prowadzi do ujawnienia mrocznych tajemnic.",
                "Dramat historyczny",
                "Dla dorosłych",
                "Masaki Kobayashi",
                "Japoński"
        );

        Film f22 = new Film(
                "Sól ziemi",
                110,
                "Dokument przedstawiający życie i pracę fotografa Sebastião Salgado, który przez 40 lat dokumentował ludzkość na wszystkich kontynentach.",
                "Dokumentalny",
                "Dla dorosłych",
                "Wim Wenders, Juliano Ribeiro Salgado",
                "Portugalski"
        );

        Film f23 = new Film(
                "Podziemny krąg",
                139,
                "Młody mężczyzna (Edward Norton) cierpiący na bezsenność spotyka charyzmatycznego sprzedawcę mydła, Tylera Durdena (Brad Pitt). Razem zakładają tajny klub walki, który szybko przekształca się w anarchistyczny ruch.",
                "Dramat, Thriller",
                "Dla dorosłych",
                "David Fincher",
                "Angielski"
        );

        Film f24 = new Film(
                "House, M.D., Season Four: New Beginnings",
                44,
                "Dokument przedstawiający kulisy powstawania czwartego sezonu serialu 'Dr House', ukazujący pracę ekipy filmowej i aktorów.",
                "Dokumentalny, Krótkometrażowy",
                "Dla dorosłych",
                "Greg Yaitanes",
                "Angielski"
        );

        Film f25 = new Film(
                "Nietykalni",
                112,
                "Sparaliżowany milioner Philippe (François Cluzet) zatrudnia jako opiekuna Drissa (Omar Sy), młodego chłopaka z przedmieścia, co prowadzi do niezwykłej przyjaźni.",
                "Biograficzny, Dramat, Komedia",
                "Dla dorosłych",
                "Olivier Nakache, Éric Toledano",
                "Francuski"
        );

        Film f99 = new Film(
                "Chłopcy z ferajny",
                146,
                "Henry Hill opowiada o swoim życiu jako członka mafii, od wczesnych lat po upadek imperium.",
                "Biograficzny, Gangsterski",
                "Dla dorosłych",
                "Martin Scorsese",
                "Angielski"
        );

        Film f100 = new Film(
                "Top Gun",
                109,
                "Film opowiada historię porucznika Pete'a \"Mavericka\" Mitchella (Tom Cruise), młodego i ambitnego pilota myśliwca marynarki wojennej USA. Razem ze swoim nawigatorem, podporucznikiem Nickiem \"Goose'em\" Bradshawem (Anthony Edwards), zostają wybrani do elitarnej szkoły dla pilotów myśliwców – Top Gun. Podczas szkolenia Maverick rywalizuje z porucznikiem Tomem \"Icemanem\" Kazanskym (Val Kilmer) o miano najlepszego pilota, a także nawiązuje romans ze swoją instruktorką, Charlotte \"Charlie\" Blackwood (Kelly McGillis).", // description
                "Action, Drama",
                "dla dzieci",
                "Tony Scott",
                "English"
        );


        //############################ TWORZENIE SEANSÓW ############################//

        Session s1 = SessionManager.createSession(
                1,
                f100,
                1,
                LocalDateTime.of(2025, 2, 7, 19, 45),
                20.0,
                100
        );

        Session s2 = SessionManager.createSession(
                2,
                f11,
                1,
                LocalDateTime.of(2025, 2, 20, 21, 30),
                25.0,
                100
        );


        Session s3 = SessionManager.createSession(
                3,
                f15,
                1,
                LocalDateTime.of(2025, 2, 21, 18, 0),
                20.0,
                100
        );

        Session s4 = SessionManager.createSession(
                4,
                f22,
                2,
                LocalDateTime.of(2025, 2, 14, 20, 0),
                15.0,
                50
        );

        Session s5 = SessionManager.createSession(
                5,
                f20,
                2,
                LocalDateTime.of(2025, 2, 14, 17, 0),
                15.0,
                50
        );

    //############################ ##################### ############################//

        System.out.println(SessionManager.readSessionById(1).getFilm().getName());

        List<Session> sessions = SessionManager.getSessionsSortedByPrice();

        for(Session session : sessions){
            System.out.println(session);
        }

        List<Session> sessions1 = SessionManager.findSessionsByFilmTitle("Top Gun");

        for(Session session : sessions1){
            System.out.println(session);
        }
    }
}



