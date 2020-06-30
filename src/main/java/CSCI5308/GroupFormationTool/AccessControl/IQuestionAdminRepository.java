package CSCI5308.GroupFormationTool.AccessControl;

import java.util.ArrayList;

public interface IQuestionAdminRepository {
	ArrayList<IQuestion> getQuestionListForInstructor(String emailId);
	ArrayList<IQuestion> getSortedQuestionListForInstructor(String emailId, String sortBy);
	IQuestion getQuestionById(long questionId);
	ArrayList<IChoice> getOptionsForTheQuestion(long questionId);
}
