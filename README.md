**HatchWorksTest Project Description**
1. Project Modularization: The application is structured into 3 main modules:
* __App__: Handles the Presentation Layer (Activity, Composable screens, Navigation & Design system).
* __Network__: Contains utility classes for managing Dispatchers, Network & Error.
* __Movies__: Manages the Data and Domain Layers for consuming data sources.

2. Clean Architecture Implementation: The app adheres to Clean Architecture principles, separating the codebase into three distinct layers:.
* __Data Layer__ :This layer is used to manage network data for the business logic.
  * __datasource__: This package contains everything related to service access and associated data models, included the pa.
  * __repository__: Implements the actual API requests.

* __Domain Layer__: This layer handles the core Business Logic.
  * __repository__: Package containing the interfaces to access the repository implementations (from the Data Layer).
  * __mapper__: Package containing the mappers to transform data models to domain models 
  * __model__: Stores the domain data models.

* __Presentation Layer__: This layer handles the viewmodels.
  * __viewmodel__: Package containing the viewmodels charge to expose the stateflow to App.

* __App Module__:
  * __activity__: Stores the main starting Activity.
  * __designsystem__: Package managing the app's theme classes (Colors, Typography, Shapes, composable components & some utils constants).
  * __navigation__: Package managing the navigation app, included interface to expose navigation, and graph. This is used to define the application's navigation flow.
  * __screen__: Package managing the application screens (Home movies list, Movie detail, Movies list paginated, Search movie).

3. Development Principles and Practices
- SOLID Principles: The application was developed considering the SOLID principles (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion).
- Testing and Stability: Unit tests were implemented for the Data, Domain & Presentation Layers within the 'movies' module.
- Clean Code Standards: The development adhered to Clean Code standards, ensuring an architecture that is scalable and reliable:
- The code is easy to read (KISS principle).
- The code avoids duplication and unnecessary external libraries (DRY principle).
- The code is descriptive and well-named.
- The code includes unit tests.
- The code is decoupled and easily scalable.
- The code is readable and reliable.

4. Technologies and Libraries Used: The app was developed using modern, industry-standard libraries and trends:

- Compose UI: For developing declarative user interfaces.
- Retrofit and OKHttp: For consuming web services.
- Material Design: For standardized UI components.
- Hilt: For dependency injection.
- Coil: For efficient image loading.
- Coroutines: For thread management and asynchronous operations.
- Paging : For efficiente data load with pages.
- Kotlin Serialization: For data serialization.
- Mockk: For mocking data in unit tests.

5. Data Source
-  TMDB Movie API was used to fetch the movies data: https://developer.themoviedb.org/


This app was developed with Love and Passion by Esteban Barrios ❤️.
