## klasa Reference
jest ona zrobiona po to żeby łatwo można było w całym projekcie odwołać się do pewnej stałej wartości. jest to coś w stylu wartości globalnych, jeśli do niej wejdziecie to są w niej zapisane wartości takie jak szerokość piksela, długość i szerokość okienka czy chociażby kierunki, żeby przy projektowaniu poruszania nie głowić się nad znaczeniem numerków tylko przeczytać co ona reprezentuje

## klasa Window

Klasa window jest główną klasą w której jest również funkcja main. Od niego wszystko się zaczyna tworzą sie w niej okienka z ustawieniami oraz okno z fabryką oraz przypisuję się fabryke do tego okna żeby mogła się ona w nim wyświetlać

## klasa Factory

Klasa Factory jest canvasem i jest ona tworzona w klasie Window a następnie przypisana do okienka, w niej dzieją się główne wywołania wszystkich funkcji oraz pętla symulacji w mainie jest pętla która odpala się co określony czas i wywołuje funkcję draw z Factory w której dzieją sie wszystkie aktualizacje a następnie factory wywołuje funkcje repaint żeby przerysować mapkę

## klasa Map

W niej generowana jest mapa. jest ona przechowywana w dwuwymiarowej zmiennej. następnie w metodzie Map.draw(g2) której jest przekazywany kontekst graficzny (żeby na nim coś narysować po prostu) mapa ta zostaje przetłumaczona na różne kolory w tej funkcji i wyrysowana na bufor jeśli nie była wcześniej wygenerowana (żeby szybciej się rysowała cała symulacja), a jeśli mapa była już raz wygenerowana to poprostu jest rysowany bufor na którym wczesniej wygenerowała się mapa wszystko rysuję sie w kontekscie który był przekazany do funkcji

## klasa settings

W niej będą późiej określane parametry symulacji które będą możliwe do ustawienia przez użytkownika

## klasa PerlinNoise

generuje ten gładki szum o którym wcześniej mówiłem ona będzie wykorzystana przy rynku