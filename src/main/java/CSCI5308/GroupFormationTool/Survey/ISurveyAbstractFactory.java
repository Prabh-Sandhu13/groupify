package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Question.IQuestion;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISurveyAbstractFactory {

    ISurvey createSurveyInstance();

    IResponse createResponseInstance();

    ArrayList<IResponse> createResponseListInstance();

    SurveyRepository createSurveyRepositoryInstance();

    ResponseRepository createResponseRepositoryInstance();

    ArrayList<IQuestion> createSurveyQuestionListInstance();

    HashMap<Long, HashMap<Long, IResponse>> createAllStudentResponsesInstance();

    HashMap<Long, IResponse> createQuestionResponseInstance();

}
