package CSCI5308.GroupFormationTool.Service;

import CSCI5308.GroupFormationTool.AccessControl.IChoice;
import CSCI5308.GroupFormationTool.AccessControl.IQuestion;
import CSCI5308.GroupFormationTool.AccessControl.IQuestionManagerRepository;
import CSCI5308.GroupFormationTool.AccessControl.IQuestionManagerService;
import CSCI5308.GroupFormationTool.DomainConstants;
import CSCI5308.GroupFormationTool.Injector;
import CSCI5308.GroupFormationTool.Model.Choice;

import java.util.*;

public class QuestionManagerService implements IQuestionManagerService {
    IQuestionManagerRepository questionManagerRepository;

    @Override
    public long createQuestion(IQuestion question, List<String> optionText, List<String> optionValue) {
        int type = question.getType();
        Set<String> optionTextSet = new HashSet<>();
        Set<String> optionValueSet = new HashSet<>();

        if (checkIfInvalid(question.getTitle(), question.getText(), type, optionText, optionValue)) {
            return DomainConstants.invalidData;
        } else {
            ArrayList<IChoice> choices = new ArrayList<>();
            for (String text : optionText) {
                optionTextSet.add(text);
            }
            for (String value : optionValue) {
                optionValueSet.add(value);
            }

            if (type == DomainConstants.MCQMultiple || type == DomainConstants.MCQOne) {

                if (optionTextSet.size() == optionValueSet.size()) {
                    Iterator<String> optionTextIterator = optionTextSet.iterator();
                    Iterator<String> optionValueIterator = optionValueSet.iterator();

                    while (optionTextIterator.hasNext() && optionValueIterator.hasNext()) {
                        IChoice choice = new Choice();
                        choice.setText(optionTextIterator.next());
                        choice.setValue(Integer.parseInt(optionValueIterator.next()));
                        choices.add(choice);
                    }
                } else {
                    for (int i = 0; i < optionText.size(); i++) {
                        IChoice choice = new Choice();
                        choice.setText(optionText.get(i));
                        choice.setValue(Integer.parseInt(optionValue.get(i)));
                        choices.add(choice);
                    }
                }
                question.setChoices(choices);
            } else {
                question.setChoices(null);
            }
            questionManagerRepository = Injector.instance().getQuestionManagerRepository();
            return questionManagerRepository.createQuestion(question);
        }
    }

    private boolean checkIfInvalid(String title, String text, int type, List<String> optionText,
                                   List<String> optionValue) {

        if (title == null || title.isEmpty() || text == null || text.isEmpty()) {
            return true;
        }
        if (type == DomainConstants.MCQMultiple || type == DomainConstants.MCQOne) {
            if (optionText == null || optionText.isEmpty() || optionText.contains("") || optionValue == null
                    || optionValue.isEmpty() || optionValue.contains("")) {
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean deleteQuestion(long questionId) {
        questionManagerRepository = Injector.instance().getQuestionManagerRepository();
        return questionManagerRepository.deleteQuestion(questionId);
    }


}
