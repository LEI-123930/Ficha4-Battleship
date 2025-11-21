package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Game Class – Full Test Suite")
class GameTest {

    private Game game;
    private IFleet fleet;
    private IShip ship;
    private IPosition pos1, pos2;

    @BeforeAll
    void beforeAll() {
        System.out.println("Starting GameTest suite...");
    }

    @AfterAll
    void afterAll() {
        System.out.println("Finished GameTest suite.");
    }

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
        pos1 = new Position(0,0);
        pos2 = new Position(0,1);
        ship = new Caravel(Compass.EAST, pos1);
        fleet.addShip(ship);
        game = new Game(fleet);
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Nested
    @DisplayName("Fire Method Tests")
    class FireTests {

        @Test
        @DisplayName("Invalid shot increments invalid counter")
        void testInvalidShot() {
            IPosition invalid = new Position(-1, -1);
            assertNull(game.fire(invalid));
            assertEquals(1, game.getInvalidShots());
        }

        @Test
        @DisplayName("Repeated shot increments repeated counter")
        void testRepeatedShot() {
            game.fire(pos1);
            game.fire(pos1);
            assertEquals(1, game.getRepeatedShots());
        }

        @Test
        @DisplayName("Hit increments hits counter")
        void testHit() {
            game.fire(pos1);
            assertEquals(1, game.getHits());
        }

        @Test
        @DisplayName("Sinking ship increments sinks counter")
        void testSink() {
            for (IPosition p : ship.getPositions()) {
                game.fire(p);
            }
            assertEquals(ship.getSize(), game.getHits());
            assertEquals(1, game.getSunkShips());
        }

        @Test
        @DisplayName("Missed shot adds to shots without hitting")
        void testMissShot() {
            IPosition miss = new Position(9,9);
            assertNull(game.fire(miss));
            assertTrue(game.getShots().contains(miss));
        }
    }

    @Nested
    @DisplayName("Remaining Ships Tests")
    class RemainingShipsTests {

        @Test
        @DisplayName("Get remaining ships after hit")
        void testRemainingShips() {
            assertEquals(1, game.getRemainingShips());
            for (IPosition p : ship.getPositions()) {
                game.fire(p);
            }
            assertEquals(0, game.getRemainingShips());
        }
    }

    @Nested
    @DisplayName("Board Print Tests")
    class BoardPrintTests {

        @Test
        @DisplayName("Print valid shots board")
        void testPrintValidShots() {
            game.fire(pos1);
            game.printValidShots();  // Just for coverage
        }

        @Test
        @DisplayName("Print fleet board")
        void testPrintFleet() {
            game.printFleet();  // Just for coverage
        }

        @ParameterizedTest
        @CsvSource({
                "0,0,'X'",
                "1,1,'#'"
        })
        @DisplayName("Print custom board")
        void testPrintBoard(int row, int col, char marker) {
            List<IPosition> positions = new ArrayList<>();
            positions.add(new Position(row, col));
            game.printBoard(positions, marker);  // Just for coverage
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {

        @Test
        @DisplayName("Getters return correct initial counts")
        void testInitialCounts() {
            assertEquals(0, game.getHits());
            assertEquals(0, game.getInvalidShots());
            assertEquals(0, game.getRepeatedShots());
            assertEquals(0, game.getSunkShips());
        }
    }
}