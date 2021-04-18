package fr.foacs.hostocars;

import static java.util.Objects.isNull;

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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Helper class for unit tests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestHelper {

    private static Validator pojoValidator;

    /**
     * Validates a POJO class.
     *
     * @param pojoClass
     *     The POJO class to validate
     */
    public static void validatePojo(final Class pojoClass) {
        getPojoValidator().validate(PojoClassFactory.getPojoClass(pojoClass));
    }

    /**
     * Returns the static POJO validator after initializing it if necessary.
     *
     * @return a POJO validator
     */
    private static Validator getPojoValidator() {
        if (isNull(pojoValidator)) {
            pojoValidator = ValidatorBuilder.create()
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
        }

        return pojoValidator;
    }

}
