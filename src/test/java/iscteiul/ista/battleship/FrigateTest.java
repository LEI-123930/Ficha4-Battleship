package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Frigate Class Test Suite")
public class FrigateTest {

    private Frigate frigate;
    private IPosition startPos;

    @BeforeAll
    @DisplayName("Before All Frigate Tests")
    void beforeAll() {
        System.out.println("Running FrigateTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Frigate Tests")
    void afterAll() {
        System.out.println("Running FrigateTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Frigate Before Each Test")
    void setup() {
        startPos = new Position(0, 0);
        frigate = new Frigate(Compass.NORTH, startPos);
    }

    @AfterEach
    @DisplayName("Cleanup After Each Test")
    void teardown() {
        frigate = null;
        startPos = null;
    }

    // ------------------------------
    @Test
    @DisplayName("Frigate category name should be 'Fragata'")
    void testCategory() {
        assertEquals("Fragata", frigate.getCategory());
    }

    @Test
    @DisplayName("Frigate size should be 4")
    void testSize() {
        assertEquals(4, frigate.getSize());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    @DisplayName("Frigate positions correctly assigned for valid bearings")
    void testPositionsForValidBearings(Compass bearing) {
        Frigate f = new Frigate(bearing, new Position(1, 1));
        if (bearing == Compass.NORTH || bearing == Compass.SOUTH) {
            assertAll(
                    () -> assertEquals(4, f.getPositions().size()),
                    () -> assertEquals(1, f.getPositions().get(0).getColumn()),
                    () -> assertEquals(1, f.getPositions().get(1).getColumn()),
                    () -> assertEquals(1, f.getPositions().get(2).getColumn()),
                    () -> assertEquals(1, f.getPositions().get(3).getColumn()),
                    () -> assertEquals(1, f.getPositions().get(0).getRow()),
                    () -> assertEquals(2, f.getPositions().get(1).getRow()),
                    () -> assertEquals(3, f.getPositions().get(2).getRow()),
                    () -> assertEquals(4, f.getPositions().get(3).getRow())
            );
        } else {
            assertAll(
                    () -> assertEquals(4, f.getPositions().size()),
                    () -> assertEquals(1, f.getPositions().get(0).getRow()),
                    () -> assertEquals(1, f.getPositions().get(1).getRow()),
                    () -> assertEquals(1, f.getPositions().get(2).getRow()),
                    () -> assertEquals(1, f.getPositions().get(3).getRow()),
                    () -> assertEquals(1, f.getPositions().get(0).getColumn()),
                    () -> assertEquals(2, f.getPositions().get(1).getColumn()),
                    () -> assertEquals(3, f.getPositions().get(2).getColumn()),
                    () -> assertEquals(4, f.getPositions().get(3).getColumn())
            );
        }
    }

    @Test
    @DisplayName("Frigate is floating initially")
    void testStillFloatingInitially() {
        assertTrue(frigate.stillFloating());
    }

    @Test
    @DisplayName("Shooting all positions makes Frigate sink")
    void testStillFloatingAfterShooting() {
        for (IPosition pos : frigate.getPositions()) {
            frigate.shoot(pos);
        }
        assertFalse(frigate.stillFloating());
    }

    @Test
    @DisplayName("toString() returns non-null string")
    void testToString() {
        assertNotNull(frigate.toString());
    }

    @Nested
    @DisplayName("Exception Handling Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Constructor throws IllegalArgumentException for UNKNOWN bearing")
        void testConstructorIllegalBearing() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Frigate(Compass.UNKNOWN, new Position(0, 0)));
        }
    }
}