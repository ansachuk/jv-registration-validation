package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserData("User login can't be null");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidUserData("User login must be at least 6 symbols");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserData("User password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidUserData("User password must be at least 6 symbols");
        }

        if (user.getAge() == null) {
            throw new InvalidUserData("User age can't be null");
        }

        if (user.getAge() < 18) {
            throw new InvalidUserData("User age must be at least 18");
        }

        if (user.getAge() > 125) {
            throw new InvalidUserData("User age must be under 125");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExist("User with this login already exist");
        }

        return storageDao.add(user);
    }
}
