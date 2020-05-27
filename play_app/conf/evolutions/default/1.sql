# --- !Ups

PRAGMA foreign_keys = true;

CREATE TABLE "actor" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "comment" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "content" VARCHAR NOT NULL,
    "user" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id") ON DELETE CASCADE ,
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE
);

CREATE TABLE "director" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "genre" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "movie" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "title" VARCHAR NOT NULL,
    "description" TEXT NOT NULL,
    "price" FLOAT NOT NULL,
    "productionYear" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "order" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "user" VARCHAR NOT NULL,
    "payment" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id") ON DELETE CASCADE,
    FOREIGN KEY ("payment") REFERENCES payment("id") ON DELETE CASCADE
);

CREATE TABLE "orderItem" (
    "order" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    "price" FLOAT NOT NULL,
    PRIMARY KEY ("order", "movie"),
    FOREIGN KEY ("order") REFERENCES [order]("id") ON DELETE CASCADE,
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE
);

CREATE TABLE "payment" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "rating" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "value" INT NOT NULL,
    "user" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id") ON DELETE CASCADE,
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE
);

CREATE TABLE "user" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL
);

CREATE TABLE "movie_actor" (
    "movie" VARCHAR NOT NULL,
    "actor" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "actor"),
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE,
    FOREIGN KEY ("actor") REFERENCES actor("id") ON DELETE CASCADE
);

CREATE TABLE "movie_director" (
    "movie" VARCHAR NOT NULL,
    "director" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "director"),
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE,
    FOREIGN KEY ("director") REFERENCES director("id") ON DELETE CASCADE
);

CREATE TABLE "movie_genre" (
    "movie" VARCHAR NOT NULL,
    "genre" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "genre"),
    FOREIGN KEY ("movie") REFERENCES movie("id") ON DELETE CASCADE,
    FOREIGN KEY ("genre") REFERENCES genre("id") ON DELETE CASCADE
);

INSERT INTO [user] VALUES ('1', 'Jan', 'Kowalski', 'jk@gmail.com', 'admin123');
INSERT INTO [user] VALUES ('2', 'Piotr', 'Nowak', 'pn@gmail.com', 'admin123');

INSERT INTO actor VALUES ('1', 'John', 'Travolta', 'photo url');
INSERT INTO actor VALUES ('2', 'Samuel', 'L. Jackson', 'photo url');
INSERT INTO actor VALUES ('3', 'Bruce', 'Wilis', 'photo url');
INSERT INTO actor VALUES ('4', 'Leonardo', 'Di Caprio', 'photo url');
INSERT INTO actor VALUES ('5', 'Jamie', 'Foxx', 'photo url');
INSERT INTO actor VALUES ('6', 'Christoph', 'Waltz', 'photo url');
INSERT INTO actor VALUES ('7', 'Brad', 'Pitt', 'photo url');
INSERT INTO actor VALUES ('8', 'Eli', 'Roth', 'photo url');
INSERT INTO actor VALUES ('9', 'Christopher ', 'Carley', 'photo url');
INSERT INTO actor VALUES ('10', 'Bee', 'Wang', 'photo url');
INSERT INTO actor VALUES ('11', 'Morgan', 'Freeman', 'photo url');
INSERT INTO actor VALUES ('12', 'Mike', 'Colter', 'photo url');
INSERT INTO actor VALUES ('13', 'Clint', 'Eastwood', 'photo url');
INSERT INTO actor VALUES ('14', 'Kevin', 'Spacey', 'photo url');
INSERT INTO actor VALUES ('15', 'Ben', 'Affleck', 'photo url');
INSERT INTO actor VALUES ('16', 'Rosamund ', 'Pike', 'photo url');
INSERT INTO actor VALUES ('17', 'Tom ', 'Hardy', 'photo url');
INSERT INTO actor VALUES ('18', 'Fionn', 'Whitehead', 'photo url');
INSERT INTO actor VALUES ('19', 'Tom ', 'Glynn-Carney', 'photo url');
INSERT INTO actor VALUES ('20', 'Mark ', 'Rylance', 'photo url');
INSERT INTO actor VALUES ('21', 'Cilian ', 'Murphy', 'photo url');

