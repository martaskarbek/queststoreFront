package com.codecool.queststore.dao;

import com.codecool.queststore.models.Module;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO implements IModuleDAO {


    private Connector connector;

    public ModuleDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(Module module) {

    }

    @Override
    public void edit(Module module) {

    }

    @Override
    public void remove(Module module) {

    }

    @Override
    public List<Module> getAll() throws Exception {
        return null;
    }

    @Override
    public Module get(int id) {
        return null;
    }

    public List<Module> getMentorModules(int mentorId) {
        List<Module> mentorModules = new ArrayList<>();

        try {
            connector.connect();
            PreparedStatement preparedStatement = connector.connection.prepareStatement("select modules.id, modules.name\n" +
                    "from modules, mentor_modules\n" +
                    "where modules.id=mentor_modules.module_id\n" +
                    "and mentor_id=?;");
            preparedStatement.setInt(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Module module = create(resultSet);
                mentorModules.add(module);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorModules;
    }

    private Module create(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        Module module = new Module(id,name);

        return module;
        }


}
