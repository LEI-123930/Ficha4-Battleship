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

    @BeforeAll
    void beforeAll() { System.out.println("Running @BeforeAll"); }

    @BeforeEach
    void setUp() { ship = new TestShip(Compass.NORTH, new Position(0, 0), 3); }

    @AfterEach
    void afterEach() { System.out.println("Completed a test."); }

    @AfterAll
    void afterAll() { System.out.println("Running @AfterAll"); }

    // ------------------------------ BASIC TESTS ------------------------------

    @Test
    void testCategory() { assertEquals("testShip", ship.getCategory()); }

    @Test
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
    void testSink() {
        ship.shoot(new Position(0, 0));
        ship.shoot(new Position(1, 0));
        ship.shoot(new Position(2, 0));
        assertFalse(ship.stillFloating());
    }

    // ------------------------------ PARAMETERIZED TESTS ------------------------------

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testOccupiesParameterized(int row) {
        assertTrue(ship.occupies(new Position(row, 0)));
    }

    // ------------------------------ BOUNDARY TESTS ------------------------------

    @Nested
    class BoundaryTests {

        @Test void testTopMost() { assertEquals(0, ship.getTopMostPos()); }
        @Test void testBottomMost() { assertEquals(2, ship.getBottomMostPos()); }
        @Test void testLeftMost() { assertEquals(0, ship.getLeftMostPos()); }
        @Test void testRightMost() { assertEquals(0, ship.getRightMostPos()); }
    }

    // ------------------------------ NULL AND EDGE CASES ------------------------------

    @Test void testShootNull() {
        assertThrows(AssertionError.class, () -> ship.shoot(null));
    }

    @Test void testOccupiesNull() {
        assertThrows(AssertionError.class, () -> ship.occupies(null));
    }

    @Test void testTooCloseToNullShip() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null));
    }

    @Test void testTooCloseToNonAdjacentPosition() {
        Position far = new Position(10, 10);
        assertFalse(ship.tooCloseTo(far));
    }

    // ------------------------------ toString & buildShip ------------------------------

    @Test void testToString() { assertNotNull(ship.toString()); }

    @Test
    void testBuildShip() {
        Position pos = new Position(0, 0);
        assertTrue(Ship.buildShip("barca", Compass.NORTH, pos) instanceof Barge);
        assertTrue(Ship.buildShip("caravela", Compass.NORTH, pos) instanceof Caravel);
        assertTrue(Ship.buildShip("nau", Compass.NORTH, pos) instanceof Carrack);
        assertTrue(Ship.buildShip("fragata", Compass.NORTH, pos) instanceof Frigate);
        assertTrue(Ship.buildShip("galeao", Compass.NORTH, pos) instanceof Galleon);
        assertNull(Ship.buildShip("InvalidShip", Compass.NORTH, pos));
    }
}