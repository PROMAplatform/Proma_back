package ai.platform.proma.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (value == null || value.isEmpty()) {
//            return true;
//        }
//
//        return Arrays.stream(enumClass.getEnumConstants())
//                .anyMatch(e -> e.name().equals(value));
//    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumConstant -> {
                    try {
                        Object displayName = enumClass.getMethod("toString").invoke(enumConstant);
                        return displayName.toString().equalsIgnoreCase(value);
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
}
