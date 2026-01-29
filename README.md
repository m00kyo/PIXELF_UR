# PIXELF - System Zarządzania Żywnością i Przepisami

![Java](https://img.shields.io/badge/Java-JDK%2021-orange.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14%2B-blue.svg)
![Swing](https://img.shields.io/badge/GUI-Swing-red.svg)

**PIXELF** to aplikacja desktopowa stworzona w języku Java, służąca do efektywnego zarządzania domowymi zasobami spożywczymi oraz bazą przepisów kulinarnych. System pozwala użytkownikom na śledzenie posiadanych składników, tworzenie przepisów oraz bezpieczne przechowywanie danych na indywidualnych kontach.

## Spis treści
- [O projekcie](#-o-projekcie)
- [Funkcjonalności](#-funkcjonalności)
- [Architektura i Technologie](#-architektura-i-technologie)
- [Wymagania systemowe](#-wymagania-systemowe)
- [Konfiguracja i Instalacja](#-konfiguracja-i-instalacja)
- [Struktura Bazy Danych](#-struktura-bazy-danych)
- [Autor](#-autor)

## O projekcie
Głównym celem systemu PIXELF jest wyeliminowanie problemu rozproszonych notatek kulinarnych oraz trudności w monitorowaniu stanów magazynowych w kuchni. Aplikacja łączy te dwa obszary w spójny interfejs graficzny. Dzięki zastosowaniu wieloużytkownikowości, każdy domownik może posiadać własne, odseparowane konto z prywatnymi danymi.

## Funkcjonalności
* **Bezpieczna autoryzacja:** Rejestracja i logowanie użytkowników z weryfikacją w bazie danych.
* **Zarządzanie składnikami:** Dodawanie, edycja i usuwanie produktów spożywczych (nazwa, ilość).
* **Baza przepisów:** Tworzenie i przechowywanie przepisów wraz z opisem wykonania.
* **Izolacja danych:** Użytkownik widzi tylko i wyłącznie własne rekordy (Relacja 1:N).
* **Responsywny interfejs:** Wykorzystanie `SwingWorker` do asynchronicznych operacji na bazie danych (brak "zamrażania" okna).

## Architektura i Technologie
Projekt został zrealizowany w architekturze trójwarstwowej (MVC) z wykorzystaniem wzorców projektowych:
* **Język:** Java (JDK 21)
* **GUI:** Java Swing
* **Baza danych:** PostgreSQL (v14+)
* **Komunikacja z DB:** JDBC Driver
* **Wzorce:**
    * **DAO (Data Access Object):** Oddzielenie logiki biznesowej od dostępu do danych.
    * **Singleton:** Zarządzanie sesją użytkownika (`UserSession`).
    * **SwingWorker:** Obsługa zadań w tle.

## Wymagania systemowe
Aby uruchomić projekt, upewnij się, że posiadasz:
* **Java Development Kit (JDK):** wersja 21 lub nowsza.
* **PostgreSQL:** wersja 14 lub nowsza.
* **Sterownik:** PostgreSQL JDBC Driver (dodany do bibliotek projektu).

## Konfiguracja i Instalacja

### 1. Konfiguracja Bazy Danych
Przed uruchomieniem aplikacji należy przygotować środowisko bazodanowe:

1.  Zainstaluj PostgreSQL. Podczas instalacji ustaw hasło dla użytkownika `postgres` na: **`admin`**.
    * *Uwaga: Jeśli używasz innego hasła, zmień je w pliku `src/Database/DbConnection.java`.*
2.  Upewnij się, że serwer działa na porcie **5432**.
3.  Stwórz pustą bazę danych o nazwie `poprojekt`. Możesz to zrobić przez pgAdmin lub terminal:
    ```sql
    CREATE DATABASE poprojekt;
    ```
    *Tabele (`users`, `recipes`, `ingredients`) zostaną utworzone **automatycznie** przez klasę `DbInitializer` przy pierwszym uruchomieniu aplikacji.*

### 2. Uruchomienie aplikacji
1.  Sklonuj repozytorium:
    ```bash
    git clone [https://github.com/m00kyo/PIXELF_UR.git](https://github.com/m00kyo/PIXELF_UR.git)
    ```
2.  Otwórz projekt w IDE (IntelliJ IDEA / NetBeans).
3.  Upewnij się, że biblioteka JDBC jest dodana do `classpath`.
4.  Uruchom plik `src/Main.java`.

##  Struktura Bazy Danych
System opiera się na trzech głównych tabelach:
1.  **users**: Przechowuje loginy i hasła.
2.  **recipes**: Przechowuje nazwy i opisy potraw (klucz obcy `id_user`).
3.  **ingredients**: Przechowuje stany magazynowe (klucz obcy `id_user`).

Relacje są zabezpieczone mechanizmem `ON DELETE CASCADE` – usunięcie użytkownika usuwa jego wszystkie dane.

##  Autor
**Oliwia Stoś**
Uniwersytet Rzeszowski<br>
Wydział Nauk Ścisłych i Technicznych<br>
Kierunek: Informatyka i Ekonometria  
