package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Caravel Class Test Suite")
@TmsLink("C4")
public class CaravelTest {

    private Caravel caravel;
    private IPosition startPos;

    @BeforeAll
    @DisplayName("Before All Caravel Tests")
    void beforeAll() {
        System.out.println("Running CaravelTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Caravel Tests")
    void afterAll() {
        System.out.println("Running CaravelTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Caravel Before Each Test")
    void setup() {
        startPos = new Position(0, 0);
        caravel = new Caravel(Compass.NORTH, startPos);
    }

    @AfterEach
    @DisplayName("Cleanup After Each Test")
    void teardown() {
        caravel = null;
        startPos = null;
    }

    // ------------------------------
    @Test
    @DisplayName("Caravel category name should be 'Caravela'")
    void testCategory() {
        assertEquals("Caravela", caravel.getCategory());
    }

    @Test
    @DisplayName("Caravel size should be 2")
    void testSize() {
        assertEquals(2, caravel.getSize());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    @DisplayName("Caravel positions correctly assigned for valid bearings")
    void testPositionsForValidBearings(Compass bearing) {
        Caravel c = new Caravel(bearing, new Position(1, 1));
        if (bearing == Compass.NORTH || bearing == Compass.SOUTH) {
            assertAll(
                    () -> assertEquals(2, c.getPositions().size()),
                    () -> assertEquals(1, c.getPositions().get(0).getColumn()),
                    () -> assertEquals(1, c.getPositions().get(1).getColumn()),
                    () -> assertEquals(1, c.getPositions().get(0).getRow()),
                    () -> assertEquals(2, c.getPositions().get(1).getRow())
            );
        } else {
            assertAll(
                    () -> assertEquals(2, c.getPositions().size()),
                    () -> assertEquals(1, c.getPositions().get(0).getRow()),
                    () -> assertEquals(1, c.getPositions().get(1).getRow()),
                    () -> assertEquals(1, c.getPositions().get(0).getColumn()),
                    () -> assertEquals(2, c.getPositions().get(1).getColumn())
            );
        }
    }

    @Test
    @DisplayName("Caravel is floating initially")
    void testStillFloatingInitially() {
        assertTrue(caravel.stillFloating());
    }

    @Test
    @DisplayName("Shooting Caravel positions makes it sink")
    void testStillFloatingAfterShooting() {
        for (IPosition pos : caravel.getPositions()) {
            caravel.shoot(pos);
        }
        assertFalse(caravel.stillFloating());
    }

    @Test
    @DisplayName("toString() returns non-null string")
    void testToString() {
        assertNotNull(caravel.toString());
    }

    @Nested
    @DisplayName("Exception Handling Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Constructor throws AssertionError for null bearing")
        void testConstructorNullBearing() {
            assertThrows(AssertionError.class, () -> new Caravel(null, new Position(0, 0)));
        }

        @Test
        @DisplayName("Constructor throws IllegalArgumentException for UNKNOWN bearing")
        void testConstructorIllegalBearing() {
            assertThrows(IllegalArgumentException.class, () -> new Caravel(Compass.UNKNOWN, new Position(0, 0)));
        }
    }
}