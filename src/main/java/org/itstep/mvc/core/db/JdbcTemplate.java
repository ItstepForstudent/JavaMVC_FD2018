package org.itstep.mvc.core.db;

import org.itstep.mvc.core.annotation.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate {
    Connection connection;

    public JdbcTemplate() {
        try {
            connection = DatabaseHelper.getInstance().getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public<T> List<T> query(String query, List<Object> params, RowMapper<T> mapper){
        LinkedList<T> result = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0;i<params.size();i++){
                statement.setObject(i+1,params.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(mapper.map(resultSet));
            }
            return result;

        } catch (SQLException e) {
            return result;
        }
    }
    public<T> List<T> query(String query, RowMapper<T> mapper){
        LinkedList<T> result = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(mapper.map(resultSet));
            }
            return result;

        } catch (SQLException e) {
            return result;
        }
    }

    public void update(String query, List<Object> params){
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0;i<params.size();i++){
                statement.setObject(i+1,params.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) { }
    }

    public interface RowMapper<T> {
        T map(ResultSet set) throws SQLException;
    }

    public static class PropertyBeanRowMapper<T> implements RowMapper<T>{
        private Class<T> tClass;
        public PropertyBeanRowMapper(Class<T> clazz){
            tClass=clazz;
        }

        @Override
        public T map(ResultSet set) throws SQLException {

            try {
                T o = tClass.getConstructor().newInstance();

                Field[] fields = tClass.getDeclaredFields();
                for (Field f:fields){
                    String name = f.getName();
                    f.setAccessible(true);
                    f.set(o,set.getObject(name));
                }
                return o;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
