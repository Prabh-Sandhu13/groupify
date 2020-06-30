package CSCI5308.GroupFormationTool.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CSCI5308.GroupFormationTool.AccessControl.IChoice;
import CSCI5308.GroupFormationTool.AccessControl.IQuestion;
import CSCI5308.GroupFormationTool.AccessControl.IQuestionAdminRepository;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import CSCI5308.GroupFormationTool.Model.Choice;
import CSCI5308.GroupFormationTool.Model.Question;

public class QuestionAdminRepository implements IQuestionAdminRepository{

    @Override
    public ArrayList<IQuestion> getQuestionListForInstructor(String emailId) {

        StoredProcedure storedProcedure = null;
        ArrayList<IQuestion> questionList = new ArrayList<IQuestion>();
        try {
            storedProcedure = new StoredProcedure("sp_getQuestionsForInstructor(?)");
            storedProcedure.setInputStringParameter(1, emailId);

            ResultSet results = storedProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = new Question();
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                        questionList.add(question);
                    }
                }
            }
        } catch (SQLException ex) {
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }
    

    @Override
    public IQuestion getQuestionById(long questionId) {
        StoredProcedure storedProcedure = null;
        IQuestion question = new Question();
        try {
            storedProcedure = new StoredProcedure("sp_getQuestionById(?)");
            storedProcedure.setInputIntParameter(1, questionId);
            ResultSet results = storedProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    {
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                    }
                }
            }

        } catch (SQLException ex) {
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return question;
    }

    @Override
    public ArrayList<IChoice> getOptionsForTheQuestion(long questionId) {

        StoredProcedure storedProcedure = null;
        ArrayList<IChoice> choiceList = new ArrayList<>();
        try {
            storedProcedure = new StoredProcedure("sp_getOptionsForQuestion(?)");
            storedProcedure.setInputIntParameter(1, questionId);
            ResultSet results = storedProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    {

                        IChoice choice = new Choice();
                        choice.setText(results.getString("options_text"));
                        choice.setValue(results.getInt("options_value"));
                        choiceList.add(choice);

                    }
                }
            }

        } catch (SQLException ex) {
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return choiceList;
    }


	@Override

    public ArrayList<IQuestion> getSortedQuestionListForInstructor(String emailId, String sortBy) {

        StoredProcedure storedProcedure = null;
        ArrayList<IQuestion> questionList = new ArrayList<IQuestion>();
        try {
            storedProcedure = new StoredProcedure("sp_getSortedQuestionsForInstructor(?,?)");
            storedProcedure.setInputStringParameter(1, emailId);
            storedProcedure.setInputStringParameter(2, sortBy);

            ResultSet results = storedProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = new Question();
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                        questionList.add(question);
                    }
                }
            }

        } catch (SQLException ex) {

        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }

}
