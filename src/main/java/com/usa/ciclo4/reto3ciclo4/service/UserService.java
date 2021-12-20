package com.usa.ciclo4.reto3ciclo4.service;

import com.usa.ciclo4.reto3ciclo4.model.User;
import com.usa.ciclo4.reto3ciclo4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class User Service
 * @author Ronald
 */
@Service

public class UserService {
    /**
     * Objeto de clase UserRepository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Metodo Get para obtener todos los datos de los usuarios.
     * @return Lista de usuarios.
     */
    public List<User> getAll() {
        return userRepository.getAll();
    }

    /**
     * Metodo que obtiene usuario buscado por id.
     * @param id parametro.
     * @return usuario.
     */
    public Optional<User> getUser(int id) {
        return userRepository.getUser(id);
    }

    /**
     * Metodo que valida email.
     * @param email recibe el correo.
     * @return true o false si existe o no el usuario.
     */
    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }

    /**
     * Metodo GET para realizar consulta de validacion.
     * @param email recibe el correo.
     * @param password recibe la contrasena.
     * @return datos del usuario no nulos.
     */
    public User authenticateUser(String email, String password){
        Optional<User> user = userRepository.authenticateUser(email, password);
        if (user.isEmpty()){
            return new User();
        }
        return user.get();
    }

    /**
     * Metodo Post para el ingreso de datos para el usuario.
     * @param user recibe datos.
     * @return datos del usuario ingresado.
     */
    public User save(User user) {
        if (user.getId() == null) {
            return user;
        } else {
            Optional<User> dbUser = userRepository.getUser(user.getId());
            if (dbUser.isEmpty()) {
                if (emailExists(user.getEmail()) == false) {
                    return userRepository.save(user);
                } else {
                    return user;
                }
            } else {
                return user;
            }
        }
    }

    /**
     * Metodo que sirve para actualizar registros.
     * @param user Parametro que permite obtener valores del usuario.
     * @return datos actualizados.
     */
    public User update(User user) {
        if (user.getId() != null) {
            Optional<User> dbUser = userRepository.getUser(user.getId());
            if (!dbUser.isEmpty()) {
                if (user.getIdentification() != null) {
                    dbUser.get().setIdentification(user.getIdentification());
                }
                if (user.getName() != null) {
                    dbUser.get().setName(user.getName());
                }

                if (user.getBirthtDay() != null){
                    dbUser.get().setBirthtDay(user.getBirthtDay());
                }

                if (user.getMonthBirthtDay() != null){
                    dbUser.get().setMonthBirthtDay(user.getMonthBirthtDay());
                }
                if (user.getAddress() != null) {
                    dbUser.get().setAddress(user.getAddress());
                }
                if (user.getCellPhone() != null) {
                    dbUser.get().setCellPhone(user.getCellPhone());
                }
                if (user.getEmail() != null) {
                    dbUser.get().setEmail(user.getEmail());
                }
                if (user.getPassword() != null) {
                    dbUser.get().setPassword(user.getPassword());
                }
                if (user.getZone() != null) {
                    dbUser.get().setZone(user.getZone());
                }
                if (user.getType() != null) {
                    dbUser.get().setType(user.getType());
                }
                userRepository.update(dbUser.get());
                return dbUser.get();
            } else {
                return user;
            }
        }
        return user;
    }

    /**
     * Metodo que permite eliminar registros.
     * @param userId parametro.
     * @return true o false.
     */
    public boolean delete(int userId) {
        Boolean userBoolean = getUser(userId).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
        return userBoolean;
    }

}
