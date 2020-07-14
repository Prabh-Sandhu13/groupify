package CSCI5308.GroupFormationTool.Survey;

import java.sql.Date;
import java.util.ArrayList;

import CSCI5308.GroupFormationTool.Common.Injector;
import CSCI5308.GroupFormationTool.Course.ICourse;
import CSCI5308.GroupFormationTool.Question.IQuestion;

public class Survey implements ISurvey {
    private long id;

    private ICourse course;

    private String survey_description;

    private Date survey_start_date;

    private Date survey_end_date;

    private String published;
    
	private ISurveyRepository surveyRepository;
	
	public String getSurveyId(String courseId) {
		surveyRepository = Injector.instance().getSurveyRepository();
		return surveyRepository.getSurveyId(courseId);
	}
	public ArrayList<IQuestion> getSurveyQuestions(String surveyId) {
		surveyRepository = Injector.instance().getSurveyRepository();
		return surveyRepository.getSurveyQuestions(surveyId);
	}
	
}