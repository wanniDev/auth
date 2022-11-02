package me.spring.auth.common.validation

import org.springframework.util.StringUtils
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordValidator : ConstraintValidator<Password, String> {
    private var min = 0
    private var max = 0

    override fun initialize(constraintAnnotation: Password?) {
        this.min = constraintAnnotation!!.min
        this.max = constraintAnnotation.max
    }

    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean {
        if (!StringUtils.hasText(password))
            return sendViolation(context, "Password must be filled.")
        if (isValidPasswdLength(password))
            return sendViolation(context, "Password must be between ${this.min} and ${this.max} characters..!")
        return password!!
            .matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{${this.min},${this.max}}$".toRegex())
    }

    private fun sendViolation(context: ConstraintValidatorContext?, msg: String): Boolean {
        context!!.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msg)
            .addConstraintViolation();
        return false
    }

    private fun isValidPasswdLength(password: String?): Boolean {
        val length = password!!.length
        return length < min || length > max
    }

}