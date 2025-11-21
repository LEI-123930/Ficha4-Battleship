package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Fleet Class – Complete Test Suite")
public class FleetTest {

    private Fleet fleet;
    private IShip testShip1;
    private IShip testShip2;

    // Dummy concrete ship class for testing
    private static class DummyShip extends Ship {

        private final int size;

        public DummyShip(String category, Compass bearing, IPosition pos, int size) {
            super(category, bearing, pos);
            this.size = size;
            for (int i = 0; i < size; i++) {
                positions.add(new Position(pos.getRow() + i, pos.getColumn()));
            }
        }

        @Override
        public Integer getSize() {
            return size;
        }
    }

    // ------------------------------
    // LIFECYCLE METHODS
    // ------------------------------

    @BeforeAll
    @DisplayName("Before All Fleet Tests")
    void beforeAll() {
        System.out.println("Running FleetTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Fleet Tests")
    void afterAll() {
        System.out.println("Running FleetTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Fleet and Ships")
    void setup() {
        fleet = new Fleet();
        testShip1 = new DummyShip("Galeao", Compass.NORTH, new Position(0, 0), 3);
        testShip2 = new DummyShip("Fragata", Compass.EAST, new Position(5, 5), 2);
    }

    @AfterEach
    @DisplayName("Cleanup after each test")
    void teardown() {
        fleet = null;
        testShip1 = null;
        testShip2 = null;
    }

    // ------------------------------
    // BASIC TESTS
    // ------------------------------

    @Test
    @DisplayName("Fleet initially empty")
    void testInitialFleetEmpty() {
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("Add ship successfully to fleet")
    void testAddShipSuccess() {
        boolean added = fleet.addShip(testShip1);
        assertTrue(added);
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Cannot add overlapping ship")
    void testAddShipCollision() {
        fleet.addShip(testShip1);
        IShip overlappingShip = new DummyShip("Nau", Compass.SOUTH, new Position(0, 0), 3);
        boolean added = fleet.addShip(overlappingShip);
        assertFalse(added);
    }

    @Test
    @DisplayName("Cannot add ship outside board")
    void testAddShipOutsideBoard() {
        IShip outsideShip = new DummyShip("Caravela", Compass.NORTH, new Position(Fleet.BOARD_SIZE, 0), 2);
        assertFalse(fleet.addShip(outsideShip));
    }

    @Test
    @DisplayName("Cannot add more ships than FLEET_SIZE")
    void testAddShipExceedsFleetSize() {
        for (int i = 0; i < Fleet.FLEET_SIZE; i++) {
            fleet.addShip(new DummyShip("Barca", Compass.NORTH, new Position(i, 0), 1));
        }
        IShip extraShip = new DummyShip("Barca", Compass.NORTH, new Position(0, 1), 1);
        assertFalse(fleet.addShip(extraShip));
    }

    // ------------------------------
    @Nested
    @DisplayName("Get Ships Like Category")
    class ShipsLikeTests {

        @Test
        @DisplayName("Return ships by category")
        void testGetShipsLikeCategory() {
            fleet.addShip(testShip1);
            fleet.addShip(testShip2);

            List<IShip> galeaoShips = fleet.getShipsLike("Galeao");
            List<IShip> fragataShips = fleet.getShipsLike("Fragata");

            assertAll(
                    () -> assertEquals(1, galeaoShips.size()),
                    () -> assertEquals(testShip1, galeaoShips.get(0)),
                    () -> assertEquals(1, fragataShips.size()),
                    () -> assertEquals(testShip2, fragataShips.get(0))
            );
        }

        @Test
        @DisplayName("Return empty list if no ships match category")
        void testGetShipsLikeEmpty() {
            fleet.addShip(testShip1);
            List<IShip> result = fleet.getShipsLike("Caravela");
            assertTrue(result.isEmpty());
        }
    }

    // ------------------------------
    @Nested
    @DisplayName("Floating Ships Tests")
    class FloatingShipsTests {

        @Test
        @DisplayName("Return only still floating ships")
        void testGetFloatingShips() {
            fleet.addShip(testShip1);
            fleet.addShip(testShip2);

            // shoot all positions of testShip2 to sink it
            for (IPosition pos : testShip2.getPositions()) {
                pos.shoot();
            }

            List<IShip> floating = fleet.getFloatingShips();
            assertAll(
                    () -> assertEquals(1, floating.size()),
                    () -> assertEquals(testShip1, floating.get(0))
            );
        }
    }

    // ------------------------------
    @Nested
    @DisplayName("Ship At Position Tests")
    class ShipAtTests {

        @Test
        @DisplayName("Return ship at given position")
        void testShipAtPosition() {
            fleet.addShip(testShip1);
            IPosition pos = testShip1.getPositions().get(1);
            IShip found = fleet.shipAt(pos);
            assertEquals(testShip1, found);
        }

        @Test
        @DisplayName("Return null if no ship at position")
        void testShipAtNull() {
            fleet.addShip(testShip1);
            IShip found = fleet.shipAt(new Position(10, 10));
            assertNull(found);
        }
    }

    // ------------------------------
    @Nested
    @DisplayName("Printing Methods Tests")
    class PrintingTests {

        @Test
        @DisplayName("Printing all ships does not throw")
        void testPrintAllShips() {
            fleet.addShip(testShip1);
            assertDoesNotThrow(fleet::printAllShips);
        }

        @Test
        @DisplayName("Printing floating ships does not throw")
        void testPrintFloatingShips() {
            fleet.addShip(testShip1);
            assertDoesNotThrow(fleet::printFloatingShips);
        }

        @Test
        @DisplayName("Printing ships by category does not throw")
        void testPrintShipsByCategory() {
            fleet.addShip(testShip1);
            assertDoesNotThrow(() -> fleet.printShipsByCategory("Galeao"));
        }

        @Test
        @DisplayName("Printing ships by null category triggers assertion")
        void testPrintShipsByCategoryNull() {
            assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null));
        }
    }
}
