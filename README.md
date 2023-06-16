# Hannya

Hannya jest grą polegającą na rozwiązywaniu zadań w stylu competitive programming w języku c++.

Gracz ma do swojej dyspozycji pięć różnych ekranów: menu, treść, edytor, testerkę oraz statystyki.

Menu udostępnia 6 różnych trybów które można aktywować aby utrudnić rozgrywkę.

Jeżeli zadanie okaże się zbyt wymagające to można je ominąć za pomocą ALT + S (S jak słabeusz). 

W edytorze gracz może zaciągać kod z gotowej biblioteczki za pomocą trybu zaciągania w który się wchodzi tworząc wydłużony komentarz /// wewnątrz edytora.
Tryb zaciągania pozwala na wyszukiwanie algorytmów oraz zaciąganie ich za pomocą wciśnięcia TAB.
Dodatkowo w trybie zaciągania znajduje się przykładowe rozwiązanie zadania Hello world. Gracz może je znaleźć pisząc /// Hello.

Testerka pozwala na przetestowanie rozwiązania przed wysłaniem zgłoszenia (ALT + T) oraz wysyłanie zgłoszeń (ALT + C). Testowane zgłoszenia dają użytkownikowi test na którym jego program się wysypał, out który na nim wypisał oraz poprawny out, aby użytkownik mógł zdebugować rozwiązanie łatwiej. Powiadamiamy gracza o werdykcie za pomocą bardzo starannie dobranych animacji o uniwersalnym przekazie. Dzięki temu projekt łatwo można wzbogacić o nowe języki, bez potrzeby ingerowania w nie (skalowalność).

Statystyki są generowane w obrębie jednej rozgrywki.

Gracz może nawigować się pomiędzy wszystkimi powyższymi ekranami za pomocą kombinacji ALT + {UP, RIGHT, LEFT, DOWN}.

# Uwagi dotyczące kompilacji 

Zależności w pom.xml powinny w zupełności wystarczać do skompilowania lokalnie projektu (przynajmniej na macOS oraz względnie normalnych dystrybucjach linuxa).

W razie kłopotów z kompilacją, może się okazać pomocne zainstalowania libavformat lub libavcodec w jednej z
następujących wersji: 54, 56, 57, ffmpeg-56, ffmpeg-57

Można też zakomentować parę linijek w App.java aby projekt nie był zależny od javafx media, ale jest to mocno odradzane, jako że gracz bardzo dużo wtedy traci na imersji.



