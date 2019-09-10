package fr.vulture.hostocars;

import static java.util.Objects.isNull;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Helper class for testing.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestHelper {

    private static Validator beanValidator;

    /**
     * Validates a bean class.
     *
     * @param beanClass
     *     The bean class to validate
     */
    public static void validateBean(@NotNull final Class beanClass) {
        getBeanValidator().validate(PojoClassFactory.getPojoClass(beanClass));
    }

    /**
     * Returns the static bean validator after initializing it if necessary.
     *
     * @return a bean validator
     */
    private static Validator getBeanValidator() {
        if (isNull(beanValidator)) {
            beanValidator = ValidatorBuilder.create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        }

        return beanValidator;
    }

}
