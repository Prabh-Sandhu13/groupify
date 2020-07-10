package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Common.Injector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryptor;
import org.apache.commons.text.RandomStringGenerator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StudentRepository implements IStudentRepository {

    @Override
    public Map<Integer, List<StudentCSV>> createStudent(List<StudentCSV> studentCSVList, String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = Injector.instance().getDatabaseAbstractFactory();
        ICourseAbstractFactory courseAbstractFactory = Injector.instance().getCourseAbstractFactory();
        List<StudentCSV> newStudents = courseAbstractFactory.createStudentCSVListInstance();
        List<StudentCSV> oldStudents = courseAbstractFactory.createStudentCSVListInstance();
        IPasswordEncryptor encryptor = Injector.instance().getPasswordEncryptor();
        RandomStringGenerator pwdGenerator = courseAbstractFactory.createRandomStringGeneratorInstance();
        Map<Integer, List<StudentCSV>> studentLists = courseAbstractFactory.createStudentHashMapInstance();
        StoredProcedure storedProcedure = null;

        for (StudentCSV studentCSV : studentCSVList) {
            try {
                storedProcedure = databaseAbstractFactory.
                        createStoredProcedureInstance("sp_createStudentFromCSV(?,?,?,?,?,?,?)");
                storedProcedure.setInputStringParameter(1, studentCSV.getBannerId());
                storedProcedure.setInputStringParameter(2, studentCSV.getFirstName());
                storedProcedure.setInputStringParameter(3, studentCSV.getLastName());
                storedProcedure.setInputStringParameter(4, studentCSV.getEmail());
                String password = pwdGenerator.generate(10);
                studentCSV.setPassword(password);
                storedProcedure.setInputStringParameter(5, encryptor.encoder(password));
                storedProcedure.setInputStringParameter(6, courseId);
                storedProcedure.registerOutputParameterBoolean(7);
                storedProcedure.execute();
                Boolean studentStatus = storedProcedure.getParameter(7);
                if (studentStatus) {
                    newStudents.add(studentCSV);
                } else {
                    oldStudents.add(studentCSV);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (storedProcedure != null) {
                    storedProcedure.removeConnections();
                }
            }
        }
        studentLists.put(DomainConstants.newStudents, newStudents);
        studentLists.put(DomainConstants.oldStudents, oldStudents);
        return studentLists;
    }

}
