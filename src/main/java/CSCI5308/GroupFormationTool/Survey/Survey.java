package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.CourseInjector;
import CSCI5308.GroupFormationTool.Course.ICourse;
import CSCI5308.GroupFormationTool.Course.IUserCoursesRepository;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;

public class Survey implements ISurvey {

    private static final Logger log = LoggerFactory.getLogger(Survey.class.getName());

    private long id;

    private ICourse course;

    private String surveyDescription;

    private Date surveyStartDate;

    private Date surveyEndDate;

    private String published;

    private String surveyId;

    private String description;

    private String courseId;

    private ISurveyRepository surveyRepository;

    public Survey() {
        this.surveyId = null;
        this.description = null;
        this.courseId = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ICourse getCourse() {
        return course;
    }

    public void setCourse(ICourse course) {
        this.course = course;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public Date getSurveyStartDate() {
        return surveyStartDate;
    }

    public void setSurveyStartDate(Date surveyStartDate) {
        this.surveyStartDate = surveyStartDate;
    }

    public Date getSurveyEndDate() {
        return surveyEndDate;
    }

    public void setSurveyEndDate(Date surveyEndDate) {
        this.surveyEndDate = surveyEndDate;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String courseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean checkIfSurveyCreated(String courseId) {
        log.info("Checking if the survey is created for the course from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyCreated(courseId);
    }

    @Override
    public boolean checkIfSurveyPublished(String courseId) {
        log.info("Checking if the survey is published for the course from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyPublished(courseId);
    }

    @Override
    public boolean checkIfSurveyHasFormula(String courseId) {
        log.info("Checking if the survey has an algorithm for group formation for the course from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyHasFormula(courseId);
    }

    @Override
    public int checkIfGroupsCanBeFormedForSurvey(String courseId) {
        if (!checkIfSurveyCreated(courseId)) {
            return DomainConstants.surveyNotCreated;
        }
        if (!checkIfSurveyPublished(courseId)) {
            return DomainConstants.surveyNotPublished;
        }
        if (!checkIfSurveyHasFormula(courseId)) {
            return DomainConstants.surveyNotHavingAlgorithm;
        }
        return DomainConstants.surveyGroupFormationPossible;
    }

    @Override
    public boolean publishSurvey(String courseId) {
        log.info("Publishing the survey");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.publishSurvey(courseId);
    }

    @Override
    public int getSurveyIdByCourseId(String courseId) {
        log.info("Getting survey id based on course id");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyIdByCourseId(courseId);
    }

    public String getSurveyId(String courseId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyId(courseId);
    }

    public ArrayList<IQuestion> getSurveyQuestions(String surveyId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyQuestions(surveyId);
    }

    @Override
    public boolean isSurveyPublished(String courseId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        String surveyId = surveyRepository.getSurveyId(courseId);
        return surveyRepository.isSurveyPublished(surveyId);
    }

    @Override
    public boolean isSurveyCompleted(String courseId, String userId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        String surveyId = surveyRepository.getSurveyId(courseId);
        return surveyRepository.isSurveyCompleted(surveyId, userId);
    }

    public int createSurvey(String courseId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.createSurvey(courseId);
    }

    public boolean addQuestionToSurvey(long questionId, long surveyId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.addQuestionToSurvey(questionId, surveyId);
    }

    public ArrayList<IQuestion> getQuestionsForSurvey(String courseId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getQuestionsForSurvey(courseId);
    }

    public boolean deleteQuestionFromSurvey(long questionId, long surveyId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.deleteQuestionFromSurvey(questionId, surveyId);
    }

    @Override
    public ArrayList<IQuestion> getQuestionListForSurvey(String emailId, int surveyId, String courseId,
                                                         String questionTitle) {
        IUserCoursesRepository userCoursesRepository;
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        String userType = userCoursesRepository.getUserRoleByEmailId(emailId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        ArrayList<IQuestion> questionList = null;
        if (userType.equals(DomainConstants.instructorRole)) {
            questionList = surveyRepository.getSurveyQuestionListForInstructor(emailId, surveyId, questionTitle);
        } else if (userType == "TA") {
            ArrayList<IUser> instructorsList = userCoursesRepository.getInstructorsForCourse(courseId);
            ArrayList<Long> instructorIds = userAbstractFactory.createUserIdList();
            for (IUser user : instructorsList) {
                instructorIds.add(user.getId());
            }
            questionList = surveyRepository.getSurveyQuestionListForTA(instructorIds, surveyId, questionTitle);
        }
        return questionList;
    }
}
