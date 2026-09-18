package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static User validUser;
    private static final String EMPTY_STRING = "";
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_LOGIN = "user_login";
    private static final int VALID_AGE = 18;
    private static final String INVALID_PASSWORD_OR_LOGIN1 = "p";
    private static final String INVALID_PASSWORD_OR_LOGIN2 = "pa";
    private static final String INVALID_PASSWORD_OR_LOGIN3 = "pas";
    private static final String INVALID_PASSWORD_OR_LOGIN4 = "pass";
    private static final String INVALID_PASSWORD_OR_LOGIN5 = "users";
    private static final String ERROR_MESSAGE_TEMPLATE = "Storage must have been rejected"
            + " adding user with ";

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void register_EmptyUser_notOk() {
        assertThrows(InvalidUserData.class, () -> service.register(new User()));
    }

    @Test
    void register_loginNull_notOk() {
        validUser.setLogin(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_InvalidLogin_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "login ";

        validUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + EMPTY_STRING);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN1);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN1);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN2);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN2);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN3);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN3);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN4);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN4);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN5);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN5);
    }

    @Test
    void register_passwordNull_notOk() {
        validUser.setPassword(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_InvalidPassword_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "password ";

        validUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + EMPTY_STRING);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN1);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN1);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN2);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN2);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN3);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN3);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN4);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN4);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN5);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN5);
    }

    @Test
    void register_ageNull_notOk() {
        validUser.setAge(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_InvalidAge_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "age ";
        final int negativeAge = -1;
        final int ageUnder18 = 17;
        final int maxAge = 126;

        validUser.setAge(negativeAge);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + negativeAge + ". Age can't be less than 0");

        validUser.setAge(ageUnder18);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + ageUnder18 + ". Age must be at least 18 years old");

        validUser.setAge(maxAge);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + maxAge + ". Age can't be at higher than 125");

    }

    @Test
    void register_missingLogin_notOk() {
        validUser.setLogin(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " login");
    }

    @Test
    void register_noPassword_notOk() {
        validUser.setPassword(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " password");
    }

    @Test
    void register_noAge_notOk() {
        validUser.setAge(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " age");
    }

    @Test
    void register_userWithSameLogin_notOk() {
        service.register(validUser);

        assertThrows(UserAlreadyExist.class, () -> service.register(validUser));
    }

    @Test
    void register_validUserHasID_ok() {
        User newUser = service.register(validUser);
        assertNotNull(newUser.getId(),
                "User must have ID");
    }

    @Test
    void register_validUser_ok() {
        User newUser = service.register(validUser);
        assertEquals(validUser, newUser,
                "Added user and user in storage are not equal");
    }
}
