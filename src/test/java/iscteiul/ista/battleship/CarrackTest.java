package iscteiul.ista.battleship;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Carrack Class Test Suite")
@TmsLink("C5")
public class CarrackTest {

    private Carrack carrack;
    private IPosition startPos;

    @BeforeAll
    @DisplayName("Before All Carrack Tests")
    void beforeAll() {
        System.out.println("Running CarrackTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Carrack Tests")
    void afterAll() {
        System.out.println("Running CarrackTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Carrack Before Each Test")
    void setup() {
        startPos = new Position(0, 0);
        carrack = new Carrack(Compass.NORTH, startPos);
    }

    @AfterEach
    @DisplayName("Cleanup After Each Test")
    void teardown() {
        carrack = null;
        startPos = null;
    }

    // ------------------------------
    @Test
    @DisplayName("Carrack category name should be 'Nau'")
    void testCategory() {
        assertEquals("Nau", carrack.getCategory());
    }

    @Test
    @DisplayName("Carrack size should be 3")
    void testSize() {
        assertEquals(3, carrack.getSize());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    @DisplayName("Carrack positions correctly assigned for valid bearings")
    void testPositionsForValidBearings(Compass bearing) {
        Carrack c = new Carrack(bearing, new Position(1, 1));
        if (bearing == Compass.NORTH || bearing == Compass.SOUTH) {
            assertAll(
                    () -> assertEquals(3, c.getPositions().size()),
                    () -> assertEquals(1, c.getPositions().get(0).getColumn()),
                    () -> assertEquals(1, c.getPositions().get(1).getColumn()),
                    () -> assertEquals(1, c.getPositions().get(2).getColumn()),
                    () -> assertEquals(1, c.getPositions().get(0).getRow()),
                    () -> assertEquals(2, c.getPositions().get(1).getRow()),
                    () -> assertEquals(3, c.getPositions().get(2).getRow())
            );
        } else {
            assertAll(
                    () -> assertEquals(3, c.getPositions().size()),
                    () -> assertEquals(1, c.getPositions().get(0).getRow()),
                    () -> assertEquals(1, c.getPositions().get(1).getRow()),
                    () -> assertEquals(1, c.getPositions().get(2).getRow()),
                    () -> assertEquals(1, c.getPositions().get(0).getColumn()),
                    () -> assertEquals(2, c.getPositions().get(1).getColumn()),
                    () -> assertEquals(3, c.getPositions().get(2).getColumn())
            );
        }
    }

    @Test
    @DisplayName("Carrack is floating initially")
    void testStillFloatingInitially() {
        assertTrue(carrack.stillFloating());
    }

    @Test
    @DisplayName("Shooting all positions makes Carrack sink")
    void testStillFloatingAfterShooting() {
        for (IPosition pos : carrack.getPositions()) {
            carrack.shoot(pos);
        }
        assertFalse(carrack.stillFloating());
    }

    @Test
    @DisplayName("toString() returns non-null string")
    void testToString() {
        assertNotNull(carrack.toString());
    }

    @Nested
    @DisplayName("Exception Handling Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Constructor throws IllegalArgumentException for UNKNOWN bearing")
        void testConstructorIllegalBearing() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Carrack(Compass.UNKNOWN, new Position(0, 0)));
        }
    }
}