# Dart Comarques REST Client

This Dart application serves as a command-line interface to interact with a RESTful API providing information about regions (comarcas) based on provinces. It uses the `http` package for HTTP requests and includes a `Comarca.dart` file for data modeling.

## Usage

Ensure you have Dart installed on your system.

### Commands

#### 1. List Comarcas by Province

```bash
dart run main.dart comarcas <Provincia>
```

Returns a list of regions belonging to the specified province.

#### 2. Get Info about a Comarca

```bash
dart run main.dart infocomarca <Comarca>
```

Returns detailed information about the specified region.

### Examples

```bash
# List comarcas in a province
dart main.dart comarcas Barcelona

# Get info about a specific comarca
dart main.dart infocomarca Priorat
```

## Project Structure

- **main.dart:** The entry point of the application.
- **Comarca.dart:** Contains the data model for representing a comarca.

## Configuration

- **BASE_URL:** The base URL for the REST API.
- **BASE_API_GET:** The endpoint for retrieving comarcas.

## Dependencies

- **http:** Dart package for making HTTP requests.
- **convert:** Dart package for encoding and decoding data.

## How to Run

1. Install Dart on your system.
2. Open a terminal and navigate to the project directory.
3. Run the desired commands as explained in the "Usage" section.

## Notes

- The application provides feedback in case of unknown commands or unrecognized provinces/comarcas.
- Make sure to provide the correct number of arguments when running commands.

## Author

[@NefloDev](https://github.com/NefloDev)

## Teacher

[@nafarrin](https://github.com/nafarrin)
