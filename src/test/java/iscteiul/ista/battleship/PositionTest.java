package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test Suite for Position Class")
public class PositionTest {

    private Position position;

    @BeforeAll
    @DisplayName("Setting up test suite resources")
    void setupAll() {
        System.out.println("BeforeAll executed");
    }

    @AfterAll
    @DisplayName("Cleaning up test suite resources")
    void tearDownAll() {
        System.out.println("AfterAll executed");
    }

    @BeforeEach
    @DisplayName("Creating a default Position before each test")
    void setup() {
        position = new Position(3, 5);
    }

    @AfterEach
    @DisplayName("Cleaning up after each test")
    void tearDown() {
        position = null;
    }

    // ───────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Constructor & Getter Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor sets row and column correctly")
        void testConstructor() {
            assertAll(
                    () -> assertEquals(3, position.getRow()),
                    () -> assertEquals(5, position.getColumn())
            );
        }

        @ParameterizedTest
        @DisplayName("Parameterized test for valid row values")
        @ValueSource(ints = {0, 1, 5, 9})
        void testRowParameterized(int row) {
            Position p = new Position(row, 4);
            assertEquals(row, p.getRow());
        }
    }

    // ───────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Occupation & Hit State Tests")
    class StateTests {

        @Test
        @DisplayName("Position starts not occupied and not hit")
        void testInitialState() {
            assertAll(
                    () -> assertFalse(position.isOccupied()),
                    () -> assertFalse(position.isHit())
            );
        }

        @Test
        @DisplayName("Occupy() sets isOccupied to true")
        void testOccupy() {
            position.occupy();
            assertTrue(position.isOccupied());
        }

        @Test
        @DisplayName("Shoot() sets isHit to true")
        void testShoot() {
            position.shoot();
            assertTrue(position.isHit());
        }
    }

    // ───────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Adjacent Position Tests")
    class AdjacentTests {

        @Test
        @DisplayName("Position is adjacent to another within 1 cell")
        void testAdjacentTrue() {
            Position other = new Position(4, 6);
            assertTrue(position.isAdjacentTo(other));
        }

        @Test
        @DisplayName("Position is NOT adjacent when too far")
        void testAdjacentFalse() {
            Position other = new Position(10, 10);
            assertFalse(position.isAdjacentTo(other));
        }
    }

    // ───────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Equality, HashCode, and toString Tests")
    class EqualityHashToStringTests {

        @Test
        @DisplayName("Equal positions return true")
        void testEquals() {
            Position other = new Position(3, 5);
            assertEquals(position, other);
        }

        @Test
        @DisplayName("Different positions return false")
        void testNotEquals() {
            Position other = new Position(7, 2);
            assertNotEquals(position, other);
        }

        @Test
        @DisplayName("Equals returns false when argument is null")
        void testEqualsNull() {
            assertNotEquals(position, null);
        }

        @Test
        @DisplayName("Equals returns false when argument is not a Position")
        void testEqualsOtherType() {
            assertNotEquals(position, "NotAPosition");
        }

        @Test
        @DisplayName("Equal positions have same hashCode")
        void testHashCode() {
            Position other = new Position(3, 5);
            assertEquals(position.hashCode(), other.hashCode());
        }

        @Test
        @DisplayName("toString returns expected format")
        void testToString() {
            assertEquals("Linha = 3 Coluna = 5", position.toString());
        }
    }
}