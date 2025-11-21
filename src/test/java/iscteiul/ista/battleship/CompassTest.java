package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Compass Enum Test Suite")
public class CompassTest {

    @BeforeAll
    @DisplayName("Before All Compass Tests")
    void beforeAll() {
        System.out.println("Running CompassTest @BeforeAll");
    }

    @AfterAll
    @DisplayName("After All Compass Tests")
    void afterAll() {
        System.out.println("Running CompassTest @AfterAll");
    }

    @BeforeEach
    @DisplayName("Before Each Test")
    void setup() {
        System.out.println("Starting individual test");
    }

    @AfterEach
    @DisplayName("After Each Test")
    void teardown() {
        System.out.println("Finished individual test");
    }

    // ───────────────────────────────────────────────
    @Nested
    @DisplayName("Enum Basic Tests")
    class EnumBasics {

        @Test
        @DisplayName("Compass enum constants should exist")
        void testEnumValues() {
            assertAll(
                    () -> assertEquals('n', Compass.NORTH.getDirection()),
                    () -> assertEquals('s', Compass.SOUTH.getDirection()),
                    () -> assertEquals('e', Compass.EAST.getDirection()),
                    () -> assertEquals('o', Compass.WEST.getDirection()),
                    () -> assertEquals('u', Compass.UNKNOWN.getDirection())
            );
        }

        @Test
        @DisplayName("toString() should match direction character")
        void testToString() {
            assertAll(
                    () -> assertEquals("n", Compass.NORTH.toString()),
                    () -> assertEquals("s", Compass.SOUTH.toString()),
                    () -> assertEquals("e", Compass.EAST.toString()),
                    () -> assertEquals("o", Compass.WEST.toString()),
                    () -> assertEquals("u", Compass.UNKNOWN.toString())
            );
        }
    }

    // ───────────────────────────────────────────────
    @Nested
    @DisplayName("Static charToCompass() Method Tests")
    class CharToCompassTests {

        @ParameterizedTest(name = "char {0} -> Compass {1}")
        @CsvSource({
                "n, NORTH",
                "s, SOUTH",
                "e, EAST",
                "o, WEST",
                "x, UNKNOWN",
                "u, UNKNOWN"
        })
        @DisplayName("charToCompass converts char to correct Compass")
        void testCharToCompass(char input, String expectedEnumName) {
            Compass result = Compass.charToCompass(input);
            assertEquals(Compass.valueOf(expectedEnumName), result);
        }
    }
}