INSERT INTO director VALUES ('1', 'Quentin', 'Tarantino', 'https://fwcdn.pl/ppo/01/11/111/449997.2.jpg');
INSERT INTO director VALUES ('2', 'Clint', 'Eastwood', 'https://fwcdn.pl/ppo/01/22/122/449655.2.jpg');
INSERT INTO director VALUES ('3', 'David', 'Fincher', 'https://fwcdn.pl/ppo/04/59/10459/450510.2.jpg');
INSERT INTO director VALUES ('4', 'Christopher', 'Nolan', 'https://fwcdn.pl/ppo/08/96/40896/449999.2.jpg');

INSERT INTO genre VALUES ('1', 'Gangsterski');
INSERT INTO genre VALUES ('2', 'Western');
INSERT INTO genre VALUES ('3', 'Wojenny');
INSERT INTO genre VALUES ('4', 'Dramat');
INSERT INTO genre VALUES ('5', 'Kryminał');
INSERT INTO genre VALUES ('6', 'Thriller');
INSERT INTO genre VALUES ('7', 'Sci-Fi');

INSERT INTO movie VALUES ('1', 'Pulp Fiction', 'Płatni mordercy, Jules (Samuel L. Jackson) i Vincent (John Travolta), dostają zlecenie, by odzyskać z rąk przypadkowych rabusiów tajemniczą walizkę bossa mafii. Nie dość tego, Vincent dostaje kolejną robotę - na czas nieobecności gangstera w mieście ma zaopiekować się jego poszukującą wrażeń żoną Mią (Uma Thurman). Vincent i Jules niespodziewanie wpadają po uszy, gdy przypadkowo zabijają zakładnika. Kłopoty ma też podupadły bokser (Bruce Willis), który otrzymał dużą sumę za przegranie swojej walki. Walkę jednak wygrywa, a Los Angeles staje się od tej chwili dla niego za małe. Specjaliści od mokrej roboty będą mieli co robić...', 11.99, '1994', 'https://xl.movieposterdb.com/07_10/1994/110912/xl_110912_55345443.jpg');
INSERT INTO movie VALUES ('2', 'Django', 'Rok 1858, południe Stanów Zjednoczonych. Niewolnik Django (Jamie Foxx) spotyka na swojej drodze doktora Kinga Schultza (Christoph Waltz), pochodzącego z Niemiec łowcę nagród. Ten, obiecuje mu wolność i zapłatę w zamian za pomoc w odnalezieniu poszukiwanych przez prawo braci Brittle. Django zgadza się i wkrótce obaj wykonują robotę. Po wszystkim zawiązują spółkę. Schultz wprowadza Django w tajniki zawodu łowcy nagród. Ponadto, poruszony historią swojego nowego przyjaciela, postanawia pomóc mu w odszukaniu żony Broomhildy (Kerry Washington). Okazuje się, że znajduje się ona na niesławnej plantacji należącej do Calvina Candie (Leonardo DiCaprio), odrażającego dżentelmena pasjonującego się walkami niewolników.', 12.99, '2012', 'https://xl.movieposterdb.com/12_12/2012/1853728/xl_1853728_d287098c.jpg');
INSERT INTO movie VALUES ('3', 'Bękarty Wojny', 'Akcja filmu rozpoczyna się w okupowanej Francji podczas egzekucji rodziny Shosanny Dreyfus (Mélanie Laurent), której dziewczyna jest świadkiem. Stracenia dokonuje nazistowski pułkownik Hans Landa (Christoph Waltz). Shosannie udaje się uciec i wyjechać do Paryża, gdzie, jako właścicielka kina, przyjmuje nową tożsamość. W innym miejscu w Europie, porucznik Aldo Raine (Brad Pitt) organizuje grupę żydowskich żołnierzy, którzy mają dokonywać aktów zemsty. Do oddziału Raine''a dołącza niemiecka aktorka i agentka Bridget Von Hammersmark (Diane Kruger), której misją jest pozbawienie władzy przywódców Trzeciej Rzeszy. Losy tych ludzi zbiegają się pod kinowym afiszem, gdzie Shosanna przygotowuje własny plan zemsty...', 19.99, '2009', 'https://xl.movieposterdb.com/09_09/2009/361748/xl_361748_7248b6dd.jpg');
INSERT INTO movie VALUES ('4', 'Gran Torino', 'Walt Kowalski, weteran wojny w Korei i zapiekły rasista z krwi i kości o stalowej woli, żyjący w świecie, który nieustannie ulega zmianom, zostaje zmuszony przez sąsiadów-imigrantów, którzy wraz ze swoją rodziną wprowadzili się po sąsiedzku, do trudnej konfrontacji z własnymi zadawnionymi uprzedzeniami. A wszystko zacznie się od klasycznego samochodu Kowalskiego - Gran Torino z 1972 roku.', 13.99, '2008', 'https://xl.movieposterdb.com/08_12/2008/1205489/xl_1205489_9ab064dc.jpg');
INSERT INTO movie VALUES ('5', 'Za wszelką cenę', 'Frankie Dunn (Clint Eastwood) w swoim życiu wytrenował wielu świetnych bokserów. Jego jedynym przyjacielem jest były bokser Scrap (Morgan Freeman). Pewnego dnia u Dunna zjawia się 30-letnia Maggie Fitzgerald (Hilary Swank). Maggie jest utalentowana, dokładnie wie, czego chce i zrobi wszystko, aby to osiągnąć. Jedynie brak jej wiary w siebie. Frankie początkowo nie sprzyja dziewczynie, gdyż nigdy nie trenował kobiet, a na dodatek uważa, że w wieku 30 lat nie ma już mowy o zrobieniu kariery w boksie. Maggie jednak nie poddaje się, w czym wspiera ją Scrap. Wkrótce i Frankie zmieni zdanie na jej temat.', 11.99, '2004', 'https://xl.movieposterdb.com/05_01/2004/0405159/xl_4825_0405159_32599334.jpg');
INSERT INTO movie VALUES ('6', 'Dobry, zły i brzydki', 'Ostatnia część "dolarowej trylogii" Sergia Leone. Trzej mężczyźni, Zły - bezwzględny łowca nagród, Brzydki - wielokrotny przestępca, Dobry - łowca głów z zasadami, usiłują dotrzeć do skarbu ukrytego przez wojsko. Brzydki jest w posiadaniu informacji o nazwie cmentarza, na którym został on zakopany, Dobry zna nazwisko na poszukiwanym grobie. Czyni to z nich mimowolny zespół. Po piętach depcze im Zły.', 6.99, '1966', 'https://xl.movieposterdb.com/08_12/1966/60196/xl_60196_7da39a90.jpg');
INSERT INTO movie VALUES ('7', 'Siedem', 'Dwaj policjanci: czarnoskóry William Somerset (Morgan Freeman) i biały David Mills (Brad Pitt) skrajnie się różnią. Somerset jest apatycznym, zmęczonym życiem samotnikiem, który z obawy przed odpowiedzialnością nie założył rodziny. Od wymarzonej emerytury dzieli go tylko jeden dzień. Podczas 30-letniej pracy w policji ani razu nie użył broni. Jest jednak świetnym detektywem, doskonale kojarzącym fakty. Mills natomiast to porywczy, pełen energii młodzieniec. Wraz z żoną Tracy przenieśli się ze spokojnej prowincji do dużego miasta. Woli on działać niż analizować i bardzo angażuje się w swoją pracę. W wielkim mieście, w którym obaj pełnią służbę, panuje siedem grzechów głównych: lenistwo, chciwość, żądza, obżarstwo, pycha, zazdrość i gniew. Tu również grasuje tajemniczy "mściciel". Pierwszą ofiarą odkrytą przez policję jest mężczyzna, który zmarł w wyniku obżarstwa pod przymusem. Wkrótce policjanci znajdują drugą ofiarę, którą jest bogaty adwokat. W jego domu morderca zostawił krwawy napis: "chciwość". Somerset wysuwa hipotezę, że ostatnie zbrodnie to dzieło seryjnego, psychopatycznego mordercy.', 9.99, '1995', 'https://xl.movieposterdb.com/06_05/1995/0114369/xl_115148_0114369_f2af901e.jpg');
INSERT INTO movie VALUES ('8', 'Zaginiona dziewczyna', 'Po utracie pracy Nick Dunne (Ben Affleck) przeprowadza się wraz z żoną, Amy (Rosamund Pike), z Nowego Jorku do rodzinnej miejscowości w stanie Missouri. Tam otwiera bar, korzystając z oszczędności małżonki. Pomimo dobrze prosperującego interesu oboje zaczynają coraz bardziej się od siebie oddalać. Gdy w rocznicę ślubu Amy przepada bez śladu, Nick staje się głównym podejrzanym.', 12.99, '2014', 'https://xl.movieposterdb.com/14_08/2014/2267998/xl_2267998_7b0a1735.jpg');
INSERT INTO movie VALUES ('9', 'Incepcja', 'Światowej sławy filmowiec Christopher Nolan wyreżyserował film z gwiazdorską obsadą, który zabiera widzów w podróż dookoła ziemskiego globu oraz w głąb intymnego i nieskończonego świata snów. Dom Cobb (Leonardo DiCaprio) jest niezwykle sprawnym złodziejem, mistrzem w wydobywaniu wartościowych sekretów ukrytych głęboko w świadomości podczas fazy snu, kiedy umysł jest najbardziej wrażliwy. Wyjątkowe umiejętności Cobba uczyniły z niego ważnego gracza w świecie szpiegostwa przemysłowego, ale i najbardziej poszukiwanego zbiega, a za swoją pozycję zapłacił utratą wszystkiego, co kocha. Teraz Cobb otrzymuje szansę na odkupienie. Za sprawą jednego, ostatniego zadania może odzyskać stracone życie. Musi tylko wraz ze swym zespołem dokonać rzeczy niemożliwej: zamiast skraść myśl, zaszczepić ją w śpiącym umyśle. Jeśli im się to uda, dokonają zbrodni doskonałej. Jednak nawet najbardziej precyzyjne planowanie nie jest w stanie przygotować ich na spotkanie z niezwykłym przeciwnikiem, który potrafi przewidzieć każdy ich ruch. Wróg, którego tylko Cobb mógł się spodziewać.', 17.99, '2010', 'https://xl.movieposterdb.com/10_08/2010/1375666/xl_1375666_7a0bed4a.jpg');
INSERT INTO movie VALUES ('10', 'Dunkierka', 'Lato 1940 roku. Wróg przyparł brytyjskie i francuskie armie do morza. Otoczeni w Dunkierce żołnierze czekają na swój los, licząc na cudowne ocalenie. Ewakuacją z portu dowodzą komandor Bolton (Kenneth Branagh) i pułkownik Winnant (James D''Arcy), podczas gdy zwykli szeregowcy, tacy jak Tommy (Fionn Whitehead), Gibson (Aneurin Barnard) i Alex (Harry Styles) próbują wydostać się z zatłoczonej plaży. Tymczasem w Anglii na wezwanie rządu odpowiadają właściciele cywilnych statków, w tym pan Dawson (Mark Rylance), który na pokładzie swojego jachtu wyrusza w niebezpieczny rejs do Dunkierki, chcąc pomóc w ewakuacji. Jednocześnie mała eskadra lotnicza, w skład której wchodzi Farrier (Tom Hardy), ma zapewnić osłonę wycofującym się z kontynentu żołnierzom. Piloci RAF-u muszą stawić czoła dominującemu w powietrzu przeciwnikowi.', 19.99, '2017', 'https://i.imgur.com/7wowqA7.jpg');

