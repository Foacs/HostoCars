package fr.vulture.hostocars;

import static com.openpojo.validation.ValidatorBuilder.create;
import static java.util.Objects.isNull;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
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
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SerializableTester;
import com.openpojo.validation.test.impl.SetterTester;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Helper class for unit tests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestHelper {

    private static Validator beanValidator;

    /**
     * Validates a bean class.
     *
     * @param beanClass
     *     The bean class to validate
     */
    public static void validateBean(@NonNull final Class beanClass) {
        getBeanValidator().validate(PojoClassFactory.getPojoClass(beanClass));
    }

    /**
     * Returns the static bean validator after initializing it if necessary.
     *
     * @return a bean validator
     */
    private static Validator getBeanValidator() {
        if (isNull(beanValidator)) {
            beanValidator = create().with(new DefaultValuesNullTester())
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

        return beanValidator;
    }

}
