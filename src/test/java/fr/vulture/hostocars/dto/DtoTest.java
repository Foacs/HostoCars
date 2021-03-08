package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validatePojo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Abstract test class for implementations of the {@link Dto} class.
 *
 * @param <D>
 *     The implementation class of the {@link Dto} class to test
 */
abstract class DtoTest<D extends Dto> {

    private static final String[] IDS = new String[] {"1", "2"};

    /**
     * Tests the {@link D} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(this.getTestClass());
    }

    /**
     * Returns the {@link Dto} class under test.
     *
     * @return the {@link Dto} class under test
     */
    abstract Class<D> getTestClass();

    /**
     * Tests the {@link Dto#equals(Object)} method's nullity.
     */
    @Test
    @DisplayName("Equals (nullity)")
    final void testEqualsNullity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the result
        assertNotEquals(x, null, "The equals method isn't null-proof");
    }

    /**
     * Creates a new instance of {@link Dto} with the default values.
     *
     * @return a new instance of {@link Dto}
     */
    private D createDefaultObject() {
        return this.createTestObject(Integer.valueOf(IDS[0]));
    }

    /**
     * Creates a new instance of {@link Dto} with the given values.
     *
     * @param id
     *     The ID to set
     *
     * @return a new instance of {@link Dto}
     */
    private D createTestObject(final Integer id) {
        final D testObject = this.constructTestObject();
        testObject.setId(id);
        return testObject;
    }

    /**
     * Creates a new instance of {@link Dto}.
     *
     * @return a new instance of {@link Dto}
     */
    abstract D constructTestObject();

    /**
     * Tests the {@link Dto#equals(Object)} method's identity.
     */
    @Test
    @DisplayName("Equals (identity)")
    final void testEqualsIdentity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the result
        assertNotEquals(x, new Object(), "The equals method isn't identity-proof");
    }

    /**
     * Tests the {@link Dto#equals(Object)} method's reflexivity.
     */
    @Test
    @DisplayName("Equals (reflexivity)")
    final void testEqualsReflexivity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the result
        assertEquals(x, x, "The equals method isn't reflexive");
    }

    /**
     * Tests the {@link Dto#hashCode()} method's reflexivity.
     */
    @Test
    @DisplayName("Hash code (reflexivity)")
    final void testHashCodeReflexivity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the result
        assertEquals(x.hashCode(), x.hashCode(), "The hashCode method isn't reflexive");
    }

    /**
     * Tests the {@link Dto#equals(Object)} method's symmetry.
     */
    @Test
    @DisplayName("Equals (symmetry)")
    final void testEqualsSymmetry() {
        // Prepares the inputs
        final D x = this.createDefaultObject();
        final D y = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't symmetry", () -> assertEquals(x, y), () -> assertEquals(y, x));
    }

    /**
     * Tests the {@link Dto#hashCode()} method's symmetry.
     */
    @Test
    @DisplayName("Hash code (symmetry)")
    final void testHashCodeSymmetry() {
        // Prepares the inputs
        final D x = this.createDefaultObject();
        final D y = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't symmetry", () -> assertEquals(x.hashCode(), y.hashCode()), () -> assertEquals(y.hashCode(), x.hashCode()));
    }

    /**
     * Tests the {@link Dto#equals(Object)} method's transitivity.
     */
    @Test
    @DisplayName("Equals (transitivity)")
    final void testEqualsTransitivity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();
        final D y = this.createDefaultObject();
        final D z = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't transitive", () -> assertEquals(x, y), () -> assertEquals(y, z), () -> assertEquals(x, z));
    }

    /**
     * Tests the {@link Dto#hashCode()} method's transitivity.
     */
    @Test
    @DisplayName("Hash code (transitivity)")
    final void testHashCodeTransitivity() {
        // Prepares the inputs
        final D x = this.createDefaultObject();
        final D y = this.createDefaultObject();
        final D z = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't transitive", () -> assertEquals(x.hashCode(), y.hashCode()), () -> assertEquals(y.hashCode(), z.hashCode()),
            () -> assertEquals(x.hashCode(), z.hashCode()));
    }

    /**
     * Tests the {@link Dto#equals(Object)} method's consistency.
     */
    @Test
    @DisplayName("Equals (consistency)")
    final void testEqualsConsistency() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't consistent", () -> assertNotEquals(x, this.createTestObject(Integer.valueOf(IDS[1]))),
            () -> assertNotEquals(x, this.createTestObject(null)));
    }

    /**
     * Tests the {@link Dto#hashCode()} method's consistency.
     */
    @Test
    @DisplayName("Hash code (consistency)")
    final void testHashCodeConsistency() {
        // Prepares the inputs
        final D x = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't consistent", () -> assertNotEquals(x.hashCode(), this.createTestObject(Integer.valueOf(IDS[1])).hashCode()),
            () -> assertNotEquals(x.hashCode(), this.createTestObject(null).hashCode()));
    }

}
