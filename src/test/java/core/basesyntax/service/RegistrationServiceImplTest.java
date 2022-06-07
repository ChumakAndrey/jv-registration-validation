package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_VALID = "Alice";
    private static final String NULL = null;
    private static final String EMPTY_STRING = "";
    private static final String ONLY_WHITESPACES = "       ";

    private static final String PASSWORD_VALID = "123456";
    private static final String PASSWORD_LESS_6 = "12345";

    private static final Integer AGE_VALID = 18;
    private static final Integer AGE_INVALID_LESS_18 = 17;

    private static RegistrationService regService;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_passwordMore6Char_ok() {
        User expectedUser = new User();
        expectedUser.setLogin(LOGIN_VALID);
        expectedUser.setPassword(PASSWORD_VALID);
        expectedUser.setAge(AGE_VALID);
        User actual = regService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_addTheSameUser_notOk() {
        User validUser1 = new User();
        validUser1.setLogin(LOGIN_VALID);
        validUser1.setPassword(PASSWORD_VALID);
        validUser1.setAge(AGE_VALID);

        regService.register(validUser1);
        assertThrows(RuntimeException.class, () -> {
            regService.register(validUser1);
        });
    }

    @Test
    void register_passwordLess6Char_notOk() {
        User userWithShortPass = new User();
        userWithShortPass.setPassword(PASSWORD_LESS_6);
        userWithShortPass.setAge(AGE_VALID);
        userWithShortPass.setLogin(LOGIN_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithShortPass);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User userWithNullPass = new User();
        userWithNullPass.setLogin(LOGIN_VALID);
        userWithNullPass.setPassword(NULL);
        userWithNullPass.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullPass);
        });
    }

    @Test
    void register_passwordEmpty_notOk() {
        User userWithEmptyPass = new User();
        userWithEmptyPass.setLogin(LOGIN_VALID);
        userWithEmptyPass.setPassword(EMPTY_STRING);
        userWithEmptyPass.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithEmptyPass);
        });
    }

    @Test
    void register_passwordWhitespacesOnly_notOk() {
        User userWithEmptyPass = new User();
        userWithEmptyPass.setLogin(LOGIN_VALID);
        userWithEmptyPass.setPassword(ONLY_WHITESPACES);
        userWithEmptyPass.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithEmptyPass);
        });
    }

    @Test
    void register_loginEmpty_notOk() {
        User userWithEmptyLogin = new User();
        userWithEmptyLogin.setLogin(EMPTY_STRING);
        userWithEmptyLogin.setPassword(PASSWORD_VALID);
        userWithEmptyLogin.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithEmptyLogin);
        });
    }

    @Test
    void register_loginNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setLogin(NULL);
        userWithNullLogin.setPassword(PASSWORD_VALID);
        userWithNullLogin.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullLogin);
        });
    }

    @Test
    void register_loginWhitespacesOnly_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setLogin(ONLY_WHITESPACES);
        userWithNullLogin.setPassword(PASSWORD_VALID);
        userWithNullLogin.setAge(AGE_VALID);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullLogin);
        });
    }

    @Test
    void register_less18_notOk() {
        User userAgeLess18 = new User();
        userAgeLess18.setLogin(ONLY_WHITESPACES);
        userAgeLess18.setPassword(PASSWORD_VALID);
        userAgeLess18.setAge(AGE_INVALID_LESS_18);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userAgeLess18);
        });
    }

    @Test
    void register_ageNull_notOk() {
        User userAgeLess18 = new User();
        userAgeLess18.setLogin(ONLY_WHITESPACES);
        userAgeLess18.setPassword(PASSWORD_VALID);
        userAgeLess18.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userAgeLess18);
        });
    }
}