INSERT INTO movie_actor VALUES ('1', '1');
INSERT INTO movie_actor VALUES ('1', '2');
INSERT INTO movie_actor VALUES ('1', '3');
INSERT INTO movie_actor VALUES ('2', '2');
INSERT INTO movie_actor VALUES ('2', '4');
INSERT INTO movie_actor VALUES ('2', '5');
INSERT INTO movie_actor VALUES ('2', '6');
INSERT INTO movie_actor VALUES ('3', '6');
INSERT INTO movie_actor VALUES ('3', '7');
INSERT INTO movie_actor VALUES ('3', '8');
INSERT INTO movie_actor VALUES ('4', '9');
INSERT INTO movie_actor VALUES ('4', '10');
INSERT INTO movie_actor VALUES ('4', '13');
INSERT INTO movie_actor VALUES ('5', '11');
INSERT INTO movie_actor VALUES ('5', '12');
INSERT INTO movie_actor VALUES ('5', '13');
INSERT INTO movie_actor VALUES ('6', '13');
INSERT INTO movie_actor VALUES ('7', '11');
INSERT INTO movie_actor VALUES ('7', '7');
INSERT INTO movie_actor VALUES ('7', '14');
INSERT INTO movie_actor VALUES ('8', '15');
INSERT INTO movie_actor VALUES ('8', '16');
INSERT INTO movie_actor VALUES ('9', '4');
INSERT INTO movie_actor VALUES ('9', '17');
INSERT INTO movie_actor VALUES ('10', '18');
INSERT INTO movie_actor VALUES ('10', '19');
INSERT INTO movie_actor VALUES ('10', '20');
INSERT INTO movie_actor VALUES ('10', '21');

