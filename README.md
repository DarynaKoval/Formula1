# Formula1_OOP - Симулятор команди Формули 1

## Опис проєкту

**Formula1_OOP** - це об'єктно-орієнтована програма-симулятор, яка дозволяє користувачеві виступати в ролі **Team Principal** (головного команди) і керувати командою Формули 1. Програма демонструє застосування принципів ООП, шаблонів проектування, тестування та інших важливих концепцій програмування.

### Основна ідея

Користувач грає роль керівника команди. Сценарій роботи програми:

1. **Підготовка (Гараж):** Створення команди, найм гонщиків, конструювання боліда
2. **Кваліфікація:** Вибір шин і стратегії
3. **Гонка (Симуляція):** Текстова симуляція гонки коло за колом
4. **Фініш:** Підрахунок балів та результатів

## Реалізовані функції

### Етап 1: Базова структура та ООП принципи

- **Класи:** TeamMember (абстрактний), Driver, Engineer, Mechanic, TeamPrincipal
- **ООП принципи:** Наслідування, Інкапсуляція, Поліморфізм, Абстракція

### Етап 2: Модель боліда (Car)

- Потужність двигуна: 500-1000 к.с.
- Аеродинаміка: Рівень 1-10
- Паливо: Система споживання та заправки
- Типи шин: Soft, Medium, Hard

### Етап 3: Ієрархія виключень

- **F1Exception** - базовий клас
- **FuelException** - помилки з паливом
- **TireException** - помилки з шинами
- **RaceException** - загальні помилки гонки

### Етап 4: Породжувальні шаблони

- **Factory Method** - TireFactory
- **Builder** - CarBuilder
- **Singleton** - TeamManager

### Етап 5: Поведінкові шаблони

- **Strategy** - DrivingStrategy (Aggressive, Economical, Balanced)
- **State** - CarState (Normal, Optimal, Overheating, Critical)
- **Observer** - RaceObserver (ConsoleObserver, LoggingObserver, EventCounterObserver)

### Етап 6: Структурні шаблони

- **Adapter** - EngineAdapter
- **Decorator** - EngineDecorator (TurboCharger, EnergyRecoverySystem)

### Етап 7: Team клас та GRASP принципи

- Creator, Information Expert, Low Coupling, High Cohesion, Controller

### Етап 8-9: Симуляція гонки та система балів

- Race - симуляція гонки
- ScoringSystem - система балів F1
- RaceResult - результати гонки

### Етап 10-11: TDD та Рефакторинг

- Повне тестове покриття
- Validator клас для валідації
- Константи замість магічних чисел

### Етап 12-13: Головний клас та UML діаграма

- App.java з інтерфейсом користувача
- UML_Diagram.puml - діаграма класів

## Структура проєкту

```
Formula1_OOP/
├── src/
│   ├── models/              # Моделі даних
│   │   ├── Car.java
│   │   ├── Tire.java
│   │   ├── Team.java
│   │   ├── Race.java
│   │   ├── RaceResult.java
│   │   ├── ScoringSystem.java
│   │   └── team/            # Члени команди
│   │       ├── TeamMember.java
│   │       ├── Driver.java
│   │       ├── Engineer.java
│   │       ├── Mechanic.java
│   │       └── TeamPrincipal.java
│   ├── exceptions/          # Ієрархія виключень
│   ├── patterns/            # Шаблони проектування
│   │   ├── creational/
│   │   ├── behavioral/
│   │   └── structural/
│   ├── utils/               # Утиліти
│   ├── test/                # Тести
│   └── App.java             # Головний клас
└── README.md
```

## Як запустити програму

### Компіляція та запуск:

- `compile.bat` - компіляція
- `run.bat` - запуск програми
- `test_all.bat` - запуск всіх тестів
- `test_simple.bat` - простий тест

### Запуск вручну:

```bash
cd src
javac App.java models/**/*.java patterns/**/*.java exceptions/*.java utils/*.java
java App
```

### Запуск тестів:

```bash
cd src
javac test/*.java models/**/*.java patterns/**/*.java exceptions/*.java
java -ea test.TeamMemberTest
java -ea test.CarTest
# ... інші тести
```
