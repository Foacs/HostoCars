package fr.foacs.hostocars.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.EqualsAndHashCodeMatchRule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoPrimitivesRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SerializableMustHaveSerialVersionUIDRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.rule.impl.TestClassMustBeProperlyNamedRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SerializableTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Abstract test class for implementations of the {@link AbstractEntity} class.
 *
 * @param <E>
 *     The implementation class of the {@link AbstractEntity} class to test
 */
abstract class AbstractEntityTest<E extends AbstractEntity> {

    private static final Validator pojoValidator = ValidatorBuilder.create()
        .with(new EqualsAndHashCodeMatchRule())
        .with(new GetterMustExistRule())
        .with(new GetterTester())
        .with(new NoFieldShadowingRule())
        .with(new NoNestedClassRule())
        .with(new NoPrimitivesRule())
        .with(new NoPublicFieldsRule())
        .with(new NoStaticExceptFinalRule())
        .with(new SerializableMustHaveSerialVersionUIDRule())
        .with(new SerializableTester())
        .with(new SetterMustExistRule())
        .with(new SetterTester())
        .with(new TestClassMustBeProperlyNamedRule())
        .build();

    private static final String[] IDS = new String[] {"1", "2"};

    /**
     * Tests the {@link E} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        pojoValidator.validate(PojoClassFactory.getPojoClass(this.getTestClass()));
    }

    /**
     * Returns the {@link AbstractEntity} class under test.
     *
     * @return the {@link AbstractEntity} class under test
     */
    abstract Class<E> getTestClass();

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's nullity.
     */
    @Test
    @DisplayName("Equals (nullity)")
    final void testEqualsNullity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the result
        assertNotEquals(x, null, "The equals method isn't null-proof");
    }

    /**
     * Creates a new instance of {@link AbstractEntity} with the default values.
     *
     * @return a new instance of {@link AbstractEntity}
     */
    private E createDefaultObject() {
        return this.createTestObject(Integer.valueOf(IDS[0]));
    }

    /**
     * Tests the {@link AbstractEntity#getId()} method.
     */
    @Test
    @DisplayName("Get ID")
    final void testGetId() {
        // Prepares the inputs
        final Integer id = 1;
        final var x = this.createTestObject(id);

        // Calls the method and checks the result
        assertEquals(id, x.getId(), "The ID is different from expected");
    }

    /**
     * Creates a new instance of {@link AbstractEntity}.
     *
     * @return a new instance of {@link AbstractEntity}
     */
    abstract E constructTestObject();

    /**
     * Creates a new instance of {@link AbstractEntity} with the given values.
     *
     * @param id
     *     The ID to set
     *
     * @return a new instance of {@link AbstractEntity}
     */
    private E createTestObject(final Integer id) {
        final var testObject = this.constructTestObject();
        testObject.setId(id);
        return testObject;
    }

    /**
     * Tests the {@link AbstractEntity#setId(Integer)} method.
     */
    @Test
    @DisplayName("Set ID")
    final void testSetId() {
        // Prepares the inputs
        final Integer id = 1;
        final var x = this.constructTestObject();

        // Call the method
        x.setId(id);

        // Checks the result
        assertEquals(id, x.getId(), "The ID is different from expected");
    }

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's identity.
     */
    @Test
    @DisplayName("Equals (identity)")
    final void testEqualsIdentity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the result
        assertNotEquals(x, new Object(), "The equals method isn't identity-proof");
    }

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's reflexivity.
     */
    @Test
    @DisplayName("Equals (reflexivity)")
    final void testEqualsReflexivity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the result
        assertEquals(x, x, "The equals method isn't reflexive");
    }

    /**
     * Tests the {@link AbstractEntity#hashCode()} method's reflexivity.
     */
    @Test
    @DisplayName("Hash code (reflexivity)")
    final void testHashCodeReflexivity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the result
        assertEquals(x.hashCode(), x.hashCode(), "The hashCode method isn't reflexive");
    }

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's symmetry.
     */
    @Test
    @DisplayName("Equals (symmetry)")
    final void testEqualsSymmetry() {
        // Prepares the inputs
        final var x = this.createDefaultObject();
        final var y = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't symmetry", () -> assertEquals(x, y), () -> assertEquals(y, x));
    }

    /**
     * Tests the {@link AbstractEntity#hashCode()} method's symmetry.
     */
    @Test
    @DisplayName("Hash code (symmetry)")
    final void testHashCodeSymmetry() {
        // Prepares the inputs
        final var x = this.createDefaultObject();
        final var y = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't symmetry", () -> assertEquals(x.hashCode(), y.hashCode()), () -> assertEquals(y.hashCode(), x.hashCode()));
    }

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's transitivity.
     */
    @Test
    @DisplayName("Equals (transitivity)")
    final void testEqualsTransitivity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();
        final var y = this.createDefaultObject();
        final var z = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't transitive", () -> assertEquals(x, y), () -> assertEquals(y, z), () -> assertEquals(x, z));
    }

    /**
     * Tests the {@link AbstractEntity#hashCode()} method's transitivity.
     */
    @Test
    @DisplayName("Hash code (transitivity)")
    final void testHashCodeTransitivity() {
        // Prepares the inputs
        final var x = this.createDefaultObject();
        final var y = this.createDefaultObject();
        final var z = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't transitive", () -> assertEquals(x.hashCode(), y.hashCode()), () -> assertEquals(y.hashCode(), z.hashCode()),
            () -> assertEquals(x.hashCode(), z.hashCode()));
    }

    /**
     * Tests the {@link AbstractEntity#equals(Object)} method's consistency.
     */
    @Test
    @DisplayName("Equals (consistency)")
    final void testEqualsConsistency() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The equals method isn't consistent", () -> assertNotEquals(x, this.createTestObject(Integer.valueOf(IDS[1]))),
            () -> assertNotEquals(x, this.createTestObject(null)));
    }

    /**
     * Tests the {@link AbstractEntity#hashCode()} method's consistency.
     */
    @Test
    @DisplayName("Hash code (consistency)")
    final void testHashCodeConsistency() {
        // Prepares the inputs
        final var x = this.createDefaultObject();

        // Calls the method and checks the results
        assertAll("The hashCode method isn't consistent", () -> assertNotEquals(x.hashCode(), this.createTestObject(Integer.valueOf(IDS[1])).hashCode()),
            () -> assertNotEquals(x.hashCode(), this.createTestObject(null).hashCode()));
    }

}