INSERT INTO movie_director VALUES ('1', '1');
INSERT INTO movie_director VALUES ('2', '1');
INSERT INTO movie_director VALUES ('3', '1');
INSERT INTO movie_director VALUES ('4', '2');
INSERT INTO movie_director VALUES ('5', '2');
INSERT INTO movie_director VALUES ('6', '2');
INSERT INTO movie_director VALUES ('7', '3');
INSERT INTO movie_director VALUES ('8', '3');
INSERT INTO movie_director VALUES ('9', '4');
INSERT INTO movie_director VALUES ('10', '4');

INSERT INTO movie_genre VALUES ('1', '1');
INSERT INTO movie_genre VALUES ('2', '2');
INSERT INTO movie_genre VALUES ('3', '3');
INSERT INTO movie_genre VALUES ('4', '4');
INSERT INTO movie_genre VALUES ('5', '4');
INSERT INTO movie_genre VALUES ('6', '2');
INSERT INTO movie_genre VALUES ('7', '5');
INSERT INTO movie_genre VALUES ('7', '6');
INSERT INTO movie_genre VALUES ('8', '4');
INSERT INTO movie_genre VALUES ('8', '6');
INSERT INTO movie_genre VALUES ('9', '6');
INSERT INTO movie_genre VALUES ('9', '7');
INSERT INTO movie_genre VALUES ('10', '3');
INSERT INTO movie_genre VALUES ('10', '4');

