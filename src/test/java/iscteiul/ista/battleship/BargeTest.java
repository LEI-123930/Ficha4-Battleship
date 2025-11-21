package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import io.qameta.allure.TmsLink; // <--- ADICIONAR ESTE IMPORT

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Barge Class Test Suite")
@TmsLink("C3")
public class BargeTest {

    private Barge barge;
    private IPosition startPos;

    @BeforeAll
    @DisplayName("Before All Barge Tests")
    void beforeAll() {
        System.out.println("Running BargeTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Barge Tests")
    void afterAll() {
        System.out.println("Running BargeTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Setup Barge Before Each Test")
    void setup() {
        startPos = new Position(0, 0);
        barge = new Barge(Compass.NORTH, startPos);
    }

    @AfterEach
    @DisplayName("Cleanup After Each Test")
    void teardown() {
        barge = null;
        startPos = null;
    }

    // ------------------------------
    @Test
    @DisplayName("Barge category name should be 'Barca'")
    void testCategory() {
        assertEquals("Barca", barge.getCategory());
    }

    @Test
    @DisplayName("Barge size should be 1")
    void testSize() {
        assertEquals(1, barge.getSize());
    }

    @Test
    @DisplayName("Barge initial position should match constructor")
    void testPosition() {
        IPosition pos = barge.getPosition();
        assertAll(
                () -> assertEquals(startPos.getRow(), pos.getRow()),
                () -> assertEquals(startPos.getColumn(), pos.getColumn())
        );
    }

    @ParameterizedTest
    @EnumSource(Compass.class)
    @DisplayName("Barge can be created with any Compass direction")
    void testAllBearings(Compass bearing) {
        Barge b = new Barge(bearing, new Position(1, 1));
        assertEquals(bearing, b.getBearing());
    }

    @Test
    @DisplayName("Barge positions list contains only one position")
    void testPositionsList() {
        assertEquals(1, barge.getPositions().size());
        IPosition pos = barge.getPositions().get(0);
        assertAll(
                () -> assertEquals(startPos.getRow(), pos.getRow()),
                () -> assertEquals(startPos.getColumn(), pos.getColumn())
        );
    }

    @Test
    @DisplayName("Barge still floating initially")
    void testStillFloatingInitially() {
        assertTrue(barge.stillFloating());
    }

    @Test
    @DisplayName("Shooting Barge position makes it no longer floating")
    void testStillFloatingAfterHit() {
        IPosition pos = barge.getPositions().get(0);
        barge.shoot(pos);
        assertFalse(barge.stillFloating());
    }

    @Test
    @DisplayName("toString() returns non-null string")
    void testToString() {
        assertNotNull(barge.toString());
    }
}