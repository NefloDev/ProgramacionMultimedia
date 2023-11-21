# MVVMPokemon

MVVMPokemon is a simple Android application that simulates Pokemon battles. The application is written in Java and Kotlin, and uses the Model-View-ViewModel (MVVM) architectural pattern.

## Main Components

1. `MainMenu.java`: This class represents the main menu of the application. It's a Fragment that inflates a layout from `FragmentMainMenuBinding`. It sets up navigation to the `PokemonBattleFragment` and `EditPokemonFragment` when certain UI elements are clicked.

2. `PokemonBattleFragment.java`: This class represents the battle screen of the application. It's a Fragment that inflates a layout from `FragmentPokemonBattleBinding`. It sets up the UI for a Pokemon battle, including displaying the Pokemon's stats and handling the battle logic.

3. `PokemonModel.java`: This class represents the data model for a Pokemon. It includes the Pokemon's stats (name, hp, atk, def, spAtk, spDef) and methods for creating a Pokemon and starting a battle. It also includes interfaces for callbacks and battle listeners.

4. `PokemonViewModel.java`: This class is the ViewModel in the MVVM pattern. It's responsible for preparing and managing the data for the UI. It includes methods for creating Pokemon, starting a battle, and setting Pokemon data. It also includes LiveData objects for observing changes to the Pokemon data and battle results.

## Project Structure

The project uses Android's Navigation component for navigating between screens (Fragments), and LiveData for observing changes to the data. The battle logic is handled in the `PokemonModel` class, and the results are observed and displayed in the `PokemonBattleFragment`.

## Building the Project

The project uses Gradle for building and managing dependencies. To build the project, you can use the `gradlew build` command in the project root directory.

## Running the Project

To run the project, you can use the `gradlew run` command in the project root directory. This will start the Android emulator and launch the application.