INSERT INTO payment VALUES ('1', 'BLIK');
INSERT INTO payment VALUES ('2', 'Karta Visa/MasterCard');
INSERT INTO payment VALUES ('3', 'Przelew tradycyjny');
INSERT INTO payment VALUES ('4', 'Kupon');

INSERT INTO comment VALUES ('1', 'Komentarz 1', '1', '1');
INSERT INTO comment VALUES ('2', 'Komentarz 2', '2', '2');
INSERT INTO comment VALUES ('3', 'Komentarz 3', '2', '3');
INSERT INTO comment VALUES ('4', 'Komentarz 4', '1', '4');
INSERT INTO comment VALUES ('5', 'Komentarz 5', '1', '2');
INSERT INTO comment VALUES ('6', 'Komentarz 6', '2', '1');
INSERT INTO comment VALUES ('7', 'Komentarz 7', '1', '3');

INSERT INTO [order] VALUES ('1', '1', '2');
INSERT INTO [order] VALUES ('2', '2', '1');
INSERT INTO [order] VALUES ('3', '1', '3');
INSERT INTO [order] VALUES ('4', '2', '1');
INSERT INTO [order] VALUES ('5', '2', '4');

INSERT INTO orderItem VALUES ('1', '1', 9.99);
INSERT INTO orderItem VALUES ('1', '2', 6.99);
INSERT INTO orderItem VALUES ('1', '3', 9.99);
INSERT INTO orderItem VALUES ('2', '4', 4.99);
INSERT INTO orderItem VALUES ('2', '5', 12.99);
INSERT INTO orderItem VALUES ('3', '6', 9.99);
INSERT INTO orderItem VALUES ('4', '7', 9.99);
INSERT INTO orderItem VALUES ('4', '8', 11.99);
INSERT INTO orderItem VALUES ('5', '9', 10.99);
INSERT INTO orderItem VALUES ('5', '10', 9.99);
INSERT INTO orderItem VALUES ('1', '9', 18.99);
INSERT INTO orderItem VALUES ('3', '1', 9.99);
INSERT INTO orderItem VALUES ('4', '1', 4.99);
INSERT INTO orderItem VALUES ('5', '4', 7.99);

INSERT INTO rating VALUES ('1', '8', '1', '1');
INSERT INTO rating VALUES ('2', '3', '2', '2');
INSERT INTO rating VALUES ('3', '6', '1', '2');
INSERT INTO rating VALUES ('4', '7', '1', '3');
INSERT INTO rating VALUES ('5', '10', '2', '3');

# --- !Downs

DROP TABLE actor;
DROP TABLE comment;
DROP TABLE director;
DROP TABLE genre;
DROP TABLE movie;
DROP TABLE [order];
DROP TABLE orderItem;
DROP TABLE payment;
DROP TABLE rating;
DROP TABLE [user];