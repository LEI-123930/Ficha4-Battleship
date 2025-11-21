package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Ship Abstract Class – Complete Test Suite")
public class ShipTest {

    private Ship ship;

    // Dummy concrete subclass
    private static class TestShip extends Ship {

        private final int size;

        public TestShip(Compass bearing, IPosition pos, int size) {
            super("testShip", bearing, pos);
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
    @DisplayName("Before All Tests")
    void beforeAll() {
        System.out.println("Running @BeforeAll");
    }

    @BeforeEach
    @DisplayName("Setting Up Test Ship Instance")
    void setUp() {
        ship = new TestShip(Compass.NORTH, new Position(0, 0), 3);
    }

    @AfterEach
    @DisplayName("Cleaning up after each test")
    void afterEach() {
        // No cleanup needed but included to demonstrate usage
        System.out.println("Completed a test.");
    }

    @AfterAll
    @DisplayName("After All Tests")
    void afterAll() {
        System.out.println("Running @AfterAll");
    }

    // ------------------------------
    // BASIC TESTS
    // ------------------------------

    @Test
    @DisplayName("Category Should Match Constructor")
    void testCategory() {
        assertEquals("testShip", ship.getCategory());
    }

    @Test
    @DisplayName("Initial State Should Be Valid")
    void testInitialState() {
        assertAll(
                () -> assertEquals(Compass.NORTH, ship.getBearing()),
                () -> assertEquals(0, ship.getPosition().getRow()),
                () -> assertEquals(0, ship.getPosition().getColumn()),
                () -> assertEquals(3, ship.getSize()),
                () -> assertTrue(ship.stillFloating())
        );
    }

    @Test
    @DisplayName("Ship Sinks After All Positions Hit")
    void testSink() {
        ship.shoot(new Position(0, 0));
        ship.shoot(new Position(1, 0));
        ship.shoot(new Position(2, 0));
        assertFalse(ship.stillFloating());
    }

    // ------------------------------
    // PARAMETERIZED TEST
    // ------------------------------

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("Ship Should Occupy All Its Own Coordinates")
    void testOccupiesParameterized(int row) {
        assertTrue(ship.occupies(new Position(row, 0)));
    }

    // ------------------------------
    // NESTED TESTS
    // ------------------------------

    @Nested
    @DisplayName("Boundary Position Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Top-most Position Should Be Computed Correctly")
        void testTopMost() {
            assertEquals(0, ship.getTopMostPos());
        }

        @Test
        @DisplayName("Bottom-most Position Should Be Computed Correctly")
        void testBottomMost() {
            assertEquals(2, ship.getBottomMostPos());
        }

        @Test
        @DisplayName("Left-most Position Should Be Computed Correctly")
        void testLeftMost() {
            assertEquals(0, ship.getLeftMostPos());
        }

        @Test
        @DisplayName("Right-most Position Should Be Computed Correctly")
        void testRightMost() {
            assertEquals(0, ship.getRightMostPos());
        }
    }

    // ------------------------------
    // ERROR CASE TEST
    // ------------------------------

    @Test
    @DisplayName("shoot() Should Not Allow Null")
    void testShootNull() {
        assertThrows(AssertionError.class, () -> ship.shoot(null));
    }

    @Test
    @DisplayName("toString() Should Not Return Null")
    void testToString() {
        assertNotNull(ship.toString());
    }
}
