package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Galleon Class Test Suite")
@TmsLink("C7")
public class GalleonTest {

    private Galleon galleon;
    private IPosition startPos;

    @BeforeAll
    @DisplayName("Before All Galleon Tests")
    void beforeAll() {
        System.out.println("Running GalleonTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Galleon Tests")
    void afterAll() {
        System.out.println("Running GalleonTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Galleon Before Each Test")
    void setup() {
        startPos = new Position(0, 0);
        galleon = new Galleon(Compass.NORTH, startPos);
    }

    @AfterEach
    @DisplayName("Cleanup After Each Test")
    void teardown() {
        galleon = null;
        startPos = null;
    }

    // ------------------------------
    @Test
    @DisplayName("Galleon category name should be 'Galeao'")
    void testCategory() {
        assertEquals("Galeao", galleon.getCategory());
    }

    @Test
    @DisplayName("Galleon size should be 5")
    void testSize() {
        assertEquals(5, galleon.getSize());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    @DisplayName("Galleon positions assigned for all valid bearings")
    void testPositionsForBearings(Compass bearing) {
        Galleon g = new Galleon(bearing, new Position(1, 1));
        assertEquals(5, g.getPositions().size(), "Galleon should always have 5 positions");
    }

    @Test
    @DisplayName("Galleon is floating initially")
    void testStillFloatingInitially() {
        assertTrue(galleon.stillFloating());
    }

    @Test
    @DisplayName("Shooting all positions makes Galleon sink")
    void testStillFloatingAfterShooting() {
        for (IPosition pos : galleon.getPositions()) {
            galleon.shoot(pos);
        }
        assertFalse(galleon.stillFloating());
    }

    @Test
    @DisplayName("toString() returns non-null string")
    void testToString() {
        assertNotNull(galleon.toString());
    }

    @Nested
    @DisplayName("Exception Handling Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Constructor throws AssertionError for null bearing")
        void testConstructorNullBearing() {
            assertThrows(AssertionError.class, () -> new Galleon(null, new Position(0, 0)));
        }

        @Test
        @DisplayName("Constructor throws IllegalArgumentException for UNKNOWN bearing")
        void testConstructorIllegalBearing() {
            assertThrows(IllegalArgumentException.class, () -> new Galleon(Compass.UNKNOWN, new Position(0, 0)));
        }
    }
}