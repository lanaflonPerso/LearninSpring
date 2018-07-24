package ru.ivmiit.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ivmiit.mvc.models.Car;
import ru.ivmiit.mvc.models.User;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UsersDaoJdbcTemplateImpl implements UsersDao {


    private final String SQL_ALL_BY_NAME =
            "SELECT * FROM fix_user WHERE first_name = ?";

    //language=sql
    private final String SQL_SELECt_USER_WITH_CAR =
            "SELECT fix_user.*, fix_car.id as car_id, fix_car.model FROM fix_user LEFT JOIN fix_car ON fix_user.id = fix_car.owner_id WHERE fix_user.id = ?";


    private final String SQL_SELECT_USERS_WITH_CARS =
            "SELECT fix_user.*, fix_car.id as car_id, fix_car.model FROM fix_user LEFT JOIN fix_car ON fix_user.id = fix_car.owner_id";

    //language=sql
    private final String  SQL_SELECT_BY_ID = "SELECT * from  fix_user where id= :id";

    //language=sql
    private final String SQL_INSERT_USER = "" +
            "INSERT INTO fix_user(first_name, last_name) VALUES (:firstName, :lastName)";

    private JdbcTemplate template;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Map<Long, User> userMap = new HashMap<>();


    @Autowired
    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    private RowMapper<User> userRowMapperWithoutCars = (resultSet, i) -> User.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .build();

    private RowMapper<User> userRowMapper
            = (ResultSet resultSet, int i) -> {
        Long id = resultSet.getLong("id");

        if (!userMap.containsKey(id)) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            User user = new User(id, firstName, lastName, new ArrayList<>());
            userMap.put(id, user);
        }

        Car car = new Car(resultSet.getLong("car_id"),
                resultSet.getString("model"), userMap.get(id));

        userMap.get(id).getCars().add(car);

        return userMap.get(id);
    };


    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_ALL_BY_NAME, userRowMapperWithoutCars, firstName);
    }

    @Override
    public Optional<User> find(Long id) {
        Map<String, Object>  params = new HashMap<>();
        params.put("id", id);
       List<User> result = namedParameterJdbcTemplate.query(SQL_SELECT_BY_ID,params , userRowMapperWithoutCars);
       if(result.isEmpty()){
           return Optional.empty();
       }
       return Optional.of(result.get(0));
    }


    @Override
    public void save(User model) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", model.getFirstName());
        params.put("lastName", model.getLastName());
        namedParameterJdbcTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT_USERS_WITH_CARS, userRowMapper);
    }
}
