package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validatePojo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Intervention} class.
 */
@DisplayName("Intervention")
class InterventionTest {

    private static final String[] IDS = new String[] {"1", "2"};

    /**
     * Creates a new instance of {@link Intervention} with the default values.
     *
     * @return a new instance of {@link Intervention}
     */
    private static Intervention createDefaultObject() {
        return createTestObject(Integer.valueOf(IDS[0]));
    }

    /**
     * Creates a new instance of {@link Intervention} with the given values.
     *
     * @param id
     *     The ID to set
     *
     * @return a new instance of {@link Intervention}
     */
    private static Intervention createTestObject(final Integer id) {
        final Intervention testObject = new Intervention();
        testObject.setId(id);
        return testObject;
    }

    /**
     * Tests the {@link Intervention} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(Intervention.class);
    }

    /**
     * Tests the {@link Intervention#equals} method's nullity.
     */
    @Test
    @DisplayName("Equals (nullity)")
    final void testEqualsNullity() {
        // Prepares the inputs
        final Intervention x = createDefaultObject();

        // Calls the method and checks the result
        assertNotEquals(x, null, "The equals method isn't null-proof");
    }

    /**
     * Tests the {@link Intervention#equals} method's reflexivity.
     */
    @Test
    @DisplayName("Equals (reflexivity)")
    final void testEqualsReflexivity() {
        // Prepares the inputs
        final Intervention x = createDefaultObject();

        // Calls the method and checks the result
        assertEquals(x, x, "The equals method isn't reflexive");
    }

    /**
     * Tests the {@link Intervention#equals} method's symmetry.
     */
    @Test
    @DisplayName("Equals (symmetry)")
    final void testEqualsSymmetry() {
        // Prepares the inputs
        final Intervention x = createDefaultObject();
        final Intervention y = createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't symmetry", () -> assertEquals(x, y), () -> assertEquals(y, x));
    }

    /**
     * Tests the {@link Intervention#equals} method's transitivity.
     */
    @Test
    @DisplayName("Equals (transitivity)")
    final void testEqualsTransitivity() {
        // Prepares the inputs
        final Intervention x = createDefaultObject();
        final Intervention y = createDefaultObject();
        final Intervention z = createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't transitive", () -> assertEquals(x, y), () -> assertEquals(y, z), () -> assertEquals(x, z));
    }

    /**
     * Tests the {@link Intervention#equals} method's consistency.
     */
    @Test
    @DisplayName("Equals (consistency)")
    final void testEqualsConsistency() {
        // Prepares the inputs
        final Intervention x = createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't consistent", () -> assertNotEquals(x, createTestObject(Integer.valueOf(IDS[1]))), () -> assertNotEquals(x, createTestObject(null)));
    }

    /**
     * Tests the {@link Intervention#hashCode} method.
     */
    @Test
    @DisplayName("Hash code")
    final void testHashCode() {
        assertEquals(0, createDefaultObject().hashCode(), "Value different from expected");
    }

}
