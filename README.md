
# Mancala/Kalaha Game Backend

This project is a backend service for the Mancala/Kalaha game, implemented using Spring Boot. It features a RESTful API for game interactions, an H2 in-memory database for data persistence, and includes JUnit tests to ensure code quality.

## Game Rules

Mancala, also known as Kalaha, is a two-player board game that involves the following rules:

1. **Objective**: The goal is to capture more stones than your opponent.
2. **Board Setup**: The board consists of two rows of six pits each, with a larger pit (store) at each end.
3. **Starting the Game**: Each pit contains four stones at the start of the game.
4. **Gameplay**:
   - Players take turns choosing a pit on their side of the board.
   - The chosen pit's stones are distributed one by one into the subsequent pits in a counter-clockwise direction.
   - If the last stone lands in the player's own store, they get another turn.
   - If the last stone lands in an empty pit on the player's side, they capture the stones in the opposite pit and place them in their store.
5. **Ending the Game**: The game ends when all pits on one side are empty. The player with the most stones in their store wins.

## Features

- **RESTful APIs**: Interact with the game through a set of RESTful endpoints.
- **H2 Database**: Uses an H2 in-memory database for game state management.
- **JUnit Tests**: Includes comprehensive tests to validate functionality and ensure code reliability.

## Getting Started

1. **Clone the Repository**:

   ```sh
   git clone https://github.com/yourusername/mancala-game-backend.git
   cd mancala-game-backend
   ```

2. **Build the Project**:

   ```sh
   ./mvnw clean install
   ```

3. **Run the Application**:

   ```sh
   ./mvnw spring-boot:run
   ```

4. **Access the API**: The application will be available at `http://localhost:8012`. Refer to the API documentation for available endpoints.

## Testing

To run JUnit tests:

```sh
./mvnw test
```

## License

This project is not licensed.

## Acknowledgments

- Inspired by the traditional Mancala/Kalaha game.
- Spring Boot and H2 Database for robust backend support.

---

Feel free to adjust any details according to your actual project setup or preferences!
